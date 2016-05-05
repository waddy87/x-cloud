package com.sugon.cloudview.cloudmanager.managedvm.serviceImpl.dao.exception;

import com.sugon.cloudview.cloudmanager.common.base.exception.CloudviewRuntimeException;

public class OldVirtualMachineRuntimeException extends CloudviewRuntimeException {

	private static final long serialVersionUID = -7924351174099571262L;

	public OldVirtualMachineRuntimeException() {
		super();
	}

	public OldVirtualMachineRuntimeException(String message, Exception e) {
		super(message, e);
	}

	public OldVirtualMachineRuntimeException(String message, Throwable t) {
		super(message, t);
	}

	public OldVirtualMachineRuntimeException(String message) {
		super(message);
	}

	public OldVirtualMachineRuntimeException(Throwable t) {
		super(t);
	}
}
