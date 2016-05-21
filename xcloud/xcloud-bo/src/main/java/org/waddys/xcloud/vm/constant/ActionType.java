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
public enum ActionType {

    REFRESH("refresh"), START("start"), STOP("stop"), ASSIGN("assign"), REVOKE("revoke"), DESTROY("destroy"), RELEASE(
            "release"), RESET_PASSWORD("resetPassword");

    private String type;

    private ActionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

}
