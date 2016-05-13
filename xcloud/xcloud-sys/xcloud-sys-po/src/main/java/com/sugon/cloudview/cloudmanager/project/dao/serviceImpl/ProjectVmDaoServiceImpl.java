package com.sugon.cloudview.cloudmanager.project.dao.serviceImpl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sugon.cloudview.cloudmanager.project.dao.entity.ProjectVM;
import com.sugon.cloudview.cloudmanager.project.dao.repository.ProjectVmRepository;
import com.sugon.cloudview.cloudmanager.project.dao.service.ProjectVmDaoService;

@Component("projectVmDaoService")
@Transactional
public class ProjectVmDaoServiceImpl implements ProjectVmDaoService {

    @Autowired
    private ProjectVmRepository projectVmRepository;

    @Override
    public ProjectVM create(ProjectVM projectVM) {
        // TODO Auto-generated method stub
        return projectVmRepository.save(projectVM);
    }

    @Override
    public void delete(ProjectVM projectVM) {
        // TODO Auto-generated method stub
        ProjectVM target = projectVmRepository.findByProjectIdAndVmId(projectVM.getProjectId(), projectVM.getVmId());
        projectVmRepository.delete(target);
    }

}
