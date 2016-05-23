package org.waddys.xcloud.res.service.service.venv;

import org.waddys.xcloud.res.bo.venv.ResourcePool;
import org.waddys.xcloud.res.exception.venv.VenvException;

public interface ResourcePoolService {

    public ResourcePool addResourcePool(ResourcePool resourcePool, String venvConfigId, String clusterId)
            throws VenvException;

    public ResourcePool findByrpId(String rpId) throws VenvException;

}
