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

package com.vmware.vim25;

/**
* @author Steve Jin (http://www.doublecloud.org)
* @version 5.1
*/

@SuppressWarnings("all")
public class HostVirtualNicSpec extends DynamicData {
  public HostIpConfig ip;
  public String mac;
  public DistributedVirtualSwitchPortConnection distributedVirtualPort;
  public String portgroup;
  public Integer mtu;
  public Boolean tsoEnabled;
  public String netStackInstanceKey;

  public HostIpConfig getIp() {
    return this.ip;
  }

  public String getMac() {
    return this.mac;
  }

  public DistributedVirtualSwitchPortConnection getDistributedVirtualPort() {
    return this.distributedVirtualPort;
  }

  public String getPortgroup() {
    return this.portgroup;
  }

  public Integer getMtu() {
    return this.mtu;
  }

  public Boolean getTsoEnabled() {
    return this.tsoEnabled;
  }

  public String getNetStackInstanceKey() {
    return this.netStackInstanceKey;
  }

  public void setIp(HostIpConfig ip) {
    this.ip=ip;
  }

  public void setMac(String mac) {
    this.mac=mac;
  }

  public void setDistributedVirtualPort(DistributedVirtualSwitchPortConnection distributedVirtualPort) {
    this.distributedVirtualPort=distributedVirtualPort;
  }

  public void setPortgroup(String portgroup) {
    this.portgroup=portgroup;
  }

  public void setMtu(Integer mtu) {
    this.mtu=mtu;
  }

  public void setTsoEnabled(Boolean tsoEnabled) {
    this.tsoEnabled=tsoEnabled;
  }

  public void setNetStackInstanceKey(String netStackInstanceKey) {
    this.netStackInstanceKey=netStackInstanceKey;
  }
}
