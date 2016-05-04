package com.sugon.cloudview.cloudmanager.managedvm.service.bo;

/**
 * 利旧虚拟机业务实体
 * 
 * @author 赵春龙
 *
 */
public class OldVirtualMachine {

	private String id;
	private String vmId;
	private int isAssigned;
	private String assignData;// UI上显示的分配状态的信息
	private int isDeleted;
	private String orgId;
	private String orgName;


	private String ipAddr;// 虚拟机IP地址
	private String status;// 虚拟机状态
	private String hostId;// 虚拟机所属Host Id
	private String hostName;// 虚拟机所属Host名称
	private String clusterName;// 虚拟机所属集群
	private String dateCenterName;// 虚拟机所属数据中心
	private String powerStatus;// 虚拟机电源状态
	private String os;// 虚拟机操作系统
	private String name;// 名称
	private String cpuMHZTotal;// CPU频率
	private String cpuMHZUsed;// 已使用cpu MHZ数
	private String cpuNumber;// cpu数量
	private String cpuUsage;// cpu利用率
	private String memoryTotal;// 内存总量
	private String memoryUsage;// 内存利用率
	private String memoryUsed;// 内存使用量
	private String diskTotal;// 磁盘容量
	private String diskUsage;// 磁盘利用率
	private String diskUsed;// 磁盘使用量
	private String diskIops;// 磁盘iops
	private String diskReadSpeed;// 磁盘读速率
	private String diskWriteSpeed;// 磁盘写速率
	private String diskIOSpeed;// 磁盘IO速率，通常为读写之和
	private String networkSendSpeed;// 网络发送速率
	private String networkReceiveSpeed;// 网络接收速率
	private String networkTransmitSpeed;// 网络IO速率

	public String getId() {
		return id;
	}

	public String getVmId() {
		return vmId;
	}

	public int getIsAssigned() {
		return isAssigned;
	}

	public String getAssignData() {
		return assignData;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public String getOrgId() {
		return orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public String getStatus() {
		return status;
	}

	public String getHostId() {
		return hostId;
	}

	public String getHostName() {
		return hostName;
	}

	public String getClusterName() {
		return clusterName;
	}

	public String getDateCenterName() {
		return dateCenterName;
	}

	public String getPowerStatus() {
		return powerStatus;
	}

	public String getOs() {
		return os;
	}

	public String getName() {
		return name;
	}

	public String getCpuMHZTotal() {
		return cpuMHZTotal;
	}

	public String getCpuMHZUsed() {
		return cpuMHZUsed;
	}

	public String getCpuNumber() {
		return cpuNumber;
	}

	public String getCpuUsage() {
		return cpuUsage;
	}

	public String getMemoryTotal() {
		return memoryTotal;
	}

	public String getMemoryUsage() {
		return memoryUsage;
	}

	public String getMemoryUsed() {
		return memoryUsed;
	}

	public String getDiskTotal() {
		return diskTotal;
	}

	public String getDiskUsage() {
		return diskUsage;
	}

	public String getDiskUsed() {
		return diskUsed;
	}

	public String getDiskIops() {
		return diskIops;
	}

	public String getDiskReadSpeed() {
		return diskReadSpeed;
	}

	public String getDiskWriteSpeed() {
		return diskWriteSpeed;
	}

	public String getDiskIOSpeed() {
		return diskIOSpeed;
	}

	public String getNetworkSendSpeed() {
		return networkSendSpeed;
	}

	public String getNetworkReceiveSpeed() {
		return networkReceiveSpeed;
	}

	public String getNetworkTransmitSpeed() {
		return networkTransmitSpeed;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	public void setIsAssigned(int isAssigned) {
		this.isAssigned = isAssigned;
	}

	public void setAssignData(String assignData) {
		this.assignData = assignData;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public void setDateCenterName(String dateCenterName) {
		this.dateCenterName = dateCenterName;
	}

	public void setPowerStatus(String powerStatus) {
		this.powerStatus = powerStatus;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCpuMHZTotal(String cpuMHZTotal) {
		this.cpuMHZTotal = cpuMHZTotal;
	}

	public void setCpuMHZUsed(String cpuMHZUsed) {
		this.cpuMHZUsed = cpuMHZUsed;
	}

	public void setCpuNumber(String cpuNumber) {
		this.cpuNumber = cpuNumber;
	}

	public void setCpuUsage(String cpuUsage) {
		this.cpuUsage = cpuUsage;
	}

	public void setMemoryTotal(String memoryTotal) {
		this.memoryTotal = memoryTotal;
	}

	public void setMemoryUsage(String memoryUsage) {
		this.memoryUsage = memoryUsage;
	}

	public void setMemoryUsed(String memoryUsed) {
		this.memoryUsed = memoryUsed;
	}

	public void setDiskTotal(String diskTotal) {
		this.diskTotal = diskTotal;
	}

	public void setDiskUsage(String diskUsage) {
		this.diskUsage = diskUsage;
	}

	public void setDiskUsed(String diskUsed) {
		this.diskUsed = diskUsed;
	}

	public void setDiskIops(String diskIops) {
		this.diskIops = diskIops;
	}

	public void setDiskReadSpeed(String diskReadSpeed) {
		this.diskReadSpeed = diskReadSpeed;
	}

	public void setDiskWriteSpeed(String diskWriteSpeed) {
		this.diskWriteSpeed = diskWriteSpeed;
	}

	public void setDiskIOSpeed(String diskIOSpeed) {
		this.diskIOSpeed = diskIOSpeed;
	}

	public void setNetworkSendSpeed(String networkSendSpeed) {
		this.networkSendSpeed = networkSendSpeed;
	}

	public void setNetworkReceiveSpeed(String networkReceiveSpeed) {
		this.networkReceiveSpeed = networkReceiveSpeed;
	}

	public void setNetworkTransmitSpeed(String networkTransmitSpeed) {
		this.networkTransmitSpeed = networkTransmitSpeed;
	}
}
