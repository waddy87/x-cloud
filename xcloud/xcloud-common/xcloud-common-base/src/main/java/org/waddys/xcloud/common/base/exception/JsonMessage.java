package org.waddys.xcloud.common.base.exception;

import java.io.Serializable;
import java.util.List;

public class JsonMessage implements Serializable {

    private static final long serialVersionUID = 2026439655797019548L;

    private String code;
    private List<String> params;
    private String desc;
    private String solution;

    public String getCode() {
        return code;
    }

    public JsonMessage setCode(String code) {
        this.code = code;
        return this;
    }

    public List<String> getParams() {
        return params;
    }

    public JsonMessage setParams(List<String> params) {
        this.params = params;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public JsonMessage setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public String getSolution() {
        return solution;
    }

    public JsonMessage setSolution(String solution) {
        this.solution = solution;
        return this;
    }

    @Override
    public String toString() {
        return "JsonMessage [code=" + code + ", params=" + params + ", desc=" + desc + ", solution=" + solution + "]";
    }

}
