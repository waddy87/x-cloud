package org.waddys.xcloud.monitor.service.exception;


public class CloudViewPerfException extends Exception{
	
	private static final long serialVersionUID = 5747903124031318619L;

	private String desc = "";
	public CloudViewPerfException() {
		// TODO Auto-generated constructor stub
		super();		
	}
	public CloudViewPerfException(String desc) {
		super();
		this.desc = desc;
	}
	public CloudViewPerfException(Exception e) {
		super(e);
		if (e instanceof CloudViewPerfException) {
			String getDesc = ((CloudViewPerfException) e).getDesc();
			this.desc = getDesc;
		}
	}

	public CloudViewPerfException(String desc, Exception e) {
		super(e);
		if (e instanceof CloudViewPerfException) {
			String getDesc = ((CloudViewPerfException) e).getDesc();
			this.desc = desc + " | " + getDesc;
		} else {
			this.desc = desc;
		}

	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
