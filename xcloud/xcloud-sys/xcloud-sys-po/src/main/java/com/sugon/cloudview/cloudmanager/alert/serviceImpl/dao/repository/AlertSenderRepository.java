/**
 * Created on 2016年3月25日
 */
package com.sugon.cloudview.cloudmanager.alert.serviceImpl.dao.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;






import com.sugon.cloudview.cloudmanager.alert.serviceImpl.entity.AlertSender;
/**
 * 功能名: 请填写功能名
 * 功能描述: 请简要描述功能的要点
 * Copyright: Copyright (c) 2016
 * 公司: 曙光云计算技术有限公司
 *
 * @author yangkun
 * @version 2.0.0 sp1
 */
public interface AlertSenderRepository extends JpaRepository<AlertSender,Long>{

	public List<AlertSender>findAll();
	
	public Page<AlertSender> findAll(Pageable paramPageable);
	
	public Page<AlertSender>findAll(Specification<AlertSender>spe,Pageable paramPageable);
	
	public  AlertSender findById(Long id);
	
	public  Page<AlertSender> findByResTypeAndResIdAndAlertSendLevel(String resType , String resId , String alertSendLevel,Pageable paramPageable);
	
	@SuppressWarnings("unchecked")
	public AlertSender save(AlertSender as);

	public void delete(Long id);
	
	public List<AlertSender> findByResIdAndTriggerId(String resId,String triggerId);
	
	public Long countByResType(String resType);
	
	public List<AlertSender> findByResType(String resType);
	
	public Long countByResTypeAndResId(String resType,String resId);
	
	public List<AlertSender> findByResTypeAndResId(String resType,String resId);
	
	public Long countByResTypeAndResIdAndTriggerId(String resType,String resId,String triggerId);
	
	public List<AlertSender> findByResTypeAndResIdAndTriggerId(String resType,String resId,String triggerId);
}
