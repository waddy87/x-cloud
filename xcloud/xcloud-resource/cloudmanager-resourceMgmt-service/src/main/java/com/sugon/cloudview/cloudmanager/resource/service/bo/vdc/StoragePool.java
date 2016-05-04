package com.sugon.cloudview.cloudmanager.resource.service.bo.vdc;

import java.io.Serializable;
import java.util.Date;

/**
 * 存储池
 * 
 * @author sun
 *
 */

public class StoragePool implements Serializable {

    private static final long serialVersionUID = 1L;

    private String spId;

    private String configId;

    private String name;

    private long spTotal;

    private long spSurplus;

    private long spUsed;

    private Boolean isAvl;

    private Date synDate;

    public Date getSynDate() {
        return synDate;
    }

    public void setSynDate(Date synDate) {
        this.synDate = synDate;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Boolean getIsAvl() {
        return isAvl;
    }

    public void setIsAvl(Boolean isAvl) {
        this.isAvl = isAvl;
    }

    @Override
    public String toString() {
        return "StoragePool [spId=" + spId + ", configId=" + configId
                + ", name=" + name + ", spTotal=" + spTotal + ", spSurplus="
                + spSurplus + ", spUsed=" + spUsed + ", isAvl=" + isAvl
                + ", synDate=" + synDate + "]";
    }
}
