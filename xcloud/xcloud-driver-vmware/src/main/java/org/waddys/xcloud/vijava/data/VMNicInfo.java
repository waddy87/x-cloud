package org.waddys.xcloud.vijava.data;

import com.vmware.vim25.VirtualDeviceConfigSpecOperation;

public class VMNicInfo {
	
	private String adapterId;
	private String adapterIP;
	private String adapterDNS;
	private String adapterNetMask;
	private String adapterGateWay;
	private String adapterSubNet;
    private String adapterName;
    private String networkId;
    private VirtualDeviceConfigSpecOperation adapterOperation;
    
    /**
     * 合法值：VirtualE1000 , VirtualPCNet32, VirtualVmxnet, VirtualVmxnet2,
     * VirtualVmxnet3 , Unknown;
     */
    private String adapterType;
    
    /**
     * 合法值：
     * "generated":自动生成MAC地址, 
     * "manual"：固定分配MAC地址,
     * "assigned"：由VirtualCenter分配MAC地址
     */
    // private String addressType;

    public String getAdapterName() {
        return adapterName;
    }

    public String getAdapterId() {
		return adapterId;
	}

	public void setAdapterId(String adapterId) {
		this.adapterId = adapterId;
	}

	public String getAdapterIP() {
		return adapterIP;
	}

	public void setAdapterIP(String adapterIP) {
		this.adapterIP = adapterIP;
	}

	public String getAdapterDNS() {
		return adapterDNS;
	}

	public void setAdapterDNS(String adapterDNS) {
		this.adapterDNS = adapterDNS;
	}

	public String getAdapterGateWay() {
		return adapterGateWay;
	}

	public void setAdapterGateWay(String adapterGateWay) {
		this.adapterGateWay = adapterGateWay;
	}

	public void setAdapterName(String adapterName) {
        this.adapterName = adapterName;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public String getAdapterType() {
        return adapterType;
    }

    public void setAdapterType(String adapterType) {
        this.adapterType = adapterType;
    }
    
    public String getAdapterNetMask() {
		return adapterNetMask;
	}

	public void setAdapterNetMask(String adapterNetMask) {
		this.adapterNetMask = adapterNetMask;
	}

	public String getAdapterSubNet() {
		return adapterSubNet;
	}

	public void setAdapterSubNet(String adapterSubNet) {
		this.adapterSubNet = adapterSubNet;
	}

	public VirtualDeviceConfigSpecOperation getAdapterOperation() {
		return adapterOperation;
	}

	public void setAdapterOperation(VirtualDeviceConfigSpecOperation adapterOperation) {
		this.adapterOperation = adapterOperation;
	}

	@Override
	public String toString() {
		return "VMNicInfo [adapterId=" + adapterId + ", adapterIP=" + adapterIP + ", adapterDNS=" + adapterDNS
				+ ", adapterNetMask=" + adapterNetMask + ", adapterGateWay=" + adapterGateWay + ", adapterSubNet="
				+ adapterSubNet + ", adapterName=" + adapterName + ", networkId=" + networkId + ", adapterOperation="
				+ adapterOperation + ", adapterType=" + adapterType + "]";
	}

}
