package com.sugon.cloudview.cloudmanager.vijava.base;

import com.sugon.cloudview.cloudmanager.vijava.exception.VirtException;

public interface CloudviewExecutor {

    public <T extends Answer> T execute(Cmd<T> cmd) throws VirtException;

}
