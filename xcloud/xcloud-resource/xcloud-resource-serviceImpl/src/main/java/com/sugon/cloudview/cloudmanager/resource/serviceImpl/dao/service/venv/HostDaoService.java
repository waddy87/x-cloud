package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.service.venv;

import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.venv.HostE;

public interface HostDaoService {
    HostE addHost(HostE hostE);

    HostE findByhostId(String hostId);
}
