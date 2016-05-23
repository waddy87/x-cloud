package org.waddys.xcloud.res.po.dao.impl.venv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.waddys.xcloud.res.po.dao.repository.venv.VenvConfigRepository;
import org.waddys.xcloud.res.po.dao.venv.VenvConfigDaoService;
import org.waddys.xcloud.res.po.entity.venv.VenvConfigE;

@Component("venvDaoServiceImpl")
@Transactional
public class VenvConfigDaoServiceImpl implements VenvConfigDaoService {

    @Autowired
    private VenvConfigRepository venvconfigRepository;

    @Override
    public List<VenvConfigE> findConfigInfos() {
        return venvconfigRepository.findAll();
    }

    @Override
    public Page<VenvConfigE> findVenvConfigs(Pageable pageable) {
        return venvconfigRepository.findAll(pageable);
    }

    @Override
    public Page<VenvConfigE> findVenvConfigs(VenvConfigE venvConfigE,
            Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public VenvConfigE addVenvConfig(VenvConfigE venvConfigE) {
        return this.venvconfigRepository.save(venvConfigE);
    }

    @Override
    public void delVenvConfig(VenvConfigE venvConfigE) {
        this.venvconfigRepository.delete(venvConfigE);
    }

    @Override
    public void updateVenvConfig(VenvConfigE venvConfigE) {
        this.venvconfigRepository.save(venvConfigE);
    }

    @Override
    public VenvConfigE findVenvConfigById(String venvConfigId) {
        // TODO Auto-generated method stub
        return this.venvconfigRepository.findByconfigId(venvConfigId);
    }

    @Override
    public Page<VenvConfigE> findByconfigNameContaining(String name,
            Pageable pageable) {

        return this.venvconfigRepository.findByconfigNameContaining(name,
                pageable);
    }



}
