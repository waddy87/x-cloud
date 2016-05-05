/**
 * 
 */
package com.sugon.cloudview.cloudmanager.vm.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sugon.cloudview.cloudmanager.vm.dao.entity.VmNetE;

/**
 * 虚机网络仓库组件
 * 
 * @author zhangdapeng
 *
 */
public interface VmNetRepository extends JpaRepository<VmNetE, Long>, JpaSpecificationExecutor<VmNetE> {

    public VmNetE findById(String id);

    @Override
    public void delete(VmNetE net);

}
