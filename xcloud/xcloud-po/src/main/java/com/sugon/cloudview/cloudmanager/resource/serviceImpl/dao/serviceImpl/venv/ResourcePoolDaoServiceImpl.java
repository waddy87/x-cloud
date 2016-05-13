package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.serviceImpl.venv;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.venv.ResourcePoolE;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.repository.venv.ResourcePoolRepository;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.service.venv.ResourcePoolDaoService;

@Component("resourcePoolDaoServiceImpl")
@Transactional
public class ResourcePoolDaoServiceImpl implements ResourcePoolDaoService {

    @Autowired
    private ResourcePoolRepository resourcePoolRepository;

    @Override
    public ResourcePoolE addResourcePool(ResourcePoolE resourcePoolE) {
        return resourcePoolRepository.save(resourcePoolE);
    }

    @Override
    public ResourcePoolE findByrpId(String rpId) {
        return resourcePoolRepository.findByrpId(rpId);
    }

}
