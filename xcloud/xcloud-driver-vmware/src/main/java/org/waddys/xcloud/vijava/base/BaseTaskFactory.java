package org.waddys.xcloud.vijava.base;

import org.waddys.xcloud.vijava.exception.VirtException;

public interface BaseTaskFactory {

    public <T extends Answer> BaseTask<T> createTask(Cmd<T> cmd) throws VirtException;
}
