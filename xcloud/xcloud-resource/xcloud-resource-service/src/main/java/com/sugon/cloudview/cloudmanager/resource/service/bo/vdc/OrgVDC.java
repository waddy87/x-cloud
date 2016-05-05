package com.sugon.cloudview.cloudmanager.resource.service.bo.vdc;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 组织vDC
 * 
 * @author sunht
 *
 */
public class OrgVDC implements Serializable {

    private static final long serialVersionUID = 1L;
    private String orgVDCId;
    private String pVDCId;
    private String name;
    private Integer vCpuNum;
    private Integer vCpuOverplus;
    private Integer memorySize;
    private Integer memoryOverplus;
    private String description;
    private Date createDate;
    private Boolean isLimit;
    private String orgId;
    private String orgName;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Integer getvCpuOverplus() {
        return vCpuOverplus;
    }

    public void setvCpuOverplus(Integer vCpuOverplus) {
        this.vCpuOverplus = vCpuOverplus;
    }

    public Integer getMemoryOverplus() {
        return memoryOverplus;
    }

    public void setMemoryOverplus(Integer memoryOverplus) {
        this.memoryOverplus = memoryOverplus;
    }

    private List<OrgVDCStoragePool> orgVDCStoragePool;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public List<OrgVDCStoragePool> getOrgVDCStoragePool() {
        return orgVDCStoragePool;
    }

    public void setOrgVDCStoragePool(List<OrgVDCStoragePool> orgVDCStoragePool) {
        this.orgVDCStoragePool = orgVDCStoragePool;
    }

    public String getOrgVDCId() {
        return orgVDCId;
    }

    public void setOrgVDCId(String orgVDCId) {
        this.orgVDCId = orgVDCId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getvCpuNum() {
        return vCpuNum;
    }

    public void setvCpuNum(Integer vCpuNum) {
        this.vCpuNum = vCpuNum;
    }

    public Integer getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(Integer memorySize) {
        this.memorySize = memorySize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Boolean getIsLimit() {
        return isLimit;
    }

    public void setIsLimit(Boolean isLimit) {
        this.isLimit = isLimit;
    }

    public String getpVDCId() {
        return pVDCId;
    }

    public void setpVDCId(String pVDCId) {
        this.pVDCId = pVDCId;
    }

}
