package org.waddys.xcloud.vijava.vm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.waddys.xcloud.vijava.base.BaseTask;
import org.waddys.xcloud.vijava.exception.VirtException;
import org.waddys.xcloud.vijava.impl.Session;
import org.waddys.xcloud.vijava.impl.VirtualMachineImpl;
import org.waddys.xcloud.vijava.util.TaskAnswerConvert;
import org.waddys.xcloud.vijava.vm.VMPowerOperate.PowerOPType;
import org.waddys.xcloud.vijava.vm.VMPowerOperate.VMPowerOpAnswer;
import org.waddys.xcloud.vijava.vm.VMPowerOperate.VMPowerOpCmd;

import com.sugon.vim25.VirtualMachinePowerState;
import com.sugon.vim25.mo.ServiceInstance;
import com.sugon.vim25.mo.Task;
import com.sugon.vim25.mo.VirtualMachine;

public class VMPowerOpTask extends BaseTask<VMPowerOpAnswer> {

    private static Logger logger = LoggerFactory.getLogger(VMPowerOpTask.class);

    /**
     * 虚拟机操作的参数
     */
    private VMPowerOpCmd powerOpCmd;
    private ServiceInstance si = null;

    public VMPowerOpTask(VMPowerOpCmd powerOpCmd) throws VirtException {
        super();
        this.powerOpCmd = powerOpCmd;

		try {
			this.setSi(Session.getInstanceByToken(powerOpCmd.getToken()));
		} catch (Exception e) {
			logger.error("连接" + powerOpCmd.getToken() + "失败，原因" + e.getMessage());
			throw new VirtException("虚拟环境" + "连接异常 ：" + e.getMessage());
		}
		
    }

    @Override
    public VMPowerOpAnswer execute() {

        logger.debug("参数=" + this.powerOpCmd);
        try {
            // 1.获取连接
//           ServiceInstance si = Session.getInstance();
            // 2.获取要操作的虚拟机对象
            String vmMoId = this.powerOpCmd.getVmId();
            VirtualMachine vm = VirtualMachineImpl.getVirtualMachineById(si, vmMoId);
            logger.debug("要操作的虚拟机,vmId=" + vmMoId + ",vmName=" + vm.getName());
            // 3.根据操作类型对虚拟机执行相应操作
            PowerOPType opType = this.powerOpCmd.getOpType();

            Task task = null;
            if (PowerOPType.powerOn.equals(opType)) {
            	if (VirtualMachinePowerState.poweredOn.name().equals(vm.getRuntime().getPowerState().name())) {
                    logger.error("虚拟机电源状态已经是开启了");
                    throw new VirtException("虚拟机电源状态已经是开启了，您无需再开启");
                }
                task = vm.powerOnVM_Task(null);
            } else if (PowerOPType.powerOff.equals(opType)) {
            	if (VirtualMachinePowerState.poweredOff.name().equals(vm.getRuntime().getPowerState().name())) {
                    logger.error("虚拟机电源状态已经是关闭了");
                    throw new VirtException("虚拟机电源状态已经是关闭了，您无需再关闭");
                }
                task = vm.powerOffVM_Task();
            } else if (PowerOPType.restart.equals(opType)) {
                task = vm.resetVM_Task();
            } else if (PowerOPType.suspend.equals(opType)) {
                task = vm.suspendVM_Task();
            }
            // else if (PowerOPType.restart.equals(opType)) {
            // vm.rebootGuest();
            // }
            // else if (VMPowerOpType.standby.equals(opType)) {
            // vm.standbyGuest();
            // } else if (VMPowerOpType.shutdown.equals(opType)) {
            // task = vm.suspendVM_Task();
            // }
            else {
                throw new VirtException("不支持的操作类型：" + opType);
            }

            VMPowerOpAnswer answer = new VMPowerOpAnswer().setSuccess(true);
            if (task != null) {
            	TaskAnswerConvert.setTask(answer, task);
            	}
            return answer;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new VMPowerOpAnswer().setSuccess(false).setErrMsg(e.getMessage());
        } finally {
            // TODO:是否要断开连接
        }
    }


	public ServiceInstance getSi() {
		return si;
	}

	public void setSi(ServiceInstance si) {
		this.si = si;
	}


    public static void main(String args[]) throws VirtException {
    	
        VMPowerOpCmd powerOpCmd = new VMPowerOpCmd();
        powerOpCmd.setOpType(PowerOPType.powerOff);
        powerOpCmd.setVmId("vm-1248");
        VMPowerOpTask task = new VMPowerOpTask(powerOpCmd);
        VMPowerOpAnswer answer = task.execute();
        System.out.println(answer);
    }

}
