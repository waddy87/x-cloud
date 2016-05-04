package com.sugon.cloudview.cloudmanager.managedvm.serviceImpl.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sugon.cloudview.cloudmanager.managedvm.service.bo.OldVirtualMachine;
import com.sugon.cloudview.cloudmanager.managedvm.service.exception.OldVirtualMachineException;
import com.sugon.cloudview.cloudmanager.managedvm.service.service.OldVirtualMachineService;
import com.sugon.cloudview.cloudmanager.managedvm.serviceImpl.dao.entity.OldVirtualMachineE;
import com.sugon.cloudview.cloudmanager.managedvm.serviceImpl.dao.service.OldVirtualMachineDaoService;
import com.sugon.cloudview.cloudmanager.managedvm.serviceImpl.utils.Transfer;
import com.sugon.cloudview.cloudmanager.managedvm.serviceImpl.utils.Validate;
import com.sugon.cloudview.cloudmanager.monitor.service.bo.VMBo;
import com.sugon.cloudview.cloudmanager.monitor.service.service.VMService;
import com.sugon.cloudview.cloudmanager.org.service.OrganizationService;
import com.sugon.cloudview.cloudmanager.vijava.base.CloudviewExecutor;
import com.sugon.cloudview.cloudmanager.vijava.data.VMachine;
import com.sugon.cloudview.cloudmanager.vijava.exception.VirtException;
import com.sugon.cloudview.cloudmanager.vijava.vm.DeleteVM.DeleteVMAnswer;
import com.sugon.cloudview.cloudmanager.vijava.vm.DeleteVM.DeleteVMCmd;
import com.sugon.cloudview.cloudmanager.vijava.vm.QueryVM.QueryVMCmd;
import com.sugon.cloudview.cloudmanager.vijava.vm.VMPowerOperate.PowerOPType;
import com.sugon.cloudview.cloudmanager.vijava.vm.VMPowerOperate.VMPowerOpCmd;

import net.sf.json.JSONObject;

@Service("managedvm-oldVirtualMachineServiceImpl")
public class OldVirtualMachineServiceImpl implements OldVirtualMachineService {

	private static Logger logger = LoggerFactory.getLogger(OldVirtualMachineServiceImpl.class);

	@Autowired
	private CloudviewExecutor cloudviewExecutor;

	@Autowired
	private OldVirtualMachineDaoService oldVirtualMachineDaoService;

	// @Autowired
	// private OrganizationDaoService organizationDaoService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private VMService vmService;

