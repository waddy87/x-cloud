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
public class VirtualPCIControllerOption extends VirtualControllerOption {
  public IntOption numSCSIControllers;
  public IntOption numEthernetCards;
  public IntOption numVideoCards;
  public IntOption numSoundCards;
  public IntOption numVmiRoms;
  public IntOption numVmciDevices;
  public IntOption numPCIPassthroughDevices;
  public IntOption numSasSCSIControllers;
  public IntOption numVmxnet3EthernetCards;
  public IntOption numParaVirtualSCSIControllers;
  public IntOption numSATAControllers;

  public IntOption getNumSCSIControllers() {
    return this.numSCSIControllers;
  }

  public IntOption getNumEthernetCards() {
    return this.numEthernetCards;
  }

  public IntOption getNumVideoCards() {
    return this.numVideoCards;
  }

  public IntOption getNumSoundCards() {
    return this.numSoundCards;
  }

  public IntOption getNumVmiRoms() {
    return this.numVmiRoms;
  }

  public IntOption getNumVmciDevices() {
    return this.numVmciDevices;
  }

  public IntOption getNumPCIPassthroughDevices() {
    return this.numPCIPassthroughDevices;
  }

  public IntOption getNumSasSCSIControllers() {
    return this.numSasSCSIControllers;
  }

  public IntOption getNumVmxnet3EthernetCards() {
    return this.numVmxnet3EthernetCards;
  }

  public IntOption getNumParaVirtualSCSIControllers() {
    return this.numParaVirtualSCSIControllers;
  }

  public IntOption getNumSATAControllers() {
    return this.numSATAControllers;
  }

  public void setNumSCSIControllers(IntOption numSCSIControllers) {
    this.numSCSIControllers=numSCSIControllers;
  }

  public void setNumEthernetCards(IntOption numEthernetCards) {
    this.numEthernetCards=numEthernetCards;
  }

  public void setNumVideoCards(IntOption numVideoCards) {
    this.numVideoCards=numVideoCards;
  }

  public void setNumSoundCards(IntOption numSoundCards) {
    this.numSoundCards=numSoundCards;
  }

  public void setNumVmiRoms(IntOption numVmiRoms) {
    this.numVmiRoms=numVmiRoms;
  }

  public void setNumVmciDevices(IntOption numVmciDevices) {
    this.numVmciDevices=numVmciDevices;
  }

  public void setNumPCIPassthroughDevices(IntOption numPCIPassthroughDevices) {
    this.numPCIPassthroughDevices=numPCIPassthroughDevices;
  }

  public void setNumSasSCSIControllers(IntOption numSasSCSIControllers) {
    this.numSasSCSIControllers=numSasSCSIControllers;
  }

  public void setNumVmxnet3EthernetCards(IntOption numVmxnet3EthernetCards) {
    this.numVmxnet3EthernetCards=numVmxnet3EthernetCards;
  }

  public void setNumParaVirtualSCSIControllers(IntOption numParaVirtualSCSIControllers) {
    this.numParaVirtualSCSIControllers=numParaVirtualSCSIControllers;
  }

  public void setNumSATAControllers(IntOption numSATAControllers) {
    this.numSATAControllers=numSATAControllers;
  }
}
