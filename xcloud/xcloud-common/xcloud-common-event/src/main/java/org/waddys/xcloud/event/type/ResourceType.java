package org.waddys.xcloud.event.type;

import java.util.ArrayList;


public enum ResourceType{

	host("host"), 
	vm("vm");

	private String name;

	private ResourceType(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
	
	public static ArrayList<String> getAllResourceType(){
		
		ArrayList<String> arrayResourceType = new ArrayList<String>();
		for(ResourceType module:ResourceType.values()){
			arrayResourceType.add(module.toString());
		}
		return arrayResourceType;
	}	
	
	public static String getResourceType(String resourceTypeName){
		
		for(ResourceType module:ResourceType.values()){
			if(module.toString().equals(resourceTypeName)){
				return resourceTypeName;
			}
		}
		return "*";
	}	
	
	
	public static String getResourceType(String[] resourceTypeNames){
		
		for(ResourceType module:ResourceType.values()){
			for(String resourceTypeName: resourceTypeNames){
				if(module.toString().equals(resourceTypeName)){
					return resourceTypeName;
				}
			}
		}
		return "*";
	}	
}
