package com.sugon.cloudview.cloudmanager.resource.service.service.venv;

import com.sugon.cloudview.cloudmanager.resource.service.bo.venv.ResourcePool;
import com.sugon.cloudview.cloudmanager.resource.service.exception.venv.VenvException;

public interface ResourcePoolService {

    public ResourcePool addResourcePool(ResourcePool resourcePool, String venvConfigId, String clusterId)
            throws VenvException;

    public ResourcePool findByrpId(String rpId) throws VenvException;

}
