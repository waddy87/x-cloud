package com.sugon.cloudview.cloudmanager.monitor.serviceImpl.impl;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sugon.cloudview.cloudmanager.monitor.service.bo.AlarmEntity;
import com.sugon.cloudview.cloudmanager.monitor.service.bo.AlertEvent;
import com.sugon.cloudview.cloudmanager.monitor.service.bo.MetricValue;
import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.common.SystemResourceType;
import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.service.HistoryPerfAndAlertServiceI;
import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.util.Connection;
import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.util.ToolsUtils;
import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.util.VCenterManageUtils;
import com.sugon.vim25.AlarmInfo;
import com.sugon.vim25.AlarmState;
import com.sugon.vim25.Event;
import com.sugon.vim25.EventFilterSpec;
import com.sugon.vim25.InvalidProperty;
import com.sugon.vim25.PerfCounterInfo;
import com.sugon.vim25.PerfEntityMetric;
import com.sugon.vim25.PerfEntityMetricBase;
import com.sugon.vim25.PerfFormat;
import com.sugon.vim25.PerfMetricId;
import com.sugon.vim25.PerfMetricIntSeries;
import com.sugon.vim25.PerfMetricSeries;
import com.sugon.vim25.PerfQuerySpec;
import com.sugon.vim25.PerfSampleInfo;
import com.sugon.vim25.RuntimeFault;
import com.sugon.vim25.mo.Alarm;
import com.sugon.vim25.mo.EventHistoryCollector;
import com.sugon.vim25.mo.EventManager;
import com.sugon.vim25.mo.HostSystem;
import com.sugon.vim25.mo.InventoryNavigator;
import com.sugon.vim25.mo.ManagedEntity;
import com.sugon.vim25.mo.PerformanceManager;
import com.sugon.vim25.mo.ServerConnection;
import com.sugon.vim25.mo.ServiceInstance;
import com.sugon.vim25.mo.VirtualMachine;



@Service("monitor-historyPerfAndAlertServiceImpl")
public class HistoryPerfAndAlertServiceImpl  implements HistoryPerfAndAlertServiceI{
	
	static int perfInterval = 1800; // 30 minutes for PastWeek

    @Qualifier("monitor-connection")
	@Autowired
	private Connection connection;

	private VCenterManageUtils opUtil;
	
