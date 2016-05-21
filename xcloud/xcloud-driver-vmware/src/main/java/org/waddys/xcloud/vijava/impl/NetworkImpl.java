package org.waddys.xcloud.vijava.impl;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.waddys.xcloud.vijava.api.ClusterI;
import org.waddys.xcloud.vijava.api.HostI;
import org.waddys.xcloud.vijava.api.NetworkI;
import org.waddys.xcloud.vijava.api.ResourcePoolI;
import org.waddys.xcloud.vijava.data.NetPool;

import com.vmware.vim25.DVPortgroupConfigInfo;
import com.vmware.vim25.DVPortgroupConfigSpec;
import com.vmware.vim25.HostNetworkPolicy;
import com.vmware.vim25.HostPortGroup;
import com.vmware.vim25.HostPortGroupSpec;
import com.vmware.vim25.HostVirtualSwitch;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.Tag;
import com.vmware.vim25.VMwareDVSPortSetting;
import com.vmware.vim25.VmwareDistributedVirtualSwitchTrunkVlanSpec;
import com.vmware.vim25.VmwareDistributedVirtualSwitchVlanIdSpec;
import com.vmware.vim25.VmwareDistributedVirtualSwitchVlanSpec;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.ComputeResource;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.DistributedVirtualPortgroup;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostNetworkSystem;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.Network;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VmwareDistributedVirtualSwitch;
import com.vmware.vim25.mo.util.MorUtil;

public class NetworkImpl implements NetworkI {

	ServiceInstance si;

	@Override
	public List<HostPortGroup> getPortGroupByNetwork(Network network)
			throws RemoteException, RuntimeFault, InvalidProperty {

		List<HostPortGroup> list = new ArrayList<HostPortGroup>();

		HostSystem[] hosts = network.getHosts();
		for (HostSystem host : hosts) {
			HostPortGroup[] hps = host.getConfig().getNetwork().getPortgroup();
			for (HostPortGroup hp : hps) {
				// System.out.println(hp.getSpec().getName());
				if (hp.getSpec().getName().equals(network.getName()))
					list.add(hp);
			}
			// list.addAll(Arrays.asList(hps));
		}

		return list;
	}

	@Override
	public List<HostPortGroup> getPortGroupByHost(String hostid)
			throws RemoteException, RuntimeFault, InvalidProperty {

		HostI hosti = new HostImpl();
		hosti.setSi(si);
		HostSystem host = hosti.getHostSystemByMoid(hostid);
		HostPortGroup[] hps = host.getConfig().getNetwork().getPortgroup();
		List<HostPortGroup> list = Arrays.asList(hps);
		return list;
	}

	@Override
	public List<HostVirtualSwitch> getHostVirtualSwitchByHost(String hostid)
			throws RemoteException, RuntimeFault, InvalidProperty {

		HostI hosti = new HostImpl();
		hosti.setSi(si);
		HostSystem host = hosti.getHostSystemByMoid(hostid);
		HostVirtualSwitch[] hps = host.getConfig().getNetwork().getVswitch();
		List<HostVirtualSwitch> list = Arrays.asList(hps);
		return list;
	}

	@Override
	public List<Network> getNetworkByHost(String hostid)
			throws RemoteException, RuntimeFault, InvalidProperty {

		HostI hosti = new HostImpl();
		hosti.setSi(si);
		HostSystem host = hosti.getHostSystemByMoid(hostid);
		Network[] hns = host.getNetworks();
		List<Network> list = Arrays.asList(hns);
		return list;
	}

	@Override
	public List<Network> getNetworkByDatacenter(String datacenterid)
			throws RemoteException, RuntimeFault, InvalidProperty {

		HostI hosti = new HostImpl();
		hosti.setSi(si);
		Datacenter dc = hosti.getDatacenterByMoid(datacenterid);
		Network[] hns = dc.getNetworks();
		List<Network> list = Arrays.asList(hns);
		return list;
	}

