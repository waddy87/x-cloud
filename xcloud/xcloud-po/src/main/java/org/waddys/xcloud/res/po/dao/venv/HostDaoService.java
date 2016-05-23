package org.waddys.xcloud.res.po.dao.venv;

import org.waddys.xcloud.res.po.entity.venv.HostE;

public interface HostDaoService {
    HostE addHost(HostE hostE);

    HostE findByhostId(String hostId);
}
