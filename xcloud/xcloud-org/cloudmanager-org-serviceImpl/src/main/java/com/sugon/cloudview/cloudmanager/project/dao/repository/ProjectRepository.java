package com.sugon.cloudview.cloudmanager.project.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.sugon.cloudview.cloudmanager.project.bo.Project;
import com.sugon.cloudview.cloudmanager.project.dao.entity.ProjectE;


public interface ProjectRepository extends JpaRepository<ProjectE, Long>, JpaSpecificationExecutor<ProjectE> {

    ProjectE findByName(String name);

    ProjectE findByIdAllIgnoringCase(String id);

    Page<Project> findByStatusAndNameContainingAllIgnoringCase(char status, String name, Pageable pageable);

    Page<ProjectE> findByStatusAllIgnoringCase(char status, Pageable pageable);

    Page<ProjectE> findByNameContainingAllIgnoringCase(String name, Pageable pageable);

    @Query(value = "select count( distinct o) from ProjectE o where o.status=?1 and o.name like %?2%")
    long countByStatusAndName(char status, String name);

    ProjectE findById(String id);

    @Query("select count(distinct o) from ProjectE o where o.name like %?1%")
    long countByName(String name);

    @Query("select count(distinct o) from ProjectE o where o.status = ?1")
    long countByStatus(char status);
    
    @Query("select p from ProjectE p where p.orgId = ?1")
    public Page<Project> findProjectsByOrgid(String orgid, Pageable pageable);

    @Query("select count(distinct p) from ProjectE p where p.orgId = ?1")
    public long findProjectsByOrgid(String orgid);

}
