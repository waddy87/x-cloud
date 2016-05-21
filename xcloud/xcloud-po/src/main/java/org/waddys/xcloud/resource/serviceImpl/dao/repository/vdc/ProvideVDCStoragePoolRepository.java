package org.waddys.xcloud.resource.serviceImpl.dao.repository.vdc;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.waddys.xcloud.resource.serviceImpl.dao.entity.vdc.ProvideVDCStoragePoolE;

public interface ProvideVDCStoragePoolRepository extends
        Repository<ProvideVDCStoragePoolE, String> {

    ProvideVDCStoragePoolE save(ProvideVDCStoragePoolE provideVDCStoragePool);

    List<ProvideVDCStoragePoolE> findByPVDCId(String pVDCId);

    @Modifying
    @Query("delete from ProvideVDCStoragePoolE p where p.pVDCId = ?1")
    void deleteByProVDCId(String proVDCId);

    @Modifying
    @Query("update ProvideVDCStoragePoolE p set p.spTotal = :spTotal,p.spSurplus = :spSurplus where p.pVDCStoragePoolId = :pVDCStoragePoolId")
    void updateProvideVDCStoragePool(
            @Param("pVDCStoragePoolId") String pVDCStoragePoolId,
            @Param("spTotal") Long spTotal, @Param("spSurplus") Long spSurplus);

    ProvideVDCStoragePoolE findByPVDCIdAndSpId(String pVDCId, String spId);

    ProvideVDCStoragePoolE findOne(String id);
    // @Modifying
    // @Query("update ProvideVDCStoragePoolE p set p.spSurplus = :spSurplus ,p.spUsed = :spUsed where p.spId = :spId and p.pVDCId = :pVDCId")
    // void updateProvideVDCStoragePool(@Param("spId") String spId,
    // @Param("pVDCId") String pVDCId, @Param("spSurplus") Long spSurplus,
    // @Param("spUsed") Long spUsed);
}
