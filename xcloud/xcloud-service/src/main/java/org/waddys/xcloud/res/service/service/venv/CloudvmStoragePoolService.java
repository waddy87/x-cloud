package org.waddys.xcloud.res.service.service.venv;

import org.waddys.xcloud.res.bo.venv.CloudvmStoragePool;
import org.waddys.xcloud.res.exception.venv.VenvException;

public interface CloudvmStoragePoolService {
    public CloudvmStoragePool addCloudvmStoragePool(CloudvmStoragePool cloudvmStoragePool) throws VenvException;

}
