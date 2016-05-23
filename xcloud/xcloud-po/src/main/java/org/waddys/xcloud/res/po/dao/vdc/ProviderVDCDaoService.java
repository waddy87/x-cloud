package org.waddys.xcloud.res.po.dao.vdc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.waddys.xcloud.res.po.entity.vdc.ProviderVDCE;

/**
 * 提供者vdc dao层接口
 * 
 * @author sun
 *
 */
public interface ProviderVDCDaoService {
    /**
     * 创建
     * 
     * @param computingPoolE
     * @return
     */
    public ProviderVDCE save(ProviderVDCE ProviderVDC);

    /**
     * 查询全部
     * 
     * @return
     */
    public List<ProviderVDCE> findAllProviderVDCs();

    /**
     * 获取详情
     * 
     * @return
     */
    public ProviderVDCE findProviderVDC(String id);

    /**
     * 修改提供者vdc
     * 
     * @param providerVDCE
     */
    public void updateProviderVDC(ProviderVDCE providerVDCE);

    public void delete(String proVDCId);

    public Page<ProviderVDCE> findProviderVDCs(ProviderVDCE providerVDCE,
            Pageable pageable);

    public List<ProviderVDCE> findAllProviderVDCs(ProviderVDCE providerVDCE);
}
