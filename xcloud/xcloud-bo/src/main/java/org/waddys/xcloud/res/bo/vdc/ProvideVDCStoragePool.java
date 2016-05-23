package org.waddys.xcloud.res.bo.vdc;

import java.io.Serializable;

public class ProvideVDCStoragePool implements Serializable {

    private static final long serialVersionUID = 1L;
    private String pVDCStoragePoolId;

    private Long spTotal;
    private Long spSurplus;
    private Long spUsed;

    private String pVDCId;
    private String spId;
    private String spName;
    private Boolean isCreate;

    public Boolean getIsCreate() {
        return isCreate;
    }

    public void setIsCreate(Boolean isCreate) {
        this.isCreate = isCreate;
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
