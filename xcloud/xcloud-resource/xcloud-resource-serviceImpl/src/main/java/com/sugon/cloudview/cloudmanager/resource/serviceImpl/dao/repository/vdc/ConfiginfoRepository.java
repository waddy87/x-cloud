package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.repository.vdc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.vdc.ConfigInfoE;

public interface ConfiginfoRepository extends CrudRepository<ConfigInfoE, Long> {
    Page<ConfigInfoE> findAll(Pageable pageable);

    @Override
    List<ConfigInfoE> findAll();

    ConfigInfoE findByConfigId(String configId);


}
