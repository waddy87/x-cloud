package com.sugon.cloudview.cloudmanager.event.impl;


import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;

import com.sugon.cloudview.cloudmanager.event.api.EventProcesser;
import com.sugon.cloudview.cloudmanager.event.api.SerializeI;
import com.sugon.cloudview.cloudmanager.event.type.EventMessage;
import com.sugon.cloudview.cloudmanager.event.util.StringUtils;

/**
 * MessageListenerAdapter的Pojo
 * <p>消息处理适配器，主要功能：</p>
 * <p>1、将不同的消息类型绑定到对应的处理器并缓存，如将queue+exchange的消息统一交由A处理器来出来</p>
 * <p>2、执行消息的消费分发，调用相应的处理器来消费属于它的消息</p>
 */
public class MessageAdapterHandler {

	private static final Logger logger = Logger.getLogger(MessageAdapterHandler.class);

	private ConcurrentMap<String, EventProcessorWrap> epwMap;

	public MessageAdapterHandler() {
		this.epwMap = new ConcurrentHashMap<String, EventProcessorWrap>();
	}

	public void handleMessage(EventMessage eem) {
		
		logger.debug("Receive an EventMessage: [" + eem + "]");
		// 先要判断接收到的message是否是空的，在某些异常情况下，会产生空值
		if (eem == null) {
			logger.warn("Receive an null EventMessage, it may product some errors, and processing message is canceled.");
			return;
		}
		if (StringUtils.isEmpty(eem.getRouteKey()) || StringUtils.isEmpty(eem.getExchangeName())) {
			logger.warn("The EventMessage's queueName and exchangeName is empty, this is not allowed, and processing message is canceled.");
			return;
		}
		
		for(String key: epwMap.keySet()){
			
			//选择相应的queueName，匹配RouteKey
			if(StringUtils.isRoutingQueue(key, eem.getRouteKey())){
			
				// 解码，并交给对应的EventHandle执行
				EventProcessorWrap eepw = epwMap.get(key);
				if (eepw == null) {
					logger.warn("Receive an EopEventMessage, but no processor can do it.");
					return;
				}
				try {
					eepw.process(eem.getRouteKey(), eem.getEventData());
				} catch (IOException e) {
					logger.error("Event content can not be Deserialized, check the provided CodecFactory.",e);
					return;
				}
			}
		}
	}

	protected void add(String queueName, String exchangeName, EventProcesser processor,SerializeI codecFactory) {
		if (StringUtils.isEmpty(queueName) || StringUtils.isEmpty(exchangeName) || processor == null || codecFactory == null) {
			throw new RuntimeException("queueName and exchangeName can not be empty,and processor or codecFactory can not be null. ");
		}
		EventProcessorWrap epw = new EventProcessorWrap(codecFactory,processor);
		EventProcessorWrap oldProcessorWrap = epwMap.putIfAbsent(queueName + "|" + exchangeName, epw);
		if (oldProcessorWrap != null) {
			logger.warn("The processor of this queue and exchange exists, and the new one can't be add");
		}
	}

	protected Set<String> getAllBinding() {
		Set<String> keySet = epwMap.keySet();
		return keySet;
	}

	protected static class EventProcessorWrap {

		private SerializeI codecFactory;

		private EventProcesser eep;

		protected EventProcessorWrap(SerializeI codecFactory,
				EventProcesser eep) {
			this.codecFactory = codecFactory;
			this.eep = eep;
		}

		public void process(String routeKey, byte[] eventData) throws IOException{
			Object obj = codecFactory.deSerialize(eventData);
			eep.process(routeKey, obj);
		}
	}
}
