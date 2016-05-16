package com.sugon.vim25.mo;

import java.rmi.RemoteException;

import com.sugon.vim25.HostConfigFault;
import com.sugon.vim25.PowerSystemCapability;
import com.sugon.vim25.PowerSystemInfo;
import com.sugon.vim25.RuntimeFault;

public class HostPowerSystem extends ManagedObject
{
  public PowerSystemCapability getCapability()
  {
    return (PowerSystemCapability) getCurrentProperty("capability");
  }
  
  public PowerSystemInfo getInfo()
  {
    return (PowerSystemInfo) getCurrentProperty("info");
  }
  
  public void configurePowerPolicy(int key) throws HostConfigFault, RuntimeFault, RemoteException
  {
    getVimService().configurePowerPolicy(getMOR(), key);
  }
}
