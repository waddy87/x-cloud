package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.service.venv;

import java.io.Serializable;

import org.springframework.util.Assert;


public class VenvConfigSearchCriteria implements Serializable {

    /**
     * 功能：虚拟机环境记录查询
     */
    private static final long serialVersionUID = 1L;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VenvConfigSearchCriteria() {
    }

    public VenvConfigSearchCriteria(String name) {
        Assert.notNull(name, "this name is not null");
        this.name = name;
    }
}
