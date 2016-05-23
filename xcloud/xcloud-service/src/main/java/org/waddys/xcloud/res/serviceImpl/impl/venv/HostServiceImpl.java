package org.waddys.xcloud.res.serviceImpl.impl.venv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.waddys.xcloud.res.bo.venv.Host;
import org.waddys.xcloud.res.exception.venv.VenvException;
import org.waddys.xcloud.res.po.dao.venv.ClusterDaoService;
import org.waddys.xcloud.res.po.dao.venv.HostDaoService;
import org.waddys.xcloud.res.po.dao.venv.VenvConfigDaoService;
import org.waddys.xcloud.res.po.entity.venv.ClusterE;
import org.waddys.xcloud.res.po.entity.venv.HostE;
import org.waddys.xcloud.res.po.entity.venv.VenvConfigE;
import org.waddys.xcloud.res.service.service.venv.HostService;

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
