package org.waddys.xcloud.resource.serviceImpl.dao.service.vdc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.waddys.xcloud.resource.serviceImpl.dao.entity.vdc.OrgVDCE;

/**
 * 组织vdc dao层接口
 * 
 * @author sun
 *
 */
public interface OrgVDCDaoService {
    /**
     * 创建
     * 
     * @param orgVDC
     * @return
     */
    public OrgVDCE save(OrgVDCE orgVDC);

    /**
     * 查询全部
     * 
     * @return
     */
    public List<OrgVDCE> findAllOrgVDCs();

    /**
     * 获取详情
     * 
     * @return
     */
    public OrgVDCE findOrgVDC(String id);

    /**
     * 分页查询
     * 
     * @param OrgVDCName
     * @param pageable
     * @return
     */
    public Page<OrgVDCE> findOrgVDCs(String OrgVDCName, Pageable pageable);

    /**
     * 修改组织vdc
     * 
     * @param orgVDCE
     */
    public void updateOrgVDC(OrgVDCE orgVDCE);

    /**
     * 统计总数
     * 
     * @param orgVDCE
     * @return
     */
    public long count(OrgVDCE orgVDCE);

}
