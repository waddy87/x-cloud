/**
 * 
 */
package com.sugon.cloudview.cloudmanager.vm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.sugon.cloudview.cloudmanager.vm.bo.VmNet;

/**
 * 虚机网络服务接口
 * 
 * @author zhangdapeng
 *
 */
public interface VmNetService {

    /**
     * 增加一个虚机网络
     * 
     * @param vmNet
     * @return
     * @throws Exception
     */
    public VmNet add(VmNet vmNet) throws Exception;

    /**
     * 删除一个虚机网络
     * 
     * @param vmNet
     * @throws Exception
     */
    public void delete(String netId) throws Exception;

    /**
     * 根据虚机查询网络列表
     * 
     * @param vmId
     *            虚机业务唯一标识
     * @return
     */
    public List<VmNet> listByVm(String vmId);

    /**
     * 根据虚机分页查询网络列表
     * 
     * @param vmId
     *            虚机业务唯一标识
     * @return
     */
    public Page<VmNet> pageByVm(String vmId, PageRequest pageRequest);

    /**
     * 根据虚机和网络联合查询
     * 
     * @param vmId
     *            虚机业务标识
     * @param netId
     *            网络唯一标识
     * @return
     */
    public VmNet findByVmAndNet(String vmId, String netId);

}
