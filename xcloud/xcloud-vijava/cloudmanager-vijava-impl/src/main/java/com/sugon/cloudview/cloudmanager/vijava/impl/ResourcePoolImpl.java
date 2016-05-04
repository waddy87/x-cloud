package com.sugon.cloudview.cloudmanager.vijava.impl;

import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sugon.cloudview.cloudmanager.vijava.api.ResourcePoolI;
import com.sugon.cloudview.cloudmanager.vijava.vm.CreateVMTask;
import com.sugon.vim25.ManagedObjectReference;
import com.sugon.vim25.mo.ClusterComputeResource;
import com.sugon.vim25.mo.ResourcePool;
import com.sugon.vim25.mo.ServiceInstance;
import com.sugon.vim25.mo.util.MorUtil;

public class ResourcePoolImpl implements ResourcePoolI {
	private static Logger logger = LoggerFactory.getLogger(ResourcePoolImpl.class);

	ServiceInstance si;

	public ServiceInstance getSi() {
		return si;
	}

	public void setSi(ServiceInstance si) {
		this.si = si;
	}

	public ResourcePool getResoucePoolbyMoid(String moid) throws RemoteException {
		ResourcePool rp = null;
		try {
			ManagedObjectReference mor = new ManagedObjectReference();
			mor.set_value(moid);
			mor.setType("ResourcePool");
			rp = (ResourcePool) MorUtil.createExactManagedEntity(
					si.getServerConnection(), mor);
			logger.debug("resource pool is {}", rp.getName());
		} catch (Exception e) {
			logger.error("can not find resource pool " + moid);
			throw new RemoteException("can not find resource pool" + moid);
		}
		return rp;

	}
}
