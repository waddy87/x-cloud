package com.sugon.cloudview.cloudmanager.resource.serviceImpl.impl.venv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sugon.cloudview.cloudmanager.resource.service.bo.venv.CloudvmStoragePool;
import com.sugon.cloudview.cloudmanager.resource.service.exception.venv.VenvException;
import com.sugon.cloudview.cloudmanager.resource.service.service.venv.CloudvmStoragePoolService;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.venv.CloudvmStoragePoolE;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.venv.VenvConfigE;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.service.venv.CloudvmStoragePoolDaoService;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.service.venv.VenvConfigDaoService;

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
