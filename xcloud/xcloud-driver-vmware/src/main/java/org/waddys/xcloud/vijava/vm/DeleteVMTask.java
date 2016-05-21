package org.waddys.xcloud.vijava.vm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.waddys.xcloud.vijava.base.BaseTask;
import org.waddys.xcloud.vijava.exception.VirtException;
import org.waddys.xcloud.vijava.impl.Session;
import org.waddys.xcloud.vijava.impl.VirtualMachineImpl;
import org.waddys.xcloud.vijava.util.TaskAnswerConvert;
import org.waddys.xcloud.vijava.vm.DeleteVM.DeleteVMAnswer;
import org.waddys.xcloud.vijava.vm.DeleteVM.DeleteVMCmd;

import com.vmware.vim25.VirtualMachinePowerState;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

public class DeleteVMTask extends BaseTask<DeleteVMAnswer> {

    private static Logger logger = LoggerFactory.getLogger(DeleteVMTask.class);

    private static final Boolean success = true;
    private static final Boolean failure = false;

    private DeleteVMCmd deleteVMCmd;
    private ServiceInstance si = null;

    public DeleteVMTask(DeleteVMCmd deleteVMCmd) throws VirtException {
        super();
        this.deleteVMCmd = deleteVMCmd;
    	
		try {
			this.setSi(Session.getInstanceByToken(deleteVMCmd.getToken()));
		} catch (Exception e) {
			logger.error("连接失败，原因" + e.getMessage());
			throw new VirtException("虚拟环境" + "连接异常 ：" + e.getMessage());
		}
    }

    @Override
    public DeleteVMAnswer execute() {
        logger.debug("deleteVMCmd is:" + deleteVMCmd);
        try {
        // 1.获取连接
//        ServiceInstance si = Session.getInstance();
        // 2.获取要删除的虚拟机对象
        String vmMoId = this.deleteVMCmd.getVmId();
        VirtualMachine vm = VirtualMachineImpl.getVirtualMachineById(si, vmMoId);
        if (vm == null) {
            throw new VirtException("指定的虚拟机:" + vmMoId + "不存在");
        }
            if (!VirtualMachinePowerState.poweredOff.name().equals(vm.getRuntime().getPowerState().name())) {
                logger.error("虚拟机电源状态不是关闭");
                throw new VirtException("请先关闭虚拟机，只有关闭状态的虚拟机才能被删除");
            }
            Task task = vm.destroy_Task();
            DeleteVMAnswer answer = new DeleteVMAnswer();
            TaskAnswerConvert.setTask(answer, task);
            return answer.setSuccess(success);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new DeleteVMAnswer().setSuccess(failure).setErrMsg(e.getMessage());
        }
    }
    

	public ServiceInstance getSi() {
		return si;
	}

	public void setSi(ServiceInstance si) {
		this.si = si;
	}

	public static void main(String ap[]){
		DeleteVMCmd deleteVMCmd = new DeleteVMCmd();
		deleteVMCmd.setVmId("vm-1288");
		try {
			System.out.println(new DeleteVMTask(deleteVMCmd).execute());
		} catch (VirtException e) {
			// TODO Auto-generated catch block
			logger.error("there is some err...");
		}
	}

}
