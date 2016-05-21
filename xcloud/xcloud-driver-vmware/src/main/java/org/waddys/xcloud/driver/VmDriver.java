/**
 * 
 */
package org.waddys.xcloud.driver;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.waddys.xcloud.common.base.utils.CIDRUtils;
import org.waddys.xcloud.driver.IVmDriver;
import org.waddys.xcloud.util.JsonUtil;
import org.waddys.xcloud.vijava.base.Answer;
import org.waddys.xcloud.vijava.base.CloudviewExecutor;
import org.waddys.xcloud.vijava.data.VMDiskInfo;
import org.waddys.xcloud.vijava.data.VMNicInfo;
import org.waddys.xcloud.vijava.data.VMachine;
import org.waddys.xcloud.vijava.vm.ReconfigVMTask;
import org.waddys.xcloud.vijava.vm.CreateVM.CreateVMAnswer;
import org.waddys.xcloud.vijava.vm.CreateVM.CreateVMCmd;
import org.waddys.xcloud.vijava.vm.DeleteVM.DeleteVMAnswer;
import org.waddys.xcloud.vijava.vm.DeleteVM.DeleteVMCmd;
import org.waddys.xcloud.vijava.vm.QueryTask.QueryTaskAnswer;
import org.waddys.xcloud.vijava.vm.QueryTask.QueryTaskCmd;
import org.waddys.xcloud.vijava.vm.QueryTask.QueryTaskAnswer.QueriedTaskInfo;
import org.waddys.xcloud.vijava.vm.QueryVM.QueryVMAnswer;
import org.waddys.xcloud.vijava.vm.QueryVM.QueryVMCmd;
import org.waddys.xcloud.vijava.vm.QueryVMConsole.QueryVMConsoleAnswer;
import org.waddys.xcloud.vijava.vm.QueryVMConsole.QueryVMConsoleCmd;
import org.waddys.xcloud.vijava.vm.ReconfigVM.ReconfigVMAnswer;
import org.waddys.xcloud.vijava.vm.ReconfigVM.ReconfigVMCmd;
import org.waddys.xcloud.vijava.vm.VMPowerOperate.PowerOPType;
import org.waddys.xcloud.vijava.vm.VMPowerOperate.VMPowerOpAnswer;
import org.waddys.xcloud.vijava.vm.VMPowerOperate.VMPowerOpCmd;
import org.waddys.xcloud.vm.bo.VmConfig;
import org.waddys.xcloud.vm.bo.VmDisk;
import org.waddys.xcloud.vm.bo.VmHost;
import org.waddys.xcloud.vm.bo.VmNet;
import org.waddys.xcloud.vm.bo.VmTask;
import org.waddys.xcloud.vm.constant.RunStatus;
import org.waddys.xcloud.vm.constant.VmStatus;

import com.vmware.vim25.VirtualDeviceConfigSpecOperation;

/**
 * @author zhangdapeng
 *
 */
@Service("vmDriver")
public class VmDriver implements IVmDriver {
    private static final Logger logger = LoggerFactory.getLogger(VmDriver.class);

    @Autowired
    private CloudviewExecutor cloudviewExecutor;

