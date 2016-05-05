package com.sugon.cloudview.cloudmanager.resource.service.service.vdc;

import java.util.List;

import com.sugon.cloudview.cloudmanager.resource.service.bo.vdc.StoragePool;
import com.sugon.cloudview.cloudmanager.resource.service.exception.vdc.VDCException;

public interface StoragePoolService {
    public StoragePool save(StoragePool storagePool) throws VDCException;

    public void deleteByConfigId(String configId) throws VDCException;

    public StoragePool findStoragePool(String storagePoolId)
            throws VDCException;

    public List<StoragePool> findAllStoragePools() throws VDCException;
}
