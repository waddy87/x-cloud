package com.sugon.cloudview.cloudmanager.event.type;

public enum VmOptorType4Event{

	CreateVM("CreateVM"), 
	/**
	 * 创建虚拟机：CloneVM
	 * 部署虚拟机：DeployVM
	 */
	CloneVM("CloneVM"), 
	
	/*
	 * 从磁盘删除: Destroy
	 * 从列表移除：UnregisterVM
	 */
	Destroy("Destroy"), 
	
	/**
	 * 开启电源
	 * PowerOnVM
	 * PowerOnMultiVM
	 */
	PowerOnVM("PowerOnVM"),
	
	/**
	 * 关闭电源
	 */
	PowerOffVM("PowerOffVM"),
	
	
	/**
	 * 迁移虚拟机
	 */
	MigrationVM("MigrationVM"),
	
	/**
	 * 未知
	 */
	NONE("NONE");
	
	public String value;
	VmOptorType4Event(String value){
		this.value = value;
	}
	
}

