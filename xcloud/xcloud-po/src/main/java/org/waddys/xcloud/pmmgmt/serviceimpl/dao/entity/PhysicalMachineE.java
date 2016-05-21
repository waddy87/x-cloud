package org.waddys.xcloud.pmmgmt.serviceimpl.dao.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.waddys.xcloud.db.EntityBase;

@Entity
@Table(name = "physical_machine")
public class PhysicalMachineE extends EntityBase {

    private static final long serialVersionUID = 6182332156851340659L;

    private String id;
    private String name;
    private String description;
    private String ip;
    private String os;// 操作系统
    private String ipmiIp;
    private String ipmiUserName;
    private String ipmiPassword;
    private String orgId;
    private String orgName;
    private String monitorMac;// mac地址
    private String hostType;
    private String cpuType;
    private String deviceModel;
    private String serialNumber;
    private Date createDate;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getMonitorMac() {
        return monitorMac;
    }

    public void setMonitorMac(String monitorMac) {
        this.monitorMac = monitorMac;
    }

    public String getHostType() {
        return hostType;
    }

    public void setHostType(String hostType) {
        this.hostType = hostType;
    }

    public String getCpuType() {
        return cpuType;
    }

    public void setCpuType(String cpuType) {
        this.cpuType = cpuType;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getIpmiIp() {
        return ipmiIp;
    }

    public void setIpmiIp(String ipmiIp) {
        this.ipmiIp = ipmiIp;
    }

    public String getIpmiUserName() {
        return ipmiUserName;
    }

    public void setIpmiUserName(String ipmiUserName) {
        this.ipmiUserName = ipmiUserName;
    }

    public String getIpmiPassword() {
        return ipmiPassword;
    }

    public void setIpmiPassword(String ipmiPassword) {
        this.ipmiPassword = ipmiPassword;
    }

}
