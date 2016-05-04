package com.sugon.cloudview.cloudmanager.event.api;

public interface EventProcesser {
	
	public void process(String routeKey, Object eventContent);
	
}
