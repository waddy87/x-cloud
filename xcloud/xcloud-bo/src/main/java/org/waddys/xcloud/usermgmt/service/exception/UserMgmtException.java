package org.waddys.xcloud.usermgmt.service.exception;

import org.waddys.xcloud.common.base.exception.CloudviewException;

public class UserMgmtException extends CloudviewException {

    private static final long serialVersionUID = 1L;

    public UserMgmtException() {
        super();
    }

    public UserMgmtException(String message) {
        super(message);
    }

}
