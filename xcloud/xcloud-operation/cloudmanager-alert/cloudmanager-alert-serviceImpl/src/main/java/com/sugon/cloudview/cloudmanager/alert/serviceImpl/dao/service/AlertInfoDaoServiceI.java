/**
 * Created on 2016年3月25日
 */
package com.sugon.cloudview.cloudmanager.alert.serviceImpl.dao.service;

import java.util.List;
import java.util.Map;

import com.sugon.cloudview.cloudmanager.alert.serviceImpl.entity.AlertInfo;
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
public interface AlertInfoDaoServiceI {

	public List<AlertInfo>findAll();
	
    public List<AlertInfo>findAll(List<Long>ids);
	
	public Map<String, Object> findAll(int pageNum ,int  pageSize);
	
	public  AlertInfo findById(Long id);
	
	public List<AlertInfo>findByIdNotIn(List<Long> ids);
	
	public  Map<String, Object> findByMutilColumn(String time,String resType, String resName, String alertLevel ,int pageNum ,int  pageSize,List<String> resIds);
	
	public  Map<String, Object> findByMutilColumn(String resType, String resName, String alertLevel ,int pageNum ,int  pageSize);
	
	public AlertInfo save(AlertInfo as);
	
	public AlertInfo update(AlertInfo as);
	
	//一个资源的告警记录，只保存最新的一条
	public Long findIdByResIdAndAlertId(String resId,String alertId);
	
	public AlertInfo findByResIdAndAlertId(String resId, String alertId) ;

	public void delete(Long id);
	
	public void  deleteInBatch(List<AlertInfo> id);

	public long countAll();
	
	//多条件查询，其中resName为模糊查询
	public long countByMutiColumn(String isAquire,String resName,String resType,String alertLevel);
}