	@Override
	public void syncVM() throws OldVirtualMachineException {

		// 从vcenter中获得
		List<OldVirtualMachineE> listVcenter = new ArrayList<OldVirtualMachineE>();
		List<String> listVcenterIds = new ArrayList<String>();
		// 从数据库中获得
		List<OldVirtualMachineE> listOldVM = new ArrayList<OldVirtualMachineE>();
		// 需要删除的虚拟机
		List<OldVirtualMachineE> listNeedDeleted = new ArrayList<OldVirtualMachineE>();
		// 需要改名的
		List<OldVirtualMachineE> listNeedModifyName = new ArrayList<OldVirtualMachineE>();

		List<VMachine> queryResult = new ArrayList<VMachine>();
		QueryVMCmd queryVMCmd = new QueryVMCmd();
		queryVMCmd.setIsCloudviewVM(false);
		queryVMCmd.setIsTemplate(false);// 不查询模板
		try {
			queryResult = cloudviewExecutor.execute(queryVMCmd).getVmList();
		} catch (VirtException e) {
			logger.error("OldVirtualMachineException：从Vcenter中查询数据失败！");
			throw new OldVirtualMachineException("从Vcenter中查询数据失败！");
		}

		// VMachine 2 OldVirtualMachineE
		for (VMachine vMachine : queryResult) {
			OldVirtualMachineE oldVirtualMachineE = new OldVirtualMachineE();
			oldVirtualMachineE.setVmId(vMachine.getId());
			oldVirtualMachineE.setIsAssigned(0);
			oldVirtualMachineE.setIsDeleted(0);
			oldVirtualMachineE.setName(vMachine.getName());
			listVcenter.add(oldVirtualMachineE);
			listVcenterIds.add(vMachine.getId());
		}

		listOldVM = oldVirtualMachineDaoService.findAll();

		for (OldVirtualMachineE oldVirtualMachineE : listOldVM) {
			if (!listVcenterIds.contains(oldVirtualMachineE.getVmId())) {// 不包含即为要记为“孤立的”
				oldVirtualMachineE.setIsDeleted(1);
				listNeedDeleted.add(oldVirtualMachineE);// 记为孤立的
			} else {// 包含，即为两表中都有的，修改名字需要重新保存，没修改的不需要更改，都需要从vcenter数据集合中删除该条记录

				// 索引通过在listVcenterIds集合中查数据获得
				int index = listVcenterIds.indexOf(oldVirtualMachineE.getVmId());
				OldVirtualMachineE vmInVsphere = listVcenter.get(index);

				if (!oldVirtualMachineE.getName().equals(vmInVsphere.getName())) {
					// 不相同即为修改的
					oldVirtualMachineE.setName(vmInVsphere.getName());
					listNeedModifyName.add(oldVirtualMachineE);
				}
				// 从listVcenter中删除两表都有的数据，使得listVcenter中只包含需要新增的数据
				listVcenter.remove(index);
				// 同步listVcenter和listVcenterIds中数据的一致性，即索引一一对应
				listVcenterIds.remove(index);
			}
		}

		oldVirtualMachineDaoService.saveOrUpdate(listVcenter);// 添加
		// oldVirtualMachineDaoService.saveOrUpdate(listNeedDeleted);// 标记为孤立的
		oldVirtualMachineDaoService.deleteBatch(listNeedDeleted);// 需要删除的
		oldVirtualMachineDaoService.saveOrUpdate(listNeedModifyName);// 需要改名的
	}

	@Override
	public List<OldVirtualMachine> listAll(int pageNum, int pageSize) throws OldVirtualMachineException {

		if (pageNum <= 0 || pageNum < 0) {
			logger.debug("OldVirtualMachineService：页码输入不正确！");
			throw new OldVirtualMachineException("页码输入不正确！");
		}
		
		Page<OldVirtualMachineE> oldVirtualMachineEs = oldVirtualMachineDaoService
		        .findAll(new PageRequest(pageNum - 1, pageSize));

		// 得到查询集合的vmId
		// List<String> vmIds = new ArrayList<String>();
		// for (OldVirtualMachineE oldVirtualMachineE : oldVirtualMachineEs) {
		// vmIds.add(oldVirtualMachineE.getVmId());
		// }
		// List<VMBo> vmBos = vmService.getVMsByIds(vmIds);

		List<OldVirtualMachine> oldVirtualMachines = new ArrayList<OldVirtualMachine>();

		// 迭代分页集合中的数据，转换成BO实体，加入list中
		Iterator<OldVirtualMachineE> iterator = oldVirtualMachineEs.iterator();
		int index = 0;
		while (iterator.hasNext()) {
			String orgName = null;
			OldVirtualMachineE oldVirtualMachineE = iterator.next();
			if (oldVirtualMachineE.getOrgId() != null) {
				// orgName =
				// organizationDaoService.showOrganizationById(oldVirtualMachineE.getOrgId()).getName();
				orgName = organizationService.showById(oldVirtualMachineE.getOrgId()).getName();
			}
			// oldVirtualMachines.add(Transfer.e2Bo(oldVirtualMachineE, orgName,
			// vmBos.get(index)));
			oldVirtualMachines.add(Transfer.e2Bo(oldVirtualMachineE, orgName));
			index++;
		}
		return oldVirtualMachines;
	}

