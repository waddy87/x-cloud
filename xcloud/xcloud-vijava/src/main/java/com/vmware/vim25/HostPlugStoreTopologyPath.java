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
public class HostPlugStoreTopologyPath extends DynamicData {
  public String key;
  public String name;
  public Integer channelNumber;
  public Integer targetNumber;
  public Integer lunNumber;
  public String adapter;
  public String target;
  public String device;

  public String getKey() {
    return this.key;
  }

  public String getName() {
    return this.name;
  }

  public Integer getChannelNumber() {
    return this.channelNumber;
  }

  public Integer getTargetNumber() {
    return this.targetNumber;
  }

  public Integer getLunNumber() {
    return this.lunNumber;
  }

  public String getAdapter() {
    return this.adapter;
  }

  public String getTarget() {
    return this.target;
  }

  public String getDevice() {
    return this.device;
  }

  public void setKey(String key) {
    this.key=key;
  }

  public void setName(String name) {
    this.name=name;
  }

  public void setChannelNumber(Integer channelNumber) {
    this.channelNumber=channelNumber;
  }

  public void setTargetNumber(Integer targetNumber) {
    this.targetNumber=targetNumber;
  }

  public void setLunNumber(Integer lunNumber) {
    this.lunNumber=lunNumber;
  }

  public void setAdapter(String adapter) {
    this.adapter=adapter;
  }

  public void setTarget(String target) {
    this.target=target;
  }

  public void setDevice(String device) {
    this.device=device;
  }
}
