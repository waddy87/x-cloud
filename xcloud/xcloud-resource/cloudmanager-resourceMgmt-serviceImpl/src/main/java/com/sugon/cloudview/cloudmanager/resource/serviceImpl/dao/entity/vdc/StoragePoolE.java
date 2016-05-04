package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.vdc;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 存储池
 * 
 * @author sun
 *
 */
@Entity
@Table(name = "storage_pool")
public class StoragePoolE implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    // @GeneratedValue(generator = "uuid")
    // @GenericGenerator(name = "uuid", strategy = "uuid")
    /**
     * 主键
     */
    private String spId;
    /**
     * 连接id
     */
    @Column(length = 500)
    private String configId;
    /**
     * 存储池名称
     */
    private String name;
    /**
     * 存储总量(GB)
     */
    private long spTotal;
    /**
     * 存储剩余
     */
    private long spSurplus;
    /**
     * 存储已用
     */
    private long spUsed;

    /**
     * 同步时间
     */
    private Date synDate;

    /**
     * 是否可用
     */
    private Boolean isAvl;

    // @ManyToMany(cascade = CascadeType.REFRESH)
    // @JoinTable(name = "computingPool_storagePool", joinColumns =
    // @JoinColumn(name = "storagePool_id"), inverseJoinColumns =
    // @JoinColumn(name = "computingPool_id"))
    //
    @ManyToMany(mappedBy = "storagePool", cascade = CascadeType.ALL)
    private Set<ComputingPoolE> computingPool;

    // @OneToMany(mappedBy = "storagePool", cascade = CascadeType.ALL)
    // private Set<ProvideVDCStoragePoolE> pVDCStoragePools;

    public Date getSynDate() {
        return synDate;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
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

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String getName() {
        return name;
    }

    public long getSpTotal() {
        return spTotal;
    }

    public void setSpTotal(long spTotal) {
        this.spTotal = spTotal;
    }

    public long getSpSurplus() {
        return spSurplus;
    }

    public void setSpSurplus(long spSurplus) {
        this.spSurplus = spSurplus;
    }

    public long getSpUsed() {
        return spUsed;
    }

    public void setSpUsed(long spUsed) {
        this.spUsed = spUsed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ComputingPoolE> getComputingPool() {
        return computingPool;
    }

    public void setComputingPool(Set<ComputingPoolE> computingPool) {
        this.computingPool = computingPool;
    }

}
