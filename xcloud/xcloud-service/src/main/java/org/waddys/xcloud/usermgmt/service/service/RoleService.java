package org.waddys.xcloud.usermgmt.service.service;

import java.util.List;

import org.waddys.xcloud.usermgmt.service.bo.Role;
import org.waddys.xcloud.usermgmt.service.exception.UserMgmtException;

public interface RoleService {

    public List<Role> findAllRoles() throws UserMgmtException;
}
