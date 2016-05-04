package com.sugon.cloudview.cloudmanager.managedvm.serviceImpl.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sugon.cloudview.dboperation.EntityBase;

@Entity
@Table(name = "managedvm_info")
public class OldVirtualMachineE extends EntityBase {

	private static final long serialVersionUID = 8115716273235834782L;

	private String id;
	private String vmId;
	// private int isMandated;
	private int isAssigned;
	private int isDeleted;
	private String orgId;
	private String name;

	@Id
	@GenericGenerator(name = "managedvm_info-uuid", strategy = "uuid")
	@GeneratedValue(generator = "managedvm_info-uuid")
	public String getId() {
		return id;
	}

	@Column(name = "vm_id", length = 32, nullable = false)
	public String getVmId() {
		return vmId;
	}
	//
	// @Column(name = "is_mandated", length = 1, nullable = false)
	// public int getIsMandated() {
	// return isMandated;
	// }

	@Column(name = "is_assigned", length = 1, nullable = false)
	public int getIsAssigned() {
		return isAssigned;
	}

	@Column(name = "is_deleted", length = 1, nullable = false)
	public int getIsDeleted() {
		return isDeleted;
	}

	@Column(name = "org_id", length = 32, nullable = true)
	public String getOrgId() {
		return orgId;
	}

	@Column(name = "name", length = 32, nullable = false)
	public String getName() {
		return name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	// public void setIsMandated(int isMandated) {
	// this.isMandated = isMandated;
	// }

	public void setIsAssigned(int isAssigned) {
		this.isAssigned = isAssigned;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public void setName(String name) {
		this.name = name;
	}
}
