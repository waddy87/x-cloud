/**
 * Created on 2016年4月22日
 */
package com.sugon.cloudview.cloudmanager.monitor.api.bo;

import java.util.List;


/**
 * 功能名: 请填写功能名
 * 功能描述: 请简要描述功能的要点
 * Copyright: Copyright (c) 2016
 * 公司: 曙光云计算技术有限公司
 *
 * @author yangkun
 * @version 2.0.0 sp1
 */
public class HostTopo {
	String hostName;
	String  hostId;
	 // 物理机状态
    String status;
    // 电源状态
    String powerStatus;
    //  物理机IP地址
    String ipAddr;
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPowerStatus() {
		return powerStatus;
	}
	public void setPowerStatus(String powerStatus) {
		this.powerStatus = powerStatus;
	}
	List<VMTopo>vmsTopo;
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getHostId() {
		return hostId;
	}
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}
	public List<VMTopo> getVmsTopo() {
		return vmsTopo;
	}
	public void setVmsTopo(List<VMTopo> vmsTopo) {
		this.vmsTopo = vmsTopo;
	}
}
