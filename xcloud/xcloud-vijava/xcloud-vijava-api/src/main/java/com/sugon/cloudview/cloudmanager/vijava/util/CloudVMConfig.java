package com.sugon.cloudview.cloudmanager.vijava.util;

public class CloudVMConfig {
	
	public String configDate = null;
	
	public ConnectionInfo vcenterInfo[];
	
	public static class ConnectionInfo{
		
		private String vcenter_ip = null;
		
		private String vcenter_port = null;
		
		private String vcenter_user = null;
		
		private String vcenter_passwd = null;
		
		private String vcenter_token = null;

		public String getVcenter_ip() {
			return vcenter_ip;
		}

		public void setVcenter_ip(String vcenter_ip) {
			this.vcenter_ip = vcenter_ip;
		}

		public String getVcenter_port() {
			return vcenter_port;
		}

		public void setVcenter_port(String vcenter_port) {
			this.vcenter_port = vcenter_port;
		}

		public String getVcenter_user() {
			return vcenter_user;
		}

		public void setVcenter_user(String vcenter_user) {
			this.vcenter_user = vcenter_user;
		}

		public String getVcenter_passwd() {
			return vcenter_passwd;
		}

		public void setVcenter_passwd(String vcenter_passwd) {
			this.vcenter_passwd = vcenter_passwd;
		}

		public String getVcenter_token() {
			return vcenter_token;
		}

		public void setVcenter_token(String vcenter_token) {
			this.vcenter_token = vcenter_token;
		}

		@Override
		public String toString() {
			return "ConnectionInfo [vcenter_ip=" + vcenter_ip + ", vcenter_port=" + vcenter_port + ", vcenter_user="
					+ vcenter_user + ", vcenter_passwd=" + vcenter_passwd + ", vcenter_token=" + vcenter_token + "]";
		}
		
	}

	public String getConfigDate() {
		return configDate;
	}

	public void setConfigDate(String configDate) {
		this.configDate = configDate;
	}

	public ConnectionInfo[] getVcenterInfo() {
		return vcenterInfo;
	}

	public void setVcenterInfo(ConnectionInfo[] vcenterInfo) {
		this.vcenterInfo = vcenterInfo;
	}

	@Override
	public String toString() {
		return "CloudVMConfig [configDate=" + configDate + ", vcenterInfo=" + vcenterInfo + "]";
	}
}
