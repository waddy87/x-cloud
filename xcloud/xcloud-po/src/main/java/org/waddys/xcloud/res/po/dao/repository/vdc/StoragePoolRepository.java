package org.waddys.xcloud.res.po.dao.repository.vdc;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.waddys.xcloud.res.po.entity.vdc.StoragePoolE;

public interface StoragePoolRepository extends Repository<StoragePoolE, String> {

    StoragePoolE save(StoragePoolE storagePool);

    @Modifying
    @Query("delete from StoragePoolE s where s.configId = ?1")
    void deleteByConfigId(String cfId);

    StoragePoolE findOne(String id);

    List<StoragePoolE> findAll();
}