    @Override
    public VmHost create(VmHost vmHost) throws Exception {
        logger.info("准备调用虚拟化接口：" + JsonUtil.toJson(vmHost));
        /**
         * 调用虚拟化接口 （入参：虚机名称、模板id、计算池id、vcpu数量、内存容量、存储池id、存储容量， 返回：虚机创建任务id）
         */
        VmTask taskInfo = new VmTask();
        final String vmName = vmHost.getName();
        final int vcpuNumber = vmHost.getvCpuNumer();
        final Long vmemCapacity = vmHost.getvMemCapacity();
        final String cPoolId = vmHost.getcPoolId();
        final String sPoolId = vmHost.getsPoolId();
        final String templateId = vmHost.getTemplateId();
        CreateVMAnswer answer = null;
        List<VmNet> targetNets = vmHost.getNets();

        // 构造cmd参数
        CreateVMCmd cmd = new CreateVMCmd();
        try {
            // 基本信息
            cmd.setName(vmName).setTemplateId(templateId);
            // 资源信息
            cmd.setDatastoreId(sPoolId).setPoolId(cPoolId).setCpuNum(vcpuNumber)
                    .setMemSizeMB(vmemCapacity);
            // 系统账户
            if (!StringUtils.isEmpty(vmHost.getOsPassword())) {
                cmd.setPasswd(vmHost.getOsPassword());
            }
            // 网络参数
            if (!CollectionUtils.isEmpty(targetNets)) {
                List<VMNicInfo> vmNicInfos = new LinkedList<VMNicInfo>();
                for (VmNet net : targetNets) {
                    CIDRUtils cidrUtils = new CIDRUtils(net.getSubnet());
                    VMNicInfo vmNicInfo = new VMNicInfo();
                    vmNicInfo.setAdapterIP(net.getIp());
                    vmNicInfo.setAdapterGateWay(net.getGateway());
                    vmNicInfo.setAdapterDNS(net.getDns());
                    vmNicInfo.setNetworkId(net.getNetId());
                    vmNicInfo.setAdapterSubNet(net.getSubnet());
                    vmNicInfo.setAdapterSubNet(cidrUtils.getNetworkAddress());
                    vmNicInfo.setAdapterNetMask(cidrUtils.getNetMask());
                    vmNicInfos.add(vmNicInfo);
                }
                cmd.setNicInfos(vmNicInfos);
            }
            answer = cloudviewExecutor.execute(cmd);
        } catch (Exception e) {
            logger.error("调用虚拟化接口出错！", e);
            throw new Exception("调用虚拟化接口出错！", e);
        }
        // 检查执行结果
        if (answer == null) {
            logger.error("虚拟化接口返回对象为空！");
            throw new Exception("虚拟化接口返回对象为空！");
        }
        if (answer.isSuccess()) {
            // 回填任务标识
            String taskId = answer.getTaskId();
            logger.debug("成功调用虚拟化接口，获取taskId=" + taskId);
            taskInfo.setTaskId(taskId);
            taskInfo.setCreateTime(answer.getTaskCreateTime());
            taskInfo.setResourceId(answer.getResourceId());
            taskInfo.setTaskName(answer.getTaskName());
            vmHost.setTaskId(taskId);
            vmHost.setTaskInfo(taskInfo);
            vmHost.setOsPassword(answer.getPasswd());
            // 回填网卡内部标识（与云鹏确认）
            if (!CollectionUtils.isEmpty(targetNets)) {
                try {
                    List<VMNicInfo> vmNicInfos = answer.getNicInfos();
                    for (int index = 0; index < targetNets.size(); index++) {
                        String internalId = vmNicInfos.get(index).getAdapterId();
                        targetNets.get(index).setInternalId(internalId);
                    }
                } catch (Exception e) {
                    logger.error("虚拟化接口返回网卡列表有误！");
                    throw new Exception("虚拟化接口返回网卡列表有误！");
                }
            }
        } else {
            logger.error("虚拟化接口内部执行失败：" + answer.getErrMsg());
            throw new Exception("虚拟化接口内部执行失败：" + answer.getErrMsg());
        }
        return vmHost;
    }

    @Override
    public VmTask delete(String vmId) throws Exception {
        logger.info("开始删除虚机：" + vmId);
        DeleteVMAnswer answer = null;
        // 调用虚拟化接口
        try {
            DeleteVMCmd cmd = new DeleteVMCmd();
            cmd.setVmId(vmId);
            answer = cloudviewExecutor.execute(cmd);
        } catch (Exception e) {
            logger.error("调用虚拟化接口出错！", e);
            throw new Exception("调用虚拟化接口出错！", e);
        }
        // 检查执行结果
        return parseResult(answer);
    }

    @Override
    public VmTask config(String vmId, VmConfig vmConfig) throws Exception {
        logger.info("开始更新虚机：" + vmId + " - " + JsonUtil.toJson(vmConfig));
        // 校验参数
        if (StringUtils.isEmpty(vmId)) {
            logger.error("修改目标虚机内部标识为空！");
            throw new Exception("修改目标虚机内部标识为空！");
        }
        if (vmConfig == null) {
            logger.error("虚机配置信息为空！");
            throw new Exception("虚机配置信息为空！");
        }

        // 调用虚拟化接口
        ReconfigVMAnswer answer = null;
        try {
            ReconfigVMCmd reconfigVMCmd = new ReconfigVMCmd();
            reconfigVMCmd.setVmId(vmId);
            if (!StringUtils.isEmpty(vmConfig.getName())) {
                reconfigVMCmd.setName(vmConfig.getName());
            }
            if (vmConfig.getvCpuNumer() != null) {
                reconfigVMCmd.setCpuNum(vmConfig.getvCpuNumer());
            }
            if (vmConfig.getvMemCapacity() != null) {
                reconfigVMCmd.setMemSizeMB(vmConfig.getvMemCapacity());
            }
            ReconfigVMTask createVMTask = new ReconfigVMTask(reconfigVMCmd);
            answer = createVMTask.execute();
        } catch (Exception e) {
            logger.error("调用虚拟化接口出错！" + e.getMessage(), e);
            throw new Exception("调用虚拟化接口出错！" + e.getMessage(), e);
        }
        // 检查执行结果
        return parseResult(answer);
    }

    @Override
    public VmTask start(String vmId) throws Exception {
        logger.info("开始启动虚机：" + vmId);
        VMPowerOpAnswer answer = null;
        try {
            VMPowerOpCmd cmd = new VMPowerOpCmd();
            cmd.setVmId(vmId);
            cmd.setOpType(PowerOPType.powerOn);
            answer = cloudviewExecutor.execute(cmd);
        } catch (Exception e) {
            logger.error("调用虚拟化接口出错！", e);
            throw new Exception("调用虚拟化接口出错！", e);
        }
        // 检查执行结果
        return parseResult(answer);
    }

