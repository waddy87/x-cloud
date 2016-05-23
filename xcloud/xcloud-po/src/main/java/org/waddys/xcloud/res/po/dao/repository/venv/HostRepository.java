package org.waddys.xcloud.res.po.dao.repository.venv;

import org.springframework.data.repository.CrudRepository;
import org.waddys.xcloud.res.po.entity.venv.HostE;

public interface HostRepository extends CrudRepository<HostE, Long> {
    HostE findByhostId(String hostId);
}
