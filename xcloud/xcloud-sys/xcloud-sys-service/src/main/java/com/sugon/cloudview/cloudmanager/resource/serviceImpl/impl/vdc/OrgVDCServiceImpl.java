package com.sugon.cloudview.cloudmanager.resource.serviceImpl.impl.vdc;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sugon.cloudview.cloudmanager.resource.service.bo.vdc.OrgVDC;
import com.sugon.cloudview.cloudmanager.resource.service.bo.vdc.OrgVDCStoragePool;
import com.sugon.cloudview.cloudmanager.resource.service.exception.vdc.VDCException;
import com.sugon.cloudview.cloudmanager.resource.service.service.vdc.OrgVDCService;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.vdc.OrgVDCE;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.vdc.OrgVDCStoragePoolE;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.service.vdc.OrgVDCDaoService;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.service.vdc.OrgVDCStoragePoolDaoService;

@Service("orgVDCServiceImpl")
public class OrgVDCServiceImpl implements OrgVDCService {
    private static Logger logger = LoggerFactory
            .getLogger(OrgVDCServiceImpl.class);
    @Autowired
    private OrgVDCDaoService orgVDCDaoServiceImpl;

    @Autowired
    private OrgVDCStoragePoolDaoService orgVDCStoragePoolDaoServiceImpl;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(OrgVDC orgVDC) throws VDCException {
        OrgVDCE orgVDCE = new OrgVDCE();
        try {
            BeanUtils.copyProperties(orgVDC, orgVDCE);
            orgVDCE = orgVDCDaoServiceImpl.save(orgVDCE);
            List<OrgVDCStoragePool> orgVDCStoragePoolList = orgVDC
                    .getOrgVDCStoragePool();
            for (OrgVDCStoragePool orgVDCStoragePool : orgVDCStoragePoolList) {
                OrgVDCStoragePoolE orgVDCStoragePoolE = new OrgVDCStoragePoolE();
                orgVDCStoragePoolE.setpVDCStoragePoolId(orgVDCStoragePool
                        .getpVDCStoragePoolId());
                orgVDCStoragePoolE.setOrgVDCId(orgVDCE.getOrgVDCId());
                orgVDCStoragePoolE.setSpId(orgVDCStoragePool.getSpId());
                orgVDCStoragePoolE.setSpName(orgVDCStoragePool.getSpName());
                orgVDCStoragePoolE.setSpAvlCapacity(orgVDCStoragePool
                        .getSpAvlCapacity());
                orgVDCStoragePoolE.setSpTotalCapacity(orgVDCStoragePool
                        .getSpTotalCapacity());
                orgVDCStoragePoolE.setSpUsed(orgVDCStoragePool.getSpUsed());
                orgVDCStoragePoolDaoServiceImpl.save(orgVDCStoragePoolE);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("保存组织VDC失败！");
        }
    }

    @Override
    public OrgVDC findOrgVDC(String orgVDCId) throws VDCException {
        OrgVDCE orgVDCE = new OrgVDCE();
        OrgVDC orgVDC = new OrgVDC();
        try {
            orgVDCE = orgVDCDaoServiceImpl.findOrgVDC(orgVDCId);
            BeanUtils.copyProperties(orgVDCE, orgVDC);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("查询组织VDC失败！");
        }
        return orgVDC;
    }

    @Override
    public List<OrgVDCStoragePool> findStoragePools(String orgVDCId)
            throws VDCException {
        List<OrgVDCStoragePool> orgVDCStoragePoolList = new ArrayList<OrgVDCStoragePool>();
        try {
            List<OrgVDCStoragePoolE> orgVDCStoragePoolListEList = orgVDCStoragePoolDaoServiceImpl
                    .findByOrgVDCId(orgVDCId);
            for (OrgVDCStoragePoolE orgVDCStoragePoolE : orgVDCStoragePoolListEList) {
                OrgVDCStoragePool orgVDCStoragePool = new OrgVDCStoragePool();
                BeanUtils.copyProperties(orgVDCStoragePoolE, orgVDCStoragePool);
                orgVDCStoragePoolList.add(orgVDCStoragePool);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("查询组织VDC下所属存储池失败！");
        }
        return orgVDCStoragePoolList;
    }

    @Override
    public void updateOrgVDC(OrgVDC orgVDC) throws VDCException {
        OrgVDCE orgVDCE = new OrgVDCE();
        try {
            BeanUtils.copyProperties(orgVDC, orgVDCE);
            orgVDCDaoServiceImpl.updateOrgVDC(orgVDCE);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("更新组织VDC失败！");
        }
    }

    @Override
    public List<OrgVDC> findOrgVDCs(OrgVDC orgVDC, int pageNum, int pageSize)
            throws VDCException {
        List<OrgVDC> orgVDCList = new ArrayList<OrgVDC>();
        try {
            Pageable pageable = new PageRequest(pageNum - 1, pageSize);
            Page<OrgVDCE> orgVDCListPage = orgVDCDaoServiceImpl.findOrgVDCs(
                    orgVDC.getName(), pageable);
            List<OrgVDCE> orgVDCEList = orgVDCListPage.getContent();
            for (OrgVDCE orgVDCE : orgVDCEList) {
                OrgVDC orgVDCTmp = new OrgVDC();
                BeanUtils.copyProperties(orgVDCE, orgVDCTmp);
                orgVDCList.add(orgVDCTmp);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("分页查询组织VDC失败！");
        }

        return orgVDCList;
    }

    @Override
    public long count(OrgVDC orgVDC) throws VDCException {
        OrgVDCE orgVDCE = new OrgVDCE();
        long total = 0;
        try {
            BeanUtils.copyProperties(orgVDC, orgVDCE);
            total = orgVDCDaoServiceImpl.count(orgVDCE);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("查询总数失败！");
        }
        return total;
    }

}
