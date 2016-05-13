/**
 * Created on 2016年3月31日
 */
package com.sugon.cloudview.cloudmanager.alert.serviceImpl.dao.service;

import java.util.List;

import com.sugon.cloudview.cloudmanager.alert.serviceImpl.entity.AlertSyncTag;

/**
 * 功能名: 请填写功能名
 * 功能描述: 请简要描述功能的要点
 * Copyright: Copyright (c) 2016
 * 公司: 曙光云计算技术有限公司
 *
 * @author yangkun
 * @version 2.0.0 sp1
 */
public interface AlertSyncTagDaoServiceI {
	
	public List<AlertSyncTag>findAll();
	

	public AlertSyncTag save(AlertSyncTag ast );
	

	public AlertSyncTag update(AlertSyncTag as);

}
