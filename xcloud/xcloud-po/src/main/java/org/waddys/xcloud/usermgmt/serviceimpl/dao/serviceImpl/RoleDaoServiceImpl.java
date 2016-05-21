package org.waddys.xcloud.usermgmt.serviceimpl.dao.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.waddys.xcloud.usermgmt.serviceimpl.dao.entity.RoleE;
import org.waddys.xcloud.usermgmt.serviceimpl.dao.repository.RoleRepository;
import org.waddys.xcloud.usermgmt.serviceimpl.dao.service.RoleDaoService;

@Component("roleDaoServiceImpl")
@Transactional
public class RoleDaoServiceImpl implements RoleDaoService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<RoleE> findAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public RoleE findRole(String id) {
        return roleRepository.findOne(id);
    }

}
