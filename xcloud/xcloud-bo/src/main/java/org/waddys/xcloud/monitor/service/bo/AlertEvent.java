package org.waddys.xcloud.monitor.service.bo;

public class AlertEvent {
	
	//事件id
	private String  eventId;

	//资源名称
	private  String  name;
	
	//资源类型
	private String  type;
	
	//事件产生时间
	private String time;
	
	//事件级别
	private  String level;
	
	//事件内容
	private String content;

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "AlertEvent [eventId=" + eventId + ", name=" + name + ", type="
				+ type + ", time=" + time + ", level=" + level + ", content="
				+ content + "]";
	}
	
	
 } 
