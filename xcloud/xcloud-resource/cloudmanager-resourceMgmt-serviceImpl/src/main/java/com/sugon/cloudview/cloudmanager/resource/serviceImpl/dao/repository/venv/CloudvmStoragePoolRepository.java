package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.repository.venv;

import org.springframework.data.repository.CrudRepository;

import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.venv.CloudvmStoragePoolE;

public interface CloudvmStoragePoolRepository extends
        CrudRepository<CloudvmStoragePoolE, Long> {

}
