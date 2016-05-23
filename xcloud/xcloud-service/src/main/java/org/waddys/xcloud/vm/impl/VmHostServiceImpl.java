package org.waddys.xcloud.vm.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.waddys.xcloud.driver.IVmDriver;
import org.waddys.xcloud.res.service.service.vdc.ProviderVDCService;
import org.waddys.xcloud.util.JsonUtil;
import org.waddys.xcloud.util.PasswordUtil;
import org.waddys.xcloud.vm.bo.VmConfig;
import org.waddys.xcloud.vm.bo.VmHost;
import org.waddys.xcloud.vm.bo.VmNet;
import org.waddys.xcloud.vm.bo.VmTask;
import org.waddys.xcloud.vm.constant.RunStatus;
import org.waddys.xcloud.vm.constant.VmStatus;
import org.waddys.xcloud.vm.po.dao.VmHostDaoService;
import org.waddys.xcloud.vm.po.dao.VmNetDaoService;
import org.waddys.xcloud.vm.service.VmService;

/**
 * 虚机服务组件
 * 
 * @author zhangdapeng
 *
 */
@Service("vmHostService")
@Transactional(rollbackFor = Exception.class)
public class VmHostServiceImpl implements VmService {
    private static final Logger logger = LoggerFactory.getLogger(VmHostServiceImpl.class);
	
	@Autowired
    private VmHostDaoService vmHostDaoService;

    @Autowired
    private VmNetDaoService vmNetDaoService;

    @Autowired
    private ProviderVDCService vdcService;

    @Autowired
    private IVmDriver vmDriver;

    @Override
    public boolean exists(String name) {
        return vmHostDaoService.exists(name);
    }

    @Override
    public VmHost create(VmHost vmHost) throws Exception {
        logger.info("开始创建虚机：" + vmHost.getName());

        /**
         * 参数非空校验
         */
        if (StringUtils.isEmpty(vmHost.getName())) {
            logger.error("参数校验失败：name未设置！");
            throw new Exception("参数校验失败：name未设置！");
        }
        final String vdcId = vmHost.getVdcId();
        if (StringUtils.isEmpty(vdcId)) {
            logger.error("参数校验失败：vdc未设置！");
            throw new Exception("参数校验失败：vdc未设置！");
        }
        final int vcpuNumber = vmHost.getvCpuNumer();
        if (StringUtils.isEmpty(vcpuNumber)) {
            logger.error("参数校验失败：cpu数量未设置！");
            throw new Exception("参数校验失败：cpu数量未设置！");
        }
        final Long vmemCapacity = vmHost.getvMemCapacity();
        if (StringUtils.isEmpty(vmemCapacity)) {
            logger.error("参数校验失败：mem容量未设置！");
            throw new Exception("参数校验失败：mem容量未设置！");
        }
        final String storId = vmHost.getsPoolId();
        if (StringUtils.isEmpty(storId)) {
            logger.error("参数校验失败：存储池未设置！");
            throw new Exception("参数校验失败：存储池未设置！");
        }
        final Long storCapacity = vmHost.getStorCapacity();
        if (StringUtils.isEmpty(storCapacity)) {
            logger.error("参数校验失败：存储容量未设置！");
            throw new Exception("参数校验失败：存储容量未设置！");
        }

        /**
         * 1.重名验证
         */
        final String name = vmHost.getName();
        if (vmHostDaoService.exists(name)) {
            logger.error("存在同名虚机！");
            throw new Exception("存在同名虚机！");
        }

        /**
         * 2.调用资源接口，锁定逻辑资源（vdcId、vcpu个数、vmem容量，storId、stor容量）
         */
        logger.debug("开始锁定逻辑资源：vdcId=" + vdcId + ",vcpu=" + vcpuNumber + ",mem=" + vmemCapacity + ",storId=" + storId
                + ",storCap=" + storCapacity);
        try {
            vdcService.expenseProVDC(vdcId, vcpuNumber, vmemCapacity, storId, storCapacity);
        } catch (Exception e) {
            logger.error("锁定资源失败！", e);
            throw new Exception("锁定资源失败：" + e.getMessage(), e);
        }
        logger.debug("锁定资源成功：" + vmHost.getName());
    
        /**
         * 3.调用虚拟化接口 （入参：虚机名称、模板id、计算池id、vcpu数量、内存容量、存储池id、存储容量， 返回：虚机创建任务id）
         */
        logger.debug("开始调用虚拟化接口！" + name);
        if (StringUtils.isEmpty(vmHost.getOsPassword())) {
            String password = PasswordUtil.genPassword(8);
            vmHost.setOsPassword(password);
        }
        vmHost = vmDriver.create(vmHost);
        logger.debug("成功调用虚拟化接口:" + vmHost);
    
        /**
         * 4.增加数据库记录
         */
        VmHost result = null;
        // 添加虚机记录
        try {
            vmHost.setInternalName(name);
            vmHost.setStatus("A");
            vmHost.setCreateTime(new Date());
            vmHost.setRunStatus(RunStatus.CREATING);
            vmHost.setVmStatus(VmStatus.NONE);
            vmHost.setTaskId(vmHost.getTaskId());
            vmHost.setIsAssigned(false);
            result = vmHostDaoService.createVmHost(vmHost);
            // 添加任务信息
            result.setTaskInfo(vmHost.getTaskInfo());
        } catch (Exception e) {
            logger.error("添加虚机记录失败！", e);
            throw new Exception("添加虚机记录失败！", e);
        }
        // 添加网络记录
        List<VmNet> nets = vmHost.getNets();
        if (nets != null) {
            for (VmNet net : nets) {
                try {
                    net.setVmId(result.getId());
                    net.setCreateTime(new Date());
                    vmNetDaoService.add(net);
                } catch (Exception e) {
                    logger.error("添加网络记录失败：net=" + net.getIp(), e);
                }
            }
            result.setNets(nets);
        }
    
        logger.info("完成创建虚机：" + vmHost.getName());
        return result;
    }

