package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.service.venv;

import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.venv.ResourcePoolE;

public interface ResourcePoolDaoService {

    ResourcePoolE addResourcePool(ResourcePoolE resourcePoolE);

    ResourcePoolE findByrpId(String rpId);
}
