package com.sugon.cloudview.cloudmanager.project.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sugon.cloudview.cloudmanager.project.dao.entity.ProjectVM;

public interface ProjectVmRepository extends JpaRepository<ProjectVM, Long> {

    @Override
    public ProjectVM save(ProjectVM projectVM);

    @Override
    public void delete(ProjectVM projectVM);

    public ProjectVM findByProjectIdAndVmId(String pid, String vid);

}
