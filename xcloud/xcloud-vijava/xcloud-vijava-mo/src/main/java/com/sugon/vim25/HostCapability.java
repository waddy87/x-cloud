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
public class HostCapability extends DynamicData {
  public boolean recursiveResourcePoolsSupported;
  public boolean cpuMemoryResourceConfigurationSupported;
  public boolean rebootSupported;
  public boolean shutdownSupported;
  public boolean vmotionSupported;
  public boolean standbySupported;
  public Boolean ipmiSupported;
  public Integer maxSupportedVMs;
  public Integer maxRunningVMs;
  public Integer maxSupportedVcpus;
  public Integer maxRegisteredVMs;
  public boolean datastorePrincipalSupported;
  public boolean sanSupported;
  public boolean nfsSupported;
  public boolean iscsiSupported;
  public boolean vlanTaggingSupported;
  public boolean nicTeamingSupported;
  public boolean highGuestMemSupported;
  public boolean maintenanceModeSupported;
  public boolean suspendedRelocateSupported;
  public boolean restrictedSnapshotRelocateSupported;
  public boolean perVmSwapFiles;
  public boolean localSwapDatastoreSupported;
  public boolean unsharedSwapVMotionSupported;
  public boolean backgroundSnapshotsSupported;
  public boolean preAssignedPCIUnitNumbersSupported;
  public boolean screenshotSupported;
  public boolean scaledScreenshotSupported;
  public Boolean storageVMotionSupported;
  public Boolean vmotionWithStorageVMotionSupported;
  public Boolean vmotionAcrossNetworkSupported;
  public Boolean hbrNicSelectionSupported;
  public Boolean recordReplaySupported;
  public Boolean ftSupported;
  public String replayUnsupportedReason;
  public String[] replayCompatibilityIssues;
  public String[] ftCompatibilityIssues;
  public Boolean loginBySSLThumbprintSupported;
  public Boolean cloneFromSnapshotSupported;
  public Boolean deltaDiskBackingsSupported;
  public Boolean perVMNetworkTrafficShapingSupported;
  public Boolean tpmSupported;
  public HostCpuIdInfo[] supportedCpuFeature;
  public Boolean virtualExecUsageSupported;
  public Boolean storageIORMSupported;
  public Boolean vmDirectPathGen2Supported;
  public String[] vmDirectPathGen2UnsupportedReason;
  public String vmDirectPathGen2UnsupportedReasonExtended;
  public int[] supportedVmfsMajorVersion;
  public Boolean vStorageCapable;
  public Boolean snapshotRelayoutSupported;
  public Boolean firewallIpRulesSupported;
  public Boolean servicePackageInfoSupported;
  public Integer maxHostRunningVms;
  public Integer maxHostSupportedVcpus;
  public Boolean vmfsDatastoreMountCapable;
  public Boolean eightPlusHostVmfsSharedAccessSupported;
  public Boolean nestedHVSupported;
  public Boolean vPMCSupported;
  public Boolean interVMCommunicationThroughVMCISupported;
  public Boolean scheduledHardwareUpgradeSupported;
  public Boolean featureCapabilitiesSupported;
  public Boolean latencySensitivitySupported;
  public Boolean storagePolicySupported;
  public Boolean accel3dSupported;
  public Boolean reliableMemoryAware;
  public Boolean multipleNetworkStackInstanceSupported;
  public Boolean vsanSupported;
  public Boolean vFlashSupported;

  public boolean isRecursiveResourcePoolsSupported() {
    return this.recursiveResourcePoolsSupported;
  }

  public boolean isCpuMemoryResourceConfigurationSupported() {
    return this.cpuMemoryResourceConfigurationSupported;
  }

  public boolean isRebootSupported() {
    return this.rebootSupported;
  }

  public boolean isShutdownSupported() {
    return this.shutdownSupported;
  }

  public boolean isVmotionSupported() {
    return this.vmotionSupported;
  }

  public boolean isStandbySupported() {
    return this.standbySupported;
  }

  public Boolean getIpmiSupported() {
    return this.ipmiSupported;
  }

  public Integer getMaxSupportedVMs() {
    return this.maxSupportedVMs;
  }

  public Integer getMaxRunningVMs() {
    return this.maxRunningVMs;
  }

  public Integer getMaxSupportedVcpus() {
    return this.maxSupportedVcpus;
  }

