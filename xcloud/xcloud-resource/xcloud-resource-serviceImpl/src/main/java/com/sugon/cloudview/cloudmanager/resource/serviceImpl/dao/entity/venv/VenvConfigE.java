package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.venv;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

/**
 * 连接信息
 * 
 * @author sunht
 *
 */
@Entity(name = "VenvConfig")
public class VenvConfigE implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "configId")
    /**
     * 主键
     */
    private String configId;
    /**
     * 连接名称
     */
    private String configName;
    /**
     * 连接地址
     */
    private String iP;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 版本
     */
    private String version;
    /**
     * 状态
     */
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @OneToMany(mappedBy = "configInfo", cascade = CascadeType.ALL)
    private Set<HostE> host;

    @OneToMany(mappedBy = "configInfo", cascade = CascadeType.ALL)
    private Set<ResourcePoolE> resourcePool;

    @OneToMany(mappedBy = "configInfo", cascade = CascadeType.ALL)
    private Set<ClusterE> cluster;

    @OneToMany(mappedBy = "configInfo", cascade = CascadeType.ALL)
    private Set<CloudvmNetPoolE> cloudvm_NetPool;

    public Set<HostE> getHost() {
        return host;
    }

    public void setHost(Set<HostE> host) {
        this.host = host;
    }

    public Set<ResourcePoolE> getResourcePool() {
        return resourcePool;
    }

    public void setResourcePool(Set<ResourcePoolE> resourcePool) {
        this.resourcePool = resourcePool;
    }

    public Set<ClusterE> getCluster() {
        return cluster;
    }

    public void setCluster(Set<ClusterE> cluster) {
        this.cluster = cluster;
    }

    public Set<CloudvmNetPoolE> getCloudvm_NetPool() {
        return cloudvm_NetPool;
    }

    public void setCloudvm_NetPool(Set<CloudvmNetPoolE> cloudvm_NetPool) {
        this.cloudvm_NetPool = cloudvm_NetPool;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getiP() {
        return iP;
    }

    public void setiP(String iP) {
        this.iP = iP;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public VenvConfigE() {
    }
}
