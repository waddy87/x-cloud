package org.waddys.xcloud.res.po.dao.impl.venv;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.waddys.xcloud.res.po.dao.repository.venv.HostRepository;
import org.waddys.xcloud.res.po.dao.venv.HostDaoService;
import org.waddys.xcloud.res.po.entity.venv.HostE;

@Component("hostDaoServiceImpl")
@Transactional
public class HostDaoServiceImpl implements HostDaoService {

    @Autowired
    private HostRepository hostRepository;

    @Override
    public HostE findByhostId(String hostId) {
        return hostRepository.findByhostId(hostId);
    }

    @Override
    public HostE addHost(HostE hostE) {
        return hostRepository.save(hostE);
    }

}
