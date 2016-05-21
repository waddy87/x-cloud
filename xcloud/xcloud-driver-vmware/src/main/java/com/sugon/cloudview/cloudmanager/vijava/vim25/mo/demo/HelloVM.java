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

package com.sugon.cloudview.cloudmanager.vijava.vim25.mo.demo;

import java.net.URL;

import com.sugon.cloudview.cloudmanager.util.JsonUtil;
import com.sugon.vim25.*;
import com.sugon.vim25.mo.*;

public class HelloVM {

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		ServiceInstance si = new ServiceInstance(new URL("https://10.0.33.71/sdk"), "administrator", "Sugon!!123",
				true);
		long end = System.currentTimeMillis();
		System.out.println("time taken:" + (end - start));
		Folder rootFolder = si.getRootFolder();
		String name = rootFolder.getName();
		System.out.println("root:" + name);
		//查询存储
		ManagedEntity[] mes = new InventoryNavigator(rootFolder).searchManagedEntities("Datastore");
//		for(ManagedEntity entity : mes){
//			Datastore ds = (Datastore) entity;
//			DatastoreInfo info = ds.getInfo();
//			System.out.println(ds.getSummary().type+" - "+JsonUtil.toJson(info));
//		}
		//查询网络
		mes = new InventoryNavigator(rootFolder).searchManagedEntities("Network");
		for(ManagedEntity entity : mes){
			Network net = (Network)entity;
			System.out.println(net.getName()+" - " + net );
		}
//		ManagedEntity[] mes = new InventoryNavigator(rootFolder).searchManagedEntities("VirtualMachine");
//		if (mes == null || mes.length == 0) {
//			return;
//		}
//
//		System.out.println("vm count："+mes.length);
//		VirtualMachine vm = (VirtualMachine) mes[0];
//		//vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", "wchchtest");
//
//		VirtualMachineConfigInfo vminfo = vm.getConfig();
//		VirtualMachineCapability vmc = vm.getCapability();
//
//		vm.getResourcePool();
//		System.out.println("Hello " + vm.getName());
//		System.out.println("GuestOS: " + vminfo.getGuestFullName());
//		System.out.println("Multiple snapshot supported: " + vmc.isMultipleSnapshotsSupported());

		si.getServerConnection().logout();
	}

}
