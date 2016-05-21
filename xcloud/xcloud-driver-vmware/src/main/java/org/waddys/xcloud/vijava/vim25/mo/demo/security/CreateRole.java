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

package org.waddys.xcloud.vijava.vim25.mo.demo.security;

import java.net.URL;

import com.sugon.vim25.Permission;
import com.sugon.vim25.mo.AuthorizationManager;
import com.sugon.vim25.mo.ServiceInstance;

/**
 * http://vijava.sf.net
 * @author Steve Jin
 */

public class CreateRole
{
  public static void main(String[] args) throws Exception
  {
    if(args.length != 3)
    {
      System.out.println("Usage: java CreateRole <url>" 
        + " <username> <password>");
      return;
    }
    
    ServiceInstance si = new ServiceInstance(
        new URL(args[0]), args[1], args[2], true);

    AuthorizationManager am = si.getAuthorizationManager();
    
    int roleId = am.addAuthorizationRole("master1", 
        new String[] { "System.View", "System.Read", 
        "System.Anonymous", "Global.LogEvent" } );
    
    //even if you just want to rename the role, you 
    // still need to provide full list of privileges
    am.updateAuthorizationRole(roleId, "master", 
        new String[] { "System.View", "System.Read", 
        "System.Anonymous", "Global.LogEvent",
        "Global.Diagnostics", "Folder.Create"} );
    
    System.out.println("The new role ID: " + roleId);
    
    Permission perm = new Permission();
    perm.setGroup(false); // false for user, true for group
    perm.setPrincipal("vimaster"); // the vimaster must exist
    perm.setPropagate(true); // propagate down the hierarchy
    perm.setRoleId(roleId);
    
    am.setEntityPermissions( si.getRootFolder(), 
        new Permission[] {perm} );
    
    Permission[] ps = am.retrieveEntityPermissions(
        si.getRootFolder(), false);
    
    System.out.println("print the permissions on root:");
    printPermissions(ps);
    
    si.getServerConnection().logout();
  }
  
  static void printPermissions(Permission[] ps)
  {
    for(int i=0; ps!=null && i< ps.length; i++)
    {
      System.out.println("\nEntity:" 
          + ps[i].getEntity().getType() + ":" 
          + ps[i].getEntity().get_value());
      System.out.println("IsGroup:" + ps[i].isGroup());
      System.out.println("Principal:" + ps[i].getPrincipal());
      System.out.println("Propogated:" + ps[i].isPropagate());
      System.out.println("RoleId:" + ps[i].getRoleId());
    }
  }
}
