package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.vdc;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "providevdc_storage_pool")
public class ProvideVDCStoragePoolE implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    /**
     * 主键
     */
    private String pVDCStoragePoolId;
    @Version
    private int version;
    /**
     * 存储总量(GB)
     */
    private Long spTotal;
    /**
     * 存储剩余
     */
    private Long spSurplus;

    /**
     * 存储已用
     */
    private Long spUsed;
    /**
     * 提供者vdcid
     */
    private String pVDCId;
    /**
     * 存储池id
     */
    private String spId;
    /**
     * 存储池名称
     */
    private String spName;

    // @ManyToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name = "pVDCId", unique = true)
    // private ProviderVDCE providerVDC;

    // @ManyToOne
    // @JoinColumn(name = "spId", unique = true)
    // private StoragePoolE storagePool;

    // @OneToMany(mappedBy = "provideVDCStoragePool", cascade = CascadeType.ALL)
    // private Set<OrgVDCStoragePoolE> orgVDCStoragePool;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Long getSpTotal() {
        return spTotal;
    }

    public void setSpTotal(Long spTotal) {
        this.spTotal = spTotal;
    }

    public Long getSpSurplus() {
        return spSurplus;
    }

    public void setSpSurplus(Long spSurplus) {
        this.spSurplus = spSurplus;
    }

    public Long getSpUsed() {
        return spUsed;
    }

    public void setSpUsed(Long spUsed) {
        this.spUsed = spUsed;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public String getpVDCStoragePoolId() {
        return pVDCStoragePoolId;
    }

    public void setpVDCStoragePoolId(String pVDCStoragePoolId) {
        this.pVDCStoragePoolId = pVDCStoragePoolId;
    }

    public String getpVDCId() {
        return pVDCId;
    }

    public void setpVDCId(String pVDCId) {
        this.pVDCId = pVDCId;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

}
