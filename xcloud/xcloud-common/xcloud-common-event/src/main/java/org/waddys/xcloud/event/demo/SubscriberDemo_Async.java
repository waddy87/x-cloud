package org.waddys.xcloud.event.demo;

import org.waddys.xcloud.event.api.EventController;
import org.waddys.xcloud.event.api.EventProcesser;
import org.waddys.xcloud.event.impl.TopicEventController;
import org.waddys.xcloud.event.type.ConnectionInfo;
import org.waddys.xcloud.event.type.ResourceType;
import org.waddys.xcloud.event.util.StringUtils;

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
