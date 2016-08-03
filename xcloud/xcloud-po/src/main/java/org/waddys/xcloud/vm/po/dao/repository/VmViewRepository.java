package org.waddys.xcloud.vm.po.dao.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.waddys.xcloud.vm.po.entity.VmView;


public interface VmViewRepository extends JpaRepository<VmView, Long>, JpaSpecificationExecutor<VmView> {

	@Query(value = "from VmView")
    List<VmView> findAll();

}
