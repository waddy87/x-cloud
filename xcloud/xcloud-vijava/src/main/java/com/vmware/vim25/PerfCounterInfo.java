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
public class PerfCounterInfo extends DynamicData {
  public int key;
  public ElementDescription nameInfo;
  public ElementDescription groupInfo;
  public ElementDescription unitInfo;
  public PerfSummaryType rollupType;
  public PerfStatsType statsType;
  public Integer level;
  public Integer perDeviceLevel;
  public int[] associatedCounterId;

  public int getKey() {
    return this.key;
  }

  public ElementDescription getNameInfo() {
    return this.nameInfo;
  }

  public ElementDescription getGroupInfo() {
    return this.groupInfo;
  }

  public ElementDescription getUnitInfo() {
    return this.unitInfo;
  }

  public PerfSummaryType getRollupType() {
    return this.rollupType;
  }

  public PerfStatsType getStatsType() {
    return this.statsType;
  }

  public Integer getLevel() {
    return this.level;
  }

  public Integer getPerDeviceLevel() {
    return this.perDeviceLevel;
  }

  public int[] getAssociatedCounterId() {
    return this.associatedCounterId;
  }

  public void setKey(int key) {
    this.key=key;
  }

  public void setNameInfo(ElementDescription nameInfo) {
    this.nameInfo=nameInfo;
  }

  public void setGroupInfo(ElementDescription groupInfo) {
    this.groupInfo=groupInfo;
  }

  public void setUnitInfo(ElementDescription unitInfo) {
    this.unitInfo=unitInfo;
  }

  public void setRollupType(PerfSummaryType rollupType) {
    this.rollupType=rollupType;
  }

  public void setStatsType(PerfStatsType statsType) {
    this.statsType=statsType;
  }

  public void setLevel(Integer level) {
    this.level=level;
  }

  public void setPerDeviceLevel(Integer perDeviceLevel) {
    this.perDeviceLevel=perDeviceLevel;
  }

  public void setAssociatedCounterId(int[] associatedCounterId) {
    this.associatedCounterId=associatedCounterId;
  }
}
