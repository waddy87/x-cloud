package com.sugon.cloudview.cloudmanager.event.demo;

import com.sugon.cloudview.cloudmanager.event.impl.DirectEventController;
import com.sugon.cloudview.cloudmanager.event.impl.TopicEventController;
import com.sugon.cloudview.cloudmanager.event.type.ConnectionInfo;
import com.sugon.cloudview.cloudmanager.event.type.ResourceType;
import com.sugon.cloudview.cloudmanager.event.type.SeverityLevel;
import com.sugon.cloudview.cloudmanager.event.util.StringUtils;

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
