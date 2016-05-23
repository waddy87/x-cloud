package org.waddys.xcloud.res.po.dao.impl.venv;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.waddys.xcloud.res.po.dao.repository.venv.CloudvmStoragePoolRepository;
import org.waddys.xcloud.res.po.dao.venv.CloudvmStoragePoolDaoService;
import org.waddys.xcloud.res.po.entity.venv.CloudvmStoragePoolE;

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
