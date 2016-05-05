/**
 * 
 */
package com.sugon.cloudview.cloudmanager.monitor.serviceImpl.impl;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sugon.cloudview.cloudmanager.monitor.service.exception.CloudViewPerfException;
import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.entity.Host;
import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.entity.VM;
import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.service.HistoryPerfAndAlertServiceI;
import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.service.HostAndVmPerfOp;
import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.util.Connection;
import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.util.PerfConstants;
import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.util.PerfUtils;
import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.util.SortUtils;
import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.util.ToolsUtils;
import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.util.VCenterManageUtils;
import com.sugon.vim25.DatastoreSummary;
import com.sugon.vim25.DynamicProperty;
import com.sugon.vim25.HostHardwareInfo;
import com.sugon.vim25.HostListSummary;
import com.sugon.vim25.HostSystemConnectionState;
import com.sugon.vim25.HostSystemPowerState;
import com.sugon.vim25.InvalidProperty;
import com.sugon.vim25.ManagedEntityStatus;
import com.sugon.vim25.ManagedObjectReference;
import com.sugon.vim25.ObjectContent;
import com.sugon.vim25.ObjectSpec;
import com.sugon.vim25.PropertyFilterSpec;
import com.sugon.vim25.PropertySpec;
import com.sugon.vim25.RetrieveOptions;
import com.sugon.vim25.RetrieveResult;
import com.sugon.vim25.RuntimeFault;
import com.sugon.vim25.SelectionSpec;
import com.sugon.vim25.TraversalSpec;
import com.sugon.vim25.VirtualMachineConfigInfo;
import com.sugon.vim25.VirtualMachineStorageSummary;
import com.sugon.vim25.VirtualMachineSummary;
import com.sugon.vim25.mo.ClusterComputeResource;
import com.sugon.vim25.mo.ContainerView;
import com.sugon.vim25.mo.Datastore;
import com.sugon.vim25.mo.HostSystem;
import com.sugon.vim25.mo.ManagedEntity;
import com.sugon.vim25.mo.PropertyCollector;
import com.sugon.vim25.mo.ServiceInstance;
import com.sugon.vim25.mo.ViewManager;
import com.sugon.vim25.mo.VirtualMachine;

/**
 * @author dengjq
 *
 */
@Service("monitor-hostAndVmPerfOpImpl")
public class HostAndVmPerfOpImpl implements HostAndVmPerfOp {

    @Qualifier("monitor-connection")
    @Autowired
    private Connection connection;
    String accessble = "accessble";
    String unaccessble = "unaccessble";
    private VCenterManageUtils opUtil;

    @Qualifier("monitor-historyPerfAndAlertServiceImpl")
    @Autowired
    private HistoryPerfAndAlertServiceI alarmUtil;

    @Qualifier("monitor-resourceServiceImpl")
    @Autowired
    private ResourceServiceImpl resourceServiceImpl;

    private static Logger logger = LogManager.getLogger(HostAndVmPerfOpImpl.class.getName());

