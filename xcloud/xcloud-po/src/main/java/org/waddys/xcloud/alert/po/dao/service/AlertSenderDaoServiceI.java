/**
 * Created on 2016年3月25日
 */
package org.waddys.xcloud.alert.po.dao.service;

import java.util.List;
import java.util.Map;

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
public interface AlertSenderDaoServiceI {

    public List<AlertSender>findAll();
    
    public List<AlertSender>findAll(List<Long>ids);
	
	public Map<String, Object> findAll(int pageNum ,int  pageSize);
	
	public  Map<String, Object> findByMutilColumn(String resType, String resName, String alertSendLevel ,int pageNum ,int  pageSize);
	
	public  AlertSender findById(Long id);
	
	public  List<AlertSender> findByTriggerIdAndAlertSendLevel(String resType , String resId,String alertSendLevel,int pageNum,int  pageSize);
	
	public AlertSender save(AlertSender as);

	public void delete(Long id);
	
	public void  deleteInBatch(List<AlertSender> id);
	
	public List<AlertSender> findByResIdAndAlertId(String resId, String alertId) ;
	
	public AlertSender update(AlertSender as);
	
	public Long countByResType(String resType);
	
	public List<AlertSender> findByResType(String resType);
	
	public Long countByResTypeAndResId(String resType,String resId);
	
	public List<AlertSender> findByResTypeAndResId(String resType,String resId);
	
	public Long countByResTypeAndResIdAndTriggerId(String resType,String resId,String triggerId);
	
	public List<AlertSender> findByResTypeAndResIdAndTriggerId(String resType,String resId,String triggerId);
}
