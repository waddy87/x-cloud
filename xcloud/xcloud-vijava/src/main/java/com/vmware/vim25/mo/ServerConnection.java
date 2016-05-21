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

package com.vmware.vim25.mo;

import java.net.URL;

import com.vmware.vim25.*;
import com.vmware.vim25.ws.WSClient;

/**
 * The class representing the connection to a server either VC server or ESX.
 * @author Steve JIN (http://www.doublecloud.org)
 */
final public class ServerConnection
{
	private URL url = null;
	private UserSession userSession = null;
	private ServiceInstance serviceInstance = null;
	private VimPortType vimService = null;
	
	public ServerConnection(URL url, VimPortType vimService, ServiceInstance serviceInstance)
	{
		this.url = url;
		this.vimService = vimService;
		this.serviceInstance = serviceInstance;
	}

	/**
	 * @return the current session string in format like:
	 * vmware_soap_session="B3240D15-34DF-4BB8-B902-A844FDF42E85"
	 */
	public String getSessionStr()
	{
		WSClient wsc = vimService.getWsc();
		return wsc.getCookie();
	}
	
	/**
	 * Disconnect from the server and clean up
	 */
	public void logout()
	{
		if(vimService!=null)
		{
			try
			{
				serviceInstance.getSessionManager().logout();
			} catch (Exception e)
			{
				System.err.println("Failed to disconnect...");
			}
			vimService =null;
			serviceInstance = null;
		}
	}

	public ServiceInstance getServiceInstance()
	{
		return serviceInstance;
	}
	
	public VimPortType getVimService() 
	{
		return vimService;
	}
	
	public URL getUrl() 
	{
		return url;
	}
	
	public String getUsername() 
	{
		return userSession.getUserName();
	}
	
	/** 
	 * @deprecated
	 * This method returns a cached UserSession which holds dynamic properties,
	 * for example, lastActiveTime, but are not updated. To avoid this confusion,
	 * deprecate it. Also see bug: 3403474
	 * To get updated UserSession, always use SessionManager. 
	 */
	public UserSession getUserSession() 
	{
		return userSession;
	}
	
	void setUserSession(UserSession userSession) 
	{
		this.userSession = userSession;
	}
	
	void setServiceInstance(ServiceInstance si)
	{
	  this.serviceInstance = si;
	}
//	@Override
//	protected void finalize() throws Throwable 
//	{
//		logout(); //last defense to log out the connection
//		super.finalize();
//	}
//	
	
}
