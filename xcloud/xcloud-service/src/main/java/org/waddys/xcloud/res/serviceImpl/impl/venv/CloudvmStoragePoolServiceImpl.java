package org.waddys.xcloud.res.serviceImpl.impl.venv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.waddys.xcloud.res.bo.venv.CloudvmStoragePool;
import org.waddys.xcloud.res.exception.venv.VenvException;
import org.waddys.xcloud.res.po.dao.venv.CloudvmStoragePoolDaoService;
import org.waddys.xcloud.res.po.dao.venv.VenvConfigDaoService;
import org.waddys.xcloud.res.po.entity.venv.CloudvmStoragePoolE;
import org.waddys.xcloud.res.po.entity.venv.VenvConfigE;
import org.waddys.xcloud.res.service.service.venv.CloudvmStoragePoolService;

@Component("cloudvmStoragePoolServiceImpl")
public class CloudvmStoragePoolServiceImpl implements CloudvmStoragePoolService {

    @Autowired
    private VenvConfigDaoService venvConfigDaoService;
    @Autowired
    private CloudvmStoragePoolDaoService cloudvmStoragePoolDaoService;
    @Override
    public CloudvmStoragePool addCloudvmStoragePool(CloudvmStoragePool cloudvmStoragePool) throws VenvException {
        CloudvmStoragePoolE cloudvmStoragePoolE = new CloudvmStoragePoolE();
        VenvConfigE venvConfigE = venvConfigDaoService.findVenvConfigById("8a809e285359147b01535915e0b00000");
        cloudvmStoragePoolE.setConfigInfo(venvConfigE);
        cloudvmStoragePoolE.setName(cloudvmStoragePool.getName());
        cloudvmStoragePoolE.setSpAvlCapacity(cloudvmStoragePool.getSpAvlCapacity());
        cloudvmStoragePoolE.setSpTotalCapacity(cloudvmStoragePool.getSpTotalCapacity());
        cloudvmStoragePoolE.setSpUsedCapacity(cloudvmStoragePool.getSpUsedCapacity());
        cloudvmStoragePoolE.setSynDate(cloudvmStoragePool.getSynDate());
        cloudvmStoragePoolDaoService.addCloudvmStoragePoolE(cloudvmStoragePoolE);
        return null;
    }

}
