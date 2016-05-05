package com.sugon.cloudview.cloudmanager.resource.serviceImpl.impl.venv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sugon.cloudview.cloudmanager.resource.service.bo.venv.Host;
import com.sugon.cloudview.cloudmanager.resource.service.exception.venv.VenvException;
import com.sugon.cloudview.cloudmanager.resource.service.service.venv.HostService;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.venv.ClusterE;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.venv.HostE;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.venv.VenvConfigE;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.service.venv.ClusterDaoService;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.service.venv.HostDaoService;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.service.venv.VenvConfigDaoService;

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
