package org.waddys.xcloud.user.service.service;

import java.util.List;

import org.waddys.xcloud.user.bo.Role;
import org.waddys.xcloud.user.exception.UserMgmtException;

public interface RoleService {

    public List<Role> findAllRoles() throws UserMgmtException;
}