    @Override
    public void deleteById(String id) throws Exception {
        logger.info("开始删除虚机：" + id);
        // 参数校验
        VmHost vmHost = vmHostDaoService.findById(id);
        if (vmHost == null)
            throw new Exception("目标虚机不存在！");
        // 逻辑删除：删除数据库记录（标记删除完成）
        try {
            vmHost.setName(vmHost.getName() + "- DELETED - " + new Date());
            vmHost.setRunStatus(RunStatus.NONE);
            vmHost.setVmStatus(VmStatus.DELETED);
            vmHost.setStatus("P");
            vmHostDaoService.updateVmHost(vmHost);
        } catch (Exception e) {
            throw new Exception("更新数据库状态失败！", e);
        }
        logger.info("完成删除虚机：" + id);
    }

    @Override
    public void update(VmHost vmHost) throws Exception {
        logger.info("开始更新虚机：" + vmHost);
        try {
            vmHostDaoService.updateVmHost(vmHost);
        } catch (Exception e) {
            throw new Exception("数据库操作失败！", e);
        }
    }

    @Override
    public VmTask remove(String id) throws Exception {
        logger.info("开始移除虚机：" + id);
        VmTask taskInfo = null;
        VmHost vmHost = vmHostDaoService.findById(id);
        if (vmHost == null)
            throw new Exception("目标虚机不存在！");
        String vmId = vmHost.getInternalId();
        // 前置条件：进行中的虚机 拒绝删除
        if (!RunStatus.NONE.equals(vmHost.getRunStatus())) {
            throw new Exception("虚机正在操作中，拒绝删除操作！");
        }
        // 判断逻辑：如果是创建失败的虚机，仅从数据库中删除即可
        if (VmStatus.NONE.equals(vmHost.getVmStatus()) || RunStatus.CreateError.equals(vmHost.getRunStatus())
                || !vmHost.getIsAvailable()) {
            // 逻辑删除：删除数据库记录（标记删除完成）
            this.deleteById(id);
            this.releaseRes(id);
        } else {
            // 物理删除：调用虚拟化接口
            taskInfo = vmDriver.delete(vmId);
            // 逻辑删除：删除数据库记录（标记为删除中）
            try {
                vmHost.setTaskId(taskInfo.getTaskId());
                vmHost.setRunStatus(RunStatus.DELETING);
                vmHostDaoService.updateVmHost(vmHost);
            } catch (Exception e) {
                throw new Exception("更新数据库状态失败！", e);
            }
        }
        logger.info("完成移除虚机：" + id);
        return taskInfo;
    }

