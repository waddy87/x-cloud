package com.sugon.cloudview.cloudmanager.project.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.sugon.cloudview.cloudmanager.project.bo.Project;
import com.sugon.cloudview.cloudmanager.usermgmt.service.bo.User;
import com.sugon.cloudview.cloudmanager.vm.bo.VmHost;

/**
 * 项目管理服务组件
 * 
 * @author zhangdapeng
 *
 */
public interface ProjectService {

    /**
     * 添加一个项目
     * 
     * @param project
     * @return
     */
    public Project add(Project project);

    /**
     * 删除一个项目
     * 
     * @param id
     *            项目唯一标识
     * @throws Exception
     */
    public void deleteById(String id) throws Exception;

    /**
     * 修改一个项目
     * 
     * @param project
     * @return
     * @throws Exception
     */
    public Project update(Project project) throws Exception;

    /**
     * 显示全部项目列表
     * 
     * @return
     */
    public List<Project> listAll();

    /**
     * 显示项目列表（支持过滤、分页）
     * 
     * @param status
     *            有效状态（A-有效、P-无效）
     * @param name
     *            项目名称
     * @param page
     *            当前页码
     * @param per_page
     *            每页条数
     * @return
     */
    public Page<Project> list(char status, String name, PageRequest pageRequest);

    /**
     * 根据组织显示项目列表
     * 
     * @param oid
     *            组织唯一标识
     * @param page
     *            当前页码
     * @param per_page
     *            每页条数
     * @return
     */
    public Page<Project> listByOrg(String oid, PageRequest pageRequest);

    /**
     * 显示一个项目的详情信息
     * 
     * @param id
     * @return
     */
    public Project show(String id);

    /**
     * 统计全部项目总数
     * 
     * @param status
     * @param name
     * @return
     */
    public Long total(char status, String name);

    /**
     * 根据组织统计项目总数
     * 
     * @param oid
     *            组织唯一标识
     * @return
     */
    public Long totalByOrg(String oid);

    /**
     * 显示该项目下全部虚机列表
     * 
     * @param id
     *            项目唯一标识
     * @return
     */
    public List<VmHost> listVm(String id);

    /**
     * 为该项目关联一个虚机（关系管理）
     * 
     * @param pid
     *            项目唯一标识
     * @param vid
     *            虚机唯一标识
     * @throws Exception
     */
    public void addVm(String pid, String vid) throws Exception;

    /**
     * 为该项目移除一个虚机（关系管理）
     * 
     * @param pid
     *            项目唯一标识
     * @param vid
     *            虚机唯一标识
     * @throws Exception
     */
    public void removeVm(String pid, String vid) throws Exception;

    /**
     * 为该项目关联用户（批量）
     * 
     * @param pid
     *            项目唯一标识
     * @param users
     *            目标用户集合
     * @throws Exception
     */
    public void addUsers(String pid, Set<User> users) throws Exception;

    /**
     * 为该项目关联用户
     * 
     * @param pid
     *            项目唯一标识
     * @param user
     *            目标用户
     * @throws Exception
     */
    public void addUser(String pid, User user) throws Exception;

    /**
     * 为该项目移出用户（批量）
     * 
     * @param pid
     *            项目唯一标识
     * @param users
     *            目标用户集合
     * @throws Exception
     */
    public void removeUsers(String pid, Set<User> users) throws Exception;

    /**
     * 为该项目移出用户
     * 
     * @param pid
     *            项目唯一标识
     * @param user
     *            目标用户
     * @throws Exception
     */
    public void removeUser(String pid, User user) throws Exception;

    /**
     * 获取指定项目下的用户列表
     * 
     * @param pid
     *            项目唯一标识
     * @return
     */
    public Set<User> listUser(String pid);

    /**
     * 分页查询项目列表
     * 
     * @param search
     * @param pageable
     * @return
     */
    public Page<Project> pageByBO(Project search, Pageable pageable);

}
