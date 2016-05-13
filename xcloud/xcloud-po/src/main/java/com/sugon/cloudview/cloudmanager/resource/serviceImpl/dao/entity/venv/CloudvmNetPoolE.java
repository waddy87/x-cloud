package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.venv;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

/**
 * 与cloudvm对接的网络池
 * 
 * @author ghj
 */
@Entity(name = "Cloudvm_NetPool")
public class CloudvmNetPoolE {
    /**
     * 主键 网络池Id
     */
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "netPoolId")
    private String netPoolId;

    /**
     * 连接信息，底层cloudvm
     */
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "configId")
    private VenvConfigE configInfo;

    /**
     * 网络名称
     */
    @Column(name = "netName", length = 255)
    private String netName;
    /**
     * vlan号
     */
    @Column(name = "vlanNo")
    private Integer vlanNO;

    /**
     * 网关
     */
    @Column(name = "gateway", length = 255)
    private String gateway;

    /**
     * 子网
     */
    @Column(name = "subNet", length = 255)
    private String subNet;

    /**
     * dns
     */
    @Column(name = "dns", length = 255)
    private String dns;

    /**
     * 同步数据时间
     */
    private Date synDate;

    /**
     * 是否可用
     */
    private Boolean isAvl;

    public VenvConfigE getConfigInfo() {
        return configInfo;
    }

    public void setConfigInfo(VenvConfigE configInfo) {
        this.configInfo = configInfo;
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


}
