package com.sugon.cloudview.cloudmanager.managedvm.service.exception;

import com.sugon.cloudview.cloudmanager.common.base.exception.CloudviewException;
import com.sugon.cloudview.cloudmanager.common.base.exception.CloudviewExceptionCode;

public class OldVirtualMachineException extends CloudviewException {

	private static final long serialVersionUID = 3793470295455856040L;

	public OldVirtualMachineException() {
		super();
	}

	public OldVirtualMachineException(CloudviewExceptionCode code, String... params) {
		super(code, params);
	}

	public OldVirtualMachineException(CloudviewExceptionCode code) {
		super(code);
	}

	public OldVirtualMachineException(String message, Exception e) {
		super(message, e);
	}

	public OldVirtualMachineException(String message) {
		super(message);
	}

	public OldVirtualMachineException(Throwable t) {
		super(t);
	}
}
