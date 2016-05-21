package org.waddys.xcloud.vijava.vim25.mo.demo;
import static com.sugon.vim.cf.NullObject.NULL;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sugon.vim.cf.CacheInstance;
import com.sugon.vim25.VirtualMachineSummary;
import com.sugon.vim25.mo.Folder;
import com.sugon.vim25.mo.InventoryNavigator;
import com.sugon.vim25.mo.ManagedEntity;
import com.sugon.vim25.mo.ServiceInstance;

public class CacheFrameworkSample
{
  public static void main(String[] args) throws Exception
  {
    ServiceInstance si = new ServiceInstance(new URL("http://10.20.143.205/sdk"), "root", "password", true); 
    Folder rootFolder = si.getRootFolder();
    ManagedEntity[] vms = new InventoryNavigator(rootFolder).searchManagedEntities("VirtualMachine");
    ManagedEntity[] hosts = new InventoryNavigator(rootFolder).searchManagedEntities("HostSystem");
  
    CacheInstance vicf = new CacheInstance(si);
    vicf.watch(vms, new String[] {"name", "runtime.powerState", "summary"});
    vicf.watch(hosts, new String[] {"name", "summary"});
    vicf.start();

    //check if the caching is ready to use; otherwise wait
    while(!vicf.isReady()) 
    {
    	Thread.sleep(1000);
    }
    
    Thread[] vrs = new VimReader[2];

    for(int i=0; i<vrs.length; i++)
    {
      vrs[i] = new VimReader("Thread " + i, vicf, vms, hosts);
      vrs[i].start();
    }
    
    for(int i=0; i<vrs.length; i++)
    {
      vrs[i].join();
    }
    si.getServerConnection().logout();
  }
}

class VimReader extends Thread
{
  private CacheInstance vicf;
  private ManagedEntity[] vms;
  private ManagedEntity[] hosts;
  
  public VimReader(String name, CacheInstance vicf, ManagedEntity[] vms, ManagedEntity[] hosts)
  {
    super(name);
    this.vicf = vicf;
    this.vms = vms;
    this.hosts = hosts;
  }
  
  public void run()
  {
    for(;;)
    {
      for(int i=0; i<1; i++)
      {
        String name = (String) vicf.get(vms[i], "name");
        SimpleDateFormat sdf = new SimpleDateFormat();
        Object power = vicf.get(vms[i], "runtime.powerState");
        //show how to test the null value
        if(power==NULL) // or == NullObject.NULL
        {
        	System.out.println("power is null");
        }
        VirtualMachineSummary summary = (VirtualMachineSummary )vicf.get(vms[i], "summary");
        System.out.println(this.getName() + " reading vm: " + name + " = " + power + " @ " + sdf.format(new Date(System.currentTimeMillis())));//+ summary.getRuntime().getMaxMemoryUsage());
      }
      
      for(int i=0; i<1; i++)
      {
        String name = (String) vicf.get(hosts[i], "name");
        Object summary = vicf.get(hosts[i], "summary");
        System.out.println(this.getName() + " reading host: " + name + " = " + summary);
      }
      
      System.out.flush();
      try
      {
        Thread.sleep(30000);
      } catch(Exception e)
      {}
    }
  }
}
