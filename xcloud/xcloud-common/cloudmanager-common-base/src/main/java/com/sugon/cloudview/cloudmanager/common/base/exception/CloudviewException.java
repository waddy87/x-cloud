package com.sugon.cloudview.cloudmanager.common.base.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CloudviewException extends Exception {

    private static final long serialVersionUID = 1059098660255819814L;
    private CloudviewExceptionCode code;
    private String[] params;

    public CloudviewException() {
        super();
    }

    public CloudviewException(String message) {
        super(message);
    }

    public CloudviewException(Throwable t) {
        super(t);
        if (t != null && t instanceof CloudviewException) {
            this.code = ((CloudviewException) t).code;
            this.params = ((CloudviewException) t).params;
        }
    }

    public CloudviewException(String message, Exception e) {
        super(message, e);
        if (e != null && e instanceof CloudviewException) {
            code = ((CloudviewException) e).code;
            params = ((CloudviewException) e).params;
        }
    }

    public CloudviewException(CloudviewExceptionCode code) {
        super(getString(code));
        this.code = code;
    }

    public CloudviewException(CloudviewExceptionCode code, String... params) {
        super(getString(code, params));
        this.code = code;
        this.params = params;
    }

    private static String getString(CloudviewExceptionCode code) {
        return code.getDesc() != null ? code.getDesc() : "Cloudview Unknonwn Exception";
    }

    private static String getString(CloudviewExceptionCode code, String... params) {
        return new StringBuilder(code.getCode()).append("|")
                .append(code.getDesc() != null ? code.getDesc() : "Cloudview Unknonwn Exception").append("|")
                .append(Arrays.asList(params).toString()).toString();
    }

    public CloudviewExceptionCode getCode() {
        return code;
    }

    public void setCode(CloudviewExceptionCode code) {
        this.code = code;
    }

    public List<String> getParams() {
        if (this.params != null) {
            return new ArrayList<String>(Arrays.asList(this.params));
        } else {
            return new ArrayList<String>();
        }
    }

    public String[] getParamsArrays() {
        return this.params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

}
