package org.waddys.xcloud.event.demo;

import org.waddys.xcloud.event.impl.DirectEventController;
import org.waddys.xcloud.event.impl.TopicEventController;
import org.waddys.xcloud.event.type.ConnectionInfo;
import org.waddys.xcloud.event.type.ResourceType;
import org.waddys.xcloud.event.type.SeverityLevel;
import org.waddys.xcloud.event.util.StringUtils;

public class PublisherDemo {

    public static void main(String[] argv) throws Exception {

        ConnectionInfo connectionInfo = ConnectionInfo.getConnectionInfo("10.0.36.73");

        DirectEventController directController = new DirectEventController(connectionInfo);
        TopicEventController topicController = new TopicEventController(connectionInfo);

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
    }
}
