/**
 * Created on 2016年3月25日
 */
package org.waddys.xcloud.alert.serviceImpl.dao.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.waddys.xcloud.alert.serviceImpl.entity.AlertInfo;

/**
 * 功能名: 请填写功能名
 * 功能描述: 请简要描述功能的要点
 * Copyright: Copyright (c) 2016
 * 公司: 曙光云计算技术有限公司
 *
 * @author yangkun
 * @version 2.0.0 sp1
 */
public interface AlertInfoRepository extends JpaRepository<AlertInfo, Long>{
		
	public List<AlertInfo>findAll();
	
	public Page<AlertInfo> findAll(Pageable paramPageable);
	
	public Page<AlertInfo>findAll(Specification<AlertInfo>spe,Pageable paramPageable);
	
	public  AlertInfo findById(Long id);
	
	public List<AlertInfo>findByIdNotIn(List<Long> ids);
	
	public  Page<AlertInfo> findByIsAquireAndResTypeAndResNameContainingAndAlertLevel(boolean isAquire,	String resType, String resName, String alertLevel ,Pageable paramPageable);
	
	public  Page<AlertInfo> findByResTypeAndResNameContainingAndAlertLevel(String resType, String resName, String alertLevel ,Pageable paramPageable);
	
	@SuppressWarnings("unchecked")
	public AlertInfo save(AlertInfo as);

	public void delete(Long id);
	
	public List<AlertInfo> findByResIdAndAlertId(String resId,String triggerId);
	
	@Query("select count(distinct p) from AlertInfo p where p.isAquire=?1 and p.resName like %?2% and p.resType=?3 and p.alertLevel=?4")
	public long countByMutiColumn(String isAquire,String resName,String resType,String alertLevel);
}
