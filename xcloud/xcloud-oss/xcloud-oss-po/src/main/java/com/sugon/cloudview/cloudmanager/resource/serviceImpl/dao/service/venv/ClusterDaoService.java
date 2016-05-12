package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.service.venv;

import java.util.List;

import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.venv.ClusterE;

public interface ClusterDaoService {
    ClusterE addCluster(ClusterE clusterE);

    ClusterE findByclusterId(String clusterId);

    Iterable<ClusterE> findAll();

    List<ClusterE> findByName(String name);

}