    @Override
    public void releaseRes(String id) throws Exception {
        logger.info("开始释放虚机资源：" + id);
        VmHost vmHost = vmHostDaoService.findById(id);
        // 非空校验
        if (vmHost == null) {
            logger.error("目标虚机不存在！");
            throw new Exception("目标虚机不存在！");
        }
        // 调用接口
        try {
            String vdcId = vmHost.getVdcId();
            int vCpuNumber = vmHost.getvCpuNumer();
            Long vMemCap = vmHost.getvMemCapacity();
            String sPoolId = vmHost.getsPoolId();
            Long storCap = vmHost.getStorCapacity();
            logger.debug("开始释放资源：{vdcId:" + vdcId + ",vCpuNumber:" + vCpuNumber + ",vMemCap:" + vMemCap + ",sPoolId:"
                    + sPoolId + ",storCap:" + storCap + "}");
            vdcService.recycleProVDC(vdcId, vCpuNumber, vMemCap, sPoolId, storCap);
        } catch (Exception e) {
            logger.error("释放虚机资源异常！", e);
            throw new Exception("释放虚机资源异常！" + e.getMessage(), e);
        }
        logger.info("完成释放虚机资源：" + id);
    }

    @Override
    public VmTask resetPassword(String id) throws Exception {
        logger.info("开始配置虚机：" + id);
        VmTask taskInfo = null;
        // 参数校验
        VmHost vmHost = vmHostDaoService.findById(id);
        if (vmHost == null) {
            logger.error("目标虚机不存在！");
            throw new Exception("目标虚机不存在！");
        }
        if (StringUtils.isEmpty(vmHost.getInternalId())) {
            throw new Exception("虚机内部标识为空！");
        }
        // 约束条件
        if (VmStatus.NONE.equals(vmHost.getVmStatus())) {
            logger.error("拒绝操作：目标虚机未初始化！");
            throw new Exception("拒绝操作：目标虚机未初始化！");
        }
        if (!vmHost.getIsAvailable()) {
            throw new Exception("目标虚机不可用！");
        }

        // 生成随机密码
        String newPassword = PasswordUtil.genPassword(8);

        // 物理操作
        String internalId = vmHost.getInternalId();
        String oldPassword = vmHost.getOsPassword();
        taskInfo = vmDriver.setPassword(internalId, newPassword, oldPassword);

        // 逻辑操作
        try {
            vmHost.setOsPassword(newPassword);
            vmHostDaoService.updateVmHost(vmHost);
        } catch (Exception e) {
            throw new Exception("更新数据库失败！", e);
        }
        return taskInfo;
    }

    @Override
    public VmTask config(String id, VmConfig vmConfig) throws Exception {
        logger.info("开始配置虚机：" + id + ",config:" + JsonUtil.toJson(vmConfig));
        VmTask taskInfo = null;
        // 参数校验
        VmHost vmHost = vmHostDaoService.findById(id);
        if (vmHost == null) {
            logger.error("目标虚机不存在！");
            throw new Exception("目标虚机不存在！");
        }
        if (StringUtils.isEmpty(vmHost.getInternalId())) {
            throw new Exception("虚机内部标识为空！");
        }
        // 约束条件
        if (VmStatus.NONE.equals(vmHost.getVmStatus())) {
            logger.error("拒绝操作：目标虚机未初始化！");
            throw new Exception("拒绝操作：目标虚机未初始化！");
        }
        if (!vmHost.getIsAvailable()) {
            throw new Exception("目标虚机不可用！");
        }

        // 资源变更
        String vdcId = vmHost.getVdcId();
        Integer vCpuNum = vmConfig.getvCpuNumer() - vmHost.getvCpuNumer();
        Long vMemCap = vmConfig.getvMemCapacity() - vmHost.getvMemCapacity();
        logger.debug("同步预占逻辑资源：{vdc:" + vdcId + ",cpu:" + vCpuNum + ",mem:" + vMemCap + "}");
        lockCpool(vdcId, vCpuNum, vMemCap);

        // 物理操作
        String internalId = vmHost.getInternalId();
        taskInfo = vmDriver.config(internalId, vmConfig);

        // 逻辑操作
        try {
            if (vmConfig.getvCpuNumer() != null) {
                vmHost.setvCpuNumer(vmConfig.getvCpuNumer());
            }
            if (vmConfig.getvMemCapacity() != null) {
                vmHost.setvMemCapacity(vmConfig.getvMemCapacity());
            }
            vmHostDaoService.updateVmHost(vmHost);
        } catch (Exception e) {
            throw new Exception("更新数据库失败！", e);
        }
        return taskInfo;
    }

