package org.waddys.xcloud.user.po.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "role")
public class RoleE implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String roleCode;// 角色标识 程序中判断使用,如"admin"
    private String roleName;
    private String description; // 角色描述,UI界面显示使用
    private Set<UserE> users;
    private Set<ResourceE> resources; // 拥有的资源
    private Boolean available = Boolean.FALSE; // 是否可用,如果不可用将不会添加给用户

    public RoleE() {
    }

    public RoleE(String roleName, String roleCode, String description,
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

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
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

    @ManyToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
            CascadeType.MERGE })
    @JoinTable(name = "role_resource", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "resource_id"))
    public Set<ResourceE> getResources() {
        return resources;
    }

    public void setResources(Set<ResourceE> resources) {
        this.resources = resources;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @ManyToMany(mappedBy="roles")
    public Set<UserE> getUsers() {
        return users;
    }

    public void setUsers(Set<UserE> users) {
        this.users = users;
    }
}
