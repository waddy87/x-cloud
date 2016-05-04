package com.sugon.cloudview.cloudmanager.vijava.environment;

import com.sugon.cloudview.cloudmanager.vijava.base.Answer;
import com.sugon.cloudview.cloudmanager.vijava.base.Cmd;

public class DeleteConnectInfo {
	public static class DeleteConnectInfoCmd extends Cmd<DeleteConnectInfoAnswer> {

		/**
		 * 
		 */
		
		
		private static final long serialVersionUID = 6325224947448413421L;
		
		public String getToken(){
			return this.token;
		}
		
		public DeleteConnectInfoCmd setToken(String token){
			this.token = token;
			return this;
		}
		
	}
	
	public static class DeleteConnectInfoAnswer extends Answer{

		/**
		 * 
		 */
		private static final long serialVersionUID = -7125290288887460566L;
		
	}
}