    /**
     * 锁定计算池资源
     * 
     * @param vdcId
     *            提供者vdc标识
     * @param vCpuNum
     *            变更的cpu数量（相对值，负值代表在原基础上减少）
     * @param vMemCap
     *            变更的mem容量（相对值，负值代表在原基础上减少）
     * @throws Exception
     */
    private void lockCpool(String vdcId, Integer vCpuNum, Long vMemCap) throws Exception {
        try {
            if (vCpuNum > 0) {
                vdcService.expenseProVDCCpu(vdcId, vCpuNum);
            } else if (vCpuNum < 0) {
                vCpuNum = -1 * vCpuNum;
                vdcService.recycleProVDCCpu(vdcId, vCpuNum);
            }
        } catch (Exception e) {
            logger.error("变更cpu资源失败！", e);
            throw new Exception("变更cpu资源失败！" + e.getMessage(), e);
        }
        try {
            if (vMemCap > 0) {
                vdcService.expenseProVDCMemory(vdcId, vMemCap);
            } else if (vMemCap < 0) {
                vMemCap = -1 * vMemCap;
                vdcService.recycleProVDCMemory(vdcId, vMemCap);
            }
        } catch (Exception e) {
            logger.error("变更内存资源失败！", e);
            throw new Exception("变更内存资源失败！" + e.getMessage(), e);
        }
    }

    @Override
    public VmTask start(String id) throws Exception {
        logger.info("开始启动虚机：" + id);
        VmTask taskInfo = null;
        // 参数校验
        VmHost vmHost = vmHostDaoService.findById(id);
        if (vmHost == null)
            throw new Exception("目标虚机不存在！");
        String vmId = vmHost.getInternalId();
        if (StringUtils.isEmpty(vmId)) {
            throw new Exception("虚机内部标识为空！");
        }
        // 约束条件
        if (!vmHost.getIsAvailable()) {
            throw new Exception("目标虚机不可用！");
        }
        // 物理操作
        taskInfo = vmDriver.start(vmId);
        // 修改状态
        try {
            vmHost.setTaskId(taskInfo.getTaskId());
            vmHost.setRunStatus(RunStatus.STARTING);
            vmHostDaoService.updateVmHost(vmHost);
        } catch (Exception e) {
            throw new Exception("更新数据库状态失败！", e);
        }
        logger.info("完成启动虚机：" + id);
        return taskInfo;
    }

    @Override
    public VmTask stop(String id) throws Exception {
        logger.info("开始关闭虚机：" + id);
        VmTask taskInfo = null;
        // 参数校验
        VmHost vmHost = vmHostDaoService.findById(id);
        if (vmHost == null)
            throw new Exception("目标虚机不存在！");
        String vmId = vmHost.getInternalId();
        if (StringUtils.isEmpty(vmId)) {
            throw new Exception("虚机内部标识为空！");
        }
        // 约束条件
        if (!vmHost.getIsAvailable()) {
            throw new Exception("目标虚机不可用！");
        }
        // 物理操作
        taskInfo = vmDriver.stop(vmId);
        // 修改状态
        try {
            vmHost.setTaskId(taskInfo.getTaskId());
            vmHost.setRunStatus(RunStatus.STOPPING);
            vmHostDaoService.updateVmHost(vmHost);
        } catch (Exception e) {
            throw new Exception("更新数据库状态失败！", e);
        }
        logger.info("完成关闭虚机：" + id);
        return taskInfo;
    }

    @Override
    public VmHost refresh(String id) throws Exception {
        logger.info("开始刷新虚机：" + id);
        VmHost result = null;
        // 参数校验
        VmHost vmHost = vmHostDaoService.findById(id);
        if (vmHost == null)
            throw new Exception("目标虚机不存在！");
        // 物理操作
        String vmId = vmHost.getInternalId();
        if (StringUtils.isEmpty(vmId)) {
            String taskId = vmHost.getTaskId();
            result = vmDriver.refreshVmByTask(taskId, vmHost);
        } else {
            result = vmDriver.refreshVmById(vmId, vmHost);
        }
        // 逻辑操作
        if (VmStatus.DELETED.equals(result.getVmStatus())) {
            this.releaseRes(result.getId());
            this.deleteById(id);
        } else {
            this.update(result);
        }
        return result;
    }

	@Override
	public String getVncUrl(String id) throws Exception {
        logger.info("开始刷新虚机：" + id);
        String vncUrl = "";
        // 参数校验
        VmHost vmHost = vmHostDaoService.findById(id);
        if (vmHost == null)
            throw new Exception("目标虚机不存在！");
        String vmId = vmHost.getInternalId();
        if (StringUtils.isEmpty(vmId)) {
        	throw new Exception("虚机内部标识不存在！");
        }
        // 物理操作
        vncUrl = vmDriver.getVncUrl(vmId);
        logger.debug("get vncUrl: "+vncUrl);
        return vncUrl;
	}

