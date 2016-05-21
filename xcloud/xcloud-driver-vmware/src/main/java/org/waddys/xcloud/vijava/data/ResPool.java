package org.waddys.xcloud.vijava.data;

import java.util.List;

public class ResPool {
	
	private String id;
	private String name;
	private long cpuTotal;//MHZ
	private long cpuUsed;//MHZ
	private long cpuAvailable;//MHZ
	private long memoryTotal;//MB
	private long memoryUsed;//MB
	private long menoryAvailable;//MB
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

	/**
	 * the total of cpu ,the pieces is MHZ
	 * @return long
	 */ 
	public long getCpuTotal() {
		return cpuTotal;
	}

	
	public void setCpuTotal(long cpuTotal) {
		this.cpuTotal = cpuTotal;
	}
	
	/**
	 * used of cpu ,the pieces is MHZ
	 * @return long
	 */
	public long getCpuUsed() {
		return cpuUsed;
	}

	public void setCpuUsed(long cpuUsed) {
		this.cpuUsed = cpuUsed;
	}

	/**
	 * available of cpu ,the pieces is MHZ
	 * @return long
	 */
	public long getCpuAvailable() {
		return cpuAvailable;
	}

	public void setCpuAvailable(long cpuAvailable) {
		this.cpuAvailable = cpuAvailable;
	}

	/**
	 * the total of memory ,the pieces is MB
	 * @return long
	 */
	public long getMemoryTotal() {
		return memoryTotal;
	}

	public void setMemoryTotal(long memoryTotal) {
		this.memoryTotal = memoryTotal;
	}

	/**
	 * used of memory ,the pieces is MB
	 * @return long
	 */
	public long getMemoryUsed() {
		return memoryUsed;
	}

	public void setMemoryUsed(long memoryUsed) {
		this.memoryUsed = memoryUsed;
	}

	/**
	 * avalilable of memory ,the pieces is MB
	 * @return long
	 */
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

	@Override
	public String toString() {
		String result =  "ResPool [id=" + id + ", name=" + name + ", cpuTotal="
				+ cpuTotal + ", cpuUsed=" + cpuUsed + ", cpuAvailable="
				+ cpuAvailable + ", memoryTotal=" + memoryTotal
				+ ", memoryUsed=" + memoryUsed + ", menoryAvailable="
				+ menoryAvailable + ", storagePools={";
		if (storagePools != null)
			for (StoragePool storage : storagePools)
				result += storage + " ";
		result += "}]";
		return result;
	}
	
	

}
