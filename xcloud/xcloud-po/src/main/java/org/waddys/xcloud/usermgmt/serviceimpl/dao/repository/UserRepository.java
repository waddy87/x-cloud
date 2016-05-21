package org.waddys.xcloud.usermgmt.serviceimpl.dao.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.waddys.xcloud.usermgmt.serviceimpl.dao.entity.UserE;

public interface UserRepository extends
        PagingAndSortingRepository<UserE, String>,
        JpaSpecificationExecutor<UserE> {

    List<UserE> findAll();

    UserE findOne(String id);

    void delete(String id);

    Page<UserE> findByUsernameContaining(String name, Pageable pageable);

    @Query("select count(distinct u) from UserE u where u.username like %?1%")
    long countByUsername(String username);

    Page<UserE> findAll(Pageable pageable);

    UserE findByUsername(String username);

    List<UserE> findByOrgId(String orgId);

    @Modifying
    @Transactional
    @Query("update UserE u set u.isDelete = :isDelete  where u.orgId = :orgId")
    void updateUserStatusByOrgId(@Param("orgId") String orgId,
            @Param("isDelete") Boolean isDelete);
}
