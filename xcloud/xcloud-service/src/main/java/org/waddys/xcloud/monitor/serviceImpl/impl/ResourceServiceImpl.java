package org.waddys.xcloud.monitor.serviceImpl.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.waddys.xcloud.monitor.bo.AlarmEntity;
import org.waddys.xcloud.monitor.service.exception.CloudViewPerfException;
import org.waddys.xcloud.monitor.serviceImpl.entity.Cluster;
import org.waddys.xcloud.monitor.serviceImpl.entity.DataCenter;
import org.waddys.xcloud.monitor.serviceImpl.entity.Host;
import org.waddys.xcloud.monitor.serviceImpl.entity.VM;
import org.waddys.xcloud.monitor.serviceImpl.service.HistoryPerfAndAlertServiceI;
import org.waddys.xcloud.monitor.serviceImpl.service.ResourceServiceI;
import org.waddys.xcloud.monitor.serviceImpl.util.Connection;
import org.waddys.xcloud.monitor.serviceImpl.util.PerfConstants;
import org.waddys.xcloud.monitor.serviceImpl.util.PerfUtils;
import org.waddys.xcloud.monitor.serviceImpl.util.ToolsUtils;
import org.waddys.xcloud.monitor.serviceImpl.util.VCenterManageUtils;

import com.vmware.vim25.DatastoreSummary;
import com.vmware.vim25.HostCpuInfo;
import com.vmware.vim25.HostHardwareInfo;
import com.vmware.vim25.HostListSummary;
import com.vmware.vim25.HostVirtualNic;
import com.vmware.vim25.ManagedEntityStatus;
import com.vmware.vim25.VirtualHardware;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.VirtualMachineSummary;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.VirtualMachine;

@Service("monitor-resourceServiceImpl")
public class ResourceServiceImpl implements ResourceServiceI {

    public static final String ENTITY_CLUSTER = "ClusterComputeResource";
    String accessble = "accessble";
    String unaccessble = "unaccessble";
	private VCenterManageUtils opUtil;
    @Qualifier("monitor-connection")
	@Autowired
	private Connection connection;
	
    @Qualifier("monitor-historyPerfAndAlertServiceImpl")
	@Autowired
	private HistoryPerfAndAlertServiceI alarmUtil;

