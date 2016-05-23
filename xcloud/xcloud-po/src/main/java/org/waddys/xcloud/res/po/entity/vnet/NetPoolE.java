package org.waddys.xcloud.res.po.entity.vnet;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 网络池
 * 
 * @author ghj
 *
 */
@Entity
@Table(name = "NetPool")
public class NetPoolE {
    /**
     * 主键 网络池Id
     * （底层cloudVM中网络池Id）
     */
	@Id
	@Column(name="netPoolId")
	private String netPoolId;
    /*
     * @Id
     * 
     * @GeneratedValue(generator = "uuid")
     * 
     * @GenericGenerator(name = "uuid", strategy = "uuid")
     * 
     * @Column(name = "netPoolId") private String netPoolId;
     */

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
    @Column(length = 500)
    private String configId;
    
    
   /* @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "configId")
    private VenvConfigE configInfo;
    
    
    public VenvConfigE getConfigInfo() {
		return configInfo;
	}

	public void setConfigInfo(VenvConfigE configInfo) {
		this.configInfo = configInfo;
	}*/

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
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

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Override
	public String toString() {
		return "NetPoolE [netPoolId=" + netPoolId + ", netName=" + netName + ", vlanNO=" + vlanNO + ", gateway="
				+ gateway + ", subNet=" + subNet + ", dns=" + dns + ", synDate=" + synDate + ", isAvl=" + isAvl
				+ ", orgId=" + orgId + ", configId=" + configId + "]";
	}

}
