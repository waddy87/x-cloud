/**
 * 
 */
package org.waddys.xcloud.vm.constant;

/**
 * 操作状态
 * 
 * @author zhangdapeng
 *
 */
public enum VmStatus {

    // NONE("none"), INITED("inited"), STARTED("started"), STOPPED("stopped"),
    // DELETED("deleted");
    NONE(""), INITED("已创建"), STARTED("已启动"), STOPPED("已关闭"), DELETED("已删除");

    private String status;

    private VmStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
