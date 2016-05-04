package com.sugon.cloudview.cloudmanager.resource.serviceImpl.impl.venv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sugon.cloudview.cloudmanager.resource.service.bo.venv.CloudvmNetPool;
import com.sugon.cloudview.cloudmanager.resource.service.exception.venv.VenvException;
import com.sugon.cloudview.cloudmanager.resource.service.service.venv.CloudvmNetPoolService;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.venv.CloudvmNetPoolE;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.venv.VenvConfigE;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.service.venv.CloudvmNetPoolDaoService;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.service.venv.VenvConfigDaoService;

@Component("cloudvmNetPoolServiceImpl")
public class CloudvmNetPoolServiceImpl implements CloudvmNetPoolService {

    @Autowired
    private VenvConfigDaoService venvConfigDaoService;
    @Autowired
    private CloudvmNetPoolDaoService cloudvmNetPoolDaoService;

    @Override
    public CloudvmNetPool addCloudvmNetPool(CloudvmNetPool cloudvmNetPool) throws VenvException {
        CloudvmNetPoolE cloudvmNetPoolE = new CloudvmNetPoolE();
        VenvConfigE venvConfigE = venvConfigDaoService.findVenvConfigById("8a809e285359147b01535915e0b00000");
        cloudvmNetPoolE.setDns(cloudvmNetPool.getDns());
        cloudvmNetPoolE.setGateway(cloudvmNetPool.getGateway());
        cloudvmNetPoolE.setIsAvl(cloudvmNetPool.getIsAvl());
        cloudvmNetPoolE.setNetName(cloudvmNetPool.getNetName());
        cloudvmNetPoolE.setSubNet(cloudvmNetPool.getSubNet());
        cloudvmNetPoolE.setSynDate(cloudvmNetPool.getSynDate());
        cloudvmNetPoolE.setVlanNO(cloudvmNetPool.getVlanNO());
        cloudvmNetPoolDaoService.addCloudvmNetPoolE(cloudvmNetPoolE);
        return null;
    }
}
