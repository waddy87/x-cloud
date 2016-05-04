package com.sugon.cloudview.cloudmanager.vijava.environment;

import java.util.List;

import com.sugon.cloudview.cloudmanager.vijava.base.Answer;
import com.sugon.cloudview.cloudmanager.vijava.base.Cmd;
import com.sugon.cloudview.cloudmanager.vijava.data.NetPool;

public class QueryNetPool {

	public static class QueryNetPoolCmd extends Cmd<QueryNetPoolAnswer> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5041381136183873297L;
		private String ip;
		
		/** 
		 * @deprecated
		 * 
		 * This method is deprecated on 20160331 ,which is instead by getToken.
		 * 
		 */
		public String getIp() {
			return ip;
		}

		/** 
		 * @deprecated
		 * 
		 * This method is deprecated on 20160331 ,which is instead by setToken.
		 * 
		 */
		public QueryNetPoolCmd setIp(String ip) {
			this.ip = ip;
			return this;
		}
		
		public String getToken() {
			return token;
		}

		public QueryNetPoolCmd setToken(String token) {
			this.token = token;
			return this;
		}
	}

	public static class QueryNetPoolAnswer extends Answer {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6997352002038149302L;

		private List<NetPool> netList;

		public List<NetPool> getNetList() {
			return netList;
		}

		public QueryNetPoolAnswer setNetList(List<NetPool> netList) {
			this.netList = netList;
			return this;
		}

		@Override
		public String toString() {
			
			String result = "";
			result += "isSucess:" + isSuccess();
			for (NetPool np : netList) {
				result += "id:" + np.getId() + "\n";
				result += "name:" + np.getName() + "\n";
				result += "vlan:" + np.getVlan() + "\n";
			}
			
			return result;
		}

	}
}
