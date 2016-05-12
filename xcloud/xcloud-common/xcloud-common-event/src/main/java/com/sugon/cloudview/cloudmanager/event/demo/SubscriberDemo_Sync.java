package com.sugon.cloudview.cloudmanager.event.demo;

import com.sugon.cloudview.cloudmanager.event.api.EventController;
import com.sugon.cloudview.cloudmanager.event.impl.DirectEventController;
import com.sugon.cloudview.cloudmanager.event.type.ConnectionInfo;
import com.sugon.cloudview.cloudmanager.event.type.ResourceType;
import com.sugon.cloudview.cloudmanager.event.type.SeverityLevel;

public class SubscriberDemo_Sync {

    public static void main(String[] argv) throws Exception {
        EventController controller = new DirectEventController(ConnectionInfo.getConnectionInfo("10.0.36.73"));
        Object o = controller.recv(ResourceType.host.toString() + "." + SeverityLevel.info.toString());
        System.out.println("===========:" + o);
    }
}
