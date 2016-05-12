package com.sugon.cloudview.cloudmanager.monitor.serviceImpl.entity;

import java.util.List;

import com.sugon.cloudview.cloudmanager.monitor.service.bo.AlarmEntity;

public class ClusterUI {

    private String id;
    private String name;

    // CPU整体视图
    private String cpuTotalGHz;
    private String cpuUsedGHz;
    private String cpuFreeGHz;
    private String cpuUsage;
    // 内存整体视图
    private String memTotalGB;
    private String memUsedGB;
    private String memFreeGB;
    private String memUsage;
    // 存储整体视图
    private String storeTotalTB;
    private String storeUsedTB;
    private String storeFreeTB;
    private String storeUsage;

    // 群集内部主机 虚拟机 存储的总量，正常数量，异常数量
    private String hostNum;
    private String hostAccessibleNum;
    private String hostUnaccessibleNum;

    private String vmNum;
    private String vmAccessibleNum;
    private String vmUnaccessibleNum;

    private String storeNum;
    private String storeAccessibleNum;
    private String storeUnaccessibleNum;

    // TOP3主机视图里左侧的平均值
    private String avgHostCpuUsage;
    private String avgHostMemUsage;
    private String avgHostDiskIO;
    private String avgHostNetIO;

    // TOP3主机视图右侧的数据列表
    private List<Host> top3HostCpuUsage;
    private List<Host> top3HostMemUsage;
    private List<Host> top3HostDiskIO;
    private List<Host> top3HostNetIO;

    // TOP3虚拟机视图里左侧的平均值
    private String avgVMCpuUsage;
    private String avgVMMemUsage;
    private String avgVMDiskIO;
    private String avgVMNetIO;

    // TOP3虚拟机视图右侧的数据列表
    private List<VM> top3VMCpuUsage;
    private List<VM> top3VMMemUsage;
    private List<VM> top3VMDiskIO;
    private List<VM> top3VMNetIO;

    // 告警事件列表
    private List<AlarmEntity> alarmList;
    
    //告警分类
    private int host_warning;
    private int host_ciritical;
    private  int vm_warning;
    private int vm_ciritical;
    private int store_warning;
    private int store_ciritical;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the cpuTotalGHz
     */
    public String getCpuTotalGHz() {
        return cpuTotalGHz;
    }

    /**
     * @param cpuTotalGHz
     *            the cpuTotalGHz to set
     */
    public void setCpuTotalGHz(String cpuTotalGHz) {
        this.cpuTotalGHz = cpuTotalGHz;
    }

    /**
     * @return the cpuUsedGHz
     */
    public String getCpuUsedGHz() {
        return cpuUsedGHz;
    }

    /**
     * @param cpuUsedGHz
     *            the cpuUsedGHz to set
     */
    public void setCpuUsedGHz(String cpuUsedGHz) {
        this.cpuUsedGHz = cpuUsedGHz;
    }

    /**
     * @return the cpuFreeGHz
     */
    public String getCpuFreeGHz() {
        return cpuFreeGHz;
    }

    /**
     * @param cpuFreeGHz
     *            the cpuFreeGHz to set
     */
    public void setCpuFreeGHz(String cpuFreeGHz) {
        this.cpuFreeGHz = cpuFreeGHz;
    }

    /**
     * @return the cpuUsage
     */
    public String getCpuUsage() {
        return cpuUsage;
    }

