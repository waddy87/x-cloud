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
public class HostInternetScsiHbaIPCapabilities extends DynamicData {
  public boolean addressSettable;
  public boolean ipConfigurationMethodSettable;
  public boolean subnetMaskSettable;
  public boolean defaultGatewaySettable;
  public boolean primaryDnsServerAddressSettable;
  public boolean alternateDnsServerAddressSettable;
  public Boolean ipv6Supported;
  public Boolean arpRedirectSettable;
  public Boolean mtuSettable;
  public Boolean hostNameAsTargetAddress;
  public Boolean nameAliasSettable;

  public boolean isAddressSettable() {
    return this.addressSettable;
  }

  public boolean isIpConfigurationMethodSettable() {
    return this.ipConfigurationMethodSettable;
  }

  public boolean isSubnetMaskSettable() {
    return this.subnetMaskSettable;
  }

  public boolean isDefaultGatewaySettable() {
    return this.defaultGatewaySettable;
  }

  public boolean isPrimaryDnsServerAddressSettable() {
    return this.primaryDnsServerAddressSettable;
  }

  public boolean isAlternateDnsServerAddressSettable() {
    return this.alternateDnsServerAddressSettable;
  }

  public Boolean getIpv6Supported() {
    return this.ipv6Supported;
  }

  public Boolean getArpRedirectSettable() {
    return this.arpRedirectSettable;
  }

  public Boolean getMtuSettable() {
    return this.mtuSettable;
  }

  public Boolean getHostNameAsTargetAddress() {
    return this.hostNameAsTargetAddress;
  }

  public Boolean getNameAliasSettable() {
    return this.nameAliasSettable;
  }

  public void setAddressSettable(boolean addressSettable) {
    this.addressSettable=addressSettable;
  }

  public void setIpConfigurationMethodSettable(boolean ipConfigurationMethodSettable) {
    this.ipConfigurationMethodSettable=ipConfigurationMethodSettable;
  }

  public void setSubnetMaskSettable(boolean subnetMaskSettable) {
    this.subnetMaskSettable=subnetMaskSettable;
  }

  public void setDefaultGatewaySettable(boolean defaultGatewaySettable) {
    this.defaultGatewaySettable=defaultGatewaySettable;
  }

  public void setPrimaryDnsServerAddressSettable(boolean primaryDnsServerAddressSettable) {
    this.primaryDnsServerAddressSettable=primaryDnsServerAddressSettable;
  }

  public void setAlternateDnsServerAddressSettable(boolean alternateDnsServerAddressSettable) {
    this.alternateDnsServerAddressSettable=alternateDnsServerAddressSettable;
  }

  public void setIpv6Supported(Boolean ipv6Supported) {
    this.ipv6Supported=ipv6Supported;
  }

  public void setArpRedirectSettable(Boolean arpRedirectSettable) {
    this.arpRedirectSettable=arpRedirectSettable;
  }

  public void setMtuSettable(Boolean mtuSettable) {
    this.mtuSettable=mtuSettable;
  }

  public void setHostNameAsTargetAddress(Boolean hostNameAsTargetAddress) {
    this.hostNameAsTargetAddress=hostNameAsTargetAddress;
  }

  public void setNameAliasSettable(Boolean nameAliasSettable) {
    this.nameAliasSettable=nameAliasSettable;
  }
}
