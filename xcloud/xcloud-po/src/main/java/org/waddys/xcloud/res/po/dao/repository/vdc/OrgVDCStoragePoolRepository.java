package org.waddys.xcloud.res.po.dao.repository.vdc;

import java.util.List;

import org.springframework.data.repository.Repository;
import org.waddys.xcloud.res.po.entity.vdc.OrgVDCStoragePoolE;

public interface OrgVDCStoragePoolRepository extends
        Repository<OrgVDCStoragePoolE, String> {

    OrgVDCStoragePoolE save(OrgVDCStoragePoolE orgVDCStoragePool);

    List<OrgVDCStoragePoolE> findByOrgVDCId(String orgVDCId);
}
