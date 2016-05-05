package com.sugon.cloudview.cloudmanager.vijava.environment;

import com.sugon.cloudview.cloudmanager.vijava.base.Answer;
import com.sugon.cloudview.cloudmanager.vijava.base.BaseTask;
import com.sugon.cloudview.cloudmanager.vijava.base.BaseTaskFactory;
import com.sugon.cloudview.cloudmanager.vijava.base.Cmd;
import com.sugon.cloudview.cloudmanager.vijava.exception.VirtException;
import com.sugon.cloudview.cloudmanager.vijava.vm.QueryTask.QueryTaskCmd;

public class QueryTaskTaskFactory implements BaseTaskFactory {

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Answer> BaseTask<T> createTask(Cmd<T> cmd) throws VirtException {
    	QueryTaskCmd queryTaskCmd = (QueryTaskCmd)(cmd);
        return (BaseTask<T>) new QueryTaskTask(queryTaskCmd);
    }
    
}
