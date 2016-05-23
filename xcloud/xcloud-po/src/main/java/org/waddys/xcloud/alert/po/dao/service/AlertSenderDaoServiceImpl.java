/**
 * Created on 2016年3月25日
 */
package org.waddys.xcloud.alert.po.dao.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.waddys.xcloud.alert.po.dao.repository.AlertSenderRepository;
import org.waddys.xcloud.alert.po.entity.AlertSender;

/**
 * 功能名: 请填写功能名
 * 功能描述: 请简要描述功能的要点
 * Copyright: Copyright (c) 2016
 * 公司: 曙光云计算技术有限公司
 *
 * @author yangkun
 * @version 2.0.0 sp1
 */

@Component("alertSenderDaoServiceImpl")
@Transactional
public class AlertSenderDaoServiceImpl implements AlertSenderDaoServiceI{


	  @Autowired
	  private AlertSenderRepository alertSenderRepository;
	
	
	@Override
	public List<AlertSender> findAll() {
		return alertSenderRepository.findAll();
	}


	@Override
	public Map<String, Object> findAll(int pageNum ,int  pageSize) {
		    Map<String ,Object> map=new HashMap<String,Object>();
			   Page<AlertSender> pageList=	alertSenderRepository.findAll( new PageRequest(pageNum-1, pageSize));
			   if(pageList!=null){
				   map.put("total", pageList.getTotalElements());
				   map.put("rows", pageList.getContent());
				   return map;
			   }
			   return null;
	}


	@Override
	public AlertSender findById(Long id) {
		return alertSenderRepository.findById(id);
	}


	@Override
	public List<AlertSender> findByTriggerIdAndAlertSendLevel(String resType , String resId,String alertSendLevel,int pageNum,int  pageSize) {
		 Page<AlertSender> pageList=	alertSenderRepository.findByResTypeAndResIdAndAlertSendLevel(resType, resId, alertSendLevel, new PageRequest(pageNum-1, pageSize));
		   if(pageList!=null){
			   return pageList.getContent();
		   }
		   return null;
	}


	@Override
	public AlertSender save(AlertSender as) {
		return alertSenderRepository.save(as);
	}


	@Override
	public void delete(Long id) {
	    alertSenderRepository.delete(id);
	}
	
	@Override
	public Map<String, Object> findByMutilColumn(String resType,
			String resName, String alertSendLevel, int pageNum, int pageSize) {
		
		   Map<String ,Object> map=new HashMap<String,Object>();
		   Page<AlertSender> pageList=	alertSenderRepository.findAll(new Specification<AlertSender>() {

			@Override
			public Predicate toPredicate(Root<AlertSender> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				List<Expression<Boolean>> expressions=predicate.getExpressions();
				if(!StringUtils.isEmpty(resType)){
					expressions.add(cb.equal(root.get("resType"), resType));
				}
				if(!StringUtils.isEmpty(alertSendLevel)){
					expressions.add(cb.equal(root.get("alertSendLevel"), alertSendLevel));
				}
				if(!StringUtils.isEmpty(resName)){
					expressions.add(cb.like(root.get("resName"), "%"+resName+"%"));
				}
				return predicate;
			}
		},new PageRequest(pageNum-1, pageSize));

		   if(pageList!=null){
			   map.put("total", pageList.getTotalElements());
			   map.put("rows", pageList.getContent());
			   return map;
		   }
		   return null;
	}


	@Override
	public List<AlertSender> findByResIdAndAlertId(String resId, String alertId) {
		return  alertSenderRepository.findByResIdAndTriggerId(resId, alertId);
	}
	
	@Override
	public AlertSender update(AlertSender as) {
		return alertSenderRepository.saveAndFlush(as);
	}


	@Override
	public Long countByResType(String resType) {
		return alertSenderRepository.countByResType(resType);
	}
	
	@Override
	public List<AlertSender> findByResType(String resType){
		return alertSenderRepository.findByResType(resType);
	}
	@Override
	public Long countByResTypeAndResId(String resType,String resId){
		return alertSenderRepository.countByResTypeAndResId(resType, resId);
	}
	
	@Override
	public List<AlertSender> findByResTypeAndResId(String resType,String resId){
		return alertSenderRepository.findByResTypeAndResId(resType, resId);
	}
	@Override
	public Long countByResTypeAndResIdAndTriggerId(String resType,String resId,String triggerId){
		return alertSenderRepository.countByResTypeAndResIdAndTriggerId(resType, resId, triggerId);
	}
	@Override
	public List<AlertSender> findByResTypeAndResIdAndTriggerId(String resType,String resId,String triggerId){
		return alertSenderRepository.findByResTypeAndResIdAndTriggerId( resType, resId, triggerId);
	}

	@Override
	public List<AlertSender> findAll(List<Long> ids) {
		return alertSenderRepository.findAll(ids);
	}


	@Override
	public void deleteInBatch(List<AlertSender> ids) {
		 alertSenderRepository.deleteInBatch(ids);
	}
	
}
