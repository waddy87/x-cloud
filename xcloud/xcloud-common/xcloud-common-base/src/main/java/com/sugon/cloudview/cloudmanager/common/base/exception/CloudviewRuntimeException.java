package com.sugon.cloudview.cloudmanager.common.base.exception;

public class CloudviewRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -8396102968243284068L;

    public CloudviewRuntimeException() {
        super();
    }

    public CloudviewRuntimeException(String message) {
        super(message);
    }

    public CloudviewRuntimeException(Throwable t) {
        super(t);
    }

    public CloudviewRuntimeException(String message, Exception e) {
        super(message, e);
    }

    public CloudviewRuntimeException(String message, Throwable t) {
        super(message, t);
    }
}
