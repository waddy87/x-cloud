package org.waddys.xcloud.monitor.serviceImpl.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.waddys.xcloud.monitor.bo.MetricValue;
import org.waddys.xcloud.monitor.bo.VMBo;
import org.waddys.xcloud.monitor.service.exception.CloudViewPerfException;
import org.waddys.xcloud.monitor.service.service.VMService;
import org.waddys.xcloud.monitor.serviceImpl.common.SystemResourceType;
import org.waddys.xcloud.monitor.serviceImpl.entity.VM;
import org.waddys.xcloud.monitor.serviceImpl.service.HistoryPerfAndAlertServiceI;
import org.waddys.xcloud.monitor.serviceImpl.util.Connection;
import org.waddys.xcloud.monitor.serviceImpl.util.HistoryData;
import org.waddys.xcloud.monitor.serviceImpl.util.PerfConstants;
import org.waddys.xcloud.monitor.serviceImpl.util.PerfUtils;
import org.waddys.xcloud.monitor.serviceImpl.util.ToolsUtils;
import org.waddys.xcloud.monitor.serviceImpl.util.VCenterManageUtils;

import com.vmware.vim25.HostHardwareInfo;
import com.vmware.vim25.HostSystemConnectionState;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.VirtualMachineStorageSummary;
import com.vmware.vim25.VirtualMachineSummary;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.VirtualMachine;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service("monitor-vmServiceImpl")
public class VMServiceImpl implements VMService {
    private static final Logger logger = LoggerFactory.getLogger(VMServiceImpl.class);

    @Qualifier("monitor-connection")
    @Autowired
    private Connection connection;

    @Qualifier("monitor-historyPerfAndAlertServiceImpl")
    @Autowired
    private HistoryPerfAndAlertServiceI historyPerfAndAlertService;

    @Qualifier("monitor-history")
    @Autowired
    private HistoryData historyData;

    @Qualifier("monitor-toolsutils")
    @Autowired
    private ToolsUtils toolsUtils;

    private VCenterManageUtils opUtil;

    @Override
    public List<VMBo> getVMsByIds(List<String> vmIds) {
        List<VirtualMachine> virtualMachines = new ArrayList<VirtualMachine>();
        List<VMBo> vmBos = new ArrayList<VMBo>();
        VirtualMachine v = null;
        if (vmIds == null) {
            return vmBos;
        }
        for (int i = 0; i < vmIds.size(); i++) {
            try {
                v = this.getOpUtil().getVirtualMachineById(vmIds.get(i));
            } catch (CloudViewPerfException e) {
                logger.error(e.getMessage(), e);
                e.printStackTrace();
            }
            virtualMachines.add(v);
        }

        if (virtualMachines == null || virtualMachines.size() == 0) {
            return vmBos;
        }
        for (int i = 0; i < virtualMachines.size(); i++) {
            VirtualMachine virtualMachine = virtualMachines.get(i);
            VM vm = ConvertVirtualMachineToVM(virtualMachine);
            if (vm != null) {
                VMBo vmBo = new VMBo();
                try {
                    toolsUtils.convert(vm, vmBo);
                    vmBos.add(vmBo);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("----entity:" + vm + "转bo:" + vmBo + "失败！");
                }
            } else {
                vmBos.add(null);
            }
        }

        return vmBos;
    }