	@Override
	public OldVirtualMachine assign(String id, String orgId) throws OldVirtualMachineException {

		OldVirtualMachineE oldVirtualMachineE = getAndValidate(id);

		// if (organizationDaoService.showOrganizationById(orgId) == null) {
		// logger.debug("OldVirtualMachineException：组织不存在！");
		// throw new OldVirtualMachineException("组织不存在！");
		// }

		if (organizationService.showById(orgId) == null) {
			logger.debug("OldVirtualMachineException：组织不存在！");
			throw new OldVirtualMachineException("组织不存在！");
		}

		oldVirtualMachineE.setOrgId(orgId);
		oldVirtualMachineE.setIsAssigned(1);
		return getOrgNameAndTransfer2Bo(oldVirtualMachineE);
	}

	@Override
	public void assign(List<String> ids, String orgId) throws OldVirtualMachineException {

		List<OldVirtualMachineE> oldVirtualMachineEs = oldVirtualMachineDaoService.findByIdIn(ids);

		Validate.isExistsAndIsDeleted(oldVirtualMachineEs);

		// if (organizationDaoService.showOrganizationById(orgId) == null) {
		// logger.debug("OldVirtualMachineException：组织不存在！");
		// throw new OldVirtualMachineException("组织不存在！");
		// }

		if (organizationService.showById(orgId) == null) {
			logger.debug("OldVirtualMachineException：组织不存在！");
			throw new OldVirtualMachineException("组织不存在！");
		}

		for (OldVirtualMachineE oldVirtualMachineE : oldVirtualMachineEs) {
			oldVirtualMachineE.setOrgId(orgId);
			oldVirtualMachineE.setIsAssigned(1);
		}

		oldVirtualMachineDaoService.saveOrUpdate(oldVirtualMachineEs);
	}

	@Override
	public OldVirtualMachine recycle(String id) throws OldVirtualMachineException {

		OldVirtualMachineE oldVirtualMachineE = getAndValidate(id);

		if (oldVirtualMachineE.getIsAssigned() != 1) {
			logger.debug("OldVirtualMachineException：利旧虚拟机需要被分配才能回收！");
			throw new OldVirtualMachineException("利旧虚拟机需要被分配才能回收！");
		}

		oldVirtualMachineE.setOrgId(null);
		oldVirtualMachineE.setIsAssigned(0);
		return getOrgNameAndTransfer2Bo(oldVirtualMachineE);
	}

	@Override
	public void recycle(List<String> ids) throws OldVirtualMachineException {

		List<OldVirtualMachineE> oldVirtualMachineEs = oldVirtualMachineDaoService.findByIdIn(ids);

		Validate.isExistsAndIsDeleted(oldVirtualMachineEs);

		for (OldVirtualMachineE oldVirtualMachineE : oldVirtualMachineEs) {
			if (oldVirtualMachineE.getIsAssigned() != 1) {
				logger.debug("OldVirtualMachineException：利旧虚拟机需要被分配才能回收！");
				throw new OldVirtualMachineException("利旧虚拟机需要被分配才能回收！");
			}
			oldVirtualMachineE.setOrgId(null);
			oldVirtualMachineE.setIsAssigned(0);
		}

		oldVirtualMachineDaoService.saveOrUpdate(oldVirtualMachineEs);
	}

	@Override
	public void deleteVMRecord(String id) throws OldVirtualMachineException {

		OldVirtualMachineE oldVirtualMachineE = oldVirtualMachineDaoService.findById(id);

		if (oldVirtualMachineE == null) {
			logger.debug("OldVirtualMachineException：该利旧虚拟机不存在！");
			throw new OldVirtualMachineException("该利旧虚拟机不存在！");
		}

		// if (oldVirtualMachineE.getIsDeleted() == 0) {
		// logger.debug("OldVirtualMachineException：只能对孤立的利旧虚拟机执行删除！");
		// throw new OldVirtualMachineException("只能对孤立的利旧虚拟机执行删除！");
		// }

		oldVirtualMachineDaoService.deleteOne(id);
	}

