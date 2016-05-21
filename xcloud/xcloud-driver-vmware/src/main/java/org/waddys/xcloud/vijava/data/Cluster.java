package org.waddys.xcloud.vijava.data;

import java.util.List;

public class Cluster {
	
	private String id;
	private String name;
	private long cpuTotal;
	private long cpuUsed;
	private long cpuAvailable;
	private long memoryTotal;
	private long memoryUsed;
	private long menoryAvailable;
	private List<StoragePool> storagePools;

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

	public long getCpuTotal() {
		return cpuTotal;
	}

	public void setCpuTotal(long cpuTotal) {
		this.cpuTotal = cpuTotal;
	}

	public long getCpuUsed() {
		return cpuUsed;
	}

	public void setCpuUsed(long cpuUsed) {
		this.cpuUsed = cpuUsed;
	}

	public long getCpuAvailable() {
		return cpuAvailable;
	}

	public void setCpuAvailable(long cpuAvailable) {
		this.cpuAvailable = cpuAvailable;
	}

	public long getMemoryTotal() {
		return memoryTotal;
	}

	public void setMemoryTotal(long memoryTotal) {
		this.memoryTotal = memoryTotal;
	}

	public long getMemoryUsed() {
		return memoryUsed;
	}

	public void setMemoryUsed(long memoryUsed) {
		this.memoryUsed = memoryUsed;
	}

	public long getMenoryAvailable() {
		return menoryAvailable;
	}

	public void setMenoryAvailable(long menoryAvailable) {
		this.menoryAvailable = menoryAvailable;
	}

	public List<StoragePool> getStoragePools() {
		return storagePools;
	}

	public void setStoragePools(List<StoragePool> storagePools) {
		this.storagePools = storagePools;
	}

}
