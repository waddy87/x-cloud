package org.waddys.xcloud.res.po.dao.vdc;

import java.util.List;

import org.waddys.xcloud.res.po.entity.vdc.StoragePoolE;

/**
 * 存储池dao层接口
 * 
 * @author sun
 *
 */
public interface StoragePoolDaoService {
    /**
     * 创建
     * 
     * @param storagePool
     * @return
     */
    public StoragePoolE save(StoragePoolE storagePool);

    public void deleteByConfigId(String configId);

    public StoragePoolE findStoragePool(String id);

    public List<StoragePoolE> findAllStoragePools();
}
