package com.sugon.cloudview.cloudmanager.managedvm.serviceImpl.dao.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sugon.cloudview.cloudmanager.managedvm.serviceImpl.dao.entity.OldVirtualMachineE;

public interface OldVirtualMachineDaoService {

	/**
	 * 列出所有利旧虚拟机
	 * 
	 * @return 利旧虚拟机集合
	 */
	List<OldVirtualMachineE> findAll();

	Page<OldVirtualMachineE> findAll(Pageable pageable);

	/**
	 * 根据id查询利旧虚拟机
	 * 
	 * @param id
	 *            利旧虚拟机id
	 * @return 查询的利旧虚拟机
	 */
	OldVirtualMachineE findById(String id);

	OldVirtualMachineE findByVmId(String vmId);

	/**
	 * 根据id集合获得虚拟机集合
	 * 
	 * @param ids
	 * @return
	 */
	List<OldVirtualMachineE> findByIdIn(List<String> ids);

	/**
	 * 保存或更新利旧虚拟机
	 * 
	 * @param oldVirtualMachineE
	 * @return
	 */
	OldVirtualMachineE saveOrUpdate(OldVirtualMachineE oldVirtualMachineE);

	/**
	 * 批量保存或更新利旧虚拟机
	 * 
	 * @param oldVirtualMachineEs
	 * @return
	 */
	List<OldVirtualMachineE> saveOrUpdate(List<OldVirtualMachineE> oldVirtualMachineEs);

	/**
	 * 删除虚拟机记录
	 * 
	 * @param id
	 */
	void deleteOne(String id);

	/**
	 * 批量删除利旧虚拟机记录
	 * 
	 * @param oldVirtualMachineEs
	 */
	void deleteBatch(List<OldVirtualMachineE> oldVirtualMachineEs);

	int count();

	int countByName(String name);

	Page<OldVirtualMachineE> findByName(String name, Pageable pageable);

	Page<OldVirtualMachineE> findByEntity(OldVirtualMachineE paramOldVME, Pageable paramPageable);
}
