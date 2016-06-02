/**
 * 
 */
package org.waddys.xcloud.vm.constant;

/**
 * 虚拟磁盘业务类型
 * 
 * @author zhangdapeng
 *
 */
public enum DiskBizType {

    SYSTEM("系统盘"), DATA("数据盘");

    private String value;

    private DiskBizType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
