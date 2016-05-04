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
public class HostService extends DynamicData {
  public String key;
  public String label;
  public boolean required;
  public boolean uninstallable;
  public boolean running;
  public String[] ruleset;
  public String policy;
  public HostServiceSourcePackage sourcePackage;

  public String getKey() {
    return this.key;
  }

  public String getLabel() {
    return this.label;
  }

  public boolean isRequired() {
    return this.required;
  }

  public boolean isUninstallable() {
    return this.uninstallable;
  }

  public boolean isRunning() {
    return this.running;
  }

  public String[] getRuleset() {
    return this.ruleset;
  }

  public String getPolicy() {
    return this.policy;
  }

  public HostServiceSourcePackage getSourcePackage() {
    return this.sourcePackage;
  }

  public void setKey(String key) {
    this.key=key;
  }

  public void setLabel(String label) {
    this.label=label;
  }

  public void setRequired(boolean required) {
    this.required=required;
  }

  public void setUninstallable(boolean uninstallable) {
    this.uninstallable=uninstallable;
  }

  public void setRunning(boolean running) {
    this.running=running;
  }

  public void setRuleset(String[] ruleset) {
    this.ruleset=ruleset;
  }

  public void setPolicy(String policy) {
    this.policy=policy;
  }

  public void setSourcePackage(HostServiceSourcePackage sourcePackage) {
    this.sourcePackage=sourcePackage;
  }
}
