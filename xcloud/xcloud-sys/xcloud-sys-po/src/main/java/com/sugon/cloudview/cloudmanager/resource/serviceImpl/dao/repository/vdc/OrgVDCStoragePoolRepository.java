package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.repository.vdc;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.vdc.OrgVDCStoragePoolE;

public interface OrgVDCStoragePoolRepository extends
        Repository<OrgVDCStoragePoolE, String> {

    OrgVDCStoragePoolE save(OrgVDCStoragePoolE orgVDCStoragePool);

    List<OrgVDCStoragePoolE> findByOrgVDCId(String orgVDCId);
}
