package org.waddys.xcloud.vijava.environment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.waddys.xcloud.vijava.base.BaseTask;
import org.waddys.xcloud.vijava.environment.TestConnectInfo.TestConnectInfoAnswer;
import org.waddys.xcloud.vijava.environment.TestConnectInfo.TestConnectInfoCmd;
import org.waddys.xcloud.vijava.impl.Session;

import com.sugon.vim25.mo.ServiceInstance;

public class TestConnectInfoTask extends BaseTask<TestConnectInfoAnswer> {
	private static Logger logger = LoggerFactory
			.getLogger(TestConnectInfoTask.class);

	TestConnectInfoCmd cmd;
	ServiceInstance si;

	TestConnectInfoTask(TestConnectInfoCmd cmd) {
		super();
		this.setCmd(cmd);
		try {
			if (("".equals(cmd.getIp()) || null == cmd.getIp())
					&& cmd.getToken() != null && !"".equals(cmd.getToken())){
				this.setSi(Session.getInstanceByToken(cmd.getToken()));
			}else{
				this.setSi(Session.getInstance(cmd.getIp(), cmd.getUsername(),
						cmd.getUserpwd()));
			}
		} catch (Exception e) {
			logger.error("连接失败，原因" + e.getMessage());
		}
	}

	@Override
	public TestConnectInfoAnswer execute() {

		TestConnectInfoAnswer answer = new TestConnectInfoAnswer();
		answer.setSuccess(false);
		answer.setErrMsg("can not get connection instances...");
		if(null != si){
			answer.setSuccess(true);
			answer.setErrMsg("");
		}
		
		return answer;
	}

	public ServiceInstance getSi() {
		return si;
	}

	public void setSi(ServiceInstance si) {
		this.si = si;
	}

	public TestConnectInfoCmd getCmd() {
		return cmd;
	}

	public void setCmd(TestConnectInfoCmd cmd) {
		this.cmd = cmd;
	}

}
