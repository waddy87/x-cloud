package org.waddys.xcloud.vijava.api;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import com.sugon.vim25.HostHardwareInfo;
import com.sugon.vim25.HostListSummary;
import com.sugon.vim25.HostVirtualSwitch;
import com.sugon.vim25.InvalidProperty;
import com.sugon.vim25.RuntimeFault;
import com.sugon.vim25.mo.Datacenter;
import com.sugon.vim25.mo.HostSystem;
import com.sugon.vim25.mo.ManagedEntity;
//批量操作     拆分功能     对照vimstub接口列表
import com.sugon.vim25.mo.ServiceInstance;

/**
 * 主机操作接口
 * 
 * @category see
 * @author
 */
public interface HostI {
	/**
	 * 查询集合，模糊查询
	 * 1. 如果参数为空，返回所有的主机
	 * ? vijava提供哪些查询接口
	 * ？根据哪些条件查询
	 * SearchIndex 提供简单findxxxx
	 * @return 满足条件的主机
	 */
	public List<HostSystem> getHostSystemList() 
			throws RuntimeFault, RemoteException;
	
	
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
	 * 精确查询
	 * 1. 如果参数为空，返回所有的主机
	 * ? vijava提供哪些查询接口
	 * ？根据哪些条件查询
	 * SearchIndex 提供简单findxxxx
	 * 检索条件 可以由Ip Name 等 
	 * vmSearch false的时候 搜索的是主机
	 * @param主机
	 * @return 满足条件的主机
	 */
	public HostSystem getHostSystemByMoid(String Moid)
			throws RuntimeFault, RemoteException;
	
	/**
	 * 注册主机
	 * 1. 注册失败，返回null，并抛出异常
	 * 2. 注册成功 返回HostSystem对象
	 * ? vijava是否提供注册接口
	 * 
	 *  从HostSystem Folder DataCenter Cluster 这几个地方暂时没有找到注册的入口
	 * @param ipAddr 主机ip地址 uname 注册主机的用户名 pwd 注册主机的密码
	 * @return null或HostSystem对象
	 */
	public HostSystem registerHost(String ipAddr,String uname,String pwd)throws RuntimeFault, RemoteException;
	/**
	 * 注销主机
	 * 1. 注销主机失败，返回false，并抛出异常
	 * 2. 注销主机成功， 返回true。 
	 * ? vijava是否提供注销接口
	 * 
	 *  从HostSystem Folder DataCenter Cluster 这几个地方暂时没有找到注销的入口
	 * @param 要注销主机的moid
	 * @return 是否注销成功
	 */
	public boolean removeHost(String moid)throws RuntimeFault, RemoteException;
	//编辑主机信息
	//关键找到 主机可编辑的信息
	public boolean editHost()throws RuntimeFault, RemoteException;
	/**
	 * 关闭主机
	 * 1. 失败，返回false，并抛出异常
	 * 2. 成功， 返回true。 
	 * ? vijava如何开机  连接 
	 * 可通过Host关机 重启 维护 取消维护 断开连接
	 *  
	 * @param String
	 * @return true/false 
	 */
	public boolean shutdownHost(String moid)throws RuntimeFault, RemoteException;
	/**
	 * 重新启动主机
	 * 1. 失败，返回false，并抛出异常
	 * 2. 成功， 返回true。 
	 * ? vijava如何开机  连接 
	 * 可通过Host关机 重启 维护 取消维护 断开连接
	 *  
	 * @param String
	 * @return true/false 
	 */
	public boolean restartHost(String moid)throws RuntimeFault, RemoteException;
	/**
	 * 断开主机连接
	 * 1. 失败，返回false，并抛出异常
	 * 2. 成功， 返回true。 
	 * ? vijava如何开机  连接 
	 * 可通过Host关机 重启 维护 取消维护 断开连接
	 *  
	 * @param String
	 * @return true/false 
	 */
	public boolean disconnectHost(String moid)throws RuntimeFault, RemoteException;
	/**
	 * 重新连接主机
	 * 1. 失败，返回false，并抛出异常
	 * 2. 成功， 返回true。 
	 * ? vijava如何开机  连接 
	 * 可通过Host关机 重启 维护 取消维护 断开连接
	 *  
	 * @param String
	 * @return true/false 
	 */
	public boolean reconnectHost(String moid)throws RuntimeFault, RemoteException;
	/**
	 * 主机进入维护模式
	 * 1. 失败，返回false，并抛出异常
	 * 2. 成功， 返回true。 
	 * ? vijava如何开机  连接 
	 * 可通过Host关机 重启 维护 取消维护 断开连接
	 *  
	 * @param String
	 * @return true/false 
	 */
	public boolean entranceHostMaintance(String moid)throws RuntimeFault, RemoteException;
	/**
	 * 主机退出维护模式
	 * 1. 失败，返回false，并抛出异常
	 * 2. 成功， 返回true。 
	 * ? vijava如何开机  连接 
	 * 可通过Host关机 重启 维护 取消维护 断开连接
	 *  
	 * @param String
	 * @return true/false 
	 */
	public boolean exitHostMaintance(String moid)throws RuntimeFault, RemoteException;
	/**
	 * 启动主机
	 * 1. 失败，返回false，并抛出异常
	 * 2. 成功， 返回true。 
	 * 可能得借助IPMI
	 *  
	 * @param String
	 * @return true/false 
	 */
	public boolean startupHost(String moid)throws RuntimeFault, RemoteException;
	/**
	 * 连接主机
	 * 1. 失败，返回false，并抛出异常
	 * 2. 成功， 返回true。 
	 * 可能得借助IPMI
	 *  
	 * @param String
	 * @return true/false 
	 */
	public boolean connectHost(String moid,String ipAddr,String uname,String pwd)throws RuntimeFault, RemoteException;
	
	/**获取主机信息
	 * 
	 *	HostListSummary summary = hostSystem.getSummary();
	 *	HostHardwareInfo hwi = hostSystem.getHardware();
	 * @param 要注销主机的moid
	 * @return 返回cpu 内存  存储等实时信息的map
	 */
	public Map<String,Object> getHostSystemInfo(String moid)throws RuntimeFault, RemoteException;
	/**获取主机实时信息
	 * 
	 * 
	 *HostListSummary summary = hostSystems.getSummary();
	 * 
	 *  summary.quickStats.overallCpuUsage;
	 * @param 要注销主机的moid
	 * @return 返回cpu 内存  存储等实时信息的map
	 */
	public Map<String,String> getRealtimeHostInfo(String moid)throws RuntimeFault, RemoteException;
	//获取
	//如何保持连接
	
	//数据同步是否单独接口
	
	//确定接口 -> 梳理业务
	/***
	 * 
	 * 根据主机的的moid获取其所在的Datacenter
	 * @param hostid
	 * @return Datacenter
	 * @throws RemoteException 
	 * @throws RuntimeFault 
	 * @throws InvalidProperty 
	 **/
	public Datacenter getDatacenterByHost(String hostid) throws RemoteException,RuntimeFault,InvalidProperty;

	/***
	 * 
	 * 根据moid获取Datacenter
	 * @param moid
	 * @return Datacenter
	 * @throws RemoteException 
	 * @throws RuntimeFault 
	 * @throws InvalidProperty 
	 **/
	public Datacenter getDatacenterByMoid(String moid);
}
