/**
 * 
 */
package org.waddys.xcloud.vm.constant;

/**
 * 虚拟网卡状态
 * 
 * @author zhangdapeng
 *
 */
public enum NetStatus {

    NONE(""), DELETED("已删除");

    private String status;

    private NetStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
