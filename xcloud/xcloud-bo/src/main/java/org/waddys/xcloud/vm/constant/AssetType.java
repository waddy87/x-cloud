/**
 * 
 */
package org.waddys.xcloud.vm.constant;

/**
 * 资产类型
 * 
 * @author zhangdapeng
 *
 */
public enum AssetType {

    VM("虚机"), DISK("磁盘"), NIC("网卡");

    private String type;

    private AssetType(String type) {
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
