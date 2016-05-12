package com.sugon.cloudview.cloudmanager.resource.service.bo.vdc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ComputingPool {
    private String computingPoolId;
    private String clusterId;// 暂时不用
    private String rpId;// 暂时不用
    private String cptName;
    private long cpuTotCapacity;
    private long cpuUsedCapacity;
    private long cpuAvlCapacity;
    private long memoryTotCapacity;
    private long memoryUsedCapacity;
    private long memoryAvlCapacity;
    private Date synDate;
    private Boolean isAvl;// 是否可用 true 1 可用 false 0不可用
    private String configId;
    private String configName;
    private List<StoragePool> storagePools = new ArrayList<StoragePool>();
    private Boolean isDistribute;// 是否被分配，true 1 已分配 false
                                 // 0未分配，被分配的计算池不能再被其他提供者vdc使用

    public Boolean getIsDistribute() {
        return isDistribute;
    }

    public void setIsDistribute(Boolean isDistribute) {
        this.isDistribute = isDistribute;
    }

    public List<StoragePool> getStoragePools() {
        return storagePools;
    }

    public void setStoragePools(List<StoragePool> storagePools) {
        this.storagePools = storagePools;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
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

    @Override
    public String toString() {
        return "ComputingPool [computingPoolId=" + computingPoolId
                + ", clusterId=" + clusterId + ", rpId=" + rpId + ", cptName="
                + cptName + ", cpuTotCapacity=" + cpuTotCapacity
                + ", cpuUsedCapacity=" + cpuUsedCapacity + ", cpuAvlCapacity="
                + cpuAvlCapacity + ", memoryTotCapacity=" + memoryTotCapacity
                + ", memoryUsedCapacity=" + memoryUsedCapacity
                + ", memoryAvlCapacity=" + memoryAvlCapacity + ", synDate="
                + synDate + ", isAvl=" + isAvl + ", configId=" + configId
                + ", configName=" + configName + ", storagePools="
                + storagePools.size() + ", isDistribute=" + isDistribute + "]";
    }

}
