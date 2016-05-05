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

package com.sugon.cloudview.cloudmanager.vijava.vim25.mo.demo.storage;

import java.net.URL;

import com.sugon.vim25.DatastoreInfo;
import com.sugon.vim25.HostNasVolumeSpec;
import com.sugon.vim25.mo.Datastore;
import com.sugon.vim25.mo.Folder;
import com.sugon.vim25.mo.HostDatastoreSystem;
import com.sugon.vim25.mo.HostSystem;
import com.sugon.vim25.mo.InventoryNavigator;
import com.sugon.vim25.mo.ServiceInstance;

/**
 * http://vijava.sf.net
 * @author Steve Jin
 */

public class AddDatastore
{
  public static void main(String[] args) throws Exception
  {
    if(args.length != 3)
    {
      System.out.println("Usage: java AddDatastore " 
        + "<url> <username> <password>");
      return;
    }

    ServiceInstance si = new ServiceInstance(
      new URL(args[0]), args[1], args[2], true);
    
    String hostname = "sjin-dev1.eng.vmware.com";

    Folder rootFolder = si.getRootFolder();
    HostSystem host = null;

    host = (HostSystem) new InventoryNavigator(
        rootFolder).searchManagedEntity("HostSystem", hostname);
  
    if(host==null)
    {
      System.out.println("Host not found");
      si.getServerConnection().logout();
      return;
    }
    
    HostDatastoreSystem hds = host.getHostDatastoreSystem();
    
    HostNasVolumeSpec hnvs = new HostNasVolumeSpec();
    hnvs.setRemoteHost("10.20.140.25");
    hnvs.setRemotePath("/home/vm_share");
    hnvs.setLocalPath("VM_Share");
    hnvs.setAccessMode("readWrite"); // or, "readOnly"
    
    Datastore ds = hds.createNasDatastore(hnvs);
    DatastoreInfo di = ds.getInfo();
    
    System.out.println("Name:" + di.getName());
    System.out.println("FreeSpace:" + di.getFreeSpace());
    
    si.getServerConnection().logout();
  }
}
