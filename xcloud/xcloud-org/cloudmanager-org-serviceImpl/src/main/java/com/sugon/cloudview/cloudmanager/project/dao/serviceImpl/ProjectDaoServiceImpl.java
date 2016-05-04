package com.sugon.cloudview.cloudmanager.project.dao.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.sugon.cloudview.cloudmanager.project.bo.Project;
import com.sugon.cloudview.cloudmanager.project.dao.entity.ProjectE;
import com.sugon.cloudview.cloudmanager.project.dao.repository.ProjectRepository;
import com.sugon.cloudview.cloudmanager.project.dao.service.ProjectDaoService;
import com.sugon.cloudview.cloudmanager.usermgmt.service.bo.User;
import com.sugon.cloudview.cloudmanager.usermgmt.serviceimpl.dao.entity.UserE;

@Component("projectDaoService")
@Transactional
public class ProjectDaoServiceImpl implements ProjectDaoService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    public ProjectDaoServiceImpl(ProjectRepository projectRepository) {
        super();
        this.projectRepository = projectRepository;
    }

    @Override
    public Project addProject(Project project) {
        ProjectE projectE = bo2po(project);
        return po2bo(projectRepository.save(projectE));
    }

    @Override
    public Project updateProject(Project project) {
        ProjectE projectE = bo2po(project);
        return po2bo(projectRepository.saveAndFlush(projectE));
    }

    @Override
    public Project showProject(String name) {
        return po2bo(projectRepository.findByName(name));
    }

    @Override
    public Project findById(String id) {
        return po2bo(projectRepository.findById(id));
    }

    @Override
    public Page<Project> ListProjects(char status, String name, PageRequest pageRequest) {
        if(name==null)name="";
        Page<Project> pageList = projectRepository.findByStatusAndNameContainingAllIgnoringCase(status, name,
                pageRequest);
        return pageList;
    }

    @Override
    public Page<Project> findByBO(Project search, Pageable pageable) {
        Page<ProjectE> page = projectRepository.findAll(new Specification<ProjectE>() {
            @Override
            public Predicate toPredicate(Root<ProjectE> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                List<Expression<Boolean>> expressions = predicate.getExpressions();
                if (search != null) {
                    if (!StringUtils.isEmpty(search.getOrgId())) {
                        expressions.add(cb.equal(root.get("orgId"), search.getOrgId()));
                    }
                    if (!StringUtils.isEmpty(search.getStatus())) {
                        expressions.add(cb.equal(root.get("status"), search.getStatus()));
                    }
                    if (!StringUtils.isEmpty(search.getName())) {
                        expressions.add(cb.like(root.get("name"), search.getName()));
                    }
                }
                return predicate;
            }
        }, pageable);
        return page.map(new Converter<ProjectE, Project>() {
            @Override
            public Project convert(ProjectE source) {
                Project target = new Project();
                BeanUtils.copyProperties(source, target);
                return target;
            }
        });
    }

    @Override
    public void deleteProject(String name) {
        ProjectE project = projectRepository.findByName(name);
        if (null != project) {
            projectRepository.delete(project);
        }
    }

    @Override
    public void deleteProjectById(String id) {

        ProjectE projectE = projectRepository.findById(id);
        if (projectE != null) {
            projectRepository.delete(projectE);

        }
    }

    @Override
    public List<Project> findAllProject() {
        List<Project> list = new ArrayList<Project>();
        List<ProjectE> listE = projectRepository.findAll();
        for (ProjectE projectE : listE) {
            list.add(po2bo(projectE));
        }
        return list;
    }

    @Override
    public long countByStatusAndname(char status, String name) {
        // TODO Auto-generated method stub
        long num = 0;
        if (status != '\0' && (null == name || "" == name)) {
            num = projectRepository.countByStatus(status);
        } else if (status == '\0' && (null != name || "" != name)) {
            num = projectRepository.countByName(name);
        } else if (status == '\0' && (null == name || "" == name)) {
            num = projectRepository.count();
        } else {
            num = projectRepository.countByStatusAndName(status, name);
        }
        return num;
    }

    private Project po2bo(ProjectE po) {
        Project bo = new Project();
        BeanUtils.copyProperties(po, bo);
        if (!CollectionUtils.isEmpty(po.getUsers())) {
            Set<User> users = new HashSet<User>();
            for (UserE userPo : po.getUsers()) {
                User userBo = new User();
                BeanUtils.copyProperties(userPo, userBo);
                users.add(userBo);
            }
            bo.setUsers(users);
        }
        return bo;
    }

    private ProjectE bo2po(Project bo) {
        ProjectE po = new ProjectE();
        BeanUtils.copyProperties(bo, po);
        if (!CollectionUtils.isEmpty(bo.getUsers())) {
            Set<UserE> users = new HashSet<UserE>();
            for (User userBo : bo.getUsers()) {
                UserE userPo = new UserE();
                BeanUtils.copyProperties(userBo, userPo);
                users.add(userPo);
            }
            po.setUsers(users);
        }
        return po;
    }

    // public Page<Project> pageToBo(Page<ProjectE> pageList) {
    // List<Project> list = new ArrayList<Project>();
    // for (ProjectE projectE : pageList) {
    // list.add(convertToBo(projectE));
    // }
    // return list;
    // }

    @Override
    public Long totalByOrg(String oid) {
        // TODO Auto-generated method stub
        return projectRepository.findProjectsByOrgid(oid);
    }

    @Override
    public Page<Project> listByOrg(String oid, PageRequest pageRequest) {
        // TODO Auto-generated method stub
        Page<Project> pageList = projectRepository.findProjectsByOrgid(oid, pageRequest);
        return pageList;
    }

}
