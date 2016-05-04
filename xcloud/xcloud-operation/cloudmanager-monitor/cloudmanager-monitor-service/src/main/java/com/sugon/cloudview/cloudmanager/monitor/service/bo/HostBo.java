/**
 * Created on 2016年3月28日
 */
package com.sugon.cloudview.cloudmanager.monitor.service.bo;

import java.util.List;

/**
 * 功能名: 请填写功能名 功能描述: 请简要描述功能的要点 Copyright: Copyright (c) 2016 公司: 曙光云计算技术有限公司
 *
 * @author wq
 * @version 2.0.0 sp1
 */
public class HostBo extends ComputeSystemBo {
    // 物理机IP地址
    private String ipAddr;

    // 物理机状态
    private String status;

    // 电源状态
    private String powerStatus;

    // private int vmNumber;

    // 虚拟机所属集群
    private String clusterName;

    // 虚拟机所属数据中心
    private String dataCenterName;

    // 虚拟机列表信息
    private List<VMBo> vmList;

    private int vmNum;
    private int vmAccessibleNum;
    private int vmUnaccessibleNum;
    private int storeNum;
    private int storeAccessibleNum;
    private int storeUnaccessibleNum;

    private double cpuGHzTotal;
    private double cpuGHzUsed;
    private double memGBTotal;
    private double memGBUsed;
    private double storeTBTotal;
    private double storeTBUsed;

    private String connectionStatus;

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPowerStatus() {
        return powerStatus;
    }

    public void setPowerStatus(String powerStatus) {
        this.powerStatus = powerStatus;
    }

    // public int getVmNumber() {
    // return vmNumber;
    // }
    //
    // public void setVmNumber(int vmNumber) {
    // this.vmNumber = vmNumber;
    // }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getDataCenterName() {
        return dataCenterName;
    }

    public void setDataCenterName(String dataCenterName) {
        this.dataCenterName = dataCenterName;
    }

    public List<VMBo> getVmList() {
        return vmList;
    }

    public void setVmList(List<VMBo> vmList) {
        this.vmList = vmList;
    }

    public int getVmNum() {
        return vmNum;
    }

    public void setVmNum(int vmNum) {
        this.vmNum = vmNum;
    }

    public int getVmAccessibleNum() {
        return vmAccessibleNum;
    }

    public void setVmAccessibleNum(int vmAccessibleNum) {
        this.vmAccessibleNum = vmAccessibleNum;
    }

    public int getVmUnaccessibleNum() {
        return vmUnaccessibleNum;
    }

    public void setVmUnaccessibleNum(int vmUnaccessibleNum) {
        this.vmUnaccessibleNum = vmUnaccessibleNum;
    }

    public int getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(int storeNum) {
        this.storeNum = storeNum;
    }

    public int getStoreAccessibleNum() {
        return storeAccessibleNum;
    }

    public void setStoreAccessibleNum(int storeAccessibleNum) {
        this.storeAccessibleNum = storeAccessibleNum;
    }

    public int getStoreUnaccessibleNum() {
        return storeUnaccessibleNum;
    }

    public void setStoreUnaccessibleNum(int storeUnaccessibleNum) {
        this.storeUnaccessibleNum = storeUnaccessibleNum;
    }

    public double getCpuGHzTotal() {
        return cpuGHzTotal;
    }

    public void setCpuGHzTotal(double cpuGHzTotal) {
        this.cpuGHzTotal = cpuGHzTotal;
    }

    public double getCpuGHzUsed() {
        return cpuGHzUsed;
    }

    public void setCpuGHzUsed(double cpuGHzUsed) {
        this.cpuGHzUsed = cpuGHzUsed;
    }

    public double getMemGBTotal() {
        return memGBTotal;
    }

    public void setMemGBTotal(double memGBTotal) {
        this.memGBTotal = memGBTotal;
    }

    public double getMemGBUsed() {
        return memGBUsed;
    }

    public void setMemGBUsed(double memGBUsed) {
        this.memGBUsed = memGBUsed;
    }

    public double getStoreTBTotal() {
        return storeTBTotal;
    }

    public void setStoreTBTotal(double storeTBTotal) {
        this.storeTBTotal = storeTBTotal;
    }

    public double getStoreTBUsed() {
        return storeTBUsed;
    }

    public void setStoreTBUsed(double storeTBUsed) {
        this.storeTBUsed = storeTBUsed;
    }

    public String getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(String connectionStatus) {
        this.connectionStatus = connectionStatus;
    }
}
