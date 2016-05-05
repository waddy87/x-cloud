package com.sugon.cloudview.cloudmanager.resource.service.bo.venv;

import java.util.Date;

/**
 * 主机
 * 
 * @author ghj
 */
public class Host {
	/**
     * 主机id
     */
    private String hostId;

    /**
     * 连接信息，底层cloudvm
     */
    private VenvConfig configInfo;
    /**
     * 集群id
     */
    private Cluster cluster;

    /**
     * 主机名称
     */
    private String hostname;

    /**
     * 数据同步时间
     */
    private Date synDate;


    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public String getHostname() {
        return hostname;
    }

    public VenvConfig getConfigInfo() {
        return configInfo;
    }

    public void setConfigInfo(VenvConfig configInfo) {
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