  public Integer getMaxRegisteredVMs() {
    return this.maxRegisteredVMs;
  }

  public boolean isDatastorePrincipalSupported() {
    return this.datastorePrincipalSupported;
  }

  public boolean isSanSupported() {
    return this.sanSupported;
  }

  public boolean isNfsSupported() {
    return this.nfsSupported;
  }

  public boolean isIscsiSupported() {
    return this.iscsiSupported;
  }

  public boolean isVlanTaggingSupported() {
    return this.vlanTaggingSupported;
  }

  public boolean isNicTeamingSupported() {
    return this.nicTeamingSupported;
  }

  public boolean isHighGuestMemSupported() {
    return this.highGuestMemSupported;
  }

  public boolean isMaintenanceModeSupported() {
    return this.maintenanceModeSupported;
  }

  public boolean isSuspendedRelocateSupported() {
    return this.suspendedRelocateSupported;
  }

  public boolean isRestrictedSnapshotRelocateSupported() {
    return this.restrictedSnapshotRelocateSupported;
  }

  public boolean isPerVmSwapFiles() {
    return this.perVmSwapFiles;
  }

  public boolean isLocalSwapDatastoreSupported() {
    return this.localSwapDatastoreSupported;
  }

  public boolean isUnsharedSwapVMotionSupported() {
    return this.unsharedSwapVMotionSupported;
  }

  public boolean isBackgroundSnapshotsSupported() {
    return this.backgroundSnapshotsSupported;
  }

  public boolean isPreAssignedPCIUnitNumbersSupported() {
    return this.preAssignedPCIUnitNumbersSupported;
  }

  public boolean isScreenshotSupported() {
    return this.screenshotSupported;
  }

  public boolean isScaledScreenshotSupported() {
    return this.scaledScreenshotSupported;
  }

  public Boolean getStorageVMotionSupported() {
    return this.storageVMotionSupported;
  }

  public Boolean getVmotionWithStorageVMotionSupported() {
    return this.vmotionWithStorageVMotionSupported;
  }

  public Boolean getVmotionAcrossNetworkSupported() {
    return this.vmotionAcrossNetworkSupported;
  }

  public Boolean getHbrNicSelectionSupported() {
    return this.hbrNicSelectionSupported;
  }

  public Boolean getRecordReplaySupported() {
    return this.recordReplaySupported;
  }

  public Boolean getFtSupported() {
    return this.ftSupported;
  }

  public String getReplayUnsupportedReason() {
    return this.replayUnsupportedReason;
  }

  public String[] getReplayCompatibilityIssues() {
    return this.replayCompatibilityIssues;
  }

  public String[] getFtCompatibilityIssues() {
    return this.ftCompatibilityIssues;
  }

  public Boolean getLoginBySSLThumbprintSupported() {
    return this.loginBySSLThumbprintSupported;
  }

  public Boolean getCloneFromSnapshotSupported() {
    return this.cloneFromSnapshotSupported;
  }

  public Boolean getDeltaDiskBackingsSupported() {
    return this.deltaDiskBackingsSupported;
  }

  public Boolean getPerVMNetworkTrafficShapingSupported() {
    return this.perVMNetworkTrafficShapingSupported;
  }

  public Boolean getTpmSupported() {
    return this.tpmSupported;
  }

  public HostCpuIdInfo[] getSupportedCpuFeature() {
    return this.supportedCpuFeature;
  }

  public Boolean getVirtualExecUsageSupported() {
    return this.virtualExecUsageSupported;
  }

  public Boolean getStorageIORMSupported() {
    return this.storageIORMSupported;
  }

  public Boolean getVmDirectPathGen2Supported() {
    return this.vmDirectPathGen2Supported;
  }

  public String[] getVmDirectPathGen2UnsupportedReason() {
    return this.vmDirectPathGen2UnsupportedReason;
  }

  public String getVmDirectPathGen2UnsupportedReasonExtended() {
    return this.vmDirectPathGen2UnsupportedReasonExtended;
  }

  public int[] getSupportedVmfsMajorVersion() {
    return this.supportedVmfsMajorVersion;
  }

  public Boolean getVStorageCapable() {
    return this.vStorageCapable;
  }

  public Boolean getSnapshotRelayoutSupported() {
    return this.snapshotRelayoutSupported;
  }

  public Boolean getFirewallIpRulesSupported() {
    return this.firewallIpRulesSupported;
  }

