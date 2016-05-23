package org.waddys.xcloud.res.po.dao.repository.venv;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.waddys.xcloud.res.po.entity.venv.ClusterE;

public interface ClusterRepository extends CrudRepository<ClusterE, Long> {
    ClusterE findByclusterId(String clusterId);

    @Query("select a from Cluster a where a.name like %?1%")
    List<ClusterE> findByname(String name);
}
