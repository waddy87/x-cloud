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
public class VirtualDiskFlatVer2BackingInfo extends VirtualDeviceFileBackingInfo {
  public String diskMode;
  public Boolean split;
  public Boolean writeThrough;
  public Boolean thinProvisioned;
  public Boolean eagerlyScrub;
  public String uuid;
  public String contentId;
  public String changeId;
  public VirtualDiskFlatVer2BackingInfo parent;
  public String deltaDiskFormat;
  public Boolean digestEnabled;
  public Integer deltaGrainSize;

  public String getDiskMode() {
    return this.diskMode;
  }

  public Boolean getSplit() {
    return this.split;
  }

  public Boolean getWriteThrough() {
    return this.writeThrough;
  }

  public Boolean getThinProvisioned() {
    return this.thinProvisioned;
  }

  public Boolean getEagerlyScrub() {
    return this.eagerlyScrub;
  }

  public String getUuid() {
    return this.uuid;
  }

  public String getContentId() {
    return this.contentId;
  }

  public String getChangeId() {
    return this.changeId;
  }

  public VirtualDiskFlatVer2BackingInfo getParent() {
    return this.parent;
  }

  public String getDeltaDiskFormat() {
    return this.deltaDiskFormat;
  }

  public Boolean getDigestEnabled() {
    return this.digestEnabled;
  }

  public Integer getDeltaGrainSize() {
    return this.deltaGrainSize;
  }

  public void setDiskMode(String diskMode) {
    this.diskMode=diskMode;
  }

  public void setSplit(Boolean split) {
    this.split=split;
  }

  public void setWriteThrough(Boolean writeThrough) {
    this.writeThrough=writeThrough;
  }

  public void setThinProvisioned(Boolean thinProvisioned) {
    this.thinProvisioned=thinProvisioned;
  }

  public void setEagerlyScrub(Boolean eagerlyScrub) {
    this.eagerlyScrub=eagerlyScrub;
  }

  public void setUuid(String uuid) {
    this.uuid=uuid;
  }

  public void setContentId(String contentId) {
    this.contentId=contentId;
  }

  public void setChangeId(String changeId) {
    this.changeId=changeId;
  }

  public void setParent(VirtualDiskFlatVer2BackingInfo parent) {
    this.parent=parent;
  }

  public void setDeltaDiskFormat(String deltaDiskFormat) {
    this.deltaDiskFormat=deltaDiskFormat;
  }

  public void setDigestEnabled(Boolean digestEnabled) {
    this.digestEnabled=digestEnabled;
  }

  public void setDeltaGrainSize(Integer deltaGrainSize) {
    this.deltaGrainSize=deltaGrainSize;
  }
}
