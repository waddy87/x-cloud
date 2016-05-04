package com.sugon.cloudview.cloudmanager.vijava.vm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sugon.cloudview.cloudmanager.vijava.base.Answer;
import com.sugon.cloudview.cloudmanager.vijava.base.BaseTask;
import com.sugon.cloudview.cloudmanager.vijava.base.BaseTaskFactory;
import com.sugon.cloudview.cloudmanager.vijava.base.Cmd;
import com.sugon.cloudview.cloudmanager.vijava.exception.VirtException;
import com.sugon.cloudview.cloudmanager.vijava.util.ParamValidator;
import com.sugon.cloudview.cloudmanager.vijava.util.VmConvertUtils;
import com.sugon.cloudview.cloudmanager.vijava.vm.QueryVMConsole.QueryVMConsoleCmd;

public class QueryVMConsoleTaskFactory implements BaseTaskFactory {
    private static Logger logger = LoggerFactory.getLogger(QueryVMConsoleTaskFactory.class);
    
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Answer> BaseTask<T> createTask(Cmd<T> cmd) throws VirtException {
        logger.debug("参数cmd=" + cmd);
        if (cmd == null) {
            throw new VirtException("参数不能为空");
        }
        QueryVMConsoleCmd queryVMConsoleCmd = this.checkQueryVMConsoleCmdParams(cmd);
        return (BaseTask<T>) new QueryVMConsoleTask(queryVMConsoleCmd);
    }

    private <T extends Answer> QueryVMConsoleCmd checkQueryVMConsoleCmdParams(Cmd<T> cmd) throws VirtException {
    	QueryVMConsoleCmd queryVMConsoleCmd = VmConvertUtils.convertWithException(cmd, QueryVMConsoleCmd.class);
        logger.debug("queryVMConsoleCmd is:" + queryVMConsoleCmd);
        String vmMoId = queryVMConsoleCmd.getVmId();
        if (ParamValidator.validatorParamsIsEmpty(vmMoId)) {
            logger.error("虚拟机ID为空");
            throw new VirtException("虚拟机ID为空");
        }
        return queryVMConsoleCmd;
    }
}
