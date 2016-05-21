package org.waddys.xcloud.resource.serviceImpl.dao.service.venv;

import java.util.List;

import org.waddys.xcloud.resource.serviceImpl.dao.entity.venv.ClusterE;

public interface ClusterDaoService {
    ClusterE addCluster(ClusterE clusterE);

    ClusterE findByclusterId(String clusterId);

    Iterable<ClusterE> findAll();

    List<ClusterE> findByName(String name);

}
