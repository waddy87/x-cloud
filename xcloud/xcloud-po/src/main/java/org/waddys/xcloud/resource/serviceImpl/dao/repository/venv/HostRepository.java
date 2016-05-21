package org.waddys.xcloud.resource.serviceImpl.dao.repository.venv;

import org.springframework.data.repository.CrudRepository;
import org.waddys.xcloud.resource.serviceImpl.dao.entity.venv.HostE;

public interface HostRepository extends CrudRepository<HostE, Long> {
    HostE findByhostId(String hostId);
}
