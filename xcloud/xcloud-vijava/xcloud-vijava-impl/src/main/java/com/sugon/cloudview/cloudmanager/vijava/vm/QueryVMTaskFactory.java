package com.sugon.cloudview.cloudmanager.vijava.vm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sugon.cloudview.cloudmanager.vijava.base.Answer;
import com.sugon.cloudview.cloudmanager.vijava.base.BaseTask;
import com.sugon.cloudview.cloudmanager.vijava.base.BaseTaskFactory;
import com.sugon.cloudview.cloudmanager.vijava.base.Cmd;
import com.sugon.cloudview.cloudmanager.vijava.exception.VirtException;
import com.sugon.cloudview.cloudmanager.vijava.util.VmConvertUtils;
import com.sugon.cloudview.cloudmanager.vijava.vm.QueryVM.QueryVMCmd;

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
