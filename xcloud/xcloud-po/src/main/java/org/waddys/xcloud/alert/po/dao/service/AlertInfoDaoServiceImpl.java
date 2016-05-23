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
import org.waddys.xcloud.alert.po.dao.repository.AlertInfoRepository;
import org.waddys.xcloud.alert.po.entity.AlertInfo;

/**
 * 功能名: 请填写功能名
 * 功能描述: 请简要描述功能的要点
 * Copyright: Copyright (c) 2016
 * 公司: 曙光云计算技术有限公司
 *
 * @author yangkun
 * @version 2.0.0 sp1
 */
@Component("alertInfoDaoServiceImpl")
@Transactional
public class AlertInfoDaoServiceImpl implements AlertInfoDaoServiceI {

	  @Autowired
	  private AlertInfoRepository alertInfoRepository;
	

	@Override
	public List<AlertInfo> findAll() {
		return alertInfoRepository.findAll();

	}

	@Override
	public Map<String, Object>findAll(int pageNum , int pageSize ) {
		      Map<String ,Object> map=new HashMap<String,Object>();
			   Page<AlertInfo> pageList=	alertInfoRepository.findAll( new PageRequest(pageNum-1, pageSize));
			   if(pageList!=null){
				   map.put("total", pageList.getTotalElements());
				   map.put("rows", pageList.getContent());
				   return map;
			   }
			   return null;
	}


	@Override
	public AlertInfo findById(Long id) {
		return alertInfoRepository.findById(id);
	}

	@Override
	public Map<String, Object> findByMutilColumn(String time,
			String resType, String resName, String alertLevel, int pageNum, int pageSize,List<String> resIds) {
		
		   Map<String ,Object> map=new HashMap<String,Object>();
		   Page<AlertInfo> pageList=	alertInfoRepository.findAll(new Specification<AlertInfo>() {

			@Override
			public Predicate toPredicate(Root<AlertInfo> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				List<Expression<Boolean>> expressions=predicate.getExpressions();
				if(!StringUtils.isEmpty(time)){
					if(time.contains("now"))
					{
						expressions.add(cb.equal(root.get("isHistory"), false));
					}else if(time.contains("history")){
						expressions.add(cb.equal(root.get("isHistory"), true));
					}
				}
				if(!StringUtils.isEmpty(resType)){
					expressions.add(cb.equal(root.get("resType"), resType));
				}
				if(!StringUtils.isEmpty(alertLevel)){
					expressions.add(cb.equal(root.get("alertLevel"), alertLevel));
				}
				if(!StringUtils.isEmpty(resName)){
					expressions.add(cb.like(root.get("resName"), "%"+resName+"%"));
				}
				if(!StringUtils.isEmpty(resIds) && resIds.size()>0){
					expressions.add(cb.in(root.get("resId")).value(resIds));
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
	public Map<String, Object> findByMutilColumn(String resType,
			String resName, String alertLevel, int pageNum, int pageSize) {
		
		   Map<String ,Object> map=new HashMap<String,Object>();
		   Page<AlertInfo> pageList=	alertInfoRepository.findAll(new Specification<AlertInfo>() {

			@Override
			public Predicate toPredicate(Root<AlertInfo> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				List<Expression<Boolean>> expressions=predicate.getExpressions();
				if(!StringUtils.isEmpty(resType)){
					expressions.add(cb.equal(root.get("resType"), resType));
				}
				if(!StringUtils.isEmpty(alertLevel)){
					expressions.add(cb.equal(root.get("alertLevel"), alertLevel));
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
	public AlertInfo save(AlertInfo as) {
		return alertInfoRepository.save(as);
	}

	@Override
	public void delete(Long id) {
		alertInfoRepository.delete(id);
	}

	@Override
	public Long findIdByResIdAndAlertId(String resId, String alertId) {
		List<AlertInfo> aList=  alertInfoRepository.findByResIdAndAlertId(resId, alertId);
		if(aList!=null && !aList.isEmpty()){
			return aList.get(0).getId();
		}
		return null;
	}
	
	@Override
	public AlertInfo findByResIdAndAlertId(String resId, String alertId) {
		List<AlertInfo> aList=  alertInfoRepository.findByResIdAndAlertId(resId, alertId);
		if(aList!=null && !aList.isEmpty()){
			return aList.get(0);
		}
		return null;
	}


	@Override
	public AlertInfo update(AlertInfo as) {
		return alertInfoRepository.saveAndFlush(as);
	}

	@Override
	public long countAll() {
		return  alertInfoRepository.count();
	}

	
	@Override
	public long countByMutiColumn(String isAquire, String resName,
			String resType, String alertLevel) {
		return alertInfoRepository.countByMutiColumn(isAquire, resName, resType, alertLevel);
	}
	
	@Override
	public List<AlertInfo>findByIdNotIn(List<Long> ids){
		return alertInfoRepository.findByIdNotIn(ids);
	}
	
	@Override
	public List<AlertInfo> findAll(List<Long> ids) {
		return alertInfoRepository.findAll(ids);
	}

	@Override
	public void deleteInBatch(List<AlertInfo> ids) {
		alertInfoRepository.deleteInBatch(ids);
	}
	
}
