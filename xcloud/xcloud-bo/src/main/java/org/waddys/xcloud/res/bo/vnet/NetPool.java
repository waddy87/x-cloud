package org.waddys.xcloud.res.bo.vnet;

import java.util.Date;

/**
 * 网络池
 * 
 * @author ghj
 *
 */
public class NetPool {

    @Override
	public String toString() {
		return "NetPool [netPoolId=" + netPoolId + ", netName=" + netName + ", vlanNO=" + vlanNO + ", gateway="
				+ gateway + ", subNetNo=" + subNetNo + ", subNet=" + subNet + ", dns=" + dns + ", synDate=" + synDate
				+ ", isAvl=" + isAvl + ", orgId=" + orgId + ", orgName=" + orgName + ", configId=" + configId + "]";
	}

	private String netPoolId;

    private String netName;

    private Integer vlanNO;

    private String gateway;
    private String subNetNo;

    public String getSubNetNo() {
        return subNetNo;
    }

    public void setSubNetNo(String subNetNo) {
        this.subNetNo = subNetNo;
    }

    private String subNet;

    private String dns;

    /**
     * 同步时间
     */
    private Date synDate;

    /**
     * 是否可用
     */
    private Boolean isAvl;

    /**
     * 组织Id
     */
    private String orgId;
    /**
     * 组织名称
     */
    private String orgName;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * 连接信息
     */
    private String configId;

    /*
     * private VenvConfig configInfo;
     * 
     * 
     * public VenvConfig getConfigInfo() { return configInfo; }
     * 
     * public void setConfigInfo(VenvConfig configInfo) { this.configInfo =
     * configInfo; }
     */

    public void setNetPoolId(String netPoolId) {
        this.netPoolId = netPoolId;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getNetPoolId() {
        return netPoolId;
    }

    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }

    public Integer getVlanNO() {
        return vlanNO;
    }

    public void setVlanNO(Integer vlanNO) {
        this.vlanNO = vlanNO;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getSubNet() {
        return subNet;
    }

    public void setSubNet(String subNet) {
        this.subNet = subNet;
    }

    public String getDns() {
        return dns;
    }

    public void setDns(String dns) {
        this.dns = dns;
    }

    public Date getSynDate() {
        return synDate;
    }

    public void setSynDate(Date synDate) {
        this.synDate = synDate;
    }

    public Boolean getIsAvl() {
        return isAvl;
    }

    public void setIsAvl(Boolean isAvl) {
        this.isAvl = isAvl;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

}
