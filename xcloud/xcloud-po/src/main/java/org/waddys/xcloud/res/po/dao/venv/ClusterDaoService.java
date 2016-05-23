package org.waddys.xcloud.res.po.dao.venv;

import java.util.List;

import org.waddys.xcloud.res.po.entity.venv.ClusterE;

public interface ClusterDaoService {
    ClusterE addCluster(ClusterE clusterE);

    ClusterE findByclusterId(String clusterId);

    Iterable<ClusterE> findAll();

    List<ClusterE> findByName(String name);

}
