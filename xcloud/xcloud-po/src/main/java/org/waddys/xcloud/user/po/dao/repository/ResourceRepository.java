package org.waddys.xcloud.user.po.dao.repository;

import java.util.List;

import org.springframework.data.repository.Repository;
import org.waddys.xcloud.user.po.entity.ResourceE;

public interface ResourceRepository extends Repository<ResourceE, String> {

    ResourceE save(ResourceE resourceE);

    List<ResourceE> findAll();

    ResourceE findOne(String id);

    void delete(String id);
}
