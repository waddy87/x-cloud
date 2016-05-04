package com.sugon.cloudview.cloudmanager.usermgmt.service.exception;

import com.sugon.cloudview.cloudmanager.common.base.exception.CloudviewException;

public class UserMgmtException extends CloudviewException {

    private static final long serialVersionUID = 1L;

    public UserMgmtException() {
        super();
    }

    public UserMgmtException(String message) {
        super(message);
    }

}