	@Override
	public Map<String, List<MetricValue>> get24HPerformData(String name,
			String Type) {
			
		
		PerformanceManager perfMgr =null;
		//最终返回的的所有指标的所有采样值的列表
		Map<String, List<MetricValue>> returnMapList = new HashMap<String, List<MetricValue>>();
		HashMap<Integer, String> keyMap = new HashMap<Integer, String>();
		
		//判断是物理机还是虚拟机类型,并获取性能数据
		PerfEntityMetric perfData =null;
		if(Type=="HostSystem"){
			HostSystem host=null;
			try {
				host = (HostSystem) new InventoryNavigator(connection.getSi().getRootFolder()).searchManagedEntity("HostSystem", name);
			} catch (InvalidProperty e) {
				e.printStackTrace();
			} catch (RuntimeFault e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
			try {
				ServiceInstance si= connection.getSi();
				perfMgr = si.getPerformanceManager();
				perfData = getPerformanceContent(si,perfMgr,host, keyMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (Type=="VirtualMachine") {
			VirtualMachine host=null;
			try {
				host = (VirtualMachine) new InventoryNavigator(connection.getSi().getRootFolder()).searchManagedEntity("VirtualMachine", name);
			} catch (InvalidProperty e) {
				e.printStackTrace();
			} catch (RuntimeFault e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			try {
				ServiceInstance si= connection.getSi();
				perfMgr = si.getPerformanceManager();
				perfData = getPerformanceContent(si,perfMgr,host, keyMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	

		if (perfData == null) {
			return null;
		}

		PerfMetricSeries[] perfDataValues = perfData.value;
		PerfSampleInfo[] listinfo = perfData.sampleInfo;
		
		//获取设备的所有指标的采样值
		for(int i=0;i<perfDataValues.length;i++){
			
			//具体某个指标的所有采样列表
			List<MetricValue> metricList=new  ArrayList<MetricValue>();
			PerfMetricSeries  perfDataValue=perfDataValues[i];
			if (perfDataValue instanceof PerfMetricIntSeries) {
				long[] values = ((PerfMetricIntSeries) perfDataValue).value;
				
				//获取所有指标的所有采样值
				for(int j=0;j<values.length;j++){
					
					int id = perfDataValue.id.counterId;
					String value = String.valueOf(values[j]);
					String keyString = keyMap.get(id);
					SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd HH:mm");
					String time1 = sdf1.format(listinfo[j].timestamp.getTime());
					
			
					// CPU信息
					if ("cpu_usage".equals(keyString)) {
						/*if (instance == null || instance.equals("")) */{		
						    //单个采样值数据结构
							MetricValue mV=new MetricValue();
							//设置时间和值
							mV.setCollectTime(time1);
							//cpu利用率需要除以100，坑啊
							 DecimalFormat df2 = new DecimalFormat("###0.00");
							mV.setMetricValue(df2.format(Double.valueOf(value)/100.0));
							metricList.add(mV);
							returnMapList.put("CPU_RATE", metricList);
						}
					}
					// 内存信息
					else if ("mem_usage".equals(keyString)) {		
					    //单个采样值数据结构
						MetricValue mV=new MetricValue();
						//设置时间和值
						mV.setCollectTime(time1);
						//mem利用率需要除以100
						 DecimalFormat df2 = new DecimalFormat("###0.00");
						mV.setMetricValue(df2.format(Double.valueOf(value)/100.0));
						metricList.add(mV);
						returnMapList.put("MEM_RATE", metricList);
					} 
					// 网卡信息
				  else if ("net_usage".equals(keyString)) {
						    //单个采样值数据结构
							MetricValue mV=new MetricValue();
							//设置时间和值
							mV.setCollectTime(time1);
							mV.setMetricValue(value);
							metricList.add(mV);
							returnMapList.put("NIC_AVG", metricList);
			
					} 
					// 存储信息
					else if ("disk_usage".equals(keyString)) {
					    //单个采样值数据结构
						MetricValue mV=new MetricValue();
						//设置时间和值
						mV.setCollectTime(time1);
						mV.setMetricValue(value);
						metricList.add(mV);
						returnMapList.put("DSK_USAGE", metricList);
					} 
					
				}
			
			}
		}

	/*	for(String aa:returnMapList.keySet()){
		     for(MetricValue mv:	returnMapList.get(aa)){
		    	 
		    		//System.out.println(aa  + "\t" +mv.getCollectTime()  + "\t" + mv.getMetricValue());
		     }
		}*/
		
		return returnMapList;	
	}

	
	/**
	 * 获取指定对象的全部实时的性能数据
	 *
	 * @param perfMgr
	 * @param entity
	 * @param idslist
	 * @return
	 * @throws RuntimeFault
	 * @throws RemoteException
	 */
	private   PerfEntityMetric getPerformanceContent(ServiceInstance si,PerformanceManager perfMgr,
			ManagedEntity entity, Map<Integer, String> valueKey)
			throws Exception {

		try {
			PerfMetricId[] perfMetricIds = perfMgr.queryAvailablePerfMetric(
					entity, null, null, perfInterval);

			PerfQuerySpec qSpec = new PerfQuerySpec();
			qSpec.setEntity(entity.getMOR());// 观察对象
			// qSpec.setStartTime(xmlgc2Before);
			// qSpec.setEndTime(xmlgc1Before);

			qSpec.setMetricId(perfMetricIds);
			
			//by duan
			Calendar curTime =si.currentTime();
		    Calendar startTime = (Calendar) curTime.clone();
		    // startTime.roll(Calendar.DATE, -1); //这是个坑，2月1号减1，变为2月31号
		    startTime.set(Calendar.DATE, startTime.get(Calendar.DATE) - 1);
			qSpec.setStartTime(startTime);
		/*	Calendar endTime = (Calendar) curTime.clone();
			endTime.roll(Calendar.DATE,0);
			qSpec.setEndTime(endTime);*/
			//by duan

			// 设置了这个参数不是20，setMaxSample都不管用了
			// 超出了建立虚拟机的时间，不返回-1，直接空着
			qSpec.setIntervalId(perfInterval);// 20 300 1800 7200 86400
		//	qSpec.setMaxSample(1);// 最大样品数 =1，只取一个最新的
			qSpec.setFormat(PerfFormat.normal.toString());

			PerfQuerySpec[] qSpecs = new PerfQuerySpec[1];
			qSpecs[0] = qSpec;
			PerfEntityMetricBase[] values = perfMgr.queryPerf(qSpecs);

			if (values == null || values.length == 0) {
				throw new Exception(
						"Get Realtime Proformance Data fault");
			}

			int[] idsGroup = new int[perfMetricIds.length];
			for (int i = 0; i < perfMetricIds.length; i++) {
				idsGroup[i] = perfMetricIds[i].getCounterId();
			}

			PerfCounterInfo[] pcinfolist = perfMgr.queryPerfCounter(idsGroup);
			for (int i = 0; i != pcinfolist.length; ++i) {
				int perfConterInfoKey = pcinfolist[i].key;
				String groupInfoKey = pcinfolist[i].groupInfo.key;
				String nameInfoKey = pcinfolist[i].nameInfo.key;
				valueKey.put(perfConterInfoKey, groupInfoKey + "_"
						+ nameInfoKey);
			}
			return (PerfEntityMetric) values[0];

		} catch (Exception e) {
			throw new Exception(
					"Get Realtime Proformance Data fault", e);
		}

	}


	@Override
	public List<AlertEvent> getAlertEventByType(String Type, String name,
			int number)  {
			EventManager evtMgr = connection.getSi().getEventManager();
			List<AlertEvent> retunAlertEvents=	new ArrayList<AlertEvent>();
			if(evtMgr!= null)
			{
				EventFilterSpec eventFilter =  new EventFilterSpec();					
				EventHistoryCollector ehc = null;
				try {
					ehc = evtMgr.createCollectorForEvents(eventFilter);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				try {
					ehc.resetCollector();
				}catch (Exception e) {
					try {
						ehc.destroyCollector();
					}catch (Exception e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
			
		
				int typeNumber=0;
				
				if(Type.equals(SystemResourceType.dataCenter)){		 //数据中心
					typeNumber=1;
				}else if(Type.equals(SystemResourceType.computerResource)){  //集群
					typeNumber=2;
				}else if(Type.equals(SystemResourceType.hostSystem)){	        //主机
					typeNumber=3;
				}else if(Type.equals(SystemResourceType.virtualMachine)){  //虚拟机
					typeNumber=4;
				}
				
				int totalEvent=0;
				while( true)
				{
					 if(totalEvent  >= number)
                     { 
						 //跳出外层while循环
						 break;
                     }				
					 
					Event[] events = null;
					try {
						//最多一次性读取1000条数据
						events = ehc.readPreviousEvents(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}					
					if(events== null)
					{
						break;
					}

					for( int i=0; i<events.length; i++)
					{
						 if(totalEvent  >= number)
	                     {
							 //跳出里层for循环
							 break;
	                     }
	                    Event evt=events[i];
	                    switch(typeNumber){
	                    case 1:
	                        if(events[i].getDatacenter()!=null){
		          				if( events[i].getDatacenter().getName() .contains(name)){
		          					AlertEvent aEvent=	getAlertEventObject( evt,Type,  name);
		  	       					retunAlertEvents.add(aEvent);
		  	       					totalEvent++;
		  	       					
		  	          				}
		  	                      }
		          					
	                    	break;
	                    case 2:
	                    	 if(events[i].getComputeResource()!=null){
			          				if( events[i].getComputeResource().getName() .contains(name)){
			          					AlertEvent aEvent=	getAlertEventObject( evt,Type,  name);
			  	       					retunAlertEvents.add(aEvent);
			  	       					totalEvent++;
			  	       					
			  	          				}
			  	                      }
	                    	break;
	                    case 3:
	                    	 if(events[i].getHost()!=null){
			          				if( events[i].getHost().getName() .contains(name)){
			          					AlertEvent aEvent=	getAlertEventObject( evt,Type,  name);
			  	       					retunAlertEvents.add(aEvent);
			  	       					totalEvent++;
			  	       					
			  	          				}
			  	                      }
	                    	break;
	                    case 4:
	                    	 if(events[i].getVm()!=null){
			          				if( events[i].getVm().getName() .contains(name)){
			          					AlertEvent aEvent=	getAlertEventObject( evt,Type,  name);
			  	       					retunAlertEvents.add(aEvent);
			  	       					totalEvent++;
			  	       					
			  	          				}
			  	                      }
	                    	break;
	                    case 0:
	                    	return null;	
	                    }              
					
				}
				
				}
				//销毁ehc对象
				try {
					ehc.destroyCollector();
				}catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		
			
			return retunAlertEvents;
	}
	
    AlertEvent getAlertEventObject(Event evt,String Type, String name){
		
		AlertEvent aEvent=	new AlertEvent();
		
		//System.out.println("EventId:" + evt.getKey());
		aEvent.setEventId(String.valueOf(evt.getKey()));
		
		//System.out.println("资源名称:" + name);
		aEvent.setName(name);
	
		//System.out.println("资源类型:" +Type);
		aEvent.setType(Type);
		
		//System.out.println("Time:" + evt.getCreatedTime().getTime());
		String dateStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(evt.getCreatedTime().getTime());
		aEvent.setTime(dateStr);
		
		//System.out.println("事件级别:" + evt.getDynamicType());
		aEvent.setLevel(evt.getDynamicType());
		
		//System.out.println("事件内容:"+ evt.getFullFormattedMessage());
		aEvent.setContent( evt.getFullFormattedMessage());

		return  aEvent;
			
	}

    public AlarmEntity createAlarmEntity(AlarmState as, ServerConnection conn){
    	//业务对象
    	AlarmEntity ae = new AlarmEntity();
		//内建对象
		Alarm alarm = this.getOpUtil().getAlarmByMor(as.getAlarm());
		AlarmInfo ai = alarm.getAlarmInfo();			
		if(ai == null){
			return null;
		}
		ae.setId(alarm.getMOR().getVal());
		ae.setKey(as.getKey());
		ae.setName(ai.getName());
		ae.setDescription(ai.getDescription());
		
		//UTC转CST 加8小时
		Calendar cstCalendar = as.getTime();  
		if(cstCalendar!=null){
			cstCalendar.add(Calendar.HOUR, +8);  
		}
		ae.setTime(ToolsUtils.getCalendarAsString(cstCalendar, 2));
		ae.setEntityId(as.getEntity().getVal());
		ManagedEntity me = new ManagedEntity(conn,as.getEntity());
		ae.setEntityName(me.getName());
		
		//UTC转CST 加8小时
		Calendar cstCalendar1= as.getAcknowledgedTime();  
		if(cstCalendar1!=null){
			cstCalendar1.add(Calendar.HOUR, +8);  
		}
	
		ae.setAcknowledgedTime(ToolsUtils.getCalendarAsString(cstCalendar1,2));
		ae.setAcknowledgedUser(as.getAcknowledgedByUser());
		ae.setAcknowledged(as.getAcknowledged());
		ae.setLevel(ToolsUtils.convertManageObjectStatusToString(as.getOverallStatus()));
		
		return ae;
    }

    
	@Override
	public List<AlarmEntity> getTriggeredAlarms(ManagedEntity entity) {
		// TODO Auto-generated method stub
		//HostSystem host = (HostSystem)entity;
		List<AlarmEntity> alarmsReturn = new ArrayList<AlarmEntity>();
		AlarmState[] alarmstates = entity.getTriggeredAlarmState();
		if(alarmstates != null && alarmstates.length > 0){
			for(AlarmState as : alarmstates){
				//业务对象
				AlarmEntity ae = createAlarmEntity(as,entity.getServerConnection());
				//ae.setEntityName(entity.getName());
				if(ae != null){
					alarmsReturn.add(ae);
				}								
			}
		}
		//按告警发生的时间降序排列
		Collections.sort(alarmsReturn, new Comparator<AlarmEntity>(){			
			@Override
			public int compare(AlarmEntity o1, AlarmEntity o2) {
				// TODO Auto-generated method stub
				if(o1.getTime().compareTo(o2.getTime()) > 0){
					return -1;
				}else if (o1.getTime().compareTo(o2.getTime()) < 0){
					return 1;
				}
				return 0;
			}
					
		});
		return alarmsReturn;
	}


	@Override
	public List<AlarmEntity> getAllAlarms(ManagedEntity entity) {
		// TODO Auto-generated method stub
		//HostSystem host = (HostSystem)entity;
		List<AlarmEntity> alarmsReturn = new ArrayList<AlarmEntity>();
		AlarmState[] alarmstates = entity.getDeclaredAlarmState();
		if(alarmstates != null && alarmstates.length > 0){
			for(AlarmState as : alarmstates){
				//业务对象
				AlarmEntity ae = createAlarmEntity(as,entity.getServerConnection());
				//ae.setEntityName(entity.getName());
				if(ae != null){
					alarmsReturn.add(ae);
				}								
			}
		}
		//按告警发生的时间降序排列
		Collections.sort(alarmsReturn, new Comparator<AlarmEntity>(){			
			@Override
			public int compare(AlarmEntity o1, AlarmEntity o2) {
				// TODO Auto-generated method stub
				if(o1.getTime().compareTo(o2.getTime()) > 0){
					return -1;
				}else if (o1.getTime().compareTo(o2.getTime()) < 0){
					return 1;
				}
				return 0;
			}
							
		});		
		return alarmsReturn;
	}
	
	/**
	 * @return the opUtil
	 */
	public VCenterManageUtils getOpUtil() {
		//if (this.opUtil == null)
		this.opUtil = new VCenterManageUtils(this.connection);
		return opUtil;
	}
	
}
