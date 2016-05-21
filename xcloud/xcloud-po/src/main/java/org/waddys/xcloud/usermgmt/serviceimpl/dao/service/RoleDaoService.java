package org.waddys.xcloud.usermgmt.serviceimpl.dao.service;

import java.util.List;

import org.waddys.xcloud.usermgmt.serviceimpl.dao.entity.RoleE;

public interface RoleDaoService {

    public List<RoleE> findAllRole();

    public RoleE findRole(String id);
}
