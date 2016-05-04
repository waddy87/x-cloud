package com.sugon.cloudview.cloudmanager.vijava.environment;

import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sugon.cloudview.cloudmanager.vijava.api.NetworkI;
import com.sugon.cloudview.cloudmanager.vijava.base.BaseTask;
import com.sugon.cloudview.cloudmanager.vijava.environment.QueryNetPool.QueryNetPoolAnswer;
import com.sugon.cloudview.cloudmanager.vijava.environment.QueryNetPool.QueryNetPoolCmd;
import com.sugon.cloudview.cloudmanager.vijava.exception.VirtException;
import com.sugon.cloudview.cloudmanager.vijava.impl.NetworkImpl;
import com.sugon.cloudview.cloudmanager.vijava.impl.Session;
import com.sugon.vim25.mo.ServiceInstance;

public class QueryNetPoolTask extends BaseTask<QueryNetPoolAnswer> {
	private static Logger logger = LoggerFactory
			.getLogger(QueryNetPoolTask.class);

	QueryNetPoolCmd cmd;
	ServiceInstance si;

	QueryNetPoolTask(QueryNetPoolCmd cmd) throws VirtException {
		super();
		this.setCmd(cmd);
		
		try {
			//TODO 连接池中获取instance
			
			
			String ip = cmd.getIp();
			if (null != ip && !"".equals(ip)) {
				this.setSi(Session.getInstanceByIP(cmd.getIp()));
			} else {
				this.setSi(Session.getInstanceByToken(cmd.getToken()));
			}
		} catch (Exception e) {
			logger.error("连接 " + cmd.getIp() + "失败，原因" + e.getMessage());
			throw new VirtException("虚拟环境" + cmd.getIp() + "连接异常 ：" + e.getMessage());

		}
	}

	@Override
	public QueryNetPoolAnswer execute() {

		QueryNetPoolAnswer answer = new QueryNetPoolAnswer();
		NetworkI neti = new NetworkImpl();
		neti.setSi(si);

		try {
			answer = answer.setNetList(neti.getNetPool());
			answer.setSuccess(true);
		} catch (RemoteException e) {
			answer.setNetList(null);
			answer.setSuccess(false);
			logger.error("QueryNetPool任务执行失败，原因是" + e.getMessage());
			answer.setErrMsg("QueryNetPool任务执行失败，原因是：" + "远程查询失败");
		}
		return answer;
	}

	public QueryNetPoolCmd getCmd() {
		return cmd;
	}

	public void setCmd(QueryNetPoolCmd cmd) {
		this.cmd = cmd;
	}

	public ServiceInstance getSi() {
		return si;
	}

	public void setSi(ServiceInstance si) {
		this.si = si;
	}

}
