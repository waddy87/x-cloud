package com.sugon.cloudview.cloudmanager.log.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "managerLogInfoEntity")
public class LogInfoEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "id")
	String id;

	/**
	 * @return the detailMessage
	 */
	public String getDetailMessage() {
		return detailMessage;
	}

	/**
	 * @param detailMessage
	 *            the detailMessage to set
	 */
	public void setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
	}

	/**
	 * 日志信息
	 */
	@Column(name = "message")
	String message;

	/**
	 * 日志详细信息
	 */
	@Column(name = "detailMessage")
	String detailMessage;

	/**
	 * 日志所属组织ID
	 */
	@Column(name = "orgId")
	String orgId;

	/**
	 * @return the orgId
	 */
	public String getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId
	 *            the orgId to set
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 业务类型
	 */
	@Column(name = "businessType")
	String businessType;

	/**
	 * 业务操作类型
	 */
	@Column(name = "operationType")
	String operationType;

    
	/**
	 * 模块类型
	 */
	@Column(name = "moduleType")
	String moduleType;

	/**
	 * 资源类型
	 */
	@Column(name = "resourceType")
	String resourceType;

	/**
	 * 用户名称
	 */
	@Column(name = "userName")
	String userName;
    
	/**
	 * @return the userRealName
	 */
	public String getUserRealName() {
		return userRealName;
	}

	/**
	 * @param userRealName
	 *            the userRealName to set
	 */
	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	/**
	 * 用户名称
	 */
	@Column(name = "userRealName")
	String userRealName;
    
	/**
	 * 日志记录等级
	 */
	@Column(name = "level")
	String level;


	/**
	 * 日志操作
	 */
    @Column(name = "operatingTime")
	Date operatingTime;
    
    
	/**
	 * 系统访问IP
	 */
    @Column(name = "ip")
	String ip;
    
	/**
	 * 资源名称
	 */
    @Column(name = "resourceName")
    String resourceName;
    
    
	/**
	 * 资源Id
	 */
    @Column(name = "resourceId")
    String resourceId;
    
    
	/**
	 * 操作结果
	 */
    @Column(name = "operationResult")
    String operationResult;

	/**
	 * 执行时长
	 */
	@Column(name = "executeTime")
	Long executeTime;

	/**
	 * @return the executeTime
	 */
	public Long getExecuteTime() {
		return executeTime;
	}

	/**
	 * @param executeTime
	 *            the executeTime to set
	 */
	public void setExecuteTime(Long executeTime) {
		this.executeTime = executeTime;
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getBusinessType() {
		return businessType;
	}


	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}


	public String getOperationType() {
		return operationType;
	}


	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}


	public String getModuleType() {
		return moduleType;
	}


	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}


	public String getResourceType() {
		return resourceType;
	}


	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getLevel() {
		return level;
	}


	public void setLevel(String level) {
		this.level = level;
	}


	public Date getOperatingTime() {
		return operatingTime;
	}


	public void setOperatingTime(Date operatingTime) {
		this.operatingTime = operatingTime;
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public String getResourceName() {
		return resourceName;
	}


	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}


	public String getResourceId() {
		return resourceId;
	}


	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}


	public String getOperationResult() {
		return operationResult;
	}


	public void setOperationResult(String operationResult) {
		this.operationResult = operationResult;
	}


	@Override
	public String toString() {
		return "LogInfoEntity [id=" + id + ", message=" + message + ", businessType=" + businessType
				+ ", operationType=" + operationType + ", moduleType=" + moduleType + ", resourceType=" + resourceType
				+ ", userName=" + userName + ", level=" + level + ", operatingTime=" + operatingTime + ", ip=" + ip
				+ ", resourceName=" + resourceName + ", resourceId=" + resourceId + ", operationResult="
				+ operationResult + "]";
	}
    
}
