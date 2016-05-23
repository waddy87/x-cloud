package org.waddys.xcloud.org.po.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.waddys.xcloud.db.EntityBase;
import org.waddys.xcloud.org.constant.OrgStatus;

@Entity
@Table(name = "organization")
public class OrganizationE extends EntityBase{
    /**
     * 
     */
    private static final long serialVersionUID = -8942275116405514036L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(nullable = false, unique = true, length = 32)
    private String id;

    @Column(unique = true, length = 128)
    private String name;

    @Column(length = 512)
    private String address;

    @Column(nullable = true, length = 512)
    private String remarks;

    @Column(nullable = false, length = 32)
    private String creater;

    @Column(length = 32)
    private String owner;

    @Column(name = "create_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date create_time;

    @Column(nullable = false)
    private OrgStatus status;

    public OrganizationE() {

    }

    public OrganizationE(String name, String address, String remarks, String creater, String owner, Date create_time,
            OrgStatus status) {
        super();
        this.name = name;
        this.address = address;
        this.remarks = remarks;
        this.creater = creater;
        this.owner = owner;
        this.create_time = create_time;
        this.status = status;
    }

    public OrganizationE(String id, String name, String address, String remarks, String creater, String owner,
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