	@Override
	public List<Network> getNetworkByCluster(String clusterid)
			throws RemoteException, RuntimeFault, InvalidProperty {

		List<Network> list = new ArrayList<Network>();
		ClusterI ci = new ClusterImpl();
		ci.setSi(si);
		ClusterComputeResource cluster = ci.getClusterByMoid(clusterid);
		Network[] hns = cluster.getNetworks();
		for (Network hn : hns) {
			if (hn.getHosts() == null
					|| hn.getHosts().length < cluster.getHosts().length)
				continue;
			list.add(hn);
		}

		return list;
	}

	@Override
	public List<Network> getNetworkByResourcepool(String poolid)
			throws RemoteException, RuntimeFault, InvalidProperty {

		ResourcePoolI ri = new ResourcePoolImpl();
		ri.setSi(si);
		ResourcePool pool = ri.getResoucePoolbyMoid(poolid);
		ComputeResource cl = pool.getOwner();
		List<Network> list = new ArrayList<Network>();

		// Network[] hns = cl.getNetworks();

		// 集群资源
		if ("ClusterComputeResource".equals(cl.getMOR().getType()))
			list = this.getNetworkByCluster(cl.getMOR().get_value());
		// 主机资源
		else
			list = Arrays.asList(cl.getNetworks());
		// System.out.println("cluster name is "+ cl.getName());
		return list;
	}

	@Override
	public VmwareDistributedVirtualSwitch getVDSbymoid(String vdsid)
			throws RemoteException, RuntimeFault, InvalidProperty {

		ManagedObjectReference mor = new ManagedObjectReference();
		mor.set_value(vdsid);
		mor.setType("VmwareDistributedVirtualSwitch");
		if (si != null)
			;
		else
			return null;
		VmwareDistributedVirtualSwitch vds = (VmwareDistributedVirtualSwitch) MorUtil
				.createExactManagedEntity(si.getServerConnection(), mor);
		return vds;
	}

	@Override
	public List<NetPool> getNetPool() throws InvalidProperty, RuntimeFault,
			RemoteException {

		List<NetPool> npList = new ArrayList<NetPool>();
		Folder rootFolder = si.getRootFolder();
		ManagedEntity[] nks = new InventoryNavigator(rootFolder)
				.searchManagedEntities("Network");
		int hostCount = new InventoryNavigator(rootFolder)
		.searchManagedEntities("HostSystem").length;
		for (ManagedEntity n : nks) {
			Network net = (Network) n;
			boolean uplinkFlag = false;
			//通过network 的tag判断是否uplink 如果uplink需要屏蔽
			if (n.getTag() != null && n.getTag().length > 0){
					for(Tag tag:n.getTag()){
						if(n.getTag()[0].getKey().equals("SYSTEM/DVS.UPLINKPG"))
						uplinkFlag =true;
					}
			}
			if (uplinkFlag)
				continue;
			if(net.getHosts() == null || net.getHosts().length!=hostCount){
				continue;
			}
			NetPool np = new NetPool();
			np.setName(n.getName());
			np.setId(n.getMOR().get_value());
			np.setVlan(getVlanByNetwork((Network) n));
			npList.add(np);

		}
		return npList;
	}

	@Override
	public ServiceInstance getSi() {
		return si;
	}

	@Override
	public void setSi(ServiceInstance si) {
		this.si = si;
	}

