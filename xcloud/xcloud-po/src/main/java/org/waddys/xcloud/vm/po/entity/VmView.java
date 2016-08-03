package org.waddys.xcloud.vm.po.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class VmView implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6019325515230006191L;

	@Id
	private String id;
	
	private String vm_name;
	
	private String t_name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVm_name() {
		return vm_name;
	}

	public void setVm_name(String vm_name) {
		this.vm_name = vm_name;
	}

	public String getT_name() {
		return t_name;
	}

	public void setT_name(String t_name) {
		this.t_name = t_name;
	}
	
	
}
