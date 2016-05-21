package org.waddys.xcloud.vijava.environment;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.waddys.xcloud.vijava.api.DatastoreI;
import org.waddys.xcloud.vijava.base.BaseTask;
import org.waddys.xcloud.vijava.data.ResPool;
import org.waddys.xcloud.vijava.environment.QueryResources.QueryResourceAnswer;
import org.waddys.xcloud.vijava.environment.QueryResources.QueryResourceCmd;
import org.waddys.xcloud.vijava.exception.VirtException;
import org.waddys.xcloud.vijava.impl.DatastoreImpl;
import org.waddys.xcloud.vijava.impl.Session;
import org.waddys.xcloud.vijava.util.VmConvertUtils;

import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.ServiceInstance;

public class QueryResourceTask extends BaseTask<QueryResourceAnswer> {
	private static Logger logger = LoggerFactory
			.getLogger(QueryResourceTask.class);

	QueryResourceCmd cmd;
	ServiceInstance si;

	QueryResourceTask(QueryResourceCmd cmd) throws VirtException {
		super();
		this.setCmd(cmd);
		
		try {
			//TODO 连接池中获取instance
			long starttime = System.currentTimeMillis();
			System.out.println(starttime);
			String ip = cmd.getIp();
			if (null != ip && !"".equals(ip)) {
				this.setSi(Session.getInstanceByIP(cmd.getIp()));
			} else {
				this.setSi(Session.getInstanceByToken(cmd.getToken()));
			}
			long endtime = System.currentTimeMillis();
			System.out.println(endtime);
		} catch (Exception e) {
			logger.error("虚拟环境" + cmd.getIp() + "连接异常 ：" + e.getMessage());
			throw new VirtException("虚拟环境" + cmd.getIp() + "连接异常 ："
					+ e.getMessage());

			}
	}

	@Override
	public QueryResourceAnswer execute() {

		QueryResourceAnswer answer = new QueryResourceAnswer();
		ArrayList<ResPool> rpList = new ArrayList<ResPool>();
		Folder rootFolder = si.getRootFolder();
		DatastoreI dsi = new DatastoreImpl();
		dsi.setSi(si);

		try {
			// 1.查询si对应rootFolder下所有资源池
			ManagedEntity[] rps = new InventoryNavigator(rootFolder)
					.searchManagedEntities("ResourcePool");
			// 2.将查询所得资源池信息 对接到自定义类型rps上
			for (ManagedEntity rp : rps) {
				ResPool resp = new ResPool();
				ResourcePool rep = (ResourcePool) rp;

				//4.23需求 要求Resources改为owner+默认计算池字样
				try {
					if (!rep.getParent().getMOR()
							.equals(rep.getOwner().getMOR()))
						resp.setName(rep.getName());
					else
						resp.setName(rep.getOwner().getName() + "默认计算池");
				} catch (Exception e) {
					// 设置名称失败 直接跳过
					continue;
				}
				
				resp.setId(rep.getMOR().get_value());
				// 此处所有数据均采用long 类型 其中cpu单位为MHZ memory单位为B
				long cpuTotal = rep.getRuntime().getCpu().getMaxUsage();
				long cpuUsed = rep.getRuntime().getCpu().getReservationUsed();
				long memoryTotal = rep.getRuntime().getMemory().getMaxUsage();
				long memoryUsed = rep.getRuntime().getMemory()
						.getReservationUsed();
				
				// 此处所有数据均采用long 类型 其中cpu单位转为MHZ memory单位转为MB
//				cpuTotal = VmConvertUtils.HZ2MHZ(cpuTotal);
//				cpuUsed = VmConvertUtils.HZ2MHZ(cpuUsed);
				memoryTotal = VmConvertUtils.B2MB(memoryTotal);
				memoryUsed = VmConvertUtils.B2MB(memoryUsed);
				
				resp.setCpuTotal(cpuTotal);
				resp.setCpuUsed(cpuUsed);
				resp.setCpuAvailable(cpuTotal - cpuUsed);
				resp.setMemoryTotal(memoryTotal);
				resp.setMemoryUsed(memoryUsed);
				resp.setMenoryAvailable(memoryTotal - memoryUsed);
				resp.setStoragePools(dsi.getStorageByResourcePool(resp.getId()));
				rpList.add(resp);
				
				// 3.将对接后的rpList列表 放到answer中 提交到上层
				answer.setRpList(rpList);
				answer.setSuccess(true);
			}
		} catch (Exception e) {
			logger.error("QueryResource任务执行失败，原因是：" + e.getMessage());
            answer.setSuccess(false);
			answer.setErrMsg("QueryResource任务执行失败，原因是：" + "远程查询失败");
		}

		return answer;
	}

	public QueryResourceCmd getCmd() {
		return cmd;
	}

	public void setCmd(QueryResourceCmd cmd) {
		this.cmd = cmd;
	}

	public ServiceInstance getSi() {
		return si;
	}

	public void setSi(ServiceInstance si) {
		this.si = si;
	}

}
