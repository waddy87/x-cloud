package org.waddys.xcloud.vijava.impl;

import com.vmware.vim25.DynamicData;


public class VolumeSpec  extends DynamicData{
	private String name;
	private String path;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
