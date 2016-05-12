package com.sugon.cloudview.cloudmanager.resource.service.service.venv;

import com.sugon.cloudview.cloudmanager.resource.service.bo.venv.CloudvmNetPool;
import com.sugon.cloudview.cloudmanager.resource.service.exception.venv.VenvException;
public interface CloudvmNetPoolService {
    public CloudvmNetPool addCloudvmNetPool(CloudvmNetPool cloudvmNetPool)throws VenvException;

}
