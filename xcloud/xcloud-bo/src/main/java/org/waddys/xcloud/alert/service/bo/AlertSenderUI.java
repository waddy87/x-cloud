/**
 * Created on 2016年3月25日
 */
package org.waddys.xcloud.alert.service.bo;

import java.util.Date;

/**
 * 功能名: 请填写功能名
 * 功能描述: 请简要描述功能的要点
 * Copyright: Copyright (c) 2016
 * 公司: 曙光云计算技术有限公司
 *
 * @author yangkun
 * @version 2.0.0 sp1
 */
public class AlertSenderUI {

		// AlertSender实体相关的信息
		private  Long id;
		
		private  String name;
		
		private String alertSendLevel;
		
		private String sendType;
		
		private String receiver;
		
		private String  message;

		private boolean isEnable;
		
		private Date sendDate;
		
		//	Trigger实体对应的信息
		private String triggerId;
		
		private String triggerName;
		
		private String resType;//(全部资源类型/单个资源类型)
		
		private String resId;//String(全部资源/单个资源)
		
		private String resName;//String(全部资源/单个资源)
		
		private String triggerDetail;
		
		private String alertLevel;

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
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the sendType
		 */
		public String getSendType() {
			return sendType;
		}

		/**
		 * @param sendType the sendType to set
		 */
		public void setSendType(String sendType) {
			this.sendType = sendType;
		}

		/**
		 * @return the receiver
		 */
		public String getReceiver() {
			return receiver;
		}

		/**
		 * @param receiver the receiver to set
		 */
		public void setReceiver(String receiver) {
			this.receiver = receiver;
		}

		/**
		 * @return the message
		 */
		public String getMessage() {
			return message;
		}

		/**
		 * @param message the message to set
		 */
		public void setMessage(String message) {
			this.message = message;
		}

		/**
		 * @return the isEnable
		 */
		public boolean isEnable() {
			return isEnable;
		}

		/**
		 * @param isEnable the isEnable to set
		 */
		public void setEnable(boolean isEnable) {
			this.isEnable = isEnable;
		}

		/**
		 * @return the triggerId
		 */
		public String getTriggerId() {
			return triggerId;
		}

		/**
		 * @param triggerId the triggerId to set
		 */
		public void setTriggerId(String triggerId) {
			this.triggerId = triggerId;
		}

		/**
		 * @return the triggerName
		 */
		public String getTriggerName() {
			return triggerName;
		}

		/**
		 * @param triggerName the triggerName to set
		 */
		public void setTriggerName(String triggerName) {
			this.triggerName = triggerName;
		}

		/**
		 * @return the resType
		 */
		public String getResType() {
			return resType;
		}

		/**
		 * @param resType the resType to set
		 */
		public void setResType(String resType) {
			this.resType = resType;
		}

		/**
		 * @return the resId
		 */
		public String getResId() {
			return resId;
		}

		/**
		 * @param resId the resId to set
		 */
		public void setResId(String resId) {
			this.resId = resId;
		}

		/**
		 * @return the resName
		 */
		public String getResName() {
			return resName;
		}

		/**
		 * @param resName the resName to set
		 */
		public void setResName(String resName) {
			this.resName = resName;
		}

		/**
		 * @return the triggerDetail
		 */
		public String getTriggerDetail() {
			return triggerDetail;
		}

		/**
		 * @param triggerDetail the triggerDetail to set
		 */
		public void setTriggerDetail(String triggerDetail) {
			this.triggerDetail = triggerDetail;
		}

		/**
		 * @return the alertSendLevel
		 */
		public String getAlertSendLevel() {
			return alertSendLevel;
		}

		/**
		 * @param alertSendLevel the alertSendLevel to set
		 */
		public void setAlertSendLevel(String alertSendLevel) {
			this.alertSendLevel = alertSendLevel;
		}

		/**
		 * @return the alertLevel
		 */
		public String getAlertLevel() {
			return alertLevel;
		}

		/**
		 * @param alertLevel the alertLevel to set
		 */
		public void setAlertLevel(String alertLevel) {
			this.alertLevel = alertLevel;
		}
		
		

		/**
		 * @return the sendDate
		 */
		public Date getSendDate() {
			return sendDate;
		}

		/**
		 * @param sendDate the sendDate to set
		 */
		public void setSendDate(Date sendDate) {
			this.sendDate = sendDate;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "AlertSenderUI [id=" + id + ", name=" + name
					+ ", alertSendLevel=" + alertSendLevel + ", sendType="
					+ sendType + ", receiver=" + receiver + ", message="
					+ message + ", isEnable=" + isEnable + ", sendDate="
					+ sendDate + ", triggerId=" + triggerId + ", triggerName="
					+ triggerName + ", resType=" + resType + ", resId=" + resId
					+ ", resName=" + resName + ", triggerDetail="
					+ triggerDetail + ", alertLevel=" + alertLevel + "]";
		}


}
