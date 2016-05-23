package org.waddys.xcloud.project.bo;

import java.util.Date;
import java.util.Set;

import org.waddys.xcloud.user.bo.User;

/**
 * 
 * @author zhaoych@sugon.com
 *
 */

public class Project {

    // GUID, length = 32
    private String id;

    // length = 128
    private String name;

    // length = 512
    private String description;

    // GUID, length = 32
    private String orgId;

    // length = 512
    private String orgName;

    private Set<User> users;

    // type = java.util.Date
    private Date createTime;

    // lenth = 1
    private String status;

    public Project() {
    }

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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return " {\"id\":" + id + ",\"name\":" + name + ",\"description\":" + description + ",\"orgName\":" + orgName
                + ",\"status\":" + status + "}";
    }

}