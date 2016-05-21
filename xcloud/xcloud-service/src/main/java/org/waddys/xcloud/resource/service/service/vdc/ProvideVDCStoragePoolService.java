package org.waddys.xcloud.resource.service.service.vdc;

import org.waddys.xcloud.resource.service.bo.vdc.ProvideVDCStoragePool;
import org.waddys.xcloud.resource.service.exception.vdc.VDCException;

public interface ProvideVDCStoragePoolService {
    public ProvideVDCStoragePool findByPVDCIdAndSpId(String pVDCId, String spId)
            throws VDCException;

    public void expenseProvideVDCStoragePool(String pVDCStoragePoolId,
            Long storageSize) throws VDCException;

    public void recycleProvideVDCStoragePool(String pVDCStoragePoolId,
            Long storageSize) throws VDCException;

}
