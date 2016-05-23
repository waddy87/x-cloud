/**
 * Created on 2016年3月28日
 */
package org.waddys.xcloud.monitor.bo;

/**
 * 功能名: 请填写功能名 功能描述: 请简要描述功能的要点 Copyright: Copyright (c) 2016 公司: 曙光云计算技术有限公司
 *
 * @author wq
 * @version 2.0.0 sp1
 */
public class VMBo extends ComputeSystemBo {
    // 虚拟机IP地址
    private String ipAddr;

    // 虚拟机状态
    private String status;

    // 虚拟机所属Host Id
    private String hostId;

    // 虚拟机所属Host名称
    private String hostName;

    // 虚拟机所属集群
    private String clusterName;

    // 虚拟机所属数据中心
    private String dateCenterName;

    // 虚拟机电源状态
    private String powerStatus;

    private String connectionStatus;

    // 虚拟机操作系统
    private String os;

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

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getDateCenterName() {
        return dateCenterName;
    }

    public void setDateCenterName(String dateCenterName) {
        this.dateCenterName = dateCenterName;
    }

    public String getPowerStatus() {
        return powerStatus;
    }

    public void setPowerStatus(String powerStatus) {
        this.powerStatus = powerStatus;
    }

    public String getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(String connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }
}