    @Override
    public VMBo getVMById(String vmId) {
        VirtualMachine virtualMachine = null;
        VMBo vmBo = null;
        try {
            virtualMachine = this.getOpUtil().getVirtualMachineById(vmId);
        } catch (CloudViewPerfException e) {
            logger.error("----根据虚拟机id：" + vmId + " 获取虚拟机失败！");
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        if (virtualMachine == null) {
            return vmBo;
        } else {
            VM vm = ConvertVirtualMachineToVM(virtualMachine);
            if (vm == null) {
                return vmBo;
            }
            try {
                vm.setTriggeredAlarm(historyPerfAndAlertService.getTriggeredAlarms(virtualMachine));
                vmBo = new VMBo();
                toolsUtils.convert(vm, vmBo);
            } catch (Exception e) {
                logger.error("----entity:" + vm + "转bo:" + vmBo + "失败！");
            }
        }
        return vmBo;
    }


    @Override
    public List<VMBo> getAllVMs() {
        List<VirtualMachine> virtualMachines = new ArrayList<VirtualMachine>();
        List<VMBo> vmBos = new ArrayList<VMBo>();
        try {
            virtualMachines = this.getOpUtil().getAllVirtualMachines();
        } catch (CloudViewPerfException e) {
            e.printStackTrace();
        }

        if (virtualMachines == null || virtualMachines.size() == 0) {
            return vmBos;
        }
        for (int i = 0; i < virtualMachines.size(); i++) {
            VirtualMachine virtualMachine = virtualMachines.get(i);
            VM vm = ConvertVirtualMachineToVM(virtualMachine);
            if (vm != null) {
                VMBo vmBo = new VMBo();
                try {
                    toolsUtils.convert(vm, vmBo);
                    vmBos.add(vmBo);
                } catch (Exception e) {
                    logger.error("----entity:" + vm + "转bo:" + vmBo + "失败！");
                }
            }
        }
        return vmBos;
    }

    /**
     * 功能: 将VirtualMachine对象转为对应的VM对象
     *
     * @param virtualMachine
     * @return
     */
    public VM ConvertVirtualMachineToVM(VirtualMachine virtualMachine) {
        
		try {
			
			VM vm = null;
	        if (virtualMachine == null) {
	            return vm;
	        }
	        VirtualMachineSummary summary = null;
	        VirtualMachineConfigInfo vmConfigInfo = null;
	        try {
	            summary = virtualMachine.getSummary();
	            vmConfigInfo = virtualMachine.getConfig();

	        } catch (Exception e) {

	            return vm;
	        }

	        VirtualMachineStorageSummary vmss = summary.storage;

	        
	        // 只要任意属性为空，则表示虚拟机正在创建或者删除中
	        if (summary == null || summary.quickStats == null || vmConfigInfo == null || vmConfigInfo.getHardware() == null
	                || vmss == null) {
	            return vm;
	        }
	        // 过滤掉模板
	        if(virtualMachine!=null && virtualMachine.getConfig()!=null){
	        	 if (virtualMachine.getConfig().isTemplate()) {
	                 return vm;
	             }
	        }
	       
	        vm = new VM();
	        vm.setId(virtualMachine.getMOR().get_value());
	        vm.setName(virtualMachine.getName());
	        
	        // 系统类型
	        if(virtualMachine.getSummary()!=null &&  virtualMachine.getSummary().getConfig()!=null){
	        	   String os = virtualMachine.getSummary().getConfig().guestFullName;
	               vm.setOs(os);
	        }
	     
	        // IP地址
	        // String ipAddress = summary.getGuest().getIpAddress();
	        Map<String, String> commonInfo;
			Map<String, String> otherInfo;
			if(virtualMachine.getGuest()!=null){
				  String ipAddress = virtualMachine.getGuest().getIpAddress();
			      vm.setIpAddr(ipAddress);
			}
     
			
			vm.setCpuNumber(Integer.toString(vmConfigInfo.getHardware().getNumCPU()));

			//虚拟机关联主机  
			ManagedObjectReference mor = new ManagedObjectReference();  
			mor.setType("HostSystem");  
			
			long hzd=0L;
			try{
			    if(virtualMachine.getRuntime()!=null && virtualMachine.getRuntime().getHost()!=null){
			    	 mor.setVal(virtualMachine.getRuntime().getHost().getVal());  
			         HostSystem host = new HostSystem(connection.getSi().getServerConnection(), mor);

			         HostHardwareInfo hwi = host.getHardware();
			         long hz = hwi.cpuInfo.hz;
			         long e6 = 1000000;
			         hzd = (hz) / e6;
			         vm.setHostId(host.getMOR().get_value());
			         vm.setHostName(host.getName());

			         if (host.getParent() instanceof ClusterComputeResource) {
			             vm.setClusterName(host.getParent().getName());
			         }

			         if(host!=null && host.getParent()!=null ){
			        	 if(host.getParent().getParent()!=null && host.getParent().getParent().getParent()!=null){
			        		 if(host.getParent().getParent().getParent().getName()!=null){
			        			 vm.setDateCenterName(host.getParent().getParent().getParent().getName());
			        		 }
			        		 
			        	 }
			        	  ManagedEntity me = host.getParent();
			              if (me instanceof ClusterComputeResource) {
			                  vm.setBStandalone(false);
			              } else {
			                  vm.setBStandalone(true);
			              }
			              if (host.getRuntime().getConnectionState() == HostSystemConnectionState.disconnected) {
			                  vm.setConnectionStatus(PerfConstants.HOST_CONN_STATUS_DISCONNECTED);
			                  PerfUtils.InitialVM(vm);
			                  return vm;
			              }
			         }
			       
			    }
			}catch(Exception e){
				logger.error("获取虚拟机关联的主机状态异常"+e);
			}
      
			commonInfo = PerfUtils.getVmCommonPerf(virtualMachine);
			otherInfo = PerfUtils.getVmOtherPerf(virtualMachine);

			vm.setStatus(ToolsUtils.convertManageObjectStatusToString(virtualMachine.getSummary().getOverallStatus()));

			// 设置电源状态
			if(virtualMachine.getRuntime()!=null){
				  vm.setPowerStatus(ToolsUtils.convertVmPowerStatusToString(virtualMachine.getRuntime().getPowerState()));
			}
     
			vm.setConnectionStatus(PerfConstants.HOST_CONN_STATUS_CONNECTED);

			if(virtualMachine!=null &&  virtualMachine.getConfig()!=null){
			    long totalHz = hzd * virtualMachine.getConfig().getHardware().getNumCPU();
			    vm.setCpuMHZTotal(String.valueOf(totalHz));
			    vm.setCpuMHZUsed(commonInfo.get(PerfConstants.CPU_USED_ID));
			    double cpuUsage =0;
			    if(totalHz!=0L){
			    	cpuUsage = (Double.valueOf(commonInfo.get(PerfConstants.CPU_USED_ID)) / totalHz) * 100;
			    }
			    BigDecimal cpuUsageBd = new BigDecimal(cpuUsage);
			    double cpuUsagePercent = cpuUsageBd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			    vm.setCpuUsage(String.valueOf(cpuUsagePercent));
			}
			
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

		        // System.out.println("IOps r="+ ToolsUtils.GetMapValue(otherInfo,
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
		        // vm.setTriggeredAlarm(historyPerfAndAlertService.getTriggeredAlarms(virtualMachine));

		        //自定义网卡信息  
		        // GuestNicInfo[] guestNicInfos=guestInfo.getNet();
		        // if(guestNicInfos!=null && guestNicInfos.length>0){
		        // for(GuestNicInfo guestNicInfo:guestNicInfos){
		        // System.out.println(guestNicInfo.getMacAddress());//mac 地址
		        // System.out.println(guestNicInfo.getDeviceConfigId());//deviceConfigId
		        // }
		        // }
		        return vm;  
		} catch (Exception e) {
			logger.error("获取状态异常"+e);
			return null;
		}
    
      
    }

    @Override
    public JSONObject getHistory(String vmId) {
         JSONObject resultJson = new JSONObject();
         VirtualMachine virtualMachine = null;
         try {
         virtualMachine = this.getOpUtil().getVirtualMachineById(vmId);
         } catch (CloudViewPerfException e) {
         e.printStackTrace();
         }
         if (virtualMachine == null) {
         return resultJson;
         }
         String vmName = "";
         try {
         vmName = virtualMachine.getName();
         } catch (Exception e) {
         System.err.println("----getHistory--vmId:" + vmId + "--查询失败");
         logger.info("----getHistory--vmId:" + vmId + "--查询失败");
         return resultJson;
         }

        // 获取24小时性能数据对象的JSON信息
         Map<String, List<MetricValue>> metricListMap =
         historyPerfAndAlertService.get24HPerformData(vmName,
         SystemResourceType.virtualMachine);
        
         JSONArray cpuTime = new JSONArray();
         JSONArray cpuValue = new JSONArray();
        
         JSONArray memTime = new JSONArray();
         JSONArray memValue = new JSONArray();
        
         JSONArray diskTime = new JSONArray();
         JSONArray diskValue = new JSONArray();
        
         JSONArray netTime = new JSONArray();
         JSONArray netValue = new JSONArray();
        
         if (metricListMap != null && metricListMap.isEmpty() == false) {
         for (String key : metricListMap.keySet()) {
         for (MetricValue mv : metricListMap.get(key)) {
         if (key.equals("CPU_RATE")) {
         cpuTime.add(mv.getCollectTime());
         cpuValue.add(mv.getMetricValue());
         } else if (key.equals("MEM_RATE")) {
         memTime.add(mv.getCollectTime());
         memValue.add(mv.getMetricValue());
         } else if (key.equals("DSK_USAGE")) {
         diskTime.add(mv.getCollectTime());
         diskValue.add(mv.getMetricValue());
         } else if (key.equals("NIC_AVG")) {
         netTime.add(mv.getCollectTime());
         netValue.add(mv.getMetricValue());
         }
         }
         }
         }
        
         // 组装单个指标值的json对象
         JSONObject mapCpu = new JSONObject();
         mapCpu.put("collectTime", cpuTime);
         mapCpu.put("values", cpuValue);
        
         JSONObject mapMem = new JSONObject();
         mapMem.put("collectTime", memTime);
         mapMem.put("values", memValue);
        
         JSONObject mapDisk = new JSONObject();
         mapDisk.put("collectTime", diskTime);
         mapDisk.put("values", diskValue);
        
         JSONObject mapNet = new JSONObject();
         mapNet.put("collectTime", netTime);
         mapNet.put("values", netValue);
        
         // 组装所有指标值json对象
         JSONObject mapAllMertic = new JSONObject();
         mapAllMertic.put("cpuUsage", mapCpu);
         mapAllMertic.put("memUsage", mapMem);
         mapAllMertic.put("diskTps", mapDisk);
         mapAllMertic.put("netTps", mapNet);
        
         resultJson.put("perf24", mapAllMertic);
//        JSONObject resultJson = historyData.getJsonHistoryData(vmId, PerfConstants.ENTITY_VM,
//                PerfConstants.HISTORY_ONEDAY);
        return resultJson;
    }


    /**
     * @return the opUtil
     */
    public VCenterManageUtils getOpUtil() {
        // if (this.opUtil == null)
        this.opUtil = new VCenterManageUtils(this.connection);
        return opUtil;
    }

}
