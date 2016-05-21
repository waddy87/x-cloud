package org.waddys.xcloud.vijava.api;

import java.rmi.RemoteException;
import java.util.List;

import org.waddys.xcloud.vijava.data.NetPool;

import com.vmware.vim25.HostPortGroup;
import com.vmware.vim25.HostVirtualSwitch;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.Network;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VmwareDistributedVirtualSwitch;

public interface NetworkI {

	
	/***
	 * 
	 * 根据moid获取分布式交换机
	 * @param vdsid
	 * @return VmwareDistributedVirtualSwitch
	 * @throws RemoteException 
	 * @throws RuntimeFault 
	 * @throws InvalidProperty 
	 **/
	public VmwareDistributedVirtualSwitch getVDSbymoid(String vdsid) throws RemoteException,RuntimeFault,InvalidProperty;
	/***
	 * 
	 * 根据主机的的moid获取其portgroup列表
	 * @param hostid
	 * @return List<HostPortGroup>
	 * @throws RemoteException 
	 * @throws RuntimeFault 
	 * @throws InvalidProperty 
	 **/
	public List<HostPortGroup> getPortGroupByHost(String hostid) throws RemoteException,RuntimeFault,InvalidProperty;
	/***
	 * 
	 * 根据主机的的moid获取其virtualswitch列表
	 * @param hostid
	 * @return List<HostVirtualSwitch>
	 * @throws RemoteException 
	 * @throws RuntimeFault 
	 * @throws InvalidProperty 
	 **/
	public List<HostVirtualSwitch> getHostVirtualSwitchByHost(String hostid) throws RemoteException,RuntimeFault,InvalidProperty;
	/***
	 * 
	 * 根据主机的的moid获取其network列表
	 * @param hostid
	 * @return List<Network>
	 * @throws RemoteException 
	 * @throws RuntimeFault 
	 * @throws InvalidProperty 
	 **/
	public List<Network> getNetworkByHost(String hostid) throws RemoteException,RuntimeFault,InvalidProperty;
	/***
	 * 
	 * 根据集群的的moid获取其network列表
	 * @param datacenterid
	 * @return List<Network>
	 * @throws RemoteException 
	 * @throws RuntimeFault 
	 * @throws InvalidProperty 
	 **/
	public List<Network> getNetworkByCluster(String clusterid) throws RemoteException,RuntimeFault,InvalidProperty;
	/***
	 * 
	 * 根据资源池的的moid获取其network列表
	 * @param datacenterid
	 * @return List<Network>
	 * @throws RemoteException 
	 * @throws RuntimeFault 
	 * @throws InvalidProperty 
	 **/
	public List<Network> getNetworkByResourcepool(String poolid) throws RemoteException,RuntimeFault,InvalidProperty;
	/***
	 * 
	 * 根据集群的的moid获取其network列表
	 * @param datacenterid
	 * @return List<Network>
	 * @throws RemoteException 
	 * @throws RuntimeFault 
	 * @throws InvalidProperty 
	 **/
	public List<Network> getNetworkByDatacenter(String datacenterid)
			throws RemoteException, RuntimeFault, InvalidProperty;
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
	/***
	 * 
	 * 根据网络信息获取其所对应的portgroup列表
	 * @param Network
	 * @return List<HostPortGroup>
	 * @throws RemoteException 
	 * @throws RuntimeFault 
	 * @throws InvalidProperty 
	 **/
	List<HostPortGroup> getPortGroupByNetwork(Network network)
			throws RemoteException, RuntimeFault, InvalidProperty;
	
	/***
	 * 获取当前环境下的网络池
	 * @throws RemoteException 
	 * @throws RuntimeFault 
	 * @throws InvalidProperty 
	 * 
	 * **/
	public List<NetPool> getNetPool() throws InvalidProperty, RuntimeFault, RemoteException;
	/***
	 * 
	 * 根据网络信息获取其所对应的portgroup所对应vlan列表
	 * @param Network
	 * @return List<HostPortGroup>
	 * @throws RemoteException 
	 * @throws RuntimeFault 
	 * @throws InvalidProperty 
	 **/
	public int getVlanByNetwork(Network network) throws RuntimeFault, InvalidProperty, RemoteException;
	
}
