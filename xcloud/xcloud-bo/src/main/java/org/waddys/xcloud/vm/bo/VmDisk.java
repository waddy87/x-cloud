/**
 * 
 */
package org.waddys.xcloud.vm.bo;

import java.util.Date;

import org.waddys.xcloud.res.bo.vdc.StoragePool;
import org.waddys.xcloud.vm.constant.DiskBizType;


/**
 * 虚机磁盘实体
 * 
 * @author zhangdapeng
 *
 */
public class VmDisk extends Asset {

    /**
     * 唯一标识
     */
    private String id;

    /**
     * 虚拟磁盘内部唯一标识
     */
    private String internalId;

    /**
     * 磁盘类型
     */
    private DiskBizType bizType;

    /**
     * 任务标识
     */
    private String taskId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 对应虚机唯一业务标识
     */
    private String vmId;

    /**
     * 物理存储池唯一标识（内外合一）
     */
    private String sPoolId;
    private StoragePool sPool;

    /**
     * 逻辑存储池
     */
    private String logicalPoolId;

    /**
     * 磁盘总容量（GB）
     */
    private Long totalCapacity;

    /**
     * 磁盘剩余容量（GB）
     */
    private Long usedCapacity;

    public DiskBizType getBizType() {
		return bizType;
	}

	public void setBizType(DiskBizType bizType) {
		this.bizType = bizType;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public String getsPoolId() {
        return sPoolId;
    }

    public void setsPoolId(String sPoolId) {
        this.sPoolId = sPoolId;
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

    public StoragePool getsPool() {
        return sPool;
    }

    public void setsPool(StoragePool sPool) {
        this.sPool = sPool;
    }

    public String getLogicalPoolId() {
        return logicalPoolId;
    }

    public void setLogicalPoolId(String logicalPoolId) {
        this.logicalPoolId = logicalPoolId;
    }

    @Override
    public String toString() {
        return "{vmId:" + vmId + ",sPoolId:" + sPoolId + "}";
    }

}
