/**
 * 
 */
package org.waddys.xcloud.vm.po.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.waddys.xcloud.vm.po.entity.VmNetE;

/**
 * 虚机网络仓库组件
 * 
 * @author zhangdapeng
 *
 */
public interface VmNetRepository extends JpaRepository<VmNetE, Long>, JpaSpecificationExecutor<VmNetE> {

    public VmNetE findById(String id);

    public VmNetE findByVmIdAndNetId(String vmId, String netId);

    @Override
    public void delete(VmNetE net);

}
