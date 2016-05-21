package org.waddys.xcloud.resource.serviceImpl.dao.entity.vdc;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

/**
 * 提供者vDC
 * 
 * @author sunht
 *
 */
@Entity
@Table(name = "providerVDC")
public class ProviderVDCE implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    /**
     * 主键
     */
    private String pVDCId;

    @Version
    private int version;
    /**
     * 计算池id
     */
    @Column(length = 500)
    private String computingPoolId;

    /**
     * 是否公开(根据需求评审，暂时去掉)
     */
    // private Boolean isPublic;

    /**
     * 提供者vDC名称
     */
    @Column(length = 500)
    private String name;
    /**
     * vCpu数量(个)
     */
    private Integer vCpuNum;
    /**
     * vCPU剩余数量(个)
     */
    private Integer vCpuOverplus;
    private Integer vCpuUsed;
    /**
     * 内存大小(GB)
     */
    private Long memorySize;
    /**
     * 内存剩余(GB)
     */
    private Long memoryOverplus;
    private Long memoryUsed;

    /**
     * 描述
     */
    private String description;
    /**
     * 创建时间
     */
    private Date createDate;

    // @OneToMany(cascade = CascadeType.ALL)
    // private Set<ProvideVDCStoragePoolE> pVDCStoragePools;

    // @OneToMany(mappedBy = "providerVDC", cascade = CascadeType.ALL)
    // private Set<OrgVDCE> OrgVDC;

    // public Set<OrgVDCE> getOrgVDC() {
    // return OrgVDC;
    // }
    //
    // public void setOrgVDC(Set<OrgVDCE> orgVDC) {
    // OrgVDC = orgVDC;
    // }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Long getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(Long memorySize) {
        this.memorySize = memorySize;
    }

    public Long getMemoryOverplus() {
        return memoryOverplus;
    }

    public void setMemoryOverplus(Long memoryOverplus) {
        this.memoryOverplus = memoryOverplus;
    }

    public Long getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(Long memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    public String getpVDCId() {
        return pVDCId;
    }

    public Integer getvCpuUsed() {
        return vCpuUsed;
    }

    public void setvCpuUsed(Integer vCpuUsed) {
        this.vCpuUsed = vCpuUsed;
    }

    public void setpVDCId(String pVDCId) {
        this.pVDCId = pVDCId;
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

    public String getComputingPoolId() {
        return computingPoolId;
    }

    public void setComputingPoolId(String computingPoolId) {
        this.computingPoolId = computingPoolId;
    }

    public Integer getvCpuOverplus() {
        return vCpuOverplus;
    }

    public void setvCpuOverplus(Integer vCpuOverplus) {
        this.vCpuOverplus = vCpuOverplus;
    }

}