	@Override
	public void deleteVM(String id) throws OldVirtualMachineException {

		OldVirtualMachineE oldVirtualMachineE = oldVirtualMachineDaoService.findById(id);

		if (oldVirtualMachineE == null) {
			logger.debug("OldVirtualMachineException：该利旧虚拟机不存在！");
			throw new OldVirtualMachineException("该利旧虚拟机不存在！");
		}

		DeleteVMCmd deleteVMCmd = new DeleteVMCmd();
		deleteVMCmd.setVmId(oldVirtualMachineE.getVmId());
		DeleteVMAnswer deleteVMAnswer = null;
		try {
			deleteVMAnswer = cloudviewExecutor.execute(deleteVMCmd);
		} catch (VirtException e) {
			logger.error("OldVirtualMachineException：" + e.getMessage());
			throw new OldVirtualMachineException(e.getMessage());
		}
		if (deleteVMAnswer == null) {
			logger.error("虚拟化接口返回对象为空！");
			throw new OldVirtualMachineException("虚拟化接口返回对象为空！");
		}
		if (!deleteVMAnswer.isSuccess()) {
			logger.error("虚拟化接口执行失败：" + deleteVMAnswer.getErrMsg());
			throw new OldVirtualMachineException("虚拟化接口执行失败：" + deleteVMAnswer.getErrMsg());
		}
	}

	@Override
	public OldVirtualMachine findByVmId(String vmId) throws OldVirtualMachineException {

		OldVirtualMachineE oldVirtualMachineE = oldVirtualMachineDaoService.findByVmId(vmId);
		String orgName = null;
		if (oldVirtualMachineE.getOrgId() != null) {
			orgName = organizationService.showById(oldVirtualMachineE.getOrgId()).getName();
		}
		return Transfer.e2Bo(oldVirtualMachineE, orgName);
	}

	@Override
	public void start(String id) throws OldVirtualMachineException {

		OldVirtualMachineE oldVirtualMachineE = getAndValidate(id);

		// VMPowerOpCmd powerOpCmd = new VMPowerOpCmd();
		// powerOpCmd.setOpType(PowerOPType.powerOn);
		// powerOpCmd.setVmId(oldVirtualMachineE.getVmId());
		// try {
		// cloudviewExecutor.execute(powerOpCmd);
		// } catch (VirtException e) {
		// logger.debug("OldVirtualMachineException：" + e.getMessage());
		// throw new OldVirtualMachineException(e.getMessage());
		// }
		execute(oldVirtualMachineE, PowerOPType.powerOn);
	}

	@Override
	public void startBatch(List<String> ids) throws OldVirtualMachineException {

		List<OldVirtualMachineE> oldVirtualMachineEs = oldVirtualMachineDaoService.findByIdIn(ids);

		Validate.isExistsAndIsDeleted(oldVirtualMachineEs);

		// VMPowerOpCmd powerOpCmd = new VMPowerOpCmd();
		// powerOpCmd.setOpType(PowerOPType.powerOn);
		//
		// for (OldVirtualMachineE oldVirtualMachineE : oldVirtualMachineEs) {
		// powerOpCmd.setVmId(oldVirtualMachineE.getVmId());
		// try {
		// cloudviewExecutor.execute(powerOpCmd);
		// } catch (VirtException e) {
		// logger.debug("OldVirtualMachineException：" + e.getMessage());
		// throw new OldVirtualMachineException(e.getMessage());
		// }
		// }

		executeBatch(oldVirtualMachineEs, PowerOPType.powerOn);
	}