    /**
     * @param cpuUsage
     *            the cpuUsage to set
     */
    public void setCpuUsage(String cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    /**
     * @return the memTotalGB
     */
    public String getMemTotalGB() {
        return memTotalGB;
    }

    /**
     * @param memTotalGB
     *            the memTotalGB to set
     */
    public void setMemTotalGB(String memTotalGB) {
        this.memTotalGB = memTotalGB;
    }

    /**
     * @return the memUsedGB
     */
    public String getMemUsedGB() {
        return memUsedGB;
    }

    /**
     * @param memUsedGB
     *            the memUsedGB to set
     */
    public void setMemUsedGB(String memUsedGB) {
        this.memUsedGB = memUsedGB;
    }

    /**
     * @return the memFreeGB
     */
    public String getMemFreeGB() {
        return memFreeGB;
    }

    /**
     * @param memFreeGB
     *            the memFreeGB to set
     */
    public void setMemFreeGB(String memFreeGB) {
        this.memFreeGB = memFreeGB;
    }

    /**
     * @return the memUsage
     */
    public String getMemUsage() {
        return memUsage;
    }

    /**
     * @param memUsage
     *            the memUsage to set
     */
    public void setMemUsage(String memUsage) {
        this.memUsage = memUsage;
    }

    /**
     * @return the storeTotalTB
     */
    public String getStoreTotalTB() {
        return storeTotalTB;
    }

    /**
     * @param storeTotalTB
     *            the storeTotalTB to set
     */
    public void setStoreTotalTB(String storeTotalTB) {
        this.storeTotalTB = storeTotalTB;
    }

    /**
     * @return the storeUsedTB
     */
    public String getStoreUsedTB() {
        return storeUsedTB;
    }

    /**
     * @param storeUsedTB
     *            the storeUsedTB to set
     */
    public void setStoreUsedTB(String storeUsedTB) {
        this.storeUsedTB = storeUsedTB;
    }

    /**
     * @return the storeFreeTB
     */
    public String getStoreFreeTB() {
        return storeFreeTB;
    }

    /**
     * @param storeFreeTB
     *            the storeFreeTB to set
     */
    public void setStoreFreeTB(String storeFreeTB) {
        this.storeFreeTB = storeFreeTB;
    }

    /**
     * @return the storeUsage
     */
    public String getStoreUsage() {
        return storeUsage;
    }

    /**
     * @param storeUsage
     *            the storeUsage to set
     */
    public void setStoreUsage(String storeUsage) {
        this.storeUsage = storeUsage;
    }

    /**
     * @return the hostNum
     */
    public String getHostNum() {
        return hostNum;
    }

    /**
     * @param hostNum
     *            the hostNum to set
     */
    public void setHostNum(String hostNum) {
        this.hostNum = hostNum;
    }

    /**
     * @return the hostAccessibleNum
     */
    public String getHostAccessibleNum() {
        return hostAccessibleNum;
    }

    /**
     * @param hostAccessibleNum
     *            the hostAccessibleNum to set
     */
    public void setHostAccessibleNum(String hostAccessibleNum) {
        this.hostAccessibleNum = hostAccessibleNum;
    }

    /**
     * @return the hostUnaccessibleNum
     */
    public String getHostUnaccessibleNum() {
        return hostUnaccessibleNum;
    }

    /**
     * @param hostUnaccessibleNum
     *            the hostUnaccessibleNum to set
     */
    public void setHostUnaccessibleNum(String hostUnaccessibleNum) {
        this.hostUnaccessibleNum = hostUnaccessibleNum;
    }

    /**
     * @return the vmNum
     */
    public String getVmNum() {
        return vmNum;
    }

    /**
     * @param vmNum
     *            the vmNum to set
     */
    public void setVmNum(String vmNum) {
        this.vmNum = vmNum;
    }

    /**
     * @return the vmAccessibleNum
     */
    public String getVmAccessibleNum() {
        return vmAccessibleNum;
    }

    /**
     * @param vmAccessibleNum
     *            the vmAccessibleNum to set
     */
    public void setVmAccessibleNum(String vmAccessibleNum) {
        this.vmAccessibleNum = vmAccessibleNum;
    }

    /**
     * @return the vmUnaccessibleNum
     */
    public String getVmUnaccessibleNum() {
        return vmUnaccessibleNum;
    }

    /**
     * @param vmUnaccessibleNum
     *            the vmUnaccessibleNum to set
     */
    public void setVmUnaccessibleNum(String vmUnaccessibleNum) {
        this.vmUnaccessibleNum = vmUnaccessibleNum;
    }

    /**
     * @return the storeNum
     */
    public String getStoreNum() {
        return storeNum;
    }

    /**
     * @param storeNum
     *            the storeNum to set
     */
    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }

    /**
     * @return the storeAccessibleNum
     */
    public String getStoreAccessibleNum() {
        return storeAccessibleNum;
    }

    /**
     * @param storeAccessibleNum
     *            the storeAccessibleNum to set
     */
    public void setStoreAccessibleNum(String storeAccessibleNum) {
        this.storeAccessibleNum = storeAccessibleNum;
    }

