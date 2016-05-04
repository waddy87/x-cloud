package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.service.vdc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.vdc.ConfigInfoE;

public interface ConfigInfoDaoService {

    public ConfigInfoE save(ConfigInfoE configInfoE);

    public Page<ConfigInfoE> findConfigInfos(Pageable pageable);

    public List<ConfigInfoE> findConfigInfos();

    public ConfigInfoE findByconfigId(String configId);

    public void updateConfigInfo(String configName, String configId);

    public void updateConfigInfo(ConfigInfoE configInfoE);

    public void delConfigInfo(ConfigInfoE configInfoE);

}
