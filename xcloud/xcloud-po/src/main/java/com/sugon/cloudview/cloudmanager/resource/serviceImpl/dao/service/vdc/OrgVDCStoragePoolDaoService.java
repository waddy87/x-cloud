package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.service.vdc;

import java.util.List;

import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.vdc.OrgVDCStoragePoolE;

/**
 * 组织vdc-存储池 dao层接口
 * 
 * @author sun
 *
 */
public interface OrgVDCStoragePoolDaoService {
    /**
     * 创建
     * 
     * @param orgVDCStoragePool
     * @return
     */
    public OrgVDCStoragePoolE save(OrgVDCStoragePoolE orgVDCStoragePool);

    public List<OrgVDCStoragePoolE> findByOrgVDCId(String orgVDCId);
}
