package org.waddys.xcloud.resource.serviceImpl.dao.entity.venv;

import java.io.Serializable;
import java.util.Date;
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

import org.hibernate.annotations.GenericGenerator;

/**
 * 与cloudvm对接的存储池
 * 
 * @author ghj
 */
@Entity(name = "CloudvmStoragePool")
public class CloudvmStoragePoolE implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "storagePoolId")
	/**
	 * 主键
	 */
    private String storagePoolId;
	/**
     * 连接信息，底层cloudvm
     */
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "configId")
    private VenvConfigE configInfo;
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


    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinTable(name = "cluster_cloudvmstoragepool", joinColumns = @JoinColumn(name = "storagePoolId") , inverseJoinColumns = @JoinColumn(name = "clusterId") )

    private Set<ClusterE> clusterE;

    public Set<ClusterE> getClusterE() {
        return clusterE;
    }

    public void setClusterE(Set<ClusterE> clusterE) {
        this.clusterE = clusterE;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public VenvConfigE getConfigInfo() {
        return configInfo;
    }

    public void setConfigInfo(VenvConfigE configInfo) {
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

    public String getStoragePoolId() {
        return storagePoolId;
    }

    public void setStoragePoolId(String storagePoolId) {
        this.storagePoolId = storagePoolId;
    }

    public Date getSynDate() {
        return synDate;
    }

    public void setSynDate(Date synDate) {
        this.synDate = synDate;
    }
}
