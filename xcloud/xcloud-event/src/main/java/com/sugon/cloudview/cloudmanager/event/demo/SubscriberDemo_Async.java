package com.sugon.cloudview.cloudmanager.event.demo;

import com.sugon.cloudview.cloudmanager.event.api.EventController;
import com.sugon.cloudview.cloudmanager.event.api.EventProcesser;
import com.sugon.cloudview.cloudmanager.event.impl.TopicEventController;
import com.sugon.cloudview.cloudmanager.event.type.ConnectionInfo;
import com.sugon.cloudview.cloudmanager.event.type.ResourceType;
import com.sugon.cloudview.cloudmanager.event.util.StringUtils;

public class SubscriberDemo_Async implements EventProcesser {

    @Override
    public void process(String routeKey, Object e) {
        System.out.println("-----------------------------------");
        System.out.println("routeKey = " + routeKey);
        System.out.println(e);
        System.out.println("-----------------------------------");
    }

    public static void main(String[] argv) throws Exception {
        EventController controller = new TopicEventController(ConnectionInfo.getConnectionInfo("10.0.36.73"));
        String queueKey = StringUtils.getQueueKey(ResourceType.vm.toString(), null, null, "admin");
        controller.add(queueKey, new SubscriberDemo_Async());
        controller.start();
    }
}
