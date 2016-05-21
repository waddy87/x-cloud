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
import org.waddys.xcloud.vijava.vm.VMPowerOperate.PowerOPType;
import org.waddys.xcloud.vijava.vm.VMPowerOperate.VMPowerOpCmd;

public class VMPowerOpTaskFactory implements BaseTaskFactory {

    private static Logger logger = LoggerFactory.getLogger(VMPowerOpTaskFactory.class);

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Answer> BaseTask<T> createTask(Cmd<T> cmd) throws VirtException {
        logger.debug("参数cmd=" + cmd);
        if (cmd == null) {
            throw new VirtException("参数不能为空");
        }
        VMPowerOpCmd powerOpCmd = this.checkPowerOpParams(cmd);
        return (BaseTask<T>) new VMPowerOpTask(powerOpCmd);
    }

    private <T extends Answer> VMPowerOpCmd checkPowerOpParams(Cmd<T> cmd) throws VirtException {
    	
        VMPowerOpCmd powerOpCmd = VmConvertUtils.convertWithException(cmd, VMPowerOpCmd.class);
        logger.debug("powerOpCmd is:" + powerOpCmd);
        String vmId = powerOpCmd.getVmId();
        if (ParamValidator.validatorParamsIsEmpty(vmId)) {
            logger.error("参数中未指定虚拟机");
            throw new VirtException("必须指定一个虚拟机");
        }
        
        PowerOPType  operateType = powerOpCmd.getOpType();
        if (ParamValidator.validatorParamsIsEmpty(operateType)) {
            logger.error("参数中未指定虚拟机操作类型");
            throw new VirtException("必须指定虚拟机的操作类型");
        }
        return powerOpCmd;
    }
}
