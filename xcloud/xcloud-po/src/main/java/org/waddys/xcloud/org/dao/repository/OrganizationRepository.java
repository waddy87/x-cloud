package org.waddys.xcloud.org.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.waddys.xcloud.org.dao.entity.OrganizationE;


public interface OrganizationRepository
        extends JpaRepository<OrganizationE, Long>, JpaSpecificationExecutor<OrganizationE> {

    // List<OrganizationE> findAll();

    // Page<OrganizationE> findAll(Pageable pageable);

    // Page<OrganizationE> findByNameContainingAllIgnoringCase(String name,
    // Pageable pageable);

    OrganizationE findByName(String name);

    OrganizationE findByIdAllIgnoringCase(String id);

    Page<OrganizationE> findByNameContainingAllIgnoringCase(String name, Pageable pageable);

    OrganizationE findById(String id);

    @Query("select count(distinct o) from OrganizationE o where o.name like %?1%")
    long countByName(String name);

    // OrganizationE save(OrganizationE organization);
    
    // boolean exists(Long id);
    
    // void delete(Long id);
    
    // long count();
    
    

}
