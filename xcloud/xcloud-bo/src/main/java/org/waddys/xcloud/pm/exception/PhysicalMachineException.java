package org.waddys.xcloud.pm.exception;

import org.waddys.xcloud.common.base.exception.CloudviewException;

public class PhysicalMachineException extends CloudviewException {

    private static final long serialVersionUID = 1L;

    public PhysicalMachineException() {
        super();
    }

    public PhysicalMachineException(String message) {
        super(message);
    }

}
