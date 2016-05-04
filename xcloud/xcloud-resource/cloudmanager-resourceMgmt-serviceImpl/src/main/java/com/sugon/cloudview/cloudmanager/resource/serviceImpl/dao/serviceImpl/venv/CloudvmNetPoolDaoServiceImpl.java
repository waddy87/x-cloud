package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.serviceImpl.venv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.venv.CloudvmNetPoolE;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.repository.venv.CloudvmNetpoolRepository;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.service.venv.CloudvmNetPoolDaoService;

@Component("cloudvmNetPoolDaoServiceImpl")
public class CloudvmNetPoolDaoServiceImpl implements CloudvmNetPoolDaoService {

    @Autowired
    private CloudvmNetpoolRepository cloudvmNetpoolRepository;

    @Override
    public CloudvmNetPoolE addCloudvmNetPoolE(CloudvmNetPoolE cloudvmNetPoolE) {
        return cloudvmNetpoolRepository.save(cloudvmNetPoolE);
    }

}
