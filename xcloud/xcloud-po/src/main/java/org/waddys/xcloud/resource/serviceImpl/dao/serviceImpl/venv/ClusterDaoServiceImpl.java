package org.waddys.xcloud.resource.serviceImpl.dao.serviceImpl.venv;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.waddys.xcloud.resource.serviceImpl.dao.entity.venv.ClusterE;
import org.waddys.xcloud.resource.serviceImpl.dao.repository.venv.ClusterRepository;
import org.waddys.xcloud.resource.serviceImpl.dao.service.venv.ClusterDaoService;

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
