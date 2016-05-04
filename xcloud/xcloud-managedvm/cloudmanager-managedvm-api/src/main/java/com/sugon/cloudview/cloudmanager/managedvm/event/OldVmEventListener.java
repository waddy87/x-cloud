package com.sugon.cloudview.cloudmanager.managedvm.event;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sugon.cloudview.cloudmanager.event.api.EventController;
import com.sugon.cloudview.cloudmanager.event.api.EventProcesser;
import com.sugon.cloudview.cloudmanager.event.type.ResourceType;
import com.sugon.cloudview.cloudmanager.event.type.VmOperatorResult;
import com.sugon.cloudview.cloudmanager.event.type.VmOperatorSuccess;
import com.sugon.cloudview.cloudmanager.event.type.VmOptorType4Event;
import com.sugon.cloudview.cloudmanager.event.util.StringUtils;
import com.sugon.cloudview.cloudmanager.managedvm.service.bo.OldVirtualMachine;
import com.sugon.cloudview.cloudmanager.managedvm.service.service.OldVirtualMachineService;

@Service
public class OldVmEventListener {

	private static Logger logger = LoggerFactory.getLogger(OldVmEventListener.class);

	@Autowired
	@Qualifier("TopicEventController")
	private EventController topicController;

	@Autowired
	private OldVirtualMachineService oldVirtualMachineService;

	@PostConstruct
	public void init() {
		logger.info("开启利旧虚拟机删除事件监听器");

		try {
			String queueKey = StringUtils.getQueueKey(ResourceType.vm.toString(), null, "info", null);
			this.topicController.add(queueKey, new EventProcesser() {

				@Override
				@Transactional
				public void process(String routeKey, Object eventObject) {

					System.out.println("开始了");
					try {
						if (eventObject instanceof VmOperatorResult) {
							VmOperatorResult eventResult = (VmOperatorResult) eventObject;
							String taskId = eventResult.getTaskId();
							String actionTypeLable = eventResult.getOperatorType();
							String vmId = eventResult.getVmId();
							logger.info("接收到利旧虚拟机操作完成事件：taskId=" + taskId + ",vmId=" + vmId);

							if (StringUtils.isBlank(taskId)) {
								logger.error("事件无效：taskId is null！");
								return;
							}
							if (StringUtils.isBlank(actionTypeLable)) {
								logger.error("事件无效：actionTypeLable is null！");
								return;
							}
							boolean isSuccess = false;
							if (eventObject instanceof VmOperatorSuccess) {
								isSuccess = true;
							}
							VmOptorType4Event actionType = null;
							try {
								actionType = VmOptorType4Event.valueOf(actionTypeLable);
							} catch (Exception e) {
								logger.info("不支持的动作类型！");
								return;
							}

							System.out.println(actionType.ordinal() + "!!!!!!!!!!!!!!!!");
							switch (actionType) {
							case Destroy:
								logger.info("处理删除利旧虚拟机事件！");
								OldVirtualMachine oldVirtualMachine = oldVirtualMachineService.findByVmId(vmId);
								if (oldVirtualMachine == null) {
									logger.error("vmId不存在！");
								} else {
									if (isSuccess) {
										logger.info("删除利旧虚拟机成功！");
										oldVirtualMachineService.deleteVMRecord(oldVirtualMachine.getId());
									} else {
										logger.error("删除利旧虚拟机失败！");
									}
								}
								break;
							default:
								logger.info("非监听事件，丢弃！");
							}
							logger.info("处理事件成功！");
						}
					} catch (Exception e) {
						logger.error("处理删除事件失败：" + e.getMessage());
					}
				}
			});
			this.topicController.start();
			logger.info("启动利旧虚拟机监听器成功！");
		} catch (Exception e) {
			logger.error("启动利旧虚拟机监听器失败！");
		}
	}
}
