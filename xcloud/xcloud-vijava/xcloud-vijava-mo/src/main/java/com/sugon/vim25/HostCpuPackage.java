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
public class HostCpuPackage extends DynamicData {
  public short index;
  public String vendor;
  public long hz;
  public long busHz;
  public String description;
  public short[] threadId;
  public HostCpuIdInfo[] cpuFeature;

  public short getIndex() {
    return this.index;
  }

  public String getVendor() {
    return this.vendor;
  }

  public long getHz() {
    return this.hz;
  }

  public long getBusHz() {
    return this.busHz;
  }

  public String getDescription() {
    return this.description;
  }

  public short[] getThreadId() {
    return this.threadId;
  }

  public HostCpuIdInfo[] getCpuFeature() {
    return this.cpuFeature;
  }

  public void setIndex(short index) {
    this.index=index;
  }

  public void setVendor(String vendor) {
    this.vendor=vendor;
  }

  public void setHz(long hz) {
    this.hz=hz;
  }

  public void setBusHz(long busHz) {
    this.busHz=busHz;
  }

  public void setDescription(String description) {
    this.description=description;
  }

  public void setThreadId(short[] threadId) {
    this.threadId=threadId;
  }

  public void setCpuFeature(HostCpuIdInfo[] cpuFeature) {
    this.cpuFeature=cpuFeature;
  }
}