  public Boolean getServicePackageInfoSupported() {
    return this.servicePackageInfoSupported;
  }

  public Integer getMaxHostRunningVms() {
    return this.maxHostRunningVms;
  }

  public Integer getMaxHostSupportedVcpus() {
    return this.maxHostSupportedVcpus;
  }

  public Boolean getVmfsDatastoreMountCapable() {
    return this.vmfsDatastoreMountCapable;
  }

  public Boolean getEightPlusHostVmfsSharedAccessSupported() {
    return this.eightPlusHostVmfsSharedAccessSupported;
  }

  public Boolean getNestedHVSupported() {
    return this.nestedHVSupported;
  }

  public Boolean getVPMCSupported() {
    return this.vPMCSupported;
  }

  public Boolean getInterVMCommunicationThroughVMCISupported() {
    return this.interVMCommunicationThroughVMCISupported;
  }

  public Boolean getScheduledHardwareUpgradeSupported() {
    return this.scheduledHardwareUpgradeSupported;
  }

  public Boolean getFeatureCapabilitiesSupported() {
    return this.featureCapabilitiesSupported;
  }

  public Boolean getLatencySensitivitySupported() {
    return this.latencySensitivitySupported;
  }

  public Boolean getStoragePolicySupported() {
    return this.storagePolicySupported;
  }

  public Boolean getAccel3dSupported() {
    return this.accel3dSupported;
  }

  public Boolean getReliableMemoryAware() {
    return this.reliableMemoryAware;
  }

  public Boolean getMultipleNetworkStackInstanceSupported() {
    return this.multipleNetworkStackInstanceSupported;
  }

  public Boolean getVsanSupported() {
    return this.vsanSupported;
  }

  public Boolean getVFlashSupported() {
    return this.vFlashSupported;
  }

  public void setRecursiveResourcePoolsSupported(boolean recursiveResourcePoolsSupported) {
    this.recursiveResourcePoolsSupported=recursiveResourcePoolsSupported;
  }

  public void setCpuMemoryResourceConfigurationSupported(boolean cpuMemoryResourceConfigurationSupported) {
    this.cpuMemoryResourceConfigurationSupported=cpuMemoryResourceConfigurationSupported;
  }

  public void setRebootSupported(boolean rebootSupported) {
    this.rebootSupported=rebootSupported;
  }

  public void setShutdownSupported(boolean shutdownSupported) {
    this.shutdownSupported=shutdownSupported;
  }

  public void setVmotionSupported(boolean vmotionSupported) {
    this.vmotionSupported=vmotionSupported;
  }

  public void setStandbySupported(boolean standbySupported) {
    this.standbySupported=standbySupported;
  }

  public void setIpmiSupported(Boolean ipmiSupported) {
    this.ipmiSupported=ipmiSupported;
  }

  public void setMaxSupportedVMs(Integer maxSupportedVMs) {
    this.maxSupportedVMs=maxSupportedVMs;
  }

  public void setMaxRunningVMs(Integer maxRunningVMs) {
    this.maxRunningVMs=maxRunningVMs;
  }

  public void setMaxSupportedVcpus(Integer maxSupportedVcpus) {
    this.maxSupportedVcpus=maxSupportedVcpus;
  }

  public void setMaxRegisteredVMs(Integer maxRegisteredVMs) {
    this.maxRegisteredVMs=maxRegisteredVMs;
  }

  public void setDatastorePrincipalSupported(boolean datastorePrincipalSupported) {
    this.datastorePrincipalSupported=datastorePrincipalSupported;
  }

  public void setSanSupported(boolean sanSupported) {
    this.sanSupported=sanSupported;
  }

  public void setNfsSupported(boolean nfsSupported) {
    this.nfsSupported=nfsSupported;
  }

  public void setIscsiSupported(boolean iscsiSupported) {
    this.iscsiSupported=iscsiSupported;
  }

  public void setVlanTaggingSupported(boolean vlanTaggingSupported) {
    this.vlanTaggingSupported=vlanTaggingSupported;
  }

  public void setNicTeamingSupported(boolean nicTeamingSupported) {
    this.nicTeamingSupported=nicTeamingSupported;
  }

  public void setHighGuestMemSupported(boolean highGuestMemSupported) {
    this.highGuestMemSupported=highGuestMemSupported;
  }

