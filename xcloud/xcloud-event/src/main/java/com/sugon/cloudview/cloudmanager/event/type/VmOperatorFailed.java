package com.sugon.cloudview.cloudmanager.event.type;

import java.io.Serializable;

public class VmOperatorFailed extends VmOperatorResult implements Serializable{
	
	private static final long serialVersionUID = -7889126358299280688L;
	
	public VmOperatorFailed(VmOperatorResult vmOperatorResult){
		super(vmOperatorResult);
	}
	
	@Override
	public String toString() {
		return "VmOperatorFailed [taskId=" + taskId + ", createdTime=" + createdTime + ", entityName=" + entityName
				+ ", result=" + result + ", operatorType=" + operatorType + ", vmId=" + vmId + ", desc=" + desc + "]";
	}

}
