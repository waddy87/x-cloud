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
public class ClusterConfigInfoEx extends ComputeResourceConfigInfo {
  public ClusterDasConfigInfo dasConfig;
  public ClusterDasVmConfigInfo[] dasVmConfig;
  public ClusterDrsConfigInfo drsConfig;
  public ClusterDrsVmConfigInfo[] drsVmConfig;
  public ClusterRuleInfo[] rule;
  public ClusterDpmConfigInfo dpmConfigInfo;
  public ClusterDpmHostConfigInfo[] dpmHostConfig;
  public VsanClusterConfigInfo vsanConfigInfo;
  public VsanHostConfigInfo[] vsanHostConfig;
  public ClusterGroupInfo[] group;

  public ClusterDasConfigInfo getDasConfig() {
    return this.dasConfig;
  }

  public ClusterDasVmConfigInfo[] getDasVmConfig() {
    return this.dasVmConfig;
  }

  public ClusterDrsConfigInfo getDrsConfig() {
    return this.drsConfig;
  }

  public ClusterDrsVmConfigInfo[] getDrsVmConfig() {
    return this.drsVmConfig;
  }

  public ClusterRuleInfo[] getRule() {
    return this.rule;
  }

  public ClusterDpmConfigInfo getDpmConfigInfo() {
    return this.dpmConfigInfo;
  }

  public ClusterDpmHostConfigInfo[] getDpmHostConfig() {
    return this.dpmHostConfig;
  }

  public VsanClusterConfigInfo getVsanConfigInfo() {
    return this.vsanConfigInfo;
  }

  public VsanHostConfigInfo[] getVsanHostConfig() {
    return this.vsanHostConfig;
  }

  public ClusterGroupInfo[] getGroup() {
    return this.group;
  }

  public void setDasConfig(ClusterDasConfigInfo dasConfig) {
    this.dasConfig=dasConfig;
  }

  public void setDasVmConfig(ClusterDasVmConfigInfo[] dasVmConfig) {
    this.dasVmConfig=dasVmConfig;
  }

  public void setDrsConfig(ClusterDrsConfigInfo drsConfig) {
    this.drsConfig=drsConfig;
  }

  public void setDrsVmConfig(ClusterDrsVmConfigInfo[] drsVmConfig) {
    this.drsVmConfig=drsVmConfig;
  }

  public void setRule(ClusterRuleInfo[] rule) {
    this.rule=rule;
  }

  public void setDpmConfigInfo(ClusterDpmConfigInfo dpmConfigInfo) {
    this.dpmConfigInfo=dpmConfigInfo;
  }

  public void setDpmHostConfig(ClusterDpmHostConfigInfo[] dpmHostConfig) {
    this.dpmHostConfig=dpmHostConfig;
  }

  public void setVsanConfigInfo(VsanClusterConfigInfo vsanConfigInfo) {
    this.vsanConfigInfo=vsanConfigInfo;
  }

  public void setVsanHostConfig(VsanHostConfigInfo[] vsanHostConfig) {
    this.vsanHostConfig=vsanHostConfig;
  }

  public void setGroup(ClusterGroupInfo[] group) {
    this.group=group;
  }
}
