package com.sugon.cloudview.cloudmanager.vijava.environment;

import java.util.List;

import com.sugon.cloudview.cloudmanager.vijava.base.Answer;
import com.sugon.cloudview.cloudmanager.vijava.base.Cmd;
import com.sugon.cloudview.cloudmanager.vijava.data.Cluster;

public class QueryCluster {
	public static class QueryClusterCmd extends Cmd<QueryClusterAnswer> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -60510581061169713L;
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
		public QueryClusterCmd setIp(String ip) {
			this.ip = ip;
			return this;
		}
		

		public String getToken() {
			return token;
		}

		public QueryClusterCmd setToken(String token) {
			this.token = token;
			return this;
		}

	}

	public static class QueryClusterAnswer extends Answer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2198085005330383631L;
		private List<Cluster> clusterList;

		public List<Cluster> getClusterList() {
			return clusterList;
		}

		public QueryClusterAnswer setClusterList(List<Cluster> clusterList) {
			this.clusterList = clusterList;
			return this;
		}

	}
}
