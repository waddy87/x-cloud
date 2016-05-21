package org.waddys.xcloud.alert.serviceImpl.service.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.waddys.xcloud.alert.service.bo.AlarmEntity;

import com.sugon.vim25.AlarmExpression;
import com.sugon.vim25.AlarmInfo;
import com.sugon.vim25.AlarmState;
import com.sugon.vim25.AndAlarmExpression;
import com.sugon.vim25.EventAlarmExpression;
import com.sugon.vim25.MetricAlarmExpression;
import com.sugon.vim25.OrAlarmExpression;
import com.sugon.vim25.PerfCounterInfo;
import com.sugon.vim25.StateAlarmExpression;
import com.sugon.vim25.mo.Alarm;
import com.sugon.vim25.mo.AlarmManager;
import com.sugon.vim25.mo.ManagedEntity;
import com.sugon.vim25.mo.PerformanceManager;
import com.sugon.vim25.mo.ServerConnection;
import com.sugon.vim25.mo.ServiceInstance;

/**
 * 功能名: 请填写功能名
 * 功能描述: 请简要描述功能的要点
 * Copyright: Copyright (c) 2016
 * 公司: 曙光云计算技术有限公司
 *
 * @author yangkun
 * @version 2.0.0 sp1
 */

@Service
public class AlertMannagerUtils{
	
	 private static final Logger logger = LoggerFactory
	            .getLogger(AlertMannagerUtils.class);
	
