package org.waddys.xcloud.resource.serviceImpl.dao.repository.venv;

import org.springframework.data.repository.CrudRepository;
import org.waddys.xcloud.resource.serviceImpl.dao.entity.venv.ResourcePoolE;

public interface ResourcePoolRepository extends
        CrudRepository<ResourcePoolE, Long> {
    ResourcePoolE findByrpId(String rpId);

}