    @Override
    public VmTask stop(String vmId) throws Exception {
        logger.info("开始关闭虚机：" + vmId);
        VMPowerOpAnswer answer = null;
        try {
            VMPowerOpCmd powerOpCmd = new VMPowerOpCmd();
            powerOpCmd.setVmId(vmId);
            powerOpCmd.setOpType(PowerOPType.powerOff);
            answer = cloudviewExecutor.execute(powerOpCmd);
        } catch (Exception e) {
            logger.error("调用虚拟化接口出错！", e);
            throw new Exception("调用虚拟化接口出错！", e);
        }
        // 检查执行结果
        return parseResult(answer);
    }

    @Override
    public VmNet addNet(String vmId, VmNet net) throws Exception {
        logger.info("开始为虚机：" + vmId + "添加虚拟网络" + JsonUtil.toJson(net));
        // 参数校验
        if (net == null) {
            logger.error("虚拟网络对象为空！");
            throw new Exception("虚拟网络对象为空！");
        }
        if (StringUtils.isEmpty(net.getSubnet())) {
            logger.error("子网为空！");
            throw new Exception("子网为空！");
        }
        if (StringUtils.isEmpty(net.getOsPassword())) {
            logger.error("操作系统密码为空！");
            throw new Exception("操作系统密码为空！");
        }

        if (StringUtils.isEmpty(vmId)) {
            logger.error("修改目标虚机内部标识为空！");
            throw new Exception("修改目标虚机内部标识为空！");
        }

        // 调用虚拟化接口
        ReconfigVMAnswer answer = null;
        try {
            // CIDRUtils cidrUtils = new CIDRUtils(net.getSubnet());
            CIDRUtils cidrUtils = new CIDRUtils(net.getSubnet());
            ReconfigVMCmd reconfigVMCmd = new ReconfigVMCmd();
            reconfigVMCmd.setVmId(vmId);

            VMNicInfo vmNicInfo = new VMNicInfo();
            vmNicInfo.setAdapterIP(net.getIp());
            vmNicInfo.setAdapterGateWay(net.getGateway());
            vmNicInfo.setAdapterDNS(net.getDns());
            vmNicInfo.setNetworkId(net.getNetId());
            vmNicInfo.setAdapterOperation(VirtualDeviceConfigSpecOperation.add);
            vmNicInfo.setAdapterSubNet(cidrUtils.getNetworkAddress());
            vmNicInfo.setAdapterNetMask(cidrUtils.getNetMask());

            List<VMNicInfo> vmNicInfos = new LinkedList<VMNicInfo>();
            vmNicInfos.add(vmNicInfo);
            reconfigVMCmd.setNicInfos(vmNicInfos);
            reconfigVMCmd.setCurrentPassword(net.getOsPassword());

            answer = cloudviewExecutor.execute(reconfigVMCmd);
            logger.debug("answer=" + answer);
        } catch (Exception e) {
            logger.error("调用虚拟化接口出错！" + e.getMessage(), e);
            throw new Exception("调用虚拟化接口出错！" + e.getMessage(), e);
        }
        // 检查执行结果
        if (answer == null) {
            logger.error("虚拟化接口返回对象为空！");
            throw new Exception("虚拟化接口返回对象为空！");
        }
        if (answer.isSuccess()) {
            String taskId = answer.getTaskId();
            if (!StringUtils.isEmpty(taskId)) {
                logger.debug("成功调用虚拟化接口，获取taskId=" + taskId);
                VmTask taskInfo = new VmTask();
                taskInfo.setTaskId(taskId);
                taskInfo.setResourceId(answer.getResourceId());
                taskInfo.setTaskName(answer.getTaskName());
                taskInfo.setCreateTime(answer.getTaskCreateTime());
                net.setTaskId(taskId);
                net.setTaskInfo(taskInfo);
            }
            // 从接口中获取网卡内部ID（虚拟化层仅能提供name）
            if (CollectionUtils.isEmpty(answer.getNicInfos()) || answer.getNicInfos().get(0) == null) {
                logger.error("虚拟化接口返回网卡结果为空！");
                throw new Exception("虚拟化接口返回网卡结果为空！");
            }
            VMNicInfo nicInfo = answer.getNicInfos().get(0);
            logger.debug("虚拟化接口返回的磁盘信息：taskId=" + answer.getTaskId() + ",nicid=" + nicInfo.getAdapterId() + ",nicName="
                    + nicInfo.getAdapterName());
            net.setTaskId(answer.getTaskId());

            if ("1".equals(nicInfo.getAdapterId())) {
                net.setInternalId("4000");
            } else {
                net.setInternalId(nicInfo.getAdapterId());
            }
        } else {
            logger.error("虚拟化接口内部执行失败：" + answer.getErrMsg());
            throw new Exception("虚拟化接口内部执行失败：" + answer.getErrMsg());
        }
        return net;
    }

