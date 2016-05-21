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
import org.waddys.xcloud.vijava.vm.CreateVM.CreateVMCmd;

public class CreateVMTaskFactory implements BaseTaskFactory {
    private static Logger logger = LoggerFactory.getLogger(CreateVMTaskFactory.class);
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Answer> BaseTask<T> createTask(Cmd<T> cmd) throws VirtException {
        logger.debug("参数cmd=" + cmd);
        if (cmd == null) {
            throw new VirtException("参数不能为空");
        }
        CreateVMCmd createVmCmd = this.checkCreateVmParams(cmd);
        return (BaseTask<T>) new CreateVMTask(createVmCmd);
    }

    private <T extends Answer> CreateVMCmd checkCreateVmParams(Cmd<T> cmd) throws VirtException {
        CreateVMCmd createVmCmd = VmConvertUtils.convertWithException(cmd, CreateVMCmd.class);
        logger.debug("createVmCmd is:" + createVmCmd);
        String templMoId = createVmCmd.getTemplateId();
        if (ParamValidator.validatorParamsIsEmpty(templMoId)) {
            logger.error("参数中模板为空");
            throw new VirtException("必须指定模板");
        }
        String vmName = createVmCmd.getName();
        // TODO:还是业务为空时我们设置生成规则？
        if (ParamValidator.validatorParamsIsEmpty(vmName)) {
            logger.error("参数中虚拟机名称为空");
            throw new VirtException("必须指定有效的虚拟机名称");
        }
        return createVmCmd;
    }
}
