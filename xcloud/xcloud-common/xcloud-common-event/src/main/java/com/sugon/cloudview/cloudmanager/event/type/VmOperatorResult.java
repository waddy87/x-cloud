package com.sugon.cloudview.cloudmanager.event.type;

import java.io.Serializable;
import java.util.Date;

public class VmOperatorResult implements Serializable{
	
	protected static final long serialVersionUID = -7889111058299280688L;
	
	protected String taskId = null;
	
	protected Date createdTime = null;
	
	protected String entityName = null;
	
	protected String result = null;
	
	protected String operatorType = null;
	
	protected String vmId = null;
	
	protected String desc = null;

	public VmOperatorResult(){
		
	}
	
	public VmOperatorResult(VmOperatorResult vmOperatorResult){
		this.taskId = vmOperatorResult.taskId;
		this.createdTime = vmOperatorResult.createdTime;
		this.entityName = vmOperatorResult.entityName;
		this.result = vmOperatorResult.result;
		this.operatorType = vmOperatorResult.operatorType;
		this.vmId = vmOperatorResult.vmId;
		this.desc = vmOperatorResult.desc;
	}
	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Date getCreatedTimeofVM() {
		return createdTime;
	}

	public void setCreatedTimeofVM(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "VmOperatorResult [taskId=" + taskId + ", createdTime=" + createdTime + ", entityName=" + entityName
				+ ", result=" + result + ", operatorType=" + operatorType + ", vmId=" + vmId + ", desc=" + desc + "]";
	}

}
