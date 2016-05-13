package com.sugon.cloudview.cloudmanager.resource.service.service.venv;

import com.sugon.cloudview.cloudmanager.resource.service.bo.venv.Host;
import com.sugon.cloudview.cloudmanager.resource.service.exception.venv.VenvException;

public interface HostService {
    public Host addHost(Host host, String venvConfigId, String clusterId) throws VenvException;

    public Host findByhostId(String hostId) throws VenvException;

}
