package org.waddys.xcloud.vm.po.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.waddys.xcloud.db.EntityBase;
import org.waddys.xcloud.project.po.entity.ProjectE;
import org.waddys.xcloud.vm.constant.RunStatus;
import org.waddys.xcloud.vm.constant.SourceType;
import org.waddys.xcloud.vm.constant.VmStatus;

@Entity
@Table(name = "vm_host")
public class VmHostE extends EntityBase{
    private static final long serialVersionUID = -8942275116405514036L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(nullable = false, unique = true, length = 32)
    private String id;

    @Column(length = 128)
    private String name;

    /**
     * 虚机内部唯一标识（wmware）
     */
    @Column(name = "internal_id")
    private String internalId;

    /**
     * 虚机内部名称
     */
    @Column(name = "internal_name")
    private String internalName;

    @Column(length = 512)
    private String address;

    @Column(nullable = true, length = 512)
    private String remarks;

    /**
     * 创建者：组织管理员、运营管理员、系统（例旧）
     */
    @Column(name = "creater_id", length = 32)
    private String createrId;

    /**
     * 拥有者：分配给某组织成员
     */
    @Column(name = "owner_id", length = 32)
    private String ownerId;
    
    @ManyToOne
    @JoinColumn(name="project_id")
    private ProjectE project;
    
//    @OneToMany(mappedBy="host",cascade={CascadeType.PERSIST})
    @OneToMany(mappedBy="vmId")
    private Set<VmNetE> nets = new HashSet<VmNetE>();

    /**
     * 虚机来源：申请、分配
     */
    @Column(name = "source_type")
    private SourceType sourceType;

    /**
     * 是否分配给项目：true-已分配、false-未分配
     */
    @Column(name = "assign_flag", nullable = false, length = 32)
    private Boolean isAssigned = false;

    /**
     * 所属组织标识
     */
    @Column(name = "org_id", nullable = false, length = 32)
    private String orgId;

    /**
     * 所属组织名称
     */
    // private String orgName;
    // @ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, optional
    // = true)
    // @JoinColumn(name = "organization_id", referencedColumnName = "id")
    // private OrganizationE organization;

    /**
     * 所属VDC标识（提供者vdc）
     */
    @Column(name = "vdc_id", nullable = false, length = 32)
    private String vdcId;

    /**
     * 计算池唯一标识
     */
    @Column(name = "cpool_id", nullable = false, length = 32)
    private String cPoolId;

    /**
     * 虚拟cpu数量
     */
    @Column(name = "vcpu_number", nullable = false)
    private int vCpuNumer = 1;

    /**
     * 虚拟mem容量(MB)
     */
    @Column(name = "vmem_capacity", nullable = false)
    private Long vMemCapacity;

    /**
     * 存储池唯一标识
     */
    @Column(name = "spool_id", nullable = false, length = 32)
    private String sPoolId;

    /**
     * 存储容量（GB）
     */
    @Column(name = "stor_capacity", nullable = false)
    private Long storCapacity;

    /**
     * 虚机模板唯一标识
     */
    @Column(name = "template_id", nullable = false, length = 32)
    private String templateId;

    /**
     * OS用户名
     */
    private String osUsername;

    /**
     * OS密码
     */
    private String osPassword;

    /**
     * 操作任务唯一标识
     */
    @Column(name = "task_id", length = 32)
    private String taskId;

    /**
     * 操作状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "run_status")
    private RunStatus runStatus;

    /**
     * 虚机状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "vm_status")
    private VmStatus vmStatus;

    /**
     * 可用状态：true-可用、不可用
     */
    private Boolean isAvailable;

    /**
     * 有效状态
     */
    @Column(length = 1, nullable = false)
    private String status;

    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    public VmHostE() {

    }

    public VmHostE(String name, String address, String remarks, String creater, String owner, Date createTime,
            String status) {
        super();
        this.name = name;
        this.address = address;
        this.remarks = remarks;
        this.createTime = createTime;
        this.status = status;
    }

    public VmHostE(String id, String name, String address, String remarks, String creater, String owner,
            Date createTime, String status) {
        super();
        this.id = id;
        this.name = name;
        this.address = address;
        this.remarks = remarks;
        this.createTime = createTime;
        this.status = status;
    }

    public VmStatus getVmStatus() {
        return vmStatus;
    }

    public void setVmStatus(VmStatus vmStatus) {
        this.vmStatus = vmStatus;
    }

    public Boolean getIsAssigned() {
        return isAssigned;
    }

    public void setIsAssigned(Boolean isAssigned) {
        this.isAssigned = isAssigned;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public RunStatus getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(RunStatus runStatus) {
        this.runStatus = runStatus;
    }

    public String getVdcId() {
        return vdcId;
    }

    public void setVdcId(String vdcId) {
        this.vdcId = vdcId;
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

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
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

	public ProjectE getProject() {
		return project;
	}

	public void setProject(ProjectE project) {
		this.project = project;
	}
	
	public void addNet(VmNetE net){
		this.nets.add(net);
	}

    // @Query("select org.name from organization org where org.id=:aaa")
    // public String getOrgName(@Param("aaa") String orgId) {
    // return orgName;
    // }
    //
    // public void setOrgName(String orgName) {
    // this.orgName = orgName;
    // }

}
