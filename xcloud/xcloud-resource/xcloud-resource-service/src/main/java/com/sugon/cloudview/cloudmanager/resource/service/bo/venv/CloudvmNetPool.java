package com.sugon.cloudview.cloudmanager.resource.service.bo.venv;

import java.util.Date;

/**
 * 与cloudvm对接的网络池
 * @author ghj
 */
public class CloudvmNetPool {
    /**
     * 主键 网络池Id
     */
    private String netPoolId;
    /**
     * 网络名称
     */
    private String netName;
    /**
     * vlan号
     */
    private Integer vlanNO;

    /**
     * 网关
     */
    private String gateway;

    /**
     * 子网
     */
    private String subNet;

    /**
     * dns
     */
    private String dns;

    /**
     * 数据同步时间
     */
    private Date synDate;

    /**
     * 连接信息，底层cloudvm
     */
    private VenvConfig configInfo;
    /**
     * 是否可用
     */
    private Boolean isAvl;

    public Boolean getIsAvl() {
        return isAvl;
    }

    public void setIsAvl(Boolean isAvl) {
        this.isAvl = isAvl;
    }

    public VenvConfig getConfigInfo() {
        return configInfo;
    }

    public void setConfigInfo(VenvConfig configInfo) {
        this.configInfo = configInfo;
    }

    public String getNetPoolId() {
        return netPoolId;
    }

    public void setNetPoolId(String netPoolId) {
        this.netPoolId = netPoolId;
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
}
