package org.waddys.xcloud.resource.serviceImpl.impl.vdc;

import org.hibernate.StaleObjectStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.waddys.xcloud.resource.service.bo.vdc.ProvideVDCStoragePool;
import org.waddys.xcloud.resource.service.exception.vdc.VDCException;
import org.waddys.xcloud.resource.service.service.vdc.ProvideVDCStoragePoolService;
import org.waddys.xcloud.resource.serviceImpl.dao.entity.vdc.ProvideVDCStoragePoolE;
import org.waddys.xcloud.resource.serviceImpl.dao.service.vdc.ProvideVDCStoragePoolDaoService;

@Service("provideVDCStoragePoolServiceImpl")
public class ProvideVDCStoragePoolServiceImpl implements
        ProvideVDCStoragePoolService {
    private static Logger logger = LoggerFactory
            .getLogger(ProvideVDCStoragePoolServiceImpl.class);
    @Autowired
    private ProvideVDCStoragePoolDaoService provideVDCStoragePoolDaoServiceImpl;

    @Override
    public ProvideVDCStoragePool findByPVDCIdAndSpId(String pVDCId, String spId)
            throws VDCException {
        ProvideVDCStoragePoolE provideVDCStoragePoolE = new ProvideVDCStoragePoolE();
        ProvideVDCStoragePool provideVDCStoragePool = new ProvideVDCStoragePool();
        try {
            provideVDCStoragePoolE = provideVDCStoragePoolDaoServiceImpl
                    .findByPVDCIdAndSpId(pVDCId, spId);
            BeanUtils.copyProperties(provideVDCStoragePool,
                    provideVDCStoragePoolE);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("查询提供者VDC关联存储失败！");
        }
        return provideVDCStoragePool;
    }

    @Override
    public void expenseProvideVDCStoragePool(String pVDCStoragePoolId,
            Long storageSize) throws VDCException {
        ProvideVDCStoragePoolE provideVDCStoragePoolE = provideVDCStoragePoolDaoServiceImpl
                .findProvideVDCStoragePool(pVDCStoragePoolId);

        Long spSurplus = provideVDCStoragePoolE.getSpSurplus();
        Long spUsed = provideVDCStoragePoolE.getSpUsed();
        if (spSurplus < storageSize) {
            throw new VDCException("存储资源不足！");
        }
        try {
            provideVDCStoragePoolE.setSpSurplus(spSurplus - storageSize);
            provideVDCStoragePoolE.setSpUsed(spUsed + storageSize);
            provideVDCStoragePoolDaoServiceImpl.save(provideVDCStoragePoolE);
        } catch (StaleObjectStateException e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("资源竞争中，请稍后再试！");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new VDCException("执行expenseProvideVDCStoragePool接口失败！");
        }
    }

    @Override
    public void recycleProvideVDCStoragePool(String pVDCStoragePoolId,
            Long storageSize) throws VDCException {
        ProvideVDCStoragePoolE provideVDCStoragePoolE = provideVDCStoragePoolDaoServiceImpl
                .findProvideVDCStoragePool(pVDCStoragePoolId);
        Long spSurplus = provideVDCStoragePoolE.getSpSurplus();
        Long spUsed = provideVDCStoragePoolE.getSpUsed();
        if (spUsed < storageSize) {
            throw new VDCException("回收存储资源大于已用存储资源！");
        }
        try {
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
}
