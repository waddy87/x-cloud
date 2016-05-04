/*================================================================================
Copyright (c) 2013 Steve Jin. All Rights Reserved.

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

package com.sugon.vim25.mo;

import java.rmi.RemoteException;

import com.sugon.vim25.HostConfigFault;
import com.sugon.vim25.HostVFlashManagerVFlashCacheConfigSpec;
import com.sugon.vim25.HostVFlashManagerVFlashConfigInfo;
import com.sugon.vim25.HostVFlashManagerVFlashResourceConfigSpec;
import com.sugon.vim25.InaccessibleVFlashSource;
import com.sugon.vim25.ManagedObjectReference;
import com.sugon.vim25.NotFound;
import com.sugon.vim25.ResourceInUse;
import com.sugon.vim25.RuntimeFault;
import com.sugon.vim25.VirtualDiskVFlashCacheConfigInfo;

/**
 * The managed object class corresponding to the one defined in VI SDK API reference.
 * @author Steve JIN (http://www.doublecloud.org)
 * @since SDK5.5
 */

public class HostVFlashManager extends ManagedObject 
{
	public HostVFlashManager(ServerConnection serverConnection, ManagedObjectReference mor) 
	{
		super(serverConnection, mor);
	}
	
	public HostVFlashManagerVFlashConfigInfo getVFlashConfigInfo()
	{
	  return (HostVFlashManagerVFlashConfigInfo) getCurrentProperty("vFlashConfigInfo");
	}
	
	public Task configureVFlashResourceEx_Task(String[] devicePath) throws HostConfigFault, RuntimeFault, RemoteException 
	{
		ManagedObjectReference mor = getVimService().configureVFlashResourceEx_Task(this.getMOR(), devicePath);
		return new Task(getServerConnection(), mor);
	}
	
	public void hostConfigureVFlashResource(HostVFlashManagerVFlashResourceConfigSpec spec) throws HostConfigFault, ResourceInUse, RuntimeFault, RemoteException
	{
	  getVimService().hostConfigureVFlashResource(this.getMOR(), spec);
	}
	
	public void hostConfigVFlashCache(HostVFlashManagerVFlashCacheConfigSpec spec) throws HostConfigFault, InaccessibleVFlashSource, ResourceInUse, RuntimeFault, RemoteException
	{
	  getVimService().hostConfigVFlashCache(this.getMOR(), spec);
	}
	
	public VirtualDiskVFlashCacheConfigInfo hostGetVFlashModuleDefaultConfig(String vFlashModule) throws NotFound, HostConfigFault, RuntimeFault, RemoteException
	{
	  return getVimService().hostGetVFlashModuleDefaultConfig(this.getMOR(), vFlashModule);
	}
	
	public void hostRemoveVFlashResource() throws NotFound, HostConfigFault, ResourceInUse, RuntimeFault, RemoteException
	{
	  getVimService().hostRemoveVFlashResource(this.getMOR());
	}
}
