package org.waddys.xcloud.event.api;

public interface EventProcesser {
	
	public void process(String routeKey, Object eventContent);
	
}
