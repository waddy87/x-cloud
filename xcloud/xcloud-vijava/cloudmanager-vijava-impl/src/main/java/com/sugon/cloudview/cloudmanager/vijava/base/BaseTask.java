package com.sugon.cloudview.cloudmanager.vijava.base;

import com.sugon.cloudview.cloudmanager.vijava.exception.VirtException;

public abstract class BaseTask<T extends Answer> {

    public abstract T execute () throws VirtException;

}

