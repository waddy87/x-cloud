package org.waddys.xcloud.monitor.service.bo;

public class MetricValue {
	
	//采集时间
	String collectTime;
	
	//指标值
	String metricValue;

	public String getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(String collectTime) {
		this.collectTime = collectTime;
	}

	public String getMetricValue() {
		return metricValue;
	}

	public void setMetricValue(String metricValue) {
		this.metricValue = metricValue;
	}

	@Override
	public String toString() {
		return "MetricValue [collectTime=" + collectTime + ", metricValue="
				+ metricValue + "]";
	}
	
}
