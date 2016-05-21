package org.waddys.xcloud.resource.serviceImpl.dao.serviceImpl.vdc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.waddys.xcloud.resource.serviceImpl.dao.entity.vdc.OrgVDCStoragePoolE;
import org.waddys.xcloud.resource.serviceImpl.dao.repository.vdc.OrgVDCStoragePoolRepository;
import org.waddys.xcloud.resource.serviceImpl.dao.service.vdc.OrgVDCStoragePoolDaoService;

/**
 * 计算池dao层接口实现
 * 
 * @author sun
 *
 */
@Component("orgVDCStoragePoolDaoServiceImpl")
@Transactional
public class OrgVDCStoragePoolDaoServiceImpl implements
        OrgVDCStoragePoolDaoService {
    @Autowired
    private OrgVDCStoragePoolRepository orgVDCStoragePoolRepository;

    @Override
    public OrgVDCStoragePoolE save(OrgVDCStoragePoolE orgVDCStoragePool) {
        return orgVDCStoragePoolRepository.save(orgVDCStoragePool);
    }

    @Override
    public List<OrgVDCStoragePoolE> findByOrgVDCId(String orgVDCId) {
        return orgVDCStoragePoolRepository.findByOrgVDCId(orgVDCId);
    }

}
