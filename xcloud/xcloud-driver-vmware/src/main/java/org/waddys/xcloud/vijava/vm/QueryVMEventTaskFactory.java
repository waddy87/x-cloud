package org.waddys.xcloud.vijava.vm;

import org.waddys.xcloud.vijava.base.Answer;
import org.waddys.xcloud.vijava.base.BaseTask;
import org.waddys.xcloud.vijava.base.BaseTaskFactory;
import org.waddys.xcloud.vijava.base.Cmd;
import org.waddys.xcloud.vijava.exception.VirtException;
import org.waddys.xcloud.vijava.vm.QueryVMEvent.QueryVMEventCmd;

public class QueryVMEventTaskFactory implements BaseTaskFactory {

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Answer> BaseTask<T> createTask(Cmd<T> cmd) throws VirtException {
    	QueryVMEventCmd queryVMEventCmd = (QueryVMEventCmd)(cmd);
        return (BaseTask<T>) new QueryVMEventTask(queryVMEventCmd);
    }
    
}
