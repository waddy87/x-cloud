/**
 * 
 */
package com.sugon.cloudview.cloudmanager.vm.constant;

/**
 * 虚机来源类型
 * 
 * @author zhangdapeng
 *
 */
public enum SourceType {

    ASSIGN("分配"), APPLY("申请");

    private String type;

    private SourceType(String type) {
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
