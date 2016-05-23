package org.waddys.xcloud.res.po.dao.vdc;

import java.util.List;

import org.waddys.xcloud.res.po.entity.vdc.ProvideVDCStoragePoolE;

/**
 * 提供者vdc-存储池 dao层接口
 * 
 * @author sun
 *
 */
public interface ProvideVDCStoragePoolDaoService {
    /**
     * 创建
     * 
     * @param ProvideVDCStoragePool
     * @return
     */
    public ProvideVDCStoragePoolE save(
            ProvideVDCStoragePoolE ProvideVDCStoragePool);

    public List<ProvideVDCStoragePoolE> findByPVDCId(String pVDCId);

    public void deleteByProVDCId(String proVDCId);

    public void updateProvideVDCStoragePool(
            ProvideVDCStoragePoolE provideVDCStoragePoolE);

    public ProvideVDCStoragePoolE findByPVDCIdAndSpId(String pVDCId, String spId);

    public ProvideVDCStoragePoolE findProvideVDCStoragePool(String id);
}
