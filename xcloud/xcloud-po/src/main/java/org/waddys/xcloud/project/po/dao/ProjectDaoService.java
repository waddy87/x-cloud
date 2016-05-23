package org.waddys.xcloud.project.po.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.waddys.xcloud.project.bo.Project;

public interface ProjectDaoService {

    public Project addProject(Project project);

    public Project updateProject(Project project);

    public Project showProject(String name);

    public Project findById(String id);

    public Page<Project> ListProjects(char status, String name, PageRequest pageRequest);

    public Page<Project> listByOrg(String oid, PageRequest pageRequest);

    public Page<Project> findByBO(Project search, Pageable pageable);

    public void deleteProject(String name);

    public List<Project> findAllProject();

    public long countByStatusAndname(char status, String name);

    public void deleteProjectById(String id);

    public Long totalByOrg(String oid);

	public Project findByVm(String vmId);

}
