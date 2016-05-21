package org.waddys.xcloud.usermgmt.serviceimpl.dao.repository;

import java.util.List;

import org.springframework.data.repository.Repository;
import org.waddys.xcloud.usermgmt.serviceimpl.dao.entity.ResourceE;

public interface ResourceRepository extends Repository<ResourceE, String> {

    ResourceE save(ResourceE resourceE);

    List<ResourceE> findAll();

    ResourceE findOne(String id);

    void delete(String id);
}
