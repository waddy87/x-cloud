package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.serviceImpl.venv;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.venv.CloudvmStoragePoolE;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.repository.venv.CloudvmStoragePoolRepository;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.service.venv.CloudvmStoragePoolDaoService;

@Component("cloudvmStoragePoolDaoServiceImpl")
@Transactional
public class CloudvmStoragePoolDaoServiceImpl implements
        CloudvmStoragePoolDaoService {
    @Autowired
    private CloudvmStoragePoolRepository cloudvmStoragePoolRepository;

    @Override
    public CloudvmStoragePoolE addCloudvmStoragePoolE(CloudvmStoragePoolE cloudvmStoragePoolE) {
        return cloudvmStoragePoolRepository.save(cloudvmStoragePoolE);
    }

}
