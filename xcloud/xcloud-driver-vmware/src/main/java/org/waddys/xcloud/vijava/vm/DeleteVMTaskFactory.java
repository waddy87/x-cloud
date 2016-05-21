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
import org.waddys.xcloud.vijava.vm.DeleteVM.DeleteVMCmd;

public class DeleteVMTaskFactory implements BaseTaskFactory {

    private static Logger logger = LoggerFactory.getLogger(DeleteVMTaskFactory.class);

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Answer> BaseTask<T> createTask(Cmd<T> cmd) throws VirtException {
        logger.debug("参数cmd=" + cmd);
        if (cmd == null) {
            throw new VirtException("参数不能为空");
        }
        DeleteVMCmd deleteVMCmd = this.checkDeleteVMParams(cmd);
        return (BaseTask<T>) new DeleteVMTask(deleteVMCmd);

    }

    private <T extends Answer> DeleteVMCmd checkDeleteVMParams(Cmd<T> cmd) throws VirtException {
        DeleteVMCmd deleteVMCmd = VmConvertUtils.convertWithException(cmd, DeleteVMCmd.class);
        logger.debug("deleteVMCmd is:" + deleteVMCmd);
        String vmId = deleteVMCmd.getVmId();
        if (ParamValidator.validatorParamsIsEmpty(vmId)) {
            logger.error("参数中虚拟机id为空");
            throw new VirtException("必须指定要删除的虚拟机");
        }
        return deleteVMCmd;
    }

}
