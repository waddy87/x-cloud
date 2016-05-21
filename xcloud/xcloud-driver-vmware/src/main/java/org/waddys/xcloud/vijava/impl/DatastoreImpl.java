package org.waddys.xcloud.vijava.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.naming.ldap.ManageReferralControl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.waddys.xcloud.vijava.api.ClusterI;
import org.waddys.xcloud.vijava.api.DatastoreI;
import org.waddys.xcloud.vijava.api.HostI;
import org.waddys.xcloud.vijava.api.ResourcePoolI;
import org.waddys.xcloud.vijava.data.StoragePool;
import org.waddys.xcloud.vijava.util.MoQueryUtils;
import org.waddys.xcloud.vijava.util.VmConvertUtils;

import com.vmware.vim25.DatastoreHostMount;
import com.vmware.vim25.DynamicData;
import com.vmware.vim25.FileFault;
import com.vmware.vim25.HostNasVolumeSpec;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.TaskInfo;
import com.vmware.vim25.VirtualDeviceConfigSpec;
import com.vmware.vim25.VirtualDeviceConfigSpecFileOperation;
import com.vmware.vim25.VirtualDeviceConfigSpecOperation;
import com.vmware.vim25.VirtualDisk;
import com.vmware.vim25.VirtualDiskAdapterType;
import com.vmware.vim25.VirtualDiskFlatVer2BackingInfo;
import com.vmware.vim25.VirtualDiskSpec;
import com.vmware.vim25.VirtualDiskType;
import com.vmware.vim25.VirtualLsiLogicController;
import com.vmware.vim25.VirtualSCSISharing;
import com.vmware.vim25.VmfsDatastoreCreateSpec;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.ComputeResource;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.HostDatastoreSystem;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedObject;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualDiskManager;
import com.vmware.vim25.mo.util.MorUtil;

public class DatastoreImpl implements DatastoreI {

	ServiceInstance si;
    private static Logger logger = LoggerFactory.getLogger(DatastoreImpl.class);
	@Override
	public List<Datastore> getDataStoresByHost(String hostid)
			throws InvalidProperty, RuntimeFault, RemoteException {

		List<Datastore> list = new ArrayList<Datastore>();
		HostI hosti = new HostImpl();
		hosti.setSi(si);
		HostSystem host = hosti.getHostSystemByMoid(hostid);
		if (host != null) {
			Datastore[] ds = host.getDatastores();
			list = Arrays.asList(ds);
		}
		return list;
	}

	@Override
	public List<Datastore> getDataStoresByResoucePool(String moid)
			throws InvalidProperty, RuntimeFault, RemoteException {

		/*
		 * ResourcePool rp; Datastore[] ds = rp.getConfig(). return
		 * Arrays.asList(ds);
		 */
		ResourcePoolI rpi = new ResourcePoolImpl();
		rpi.setSi(si);
		
		ResourcePool rp = rpi.getResoucePoolbyMoid(moid);
		List<Datastore> list = new ArrayList<Datastore>();
//		rp.getOwner().getConfigStatus();
		ComputeResource cl = rp.getOwner();
		
		// 集群资源
		if ("ClusterComputeResource".equals(cl.getMOR().getType()))
			list = this.getDataStoresByCluster(cl.getMOR().get_value());
		
		// 主机资源
		else
			list = Arrays.asList(cl.getDatastores());
		
		return list;
	}

	@Override
	public List<Datastore> getDataStoresByCluster(String clusterid) {

		ClusterI clusteri = new ClusterImpl();
		clusteri.setSi(si);
		List<Datastore> list = new ArrayList<Datastore>();
		ClusterComputeResource cluster = clusteri.getClusterByMoid(clusterid);
		HostSystem[] host = cluster.getHosts();// important

		Datastore[] ds = cluster.getDatastores();
		for (Datastore d : ds) {
			int size = d.getHost().length;
			// System.out.println("datastore "+d.getName()+" List size is "+size);
			if (host.length != d.getHost().length)
				continue;
			else
				list.add(d);
		}
		return list;
	}

	@Override
	public List<Datastore> getDataStoresByDatacenter(Datacenter datacenter) {

		Datastore[] ds = datacenter.getDatastores();
		return Arrays.asList(ds);
	}

	@Override
	public Datastore getDatastoreByMoid(String moid) throws RemoteException {

		// si = new ServiceInstance();
		Datastore ds = null;
		try{
		ManagedObjectReference mor = new ManagedObjectReference();
		mor.set_value(moid);
		mor.setType("Datastore");
		ds = (Datastore) MorUtil.createExactManagedEntity(
				si.getServerConnection(), mor);
		logger.info("datastore is {} ",ds.getName());
		}catch(Exception e){
			logger.error("can not find datastore " + moid);
			throw new RemoteException("can not find datastore " + moid);
		}
		return ds;
	}

