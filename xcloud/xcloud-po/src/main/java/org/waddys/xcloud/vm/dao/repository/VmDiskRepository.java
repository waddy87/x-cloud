/**
 * 
 */
package org.waddys.xcloud.vm.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.waddys.xcloud.vm.dao.entity.VmDiskE;

/**
 * 虚机磁盘仓库组件
 * 
 * @author zhangdapeng
 *
 */
public interface VmDiskRepository extends JpaRepository<VmDiskE, Long>, JpaSpecificationExecutor<VmDiskE> {

    public VmDiskE findById(String id);

    @Override
    public void delete(VmDiskE vmDiskE);

}
