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
import org.waddys.xcloud.vijava.vm.MigrateVM.MigrateVMCmd;

public class MigrateVMTaskFactory implements BaseTaskFactory {

    private static Logger logger = LoggerFactory.getLogger(MigrateVMTaskFactory.class);

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Answer> BaseTask<T> createTask(Cmd<T> cmd) throws VirtException {
        logger.debug("参数cmd=" + cmd);
        if (cmd == null) {
            throw new VirtException("参数不能为空");
        }
        MigrateVMCmd migrateVmCmd = this.checkMigrateVmParams(cmd);
        return (BaseTask<T>) new MigrateVMTask(migrateVmCmd);
    }

    private <T extends Answer> MigrateVMCmd checkMigrateVmParams(Cmd<T> cmd) throws VirtException {
        MigrateVMCmd migrateVmCmd = VmConvertUtils.convertWithException(cmd, MigrateVMCmd.class);
        logger.debug("migrateVmCmd is:" + migrateVmCmd);
        String vmId = migrateVmCmd.getVmId();
        if (ParamValidator.validatorParamsIsEmpty(vmId)) {
            logger.error("参数中虚拟机id为空");
            throw new VirtException("必须指定要迁移的虚拟机");
        }
        return migrateVmCmd;
    }
}
