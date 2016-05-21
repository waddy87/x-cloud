/**
 * 
 */
package com.sugon.cloudview.cloudmanager.vm.dao.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.sugon.cloudview.cloudmanager.vm.bo.VmNet;

/**
 * 虚机网络DAO接口
 * 
 * @author zhangdapeng
 *
 */
public interface VmNetDaoService {

    public VmNet add(VmNet net) throws Exception;

    public void addBatch(List<VmNet> nets) throws Exception;

    public VmNet findById(String id);

    public void delete(VmNet net) throws Exception;

    public Page<VmNet> findByBO(VmNet vmNet, PageRequest pageable);

	public VmNet findByVmAndNet(String vmId, String netId);

}
