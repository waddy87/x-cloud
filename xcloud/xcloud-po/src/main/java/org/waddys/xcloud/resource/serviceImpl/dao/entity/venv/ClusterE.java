package org.waddys.xcloud.resource.serviceImpl.dao.entity.venv;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

/**
 * 集群（是否可以不需要？）
 * 
 * @author sunht
 *
 */
@Entity(name = "Cluster")
public class ClusterE implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 集群id（底层cloudVM中集群 id）
	 */
	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "clusterId", length = 255)
	private String clusterId;
    /**
     * 连接信息，底层cloudvm
     */
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "configId")
    private VenvConfigE configInfo;
    /*
     * 集群名称
     */
    @Column(name = "name", length = 255)
    private String name;

    /*
     * CPU总容量 cpuTotalCapacity简写cpuTotCapacity
     */
    @Column(name = "cpuTotCapacity", length = 200)
    private Double cpuTotCapacity;

    /*
     * CPU已用容量cpuUsedCapacity
     */
    @Column(name = "cpuUsedCapacity", length = 200)
    private Double cpuUsedCapacity;

    /*
     * CPU可用容量 cpuAvailableCapacity简写cpuAvlCapacity
     */
    @Column(name = "cpuAvlCapacity", length = 200)
    private Double cpuAvlCapacity;

    /*
     * 内存总容量 memoryTotalCapacity简写memoryTotCapacity
     */
    @Column(name = "memoryTotCapacity")
    private Integer memoryTotCapacity;

    /*
     * 内存已用容量
     */
    @Column(name = "memoryUsedCapacity")
    private Integer memoryUsedCapacity;

    /*
     * 内存可用容量 memoryAvailableCapacity简写memoryAvlCapacity
     */
    @Column(name = "memoryAvlCapacity")
    private Integer memoryAvlCapacity;

    /**
     * 创建时间
     */
    private Date synDate;
    /**
     * 
     */
    @OneToMany(mappedBy = "cluster", cascade = CascadeType.ALL)
    private List<HostE> host;
    /**
     * 
     */
    @OneToMany(mappedBy = "cluster", cascade = CascadeType.ALL)
    private List<ResourcePoolE> resourcePool;
    /**
     * 
     */
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinTable(name = "cluster_cloudvmstoragepool", joinColumns = @JoinColumn(name = "clusterId") , inverseJoinColumns = @JoinColumn(name = "storagePoolId") )
    private Set<CloudvmStoragePoolE> cloudvmStoragePoolE;
    

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

    public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

    public VenvConfigE getConfigInfo() {
        return configInfo;
    }

    public void setConfigInfo(VenvConfigE configInfo) {
        this.configInfo = configInfo;
    }

    public List<HostE> getHost() {
        return host;
    }

    public void setHost(List<HostE> host) {
        this.host = host;
    }

    public List<ResourcePoolE> getResourcePool() {
        return resourcePool;
    }

    public void setResourcePool(List<ResourcePoolE> resourcePool) {
        this.resourcePool = resourcePool;
    }

    public Set<CloudvmStoragePoolE> getCloudvmStoragePoolE() {
        return cloudvmStoragePoolE;
    }

    public void setCloudvmStoragePoolE(Set<CloudvmStoragePoolE> cloudvmStoragePoolE) {
        this.cloudvmStoragePoolE = cloudvmStoragePoolE;
    }



   

}
