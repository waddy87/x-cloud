package org.waddys.xcloud.res.po.entity.venv;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

/**
 * 资源池（是否可以不需要？）
 * 
 * @author sunht
 *
 */
@Entity(name = "ResourcePool")
public class ResourcePoolE implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 资源池id（底层cloudVM中资源池 id）
	 */
	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "rpId", length = 255)
	private String rpId;
	/**
	 * 集群id
	 */
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "clusterId")
    private ClusterE cluster;

    /**
     * 连接信息，底层cloudvm
     */
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "configId")
    private VenvConfigE configInfo;

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

    public String getRpId() {
		return rpId;
	}

    public VenvConfigE getConfigInfo() {
        return configInfo;
    }

    public void setConfigInfo(VenvConfigE configInfo) {
        this.configInfo = configInfo;
    }

    public void setRpId(String rpId) {
		this.rpId = rpId;
	}

    public ClusterE getCluster() {
        return cluster;
    }

    public void setCluster(ClusterE cluster) {
        this.cluster = cluster;
    }

}
