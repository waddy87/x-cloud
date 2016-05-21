/**
 * Created on 2016年3月31日
 */
package org.waddys.xcloud.alert.serviceImpl.dao.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.waddys.xcloud.alert.serviceImpl.dao.repository.AlertSyncTagRepository;
import org.waddys.xcloud.alert.serviceImpl.entity.AlertSyncTag;

/**
 * 功能名: 请填写功能名
 * 功能描述: 请简要描述功能的要点
 * Copyright: Copyright (c) 2016
 * 公司: 曙光云计算技术有限公司
 *
 * @author yangkun
 * @version 2.0.0 sp1
 */

@Component("alertSyncTagDaoServiceImpl")
@Transactional
public class AlertSyncTagDaoServiceImpl implements AlertSyncTagDaoServiceI {

	
	 @Autowired
	  private AlertSyncTagRepository alertSyncTagRepository;
	
	
	@Override
	public List<AlertSyncTag> findAll() {
		List<AlertSyncTag> all=alertSyncTagRepository.findAll();
		//数据为空时，默认添加一条为 1L的记录
		if(all==null || all.isEmpty()){
			AlertSyncTag defaultAlertSyncTag=	new AlertSyncTag();
			defaultAlertSyncTag.setLatestAlertTime(1L);
			save(defaultAlertSyncTag);
			List<AlertSyncTag> reSyncTags=new ArrayList<AlertSyncTag>();
			reSyncTags.add(defaultAlertSyncTag);
			return  reSyncTags;
		}
		return all;
	}

	@Override
	public AlertSyncTag save(AlertSyncTag ast) {
		return alertSyncTagRepository.save(ast);
	}

	@Override
	public AlertSyncTag update(AlertSyncTag as) {
		return alertSyncTagRepository.saveAndFlush(as);
	}

}
