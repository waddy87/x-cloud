/*================================================================================
Copyright (c) 2008 VMware, Inc. All Rights Reserved.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, 
this list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice, 
this list of conditions and the following disclaimer in the documentation 
and/or other materials provided with the distribution.

* Neither the name of VMware, Inc. nor the names of its contributors may be used
to endorse or promote products derived from this software without specific prior 
written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
IN NO EVENT SHALL VMWARE, INC. OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
POSSIBILITY OF SUCH DAMAGE.
================================================================================*/

package com.vmware.vim25.mo;

import java.rmi.RemoteException;

import com.vmware.vim25.*;
import com.vmware.vim25.mo.util.*;

/**
 * The managed object class corresponding to the one defined in VI SDK API reference.
 * @author Steve JIN (http://www.doublecloud.org)
 */

public class Folder extends ManagedEntity 
{
	public Folder(ServerConnection sc, ManagedObjectReference mor) 
	{
		super(sc, mor);
	}
	
	// the array could have different real types, therefore cannot use getManagedObjects()
	public ManagedEntity[] getChildEntity() throws InvalidProperty, RuntimeFault, RemoteException 
	{
		ManagedObjectReference[] mors = (ManagedObjectReference[]) getCurrentProperty("childEntity");;
		
		if(mors==null)
		{
			return new ManagedEntity[] {};
		}
		
		ManagedEntity[] mes = new ManagedEntity[mors.length];
		for(int i=0; i<mors.length; i++)
		{
			mes[i] = MorUtil.createExactManagedEntity(getServerConnection(), mors[i]);
		}
		return mes;
	}

	public String[] getChildType()
	{
		return (String[]) getCurrentProperty("childType");
	}

	// SDK2.5 signature for back compatibility
	public Task addStandaloneHost_Task(HostConnectSpec spec, ComputeResourceConfigSpec compResSpec, boolean addConnected) throws InvalidLogin, HostConnectFault, RuntimeFault, RemoteException 
	{
		return addStandaloneHost_Task(spec, compResSpec, addConnected, null);
	}

	// new 4.0 signature
	public Task addStandaloneHost_Task(HostConnectSpec spec, ComputeResourceConfigSpec compResSpec, boolean addConnected, String license) throws InvalidLogin, HostConnectFault, RuntimeFault, RemoteException 
	{
		return new Task(getServerConnection(),
			getVimService().addStandaloneHost_Task(getMOR(), spec, compResSpec, addConnected, license));
	}

	public ClusterComputeResource createCluster(String name, ClusterConfigSpec spec) throws InvalidName, DuplicateName, RuntimeFault, RemoteException 
	{
		return new ClusterComputeResource(getServerConnection(),
			getVimService().createCluster(getMOR(), name, spec) );
	}
	
	public ClusterComputeResource createClusterEx(String name, ClusterConfigSpecEx spec) throws InvalidName, DuplicateName, RuntimeFault, RemoteException 
	{
		return new ClusterComputeResource(getServerConnection(),
			getVimService().createClusterEx(getMOR(), name, spec) );
	}
	
	public Datacenter createDatacenter(String name) throws InvalidName, DuplicateName, RuntimeFault, RemoteException 
	{
		return new Datacenter(getServerConnection(),
			getVimService().createDatacenter(getMOR(), name) );
	}
	
	/**
	 * @since 4.0
	 */
	public Task createDVS_Task(DVSCreateSpec spec) throws DvsNotAuthorized, DvsFault, DuplicateName, InvalidName, NotFound, RuntimeFault, RemoteException
	{
		ManagedObjectReference taskMor = getVimService().createDVS_Task(getMOR(), spec);
		return new Task(getServerConnection(), taskMor);
	}
	
	public Folder createFolder(String name) throws InvalidName, DuplicateName, RuntimeFault, RemoteException 
	{
		return new Folder(getServerConnection(),
			getVimService().createFolder(getMOR(), name) );
	}

	/**
	 * @since SDK5.0
	 */
	public StoragePod createStoragePod(String name) throws DuplicateName, InvalidName, RuntimeFault, RemoteException
	{
	  ManagedObjectReference mor = getVimService().createStoragePod(getMOR(), name);
	  return new StoragePod(getServerConnection(), mor);
	}
	
	public Task createVM_Task(VirtualMachineConfigSpec config, ResourcePool pool, HostSystem host) throws InvalidName, VmConfigFault, DuplicateName, FileFault, OutOfBounds, InsufficientResourcesFault, InvalidDatastore, RuntimeFault, RemoteException 
	{
		return new Task(getServerConnection(),
			getVimService().createVM_Task(getMOR(), config, pool.getMOR(), host==null? null : host.getMOR()) );
	}
	
	public Task moveIntoFolder_Task(ManagedEntity[] entities) throws DuplicateName, InvalidState, InvalidFolder, RuntimeFault, RemoteException 
	{
		if(entities==null)
		{
			throw new IllegalArgumentException("entities must not be null");
		}
			
		return new Task( getServerConnection(),
			getVimService().moveIntoFolder_Task(getMOR(), MorUtil.createMORs(entities)) );
	}
	
	public Task registerVM_Task(String path, String name, boolean asTemplate, ResourcePool pool, HostSystem host) throws VmConfigFault, InvalidName, DuplicateName, FileFault, OutOfBounds, InsufficientResourcesFault, InvalidDatastore, AlreadyExists, NotFound, RuntimeFault, RemoteException 
	{
		return new Task( getServerConnection(),
			getVimService().registerVM_Task(getMOR(), path, name, asTemplate, 
			pool==null? null : pool.getMOR(), host==null? null : host.getMOR()) );
	}
	
	public Task unregisterAndDestroy_Task() throws InvalidState, ConcurrentAccess, RuntimeFault, RemoteException 
	{
		return new Task( getServerConnection(),
			getVimService().unregisterAndDestroy_Task(getMOR()) );
	}
}
