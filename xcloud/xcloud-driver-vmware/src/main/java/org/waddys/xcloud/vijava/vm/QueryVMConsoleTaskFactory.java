package org.waddys.xcloud.vijava.vm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.waddys.xcloud.vijava.base.Answer;
import org.waddys.xcloud.vijava.base.BaseTask;
import org.waddys.xcloud.vijava.base.BaseTaskFactory;
import org.waddys.xcloud.vijava.base.Cmd;
import org.waddys.xcloud.vijava.exception.VirtException;
import org.waddys.xcloud.vijava.util.ParamValidator;
import org.waddys.xcloud.vijava.util.VmConvertUtils;
import org.waddys.xcloud.vijava.vm.QueryVMConsole.QueryVMConsoleCmd;

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
