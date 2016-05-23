package org.waddys.xcloud.res.po.dao.impl.venv;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.waddys.xcloud.res.po.dao.repository.venv.ClusterRepository;
import org.waddys.xcloud.res.po.dao.venv.ClusterDaoService;
import org.waddys.xcloud.res.po.entity.venv.ClusterE;

@Component("clusterDaoServiceImpl")
@Transactional
public class ClusterDaoServiceImpl implements ClusterDaoService {

    @Autowired
    private ClusterRepository clusterRepository;

    @Override
    public ClusterE addCluster(ClusterE clusterE) {
        return clusterRepository.save(clusterE);
    }

    @Override
    public ClusterE findByclusterId(String clusterId) {
        return clusterRepository.findByclusterId(clusterId);
    }

    @Override
    public Iterable<ClusterE> findAll() {

        return clusterRepository.findAll();
    }

    @Override
    public List<ClusterE> findByName(String name) {
        return clusterRepository.findByname(name);
    }

}
