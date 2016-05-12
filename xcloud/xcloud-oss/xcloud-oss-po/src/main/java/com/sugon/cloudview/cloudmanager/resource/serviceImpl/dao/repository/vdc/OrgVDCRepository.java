package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.repository.vdc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.vdc.OrgVDCE;

public interface OrgVDCRepository extends Repository<OrgVDCE, String> {

    OrgVDCE save(OrgVDCE orgVDC);

    List<OrgVDCE> findAll();

    OrgVDCE findOne(String id);

    Page<OrgVDCE> findByNameContaining(String name, Pageable pageable);

    Page<OrgVDCE> findAll(Pageable pageable);

    @Query("select count(distinct o) from OrgVDCE o where o.name like %?1%")
    long countByName(String name);

    @Modifying
    @Query("update OrgVDCE o set o.name = :orgVDCName where o.orgVDCId = :orgVDCId")
    void updateOrgVDC(@Param("orgVDCId") String orgVDCId,
            @Param("orgVDCName") String orgVDCName);
}