    /**
     * @return the storeUnaccessibleNum
     */
    public String getStoreUnaccessibleNum() {
        return storeUnaccessibleNum;
    }

    /**
     * @param storeUnaccessibleNum
     *            the storeUnaccessibleNum to set
     */
    public void setStoreUnaccessibleNum(String storeUnaccessibleNum) {
        this.storeUnaccessibleNum = storeUnaccessibleNum;
    }

    /**
     * @return the avgHostCpuUsage
     */
    public String getAvgHostCpuUsage() {
        return avgHostCpuUsage;
    }

    /**
     * @param avgHostCpuUsage
     *            the avgHostCpuUsage to set
     */
    public void setAvgHostCpuUsage(String avgHostCpuUsage) {
        this.avgHostCpuUsage = avgHostCpuUsage;
    }

    /**
     * @return the avgHostMemUsage
     */
    public String getAvgHostMemUsage() {
        return avgHostMemUsage;
    }

    /**
     * @param avgHostMemUsage
     *            the avgHostMemUsage to set
     */
    public void setAvgHostMemUsage(String avgHostMemUsage) {
        this.avgHostMemUsage = avgHostMemUsage;
    }

    /**
     * @return the avgHostDiskIO
     */
    public String getAvgHostDiskIO() {
        return avgHostDiskIO;
    }

    /**
     * @param avgHostDiskIO
     *            the avgHostDiskIO to set
     */
    public void setAvgHostDiskIO(String avgHostDiskIO) {
        this.avgHostDiskIO = avgHostDiskIO;
    }

    /**
     * @return the avgHostNetIO
     */
    public String getAvgHostNetIO() {
        return avgHostNetIO;
    }

    /**
     * @param avgHostNetIO
     *            the avgHostNetIO to set
     */
    public void setAvgHostNetIO(String avgHostNetIO) {
        this.avgHostNetIO = avgHostNetIO;
    }

    /**
     * @return the top3HostCpuUsage
     */
    public List<Host> getTop3HostCpuUsage() {
        return top3HostCpuUsage;
    }

    /**
     * @param top3HostCpuUsage
     *            the top3HostCpuUsage to set
     */
    public void setTop3HostCpuUsage(List<Host> top3HostCpuUsage) {
        this.top3HostCpuUsage = top3HostCpuUsage;
    }

    /**
     * @return the top3HostMemUsage
     */
    public List<Host> getTop3HostMemUsage() {
        return top3HostMemUsage;
    }

    /**
     * @param top3HostMemUsage
     *            the top3HostMemUsage to set
     */
    public void setTop3HostMemUsage(List<Host> top3HostMemUsage) {
        this.top3HostMemUsage = top3HostMemUsage;
    }

    /**
     * @return the top3HostDiskIO
     */
    public List<Host> getTop3HostDiskIO() {
        return top3HostDiskIO;
    }

    /**
     * @param top3HostDiskIO
     *            the top3HostDiskIO to set
     */
    public void setTop3HostDiskIO(List<Host> top3HostDiskIO) {
        this.top3HostDiskIO = top3HostDiskIO;
    }

    /**
     * @return the top3HostNetIO
     */
    public List<Host> getTop3HostNetIO() {
        return top3HostNetIO;
    }

    /**
     * @param top3HostNetIO
     *            the top3HostNetIO to set
     */
    public void setTop3HostNetIO(List<Host> top3HostNetIO) {
        this.top3HostNetIO = top3HostNetIO;
    }

    /**
     * @return the avgVMCpuUsage
     */
    public String getAvgVMCpuUsage() {
        return avgVMCpuUsage;
    }

    /**
     * @param avgVMCpuUsage
     *            the avgVMCpuUsage to set
     */
    public void setAvgVMCpuUsage(String avgVMCpuUsage) {
        this.avgVMCpuUsage = avgVMCpuUsage;
    }

    /**
     * @return the avgVMMemUsage
     */
    public String getAvgVMMemUsage() {
        return avgVMMemUsage;
    }

    /**
     * @param avgVMMemUsage
     *            the avgVMMemUsage to set
     */
    public void setAvgVMMemUsage(String avgVMMemUsage) {
        this.avgVMMemUsage = avgVMMemUsage;
    }

