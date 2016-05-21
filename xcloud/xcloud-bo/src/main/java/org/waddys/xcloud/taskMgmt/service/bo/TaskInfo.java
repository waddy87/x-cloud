package org.waddys.xcloud.taskMgmt.service.bo;

import java.util.Date;

public class TaskInfo {
    /**
     * 任务id
     */
    private String taskinfoId;

    /**
     * 任务名称
     */
    private String taskinfoName;

    /**
     * 资源名称
     */
    private String resourceName;

    public String getTaskinfoDetail() {
        return taskinfoDetail;
    }

    public void setTaskinfoDetail(String taskinfoDetail) {
        this.taskinfoDetail = taskinfoDetail;
    }

    /**
     * 任务描述
     */
    private String taskinfoDetail;

    /**
     * 资源名称
     */
    private String resourceId;
    /**
     * 资源类型
     */
    private String resourceType;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 组织ID
     */
    private String orgId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * 任务进度
     */
    private Integer process;
    /**
     * 任务状态
     */
    private String status;
    /**
     * 任务描述
     */
    private String description;

    /**
     * 开始时间
     */
    private Date createDate;
    /**
     * 完成时间
     */
    private Date completDate;

    public Date getCompletDate() {
        return completDate;
    }

    public void setCompletDate(Date completDate) {
        this.completDate = completDate;
    }

    public String getTaskinfoId() {
        return taskinfoId;
    }

    public void setTaskinfoId(String taskinfoId) {
        this.taskinfoId = taskinfoId;
    }

    public String getTaskinfoName() {
        return taskinfoName;
    }

    public void setTaskinfoName(String taskinfoName) {
        this.taskinfoName = taskinfoName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Integer getProcess() {
        return process;
    }

    public void setProcess(Integer process) {
        this.process = process;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "TaskInfo [taskinfoId=" + taskinfoId + ", taskinfoName=" + taskinfoName + ", resourceName="
                + resourceName + ", resourceId=" + resourceId + ", resourceType=" + resourceType + ", userId=" + userId
                + ", orgId=" + orgId + ", process=" + process + ", status=" + status + ", description=" + description
                + ", createDate=" + createDate + ", completDate=" + completDate + "]";
    }
}
