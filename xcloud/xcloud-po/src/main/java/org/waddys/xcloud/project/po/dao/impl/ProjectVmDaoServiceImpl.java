package org.waddys.xcloud.project.po.dao.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.waddys.xcloud.project.po.dao.ProjectVmDaoService;
import org.waddys.xcloud.project.po.dao.repository.ProjectVmRepository;
import org.waddys.xcloud.project.po.entity.ProjectVM;

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
