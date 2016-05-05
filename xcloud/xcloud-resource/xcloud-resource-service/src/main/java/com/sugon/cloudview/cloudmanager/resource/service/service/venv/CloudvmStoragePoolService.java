package com.sugon.cloudview.cloudmanager.resource.service.service.venv;

import com.sugon.cloudview.cloudmanager.resource.service.bo.venv.CloudvmStoragePool;
import com.sugon.cloudview.cloudmanager.resource.service.exception.venv.VenvException;

public interface CloudvmStoragePoolService {
    public CloudvmStoragePool addCloudvmStoragePool(CloudvmStoragePool cloudvmStoragePool) throws VenvException;

}
