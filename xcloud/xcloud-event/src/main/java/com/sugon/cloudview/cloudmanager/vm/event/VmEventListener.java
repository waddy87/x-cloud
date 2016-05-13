/**
 * 
 */
package com.sugon.cloudview.cloudmanager.vm.event;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sugon.cloudview.cloudmanager.event.api.EventController;
import com.sugon.cloudview.cloudmanager.event.api.EventProcesser;
import com.sugon.cloudview.cloudmanager.event.type.VmOperatorResult;
import com.sugon.cloudview.cloudmanager.event.type.VmOperatorSuccess;
import com.sugon.cloudview.cloudmanager.event.type.VmOptorType4Event;
import com.sugon.cloudview.cloudmanager.event.util.StringUtils;
import com.sugon.cloudview.cloudmanager.vm.bo.VmHost;
import com.sugon.cloudview.cloudmanager.vm.constant.RunStatus;
import com.sugon.cloudview.cloudmanager.vm.constant.VmStatus;
import com.sugon.cloudview.cloudmanager.vm.service.VmService;

/**
 * 虚机事件监听器
 * 
 * @author zhangdapeng
 *
 */
@Service
public class VmEventListener {
    private static final Logger logger = LoggerFactory.getLogger(VmEventListener.class);

    @Autowired
    @Qualifier("TopicEventController")
    private EventController topicController;

    @Autowired
    private VmService vmService;

    /**
     * 接收到的全部虚机主题事件数量
     */
    public static int allCount = 0;

    /**
     * 丢弃的事件数量
     */
    public static int discardCount = 0;

    /**
     * 成功处理的事件数量
     */
    public static int successCount = 0;

    /**
     * 处理失败的事件数量
     */
    public static int failedCount = 0;

