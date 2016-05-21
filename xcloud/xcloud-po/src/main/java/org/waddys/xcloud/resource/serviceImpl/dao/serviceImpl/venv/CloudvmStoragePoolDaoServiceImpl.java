package org.waddys.xcloud.resource.serviceImpl.dao.serviceImpl.venv;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.waddys.xcloud.resource.serviceImpl.dao.entity.venv.CloudvmStoragePoolE;
import org.waddys.xcloud.resource.serviceImpl.dao.repository.venv.CloudvmStoragePoolRepository;
import org.waddys.xcloud.resource.serviceImpl.dao.service.venv.CloudvmStoragePoolDaoService;

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
