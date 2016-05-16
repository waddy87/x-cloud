package com.sugon.vim25.mo;

import com.sugon.vim25.HostAuthenticationStoreInfo;
import com.sugon.vim25.ManagedObjectReference;

public class HostAuthenticationStore extends ManagedObject
{
  public HostAuthenticationStore(ServerConnection sc, ManagedObjectReference mor) 
  {
    super(sc, mor);
  }
  
  public HostAuthenticationStoreInfo getInfo()
  {
    return (HostAuthenticationStoreInfo) getCurrentProperty("info");
  }
}