    @Override
    public VmTask removeNet(String vmId, VmNet net) throws Exception {
        logger.info("开始为虚机：" + vmId + "移除虚拟网络 " + JsonUtil.toJson(net));
        // 参数校验
        if (net == null) {
            logger.error("虚拟网络对象为空！");
            throw new Exception("虚拟网络对象为空！");
        }
        if (StringUtils.isEmpty(vmId)) {
            logger.error("修改目标虚机内部标识为空！");
            throw new Exception("修改目标虚机内部标识为空！");
        }
        if (StringUtils.isEmpty(net.getNetId())) {
            logger.error("传入的虚拟网络id为空！");
            throw new Exception("传入的虚拟网络id为空！");
        }

        // 调用虚拟化接口
        ReconfigVMAnswer answer = null;
        try {
            ReconfigVMCmd reconfigVMCmd = new ReconfigVMCmd();
            // reconfigVMCmd.setName("11123454");
            reconfigVMCmd.setVmId(vmId);

            VMNicInfo vmNicInfo = new VMNicInfo();
            vmNicInfo.setNetworkId(net.getNetId());
            vmNicInfo.setAdapterId(net.getInternalId());
            vmNicInfo.setAdapterOperation(VirtualDeviceConfigSpecOperation.remove);

            List<VMNicInfo> vmNicInfos = new LinkedList<VMNicInfo>();
            vmNicInfos.add(vmNicInfo);
            reconfigVMCmd.setNicInfos(vmNicInfos);
            answer = cloudviewExecutor.execute(reconfigVMCmd);
        } catch (Exception e) {
            logger.error("调用虚拟化接口出错！" + e.getMessage(), e);
            throw new Exception("调用虚拟化接口出错！" + e.getMessage(), e);
        }
        // 检查执行结果
        return parseResult(answer);
    }

    @Override
    public VmDisk addDisk(String vmId, VmDisk vmDisk) throws Exception {
        logger.info("开始为虚机：" + vmId + "添加磁盘 " + JsonUtil.toJson(vmDisk));
        // 参数校验
        if (vmDisk == null) {
            logger.error("磁盘对象为空！");
            throw new Exception("磁盘对象为空！");
        }
        if (StringUtils.isEmpty(vmId)) {
            logger.error("修改目标虚机内部标识为空！");
            throw new Exception("修改目标虚机内部标识为空！");
        }
        if (StringUtils.isEmpty(vmDisk.getTotalCapacity())) {
            logger.error("传入的存储池容量为空！");
            throw new Exception("传入的存储池容量为空！");
        }
        if (StringUtils.isEmpty(vmDisk.getsPoolId())) {
            logger.error("传入的存储池id为空！");
            throw new Exception("传入的存储池id为空！");
        }

        // 调用虚拟化接口
        ReconfigVMAnswer answer = null;
        ReconfigVMCmd reconfigVMCmd = new ReconfigVMCmd();
        VMDiskInfo diskInfo = new VMDiskInfo();
        List<VMDiskInfo> diskInfos = new LinkedList<VMDiskInfo>();
        try {
            reconfigVMCmd.setVmId(vmId);
            diskInfo.setDiskSizeGB(vmDisk.getTotalCapacity());
            diskInfo.setDatastoreId(vmDisk.getsPoolId());
            diskInfo.setDiskOperation(VirtualDeviceConfigSpecOperation.add);

            diskInfos.add(diskInfo);
            reconfigVMCmd.setDiskInfos(diskInfos);

            answer = cloudviewExecutor.execute(reconfigVMCmd);
            logger.debug("answer=" + answer);
        } catch (Exception e) {
            logger.error("调用虚拟化接口出错！" + e.getMessage(), e);
            throw new Exception("调用虚拟化接口出错！" + e.getMessage(), e);
        }
        // 检查执行结果
        if (answer == null) {
            logger.error("虚拟化接口返回对象为空！");
            throw new Exception("虚拟化接口返回对象为空！");
        }
        if (answer.isSuccess()) {
            String taskId = answer.getTaskId();
            if (!StringUtils.isEmpty(taskId)) {
                logger.debug("成功调用虚拟化接口，获取taskId=" + taskId);
                VmTask taskInfo = new VmTask();
                taskInfo.setTaskId(taskId);
                taskInfo.setCreateTime(answer.getTaskCreateTime());
                taskInfo.setResourceId(answer.getResourceId());
                taskInfo.setTaskName(answer.getTaskName());
                vmDisk.setTaskId(taskId);
                vmDisk.setTaskInfo(taskInfo);
            }

            // 检查参数合法性
            if (CollectionUtils.isEmpty(answer.getDiskInfos()) || answer.getDiskInfos().get(0) == null) {
                logger.error("虚拟化接口返回磁盘结果为空！");
                throw new Exception("虚拟化接口返回磁盘结果为空！");
            }
            diskInfo = answer.getDiskInfos().get(0);
            logger.debug("虚拟化接口返回的磁盘信息：taskId=" + answer.getTaskId() + ",diskId=" + diskInfo.getDiskId() + ",diskName="
                    + diskInfo.getName());
            vmDisk.setTaskId(answer.getTaskId());
            vmDisk.setInternalId(diskInfo.getDiskId());
        } else {
            logger.error("虚拟化接口内部执行失败：" + answer.getErrMsg());
            throw new Exception("虚拟化接口内部执行失败：" + answer.getErrMsg());
        }
        return vmDisk;
    }

