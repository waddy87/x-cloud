package com.sugon.cloudview.cloudmanager.usermgmt.serviceimpl.impl;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sugon.cloudview.cloudmanager.usermgmt.service.bo.Role;
import com.sugon.cloudview.cloudmanager.usermgmt.service.exception.UserMgmtException;
import com.sugon.cloudview.cloudmanager.usermgmt.service.service.RoleService;
import com.sugon.cloudview.cloudmanager.usermgmt.serviceimpl.dao.entity.RoleE;
import com.sugon.cloudview.cloudmanager.usermgmt.serviceimpl.dao.service.RoleDaoService;

@Service("roleServiceImpl")
public class RoleServiceImpl implements RoleService {
    private static Logger logger = LoggerFactory
            .getLogger(RoleServiceImpl.class);
    @Autowired
    private RoleDaoService roleDaoServiceImpl;

    @Override
    public List<Role> findAllRoles() throws UserMgmtException {
        List<Role> roleList = new ArrayList<Role>();
        Mapper mapper = new DozerBeanMapper();
        try {
            List<RoleE> roleEList = roleDaoServiceImpl.findAllRole();
            for (RoleE roleE : roleEList) {
                Role roleTmp = new Role();
                roleTmp = mapper.map(roleE, Role.class);
                roleList.add(roleTmp);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserMgmtException("查询全部角色失败！");
        }
        return roleList;
    }

}
