/**
 * Created on 2016年4月13日
 */
package com.sugon.cloudview.cloudmanager.alert.serviceImpl.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="Alertinfostatus")
public class AlertInfoStatus implements Serializable {

		private static final long serialVersionUID = -4053281793162500815L;

		@Id
		@GeneratedValue(generator="aisid")
		@GenericGenerator(name="aisid",strategy="native")
		private Long  id;
		
		@ManyToOne
		@JoinColumn(name="alertInfoId",updatable=false)
		private AlertInfo alertInfoId;
		
		private String receiver ;
		
		private String status;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public AlertInfo getAlertInfoId() {
			return alertInfoId;
		}

		public void setAlertInfoId(AlertInfo alertInfoId) {
			this.alertInfoId = alertInfoId;
		}

		public String getReceiver() {
			return receiver;
		}

		public void setReceiver(String receiver) {
			this.receiver = receiver;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}	
	
}