	@Override
	public void stop(String id) throws OldVirtualMachineException {

		OldVirtualMachineE oldVirtualMachineE = getAndValidate(id);

		VMPowerOpCmd powerOpCmd = new VMPowerOpCmd();
		powerOpCmd.setOpType(PowerOPType.powerOff);
		powerOpCmd.setVmId(oldVirtualMachineE.getVmId());
		try {
			cloudviewExecutor.execute(powerOpCmd);
		} catch (VirtException e) {
			logger.debug("OldVirtualMachineException：" + e.getMessage());
			throw new OldVirtualMachineException(e.getMessage());
		}
	}

	@Override
	public void stopBatch(List<String> ids) throws OldVirtualMachineException {

		List<OldVirtualMachineE> oldVirtualMachineEs = oldVirtualMachineDaoService.findByIdIn(ids);

		Validate.isExistsAndIsDeleted(oldVirtualMachineEs);

		VMPowerOpCmd powerOpCmd = new VMPowerOpCmd();
		powerOpCmd.setOpType(PowerOPType.powerOff);

		for (OldVirtualMachineE oldVirtualMachineE : oldVirtualMachineEs) {
			powerOpCmd.setVmId(oldVirtualMachineE.getVmId());
			try {
				cloudviewExecutor.execute(powerOpCmd);
			} catch (VirtException e) {
				logger.debug("OldVirtualMachineException：" + e.getMessage());
				throw new OldVirtualMachineException(e.getMessage());
			}
		}
	}

	@Override
	public void restart(String id) throws OldVirtualMachineException {

		OldVirtualMachineE oldVirtualMachineE = getAndValidate(id);

		VMPowerOpCmd powerOpCmd = new VMPowerOpCmd();
		powerOpCmd.setOpType(PowerOPType.restart);
		powerOpCmd.setVmId(oldVirtualMachineE.getVmId());
		try {
			cloudviewExecutor.execute(powerOpCmd);
		} catch (VirtException e) {
			logger.debug("OldVirtualMachineException：" + e.getMessage());
			throw new OldVirtualMachineException(e.getMessage());
		}
	}

	@Override
	public void restatBatch(List<String> ids) throws OldVirtualMachineException {

		List<OldVirtualMachineE> oldVirtualMachineEs = oldVirtualMachineDaoService.findByIdIn(ids);

		Validate.isExistsAndIsDeleted(oldVirtualMachineEs);

		VMPowerOpCmd powerOpCmd = new VMPowerOpCmd();
		powerOpCmd.setOpType(PowerOPType.restart);

		for (OldVirtualMachineE oldVirtualMachineE : oldVirtualMachineEs) {
			powerOpCmd.setVmId(oldVirtualMachineE.getVmId());
			try {
				cloudviewExecutor.execute(powerOpCmd);
			} catch (VirtException e) {
				logger.debug("OldVirtualMachineException：" + e.getMessage());
				throw new OldVirtualMachineException(e.getMessage());
			}
		}
	}

	@Override
	public void vncAccess(String id) throws OldVirtualMachineException {
		// TODO Auto-generated method stub

	}

	@Override
	public OldVirtualMachine display(String id) throws OldVirtualMachineException {

		OldVirtualMachineE oldVirtualMachineE = oldVirtualMachineDaoService.findById(id);

		if (oldVirtualMachineE == null) {
			logger.debug("OldVirtualMachineException：该利旧虚拟机不存在！");
			throw new OldVirtualMachineException("该利旧虚拟机不存在！");
		}

		String vmID = oldVirtualMachineE.getVmId();
		VMBo vmBo = vmService.getVMById(vmID);
		String orgName = null;
		if (oldVirtualMachineE.getOrgId() != null) {
			// orgName =
			// organizationDaoService.showOrganizationById(oldVirtualMachineE.getOrgId()).getName();
			orgName = organizationService.showById(oldVirtualMachineE.getOrgId()).getName();
		}
		return Transfer.e2Bo(oldVirtualMachineE, orgName, vmBo);
	}

