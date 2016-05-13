/**
 * 
 */
package com.sugon.cloudview.cloudmanager.vm.dao.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.sugon.cloudview.cloudmanager.vm.bo.VmDisk;

/**
 * 虚机磁盘DAO接口
 * 
 * @author zhangdapeng
 *
 */
public interface VmDiskDaoService {

    public VmDisk add(VmDisk vmDisk) throws Exception;

    public VmDisk findById(String id);

    public void delete(VmDisk vmDisk) throws Exception;

    public Page<VmDisk> findByBO(VmDisk vmDisk, PageRequest pageable);

}
