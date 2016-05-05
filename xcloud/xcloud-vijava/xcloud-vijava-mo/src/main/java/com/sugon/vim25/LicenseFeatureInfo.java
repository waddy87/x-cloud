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
import java.util.Calendar;

/**
* @author Steve Jin (http://www.doublecloud.org)
* @version 5.1
*/

@SuppressWarnings("all")
public class LicenseFeatureInfo extends DynamicData {
  public String key;
  public String featureName;
  public String featureDescription;
  public LicenseFeatureInfoState state;
  public String costUnit;
  public String sourceRestriction;
  public String[] dependentKey;
  public Boolean edition;
  public Calendar expiresOn;

  public String getKey() {
    return this.key;
  }

  public String getFeatureName() {
    return this.featureName;
  }

  public String getFeatureDescription() {
    return this.featureDescription;
  }

  public LicenseFeatureInfoState getState() {
    return this.state;
  }

  public String getCostUnit() {
    return this.costUnit;
  }

  public String getSourceRestriction() {
    return this.sourceRestriction;
  }

  public String[] getDependentKey() {
    return this.dependentKey;
  }

  public Boolean getEdition() {
    return this.edition;
  }

  public Calendar getExpiresOn() {
    return this.expiresOn;
  }

  public void setKey(String key) {
    this.key=key;
  }

  public void setFeatureName(String featureName) {
    this.featureName=featureName;
  }

  public void setFeatureDescription(String featureDescription) {
    this.featureDescription=featureDescription;
  }

  public void setState(LicenseFeatureInfoState state) {
    this.state=state;
  }

  public void setCostUnit(String costUnit) {
    this.costUnit=costUnit;
  }

  public void setSourceRestriction(String sourceRestriction) {
    this.sourceRestriction=sourceRestriction;
  }

  public void setDependentKey(String[] dependentKey) {
    this.dependentKey=dependentKey;
  }

  public void setEdition(Boolean edition) {
    this.edition=edition;
  }

  public void setExpiresOn(Calendar expiresOn) {
    this.expiresOn=expiresOn;
  }
}
