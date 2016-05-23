package org.waddys.xcloud.user.po.dao.repository;

import java.util.List;

import org.springframework.data.repository.Repository;
import org.waddys.xcloud.user.po.entity.RoleE;

public interface RoleRepository extends Repository<RoleE, String> {

    RoleE save(RoleE roleE);

    List<RoleE> findAll();

    RoleE findOne(String id);

    void delete(String id);
}
