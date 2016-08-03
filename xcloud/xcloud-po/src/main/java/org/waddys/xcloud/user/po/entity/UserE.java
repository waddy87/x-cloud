package org.waddys.xcloud.user.po.entity;

import java.io.Serializable;
import java.util.Date;
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
import org.waddys.xcloud.project.po.entity.ProjectE;

@Entity
@Table(name = "user")
public class UserE implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String orgId;
    private String orgName;
    private String email;
    private String telephone;
    private String username; // 用户名
    private String realname;
    private String password; // 密码
    private String salt; // 加密密码的盐
    private Set<RoleE> roles;
    private Boolean locked = Boolean.FALSE;
    private Date createDate;
    private Boolean isDelete = Boolean.FALSE;// 是否被删除
    private Set<ProjectE> projects;

    @ManyToMany(mappedBy = "users")
    public Set<ProjectE> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectE> projects) {
        this.projects = projects;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public UserE() {
    }

    public UserE(String username, String password) {
        this.username = username;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @ManyToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
            CascadeType.MERGE })
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), 
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<RoleE> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleE> roles) {
        this.roles = roles;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

}
