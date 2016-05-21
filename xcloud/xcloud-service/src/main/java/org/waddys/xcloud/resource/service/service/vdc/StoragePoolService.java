package org.waddys.xcloud.resource.service.service.vdc;

import java.util.List;

import org.waddys.xcloud.resource.service.bo.vdc.StoragePool;
import org.waddys.xcloud.resource.service.exception.vdc.VDCException;

public interface StoragePoolService {
    public StoragePool save(StoragePool storagePool) throws VDCException;

    public void deleteByConfigId(String configId) throws VDCException;

    public StoragePool findStoragePool(String storagePoolId)
            throws VDCException;

    public List<StoragePool> findAllStoragePools() throws VDCException;
}
