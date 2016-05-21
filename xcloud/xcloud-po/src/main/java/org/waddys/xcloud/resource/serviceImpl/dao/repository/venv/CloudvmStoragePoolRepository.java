package org.waddys.xcloud.resource.serviceImpl.dao.repository.venv;

import org.springframework.data.repository.CrudRepository;
import org.waddys.xcloud.resource.serviceImpl.dao.entity.venv.CloudvmStoragePoolE;

public interface CloudvmStoragePoolRepository extends
        CrudRepository<CloudvmStoragePoolE, Long> {

}
