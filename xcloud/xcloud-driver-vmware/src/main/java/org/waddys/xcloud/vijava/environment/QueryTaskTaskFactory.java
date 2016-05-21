package org.waddys.xcloud.vijava.environment;

import org.waddys.xcloud.vijava.base.Answer;
import org.waddys.xcloud.vijava.base.BaseTask;
import org.waddys.xcloud.vijava.base.BaseTaskFactory;
import org.waddys.xcloud.vijava.base.Cmd;
import org.waddys.xcloud.vijava.exception.VirtException;
import org.waddys.xcloud.vijava.vm.QueryTask.QueryTaskCmd;

public class QueryTaskTaskFactory implements BaseTaskFactory {

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Answer> BaseTask<T> createTask(Cmd<T> cmd) throws VirtException {
    	QueryTaskCmd queryTaskCmd = (QueryTaskCmd)(cmd);
        return (BaseTask<T>) new QueryTaskTask(queryTaskCmd);
    }
    
}