    /**
     * @return the avgVMDiskIO
     */
    public String getAvgVMDiskIO() {
        return avgVMDiskIO;
    }

    /**
     * @param avgVMDiskIO
     *            the avgVMDiskIO to set
     */
    public void setAvgVMDiskIO(String avgVMDiskIO) {
        this.avgVMDiskIO = avgVMDiskIO;
    }

    /**
     * @return the avgVMNetIO
     */
    public String getAvgVMNetIO() {
        return avgVMNetIO;
    }

    /**
     * @param avgVMNetIO
     *            the avgVMNetIO to set
     */
    public void setAvgVMNetIO(String avgVMNetIO) {
        this.avgVMNetIO = avgVMNetIO;
    }

    /**
     * @return the top3VMCpuUsage
     */
    public List<VM> getTop3VMCpuUsage() {
        return top3VMCpuUsage;
    }

    /**
     * @param top3vmCpuUsage
     *            the top3VMCpuUsage to set
     */
    public void setTop3VMCpuUsage(List<VM> top3vmCpuUsage) {
        top3VMCpuUsage = top3vmCpuUsage;
    }

    /**
     * @return the top3VMMemUsage
     */
    public List<VM> getTop3VMMemUsage() {
        return top3VMMemUsage;
    }

    /**
     * @param top3vmMemUsage
     *            the top3VMMemUsage to set
     */
    public void setTop3VMMemUsage(List<VM> top3vmMemUsage) {
        top3VMMemUsage = top3vmMemUsage;
    }

    /**
     * @return the top3VMDiskIO
     */
    public List<VM> getTop3VMDiskIO() {
        return top3VMDiskIO;
    }

    /**
     * @param top3vmDiskIO
     *            the top3VMDiskIO to set
     */
    public void setTop3VMDiskIO(List<VM> top3vmDiskIO) {
        top3VMDiskIO = top3vmDiskIO;
    }

    /**
     * @return the top3VMNetIO
     */
    public List<VM> getTop3VMNetIO() {
        return top3VMNetIO;
    }

    /**
     * @param top3vmNetIO
     *            the top3VMNetIO to set
     */
    public void setTop3VMNetIO(List<VM> top3vmNetIO) {
        top3VMNetIO = top3vmNetIO;
    }

    /**
     * @return the eventList
     */
    public List<AlarmEntity> getAlarmList() {
        return alarmList;
    }

    /**
     * @param eventList
     *            the eventList to set
     */
    public void setAlarmList(List<AlarmEntity> alarmList) {
        this.alarmList = alarmList;
    }

	/**
	 * @return the host_warning
	 */
	public int getHost_warning() {
		return host_warning;
	}

	/**
	 * @param host_warning the host_warning to set
	 */
	public void setHost_warning(int host_warning) {
		this.host_warning = host_warning;
	}

	/**
	 * @return the host_ciritical
	 */
	public int getHost_ciritical() {
		return host_ciritical;
	}

	/**
	 * @param host_ciritical the host_ciritical to set
	 */
	public void setHost_ciritical(int host_ciritical) {
		this.host_ciritical = host_ciritical;
	}

	/**
	 * @return the vm_warning
	 */
	public int getVm_warning() {
		return vm_warning;
	}

	/**
	 * @param vm_warning the vm_warning to set
	 */
	public void setVm_warning(int vm_warning) {
		this.vm_warning = vm_warning;
	}

	/**
	 * @return the vm_ciritical
	 */
	public int getVm_ciritical() {
		return vm_ciritical;
	}

	/**
	 * @param vm_ciritical the vm_ciritical to set
	 */
	public void setVm_ciritical(int vm_ciritical) {
		this.vm_ciritical = vm_ciritical;
	}

	/**
	 * @return the store_warning
	 */
	public int getStore_warning() {
		return store_warning;
	}

	/**
	 * @param store_warning the store_warning to set
	 */
	public void setStore_warning(int store_warning) {
		this.store_warning = store_warning;
	}

	/**
	 * @return the store_ciritical
	 */
	public int getStore_ciritical() {
		return store_ciritical;
	}

	/**
	 * @param store_ciritical the store_ciritical to set
	 */
	public void setStore_ciritical(int store_ciritical) {
		this.store_ciritical = store_ciritical;
	}
    
    

}
