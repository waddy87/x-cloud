package org.waddys.xcloud.resource.serviceImpl.dao.repository.venv;

import org.springframework.data.repository.CrudRepository;
import org.waddys.xcloud.resource.serviceImpl.dao.entity.venv.CloudvmNetPoolE;

public interface CloudvmNetpoolRepository extends
        CrudRepository<CloudvmNetPoolE, Long> {

}
