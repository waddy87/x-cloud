/**
 * Created on 2016年3月28日
 */
package org.waddys.xcloud.monitor.bo;

import java.util.List;

/**
 * 功能名: 请填写功能名 功能描述: 请简要描述功能的要点 Copyright: Copyright (c) 2016 公司: 曙光云计算技术有限公司
 *
 * @author wq
 * @version 2.0.0 sp1
 */
public class StorageBo extends ComputeSystemBo {

    private String storageType;

    private List<AlarmEntity> alarmList;
    private List<HostBo> hostList;
    private List<VMBo> vmList;

    private int hostNum;
    private int hostAccessibleNum;
    private int hostUnaccessibleNum;
    private int vmNum;
    private int vmAccessibleNum;
    private int vmUnaccessibleNum;

    private double storeTBTotal;
    private double storeTBUsed;

    // 存储状态
    private String status;

    // 电源状态
    private String powerStatus;

    // 连接状态
    private String connectionStatus;

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
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

    public int getHostNum() {
        return hostNum;
    }

    public void setHostNum(int hostNum) {
        this.hostNum = hostNum;
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

    public String getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(String connectionStatus) {
        this.connectionStatus = connectionStatus;
    }
}