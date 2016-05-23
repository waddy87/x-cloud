package org.waddys.xcloud.monitor.serviceImpl.service;

import java.util.List;
import java.util.Map;

import org.waddys.xcloud.monitor.bo.AlarmEntity;
import org.waddys.xcloud.monitor.bo.AlertEvent;
import org.waddys.xcloud.monitor.bo.MetricValue;
import org.waddys.xcloud.monitor.serviceImpl.entity.Cluster;
import org.waddys.xcloud.monitor.serviceImpl.entity.ClusterUI;
import org.waddys.xcloud.monitor.serviceImpl.entity.DataCenter;
import org.waddys.xcloud.monitor.serviceImpl.entity.Host;
import org.waddys.xcloud.monitor.serviceImpl.entity.VM;
import org.waddys.xcloud.monitor.serviceImpl.util.Connection;

import com.vmware.vim25.mo.ManagedEntity;

import net.sf.json.JSONObject;


public interface ServiceI {

    /**
     * 功能: 登录接口
     * 
     * @return
     */
  /*  String login(String userName, String password, boolean isSSO);*/

	 //获取虚拟机基本信息
	 VM  getVmInfo(String name);
	 VM  getVmInfoEx(String vmId);
	 
	 //获取物理机基本信息
	 Host getHostInfo(String id);
	 Host getHostInfoEx(String hostId);
	 
	 //获取集群基本信息
	 Cluster getClusterInfo(String id);
	 
	 //获取集群列表
	 List<Cluster> getClustersList( );
	 
	 //获取数据中心信息
	 DataCenter getDataCenterInfo();

	 JSONObject getClusterInfo(ClusterUI cu);
	 
    // 用于单个群集界面展示的查询接口
    ClusterUI getAllClusterUIInfo();

    // 用于全部群集界面展示的查询接口
    ClusterUI getClusterUIInfo(String name);
	
	 //获取告警信息
	 List<AlertEvent>  getAlertEventByType(String Type,String name, int number);
	 
	//获取所有虚拟机的通用数据降序TopN(cpu利用率、内存利用率、存储io速率、网络io速率,cpu Mhz、内存的容量、磁盘的iops、存储利用率),
	//同时也会返回虚拟机列表信息，
	 JSONObject getVMTopN(int n);
	 
	 //批量获取虚拟机信息
	 JSONObject getVMTopNBatch(int n);
	 
	//获取所有物理机的通用数据降序TopN(cpu利用率、内存利用率、存储io速率、网络io速率,cpu Mhz、内存的容量、磁盘的iops、存储利用率),
	//同时也会返回主机列表信息，
	 JSONObject getHostTopN(int n);
	 
	 //批量获取主机信息
	 JSONObject getHostTopNBatch(int n);
	 
	 //获取Host的json对象
	 JSONObject getHostInfoJson(String hostId);
	
	 //获取VM的json对象
	 JSONObject getVmInfoJson(String vmId) ;
	 
	 //获取指定集群id下的物理机的通用数据降序TopN(cpu利用率、内存利用率、存储io速率、网络io速率,cpu Mhz、内存的容量、磁盘的iops、存储利用率),
	 //同时也会返回主机列表信息，主机列表的key以特定串作为前缀
	 Map<String, Map<String,String>> getHostTopN(int n,String clusterId);
	 
	 //获取物理机的通用数据降序TopN(cpu利用率、内存利用率、存储io速率、网络io速率) 
	 Map<String, Map<String,String>> getHostCommonTopN(int n,String clusterId);
	  
	 //获取物理机的通用数据降序TopN(cpu Mhz、内存的容量、磁盘的iops、存储利用率) 
	 // Map<Metric, Map<hostName,Value>>
	 Map<String, Map<String,String>> getHostOtherTopN(int n,String clusterId);
	 
	 // 获取虚拟机的通用数据降序TopN(cpu利用率、内存利用率、存储io速率、网络io速率) 
	 Map<String, Map<String,String>> getVmCommonTopN(int n,String clusterId);
	  
	 //获取虚拟机的通用数据降序TopN(cpu Mhz、内存的容量、磁盘的iops、存储利用率) 
	 Map<String, Map<String,String>> getVmOtherTopN(int n,String clusterId);

	// 获取某个主机上虚拟机的通用数据降序TopN(cpu利用率、内存利用率、存储io速率、网络io速率) 
	 Map<String, Map<String,String>> getVmCommonTopNOnHost(int n,String hostId);
			
	//获取某个主机上虚拟机的通用数据降序TopN(cpu Mhz、内存的容量、磁盘的iops、存储利用率) 
	 Map<String, Map<String,String>> getVmOtherTopNOnHost(int n,String hostId);
	 
	 //获取物理机、虚拟机24小时性能数据
	 // Map<Metric,List<value>>
	 Map<String,List<MetricValue>> get24HPerformData(String id,String Type);

	 //设置serviceInstance连接接口
	void setServiceInstance(Connection con);
	 
	public List<AlarmEntity> getAllTriggeredAlarms();
}
