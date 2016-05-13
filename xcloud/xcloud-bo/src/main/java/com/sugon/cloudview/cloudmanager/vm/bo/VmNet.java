/**
 * 
 */
package com.sugon.cloudview.cloudmanager.vm.bo;

import java.util.Date;

import com.sugon.cloudview.cloudmanager.resource.service.bo.vnet.NetPool;

/**
 * 虚机网络实体
 * 
 * @author zhangdapeng
 *
 */
public class VmNet extends Asset {

    /**
     * 唯一标识
     */
    private String id;

    /**
     * 唯一标识
     */
    private String internalId;

    /**
     * 任务标识
     */
    private String taskId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 对应网络唯一标识（内外合一）
     */
    private String netId;
    private NetPool net;

    /**
     * 对应虚机唯一标识
     */
    private String vmId;

    /**
     * vlan号（1~4096）
     */
    private Integer vlan;

    /**
     * 子网（192.168.0.0/16）
     */
    private String subnet;

    /**
     * 网关地址（192.168.0.254）
     */
    private String gateway;

    /**
     * 主dns地址
     */
    private String dns;

    /**
     * IP地址（192.168.0.2）
     */
    private String ip;

    /**
     * OS用户名（不做持久化；设置网络时，虚拟化接口需要）
     */
    private String osUsername;

    /**
     * OS密码（不做持久化；设置网络时，虚拟化接口需要）
     */
    private String osPassword;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNetId() {
        return netId;
    }

    public void setNetId(String netId) {
        this.netId = netId;
    }

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public Integer getVlan() {
        return vlan;
    }

    public void setVlan(Integer vlan) {
        this.vlan = vlan;
    }

    public String getSubnet() {
        return subnet;
    }

    public void setSubnet(String subnet) {
        this.subnet = subnet;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getDns() {
        return dns;
    }

    public void setDns(String dns) {
        this.dns = dns;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public NetPool getNet() {
        return net;
    }

    public void setNet(NetPool net) {
        this.net = net;
    }

    public String getOsUsername() {
        return osUsername;
    }

    public void setOsUsername(String osUsername) {
        this.osUsername = osUsername;
    }

    public String getOsPassword() {
        return osPassword;
    }

    public void setOsPassword(String osPassword) {
        this.osPassword = osPassword;
    }

    @Override
    public String toString() {
        return "{vmId:" + vmId + ",net:" + netId + ",vlan:" + vlan + ",ip:" + ip + "}";
    }

}
