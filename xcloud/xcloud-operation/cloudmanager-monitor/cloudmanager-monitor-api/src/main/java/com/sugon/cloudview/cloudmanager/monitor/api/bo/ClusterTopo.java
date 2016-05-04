/**
 * Created on 2016年4月22日
 */
package com.sugon.cloudview.cloudmanager.monitor.api.bo;

import java.util.List;


/**
 * 功能名: 请填写功能名
 * 功能描述: 请简要描述功能的要点
 * Copyright: Copyright (c) 2016
 * 公司: 曙光云计算技术有限公司
 *
 * @author yangkun
 * @version 2.0.0 sp1
 */
public class ClusterTopo {
	    String clusterName;
	    String  clusterId;
	    List<HostTopo> hostsTopo;
		public String getClusterName() {
			return clusterName;
		}
		public void setClusterName(String clusterName) {
			this.clusterName = clusterName;
		}
		public String getClusterId() {
			return clusterId;
		}
		public void setClusterId(String clusterId) {
			this.clusterId = clusterId;
		}
		public List<HostTopo> getHostsTopo() {
			return hostsTopo;
		}
		public void setHostsTopo(List<HostTopo> hostsTopo) {
			this.hostsTopo = hostsTopo;
		}
}
