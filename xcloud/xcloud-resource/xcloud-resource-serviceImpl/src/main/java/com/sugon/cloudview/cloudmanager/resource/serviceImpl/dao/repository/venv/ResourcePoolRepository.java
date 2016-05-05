package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.repository.venv;

import org.springframework.data.repository.CrudRepository;

import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.venv.ResourcePoolE;

public interface ResourcePoolRepository extends
        CrudRepository<ResourcePoolE, Long> {
    ResourcePoolE findByrpId(String rpId);

}
