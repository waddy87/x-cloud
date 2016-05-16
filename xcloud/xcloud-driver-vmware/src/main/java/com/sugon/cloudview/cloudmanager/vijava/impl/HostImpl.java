package com.sugon.cloudview.cloudmanager.vijava.impl;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import com.sugon.cloudview.cloudmanager.vijava.api.HostI;
import com.sugon.cloudview.cloudmanager.vijava.util.MoQueryUtils;
import com.sugon.vim25.HostHardwareInfo;
import com.sugon.vim25.HostListSummary;
import com.sugon.vim25.InvalidProperty;
import com.sugon.vim25.ManagedObjectReference;
import com.sugon.vim25.RuntimeFault;
import com.sugon.vim25.mo.Datacenter;
import com.sugon.vim25.mo.HostSystem;
import com.sugon.vim25.mo.ServiceInstance;
import com.sugon.vim25.mo.util.MorUtil;

public class HostImpl implements HostI {

	private ServiceInstance si;

	public ServiceInstance getSi() {
		return si;
	}

	public void setSi(ServiceInstance si) {
		this.si = si;
	}

	public HostSystem registerHost(String ipAddr, String uname, String pwd)
			throws RuntimeFault, RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean removeHost(String moid) throws RuntimeFault, RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean editHost() throws RuntimeFault, RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean setHostStatus(String moid, String status)
			throws RuntimeFault, RemoteException {
		// TODO Auto-generated method stub
		// Datacenter dc;
		// dc.getHostFolder().registerVM_Task(path, name, asTemplate, pool,
		// host);
		// HostSystem host = null;
		// host.getConfig();
		// host.reconnectHost_Task(hcs)//闂佹彃绉甸弻濠冩交閻愭潙澶�//
		// host.shutdownHost_Task(force);//闁稿繗娅曞┃锟�//
		// host.disconnectHost();//闁哄偆鍘肩槐鎴炴交閻愭潙澶�//
		// host.rebootHost(force);//闂佹彃绉撮幆锟� host.enterMaintenanceMode(timeout,
		// evacuatePoweredOffVms);//缂備礁鐡ㄦ慨锟�
		// host.exitMaintenanceMode(timeout);//闂侇偓鎷烽崵顓犵磼鐎涙ê袘
		return false;
	}

	public Map<String, String> getRealtimeHostInfo(String moid)
			throws RuntimeFault, RemoteException {
		// TODO Auto-generated method stub
		/*
		 * HostSystem systems = (HostSystem)entity;
		 * 
		 * HostListSummary summary = systems.getSummary(); HostHardwareInfo hwi
		 * = systems.getHardware(); long hz = hwi.cpuInfo.hz; long e6 = 1000000;
		 * long hzd = (long)((double) hz) / e6;
		 * 
		 * long totalHz = hzd * hwi.cpuInfo.numCpuCores; long cpuUsage =
		 * summary.quickStats.overallCpuUsage; long totalMem =
		 * hwi.getMemorySize(); long memUsage =
		 * summary.quickStats.overallMemoryUsage;
		 */
		return null;
	}

	public List<HostSystem> getHostSystemList() throws RuntimeFault,
			RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Object> getHostSystemInfo(String moid)
			throws RuntimeFault, RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean shutdownHost(String moid) throws RuntimeFault,
			RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean restartHost(String moid) throws RuntimeFault,
			RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean disconnectHost(String moid) throws RuntimeFault,
			RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean reconnectHost(String moid) throws RuntimeFault,
			RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean entranceHostMaintance(String moid) throws RuntimeFault,
			RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean exitHostMaintance(String moid) throws RuntimeFault,
			RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean startupHost(String moid) throws RuntimeFault,
			RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean connectHost(String moid, String ipAddr, String uname,
			String pwd) throws RuntimeFault, RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Datacenter getDatacenterByHost(String hostid)
			throws RemoteException, RuntimeFault, InvalidProperty {
		HostSystem host = getHostSystemByMoid(hostid);
		Datacenter dc = (Datacenter) MoQueryUtils.getNearestAncesterByType(
				host, "Datacenter");
		return dc;
	}

	public HostSystem getHostSystemByMoid(String Moid) {

		ManagedObjectReference mor = new ManagedObjectReference();
		mor.set_value(Moid);
		mor.setType("HostSystem");
		if (si != null)
			;
		else
			return null;
		HostSystem hs = (HostSystem) MorUtil.createExactManagedEntity(
				si.getServerConnection(), mor);
		return hs;
	}

	@Override
	public Datacenter getDatacenterByMoid(String moid) {

		ManagedObjectReference mor = new ManagedObjectReference();
		mor.set_value(moid);
		mor.setType("Datacenter");
		if (si != null)
			;
		else
			return null;
		Datacenter dc = (Datacenter) MorUtil.createExactManagedEntity(
				si.getServerConnection(), mor);
		return dc;
	}

}
