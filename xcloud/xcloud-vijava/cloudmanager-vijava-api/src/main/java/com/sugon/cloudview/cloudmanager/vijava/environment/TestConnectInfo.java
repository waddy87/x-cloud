package com.sugon.cloudview.cloudmanager.vijava.environment;

import com.sugon.cloudview.cloudmanager.vijava.base.Answer;
import com.sugon.cloudview.cloudmanager.vijava.base.Cmd;

public class TestConnectInfo {
	public static class TestConnectInfoCmd extends Cmd<TestConnectInfoAnswer> {

		private static final long serialVersionUID = 5377790358830866402L;
		private String name;
		private String ip;
		private String username;
		private String userpwd;

		public String getName() {
			return name;
		}

		public TestConnectInfoCmd setName(String name) {
			this.name = name;
			return this;
		}

		public String getIp() {
			return ip;
		}

		public TestConnectInfoCmd setIp(String ip) {
			this.ip = ip;
			return this;
		}

		public String getUsername() {
			return username;
		}

		public TestConnectInfoCmd setUsername(String username) {
			this.username = username;
			return this;
		}

		public String getUserpwd() {
			return userpwd;
		}

		public TestConnectInfoCmd setUserpwd(String userpwd) {
			this.userpwd = userpwd;
			return this;
		}
		
		public String getToken() {
			return token;
		}

		public TestConnectInfoCmd setToken(String token) {
			this.token = token;
			return this;
		}

		@Override
		public String toString() {
			return "TestConnectInfoCmd [name=" + name 
					+ ", ip=" + ip + ", username=" 
					+ username + ", userpwd=" + userpwd
					+ "]";
		}
	}
	

	public static class TestConnectInfoAnswer extends Answer {

		private static final long serialVersionUID = -5587033721671838663L;

		@Override
		public String toString() {
			return "TestConnectInfoAnswer [result=" + isSuccess() + "]";
		}
	}
	
}