    @Override
    public VmTask removeDisk(String vmId, VmDisk vmDisk) throws Exception {
        logger.info("开始为虚机：" + vmId + "移除磁盘 " + JsonUtil.toJson(vmDisk));
        // 参数校验
        if (vmDisk == null) {
            logger.error("磁盘对象为空！");
            throw new Exception("磁盘对象为空！");
        }

        if (StringUtils.isEmpty(vmDisk.getInternalId())) {
            logger.error("磁盘对象内部为空！");
            throw new Exception("磁盘对象内部为空！");
        }

        if (StringUtils.isEmpty(vmId)) {
            logger.error("修改目标虚机内部标识为空！");
            throw new Exception("修改目标虚机内部标识为空！");
        }

        // 调用虚拟化接口
        Answer answer = null;
        ReconfigVMCmd reconfigVMCmd = new ReconfigVMCmd();
        VMDiskInfo diskInfo = new VMDiskInfo();
        List<VMDiskInfo> diskInfos = new LinkedList<VMDiskInfo>();
        try {
            reconfigVMCmd.setVmId(vmId);
            diskInfo.setDiskId(vmDisk.getInternalId());
            diskInfo.setDiskOperation(VirtualDeviceConfigSpecOperation.remove);


            diskInfos.add(diskInfo);
            reconfigVMCmd.setDiskInfos(diskInfos);

            answer = cloudviewExecutor.execute(reconfigVMCmd);
        } catch (Exception e) {
            logger.error("调用虚拟化接口出错！" + e.getMessage(), e);
            throw new Exception("调用虚拟化接口出错！" + e.getMessage(), e);
        }
        // 检查执行结果
        return parseResult(answer);
    }

    /**
     * 检查执行结果
     * 
     * @param answer
     * @return
     * @throws Exception
     */
    private VmTask parseResult(Answer answer) throws Exception {
        VmTask taskInfo = new VmTask();
        if (answer == null) {
            logger.error("虚拟化接口返回对象为空！");
            throw new Exception("虚拟化接口返回对象为空！");
        }
        if (answer.isSuccess()) {
            String taskId = answer.getTaskId();
            Date createTime = answer.getTaskCreateTime();
            String resourceId = answer .getResourceId();
            String taskName = answer.getTaskName();
            logger.debug("成功调用虚拟化接口，获取taskId=" + taskId);
            taskInfo.setTaskId(taskId);
            taskInfo.setCreateTime(createTime);
            taskInfo.setResourceId(resourceId);
            taskInfo.setTaskName(taskName);
        } else {
            logger.error("虚拟化接口内部执行失败：" + answer.getErrMsg());
            throw new Exception("虚拟化接口内部执行失败：" + answer.getErrMsg());
        }
        return taskInfo;
    }

