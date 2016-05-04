package com.sugon.cloudview.cloudmanager.vijava.vm;

import com.sugon.cloudview.cloudmanager.vijava.base.CloudviewExecutorImpl;
import com.sugon.cloudview.cloudmanager.vijava.exception.VirtException;
import com.sugon.cloudview.cloudmanager.vijava.vm.QueryVM.QueryVMAnswer;
import com.sugon.cloudview.cloudmanager.vijava.vm.QueryVM.QueryVMCmd;

public class QueryVMTestCase {
    public static void main(String[] args) throws VirtException {

        // 通过注解方式注入依赖
        CloudviewExecutorImpl impl = new CloudviewExecutorImpl();

        // QueryVMCmd cmd = new QueryVMCmd().setTag("ss");
        /**
         * 1.查询所有虚拟机和模板
         */
        // QueryVMCmd cmd = new QueryVMCmd();
        /**
         * 2.查询所有虚拟机
         */
        // QueryVMCmd cmd = new QueryVMCmd().setIsTemplate(false);
        /**
         * 3.查询所有模板
         */
        // QueryVMCmd cmd = new QueryVMCmd().setIsTemplate(true);
        /**
         * 4.查询指定id的虚拟机/模板
         */
        // QueryVMCmd cmd = new QueryVMCmd().setVmId("vm-942");
        /**
         * 5.查询指定名称的虚拟机/模板，支持模糊查询
         */
        // QueryVMCmd cmd = new QueryVMCmd().setVmName("21-");
        /**
         * 6.查询指定电源状态的虚拟机/模板
         */
        // QueryVMCmd cmd = new
        // QueryVMCmd().setPowerStatus(PowerState.poweredOn);
        // QueryVMCmd cmd = new
        // QueryVMCmd().setPowerStatus(PowerState.poweredOff);
        /**
         * 7.查询指定OSID的虚拟机/模板，支持模糊查询
         */
        // QueryVMCmd cmd = new QueryVMCmd().setGuestOSId("cent");
        /**
         * TODO:只有启动状态的虚拟机才有IP 8.查询指定ip的虚拟机/模板，支持模糊查询
         */
        QueryVMCmd cmd = new QueryVMCmd().setIp("169.");
        /**
         * 9.查询指定数据存储名称的虚拟机/模板
         */
        // QueryVMCmd cmd = new QueryVMCmd().setDatastoreName("31170-1");
        /**
         * 10.查询指定网络名称的虚拟机/模板
         */
        // QueryVMCmd cmd = new QueryVMCmd().setNetworkName("VM Network");
        /**
         * 11.查询指定资源池名称的虚拟机/模板
         */
        // QueryVMCmd cmd = new QueryVMCmd().setPoolName("Resources");
        /**
         * 12.查询指定资源池名称、数据存储名称和网络名称的虚拟机/模板
         */
        // QueryVMCmd cmd = new
        // QueryVMCmd().setPoolName("Resources").setNetworkName("VM Network")
        // .setDatastoreName("31170-1");
        QueryVMAnswer answer = impl.execute(cmd);
        System.out.println(cmd);
        System.out.println(answer.getVmList().size() + "  " + answer);
    }
}
