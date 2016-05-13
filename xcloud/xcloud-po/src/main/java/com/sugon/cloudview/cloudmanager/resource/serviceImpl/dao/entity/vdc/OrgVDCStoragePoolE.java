package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.vdc;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "orgvdc_storage_pool")
public class OrgVDCStoragePoolE implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    /**
     * 主键
     */
    private String orgVDCStoragePoolId;

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
    private Integer spUsed;
    /**
     * 组织vdc
     */
    private String orgVDCId;

    /**
     * 提供者vdc-存储池关联关系表id
     */
    private String pVDCStoragePoolId;

    /**
     * 存储池id
     */
    private String spId;
    /**
     * 存储池名称
     */
    private String spName;

    // @ManyToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name = "orgVDCId", unique = true)
    // private OrgVDCE orgVDC;

    // @ManyToOne
    // @JoinColumn(name = "pVDCStoragePoolId", unique = true)
    // private ProvideVDCStoragePoolE provideVDCStoragePool;

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public Integer getSpUsed() {
        return spUsed;
    }

    public void setSpUsed(Integer spUsed) {
        this.spUsed = spUsed;
    }

    public String getOrgVDCStoragePoolId() {
        return orgVDCStoragePoolId;
    }

    public void setOrgVDCStoragePoolId(String orgVDCStoragePoolId) {
        this.orgVDCStoragePoolId = orgVDCStoragePoolId;
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

    public String getOrgVDCId() {
        return orgVDCId;
    }

    public void setOrgVDCId(String orgVDCId) {
        this.orgVDCId = orgVDCId;
    }

    public String getpVDCStoragePoolId() {
        return pVDCStoragePoolId;
    }

    public void setpVDCStoragePoolId(String pVDCStoragePoolId) {
        this.pVDCStoragePoolId = pVDCStoragePoolId;
    }

}
