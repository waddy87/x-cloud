package com.sugon.cloudview.cloudmanager.vijava.environment;

import com.sugon.cloudview.cloudmanager.vijava.base.Answer;
import com.sugon.cloudview.cloudmanager.vijava.base.Cmd;

public class ConnectCloudVM {

	public static class ConnectCloudVMCmd extends Cmd<ConnectCloudVMAnswer> {

		private static final long serialVersionUID = -7092621558056031448L;

		private String name;
		private String ip;
		private String username;
		private String userpwd;

		public String getName() {
			return name;
		}

		public ConnectCloudVMCmd setNmame(String evrNmame) {
			this.name = evrNmame;
			return this;
		}

		
		public String getIp() {
			return ip;
		}
		
		
		public ConnectCloudVMCmd setIp(String ip) {
			this.ip = ip;
			return this;
		}

		public String getUsername() {
			return username;
		}

		public ConnectCloudVMCmd setUsername(String username) {
			this.username = username;
			return this;
		}

		public String getUserpwd() {
			return userpwd;
		}

		public ConnectCloudVMCmd setUserpwd(String userpwd) {
			this.userpwd = userpwd;
			return this;
		}

		public String getToken() {
			return token;
		}

		public ConnectCloudVMCmd setToken(String token) {
			this.token = token;
			return this;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return "ConnectCloudVMCmd [ip=" + ip + ", username="
					+ username + "]";
		}
	}

	public static class ConnectCloudVMAnswer extends Answer {

		private static final long serialVersionUID = -5306291453503047949L;
		private String version;
		private String token;


		public String getVersion() {
			return version;
		}

		public ConnectCloudVMAnswer setVersion(String version) {
			this.version = version;
			return this;
		}
		
		public String getToken() {
			return token;
		}

		public ConnectCloudVMAnswer setToken(String token) {
			this.token = token;
			return this;
		}

		@Override
		public String toString() {
			return "ConnectCloudVMAnswer [sucess=" + isSuccess() + ", version="
					+ version + ", token=" + token + "]";
		}
	}

	public static void main(String args[]) {

		ConnectCloudVMCmd cmd = new ConnectCloudVMCmd();
		System.out.println(cmd.cmdDesc);
	}
}
