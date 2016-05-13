package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.serviceImpl.vdc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.vdc.OrgVDCE;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.repository.vdc.OrgVDCRepository;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.service.vdc.OrgVDCDaoService;

/**
 * 计算池dao层接口实现
 * 
 * @author sun
 *
 */
@Component("orgVDCDaoServiceImpl")
@Transactional
public class OrgVDCDaoServiceImpl implements OrgVDCDaoService {
    @Autowired
    private OrgVDCRepository orgVDCRepository;

    @Override
    public OrgVDCE save(OrgVDCE orgVDC) {
        return this.orgVDCRepository.save(orgVDC);
    }

    @Override
    public List<OrgVDCE> findAllOrgVDCs() {

        return this.orgVDCRepository.findAll();
    }

    @Override
    public OrgVDCE findOrgVDC(String id) {
        return orgVDCRepository.findOne(id);
    }

    @Override
    public Page<OrgVDCE> findOrgVDCs(String orgVDCName, Pageable pageable) {

        if (!StringUtils.hasLength(orgVDCName)) {
            return this.orgVDCRepository.findAll(null);
        }

        return this.orgVDCRepository.findByNameContaining(orgVDCName, pageable);
    }

    @Override
    public void updateOrgVDC(OrgVDCE orgVDCE) {
        orgVDCRepository.updateOrgVDC(orgVDCE.getOrgVDCId(), orgVDCE.getName());
    }

    @Override
    public long count(OrgVDCE orgVDCE) {
        return orgVDCRepository.countByName(orgVDCE.getName());
    }
}
