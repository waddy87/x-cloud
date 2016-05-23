package org.waddys.xcloud.res.serviceImpl.impl.venv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.waddys.xcloud.res.bo.venv.CloudvmNetPool;
import org.waddys.xcloud.res.exception.venv.VenvException;
import org.waddys.xcloud.res.po.dao.venv.CloudvmNetPoolDaoService;
import org.waddys.xcloud.res.po.dao.venv.VenvConfigDaoService;
import org.waddys.xcloud.res.po.entity.venv.CloudvmNetPoolE;
import org.waddys.xcloud.res.po.entity.venv.VenvConfigE;
import org.waddys.xcloud.res.service.service.venv.CloudvmNetPoolService;

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
