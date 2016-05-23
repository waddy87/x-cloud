/**
 * 
 */
package org.waddys.xcloud.vm.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.waddys.xcloud.driver.IVmDriver;
import org.waddys.xcloud.res.service.service.vdc.ProvideVDCStoragePoolService;
import org.waddys.xcloud.util.JsonUtil;
import org.waddys.xcloud.vm.bo.VmDisk;
import org.waddys.xcloud.vm.bo.VmHost;
import org.waddys.xcloud.vm.constant.VmStatus;
import org.waddys.xcloud.vm.po.dao.VmDiskDaoService;
import org.waddys.xcloud.vm.po.dao.VmHostDaoService;
import org.waddys.xcloud.vm.service.VmDiskService;

/**
 * 虚机磁盘服务组件
 * 
 * @author zhangdapeng
 *
 */
@Service("vmDiskService")
public class VmDiskServiceImpl implements VmDiskService {
    private static final Logger logger = LoggerFactory.getLogger(VmDiskServiceImpl.class);

    @Autowired
    private IVmDriver vmDriver;

    @Autowired
    private VmDiskDaoService vmDiskDaoService;

    @Autowired
    private VmHostDaoService vmHostDaoService;

    @Autowired
    private ProvideVDCStoragePoolService storService;

    /* (non-Javadoc)
     * @see org.waddys.xcloud.vm.service.VmDiskService#add(org.waddys.xcloud.vm.bo.VmDisk)
     */
    @Override
    public VmDisk add(VmDisk vmDisk) throws Exception {
        logger.info("开始添加磁盘：" + JsonUtil.toJson(vmDisk));
        // 检查目标
        VmDisk result = null;
        VmHost vmHost = vmHostDaoService.findById(vmDisk.getVmId());
        if (vmHost == null || VmStatus.DELETED.equals(vmHost.getStatus())) {
            logger.error("所属虚机已不存在!");
            throw new Exception("所属虚机已不存在!");
        }
        String vmId = vmHost.getInternalId();
        // 锁定资源
        try {
            String poolId = vmDisk.getLogicalPoolId();
            Long capcity = vmDisk.getTotalCapacity();
            logger.debug("开始锁定资源：{pool:" + poolId + ",capcity:" + capcity + "}");
            storService.expenseProvideVDCStoragePool(vmDisk.getLogicalPoolId(), vmDisk.getTotalCapacity());
        } catch (Exception e) {
            logger.error("锁定资源失败！", e);
            throw new Exception("锁定资源失败：" + e.getMessage(), e);
        }
        // 物理添加：调用虚拟化接口
        vmDisk = vmDriver.addDisk(vmId, vmDisk);
        logger.debug("调用虚拟化接口成功：disk内部标识=" + vmDisk.getInternalId());
        // 逻辑添加
        try {
            vmDisk.setCreateTime(new Date());
            result = vmDiskDaoService.add(vmDisk);
            // 更新虚机占用存储池总容量信息
            Long totalStorCap = vmHost.getStorCapacity() + vmDisk.getTotalCapacity();
            vmHost.setStorCapacity(totalStorCap);
            vmHostDaoService.updateVmHost(vmHost);
        } catch (Exception e) {
            logger.error("添加数据库记录失败！", e);
            throw new Exception("添加数据库记录失败！", e);
        }
        logger.info("完成添加磁盘：" + vmDisk);
        return result;
    }

    /* (non-Javadoc)
     * @see org.waddys.xcloud.vm.service.VmDiskService#deleteById(java.lang.String)
     */
    @Override
    public void deleteById(String diskId) throws Exception {
        logger.info("开始删除磁盘：diskId=" + diskId);
        // 检查目标
        VmDisk vmDisk = vmDiskDaoService.findById(diskId);
        if (vmDisk == null) {
            logger.warn("目标磁盘已不存在！diskId=" + diskId);
            return;
        }
        VmHost vmHost = vmHostDaoService.findById(vmDisk.getVmId());
        if (vmHost == null || VmStatus.DELETED.equals(vmHost.getStatus())) {
            logger.error("所属虚机已不存在!");
            throw new Exception("所属虚机已不存在!");
        }
        String vmId = vmHost.getInternalId();
        // 物理添加：调用虚拟化接口
        vmDriver.removeDisk(vmId, vmDisk);
        // 释放资源
        try {
            storService.recycleProvideVDCStoragePool(vmDisk.getLogicalPoolId(), vmDisk.getTotalCapacity());
        } catch (Exception e) {
            logger.error("释放资源失败！", e);
            throw new Exception("释放资源失败：" + e.getMessage(), e);
        }
        // 逻辑删除
        try {
            // vmDisk.setTaskId(taskId);
            vmDiskDaoService.delete(vmDisk);
            // 更新虚机占用存储池总容量信息
            Long totalStorCap = vmHost.getStorCapacity() - vmDisk.getTotalCapacity();
            vmHost.setStorCapacity(totalStorCap);
            vmHostDaoService.updateVmHost(vmHost);
        } catch (Exception e) {
            logger.error("删除数据库记录失败！", e);
            throw new Exception("删除数据库记录失败！", e);
        }
        logger.info("完成删除磁盘：" + vmDisk);
    }

    /* (non-Javadoc)
     * @see org.waddys.xcloud.vm.service.VmDiskService#listByVm(java.lang.String)
     */
    @Override
    public List<VmDisk> listByVm(String vmId) {
        logger.debug("根据虚机查询网络列表：vmId=" + vmId);
        VmDisk search = new VmDisk();
        search.setVmId(vmId);
        return vmDiskDaoService.findByBO(search, null).getContent();
    }

    @Override
    public Page<VmDisk> pageByVm(String vmId, PageRequest pageRequest) {
        logger.debug("根据虚机查询网络列表：vmId=" + vmId);
        VmDisk search = new VmDisk();
        search.setVmId(vmId);
        return vmDiskDaoService.findByBO(search, pageRequest);
    }

}
