package com.sugon.cloudview.cloudmanager.vijava.vm;

import com.sugon.cloudview.cloudmanager.vijava.base.Answer;
import com.sugon.cloudview.cloudmanager.vijava.base.BaseTask;
import com.sugon.cloudview.cloudmanager.vijava.base.BaseTaskFactory;
import com.sugon.cloudview.cloudmanager.vijava.base.Cmd;
import com.sugon.cloudview.cloudmanager.vijava.exception.VirtException;
import com.sugon.cloudview.cloudmanager.vijava.vm.QueryVMEvent.QueryVMEventCmd;

public class QueryVMEventTaskFactory implements BaseTaskFactory {

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Answer> BaseTask<T> createTask(Cmd<T> cmd) throws VirtException {
    	QueryVMEventCmd queryVMEventCmd = (QueryVMEventCmd)(cmd);
        return (BaseTask<T>) new QueryVMEventTask(queryVMEventCmd);
    }
    
}
