package org.waddys.xcloud.vijava.api;

import java.util.List;
import java.util.Map;

import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.ServiceInstance;

/**
 * 集群操作接口
 * 
 * @category see
 * @author
 */

public interface ClusterI {
	
	
	/***
	 * 
	 * 获取ServiceInstance
	 * @return ServiceInstance
	 **/
	ServiceInstance getSi();
	/***
	 * 
	 * 设置ServiceInstance
	 * @param ServiceInstance
	 **/
	void setSi(ServiceInstance si);

	/**
	 * 获取集群信息
	 * 1. 失败，返回null，并抛出异常
	 * 2. 成功， 返回ClusterComputeResource对象 
	 * 
	 *  
	 * @param String moid 集群的moid
	 * @return ClusterComputeResource对象
	 *///获取 集群信息 HA DRS DC等
 public ClusterComputeResource getClusterByMoid(String clusterid);
	/**
	 * 获取集群信息
	 * 1. 失败，返回null，并抛出异常
	 * 2. 成功， 返回一个map  包含ha DRS状态 数据中心及主机、虚拟机信息。 
	 * 
	 *  
	 * @param String moid 集群的moid
	 * @return true/false 
	 *///获取 集群信息 HA DRS DC等
 public Map<String,Object> getClusterInfo(String moid);
 /**
	 * 设置Ha状态
	 * 1. 失败，返回false，并抛出异常
	 * 2. 成功， 返回true。 
	 *
	 *  
	 * @param boolean true 表示开 false 表示关
	 * @return true/false 
	 *///设置HA状态 
 public void setHaStatus(boolean hastatus);
 /**
	 * 设置DRS状态
	 * 1. 失败，返回false，并抛出异常
	 * 2. 成功， 返回true。 
	 *
	 * @param boolean true 表示开 false 表示关
	 * @return true/false 
	 *///设置DRS状态
 public void setDRSStatus(boolean drsstatus);
 /**
	 * 向集群中添加主机
	 * 1. 失败，返回false，并抛出异常
	 * 2. 成功， 返回true。 
	 * 
	 *  
	 * @param String 集群的moid  String hostid 主机的moid
	 * @return true/false 
	 *///添加主机
  public boolean addHost(String moid,String hostid);
  /**
	 * 从集群中移除主机
	 * 1. 失败，返回false，并抛出异常
	 * 2. 成功， 返回true。 
	 * 
	 *  
	 * @param String 集群的moid  String hostid 主机的moid
	 * @return true/false 
	 *///移除主机
  public boolean removeHost(String moid,String hostid);	
  /**
	 * 获取资源池列表
	 * 1. 失败，返回null，并抛出异常
	 * 2. 成功， 返回资源池的List。 
	 * 
	 *  
	 * @param String 集群的moid
	 * @return true/false 
	 *///获得资源池列表
  public <ResourcePool>List  getResourcePoolList(String moid);
  /**
	 * 在集群中创建资源池
	 * 1. 失败，返回false，并抛出异常
	 * 2. 成功， 返回true。 
	 * 
	 *  
	 * @param String 集群的moid ResourcePool rp 要创建资源池的配置信息
	 * @return true/false 
	 *///创建资源池
  public boolean createResourcePool(String moid,ResourcePool rp);
  /**
	 * 获取集群中的主机列表
	 * 1. 失败，返回null，并抛出异常
	 * 2. 成功， 返回主机的List。 
	 * 
	 *  
	 * @param String 集群的moid
	 * @return true/false 
	 *///获得主机列表
  public <HostSystem>List  getHostList(String moid);
  /**
	 * 获取集群中的虚拟机列表
	 * 1. 失败，返回null，并抛出异常
	 * 2. 成功， 返回虚拟机的List。 
	 * 
	 *  
	 * @param String 集群的moid
	 * @return true/false 
	 *///获得虚拟机列表
  public <HostSystem>List  getVirtualMachineList(String moid);

}
