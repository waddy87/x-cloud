package org.waddys.xcloud.event.demo;

import org.waddys.xcloud.event.api.EventController;
import org.waddys.xcloud.event.impl.DirectEventController;
import org.waddys.xcloud.event.type.ConnectionInfo;
import org.waddys.xcloud.event.type.ResourceType;
import org.waddys.xcloud.event.type.SeverityLevel;

public class SubscriberDemo_Sync {

    public static void main(String[] argv) throws Exception {
        EventController controller = new DirectEventController(ConnectionInfo.getConnectionInfo("10.0.36.73"));
        Object o = controller.recv(ResourceType.host.toString() + "." + SeverityLevel.info.toString());
        System.out.println("===========:" + o);
    }
}
