package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.repository.vdc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.vdc.ProviderVDCE;

public interface ProviderVDCRepository extends
        PagingAndSortingRepository<ProviderVDCE, String>,
        JpaSpecificationExecutor<ProviderVDCE> {

    List<ProviderVDCE> findAll();

    ProviderVDCE findOne(String id);

    Page<ProviderVDCE> findByNameContaining(String name, Pageable pageable);

    Page<ProviderVDCE> findAll(Pageable pageable);

    @Modifying
    @Query("update ProviderVDCE p set p.name = :providerVDCName,"
            + "p.description = :description," + "p.vCpuNum = :vCpuNum,"
            + "p.memorySize = :memorySize " + "where p.pVDCId = :pVDCId")
    void updateProviderVDC(@Param("pVDCId") String pVDCId,
            @Param("providerVDCName") String providerVDCName,
            @Param("description") String description,
            @Param("vCpuNum") Integer vCpuNum,
            @Param("memorySize") Long memorySize);

    void delete(String proVDCId);

    // @Modifying
    // @Query("update ProviderVDCE p set p.vCpuOverplus = :vCpuOverplus,p.vCpuUsed = :vCpuUsed"
    // + ",p.memoryOverplus = :memoryOverplus ,p.memoryUsed = :memoryUsed"
    // + " where p.pVDCId = :pVDCId")
    // void updateProviderVDC(@Param("pVDCId") String pVDCId,
    // @Param("vCpuOverplus") Integer vCpuOverplus,
    // @Param("vCpuUsed") Integer vCpuUsed,
    // @Param("memoryOverplus") Long memoryOverplus,
    // @Param("memoryUsed") Long memoryUsed);

}