  public void setMaintenanceModeSupported(boolean maintenanceModeSupported) {
    this.maintenanceModeSupported=maintenanceModeSupported;
  }

  public void setSuspendedRelocateSupported(boolean suspendedRelocateSupported) {
    this.suspendedRelocateSupported=suspendedRelocateSupported;
  }

  public void setRestrictedSnapshotRelocateSupported(boolean restrictedSnapshotRelocateSupported) {
    this.restrictedSnapshotRelocateSupported=restrictedSnapshotRelocateSupported;
  }

  public void setPerVmSwapFiles(boolean perVmSwapFiles) {
    this.perVmSwapFiles=perVmSwapFiles;
  }

  public void setLocalSwapDatastoreSupported(boolean localSwapDatastoreSupported) {
    this.localSwapDatastoreSupported=localSwapDatastoreSupported;
  }

  public void setUnsharedSwapVMotionSupported(boolean unsharedSwapVMotionSupported) {
    this.unsharedSwapVMotionSupported=unsharedSwapVMotionSupported;
  }

  public void setBackgroundSnapshotsSupported(boolean backgroundSnapshotsSupported) {
    this.backgroundSnapshotsSupported=backgroundSnapshotsSupported;
  }

  public void setPreAssignedPCIUnitNumbersSupported(boolean preAssignedPCIUnitNumbersSupported) {
    this.preAssignedPCIUnitNumbersSupported=preAssignedPCIUnitNumbersSupported;
  }

  public void setScreenshotSupported(boolean screenshotSupported) {
    this.screenshotSupported=screenshotSupported;
  }

  public void setScaledScreenshotSupported(boolean scaledScreenshotSupported) {
    this.scaledScreenshotSupported=scaledScreenshotSupported;
  }

  public void setStorageVMotionSupported(Boolean storageVMotionSupported) {
    this.storageVMotionSupported=storageVMotionSupported;
  }

  public void setVmotionWithStorageVMotionSupported(Boolean vmotionWithStorageVMotionSupported) {
    this.vmotionWithStorageVMotionSupported=vmotionWithStorageVMotionSupported;
  }

  public void setVmotionAcrossNetworkSupported(Boolean vmotionAcrossNetworkSupported) {
    this.vmotionAcrossNetworkSupported=vmotionAcrossNetworkSupported;
  }

  public void setHbrNicSelectionSupported(Boolean hbrNicSelectionSupported) {
    this.hbrNicSelectionSupported=hbrNicSelectionSupported;
  }

  public void setRecordReplaySupported(Boolean recordReplaySupported) {
    this.recordReplaySupported=recordReplaySupported;
  }

  public void setFtSupported(Boolean ftSupported) {
    this.ftSupported=ftSupported;
  }

  public void setReplayUnsupportedReason(String replayUnsupportedReason) {
    this.replayUnsupportedReason=replayUnsupportedReason;
  }

  public void setReplayCompatibilityIssues(String[] replayCompatibilityIssues) {
    this.replayCompatibilityIssues=replayCompatibilityIssues;
  }

  public void setFtCompatibilityIssues(String[] ftCompatibilityIssues) {
    this.ftCompatibilityIssues=ftCompatibilityIssues;
  }

  public void setLoginBySSLThumbprintSupported(Boolean loginBySSLThumbprintSupported) {
    this.loginBySSLThumbprintSupported=loginBySSLThumbprintSupported;
  }

  public void setCloneFromSnapshotSupported(Boolean cloneFromSnapshotSupported) {
    this.cloneFromSnapshotSupported=cloneFromSnapshotSupported;
  }

  public void setDeltaDiskBackingsSupported(Boolean deltaDiskBackingsSupported) {
    this.deltaDiskBackingsSupported=deltaDiskBackingsSupported;
  }

  public void setPerVMNetworkTrafficShapingSupported(Boolean perVMNetworkTrafficShapingSupported) {
    this.perVMNetworkTrafficShapingSupported=perVMNetworkTrafficShapingSupported;
  }

  public void setTpmSupported(Boolean tpmSupported) {
    this.tpmSupported=tpmSupported;
  }

  public void setSupportedCpuFeature(HostCpuIdInfo[] supportedCpuFeature) {
    this.supportedCpuFeature=supportedCpuFeature;
  }

  public void setVirtualExecUsageSupported(Boolean virtualExecUsageSupported) {
    this.virtualExecUsageSupported=virtualExecUsageSupported;
  }

