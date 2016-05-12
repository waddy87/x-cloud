package com.sugon.cloudview.cloudmanager.org.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sugon.cloudview.cloudmanager.org.bo.Organization;
import com.sugon.cloudview.cloudmanager.org.constant.OrgStatus;
import com.sugon.cloudview.cloudmanager.org.dao.service.OrganizationDaoService;
import com.sugon.cloudview.cloudmanager.org.service.OrganizationService;
import com.sugon.cloudview.cloudmanager.vm.bo.VmHost;
import com.sugon.cloudview.cloudmanager.vm.service.VmService;

@Service("organizationService")
public class OrganizationServiceImpl implements OrganizationService {
    private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);
	
	@Autowired
	private OrganizationDaoService organizationDaoService;

    @Autowired
    private VmService vmHostService;

    @Override
    public void deleteById(String id) throws Exception {
        // 1.检查目标组织是否存在
        Organization target = organizationDaoService.findById(id);
        if (target == null) {
            logger.error("目标组织不存在！");
            throw new Exception("目标组织不存在！");
        }
        // 2.检查该组织下是否有资产，如果有就禁止删除
        VmHost search = new VmHost();
        search.setStatus("A");
        Page<VmHost> vmPage = vmHostService.pageByOID(id, search, null);
        if (vmPage.getTotalElements() > 0) {
            logger.error("拒绝删除，请先删除该组织下的虚机！");
            throw new Exception("拒绝删除，请先删除该组织下的虚机！");
        }
        // 3.注销该组织，变更状态
        try {
            target.setName(target.getName() + "-DELETED-" + new Date().getTime());// 解决重名问题
            target.setStatus(OrgStatus.DELETED);
            organizationDaoService.updateOrganization(target);
        } catch (Exception e) {
            logger.error("修改组织状态失败！", e);
            throw new Exception("修改组织状态失败！", e);
        }
    }

    @Override
    public Organization add(Organization organization) {
        // TODO Auto-generated method stub
        organization.setStatus(OrgStatus.NORMAL);
        organization.setCreate_time(new Date());
        return organizationDaoService.addOrganization(organization);
    }

    @Override
    public Organization update(Organization organization) throws Exception {
        if (organization.getId() == null || organization.getId().length() == 0) {
            throw new Exception("要更新的组织无组织ID。");
        }
        return organizationDaoService.updateOrganization(organization);
    }

    @Override
    public Page<Organization> pageAll(Organization search, PageRequest pageRequest) {
        return organizationDaoService.findByBO(search, pageRequest);
    }

    @Override
    public Organization show(String name) {
        return organizationDaoService.showOrganization(name);
    }

    @Override
    public Organization showById(String id) {
        return organizationDaoService.showOrganizationById(id);
    }

    @Override
    public List<Organization> listAll() {
        List<Organization> list = null;
        list = organizationDaoService.findAllOrganization();
        return list;
    }
	


}
