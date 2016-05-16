package com.sugon.cloudview.cloudmanager.vijava.data;

public class StoragePool {
	
	private String id;
	private String name;
	private long total;//GB
	private long used;//GB
	private long available;//GB

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
	 * total of storage ,the pieces is GB
	 * @return long
	 */
	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	/**
	 * used of storage ,the pieces is GB
	 * @return long
	 */
	public long getUsed() {
		return used;
	}

	public void setUsed(long used) {
		this.used = used;
	}

	/**
	 * available of storage ,the pieces is GB
	 * @return long
	 */
	public long getAvailable() {
		return available;
	}

	public void setAvailable(long available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return "StoragePool [id=" + id + ", name=" + name + ", total=" + total
				+ ", used=" + used + ", available=" + available + "]";
	}

	
}
