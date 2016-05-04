/**
 * Created on 2016年4月22日
 */
package com.sugon.cloudview.cloudmanager.monitor.api.bo;

/**
 * 功能名: 请填写功能名
 * 功能描述: 请简要描述功能的要点
 * Copyright: Copyright (c) 2016
 * 公司: 曙光云计算技术有限公司
 *
 * @author yangkun
 * @version 2.0.0 sp1
 */

/////定义TOPO返回的jSON串数据结构
public class VMTopo {
	    String  vmName;
	    String  vmId;
	    // 虚拟机状态
	    String status;
	    // 电源状态
	    String powerStatus;
	    // 虚拟机IP地址
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
		public String getVmName() {
			return vmName;
		}
		public void setVmName(String vmName) {
			this.vmName = vmName;
		}
		public String getVmId() {
			return vmId;
		}
		public void setVmId(String vmId) {
			this.vmId = vmId;
		}
}
