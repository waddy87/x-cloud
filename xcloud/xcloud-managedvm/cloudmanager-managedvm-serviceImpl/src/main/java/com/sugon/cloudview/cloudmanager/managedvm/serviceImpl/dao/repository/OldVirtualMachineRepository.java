package com.sugon.cloudview.cloudmanager.managedvm.serviceImpl.dao.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.sugon.cloudview.cloudmanager.managedvm.serviceImpl.dao.entity.OldVirtualMachineE;

public interface OldVirtualMachineRepository
        extends Repository<OldVirtualMachineE, String>, JpaSpecificationExecutor<OldVirtualMachineE> {

	List<OldVirtualMachineE> findAll();

	/**
	 * 分页查询利旧虚拟机
	 * 
	 * @param pageable
	 * @return
	 */
	Page<OldVirtualMachineE> findAll(Pageable pageable);

	OldVirtualMachineE findById(String id);

	OldVirtualMachineE findByVmId(String vmId);

	List<OldVirtualMachineE> findByIdIn(List<String> ids);

	OldVirtualMachineE save(OldVirtualMachineE oldVirtualMachineE);

	List<OldVirtualMachineE> save(Iterable<OldVirtualMachineE> oldVirtualMachineEs);

	void delete(String id);

	void delete(Iterable<OldVirtualMachineE> oldVirtualMachineEs);

	@Query("select count(distinct ovm) from OldVirtualMachineE ovm")
	int count();

	@Query("select count(distinct ovm) from OldVirtualMachineE ovm where ovm.name like %?1%")
	int countByName(String name);

	@Query("select ovm from OldVirtualMachineE ovm where ovm.name like %?1%")
	Page<OldVirtualMachineE> findByName(String name, Pageable pageable);
}
