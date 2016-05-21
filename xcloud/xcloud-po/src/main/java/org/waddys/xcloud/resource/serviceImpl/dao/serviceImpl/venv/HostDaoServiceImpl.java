package org.waddys.xcloud.resource.serviceImpl.dao.serviceImpl.venv;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.waddys.xcloud.resource.serviceImpl.dao.entity.venv.HostE;
import org.waddys.xcloud.resource.serviceImpl.dao.repository.venv.HostRepository;
import org.waddys.xcloud.resource.serviceImpl.dao.service.venv.HostDaoService;

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
