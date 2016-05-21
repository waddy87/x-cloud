package org.waddys.xcloud.resource.serviceImpl.dao.service.venv;

import org.waddys.xcloud.resource.serviceImpl.dao.entity.venv.ResourcePoolE;

public interface ResourcePoolDaoService {

    ResourcePoolE addResourcePool(ResourcePoolE resourcePoolE);

    ResourcePoolE findByrpId(String rpId);
}
