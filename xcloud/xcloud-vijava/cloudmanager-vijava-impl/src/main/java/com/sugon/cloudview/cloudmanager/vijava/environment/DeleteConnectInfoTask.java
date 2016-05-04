package com.sugon.cloudview.cloudmanager.vijava.environment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sugon.cloudview.cloudmanager.vijava.base.BaseTask;
import com.sugon.cloudview.cloudmanager.vijava.environment.DeleteConnectInfo.DeleteConnectInfoAnswer;
import com.sugon.cloudview.cloudmanager.vijava.environment.DeleteConnectInfo.DeleteConnectInfoCmd;
import com.sugon.cloudview.cloudmanager.vijava.exception.VirtException;
import com.sugon.cloudview.cloudmanager.vijava.impl.Session;
import com.sugon.vim25.mo.ServiceInstance;

public class DeleteConnectInfoTask extends BaseTask<DeleteConnectInfoAnswer> {

	private static Logger logger = LoggerFactory
			.getLogger(DeleteConnectInfoTask.class);
	ServiceInstance si;
	
	public DeleteConnectInfoTask(DeleteConnectInfoCmd cmd) throws VirtException {

		try{
		String token = cmd.getToken();
		Session.getInstanceByToken(token);
		
	} catch (Exception e) {
		logger.error("连接失败，原因" + e.getMessage());
		throw new VirtException("虚拟环境" + cmd.getToken() + "连接异常 ：" + e.getMessage());
	}
	}

	@Override
	public DeleteConnectInfoAnswer execute() {
		// TODO 1.删文件中连接信息  2.池里的数据  3.si置空 connection置空
		
		return null;
	}

}
