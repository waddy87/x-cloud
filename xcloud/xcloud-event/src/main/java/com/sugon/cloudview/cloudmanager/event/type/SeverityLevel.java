package com.sugon.cloudview.cloudmanager.event.type;

import java.util.ArrayList;


public enum SeverityLevel{

		info("info"), 
		error("error");

		private String name;

		private SeverityLevel(String name) {
			this.name = name;
		}
		
		public String toString() {
			return name;
		}
		
		public ArrayList<String> getAllSeverity(){
			
			ArrayList<String> arraySeverityLevel = new ArrayList<String>();
			for(SeverityLevel severity:SeverityLevel.values()){
				arraySeverityLevel.add(severity.toString());
			}
			return arraySeverityLevel;
		}
		
		public static String getSeverityLevel(String severityLevelName){
			
			for(SeverityLevel severityLevel:SeverityLevel.values()){
				if(severityLevel.toString().equals(severityLevelName)){
					return severityLevelName;
				}
			}
			return "*";
		}	
		
		public static String getSeverityLevel(String[] severityLevelNames){
			
			for(SeverityLevel severityLevel:SeverityLevel.values()){
				for(String severityLevelName: severityLevelNames){
					if(severityLevel.toString().equals(severityLevelName)){
						return severityLevelName;
					}
				}
			}
			return "*";
		}	
}

