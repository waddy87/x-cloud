package com.sugon.cloudview.cloudmanager.project.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sugon.cloudview.dboperation.EntityBase;

@Entity
@Table(name = "project_vm")
public class ProjectVM extends EntityBase{
    /**
     * 
     */
    private static final long serialVersionUID = -8942275116405514036L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(nullable = false, unique = true, length = 32)
    private String id;

    @Column(name = "project_id", length = 32)
    private String projectId;

    @Column(name = "vm_id", unique = true, length = 32)
    private String vmId;

    public ProjectVM() {
        // TODO Auto-generated constructor stub
    }

    public ProjectVM(String projectId, String vmId) {
        this.projectId = projectId;
        this.vmId = vmId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

}
