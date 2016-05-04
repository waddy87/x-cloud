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
public class DatastoreInfo extends DynamicData {
  public String name;
  public String url;
  public long freeSpace;
  public long maxFileSize;
  public Long maxVirtualDiskCapacity;
  public Calendar timestamp;
  public String containerId;

  public String getName() {
    return this.name;
  }

  public String getUrl() {
    return this.url;
  }

  public long getFreeSpace() {
    return this.freeSpace;
  }

  public long getMaxFileSize() {
    return this.maxFileSize;
  }

  public Long getMaxVirtualDiskCapacity() {
    return this.maxVirtualDiskCapacity;
  }

  public Calendar getTimestamp() {
    return this.timestamp;
  }

  public String getContainerId() {
    return this.containerId;
  }

  public void setName(String name) {
    this.name=name;
  }

  public void setUrl(String url) {
    this.url=url;
  }

  public void setFreeSpace(long freeSpace) {
    this.freeSpace=freeSpace;
  }

  public void setMaxFileSize(long maxFileSize) {
    this.maxFileSize=maxFileSize;
  }

  public void setMaxVirtualDiskCapacity(Long maxVirtualDiskCapacity) {
    this.maxVirtualDiskCapacity=maxVirtualDiskCapacity;
  }

  public void setTimestamp(Calendar timestamp) {
    this.timestamp=timestamp;
  }

  public void setContainerId(String containerId) {
    this.containerId=containerId;
  }
}
