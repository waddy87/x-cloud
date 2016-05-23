package org.waddys.xcloud.alert.po.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="Alertinfo")
public class AlertInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3300806727601583895L;
	
	//AlertInfo实体相关信息
	@Id
	@GeneratedValue(generator="aiid")
	@GenericGenerator(name="aiid",strategy="native")
	private Long id;
	
	private int senderStatus;
	
	private Date sendTime;
	
	private Long alertSendId;
	

	//Alert实体相关信息
    private String alertId;
    
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
    
    @OneToMany(fetch=FetchType.LAZY,
    		cascade=CascadeType.ALL,
    		mappedBy="alertInfoId",
    		targetEntity=AlertInfoStatus.class
    		)
    @LazyCollection(LazyCollectionOption.EXTRA)
    private List<AlertInfoStatus> emailStatus;

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
	 * @return the alertId
	 */
	public String getAlertId() {
		return alertId;
	}

	/**
	 * @param alertId the alertId to set
	 */
	public void setAlertId(String alertId) {
		this.alertId = alertId;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

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

	/**
	 * @return the emailStatus
	 */
	public List<AlertInfoStatus> getEmailStatus() {
		return emailStatus;
	}

	/**
	 * @param emailStatus the emailStatus to set
	 */
	public void setEmailStatus(List<AlertInfoStatus> emailStatus) {
		this.emailStatus = emailStatus;
	}


	
}
