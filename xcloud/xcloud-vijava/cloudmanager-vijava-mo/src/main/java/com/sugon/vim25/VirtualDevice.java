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
public class VirtualDevice extends DynamicData {
  public int key;
  public Description deviceInfo;
  public VirtualDeviceBackingInfo backing;
  public VirtualDeviceConnectInfo connectable;
  public VirtualDeviceBusSlotInfo slotInfo;
  public Integer controllerKey;
  public Integer unitNumber;

  public int getKey() {
    return this.key;
  }

  public Description getDeviceInfo() {
    return this.deviceInfo;
  }

  public VirtualDeviceBackingInfo getBacking() {
    return this.backing;
  }

  public VirtualDeviceConnectInfo getConnectable() {
    return this.connectable;
  }

  public VirtualDeviceBusSlotInfo getSlotInfo() {
    return this.slotInfo;
  }

  public Integer getControllerKey() {
    return this.controllerKey;
  }

  public Integer getUnitNumber() {
    return this.unitNumber;
  }

  public void setKey(int key) {
    this.key=key;
  }

  public void setDeviceInfo(Description deviceInfo) {
    this.deviceInfo=deviceInfo;
  }

  public void setBacking(VirtualDeviceBackingInfo backing) {
    this.backing=backing;
  }

  public void setConnectable(VirtualDeviceConnectInfo connectable) {
    this.connectable=connectable;
  }

  public void setSlotInfo(VirtualDeviceBusSlotInfo slotInfo) {
    this.slotInfo=slotInfo;
  }

  public void setControllerKey(Integer controllerKey) {
    this.controllerKey=controllerKey;
  }

  public void setUnitNumber(Integer unitNumber) {
    this.unitNumber=unitNumber;
  }
}
