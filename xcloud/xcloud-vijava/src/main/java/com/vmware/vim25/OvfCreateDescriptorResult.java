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
public class OvfCreateDescriptorResult extends DynamicData {
  public String ovfDescriptor;
  public LocalizedMethodFault[] error;
  public LocalizedMethodFault[] warning;
  public Boolean includeImageFiles;

  public String getOvfDescriptor() {
    return this.ovfDescriptor;
  }

  public LocalizedMethodFault[] getError() {
    return this.error;
  }

  public LocalizedMethodFault[] getWarning() {
    return this.warning;
  }

  public Boolean getIncludeImageFiles() {
    return this.includeImageFiles;
  }

  public void setOvfDescriptor(String ovfDescriptor) {
    this.ovfDescriptor=ovfDescriptor;
  }

  public void setError(LocalizedMethodFault[] error) {
    this.error=error;
  }

  public void setWarning(LocalizedMethodFault[] warning) {
    this.warning=warning;
  }

  public void setIncludeImageFiles(Boolean includeImageFiles) {
    this.includeImageFiles=includeImageFiles;
  }
}
