package com.sugon.cloudview.cloudmanager.managedvm.serviceImpl.dao.entity;

public enum AssignData {

	ASSIGNED("已分配"), UNASSIGNED("未分配");

	private String value;

	private AssignData(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
