package com.sugon.cloudview.cloudmanager.vijava.vm;

import com.sugon.cloudview.cloudmanager.vijava.base.CloudviewExecutorImpl;
import com.sugon.cloudview.cloudmanager.vijava.exception.VirtException;
import com.sugon.cloudview.cloudmanager.vijava.vm.VMPowerOperate.PowerOPType;
import com.sugon.cloudview.cloudmanager.vijava.vm.VMPowerOperate.VMPowerOpAnswer;
import com.sugon.cloudview.cloudmanager.vijava.vm.VMPowerOperate.VMPowerOpCmd;

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
