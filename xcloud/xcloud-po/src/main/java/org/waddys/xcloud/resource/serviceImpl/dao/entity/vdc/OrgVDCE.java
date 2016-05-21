package org.waddys.xcloud.resource.serviceImpl.dao.entity.vdc;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 组织vDC
 * 
 * @author sunht
 *
 */
@Entity
@Table(name = "orgVDC")
public class OrgVDCE implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    // @GeneratedValue(generator = "uuid")
    // @GenericGenerator(name = "uuid", strategy = "uuid")
    /**
     * 主键
     */
    private String orgVDCId;
    /**
     * 提供者vdc
     */
    private String pVDCId;

    // @ManyToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name = "pVDCId", unique = true)
    // private ProviderVDCE providerVDC;
    /**
     * 组织vDC名称
     */
    private String name;
    /**
     * vCpu数量(个)
     */
    private Integer vCpuNum;
    /**
     * vCPU剩余数量(个)
     */
    private Integer vCpuOverplus;
    /**
     * 内存大小(GB)
     */
    private Integer memorySize;
    /**
     * 内存剩余(GB)
     */
    private Integer memoryOverplus;
    /**
     * 描述
     */
    private String description;
    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 是否限制，true：限制 false：不限制
     */
    private Boolean isLimit;

    private String orgId;
    private String orgName;

    // @OneToMany(cascade = CascadeType.ALL)
    // @JoinColumn(name = "orgVDCId", unique = true)
    // private Set<OrgVDCStoragePoolE> orgStoragePools;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgVDCId() {
        return orgVDCId;
    }

    public Integer getvCpuOverplus() {
        return vCpuOverplus;
    }

    public void setvCpuOverplus(Integer vCpuOverplus) {
        this.vCpuOverplus = vCpuOverplus;
    }

    public Integer getMemoryOverplus() {
        return memoryOverplus;
    }

    public void setMemoryOverplus(Integer memoryOverplus) {
        this.memoryOverplus = memoryOverplus;
    }

    public void setOrgVDCId(String orgVDCId) {
        this.orgVDCId = orgVDCId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getvCpuNum() {
        return vCpuNum;
    }

    public void setvCpuNum(Integer vCpuNum) {
        this.vCpuNum = vCpuNum;
    }

    public Integer getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(Integer memorySize) {
        this.memorySize = memorySize;
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

    public Boolean getIsLimit() {
        return isLimit;
    }

    public void setIsLimit(Boolean isLimit) {
        this.isLimit = isLimit;
    }

    public String getpVDCId() {
        return pVDCId;
    }

    public void setpVDCId(String pVDCId) {
        this.pVDCId = pVDCId;
    }

    // public ProviderVDCE getProviderVDC() {
    // return providerVDC;
    // }
    //
    // public void setProviderVDC(ProviderVDCE providerVDC) {
    // this.providerVDC = providerVDC;
    // }

}
