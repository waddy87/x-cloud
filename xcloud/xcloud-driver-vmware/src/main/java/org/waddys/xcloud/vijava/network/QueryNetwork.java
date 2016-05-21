package org.waddys.xcloud.vijava.network;

import java.util.List;

import org.waddys.xcloud.vijava.base.Answer;
import org.waddys.xcloud.vijava.base.Cmd;

import com.vmware.vim25.mo.DistributedVirtualPortgroup;
import com.vmware.vim25.mo.Network;

public class QueryNetwork {

	public static class QueryNetworkCmd extends Cmd<QueryNetworkAnswer> {

		/**
		 * 数据中心moid
		 */
		private String datacenterId;
		/**
		 * 主机moid
		 */
		private String hostId;
		/**
		 * 集群moid
		 */
		private String clusterId;
		/**
		 * 资源池moid
		 */
		private String resourcepoolId;
		/**
		 * 虚拟机moid
		 */
		private String vmId;

		public QueryNetworkCmd setDatacenterId(String datacenterId) {
			this.datacenterId = datacenterId;
			return this;
		}

		public QueryNetworkCmd setHostId(String hostId) {
			this.hostId = hostId;
			return this;
		}

		public QueryNetworkCmd setClusterId(String clusterId) {
			this.clusterId = clusterId;
			return this;
		}

		public QueryNetworkCmd setResourcepoolId(String resourcepoolId) {
			this.resourcepoolId = resourcepoolId;
			return this;
		}

		public QueryNetworkCmd setVmId(String vmId) {
			this.vmId = vmId;
			return this;
		}

		public String getDatacenterId() {
			return datacenterId;
		}

		public String getHostId() {
			return hostId;
		}

		public String getClusterId() {
			return clusterId;
		}

		public String getResourcepoolId() {
			return resourcepoolId;
		}

		public String getVmId() {
			return vmId;
		}

		public String getToken() {
			return token;
		}

		public QueryNetworkCmd setToken(String token) {
			this.token = token;
			return this;
		}

		@Override
		public String toString() {
			String result = "";
			result += "QueryNetworkCmd " + this.getDatacenterId() + " "
					+ this.getClusterId() + " " + this.getHostId() + " "
					+ this.getResourcepoolId() + this.getVmId();
			return result;
		}

		private static final long serialVersionUID = 7739191507687378175L;

	}

	public static class QueryNetworkAnswer extends Answer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 888343817758516799L;

		private List<Network> networkList;

		public List<Network> getNetworkList() {
			return networkList;
		}

		public QueryNetworkAnswer setNetworkList(List<Network> networkList) {
			this.networkList = networkList;
			return this;
		}

		@Override
		public String toString() {
			String result = "[";
			for (Network network : networkList) {
				if ("DistributedVirtualPortgroup".equals(network.getMOR()
						.getType())) {
					DistributedVirtualPortgroup dvp = (DistributedVirtualPortgroup) network;
					// String vdsid =
					// dvp.getConfig().getDistributedVirtualSwitch();
					// String vds =
					result += "{\"network\" :" + "\"" + network.getName()
					// + "("+dvp.getConfig().getDistributedVirtualSwitch()
							+ ")\"},";
				} else
					result += "{\"network\" :" + "\"" + network.getName()
							+ "\"},";

			}

			result += "]";
			// if(result.endsWith(",]"))
			result = result.replace(",]", "]");
			return result;
		}

	}
}
