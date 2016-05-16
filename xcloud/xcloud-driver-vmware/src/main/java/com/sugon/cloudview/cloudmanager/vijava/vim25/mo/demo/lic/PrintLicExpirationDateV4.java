/*================================================================================
Copyright (c) 2011 VMware, Inc. All Rights Reserved.

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
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import com.sugon.vim25.KeyAnyValue;
import com.sugon.vim25.LicenseManagerEvaluationInfo;
import com.sugon.vim25.LicenseManagerLicenseInfo;
import com.sugon.vim25.mo.LicenseManager;
import com.sugon.vim25.mo.ServiceInstance;

/**
 * This demo shows how to get the expiration dates of licenses.
 * @author Steve Jin (http://www.doublecloud.org)
 */

public class PrintLicExpirationDateV4
{
  public static void main(String[] args) throws Exception
  {
    ServiceInstance si = new ServiceInstance(new URL("https://8.8.8.8/sdk"), 
        "administrator", "vmware", true);
    LicenseManager lm = si.getLicenseManager();
    LicenseManagerLicenseInfo[] lics = lm.getLicenses();

    DateFormat df = DateFormat.getInstance();
    for(LicenseManagerLicenseInfo lic : lics)
    {
      Date expDate = null;
      
      if("eval".equalsIgnoreCase(lic.getEditionKey()))
      {
        LicenseManagerEvaluationInfo evalInfo = lm.getEvaluation();
        expDate = getExpirationDate(evalInfo.getProperties());
      }
      else
      {
        expDate = getExpirationDate(lic.getProperties());
      }
      
      if(expDate!=null)
      {
        System.out.println(lic.getLicenseKey() + " expires on " + df.format(expDate));
      }
      else
      {
        System.out.println(lic.getLicenseKey() + " expires on NEVER");
      }
    }
  }
  

  public static Date getExpirationDate(KeyAnyValue[] kavs)
  {
    if(kavs!=null)
    {
      for(KeyAnyValue kav : kavs)
      {
        if("expirationDate".equalsIgnoreCase(kav.getKey()))
        {
          Object val = kav.getValue();
          if(val instanceof Calendar)
          {
            Calendar expDate = (Calendar) val;
            return expDate.getTime();
          }
        }
      }
    }
    
    return null;
  }
}
