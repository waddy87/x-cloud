package org.waddys.xcloud.resource.service.service.venv;

import org.waddys.xcloud.resource.service.bo.venv.CloudvmNetPool;
import org.waddys.xcloud.resource.service.exception.venv.VenvException;
public interface CloudvmNetPoolService {
    public CloudvmNetPool addCloudvmNetPool(CloudvmNetPool cloudvmNetPool)throws VenvException;

}
