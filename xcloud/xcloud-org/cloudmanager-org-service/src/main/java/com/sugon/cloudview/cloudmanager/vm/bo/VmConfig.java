package com.sugon.cloudview.cloudmanager.vm.bo;

/**
 * 虚机配置实体
 * 
 * @author zhangdapeng
 *
 */
public class VmConfig {

    /**
     * 虚机业务唯一标识
     */
    private String vmId;

    /**
     * 虚机内部唯一标识（wmware）
     */
    private String internalId;

    private String name;

    /**
     * 虚拟cpu数量
     */
    private Integer vCpuNumer = 1;

    /**
     * 虚拟mem容量(MB)
     */
    private Long vMemCapacity;

    /**
     * 计算池唯一标识
     */
    private String cPoolId;

    /**
     * 存储池唯一标识
     */
    private String sPoolId;

    /**
     * 存储容量（GB）
     */
    private Long storCapacity;

    /**
     * 虚机模板唯一标识
     */
    private String templateId;

    /**
     * 操作任务唯一标识
     */
    private String taskId;

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getvCpuNumer() {
        return vCpuNumer;
    }

    public void setvCpuNumer(Integer vCpuNumer) {
        this.vCpuNumer = vCpuNumer;
    }

    public Long getvMemCapacity() {
        return vMemCapacity;
    }

    public void setvMemCapacity(Long vMemCapacity) {
        this.vMemCapacity = vMemCapacity;
    }

    public String getcPoolId() {
        return cPoolId;
    }

    public void setcPoolId(String cPoolId) {
        this.cPoolId = cPoolId;
    }

    public String getsPoolId() {
        return sPoolId;
    }

    public void setsPoolId(String sPoolId) {
        this.sPoolId = sPoolId;
    }

    public Long getStorCapacity() {
        return storCapacity;
    }

    public void setStorCapacity(Long storCapacity) {
        this.storCapacity = storCapacity;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        // return JsonUtil.toJson(this);
        // return this == null ? null : JsonUtil.toJson(this);
        return "{vm=" + internalId + ",name=" + name + ",cpool=" + cPoolId + ",spool="
                + sPoolId + ",template=" + templateId
 + "}";
    }

}
