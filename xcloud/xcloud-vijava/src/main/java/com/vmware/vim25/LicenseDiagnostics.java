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
import java.util.Calendar;

/**
* @author Steve Jin (http://www.doublecloud.org)
* @version 5.1
*/

@SuppressWarnings("all")
public class LicenseDiagnostics extends DynamicData {
  public Calendar sourceLastChanged;
  public String sourceLost;
  public float sourceLatency;
  public String licenseRequests;
  public String licenseRequestFailures;
  public String licenseFeatureUnknowns;
  public LicenseManagerState opState;
  public Calendar lastStatusUpdate;
  public String opFailureMessage;

  public Calendar getSourceLastChanged() {
    return this.sourceLastChanged;
  }

  public String getSourceLost() {
    return this.sourceLost;
  }

  public float getSourceLatency() {
    return this.sourceLatency;
  }

  public String getLicenseRequests() {
    return this.licenseRequests;
  }

  public String getLicenseRequestFailures() {
    return this.licenseRequestFailures;
  }

  public String getLicenseFeatureUnknowns() {
    return this.licenseFeatureUnknowns;
  }

  public LicenseManagerState getOpState() {
    return this.opState;
  }

  public Calendar getLastStatusUpdate() {
    return this.lastStatusUpdate;
  }

  public String getOpFailureMessage() {
    return this.opFailureMessage;
  }

  public void setSourceLastChanged(Calendar sourceLastChanged) {
    this.sourceLastChanged=sourceLastChanged;
  }

  public void setSourceLost(String sourceLost) {
    this.sourceLost=sourceLost;
  }

  public void setSourceLatency(float sourceLatency) {
    this.sourceLatency=sourceLatency;
  }

  public void setLicenseRequests(String licenseRequests) {
    this.licenseRequests=licenseRequests;
  }

  public void setLicenseRequestFailures(String licenseRequestFailures) {
    this.licenseRequestFailures=licenseRequestFailures;
  }

  public void setLicenseFeatureUnknowns(String licenseFeatureUnknowns) {
    this.licenseFeatureUnknowns=licenseFeatureUnknowns;
  }

  public void setOpState(LicenseManagerState opState) {
    this.opState=opState;
  }

  public void setLastStatusUpdate(Calendar lastStatusUpdate) {
    this.lastStatusUpdate=lastStatusUpdate;
  }

  public void setOpFailureMessage(String opFailureMessage) {
    this.opFailureMessage=opFailureMessage;
  }
}
