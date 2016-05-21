package org.waddys.xcloud.resource.service.service.venv;

import org.waddys.xcloud.resource.service.bo.venv.Host;
import org.waddys.xcloud.resource.service.exception.venv.VenvException;

public interface HostService {
    public Host addHost(Host host, String venvConfigId, String clusterId) throws VenvException;

    public Host findByhostId(String hostId) throws VenvException;

}
