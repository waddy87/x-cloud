package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.repository.venv;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.venv.VenvConfigE;

public interface VenvConfigRepository extends CrudRepository<VenvConfigE, Long> {

    @Override
    List<VenvConfigE> findAll();

    VenvConfigE findByconfigId(String configId);

    Page<VenvConfigE> findAll(Pageable pageable);

    Page<VenvConfigE> findByconfigNameContaining(String configName,
            Pageable pageable);
}
