package com.sugon.cloudview.cloudmanager.vijava.environment;

import java.util.List;

import com.sugon.cloudview.cloudmanager.vijava.base.Answer;
import com.sugon.cloudview.cloudmanager.vijava.base.Cmd;
import com.sugon.cloudview.cloudmanager.vijava.data.ResPool;
import com.sugon.cloudview.cloudmanager.vijava.data.StoragePool;

public class QueryResources {

	public static class QueryResourceCmd extends Cmd<QueryResourceAnswer> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4496443706289908195L;
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
		public QueryResourceCmd setIp(String ip) {
			this.ip = ip;
			return this;
		}
		
		public String getToken() {
			return token;
		}

		public QueryResourceCmd setToken(String token) {
			this.token = token;
			return this;
		}
	}

	public static class QueryResourceAnswer extends Answer {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4242200827107876716L;
		private List<ResPool> rpList;

		public List<ResPool> getRpList() {
			return rpList;
		}

		public QueryResourceAnswer setRpList(List<ResPool> rpList) {
			this.rpList = rpList;
			return this;
		}

		@Override
		public String toString() {
			
			String result = "";
			result += "isSucess:" + isSuccess() + " ,";
			
		/*	for (ResPool rp : rpList) {
				result += "id:" + rp.getId() + "\n";
				result += "name:" + rp.getName() + "\n";
				result += "cpu:" + rp.getCpuTotal() + " used "
						+ rp.getCpuUsed() + "\n";
				result += "memory:" + rp.getMemoryTotal() + " used "
						+ rp.getMemoryUsed() + "\n";
				
				List<StoragePool> spList = rp.getStoragePools();
				for (StoragePool sp : spList) {
					result += "id:" + sp.getId() + "\n";
					result += "name:" + sp.getName() + "\n";
					result += "storage:" + sp.getTotal() + " used "
							+ sp.getAvailable() + "\n";
				

				}
			}*/
			for (ResPool rp : rpList) 
				result += rp;
			return result;
		}

	}
}
