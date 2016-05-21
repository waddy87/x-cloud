package org.waddys.xcloud.resource.service.exception.vnet;

import org.waddys.xcloud.common.base.exception.CloudviewException;

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
