package org.waddys.xcloud.event.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SerializerMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.waddys.xcloud.event.api.EventController;
import org.waddys.xcloud.event.api.EventProcesser;
import org.waddys.xcloud.event.api.SerializeI;
import org.waddys.xcloud.event.type.ConnectionInfo;
import org.waddys.xcloud.event.type.EventMessage;
import org.waddys.xcloud.event.type.ExchangeName;
import org.waddys.xcloud.event.type.RecvFaultException;
import org.waddys.xcloud.event.type.SendRefuseException;
import org.waddys.xcloud.event.util.StringUtils;

/**
 * 和rabbitmq通信的控制器，主要负责：
 * <ol>
 * <li>和rabbitmq建立连接</li>
 * <li>声明exChange和queue以及它们的绑定关系</li>
 * <li>启动消息监听容器，并将不同消息的处理者绑定到对应的exchange和queue上</li>
 * <li>持有消息发送模版以及所有exchange、queue和绑定关系的本地缓存</li>
 * </ol>
 */
@Service("TopicEventController")
public class TopicEventController implements EventController {

    private static final Logger logger = Logger.getLogger(TopicEventController.class);

    @Autowired
    private ConnectionInfo config;

    private CachingConnectionFactory rabbitConnectionFactory;
    private RabbitAdmin rabbitAdmin;
    private RabbitTemplate rabbitTemplate;
    private SerializeI defaultCodecFactory = new HessionSerializeImpl();
    // rabbitMQ msg listener container
    private SimpleMessageListenerContainer msgListenerContainer;
    private MessageAdapterHandler msgAdapterHandler = new MessageAdapterHandler();
    // 直接指定
    private MessageConverter serializerMessageConverter = new SerializerMessageConverter();
    // queue cache, key is exchangeName
    private Map<String, TopicExchange> exchanges = new HashMap<String, TopicExchange>();
    // queue cache, key is queueName
    private Map<String, Queue> queues = new HashMap<String, Queue>();
    // bind relation of queue to exchange cache, value is exchangeName |
    // queueName
    private Set<String> binded = new HashSet<String>();
    private AtomicBoolean isStarted = new AtomicBoolean(false);

    public TopicEventController() {
    }

    public TopicEventController(ConnectionInfo config) {
        this.config = config;
        configTopicEventController();
    }

