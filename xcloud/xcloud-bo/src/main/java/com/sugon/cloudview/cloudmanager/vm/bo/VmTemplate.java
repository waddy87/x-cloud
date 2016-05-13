/**
 * Created on 2016年4月26日
 */
package com.sugon.cloudview.cloudmanager.vm.bo;

import java.util.Date;



/**
 * 
 * @author zhangdapeng
 *
 */
public class VmTemplate {
	
	/**
	 * 模板主键
	 */
	private Integer id;
	
	/**
	 * 虚拟化环境中模板主键ID
	 */
	private String relationId;
	
	/**
	 * @return the relationId
	 */
	public String getRelationId() {
		return relationId;
	}

	/**
	 * @param relationId the relationId to set
	 */
	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	/**
	 * 模板名称
	 */
	private String name;
	
	/**
	 * 描述
	 */
	private String des;
	
	/**
	 * 状态（0.未发布、1.已发布）
	 */
	private String status;
	
	/**
	 * 可见范围（0.系统可见，1.私有可见）
	 */
	private String visible;
	
	/**
	 * 模板CPU
	 */
	private Integer cpu;
	
	/**
	 * 模板内存
	 */
	private Long memory;
	
	/**
	 * 模板操作系统
	 */
	private String os;
	
	/**
	 * 模板操作系统版本
	 */
	private String version;
	
	/**
	 * 模板拥有者
	 */
	private String owner;
	
	/**
	 * 模板创建时间
	 */
	private Date createTime;
	
	/**
	 * 模板创建时间
	 */
	private Date updateTime;
	
	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 模板系统盘大小
	 */
	private Long diskSize;


	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the des
	 */
	public String getDes() {
		return des;
	}

	/**
	 * @param des the des to set
	 */
	public void setDes(String des) {
		this.des = des;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the visible
	 */
	public String getVisible() {
		return visible;
	}

	/**
	 * @param visible the visible to set
	 */
	public void setVisible(String visible) {
		this.visible = visible;
	}

	/**
	 * @return the os
	 */
	public String getOs() {
		return os;
	}

	/**
	 * @return the cpu
	 */
	public Integer getCpu() {
		return cpu;
	}

	/**
	 * @param cpu
	 *            the cpu to set
	 */
	public void setCpu(Integer cpu) {
		this.cpu = cpu;
	}

	/**
	 * @return the memory
	 */
	public Long getMemory() {
		return memory;
	}

	/**
	 * @param memory
	 *            the memory to set
	 */
	public void setMemory(Long memory) {
		this.memory = memory;
	}

	/**
	 * @return the diskSize
	 */
	public Long getDiskSize() {
		return diskSize;
	}

	/**
	 * @param diskSize
	 *            the diskSize to set
	 */
	public void setDiskSize(Long diskSize) {
		this.diskSize = diskSize;
	}

	/**
	 * @param os
	 *            the os to set
	 */
	public void setOs(String os) {
		this.os = os;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}


	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
