/**
 * 
 */
package org.waddys.xcloud.monitor.serviceImpl.util;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.waddys.xcloud.monitor.bo.AlarmEntity;
import org.waddys.xcloud.monitor.serviceImpl.entity.Host;
import org.waddys.xcloud.monitor.serviceImpl.entity.VM;

import com.vmware.vim25.ArrayOfPerfCounterInfo;
import com.vmware.vim25.DatastoreSummary;
import com.vmware.vim25.DynamicProperty;
import com.vmware.vim25.HostHardwareInfo;
import com.vmware.vim25.HostListSummary;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.ObjectContent;
import com.vmware.vim25.ObjectSpec;
import com.vmware.vim25.PerfCounterInfo;
import com.vmware.vim25.PerfEntityMetric;
import com.vmware.vim25.PerfEntityMetricBase;
import com.vmware.vim25.PerfFormat;
import com.vmware.vim25.PerfMetricId;
import com.vmware.vim25.PerfMetricIntSeries;
import com.vmware.vim25.PerfMetricSeries;
import com.vmware.vim25.PerfQuerySpec;
import com.vmware.vim25.PropertyFilterSpec;
import com.vmware.vim25.PropertySpec;
import com.vmware.vim25.RetrieveOptions;
import com.vmware.vim25.RetrieveResult;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.VirtualMachineStorageSummary;
import com.vmware.vim25.VirtualMachineSummary;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.PerformanceManager;
import com.vmware.vim25.mo.PropertyCollector;
import com.vmware.vim25.mo.ServerConnection;
import com.vmware.vim25.mo.VirtualMachine;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
public class PerfUtils {
	private static HashMap<String, Integer> countersIdMap = new HashMap<String, Integer>();
	private static HashMap<Integer, PerfCounterInfo> countersInfoMap = new HashMap<Integer, PerfCounterInfo>();
	private static boolean isInit = false;	
	
	public static  void InitCounterMap(ServerConnection conn) throws InvalidProperty, RuntimeFault, RemoteException{
		countersIdMap.clear();
		countersInfoMap.clear();
		PropertyCollector propColl = conn.getServiceInstance().getPropertyCollector();
		PerformanceManager performanceMgr = conn.getServiceInstance().getPerformanceManager();
		/*
		* Create an object spec to define the context to retrieve the PerformanceManager property.
		*/
		ObjectSpec oSpec = new ObjectSpec();
		oSpec.setObj(performanceMgr.getMOR());
		/*
		* Specify the property for retrieval
		* (PerformanceManager.perfCounter is the list of counters of which the vCenter Server is aware.)
		*/
		PropertySpec pSpec = new PropertySpec();
		pSpec.setType("PerformanceManager");
		pSpec.setPathSet(new String[]{"perfCounter"});

		/*
		* Create a PropertyFilterSpec and add the object and property specs to it.
		*/
		PropertyFilterSpec fSpec = new PropertyFilterSpec();
		ObjectSpec[] oSpecs = new ObjectSpec[1];
		oSpecs[0] = oSpec;
		fSpec.setObjectSet(oSpecs);
		PropertySpec[] pSpecs = new PropertySpec[1];
		pSpecs[0] = pSpec;
		fSpec.setPropSet(pSpecs);;
		/*
		* Create a list for the filter and add the spec to it.
		*/
		PropertyFilterSpec[] fSpecList = new PropertyFilterSpec[1];
		fSpecList[0] = fSpec;
		/*
		* Get the performance counters from the server.
		*/
		RetrieveOptions ro = new RetrieveOptions();
		RetrieveResult props = propColl.retrievePropertiesEx(fSpecList, ro);
		/*
		* Turn the retrieved results into an array of PerfCounterInfo.
		*/
		PerfCounterInfo[] perfCounters = new PerfCounterInfo[1];
		if (props != null) {
			for (ObjectContent oc : props.getObjects()) {
				DynamicProperty[] dps = oc.getPropSet();
				if (dps != null) {
					for (DynamicProperty dp : dps) {
						/*
						 * DynamicProperty.val is an xsd:anyType value to be cast
						 * to an ArrayOfPerfCounterInfo and assigned to a List<PerfCounterInfo>.
						 */
						perfCounters = ((ArrayOfPerfCounterInfo)dp.getVal()).getPerfCounterInfo();
					}
				}
			}
		}
		/*
		* Cycle through the PerfCounterInfo objects and load the maps.
		*/
		for(PerfCounterInfo perfCounter : perfCounters) {
			Integer counterId = new Integer(perfCounter.getKey());
			/*
			 * This map uses the counter ID to index performance counter metadata.
			 */
			countersInfoMap.put(counterId, perfCounter);
			/*
			 * Obtain the name components and construct the full counter name,
			 * for example – power.power.AVERAGE.
			 * This map uses the full counter name to index counter IDs.
			 */

			String fullCounterName = ToolsUtils.MakeMetricFullName(perfCounter);
			/*
			 * Store the counter ID in a map indexed by the full counter name.
			 */
			countersIdMap.put(fullCounterName, counterId);
			//System.out.println("metricID=" + counterId + ",full counter name=" +fullCounterName);
			
			isInit = true;
		}
	}
	
