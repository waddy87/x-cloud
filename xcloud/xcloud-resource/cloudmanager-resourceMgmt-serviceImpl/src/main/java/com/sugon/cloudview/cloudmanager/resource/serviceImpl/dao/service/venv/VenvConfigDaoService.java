package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.service.venv;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.venv.VenvConfigE;

public interface VenvConfigDaoService {
    public List<VenvConfigE> findConfigInfos();

    Page<VenvConfigE> findVenvConfigs(Pageable pageable);

    Page<VenvConfigE> findVenvConfigs(VenvConfigE venvConfigE, Pageable pageable);

    Page<VenvConfigE> findByconfigNameContaining(String name, Pageable pageable);

    VenvConfigE findVenvConfigById(String venvConfigId);

    VenvConfigE addVenvConfig(VenvConfigE venvConfigE);

    void delVenvConfig(VenvConfigE venvConfigE);

    void updateVenvConfig(VenvConfigE venvConfigE);
}
