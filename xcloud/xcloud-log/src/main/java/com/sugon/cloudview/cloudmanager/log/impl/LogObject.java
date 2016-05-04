package com.sugon.cloudview.cloudmanager.log.impl;

import java.util.ArrayList;
import java.util.Date;

import com.sugon.cloudview.cloudmanager.taskMgmt.service.bo.TaskInfo;

public class LogObject {
	
	private String userId;
	private String ip;
	private String userName;
	private String resourceId;
	private String resourceName;
	private String resourceType;
	private String operationResult;
	private Long executeTime;
	private String detailMessage;
	private String resourceOwnerId;
	private String taskId;
	private Date taskCreateTime;
	private String taskInfoName;
	private ArrayList<TaskInfo> taskInfoList = new ArrayList<TaskInfo>();

	/**
	 * @return the taskInfoList
	 */
	public ArrayList<TaskInfo> getTaskInfoList() {
		return taskInfoList;
	}

	/**
	 * @param taskInfoList
	 *            the taskInfoList to set
	 */
	public void setTaskInfoList(ArrayList<TaskInfo> taskInfoList) {
		this.taskInfoList = taskInfoList;
	}

	/**
	 * @return the taskInfoName
	 */
	public String getTaskInfoName() {
		return taskInfoName;
	}

	/**
	 * @param taskInfoName
	 *            the taskInfoName to set
	 */
	public void setTaskInfoName(String taskInfoName) {
		this.taskInfoName = taskInfoName;
	}

	private String orgId;
	
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
	 * @return the taskCreateTime
	 */
	public Date getTaskCreateTime() {
		return taskCreateTime;
	}

	/**
	 * @param taskCreateTime
	 *            the taskCreateTime to set
	 */
	public void setTaskCreateTime(Date taskCreateTime) {
		this.taskCreateTime = taskCreateTime;
	}

	/**
	 * @return the taskId
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId
	 *            the taskId to set
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return the resourceOwnerId
	 */
	public String getResourceOwnerId() {
		return resourceOwnerId;
	}

	/**
	 * @param resourceOwnerId
	 *            the resourceOwnerId to set
	 */
	public void setResourceOwnerId(String resourceOwnerId) {
		this.resourceOwnerId = resourceOwnerId;
	}

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

	// 是否写入log，默认为true
	private boolean isWritten = true;

	// message对象参数
	private Object[] objects;

	/**
	 * 构造函数
	 */
	public LogObject() {
	}

	/**
	 * 构造函数
	 * 
	 * @param isWritten
	 * @param objects
	 */
	public LogObject(boolean isWritten) {
		super();
		this.isWritten = isWritten;
	}

	/**
	 * 构造函数
	 * 
	 * @param isWritten
	 * @param objects
	 */
	public LogObject(boolean isWritten, Object[] objects, String ip,
			String userName, String userId) {
		super();
		this.userId = userId;
		this.isWritten = isWritten;
		this.objects = objects;
		this.ip = ip;
		this.userName = userName;
	}

	public static LogObject newWrite() {
		return new LogObject(true);
	}

	public static LogObject newIgnore() {
		return new LogObject(false);
	}

	/**
	 * 返回 isWritten 的值
	 * 
	 * @return isWritten
	 */
	public boolean isWritten() {
		return isWritten;
	}

	/**
	 * 设置 isWritten 的值
	 * 
	 * @param isWritten
	 */
	public LogObject setWritten(boolean isWritten) {
		this.isWritten = isWritten;
		return this;
	}

	/**
	 * 返回 objects 的值
	 * 
	 * @return objects
	 */
	public Object[] getObjects() {
		return objects;
	}

	/**
	 * 设置 objects 的值
	 * 
	 * @param objects
	 */
	public LogObject setObjects(Object[] objects) {
		this.objects = objects;
		return this;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getOperationResult() {
		return operationResult;
	}

	public void setOperationResult(String operationResult) {
		this.operationResult = operationResult;
	}

	@Override
	public String toString() {

		StringBuffer sb = new StringBuffer();
		sb.append("LogObject对象： userId:").append(userId).append("IP:").append(ip).append("userName:").append(userName)
				.append("resourceId:").append(resourceId).append("resouceName:").append(resourceName).append("")
				.append(operationResult).append("resourceType").append(resourceType).append("executeTime")
				.append(executeTime).append("taskId").append(taskId).append("taskCreateTime").append(taskCreateTime)
				.append("orgId").append(orgId).append("taskInfoName").append(taskInfoName).append("resourceOwnerId")
				.append(resourceOwnerId);
		return sb.toString();
	}

}
