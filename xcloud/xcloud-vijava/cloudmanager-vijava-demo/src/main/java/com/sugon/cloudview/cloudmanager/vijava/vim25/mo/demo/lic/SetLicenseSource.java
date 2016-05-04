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

package com.sugon.cloudview.cloudmanager.vijava.vim25.mo.demo.lic;

import java.net.URL;

import com.sugon.vim25.LicenseServerSource;
import com.sugon.vim25.mo.LicenseManager;
import com.sugon.vim25.mo.ServiceInstance;

/**
 * http://vijava.sf.net
 * @author Steve Jin
 */
public class SetLicenseSource
{  
  public static void main(String[] args) throws Exception
  {
    if(args.length != 3)
    {
      System.out.println("Usage: java SetLicenseSource <url> " 
        + "<username> <password>");
      return;
    }
    ServiceInstance si = new ServiceInstance(
      new URL(args[0]), args[1], args[2], true);
    LicenseManager lm = si.getLicenseManager();
    
    LicenseServerSource lss = new LicenseServerSource();
    // please change it to a license server you can access
    lss.setLicenseServer("27000@lic-serv.acme.com");
    
    lm.configureLicenseSource(null, lss);
    lm.setLicenseEdition(null, "esxFull");

    boolean enabled = lm.checkLicenseFeature(null, "iscsi");
    System.out.println("ISCSI enabled:" + enabled);

    lm.disableFeature(null, "iscsi");
    enabled = lm.checkLicenseFeature(null, "iscsi");
    System.out.println("ISCSI enabled:" + enabled);

    lm.enableFeature(null, "iscsi");
    enabled = lm.checkLicenseFeature(null, "iscsi");
    System.out.println("ISCSI enabled:" + enabled);
    
    si.getServerConnection().logout();
  }
}
