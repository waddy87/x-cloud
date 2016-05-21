package org.waddys.xcloud.event.type;

import java.io.Serializable;
import java.util.Arrays;

public class EventMessage implements Serializable{

	private static final long serialVersionUID = -7889126258299289588L;

	private String routeKey;
	
	private String exchangeName;
	
	private byte[] eventData;

	public EventMessage(String exchangeName, String routeKey, byte[] eventData) {
		this.routeKey = routeKey;
		this.exchangeName = exchangeName;
		this.eventData = eventData;
	}

	public EventMessage() {
	}	

	public String getRouteKey() {
		return routeKey;
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public byte[] getEventData() {
		return eventData;
	}

	@Override
	public String toString() {
		return "EopEventMessage [routeKey=" + routeKey + ", exchangeName="
				+ exchangeName + ", eventData=" + Arrays.toString(eventData)
				+ "]";
	}
}
