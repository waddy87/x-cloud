package org.waddys.xcloud.vm.bo;

import java.util.Date;
import java.util.List;

import org.waddys.xcloud.org.bo.Organization;
import org.waddys.xcloud.project.bo.Project;
import org.waddys.xcloud.res.bo.vdc.ProviderVDC;
import org.waddys.xcloud.res.bo.vdc.StoragePool;
import org.waddys.xcloud.user.bo.User;
import org.waddys.xcloud.vm.constant.RunStatus;
import org.waddys.xcloud.vm.constant.SourceType;
import org.waddys.xcloud.vm.constant.VmStatus;

/**
 * 虚机业务实体
 * 
 * @author zhangdapeng
 *
 */
public class VmHost extends Asset {

    /**
     * 虚机业务唯一标识
     */
    private String id;

    /**
     * 虚机业务名称
     */
    private String name;

    /**
     * 虚机内部唯一标识（wmware）
     */
    private String internalId;

    /**
     * 虚机内部名称
     */
    private String internalName;

    /**
     * 备注信息
     */
    private String remarks;

    /**
     * 所属组织唯一标识
     */
    private String orgId;

    /**
     * 所属组织
     */
    private Organization organization;

    /**
     * 创建者：组织管理员、运营管理员、系统（例旧）
     */
    private String createrId;

    /**
     * 拥有者：分配给某组织成员
     */
    private String ownerId;
    private User owner;
    
    /**
     * 关联项目
     */
    private Project project;

    /**
     * 是否分配给项目：true-已分配、false-未分配
     */
    private Boolean isAssigned;

    /**
     * 虚机来源：申请、分配
     */
    private SourceType sourceType = SourceType.ASSIGN;

    private String status;

    // type = java.util.Date
    private Date createTime;

    /**
     * VDC唯一标识（提供者vdc）
     */
    private String vdcId;
    private ProviderVDC vdc;

    /**
     * OS用户名
     */
    private String osUsername;

    /**
     * OS密码
     */
    private String osPassword;

    /**
     * 计算池唯一标识
     */
    private String cPoolId;

    /**
     * 虚拟cpu数量
     */
    private int vCpuNumer = 1;

    /**
     * 虚拟mem容量(MB)
     */
    private Long vMemCapacity;

    /**
     * 存储池唯一标识
     */
    private String sPoolId;
    private StoragePool sPool;

    /**
     * 存储容量（GB）
     */
    private Long storCapacity;

    /**
     * 虚机模板唯一标识
     */
    private String templateId;
    private VmTemplate template;

    /**
     * 操作任务唯一标识
     */
    private String taskId;

    /**
     * 操作状态
     */
    private RunStatus runStatus;

    /**
     * 虚机状态
     */
    private VmStatus vmStatus;

    /**
     * 可用状态：true-可用、不可用
     */
    private Boolean isAvailable = true;

    /**
     * 网络列表
     */
    private List<VmNet> nets;

    public List<VmNet> getNets() {
        return nets;
    }

    public void setNets(List<VmNet> nets) {
        this.nets = nets;
    }

    public VmHost() {
        // TODO Auto-generated constructor stub
    }

    public VmHost(String name) {
        // TODO Auto-generated constructor stub
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public RunStatus getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(RunStatus runStatus) {
        this.runStatus = runStatus;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getvCpuNumer() {
        return vCpuNumer;
    }

    public void setvCpuNumer(int vCpuNumer) {
        this.vCpuNumer = vCpuNumer;
    }

    public Long getvMemCapacity() {
        return vMemCapacity;
    }

    public void setvMemCapacity(Long vMemCapacity) {
        this.vMemCapacity = vMemCapacity;
    }

    public Long getStorCapacity() {
        return storCapacity;
    }

    public void setStorCapacity(Long storCapacity) {
        this.storCapacity = storCapacity;
    }

    public String getVdcId() {
        return vdcId;
    }

    public void setVdcId(String vdcId) {
        this.vdcId = vdcId;
    }

    public String getOsUsername() {
        return osUsername;
    }

    public void setOsUsername(String osUsername) {
        this.osUsername = osUsername;
    }

    public String getOsPassword() {
        return osPassword;
    }

    public void setOsPassword(String osPassword) {
        this.osPassword = osPassword;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Boolean getIsAssigned() {
        return isAssigned;
    }

    public void setIsAssigned(Boolean isAssigned) {
        this.isAssigned = isAssigned;
    }

    public VmStatus getVmStatus() {
        return vmStatus;
    }

    public void setVmStatus(VmStatus vmStatus) {
        this.vmStatus = vmStatus;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public ProviderVDC getVdc() {
        return vdc;
    }

    public void setVdc(ProviderVDC vdc) {
        this.vdc = vdc;
    }

    public StoragePool getsPool() {
        return sPool;
    }

    public void setsPool(StoragePool sPool) {
        this.sPool = sPool;
    }

    public VmTemplate getTemplate() {
        return template;
    }

    public void setTemplate(VmTemplate template) {
        this.template = template;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getInternalName() {
        return internalName;
    }

    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
    public String toString() {
        // return JsonUtil.toJson(this);
        // return this == null ? null : JsonUtil.toJson(this);
        // TODO Auto-generated method stub
        return "{id=" + id + ",name=" + name + ",orgId=" + orgId + ",cpool=" + cPoolId + ",spool="
                + sPoolId + ",template=" + templateId
 + ",status=" + status + ",creater=" + createrId + "}";
    }

}
