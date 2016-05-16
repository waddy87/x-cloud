/*================================================================================
Copyright (c) 2013 Steve Jin. All Rights Reserved.

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

package com.sugon.vim25;

/**
* @author Steve Jin (http://www.doublecloud.org)
* @version 5.1
*/

@SuppressWarnings("all")
public class HostServiceTicket extends DynamicData {
  public String host;
  public Integer port;
  public String sslThumbprint;
  public String service;
  public String serviceVersion;
  public String sessionId;

  public String getHost() {
    return this.host;
  }

  public Integer getPort() {
    return this.port;
  }

  public String getSslThumbprint() {
    return this.sslThumbprint;
  }

  public String getService() {
    return this.service;
  }

  public String getServiceVersion() {
    return this.serviceVersion;
  }

  public String getSessionId() {
    return this.sessionId;
  }

  public void setHost(String host) {
    this.host=host;
  }

  public void setPort(Integer port) {
    this.port=port;
  }

  public void setSslThumbprint(String sslThumbprint) {
    this.sslThumbprint=sslThumbprint;
  }

  public void setService(String service) {
    this.service=service;
  }

  public void setServiceVersion(String serviceVersion) {
    this.serviceVersion=serviceVersion;
  }

  public void setSessionId(String sessionId) {
    this.sessionId=sessionId;
  }
}