    @Override
    public List<VmDisk> listDisk(String vmId) throws Exception {
        logger.info("开始查询内部id为" + vmId + "的虚拟机的磁盘");
        List<VMDiskInfo> diskInfos = new LinkedList<VMDiskInfo>();
        List<VmDisk> vmDisks = new LinkedList<VmDisk>();
        QueryVMAnswer answer = null;
        
        if (StringUtils.isEmpty(vmId)) {
            logger.error("修改目标虚机内部标识为空！");
            throw new Exception("修改目标虚机内部标识为空！");
        }
        // 调用虚拟化接口
        try {
            QueryVMCmd cmd = new QueryVMCmd();
            cmd.setVmId(vmId);
            answer = cloudviewExecutor.execute(cmd);

        } catch (Exception e) {
            logger.error("调用虚拟化接口出错！", e);
            throw new Exception("调用虚拟化接口出错！", e);
        }
        // 检查执行结果
        if (answer == null) {
            logger.error("虚拟化接口返回对象为空！");
            throw new Exception("虚拟化接口返回对象为空！");
        }
        if (answer.isSuccess()) {
            if (answer.getVmList() == null) {
                logger.error("获取的虚拟机列表为空！");
                throw new Exception("获取的虚拟机列表为空！");
            }
            if (answer.getVmList().get(0).getDiskInfos() == null) {
                logger.error("获取的虚拟机磁盘列表为空！");
                throw new Exception("获取的虚拟机磁盘列表为空！");
            }
            diskInfos = answer.getVmList().get(0).getDiskInfos();

            VmTask taskInfo = new VmTask();
            taskInfo.setTaskId(answer.getTaskId());
            taskInfo.setResourceId(answer.getResourceId());
            taskInfo.setTaskName(answer.getTaskName());
            taskInfo.setCreateTime(answer.getTaskCreateTime());

            for (VMDiskInfo vMDiskInfo : diskInfos) {
                VmDisk vmDisk = new VmDisk();
                vmDisk.setTotalCapacity(vMDiskInfo.getDiskSizeGB());
                vmDisk.setsPoolId(vMDiskInfo.getDatastoreId());
                vmDisk.setInternalId(vMDiskInfo.getDiskId());
                vmDisk.setTaskInfo(taskInfo);
                vmDisks.add(vmDisk);
            }

        } else {
            logger.error("虚拟化接口内部执行失败：" + answer.getErrMsg());
            throw new Exception("虚拟化接口内部执行失败：" + answer.getErrMsg());
        }
        return vmDisks;
    }

    @Override
    public VmHost refreshVmByTask(String taskId, VmHost vmHost) throws Exception {
        logger.info("根据任务" + taskId + "刷新虚机状态：" + JsonUtil.toJson(vmHost));

        // TODO 检查是否包含模板ID（虚拟化接口需要）
        if (StringUtils.isEmpty(taskId)) {
            logger.error("校验失败：任务id为空！");
            throw new Exception("校验失败：任务id为空！");
        }
        if (vmHost == null) {
            logger.error("虚拟机对象为空！");
            throw new Exception("虚拟机对象为空！");
        }
        if (StringUtils.isEmpty(vmHost.getTemplateId())) {
            logger.error("校验失败：模板id为空！");
            throw new Exception("校验失败：模板id为空！");
        }

        // TODO 调用虚拟化接口（根据 任务ID+模板ID），查询任务状态
        logger.info("开始刷新虚机：{taskId=" + taskId + ",template=" + vmHost.getTemplateId() + "}");
        QueryTaskAnswer answer = null;
        QueryTaskCmd queryTaskCmd = new QueryTaskCmd();
        try {
            queryTaskCmd.setTaskId(taskId);
            queryTaskCmd.setVmId(vmHost.getTemplateId());
            answer = cloudviewExecutor.execute(queryTaskCmd);
        } catch (Exception e) {
            logger.error("调用虚拟化接口出错！" + e.getMessage(), e);
            throw new Exception("调用虚拟化接口出错！" + e.getMessage(), e);
        }
        // 检查执行结果
        if (answer == null) {
            logger.error("虚拟化接口返回对象为空！");
            throw new Exception("虚拟化接口返回对象为空！");
        }
        if (answer.isSuccess()) {
            // 检查参数合法性
            if (answer.getLsTaskInfo() == null || CollectionUtils.isEmpty(answer.getLsTaskInfo())) {
                logger.error("获取的任务列表为空！");
                throw new Exception("获取的虚拟机列表为空！");
            }
            VmTask taskInfo1 = new VmTask();
            taskInfo1.setTaskId(answer.getTaskId());
            taskInfo1.setResourceId(answer.getResourceId());
            taskInfo1.setTaskName(answer.getTaskName());
            taskInfo1.setCreateTime(answer.getTaskCreateTime());
            vmHost.setTaskInfo(taskInfo1);

            QueriedTaskInfo taskInfo = answer.getLsTaskInfo().get(0);
            if (taskInfo == null) {
                logger.error("获取的任务对象为空！taskInfo=" + taskInfo);
                throw new Exception("获取的任务对象为空！taskInfo=" + taskInfo);
            }
            if (StringUtils.isEmpty(taskInfo.getStatus())) {
                logger.error("获取的任务状态为空！");
                throw new Exception("获取的任务状态为空！");
            }
            // TODO 判断任务是否完成
            String taskStatus = taskInfo.getStatus();
            logger.debug("虚拟化接口返回的taskStatus=" + taskStatus);
            if (StringUtils.isEmpty(taskStatus)) {
                logger.error("虚拟化接口返回的taskStatus为空！");
                throw new Exception("虚拟化接口返回的taskStatus为空！");
            }
            if (taskStatus.equals("queued") || taskStatus.equals("running")) {
                // TODO 如果未完成，直接原样返回
                logger.debug("任务未完成！taskStatus=" + taskStatus);
            } else {
                // TODO 如果已完成，更新任务状态为“无”，判断是否成功
                logger.debug("任务已完成！taskStatus=" + taskStatus);
                // vmHost.setRunStatus(RunStatus.NONE);
                if (taskStatus.equals("success")) {
                    // TODO 如果成功，从结果中获取虚机ID，并填充
                    vmHost.setRunStatus(RunStatus.NONE);
                    logger.debug("任务创建成功！");
                    if (StringUtils.isEmpty(taskInfo.getEntityId())) {
                        logger.error("虚拟化接口返回的虚拟机id为空！");
                        throw new Exception("虚拟化接口返回的虚拟机id为空！");
                    } else {
                        vmHost.setInternalId(taskInfo.getEntityId());
                    }
                } else if (taskStatus.equals("error")) {
                    
                    if (RunStatus.DELETING.equals(vmHost.getRunStatus())) {

                        // 当事件服务器出现问题，创建完虚机又马上删除，数据库还没同步数据时，即还没获取内部id
                        if (StringUtils.isEmpty(vmHost.getInternalId())) {
                            vmHost.setVmStatus(VmStatus.DELETED);
                            vmHost.setRunStatus(RunStatus.NONE);
                            vmHost.setTaskId("");
                            return vmHost;
                        }

                        logger.error("任务执行失败，虚拟机删除失败！");
                        vmHost.setRunStatus(RunStatus.NONE);
                        return vmHost;
                    } else if (RunStatus.STARTING.equals(vmHost.getRunStatus())) {
                        logger.error("任务执行失败，虚拟机启动失败！");
                        vmHost.setRunStatus(RunStatus.NONE);
                        return vmHost;
                    } else if (RunStatus.STOPPING.equals(vmHost.getRunStatus())) {
                        logger.error("任务执行失败，虚拟机停止失败！");
                        vmHost.setRunStatus(RunStatus.NONE);
                        return vmHost;
                    } else if (RunStatus.CREATING.equals(vmHost.getRunStatus())) {
                        logger.error("任务执行失败，虚拟机创建失败！");
                        vmHost.setRunStatus(RunStatus.NONE);
                        return vmHost;
                    } else {
                        vmHost.setIsAvailable(false);
                        return vmHost;
                    }
                    
                }
            }
        } else {
            logger.error("虚拟化接口内部执行失败：" + answer.getErrMsg());
            throw new Exception("虚拟化接口内部执行失败：" + answer.getErrMsg());
        }
        return vmHost;
    }

