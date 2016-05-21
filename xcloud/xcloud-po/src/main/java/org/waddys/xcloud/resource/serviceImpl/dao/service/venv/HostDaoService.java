package org.waddys.xcloud.resource.serviceImpl.dao.service.venv;

import org.waddys.xcloud.resource.serviceImpl.dao.entity.venv.HostE;

public interface HostDaoService {
    HostE addHost(HostE hostE);

    HostE findByhostId(String hostId);
}
