package org.waddys.xcloud.res.po.dao.impl.vdc;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.waddys.xcloud.res.po.dao.repository.vdc.ConfiginfoRepository;
import org.waddys.xcloud.res.po.dao.vdc.ConfigInfoDaoService;
import org.waddys.xcloud.res.po.entity.vdc.ConfigInfoE;

@Component("configInfoDaoServiceImpl")
@Transactional
public class ConfigInfoDaoServiceImpl implements ConfigInfoDaoService {

    @Autowired
    private ConfiginfoRepository configinfoRepository;
    @Override
    public ConfigInfoE save(ConfigInfoE configInfoE) {
        return configinfoRepository.save(configInfoE);
    }

    @Override
    public Page<ConfigInfoE> findConfigInfos(Pageable pageable) {
        return configinfoRepository.findAll(pageable);
    }

    @Override
    public List<ConfigInfoE> findConfigInfos() {
        return configinfoRepository.findAll();
    }

    @Override
    public ConfigInfoE findByconfigId(String configId) {
        return configinfoRepository.findByConfigId(configId);
    }

    @Override
    public void updateConfigInfo(String configName, String configId) {
    }

    @Override
    public void updateConfigInfo(ConfigInfoE configInfoE) {
        configinfoRepository.save(configInfoE);
    }

    @Override
    public void delConfigInfo(ConfigInfoE configInfoE) {
        configinfoRepository.delete(configInfoE);
        ;
    }

}
