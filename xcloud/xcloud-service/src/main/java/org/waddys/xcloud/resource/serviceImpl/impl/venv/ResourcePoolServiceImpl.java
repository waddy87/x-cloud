package org.waddys.xcloud.resource.serviceImpl.impl.venv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.waddys.xcloud.resource.service.bo.venv.ResourcePool;
import org.waddys.xcloud.resource.service.exception.venv.VenvException;
import org.waddys.xcloud.resource.service.service.venv.ResourcePoolService;
import org.waddys.xcloud.resource.serviceImpl.dao.entity.venv.ClusterE;
import org.waddys.xcloud.resource.serviceImpl.dao.entity.venv.ResourcePoolE;
import org.waddys.xcloud.resource.serviceImpl.dao.entity.venv.VenvConfigE;
import org.waddys.xcloud.resource.serviceImpl.dao.service.venv.ClusterDaoService;
import org.waddys.xcloud.resource.serviceImpl.dao.service.venv.ResourcePoolDaoService;
import org.waddys.xcloud.resource.serviceImpl.dao.service.venv.VenvConfigDaoService;

@Component("resourcePoolServiceImpl")
public class ResourcePoolServiceImpl implements ResourcePoolService {

    @Autowired
    private ResourcePoolDaoService resourcePoolDaoService;
    @Autowired
    private ClusterDaoService clusterDaoService;
    @Autowired
    private VenvConfigDaoService venvConfigDaoService;

    @Override
    public ResourcePool addResourcePool(ResourcePool resourcePool, String venvConfigId, String clusterId)
            throws VenvException {
        ResourcePoolE resourcePoolE = new ResourcePoolE();
        if (resourcePool != null) {
            ClusterE clusterE = clusterDaoService.findByclusterId("8a809e28535ff9950153601ddf350000");
            VenvConfigE venvConfigE = venvConfigDaoService.findVenvConfigById("8a809e285359147b01535915e0b00000");
            resourcePoolE.setCluster(clusterE);
            resourcePoolE.setConfigInfo(venvConfigE);
            resourcePoolE.setName(resourcePool.getName());
            resourcePoolE.setCpuTotCapacity(resourcePool.getCpuTotCapacity());
            resourcePoolE.setCpuUsedCapacity(resourcePool.getCpuUsedCapacity());
            resourcePoolE.setCpuAvlCapacity(resourcePool.getCpuAvlCapacity());
            resourcePoolE.setMemoryAvlCapacity(resourcePool.getMemoryAvlCapacity());
            resourcePoolE.setMemoryTotCapacity(resourcePool.getMemoryTotCapacity());
            resourcePoolE.setMemoryUsedCapacity(resourcePool.getMemoryUsedCapacity());
            resourcePoolE.setSynDate(resourcePool.getSynDate());
            resourcePoolDaoService.addResourcePool(resourcePoolE);
        }
        return null;
    }

    @Override
    public ResourcePool findByrpId(String rpId) throws VenvException {
        resourcePoolDaoService.findByrpId(rpId);
        return null;
    }

}
