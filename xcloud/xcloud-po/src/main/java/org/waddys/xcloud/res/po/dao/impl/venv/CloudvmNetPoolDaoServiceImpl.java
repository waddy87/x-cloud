package org.waddys.xcloud.res.po.dao.impl.venv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.waddys.xcloud.res.po.dao.repository.venv.CloudvmNetpoolRepository;
import org.waddys.xcloud.res.po.dao.venv.CloudvmNetPoolDaoService;
import org.waddys.xcloud.res.po.entity.venv.CloudvmNetPoolE;

@Component("cloudvmNetPoolDaoServiceImpl")
public class CloudvmNetPoolDaoServiceImpl implements CloudvmNetPoolDaoService {

    @Autowired
    private CloudvmNetpoolRepository cloudvmNetpoolRepository;

    @Override
    public CloudvmNetPoolE addCloudvmNetPoolE(CloudvmNetPoolE cloudvmNetPoolE) {
        return cloudvmNetpoolRepository.save(cloudvmNetPoolE);
    }

}
