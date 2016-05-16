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
public class DVSSummary extends DynamicData {
  public String name;
  public String uuid;
  public int numPorts;
  public DistributedVirtualSwitchProductSpec productInfo;
  public ManagedObjectReference[] hostMember;
  public ManagedObjectReference[] vm;
  public ManagedObjectReference[] host;
  public String[] portgroupName;
  public String description;
  public DVSContactInfo contact;
  public Integer numHosts;

  public String getName() {
    return this.name;
  }

  public String getUuid() {
    return this.uuid;
  }

  public int getNumPorts() {
    return this.numPorts;
  }

  public DistributedVirtualSwitchProductSpec getProductInfo() {
    return this.productInfo;
  }

  public ManagedObjectReference[] getHostMember() {
    return this.hostMember;
  }

  public ManagedObjectReference[] getVm() {
    return this.vm;
  }

  public ManagedObjectReference[] getHost() {
    return this.host;
  }

  public String[] getPortgroupName() {
    return this.portgroupName;
  }

  public String getDescription() {
    return this.description;
  }

  public DVSContactInfo getContact() {
    return this.contact;
  }

  public Integer getNumHosts() {
    return this.numHosts;
  }

  public void setName(String name) {
    this.name=name;
  }

  public void setUuid(String uuid) {
    this.uuid=uuid;
  }

  public void setNumPorts(int numPorts) {
    this.numPorts=numPorts;
  }

  public void setProductInfo(DistributedVirtualSwitchProductSpec productInfo) {
    this.productInfo=productInfo;
  }

  public void setHostMember(ManagedObjectReference[] hostMember) {
    this.hostMember=hostMember;
  }

  public void setVm(ManagedObjectReference[] vm) {
    this.vm=vm;
  }

  public void setHost(ManagedObjectReference[] host) {
    this.host=host;
  }

  public void setPortgroupName(String[] portgroupName) {
    this.portgroupName=portgroupName;
  }

  public void setDescription(String description) {
    this.description=description;
  }

  public void setContact(DVSContactInfo contact) {
    this.contact=contact;
  }

  public void setNumHosts(Integer numHosts) {
    this.numHosts=numHosts;
  }
}
