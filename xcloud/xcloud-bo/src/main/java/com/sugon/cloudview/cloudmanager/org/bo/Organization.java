package com.sugon.cloudview.cloudmanager.org.bo;

import java.util.Date;

import com.sugon.cloudview.cloudmanager.org.constant.OrgStatus;

/**
 * 
 * @author zhaoych@sugon.com
 *
 */

public class Organization {

    // GUID, length = 32
    private String id;
    // length = 128
    private String name;
    // length = 512
    private String address;
    // length = 512
    private String remarks;
    // length = 128
    private String creater;
    // GUID, length = 32
    private String owner;
    // type = java.util.Date
    private Date create_time;
    // lenth = 1
    private OrgStatus status;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public OrgStatus getStatus() {
        return status;
    }

    public void setStatus(OrgStatus status) {
        this.status = status;
    }

    public Organization(String id, String name, String address, String remarks, String creater, String owner,
            Date create_time, OrgStatus status) {
        super();
        this.id = id;
        this.name = name;
        this.address = address;
        this.remarks = remarks;
        this.creater = creater;
        this.owner = owner;
        this.create_time = create_time;
        this.status = status;
    }

    public Organization() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return " {\"id\":" + id + ",\"name\":" + name + ",\"address\":" + address + ",\"remarks\":" + remarks
                + ",\"ower\":" + owner + ",\"creator\":" + creater + ",\"create_time\":" + create_time + ",\"status\":"
                + status + "}";
    }

}