package com.sugon.cloudview.cloudmanager.pmmgmt.serviceimpl.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sugon.cloudview.cloudmanager.pmmgmt.service.bo.PhysicalMachine;
import com.sugon.cloudview.cloudmanager.pmmgmt.service.exception.PhysicalMachineException;
import com.sugon.cloudview.cloudmanager.pmmgmt.service.service.PhysicalMachineService;
import com.sugon.cloudview.cloudmanager.pmmgmt.serviceimpl.dao.entity.PhysicalMachineE;
import com.sugon.cloudview.cloudmanager.pmmgmt.serviceimpl.dao.service.PhysicalMachineDaoService;

@Service("pmMgmtServiceImpl")
public class PhysicalMachineServiceImpl implements PhysicalMachineService {
    private static Logger logger = LoggerFactory
            .getLogger(PhysicalMachineServiceImpl.class);
    @Autowired
    private PhysicalMachineDaoService physicalMachineDaoServiceImpl;

    @Override
    public PhysicalMachine save(PhysicalMachine physicalMachine)
            throws PhysicalMachineException {
        PhysicalMachineE physicalMachineE = new PhysicalMachineE();
        try {
            Mapper mapper = new DozerBeanMapper();
            physicalMachineE = mapper.map(physicalMachine,
                    PhysicalMachineE.class);
            physicalMachineE = physicalMachineDaoServiceImpl
                    .save(physicalMachineE);
            physicalMachine = mapper.map(physicalMachineE,
                    PhysicalMachine.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new PhysicalMachineException("保存物理机失败！");
        }
        return physicalMachine;
    }

    @Override
    public List<PhysicalMachine> findAllPms() throws PhysicalMachineException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PhysicalMachine findPm(String id) throws PhysicalMachineException {
        PhysicalMachine physicalMachine = new PhysicalMachine();
        try {
            PhysicalMachineE physicalMachineE = physicalMachineDaoServiceImpl
                    .findPm(id);
            Mapper mapper = new DozerBeanMapper();
            physicalMachine = mapper.map(physicalMachineE,
                    PhysicalMachine.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new PhysicalMachineException("查询物理机详情失败！");
        }
        return physicalMachine;
    }

    @Override
    public void delete(String id) throws PhysicalMachineException {
        try {
            physicalMachineDaoServiceImpl.delete(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new PhysicalMachineException("删除申请单失败！");
        }
    }

    @Override
    public void update(PhysicalMachine physicalMachine)
            throws PhysicalMachineException {
        PhysicalMachineE physicalMachineE = new PhysicalMachineE();
        try {
            physicalMachineE = physicalMachineDaoServiceImpl
                    .findPm(physicalMachine.getId());
            if (StringUtils.isNotBlank(physicalMachine.getName())) {
                physicalMachineE.setName(physicalMachine.getName());
            }
            if (StringUtils.isNotBlank(physicalMachine.getCpuType())) {
                physicalMachineE.setCpuType(physicalMachine.getCpuType());
            }
            if (StringUtils.isNotBlank(physicalMachine.getDescription())) {
                physicalMachineE.setDescription(physicalMachine
                        .getDescription());
            }
            if (StringUtils.isNotBlank(physicalMachine.getDeviceModel())) {
                physicalMachineE.setDeviceModel(physicalMachine
                        .getDeviceModel());
            }
            if (StringUtils.isNotBlank(physicalMachine.getHostType())) {
                physicalMachineE.setHostType(physicalMachine.getHostType());
            }
            if (StringUtils.isNotBlank(physicalMachine.getIp())) {
                physicalMachineE.setIp(physicalMachine.getIp());
            }
            if (StringUtils.isNotBlank(physicalMachine.getIpmiIp())) {
                physicalMachineE.setIpmiIp(physicalMachine.getIpmiIp());
            }
            if (StringUtils.isNotBlank(physicalMachine.getIpmiPassword())) {
                physicalMachineE.setIpmiPassword(physicalMachine
                        .getIpmiPassword());
            }
            if (StringUtils.isNotBlank(physicalMachine.getIpmiUserName())) {
                physicalMachineE.setIpmiUserName(physicalMachine
                        .getIpmiUserName());
            }
            if (StringUtils.isNotBlank(physicalMachine.getMonitorMac())) {
                physicalMachineE.setMonitorMac(physicalMachine.getMonitorMac());
            }
            if (StringUtils.isNotBlank(physicalMachine.getOs())) {
                physicalMachineE.setOs(physicalMachine.getOs());
            }
            if (StringUtils.isNotBlank(physicalMachine.getSerialNumber())) {
                physicalMachineE.setSerialNumber(physicalMachine
                        .getSerialNumber());
            }
            physicalMachineDaoServiceImpl.save(physicalMachineE);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new PhysicalMachineException("更新物理机失败！");
        }

    }

    @Override
    public Map<String, Object> findPms(PhysicalMachine physicalMachine,
            Integer pageNum, Integer pageSize) throws PhysicalMachineException {
        List<PhysicalMachine> physicalMachineList = new ArrayList<PhysicalMachine>();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Mapper mapper = new DozerBeanMapper();
            PhysicalMachineE physicalMachineESearch = mapper.map(
                    physicalMachine, PhysicalMachineE.class);
            Pageable pageable = new PageRequest(pageNum - 1, pageSize);
            Page<PhysicalMachineE> physicalMachineListPage = physicalMachineDaoServiceImpl
                    .findPms(physicalMachineESearch, pageable);
            List<PhysicalMachineE> physicalMachineEList = physicalMachineListPage
                    .getContent();
            for (PhysicalMachineE physicalMachineE : physicalMachineEList) {
                PhysicalMachine physicalMachineTmp = new PhysicalMachine();
                BeanUtils.copyProperties(physicalMachineE, physicalMachineTmp);
                physicalMachineList.add(physicalMachineTmp);
            }
            map.put("total", physicalMachineListPage.getTotalElements());
            map.put("pmList", physicalMachineList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new PhysicalMachineException("分页查询物理机信息失败！");
        }

        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchUpdateOrgId(String orgId, String orgName, String pmIds)
            throws PhysicalMachineException {
        try {
            String[] pmIdArry = pmIds.split(",");
            for (String id : pmIdArry) {
                physicalMachineDaoServiceImpl.updateOrgIdByPmId(orgId, orgName,
                        id);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new PhysicalMachineException("批量更新物理机组织信息信息失败！");
        }

    }
}
