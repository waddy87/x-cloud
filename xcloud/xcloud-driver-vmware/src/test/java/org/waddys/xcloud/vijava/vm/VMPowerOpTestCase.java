package org.waddys.xcloud.vijava.vm;

import org.waddys.xcloud.vijava.base.CloudviewExecutorImpl;
import org.waddys.xcloud.vijava.exception.VirtException;
import org.waddys.xcloud.vijava.vm.VMPowerOperate.PowerOPType;
import org.waddys.xcloud.vijava.vm.VMPowerOperate.VMPowerOpAnswer;
import org.waddys.xcloud.vijava.vm.VMPowerOperate.VMPowerOpCmd;

public class VMPowerOpTestCase {
    public static void main(String[] args) throws VirtException {

        // 通过注解方式注入依赖
        CloudviewExecutorImpl impl = new CloudviewExecutorImpl();
        /**
         * 1.启动虚拟机
         */
        VMPowerOpCmd cmd = new VMPowerOpCmd().setVmId("vm-942").setOpType(PowerOPType.powerOn);
        /**
         * 2.关闭虚拟机
         */
        // VMPowerOpCmd cmd = new
        // VMPowerOpCmd().setVmId("vm-942").setOpType(PowerOPType.powerOff);
        /**
         * 3.重启虚拟机
         */
        // VMPowerOpCmd cmd = new
        // VMPowerOpCmd().setVmId("vm-942").setOpType(PowerOPType.restart);
        /**
         * 4.挂起虚拟机
         */
        // VMPowerOpCmd cmd = new
        // VMPowerOpCmd().setVmId("vm-942").setOpType(PowerOPType.suspend);

        VMPowerOpAnswer answer = impl.execute(cmd);
        System.out.println(answer);
    }
}
