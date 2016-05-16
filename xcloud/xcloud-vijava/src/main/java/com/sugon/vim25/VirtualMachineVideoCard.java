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
public class VirtualMachineVideoCard extends VirtualDevice {
  public Long videoRamSizeInKB;
  public Integer numDisplays;
  public Boolean useAutoDetect;
  public Boolean enable3DSupport;
  public String use3dRenderer;

  public Long getVideoRamSizeInKB() {
    return this.videoRamSizeInKB;
  }

  public Integer getNumDisplays() {
    return this.numDisplays;
  }

  public Boolean getUseAutoDetect() {
    return this.useAutoDetect;
  }

  public Boolean getEnable3DSupport() {
    return this.enable3DSupport;
  }

  public String getUse3dRenderer() {
    return this.use3dRenderer;
  }

  public void setVideoRamSizeInKB(Long videoRamSizeInKB) {
    this.videoRamSizeInKB=videoRamSizeInKB;
  }

  public void setNumDisplays(Integer numDisplays) {
    this.numDisplays=numDisplays;
  }

  public void setUseAutoDetect(Boolean useAutoDetect) {
    this.useAutoDetect=useAutoDetect;
  }

  public void setEnable3DSupport(Boolean enable3DSupport) {
    this.enable3DSupport=enable3DSupport;
  }

  public void setUse3dRenderer(String use3dRenderer) {
    this.use3dRenderer=use3dRenderer;
  }
}
