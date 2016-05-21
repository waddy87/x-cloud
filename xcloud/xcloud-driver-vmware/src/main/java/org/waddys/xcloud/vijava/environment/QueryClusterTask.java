package org.waddys.xcloud.vijava.environment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.waddys.xcloud.vijava.base.BaseTask;
import org.waddys.xcloud.vijava.environment.QueryCluster.QueryClusterAnswer;
import org.waddys.xcloud.vijava.environment.QueryCluster.QueryClusterCmd;
import org.waddys.xcloud.vijava.exception.VirtException;
import org.waddys.xcloud.vijava.impl.Session;

import com.vmware.vim25.mo.ServiceInstance;

public class QueryClusterTask extends BaseTask<QueryClusterAnswer> {
	private static Logger logger = LoggerFactory
			.getLogger(QueryClusterTask.class);

	QueryClusterCmd cmd;
	ServiceInstance si;

	QueryClusterTask(QueryClusterCmd cmd) throws VirtException {
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
			logger.error("虚拟环境" + cmd.getIp() + "连接异常 ：" + e.getMessage());
			throw new VirtException("虚拟环境" + cmd.getIp() + "连接异常 ：" + e.getMessage());

		}
	}

	@Override
	public QueryClusterAnswer execute() {
		// TODO Auto-generated method stub
		return null;
	}

	public QueryClusterCmd getCmd() {
		return cmd;
	}

	public void setCmd(QueryClusterCmd cmd) {
		this.cmd = cmd;
	}

	public ServiceInstance getSi() {
		return si;
	}

	public void setSi(ServiceInstance si) {
		this.si = si;
	}

}
