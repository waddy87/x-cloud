/**
 * Created on 2016年3月25日
 */
package com.sugon.cloudview.cloudmanager.alert.service.intf;

import java.util.List;
import java.util.Map;

import com.sugon.cloudview.cloudmanager.alert.service.bo.AlertInfoQueryUI;
import com.sugon.cloudview.cloudmanager.alert.service.bo.AlertSenderUI;
import com.sugon.cloudview.cloudmanager.alert.service.bo.ResourceInfoUI;
import com.sugon.cloudview.cloudmanager.alert.service.bo.TriggerInfoUI;

/**
 * 功能名: 请填写功能名
 * 功能描述: 请简要描述功能的要点
 * Copyright: Copyright (c) 2016
 * 公司: 曙光云计算技术有限公司
 *
 * @author yangkun
 * @version 2.0.0 sp1
 */
public interface AlertServiceI {

	//获取资源类型对应的资源列表
	List<ResourceInfoUI> getResListByResType(String resTypeName);
	
	//根据资源类型和资源名称获取触发器列表
	List<TriggerInfoUI> getTriggerListByResAndResType(String resType,String resId);
	
	 Map<String, Object> getAlertInfo(String time,String resType,String resName,String alrtLevel,int pageNum,int pageSize,List<String>resIds);
	
	 Map<String, Object> getAlertSender(String resType,String resName,String alertSenderLevel,int pageNum,int pageSize);
	
	boolean saveAlertSender(AlertSenderUI asUi);
	
	void aquireAlert(String resId,String triggerId) throws Exception;
	
	void deleteAlertSender(String id) throws Exception;
	
	void batchDeleteAlert(List<String>ids) throws Exception;
	
	void batchAquireAlert(List<String>ids) throws Exception;
	
	void batchDeleteAlertSender(List<String> ids) throws Exception;
	
	void  enableAlertSender(String id,boolean enable) throws Exception;
	
	void  batchEnableAlertSender(List<String> ids,boolean enable) throws Exception;

}
