package org.waddys.xcloud.resource.service.service.venv;

import org.waddys.xcloud.resource.service.bo.venv.ResourcePool;
import org.waddys.xcloud.resource.service.exception.venv.VenvException;

public interface ResourcePoolService {

    public ResourcePool addResourcePool(ResourcePool resourcePool, String venvConfigId, String clusterId)
            throws VenvException;

    public ResourcePool findByrpId(String rpId) throws VenvException;

}