	@Override
	public JSONObject displayHistory(String id) throws OldVirtualMachineException {

		OldVirtualMachineE oldVirtualMachineE = oldVirtualMachineDaoService.findById(id);

		if (oldVirtualMachineE == null) {
			logger.debug("OldVirtualMachineException：该利旧虚拟机不存在！");
			throw new OldVirtualMachineException("该利旧虚拟机不存在！");
		}

		return vmService.getHistory(oldVirtualMachineE.getVmId());
	}

	@Override
	public int count() throws OldVirtualMachineException {
		return this.oldVirtualMachineDaoService.count();
	}

	@Override
	public int countByName(String name) throws OldVirtualMachineException {
		return this.oldVirtualMachineDaoService.countByName(name);
	}

	@Override
	public List<OldVirtualMachine> listByName(String name, int pageNum, int pageSize)
	        throws OldVirtualMachineException {

		if (pageNum <= 0 || pageNum < 0) {
			logger.debug("OldVirtualMachineService：页码输入不正确！");
			throw new OldVirtualMachineException("页码输入不正确！");
		}

		Page<OldVirtualMachineE> oldVirtualMachineEs = oldVirtualMachineDaoService.findByName(name,
		        new PageRequest(pageNum - 1, pageSize));

		// 得到查询集合的vmId
		List<String> vmIds = new ArrayList<String>();
		for (OldVirtualMachineE oldVirtualMachineE : oldVirtualMachineEs) {
			vmIds.add(oldVirtualMachineE.getVmId());
		}
		List<VMBo> vmBos = vmService.getVMsByIds(vmIds);

		List<OldVirtualMachine> oldVirtualMachines = new ArrayList<OldVirtualMachine>();

		// 迭代分页集合中的数据，转换成BO实体，加入list中
		Iterator<OldVirtualMachineE> iterator = oldVirtualMachineEs.iterator();
		int index = 0;
		while (iterator.hasNext()) {
			String orgName = null;
			OldVirtualMachineE oldVirtualMachineE = iterator.next();
			if (oldVirtualMachineE.getOrgId() != null) {
				// orgName =
				// organizationDaoService.showOrganizationById(oldVirtualMachineE.getOrgId()).getName();
				orgName = organizationService.showById(oldVirtualMachineE.getOrgId()).getName();
			}
			oldVirtualMachines.add(Transfer.e2Bo(oldVirtualMachineE, orgName, vmBos.get(index)));
			index++;
		}
		return oldVirtualMachines;
	}

	@Override
	public Map<String, Object> list4Table(String name, String orgId, int pageNum, int pageSize)
	        throws OldVirtualMachineException {

		if (pageNum <= 0 || pageNum < 0) {
			logger.debug("OldVirtualMachineService：页码输入不正确！");
			throw new OldVirtualMachineException("页码输入不正确！");
		}

		Map<String, Object> map = new HashMap<String, Object>();

		OldVirtualMachineE paramOldVME = new OldVirtualMachineE();
		paramOldVME.setName(name);
		paramOldVME.setOrgId(orgId);

		Page<OldVirtualMachineE> oldVirtualMachineEs = oldVirtualMachineDaoService.findByEntity(paramOldVME,
		        new PageRequest(pageNum - 1, pageSize));

		// 得到查询集合的vmId
		List<String> vmIds = new ArrayList<String>();
		for (OldVirtualMachineE oldVirtualMachineE : oldVirtualMachineEs) {
			vmIds.add(oldVirtualMachineE.getVmId());
		}
		List<VMBo> vmBos = vmService.getVMsByIds(vmIds);

		List<OldVirtualMachine> oldVirtualMachines = new ArrayList<OldVirtualMachine>();

		// 迭代分页集合中的数据，转换成BO实体，加入list中
		Iterator<OldVirtualMachineE> iterator = oldVirtualMachineEs.iterator();
		int index = 0;
		while (iterator.hasNext()) {
			String orgName = null;
			OldVirtualMachineE oldVirtualMachineE = iterator.next();
			if (oldVirtualMachineE.getOrgId() != null) {
				// orgName =
				// organizationDaoService.showOrganizationById(oldVirtualMachineE.getOrgId()).getName();
				orgName = organizationService.showById(oldVirtualMachineE.getOrgId()).getName();
			}
			oldVirtualMachines.add(Transfer.e2Bo(oldVirtualMachineE, orgName, vmBos.get(index)));
			index++;
		}

		map.put("list", oldVirtualMachines);
		map.put("total", oldVirtualMachineEs.getTotalElements());

		return map;
	}

