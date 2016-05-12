package com.sugon.cloudview.cloudmanager.event.type;

import java.util.ArrayList;

public enum ExchangeName {

	DefaultExchange("DefaultExchange"), 
	DirectExchange("DirectExchange"), 
	TopicExchange("TopicExchange");

	private String name;

	private ExchangeName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<String> getAllExchangeName(){
		
		ArrayList<String> arrayExchangeName = new ArrayList<String>();
		for(ExchangeName exchangeName:ExchangeName.values()){
			arrayExchangeName.add(exchangeName.getName());
		}
		return arrayExchangeName;
	}

}
