/**
 * 
 */
package org.waddys.xcloud.vm.po.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 虚机网络实体
 * 
 * @author zhangdapeng
 *
 */
@Entity
@Table(name = "vm_net")
public class VmNetE {

    /**
     * 唯一标识
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(nullable = false, unique = true, length = 32)
    private String id;

    /**
     * 虚拟网卡内部唯一标识
     */
    @Column(name = "internal_id")
    private String internalId;

    /**
     * 任务标识
     */
    @Column(name = "task_id")
    private String taskId;

    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /**
     * 对应网络唯一标识
     */
    @Column(name = "net_id", nullable = false, length = 32)
    private String netId;

    /**
     * 对应虚机唯一业务标识
     */
    @Column(name = "vm_id", nullable = false, length = 32)
    private String vmId;

    /**
     * vlan号（1~4096）
     */
    @Column
    private Integer vlan;

    /**
     * 子网（192.168.0.0/16）
     */
    @Column(nullable = false)
    private String subnet;

    /**
     * 网关地址（192.168.0.254）
     */
    @Column
    private String gateway;

    /**
     * 主dns地址
     */
    @Column
    private String dns;

    /**
     * IP地址（192.168.0.2）
     */
    @Column(nullable = false)
    private String ip;

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

    @Override
    public String toString() {
        return "{vmId:" + vmId + ",vlan:" + vlan + ",ip:" + ip + "}";
    }

}
