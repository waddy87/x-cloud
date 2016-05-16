package com.sugon.cloudview.cloudmanager.vijava.base;

import java.io.Serializable;
import java.util.Date;

public class Answer implements Serializable {

    private static final long serialVersionUID = -3555679355705457292L;

    private boolean success;
    
    private String taskId;
    
    private String resourceId;
    
    private String taskName;
    
    private int taskProcess;
    
    private String taskStatus;
    
    private Date taskCreateTime;
    
    private String errMsg;
    
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public int getTaskProcess() {
		return taskProcess;
	}

	public void setTaskProcess(int taskProcess) {
		this.taskProcess = taskProcess;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public boolean isSuccess() {
		return success;
	}

	public Answer setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public Answer setErrMsg(String errMsg) {
		this.errMsg = errMsg;
		return this;
	}

	public String getTaskId() {
		return taskId;
	}

	public Answer setTaskId(String taskId) {
		this.taskId = taskId;
		return this;
	}

	public Date getTaskCreateTime() {
		return taskCreateTime;
	}

	public Answer setTaskCreateTime(Date taskCreateTime) {
		this.taskCreateTime = taskCreateTime;
		return this;
	}

	
    
    
    
}
