package org.waddys.xcloud.res.service.service.venv;

import org.waddys.xcloud.res.bo.venv.Host;
import org.waddys.xcloud.res.exception.venv.VenvException;

public interface HostService {
    public Host addHost(Host host, String venvConfigId, String clusterId) throws VenvException;

    public Host findByhostId(String hostId) throws VenvException;

}
