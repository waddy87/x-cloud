/**
 * 
 */
package com.sugon.cloudview.cloudmanager.vm.bo;

import java.util.Date;

/**
 * 任务信息实体（用于封装虚拟化接口返回值）
 * 
 * @author zhangdapeng
 *
 */
public class VmTask {

    /**
     * 任务唯一标识
     */
    private String taskId;

    /**
     * 任务唯一标识
     */
    private String taskName;

    /**
     * 任务创建时间
     */
    private Date createTime;

    /**
     * 资源ID
     */
    private String resourceId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

}
