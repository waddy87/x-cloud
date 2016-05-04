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
public class ClusterConfigSpecEx extends ComputeResourceConfigSpec {
  public ClusterDasConfigInfo dasConfig;
  public ClusterDasVmConfigSpec[] dasVmConfigSpec;
  public ClusterDrsConfigInfo drsConfig;
  public ClusterDrsVmConfigSpec[] drsVmConfigSpec;
  public ClusterRuleSpec[] rulesSpec;
  public ClusterDpmConfigInfo dpmConfig;
  public ClusterDpmHostConfigSpec[] dpmHostConfigSpec;
  public VsanClusterConfigInfo vsanConfig;
  public VsanHostConfigInfo[] vsanHostConfigSpec;
  public ClusterGroupSpec[] groupSpec;

  public ClusterDasConfigInfo getDasConfig() {
    return this.dasConfig;
  }

  public ClusterDasVmConfigSpec[] getDasVmConfigSpec() {
    return this.dasVmConfigSpec;
  }

  public ClusterDrsConfigInfo getDrsConfig() {
    return this.drsConfig;
  }

  public ClusterDrsVmConfigSpec[] getDrsVmConfigSpec() {
    return this.drsVmConfigSpec;
  }

  public ClusterRuleSpec[] getRulesSpec() {
    return this.rulesSpec;
  }

  public ClusterDpmConfigInfo getDpmConfig() {
    return this.dpmConfig;
  }

  public ClusterDpmHostConfigSpec[] getDpmHostConfigSpec() {
    return this.dpmHostConfigSpec;
  }

  public VsanClusterConfigInfo getVsanConfig() {
    return this.vsanConfig;
  }

  public VsanHostConfigInfo[] getVsanHostConfigSpec() {
    return this.vsanHostConfigSpec;
  }

  public ClusterGroupSpec[] getGroupSpec() {
    return this.groupSpec;
  }

  public void setDasConfig(ClusterDasConfigInfo dasConfig) {
    this.dasConfig=dasConfig;
  }

  public void setDasVmConfigSpec(ClusterDasVmConfigSpec[] dasVmConfigSpec) {
    this.dasVmConfigSpec=dasVmConfigSpec;
  }

  public void setDrsConfig(ClusterDrsConfigInfo drsConfig) {
    this.drsConfig=drsConfig;
  }

  public void setDrsVmConfigSpec(ClusterDrsVmConfigSpec[] drsVmConfigSpec) {
    this.drsVmConfigSpec=drsVmConfigSpec;
  }

  public void setRulesSpec(ClusterRuleSpec[] rulesSpec) {
    this.rulesSpec=rulesSpec;
  }

  public void setDpmConfig(ClusterDpmConfigInfo dpmConfig) {
    this.dpmConfig=dpmConfig;
  }

  public void setDpmHostConfigSpec(ClusterDpmHostConfigSpec[] dpmHostConfigSpec) {
    this.dpmHostConfigSpec=dpmHostConfigSpec;
  }

  public void setVsanConfig(VsanClusterConfigInfo vsanConfig) {
    this.vsanConfig=vsanConfig;
  }

  public void setVsanHostConfigSpec(VsanHostConfigInfo[] vsanHostConfigSpec) {
    this.vsanHostConfigSpec=vsanHostConfigSpec;
  }

  public void setGroupSpec(ClusterGroupSpec[] groupSpec) {
    this.groupSpec=groupSpec;
  }
}
