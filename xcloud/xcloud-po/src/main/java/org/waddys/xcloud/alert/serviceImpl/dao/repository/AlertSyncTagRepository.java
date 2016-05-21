/**
 * Created on 2016年3月31日
 */
package org.waddys.xcloud.alert.serviceImpl.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
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
public interface AlertSyncTagRepository extends JpaRepository<AlertSyncTag, Long>{
	
	public List<AlertSyncTag>findAll();
	
	@SuppressWarnings("unchecked")
	public AlertSyncTag save(AlertSyncTag ast );
	
	@SuppressWarnings("unchecked")
	public AlertSyncTag saveAndFlush(AlertSyncTag as);
}