    @PostConstruct
    public void init() {
        logger.info("开始启动虚机事件监听器...");
        try {
            /**
             * 监听虚机主题相关事件 
             * 资源类型：vm-代表虚机、例旧虚机、物理主机
             */
            String queueKey = StringUtils.getQueueKey("vm", null, null, null);
            topicController.add(queueKey, new EventProcesser() {
                @Override
                @Transactional
                public void process(String routeKey, Object eventObject) {
                    logger.debug("+++++++++++++++++++++++++++++++++++");
                    logger.debug("routeKey = " + routeKey);
                    logger.debug("" + eventObject);
                    logger.debug("-----------------------------------");
                    allCount++;
                    if (eventObject instanceof VmOperatorResult) {
                        try {
                            // 1.根据条件过滤：资源类型=[虚机]，事件类型=[创建成功\失败 or 删除成功\失败 or
                            // 启动成功\失败 or 关闭成功\失败]

                            VmOperatorResult event = (VmOperatorResult) eventObject;
                            String taskId = event.getTaskId();
                            String actionTypeLable = event.getOperatorType();
                            String vmId = event.getVmId();
                            logger.info(
"接收到虚机操作完成事件：taskId=" + taskId + ",vmId=" + vmId + ",actionType="
                                    + actionTypeLable + "" + event.getResult(),
                                    event);

                            // 参数合法性校验
                            if (StringUtils.isBlank(taskId)) {
                                logger.error("无效的事件：taskId is null");
                                return;
                            }
                            if (StringUtils.isBlank(actionTypeLable)) {
                                logger.error("无效的事件：actionType is null");
                                return;
                            }

                            // 获取成功失败标识
                            boolean isSuccess = false;
                            if (eventObject instanceof VmOperatorSuccess) {
                                isSuccess = true;
                            }

                            // 判断操作类型：创建/关闭/启动/删除
                            VmOptorType4Event actionType = null;
                            try {
                                actionType = VmOptorType4Event.valueOf(actionTypeLable);
                            } catch (Exception e) {
                                logger.warn("未约定的虚机动作类型，直接丢弃！actionType=" + actionTypeLable);
                                return;
                            }

                            // 2.进数据库查询目标资源
                            // 3.更新目标资源状态
                            // 4.其它操作：日志记录等
                            VmHost vmHost = null;
                            switch (actionType) {
                            case CloneVM:
                                logger.info("识别到虚机创建完成事件");
                                vmHost = vmService.findByTaskId(taskId);
                                if (vmHost == null) {
                                    logger.debug("taskId不存在，直接丢弃！");
                                    discardCount++;
                                } else {
                                    if (VmStatus.DELETED.equals(vmHost.getVmStatus())) {
                                        logger.warn("灵异事件：收到已经删除虚机的事件！");
                                        break;
                                    }
                                    if (isSuccess) {
                                        logger.debug("虚机创建成功，设置内部标识：taskId=" + taskId + ",internal-id=" + vmId);
                                        vmHost.setInternalId(vmId);
                                        vmHost.setVmStatus(VmStatus.INITED);
                                        vmHost.setRunStatus(RunStatus.NONE);
                                    } else {
                                        logger.debug("虚机创建失败，设置相应状态：taskId=" + taskId);
                                        // vmHost.setRunStatus(RunStatus.StartError);
                                        vmHost.setRunStatus(RunStatus.NONE);
                                    }
                                    vmService.update(vmHost);
                                }
                                break;
                            case Destroy:
                                logger.info("识别到虚机删除完成事件");
                                vmHost = vmService.findByInternalId(vmId);
                                if (vmHost != null) {
                                    if (VmStatus.DELETED.equals(vmHost.getVmStatus())) {
                                        logger.warn("灵异事件：收到已经删除虚机的事件！");
                                        break;
                                    }
                                    if (isSuccess) {
                                        if (RunStatus.DELETING.equals(vmHost.getRunStatus())) {
                                            // 如果当前虚机的数据库运行状态是删除中，说明是从界面删除动作（也可以根据是否为api-user操作，但现在虚拟化接口没有提供）
                                            logger.debug("虚机删除成功，设置相应状态：internal-id=" + vmId);
                                            vmService.deleteById(vmHost.getId());
                                            vmService.releaseRes(vmHost.getId());
                                        } else {
                                            // 否则认为是从后台删除，置为不可用
                                            vmHost.setIsAvailable(false);
                                            vmService.update(vmHost);
                                        }
                                    } else {
                                        if (taskId.equals(vmHost.getTaskId())) {
                                            // 如果当前虚机的数据库运行状态是删除中，说明是从界面删除动作（也可以根据是否为api-user操作，但现在虚拟化接口没有提供）
                                            logger.debug("虚机删除失败，设置相应状态：internal-id=" + vmId);
                                            // vmHost.setRunStatus(RunStatus.DeleteError);
                                            vmHost.setRunStatus(RunStatus.NONE);
                                            vmService.update(vmHost);
                                        }
                                    }
                                } else {
                                    logger.debug("vmId不存在，直接丢弃！");
                                    discardCount++;
                                }
                                break;
                            case PowerOnVM:
                                logger.info("识别到虚机启动成功事件");
                                vmHost = vmService.findByInternalId(vmId);
                                if (vmHost != null) {
                                    if (VmStatus.DELETED.equals(vmHost.getVmStatus())) {
                                        logger.warn("灵异事件：收到已经删除虚机的事件！");
                                        break;
                                    }
                                    if (isSuccess) {
                                        logger.debug("虚机启动成功，设置相应状态：internal-id=" + vmId);
                                        vmHost.setVmStatus(VmStatus.STARTED);
                                        vmHost.setRunStatus(RunStatus.NONE);
                                    } else {
                                        logger.debug("虚机启动失败，设置相应状态：internal-id=" + vmId);
                                        // vmHost.setRunStatus(RunStatus.StartError);
                                        vmHost.setRunStatus(RunStatus.NONE);
                                    }
                                    vmService.update(vmHost);
                                } else {
                                    logger.debug("vmId不存在，直接丢弃！");
                                    discardCount++;
                                }
                                break;
                            case PowerOffVM:
                                logger.info("识别到虚机关闭成功事件");
                                vmHost = vmService.findByInternalId(vmId);
                                if (vmHost != null) {
                                    if (VmStatus.DELETED.equals(vmHost.getVmStatus())) {
                                        logger.warn("灵异事件：收到已经删除虚机的事件！");
                                        break;
                                    }
                                    if (isSuccess) {
                                        logger.debug("虚机关闭成功，设置相应状态：internal-id=" + vmId);
                                        vmHost.setVmStatus(VmStatus.STOPPED);
                                        vmHost.setRunStatus(RunStatus.NONE);
                                    } else {
                                        logger.debug("虚机关闭失败，设置相应状态：internal-id=" + vmId);
                                        // vmHost.setRunStatus(RunStatus.StopError);
                                        vmHost.setRunStatus(RunStatus.NONE);
                                    }
                                    vmService.update(vmHost);
                                } else {
                                    logger.debug("vmId不存在，直接丢弃！");
                                    discardCount++;
                                }
                                break;
                            default:
                                logger.warn("未约定的虚机动作类型，直接丢弃！actionType=" + actionTypeLable);
                                discardCount++;
                                break;
                            }
                            logger.info("处理事件成功！");
                            successCount++;
                        } catch (Exception e) {
                            logger.error("处理事件失败：" + routeKey, e);
                            failedCount++;
                        }
                    } else {
                        logger.warn("非法的虚机主题事件，直接丢弃！");
                        discardCount++;
                    }

                }
            });
            topicController.start();
            logger.info("启动虚机事件监听器成功！");
        } catch (Exception e) {
            logger.error("启动虚机事件监听器失败！", e);
        }
    }

}
