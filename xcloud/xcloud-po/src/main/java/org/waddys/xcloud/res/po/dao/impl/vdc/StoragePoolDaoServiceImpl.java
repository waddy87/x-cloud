package org.waddys.xcloud.res.po.dao.impl.vdc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.waddys.xcloud.res.po.dao.repository.vdc.StoragePoolRepository;
import org.waddys.xcloud.res.po.dao.vdc.StoragePoolDaoService;
import org.waddys.xcloud.res.po.entity.vdc.StoragePoolE;

/**
 * 计算池dao层接口实现
 * 
 * @author sun
 *
 */
@Component("storagePoolDaoServiceImpl")
@Transactional
public class StoragePoolDaoServiceImpl implements StoragePoolDaoService {
    @Autowired
    private StoragePoolRepository storagePoolRepository;

    @Override
    public StoragePoolE save(StoragePoolE storagePool) {
        return storagePoolRepository.save(storagePool);
    }

    @Override
    public void deleteByConfigId(String configId) {
        storagePoolRepository.deleteByConfigId(configId);
    }

    @Override
    public StoragePoolE findStoragePool(String id) {
        return storagePoolRepository.findOne(id);
    }

    @Override
    public List<StoragePoolE> findAllStoragePools() {
        return storagePoolRepository.findAll();
    }

}
