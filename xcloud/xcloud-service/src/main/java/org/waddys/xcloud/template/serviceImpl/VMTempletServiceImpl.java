/**
 * Created on 2016年3月15日
 */
package org.waddys.xcloud.template.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.waddys.xcloud.common.base.exception.CloudviewException;
import org.waddys.xcloud.template.po.dao.VMTempletDaoService;
import org.waddys.xcloud.template.po.entity.VMTempletE;
import org.waddys.xcloud.template.service.VMTempletService;
import org.waddys.xcloud.vijava.base.CloudviewExecutor;
import org.waddys.xcloud.vijava.data.VMDiskInfo;
import org.waddys.xcloud.vijava.data.VMachine;
import org.waddys.xcloud.vijava.exception.VirtException;
import org.waddys.xcloud.vijava.vm.QueryVM.QueryVMAnswer;
import org.waddys.xcloud.vijava.vm.QueryVM.QueryVMCmd;

/**
 * 功能名: 模板管理实现类 功能描述: 管理模板生命周期操作功能 Copyright: Copyright (c) 2016 公司: 曙光云计算技术有限公司
 *
 * @author 曾兵
 * @version 2.0.0 sp1
 */
@Service("VMTempletService")
public class VMTempletServiceImpl implements VMTempletService {
	private static final Logger logger = LoggerFactory.getLogger(VMTempletServiceImpl.class);
	@Autowired
	private VMTempletDaoService vmTempletDaoService;

	@Autowired
	private CloudviewExecutor cloudviewExecutor;

	/**
	 * @return the vmTempletDaoService
	 */
	public VMTempletDaoService getVmTempletDaoService() {
		return vmTempletDaoService;
	}

	/**
	 * @param vmTempletDaoService
	 *            the vmTempletDaoService to set
	 */
	public void setVmTempletDaoService(VMTempletDaoService vmTempletDaoService) {
		this.vmTempletDaoService = vmTempletDaoService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.waddys.xcloud.templet.service.VMTempletService#
	 * synVMTemplet(java.lang.String)
	 */
	@Override
	@Transactional
	public String synVMTemplet(String venvID) {
		QueryVMCmd cmd = new QueryVMCmd().setIsTemplate(true);
		QueryVMAnswer answer;
		try {
			answer = cloudviewExecutor.execute(cmd);

			List<VMachine> vmList = answer.getVmList();
			if (null == vmList || vmList.size() < 1) {
				logger.info("模板信息列表为空，同步模板返回");
				return null;
			}
			
			for (VMachine vMachine : vmList) {
				
			}
			List<VMTempletE>  oldTempletList;
			oldTempletList = vmTempletDaoService.findAllTemplet(null);
			Map<String, VMachine> map = new HashMap<String, VMachine>();
			
			

			for (Iterator iterator = vmList.iterator(); iterator.hasNext();) {
				VMachine virtualMachine = (VMachine) iterator.next();
				VMTempletE vmTempletE;

				map.put(virtualMachine.getId(), virtualMachine);

				vmTempletE = vmTempletDaoService.findByRelationId(virtualMachine.getId());
				if (null != vmTempletE) {// 更新模板
					logger.debug("更新模板信息名称：===============" + vmTempletE.getName());
					vmTempletE.setUpdateTime(new Date());
				} else {// 新建模板
					vmTempletE = new VMTempletE();
					vmTempletE.setCreateTime(new Date());
					vmTempletE.setVisible("0");
					vmTempletE.setStatus("1");
					logger.debug("新增模板信息名称：===============" + vmTempletE.getName());
				}
				vmTempletE.setName(virtualMachine.getName());
				vmTempletE.setCpu(virtualMachine.getCpuNum());
				vmTempletE.setMemory(virtualMachine.getMemSizeMB());
				vmTempletE.setRelationId(virtualMachine.getId());
				vmTempletE.setOs(virtualMachine.getOsName());

				List<VMDiskInfo> diskList = virtualMachine.getDiskInfos();
				long totalDisk = 0;
				for (VMDiskInfo vmDiskInfo : diskList) {
					totalDisk += vmDiskInfo.getDiskSizeGB();
				}
				vmTempletE.setDiskSize(totalDisk);
				vmTempletDaoService.addVMTemplet(vmTempletE);
			}
			logger.debug("底层新模板信息总数 ===========================" + map.size());
			logger.debug("历史模板信息总数 ===========================" + oldTempletList.size());
			for (VMTempletE vmTempletE : oldTempletList) {// 删除不存在的模板
				if (!map.containsKey(vmTempletE.getRelationId() + "")) {
					vmTempletE.setVisible("1");
					vmTempletE.setUpdateTime(new Date());
					vmTempletDaoService.addVMTemplet(vmTempletE);
					logger.debug("删除模板信息名称：===============" + vmTempletE.getName());
				}
			}
		} catch (VirtException e1) {
			logger.error(e1.getMessage());
			logger.error("虚拟机模板同步失败");
		} catch (CloudviewException e) {
			logger.error(e.getMessage());
			logger.error("虚拟机模板同步失败");
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.waddys.xcloud.templet.service.VMTempletService#release
	 * (org.waddys.xcloud.templet.service.entity.VMTempletE)
	 */
	@Override
	public VMTempletE release(VMTempletE vmTempletE) throws CloudviewException {
		VMTempletE temp = vmTempletDaoService.findByRelationId(vmTempletE.getRelationId());
		if (null != temp) {
			temp.setStatus("1");
			return vmTempletDaoService.release(temp);
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.waddys.xcloud.templet.service.VMTempletService#
	 * unRelease(org.waddys.xcloud.templet.service.entity.
	 * VMTempletE)
	 */
	@Override
	public VMTempletE unRelease(VMTempletE vmTempletE) throws CloudviewException {

		VMTempletE temp = vmTempletDaoService.findByRelationId(vmTempletE.getRelationId());
		if (null != temp) {
			temp.setStatus("0");
			return vmTempletDaoService.release(temp);
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.waddys.xcloud.templet.service.VMTempletService#
	 * modifyVMTempletE(org.waddys.xcloud.templet.service.entity.
	 * VMTempletE)
	 */
	@Override
	public VMTempletE modifyVMTempletE(VMTempletE vmTempletE) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.waddys.xcloud.templet.service.VMTempletService#
	 * findByRelationId(java.lang.String)
	 */
	@Override
	public VMTempletE findByRelationId(String relationId) throws CloudviewException {
		// TODO Auto-generated method stub
		return vmTempletDaoService.findByRelationId(relationId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.waddys.xcloud.templet.service.VMTempletService#
	 * findAllVMTemplet(int, int)
	 */
	@Override
	public Page<VMTempletE> findAllVMTemplet(VMTempletE vmTempletE, int pageNum, int pageSize)
			throws CloudviewException {
		Pageable pageable = new PageRequest(pageNum - 1, pageSize);
		return vmTempletDaoService.findAllTemplet(vmTempletE, pageable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.waddys.xcloud.templet.service.VMTempletService#
	 * countAllVMTemplet(org.waddys.xcloud.templet.service.entity
	 * .VMTempletE)
	 */
	@Override
	public long countAllVMTemplet(VMTempletE vmTempletE) throws CloudviewException {

		return vmTempletDaoService.countAllTemplet(vmTempletE);
	}

    @Override
    public VMTempletE findById(Integer id) throws CloudviewException {
        return vmTempletDaoService.findById(id);
    }
	

}
