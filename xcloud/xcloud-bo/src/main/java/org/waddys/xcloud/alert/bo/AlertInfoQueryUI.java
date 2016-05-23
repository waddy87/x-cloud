/**
 * Created on 2016年3月25日
 */
package org.waddys.xcloud.alert.bo;

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
public class AlertInfoQueryUI {

	//AlertInfo实体相关信息
	private Long id;
	
	private int senderStatus;
	
	private Date sendTime;
	
	private Long alertSendId;
	

	//Alert实体相关信息
    private Long alrtId;
    
    private String alertName;
    
    private String resId;
    
    private String resType;

    private String resName;
	
    private String triggerId;
	
    private String triggerDetail;
    
    private String alertLevel;
	
    private Date   alertTime;

    private boolean isAquire;
    
    private boolean isHistory;
    
	
    private Date aquireDate;
	
    private String aquireUser;
    
	//告警描述
    private String description;

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

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
	 * @return the senderStatus
	 */
	public int getSenderStatus() {
		return senderStatus;
	}

	/**
	 * @param senderStatus the senderStatus to set
	 */
	public void setSenderStatus(int senderStatus) {
		this.senderStatus = senderStatus;
	}

	/**
	 * @return the sendTime
	 */
	public Date getSendTime() {
		return sendTime;
	}

	/**
	 * @param sendTime the sendTime to set
	 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	/**
	 * @return the alertSendId
	 */
	public Long getAlertSendId() {
		return alertSendId;
	}

	/**
	 * @param alertSendId the alertSendId to set
	 */
	public void setAlertSendId(Long alertSendId) {
		this.alertSendId = alertSendId;
	}

	/**
	 * @return the alrtId
	 */
	public Long getAlrtId() {
		return alrtId;
	}

	/**
	 * @param alrtId the alrtId to set
	 */
	public void setAlrtId(Long alrtId) {
		this.alrtId = alrtId;
	}

	/**
	 * @return the alertName
	 */
	public String getAlertName() {
		return alertName;
	}

	/**
	 * @param alertName the alertName to set
	 */
	public void setAlertName(String alertName) {
		this.alertName = alertName;
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
	 * @return the alertTime
	 */
	public Date getAlertTime() {
		return alertTime;
	}

	/**
	 * @param alertTime the alertTime to set
	 */
	public void setAlertTime(Date alertTime) {
		this.alertTime = alertTime;
	}

	/**
	 * @return the isAquire
	 */
	public boolean isAquire() {
		return isAquire;
	}

	/**
	 * @param isAquire the isAquire to set
	 */
	public void setAquire(boolean isAquire) {
		this.isAquire = isAquire;
	}

	/**
	 * @return the aquireDate
	 */
	public Date getAquireDate() {
		return aquireDate;
	}

	/**
	 * @param aquireDate the aquireDate to set
	 */
	public void setAquireDate(Date aquireDate) {
		this.aquireDate = aquireDate;
	}

	/**
	 * @return the aquireUser
	 */
	public String getAquireUser() {
		return aquireUser;
	}

	/**
	 * @param aquireUser the aquireUser to set
	 */
	public void setAquireUser(String aquireUser) {
		this.aquireUser = aquireUser;
	}

	
	/**
	 * @return the isHistory
	 */
	public boolean isHistory() {
		return isHistory;
	}

	/**
	 * @param isHistory the isHistory to set
	 */
	public void setHistory(boolean isHistory) {
		this.isHistory = isHistory;
	}

	@Override
	public String toString() {
		return "AlertInfoQueryUI [id=" + id + ", senderStatus=" + senderStatus
				+ ", sendTime=" + sendTime + ", alertSendId=" + alertSendId
				+ ", alrtId=" + alrtId + ", alertName=" + alertName
				+ ", resId=" + resId + ", resType=" + resType + ", resName="
				+ resName + ", triggerId=" + triggerId + ", triggerDetail="
				+ triggerDetail + ", alertLevel=" + alertLevel + ", alertTime="
				+ alertTime + ", isAquire=" + isAquire + ", isHistory="
				+ isHistory + ", aquireDate=" + aquireDate + ", aquireUser="
				+ aquireUser + ", description=" + description + "]";
	}
    
}