    /**
     * 返回clusterId下的所有主机信息，目前不包括虚拟机详细信息，只包括虚拟机个数
     */
    @Override
    public List<Host> getHosts(String clusterId) {
        // TODO Auto-generated method stub
        List<HostSystem> hostSystems = null;
        List<String> hostIdList = new ArrayList<String>();
        try {
            hostSystems = this.getOpUtil().getAllHostSystemInCluster(clusterId);
            for (HostSystem hostsystem : hostSystems) {
                hostIdList.add(hostsystem.getMOR().getVal());
            }
        } catch (CloudViewPerfException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        List<Host> hosts = new ArrayList<Host>();
        if (hostSystems == null) {
            return hosts;
        }
        for (String hostId : hostIdList) {
            HostSystem hostsystem = null;
            try {
                hostsystem = this.getOpUtil().getHostSystemById(hostId);
            } catch (CloudViewPerfException e3) {
                e3.printStackTrace();
            }

            long starTime = System.currentTimeMillis();
            Map<String, String> commonInfo = PerfUtils.getHostCommonPerf(hostsystem);
            Map<String, String> otherInfo = PerfUtils.getHostOtherPerf(hostsystem);

            Host host = new Host();

            host.setName(hostsystem.getName());
            host.setId(hostsystem.getMOR().getVal());
            String hostName = hostsystem.getName();

            // 断开主机特殊处理
            if (hostsystem.getRuntime().getConnectionState() == HostSystemConnectionState.disconnected) {

                PerfUtils.InitialHost(host);
                host.setConnectionStatus(
                        ToolsUtils.convertHostConnectionStatusToString(hostsystem.getRuntime().getConnectionState()));
                hosts.add(host);
                continue;
            }

            HostSystemPowerState powerStatus = hostsystem.getRuntime().getPowerState();
            ManagedEntityStatus mEnStatus = hostsystem.getSummary().getOverallStatus();

            int vmLen = 0;
            try {
                VirtualMachine[] vms = hostsystem.getVms();
                if (vms != null && vms.length != 0) {
                    for (VirtualMachine virtualmachine : vms) {
                    	if(virtualmachine.getConfig()==null){
                			continue;
                		}
                        if (!virtualmachine.getConfig().isTemplate()) {
                            vmLen++;
                        }
                    }
                }

                // vmLen = hostsystem.getVms().length;
            } catch (Exception e2) {
                // e2.printStackTrace();
            }

            // 获取已触发告警信息
            host.setTriggeredAlarm(alarmUtil.getTriggeredAlarms(hostsystem));

            try {
                host.setClusterName(this.getOpUtil().getClusterComputeResourceById(clusterId).getName());
            } catch (CloudViewPerfException e1) {
                // TODO Auto-generated catch block
                host.setClusterName("unknown");
                e1.printStackTrace();
            }
            try {
                host.setDataCenterName(
                        this.getOpUtil().getClusterComputeResourceById(clusterId).getParent().getParent().getName());
            } catch (CloudViewPerfException e1) {
                // TODO Auto-generated catch block
                host.setDataCenterName("unknown");
                e1.printStackTrace();
            }
            try {

                // 设置电源状态
                host.setPowerStatus(ToolsUtils.convertHostPowerStatusToString(powerStatus));

                // 设置主机运行状态，是否正常
                host.setStatus(ToolsUtils.convertManageObjectStatusToString(mEnStatus));

                // 设置连接状态
                host.setConnectionStatus(
                        ToolsUtils.convertHostConnectionStatusToString(hostsystem.getRuntime().getConnectionState()));

                // 设置IP地址
                host.setIpAddr(hostName);// getName返回的是主机名或者IP地址

                host.setCpuMHZUsed(commonInfo.get(PerfConstants.CPU_USED_ID));
                host.setCpuUsage(commonInfo.get(PerfConstants.CPU_USAGE_ID));

                host.setMemoryUsed(commonInfo.get(PerfConstants.MEM_USED_ID));
                host.setMemoryUsage(commonInfo.get(PerfConstants.MEM_USAGE_ID));

                host.setDiskReadSpeed(ToolsUtils.GetMapValue(otherInfo, PerfConstants.DISK_READ_ID));
                host.setDiskWriteSpeed(ToolsUtils.GetMapValue(otherInfo, PerfConstants.DISK_WRITE_ID));

                long diskIOSpeed = Long.valueOf(ToolsUtils.GetMapValue(otherInfo, PerfConstants.DISK_READ_ID))
                        + Long.valueOf(ToolsUtils.GetMapValue(otherInfo, PerfConstants.DISK_WRITE_ID));
                host.setDiskIOSpeed(String.valueOf(diskIOSpeed));

                long diskIOPS = Long.valueOf(ToolsUtils.GetMapValue(otherInfo, PerfConstants.DISK_IOPS_READ_ID))
                        + Long.valueOf(ToolsUtils.GetMapValue(otherInfo, PerfConstants.DISK_IOPS_WRITE_ID));
                host.setDiskIops(String.valueOf(diskIOPS));

                host.setNetworkReceiveSpeed(ToolsUtils.GetMapValue(otherInfo, PerfConstants.NET_RX_ID));
                host.setNetworkSendSpeed(ToolsUtils.GetMapValue(otherInfo, PerfConstants.NET_TX_ID));
                host.setNetworkTransmitSpeed(ToolsUtils.GetMapValue(otherInfo, PerfConstants.NET_IO_SPEED_ID));

                host.setCpuMHZTotal(commonInfo.get(PerfConstants.CPU_TOTAL_ID));
                host.setMemoryTotal(commonInfo.get(PerfConstants.MEM_TOTAL_ID));
                host.setDiskTotal(commonInfo.get(PerfConstants.DISK_TOTAL_ID));
                host.setDiskUsed(commonInfo.get(PerfConstants.DISK_USED_ID));
                // host.setVmNumber(vmLen);
                host.setVmNum(vmLen);

                long endTime = System.currentTimeMillis();
                long Time = endTime - starTime;
                // System.out.println("**********************************主机信息花费："
                // + Time + "ms");
                hosts.add(host);
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
        return hosts;
    }

    /**
     * 根据type创建对应的排序对象
     * 
     * @param host
     * @param index
     * @param type
     * @return
     */
    public SortUtils CreateSortUtils(Host host, int index, String type) {

        double value = 0.0;

        if (type.compareToIgnoreCase(PerfConstants.CPU_USAGE_ID) == 0) {

            value = Double.valueOf(host.getCpuUsage());

        } else if (type.compareToIgnoreCase(PerfConstants.CPU_USED_ID) == 0) {

            value = Double.valueOf(host.getCpuMHZUsed());

        } else if (type.compareToIgnoreCase(PerfConstants.MEM_USAGE_ID) == 0) {

            value = Double.valueOf(host.getMemoryUsage());

        } else if (type.compareToIgnoreCase(PerfConstants.MEM_USED_ID) == 0) {

            value = Double.valueOf(host.getMemoryUsed());

        } else if (type.compareToIgnoreCase(PerfConstants.DISK_IO_SPEED_ID) == 0) {

            value = Double.valueOf(host.getDiskIOSpeed());

        } else if (type.compareToIgnoreCase(PerfConstants.DISK_IOPS_ID) == 0) {

            value = Double.valueOf(host.getDiskIops());

        } else if (type.compareToIgnoreCase(PerfConstants.NET_IO_SPEED_ID) == 0) {

            value = Double.valueOf(host.getNetworkTransmitSpeed());

        } else if (type.compareToIgnoreCase(PerfConstants.NET_RX_ID) == 0) {

            value = Double.valueOf(host.getNetworkReceiveSpeed());

        } else if (type.compareToIgnoreCase(PerfConstants.NET_TX_ID) == 0) {

            value = Double.valueOf(host.getNetworkSendSpeed());

        } else if (type.compareToIgnoreCase(PerfConstants.VM_NUMS_ID) == 0) {

            // value = Double.valueOf(host.getVmNumber());
            value = Double.valueOf(host.getVmNum());

        } else {

            value = 0.0;

        }
        return new SortUtils(index, value);
    }

    /**
     * 根据type创建对应的排序对象
     * 
     * @param vm
     * @param index
     * @param type
     * @return
     */
    public SortUtils CreateVmSortUtils(VM vm, int index, String type) {

        double value = 0.0;

        if (type.compareToIgnoreCase(PerfConstants.CPU_USAGE_ID) == 0) {

            value = Double.valueOf(vm.getCpuUsage());

        } else if (type.compareToIgnoreCase(PerfConstants.CPU_USED_ID) == 0) {

            value = Double.valueOf(vm.getCpuMHZUsed());

        } else if (type.compareToIgnoreCase(PerfConstants.MEM_USAGE_ID) == 0) {

            value = Double.valueOf(vm.getMemoryUsage());

        } else if (type.compareToIgnoreCase(PerfConstants.MEM_USED_ID) == 0) {

            value = Double.valueOf(vm.getMemoryUsed());

        } else if (type.compareToIgnoreCase(PerfConstants.DISK_IO_SPEED_ID) == 0) {

            value = Double.valueOf(vm.getDiskIOSpeed());

        } else if (type.compareToIgnoreCase(PerfConstants.DISK_IOPS_ID) == 0) {

            value = Double.valueOf(vm.getDiskIops());

        } else if (type.compareToIgnoreCase(PerfConstants.NET_IO_SPEED_ID) == 0) {

            value = Double.valueOf(vm.getNetworkTransmitSpeed());

        } else if (type.compareToIgnoreCase(PerfConstants.NET_RX_ID) == 0) {

            value = Double.valueOf(vm.getNetworkReceiveSpeed());

        } else if (type.compareToIgnoreCase(PerfConstants.NET_TX_ID) == 0) {

            value = Double.valueOf(vm.getNetworkSendSpeed());

        } else {

            value = 0.0;

        }
        return new SortUtils(index, value);
    }

    /**
     * 返回指定根据指定性能指标类型排序的host列表
     * 
     * @param hosts
     * @param topn
     * @param type
     * @return
     */
    public List<Host> getHostMetricTopN(List<Host> hosts, int topn, String type) {
        List<Host> topnHost = new ArrayList<Host>();
        List<SortUtils> hostSort = new ArrayList<SortUtils>();
        Iterator<Host> hostIter = hosts.iterator();
        int index = 0;
        while (hostIter.hasNext()) {
            Host host = hostIter.next();
            // 断开连接的主机不参与topn排序
            if (host.getConnectionStatus() == PerfConstants.HOST_CONN_STATUS_DISCONNECTED) {
                continue;
            }
            SortUtils su = CreateSortUtils(host, index, type);// new
                                                              // SortUtils(index,Double.valueOf(host.getCpuUsage()));
            index++;
            hostSort.add(su);
        }

        Collections.sort(hostSort);
        index = 0;

        Iterator<SortUtils> iter = hostSort.iterator();
        while (iter.hasNext()) {
            if (index >= topn) {
                break;
            }
            SortUtils su = iter.next();
            topnHost.add(hosts.get(su.getIndex()));
            index++;
        }

        return topnHost;
    }

    /**
     * 返回主机cpu使用率的topn
     */
    @Override
    public List<Host> getHostCpuUsageTopN(List<Host> hosts, int topn) {
        return getHostMetricTopN(hosts, topn, PerfConstants.CPU_USAGE_ID);
    }

    /**
     * 返回主机cpu使用量的topn
     */
    @Override
    public List<Host> getHostCpuUsedTopN(List<Host> hosts, int topn) {
        return getHostMetricTopN(hosts, topn, PerfConstants.CPU_USED_ID);
    }

    /**
     * 返回主机内存使用率的topn
     */
    @Override
    public List<Host> getHostMemUsageTopN(List<Host> hosts, int topn) {
        return getHostMetricTopN(hosts, topn, PerfConstants.MEM_USAGE_ID);
    }

    /**
     * 返回主机内存使用量的topn
     */
    @Override
    public List<Host> getHostMemUsedTopN(List<Host> hosts, int topn) {
        return getHostMetricTopN(hosts, topn, PerfConstants.MEM_USED_ID);
    }

    /**
     * 返回主机磁盘IO速率的topn
     */
    @Override
    public List<Host> getHostDiskIoTopN(List<Host> hosts, int topn) {
        return getHostMetricTopN(hosts, topn, PerfConstants.DISK_IO_SPEED_ID);
    }

    /**
     * 返回主机磁盘IOPS的topn
     */
    @Override
    public List<Host> getHostDiskIopsTopN(List<Host> hosts, int topn) {
        return getHostMetricTopN(hosts, topn, PerfConstants.DISK_IOPS_ID);
    }

    /**
     * 返回主机网络速率的topn
     */
    @Override
    public List<Host> getHostNetIoTopN(List<Host> hosts, int topn) {
        return getHostMetricTopN(hosts, topn, PerfConstants.NET_IO_SPEED_ID);
    }

    // 获取网络发送速率的topn信息
    @Override
    public List<Host> getHostNetSendIoTopN(List<Host> hosts, int topn) {
        return getHostMetricTopN(hosts, topn, PerfConstants.NET_TX_ID);
    }

    // 获取网络接收速率的topn信息
    @Override
    public List<Host> getHostNetRecvIoTopN(List<Host> hosts, int topn) {
        return getHostMetricTopN(hosts, topn, PerfConstants.NET_RX_ID);
    }

    @Override
    public List<Host> getHostVmNumsTopN(List<Host> hosts, int topn) {
        return getHostMetricTopN(hosts, topn, PerfConstants.VM_NUMS_ID);
    }

    // 获取某个集群下所有虚拟机信息
    @Override
    public List<VM> getVmsOnCluster(String clusterId) {
        List<HostSystem> hostSystems = null;
        try {
            hostSystems = this.getOpUtil().getAllHostSystemInCluster(clusterId);
        } catch (CloudViewPerfException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<VM> vmsFinal = new ArrayList<VM>();
        if (hostSystems == null) {
            return vmsFinal;
        }
        for (HostSystem host : hostSystems) {
            List<VM> vms = getVmsOnHost(host.getMOR().getVal());
            // System.out.println("vm nums ="+ vms.size() + " on host " +
            // host.getName());
            vmsFinal.addAll(vms);
        }

        return vmsFinal;
    }

    // 获取某个主机下所有虚拟机信息，目前只包括虚拟机个数信息，未包括虚拟机详细信息
    @Override
    public List<VM> getVmsOnHost(String hostId) {

        HostSystem host = null;
        try {
            host = this.getOpUtil().getHostSystemById(hostId);
        } catch (CloudViewPerfException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<VM> vmsFinal = new ArrayList<VM>();
        if (host == null) {
            return vmsFinal;
        }
        VirtualMachine[] virtualmachines = null;
        try {
            virtualmachines = host.getVms();
        } catch (InvalidProperty e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RuntimeFault e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        HostListSummary summary = host.getSummary();
        HostHardwareInfo hwi = host.getHardware();
        long hz = hwi.cpuInfo.hz;
        long e6 = 1000000;
        long hzd = (hz) / e6;

        if (virtualmachines != null && virtualmachines.length > 0) {
            for (VirtualMachine virtualmachine : virtualmachines) {

                // 过滤掉临时对象，例如正在创建过程中的对象
                if (virtualmachine.getMOR() == null || virtualmachine.getSummary() == null
                        || virtualmachine.getRuntime() == null || virtualmachine.getConfig() == null) {
                    continue;
                }

                // 过滤掉模板
                if (virtualmachine.getConfig().isTemplate()) {
                    continue;
                }
                long starVmPerfTime = System.currentTimeMillis();
                VM vm = new VM();
                vm.setName(virtualmachine.getName());
                vm.setId(virtualmachine.getMOR().getVal());
                vm.setHostId(hostId);
                vm.setHostName(host.getName());
                vm.setClusterName(host.getParent().getName());
                vm.setDateCenterName(host.getParent().getParent().getParent().getName());
                ManagedEntity me = host.getParent();
                if (me instanceof ClusterComputeResource) {
                    vm.setBStandalone(false);
                } else {
                    vm.setBStandalone(true);
                }
                if (host.getRuntime().getConnectionState() == HostSystemConnectionState.disconnected) {
                    vm.setConnectionStatus(PerfConstants.HOST_CONN_STATUS_DISCONNECTED);
                    PerfUtils.InitialVM(vm);
                    vmsFinal.add(vm);
                    continue;
                }
                Map<String, String> commonInfo = PerfUtils.getVmCommonPerf(virtualmachine);
                System.out.println("get virtual machine perf data[" + virtualmachine.getName() + "]");
                Map<String, String> otherInfo = PerfUtils.getVmOtherPerf(virtualmachine);

                vm.setStatus(
                        ToolsUtils.convertManageObjectStatusToString(virtualmachine.getSummary().getOverallStatus()));

                // 设置电源状态
                vm.setPowerStatus(ToolsUtils.convertVmPowerStatusToString(virtualmachine.getRuntime().getPowerState()));
                vm.setConnectionStatus(PerfConstants.HOST_CONN_STATUS_CONNECTED);

                long totalHz = hzd * virtualmachine.getConfig().getHardware().getNumCPU();
                vm.setCpuMHZTotal(String.valueOf(totalHz));

                vm.setCpuMHZUsed(commonInfo.get(PerfConstants.CPU_USED_ID));

                double cpuUsage = (Double.valueOf(commonInfo.get(PerfConstants.CPU_USED_ID)) / totalHz) * 100;
                BigDecimal cpuUsageBd = new BigDecimal(cpuUsage);
                double cpuUsagePercent = cpuUsageBd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                vm.setCpuUsage(String.valueOf(cpuUsagePercent));

                vm.setMemoryTotal(commonInfo.get(PerfConstants.MEM_TOTAL_ID));
                vm.setMemoryUsed(commonInfo.get(PerfConstants.MEM_USED_ID));
                vm.setMemoryUsage(commonInfo.get(PerfConstants.MEM_USAGE_ID));

                vm.setDiskTotal(commonInfo.get(PerfConstants.DISK_TOTAL_ID));
                vm.setDiskUsed(commonInfo.get(PerfConstants.DISK_USED_ID));
                vm.setDiskUsage(commonInfo.get(PerfConstants.DISK_USAGE_ID));
                vm.setIpAddr(commonInfo.get(PerfConstants.IPADDRESS_ID));

                long diskIoR = Long.valueOf(ToolsUtils.GetMapValue(otherInfo, PerfConstants.VDISK_READ_ID));
                if (diskIoR < 0) {
                    diskIoR = 0;
                }
                long diskIoW = Long.valueOf(ToolsUtils.GetMapValue(otherInfo, PerfConstants.VDISK_WRITE_ID));
                if (diskIoW < 0) {
                    diskIoW = 0;
                }

                vm.setDiskReadSpeed(String.valueOf(diskIoR));
                vm.setDiskWriteSpeed(String.valueOf(diskIoW));
                vm.setDiskIOSpeed(String.valueOf(diskIoR + diskIoW));

                long diskIopsR = Long.valueOf(ToolsUtils.GetMapValue(otherInfo, PerfConstants.VDISK_IOPS_READ_ID));
                if (diskIopsR < 0) {
                    diskIopsR = 0;
                }
                long diskIopsW = Long.valueOf(ToolsUtils.GetMapValue(otherInfo, PerfConstants.VDISK_IOPS_WRITE_ID));
                if (diskIopsW < 0) {
                    diskIopsW = 0;
                }

                // System.out.println("IOps r="+
                // ToolsUtils.GetMapValue(otherInfo,
                // PerfConstants.VDISK_IOPS_READ_ID) + ",IOps w=" +
                // ToolsUtils.GetMapValue(otherInfo,
                // PerfConstants.VDISK_IOPS_WRITE_ID));
                vm.setDiskIops(String.valueOf(diskIopsR + diskIopsW));

                long netIoS = Long.valueOf(ToolsUtils.GetMapValue(otherInfo, PerfConstants.NET_TX_ID));
                if (netIoS < 0) {
                    netIoS = 0;
                }
                long netIoR = Long.valueOf(ToolsUtils.GetMapValue(otherInfo, PerfConstants.NET_RX_ID));
                if (netIoR < 0) {
                    netIoR = 0;
                }

                vm.setNetworkReceiveSpeed(ToolsUtils.GetMapValue(otherInfo, PerfConstants.NET_RX_ID));
                vm.setNetworkSendSpeed(ToolsUtils.GetMapValue(otherInfo, PerfConstants.NET_TX_ID));
                vm.setNetworkTransmitSpeed(String.valueOf(netIoS + netIoR));
                vm.setTriggeredAlarm(alarmUtil.getTriggeredAlarms(virtualmachine));

                long endVmPerfTime = System.currentTimeMillis();
                long perfVmTime = endVmPerfTime - starVmPerfTime;
                // System.out.println("**********************************虚拟机性能信息花费："
                // + perfVmTime + "ms");

                vmsFinal.add(vm);
            }
        }

        return vmsFinal;
    }

    public SortUtils CreateVMSortUtils(VM vm, int index, String type) {

        double value = 0.0;
        value = Double.valueOf(PerfUtils.getVmMetricValue(vm, type));
        return new SortUtils(index, value);
    }

    /**
     * 返回虚拟机列表某个指标项的topn数据
     * 
     * @param vms
     * @param topn
     * @param type
     * @return
     */
    public List<VM> getVMMetricTopN(List<VM> vms, int topn, String type) {
        List<VM> topnVm = new ArrayList<VM>();
        List<SortUtils> vmSort = new ArrayList<SortUtils>();
        Iterator<VM> vmIter = vms.iterator();
        int index = 0;
        while (vmIter.hasNext()) {
            VM vm = vmIter.next();
            // 断开主机上的虚拟机不参与topn排序
            if (vm.getConnectionStatus() == PerfConstants.HOST_CONN_STATUS_DISCONNECTED) {
                continue;
            }
            SortUtils su = CreateVMSortUtils(vm, index, type);// new
                                                              // SortUtils(index,Double.valueOf(host.getCpuUsage()));
            index++;
            vmSort.add(su);
        }

        Collections.sort(vmSort);
        index = 0;

        Iterator<SortUtils> iter = vmSort.iterator();
        while (iter.hasNext()) {
            if (index >= topn) {
                break;
            }
            SortUtils su = iter.next();
            topnVm.add(vms.get(su.getIndex()));
            index++;
        }

        return topnVm;
    }

    // 获取cpu利用率的topn信息
    @Override
    public List<VM> getVmCpuUsageTopN(List<VM> vms, int topn) {
        return getVMMetricTopN(vms, topn, PerfConstants.CPU_USAGE_ID);
    }

    // 获取cpu使用量的topn信息
    @Override
    public List<VM> getVmCpuUsedTopN(List<VM> vms, int topn) {
        return getVMMetricTopN(vms, topn, PerfConstants.CPU_USED_ID);
    }

    // 获取内存利用率的topn信息
    @Override
    public List<VM> getVmMemUsageTopN(List<VM> vms, int topn) {
        return getVMMetricTopN(vms, topn, PerfConstants.MEM_USAGE_ID);
    }

    // 获取内存使用量的topn信息
    @Override
    public List<VM> getVmMemUsedTopN(List<VM> vms, int topn) {
        return getVMMetricTopN(vms, topn, PerfConstants.MEM_USED_ID);
    }

    // 获取磁盘io速率的topn信息

    @Override
    public List<VM> getVmDiskIoTopN(List<VM> vms, int topn) {
        return getVMMetricTopN(vms, topn, PerfConstants.VDISK_IO_SPEED_ID);
    }

    // 获取磁盘iops的topn信息
    @Override
    public List<VM> getVmDiskIopsTopN(List<VM> vms, int topn) {
        return getVMMetricTopN(vms, topn, PerfConstants.VDISK_IOPS_ID);
    }

    // 获取磁盘使用率的topn信息
    @Override
    public List<VM> getVmDiskUsageTopN(List<VM> vms, int topn) {
        return getVMMetricTopN(vms, topn, PerfConstants.DISK_USAGE_ID);
    }

    // 获取网络速率的topn信息
    @Override
    public List<VM> getVmNetIoTopN(List<VM> vms, int topn) {
        return getVMMetricTopN(vms, topn, PerfConstants.NET_IO_SPEED_ID);
    }

    // 获取网络发送速率的topn信息
    @Override
    public List<VM> getVmNetSendIoTopN(List<VM> vms, int topn) {
        return getVMMetricTopN(vms, topn, PerfConstants.NET_TX_ID);
    }

    // 获取网络接收速率的topn信息
    @Override
    public List<VM> getVmNetRecvIoTopN(List<VM> vms, int topn) {
        return getVMMetricTopN(vms, topn, PerfConstants.NET_RX_ID);
    }

    @Override
    public List<Host> getHosts() {
        // TODO Auto-generated method stub
        List<ClusterComputeResource> clusters = null;
        try {
            clusters = this.getOpUtil().getAllClusterComputeResources();
        } catch (CloudViewPerfException e) {
            // TODO Auto-generated catch block
            // System.out.println("get all cluster failed");
            e.printStackTrace();
        }
        List<Host> hostsTotal = new ArrayList<Host>();
        if (clusters != null && clusters.size() > 0) {
            for (int i = 0; i < clusters.size(); i++) {
                ClusterComputeResource cluster = clusters.get(i);
                List<Host> hosts = getHosts(cluster.getMOR().getVal());
                hostsTotal.addAll(hosts);
            }
        }

        return hostsTotal;
    }

    /**
     * @return the opUtil
     */
    public VCenterManageUtils getOpUtil() {
        // if (this.opUtil == null)
        this.opUtil = new VCenterManageUtils(this.connection);
        return opUtil;
    }

    @Override
    public List<VM> getVms() {
        // TODO Auto-generated method stub
        List<ClusterComputeResource> clusters = null;
        try {
            clusters = this.getOpUtil().getAllClusterComputeResources();
        } catch (CloudViewPerfException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<VM> vmsFinal = new ArrayList<VM>();
        if (clusters == null) {
            return vmsFinal;
        }

        for (ClusterComputeResource cluster : clusters) {
            // System.out.println("Cluster Name="+ cluster.getName());
            List<VM> vms = getVmsOnCluster(cluster.getMOR().getVal());
            // System.out.println("vm nums on cluster ="+ vms.size());
            vmsFinal.addAll(vms);
        }

        return vmsFinal;
    }

    /**
     * 根据PropertyCollector收集的属性集进行解析，生成Host对象，并设置对应属性，
     * 
     * @param ocs
     * @param si
     * @return
     */
    public List<Host> processHostProperty(List<ObjectContent> ocs, ServiceInstance si) {
        List<Host> hosts = new ArrayList<Host>();
        if (ocs == null || ocs.size() == 0) {
            return hosts;
        }
        HostSystem[] hostSystems = new HostSystem[ocs.size()];
        int index = 0;
        for (int i = 0; i < ocs.size(); i++) {
            Host host = new Host();
            String name = null;
            String path = null;
            long totalHz = 0, totalMem = 0, cpuUsed = 0, memUsed = 0;
            DynamicProperty[] dps = ocs.get(i).getPropSet();
            if (dps != null) {
                for (int j = 0; j < dps.length; j++) {
                    DynamicProperty dp = dps[j];
                    path = dp.getName();
                    if (path.compareToIgnoreCase("name") == 0) {
                        host.setName((String) dp.getVal());
                        host.setIpAddr(host.getName());
                    } else if (path.compareToIgnoreCase("summary") == 0) {
                        HostListSummary summary = (HostListSummary) dp.getVal();
                        cpuUsed = summary.quickStats.overallCpuUsage;
                        memUsed = summary.quickStats.overallMemoryUsage;

                        host.setCpuMHZUsed(String.valueOf(cpuUsed));
                        host.setMemoryUsed(String.valueOf(memUsed));

                        HostSystem hostSystem = new HostSystem(si.getServerConnection(), summary.getHost());
                        hostSystems[index++] = hostSystem;// 添加到数组中，用于获取性能数据

                        host.setId(hostSystem.getMOR().getVal());
                        if (hostSystem.getParent() != null) {
                            host.setClusterName(hostSystem.getParent().getName());
                        }

                        if (hostSystem.getParent() != null && hostSystem.getParent().getParent() != null
                                && hostSystem.getParent().getParent().getParent() != null) {
                            host.setDataCenterName(hostSystem.getParent().getParent().getParent().getName());
                        }

                        host.setPowerStatus(
                                ToolsUtils.convertHostPowerStatusToString(summary.getRuntime().getPowerState()));
                        host.setStatus(ToolsUtils.convertManageObjectStatusToString(summary.getOverallStatus()));
                        host.setTriggeredAlarm(alarmUtil.getTriggeredAlarms(hostSystem));

                        Datastore[] datastores;
                        double capacity = 0; // 以MB为单位
                        double freeSpace = 0; // 以MB为单位

                        try {
                            datastores = hostSystem.getDatastores();
                            if (datastores != null && datastores.length > 0) {
                                for (Datastore ds : datastores) {
                                    DatastoreSummary datastoresummary = ds.getSummary();
                                    capacity += (double) datastoresummary.getCapacity() / 1024 / 1024;
                                    freeSpace += (double) datastoresummary.getFreeSpace() / 1024 / 1024;
                                }
                            }

                        } catch (InvalidProperty e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (RuntimeFault e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (RemoteException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        host.setDiskTotal(String.valueOf(capacity));
                        host.setDiskUsed(String.valueOf(capacity - freeSpace));
                        try {
                            // host.setVmNumber(hostSystem.getVms().length);
                            host.setVmNum(hostSystem.getVms().length);
                        } catch (InvalidProperty e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (RuntimeFault e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (RemoteException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    } else if (path.compareToIgnoreCase("hardware") == 0) {
                        HostHardwareInfo hwi = (HostHardwareInfo) dp.getVal();
                        long hz = hwi.cpuInfo.hz;
                        long e6 = 1000000;
                        long hzd = (hz) / e6;
                        totalHz = hzd * hwi.cpuInfo.numCpuCores;
                        totalMem = (long) ((double) hwi.getMemorySize() / 1024 / 1024);
                        host.setCpuMHZTotal(String.valueOf(totalHz));
                        host.setMemoryTotal(String.valueOf(totalMem));

                    } else {
                        logger.warn("非法属性:" + path);
                    }
                } // end for DynamicProperty
            } // end if DynamicProperty != null
            if (totalHz != 0) {
                double cpuUsagePercent_ori = ((double) cpuUsed / totalHz) * 100;
                BigDecimal cpuUsageBd = new BigDecimal(cpuUsagePercent_ori);
                double cpuUsagePercent = cpuUsageBd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                host.setCpuUsage(String.valueOf(cpuUsagePercent));
            }

            if (totalMem != 0) {
                double memUsagePercent_ori = ((double) memUsed / totalMem) * 100;
                BigDecimal memUsageBd = new BigDecimal(memUsagePercent_ori);
                double memUsagePercent = memUsageBd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                host.setMemoryUsage(String.valueOf(memUsagePercent));
            }

            hosts.add(host);
        }
        Map<String, Map<String, Long>> hostPerfDatas = PerfUtils.getMorPerfData(hostSystems, 0);
        if (hostPerfDatas != null) {
            for (int i = 0; i < hosts.size(); i++) {
                Host host = hosts.get(i);
                Map<String, Long> perfDatas = hostPerfDatas.get(host.getId());
                if (perfDatas != null) {
                    host.setDiskReadSpeed(
                            String.valueOf(ToolsUtils.GetMapValueLong(perfDatas, PerfConstants.DISK_READ_ID)));
                    host.setDiskWriteSpeed(
                            String.valueOf(ToolsUtils.GetMapValueLong(perfDatas, PerfConstants.DISK_WRITE_ID)));

                    long diskIOSpeed = ToolsUtils.GetMapValueLong(perfDatas, PerfConstants.DISK_READ_ID)
                            + ToolsUtils.GetMapValueLong(perfDatas, PerfConstants.DISK_WRITE_ID);
                    host.setDiskIOSpeed(String.valueOf(diskIOSpeed));

                    long diskIOPS = ToolsUtils.GetMapValueLong(perfDatas, PerfConstants.DISK_IOPS_READ_ID)
                            + ToolsUtils.GetMapValueLong(perfDatas, PerfConstants.DISK_IOPS_WRITE_ID);
                    host.setDiskIops(String.valueOf(diskIOPS));

                    host.setNetworkReceiveSpeed(
                            String.valueOf(ToolsUtils.GetMapValueLong(perfDatas, PerfConstants.NET_RX_ID)));
                    host.setNetworkSendSpeed(
                            String.valueOf(ToolsUtils.GetMapValueLong(perfDatas, PerfConstants.NET_TX_ID)));
                    host.setNetworkTransmitSpeed(
                            String.valueOf(ToolsUtils.GetMapValueLong(perfDatas, PerfConstants.NET_IO_SPEED_ID)));
                }
            }
        }
        return hosts;
    }

    public List<VM> processVmProperty(List<ObjectContent> ocs, ServiceInstance si) {
        List<VM> vms = new ArrayList<VM>();
        if (ocs == null || ocs.size() == 0) {
            return vms;
        }
        VirtualMachine[] virtualmachines = new VirtualMachine[ocs.size()];
        int index = 0;
        for (int i = 0; i < ocs.size(); i++) {
            VM vm = new VM();
            String name = null;
            String path = null;
            long cpuNum = 0, mhzPerCpu = 0, totalMem = 0, cpuUsed = 0, memUsed = 0;
            DynamicProperty[] dps = ocs.get(i).getPropSet();
            if (dps != null) {
                for (int j = 0; j < dps.length; j++) {
                    DynamicProperty dp = dps[j];
                    path = dp.getName();
                    if (path.compareToIgnoreCase("name") == 0) {
                        vm.setName((String) dp.getVal());
                    } else if (path.compareToIgnoreCase("summary") == 0) {
                        VirtualMachineSummary summary = (VirtualMachineSummary) dp.getVal();
                        VirtualMachineStorageSummary vmss = summary.storage;

                        VirtualMachine virtualmachine = new VirtualMachine(si.getServerConnection(), summary.getVm());
                        virtualmachines[index++] = virtualmachine;

                        vm.setId(virtualmachine.getMOR().getVal());

                        ManagedObjectReference hostMor = summary.getRuntime().getHost();
                        if (hostMor != null) {
                            HostSystem host = new HostSystem(si.getServerConnection(), hostMor);
                            vm.setHostId(host.getMOR().getVal());
                            vm.setHostName(host.getName());
                            vm.setClusterName(host.getParent().getName());
                            vm.setDateCenterName(host.getParent().getParent().getParent().getName());
                            HostHardwareInfo hwi = host.getHardware();
                            long hz = hwi.cpuInfo.hz;
                            long e6 = 1000000;
                            mhzPerCpu = (hz) / e6;
                        }

                        cpuUsed = summary.quickStats.overallCpuUsage;
                        vm.setCpuMHZUsed(String.valueOf(cpuUsed));

                        memUsed = summary.quickStats.guestMemoryUsage;
                        vm.setMemoryUsed(String.valueOf(memUsed));

                        double diskTotalMb = (double) (vmss.getCommitted() + vmss.getUncommitted()) / 1024 / 1024;
                        BigDecimal diskTotalBd = new BigDecimal(diskTotalMb);
                        double diskTotal = diskTotalBd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        vm.setDiskTotal(String.valueOf(diskTotal));

                        double diskUsedTotalMb = (double) vmss.getCommitted() / 1024 / 1024;
                        BigDecimal diskUsedBd = new BigDecimal(diskUsedTotalMb);
                        double diskUsedTotal = diskUsedBd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        vm.setDiskUsed(String.valueOf(diskUsedTotal));

                        double diskUsagePercent = 0.0;
                        if (Math.abs(diskTotal - 0) > 0.0001) {
                            BigDecimal diskUsageBd = new BigDecimal((diskUsedTotal / diskTotal) * 100);
                            diskUsagePercent = diskUsageBd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                        vm.setDiskUsage(String.valueOf(diskUsagePercent));

                        vm.setIpAddr(summary.getGuest().getIpAddress());
                        vm.setStatus(ToolsUtils.convertManageObjectStatusToString(summary.getOverallStatus()));

                        // 设置电源状态
                        vm.setPowerStatus(
                                ToolsUtils.convertVmPowerStatusToString(summary.getRuntime().getPowerState()));

                        vm.setTriggeredAlarm(alarmUtil.getTriggeredAlarms(virtualmachine));

                    } else if (path.compareToIgnoreCase("config") == 0) {
                        VirtualMachineConfigInfo vmConfigInfo = (VirtualMachineConfigInfo) dp.getVal();
                        totalMem = vmConfigInfo.getHardware().getMemoryMB();
                        vm.setMemoryTotal(String.valueOf(totalMem));

                        cpuNum = vmConfigInfo.getHardware().getNumCPU();

                    } else {
                        logger.warn("非法属性:" + path);
                    }
                } // end for DynamicProperty

            } // end if DynamicProperty != null
            if (mhzPerCpu != 0 && cpuNum != 0) {
                long totalHz = mhzPerCpu * cpuNum;
                vm.setCpuMHZTotal(String.valueOf(totalHz));

                double cpuUsagePercent_ori = ((double) cpuUsed / totalHz) * 100;
                BigDecimal cpuUsageBd = new BigDecimal(cpuUsagePercent_ori);
                double cpuUsagePercent = cpuUsageBd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                vm.setCpuUsage(String.valueOf(cpuUsagePercent));
            }

            if (totalMem != 0) {
                double memUsagePercent_ori = ((double) memUsed / totalMem) * 100;
                BigDecimal memUsageBd = new BigDecimal(memUsagePercent_ori);
                double memUsagePercent = memUsageBd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                vm.setMemoryUsage(String.valueOf(memUsagePercent));
            }

            vms.add(vm);
        }
        Map<String, Map<String, Long>> vmPerfDatas = PerfUtils.getMorPerfData(virtualmachines, 1);
        if (vmPerfDatas != null) {
            for (int i = 0; i < vms.size(); i++) {
                VM vm = vms.get(i);
                Map<String, Long> perfDatas = vmPerfDatas.get(vm.getId());
                if (perfDatas != null) {
                    vm.setDiskReadSpeed(
                            String.valueOf(ToolsUtils.GetMapValueLong(perfDatas, PerfConstants.VDISK_READ_ID)));
                    vm.setDiskWriteSpeed(
                            String.valueOf(ToolsUtils.GetMapValueLong(perfDatas, PerfConstants.VDISK_WRITE_ID)));

                    long diskIOSpeed = ToolsUtils.GetMapValueLong(perfDatas, PerfConstants.VDISK_READ_ID)
                            + ToolsUtils.GetMapValueLong(perfDatas, PerfConstants.VDISK_WRITE_ID);
                    vm.setDiskIOSpeed(String.valueOf(diskIOSpeed));

                    long diskIOPS = ToolsUtils.GetMapValueLong(perfDatas, PerfConstants.VDISK_IOPS_READ_ID)
                            + ToolsUtils.GetMapValueLong(perfDatas, PerfConstants.VDISK_IOPS_WRITE_ID);
                    vm.setDiskIops(String.valueOf(diskIOPS));

                    long netIoS = ToolsUtils.GetMapValueLong(perfDatas, PerfConstants.NET_TX_ID);
                    long netIoR = ToolsUtils.GetMapValueLong(perfDatas, PerfConstants.NET_RX_ID);
                    vm.setNetworkReceiveSpeed(String.valueOf(netIoS));
                    vm.setNetworkSendSpeed(String.valueOf(netIoR));

                    vm.setNetworkTransmitSpeed(String.valueOf(netIoS + netIoR));
                }
            }
        }
        return vms;
    }

    /**
     * 返回指定类型的指定属性集
     * 
     * @param entityType
     *            ，对象类型，例如虚拟机，物理机
     * @param properties
     *            ，属性集，例如name,summary等
     * @param si
     * @return
     */
    public List<ObjectContent> retrieveMorProperty(String entityType, String[] properties, ServiceInstance si) {
        List<ObjectContent> ocList = new ArrayList<ObjectContent>();

        ViewManager vmgr = si.getViewManager();
        PropertyCollector pc = si.getPropertyCollector();
        ContainerView cv = null;
        try {
            cv = vmgr.createContainerView(si.getRootFolder(), new String[] { entityType }, true);
        } catch (RemoteException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if (cv == null) {
            return ocList;
        }
        ObjectSpec oSpec = new ObjectSpec();
        oSpec.setObj(cv.getMOR());
        oSpec.setSkip(true);

        TraversalSpec tSpec = new TraversalSpec();
        tSpec.setName("traverseEntities");
        tSpec.setPath("view");
        tSpec.setSkip(false);
        tSpec.setType("ContainerView");

        oSpec.setSelectSet(new SelectionSpec[] { tSpec });

        PropertySpec pSpec = new PropertySpec();
        pSpec.setType(entityType);
        pSpec.setPathSet(properties);// new
                                     // String[]{"name","summary","hardware"});

        PropertyFilterSpec fSpec = new PropertyFilterSpec();
        fSpec.setObjectSet(new ObjectSpec[] { oSpec });
        fSpec.setPropSet(new PropertySpec[] { pSpec });

        PropertyFilterSpec[] pfs = new PropertyFilterSpec[1];
        pfs[0] = fSpec;

        RetrieveOptions ro = new RetrieveOptions();
        RetrieveResult props = null;

        try {
            props = pc.retrievePropertiesEx(pfs, ro);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (props != null) {
            ObjectContent[] ocs = props.getObjects();
            if (ocs != null) {
                for (int i = 0; i < ocs.length; i++) {
                    ocList.add(ocs[i]);
                }
            }
            ocs = null;
            String propertyToken = props.getToken();
            while (propertyToken != null && !propertyToken.isEmpty()) {
                try {
                    props = null;
                    propertyToken = "";
                    props = pc.continueRetrievePropertiesEx(propertyToken);
                } catch (InvalidProperty e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (RuntimeFault e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (props != null) {
                    propertyToken = props.getToken();
                    ocs = props.getObjects();
                    if (ocs != null) {
                        for (int i = 0; i < ocs.length; i++) {
                            ocList.add(ocs[i]);
                        }
                    }
                }

            }

        }
        return ocList;
    }

    /**
     * 返回所有主机的cpu总量、使用量、使用率，内存总量、使用量、使用率，存储使用量，存储总量，id,name,cluserName,
     * dataCenterName,ipAddress,状况，电源状态，告警，虚拟机数量
     */
    @Override
    public List<Host> getHostsBatch() {
        // TODO Auto-generated method stub
        long starTime = System.currentTimeMillis();
        ServiceInstance si = connection.getSi();
        List<ObjectContent> ocList = retrieveMorProperty(PerfConstants.ENTITY_HOST, PerfConstants.HOST_PROPERTIES, si);
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        // System.out.println("批量获取主机信息花费：" + Time + "ms");
        return processHostProperty(ocList, si);

    }

    @Override
    public List<VM> getVmsBatch() {
        // TODO Auto-generated method stub
        ServiceInstance si = connection.getSi();
        List<ObjectContent> ocList = retrieveMorProperty(PerfConstants.ENTITY_VM, PerfConstants.VM_PROPERTIES, si);
        return processVmProperty(ocList, si);
    }

    @Override
    public List<Host> getHostsEx() {
        // TODO Auto-generated method stub
        List<HostSystem> hostSystems = null;
        try {
            hostSystems = this.getOpUtil().getAllHostSystems();
        } catch (CloudViewPerfException e) {
            //e.printStackTrace();
        }
        List<Host> hosts = new ArrayList<Host>();
        if (hostSystems == null) {
            return hosts;
        }

        for (HostSystem hostsystem : hostSystems) {
            Host host = new Host();
            host.setName(hostsystem.getName());
            host.setIpAddr(hostsystem.getName());// getName返回的是主机名或者IP地址
            host.setId(hostsystem.getMOR().getVal());

            // 所属数据中心
            try {
            	if(hostsystem.getParent()!=null && hostsystem.getParent().getParent()!=null && hostsystem.getParent().getParent().getParent()!=null ){
            			host.setDataCenterName(hostsystem.getParent().getParent().getParent().getName());
            	}
            } catch (Exception e) {
                host.setDataCenterName("unknown");
                //e.printStackTrace();
            }

            // 所属集群
            try {
            	if(hostsystem.getParent()!=null){
            	    host.setClusterName(hostsystem.getParent().getName());
            	}        
            	
                // 是否为独立主机
                ManagedEntity me = hostsystem.getParent();
                if (me instanceof ClusterComputeResource) {
                    host.setBStandalone(false);
                } else {
                    host.setBStandalone(true);
                }

                // 断开主机特殊处理
                if (hostsystem.getRuntime().getConnectionState() == HostSystemConnectionState.disconnected) {

                    PerfUtils.InitialHost(host);
                    host.setConnectionStatus(
                            ToolsUtils.convertHostConnectionStatusToString(hostsystem.getRuntime().getConnectionState()));
                    hosts.add(host);
                    continue;
                }

                long starTime = System.currentTimeMillis();
                Map<String, String> commonInfo = PerfUtils.getHostCommonPerf(hostsystem);
                Map<String, String> otherInfo = PerfUtils.getHostOtherPerf(hostsystem);

              

                // 虚拟机数量
                int vmLen = 0;
                List<VM> vmList=new ArrayList<VM>(); 
                try {
                    VirtualMachine[] vms = hostsystem.getVms();
                    if (vms != null && vms.length != 0) {
                        for (VirtualMachine virtualmachine : vms) {
                           if(	virtualmachine.getConfig()==null){
                        	   continue;   
                           }else{
	                        	 try {
									if (!virtualmachine.getConfig().isTemplate()) {
										   VM vm=new VM();
										   //为了给出topo状态的信息，直接在物理机中加入少量虚拟机的信息
										   vm.setId( virtualmachine.getMOR().getVal());
										   vm.setName(  virtualmachine.getName());
										   Map<String, String> commonInfoVM = PerfUtils.getVmCommonPerf(virtualmachine);
										   // 设置电源状态
										   vm.setStatus(ToolsUtils.convertManageObjectStatusToString(virtualmachine.getSummary().getOverallStatus()));
										   vm.setPowerStatus(ToolsUtils.convertVmPowerStatusToString(virtualmachine.getRuntime().getPowerState()));
										   vm.setIpAddr(commonInfoVM.get(PerfConstants.IPADDRESS_ID));
									       vmLen++;
									       vmList.add(vm);
									   }
								} catch (Exception e) {
								}
                           }
                           
                        }
                    }
                } catch (Exception e) {
                	logger.debug("获取虚拟机数量异常" +e);
                }
                host.setVmNum(vmLen);
                host.setVmList(vmList);  ///给拓扑结构专用

                // 获取已触发告警信息
                host.setTriggeredAlarm(alarmUtil.getTriggeredAlarms(hostsystem));
         
            	  HostSystemPowerState powerStatus = hostsystem.getRuntime().getPowerState();
                  ManagedEntityStatus mEnStatus = hostsystem.getSummary().getOverallStatus();

                // 设置电源状态
                host.setPowerStatus(ToolsUtils.convertHostPowerStatusToString(powerStatus));

                // 设置主机运行状态，是否正常
                host.setStatus(ToolsUtils.convertManageObjectStatusToString(mEnStatus));

                // 设置连接状态
                host.setConnectionStatus(
                        ToolsUtils.convertHostConnectionStatusToString(hostsystem.getRuntime().getConnectionState()));

                host.setCpuMHZUsed(commonInfo.get(PerfConstants.CPU_USED_ID));
                host.setCpuUsage(commonInfo.get(PerfConstants.CPU_USAGE_ID));

                host.setMemoryUsed(commonInfo.get(PerfConstants.MEM_USED_ID));
                host.setMemoryUsage(commonInfo.get(PerfConstants.MEM_USAGE_ID));

                host.setDiskReadSpeed(ToolsUtils.GetMapValue(otherInfo, PerfConstants.DISK_READ_ID));
                host.setDiskWriteSpeed(ToolsUtils.GetMapValue(otherInfo, PerfConstants.DISK_WRITE_ID));

                long diskIOSpeed = Long.valueOf(ToolsUtils.GetMapValue(otherInfo, PerfConstants.DISK_READ_ID))
                        + Long.valueOf(ToolsUtils.GetMapValue(otherInfo, PerfConstants.DISK_WRITE_ID));
                host.setDiskIOSpeed(String.valueOf(diskIOSpeed));

                long diskIOPS = Long.valueOf(ToolsUtils.GetMapValue(otherInfo, PerfConstants.DISK_IOPS_READ_ID))
                        + Long.valueOf(ToolsUtils.GetMapValue(otherInfo, PerfConstants.DISK_IOPS_WRITE_ID));
                host.setDiskIops(String.valueOf(diskIOPS));

                host.setNetworkReceiveSpeed(ToolsUtils.GetMapValue(otherInfo, PerfConstants.NET_RX_ID));
                host.setNetworkSendSpeed(ToolsUtils.GetMapValue(otherInfo, PerfConstants.NET_TX_ID));
                host.setNetworkTransmitSpeed(ToolsUtils.GetMapValue(otherInfo, PerfConstants.NET_IO_SPEED_ID));

                host.setCpuMHZTotal(commonInfo.get(PerfConstants.CPU_TOTAL_ID));
                host.setMemoryTotal(commonInfo.get(PerfConstants.MEM_TOTAL_ID));
                host.setDiskTotal(commonInfo.get(PerfConstants.DISK_TOTAL_ID));
                host.setDiskUsed(commonInfo.get(PerfConstants.DISK_USED_ID));
                host.setDiskUsage(commonInfo.get(PerfConstants.DISK_USAGE_ID));
                host.setVmNum(vmLen);

                long endTime = System.currentTimeMillis();
                long Time = endTime - starTime;
                System.out.println("**********************************主机信息花费：" + Time + "ms");

            } catch (Exception e) {
            	logger.debug("获取主机状态异常" +e);
            }
            hosts.add(host);
        }
        return hosts;
    }

    @Override
    public List<VM> getVmsEx() {
        // TODO Auto-generated method stub
        List<HostSystem> hostSystems = null;
        try {
            hostSystems = this.getOpUtil().getAllHostSystems();
        } catch (CloudViewPerfException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        List<VM> vmsFinal = new ArrayList<VM>();
        if (hostSystems == null) {
            return vmsFinal;
        }
        for (HostSystem hostsystem : hostSystems) {
            List<VM> vms = getVmsOnHost(hostsystem.getMOR().getVal());
            vmsFinal.addAll(vms);
        }

        return vmsFinal;
    }

}
