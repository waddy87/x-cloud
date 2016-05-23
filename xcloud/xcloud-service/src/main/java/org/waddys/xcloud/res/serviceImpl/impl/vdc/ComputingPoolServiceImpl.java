package org.waddys.xcloud.res.serviceImpl.impl.vdc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.waddys.xcloud.res.bo.vdc.ComputingPool;
import org.waddys.xcloud.res.bo.vdc.StoragePool;
import org.waddys.xcloud.res.exception.vdc.VDCException;
import org.waddys.xcloud.res.po.dao.vdc.ComputingPoolDaoService;
import org.waddys.xcloud.res.po.dao.vdc.StoragePoolDaoService;
import org.waddys.xcloud.res.po.entity.vdc.ComputingPoolE;
import org.waddys.xcloud.res.po.entity.vdc.StoragePoolE;
import org.waddys.xcloud.res.service.service.vdc.ComputingPoolService;

@Service("computingPoolServiceImpl")
public class ComputingPoolServiceImpl implements ComputingPoolService {
    private static Logger logger = LoggerFactory
            .getLogger(ComputingPoolServiceImpl.class);
    @Autowired
    private ComputingPoolDaoService computingPoolDaoServiceImpl;

    @Autowired
    private StoragePoolDaoService storagePoolDaoServiceImpl;

    @Override
    public List<StoragePool> findStoragePools(String cpId) throws VDCException {
        List<StoragePool> storagePoolList = new ArrayList<StoragePool>();
        try {
            ComputingPoolE computingPoolE = computingPoolDaoServiceImpl
                    .findComputingPool(cpId);
            if (computingPoolE != null) {
                Set<StoragePoolE> computingPoolSet = computingPoolE
                        .getStoragePool();
                for (StoragePoolE storagePoolE : computingPoolSet) {
                    StoragePool storagePool = new StoragePool();
                    BeanUtils.copyProperties(storagePoolE, storagePool);
                    storagePoolList.add(storagePool);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("根据计算池查询存储池失败！");
        }

        return storagePoolList;
    }

    @Override
    public List<ComputingPool> findAllComputingPools() throws VDCException {
        List<ComputingPool> computingPoolListE = new ArrayList<ComputingPool>();
        try {
            List<ComputingPoolE> computingPoolListPo = computingPoolDaoServiceImpl
                    .findAllComputingPools();
            for (ComputingPoolE computingPoolE : computingPoolListPo) {
                ComputingPool computingPool = new ComputingPool();
                BeanUtils.copyProperties(computingPoolE, computingPool);
                computingPoolListE.add(computingPool);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("查询计算池失败！");
        }

        return computingPoolListE;
    }

    @Override
    public ComputingPool save(ComputingPool computingPool) throws VDCException {
        ComputingPoolE computingPoolE = new ComputingPoolE();
        Set<StoragePoolE> storagePoolESet = new HashSet<StoragePoolE>();
        try {
            BeanUtils.copyProperties(computingPool, computingPoolE);
            List<StoragePool> storagePoolList = computingPool.getStoragePools();
            for (StoragePool storagePool : storagePoolList) {
                StoragePoolE storagePoolE = new StoragePoolE();
                BeanUtils.copyProperties(storagePool, storagePoolE);
                storagePoolESet.add(storagePoolE);
            }
            computingPoolE.setStoragePool(storagePoolESet);
            computingPoolE = computingPoolDaoServiceImpl.save(computingPoolE);
            BeanUtils.copyProperties(computingPoolE, computingPool);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("保存计算池失败！");
        }
        return computingPool;
    }

    @Override
    public ComputingPool findComputingPool(String computingPoolId)
            throws VDCException {
        ComputingPoolE computingPoolE = new ComputingPoolE();
        ComputingPool computingPool = new ComputingPool();
        try {
            computingPoolE = computingPoolDaoServiceImpl
                    .findComputingPool(computingPoolId);
            if (computingPoolE != null) {
                BeanUtils.copyProperties(computingPoolE, computingPool);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("查询存储池详情失败！");
        }

        return computingPool;
    }

    @Override
    public void delete(String computingPoolId) throws VDCException {
        try {
            computingPoolDaoServiceImpl.delete(computingPoolId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("删除计算池失败！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByConfigId(String configId) throws VDCException {
        try {
            computingPoolDaoServiceImpl.deleteByConfigId(configId);
            storagePoolDaoServiceImpl.deleteByConfigId(configId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("根据配置信息id删除计算池失败！");
        }
    }

    @Override
    public List<ComputingPool> findByIsDistribute(Boolean isDistribute)
            throws VDCException {
        List<ComputingPool> cpList = new ArrayList<ComputingPool>();
        try {
            List<ComputingPoolE> cpeList = computingPoolDaoServiceImpl
                    .findByIsDistribute(isDistribute);
            for (ComputingPoolE computingPoolE : cpeList) {
                ComputingPool computingPool = new ComputingPool();
                BeanUtils.copyProperties(computingPoolE, computingPool);
                cpList.add(computingPool);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("根据已分配状态查询计算池信息失败！");
        }
        return cpList;
    }

    @Override
    public void update(ComputingPool computingPool) throws VDCException {
        ComputingPoolE computingPoolE = new ComputingPoolE();
        try {
            BeanUtils.copyProperties(computingPool, computingPoolE);
            computingPoolDaoServiceImpl.update(computingPoolE);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("更新计算池信息失败！");
        }

    }

    @Override
    public List<ComputingPool> findByIsAvl(Boolean isAvl) throws VDCException {
        List<ComputingPool> cpList = new ArrayList<ComputingPool>();
        try {
            List<ComputingPoolE> cpeList = computingPoolDaoServiceImpl
                    .findByIsAvl(isAvl);
            for (ComputingPoolE computingPoolE : cpeList) {
                ComputingPool computingPool = new ComputingPool();
                BeanUtils.copyProperties(computingPoolE, computingPool);
                cpList.add(computingPool);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("根据是否可用状态查询计算池信息失败！");
        }
        return cpList;
    }

    @Override
    public List<ComputingPool> findAllComputingPools(ComputingPool computingPool)
            throws VDCException {
        List<ComputingPool> cpList = new ArrayList<ComputingPool>();
        try {
            ComputingPoolE computingPoolE = new ComputingPoolE();
            BeanUtils.copyProperties(computingPool, computingPoolE);
            List<ComputingPoolE> cpeList = computingPoolDaoServiceImpl
                    .findAllComputingPools(computingPoolE);
            for (ComputingPoolE computingPoolETmp : cpeList) {
                ComputingPool computingPoolTmp = new ComputingPool();
                BeanUtils.copyProperties(computingPoolETmp, computingPoolTmp);
                cpList.add(computingPoolTmp);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("根据状态查询计算池信息失败！");
        }
        return cpList;
    }

}
