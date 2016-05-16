package com.sugon.cloudview.cloudmanager.vijava.data;

import com.sugon.vim25.VirtualDeviceConfigSpecOperation;

public class VMDiskInfo {
	
	private String diskId;
    private String name;
    private String datastoreId;
    
    /**
     * 磁盘模式，合法值包括
     *  persistent：立即并永久性写入磁盘 , 
     *  nonpersistent, undoable ,
     *  independent_persistent , 
     *  independent_nonpersistent, append
     */
    private String diskMode;
    
    /**
     * 置备类型，true为薄置备，false为厚置备
     */
    private Boolean thinProvisioned;

    private Long diskSizeGB;

    private VirtualDeviceConfigSpecOperation diskOperation; 
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiskMode() {
        return diskMode;
    }

    public void setDiskMode(String diskMode) {
        this.diskMode = diskMode;
    }
    
    public String getDatastoreId() {
		return datastoreId;
	}

	public void setDatastoreId(String datastoreId) {
		this.datastoreId = datastoreId;
	}

	public Boolean getThinProvisioned() {
        return thinProvisioned;
    }

    public void setThinProvisioned(Boolean thinProvisioned) {
        this.thinProvisioned = thinProvisioned;
    }

    public Long getDiskSizeGB() {
        return diskSizeGB;
    }

    public void setDiskSizeGB(Long diskSizeGB) {
        this.diskSizeGB = diskSizeGB;
    }

    public VirtualDeviceConfigSpecOperation getDiskOperation() {
		return diskOperation;
	}

	public void setDiskOperation(VirtualDeviceConfigSpecOperation diskOperation) {
		this.diskOperation = diskOperation;
	}

	public String getDiskId() {
		return diskId;
	}

	public void setDiskId(String diskId) {
		this.diskId = diskId;
	}

	@Override
	public String toString() {
		return "VMDiskInfo [diskId=" + diskId + ", name=" + name + ", datastoreId=" + datastoreId
				+ ", diskMode=" + diskMode + ", thinProvisioned=" + thinProvisioned + ", diskSizeGB="
				+ diskSizeGB + ", diskOperation=" + diskOperation + "]";
	}

}
