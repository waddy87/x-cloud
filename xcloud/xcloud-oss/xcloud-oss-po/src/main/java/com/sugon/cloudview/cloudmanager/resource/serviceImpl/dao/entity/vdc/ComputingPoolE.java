package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.vdc;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 计算池
 * 
 * @author sun
 *
 */
@Entity
@Table(name = "computing_pool")
public class ComputingPoolE {
    /**
     * 主键 计算池Id 标准版、企业版计算池id都对应资源池id（标准版集群默认有个根资源池）
     */
    @Id
    private String computingPoolId;

    @Column(length = 500)
    private String configId;

    /*
     * 集群id 暂时不用
     */
    @Column(length = 500)
    private String clusterId;

    /*
     * 资源池id 暂时不用
     */
    @Column(length = 500)
    private String rpId;

    /*
     * 计算池名称
     */
    @Column(length = 500)
    private String cptName;

    /*
     * CPU总容量
     */
    private long cpuTotCapacity;

    /*
     * CPU已用容量
     */
    private long cpuUsedCapacity;

    /*
     * CPU可用容量
     */
    private long cpuAvlCapacity;

    /*
     * 内存总容量 memoryTotalCapacity简写memoryTotCapacity
     */
    @Column
    private long memoryTotCapacity;

    /*
     * 内存已用容量
     */
    private long memoryUsedCapacity;

    /*
     * 内存可用容量 memoryAvailableCapacity简写memoryAvlCapacity
     */
    private long memoryAvlCapacity;

    /**
     * 同步时间
     */
    private Date synDate;

    /**
     * 是否可用
     */
    private Boolean isAvl;
    /**
     * 是否被分配，被分配的计算池不能再被其他提供者vdc使用
     */
    private Boolean isDistribute = false;

    // @ManyToMany(mappedBy = "computingPool", cascade = CascadeType.ALL)
    @ManyToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
            CascadeType.MERGE })
    @JoinTable(name = "computingPool_storagePool", joinColumns = @JoinColumn(name = "computingPool_id"), inverseJoinColumns = @JoinColumn(name = "storagePool_id"))
    private Set<StoragePoolE> storagePool;

    public Boolean getIsDistribute() {
        return isDistribute;
    }

    public void setIsDistribute(Boolean isDistribute) {
        this.isDistribute = isDistribute;
    }

    public Date getSynDate() {
        return synDate;
    }

    public void setSynDate(Date synDate) {
        this.synDate = synDate;
    }

    public Boolean getIsAvl() {
        return isAvl;
    }

    public void setIsAvl(Boolean isAvl) {
        this.isAvl = isAvl;
    }

    public String getComputingPoolId() {
        return computingPoolId;
    }

    public void setComputingPoolId(String computingPoolId) {
        this.computingPoolId = computingPoolId;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public String getRpId() {
        return rpId;
    }

    public void setRpId(String rpId) {
        this.rpId = rpId;
    }

    public String getCptName() {
        return cptName;
    }

    public void setCptName(String cptName) {
        this.cptName = cptName;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public Set<StoragePoolE> getStoragePool() {
        return storagePool;
    }

    public void setStoragePool(Set<StoragePoolE> storagePool) {
        this.storagePool = storagePool;
    }

    public long getCpuTotCapacity() {
        return cpuTotCapacity;
    }

    public void setCpuTotCapacity(long cpuTotCapacity) {
        this.cpuTotCapacity = cpuTotCapacity;
    }

    public long getCpuUsedCapacity() {
        return cpuUsedCapacity;
    }

    public void setCpuUsedCapacity(long cpuUsedCapacity) {
        this.cpuUsedCapacity = cpuUsedCapacity;
    }

    public long getCpuAvlCapacity() {
        return cpuAvlCapacity;
    }

    public void setCpuAvlCapacity(long cpuAvlCapacity) {
        this.cpuAvlCapacity = cpuAvlCapacity;
    }

    public long getMemoryTotCapacity() {
        return memoryTotCapacity;
    }

    public void setMemoryTotCapacity(long memoryTotCapacity) {
        this.memoryTotCapacity = memoryTotCapacity;
    }

    public long getMemoryUsedCapacity() {
        return memoryUsedCapacity;
    }

    public void setMemoryUsedCapacity(long memoryUsedCapacity) {
        this.memoryUsedCapacity = memoryUsedCapacity;
    }

    public long getMemoryAvlCapacity() {
        return memoryAvlCapacity;
    }

    public void setMemoryAvlCapacity(long memoryAvlCapacity) {
        this.memoryAvlCapacity = memoryAvlCapacity;
    }
}