    @PostConstruct
    private void configTopicEventController() {
        if (config == null) {
            throw new IllegalArgumentException("Config can not be null.");
        }
        logger.debug("TopicEventController: " + config.getServerHost());
        initRabbitConnectionFactory();
        // 初始化AmqpAdmin
        rabbitAdmin = new RabbitAdmin(rabbitConnectionFactory);
        // 初始化RabbitTemplate
        rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory);
        rabbitTemplate.setMessageConverter(serializerMessageConverter);
    }

    /**
     * 初始化rabbitmq连接
     */
    private void initRabbitConnectionFactory() {
        rabbitConnectionFactory = new CachingConnectionFactory();
        rabbitConnectionFactory.setHost(config.getServerHost());
        rabbitConnectionFactory.setChannelCacheSize(config.getEventMsgProcessNum());
        rabbitConnectionFactory.setPort(config.getPort());
        rabbitConnectionFactory.setUsername(config.getUsername());
        rabbitConnectionFactory.setPassword(config.getPassword());
        if (!StringUtils.isEmpty(config.getVirtualHost())) {
            rabbitConnectionFactory.setVirtualHost(config.getVirtualHost());
        }
    }

    /**
     * 注销程序
     */
    @Override
    public synchronized void stop() {
        if (!isStarted.get()) {
            return;
        }
        msgListenerContainer.stop();
        rabbitAdmin = null;
        rabbitConnectionFactory.destroy();
    }

    @Override
    public void start() {

        if (isStarted.get()) {
            return;
        }
        Set<String> mapping = msgAdapterHandler.getAllBinding();
        for (String relation : mapping) {
            String[] relaArr = relation.split("\\|");
            declareBinding(relaArr[1], relaArr[0]);
        }
        initMsgListenerAdapter();
        isStarted.set(true);

    }

    /**
     * 初始化消息监听器容器
     */
    private void initMsgListenerAdapter() {
        MessageListener listener = new MessageListenerAdapter(msgAdapterHandler, serializerMessageConverter);
        msgListenerContainer = new SimpleMessageListenerContainer();
        msgListenerContainer.setConnectionFactory(rabbitConnectionFactory);
        msgListenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        msgListenerContainer.setMessageListener(listener);
        msgListenerContainer.setErrorHandler(new MessageErrorHandler());
        msgListenerContainer.setPrefetchCount(config.getPrefetchSize()); // 设置每个消费者消息的预取值
        msgListenerContainer.setConcurrentConsumers(config.getEventMsgProcessNum());
        msgListenerContainer.setTxSize(config.getPrefetchSize());// 设置有事务时处理的消息数
        msgListenerContainer.setQueues(queues.values().toArray(new Queue[queues.size()]));
        msgListenerContainer.start();
    }

    @Override
    public void send(String routeKey, Object eventContent) throws SendRefuseException {
        String exchangeName = ExchangeName.TopicExchange.getName();
        if (StringUtils.isEmpty(routeKey)) {
            throw new SendRefuseException("routeKey is empty.");
        }
        byte[] eventContentBytes = null;
        if (defaultCodecFactory == null) {
            if (eventContent == null) {
                logger.warn("Find eventContent is null,are you sure...");
            } else {
                throw new SendRefuseException("codecFactory must not be null ,unless eventContent is null");
            }
        } else {
            try {
                eventContentBytes = defaultCodecFactory.serialize(eventContent);
            } catch (IOException e) {
                throw new SendRefuseException(e);
            }
        }
        // 构造成Message
        EventMessage msg = new EventMessage(exchangeName, routeKey, eventContentBytes);
        try {
            rabbitTemplate.convertAndSend(exchangeName, routeKey, msg);
        } catch (AmqpException e) {
            logger.error("send event fail. Event Message : [" + eventContent + "]", e);
            throw new SendRefuseException("send event fail", e);
        }
    }

    @Override
    public Object recv(String queueName) throws RecvFaultException {
        if (StringUtils.isEmpty(queueName)) {
            throw new RecvFaultException("queueName can not be empty.");
        }
        if (!this.beBinded(ExchangeName.TopicExchange.getName(), queueName))
            this.declareBinding(ExchangeName.TopicExchange.getName(), queueName);

        Object eventContent = null;
        EventMessage eventMessage = null;
        try {
            rabbitTemplate.setQueue(queueName);
            eventMessage = (EventMessage) rabbitTemplate.receiveAndConvert();
            if (null == eventMessage) {
                return null;
            }
            eventContent = defaultCodecFactory.deSerialize(eventMessage.getEventData());
        } catch (AmqpException e) {
            logger.error("recv event fail. Event Message : [" + eventMessage + "]", e);
            throw new RecvFaultException("recv event fail", e);
        } catch (IOException e) {
            logger.error("recv event fail. Event Message : [" + eventContent + "]", e);
            throw new RecvFaultException("recv event fail", e);
        }
        return eventContent;
    }

    @Override
    public TopicEventController add(String queueName, EventProcesser eventProcesser) {
        return add(queueName, ExchangeName.TopicExchange.getName(), eventProcesser, defaultCodecFactory);
    }

    public TopicEventController add(String queueName, String exchangeName, EventProcesser eventProcesser,
            SerializeI codecFactory) {
        msgAdapterHandler.add(queueName, exchangeName, eventProcesser, defaultCodecFactory);
        if (isStarted.get()) {
            initMsgListenerAdapter();
        }
        return this;
    }

    @Override
    public TopicEventController add(List<String> queueNames, EventProcesser eventProcesser) {
        return add(queueNames, eventProcesser, defaultCodecFactory);
    }

    public TopicEventController add(List<String> queueNames, EventProcesser eventProcesser, SerializeI codecFactory) {

        for (String queueName : queueNames)
            msgAdapterHandler.add(queueName, ExchangeName.TopicExchange.getName(), eventProcesser, codecFactory);
        return this;
    }

    /**
     * exchange和queue是否已经绑定
     */
    protected boolean beBinded(String exchangeName, String queueName) {
        return binded.contains(exchangeName + "|" + queueName);
    }

    /**
     * 声明exchange和queue已经它们的绑定关系
     */
    protected synchronized void declareBinding(String exchangeName, String queueName) {

        String bindRelation = exchangeName + "|" + queueName;
        if (binded.contains(bindRelation))
            return;

        boolean needBinding = false;
        TopicExchange topicExchange = exchanges.get(exchangeName);
        if (topicExchange == null) {
            topicExchange = new TopicExchange(exchangeName, true, false, null);
            exchanges.put(exchangeName, topicExchange);
            rabbitAdmin.declareExchange(topicExchange);// 声明exchange
            needBinding = true;
        }

        Queue queue = queues.get(queueName);
        if (queue == null) {
            queue = new Queue(queueName, true, false, false);
            queues.put(queueName, queue);
            rabbitAdmin.declareQueue(queue); // 声明queue
            needBinding = true;
        }

        if (needBinding) {
            String bindingKey = StringUtils.getBindingKey(queueName);
            Binding binding = BindingBuilder.bind(queue).to(topicExchange).with(bindingKey);// 将queue绑定到exchange
            rabbitAdmin.declareBinding(binding);// 声明绑定关系
            binded.add(bindRelation);
        }
    }

}
