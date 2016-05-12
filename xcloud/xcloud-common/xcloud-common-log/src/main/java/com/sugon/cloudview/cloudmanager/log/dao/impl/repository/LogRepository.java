/**
 * Created on 2016年3月31日
 */
package com.sugon.cloudview.cloudmanager.log.dao.impl.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sugon.cloudview.cloudmanager.log.entity.LogInfoEntity;

/**
 * 功能名: 请填写功能名
 * 功能描述: 请简要描述功能的要点
 * Copyright: Copyright (c) 2016
 * 公司: 曙光云计算技术有限公司
 *
 * @author 曾兵
 * @version 2.0.0 sp1
 */
public interface LogRepository
		extends PagingAndSortingRepository<LogInfoEntity, String>, JpaSpecificationExecutor<LogInfoEntity> {

	@Query("select count(distinct p) from managerLogInfoEntity p where p.moduleType = ?1 and p.userName = ?2 and p.resourceId = ?3 and p.operatingTime between ?4 and ?5")
	long countByAll(String moduleType, String userName, String resourceId, Date startTime, Date endTime);

	@Override
	List<LogInfoEntity> findAll();

}
