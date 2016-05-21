package org.waddys.xcloud.resource.service.service.venv;

import org.waddys.xcloud.resource.service.bo.venv.CloudvmStoragePool;
import org.waddys.xcloud.resource.service.exception.venv.VenvException;

public interface CloudvmStoragePoolService {
    public CloudvmStoragePool addCloudvmStoragePool(CloudvmStoragePool cloudvmStoragePool) throws VenvException;

}
