package com.sugon.cloudview.cloudmanager.resource.serviceImpl.impl.vdc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.hibernate.StaleObjectStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sugon.cloudview.cloudmanager.resource.service.bo.vdc.ProvideVDCStoragePool;
import com.sugon.cloudview.cloudmanager.resource.service.bo.vdc.ProviderVDC;
import com.sugon.cloudview.cloudmanager.resource.service.exception.vdc.VDCException;
import com.sugon.cloudview.cloudmanager.resource.service.service.vdc.ProviderVDCService;
import com.sugon.cloudview.cloudmanager.resource.service.service.vdc.StoragePoolService;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.vdc.ProvideVDCStoragePoolE;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.vdc.ProviderVDCE;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.service.vdc.ProvideVDCStoragePoolDaoService;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.service.vdc.ProviderVDCDaoService;

@Service("providerVDCServiceImpl")
public class ProviderVDCServiceImpl implements ProviderVDCService {
    private static Logger logger = LoggerFactory
            .getLogger(ProviderVDCServiceImpl.class);
    @Autowired
    private StoragePoolService StoragePoolServiceImpl;
    @Autowired
    private ProvideVDCStoragePoolDaoService provideVDCStoragePoolDaoServiceImpl;

    @Autowired
    private ProviderVDCDaoService providerVDCDaoServiceImpl;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ProviderVDC providerVDC) throws VDCException {
        ProviderVDCE providerVDCE = new ProviderVDCE();
        try {
            BeanUtils.copyProperties(providerVDC, providerVDCE);
            // 保存providerVDC
            providerVDCE = providerVDCDaoServiceImpl.save(providerVDCE);
            List<ProvideVDCStoragePool> provideVDCStoragePoolList = providerVDC
                    .getProvideVDCStoragePool();
            for (ProvideVDCStoragePool provideVDCStoragePool : provideVDCStoragePoolList) {
                ProvideVDCStoragePoolE provideVDCStoragePoolE = new ProvideVDCStoragePoolE();
                provideVDCStoragePoolE.setpVDCId(providerVDCE.getpVDCId());
                provideVDCStoragePoolE.setSpId(provideVDCStoragePool.getSpId());
                provideVDCStoragePoolE.setSpName(provideVDCStoragePool
                        .getSpName());
                provideVDCStoragePoolE.setSpSurplus(provideVDCStoragePool
                        .getSpSurplus());
                provideVDCStoragePoolE.setSpTotal(provideVDCStoragePool
                        .getSpTotal());
                provideVDCStoragePoolE.setSpUsed(provideVDCStoragePool
                        .getSpUsed());
                // 保存provideVDCStoragePoolE
                provideVDCStoragePoolDaoServiceImpl
                        .save(provideVDCStoragePoolE);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("保存提供者VDC失败！");
        }
    }

    @Override
    public ProviderVDC findProviderVDC(String providerVDCId)
            throws VDCException {
        ProviderVDCE providerVDCE = new ProviderVDCE();
        ProviderVDC providerVDC = new ProviderVDC();
        try {
            providerVDCE = providerVDCDaoServiceImpl
                    .findProviderVDC(providerVDCId);
            if (null != providerVDCE) {
                BeanUtils.copyProperties(providerVDCE, providerVDC);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("查询提供者VDC失败！");
        }

        return providerVDC;
    }

    @Override
    public List<ProvideVDCStoragePool> findStoragePools(String providerVDCId)
            throws VDCException {
        List<ProvideVDCStoragePool> provideVDCStoragePoolList = new ArrayList<ProvideVDCStoragePool>();
        try {
            List<ProvideVDCStoragePoolE> provideVDCStoragePoolEList = provideVDCStoragePoolDaoServiceImpl
                    .findByPVDCId(providerVDCId);
            for (ProvideVDCStoragePoolE provideVDCStoragePoolE : provideVDCStoragePoolEList) {
                ProvideVDCStoragePool provideVDCStoragePool = new ProvideVDCStoragePool();
                BeanUtils.copyProperties(provideVDCStoragePoolE,
                        provideVDCStoragePool);
                provideVDCStoragePoolList.add(provideVDCStoragePool);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("查询提供者VDC下所属存储池失败！");
        }
        return provideVDCStoragePoolList;
    }

    @Override
    public Map<String, Object> findProviderVDCs(ProviderVDC providerVDC,
            int pageNum, int pageSize) throws VDCException {
        List<ProviderVDC> providerVDCList = new ArrayList<ProviderVDC>();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Mapper mapper = new DozerBeanMapper();
            ProviderVDCE providerVDCESearch = mapper.map(providerVDC,
                    ProviderVDCE.class);
            Pageable pageable = new PageRequest(pageNum - 1, pageSize);
            Page<ProviderVDCE> providerVDCListPage = providerVDCDaoServiceImpl
                    .findProviderVDCs(providerVDCESearch, pageable);
            List<ProviderVDCE> providerVDCEList = providerVDCListPage
                    .getContent();
            for (ProviderVDCE providerVDCE : providerVDCEList) {
                ProviderVDC providerVDCTmp = new ProviderVDC();
                BeanUtils.copyProperties(providerVDCE, providerVDCTmp);
                providerVDCList.add(providerVDCTmp);
            }
            map.put("total", providerVDCListPage.getTotalElements());
            map.put("providerVDCList", providerVDCList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("分页查询提供者VDC失败！");
        }

        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProviderVDC(ProviderVDC providerVDC) throws VDCException {
        try {
            // 更新提供者vdc
            ProviderVDCE providerVDCE = providerVDCDaoServiceImpl
                    .findProviderVDC(providerVDC.getpVDCId());
            if (StringUtils.isNotBlank(providerVDC.getName())) {
                providerVDCE.setName(providerVDC.getName());
            }
            if (StringUtils.isNotBlank(providerVDC.getDescription())) {
                providerVDCE.setDescription(providerVDC.getDescription());
            }
            if (null != providerVDC.getvCpuNum()) {
                providerVDCE.setvCpuNum(providerVDC.getvCpuNum());
                providerVDCE.setvCpuOverplus(providerVDC.getvCpuNum()
                        - providerVDCE.getvCpuUsed());
            }
            if (null != providerVDC.getMemorySize()) {
                providerVDCE.setMemorySize(providerVDC.getMemorySize());
                providerVDCE.setMemoryOverplus(providerVDC.getMemorySize()
                        - providerVDCE.getMemoryUsed());
            }
            providerVDCDaoServiceImpl.updateProviderVDC(providerVDCE);

            List<ProvideVDCStoragePool> provideVDCStoragePoolList = providerVDC
                    .getProvideVDCStoragePool();
            if (provideVDCStoragePoolList != null) {
                for (ProvideVDCStoragePool provideVDCStoragePool : provideVDCStoragePoolList) {
                    ProvideVDCStoragePoolE provideVDCStoragePoolE = new ProvideVDCStoragePoolE();

                    // 更新provideVDCStoragePoolE
                    if (provideVDCStoragePool.getIsCreate()) {
                        provideVDCStoragePoolE.setpVDCId(providerVDCE
                                .getpVDCId());
                        provideVDCStoragePoolE
                                .setpVDCStoragePoolId(provideVDCStoragePool
                                        .getpVDCStoragePoolId());
                        provideVDCStoragePoolE.setSpTotal(provideVDCStoragePool
                                .getSpTotal());
                        provideVDCStoragePoolE
                                .setSpSurplus(provideVDCStoragePool
                                        .getSpTotal());
                        provideVDCStoragePoolE.setSpUsed(0l);
                        provideVDCStoragePoolE.setSpId(provideVDCStoragePool
                                .getSpId());
                        provideVDCStoragePoolE.setSpName(provideVDCStoragePool
                                .getSpName());
                        provideVDCStoragePoolDaoServiceImpl
                                .save(provideVDCStoragePoolE);
                    } else {
                        provideVDCStoragePoolE
                                .setpVDCStoragePoolId(provideVDCStoragePool
                                        .getpVDCStoragePoolId());
                        provideVDCStoragePoolE.setSpTotal(provideVDCStoragePool
                                .getSpTotal());
                        provideVDCStoragePoolE
                                .setSpSurplus(provideVDCStoragePool
                                        .getSpTotal()
                                        - provideVDCStoragePool.getSpUsed());
                        provideVDCStoragePoolDaoServiceImpl
                                .updateProvideVDCStoragePool(provideVDCStoragePoolE);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("更新提供者VDC失败！");
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String proVDCId) throws VDCException {
        try {
            providerVDCDaoServiceImpl.delete(proVDCId);
            provideVDCStoragePoolDaoServiceImpl.deleteByProVDCId(proVDCId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("删除提供者VDC失败！");
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void expenseProVDC(String proVDCId, Integer vCpuNum,
            Long memorySize, String storagePoolId, Long storageSize)
            throws VDCException {
        // 1、根据pID查询已用cpu、剩余cpu、已用内存、剩余内存、已用存储、剩余存储
        // 2、判断是否满足创建条件
        // 3、满足条件后，调用接口updateProvdc,调用接口updateProSp
        // 4、不满足条件抛出异常

        ProviderVDCE providerVDCE = providerVDCDaoServiceImpl
                .findProviderVDC(proVDCId);
        Integer vCpuOverplus = providerVDCE.getvCpuOverplus();
        Integer vCpuUsed = providerVDCE.getvCpuUsed();
        if (vCpuOverplus < vCpuNum) {
            throw new VDCException("剩余CPU数量不足！");
        }
        Long memoryOverplus = providerVDCE.getMemoryOverplus();
        Long memoryUsed = providerVDCE.getMemoryUsed();
        if (memoryOverplus < memorySize) {
            throw new VDCException("剩余内存资源不足！");
        }
        ProvideVDCStoragePoolE provideVDCStoragePoolE = provideVDCStoragePoolDaoServiceImpl
                .findByPVDCIdAndSpId(proVDCId, storagePoolId);
        Long spSurplus = provideVDCStoragePoolE.getSpSurplus();
        Long spUsed = provideVDCStoragePoolE.getSpUsed();
        if (spSurplus < storageSize) {
            throw new VDCException("存储资源不足！");
        }
        try {
            providerVDCE.setvCpuOverplus(vCpuOverplus - vCpuNum);
            providerVDCE.setvCpuUsed(vCpuUsed + vCpuNum);
            providerVDCE.setMemoryOverplus(memoryOverplus - memorySize);
            providerVDCE.setMemoryUsed(memoryUsed + memorySize);
            providerVDCE = providerVDCDaoServiceImpl.save(providerVDCE);

            provideVDCStoragePoolE.setSpSurplus(spSurplus - storageSize);
            provideVDCStoragePoolE.setSpUsed(spUsed + storageSize);
            provideVDCStoragePoolDaoServiceImpl.save(provideVDCStoragePoolE);
        } catch (StaleObjectStateException e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("资源竞争中，请稍后再试！");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("执行expenseProVDC接口失败！");
        }
    }

    @Override
    public List<ProviderVDC> findAll() throws VDCException {
        List<ProviderVDC> providerVDCList = new ArrayList<ProviderVDC>();
        try {
            List<ProviderVDCE> providerVDCEList = providerVDCDaoServiceImpl
                    .findAllProviderVDCs();
            for (ProviderVDCE providerVDCE : providerVDCEList) {
                ProviderVDC providerVDCTmp = new ProviderVDC();
                BeanUtils.copyProperties(providerVDCE, providerVDCTmp);
                providerVDCList.add(providerVDCTmp);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("查询全部提供者VDC失败！");
        }

        return providerVDCList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recycleProVDC(String proVDCId, Integer vCpuNum,
            Long memorySize, String storagePoolId, Long storageSize)
            throws VDCException {
        // 回收资源
        // 已用的减去，剩余的增加

        ProviderVDCE providerVDCE = providerVDCDaoServiceImpl
                .findProviderVDC(proVDCId);
        Integer vCpuOverplus = providerVDCE.getvCpuOverplus();
        Integer vCpuUsed = providerVDCE.getvCpuUsed();
        if (vCpuUsed < vCpuNum) {
            throw new VDCException("回收CPU数量大于已用CPU数量！");
        }
        Long memoryOverplus = providerVDCE.getMemoryOverplus();
        Long memoryUsed = providerVDCE.getMemoryUsed();
        if (memoryUsed < memorySize) {
            throw new VDCException("回收内存资源大于已用内存资源！");
        }
        ProvideVDCStoragePoolE provideVDCStoragePoolE = provideVDCStoragePoolDaoServiceImpl
                .findByPVDCIdAndSpId(proVDCId, storagePoolId);
        Long spSurplus = provideVDCStoragePoolE.getSpSurplus();
        Long spUsed = provideVDCStoragePoolE.getSpUsed();
        if (spUsed < storageSize) {
            throw new VDCException("回收存储资源大于已用存储资源！");
        }
        try {
            providerVDCE.setvCpuOverplus(vCpuOverplus + vCpuNum);
            providerVDCE.setvCpuUsed(vCpuUsed - vCpuNum);
            providerVDCE.setMemoryOverplus(memoryOverplus + memorySize);
            providerVDCE.setMemoryUsed(memoryUsed - memorySize);
            providerVDCE = providerVDCDaoServiceImpl.save(providerVDCE);

            provideVDCStoragePoolE.setSpSurplus(spSurplus + storageSize);
            provideVDCStoragePoolE.setSpUsed(spUsed - storageSize);
            provideVDCStoragePoolDaoServiceImpl.save(provideVDCStoragePoolE);
        } catch (StaleObjectStateException e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("资源竞争中，请稍后再试！");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("执行recycleProVDC接口失败！");
        }
    }

    @Override
    public List<ProviderVDC> findAllProviderVDCs(ProviderVDC providerVDC)
            throws VDCException {
        List<ProviderVDC> list = new ArrayList<ProviderVDC>();
        try {
            ProviderVDCE providerVDCE = new ProviderVDCE();
            BeanUtils.copyProperties(providerVDC, providerVDCE);
            List<ProviderVDCE> providerVDCEList = providerVDCDaoServiceImpl
                    .findAllProviderVDCs(providerVDCE);
            for (ProviderVDCE providerVDCETmp : providerVDCEList) {
                ProviderVDC providerVDCTmp = new ProviderVDC();
                BeanUtils.copyProperties(providerVDCETmp, providerVDCTmp);
                list.add(providerVDCTmp);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("根据属性查询提供者vdc信息失败！");
        }
        return list;
    }

    @Override
    public void expenseProVDCCpu(String proVDCId, Integer vCpuNum)
            throws VDCException {
        ProviderVDCE providerVDCE = providerVDCDaoServiceImpl
                .findProviderVDC(proVDCId);
        Integer vCpuOverplus = providerVDCE.getvCpuOverplus();
        Integer vCpuUsed = providerVDCE.getvCpuUsed();
        if (vCpuOverplus < vCpuNum) {
            throw new VDCException("剩余CPU数量不足！");
        }
        try {
            providerVDCE.setvCpuOverplus(vCpuOverplus - vCpuNum);
            providerVDCE.setvCpuUsed(vCpuUsed + vCpuNum);
            providerVDCE = providerVDCDaoServiceImpl.save(providerVDCE);
        } catch (StaleObjectStateException e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("资源竞争中，请稍后再试！");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("执行expenseProVDC接口失败！");
        }

    }

    @Override
    public void recycleProVDCMemory(String proVDCId, Long memorySize)
            throws VDCException {

        ProviderVDCE providerVDCE = providerVDCDaoServiceImpl
                .findProviderVDC(proVDCId);
        Long memoryOverplus = providerVDCE.getMemoryOverplus();
        Long memoryUsed = providerVDCE.getMemoryUsed();
        if (memoryUsed < memorySize) {
            throw new VDCException("回收内存资源大于已用内存资源！");
        }
        try {
            providerVDCE.setMemoryOverplus(memoryOverplus + memorySize);
            providerVDCE.setMemoryUsed(memoryUsed - memorySize);
            providerVDCE = providerVDCDaoServiceImpl.save(providerVDCE);

        } catch (StaleObjectStateException e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("资源竞争中，请稍后再试！");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("执行recycleProVDC接口失败！");
        }
    }

    @Override
    public void recycleProVDCCpu(String proVDCId, Integer vCpuNum)
            throws VDCException {
        ProviderVDCE providerVDCE = providerVDCDaoServiceImpl
                .findProviderVDC(proVDCId);
        Integer vCpuOverplus = providerVDCE.getvCpuOverplus();
        Integer vCpuUsed = providerVDCE.getvCpuUsed();
        if (vCpuUsed < vCpuNum) {
            throw new VDCException("回收CPU数量大于已用CPU数量！");
        }
        try {
            providerVDCE.setvCpuOverplus(vCpuOverplus + vCpuNum);
            providerVDCE.setvCpuUsed(vCpuUsed - vCpuNum);
            providerVDCE = providerVDCDaoServiceImpl.save(providerVDCE);
        } catch (StaleObjectStateException e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("资源竞争中，请稍后再试！");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("执行recycleProVDC接口失败！");
        }

    }

    @Override
    public void expenseProVDCMemory(String proVDCId, Long memorySize)
            throws VDCException {
        ProviderVDCE providerVDCE = providerVDCDaoServiceImpl
                .findProviderVDC(proVDCId);
        Long memoryOverplus = providerVDCE.getMemoryOverplus();
        Long memoryUsed = providerVDCE.getMemoryUsed();
        if (memoryOverplus < memorySize) {
            throw new VDCException("剩余内存资源不足！");
        }
        try {
            providerVDCE.setMemoryOverplus(memoryOverplus - memorySize);
            providerVDCE.setMemoryUsed(memoryUsed + memorySize);
            providerVDCE = providerVDCDaoServiceImpl.save(providerVDCE);

        } catch (StaleObjectStateException e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("资源竞争中，请稍后再试！");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("执行expenseProVDC接口失败！");
        }

    }
}
