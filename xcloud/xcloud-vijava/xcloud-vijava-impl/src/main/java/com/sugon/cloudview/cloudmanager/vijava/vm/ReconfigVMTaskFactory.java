package com.sugon.cloudview.cloudmanager.vijava.vm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sugon.cloudview.cloudmanager.vijava.base.Answer;
import com.sugon.cloudview.cloudmanager.vijava.base.BaseTask;
import com.sugon.cloudview.cloudmanager.vijava.base.BaseTaskFactory;
import com.sugon.cloudview.cloudmanager.vijava.base.Cmd;
import com.sugon.cloudview.cloudmanager.vijava.exception.VirtException;
import com.sugon.cloudview.cloudmanager.vijava.util.VmConvertUtils;
import com.sugon.cloudview.cloudmanager.vijava.vm.ReconfigVM.ReconfigVMCmd;

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
