/**
 * 
 */
package com.sugon.cloudview.cloudmanager.vm.constant;

/**
 * 虚拟磁盘状态
 * 
 * @author zhangdapeng
 *
 */
public enum DiskStatus {

    NONE(""), DELETED("已删除");

    private String status;

    private DiskStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
