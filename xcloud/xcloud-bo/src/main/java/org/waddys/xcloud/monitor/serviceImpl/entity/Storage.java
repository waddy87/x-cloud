/**
 * 
 */
package org.waddys.xcloud.monitor.serviceImpl.entity;

import java.util.List;

import org.waddys.xcloud.monitor.bo.AlarmEntity;

/**
 * @author wangqian
 *
 */
public class Storage extends ComputerSystem {

    private String storageType;

    private List<AlarmEntity> alarmList;
    private List<Host> hostList;
    private List<VM> vmList;

    private int hostNum;
    private int hostAccessibleNum;
    private int hostUnaccessibleNum;
    private int vmNum;
    private int vmAccessibleNum;
    private int vmUnaccessibleNum;

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

    public List<Host> getHostList() {
        return hostList;
    }

    public void setHostList(List<Host> hostList) {
        this.hostList = hostList;
    }

    public List<VM> getVmList() {
        return vmList;
    }

    public void setVmList(List<VM> vmList) {
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
