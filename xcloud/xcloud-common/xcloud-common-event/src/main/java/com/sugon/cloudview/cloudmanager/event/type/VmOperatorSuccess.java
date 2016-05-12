package com.sugon.cloudview.cloudmanager.event.type;

import java.io.Serializable;

public class VmOperatorSuccess extends VmOperatorResult implements Serializable{
	
	private static final long serialVersionUID = -7809136258299289588L;
	
	public VmOperatorSuccess(VmOperatorResult vmOperatorResult){
		super(vmOperatorResult);
	}
	
	@Override
	public String toString() {
		return "VmOperatorSuccess [taskId=" + taskId + ", createdTime=" + createdTime + ", entityName=" + entityName
				+ ", result=" + result + ", operatorType=" + operatorType + ", vmId=" + vmId + ", desc=" + desc + "]";
	}
}
