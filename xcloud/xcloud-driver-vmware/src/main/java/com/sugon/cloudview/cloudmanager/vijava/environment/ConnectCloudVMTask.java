package com.sugon.cloudview.cloudmanager.vijava.environment;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sugon.cloudview.cloudmanager.vijava.base.BaseTask;
import com.sugon.cloudview.cloudmanager.vijava.environment.ConnectCloudVM.ConnectCloudVMAnswer;
import com.sugon.cloudview.cloudmanager.vijava.environment.ConnectCloudVM.ConnectCloudVMCmd;
import com.sugon.cloudview.cloudmanager.vijava.impl.Session;
import com.sugon.cloudview.cloudmanager.vijava.util.CloudVMConfig;
import com.sugon.cloudview.cloudmanager.vijava.util.CloudVMConfig.ConnectionInfo;
import com.sugon.cloudview.cloudmanager.vijava.util.LoadSystemPropertyJYaml;
import com.sugon.cloudview.cloudmanager.vijava.util.MoQueryUtils;
import com.sugon.cloudview.cloudmanager.vijava.util.VmConvertUtils;
import com.sugon.vim25.AboutInfo;
import com.sugon.vim25.mo.Folder;
import com.sugon.vim25.mo.HostSystem;
import com.sugon.vim25.mo.InventoryNavigator;
import com.sugon.vim25.mo.ServiceInstance;

public class ConnectCloudVMTask extends BaseTask<ConnectCloudVMAnswer> {

	private static Logger logger = LoggerFactory
			.getLogger(ConnectCloudVMTask.class);

	ConnectCloudVMCmd cmd;
	ServiceInstance si;
	String token;

	public ConnectCloudVMTask(ConnectCloudVMCmd cmd) {

		super();
		this.setCmd(cmd);
		try {

			this.setSi(Session.getInstance(cmd.getIp(), cmd.getUsername(),
					cmd.getUserpwd()));
			this.setToken(si.getAboutInfo().getInstanceUuid());
		} catch (Exception e) {
			logger.error("连接失败，原因" + e.getMessage());
		}
	}

	@Override
	public ConnectCloudVMAnswer execute() {

		ConnectCloudVMAnswer answer = new ConnectCloudVMAnswer();
		if (null != si) {

			

			AboutInfo aboutInfo = si.getAboutInfo();

			if (null != aboutInfo && null != aboutInfo.getApiVersion()) {
				answer.setSuccess(true);
				answer.setVersion("");

				try {
					//XXX 支持业务现阶段需求 可不根据参数获取连接 如此 只能保持一个连接信息在配置文件中 因而在建立连接之前 暂时清理掉配置文件  后续代码保持不变
					LoadSystemPropertyJYaml.clearConfigInfo();
					CloudVMConfig cloudVMConfig = LoadSystemPropertyJYaml
							.getCloudVMConfig();
					
					//考虑文件为空的情况 初始化cloudVMConfig对象
					if (cloudVMConfig == null){
						cloudVMConfig = new CloudVMConfig();
						cloudVMConfig.setConfigDate(VmConvertUtils.convertDateToString(new Date(),"dd/MM/yyyy"));
						ConnectionInfo[] vcenterInfo = {};
						cloudVMConfig.setVcenterInfo(vcenterInfo);
					}
					
						CloudVMConfig.ConnectionInfo connInfo = new CloudVMConfig.ConnectionInfo();
						connInfo.setVcenter_ip(cmd.getIp());
						connInfo.setVcenter_user(cmd.getUsername());
						connInfo.setVcenter_passwd(cmd.getUserpwd());

						// 更新配置文件
						Integer updateIndex = -1;
						boolean isUpdate = false;
						for (CloudVMConfig.ConnectionInfo localConnInfo : cloudVMConfig
								.getVcenterInfo()) {
							updateIndex++;
							if (localConnInfo.getVcenter_ip().equals(
									connInfo.getVcenter_ip())) {
								//token信息保持不变 仍采用文件中的数据
								if(cloudVMConfig.getVcenterInfo()[updateIndex].getVcenter_token() != null)
									connInfo.setVcenter_token(cloudVMConfig.getVcenterInfo()[updateIndex].getVcenter_token());
								else
									connInfo.setVcenter_token(getToken());
								cloudVMConfig.getVcenterInfo()[updateIndex] = connInfo;
								isUpdate = true;
							}
						}

						// 新加ConnctionInfo
						if (!isUpdate) {
							CloudVMConfig.ConnectionInfo lsConnInfo[] = new CloudVMConfig.ConnectionInfo[cloudVMConfig
									.getVcenterInfo().length + 1];
							System.arraycopy(cloudVMConfig.getVcenterInfo(), 0,
									lsConnInfo, 0,
									cloudVMConfig.getVcenterInfo().length);
							//如果历史数据没有 token 则从建立连接时的session中获取 
							connInfo.setVcenter_token(getToken());
							lsConnInfo[cloudVMConfig.getVcenterInfo().length] = connInfo;
							cloudVMConfig.setVcenterInfo(lsConnInfo);
						}
					LoadSystemPropertyJYaml.saveCloudVMConfig(cloudVMConfig);
					answer.setToken(connInfo.getVcenter_token());
				} catch (Exception e) {
					logger.error("更新vCenter配置文件失败：{}", e.getMessage());
				}
			}
			// 对配置文件的操作结束 开始查询版本信息

			try {
				// 根据ServiceInstance查询主机版本
				String version = MoQueryUtils.getVsphereVersion(si);
				answer.setVersion(version);
			} catch (Exception e) {
				logger.error("获取vcenter版本信息失败：{}", e.getMessage());
			}
		}
		// 如果获取不到instance说明连接失败
		else {
			answer.setSuccess(false);
			answer.setErrMsg("can not get connection instances...");
			answer.setVersion("");
		}

		return answer;
	}

	public ConnectCloudVMCmd getCmd() {
		return cmd;
	}

	public void setCmd(ConnectCloudVMCmd cmd) {
		this.cmd = cmd;
	}

	public ServiceInstance getSi() {
		return si;
	}

	public void setSi(ServiceInstance si) {
		this.si = si;
	}

	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public static void main(String[] args) {

		ConnectCloudVMCmd cmd = new ConnectCloudVMCmd();
		cmd.setIp("10.0.36.121").setUsername("admin").setUserpwd("Sugon!123");
		ConnectCloudVMTask task = new ConnectCloudVMTask(cmd);
		ConnectCloudVMAnswer answer = task.execute();
		System.out.println(answer);
		cmd.setIp("10.0.36.128").setUsername("admin").setUserpwd("Sugon!123");
		task = new ConnectCloudVMTask(cmd);
		answer = task.execute();
		System.out.println(answer);
		cmd.setIp("10.0.31.251").setUsername("admin").setUserpwd("Sugon!123");
		task = new ConnectCloudVMTask(cmd);
		answer = task.execute();
		System.out.println(answer);

	}
}
