package com.sugon.cloudview.cloudmanager.vm.dao.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.sugon.cloudview.cloudmanager.vm.bo.VmHost;
import com.sugon.cloudview.cloudmanager.vm.dao.entity.VmHostE;


public interface VmHostRepository extends JpaRepository<VmHostE, Long>, JpaSpecificationExecutor<VmHostE> {

    // List<OrganizationE> findAll();

    // Page<OrganizationE> findAll(Pageable pageable);

    // Page<OrganizationE> findByNameContainingAllIgnoringCase(String name,
    // Pageable pageable);

    VmHostE findByName(String name);

    VmHostE findByIdAllIgnoringCase(String id);

    Page<VmHostE> findByStatusAndNameContainingAllIgnoringCase(char status, String name, Pageable pageable);

    Page<VmHost> findByOrgIdAndNameContainingAllIgnoringCase(String orgId, String name, Pageable pageable);

    Page<VmHostE> findByStatusAllIgnoringCase(char status, Pageable pageable);

    Page<VmHost> findByNameContainingAllIgnoringCase(String name, Pageable pageable);

    @Query(value = "select count( distinct o) from VmHostE o where o.status=?1 and o.name like %?2%")
    long countByStatusAndName(char status, String name);

    VmHostE findById(String id);

    @Query("select count(distinct o) from VmHostE o where o.name like %?1%")
    long countByName(String name);

    @Query("select count(distinct o) from VmHostE o where o.status = ?1")
    long countByStatus(char status);

    @Query(value = "select vm.* from vm_host vm,project_vm pv where pv.vm_id=vm.id and pv.project_id=?1", nativeQuery = true)
    public List<VmHost> listByProject(String pid);

    @Query(value = "select vm from VmHostE vm,ProjectVM pv where pv.vmId=vm.id and pv.projectId=?1")
    public Page<VmHost> listByProject(String pid, Pageable pageable);

    @Query(value = "select vm from VmHostE vm,ProjectVM pv where pv.vmId=vm.id and pv.projectId=?1 and vm.name like %?2%")
    public Page<VmHost> pageByProject(String pid, String name, Pageable pageable);
    
    // @Query(value = "select (select org.name from OrganizationE org where
    // org.id=vm.orgId) as orgName,vm from VmHostE vm")
    // @Query(value = "select (select org.name from organization org where
    // org.id=vm.org_id) as orgName,vm.* from vm_host vm", nativeQuery = true)
    // @Override
    // public Page<VmHostE> findAll(Specification<VmHostE> spec, Pageable
    // pageable);

}
