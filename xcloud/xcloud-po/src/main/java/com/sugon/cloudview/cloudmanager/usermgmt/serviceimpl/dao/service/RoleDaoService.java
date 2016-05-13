package com.sugon.cloudview.cloudmanager.usermgmt.serviceimpl.dao.service;

import java.util.List;

import com.sugon.cloudview.cloudmanager.usermgmt.serviceimpl.dao.entity.RoleE;

public interface RoleDaoService {

    public List<RoleE> findAllRole();

    public RoleE findRole(String id);
}
