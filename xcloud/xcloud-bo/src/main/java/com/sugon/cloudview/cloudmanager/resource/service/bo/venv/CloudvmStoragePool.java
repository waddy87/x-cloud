package com.sugon.cloudview.cloudmanager.resource.service.bo.venv;
import java.util.Date;
import java.util.List;
/**
 * 与cloudvm对接的存储池
 * @author ghj
 */
public class CloudvmStoragePool {
	/**
	 * 主键
	 */
    private String storagePoolId;
	/**
     * 连接信息，底层cloudvm
     */
    private VenvConfig configInfo;
    /**
     * 存储池名称
     */
	private String name;
	/**
     * 存储总量(GB)
     */
    private Integer spTotalCapacity;
    /**
     * 存储可用量
     */
    private Integer spAvlCapacity;
    /**
     * 存储已用
     */
    private Integer spUsedCapacity;
    /**
     * 数据同步时间
     */
    private Date synDate;

    public Date getSynDate() {
        return synDate;
    }

    public void setSynDate(Date synDate) {
        this.synDate = synDate;
    }

    private List<ClusterStoragePool> cluster_StoragePool;

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


    public String getStoragePoolId() {
        return storagePoolId;
    }

    public void setStoragePoolId(String storagePoolId) {
        this.storagePoolId = storagePoolId;
    }

    public VenvConfig getConfigInfo() {
        return configInfo;
    }

    public void setConfigInfo(VenvConfig configInfo) {
        this.configInfo = configInfo;
    }

    public Integer getSpTotalCapacity() {
        return spTotalCapacity;
    }

    public void setSpTotalCapacity(Integer spTotalCapacity) {
        this.spTotalCapacity = spTotalCapacity;
    }

    public Integer getSpAvlCapacity() {
        return spAvlCapacity;
    }

    public void setSpAvlCapacity(Integer spAvlCapacity) {
        this.spAvlCapacity = spAvlCapacity;
    }

    public Integer getSpUsedCapacity() {
        return spUsedCapacity;
    }

    public void setSpUsedCapacity(Integer spUsedCapacity) {
        this.spUsedCapacity = spUsedCapacity;
    }

    public List<ClusterStoragePool> getCluster_StoragePool() {
        return cluster_StoragePool;
    }

    public void setCluster_StoragePool(List<ClusterStoragePool> cluster_StoragePool) {
        this.cluster_StoragePool = cluster_StoragePool;
    }

}
