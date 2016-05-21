package org.waddys.xcloud.pmmgmt.serviceimpl.dao.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.waddys.xcloud.pmmgmt.serviceimpl.dao.entity.PhysicalMachineE;

public interface PhysicalMachineRepository extends
        PagingAndSortingRepository<PhysicalMachineE, String>,
        JpaSpecificationExecutor<PhysicalMachineE> {

    List<PhysicalMachineE> findAll();

    PhysicalMachineE findOne(String id);

    void delete(String id);

    Page<PhysicalMachineE> findAll(Pageable pageable);

    @Modifying
    @Query("update PhysicalMachineE pm set pm.orgId=?1 ,pm.orgName=?2 where pm.id=?3")
    void updateOrgIdByPmId(String orgId, String orgName, String id);

}
