package com.sugon.cloudview.cloudmanager.usermgmt.service.service;

import java.util.List;

import com.sugon.cloudview.cloudmanager.usermgmt.service.bo.Role;
import com.sugon.cloudview.cloudmanager.usermgmt.service.exception.UserMgmtException;

public interface RoleService {

    public List<Role> findAllRoles() throws UserMgmtException;
}
