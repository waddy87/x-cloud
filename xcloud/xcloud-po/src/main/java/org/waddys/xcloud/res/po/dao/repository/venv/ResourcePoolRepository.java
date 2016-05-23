package org.waddys.xcloud.res.po.dao.repository.venv;

import org.springframework.data.repository.CrudRepository;
import org.waddys.xcloud.res.po.entity.venv.ResourcePoolE;

public interface ResourcePoolRepository extends
        CrudRepository<ResourcePoolE, Long> {
    ResourcePoolE findByrpId(String rpId);

}
