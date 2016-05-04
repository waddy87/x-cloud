package com.sugon.cloudview.cloudmanager.managedvm.serviceImpl.utils;

import com.sugon.cloudview.cloudmanager.managedvm.service.bo.OldVirtualMachine;
import com.sugon.cloudview.cloudmanager.managedvm.serviceImpl.dao.entity.AssignData;
import com.sugon.cloudview.cloudmanager.managedvm.serviceImpl.dao.entity.OldVirtualMachineE;
//import com.sugon.cloudview.cloudmanager.monitor.service.bo.VMBo;
import com.sugon.cloudview.cloudmanager.monitor.service.bo.VMBo;

public class Transfer {

	public static OldVirtualMachine e2Bo(OldVirtualMachineE oldVirtualMachineE, String orgName, VMBo vmBo) {

		OldVirtualMachine oldVirtualMachine = new OldVirtualMachine();

		oldVirtualMachine.setId(oldVirtualMachineE.getId());
		oldVirtualMachine.setVmId(oldVirtualMachineE.getVmId());
		oldVirtualMachine.setIsAssigned(oldVirtualMachineE.getIsAssigned());
		oldVirtualMachine.setIsDeleted(oldVirtualMachineE.getIsDeleted());
		oldVirtualMachine.setOrgId(oldVirtualMachineE.getOrgId());
		if (oldVirtualMachineE.getIsDeleted() == 1) {
			oldVirtualMachine.setName(oldVirtualMachineE.getName() + "（孤立的）");
		} else {
			oldVirtualMachine.setName(oldVirtualMachineE.getName());
		}

		// 转换VMBo属性
		if (vmBo != null) {
			oldVirtualMachine.setIpAddr(vmBo.getIpAddr());
			System.out.println(vmBo.getIpAddr());
			oldVirtualMachine.setStatus(vmBo.getStatus());
			oldVirtualMachine.setHostId(vmBo.getHostId());
			oldVirtualMachine.setHostName(vmBo.getHostName());
			oldVirtualMachine.setClusterName(vmBo.getClusterName());
			oldVirtualMachine.setDateCenterName(vmBo.getDateCenterName());
			oldVirtualMachine.setPowerStatus(vmBo.getPowerStatus());
			oldVirtualMachine.setOs(vmBo.getOs());
			oldVirtualMachine.setCpuMHZTotal(vmBo.getCpuMHZTotal());
			oldVirtualMachine.setCpuMHZUsed(vmBo.getCpuMHZUsed());
			oldVirtualMachine.setCpuNumber(vmBo.getCpuNumber());
			oldVirtualMachine.setCpuUsage(vmBo.getCpuUsage());
			oldVirtualMachine.setMemoryTotal(vmBo.getMemoryTotal());
			oldVirtualMachine.setMemoryUsage(vmBo.getMemoryUsage());
			oldVirtualMachine.setMemoryUsed(vmBo.getMemoryUsed());
			oldVirtualMachine.setDiskTotal(vmBo.getDiskTotal());
			oldVirtualMachine.setDiskUsage(vmBo.getDiskUsage());
			oldVirtualMachine.setDiskUsed(vmBo.getDiskUsed());
			oldVirtualMachine.setDiskIops(vmBo.getDiskIops());
			oldVirtualMachine.setDiskReadSpeed(vmBo.getDiskReadSpeed());
			oldVirtualMachine.setDiskWriteSpeed(vmBo.getDiskWriteSpeed());
			oldVirtualMachine.setDiskIOSpeed(vmBo.getDiskIOSpeed());
			oldVirtualMachine.setNetworkSendSpeed(vmBo.getNetworkSendSpeed());
			oldVirtualMachine.setNetworkReceiveSpeed(vmBo.getNetworkReceiveSpeed());
			oldVirtualMachine.setNetworkTransmitSpeed(vmBo.getNetworkTransmitSpeed());
		}

		// 转换不一样的属性
		if (oldVirtualMachineE.getOrgId() != null) {
			oldVirtualMachine.setOrgName(orgName);
		}
		if (oldVirtualMachineE.getIsAssigned() == 1) {
			oldVirtualMachine.setAssignData(AssignData.ASSIGNED.getValue());
		} else {
			oldVirtualMachine.setAssignData(AssignData.UNASSIGNED.getValue());
		}
		return oldVirtualMachine;
	}

	public static OldVirtualMachine e2Bo(OldVirtualMachineE oldVirtualMachineE, String orgName) {

		OldVirtualMachine oldVirtualMachine = new OldVirtualMachine();

		oldVirtualMachine.setId(oldVirtualMachineE.getId());
		oldVirtualMachine.setVmId(oldVirtualMachineE.getVmId());
		oldVirtualMachine.setIsAssigned(oldVirtualMachineE.getIsAssigned());
		oldVirtualMachine.setIsDeleted(oldVirtualMachineE.getIsDeleted());
		oldVirtualMachine.setOrgId(oldVirtualMachineE.getOrgId());
		oldVirtualMachine.setName(oldVirtualMachineE.getName());

		// 转换不一样的属性
		if (oldVirtualMachineE.getOrgId() != null) {
			oldVirtualMachine.setOrgName(orgName);
		}
		if (oldVirtualMachineE.getIsAssigned() == 1) {
			oldVirtualMachine.setAssignData(AssignData.ASSIGNED.getValue());
		} else {
			oldVirtualMachine.setAssignData(AssignData.UNASSIGNED.getValue());
		}
		return oldVirtualMachine;
	}
}
