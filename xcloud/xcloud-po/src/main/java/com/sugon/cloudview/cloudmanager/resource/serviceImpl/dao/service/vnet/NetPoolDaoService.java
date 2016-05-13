package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.service.vnet;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sugon.cloudview.cloudmanager.resource.service.exception.vnet.VNetException;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.vnet.NetPoolE;

/**
 * 虚拟网络Dao接口
 * 
 * @author LvQP
 *
 */
public interface NetPoolDaoService {
    /**
     * 按照网络名称模糊查询，分页显示所有虚拟网络
     * 
     * @param netName
     *            输入要查询的网络名称
     * @param pageable
     *            分页的信息
     * @return List<NetPoolE> 虚拟网络List列表
     */
    Page<NetPoolE> QueryNetPoolByPage(String netName, Boolean isAvl, Pageable pageable);

    /**
     * ghj
     * 
     * @param netPoolE
     * @return
     */
    public long count(NetPoolE netPoolE, Boolean isAvl);

    /**
     * 根据虚拟化环境ID删除网络池
     * 
     * @param configId
     *            虚拟话环境ID
     */
    public void delNetPoolByConfigId(String configId);

    /**
     * 保存NetPoolE对象
     * 
     * @param netpoolE
     * @return boolean true表示成功，false表示失败
     */
    NetPoolE saveNetPool(NetPoolE netpoolE);

    /**
     * 通过netPoolId，删除NetPoolE对象
     * 
     * @param netpoolId
     *            虚拟网络Id 没有返回值
     */
    void deleteNetPool(String netpoolId) throws VNetException;

    /**
     * 修改NetPool对象
     * 
     * @param netpoolE
     *            要修改的NetPoolE对象
     * @return NetPoolE 修改后的NetPoolE对象
     */
    NetPoolE updateNetPool(NetPoolE netpoolE);

    /**
     * 为多个虚拟网络分配组织
     * 
     * @param netPoolIDs
     *            List<String>多个虚拟网络Id
     * @param orgId
     *            组织Id
     * @return boolean true表示分配成功，false表示分配失败
     */
    boolean distributionOrg(List<String> netPoolIDs, String orgId);

    /**
     * 通过虚拟网络Id得到虚拟机网络对象
     * 
     * @param poolId
     *            虚拟网络Id
     * @return NetPoolE 虚拟网络对象
     */
    NetPoolE findNetPoolEByNetPoolId(String poolId);

    /**
     * 通过orgId得到虚拟网络对象列表
     * 
     * @param orgId
     *            组织id
     * @return List<NetPoolE> 虚拟网络对象列表
     */
    List<NetPoolE> QueryNetpoolByorgId(String orgId);

    /**
     * 模糊匹配 vlanNo或网关或子网或dns的值，分页显示所有虚拟网络
     * 
     * @param vlanno
     *            vlan号
     * @param gateway
     *            网关
     * @param subnet
     *            子网
     * @param dns
     *            dns
     * @param pageable
     *            分页信息
     * @return Page<NetPoolE> 虚拟网络分页列表
     */
    Page<NetPoolE> queryVlanOrGatewayOrSubnetOrDns(Integer vlanno, String gateway, String subnet, String dns,
            Pageable pageable);

    /**
     * 查询所有虚拟网络
     * 
     * @return List<NetPoolE> 虚拟网络List列表
     */
    List<NetPoolE> QueryNetPoolAll();

    /**
     * 查询所有可用虚拟网络
     * 
     * @param isAvl
     * @return
     */
    List<NetPoolE> findByIsAvl(Boolean isAvl);

    List<NetPoolE> findByNetPool(NetPoolE netPoolE);

}
