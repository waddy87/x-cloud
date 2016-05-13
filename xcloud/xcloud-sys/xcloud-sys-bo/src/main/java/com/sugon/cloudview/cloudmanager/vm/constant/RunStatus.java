/**
 * 
 */
package com.sugon.cloudview.cloudmanager.vm.constant;

/**
 * 操作状态
 * 
 * @author zhangdapeng
 *
 */
public enum RunStatus {

    NONE("none"), CREATING("creating"), CreateError("CreateError"), DELETING("deleting"), DeleteError(
            "DeleteError"), STARTING("starting"), StartError("StartError"), STOPPING("stopping"), StopError(
                    "StopError");

    private String status;

    private RunStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
