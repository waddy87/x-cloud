package com.sugon.cloudview.cloudmanager.web.controller.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sugon.cloudview.cloudmanager.event.api.EventController;
import com.sugon.cloudview.cloudmanager.event.api.EventProcesser;
import com.sugon.cloudview.cloudmanager.event.type.ResourceType;
import com.sugon.cloudview.cloudmanager.event.type.SeverityLevel;
import com.sugon.cloudview.cloudmanager.event.util.StringUtils;

@Controller
public class EventTestController {

    private static final Logger logger = LoggerFactory.getLogger(EventTestController.class);

    @Autowired
    @Qualifier("DirectEventController")
    private EventController directController;

    @Autowired
    @Qualifier("TopicEventController")
    private EventController topicController;

    @RequestMapping(value = "/resources/send_event", method = RequestMethod.GET)
    public void sendEvent() {
        logger.debug("send event");
        try {
            String routingKey = StringUtils.getRoutingKey(ResourceType.host.toString(), "vm1",
                    SeverityLevel.info.toString(), "bush");
            directController.send(routingKey, "from directController");
            routingKey = StringUtils.getRoutingKey(ResourceType.host.toString(), "vm1", SeverityLevel.info.toString(),
                    "jobs");
            topicController.send(routingKey, "from host.info");
            routingKey = StringUtils.getRoutingKey(ResourceType.host.toString(), "vm1", SeverityLevel.error.toString(),
                    "yang");
            topicController.send(routingKey, "from host.error");
            routingKey = StringUtils.getRoutingKey(ResourceType.vm.toString(), "vm1", SeverityLevel.info.toString(),
                    "obama");
            topicController.send(routingKey, "from vm.info");
            routingKey = StringUtils.getRoutingKey(ResourceType.vm.toString(), "vm2", SeverityLevel.error.toString(),
                    "terry");
            topicController.send(routingKey, "from vm.error");
        } catch (Exception exception) {
        }
    }

    @RequestMapping(value = "/resources/recv_event", method = RequestMethod.GET)
    public void recvEvent() {
        logger.debug("recv event");
        try {
            String queueKey = StringUtils.getQueueKey(null, "vm1", null, "yang");
            topicController.add(queueKey, new EventProcesser() {
                @Override
                public void process(String routeKey, Object eventContent) {
                    logger.debug("-----------------------------------");
                    logger.debug("routeKey = " + routeKey);
                    logger.debug("" + eventContent);
                    logger.debug("-----------------------------------");
                }
            });
            topicController.start();
        } catch (Exception exception) {
        }

    }

}
