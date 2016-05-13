package com.sugon.cloudview.cloudmanager.usermgmt.serviceimpl.dao.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.sugon.cloudview.cloudmanager.usermgmt.serviceimpl.dao.entity.RoleE;

public interface RoleRepository extends Repository<RoleE, String> {

    RoleE save(RoleE roleE);

    List<RoleE> findAll();

    RoleE findOne(String id);

    void delete(String id);
}
