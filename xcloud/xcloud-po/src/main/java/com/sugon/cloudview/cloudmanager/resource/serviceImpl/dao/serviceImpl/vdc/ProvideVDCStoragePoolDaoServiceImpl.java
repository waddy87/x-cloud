package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.serviceImpl.vdc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.vdc.ProvideVDCStoragePoolE;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.repository.vdc.ProvideVDCStoragePoolRepository;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.service.vdc.ProvideVDCStoragePoolDaoService;

/**
 * 计算池dao层接口实现
 * 
 * @author sun
 *
 */
@Component("provideVDCStoragePoolDaoServiceImpl")
@Transactional
public class ProvideVDCStoragePoolDaoServiceImpl implements
        ProvideVDCStoragePoolDaoService {
    @Autowired
    private ProvideVDCStoragePoolRepository provideVDCStoragePoolRepository;

    @Override
    public ProvideVDCStoragePoolE save(
            ProvideVDCStoragePoolE provideVDCStoragePool) {

        return provideVDCStoragePoolRepository.save(provideVDCStoragePool);
    }

    @Override
    public List<ProvideVDCStoragePoolE> findByPVDCId(String pVDCId) {
        return provideVDCStoragePoolRepository.findByPVDCId(pVDCId);
    }

    @Override
    public void deleteByProVDCId(String proVDCId) {
        provideVDCStoragePoolRepository.deleteByProVDCId(proVDCId);
    }

    @Override
    public void updateProvideVDCStoragePool(
            ProvideVDCStoragePoolE provideVDCStoragePoolE) {
        provideVDCStoragePoolRepository.updateProvideVDCStoragePool(
                provideVDCStoragePoolE.getpVDCStoragePoolId(),
                provideVDCStoragePoolE.getSpTotal(),
                provideVDCStoragePoolE.getSpSurplus());

    }

    @Override
    public ProvideVDCStoragePoolE findByPVDCIdAndSpId(String pVDCId, String spId) {
        return provideVDCStoragePoolRepository
                .findByPVDCIdAndSpId(pVDCId, spId);
    }

    @Override
    public ProvideVDCStoragePoolE findProvideVDCStoragePool(String id) {
        return provideVDCStoragePoolRepository.findOne(id);
    }

    // @Override
    // public void updateProvideVDCStoragePool(String spId, String pVDCId,
    // Long spSurplus, Long spUsed) {
    // provideVDCStoragePoolRepository.updateProvideVDCStoragePool(spId,
    // pVDCId, spSurplus, spUsed);
    // }

}