  public void setStorageIORMSupported(Boolean storageIORMSupported) {
    this.storageIORMSupported=storageIORMSupported;
  }

  public void setVmDirectPathGen2Supported(Boolean vmDirectPathGen2Supported) {
    this.vmDirectPathGen2Supported=vmDirectPathGen2Supported;
  }

  public void setVmDirectPathGen2UnsupportedReason(String[] vmDirectPathGen2UnsupportedReason) {
    this.vmDirectPathGen2UnsupportedReason=vmDirectPathGen2UnsupportedReason;
  }

  public void setVmDirectPathGen2UnsupportedReasonExtended(String vmDirectPathGen2UnsupportedReasonExtended) {
    this.vmDirectPathGen2UnsupportedReasonExtended=vmDirectPathGen2UnsupportedReasonExtended;
  }

  public void setSupportedVmfsMajorVersion(int[] supportedVmfsMajorVersion) {
    this.supportedVmfsMajorVersion=supportedVmfsMajorVersion;
  }

  public void setVStorageCapable(Boolean vStorageCapable) {
    this.vStorageCapable=vStorageCapable;
  }

  public void setSnapshotRelayoutSupported(Boolean snapshotRelayoutSupported) {
    this.snapshotRelayoutSupported=snapshotRelayoutSupported;
  }

  public void setFirewallIpRulesSupported(Boolean firewallIpRulesSupported) {
    this.firewallIpRulesSupported=firewallIpRulesSupported;
  }

  public void setServicePackageInfoSupported(Boolean servicePackageInfoSupported) {
    this.servicePackageInfoSupported=servicePackageInfoSupported;
  }

  public void setMaxHostRunningVms(Integer maxHostRunningVms) {
    this.maxHostRunningVms=maxHostRunningVms;
  }

  public void setMaxHostSupportedVcpus(Integer maxHostSupportedVcpus) {
    this.maxHostSupportedVcpus=maxHostSupportedVcpus;
  }

  public void setVmfsDatastoreMountCapable(Boolean vmfsDatastoreMountCapable) {
    this.vmfsDatastoreMountCapable=vmfsDatastoreMountCapable;
  }

  public void setEightPlusHostVmfsSharedAccessSupported(Boolean eightPlusHostVmfsSharedAccessSupported) {
    this.eightPlusHostVmfsSharedAccessSupported=eightPlusHostVmfsSharedAccessSupported;
  }

  public void setNestedHVSupported(Boolean nestedHVSupported) {
    this.nestedHVSupported=nestedHVSupported;
  }

  public void setVPMCSupported(Boolean vPMCSupported) {
    this.vPMCSupported=vPMCSupported;
  }

  public void setInterVMCommunicationThroughVMCISupported(Boolean interVMCommunicationThroughVMCISupported) {
    this.interVMCommunicationThroughVMCISupported=interVMCommunicationThroughVMCISupported;
  }

  public void setScheduledHardwareUpgradeSupported(Boolean scheduledHardwareUpgradeSupported) {
    this.scheduledHardwareUpgradeSupported=scheduledHardwareUpgradeSupported;
  }

  public void setFeatureCapabilitiesSupported(Boolean featureCapabilitiesSupported) {
    this.featureCapabilitiesSupported=featureCapabilitiesSupported;
  }

  public void setLatencySensitivitySupported(Boolean latencySensitivitySupported) {
    this.latencySensitivitySupported=latencySensitivitySupported;
  }

  public void setStoragePolicySupported(Boolean storagePolicySupported) {
    this.storagePolicySupported=storagePolicySupported;
  }

  public void setAccel3dSupported(Boolean accel3dSupported) {
    this.accel3dSupported=accel3dSupported;
  }

  public void setReliableMemoryAware(Boolean reliableMemoryAware) {
    this.reliableMemoryAware=reliableMemoryAware;
  }

  public void setMultipleNetworkStackInstanceSupported(Boolean multipleNetworkStackInstanceSupported) {
    this.multipleNetworkStackInstanceSupported=multipleNetworkStackInstanceSupported;
  }

  public void setVsanSupported(Boolean vsanSupported) {
    this.vsanSupported=vsanSupported;
  }

  public void setVFlashSupported(Boolean vFlashSupported) {
    this.vFlashSupported=vFlashSupported;
  }
}
