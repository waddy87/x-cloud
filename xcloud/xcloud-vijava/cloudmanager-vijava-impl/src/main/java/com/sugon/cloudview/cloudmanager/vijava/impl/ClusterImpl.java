package com.sugon.cloudview.cloudmanager.vijava.impl;

import java.util.List;
import java.util.Map;

import com.sugon.cloudview.cloudmanager.vijava.api.ClusterI;
import com.sugon.vim25.ManagedObjectReference;
import com.sugon.vim25.mo.ClusterComputeResource;
import com.sugon.vim25.mo.HostSystem;
import com.sugon.vim25.mo.ResourcePool;
import com.sugon.vim25.mo.ServiceInstance;
import com.sugon.vim25.mo.util.MorUtil;

public class ClusterImpl implements ClusterI {

	private ServiceInstance si;
	
	public void setHaStatus(boolean hastatus) {
		// TODO Auto-generated method stub
		
	}

	public void setDRSStatus(boolean drsstatus) {
		// TODO Auto-generated method stub
		
	}

	public boolean addHost(String moid, String hostid) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean removeHost(String moid, String hostid) {
		// TODO Auto-generated method stub
		return false;
	}

	public Map<String, Object> getClusterInfo(String moid) {
		// TODO Auto-generated method stub
		return null;
	}

	public <ResourcePool> List getResourcePoolList(String moid) {
		// TODO Auto-generated method stub
		
		return null;
	}

	public boolean createResourcePool(String moid, ResourcePool rp) {
		// TODO Auto-generated method stub
		
		return false;
	}

	public <HostSystem> List getHostList(String moid) {
		// TODO Auto-generated method stub
		return null;
	}

	public <HostSystem> List getVirtualMachineList(String moid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClusterComputeResource getClusterByMoid(String clusterid) {
		// TODO Auto-generated method stub
		 ManagedObjectReference mor = new ManagedObjectReference();
		 mor.set_value(clusterid); mor.setType("ClusterComputeResource"); 
		 ClusterComputeResource  hs = (ClusterComputeResource) MorUtil.createExactManagedEntity(si.getServerConnection(), mor);
		 return hs;
	}

	public ServiceInstance getSi() {
		return si;
	}

	public void setSi(ServiceInstance si) {
		this.si = si;
	}

	
}
