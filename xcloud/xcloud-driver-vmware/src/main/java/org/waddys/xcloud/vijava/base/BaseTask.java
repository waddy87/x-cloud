package org.waddys.xcloud.vijava.base;

import org.waddys.xcloud.vijava.exception.VirtException;

public abstract class BaseTask<T extends Answer> {

    public abstract T execute () throws VirtException;

}