	/**
	 * 
	 * @param perfMgr
	 * @param entity  操作对象
	 * @param metric_full_name  指标名称列表
	 * @return 性能指标列表
	 */
	private static PerfEntityMetric getPerformanceContent(PerformanceManager perfMgr,
			ManagedEntity entity, String[] metric_full_name){

		try {
			//PerfMetricId[] perfMetricIds = perfMgr.queryAvailablePerfMetric(
			//		entity, null, null, FRESH_RATE);
			if(!PerfUtils.isInit){
				PerfUtils.InitCounterMap(entity.getServerConnection());
			}

			PerfMetricId[] perfMetricIds = new PerfMetricId[metric_full_name.length];
			for(int i = 0; i < metric_full_name.length; i++) {
				
				PerfMetricId metricId = new PerfMetricId();
				metricId.setCounterId(countersIdMap.get(metric_full_name[i]));
				metricId.setInstance("*");
				perfMetricIds[i] = metricId;
			}
			PerfQuerySpec qSpec = new PerfQuerySpec();
			qSpec.setEntity(entity.getMOR());
			// qSpec.setStartTime(xmlgc2Before);
			// qSpec.setEndTime(xmlgc1Before);

			qSpec.setMetricId(perfMetricIds);
			
			/*
			Calendar curTime = conn.getServiceInstance().currentTime();
		    Calendar startTime = (Calendar) curTime.clone();
		    startTime.roll(Calendar.DATE, -1);
			qSpec.setStartTime(startTime);
			Calendar endTime = (Calendar) curTime.clone();
			endTime.roll(Calendar.DATE,0);
			qSpec.setEndTime(endTime);
			*/
			
			qSpec.setIntervalId(PerfConstants.FRESH_RATE);// 20 300 1800 7200 86400
			qSpec.setMaxSample(1);// 鏈�ぇ鏍峰搧鏁�=1锛屽彧鍙栦竴涓渶鏂扮殑
			qSpec.setFormat(PerfFormat.normal.toString());

			PerfQuerySpec[] qSpecs = new PerfQuerySpec[1];
			qSpecs[0] = qSpec;
			PerfEntityMetricBase[] values = perfMgr.queryPerf(qSpecs);

			if (values == null || values.length == 0) {		
				//System.out.println("cannot query performance data");
			}else{
				return (PerfEntityMetric) values[0];
			}

		} catch (Exception e) {		
			//System.out.println("Get Realtime Proformance Data fault");
			e.printStackTrace();
		}
		return null;

	}
	
	
	/**
	 * 
	 * @param perfMgr
	 * @param entity  操作对象
	 * @param metric_full_name  指标名称列表
	 * @return 性能指标列表
	 */
	private static PerfEntityMetricBase[] getPerformanceContentEx(PerformanceManager perfMgr,
			ManagedEntity[] entities, String[] metric_full_name){

		try {
			//PerfMetricId[] perfMetricIds = perfMgr.queryAvailablePerfMetric(
			//		entity, null, null, FRESH_RATE);
			if(entities == null || entities.length == 0){
				return null;
			}
			if(!PerfUtils.isInit){
				PerfUtils.InitCounterMap(entities[0].getServerConnection());
			}

			PerfMetricId[] perfMetricIds = new PerfMetricId[metric_full_name.length];
			for(int i = 0; i < metric_full_name.length; i++) {
				
				PerfMetricId metricId = new PerfMetricId();
				metricId.setCounterId(countersIdMap.get(metric_full_name[i]));
				metricId.setInstance("*");
				perfMetricIds[i] = metricId;
			}
			PerfQuerySpec[] qSpecs = new PerfQuerySpec[entities.length];
			for(int i = 0; i < entities.length; i++){
				PerfQuerySpec qSpec = new PerfQuerySpec();
				qSpec.setEntity(entities[i].getMOR());
				qSpec.setMetricId(perfMetricIds);
				qSpec.setIntervalId(PerfConstants.FRESH_RATE);// 20 300 1800 7200 86400
				qSpec.setMaxSample(1);// 鏈�ぇ鏍峰搧鏁�=1锛屽彧鍙栦竴涓渶鏂扮殑
				qSpec.setFormat(PerfFormat.normal.toString());
				qSpecs[i] = qSpec;
			}
						
			PerfEntityMetricBase[] values = perfMgr.queryPerf(qSpecs);

			if (values == null || values.length == 0) {		
				//System.out.println("cannot query performance data");
			}else{
				return  values;
			}

		} catch (Exception e) {		
			//System.out.println("Get Realtime Proformance Data fault");
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 从多个采样值根据要求提取相应的值，例如平均值，最大值等方法,当前取最大值作为此次采用的有效值
	 * @param perfDataValues
	 * @return map对象
	 */
	public static Map<String,Long> mergePerfValue(PerfMetricSeries[] perfDataValues){
		Map<String,Long> valuesMap = new HashMap<String,Long>();
		//Map<String,Integer> nums = new HashMap<String,Integer>();
		for(PerfMetricSeries perfDataValue : perfDataValues){
			if (perfDataValue instanceof PerfMetricIntSeries) {
				long[] values = ((PerfMetricIntSeries) perfDataValue).value;
				int id = perfDataValue.id.counterId;
				PerfCounterInfo pcinfo = countersInfoMap.get(id);
				String keyString = ToolsUtils.MakeMetricFullName(pcinfo);
				//System.out.println("key=[" + keyString + "],value=" + values[0]);
				if(valuesMap.containsKey(keyString)){
					if(values[0] > valuesMap.get(keyString)){
						valuesMap.put(keyString, values[0]);
					}			
					//nums.put(keyString, nums.get(keyString) + 1);
				}else{
					valuesMap.put(keyString, values[0]);
					//nums.put(keyString, 1);
				}
			}
			
		}		
		return valuesMap;
	}
	
	/**
	 * 返回指定对象列表的性能值map
	 * @param entities ：管理对象数组
	 * @param objType  ： 管理对象类型，0：物理机，1：虚拟机
	 * @return
	 */
	public static Map<String,Map<String,Long>> getMorPerfData(ManagedEntity[] entities, int objType){
		Map<String,Map<String,Long>> result = new HashMap<String,Map<String,Long>>();
		if(entities == null || entities.length == 0){
			return result;			
		}
		String perfMetrics[]  = null;
		if(objType == 0){
			perfMetrics = PerfConstants.HOST_PERF;
		}else{
			perfMetrics = PerfConstants.VM_PERF;
		}
		PerfEntityMetricBase[] perfData = getPerformanceContentEx(entities[0].getServerConnection().getServiceInstance().getPerformanceManager(),
										entities,perfMetrics);
		if (perfData == null) {
			return result;
		}
		for(int i = 0; i < perfData.length && i < entities.length; i++){
			PerfMetricSeries[] perfDataValues = ((PerfEntityMetric)perfData[i]).value;
			Map<String,Long> finalPerfData = mergePerfValue(perfDataValues);
			result.put(entities[i].getMOR().getVal(), finalPerfData);
		}						
		return result;
	}

	/**
	 * 返回指定物理机的网络IO速率，磁盘IO速率，磁盘IOPS
	 * @param entity
	 * @return
	 */
	public static Map<String,String> getHostOtherPerf(ManagedEntity entity){
		Map<String,String> result = new HashMap<String,String>();
		HostSystem systems = (HostSystem)entity;
		PerfEntityMetric perfData = getPerformanceContent(systems.getServerConnection().getServiceInstance().getPerformanceManager(), 
				systems,PerfConstants.HOST_PERF);
		if (perfData == null) {
			return result;
		}
		PerfMetricSeries[] perfDataValues = perfData.value;
		Map<String,Long> finalPerfData = mergePerfValue(perfDataValues);
		for(String key:finalPerfData.keySet()){
			result.put(key, String.valueOf(finalPerfData.get(key)));
		}
		return result;
	}

    // 返回指定物理机的CPU频率，当前使用量，使用率，内存总容量，内存使用量，内存使用率，网络IO速率，磁盘IO速率
    public static Host getHostBasicPerf(Host host, HostSystem systems) {

        Map<String, String> result = new HashMap<String, String>();

        PerfEntityMetric perfData = getPerformanceContent(systems.getServerConnection().getServiceInstance()
                .getPerformanceManager(), systems, PerfConstants.HOST_BASIC_PERF);
        if (perfData == null) {
            return host;
        }
        PerfMetricSeries[] perfDataValues = perfData.value;
        Map<String, Long> finalPerfData = mergePerfValue(perfDataValues);
        for (String key : finalPerfData.keySet()) {
            result.put(key, String.valueOf(finalPerfData.get(key)));
        }

        long diskIOSpeed = Long.valueOf(ToolsUtils.GetMapValue(result, PerfConstants.DISK_READ_ID))
                + Long.valueOf(ToolsUtils.GetMapValue(result, PerfConstants.DISK_WRITE_ID));
        host.setDiskIOSpeed(String.valueOf(diskIOSpeed));
        host.setNetworkTransmitSpeed(ToolsUtils.GetMapValue(result, PerfConstants.NET_IO_SPEED_ID));

        return host;
    }

    // 返回指定虚拟机的内存使用率，网络IO速率，磁盘IO速率
    public static Map<String, String> getVMBasicPerf(ManagedEntity entity) {
        Map<String, String> result = new HashMap<String, String>();

        VirtualMachine vm = (VirtualMachine) entity;
        VirtualMachineSummary summary = vm.getSummary();
        VirtualMachineConfigInfo vmConfigInfo = vm.getConfig();
        double memUsagePercent = 0.0;
        if (vmConfigInfo.getHardware().getMemoryMB() != 0) {
            BigDecimal memUsageBd = new BigDecimal(((double) summary.quickStats.guestMemoryUsage / vmConfigInfo
                    .getHardware().getMemoryMB()) * 100);
            memUsagePercent = memUsageBd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }

        result.put(PerfConstants.MEM_USAGE_ID, String.valueOf(memUsagePercent));
        
        PerfEntityMetric perfData = getPerformanceContent(vm.getServerConnection().getServiceInstance()
                .getPerformanceManager(), vm, PerfConstants.VM_BASIC_PERF);
        if (perfData == null) {
            return result;
        }
        PerfMetricSeries[] perfDataValues = perfData.value;
        Map<String, Long> finalPerfData = mergePerfValue(perfDataValues);
        for (String key : finalPerfData.keySet()) {
            result.put(key, String.valueOf(finalPerfData.get(key)));
        }
        return result;
    }
	/**
	 * 返回指定物理机的CPU频率，当前使用量，使用率，内存总容量，内存使用量，内存使用率，存储总容量，存储使用量
	 * @param entity
	 * @return
	 */
	public static Map<String,String> getHostCommonPerf(ManagedEntity entity){
		Map<String,String> result = new HashMap<String,String>();
		HostSystem systems = (HostSystem)entity;
		
		HostListSummary summary = systems.getSummary();
		HostHardwareInfo hwi = systems.getHardware();
        long hz = hwi.cpuInfo.hz;
		long e6 = 1000000;
		long hzd = (hz) / e6;
		
		long totalHz = hzd * hwi.cpuInfo.numCpuCores;
		
        // long cpuUsage = summary.quickStats.overallCpuUsage;
        long cpuUsage = 0;
        if (summary.quickStats.overallCpuUsage != null) {
            cpuUsage += summary.quickStats.overallCpuUsage;
        }

        long totalMem = (long) ((double) hwi.getMemorySize() / 1024 / 1024);

        // long memUsage = summary.quickStats.overallMemoryUsage;
        long memUsage = 0;
        if (summary.quickStats.overallCpuUsage != null) {
            memUsage += summary.quickStats.overallMemoryUsage;
        }
		Datastore[] datastores;
		double capacity = 0;  //以MB为单位
		double freeSpace = 0; //以MB为单位
		
		try {
			datastores = systems.getDatastores();
			if(datastores != null && datastores.length > 0){
				for(Datastore ds:datastores){
		        	DatastoreSummary datastoresummary = ds.getSummary();        	
		        	capacity += (double)datastoresummary.getCapacity()/1024/1024;
		        	freeSpace += (double)datastoresummary.getFreeSpace()/1024/1024;      	
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
		
        
        result.put(PerfConstants.CPU_TOTAL_ID, String.valueOf(totalHz));
        result.put(PerfConstants.CPU_USED_ID, String.valueOf(cpuUsage));
        double cpuUsagePercent_ori = ((double)cpuUsage/totalHz)*100;
        BigDecimal cpuUsageBd = new BigDecimal(cpuUsagePercent_ori);
        double cpuUsagePercent = cpuUsageBd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();                
        result.put(PerfConstants.CPU_USAGE_ID, String.valueOf(cpuUsagePercent));
        
        result.put(PerfConstants.MEM_TOTAL_ID, String.valueOf(totalMem));
        
        BigDecimal memUsed = new BigDecimal((double)memUsage/1024);
        double memUsedDb = memUsed.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
        result.put(PerfConstants.MEM_USED_ID, String.valueOf(memUsedDb));
        
        double memUsagePercent_ori = ((double)memUsage/totalMem)*100;
        BigDecimal memUsageBd = new BigDecimal(memUsagePercent_ori);
        double memUsagePercent = memUsageBd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
        result.put(PerfConstants.MEM_USAGE_ID, String.valueOf(memUsagePercent));
        
        result.put(PerfConstants.DISK_TOTAL_ID, String.valueOf(capacity));
        result.put(PerfConstants.DISK_USED_ID, String.valueOf(capacity - freeSpace));

        double diskUsagePercent_ori = (capacity - freeSpace / capacity) * 100;
        BigDecimal diskUsageBd = new BigDecimal(diskUsagePercent_ori);
        double diskUsagePercent = diskUsageBd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        result.put(PerfConstants.DISK_USAGE_ID, String.valueOf(diskUsagePercent));
                
		return result;
	}
	
	/**
	 * 返回主机的指定字段的值
	 * @param host
	 * @param type
	 * @return
	 */
	public static String getHostMetricValue(Host host, String type){
		
		String value = "0";
		
		if(type.compareToIgnoreCase(PerfConstants.CPU_USAGE_ID) == 0){
			
			value = host.getCpuUsage();
			
		}else if(type.compareToIgnoreCase(PerfConstants.CPU_USED_ID) == 0){
			
			value = host.getCpuMHZUsed();
			
		}else if(type.compareToIgnoreCase(PerfConstants.MEM_USAGE_ID) == 0){
			
			value = host.getMemoryUsage();
			
		}else if(type.compareToIgnoreCase(PerfConstants.MEM_USED_ID) == 0){
			
			value = host.getMemoryUsed();
			
		}else if(type.compareToIgnoreCase(PerfConstants.DISK_IO_SPEED_ID) == 0){
			
			value = host.getDiskIOSpeed();
			
		}else if(type.compareToIgnoreCase(PerfConstants.DISK_IOPS_ID) == 0){
			
			value = host.getDiskIops();
			
		}else if(type.compareToIgnoreCase(PerfConstants.NET_IO_SPEED_ID) == 0){
			
			value = host.getNetworkTransmitSpeed();
			
		}else if(type.compareToIgnoreCase(PerfConstants.VM_NUMS_ID) == 0){
			
            // value = String.valueOf(host.getVmNumber());
            value = String.valueOf(host.getVmNum());
			
		}else if(type.compareToIgnoreCase(PerfConstants.CPU_TOTAL_ID) == 0){
			
			value = host.getCpuMHZTotal();
			
		}else if(type.compareToIgnoreCase(PerfConstants.MEM_TOTAL_ID) == 0){
			
			value = host.getMemoryTotal();
			
		}else if(type.compareToIgnoreCase(PerfConstants.DISK_TOTAL_ID) == 0){
			
			value = host.getDiskTotal();
			
		}else if(type.compareToIgnoreCase(PerfConstants.DISK_USED_ID) == 0){
			
			value = host.getDiskUsed();
			
		}else if(type.compareToIgnoreCase(PerfConstants.OBJ_RUN_STATUS_ID) == 0){
			
			value = host.getStatus();
			
		}else if(type.compareToIgnoreCase(PerfConstants.OBJ_POWER_STATUS_ID) == 0){
				
				value = host.getPowerStatus();
		}else{
			
			value = "0";
			
		}
		return value;
	}
	
	
	
	public static JSONObject createHostJsonObject(Host host){
		JSONObject hostJson = new JSONObject();
		hostJson.put(PerfConstants.JSON_NAME_KEY, host.getName());
		hostJson.put(PerfConstants.JSON_ID_KEY, host.getId());
		hostJson.put(PerfConstants.JSON_DC_KEY, host.getDataCenterName());
		if(host.isbStandalone()){
			hostJson.put(PerfConstants.JSON_CLUSTER_KEY, PerfConstants.UNKNOW_NAME);
		}else{
			hostJson.put(PerfConstants.JSON_CLUSTER_KEY, host.getClusterName());
		}
		
		
		hostJson.put(PerfConstants.JSON_CPU_USAGE_KEY, Double.valueOf(host.getCpuUsage()));
		hostJson.put(PerfConstants.JSON_CPU_USED_KEY, Long.valueOf(host.getCpuMHZUsed()));
		hostJson.put(PerfConstants.JSON_MEM_USAGE_KEY, Double.valueOf(host.getMemoryUsage()));
		hostJson.put(PerfConstants.JSON_MEM_USED_KEY, Double.valueOf(host.getMemoryUsed()));
		hostJson.put(PerfConstants.JSON_DISK_IO_KEY, Long.valueOf(host.getDiskIOSpeed()));
		hostJson.put(PerfConstants.JSON_DISK_IOPS_KEY, Long.valueOf(host.getDiskIops()));
		hostJson.put(PerfConstants.JSON_NET_IO_KEY, Long.valueOf(host.getNetworkTransmitSpeed()));
        // hostJson.put(PerfConstants.JSON_VM_NUMS_KEY, host.getVmNumber());
        hostJson.put(PerfConstants.JSON_VM_NUMS_KEY, host.getVmNum());
		
		hostJson.put(PerfConstants.JSON_CPU_TOTAL_KEY, Long.valueOf(host.getCpuMHZTotal()));
		hostJson.put(PerfConstants.JSON_MEM_TOTAL_KEY, Long.valueOf(host.getMemoryTotal()));
		hostJson.put(PerfConstants.JSON_DISK_TOTAL_KEY, Double.valueOf(host.getDiskTotal()));
		hostJson.put(PerfConstants.JSON_DISK_USED_KEY, Double.valueOf(host.getDiskUsed()));
		
		hostJson.put(PerfConstants.JSON_STATUSL_KEY, host.getStatus());
		hostJson.put(PerfConstants.JSON_POWER_STATUS_KEY, host.getPowerStatus());
		hostJson.put(PerfConstants.JSON_CONN_STATUS_KEY,host.getConnectionStatus());
		
		List<AlarmEntity> alarms = host.getTriggeredAlarm();
		if(alarms != null && alarms.size() > 0){
			Collections.sort(alarms);
			JSONArray alarmAry = new JSONArray();
			if(alarms != null){
				for(int i = 0; i <alarms.size(); i++){
					AlarmEntity ae = alarms.get(i);
					alarmAry.add(i, ae);
				}
			}
			
			hostJson.put(PerfConstants.JSON_TRIGGER_ALARM_KEY, alarmAry);
		}
		
							
		return hostJson;
	}
	
	public static JSONObject createVmJsonObject(VM vm){
		JSONObject vmJson = new JSONObject();
		vmJson.put(PerfConstants.JSON_NAME_KEY, vm.getName());
		vmJson.put(PerfConstants.JSON_ID_KEY, vm.getId());
		vmJson.put(PerfConstants.JSON_DC_KEY, vm.getDateCenterName());
		if(vm.isbStandalone()){
			vmJson.put(PerfConstants.JSON_CLUSTER_KEY, PerfConstants.UNKNOW_NAME);
		}else{
			vmJson.put(PerfConstants.JSON_CLUSTER_KEY, vm.getClusterName());
		}
		
		vmJson.put(PerfConstants.JSON_HOSTNAME_KEY, vm.getHostName());
		
		vmJson.put(PerfConstants.JSON_CPU_USAGE_KEY, Double.valueOf(vm.getCpuUsage()));
		vmJson.put(PerfConstants.JSON_CPU_USED_KEY, Long.valueOf(vm.getCpuMHZUsed()));
		vmJson.put(PerfConstants.JSON_MEM_USAGE_KEY, Double.valueOf(vm.getMemoryUsage()));
		vmJson.put(PerfConstants.JSON_MEM_USED_KEY, Long.valueOf(vm.getMemoryUsed()));
		vmJson.put(PerfConstants.JSON_DISK_IO_KEY, Long.valueOf(vm.getDiskIOSpeed()));
		vmJson.put(PerfConstants.JSON_DISK_IOPS_KEY, Long.valueOf(vm.getDiskIops()));
		vmJson.put(PerfConstants.JSON_NET_IO_KEY, Long.valueOf(vm.getNetworkTransmitSpeed()));
		vmJson.put(PerfConstants.JSON_DISK_USAGE_KEY, Double.valueOf(vm.getDiskUsage()));
		
		vmJson.put(PerfConstants.JSON_CPU_TOTAL_KEY, Long.valueOf(vm.getCpuMHZTotal()));
		vmJson.put(PerfConstants.JSON_MEM_TOTAL_KEY, Long.valueOf(vm.getMemoryTotal()));
		vmJson.put(PerfConstants.JSON_DISK_TOTAL_KEY, Double.valueOf(vm.getDiskTotal()));
		vmJson.put(PerfConstants.JSON_DISK_USED_KEY, Double.valueOf(vm.getDiskUsed()));
		
		vmJson.put(PerfConstants.JSON_IP_ADDRESS_KEY, vm.getIpAddr());
		
		vmJson.put(PerfConstants.JSON_STATUSL_KEY, vm.getStatus());
		vmJson.put(PerfConstants.JSON_POWER_STATUS_KEY, vm.getPowerStatus());
		vmJson.put(PerfConstants.JSON_CONN_STATUS_KEY,vm.getConnectionStatus());
		
		List<AlarmEntity> alarms = vm.getTriggeredAlarm();
		if(alarms != null && alarms.size() > 0){
			Collections.sort(alarms);
			JSONArray alarmAry = new JSONArray();
			if(alarms != null){
				for(int i = 0; i <alarms.size(); i++){
					AlarmEntity ae = alarms.get(i);
					alarmAry.add(i, ae);
				}
			}
			
			vmJson.put(PerfConstants.JSON_TRIGGER_ALARM_KEY, alarmAry);
		}
		
							
		return vmJson;
	}
	
	/**
	 * 返回topn的json对象
	 * @param hosts
	 * @param metric_name
	 * @param name_key
	 * @param value_key
	 * @param metric_type : 0(long),1(double),other(String)
	 * @return
	 */
	public static JSONObject createPerfJsonObject(List<Host> hosts, String metric_name, String name_key,String value_key,int metric_type){
		int index = 0;
		JSONObject retJson = new JSONObject();
		JSONArray nameJson = new JSONArray();
		JSONArray valueJson = new JSONArray();
        if (null != hosts && hosts.size() > 0) {
            for (index = 0; index < hosts.size(); index++) {
                Host host = hosts.get(index);
                nameJson.add(index, host.getName());
                if (metric_type == 0) {
                    valueJson.add(index, Long.valueOf(getHostMetricValue(host, metric_name)));
                } else if (metric_type == 1) {
                    valueJson.add(index, Double.valueOf(getHostMetricValue(host, metric_name)));
                } else {
                    valueJson.add(index, getHostMetricValue(host, metric_name));
                }

            }
		}

		retJson.put(name_key, nameJson);
		retJson.put(value_key, valueJson);
		return retJson;
	}
	
	
	/**
	 * 获取虚拟机某个属性值
	 * @param vm
	 * @param type
	 * @return
	 */
	public static String getVmMetricValue(VM vm, String type){
		
		String value = "0";
		
		if(type.compareToIgnoreCase(PerfConstants.CPU_USAGE_ID) == 0){
			
			value = vm.getCpuUsage();
			
		}else if(type.compareToIgnoreCase(PerfConstants.CPU_USED_ID) == 0){
			
			value = vm.getCpuMHZUsed();
			
		}else if(type.compareToIgnoreCase(PerfConstants.MEM_USAGE_ID) == 0){
			
			value = vm.getMemoryUsage();
			
		}else if(type.compareToIgnoreCase(PerfConstants.MEM_USED_ID) == 0){
			
			value = vm.getMemoryUsed();
			
		}else if(type.compareToIgnoreCase(PerfConstants.VDISK_IO_SPEED_ID) == 0){
			
			value = vm.getDiskIOSpeed();
			
		}else if(type.compareToIgnoreCase(PerfConstants.VDISK_IOPS_ID) == 0){
			
			value = vm.getDiskIops();
			
		}else if(type.compareToIgnoreCase(PerfConstants.NET_IO_SPEED_ID) == 0){
			
			value = vm.getNetworkTransmitSpeed();
			
		}else if(type.compareToIgnoreCase(PerfConstants.CPU_TOTAL_ID) == 0){
			
			value = vm.getCpuMHZTotal();
			
		}else if(type.compareToIgnoreCase(PerfConstants.MEM_TOTAL_ID) == 0){
			
			value = vm.getMemoryTotal();
			
		}else if(type.compareToIgnoreCase(PerfConstants.DISK_TOTAL_ID) == 0){
			
			value = vm.getDiskTotal();
			
		}else if(type.compareToIgnoreCase(PerfConstants.DISK_USED_ID) == 0){
			
			value = vm.getDiskUsed();
			
		}else if(type.compareToIgnoreCase(PerfConstants.DISK_USAGE_ID) == 0){
			
			value = vm.getDiskUsage();
			
		}else if(type.compareToIgnoreCase(PerfConstants.OBJ_RUN_STATUS_ID) == 0){
			
			value = vm.getStatus();
			
		}else if(type.compareToIgnoreCase(PerfConstants.OBJ_POWER_STATUS_ID) == 0){
				
				value = vm.getPowerStatus();
		}else{
			
			value = "0";
			
		}
		return value;
	}
	
	/**
	 * 返回某个性能指标的json属性的数组值
	 * @param vms
	 * @param metric_name
	 * @param name_key
	 * @param value_key
	 * @param metric_type
	 * @return
	 */
	public static JSONObject createVmPerfJsonObject(List<VM> vms, String metric_name, String name_key,String value_key,int metric_type){
		int index = 0;
		JSONObject retJson = new JSONObject();
		JSONArray nameJson = new JSONArray();
		JSONArray valueJson = new JSONArray();
        if (null != vms && vms.size() > 0) {
            for (index = 0; index < vms.size(); index++) {
                VM vm = vms.get(index);
                nameJson.add(index, vm.getName());
                if (metric_type == 0) {
                    valueJson.add(index, Long.valueOf(getVmMetricValue(vm, metric_name)));
                } else if (metric_type == 1) {
                    valueJson.add(index, Double.valueOf(getVmMetricValue(vm, metric_name)));
                } else {
                    valueJson.add(index, getVmMetricValue(vm, metric_name));
                }

            }
		}

		retJson.put(name_key, nameJson);
		retJson.put(value_key, valueJson);
		return retJson;
	}
	

	/**
	 * 返回指定性能指标的(hostname,metric_value)值对的map，
	 * @param hosts
	 * @param type
	 * @return
	 */
	public static Map<String,String> createMetricValueMap(List<Host> hosts, String type){
		Map<String, String> metricValueMap = new HashMap<String,String>();
		for(int i = 0; i < hosts.size(); i++){
			Host host = hosts.get(i);
			metricValueMap.put(host.getName(), getHostMetricValue(host, type));
		}
		return metricValueMap;
	}
	
	/**
	 * 返回指定性能指标的(vmname,metric_value)值对的map，
	 * @param hosts
	 * @param type
	 * @return
	 */
	public static Map<String,String> createVmMetricValueMap(List<VM> vms, String type){
		Map<String, String> metricValueMap = new HashMap<String,String>();
		for(int i = 0; i < vms.size(); i++){
			VM vm = vms.get(i);
			metricValueMap.put(vm.getName(), getVmMetricValue(vm, type));
		}
		return metricValueMap;
	}
	
	/**
	 * 返回指定虚拟机的cpu使用率，磁盘IO,iops,网络读写IO速率，
	 * @param entity
	 * @return
	 */
	public static Map<String,String> getVmOtherPerf(ManagedEntity entity){
		Map<String,String> result = new HashMap<String,String>();
		VirtualMachine vm = (VirtualMachine)entity;
		PerfEntityMetric perfData = getPerformanceContent(vm.getServerConnection().getServiceInstance().getPerformanceManager(), 
				vm,PerfConstants.VM_PERF);
		if (perfData == null) {
			return result;
		}
		PerfMetricSeries[] perfDataValues = perfData.value;
		Map<String,Long> finalPerfData = mergePerfValue(perfDataValues);
		for(String key:finalPerfData.keySet()){
			//System.out.println("getVmOtherPerf.after[mergePerfValue],key=["+ key + "],value="+finalPerfData.get(key));
			result.put(key, String.valueOf(finalPerfData.get(key)));
		}
		return result;
	}
	
	//
	/**
	 * 返回指定虚拟机的CPU当前使用量，内存总容量，内存使用量，内存使用率，存储总容量，存储使用量，存储使用率，ip地址，操作系统名称
	 * @param entity
	 * @return
	 */
	public static Map<String,String> getVmCommonPerf(ManagedEntity entity){
		Map<String,String> result = new HashMap<String,String>();
		VirtualMachine vm = (VirtualMachine)entity;
		VirtualMachineSummary summary = vm.getSummary();
		VirtualMachineConfigInfo vmConfigInfo=vm.getConfig();
		VirtualMachineStorageSummary vmss=summary.storage;
		//只要任意属性为空，则表示虚拟机正在创建或者删除中
		if(summary == null || summary.quickStats == null || vmConfigInfo == null || 
				vmConfigInfo.getHardware() == null || vmss == null){
			return result;
		}
		result.put(PerfConstants.CPU_USED_ID, String.valueOf(summary.quickStats.overallCpuUsage));
		
		result.put(PerfConstants.MEM_TOTAL_ID, String.valueOf(vmConfigInfo.getHardware().getMemoryMB()));
		result.put(PerfConstants.MEM_USED_ID, String.valueOf(summary.quickStats.guestMemoryUsage));
		double memUsagePercent = 0.0;
		if(vmConfigInfo.getHardware().getMemoryMB() != 0){
			BigDecimal memUsageBd = new BigDecimal(((double)summary.quickStats.guestMemoryUsage/vmConfigInfo.getHardware().getMemoryMB())*100);
	        memUsagePercent = memUsageBd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
		}
		
		result.put(PerfConstants.MEM_USAGE_ID, String.valueOf(memUsagePercent));
        
		double diskTotal = 0.0;
		/*
		VirtualDevice[] devices = vm.getConfig().getHardware().getDevice();
		List<VirtualDevice> vdList = new ArrayList<VirtualDevice>();
		for (VirtualDevice device : devices) {
			if (device.getClass().getCanonicalName().indexOf("VirtualDisk") != -1) {
				vdList.add(device);
			}
		}
		List<Long> diskList = new ArrayList<Long>();
		for (int i = 0; i < vdList.size(); i++) {
			VirtualDisk disk = (VirtualDisk) vdList.get(i);
			diskList.add(disk.capacityInKB);
		}

		
		for (int i = 0; i < diskList.size(); i++) {
			diskTotal = diskTotal + diskList.get(i);
		}
		diskTotal = diskTotal/1024;
		*/
		double diskTotalMb = (double)(vm.getSummary().getStorage().getCommitted() + vm.getSummary().getStorage().getUncommitted())/1024/1024;
		BigDecimal diskTotalBd = new BigDecimal(diskTotalMb);
		diskTotal = diskTotalBd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		result.put(PerfConstants.DISK_TOTAL_ID,String.valueOf(diskTotal));//以MB为单位
		
		/* 磁盘使用信息 */
		double diskUsedTotalMb = (double)vm.getSummary().getStorage().getCommitted()/1024/1024;
		BigDecimal diskUsedBd = new BigDecimal(diskUsedTotalMb);
    	double diskUsedTotal = diskUsedBd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
		      
        result.put(PerfConstants.DISK_USED_ID,String.valueOf(diskUsedTotal));
        double diskUsagePercent = 0.0;
        if (Math.abs(diskTotal - 0) > 0.0001){
        	BigDecimal diskUsageBd = new BigDecimal((diskUsedTotal/diskTotal)*100);
        	diskUsagePercent = diskUsageBd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
        }
        
        result.put(PerfConstants.DISK_USAGE_ID, String.valueOf(diskUsagePercent));
        
        result.put(PerfConstants.IPADDRESS_ID, summary.getGuest().getIpAddress());
        result.put(PerfConstants.OPERATION_SYSTEM_ID, summary.getGuest().getGuestFullName());
        
                
		return result;
	}
	
	
	/**
	 * 过滤告警信息，返回一周以内的最新的前n条告警信息====>修改为最近n条告警
	 * @param alarms  已排序的告警对象，按发生时间降序排列
	 * @param n   欲返回的条数
	 * @return
	 */
	public static List<AlarmEntity> filterAlarm(List<AlarmEntity> alarms, int n){
		List<AlarmEntity> retAlarms = new ArrayList<AlarmEntity>();
		if(alarms == null || alarms.size() == 0 || n <= 0){
			return retAlarms;
		}		
	/*	Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -7);//一周以前
		String strBaseTime = ToolsUtils.getCalendarAsString(calendar, 2);*/
		int index = 0;
		for(int i = 0; i < alarms.size() && index < n; i++){
			AlarmEntity ae = alarms.get(i);
		/*	if(ae.getTime().compareTo(strBaseTime) > 0){			*/
				retAlarms.add(index, ae);
				index++;
				
	/*		}*/
		}
		
		return retAlarms;
	}
		
	public static void InitialHost(Host host){
		host.setPowerStatus(PerfConstants.OBJECT_POWER_STATUS_UNKNOWN);
		host.setStatus(PerfConstants.OBJECT_STATUS_GRAY);
		host.setCpuMHZUsed("0");
		host.setCpuUsage("0");

		host.setMemoryUsed("0");
		host.setMemoryUsage("0");

		host.setDiskReadSpeed("0");
		host.setDiskWriteSpeed("0");


		host.setDiskIOSpeed("0");


		host.setDiskIops("0");

		host.setNetworkReceiveSpeed("0");
		host.setNetworkSendSpeed("0");
		host.setNetworkTransmitSpeed("0");

		host.setCpuMHZTotal("0");
		host.setMemoryTotal("0");
		host.setDiskTotal("0");
		host.setDiskUsed("0");
        // host.setVmNumber(0);
        host.setVmNum(0);
	}
	public static void InitialVM(VM vm){
		vm.setStatus(PerfConstants.OBJECT_STATUS_GRAY);
		
		// 设置电源状态
		vm.setPowerStatus(PerfConstants.OBJECT_POWER_STATUS_UNKNOWN);
		
		vm.setCpuMHZTotal("0");
		
		vm.setCpuMHZUsed("0");
		
        vm.setCpuUsage("0");
        
        vm.setMemoryTotal("0");
        vm.setMemoryUsed("0");
        vm.setMemoryUsage("0");
        
        vm.setDiskTotal("0");
        vm.setDiskUsed("0");
        vm.setDiskUsage("0");
        vm.setIpAddr("0");
               
        vm.setDiskReadSpeed("0");
        vm.setDiskWriteSpeed("0");
        vm.setDiskIOSpeed("0");       
        
        //System.out.println("IOps r="+ ToolsUtils.GetMapValue(otherInfo, PerfConstants.VDISK_IOPS_READ_ID) + ",IOps w=" + ToolsUtils.GetMapValue(otherInfo, PerfConstants.VDISK_IOPS_WRITE_ID));
        vm.setDiskIops("0");
              
        vm.setNetworkReceiveSpeed("0");
        vm.setNetworkSendSpeed("0");
        vm.setNetworkTransmitSpeed("0");
	}
	
    /**
     * 返回指定存储的网络IO速率，磁盘IO速率，磁盘IOPS
     * 
     * @param entity
     * @return
     */
    public static Map<String, String> getStorageBasicPerf(ManagedEntity entity) {
        Map<String, String> result = new HashMap<String, String>();
        Datastore systems = (Datastore) entity;
        PerfEntityMetric perfData = getPerformanceContent(
                systems.getServerConnection().getServiceInstance().getPerformanceManager(), systems,
                PerfConstants.STORAGE_PERF);
        if (perfData == null) {
            return result;
        }
        PerfMetricSeries[] perfDataValues = perfData.value;
        Map<String, Long> finalPerfData = mergePerfValue(perfDataValues);
        for (String key : finalPerfData.keySet()) {
            result.put(key, String.valueOf(finalPerfData.get(key)));
        }
        return result;
    }
}
