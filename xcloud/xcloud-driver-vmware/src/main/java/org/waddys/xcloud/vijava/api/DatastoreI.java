package org.waddys.xcloud.vijava.api;
import java.rmi.RemoteException;
import java.util.List;

import org.waddys.xcloud.vijava.data.StoragePool;

import com.sugon.vim25.DynamicData;
import com.sugon.vim25.FileFault;
import com.sugon.vim25.InvalidProperty;
import com.sugon.vim25.RuntimeFault;
import com.sugon.vim25.VirtualDiskSpec;
import com.sugon.vim25.mo.ClusterComputeResource;
import com.sugon.vim25.mo.Datacenter;
import com.sugon.vim25.mo.Datastore;
import com.sugon.vim25.mo.HostSystem;
import com.sugon.vim25.mo.ServiceInstance;
/**
 * 存储操作接口
 * 
 * @category see
 * @author
 */
public interface DatastoreI {
/***
 * 
 * 根据主机的moid获取存储列表
 * @param String hostid
 * @return List<Datastore>
 * @throws RemoteException 
 * @throws RuntimeFault 
 * @throws InvalidProperty 
 **/
List<Datastore> getDataStoresByHost(String hostid) throws InvalidProperty, RuntimeFault, RemoteException;
/***
 * 
 * 根据datastore的moid获取主机列表
 * @param String datastoreid
 * @return List<HostSystem>
 * @throws RemoteException 
 * @throws RuntimeFault 
 * @throws InvalidProperty 
 **/
List<HostSystem> getHostsByDataStores(String datastoreid) throws RemoteException;
/***
 * 
 * 根据集群的moid获取集群中各个主机所共有的存储列表
 * @param String clusterid
 * @return List<Datastore>
 * @throws RemoteException 
 * @throws RuntimeFault 
 * @throws InvalidProperty 
 **/
List<Datastore> getDataStoresByCluster(String clusterid);
/***
 * 
 * 根据集群的moid获取资源池所对应集群中各个主机所共有的存储列表
 * @param String rpid
 * @return List<Datastore>
 * @throws RemoteException 
 * @throws RuntimeFault 
 * @throws InvalidProperty 
 * @throws Exception 
 **/
List<StoragePool> getStorageByResourcePool(String rpid) throws InvalidProperty, RuntimeFault, RemoteException, Exception;
/***
 * 
 * 根据集群的moid获取资源池所对应集群中各个主机的存储列表
 * @param String rpid
 * @return List<Datastore>
 * @throws RemoteException 
 * @throws RuntimeFault 
 * @throws InvalidProperty 
 **/
List<Datastore> getDataStoresByResoucePool(String rpid) throws InvalidProperty, RuntimeFault, RemoteException;
/***
 * 
 * 根据数据中心的moid获取存储列表
 * @param datacenterid
 * @return List<Datastore>
 * @throws RemoteException 
 * @throws RuntimeFault 
 * @throws InvalidProperty 
 **/
List<Datastore> getDataStoresByDatacenter(Datacenter datacenterid);
/***
 * 
 * 根据moid获取存储
 * @param moid
 * @return Datastore
 * @throws RemoteException 
 * @throws RuntimeFault 
 * @throws InvalidProperty 
 **/
Datastore getDatastoreByMoid(String moid) throws RemoteException;	
/***
 * 
 * 创建磁盘接口
 * @param Datacenter dc
 * @param String name
 * @param VirtualDiskSpec destspec
 * @return boolean
 * @throws RemoteException 
 * @throws RuntimeFault 
 * @throws InvalidProperty 
 **/
boolean createVirtualDisk(Datacenter dc,String name,VirtualDiskSpec destspec) throws FileFault, RuntimeFault, RemoteException;
/***
 * 
 * 根据存储id 获取数据中心
 * @param moid
 * @return Datacenter
 * @throws RemoteException 
 * @throws RuntimeFault 
 * @throws InvalidProperty 
 **/
Datacenter getDatacenterByDatastore(String datastoreid) throws RemoteException;	
/***
 * 
 * 根据存储id 获取集群
 * @param moid
 * @return ClusterComputerResource
 * @throws RemoteException 
 * @throws RuntimeFault 
 * @throws InvalidProperty 
 **/
ClusterComputeResource getClusterByDatastore(String datastoreid) throws RemoteException;
/***
 * 
 * 创建数据存储接口
 * @throws RemoteException 
 * @throws RuntimeFault 
 * @throws InvalidProperty 
 **//*
Datastore createDataStore(String type, DynamicData destspec, HostSystem host)
		throws InvalidProperty, RuntimeFault, RemoteException;	*/
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

}
