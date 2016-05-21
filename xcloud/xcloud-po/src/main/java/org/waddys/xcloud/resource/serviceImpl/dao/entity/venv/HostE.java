package org.waddys.xcloud.resource.serviceImpl.dao.entity.venv;

import java.io.Serializable;
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
 * 
 * @author ghj
 *
 */
@Entity(name = "Host")
public class HostE implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
     * 主机id
     */

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "hostId", length = 255)
    private String hostId;
    /**
     * 连接信息，底层cloudvm
     */
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "configId")
    private VenvConfigE configInfo;
    /**
     * 集群id
     */
    @ManyToOne(cascade = CascadeType.REFRESH, optional = false)
    @JoinColumn(name = "clusterId")
    private ClusterE cluster;

    /**
     * 主机名称
     */
    @Column(name = "hostname", length = 255)
    private String hostname;

    /**
     * 创建时间
     */
    private Date synDate;


    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public ClusterE getCluster() {
        return cluster;
    }

    public void setCluster(ClusterE cluster) {
        this.cluster = cluster;
    }

    public String getHostname() {
        return hostname;
    }

    public VenvConfigE getConfigInfo() {
        return configInfo;
    }

    public void setConfigInfo(VenvConfigE configInfo) {
        this.configInfo = configInfo;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Date getSynDate() {
        return synDate;
    }

    public void setSynDate(Date synDate) {
        this.synDate = synDate;
    }

}
