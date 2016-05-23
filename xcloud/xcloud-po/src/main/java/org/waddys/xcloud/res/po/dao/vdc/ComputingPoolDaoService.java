package org.waddys.xcloud.res.po.dao.vdc;

import java.util.List;

import org.waddys.xcloud.res.po.entity.vdc.ComputingPoolE;

/**
 * 计算池dao层接口
 * 
 * @author sun
 *
 */
public interface ComputingPoolDaoService {
    /**
     * 保存计算池
     * 
     * @param computingPoolE
     * @return
     */
    public ComputingPoolE save(ComputingPoolE computingPoolE);

    /**
     * 查询全部计算池接口
     * 
     * @return
     */
    public List<ComputingPoolE> findAllComputingPools();

    /**
     * 获取计算池详情
     * 
     * @return
     */
    public ComputingPoolE findComputingPool(String id);

    /**
     * 删除计算池
     * 
     * @param id
     */
    public void delete(String id);

    public void deleteByConfigId(String configId);

    public List<ComputingPoolE> findByIsDistribute(Boolean isDistribute);

    public List<ComputingPoolE> findByIsAvl(Boolean isAvl);

    public void update(ComputingPoolE computingPoolE);

    public List<ComputingPoolE> findAllComputingPools(
            ComputingPoolE computingPoolE);
}
