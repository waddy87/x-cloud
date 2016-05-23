package org.waddys.xcloud.res.service.service.venv;

import org.waddys.xcloud.res.bo.venv.CloudvmNetPool;
import org.waddys.xcloud.res.exception.venv.VenvException;
public interface CloudvmNetPoolService {
    public CloudvmNetPool addCloudvmNetPool(CloudvmNetPool cloudvmNetPool)throws VenvException;

}
