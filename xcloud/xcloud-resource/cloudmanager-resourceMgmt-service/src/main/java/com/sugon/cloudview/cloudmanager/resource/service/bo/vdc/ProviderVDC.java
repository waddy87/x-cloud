package com.sugon.cloudview.cloudmanager.resource.service.bo.vdc;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 提供者vDC
 * 
 * @author sunht
 *
 */
public class ProviderVDC implements Serializable {

    private static final long serialVersionUID = 1L;
    private String pVDCId;
    private String computingPoolId;
    private String computingPoolName;
    private String name;
    private Integer vCpuNum;
    private Integer vCpuOverplus;
    private Integer vCpuUsed;
    private Long memorySize;
    private Long memoryOverplus;
    private Long memoryUsed;
    private String description;
    private Date createDate;
    private List<ProvideVDCStoragePool> provideVDCStoragePool;

    public Long getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(Long memorySize) {
        this.memorySize = memorySize;
    }

    public Long getMemoryOverplus() {
        return memoryOverplus;
    }

    public void setMemoryOverplus(Long memoryOverplus) {
        this.memoryOverplus = memoryOverplus;
    }

    public Long getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(Long memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    public Integer getvCpuUsed() {
        return vCpuUsed;
    }

    public void setvCpuUsed(Integer vCpuUsed) {
        this.vCpuUsed = vCpuUsed;
    }

    public List<ProvideVDCStoragePool> getProvideVDCStoragePool() {
        return provideVDCStoragePool;
    }

    public void setProvideVDCStoragePool(
            List<ProvideVDCStoragePool> provideVDCStoragePool) {
        this.provideVDCStoragePool = provideVDCStoragePool;
    }

    public String getComputingPoolName() {
        return computingPoolName;
    }

    public void setComputingPoolName(String computingPoolName) {
        this.computingPoolName = computingPoolName;
    }

    public String getpVDCId() {
        return pVDCId;
    }

    public void setpVDCId(String pVDCId) {
        this.pVDCId = pVDCId;
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

    public String getComputingPoolId() {
        return computingPoolId;
    }

    public void setComputingPoolId(String computingPoolId) {
        this.computingPoolId = computingPoolId;
    }

    public Integer getvCpuOverplus() {
        return vCpuOverplus;
    }

    public void setvCpuOverplus(Integer vCpuOverplus) {
        this.vCpuOverplus = vCpuOverplus;
    }

}
