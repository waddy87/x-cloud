package org.waddys.xcloud.res.po.dao.venv;

import org.waddys.xcloud.res.po.entity.venv.ResourcePoolE;

public interface ResourcePoolDaoService {

    ResourcePoolE addResourcePool(ResourcePoolE resourcePoolE);

    ResourcePoolE findByrpId(String rpId);
}
