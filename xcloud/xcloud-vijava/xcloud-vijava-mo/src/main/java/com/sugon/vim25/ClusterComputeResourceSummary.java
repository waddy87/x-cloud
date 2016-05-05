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
public class ClusterComputeResourceSummary extends ComputeResourceSummary {
  public int currentFailoverLevel;
  public ClusterDasAdmissionControlInfo admissionControlInfo;
  public int numVmotions;
  public Integer targetBalance;
  public Integer currentBalance;
  public String currentEVCModeKey;
  public ClusterDasData dasData;

  public int getCurrentFailoverLevel() {
    return this.currentFailoverLevel;
  }

  public ClusterDasAdmissionControlInfo getAdmissionControlInfo() {
    return this.admissionControlInfo;
  }

  public int getNumVmotions() {
    return this.numVmotions;
  }

  public Integer getTargetBalance() {
    return this.targetBalance;
  }

  public Integer getCurrentBalance() {
    return this.currentBalance;
  }

  public String getCurrentEVCModeKey() {
    return this.currentEVCModeKey;
  }

  public ClusterDasData getDasData() {
    return this.dasData;
  }

  public void setCurrentFailoverLevel(int currentFailoverLevel) {
    this.currentFailoverLevel=currentFailoverLevel;
  }

  public void setAdmissionControlInfo(ClusterDasAdmissionControlInfo admissionControlInfo) {
    this.admissionControlInfo=admissionControlInfo;
  }

  public void setNumVmotions(int numVmotions) {
    this.numVmotions=numVmotions;
  }

  public void setTargetBalance(Integer targetBalance) {
    this.targetBalance=targetBalance;
  }

  public void setCurrentBalance(Integer currentBalance) {
    this.currentBalance=currentBalance;
  }

  public void setCurrentEVCModeKey(String currentEVCModeKey) {
    this.currentEVCModeKey=currentEVCModeKey;
  }

  public void setDasData(ClusterDasData dasData) {
    this.dasData=dasData;
  }
}
