package org.waddys.xcloud.project.po.dao;

import org.waddys.xcloud.project.po.entity.ProjectVM;

/**
 * 项目与虚机关系管理Dao组件
 * 
 * @author zhangdapeng
 *
 */
public interface ProjectVmDaoService {

    /**
     * 创建关系
     * 
     * @param projectVM
     * @return
     */
    public ProjectVM create(ProjectVM projectVM);

    /**
     * 删除关系
     * 
     * @param projectVM
     */
    public void delete(ProjectVM projectVM);

}
