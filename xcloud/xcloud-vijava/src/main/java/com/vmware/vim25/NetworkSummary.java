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
public class NetworkSummary extends DynamicData {
  public ManagedObjectReference network;
  public String name;
  public boolean accessible;
  public String ipPoolName;
  public Integer ipPoolId;

  public ManagedObjectReference getNetwork() {
    return this.network;
  }

  public String getName() {
    return this.name;
  }

  public boolean isAccessible() {
    return this.accessible;
  }

  public String getIpPoolName() {
    return this.ipPoolName;
  }

  public Integer getIpPoolId() {
    return this.ipPoolId;
  }

  public void setNetwork(ManagedObjectReference network) {
    this.network=network;
  }

  public void setName(String name) {
    this.name=name;
  }

  public void setAccessible(boolean accessible) {
    this.accessible=accessible;
  }

  public void setIpPoolName(String ipPoolName) {
    this.ipPoolName=ipPoolName;
  }

  public void setIpPoolId(Integer ipPoolId) {
    this.ipPoolId=ipPoolId;
  }
}
