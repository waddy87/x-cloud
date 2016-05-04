package com.sugon.cloudview.cloudmanager.taskMgmt.service.exception;

import com.sugon.cloudview.cloudmanager.common.base.exception.CloudviewException;

public class TaskinfoException extends CloudviewException {

    /**
     * 任务管理异常
     */
    private static final long serialVersionUID = 1L;

    public TaskinfoException() {
        super();
    }

    public TaskinfoException(String msg, Exception e) {
        super(msg, e);
    }

    public TaskinfoException(String msg) {
        super(msg);
    }

    public TaskinfoException(Throwable t) {
        super(t);
    }
}
