/**
 * 
 */
package com.sugon.cloudview.cloudmanager.org.constant;

/**
 * 组织状态
 * 
 * @author zhangdapeng
 *
 */
public enum OrgStatus {

    NORMAL("normal"), DELETED("deleted");

    private String status;

    private OrgStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
