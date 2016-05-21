package org.waddys.xcloud.monitor.serviceImpl.entity;

import java.util.List;

public class Host extends ComputerSystem {

    // 物理机IP地址
    private String ipAddr;

    // 物理机状态
    private String status;

    // 电源状态
    private String powerStatus;

    // private int vmNumber;

    // 虚拟机所属集群
    private String clusterName;

    // 虚拟机所属数据中心
    private String dataCenterName;

    // 虚拟机列表信息
    private List<VM> vmList;

    private int vmNum;
    private int vmAccessibleNum;
    private int vmUnaccessibleNum;
    private int storeNum;
    private int storeAccessibleNum;
    private int storeUnaccessibleNum;

    private String connectionStatus;

    /**
     * @return the connectionStatus
     */
    public String getConnectionStatus() {
        return connectionStatus;
    }

    /**
     * @param connectionStatus
     *            the connectionStatus to set
     */
    public void setConnectionStatus(String connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    // /**
    // * @return the vmNumber
    // */
    // public int getVmNumber() {
    // return vmNumber;
    // }
    //
    // /**
    // * @param vmNumber
    // * the vmNumber to set
    // */
    // public void setVmNumber(int vmNumber) {
    // this.vmNumber = vmNumber;
    // }

    /**
     * @return the vmNum
     */
    public int getVmNum() {
        return vmNum;
    }

    /**
     * @param vmNum
     *            the vmNum to set
     */
    public void setVmNum(int vmNum) {
        this.vmNum = vmNum;
    }

    /**
     * @return the vmAccessibleNum
     */
    public int getVmAccessibleNum() {
        return vmAccessibleNum;
    }

    /**
     * @param vmAccessibleNum
     *            the vmAccessibleNum to set
     */
    public void setVmAccessibleNum(int vmAccessibleNum) {
        this.vmAccessibleNum = vmAccessibleNum;
    }

    /**
     * @return the vmUnaccessibleNum
     */
    public int getVmUnaccessibleNum() {
        return vmUnaccessibleNum;
    }

    /**
     * @param vmUnaccessibleNum
     *            the vmUnaccessibleNum to set
     */
    public void setVmUnaccessibleNum(int vmUnaccessibleNum) {
        this.vmUnaccessibleNum = vmUnaccessibleNum;
    }

    /**
     * @return the storeNum
     */
    public int getStoreNum() {
        return storeNum;
    }

    /**
     * @param storeNum
     *            the storeNum to set
     */
    public void setStoreNum(int storeNum) {
        this.storeNum = storeNum;
    }

    /**
     * @return the storeAccessibleNum
     */
    public int getStoreAccessibleNum() {
        return storeAccessibleNum;
    }

    /**
     * @param storeAccessibleNum
     *            the storeAccessibleNum to set
     */
    public void setStoreAccessibleNum(int storeAccessibleNum) {
        this.storeAccessibleNum = storeAccessibleNum;
    }

    /**
     * @return the storeUnaccessibleNum
     */
    public int getStoreUnaccessibleNum() {
        return storeUnaccessibleNum;
    }

    /**
     * @param storeUnaccessibleNum
     *            the storeUnaccessibleNum to set
     */
    public void setStoreUnaccessibleNum(int storeUnaccessibleNum) {
        this.storeUnaccessibleNum = storeUnaccessibleNum;
    }

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

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getDataCenterName() {
        return dataCenterName;
    }

    public void setDataCenterName(String dataCenterName) {
        this.dataCenterName = dataCenterName;
    }

    public List<VM> getVmList() {
        return vmList;
    }

    public void setVmList(List<VM> vmList) {
        this.vmList = vmList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Host [ipAddr=" + ipAddr + ", status=" + status + ", powerStatus=" + powerStatus + ", clusterName="
                + clusterName + ", dataCenterName=" + dataCenterName + ", vmList=" + vmList + ", vmNum=" + vmNum
                + ", vmAccessibleNum=" + vmAccessibleNum + ", vmUnaccessibleNum=" + vmUnaccessibleNum + ", storeNum="
                + storeNum + ", storeAccessibleNum=" + storeAccessibleNum + ", storeUnaccessibleNum="
                + storeUnaccessibleNum + "]";
    }

}
