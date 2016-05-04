package com.sugon.cloudview.cloudmanager.common.base.exception;

public enum CloudviewCommonExceptionCode implements CloudviewExceptionCode {

    EP001_S001_M001_L1_C00001("未知异常", "请联系管理员");

    private String desc;

    private String solution;

    private CloudviewCommonExceptionCode(String desc, String solution) {
        this.desc = desc;
        this.solution = solution;
    }
    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public String getSolution() {
        return solution;
    }

}
