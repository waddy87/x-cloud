/**
 * 
 */
package com.sugon.cloudview.cloudmanager.vm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.sugon.cloudview.cloudmanager.vm.bo.VmDisk;

/**
 * 虚机磁盘服务接口
 * 
 * @author zhangdapeng
 *
 */
public interface VmDiskService {

    /**
     * 添加虚机磁盘
     * 
     * @param vmDisk
     * @return
     * @throws Exception
     */
    public VmDisk add(VmDisk vmDisk) throws Exception;

    /**
     * 删除虚机磁盘
     * 
     * @param diskId
     *            目标磁盘业务唯一标识
     * @throws Exception
     */
    public void deleteById(String diskId) throws Exception;

    /**
     * 根据虚机查询磁盘列表
     * 
     * @param vmId
     *            目标虚机业务唯一标识
     * @return
     * @throws Exception
     */
    public List<VmDisk> listByVm(String vmId);

    /**
     * 根据虚机分页查询磁盘列表
     * 
     * @param vmId
     *            目标虚机业务唯一标识
     * @return
     */
    public Page<VmDisk> pageByVm(String vmId, PageRequest pageRequest);

}