	@Override
	public boolean createVirtualDisk(Datacenter dc, String name,
			VirtualDiskSpec destspec) throws FileFault, RuntimeFault,
			RemoteException {

		VirtualDiskManager vmg = si.getVirtualDiskManager();
		vmg.createVirtualDisk_Task(name, dc, destspec);

		return true;
	}

	@Override
	public ServiceInstance getSi() {
		return si;
	}

	@Override
	public void setSi(ServiceInstance si) {
		this.si = si;
	}

	@Override
	public List<HostSystem> getHostsByDataStores(String datastoreid) throws RemoteException {

		List<HostSystem> list = new ArrayList<HostSystem>();
		Datastore ds = this.getDatastoreByMoid(datastoreid);
		DatastoreHostMount[] hostmounts = ds.getHost();
		for (DatastoreHostMount hostmount : hostmounts) {

			list.add((HostSystem) MorUtil.createExactManagedEntity(
					si.getServerConnection(), hostmount.getKey()));
		}

		// HostSystem[] hostmount= ds.getHost();
		// list = Arrays.asList(hostmount);
		return list;
	}

	@Override
	public Datacenter getDatacenterByDatastore(String datastoreid) throws RemoteException {

		Datastore ds = this.getDatastoreByMoid(datastoreid);
		Datacenter dc = (Datacenter) MoQueryUtils.getNearestAncesterByType(ds,
				"Datacenter");
		return dc;
	}

	@Override
	public ClusterComputeResource getClusterByDatastore(String datastoreid) throws RemoteException {

		ClusterComputeResource cluster = null;
		List<HostSystem> list = this.getHostsByDataStores(datastoreid);
		if (list != null)
			cluster = (ClusterComputeResource) MoQueryUtils
					.getNearestAncesterByType(list.get(0),
							"ClusterComputeResource");
		return cluster;
	}
	

	@Override
	public List<StoragePool> getStorageByResourcePool(String rpid)
			throws InvalidProperty, RuntimeFault, RemoteException {

		ResourcePoolI rpi = new ResourcePoolImpl();
		rpi.setSi(si);
		
		ResourcePool rp = rpi.getResoucePoolbyMoid(rpid);
		List<StoragePool> list = new ArrayList<StoragePool>();
		/*rp.getOwner().getConfigStatus();
		ComputeResource cl = rp.getOwner();
		*/
		//Datastore[] dss = cl.getDatastores();
		
		//这里取的是资源池下的共享存储
		
		List<Datastore> dss = this.getDataStoresByResoucePool(rpid);
		for (Datastore ds : dss) {
			StoragePool sp = new StoragePool();
			sp.setId(ds.getMOR().get_value());
			sp.setName(ds.getName());
			// 此处所有数据均采用long 类型 其中存储单位为B
			long cap = ds.getSummary().getCapacity();
			long free = ds.getSummary().getFreeSpace();
			//此处数据均采用long 类型 其中存储单位转为GB
			cap = VmConvertUtils.B2GB(cap);
			free = VmConvertUtils.B2GB(free);
			
			long used = cap - free;
			sp.setTotal(cap);
			sp.setUsed(used);
			sp.setAvailable(free);
			list.add(sp);
		}
		
		return list;
	}

	public static void main(String s[]) {
		String url = "https://10.0.31.251/sdk";
		String userName = "administrator@vsphere.local";
		String password = "Sugon!123";
		String moid = "datastore-588";
		// String moid = "datastore-12";
		DatastoreImpl dsi = new DatastoreImpl();
		ServiceInstance si;
		try {
			si = new ServiceInstance(new URL(url), userName, password, true);
			System.out.println(si.currentTime().getTime());
//			si.currentTime().getTime();
			   InventoryNavigator inv = new InventoryNavigator(
				        si.getRootFolder());
			   
//			   TaskInfo task = (TaskInfo)inv.searchManagedEntity("Task", "task-2644");
			/*dsi.setSi(si);
			// List l = dsi.getDataStoresByHost("host-194");//hosti 待实现
			List l = dsi.getDataStoresByCluster("domain-c82");
			System.out.println(l.size());

			ManagedObjectReference mor = new ManagedObjectReference();
			// mor.set_value("resgroup-633");
			mor.set_value("resgroup-83");
			// mor.set_value("resgroup-484");
			mor.setType("ResourcePool");
			ResourcePool rp = (ResourcePool) MorUtil.createExactManagedEntity(
					si.getServerConnection(), mor);
			// l = dsi.getDataStoresByResoucePool(rp);
			l = dsi.getDataStoresByResoucePool(rp.getMOR().get_value());
			System.out.println(l.size());
			Datacenter dc = dsi.getDatacenterByDatastore(moid);
			System.out.println(dc.getName());
			l = dsi.getHostsByDataStores(moid);
			System.out.println("host array size :" + l.size());
			ClusterComputeResource cluster = dsi.getClusterByDatastore(moid);
			System.out.println(cluster == null ? "null" : cluster.getName());
*/		} catch (Exception  e) {

			e.printStackTrace();
		}
	}


}
