package org.waddys.xcloud.resource.serviceImpl.dao.repository.vdc;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.waddys.xcloud.resource.serviceImpl.dao.entity.vdc.ComputingPoolE;

public interface ComputingPoolRepository extends
        PagingAndSortingRepository<ComputingPoolE, String>,
        JpaSpecificationExecutor<ComputingPoolE> {

    List<ComputingPoolE> findAll();

    ComputingPoolE findOne(String id);

    void delete(String id);

    @Modifying
    @Query("delete from ComputingPoolE s where s.configId = ?1")
    void deleteByConfigId(String cfId);

    List<ComputingPoolE> findByIsDistribute(Boolean isDistribute);

    List<ComputingPoolE> findByIsAvl(Boolean isAvl);

    @Modifying
    @Query("update ComputingPoolE c set c.isDistribute = :isDistribute where c.computingPoolId = :computingPoolId")
    void update(@Param("isDistribute") Boolean isDistribute,
            @Param("computingPoolId") String computingPoolId);

}
