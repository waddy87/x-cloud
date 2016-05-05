package com.sugon.cloudview.cloudmanager.vijava.base;

import com.sugon.cloudview.cloudmanager.vijava.exception.VirtException;

public interface BaseTaskFactory {

    public <T extends Answer> BaseTask<T> createTask(Cmd<T> cmd) throws VirtException;
}
