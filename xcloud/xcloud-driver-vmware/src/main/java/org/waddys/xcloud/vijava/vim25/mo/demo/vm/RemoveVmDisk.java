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

package org.waddys.xcloud.vijava.vim25.mo.demo.vm;

import java.net.URL;

import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.VirtualDeviceConfigSpec;
import com.vmware.vim25.VirtualDeviceConfigSpecFileOperation;
import com.vmware.vim25.VirtualDeviceConfigSpecOperation;
import com.vmware.vim25.VirtualDisk;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * http://vijava.sf.net
 * @author Steve Jin
 */

public class RemoveVmDisk 
{    
  public static void main(String[] args) throws Exception 
  {     
    if(args.length!=5)
    {
      System.out.println("Usage: java RemoveVmDisk <url> " +
            "<username> <password> <vmname> <diskname>");
      System.exit(0);
    }
    String vmname = args[3];
    String diskName = args[4];
 
    ServiceInstance si = new ServiceInstance(
        new URL(args[0]), args[1], args[2], true);

    Folder rootFolder = si.getRootFolder();
    VirtualMachine vm = (VirtualMachine) new InventoryNavigator(
      rootFolder).searchManagedEntity("VirtualMachine", vmname);

    if(vm==null)
    {
      System.out.println("No VM " + vmname + " found");
      si.getServerConnection().logout();
      return;
    }
    
    VirtualMachineConfigSpec vmConfigSpec = 
        new VirtualMachineConfigSpec();

    VirtualDeviceConfigSpec vdiskSpec = 
        createRemoveDiskConfigSpec(vm.getConfig(), diskName);
    vmConfigSpec.setDeviceChange(
        new VirtualDeviceConfigSpec[]{vdiskSpec} );
    Task task = vm.reconfigVM_Task(vmConfigSpec);
    
    if(task.waitForMe()==Task.SUCCESS)
    {
      System.out.println("Disk removed.");
    }
    else
    {
      System.out.println("Error while removing disk");
    }
    
    si.getServerConnection().logout();
  }

  static VirtualDeviceConfigSpec createRemoveDiskConfigSpec(
      VirtualMachineConfigInfo vmConfig, String diskName) 
          throws Exception
  {
    VirtualDeviceConfigSpec diskSpec = 
        new VirtualDeviceConfigSpec();      
    VirtualDisk disk = (VirtualDisk) findVirtualDevice(
        vmConfig, diskName);
      
    if(disk != null)
    {
      diskSpec.setOperation(
          VirtualDeviceConfigSpecOperation.remove);    
      // remove the following line can keep the disk file
      diskSpec.setFileOperation(
          VirtualDeviceConfigSpecFileOperation.destroy);           
      diskSpec.setDevice(disk);
      return diskSpec;
    }
    else 
    {
      throw new Exception("No device found: " + diskName);
    }
  }

  private static VirtualDevice findVirtualDevice(
      VirtualMachineConfigInfo cfg, String name)
  {
    VirtualDevice [] devices = cfg.getHardware().getDevice();
    for(int i=0;devices!=null && i<devices.length; i++)
    {
      if(devices[i].getDeviceInfo().getLabel().equals(name))
      {                             
        return devices[i];
      }
    }
    return null;
  }
}
