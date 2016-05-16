package com.sugon.cloudview.cloudmanager.vijava.vm;

import java.util.ArrayList;
import java.util.List;

import com.sugon.cloudview.cloudmanager.vijava.base.CloudviewExecutorImpl;
import com.sugon.cloudview.cloudmanager.vijava.data.VMDiskInfo;
import com.sugon.cloudview.cloudmanager.vijava.data.VMNetworkInfo;
import com.sugon.cloudview.cloudmanager.vijava.data.VMNicInfo;
import com.sugon.cloudview.cloudmanager.vijava.exception.VirtException;
import com.sugon.cloudview.cloudmanager.vijava.vm.CreateVM.CreateVMCmd;

public class CreateVMTestCase {

    public static void main(String[] args) throws VirtException {
        /**
         * 1.给定模板和名称，linux虚拟机
         */
        // CreateVMCmd createCmd = new
        // CreateVMCmd().setName("1-linux-clone").setTemplateId("vm-881");
        /**
         * 2.给定模板和名称，windows虚拟机
         */
        // CreateVMCmd createCmd = new
        // CreateVMCmd().setName("2-windows-clone").setTemplateId("vm-873");

        /**
         * 3.linux虚拟机给定cpu数量
         */
        // CreateVMCmd createCmd = new
        // CreateVMCmd().setName("3-linux-clone").setTemplateId("vm-881").setCpuNum(4);

        /**
         * 4.windows虚拟机给定cpu数量
         */
        // CreateVMCmd createCmd = new
        // CreateVMCmd().setName("2-windows-clone").setTemplateId("vm-873").setCpuNum(4);

        /**
         * 5.linux给定内存大小
         */
        // CreateVMCmd createCmd = new
        // CreateVMCmd().setName("3-linux-clone").setTemplateId("vm-881").setMemSizeMB(2048L);
        /**
         * 6.windows给定内存大小
         */
        // CreateVMCmd createCmd = new
        // CreateVMCmd().setName("2-windows-clone").setTemplateId("vm-873")
        // .setMemSizeMB(2048L);

        /**
         * 7.linux给定资源池
         */
        // CreateVMCmd createCmd = new
        // CreateVMCmd().setName("3-linux-clone").setTemplateId("vm-881")
        // .setPoolId("resgroup-64");

        /**
         * 8.windows给定资源池
         */
        // CreateVMCmd createCmd = new
        // CreateVMCmd().setName("2-windows-clone").setTemplateId("vm-873")
        // .setPoolId("resgroup-779");
        /**
         * TODO:9.linux给定数据存储id
         */
        // CreateVMCmd createCmd = new
        // CreateVMCmd().setName("3-linux-clone").setTemplateId("vm-881")
        // .setDatastoreId("datastore-876");
        /**
         * TODO:10.windows给定数据存储id
         */

        List<VMNicInfo> nicInfos = new ArrayList<VMNicInfo>();
        VMNicInfo nicInfo = new VMNicInfo();
        nicInfo.setAdapterName("网络适配器 1");
        nicInfo.setNetworkId("VM Network");
        nicInfos.add(nicInfo);
        /**
         * 11.linux给定网卡信息
         */
        // CreateVMCmd createCmd = new
        // CreateVMCmd().setName("3-linux-clone").setTemplateId("vm-881")
        // .setNicInfos(nicInfos);

        /**
         * 12.windows给定网卡信息
         */
        // CreateVMCmd createCmd = new
        // CreateVMCmd().setName("2-windows-clone").setTemplateId("vm-873")
        // .setNicInfos(nicInfos);
        List<VMDiskInfo> diskInfos = new ArrayList<VMDiskInfo>();
        VMDiskInfo diskInfo = new VMDiskInfo();
        diskInfo.setDiskMode("persistent");
        diskInfo.setDiskSizeGB(3L);
        diskInfo.setThinProvisioned(true);
        diskInfos.add(diskInfo);
        /**
         * 13.linux给定磁盘信息
         */
        // CreateVMCmd createCmd = new
        // CreateVMCmd().setName("4-linux-clone").setTemplateId("vm-881")
        // .setDiskInfos(diskInfos);

        /**
         * 14.windows给定磁盘信息
         */
        // CreateVMCmd createCmd = new
        // CreateVMCmd().setName("5-windows-clone").setTemplateId("vm-873")
        // .setDiskInfos(diskInfos);

        List<VMNetworkInfo> networks = new ArrayList<VMNetworkInfo>();
        VMNetworkInfo networkInfo = new VMNetworkInfo();
        networkInfo.setIp("192.168.0.251");
        VMNetworkInfo networkInfo1 = new VMNetworkInfo();
        networkInfo1.setIp("192.168.0.252");
        networks.add(networkInfo);
        networks.add(networkInfo1);
        /**
         * 15.linux给定网络信息
         */
        // CreateVMCmd createCmd = new
        // CreateVMCmd().setName("6-linux-clone").setTemplateId("vm-881")
        // .setNetworkInfos(networks);
        /**
         * 16.windows给定网络信息
         */
        CreateVMCmd createCmd = new CreateVMCmd().setName("7-windows-clone").setTemplateId("vm-873");



        // CreateVMCmd createCmd = new
        // CreateVMCmd().setName("21-测试linux-VM").setTemplateId("vm-873")
        // .setDatastoreId("datastore-876").setPoolId("resgroup-779")
        // .setNetworkInfos(networks);
        // .setNicInfos(nicInfos)
        // .setNetworkInfos(networks)
        CloudviewExecutorImpl exec = new CloudviewExecutorImpl();
        exec.execute(createCmd);

    }

}
