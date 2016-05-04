package com.sugon.cloudview.cloudmanager.resource.service.exception.venv;

import com.sugon.cloudview.cloudmanager.common.base.exception.CloudviewException;

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
