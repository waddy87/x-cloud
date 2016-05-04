/**
 * 
 */
package com.sugon.cloudview.cloudmanager.monitor.serviceImpl.service;

import java.util.List;

import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.entity.Host;
import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.entity.VM;


/**
 * @author dengjq
 *
 */
public interface HostAndVmPerfOp {
	
	//获取所有主机信息，目前只包括虚拟机个数信息，未包括虚拟机详细信息
	public List<Host> getHosts();
	
	//获取所有主机信息，包括集群中的主机和独立主机
	public List<Host> getHostsEx();
	
	//获取所有主机信息，一次性获取，不用遍历集群
	public List<Host> getHostsBatch();
	
	//获取某个集群下所有主机信息，目前只包括虚拟机个数信息，未包括虚拟机详细信息
	public List<Host> getHosts(String clusterId);
	
	//获取cpu利用率的topn信息
	public List<Host> getHostCpuUsageTopN(List<Host> hosts, int topn);
	//获取cpu使用量的topn信息
	public List<Host> getHostCpuUsedTopN(List<Host> hosts, int topn);
	
	//获取内存利用率的topn信息
	public List<Host> getHostMemUsageTopN(List<Host> hosts, int topn);
	//获取内存使用量的topn信息
	public List<Host> getHostMemUsedTopN(List<Host> hosts, int topn);
	
	//获取磁盘io速率的topn信息
	public List<Host> getHostDiskIoTopN(List<Host> hosts, int topn);
	//获取磁盘iops的topn信息
	public List<Host> getHostDiskIopsTopN(List<Host> hosts, int topn);
	
	//获取网络速率的topn信息
	public List<Host> getHostNetIoTopN(List<Host> hosts, int topn);
	
	//获取网络发送速率的topn信息
	public List<Host> getHostNetSendIoTopN(List<Host> hosts, int topn);
		
	//获取网络接收速率的topn信息
	public List<Host> getHostNetRecvIoTopN(List<Host> hosts, int topn);
	
	//获取虚拟机数量的topn信息
	public List<Host> getHostVmNumsTopN(List<Host> hosts, int topn);
	

	//获取所有虚拟机信息，不包括独立主机上的虚拟机
	public List<VM> getVms();
	
	//获取所有虚拟机信息，
	public List<VM> getVmsEx();
	
	//批量获取所有虚拟机信息，不用遍历数据中心、集群、主机
	public List<VM> getVmsBatch();
	
	//获取某个集群下所有虚拟机信息
	public List<VM> getVmsOnCluster(String clusterId);
	
	//获取某个主机下所有虚拟机信息
	public List<VM> getVmsOnHost(String hostId);
		
	//获取cpu利用率的topn信息
	public List<VM> getVmCpuUsageTopN(List<VM> vms, int topn);
	//获取cpu使用量的topn信息
	public List<VM> getVmCpuUsedTopN(List<VM> vms, int topn);
		
	//获取内存利用率的topn信息
	public List<VM> getVmMemUsageTopN(List<VM> vms, int topn);
	//获取内存使用量的topn信息
	public List<VM> getVmMemUsedTopN(List<VM> vms, int topn);
		
	//获取磁盘io速率的topn信息
	public List<VM> getVmDiskIoTopN(List<VM> vms, int topn);
	//获取磁盘iops的topn信息
	public List<VM> getVmDiskIopsTopN(List<VM> vms, int topn);
	//获取磁盘使用率的topn信息
	public List<VM> getVmDiskUsageTopN(List<VM> vms, int topn);
		
	//获取网络速率的topn信息
	public List<VM> getVmNetIoTopN(List<VM> vms, int topn);
		
	//获取网络发送速率的topn信息
	public List<VM> getVmNetSendIoTopN(List<VM> vms, int topn);
			
	//获取网络接收速率的topn信息
	public List<VM> getVmNetRecvIoTopN(List<VM> vms, int topn);
	
	
	
		
}
