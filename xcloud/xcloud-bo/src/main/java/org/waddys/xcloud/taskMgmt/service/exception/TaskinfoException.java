package org.waddys.xcloud.taskMgmt.service.exception;

import org.waddys.xcloud.common.base.exception.CloudviewException;

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
