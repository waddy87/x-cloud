package com.sugon.cloudview.cloudmanager.taskMgmt.serviceImpl.dao.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sugon.cloudview.cloudmanager.taskMgmt.serviceImpl.dao.entity.TaskInfoE;

public interface TaskInfoRepository
		extends PagingAndSortingRepository<TaskInfoE, String>, JpaSpecificationExecutor<TaskInfoE> {
	// 组织成员-start
	@Query("SELECT task from VmHostE vm, TaskInfoE task where vm.ownerId=?1 and vm.internalId=task.resourceId order by task.createDate desc")
	Page<TaskInfoE> findAll(String ownerId, Pageable pageable);

	@Query("SELECT task from VmHostE vm, TaskInfoE task where vm.ownerId=?1 and task.resourceName like ?2 and vm.internalId=task.resourceId order by task.createDate desc")
	Page<TaskInfoE> findAllByresourceName(String ownerId, String resourceName, Pageable pageable);

	@Query("SELECT task from VmHostE vm, TaskInfoE task where vm.ownerId=?1 and task.createDate >= ?2 and task.completDate <= ?3 and vm.internalId=task.resourceId order by task.createDate desc")
	Page<TaskInfoE> findAllBycreateDate(String ownerId, Date startDate, Date endDate, Pageable pageable);

	@Query("SELECT task from VmHostE vm, TaskInfoE task where vm.ownerId=?1 and task.createDate >= ?2 and vm.internalId=task.resourceId order by task.createDate desc")
	Page<TaskInfoE> findAllBycreateDatestartDate(String ownerId, Date startDate, Pageable pageable);

	@Query("SELECT task from VmHostE vm, TaskInfoE task where vm.ownerId=?1 and task.completDate <= ?2 and vm.internalId=task.resourceId order by task.createDate desc")
	Page<TaskInfoE> findAllBycreateDateendDate(String ownerId, Date endDate, Pageable pageable);

	@Query("SELECT task from VmHostE vm, TaskInfoE task where vm.ownerId=?1 and task.createDate >=2 and task.completDate <= ?3 and task.resourceName like ?4 and vm.internalId=task.resourceId order by task.createDate desc")
	Page<TaskInfoE> findAllBycreateDateandresourceName(String ownerId, Date startDate, Date endDate,
			String resourceName, Pageable pageable);

	@Query("SELECT task from VmHostE vm, TaskInfoE task where vm.ownerId=?1 and task.createDate >= ?2  and task.resourceName like ?3 and vm.internalId=task.resourceId order by task.createDate desc")
	Page<TaskInfoE> findAllBycreateDateandresourceNamestartDate(String ownerId, Date startDate, String resourceName,
			Pageable pageable);

	@Query("SELECT task from VmHostE vm, TaskInfoE task where vm.ownerId=?1 and task.completDate <= ?2  and task.resourceName like ?3 and vm.internalId=task.resourceId order by task.createDate desc")
	Page<TaskInfoE> findAllBycreateDateandresourceNameendDate(String ownerId, Date endDate, String resourceName,
			Pageable pageable);

	// 组织成员-end
	// 组织管理员-start
	@Query("SELECT task from VmHostE vm, TaskInfoE task where vm.orgId=?1 and vm.internalId=task.resourceId order by task.createDate desc")
	Page<TaskInfoE> findAllorg(String orgId, Pageable pageable);

	@Query("SELECT task from VmHostE vm, TaskInfoE task where vm.orgId=?1 and task.resourceName like ?2 and vm.internalId=task.resourceId order by task.createDate desc")
	Page<TaskInfoE> findAllByresourceNameorg(String orgId, String resourceName, Pageable pageable);

	@Query("SELECT task from VmHostE vm, TaskInfoE task where vm.orgId=?1 and task.createDate >=2 and task.completDate <= ?3 and vm.internalId=task.resourceId order by task.createDate desc")
	Page<TaskInfoE> findAllBycreateDateorg(String orgId, Date startDate, Date endDate, Pageable pageable);

	@Query("SELECT task from VmHostE vm, TaskInfoE task where vm.orgId=?1 and task.createDate > ?2  and vm.internalId=task.resourceId order by task.createDate desc")
	Page<TaskInfoE> findAllBycreateDateorgstartTime(String orgId, Date startDate, Pageable pageable);

	@Query("SELECT task from VmHostE vm, TaskInfoE task where vm.orgId=?1 and task.completDate < ?2  and vm.internalId=task.resourceId order by task.createDate desc")
	Page<TaskInfoE> findAllBycreateDateorgendTime(String orgId, Date endDate, Pageable pageable);

	@Query("SELECT task from VmHostE vm, TaskInfoE task where vm.orgId=?1 and task.createDate >=2 and task.completDate <= ?3 and task.resourceName like ?4 and vm.internalId=task.resourceId order by task.createDate desc")
	Page<TaskInfoE> findAllBycreateDateandresourceNameorg(String orgId, Date startDate, Date endDate,
			String resourceName, Pageable pageable);

	@Query("SELECT task from VmHostE vm, TaskInfoE task where vm.orgId=?1 and task.completDate <= ?2 and task.resourceName like ?4 and vm.internalId=task.resourceId order by task.createDate desc")
	Page<TaskInfoE> findAllBycreateDateandresourceNameorgendDate(String orgId, Date endDate, String resourceName,
			Pageable pageable);

	@Query("SELECT task from VmHostE vm, TaskInfoE task where vm.orgId=?1 and task.createDate >= ?2 and task.resourceName like ?4 and vm.internalId=task.resourceId order by task.createDate desc")
	Page<TaskInfoE> findAllBycreateDateandresourceNameorgstartDate(String orgId, Date satartDate, String resourceName,
			Pageable pageable);

	// 组织管理员-end
	List<TaskInfoE> findByStatus(String status);
}
