package com.sugon.cloudview.cloudmanager.resource.service.exception.vnet;

import com.sugon.cloudview.cloudmanager.common.base.exception.CloudviewException;

/**
 * 虚拟网络Exception异常类
 */
public class VNetException extends CloudviewException {

    private static final long serialVersionUID = -8524915536927312315L;

    public VNetException() {
        super();
    }

    public VNetException(String message, Exception e) {
        super(message, e);
    }

    public VNetException(String message) {
        super(message);
    }

    public VNetException(Throwable t) {
        super(t);
    }

}
