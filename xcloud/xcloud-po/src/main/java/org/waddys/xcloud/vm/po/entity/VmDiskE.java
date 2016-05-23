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
 * 虚机磁盘实体
 * 
 * @author zhangdapeng
 *
 */
@Entity
@Table(name = "vm_disk")
public class VmDiskE {

    /**
     * 唯一标识
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(nullable = false, unique = true, length = 32)
    private String id;

    /**
     * 虚拟磁盘内部唯一标识
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
     * 对应存储唯一标识
     */
    @Column(name = "spool_id", nullable = false)
    private String sPoolId;

    /**
     * 逻辑存储池
     */
    @Column(name = "logical_spool_id")
    private String logicalPoolId;

    /**
     * 对应虚机唯一业务标识
     */
    @Column(name = "vm_id", nullable = false, length = 32)
    private String vmId;

    /**
     * 磁盘总容量（GB）
     */
    @Column(name = "capacity_total")
    private Long totalCapacity;

    /**
     * 磁盘剩余容量（GB）
     */
    @Column(name = "capacity_used")
    private Long usedCapacity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getsPoolId() {
        return sPoolId;
    }

    public void setsPoolId(String sPoolId) {
        this.sPoolId = sPoolId;
    }

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public Long getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(Long totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public Long getUsedCapacity() {
        return usedCapacity;
    }

    public void setUsedCapacity(Long usedCapacity) {
        this.usedCapacity = usedCapacity;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLogicalPoolId() {
        return logicalPoolId;
    }

    public void setLogicalPoolId(String logicalPoolId) {
        this.logicalPoolId = logicalPoolId;
    }

    @Override
    public String toString() {
        return "{vmId:" + vmId + ",spoolId:" + sPoolId + ",diskInternal:" + internalId + "}";
    }

}
