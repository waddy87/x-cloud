/**
 * 
 */
package org.waddys.xcloud.vm.po.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
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
    
    public void deleteByVmId(String vmId);

    public List<VmNetE> findByVmId(String vmId);
    
    @Query(value = "update vm_net set status='P' where vm_id=?1", nativeQuery = true)
    public void deleteBatch(String vmId);

}
