package com.sugon.cloudview.cloudmanager.managedvm.service.service;

import java.util.List;
import java.util.Map;

import com.sugon.cloudview.cloudmanager.managedvm.service.bo.OldVirtualMachine;
import com.sugon.cloudview.cloudmanager.managedvm.service.exception.OldVirtualMachineException;

import net.sf.json.JSONObject;

/**
 * 利旧虚拟机操作接口
 * 
 * @author 赵春龙
 *
 */
public interface OldVirtualMachineService {

	/**
	 * 同步vcenter数据库和利旧虚拟机数据库
	 * 
	 * @throws OldVirtualMachineException
	 */
	public void syncVM() throws OldVirtualMachineException;

	public List<OldVirtualMachine> listAll(int pageNum, int pageSize)
	        throws OldVirtualMachineException;
	
	/**
	 * 根据虚拟机id将利旧虚拟机分配给组织
	 * 
	 * @param id
	 *            虚拟机id
	 * @param orgId
	 *            组织id
	 * @throws OldVirtualMachineException
	 */
	public OldVirtualMachine assign(String id, String orgId) throws OldVirtualMachineException;

	public void assign(List<String> ids, String orgId) throws OldVirtualMachineException;

	/**
	 * 根据虚拟机id将从虚拟机从组织回收
	 * 
	 * @param id
	 *            虚拟机id
	 * @throws OldVirtualMachineException
	 */
	public OldVirtualMachine recycle(String id) throws OldVirtualMachineException;

	/**
	 * 根据虚拟机id批量回收虚拟机
	 * 
	 * @param ids
	 *            虚拟机id集合
	 * @throws OldVirtualMachineException
	 */
	public void recycle(List<String> ids) throws OldVirtualMachineException;

	/**
	 * 根据虚拟机id将vcenter中不存在的虚拟机从数据表记录中删除
	 * 
	 * @param id
	 * @throws OldVirtualMachineException
	 */
	public void deleteVMRecord(String id) throws OldVirtualMachineException;

	/**
	 * 删除虚拟机
	 * 
	 * @param id
	 * @throws OldVirtualMachineException
	 */
	public void deleteVM(String id) throws OldVirtualMachineException;

	/**
	 * 启动虚拟机
	 * 
	 * @param id
	 *            虚拟机id
	 * @throws OldVirtualMachineException
	 */
	public void start(String id) throws OldVirtualMachineException;

	/**
	 * 批量启动虚拟机
	 * 
	 * @param ids
	 *            虚拟机id
	 * @throws OldVirtualMachineException
	 */
	public void startBatch(List<String> ids) throws OldVirtualMachineException;

	/**
	 * 停止虚拟机
	 * 
	 * @param id
	 *            虚拟机id
	 * @throws OldVirtualMachineException
	 */
	public void stop(String id) throws OldVirtualMachineException;

	/**
	 * 批量停止虚拟机
	 * 
	 * @param ids
	 *            虚拟机id
	 * @throws OldVirtualMachineException
	 */
	public void stopBatch(List<String> ids) throws OldVirtualMachineException;

	/**
	 * 重启虚拟机
	 * 
	 * @param id
	 *            虚拟机id
	 * @throws OldVirtualMachineException
	 */
	public void restart(String id) throws OldVirtualMachineException;

	/**
	 * 批量重启虚拟机
	 * 
	 * @param ids
	 *            虚拟机id
	 * @throws OldVirtualMachineException
	 */
	public void restatBatch(List<String> ids) throws OldVirtualMachineException;

	/**
	 * 虚拟机vnc访问
	 * 
	 * @param id
	 *            虚拟机id
	 * @throws OldVirtualMachineException
	 */
	public void vncAccess(String id) throws OldVirtualMachineException;

	/**
	 * 展示虚拟机详细信息，包括基本信息和监控信息
	 * 
	 * @param id
	 *            虚拟机id
	 * @throws OldVirtualMachineException
	 */
	public OldVirtualMachine display(String id) throws OldVirtualMachineException;

	public JSONObject displayHistory(String id) throws OldVirtualMachineException;

	public int count() throws OldVirtualMachineException;

	public int countByName(String name) throws OldVirtualMachineException;

	public List<OldVirtualMachine> listByName(String name, int pageNum, int pageSize) throws OldVirtualMachineException;

	public Map<String, Object> list4Table(String name, String orgId, int pageNum, int pageSize)
	        throws OldVirtualMachineException;

	public OldVirtualMachine findByVmId(String vmId) throws OldVirtualMachineException;
}
