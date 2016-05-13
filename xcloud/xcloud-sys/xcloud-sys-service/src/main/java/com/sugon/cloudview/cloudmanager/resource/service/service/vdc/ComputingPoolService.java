package com.sugon.cloudview.cloudmanager.resource.service.service.vdc;

import java.util.List;

import com.sugon.cloudview.cloudmanager.resource.service.bo.vdc.ComputingPool;
import com.sugon.cloudview.cloudmanager.resource.service.bo.vdc.StoragePool;
import com.sugon.cloudview.cloudmanager.resource.service.exception.vdc.VDCException;

public interface ComputingPoolService {
    /**
     * 新建
     *
     * @param computingPool
     * @return
     * @throws VDCException
     */
    public ComputingPool save(ComputingPool computingPool) throws VDCException;

    /**
     * 查询全部计算池信息
     * 
     * @return
     * @throws VDCException
     */
    public List<ComputingPool> findAllComputingPools() throws VDCException;

    /**
     * 根据计算池id查询存储池
     * 
     * @param cpId
     * @return
     * @throws VDCException
     */
    public List<StoragePool> findStoragePools(String cpId) throws VDCException;

    /**
     * 根据计算池id查询计算池详情
     * 
     * @param computingPoolId
     * @return
     * @throws VDCException
     */
    public ComputingPool findComputingPool(String computingPoolId)
            throws VDCException;

    /**
     * 删除计算池
     * 
     * @param computingPoolId
     * @throws VDCException
     */
    public void delete(String computingPoolId) throws VDCException;

    public void deleteByConfigId(String configId) throws VDCException;

    /**
     * 根据是否分配状态查询计算池
     * 
     * @param isDistribute
     * @return
     */
    public List<ComputingPool> findByIsDistribute(Boolean isDistribute)
            throws VDCException;

    /**
     * 更新计算池，目前只支持更新是否分配字段
     * 
     * @param computingPool
     * @throws VDCException
     */
    public void update(ComputingPool computingPool) throws VDCException;

    public List<ComputingPool> findByIsAvl(Boolean isAvl) throws VDCException;

    public List<ComputingPool> findAllComputingPools(ComputingPool computingPool)
            throws VDCException;
}
