package org.waddys.xcloud.res.bo.vdc;

import java.io.Serializable;

public class OrgVDCStoragePool implements Serializable {

    private static final long serialVersionUID = 1L;

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

    private String spId;

    private String spName;

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
