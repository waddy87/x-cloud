/**
 * Created on 2016年3月28日
 */
package org.waddys.xcloud.alert.bo;

/**
 * 功能名: 请填写功能名
 * 功能描述: 请简要描述功能的要点
 * Copyright: Copyright (c) 2016
 * 公司: 曙光云计算技术有限公司
 *
 * @author yangkun
 * @version 2.0.0 sp1
 */
public class TriggerInfoUI {

	//记录触发器的名称
	private String TriggerName;
	
	private  String TriggerId;

	/**
	 * @return the triggerName
	 */
	public String getTriggerName() {
		return TriggerName;
	}

	/**
	 * @param triggerName the triggerName to set
	 */
	public void setTriggerName(String triggerName) {
		TriggerName = triggerName;
	}

	/**
	 * @return the triggerId
	 */
	public String getTriggerId() {
		return TriggerId;
	}

	/**
	 * @param triggerId the triggerId to set
	 */
	public void setTriggerId(String triggerId) {
		TriggerId = triggerId;
	}


	
}
