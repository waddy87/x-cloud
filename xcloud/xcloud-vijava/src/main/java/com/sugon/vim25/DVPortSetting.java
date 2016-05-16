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
public class DVPortSetting extends DynamicData {
  public BoolPolicy blocked;
  public BoolPolicy vmDirectPathGen2Allowed;
  public DVSTrafficShapingPolicy inShapingPolicy;
  public DVSTrafficShapingPolicy outShapingPolicy;
  public DVSVendorSpecificConfig vendorSpecificConfig;
  public StringPolicy networkResourcePoolKey;
  public DvsFilterPolicy filterPolicy;

  public BoolPolicy getBlocked() {
    return this.blocked;
  }

  public BoolPolicy getVmDirectPathGen2Allowed() {
    return this.vmDirectPathGen2Allowed;
  }

  public DVSTrafficShapingPolicy getInShapingPolicy() {
    return this.inShapingPolicy;
  }

  public DVSTrafficShapingPolicy getOutShapingPolicy() {
    return this.outShapingPolicy;
  }

  public DVSVendorSpecificConfig getVendorSpecificConfig() {
    return this.vendorSpecificConfig;
  }

  public StringPolicy getNetworkResourcePoolKey() {
    return this.networkResourcePoolKey;
  }

  public DvsFilterPolicy getFilterPolicy() {
    return this.filterPolicy;
  }

  public void setBlocked(BoolPolicy blocked) {
    this.blocked=blocked;
  }

  public void setVmDirectPathGen2Allowed(BoolPolicy vmDirectPathGen2Allowed) {
    this.vmDirectPathGen2Allowed=vmDirectPathGen2Allowed;
  }

  public void setInShapingPolicy(DVSTrafficShapingPolicy inShapingPolicy) {
    this.inShapingPolicy=inShapingPolicy;
  }

  public void setOutShapingPolicy(DVSTrafficShapingPolicy outShapingPolicy) {
    this.outShapingPolicy=outShapingPolicy;
  }

  public void setVendorSpecificConfig(DVSVendorSpecificConfig vendorSpecificConfig) {
    this.vendorSpecificConfig=vendorSpecificConfig;
  }

  public void setNetworkResourcePoolKey(StringPolicy networkResourcePoolKey) {
    this.networkResourcePoolKey=networkResourcePoolKey;
  }

  public void setFilterPolicy(DvsFilterPolicy filterPolicy) {
    this.filterPolicy=filterPolicy;
  }
}
