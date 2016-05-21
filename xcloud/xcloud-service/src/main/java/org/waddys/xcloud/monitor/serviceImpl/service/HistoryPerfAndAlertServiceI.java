package org.waddys.xcloud.monitor.serviceImpl.service;

import java.util.List;
import java.util.Map;

import org.waddys.xcloud.monitor.service.bo.AlarmEntity;
import org.waddys.xcloud.monitor.service.bo.AlertEvent;
import org.waddys.xcloud.monitor.service.bo.MetricValue;

import com.sugon.vim25.mo.ManagedEntity;


public interface HistoryPerfAndAlertServiceI {
	 
	/* 
	 * 获取物理机、虚拟机24小时性能数据
	 * @param String 指标名称 (包括 cpu利用率、内存利用率、磁盘io速率、网络io速率)
	 * @param List<MeritcValue> 指标值列表，包含采集时间和采集value
	 * 
	 * @return 返回24小时指标列表   
	 */
	 Map<String,List<MetricValue>> get24HPerformData(String name,String Type);

	 
	 /*
	  *获取告警信息列表
	  *@param String Type 查询对象类型 (Datacenter、Cluster、Host、VirtualMachine)
	  *@param String name 查询对象名称，如10.0.36.121
	  *@number String   返回的条数；如果是页面直接加载的填0(使用默认返回条数，保证查询性能)，更新调用的建议10条左右
	  *@return  List<AlertEvent>  返回告警事件列表
	  */
	
	 List<AlertEvent>  getAlertEventByType(String Type,String name,int number);
	 
	 
	 /**
	  * 返回指定管理对象上已触发的告警
	  * @param entity   管理对象，例如物理机，虚拟机，资源池等
	  * @return  告警实体的列表
	  */
	 List<AlarmEntity> getTriggeredAlarms(ManagedEntity entity);
	 
	 /**
	  * 返回指定管理对象上所有已申明的告警
	  * @param entity
	  * @return
	  */
	 List<AlarmEntity> getAllAlarms(ManagedEntity entity);
}