    @Override
    public void assign(String id, String userId) throws Exception {
        logger.info("开始分配虚机：" + id);
        VmHost vmHost = vmHostDaoService.findById(id);
        if (vmHost == null)
            throw new Exception("目标虚机不存在！");
        vmHost.setOwnerId(userId);
        vmHost.setIsAssigned(true);
        try {
            vmHostDaoService.updateVmHost(vmHost);
        } catch (Exception e) {
            throw new Exception("更新数据库状态失败！", e);
        }
    }

    @Override
    public void revoke(String id, String userId) throws Exception {
        logger.info("开始回收虚机：" + id);
        VmHost vmHost = vmHostDaoService.findById(id);
        if (vmHost == null)
            throw new Exception("目标虚机不存在！");
        vmHost.setOwnerId("");
        vmHost.setIsAssigned(false);
        try {
            vmHostDaoService.updateVmHost(vmHost);
        } catch (Exception e) {
            throw new Exception("更新数据库状态失败！", e);
        }
    }

    @Override
    public VmHost findById(String id) {
        return vmHostDaoService.findById(id);
    }

    @Override
    public VmHost findByName(String name) {
        VmHost result = null;
        VmHost search = new VmHost();
        search.setName(name);
        List<VmHost> vms = vmHostDaoService.findByBO(search);
        if (!CollectionUtils.isEmpty(vms)) {
            result = new VmHost();
            BeanUtils.copyProperties(vms.get(0), result);
        }
        return result;
    }

    @Override
    public Long total(char status, String name) {
        // TODO Auto-generated method stub
        return vmHostDaoService.countByStatusAndname(status, name);
    }

    @Override
    public VmHost findByInternalId(String internalId) {
        VmHost result = null;
        VmHost search = new VmHost();
        search.setInternalId(internalId);
        // List<VmHost> vms = vmHostDaoService.findByBO(search,
        // null).getContent();
        List<VmHost> vms = vmHostDaoService.findByBO(search);
        if (!CollectionUtils.isEmpty(vms)) {
            result = new VmHost();
            BeanUtils.copyProperties(vms.get(0), result);
        }
        return result;
    }

    @Override
    public VmHost findByTaskId(String taskId) {
        VmHost result = null;
        VmHost search = new VmHost();
        search.setTaskId(taskId);
        List<VmHost> vms = vmHostDaoService.findByBO(search);
        if (!CollectionUtils.isEmpty(vms)) {
            result = new VmHost();
            BeanUtils.copyProperties(vms.get(0), result);
        }
        return result;
    }

    @Override
    public Page<VmHost> pageAll(VmHost search, PageRequest pageRequest) {
        if (search == null)
            search = new VmHost();
        return vmHostDaoService.findByBO(search, pageRequest);
    }

    @Override
    public Page<VmHost> pageByPID(String pid, VmHost search, PageRequest pageRequest) {
        // TODO Auto-generated method stub
        return vmHostDaoService.pageByProject(pid, search, pageRequest);
    }

    @Override
    public Page<VmHost> pageByOID(String oid, VmHost search, PageRequest pageRequest) {
        if (search == null)
            search = new VmHost();
        search.setOrgId(oid);
        return vmHostDaoService.findByBO(search, pageRequest);
    }

    @Override
    public Page<VmHost> pageByUID(String uid, VmHost search, PageRequest pageRequest) {
        if (search == null)
            search = new VmHost();
        search.setOwnerId(uid);
        return vmHostDaoService.findByBO(search, pageRequest);
    }

    @Override
    public Page<VmHost> pageByVDC(String vdcId, VmHost search, PageRequest pageRequest) {
        search.setVdcId(vdcId);
        return vmHostDaoService.findByBO(search, pageRequest);
    }

    @Override
    public Page<VmHost> findByHavingTask(VmHost search, Pageable pageable) {
        return vmHostDaoService.findByHavingTask(search, pageable);
    }

    @Override
    public List<VmHost> listAll() {
        List<VmHost> list = null;
        list = vmHostDaoService.findAllVmHost();
        return list;
    }

    @Override
    public List<VmHost> listByVDC(String vdcId, VmHost search) {
        if (search == null)
            search = new VmHost();
        search.setVdcId(vdcId);
        return vmHostDaoService.findByBO(search);
    }

    public ProviderVDCService getVdcService() {
        return vdcService;
    }

    public void setVdcService(ProviderVDCService vdcService) {
        this.vdcService = vdcService;
    }

    public IVmDriver getVmDriver() {
        return vmDriver;
    }

    public void setVmDriver(IVmDriver vmDriver) {
        this.vmDriver = vmDriver;
    }

}
