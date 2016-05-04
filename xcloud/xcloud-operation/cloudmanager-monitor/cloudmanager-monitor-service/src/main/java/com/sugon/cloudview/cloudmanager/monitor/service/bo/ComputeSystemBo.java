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
public class ComputeSystemBo {
    // id
    private String id;

    // 名称
    private String name;

    // CPU频率
    private String cpuMHZTotal;

    // 已使用cpu MHZ数
    private String cpuMHZUsed;

    // cpu数量
    private String cpuNumber;

    // cpu利用率
    private String cpuUsage;

    // 内存总量
    private String memoryTotal;

    // 内存利用率
    private String memoryUsage;

    // 内存使用量
    private String memoryUsed;

    // 磁盘容量
    private String diskTotal;

    // 磁盘利用率
    private String diskUsage;

    // 磁盘使用量
    private String diskUsed;

    // 磁盘iops
    private String diskIops;

    // 磁盘读速率
    private String diskReadSpeed;

    // 磁盘写速率
    private String diskWriteSpeed;

    // 磁盘IO速率，通常为读写之和
    private String diskIOSpeed;

    // 网络发送速率
    private String networkSendSpeed;

    // 网络接收速率
    private String networkReceiveSpeed;

    // 网络IO速率
    private String networkTransmitSpeed;

    // 已触发的告警
    private List<AlarmEntity> triggeredAlarm;

    // 所有申明的告警
    private List<AlarmEntity> declaredAlarm;

    // 是否为独立主机
    private boolean bStandalone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpuMHZTotal() {
        return cpuMHZTotal;
    }

    public void setCpuMHZTotal(String cpuMHZTotal) {
        this.cpuMHZTotal = cpuMHZTotal;
    }

    public String getCpuMHZUsed() {
        return cpuMHZUsed;
    }

    public void setCpuMHZUsed(String cpuMHZUsed) {
        this.cpuMHZUsed = cpuMHZUsed;
    }

    public String getCpuNumber() {
        return cpuNumber;
    }

    public void setCpuNumber(String cpuNumber) {
        this.cpuNumber = cpuNumber;
    }

    public String getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(String cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public String getMemoryTotal() {
        return memoryTotal;
    }

    public void setMemoryTotal(String memoryTotal) {
        this.memoryTotal = memoryTotal;
    }

    public String getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(String memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public String getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(String memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    public String getDiskTotal() {
        return diskTotal;
    }

    public void setDiskTotal(String diskTotal) {
        this.diskTotal = diskTotal;
    }

    public String getDiskUsage() {
        return diskUsage;
    }

    public void setDiskUsage(String diskUsage) {
        this.diskUsage = diskUsage;
    }

    public String getDiskUsed() {
        return diskUsed;
    }

    public void setDiskUsed(String diskUsed) {
        this.diskUsed = diskUsed;
    }

    public String getDiskIops() {
        return diskIops;
    }

    public void setDiskIops(String diskIops) {
        this.diskIops = diskIops;
    }

    public String getDiskReadSpeed() {
        return diskReadSpeed;
    }

    public void setDiskReadSpeed(String diskReadSpeed) {
        this.diskReadSpeed = diskReadSpeed;
    }

    public String getDiskWriteSpeed() {
        return diskWriteSpeed;
    }

    public void setDiskWriteSpeed(String diskWriteSpeed) {
        this.diskWriteSpeed = diskWriteSpeed;
    }

    public String getDiskIOSpeed() {
        return diskIOSpeed;
    }

    public void setDiskIOSpeed(String diskIOSpeed) {
        this.diskIOSpeed = diskIOSpeed;
    }

    public String getNetworkSendSpeed() {
        return networkSendSpeed;
    }

    public void setNetworkSendSpeed(String networkSendSpeed) {
        this.networkSendSpeed = networkSendSpeed;
    }

    public String getNetworkReceiveSpeed() {
        return networkReceiveSpeed;
    }

    public void setNetworkReceiveSpeed(String networkReceiveSpeed) {
        this.networkReceiveSpeed = networkReceiveSpeed;
    }

    public String getNetworkTransmitSpeed() {
        return networkTransmitSpeed;
    }

    public void setNetworkTransmitSpeed(String networkTransmitSpeed) {
        this.networkTransmitSpeed = networkTransmitSpeed;
    }

    public List<AlarmEntity> getTriggeredAlarm() {
        return triggeredAlarm;
    }

    public void setTriggeredAlarm(List<AlarmEntity> triggeredAlarm) {
        this.triggeredAlarm = triggeredAlarm;
    }

    public List<AlarmEntity> getDeclaredAlarm() {
        return declaredAlarm;
    }

    public void setDeclaredAlarm(List<AlarmEntity> declaredAlarm) {
        this.declaredAlarm = declaredAlarm;
    }

    public boolean isbStandalone() {
        return bStandalone;
    }


    public boolean getBStandalone() {
        return bStandalone;
    }

    public void setBStandalone(boolean bStandalone) {
        this.bStandalone = bStandalone;
    }
}
