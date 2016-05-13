/**
 * Created on 2016年3月15日
 */
package com.sugon.cloudview.cloudmanager.templet.serviceImpl.dao.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sugon.cloudview.cloudmanager.templet.service.entity.VMTempletE;

/**
 * 功能名: 模板管理持久层操作类
 * 功能描述: 模板管理持久层操作类
 * Copyright: Copyright (c) 2016
 * 公司: 曙光云计算技术有限公司
 *
 * @author 曾兵
 * @version 2.0.0 sp1
 */
public interface VMTempletRepository
		extends PagingAndSortingRepository<VMTempletE, Long>, JpaSpecificationExecutor<VMTempletE> {

	
	public VMTempletE findByRelationId(String relationId);
	

	Page<VMTempletE> findByNameContaining(String name, Pageable pageable);

    @Query("select count(distinct p) from VMTempletE p where p.name like %?1%")
    long countByName(String name);
    
	@Query("select p from VMTempletE p where p.name like %?1% and p.os like %?2% and p.status like %?3%")
	Page<VMTempletE> findByNameAndOsAndStatusLikeOrderByRelationId(String name, String os,
			String status,
			Pageable pageable);

	@Query("select count(distinct p) from VMTempletE p where p.name like %?1% and p.os like %?2% and p.status like %?3%")
	long countByNameAndOsAndStatus(String name, String os, String status);

	@Override
	List<VMTempletE> findAll();

    public VMTempletE findById(Integer id);
}
