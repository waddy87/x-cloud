package com.sugon.cloudview.cloudmanager.resource.service.service.vdc;

import java.util.List;

import com.sugon.cloudview.cloudmanager.resource.service.bo.vdc.OrgVDC;
import com.sugon.cloudview.cloudmanager.resource.service.bo.vdc.OrgVDCStoragePool;
import com.sugon.cloudview.cloudmanager.resource.service.exception.vdc.VDCException;

public interface OrgVDCService {

    public void save(OrgVDC orgVDC) throws VDCException;

    /**
     * 查询组织vdc详情
     * 
     * @param orgVDCId
     * @return
     * @throws VDCException
     */
    public OrgVDC findOrgVDC(String orgVDCId) throws VDCException;

    /**
     * 查询组织vdc下的所有存储池信息
     * 
     * @param orgVDCId
     * @return
     * @throws VDCException
     */
    public List<OrgVDCStoragePool> findStoragePools(String orgVDCId)
            throws VDCException;

    /**
     * 更新组织vdc
     * 
     * @param orgVDC
     * @throws VDCException
     */
    public void updateOrgVDC(OrgVDC orgVDC) throws VDCException;

    public List<OrgVDC> findOrgVDCs(OrgVDC orgVDC, int pageNum, int pageSize)
            throws VDCException;

    public long count(OrgVDC orgVDC) throws VDCException;

}
