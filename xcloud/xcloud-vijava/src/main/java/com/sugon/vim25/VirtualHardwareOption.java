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
public class VirtualHardwareOption extends DynamicData {
  public int hwVersion;
  public VirtualDeviceOption[] virtualDeviceOption;
  public boolean deviceListReadonly;
  public int[] numCPU;
  public IntOption numCoresPerSocket;
  public boolean numCpuReadonly;
  public LongOption memoryMB;
  public IntOption numPCIControllers;
  public IntOption numIDEControllers;
  public IntOption numUSBControllers;
  public IntOption numUSBXHCIControllers;
  public IntOption numSIOControllers;
  public IntOption numPS2Controllers;
  public String[] licensingLimit;
  public IntOption numSupportedWwnPorts;
  public IntOption numSupportedWwnNodes;
  public ResourceConfigOption resourceConfigOption;

  public int getHwVersion() {
    return this.hwVersion;
  }

  public VirtualDeviceOption[] getVirtualDeviceOption() {
    return this.virtualDeviceOption;
  }

  public boolean isDeviceListReadonly() {
    return this.deviceListReadonly;
  }

  public int[] getNumCPU() {
    return this.numCPU;
  }

  public IntOption getNumCoresPerSocket() {
    return this.numCoresPerSocket;
  }

  public boolean isNumCpuReadonly() {
    return this.numCpuReadonly;
  }

  public LongOption getMemoryMB() {
    return this.memoryMB;
  }

  public IntOption getNumPCIControllers() {
    return this.numPCIControllers;
  }

  public IntOption getNumIDEControllers() {
    return this.numIDEControllers;
  }

  public IntOption getNumUSBControllers() {
    return this.numUSBControllers;
  }

  public IntOption getNumUSBXHCIControllers() {
    return this.numUSBXHCIControllers;
  }

  public IntOption getNumSIOControllers() {
    return this.numSIOControllers;
  }

  public IntOption getNumPS2Controllers() {
    return this.numPS2Controllers;
  }

  public String[] getLicensingLimit() {
    return this.licensingLimit;
  }

  public IntOption getNumSupportedWwnPorts() {
    return this.numSupportedWwnPorts;
  }

  public IntOption getNumSupportedWwnNodes() {
    return this.numSupportedWwnNodes;
  }

  public ResourceConfigOption getResourceConfigOption() {
    return this.resourceConfigOption;
  }

  public void setHwVersion(int hwVersion) {
    this.hwVersion=hwVersion;
  }

  public void setVirtualDeviceOption(VirtualDeviceOption[] virtualDeviceOption) {
    this.virtualDeviceOption=virtualDeviceOption;
  }

  public void setDeviceListReadonly(boolean deviceListReadonly) {
    this.deviceListReadonly=deviceListReadonly;
  }

  public void setNumCPU(int[] numCPU) {
    this.numCPU=numCPU;
  }

  public void setNumCoresPerSocket(IntOption numCoresPerSocket) {
    this.numCoresPerSocket=numCoresPerSocket;
  }

  public void setNumCpuReadonly(boolean numCpuReadonly) {
    this.numCpuReadonly=numCpuReadonly;
  }

  public void setMemoryMB(LongOption memoryMB) {
    this.memoryMB=memoryMB;
  }

  public void setNumPCIControllers(IntOption numPCIControllers) {
    this.numPCIControllers=numPCIControllers;
  }

  public void setNumIDEControllers(IntOption numIDEControllers) {
    this.numIDEControllers=numIDEControllers;
  }

  public void setNumUSBControllers(IntOption numUSBControllers) {
    this.numUSBControllers=numUSBControllers;
  }

  public void setNumUSBXHCIControllers(IntOption numUSBXHCIControllers) {
    this.numUSBXHCIControllers=numUSBXHCIControllers;
  }

  public void setNumSIOControllers(IntOption numSIOControllers) {
    this.numSIOControllers=numSIOControllers;
  }

  public void setNumPS2Controllers(IntOption numPS2Controllers) {
    this.numPS2Controllers=numPS2Controllers;
  }

  public void setLicensingLimit(String[] licensingLimit) {
    this.licensingLimit=licensingLimit;
  }

  public void setNumSupportedWwnPorts(IntOption numSupportedWwnPorts) {
    this.numSupportedWwnPorts=numSupportedWwnPorts;
  }

  public void setNumSupportedWwnNodes(IntOption numSupportedWwnNodes) {
    this.numSupportedWwnNodes=numSupportedWwnNodes;
  }

  public void setResourceConfigOption(ResourceConfigOption resourceConfigOption) {
    this.resourceConfigOption=resourceConfigOption;
  }
}