    @Override
    public VmHost refreshVmById(String vmId, VmHost vmHost) throws Exception {
        logger.info("根据虚机唯一标识" + vmId + "刷新虚机状态：" + JsonUtil.toJson(vmHost));
        // TODO 从结果中获取任务ID、并更新
        if (vmHost == null) {
            logger.error("虚拟机对象为空！");
            throw new Exception("虚拟机对象为空！");
        }
        if (StringUtils.isEmpty(vmId)) {
            logger.error("校验失败：目标虚机内部标识为空！");
            throw new Exception("校验失败：目标虚机内部标识为空！");
        }

        // TODO 调用虚拟化接口（根据 虚机内部标识），查询虚机状态及任务状态
        logger.info("开始刷新虚机：{vmId=" + vmId + "}");
        QueryVMCmd cmd = new QueryVMCmd();
        QueryVMAnswer answer = null;
        try {
            cmd.setVmId(vmId);
            answer = cloudviewExecutor.execute(cmd);
        } catch (Exception e) {
            logger.error("调用虚拟化接口出错！", e);
            throw new Exception("调用虚拟化接口出错！", e);
        }
        // 检查执行结果
        if (answer == null) {
            logger.error("虚拟化接口返回对象为空！");
            throw new Exception("虚拟化接口返回对象为空！");
        }
        if (answer.isSuccess()) {
            // TODO 从结果中获取虚机状态、映射并更新

            VmTask taskInfo = new VmTask();
            taskInfo.setTaskId(answer.getTaskId());
            taskInfo.setResourceId(answer.getResourceId());
            taskInfo.setTaskName(answer.getTaskName());
            taskInfo.setCreateTime(answer.getTaskCreateTime());
            vmHost.setTaskInfo(taskInfo);

            logger.debug("开始更新虚拟机状态！");
            if (answer.getVmList() == null|| answer.getVmList().size()<=0) {
                logger.info("获取的虚拟机对象为空！");
                // 当事件服务器失效，虚拟机处于删除中，删除完成后数据库无法自动更新状态
                if (RunStatus.DELETING.equals(vmHost.getRunStatus())) {
                    logger.info("事件服务器失效，虚拟机处于删除中，删除完成后数据库无法自动更新状态！");
                    vmHost.setVmStatus(VmStatus.DELETED);
                    vmHost.setRunStatus(RunStatus.NONE);
                    vmHost.setTaskId("");
                    return vmHost;
                    // 事件服务器正常工作
                } else if (VmStatus.DELETED.equals(vmHost.getVmStatus())) {
                    logger.info("事件服务器正常工作，虚拟机删除正常！");
                    return vmHost;
                    // 后台执行了非法的删除操作，将虚拟机置为不可用
                } else {
                    logger.warn("后台执行了非法的删除操作，将虚拟机置为不可用！");
                    vmHost.setIsAvailable(false);
                    return vmHost;
                }
            }
            VMachine vmInfo = answer.getVmList().get(0);
            if (vmInfo.getVmTaskStatus() == null) {
                //TODO 当前无任务状态 任务状态设为NONE
                logger.debug("虚拟化接口返回任务对象为空！说明该虚机处于稳定状态");
                vmHost.setRunStatus(RunStatus.NONE);
                vmHost.setTaskId("");
                if ("suspended".equals(vmInfo.getPowerStatus()) || "poweredOn".equals(vmInfo.getPowerStatus())) {
                    // TODO 认为当前虚机状态为开机 虚机状态设为STARTED
                    logger.debug("该虚机处于已启动状态：STARTED！");
                    vmHost.setVmStatus(VmStatus.STARTED);
                    return vmHost;
                } else if ("poweredOff".equals(vmInfo.getPowerStatus())) {
                    // TODO 认为当前虚机状态为开机 虚机状态设为STOPPED
                    logger.debug("该虚机处于已停止状态：STOPPED！");
                    vmHost.setVmStatus(VmStatus.STOPPED);
                    return vmHost;
                } else {
                    logger.warn("虚拟机处于未知的稳定状态！");
                    throw new Exception("虚拟机处于未知的稳定状态！");
                }
            } else if ("starting".equals(vmInfo.getVmTaskStatus())) {
                //TODO 虚机启动中 任务状态设为STARTING
                logger.debug("该虚机处于STARTING的任务状态！");
                vmHost.setRunStatus(RunStatus.STARTING);
                vmHost.setTaskId(vmInfo.getTaskId());
                return vmHost;
            } else if ("stopping".equals(vmInfo.getVmTaskStatus())) {
                //TODO 虚机启动中 任务状态设为STOPPING
                logger.debug("该虚机处于STOPPING的任务状态！");
                vmHost.setRunStatus(RunStatus.STOPPING);
                vmHost.setTaskId(vmInfo.getTaskId());
                return vmHost;
            } else if ("deleting".equals(vmInfo.getVmTaskStatus())) {
                //TODO 虚机删除中 任务状态设为DELETING
                logger.debug("该虚机处于DELETING的任务状态！");
                vmHost.setRunStatus(RunStatus.DELETING);
                vmHost.setTaskId(vmInfo.getTaskId());
                return vmHost;
            }

        } else {
            logger.error("虚拟化接口内部执行失败：" + answer.getErrMsg());
            throw new Exception("虚拟化接口内部执行失败：" + answer.getErrMsg());
        }
        return vmHost;
    }

