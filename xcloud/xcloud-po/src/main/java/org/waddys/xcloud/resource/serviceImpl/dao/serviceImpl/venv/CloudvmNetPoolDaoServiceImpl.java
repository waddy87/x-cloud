package org.waddys.xcloud.resource.serviceImpl.dao.serviceImpl.venv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.waddys.xcloud.resource.serviceImpl.dao.entity.venv.CloudvmNetPoolE;
import org.waddys.xcloud.resource.serviceImpl.dao.repository.venv.CloudvmNetpoolRepository;
import org.waddys.xcloud.resource.serviceImpl.dao.service.venv.CloudvmNetPoolDaoService;

@Component("cloudvmNetPoolDaoServiceImpl")
public class CloudvmNetPoolDaoServiceImpl implements CloudvmNetPoolDaoService {

    @Autowired
    private CloudvmNetpoolRepository cloudvmNetpoolRepository;

    @Override
    public CloudvmNetPoolE addCloudvmNetPoolE(CloudvmNetPoolE cloudvmNetPoolE) {
        return cloudvmNetpoolRepository.save(cloudvmNetPoolE);
    }

}
