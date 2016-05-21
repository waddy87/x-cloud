package org.waddys.xcloud.resource.service.exception.venv;

import org.waddys.xcloud.common.base.exception.CloudviewException;

public class VenvException extends CloudviewException {
    /**
     * VEnv模块异常定义
     */
    private static final long serialVersionUID = 1L;

    public VenvException() {
        super();
    }

    public VenvException(String message) {
        super(message);
    }

}