	public VM getVmInfoInner(VirtualMachine virtualMachine){
		VM vm = new VM();
		if (virtualMachine == null) {
			return null;
		}
		vm.setName(virtualMachine.getName());
		vm.setId(virtualMachine.getMOR().getVal());

		Map<String, String> commonInfo = PerfUtils.getVmCommonPerf(virtualMachine);
		Map<String, String> otherInfo = PerfUtils.getVmOtherPerf(virtualMachine);

		// 获取IP信息
		vm.setIpAddr(virtualMachine.getGuest().ipAddress);
		// 通过虚拟机连接状态
		vm.setStatus(ToolsUtils.convertManageObjectStatusToString(virtualMachine.getSummary().getOverallStatus()));

		// 获取所在host信息 集群名称 数据中心名称
		String hostId = virtualMachine.getSummary().getRuntime().getHost().get_value();
		VirtualMachineConfigInfo  vmConfg=virtualMachine.getConfig();
		Map<String, String> map = this.getClusterAndDCNameByHost(hostId);
		vm.setHostId(hostId);
		vm.setHostName(map.get("hostName"));
		vm.setClusterName(map.get("clusterName"));
		vm.setDateCenterName(map.get("dataCenterName"));

		/* CPU个数 */
		VirtualHardware hardware = vmConfg.hardware;
		vm.setCpuNumber(hardware.getNumCPU() + "");
		
		
		HostSystem host = null;;
		try {
			host = this.getOpUtil().getHostSystemById(hostId);
		} catch (CloudViewPerfException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (host == null) {
			return null;
		}
		long hz = host.getHardware().getCpuInfo().getHz();//hwi.cpuInfo.hz;
		long e6 = 1000000;
		long hzd = (hz) / e6;
		long totalHz = hzd * vmConfg.getHardware().getNumCPU();
		
		//CPU使用量
		vm.setCpuMHZUsed(commonInfo.get(PerfConstants.CPU_USED_ID));
		
        if (null != String.valueOf(totalHz)) {
            vm.setCpuMHZTotal(String.valueOf(totalHz)); // MHz为单位
        } else {
            vm.setCpuMHZTotal("0");
        }
		
		/* CPU利用率 */
		double cpuUsage = Double.valueOf(commonInfo.get(PerfConstants.CPU_USED_ID))/totalHz;
		BigDecimal cpuUsageBd = new BigDecimal(cpuUsage);
        double cpuUsagePercent = cpuUsageBd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        vm.setCpuUsage(String.valueOf(cpuUsagePercent));
        
        
		/* 内存容量 (MB) */
		vm.setMemoryTotal(hardware.memoryMB + "");
		/* 内存利用率 */
		vm.setMemoryUsage(commonInfo.get(PerfConstants.MEM_USAGE_ID));
		vm.setMemoryUsed(commonInfo.get(PerfConstants.MEM_USED_ID));

        /* 磁盘容量 */
        vm.setDiskTotal(commonInfo.get(PerfConstants.DISK_TOTAL_ID));
        vm.setDiskUsed(commonInfo.get(PerfConstants.DISK_USED_ID));
        vm.setDiskUsage(commonInfo.get(PerfConstants.DISK_USAGE_ID));

		long diskIOSpeed = Long.valueOf(ToolsUtils.GetMapValue(otherInfo, PerfConstants.VDISK_READ_ID))
				+ Long.valueOf(ToolsUtils.GetMapValue(otherInfo, PerfConstants.VDISK_WRITE_ID));

		long diskIOPS = Long.valueOf(ToolsUtils.GetMapValue(otherInfo, PerfConstants.VDISK_IOPS_READ_ID))
				+ Long.valueOf(ToolsUtils.GetMapValue(otherInfo, PerfConstants.VDISK_IOPS_WRITE_ID));
		vm.setDiskIops(String.valueOf(diskIOPS));
		vm.setDiskIOSpeed(String.valueOf(diskIOSpeed));
		vm.setDiskReadSpeed(ToolsUtils.GetMapValue(otherInfo, PerfConstants.VDISK_READ_ID));
		vm.setDiskWriteSpeed(ToolsUtils.GetMapValue(otherInfo, PerfConstants.VDISK_WRITE_ID));

		vm.setNetworkReceiveSpeed(ToolsUtils.GetMapValue(otherInfo, PerfConstants.NET_RX_ID));
		vm.setNetworkSendSpeed(ToolsUtils.GetMapValue(otherInfo, PerfConstants.NET_TX_ID));
		vm.setNetworkTransmitSpeed(ToolsUtils.GetMapValue(otherInfo, PerfConstants.NET_IO_SPEED_ID));
		// this.returnConnection(conn);

		return vm;
	}
	
	@Override
	public VM getVmInfoEx(String vmId) {
		// TODO Auto-generated method stub
		VirtualMachine virtualMachine = null;
		try {
			virtualMachine = this.getOpUtil().getVirtualMachineById(vmId);
		} catch (CloudViewPerfException e) {
			e.printStackTrace();
		}
		return getVmInfoInner(virtualMachine);
	}
	
	@Override
	public VM getVmInfo(String name) {
		// TODO Auto-generated method stub
		VirtualMachine virtualMachine = null;
		try {
			virtualMachine = this.getOpUtil().getVirtualMachineByName(name);
		} catch (CloudViewPerfException e) {
			e.printStackTrace();
		}
		return getVmInfoInner(virtualMachine);
	}

	public Host getHostInfoInner(HostSystem hostSystem){
		if(hostSystem == null){
			return null;
		}
		Host host = new Host();
		host.setId(hostSystem.getMOR().getVal());
		host.setName(hostSystem.getName());
		// 网卡信息
		StringBuffer netSb = new StringBuffer();

		boolean isFirstIP = true;
		HostVirtualNic[] hostNetworks = hostSystem.getConfig().network.vnic;
		for (HostVirtualNic vnic : hostNetworks) {
			try {
				if (vnic != null && vnic.spec != null && vnic.spec.ip != null && vnic.spec.ip.ipAddress != null) {
					if (!isFirstIP) {
						netSb.append(",");
					}
					netSb.append(vnic.spec.ip.ipAddress);
					isFirstIP = false;
				}
			} catch (Exception e) {
			}
		}
		host.setIpAddr(netSb.toString());

		Map<String, String> commonInfo = PerfUtils.getHostCommonPerf(hostSystem);
        // Map<String, String> otherInfo =
        // PerfUtils.getHostOtherPerf(hostSystem);

        // CPU MHz
        HostHardwareInfo hostHardware = hostSystem.getHardware();
        HostCpuInfo cpuInfo = hostHardware.cpuInfo;
        short cpuCoreNum = cpuInfo.numCpuCores;
        host.setCpuNumber(cpuCoreNum + "");
        double cpuHz = cpuInfo.getHz() * Double.valueOf(cpuCoreNum);
        BigDecimal bgCpu = new BigDecimal(cpuHz / 1000);
        double cpuMHz = bgCpu.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        host.setCpuMHZTotal(cpuMHz + "");
        host.setCpuMHZUsed(commonInfo.get(PerfConstants.CPU_USED_ID));
        host.setCpuUsage(commonInfo.get(PerfConstants.CPU_USAGE_ID));

		// 内存 MB
		double memTotal = hostHardware.memorySize;
		BigDecimal bgMem = new BigDecimal(memTotal / 1024 / 1024 / 1024);
		double memTotalGB = bgMem.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		host.setMemoryTotal(memTotalGB + "");
		host.setMemoryUsed(commonInfo.get(PerfConstants.MEM_USED_ID));
		host.setMemoryUsage(commonInfo.get(PerfConstants.MEM_USAGE_ID));

		// 磁盘 TB
		double diskFreeTotal = 0;
		double diskCapacityTotal = 0L;

        try {
            Datastore[] dss = hostSystem.getDatastores();
            host.setStoreNum(dss.length);
            for (Datastore ds : dss) {
                DatastoreSummary summary = ds.getSummary();
                double free = summary.freeSpace;
                double capacity = summary.capacity;
                diskFreeTotal += free;
                diskCapacityTotal += capacity;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

		BigDecimal bgDiskTotal = new BigDecimal(diskCapacityTotal / 1024 / 1024 / 1024 / 1024);
		BigDecimal bgDiskUsed = new BigDecimal((diskCapacityTotal - diskFreeTotal) / 1024 / 1024 / 1024);
		double diskTotalTB = bgDiskTotal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        double diskUsedGB = bgDiskUsed.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		host.setDiskTotal(diskTotalTB + "");
        host.setDiskUsed(diskUsedGB + "");
		// 磁盘速率
        // host.setDiskReadSpeed(ToolsUtils.GetMapValue(otherInfo,
        // PerfConstants.DISK_READ_ID));
        // host.setDiskWriteSpeed(ToolsUtils.GetMapValue(otherInfo,
        // PerfConstants.DISK_WRITE_ID));
        // long diskIOSpeed = Long.valueOf(ToolsUtils.GetMapValue(otherInfo,
        // PerfConstants.DISK_READ_ID))
        // + Long.valueOf(ToolsUtils.GetMapValue(otherInfo,
        // PerfConstants.DISK_WRITE_ID));
        // host.setDiskIOSpeed(String.valueOf(diskIOSpeed));
        // long diskIOPS = Long.valueOf(ToolsUtils.GetMapValue(otherInfo,
        // PerfConstants.DISK_IOPS_READ_ID))
        // + Long.valueOf(ToolsUtils.GetMapValue(otherInfo,
        // PerfConstants.DISK_IOPS_WRITE_ID));
        // host.setDiskIops(String.valueOf(diskIOPS));
        // // 网络速率
        // host.setNetworkReceiveSpeed(ToolsUtils.GetMapValue(otherInfo,
        // PerfConstants.NET_RX_ID));
        // host.setNetworkSendSpeed(ToolsUtils.GetMapValue(otherInfo,
        // PerfConstants.NET_TX_ID));
        // host.setNetworkTransmitSpeed(ToolsUtils.GetMapValue(otherInfo,
        // PerfConstants.NET_IO_SPEED_ID));

		// 所属信息

        ManagedEntity me = hostSystem.getParent();
        if (me instanceof ClusterComputeResource) {
            host.setClusterName(hostSystem.getParent().getName());
        } else {
            host.setClusterName("无");
        }
		host.setDataCenterName(hostSystem.getParent().getParent().getParent().getName());

		// 状态信息
		host.setStatus(ToolsUtils.convertManageObjectStatusToString(hostSystem.getSummary().getOverallStatus()));

		// 虚拟机列表
		try {
			VirtualMachine[] vms = hostSystem.getVms();
            // host.setVmNumber(vms.length);
			List<VM> vmList = new ArrayList<VM>();
			if (null != vms && vms.length > 0) {
                for (VirtualMachine virtualMachine : vms) {
                    VM vm = this.getVMBasicInfo(virtualMachine, hostSystem);
                    // VM vm = this.getVmInfo(virtualMachine.getName());
                    vmList.add(vm);
                }
			}
			host.setVmList(vmList);
            Map<String, Integer> vmMap = this.getVmStatusInfo(vms);
            host.setVmNum(vms.length);
            host.setVmAccessibleNum(vmMap.get(accessble));
            host.setVmUnaccessibleNum(vmMap.get(unaccessble));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        try {
            Datastore[] dss = hostSystem.getDatastores();
            Map<String, Integer> dssMap = this.getStoreStatusInfo(dss);
            host.setStoreNum(dss.length);
            host.setStoreAccessibleNum(dssMap.get(accessble));
            host.setStoreUnaccessibleNum(dssMap.get(unaccessble));
        } catch (Exception e) {
            e.printStackTrace();
        }

		// this.returnConnection(conn);
		return host;
	}
	
	@Override
	public Host getHostInfoEx(String hostId) {
		// TODO Auto-generated method stub
		HostSystem hostSystem = null;
		try {
			hostSystem = this.getOpUtil().getHostSystemById(hostId);
		} catch (CloudViewPerfException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return getHostInfoInner(hostSystem);
	}
	
	@Override
	public Host getHostInfo(String name) {
		// TODO Auto-generated method stub

		HostSystem hostSystem = null;
		try {
			hostSystem = this.getOpUtil().getHostSystemByName(name);
		} catch (CloudViewPerfException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return getHostInfoInner(hostSystem);
	}

	public Map<String, String> getClusterAndDCNameByHost(String id) {

		Map<String, String> map = new HashMap<String, String>();
		HostSystem hostSystem;
		try {
			hostSystem = this.getOpUtil().getHostSystemById(id);
			map.put("hostName", hostSystem.getName());
            ManagedEntity me = hostSystem.getParent();
            if (me instanceof ClusterComputeResource) {
                map.put("clusterName", hostSystem.getParent().getName());
            } else {
                map.put("clusterName", "无");
            }

			map.put("dataCenterName", hostSystem.getParent().getParent().getParent().getName());
		} catch (CloudViewPerfException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return map;
	}

    public Host getHostBasicInfo(HostSystem systems) {

        Host host = new Host();

        host.setName(systems.getName());

        HostListSummary summary = systems.getSummary();
        HostHardwareInfo hwi = systems.getHardware();
        long hz = hwi.cpuInfo.hz;
        long e6 = 1000;
        long hzd = (hz) / e6;

        long totalHz = hzd * hwi.cpuInfo.numCpuCores;
        long cpuUsage = summary.quickStats.overallCpuUsage;
        double totalMem = (double) hwi.getMemorySize() / 1024 / 1024 / 1024;
        long memUsage = summary.quickStats.overallMemoryUsage;

        host.setCpuMHZTotal(String.valueOf(totalHz));
        host.setCpuMHZUsed(String.valueOf(cpuUsage));
        double cpuUsagePercent_ori = ((double) cpuUsage / totalHz * 1000) * 100;
        BigDecimal cpuUsageBd = new BigDecimal(cpuUsagePercent_ori);
        double cpuUsagePercent = cpuUsageBd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        host.setCpuUsage(String.valueOf(cpuUsagePercent));

        BigDecimal totalMemBd = new BigDecimal(totalMem);
        totalMem = totalMemBd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        host.setMemoryTotal(String.valueOf(totalMem));
        host.setMemoryUsed(String.valueOf(memUsage));

        double memUsagePercent_ori = (memUsage / totalMem / 1000) * 100;
        BigDecimal memUsageBd = new BigDecimal(memUsagePercent_ori);
        double memUsagePercent = memUsageBd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        host.setMemoryUsage(String.valueOf(memUsagePercent));

        return PerfUtils.getHostBasicPerf(host, systems);

    }

    // public Host getHostBasicInfo(HostSystem hostSystem, Datastore datastore)
    // {
    //
    // HostSystem[] hostSystems = datastore.getHostsystem();
    // for(HostSystem hostSystem:)
    // return null;
    // }

    @Override
    public Host getHostBasicInfo(String name) {

        HostSystem hostSystem = null;
        try {
            hostSystem = this.getOpUtil().getHostSystemByName(name);
        } catch (CloudViewPerfException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Host host = new Host();
        host.setId(hostSystem.getMOR().getVal());
        host.setName(hostSystem.getName());

        Map<String, String> commonInfo = PerfUtils.getHostCommonPerf(hostSystem);
        Map<String, String> otherInfo = PerfUtils.getHostOtherPerf(hostSystem);

        // CPU MHz
        HostHardwareInfo hostHardware = hostSystem.getHardware();
        HostCpuInfo cpuInfo = hostHardware.cpuInfo;
        short cpuCoreNum = cpuInfo.numCpuCores;
        host.setCpuNumber(cpuCoreNum + "");
        double cpuHz = cpuInfo.getHz() * Double.valueOf(cpuCoreNum);
        BigDecimal bgCpu = new BigDecimal(cpuHz / 1000);
        double cpuMHz = bgCpu.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        host.setCpuMHZTotal(cpuMHz + "");
        host.setCpuMHZUsed(commonInfo.get(PerfConstants.CPU_USED_ID));
        host.setCpuUsage(commonInfo.get(PerfConstants.CPU_USAGE_ID));

        // 内存 MB
        double memTotal = hostHardware.memorySize;
        BigDecimal bgMem = new BigDecimal(memTotal / 1024 / 1024 / 1024);
        double memTotalGB = bgMem.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        host.setMemoryTotal(memTotalGB + "");
        host.setMemoryUsed(commonInfo.get(PerfConstants.MEM_USED_ID));
        host.setMemoryUsage(commonInfo.get(PerfConstants.MEM_USAGE_ID));

        // 磁盘 TB
        double diskFreeTotal = 0;
        double diskCapacityTotal = 0L;

        try {
            Datastore[] dss = hostSystem.getDatastores();
            host.setStoreNum(dss.length);
            for (Datastore ds : dss) {
                DatastoreSummary summary = ds.getSummary();
                double free = summary.freeSpace;
                double capacity = summary.capacity;
                diskFreeTotal += free;
                diskCapacityTotal += capacity;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        BigDecimal bgDiskTotal = new BigDecimal(diskCapacityTotal / 1024 / 1024 / 1024 / 1024);
        BigDecimal bgDiskUsed = new BigDecimal((diskCapacityTotal - diskFreeTotal) / 1024 / 1024 / 1024);
        double diskTotalTB = bgDiskTotal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        double diskUsedGB = bgDiskUsed.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        host.setDiskTotal(diskTotalTB + "");
        host.setDiskUsed(diskUsedGB + "");
        
        // // 网络速率
        host.setNetworkReceiveSpeed(ToolsUtils.GetMapValue(otherInfo, PerfConstants.NET_RX_ID));
        host.setNetworkSendSpeed(ToolsUtils.GetMapValue(otherInfo, PerfConstants.NET_TX_ID));


        // 所属信息
        // host.setClusterName(hostSystem.getParent().getName());
        // host.setDataCenterName(hostSystem.getParent().getParent().getParent().getName());

        // 状态信息
        // host.setStatus(ToolsUtils.convertManageObjectStatusToString(hostSystem.getSummary().getOverallStatus()));

        // 虚拟机列表
//        try {
//            VirtualMachine[] vms = hostSystem.getVms();
//            host.setVmNumber(vms.length);
//            List<VM> vmList = new ArrayList<VM>();
//            if (null != vms && vms.length > 0) {
//                for (VirtualMachine virtualMachine : vms) {
//                    VM vm = this.getVMBasicInfo(virtualMachine, hostSystem);
//                    // VM vm = this.getVmInfo(virtualMachine.getName());
//                    vmList.add(vm);
//                }
//            }
//            host.setVmList(vmList);
//            Map<String, String> vmMap = this.getVmStatusInfo(vms);
//            host.setVmNum(vms.length + "");
//            host.setVmAccessibleNum(vmMap.get(accessble));
//            host.setVmUnaccessibleNum(vmMap.get(unaccessble));
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

//        try {
//            Datastore[] dss = hostSystem.getDatastores();
//            Map<String, String> dssMap = this.getStoreStatusInfo(dss);
//            host.setStoreNum(dss.length + "");
//            host.setStoreAccessibleNum(dssMap.get(accessble));
//            host.setStoreUnaccessibleNum(dssMap.get(unaccessble));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // this.returnConnection(conn);
        return host;
    }

    public Host getHostBasicInfo(HostSystem hostSystem, Datastore datastore) {

        Host host = new Host();
        if (hostSystem == null) {
            return null;
        }

        host.setName(hostSystem.getName());
        Map<String, String> commonInfo = PerfUtils.getHostCommonPerf(hostSystem);
        Map<String, String> basicInfo = PerfUtils.getHostOtherPerf(hostSystem);

        double diskTotal = Double.valueOf(commonInfo.get(PerfConstants.DISK_TOTAL_ID));
        double usedSpace = Double.valueOf(commonInfo.get(PerfConstants.DISK_USED_ID));
        double diskUsagePercent = 0.0;

        if (Math.abs(diskTotal - 0) > 0.0001) {
            BigDecimal diskUsageBd = new BigDecimal((usedSpace / diskTotal) * 100);
            diskUsagePercent = diskUsageBd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        host.setDiskUsage(String.valueOf(diskUsagePercent));
        long diskIOPS = Long.valueOf(ToolsUtils.GetMapValue(basicInfo, PerfConstants.DISK_IOPS_READ_ID))
                + Long.valueOf(ToolsUtils.GetMapValue(basicInfo, PerfConstants.DISK_IOPS_WRITE_ID));
        host.setDiskIops(String.valueOf(diskIOPS));

        return host;
    }

    public VM getVMBasicInfo(VirtualMachine virtualMachine, HostSystem host) {

        VM vm = new VM();
        if (virtualMachine == null) {
            return null;
        }
        vm.setName(virtualMachine.getName());

        long hz = host.getHardware().getCpuInfo().getHz();
        long e6 = 1000000;
        long hzd = (hz) / e6;
        long totalHz =1L;
        if(virtualMachine.getConfig()==null){
		
		}else{
			 totalHz = hzd * virtualMachine.getConfig().getHardware().getNumCPU();
		}
        VirtualMachineSummary summary = virtualMachine.getSummary();
        double cpuUsed = summary.quickStats.overallCpuUsage;
        DecimalFormat df = new DecimalFormat("#0.00");
        vm.setCpuUsage(df.format(cpuUsed *100/ totalHz));
        Map<String, String> basicInfo = PerfUtils.getVMBasicPerf(virtualMachine);
        vm.setMemoryUsage(basicInfo.get(PerfConstants.MEM_USAGE_ID));

        long diskIOSpeed = Long.valueOf(ToolsUtils.GetMapValue(basicInfo, PerfConstants.VDISK_READ_ID))
                + Long.valueOf(ToolsUtils.GetMapValue(basicInfo, PerfConstants.VDISK_WRITE_ID));
        vm.setDiskIOSpeed(String.valueOf(diskIOSpeed));

        vm.setNetworkTransmitSpeed(ToolsUtils.GetMapValue(basicInfo, PerfConstants.NET_IO_SPEED_ID));

        return vm;
    }

    public VM getVMBasicInfo(VirtualMachine virtualMachine, Datastore host) {

        VM vm = new VM();
        if (virtualMachine == null) {
            return null;
        }
        vm.setName(virtualMachine.getName());
        Map<String, String> commonInfo = PerfUtils.getVmCommonPerf(virtualMachine);
        Map<String, String> basicInfo = PerfUtils.getVMBasicPerf(virtualMachine);

        double diskTotal = Double.valueOf(commonInfo.get(PerfConstants.DISK_TOTAL_ID));
        double usedSpace = Double.valueOf(commonInfo.get(PerfConstants.DISK_USED_ID));
        double diskUsagePercent = 0.0;

        if (Math.abs(diskTotal - 0) > 0.0001) {
            BigDecimal diskUsageBd = new BigDecimal((usedSpace / diskTotal) * 100);
            diskUsagePercent = diskUsageBd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        vm.setDiskUsage(String.valueOf(diskUsagePercent));

        long diskIOPS = Long.valueOf(ToolsUtils.GetMapValue(basicInfo, PerfConstants.DISK_IOPS_READ_ID))
                + Long.valueOf(ToolsUtils.GetMapValue(basicInfo, PerfConstants.DISK_IOPS_WRITE_ID));
        vm.setDiskIops(String.valueOf(diskIOPS));

        return vm;
    }

	@Override
    public Cluster getClusterInfo(String name) {

        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss:SSS");
        String startTime = time.format(new java.util.Date());
        //System.out.println("获取群集" + name + "开始:" + startTime);
        ClusterComputeResource clusterResource = null;
        try {
            clusterResource = this.getOpUtil().getClusterComputeResourceByName(name);
        } catch (CloudViewPerfException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        Cluster cluster = new Cluster();
        
        if(clusterResource==null){
        	
        	return null;
        }
        cluster.setName(clusterResource.getName());
        cluster.setId(clusterResource.getMOR().getVal());

        // CPU 主机状态 虚拟机状态
        HostSystem[] hosts = clusterResource.getHosts();
        List<VirtualMachine> vms = new ArrayList<VirtualMachine>();
        List<Host> hostList = new ArrayList<Host>();
        List<VM> vmList = new ArrayList<VM>();
        int hostNum = hosts.length;
        int hostAccessibleNum = 0;
        int hostUnaccessibleNum = 0;
        int vmAccessibleNum = 0;
        int vmUnaccessibleNum = 0;
        double cpuHz = 0;
        double cpuUsed = 0;
        double memTotal = 0;
        double memUsed = 0;
        if (null != hosts && hosts.length > 0) {
            for (HostSystem host : hosts) {

                if (ManagedEntityStatus.green.equals(host.getSummary().getOverallStatus())) {
                    hostAccessibleNum += 1;
                } else {
                    hostUnaccessibleNum += 1;
                }

                // 获取主机列表单项
                Host hostItem = this.getHostBasicInfo(host);

                memTotal += Double.parseDouble(hostItem.getMemoryTotal());
                memUsed += Double.parseDouble(hostItem.getMemoryUsed());

                cpuHz += Double.parseDouble(hostItem.getCpuMHZTotal());
                cpuUsed += Double.parseDouble(hostItem.getCpuMHZUsed());


                hostList.add(hostItem);
                // if (null != hostItem.getVmList()) {
                // vmList.addAll(hostItem.getVmList());
                // }

                try {
                    VirtualMachine[] hostvms = host.getVms();
                    if (null != hostvms && hostvms.length > 0) {
                        for (VirtualMachine vm : hostvms) {
                            if (ManagedEntityStatus.green.equals(vm.getSummary().getOverallStatus())) {
                                vmAccessibleNum += 1;
                            } else {
                                vmUnaccessibleNum += 1;
                            }
                            vms.add(vm);

                            // 获取虚拟机列表单项
                            VM vmItem = this.getVMBasicInfo(vm, host);
                            vmList.add(vmItem);
                        }
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
        cluster.setVmList(vmList);
        cluster.setHostList(hostList);
        int vmNum = vms.size();
        // 整体CPU信息
        BigDecimal bgCpuG = new BigDecimal(cpuHz / 1000 / 1000);
        double cpuGHz = bgCpuG.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        BigDecimal bgCpuUsedG = new BigDecimal(cpuUsed / 1000);
        double cpuUsedGHz = bgCpuUsedG.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        DecimalFormat df = new DecimalFormat("#0.00");
        DecimalFormat df1 = new DecimalFormat("#0.0000");
        if (0 == cpuHz) {
            cluster.setCpuUsage("0");
        } else {
            cluster.setCpuUsage(df1.format(cpuUsedGHz / cpuGHz) + "");
        }


        cluster.setCpuGHz(cpuGHz);
        cluster.setCpuUsedGHz(Double.parseDouble(df.format(cpuUsedGHz)));


        // 整体内存信息
        BigDecimal bgMem = new BigDecimal(memTotal);
        double memTotalGB = bgMem.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        BigDecimal bgMemUsed = new BigDecimal(memUsed / 1000);
        double memUsedGB = bgMemUsed.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        cluster.setMemoryTotal(memTotalGB + "GB");
        cluster.setMemoryUsed(memUsedGB + "");
        cluster.setMemGB(memTotalGB);
        cluster.setMemUsedGB(memUsedGB);
        if (0 == memTotalGB) {
            cluster.setMemoryUsage("0");
        } else {
            cluster.setMemoryUsage(df1.format(memUsedGB / memTotalGB) + "");
        }

        // Store
        Datastore[] dss = clusterResource.getDatastores();
        double diskFreeTotal = 0;
        double diskCapacityTotal = 0L;
        int storeNum = dss.length;
        int storeAccessibleNum = 0;
        int storeUnaccessibleNum = 0;
        if (null != dss && dss.length > 0) {
            for (Datastore ds : dss) {
                DatastoreSummary summary = ds.getSummary();
                if (summary.accessible) {
                    storeAccessibleNum += 1;
                } else {
                    storeUnaccessibleNum += 1;
                }
                double free = summary.freeSpace;
                double capacity = summary.capacity;
                diskFreeTotal += free;
                diskCapacityTotal += capacity;
            }
        }

        // 存储整体信息
        BigDecimal bgDiskTotal = new BigDecimal(diskCapacityTotal / 1024 / 1024 / 1024 / 1024);
        BigDecimal bgDiskUsed = new BigDecimal((diskCapacityTotal - diskFreeTotal) / 1024 / 1024 / 1024 / 1024);
        cluster.setDiskTotal(bgDiskTotal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "");
        cluster.setDiskUsed(bgDiskUsed.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "");
        cluster.setStoreTB(bgDiskTotal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        cluster.setStoreUsedTB(bgDiskUsed.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        if (0 == diskCapacityTotal) {
            cluster.setDiskUsage("0");
        } else {
            cluster.setDiskUsage(df1.format((diskCapacityTotal - diskFreeTotal) / diskCapacityTotal) + "");
        }

        // 存储状态数量信息
        cluster.setStoreNum(storeNum);
        cluster.setStoreAccessibleNum(storeAccessibleNum);
        cluster.setStoreUnaccessibleNum(storeUnaccessibleNum);
        // 主机状态数量信息
        cluster.setHostNumber(hostNum);
        cluster.setHostAccessibleNum(hostAccessibleNum);
        cluster.setHostUnaccessibleNum(hostUnaccessibleNum);
        // 虚拟机状态数量信息
        cluster.setVmNum(vmNum);
        cluster.setVmAccessibleNum(vmAccessibleNum);
        cluster.setVmUnaccessibleNum(vmUnaccessibleNum);

        startTime = time.format(new java.util.Date());
        //System.out.println("获取群集" + cluster.getName() + "完成:" + startTime);

        return cluster;
    }

    @Override
    public List<Cluster> getSimpleClusterList() {
        // TODO Auto-generated method stub
        List<Cluster> clusterList = new ArrayList<Cluster>();

        try {
            List<ClusterComputeResource> retList = this.getOpUtil().getAllClusterComputeResources();
            if (null != retList && retList.size() > 0) {
                for (ClusterComputeResource ret : retList) {
                    Cluster cluster = new Cluster();
                    cluster.setName(ret.getName());
                    clusterList.add(cluster);
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // this.returnConnection(conn);
        return clusterList;
    }

	@Override
	public List<Cluster> getClustersList() {
		// TODO Auto-generated method stub
		List<Cluster> clusterList = new ArrayList<Cluster>();

		try {
			List<ClusterComputeResource> retList = this.getOpUtil().getAllClusterComputeResources();
			List<String> clusterNameList =new ArrayList<String>();
			for (ClusterComputeResource ret : retList) {
				clusterNameList.add(ret.getName());
			}
			
			if (null != retList && retList.size() > 0) {
				for (String name : clusterNameList) {
                    Cluster cluster = this.getClusterInfo(name);
					clusterList.add(cluster);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// this.returnConnection(conn);
		return clusterList;
	}

	@Override
	public DataCenter getDataCenterInfo() {
		// 目前不需要使用

		return null;
	}

	public double getAverage(List<Double> list) {
        if (null != list && list.size() > 0) {
            double sum = 0;
            for (int i = 0; i < list.size(); i++) {
                sum += list.get(i).doubleValue();
            }
            BigDecimal bgAvg = new BigDecimal(sum / list.size() * 100);
            double avg = bgAvg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            return avg;
        } else {
            return 0;
        }

	}

	/**
	 * @return the opUtil
	 */
	public VCenterManageUtils getOpUtil() {
	//	if (this.opUtil == null)  //别用单例模式，si对应的root和connection会失效，每次从conection获取最新的
		this.opUtil = new VCenterManageUtils(this.connection);
		return this.opUtil ;
	}

    public Map<String, Integer> getHostStatusInfo(HostSystem[] hosts) {

        Map<String, Integer> map = new HashMap<String, Integer>();

        int hostAccessibleNum = 0;
        int hostUnaccessibleNum = 0;
        if (null != hosts && hosts.length > 0) {
            for (HostSystem host : hosts) {
                if (ManagedEntityStatus.green.equals(host.getSummary().getOverallStatus())) {
                    hostAccessibleNum += 1;
                } else {
                    hostUnaccessibleNum += 1;
                }
            }
        }
        map.put(accessble, hostAccessibleNum);
        map.put(unaccessble, hostUnaccessibleNum);
        return map;
    }

    public Map<String, Integer> getVmStatusInfo(VirtualMachine[] vms) {

        Map<String, Integer> map = new HashMap<String, Integer>();

        int vmAccessibleNum = 0;
        int vmUnaccessibleNum = 0;
        if (null != vms && vms.length > 0) {
            for (VirtualMachine vm : vms) {
                if (ManagedEntityStatus.green.equals(vm.getSummary().getOverallStatus())) {
                    vmAccessibleNum += 1;
                } else {
                    vmUnaccessibleNum += 1;
                }
            }
        }
        map.put(accessble, vmAccessibleNum);
        map.put(unaccessble, vmUnaccessibleNum);
        return map;
    }

    public Map<String, Integer> getStoreStatusInfo(Datastore[] dss) {

        Map<String, Integer> map = new HashMap<String, Integer>();
        int storeAccessibleNum = 0;
        int storeUnaccessibleNum = 0;
        if (null != dss && dss.length > 0) {
            for (Datastore ds : dss) {
                DatastoreSummary summary = ds.getSummary();
                if (summary.accessible) {
                    storeAccessibleNum += 1;
                } else {
                    storeUnaccessibleNum += 1;
                }
            }
        }
        map.put(accessble, storeAccessibleNum);
        map.put(unaccessble, storeUnaccessibleNum);
        return map;
    }

	@Override
	public List<AlarmEntity> getClusterAlarm(Cluster cu) {
		// TODO Auto-generated method stub
		ClusterComputeResource clusterResource = null;
		try {
			clusterResource = this.getOpUtil().getClusterComputeResourceByName(cu.getName());
		} catch (CloudViewPerfException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<AlarmEntity> retAlarms = new ArrayList<AlarmEntity>();
		if(clusterResource == null){
			//System.out.println("找不到相应的集群:"+cu.getName());
			return retAlarms;
		}
		return alarmUtil.getTriggeredAlarms(clusterResource);				
	}

    public List<AlarmEntity> getAllAlarms(Datastore ds) {
        List<AlarmEntity> retAlarms = new ArrayList<AlarmEntity>();
        if (ds == null) {
            // System.out.println("找不到相应的集群:"+cu.getName());
            return retAlarms;
        }
        return alarmUtil.getTriggeredAlarms(ds.getParent());
    }

    @Override
    public Cluster getAllDataStoreInfo() {
        List<Datastore> dss = null;
        try {
            dss = this.getOpUtil().getAllDatastore();
        } catch (CloudViewPerfException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        Cluster cluster = new Cluster();

        if (dss == null) {

            return null;
        }

        List<AlarmEntity> alarms = this.getAllAlarms(dss.get(0));
        cluster.setAlarmList(alarms);

        // Store
        double diskFreeTotal = 0;
        double diskCapacityTotal = 0L;
        int storeNum = dss.size();
        int storeAccessibleNum = 0;
        int storeUnaccessibleNum = 0;
        if (null != dss && dss.size() > 0) {
            for (Datastore ds : dss) {
                DatastoreSummary summary = ds.getSummary();
                if (summary.accessible) {
                    storeAccessibleNum += 1;
                } else {
                    storeUnaccessibleNum += 1;
                }
                double free = summary.freeSpace;
                double capacity = summary.capacity;
                diskFreeTotal += free;
                diskCapacityTotal += capacity;

            }
        }
        DecimalFormat df = new DecimalFormat("#0.00");

        // 存储整体信息
        BigDecimal bgDiskTotal = new BigDecimal(diskCapacityTotal / 1024 / 1024 / 1024 / 1024);
        BigDecimal bgDiskUsed = new BigDecimal((diskCapacityTotal - diskFreeTotal) / 1024 / 1024 / 1024 / 1024);
        cluster.setDiskTotal(bgDiskTotal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "");
        cluster.setDiskUsed(bgDiskUsed.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "");
        cluster.setStoreTB(bgDiskTotal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        cluster.setStoreUsedTB(bgDiskUsed.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        if (0 == diskCapacityTotal) {
            cluster.setDiskUsage("0");
        } else {
            cluster.setDiskUsage(df.format((diskCapacityTotal - diskFreeTotal) / diskCapacityTotal) + "");
        }

        // 存储状态数量信息
        cluster.setStoreNum(storeNum);
        cluster.setStoreAccessibleNum(storeAccessibleNum);
        cluster.setStoreUnaccessibleNum(storeUnaccessibleNum);

        return cluster;
    }
	


}
