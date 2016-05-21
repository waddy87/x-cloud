package org.waddys.xcloud.vijava.base;

import org.waddys.xcloud.vijava.exception.VirtException;

public interface CloudviewExecutor {

    public <T extends Answer> T execute(Cmd<T> cmd) throws VirtException;

}