	public static void main(String args[]) {

		String url = "https://10.0.36.121/sdk";
		String userName = "administrator@vsphere.local";
		String password = "Sugon!123";
		String moid = "datastore-89";
		String netid = "network-210";
		ServiceInstance si;

		try {
			si = new ServiceInstance(new URL(url), userName, password, true);
			NetworkI networki = new NetworkImpl();
			networki.setSi(si);
			ManagedObjectReference netmor = new ManagedObjectReference();
			netmor.set_value(netid);
			netmor.setType("Network");
			Network networkB = new Network(si.getServerConnection(), netmor);
			ManagedObjectReference network = networkB.getSummary().getNetwork();
			if (network.getType().equals("Network")) {
				List<HostPortGroup> list = networki
						.getPortGroupByNetwork(networkB);
				for (HostPortGroup portgroup : list) {
					System.out.println(portgroup.getKey() + " "
							+ portgroup.getSpec().getVlanId());
				}
			} else {
				DistributedVirtualPortgroup dvp = (DistributedVirtualPortgroup) MorUtil
						.createExactManagedEntity(si.getServerConnection(),
								network);
				// DistributedVirtualPortgroup dvp =
				// (DistributedVirtualPortgroup)network;
				VMwareDVSPortSetting vps = (VMwareDVSPortSetting) (dvp
						.getConfig().getDefaultPortConfig());
				VmwareDistributedVirtualSwitchVlanIdSpec vlanspec = (VmwareDistributedVirtualSwitchVlanIdSpec) vps
						.getVlan();
				vlanspec.getVlanId();
				System.out.println(dvp.getName() + " " + vlanspec.getVlanId());
			}

			/******* Hostportgroup 设置vlan ********/
			String hostid = "host-81";
			String portGroupName = "vlantest";
			ManagedObjectReference mor = new ManagedObjectReference();
			mor.set_value(hostid);
			mor.setType("HostSystem");
			HostSystem hs = (HostSystem) MorUtil.createExactManagedEntity(
					si.getServerConnection(), mor);
			HostNetworkSystem hns = hs.getHostNetworkSystem();
			HostPortGroupSpec portgrp = new HostPortGroupSpec();
			// 四个参数必不可少
			portgrp.setName("vlantest");
			portgrp.setVlanId(1);
			portgrp.setVswitchName("vSwitch0");
			portgrp.setPolicy(new HostNetworkPolicy());
			hns.updatePortGroup(portGroupName, portgrp);

			/******* Hostportgroup 设置vlan ********/
			int vlan = 1;
			String dvsid = "";
			String dvspid = "dvportgroup-187";
			DistributedVirtualPortgroup dvp = null;
			mor = new ManagedObjectReference();
			mor.set_value(dvspid);
			mor.setType("DistributedVirtualPortgroup");
			dvp = (DistributedVirtualPortgroup) MorUtil
					.createExactManagedEntity(si.getServerConnection(), mor);

			DVPortgroupConfigInfo dvpc = new DVPortgroupConfigInfo();
			DVPortgroupConfigSpec dvpg = new DVPortgroupConfigSpec();
			dvpc = dvp.getConfig();
			// configversion必须写
			dvpg.setConfigVersion(dvpc.getConfigVersion());
			VMwareDVSPortSetting ps = (VMwareDVSPortSetting) dvp.getConfig()
					.getDefaultPortConfig();
			VmwareDistributedVirtualSwitchVlanIdSpec vlanSpec = new VmwareDistributedVirtualSwitchVlanIdSpec();
			vlanSpec.setInherited(false);
			vlanSpec.setVlanId(1);
			ps.setVlan(vlanSpec);
			dvpg.setDefaultPortConfig(ps);
			dvp.reconfigureDVPortgroup_Task(dvpg);
			System.out.println(vlan);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getVlanByNetwork(Network network) throws RuntimeFault,
			InvalidProperty, RemoteException {
		// TODO Auto-generated method stub
		ManagedObjectReference networkb = network.getSummary().getNetwork();
		;
		int vlan = 0;
		try {
			if (networkb.getType().equals("Network")) {
				List<HostPortGroup> list = getPortGroupByNetwork(network);
				if (list.size() > 0)
					vlan = list.get(0).getSpec().getVlanId();
			} else {
				DistributedVirtualPortgroup dvp = (DistributedVirtualPortgroup) MorUtil
						.createExactManagedEntity(si.getServerConnection(),
								networkb);
				// DistributedVirtualPortgroup dvp =
				// (DistributedVirtualPortgroup)network;
				VMwareDVSPortSetting vps = (VMwareDVSPortSetting) (dvp
						.getConfig().getDefaultPortConfig());
				// System.out.println(dvp.getName());
				VmwareDistributedVirtualSwitchVlanIdSpec vlanspec = (VmwareDistributedVirtualSwitchVlanIdSpec) vps
						.getVlan();
				// vlan = vlanspec.getVlanId();
			}
		} catch (Exception e) {
			// throw new RemoteException("请检查底层网络配置：vlan中继在系统中不受支持");
			// 用9999表示vlan中继
			vlan = 9999;
		}
		return vlan;
	}
}
