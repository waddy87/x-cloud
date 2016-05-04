/**
 * Created on 2016年3月31日
 */
package com.sugon.cloudview.cloudmanager.alert.serviceImpl.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 功能名: 请填写功能名
 * 功能描述: 请简要描述功能的要点
 * Copyright: Copyright (c) 2016
 * 公司: 曙光云计算技术有限公司
 *
 * @author yangkun
 * @version 2.0.0 sp1
 */

@Entity
@Table(name="Alertsynctag")
public class AlertSyncTag implements Serializable{

	/**
	 * 请描述变量的功能
	 */
	private static final long serialVersionUID = 876925043701280456L;
	
	@Id
	@GeneratedValue(generator="latestAlertTime")
	@GenericGenerator(name="latestAlertTime",strategy="native")
	private Long id;
	
	private Long latestAlertTime;

	 
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the latestAlertTime
	 */
	public Long getLatestAlertTime() {
		return latestAlertTime;
	}

	/**
	 * @param latestAlertTime the latestAlertTime to set
	 */
	public void setLatestAlertTime(Long latestAlertTime) {
		this.latestAlertTime = latestAlertTime;
	}
	
	
}
