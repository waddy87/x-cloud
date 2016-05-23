package org.waddys.xcloud.user.bo;

public enum RoleEnum {
    OPERATION_MANAGER("运营管理员", "1", "operation_manager"),

    ORG_MANAGER("组织管理员", "2", "org_manager"),

    ORG_USER("组织用户", "3", "org_user"),

    SYS_MANAGER("系统管理员", "4", "sys_manager");

    // 成员变量
    private String name;
    private String value;
    private String code;

    // 构造方法
    private RoleEnum(String name, String value, String code) {
        this.name = name;
        this.value = value;
        this.code = code;
    }

    // 普通方法
    public static String getName(String value) {
        for (RoleEnum r : RoleEnum.values()) {
            if (r.getValue().equals(value)) {
                return r.name;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static void main(String[] args) {
        System.out.println(RoleEnum.OPERATION_MANAGER.getCode());
    }
}
