package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.serviceImpl.vdc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.vdc.StoragePoolE;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.repository.vdc.StoragePoolRepository;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.service.vdc.StoragePoolDaoService;

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
