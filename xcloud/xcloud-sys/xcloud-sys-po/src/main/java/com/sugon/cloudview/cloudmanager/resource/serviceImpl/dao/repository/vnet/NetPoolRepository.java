package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.repository.vnet;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.vnet.NetPoolE;

/**
 * 虚拟网络的JPA接口
 * 
 * @author LvQP
 *
 */
public interface NetPoolRepository
        extends PagingAndSortingRepository<NetPoolE, String>, JpaSpecificationExecutor<NetPoolE> {

    /**
     * 根据网络名称模糊查询，分页显示所有虚拟网络
     * 
     * @param netName
     *            网络名称
     * @param pageable
     *            分页的信息
     * @return Page<NetPoolE> 虚拟网络列表 Page<NetPoolE>
     */
    @Query("select n from NetPoolE n where n.netName like %?1% and n.isAvl= ?2")
    Page<NetPoolE> findBynetNameAndIsAvlContainingAllIgnoringCase(String netName, Boolean isAvl, Pageable pageable);

    List<NetPoolE> findByIsAvl(Boolean isAvl);

    /**
     * 
     * @param netName
     * @return
     */
    @Query("select count(distinct n) from NetPoolE n where n.netName like %?1% and n.isAvl= ?2")
    long countBynetName(String netName, Boolean isAvl);

    @Modifying
    @Query("delete from NetPoolE n where n.configId=?1")
    void delNetPoolByConfigId(String configId);
    /**
     * ghj
     * 
     * @param netPoolId
     * @return
     */
    NetPoolE findBynetPoolId(String netPoolId);

    /**
     * NetPoolE 查询所有虚拟网络
     * 
     * @return List<NetPoolE> 虚拟网络List列表
     */

    @Override
    List<NetPoolE> findAll();

    /**
     * 分页查询虚拟网络
     * 
     * @param pageable
     *            分页的信息
     * @return Page<NetPoolE> 虚拟网络列表 Page<NetPoolE>
     */
    Page<NetPoolE> findByisAvl(Boolean isAvl, Pageable pageable);


    /**
     * 输入字符，模糊匹配 vlanNo或网关或子网或dns的值，分页显示所有虚拟网络
     * 
     * @param vlanNo
     * 				vlan号
     * @param gateway
     * 				网关
     * @param subnet
     * 				子网
     * @param dns
     * 				dns
     * @param pageable
     * 				分页信息
     * @return Page<NetPoolE>
     * 				虚拟网络分页列表
     */
    @Query("SELECT np FROM NetPoolE np where np.vlanNO=?1 or np.gateway=?2 or np.subNet=?3 or np.dns=?4")
    Page<NetPoolE> queryVlanNoORgatewayORsubNetORdns(Integer vlanNo,
    		String gateway, String subnet, String dns, Pageable pageable);

    /**
     * 修改虚拟网络的网关、子网、DNS
     * 
     * @param poolId
     *            虚拟网络Id
     * @param gateway
     *            网关
     * @param subNet
     *            子网
     * @param dns
     *            DNS
     * @return
     */
    @Modifying
    @Query("update NetPoolE np set np.gateway=?2, np.subNet=?3, np.dns=?4 where np.netPoolId=?1")
    void updateNetPool(String poolId, String gateway, String subNet, String dns);
    
    
    /**
     * 更新网络池对象的组织Id
     * @param poolId
     * 			网络池对象Id
     * @param orgId
     * 			组织Id
     */
    @Modifying
    @Query("update NetPoolE np set np.orgId=?2 where np.netPoolId=?1")
    void updateNetPoolOfOrg(String poolId, String orgId);

    @Query("SELECT np FROM NetPoolE np where np.orgId=?1")
    List<NetPoolE> findByOrgId(String orgId);

}
