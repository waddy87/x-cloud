package org.waddys.xcloud.project.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.waddys.xcloud.project.bo.Project;
import org.waddys.xcloud.project.po.dao.ProjectDaoService;
import org.waddys.xcloud.project.po.dao.ProjectVmDaoService;
import org.waddys.xcloud.project.po.entity.ProjectVM;
import org.waddys.xcloud.project.service.ProjectService;
import org.waddys.xcloud.user.bo.User;
import org.waddys.xcloud.user.service.service.UserService;
import org.waddys.xcloud.vm.bo.VmHost;
import org.waddys.xcloud.vm.po.dao.VmHostDaoService;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);
	
	@Autowired
    private ProjectDaoService projectDaoService;

    @Autowired
    private VmHostDaoService vmHostDaoService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectVmDaoService projectVmDaoService;

    @Override
    public void addVm(String pid, String vid) throws Exception {
        // 检查目标项目是否存在
        Project project = projectDaoService.findById(pid);
        if (project == null) {
            logger.error("目标项目不存在！");
            throw new Exception("目标项目不存在！");
        }
        // 检查目标虚机是否存在
        VmHost vmHost = vmHostDaoService.findById(vid);
        if (vmHost == null) {
            logger.error("目标虚机不存在！");
            throw new Exception("目标虚机不存在！");
        }
        // 创建关系记录
        projectVmDaoService.create(new ProjectVM(pid, vid));
        // 设置虚机分配标志
        vmHost.setIsAssigned(true);
        vmHostDaoService.updateVmHost(vmHost);
    }

    @Override
    public void removeVm(String pid, String vid) throws Exception {
        // 检查目标项目是否存在
        Project project = projectDaoService.findById(pid);
        if (project == null) {
            logger.error("目标项目不存在！");
            throw new Exception("目标项目不存在！");
        }
        // 检查目标虚机是否存在
        VmHost vmHost = vmHostDaoService.findById(vid);
        if (vmHost == null) {
            logger.error("目标虚机不存在！");
            throw new Exception("目标虚机不存在！");
        }
        // 删除项目虚机关系
        projectVmDaoService.delete(new ProjectVM(pid, vid));
        // 设置虚机分配标识
        vmHost.setIsAssigned(false);
        vmHostDaoService.updateVmHost(vmHost);
    }

    public void addUser(String pid, String uid) throws Exception {
        // 检查目标项目是否存在
        Project project = projectDaoService.findById(pid);
        if (project == null) {
            logger.error("目标项目不存在！");
            throw new Exception("目标项目不存在！");
        }
        // 检查目标用户是否存在
        User user = userService.findUser(uid);
        if (user == null) {
            logger.error("目标用户不存在！");
            throw new Exception("目标用户不存在！");
        }
        // 创建关系记录
        Set<User> users = project.getUsers();
        if (CollectionUtils.isEmpty(users)) {
            users = new HashSet<User>();
        }
        users.add(user);
        project.setUsers(users);
        projectDaoService.updateProject(project);
    }

    @Override
    public void addUsers(String pid, Set<User> users) throws Exception {
        // 检查目标项目是否存在
        Project project = projectDaoService.findById(pid);
        if (project == null) {
            logger.error("目标项目不存在！");
            throw new Exception("目标项目不存在！");
        }
        // 创建关系记录
        Set<User> userSet = project.getUsers();
        if (CollectionUtils.isEmpty(userSet)) {
            userSet = new HashSet<User>();
        }
        userSet.addAll(users);
        project.setUsers(userSet);
        projectDaoService.updateProject(project);
    }

    @Override
    public void addUser(String pid, User user) throws Exception {
        if (user == null) {
            logger.error("目标用户不存在！");
            throw new Exception("目标用户不存在！");
        }
        Set<User> userSet = new HashSet<User>();
        userSet.add(user);
        addUsers(pid, userSet);
    }

    @Override
    public void removeUsers(String pid, Set<User> users) throws Exception {
        // 检查目标项目是否存在
        Project project = projectDaoService.findById(pid);
        if (project == null) {
            logger.error("目标项目不存在！");
            throw new Exception("目标项目不存在！");
        }
        // 移出关系记录
        Set<User> userSet = project.getUsers();
        if (CollectionUtils.isEmpty(userSet)) {
            userSet = new HashSet<User>();
        }
        userSet.removeAll(users);
        project.setUsers(userSet);
        // 更新数据库
        projectDaoService.updateProject(project);
    }

    @Override
    public void removeUser(String pid, User user) throws Exception {
        // 检查目标项目是否存在
        Project project = projectDaoService.findById(pid);
        if (project == null) {
            logger.error("目标项目不存在！");
            throw new Exception("目标项目不存在！");
        }
        if (user == null) {
            logger.error("目标用户不存在！");
            throw new Exception("目标用户不存在！");
        }
        // 移出目标用户 userSet.remove(user);
        Set<User> userSet = new HashSet<User>();
        for (User source : project.getUsers()) {
            if (source.getId().equals(user.getId()))
                continue;
            userSet.add(source);
        }
        project.setUsers(userSet);
        // 更新数据库
        projectDaoService.updateProject(project);
    }

    @Override
    public Set<User> listUser(String pid) {
        // 检查目标项目是否存在
        Project project = projectDaoService.findById(pid);
        return project.getUsers();
    }

    @Override
    public void deleteById(String id) throws Exception {
        logger.info("开始删除项目：" + id);
        // 参数校验
        Project project = projectDaoService.findById(id);
        if (project == null)
            throw new Exception("目标项目不存在！");
        // 约束条件
        List<VmHost> vms = vmHostDaoService.listByProject(id);
        if (!CollectionUtils.isEmpty(vms)) {
            logger.error("拒绝删除，请先移除该项目下的虚机！");
            throw new Exception("拒绝删除，请先删除该项目下的虚机！");
        }
        // 逻辑删除：删除数据库记录（标记删除完成）
        try {
            project.setStatus("P");
            project.setName(project.getName() + "-DELETED-" + new Date().getTime());
            projectDaoService.updateProject(project);
        } catch (Exception e) {
            throw new Exception("更新数据库状态失败！", e);
        }
        logger.info("完成删除项目：" + id);

    }

    @Override
    public Page<Project> pageByBO(Project search, Pageable pageable) {
        return projectDaoService.findByBO(search, pageable);
    }

    @Override
    public Project findByVm(String vmId) {
    	return projectDaoService.findByVm(vmId);
    }

    @Override
    public Page<Project> list(char status, String name, PageRequest pageRequest) {
        return projectDaoService.ListProjects(status, name, pageRequest);
    }

    @Override
    public List<Project> listAll() {
        List<Project> list = null;
        list = projectDaoService.findAllProject();
        return list;
    }

    @Override
    public Project add(Project project) {
        // TODO Auto-generated method stub
        return projectDaoService.addProject(project);
    }

    @Override
    public Project update(Project project) throws Exception {
        // TODO Auto-generated method stub
        if (project.getId() == null || project.getId().length() == 0) {
            throw new Exception("要更新的项目无项目ID。");
        }
        return projectDaoService.updateProject(project);
    }

    @Override
    public Project show(String id) {
        // TODO Auto-generated method stub
        return projectDaoService.findById(id);
    }

    @Override
    public Long total(char status, String name) {
        // TODO Auto-generated method stub
        return projectDaoService.countByStatusAndname(status, name);
    }

    @Override
    public Page<Project> listByOrg(String oid, PageRequest pageRequest) {
        // TODO Auto-generated method stub
        Page<Project> list = projectDaoService.listByOrg(oid, pageRequest);
        return list;
    }

    @Override
    public Long totalByOrg(String oid) {
        // TODO Auto-generated method stub
        return projectDaoService.totalByOrg(oid);
    }

    @Override
    public List<VmHost> listVm(String id) {
        // TODO Auto-generated method stub
        return vmHostDaoService.listByProject(id);
    }

}
