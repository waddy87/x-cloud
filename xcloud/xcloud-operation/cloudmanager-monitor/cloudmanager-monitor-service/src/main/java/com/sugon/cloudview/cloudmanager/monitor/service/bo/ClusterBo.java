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
public class ClusterBo extends ComputeSystemBo {
    private List<AlarmEntity> alarmList;
    private List<HostBo> hostList;
    private List<VMBo> vmList;

    private int hostNumber;
    private int hostAccessibleNum;
    private int hostUnaccessibleNum;
    private int vmNum;
    private int vmAccessibleNum;
    private int vmUnaccessibleNum;
    private int storeNum;
    private int storeAccessibleNum;
    private int storeUnaccessibleNum;

    private String avgCpuUsage;
    private String avgMemUsage;
    private String avgDiskWriteSpeed;
    private String avgDiskReadSpeed;
    private String avgNetworkSend;
    private String avgNetwordReceive;

    private double cpuGHz;
    private double cpuUsedGHz;
    private double memGB;
    private double memUsedGB;
    private double storeTB;
    private double storeUsedTB;

    private String dataCenterName;
    private String status;
    private String powerStatus;

    public String getDataCenterName() {
        return dataCenterName;
    }

    public void setDataCenterName(String dataCenterName) {
        this.dataCenterName = dataCenterName;
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

    public List<AlarmEntity> getAlarmList() {
        return alarmList;
    }

    public void setAlarmList(List<AlarmEntity> alarmList) {
        this.alarmList = alarmList;
    }

    public List<HostBo> getHostList() {
        return hostList;
    }

    public void setHostList(List<HostBo> hostList) {
        this.hostList = hostList;
    }

    public List<VMBo> getVmList() {
        return vmList;
    }

    public void setVmList(List<VMBo> vmList) {
        this.vmList = vmList;
    }


    public int getHostNumber() {
        return hostNumber;
    }

    public void setHostNumber(int hostNumber) {
        this.hostNumber = hostNumber;
    }

    public int getHostAccessibleNum() {
        return hostAccessibleNum;
    }

    public void setHostAccessibleNum(int hostAccessibleNum) {
        this.hostAccessibleNum = hostAccessibleNum;
    }

    public int getHostUnaccessibleNum() {
        return hostUnaccessibleNum;
    }

    public void setHostUnaccessibleNum(int hostUnaccessibleNum) {
        this.hostUnaccessibleNum = hostUnaccessibleNum;
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

    public String getAvgCpuUsage() {
        return avgCpuUsage;
    }

    public void setAvgCpuUsage(String avgCpuUsage) {
        this.avgCpuUsage = avgCpuUsage;
    }

    public String getAvgMemUsage() {
        return avgMemUsage;
    }

    public void setAvgMemUsage(String avgMemUsage) {
        this.avgMemUsage = avgMemUsage;
    }

    public String getAvgDiskWriteSpeed() {
        return avgDiskWriteSpeed;
    }

    public void setAvgDiskWriteSpeed(String avgDiskWriteSpeed) {
        this.avgDiskWriteSpeed = avgDiskWriteSpeed;
    }

    public String getAvgDiskReadSpeed() {
        return avgDiskReadSpeed;
    }

    public void setAvgDiskReadSpeed(String avgDiskReadSpeed) {
        this.avgDiskReadSpeed = avgDiskReadSpeed;
    }

    public String getAvgNetworkSend() {
        return avgNetworkSend;
    }

    public void setAvgNetworkSend(String avgNetworkSend) {
        this.avgNetworkSend = avgNetworkSend;
    }

    public String getAvgNetwordReceive() {
        return avgNetwordReceive;
    }

    public void setAvgNetwordReceive(String avgNetwordReceive) {
        this.avgNetwordReceive = avgNetwordReceive;
    }

    public double getCpuGHz() {
        return cpuGHz;
    }

    public void setCpuGHz(double cpuGHz) {
        this.cpuGHz = cpuGHz;
    }

    public double getCpuUsedGHz() {
        return cpuUsedGHz;
    }

    public void setCpuUsedGHz(double cpuUsedGHz) {
        this.cpuUsedGHz = cpuUsedGHz;
    }

    public double getMemGB() {
        return memGB;
    }

    public void setMemGB(double memGB) {
        this.memGB = memGB;
    }

    public double getMemUsedGB() {
        return memUsedGB;
    }

    public void setMemUsedGB(double memUsedGB) {
        this.memUsedGB = memUsedGB;
    }

    public double getStoreTB() {
        return storeTB;
    }

    public void setStoreTB(double storeTB) {
        this.storeTB = storeTB;
    }

    public double getStoreUsedTB() {
        return storeUsedTB;
    }

    public void setStoreUsedTB(double storeUsedTB) {
        this.storeUsedTB = storeUsedTB;
    }
}
