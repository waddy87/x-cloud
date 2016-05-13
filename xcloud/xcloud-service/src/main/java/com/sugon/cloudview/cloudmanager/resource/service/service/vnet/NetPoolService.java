package com.sugon.cloudview.cloudmanager.resource.service.service.vnet;

import java.util.List;

import com.sugon.cloudview.cloudmanager.resource.service.bo.vnet.NetPool;
import com.sugon.cloudview.cloudmanager.resource.service.exception.vnet.VNetException;

/**
 * 虚拟网络Service接口
 * 
 * @author LvQP
 *
 */
public interface NetPoolService {

    /**
     * 分页查询所有虚拟网络
     * 
     * @param pageNum
     *            当前是第几页
     * @param pageSize
     *            一页显示多少条
     * @param inputName
     *            查询条件输入的内容，为空串的话，是查询全部的
     * @return List<NetPoolE> 虚拟网络List列表
     */
    List<NetPool> QueryNetPoolByPage(int pageNum, int pageSize, String inputName, Boolean isAvl) throws VNetException;

    /**
     * 模糊查询符合查询条件的记录数
     * 
     * @param netPool
     *            查询条件对象
     * @return long 记录数
     * @throws VNetException
     */
    public long count(NetPool netPool, Boolean isAvl) throws VNetException;

    /**
     * 根据虚拟化环境ID删除网络池
     * 
     * @param configId
     * @throws VNetException
     */
    public void delNetPoolByConfigId(String configId) throws VNetException;

    /**
     * 查询所有虚拟网络
     * 
     * @return List<NetPoolE> 虚拟网络List列表
     */
    List<NetPool> QueryNetPoolAll();

    /**
     * 查询所有可用虚拟网络
     * 
     * @param isAvl
     * @return
     */
    List<NetPool> findByIsAvl(Boolean isAvl);

    /**
     * 查询虚拟网络
     * 
     * @param 网络对象
     * @return 网络列表
     */
    List<NetPool> findByNetPool(NetPool netPool);

    /**
     * 保存netPool对象
     * 
     * @param netpool
     *            要保存的NetPool对象
     * @return String
     *         {"flag":true,"netPoolId":"8a809ec2535a9b9501535aab32170001"}
     *         flag为true表示成功，为false表示失败。
     * 
     */
    NetPool saveNetPool(NetPool netpool) throws VNetException;

    /**
     * 删除netPool对象
     * 
     * @param netPoolId
     *            NetPool对象的Id
     * 
     */
    void delNetPool(String netPoolId) throws VNetException;

    /**
     * 修改NetPool对象的网关、子网、DNS
     * 
     * @param netPool
     *            要修改的netPool对象
     * @return NetPool 修改过后的NetPool对象
     */
    NetPool updateNetPool(NetPool netPool);

    /**
     * 为多个虚拟网络分配组织
     * 
     * @param netPoolIds
     *            多个虚拟网络Id
     * @param orgId
     *            组织Id
     * @return boolean true表示分配成功，false表示分配失败
     */
    boolean distributionNetPool(List<String> netPoolIds, String orgId);

    /**
     * 通过虚拟网络Id得到虚拟网络对象
     * 
     * @param poolId
     *            虚拟网络Id
     * @return NetPool 虚拟网络池对象
     */
    NetPool QueryNetpoolById(String poolId);

    /**
     * 通过orgId得到虚拟网络对象列表
     * 
     * @param orgId
     *            组织id
     * @return List<NetPool> 虚拟网络对象列表
     */
    List<NetPool> QueryNetpoolByorgId(String orgId);

    /**
     * 模糊匹配 vlanNo或网关或子网或dns的值，分页显示所有虚拟网络
     * 
     * @param vlanno
     *            Integer vlan号
     * @param gateway
     *            String 网关
     * @param subnet
     *            String 子网
     * @param dns
     *            String DNS
     * @param pageNum
     *            当前第几页
     * @param pageSize
     *            一页显示多少条
     * @return List<NetPool> 网络池列表
     * 
     */
    List<NetPool> queryVlanOrGatewayOrSubnetOrDns(Integer vlanno, String gateway, String subnet, String dns,
            int pageNum, int pageSize);

    /**
     * ghj 网络池
     */
    void synNetPoolData(String configId, String ip) throws VNetException;

    /**
     * 功能名称：通过orgID回收网络
     * 
     * @param orgId
     * @throws VNetException
     */
    void recycleNetpoolByorgId(String orgId) throws VNetException;

    /**
     * 功能名称：修改网络
     * 
     * @param netPool
     *            netPool.setOrgID("12121"); netPool.setOrgName("大兵哥组织");
     * @return
     * @throws VNetException
     */
    void updateNetpoolByNetPool(NetPool netPool) throws VNetException;

}
