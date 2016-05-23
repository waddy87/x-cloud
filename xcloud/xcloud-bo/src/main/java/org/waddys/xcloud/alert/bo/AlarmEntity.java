/**
 * 
 */
package org.waddys.xcloud.alert.bo;

import java.util.Date;


/**
 * @author dengjq
 *
 */
public class AlarmEntity implements Comparable<AlarmEntity>{

	//告警id
	String id;	
	//告警名称
	String name;
	//告警描述
	String description;
	//告警级别
	String level;
	//告警所在的管理对象，例如虚拟机，物理机等
	String entityId;
	
	//告警所在的管理对象名称，例如虚拟机，物理机等
	String entityName;
	
	//告警发生的时间，改为时间戳的方式。具有唯一性，time可以作为alert的唯一ID
	Long time;
	
	//告警确认时间 
	String acknowledgedTime;
	
	//告警确认用户
	String acknowledgedUser;
	
	//是否被确认
	boolean acknowledged;
	
	
	//触发器规则详情  （add by yangkun）
	 private String triggerDetail;
	
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	String key;
	
	/**
	 * @return the acknowledged
	 */
	public boolean isAcknowledged() {
		return acknowledged;
	}

	/**
	 * @param acknowledged the acknowledged to set
	 */
	public void setAcknowledged(boolean acknowledged) {
		this.acknowledged = acknowledged;
	}

	/**
	 * @return the acknowledgedTime
	 */
	public String getAcknowledgedTime() {
		return acknowledgedTime;
	}

	/**
	 * @param acknowledgedTime the acknowledgedTime to set
	 */
	public void setAcknowledgedTime(String acknowledgedTime) {
		this.acknowledgedTime = acknowledgedTime;
	}

	/**
	 * @return the acknowledgedUser
	 */
	public String getAcknowledgedUser() {
		return acknowledgedUser;
	}

	/**
	 * @param acknowledgedUser the acknowledgedUser to set
	 */
	public void setAcknowledgedUser(String acknowledgedUser) {
		this.acknowledgedUser = acknowledgedUser;
	}

	/**
	 * @return the entityName
	 */
	public String getEntityName() {
		return entityName;
	}
	
	/**
	 * @param entityName the entityName to set
	 */
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	/**
	 * 
	 */
	public AlarmEntity() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
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
	 * @return the time
	 */
	public Long getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Long time) {
		this.time = time;
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
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	/**
	 * @return the entityName
	 */
	public String getEntityId() {
		return entityId;
	}
	/**
	 * @param entityName the entityName to set
	 */
	public void setEntityId(String entityId) {
		this.entityId = entityId;
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


	//已触发时间进行排序，降序排列
	@Override
	public int compareTo(AlarmEntity o) {
		// TODO Auto-generated method stub
		if(this.time.compareTo(o.time) < 0){
			return 1;
		}else if (this.time.compareTo(o.time) > 0){
			return -1;
		}else{
			return 0;
		}
	}

	
}