    public AlarmEntity createAlarmEntity(VCenterManageUtils vUtils,AlarmState as, ServerConnection conn){
    	//业务对象
    	AlarmEntity ae = new AlarmEntity();
		//内建对象
		Alarm alarm = vUtils.getAlarmByMor(as.getAlarm());
		AlarmInfo ai = alarm.getAlarmInfo();		
		
		//将告警表达式转换为字符串
		String alertExprString="";
		try {
			alertExprString=getAlertExprString(conn.getServiceInstance(),ai.getExpression(),ai.getName());
		} catch (Exception e) {
			logger.debug("获取告警表达式异常:"+e);
		}
		ae.setTriggerDetail(alertExprString);
		ae.setId(alarm.getMOR().getVal());
		ae.setKey(as.getKey());
		ae.setName(ai.getName());
		ae.setDescription(ai.getDescription());
		Calendar cstCalendar = as.getTime();  
		ae.setTime(cstCalendar.getTime().getTime());
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

    
	
	public List<AlarmEntity> getTriggeredAlarms(VCenterManageUtils vUtils,ManagedEntity entity) {
		// TODO Auto-generated method stub
		//HostSystem host = (HostSystem)entity;
		List<AlarmEntity> alarmsReturn = new ArrayList<AlarmEntity>();
		AlarmState[] alarmstates = entity.getTriggeredAlarmState();
		if(alarmstates != null && alarmstates.length > 0){
			for(AlarmState as : alarmstates){
				//业务对象
				AlarmEntity ae = createAlarmEntity(vUtils,as,entity.getServerConnection());
				//ae.setEntityName(entity.getName());
				if(ae != null){
					alarmsReturn.add(ae);
				}								
			}
		}
		//按告警发生的时间降序排列
		Collections.sort(alarmsReturn, new Comparator<AlarmEntity>(){			
			
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


	//获取告警触发器对应的告警
	public List<AlarmEntity> getTriggerAlarms(VCenterManageUtils vUtils,ManagedEntity entity) {
		// TODO Auto-generated method stub
		//HostSystem host = (HostSystem)entity;
		List<AlarmEntity> alarmsReturn = new ArrayList<AlarmEntity>();
		AlarmState[] alarmstates = entity.getDeclaredAlarmState();
		if(alarmstates != null && alarmstates.length > 0){
			for(AlarmState as : alarmstates){
				//业务对象
				AlarmEntity ae = createAlarmEntity(vUtils,as,entity.getServerConnection());
				//ae.setEntityName(entity.getName());
				if(ae != null){
					alarmsReturn.add(ae);
				}								
			}
		}
		//按告警发生的时间降序排列
		Collections.sort(alarmsReturn, new Comparator<AlarmEntity>(){			
			
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
	
	
	public String getMetricDesByCountId(ServiceInstance si, int countId)
			throws Exception {
		PerformanceManager perfMgr = si.getPerformanceManager();
		int[] counterIds = { countId};
		PerfCounterInfo[] perfCounterInfos = perfMgr.queryPerfCounter(counterIds);
		PerfCounterInfo perfCounterInfo = perfCounterInfos[0];
		String reString=perfCounterInfo.getGroupInfo().getLabel()
				+ perfCounterInfo.getNameInfo().getLabel() + "("
				+ perfCounterInfo.getUnitInfo().getLabel() + ")";
		return reString;

	}

	@SuppressWarnings("unused")
	public  String getAlertExprString(ServiceInstance si,AlarmExpression alExpression,String alertName) throws Exception {	
		
		String reslutString="";
		if (alExpression instanceof MetricAlarmExpression) {
			StringBuilder sBuilder = new StringBuilder();
			MetricAlarmExpression mae = (MetricAlarmExpression) alExpression;
			sBuilder.append("性能指标告警规则为:\n");
			//黄色警告
			sBuilder.append("黄色警告触发规则:"+ ((mae.getYellowInterval() == null) ? 0 : mae
							.getYellowInterval()) + "秒的");
			String metricNameString=getMetricDesByCountId( si,mae.getMetric().getCounterId());
			sBuilder.append(metricNameString);
			String operString=null;
			if(mae.getOperator().equals(mae.getOperator().isAbove) ){
				operString="大于";
			}else{
				operString="小于";
			}
			sBuilder.append(operString);
			sBuilder.append(mae.getYellow()+"\n");
			//红色警告
			sBuilder.append("\n红色警告触发规则:"+ ((mae.getRedInterval()== null) ? 0 : mae
					.getRedInterval()) + "秒的");
			sBuilder.append(metricNameString);
			sBuilder.append(operString);
			sBuilder.append(mae.getRed()/100 +"\n");
			return sBuilder.toString();

		} else if (alExpression instanceof StateAlarmExpression) {
			StateAlarmExpression mae = (StateAlarmExpression) alExpression;
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("状态异常告警规则为:\n");
			//黄色警告
		   sBuilder.append("黄色警告触发规则:"+ alertName);
		   String operString=null;
		   if(mae.getOperator().equals(mae.getOperator().isEqual) ){
			   operString="等于";
			}else{
				operString="不等于";
			}
		   sBuilder.append(operString);
		   sBuilder.append(mae.getYellow()+"\n");
			
		   //红色警告
			sBuilder.append("\n红色警告触发规则:");
			sBuilder.append(alertName);
			sBuilder.append(operString);
			sBuilder.append(mae.getRed()+"\n");
			return sBuilder.toString();
		} else if (alExpression instanceof EventAlarmExpression) {
			EventAlarmExpression sae = (EventAlarmExpression) alExpression;
		
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("事件触发警告条件为:\n");
			sBuilder.append(sae.getEventTypeId());
			sBuilder.append(sae.getEventType());
			return sBuilder.toString();
		} else if (alExpression instanceof AndAlarmExpression) {
			AndAlarmExpression sae = (AndAlarmExpression) alExpression;
			AlarmExpression [] exps=sae.getExpression();
			reslutString=reslutString+"发生以下全部条件时触发警告:\n";
			for (int i = 0; exps != null && i < exps.length; i++) {
				reslutString=reslutString +getAlertExprString(si,exps[i], alertName);
			}
		} else if (alExpression instanceof OrAlarmExpression) {
			OrAlarmExpression sae = (OrAlarmExpression) alExpression;
			AlarmExpression [] exps=sae.getExpression();
			 reslutString= reslutString+"发生以下条件之一时触发警告:\n";
			for (int i = 0; exps != null && i < exps.length; i++) {
				reslutString=reslutString + getAlertExprString(si,exps[i], alertName);
			}
		}
		return reslutString;
	}
	
	/*
	 * 告警确认，设置告警确认、告警确认时间、告警确认用户
	 * 
	 */
	public Map<String, Object> aquireAlert(VCenterManageUtils vUtils,String resId,String triggerId) throws Exception {
		
		Map <String,Object >retMap=new HashMap<String, Object>();
		retMap.put("ret", false);
		ManagedEntity entity=null;
		Calendar calendar=Calendar.getInstance();
		
		try {
			if(resId.contains("domain")){
				entity=vUtils.getClusterComputeResourceById(resId);
			}else if(resId.contains("host")){
				entity=vUtils.getHostSystemById(resId);
			}else if(resId.contains("vm")){
				entity=vUtils.getVirtualMachineById(resId);
			}else if (resId.contains("datastore")) {
				entity=vUtils.getDatastoreById(resId);
			}
			if(entity==null){
				logger.debug("告警资源不存在，告警确认失败！");
				//throw new Exception("告警资源不存在，告警确认失败！");
			}
		} catch (Exception e1) {
			logger.debug("告警资源不存在，告警确认失败！");
			//throw new Exception("告警资源不存在，告警确认失败！");
		}
		
		try {
			AlarmState[] alarmstates = entity.getTriggeredAlarmState();
			if(alarmstates!=null && alarmstates.length>0){
				loop :for(int i=0;i<alarmstates.length;i++){
					String alarmIdString=alarmstates[i].getAlarm().getVal();
					if(alarmIdString.contains(triggerId)){
						try{
							Alarm alarm = new Alarm(vUtils.getSi().getServerConnection(), alarmstates[i].getAlarm());
							AlarmManager  am=vUtils.getSi().getAlarmManager();
							am.acknowledgeAlarm(alarm, entity);
							retMap.put("ret", true);
							retMap.put("time", calendar.getTime());
							retMap.put("user", "Cloudview");
						   break loop;
						} catch (Exception e) {
							logger.debug("告警确后台操作失败！");
							throw new Exception("告警确后台操作失败！");
						} 
					}
				}
			}
		} catch (Exception e) {
			logger.debug("获取告警状态失败！");
			//throw new Exception("获取告警状态失败！");
		} 
	
		return 	retMap;

	}

}