	/**
	 * 查询并校验
	 * 
	 * @param id
	 * @return
	 * @throws OldVirtualMachineException
	 */
	private OldVirtualMachineE getAndValidate(String id) throws OldVirtualMachineException {

		OldVirtualMachineE oldVirtualMachineE = oldVirtualMachineDaoService.findById(id);

		Validate.isExistsAndIsDeleted(oldVirtualMachineE);

		return oldVirtualMachineE;
	}

	/**
	 * 获得组织名称并转换成BO实体
	 * 
	 * @param oldVirtualMachineE
	 *            需要保存或更新的实体
	 * @return 保存或更新后的业务实体
	 */
	private OldVirtualMachine getOrgNameAndTransfer2Bo(OldVirtualMachineE oldVirtualMachineE) {

		OldVirtualMachineE modifyedOldVirtuanlMachineE = oldVirtualMachineDaoService.saveOrUpdate(oldVirtualMachineE);

		String orgName = null;
		if (modifyedOldVirtuanlMachineE.getOrgId() != null) {
			// orgName =
			// organizationDaoService.showOrganizationById(modifyedOldVirtuanlMachineE.getOrgId()).getName();
			orgName = organizationService.showById(oldVirtualMachineE.getOrgId()).getName();
		}

		return Transfer.e2Bo(oldVirtualMachineE, orgName);
	}

	/**
	 * 对虚拟机执行操作
	 * 
	 * @param oldVirtualMachineE
	 *            利旧虚拟机对象
	 * @param powerOPType
	 *            操作类型
	 * @throws OldVirtualMachineException
	 */
	private void execute(OldVirtualMachineE oldVirtualMachineE, PowerOPType powerOPType)
	        throws OldVirtualMachineException {

		VMPowerOpCmd powerOpCmd = new VMPowerOpCmd();
		powerOpCmd.setOpType(powerOPType);
		powerOpCmd.setVmId(oldVirtualMachineE.getVmId());
		try {
			cloudviewExecutor.execute(powerOpCmd);
		} catch (VirtException e) {
			logger.debug("OldVirtualMachineException：" + e.getMessage());
			throw new OldVirtualMachineException(e.getMessage());
		}
	}

	/**
	 * 批量对虚拟机执行操作
	 * 
	 * @param oldVirtualMachineEs
	 *            利旧虚拟机对象集合
	 * @param powerOPType
	 *            操作类型
	 * @throws OldVirtualMachineException
	 */
	private void executeBatch(List<OldVirtualMachineE> oldVirtualMachineEs, PowerOPType powerOPType)
	        throws OldVirtualMachineException {

		VMPowerOpCmd powerOpCmd = new VMPowerOpCmd();
		powerOpCmd.setOpType(powerOPType);

		for (OldVirtualMachineE oldVirtualMachineE : oldVirtualMachineEs) {
			powerOpCmd.setVmId(oldVirtualMachineE.getVmId());
			try {
				cloudviewExecutor.execute(powerOpCmd);
			} catch (VirtException e) {
				logger.debug("OldVirtualMachineException：" + e.getMessage());
				throw new OldVirtualMachineException(e.getMessage());
			}
		}
	}
}
