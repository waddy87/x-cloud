package org.waddys.xcloud.res.po.dao.impl.venv;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.waddys.xcloud.res.po.dao.repository.venv.ResourcePoolRepository;
import org.waddys.xcloud.res.po.dao.venv.ResourcePoolDaoService;
import org.waddys.xcloud.res.po.entity.venv.ResourcePoolE;

@Component("resourcePoolDaoServiceImpl")
@Transactional
public class ResourcePoolDaoServiceImpl implements ResourcePoolDaoService {

    @Autowired
    private ResourcePoolRepository resourcePoolRepository;

    @Override
    public ResourcePoolE addResourcePool(ResourcePoolE resourcePoolE) {
        return resourcePoolRepository.save(resourcePoolE);
    }

    @Override
    public ResourcePoolE findByrpId(String rpId) {
        return resourcePoolRepository.findByrpId(rpId);
    }

}
