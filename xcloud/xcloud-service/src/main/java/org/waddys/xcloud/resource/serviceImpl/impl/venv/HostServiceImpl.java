package org.waddys.xcloud.resource.serviceImpl.impl.venv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.waddys.xcloud.resource.service.bo.venv.Host;
import org.waddys.xcloud.resource.service.exception.venv.VenvException;
import org.waddys.xcloud.resource.service.service.venv.HostService;
import org.waddys.xcloud.resource.serviceImpl.dao.entity.venv.ClusterE;
import org.waddys.xcloud.resource.serviceImpl.dao.entity.venv.HostE;
import org.waddys.xcloud.resource.serviceImpl.dao.entity.venv.VenvConfigE;
import org.waddys.xcloud.resource.serviceImpl.dao.service.venv.ClusterDaoService;
import org.waddys.xcloud.resource.serviceImpl.dao.service.venv.HostDaoService;
import org.waddys.xcloud.resource.serviceImpl.dao.service.venv.VenvConfigDaoService;

@Component("hostServiceImpl")
public class HostServiceImpl implements HostService {

    @Autowired
    private HostDaoService hostDaoService;
    @Autowired
    private VenvConfigDaoService venvConfigDaoService;
    @Autowired
    private ClusterDaoService clusterDaoService;
    @Override
    public Host addHost(Host host, String venvConfigId, String clusterId) throws VenvException {
        HostE hostE = new HostE();
        VenvConfigE venvConfigE = venvConfigDaoService.findVenvConfigById(venvConfigId);
        ClusterE clusterE = clusterDaoService.findByclusterId(clusterId);
        if (venvConfigE != null && clusterE != null)
        hostE.setCluster(clusterE);
        hostE.setConfigInfo(venvConfigE);
        hostE.setHostname(host.getHostname());
        hostE.setSynDate(host.getSynDate());
        hostDaoService.addHost(hostE);
        return null;
    }

    @Override
    public Host findByhostId(String hostId) throws VenvException {

        return null;
    }
}