    @Override
    public VmTask setPassword(String vmId, String newPassword, String oldPassword) throws Exception {
        // TODO Auto-generated method stub
        logger.info("开始为虚机：" + vmId + "重设密码！");
        // 参数校验
        if (StringUtils.isEmpty(vmId)) {
            logger.error("修改目标虚机内部标识为空！");
            throw new Exception("修改目标虚机内部标识为空！");
        }
        if (StringUtils.isEmpty(oldPassword)) {
            logger.error("输入的旧密码为空！");
            throw new Exception("输入的旧密码为空！");
        }
        if (StringUtils.isEmpty(newPassword)) {
            logger.error("输入的新密码为空！");
            throw new Exception("输入的新密码为空！");
        }

        // 调用虚拟化接口
        ReconfigVMAnswer answer = null;
        try {
            ReconfigVMCmd reconfigVMCmd = new ReconfigVMCmd();
            reconfigVMCmd.setVmId(vmId);

            reconfigVMCmd.setCurrentPassword(oldPassword);
            reconfigVMCmd.setNewPassword(newPassword);

            answer = cloudviewExecutor.execute(reconfigVMCmd);
            logger.debug("answer=" + answer);
        } catch (Exception e) {
            logger.error("调用虚拟化接口出错！" + e.getMessage(), e);
            throw new Exception("调用虚拟化接口出错！" + e.getMessage(), e);
        }
        // 检查执行结果
        return parseResult(answer);
    }
    
    @Override
    public String getVncUrl(String vmId) throws Exception {
    	String vncUrl = "";
		try {
			QueryVMConsoleCmd queryVMConsoleCmd = new QueryVMConsoleCmd();
			queryVMConsoleCmd.setVmId(vmId);
			QueryVMConsoleAnswer answer = cloudviewExecutor.execute(queryVMConsoleCmd);
			vncUrl = answer.getConsoleURL();
		} catch (Exception e) {
			logger.error("获取vnc地址失败："+e.getMessage(),e);
			throw new Exception("获取vnc地址失败："+e.getMessage(),e);
		}    	
    	return vncUrl;
    }
    
}
