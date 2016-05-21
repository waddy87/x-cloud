package org.waddys.xcloud.vijava.vm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.waddys.xcloud.vijava.base.Answer;
import org.waddys.xcloud.vijava.base.BaseTask;
import org.waddys.xcloud.vijava.base.BaseTaskFactory;
import org.waddys.xcloud.vijava.base.Cmd;
import org.waddys.xcloud.vijava.exception.VirtException;
import org.waddys.xcloud.vijava.util.VmConvertUtils;
import org.waddys.xcloud.vijava.vm.QueryVM.QueryVMCmd;

public class QueryVMTaskFactory implements BaseTaskFactory {
    private static Logger logger = LoggerFactory.getLogger(QueryVMTaskFactory.class);

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Answer> BaseTask<T> createTask(Cmd<T> cmd) throws VirtException {
        logger.debug("参数cmd=" + cmd);
        if (cmd == null) {
            throw new VirtException("参数不能为空");
        }
        QueryVMCmd queryVmCmd = this.checkQueryVmCmd(cmd);
        return (BaseTask<T>) new QueryVMTask(queryVmCmd);
    }

    private <T extends Answer> QueryVMCmd checkQueryVmCmd(Cmd<T> cmd) throws VirtException {
        QueryVMCmd queryVmCmd = VmConvertUtils.convertWithException(cmd, QueryVMCmd.class);
        logger.debug("queryVmCmd is:" + queryVmCmd);
        return queryVmCmd;
    }
}
