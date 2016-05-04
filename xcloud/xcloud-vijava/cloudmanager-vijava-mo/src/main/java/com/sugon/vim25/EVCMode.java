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
public class EVCMode extends ElementDescription {
  public HostCpuIdInfo[] guaranteedCPUFeatures;
  public HostFeatureCapability[] featureCapability;
  public HostFeatureMask[] featureMask;
  public VirtualMachineFeatureRequirement[] featureRequirement;
  public String vendor;
  public String[] track;
  public int vendorTier;

  public HostCpuIdInfo[] getGuaranteedCPUFeatures() {
    return this.guaranteedCPUFeatures;
  }

  public HostFeatureCapability[] getFeatureCapability() {
    return this.featureCapability;
  }

  public HostFeatureMask[] getFeatureMask() {
    return this.featureMask;
  }

  public VirtualMachineFeatureRequirement[] getFeatureRequirement() {
    return this.featureRequirement;
  }

  public String getVendor() {
    return this.vendor;
  }

  public String[] getTrack() {
    return this.track;
  }

  public int getVendorTier() {
    return this.vendorTier;
  }

  public void setGuaranteedCPUFeatures(HostCpuIdInfo[] guaranteedCPUFeatures) {
    this.guaranteedCPUFeatures=guaranteedCPUFeatures;
  }

  public void setFeatureCapability(HostFeatureCapability[] featureCapability) {
    this.featureCapability=featureCapability;
  }

  public void setFeatureMask(HostFeatureMask[] featureMask) {
    this.featureMask=featureMask;
  }

  public void setFeatureRequirement(VirtualMachineFeatureRequirement[] featureRequirement) {
    this.featureRequirement=featureRequirement;
  }

  public void setVendor(String vendor) {
    this.vendor=vendor;
  }

  public void setTrack(String[] track) {
    this.track=track;
  }

  public void setVendorTier(int vendorTier) {
    this.vendorTier=vendorTier;
  }
}
