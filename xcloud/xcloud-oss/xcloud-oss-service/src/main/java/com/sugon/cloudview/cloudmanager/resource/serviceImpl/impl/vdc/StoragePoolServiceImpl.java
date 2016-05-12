package com.sugon.cloudview.cloudmanager.resource.serviceImpl.impl.vdc;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sugon.cloudview.cloudmanager.resource.service.bo.vdc.StoragePool;
import com.sugon.cloudview.cloudmanager.resource.service.exception.vdc.VDCException;
import com.sugon.cloudview.cloudmanager.resource.service.service.vdc.StoragePoolService;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.vdc.StoragePoolE;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.service.vdc.StoragePoolDaoService;

@Service("storagePoolServiceImpl")
public class StoragePoolServiceImpl implements StoragePoolService {
    private static Logger logger = LoggerFactory
            .getLogger(StoragePoolServiceImpl.class);
    @Autowired
    private StoragePoolDaoService storagePoolDaoServiceImpl;

    @Override
    public void deleteByConfigId(String configId) throws VDCException {
        try {
            storagePoolDaoServiceImpl.deleteByConfigId(configId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("根据配置信息id删除存储池失败！");
        }
    }

    @Override
    public StoragePool findStoragePool(String storagePoolId)
            throws VDCException {
        StoragePool storagePool = new StoragePool();
        StoragePoolE storagePoolE = new StoragePoolE();
        try {
            storagePoolE = storagePoolDaoServiceImpl
                    .findStoragePool(storagePoolId);
            if (null != storagePoolE) {
                BeanUtils.copyProperties(storagePoolE, storagePool);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("查询存储池失败！");
        }
        return storagePool;
    }

    @Override
    public List<StoragePool> findAllStoragePools() throws VDCException {
        List<StoragePool> storagePoolList = new ArrayList<StoragePool>();
        try {
            List<StoragePoolE> storagePoolEList = storagePoolDaoServiceImpl
                    .findAllStoragePools();
            for (StoragePoolE storagePoolE : storagePoolEList) {
                StoragePool storagePoolTmp = new StoragePool();
                BeanUtils.copyProperties(storagePoolE, storagePoolTmp);
                storagePoolList.add(storagePoolTmp);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("查询全部存储池失败！");
        }

        return storagePoolList;
    }

    @Override
    public StoragePool save(StoragePool storagePool) throws VDCException {
        StoragePool storagePoolTmp = new StoragePool();
        try {
            StoragePoolE storagePoolE = new StoragePoolE();
            BeanUtils.copyProperties(storagePool, storagePoolE);
            storagePoolE = storagePoolDaoServiceImpl.save(storagePoolE);
            BeanUtils.copyProperties(storagePoolE, storagePoolTmp);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("保存存储池失败！");
        }
        return storagePoolTmp;
    }
}
