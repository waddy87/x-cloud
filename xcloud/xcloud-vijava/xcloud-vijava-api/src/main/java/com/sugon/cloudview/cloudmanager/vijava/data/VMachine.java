package com.sugon.cloudview.cloudmanager.vijava.data;

import java.util.List;

public class VMachine {
    private String id;
    
    private String name;
    /**
     * "running" - Guest is running normally.
     * 
     * "shuttingdown" - Guest has a pending shutdown command.
     * 
     * "resetting" - Guest has a pending reset command.
     * 
     * "standby" - Guest has a pending standby command.
     * 
     * "notrunning" - Guest is not running.
     * 
     * "unknown" - Guest information is not available.
     */
    private String runningStatus;
    /**
     * "poweredOff"-The virtual machine is currently powered off.
     * 
     * "poweredOn"-The virtual machine is currently powered on.
     * 
     * "suspended"-The virtual machine is currently suspended.
     */
    private String powerStatus;
    private String osId;
    private String osName;
    private Integer cpuNum;
    private Long memSizeMB;
    private List<VMDiskInfo> diskInfos;
    private List<VMNetworkInfo> networkInfos;
    private String clusterId;
    private String clusterName;
    private Boolean isTemplate;
    
    private String taskId;
    private String vmTaskStatus;
    
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
    public String getRunningStatus() {
        return runningStatus;
    }
    public void setRunningStatus(String runningStatus) {
        this.runningStatus = runningStatus;
    }
    public String getPowerStatus() {
        return powerStatus;
    }
    public void setPowerStatus(String powerStatus) {
        this.powerStatus = powerStatus;
    }

    public String getOsId() {
        return osId;
    }

    public void setOsId(String osId) {
        this.osId = osId;
    }
    public String getOsName() {
        return osName;
    }
    public void setOsName(String osName) {
        this.osName = osName;
    }
    public Integer getCpuNum() {
        return cpuNum;
    }
    public void setCpuNum(Integer cpuNum) {
        this.cpuNum = cpuNum;
    }
    public Long getMemSizeMB() {
        return memSizeMB;
    }
    public void setMemSizeMB(Long memSizeMB) {
        this.memSizeMB = memSizeMB;
    }
    public List<VMDiskInfo> getDiskInfos() {
        return diskInfos;
    }
    public void setDiskInfos(List<VMDiskInfo> diskInfos) {
        this.diskInfos = diskInfos;
    }
    public List<VMNetworkInfo> getNetworkInfos() {
        return networkInfos;
    }
    public void setNetworkInfos(List<VMNetworkInfo> networkInfos) {
        this.networkInfos = networkInfos;
    }
	public String getVmTaskStatus() {
		return vmTaskStatus;
	}
	public void setVmTaskStatus(String vmTaskStatus) {
		this.vmTaskStatus = vmTaskStatus;
	}
	public String getClusterId() {
        return clusterId;
    }
    public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }
    public String getClusterName() {
        return clusterName;
    }
    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }
    public Boolean getIsTemplate() {
        return isTemplate;
    }
    public void setIsTemplate(Boolean isTemplate) {
        this.isTemplate = isTemplate;
    }
    
    @Override
	public String toString() {
		return "VMachine [id=" + id + ", name=" + name + ", runningStatus=" + runningStatus + ", powerStatus="
				+ powerStatus + ", osId=" + osId + ", osName=" + osName + ", cpuNum=" + cpuNum + ", memSizeMB="
				+ memSizeMB + ", diskInfos=" + diskInfos + ", networkInfos=" + networkInfos + ", clusterId=" + clusterId
				+ ", clusterName=" + clusterName + ", isTemplate=" + isTemplate + ", vmTaskStatus=" + vmTaskStatus
				+ "]";
	}
}
