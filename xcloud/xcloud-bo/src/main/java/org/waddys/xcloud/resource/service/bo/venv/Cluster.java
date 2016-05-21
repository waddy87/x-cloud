package org.waddys.xcloud.resource.service.bo.venv;

import java.util.Date;
import java.util.List;

/**
 * 集群
 * @author ghj
 */
public class Cluster {
	/**
	 * 集群id（底层cloudVM中集群 id）
	 */
	private String clusterId;
    /**
     * 连接信息，底层cloudvm
     */
    private VenvConfig configInfo;
    /*
     * 集群名称
     */
    private String name;

    /*
     * CPU总容量 cpuTotalCapacity简写cpuTotCapacity
     */
    private Double cpuTotCapacity;

    /*
     * CPU已用容量cpuUsedCapacity
     */
    private Double cpuUsedCapacity;

    /*
     * CPU可用容量 cpuAvailableCapacity简写cpuAvlCapacity
     */
    private Double cpuAvlCapacity;

    /*
     * 内存总容量 memoryTotalCapacity简写memoryTotCapacity
     */
    private Integer memoryTotCapacity;

    /*
     * 内存已用容量
     */
    private Integer memoryUsedCapacity;

    /*
     * 内存可用容量 memoryAvailableCapacity简写memoryAvlCapacity
     */
    private Integer memoryAvlCapacity;

    /**
     * 数据同步时间
     */
    private Date synDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCpuTotCapacity() {
        return cpuTotCapacity;
    }

    public void setCpuTotCapacity(Double cpuTotCapacity) {
        this.cpuTotCapacity = cpuTotCapacity;
    }

    public Double getCpuUsedCapacity() {
        return cpuUsedCapacity;
    }

    public void setCpuUsedCapacity(Double cpuUsedCapacity) {
        this.cpuUsedCapacity = cpuUsedCapacity;
    }

    public Double getCpuAvlCapacity() {
        return cpuAvlCapacity;
    }

    public void setCpuAvlCapacity(Double cpuAvlCapacity) {
        this.cpuAvlCapacity = cpuAvlCapacity;
    }

    public Integer getMemoryTotCapacity() {
        return memoryTotCapacity;
    }

    public void setMemoryTotCapacity(Integer memoryTotCapacity) {
        this.memoryTotCapacity = memoryTotCapacity;
    }

    public Integer getMemoryUsedCapacity() {
        return memoryUsedCapacity;
    }

    public void setMemoryUsedCapacity(Integer memoryUsedCapacity) {
        this.memoryUsedCapacity = memoryUsedCapacity;
    }

    public Integer getMemoryAvlCapacity() {
        return memoryAvlCapacity;
    }

    public void setMemoryAvlCapacity(Integer memoryAvlCapacity) {
        this.memoryAvlCapacity = memoryAvlCapacity;
    }

    public Date getSynDate() {
        return synDate;
    }

    public void setSynDate(Date synDate) {
        this.synDate = synDate;
    }

    public List<ClusterStoragePool> getClusterStoragePool() {
        return clusterStoragePool;
    }

    public void setClusterStoragePool(List<ClusterStoragePool> clusterStoragePool) {
        this.clusterStoragePool = clusterStoragePool;
    }

    public List<Host> getHost() {
        return host;
    }

    public void setHost(List<Host> host) {
        this.host = host;
    }

    public List<ResourcePool> getResourcePool() {
        return resourcePool;
    }

    public void setResourcePool(List<ResourcePool> resourcePool) {
        this.resourcePool = resourcePool;
    }

    public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

    public VenvConfig getConfigInfo() {
        return configInfo;
    }

    public void setConfigInfo(VenvConfig configInfo) {
        this.configInfo = configInfo;
    }

    private List<ClusterStoragePool> clusterStoragePool;

    private List<Host> host;

    private List<ResourcePool> resourcePool;
}
