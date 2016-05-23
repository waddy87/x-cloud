package org.waddys.xcloud.user.po.dao;

import java.util.List;

import org.waddys.xcloud.user.po.entity.RoleE;

public interface RoleDaoService {

    public List<RoleE> findAllRole();

    public RoleE findRole(String id);
}
