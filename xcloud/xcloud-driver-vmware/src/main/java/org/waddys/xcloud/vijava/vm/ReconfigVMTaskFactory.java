package org.waddys.xcloud.vijava.vm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.waddys.xcloud.vijava.base.Answer;
import org.waddys.xcloud.vijava.base.BaseTask;
import org.waddys.xcloud.vijava.base.BaseTaskFactory;
import org.waddys.xcloud.vijava.base.Cmd;
import org.waddys.xcloud.vijava.exception.VirtException;
import org.waddys.xcloud.vijava.util.VmConvertUtils;
import org.waddys.xcloud.vijava.vm.ReconfigVM.ReconfigVMCmd;

public class ReconfigVMTaskFactory implements BaseTaskFactory {
	
    private static Logger logger = LoggerFactory.getLogger(ReconfigVMTaskFactory.class);
    
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Answer> BaseTask<T> createTask(Cmd<T> cmd) throws VirtException {
        logger.debug("参数cmd=" + cmd);
        if (cmd == null) {
            throw new VirtException("参数不能为空");
        }
        ReconfigVMCmd reconfigVMCmd = VmConvertUtils.convertWithException(cmd, ReconfigVMCmd.class);
        return (BaseTask<T>) new ReconfigVMTask(reconfigVMCmd);
    }
}
