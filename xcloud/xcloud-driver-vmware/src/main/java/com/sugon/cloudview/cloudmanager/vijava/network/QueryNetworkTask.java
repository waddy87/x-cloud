package com.sugon.cloudview.cloudmanager.vijava.network;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sugon.cloudview.cloudmanager.vijava.api.NetworkI;
import com.sugon.cloudview.cloudmanager.vijava.base.BaseTask;
import com.sugon.cloudview.cloudmanager.vijava.exception.VirtException;
import com.sugon.cloudview.cloudmanager.vijava.impl.NetworkImpl;
import com.sugon.cloudview.cloudmanager.vijava.impl.Session;
import com.sugon.cloudview.cloudmanager.vijava.network.QueryNetwork.QueryNetworkAnswer;
import com.sugon.cloudview.cloudmanager.vijava.network.QueryNetwork.QueryNetworkCmd;
import com.sugon.vim25.mo.Network;
import com.sugon.vim25.mo.ServiceInstance;

public class QueryNetworkTask extends BaseTask<QueryNetworkAnswer> {

	private static Logger logger = LoggerFactory
			.getLogger(QueryNetworkTask.class);

	QueryNetworkCmd cmd;
	ServiceInstance si;
	private NetworkI neti = new NetworkImpl();

	@Override
	public QueryNetworkAnswer execute() {

		QueryNetworkAnswer answer = new QueryNetworkAnswer();
		List<Network> list = new ArrayList<Network>();
		try {

			neti.setSi(si);

			if (null != cmd.getHostId())
				list = neti.getNetworkByHost(cmd.getHostId());
			else if (null != cmd.getResourcepoolId())
				list = neti.getNetworkByResourcepool(cmd.getResourcepoolId());
			else if (null != cmd.getClusterId())
				list = neti.getNetworkByCluster(cmd.getClusterId());
			else if (null != cmd.getDatacenterId())
				list = neti.getNetworkByDatacenter(cmd.getDatacenterId());
			else

				throw new VirtException("接口暂未实现");
		} catch (Exception e) {

			e.printStackTrace();
		}
		answer.setNetworkList(list);
		return answer;
	}

	QueryNetworkTask(QueryNetworkCmd cmd) {
		super();
		this.setCmd(cmd);
		try {
			this.setSi(Session.getInstanceByToken(cmd.getToken()));
		} catch (Exception e) {
			logger.error("连接失败，原因" + e.getMessage());
		}
	}

	public QueryNetworkCmd getCmd() {
		return cmd;
	}

	public void setCmd(QueryNetworkCmd cmd) {
		this.cmd = cmd;
	}

	public ServiceInstance getSi() {
		return si;
	}

	public void setSi(ServiceInstance si) {
		this.si = si;
	}

}