package org.waddys.xcloud.usermgmt.service.bo;

import java.io.Serializable;
import java.util.List;

public class Role implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String roleName;
    private String roleCode;
    private String description; // 角色描述,UI界面显示使用
    private List<User> users;
    private List<Resource> resources; // 拥有的资源
    private Boolean available = Boolean.FALSE; // 是否可用,如果不可用将不会添加给用户

    public Role() {
    }

    public Role(String roleName, String roleCode, String description,
            Boolean available) {
        this.roleName = roleName;
        this.roleCode = roleCode;
        this.description = description;
        this.available = available;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

}
