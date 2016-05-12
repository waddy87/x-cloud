package com.sugon.cloudview.cloudmanager.vm.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sugon.cloudview.cloudmanager.exception.ApiException;
import com.sugon.cloudview.cloudmanager.org.bo.Organization;
import com.sugon.cloudview.cloudmanager.org.service.OrganizationService;
import com.sugon.cloudview.cloudmanager.resource.service.bo.vdc.ProviderVDC;
import com.sugon.cloudview.cloudmanager.resource.service.bo.vdc.StoragePool;
import com.sugon.cloudview.cloudmanager.resource.service.bo.vnet.NetPool;
import com.sugon.cloudview.cloudmanager.resource.service.exception.vdc.VDCException;
import com.sugon.cloudview.cloudmanager.resource.service.service.vdc.ProviderVDCService;
import com.sugon.cloudview.cloudmanager.resource.service.service.vdc.StoragePoolService;
import com.sugon.cloudview.cloudmanager.resource.service.service.vnet.NetPoolService;
import com.sugon.cloudview.cloudmanager.templet.service.VMTempletService;
import com.sugon.cloudview.cloudmanager.templet.service.entity.VMTempletE;
import com.sugon.cloudview.cloudmanager.usermgmt.service.bo.User;
import com.sugon.cloudview.cloudmanager.usermgmt.service.service.UserService;
import com.sugon.cloudview.cloudmanager.util.PageUtil;
import com.sugon.cloudview.cloudmanager.vm.bo.VmConfig;
import com.sugon.cloudview.cloudmanager.vm.bo.VmDisk;
import com.sugon.cloudview.cloudmanager.vm.bo.VmHost;
import com.sugon.cloudview.cloudmanager.vm.bo.VmNet;
import com.sugon.cloudview.cloudmanager.vm.bo.VmTask;
import com.sugon.cloudview.cloudmanager.vm.bo.VmTemplate;
import com.sugon.cloudview.cloudmanager.vm.constant.ActionType;
import com.sugon.cloudview.cloudmanager.vm.service.VmDiskService;
import com.sugon.cloudview.cloudmanager.vm.service.VmNetService;
import com.sugon.cloudview.cloudmanager.vm.service.VmService;

/**
 * 虚机API接口
 * 
 * @author zhangdapeng
 *
 */
@RestController
@RequestMapping(path = "/api/vms", produces = { "application/json;charset=UTF-8" })
public class VmApi {
	
    private static final Logger logger = LoggerFactory.getLogger(VmApi.class);

    @Autowired
    private VmService vmService;

    @Autowired
    private VmNetService vmNetService;

    @Autowired
    private VmDiskService vmDiskService;

    @Autowired
    private OrganizationService orgService;

    @Autowired
    private UserService userService;

    @Autowired
    private VMTempletService templateService;

    @Autowired
    private ProviderVDCService vdcService;

    @Autowired
    private StoragePoolService sPoolService;

    @Autowired
    private NetPoolService netPoolService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public VmHost show(@PathVariable String id) throws ApiException {
        VmHost vmHost = null;
        try {
            vmHost = vmService.findById(id);
        } catch (Exception e) {
            logger.error("查询虚机详情出错！", e);
            throw new ApiException("查询虚机详情出错！", e);
        }
        return buildVmHostVO(vmHost);
    }

    @Deprecated
    @RequestMapping(value = "/sample", method = RequestMethod.GET)
    public Page<VmHost> list(
@RequestParam(required = false) String name, // 根据名称模糊查询
            @RequestParam(required = false) String pid, // 项目唯一标识
            @RequestParam(required = false) String oid, // 组织唯一标识
            @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer per_page)
                    throws ApiException {
        logger.info("begin process request: {name=" + name + ",page=" + page + ",per_page=" + per_page + "}");

        // 构造分页对象
        PageRequest pageRequest = PageUtil.buildPageRequest(page, per_page);

        // 构造查询对象
        VmHost search = new VmHost(name);
        if (name == null)
            search.setName("");

        // 根据情况执行不同的查询
        Page<VmHost> result = null;
        try {
            if (!StringUtils.isEmpty(pid)) {
                result = vmService.pageByPID(pid, search, pageRequest);
            } else if (!StringUtils.isEmpty(oid)) {
                result = vmService.pageByOID(oid, search, pageRequest);
            } else {
                result = vmService.pageAll(search, pageRequest);
            }
        } catch (Exception e) {
            logger.error("查询数据库出错！", e);
            throw new ApiException("查询数据库出错！", e);
        }

        // 返回结果
        return result.map(vmConverter);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<VmHost> list(VmHost search, @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer per_page) throws ApiException {
        logger.info("收到客户端查询请求：" + search);

        // 构造分页对象
        PageRequest pageRequest = PageUtil.buildPageRequest(page, per_page);

        // 根据情况执行不同的查询
        Page<VmHost> result = null;
        try {
            result = vmService.pageAll(search, pageRequest);
        } catch (Exception e) {
            logger.error("查询数据库出错！", e);
            throw new ApiException("查询数据库出错！", e);
        }

        // 返回结果
        return result.map(vmConverter);
    }

    @RequestMapping(method = RequestMethod.POST)
    public VmHost create(@RequestBody VmHost vmHost) throws ApiException {
        logger.info("begin process request: " + vmHost);
        VmHost result = null;
        String vmName = vmHost.getName();
        if (StringUtils.isEmpty(vmName)) {
            throw new ApiException("虚机名称为空！");
        }
        try {
            result = vmService.create(vmHost);
        } catch (TransactionSystemException te) {
            logger.error("创建虚机失败！", te);
            throw new ApiException("创建虚机失败！" + te.getApplicationException().getMessage());
        } catch (Exception e) {
            logger.error("创建虚机失败！", e);
            throw new ApiException("创建虚机失败！" + e.getMessage(), e);
        }
        logger.info("end process request: " + result);
        return result;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id) throws ApiException {
        logger.info("收到删除虚机请求: " + id);
        try {
            vmService.deleteById(id);
        } catch (Exception e) {
            logger.error("删除虚机失败！", e);
            throw new ApiException("删除虚机失败！" + e.getMessage(), e);
        }
        logger.info("完成删除虚机请求: " + id);
	}

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public void update(@PathVariable String id, @RequestBody VmHost vmHost) throws ApiException {
        logger.info("收到更新虚机请求: " + vmHost);
        VmHost target = null;
        try {
            target = vmService.findById(id);
        } catch (Exception e1) {
            logger.error("查询目标虚机出错！", e1);
            throw new ApiException("查询目标虚机出错！", e1);
        }
        if (target == null) {
            logger.error("目标虚机不存在！");
            throw new ApiException("目标虚机不存在！");
        }
        try {
            String name = vmHost.getName();
            if (!StringUtils.isEmpty(name)) {
                target.setName(vmHost.getName());
            } else {
                if (vmService.exists(name)) {
                    logger.error("虚机名称已存在！");
                    throw new ApiException("虚机名称已存在！");
                }
            }
            if (!StringUtils.isEmpty(vmHost.getRemarks())) {
                target.setRemarks(vmHost.getRemarks());
            }
            vmService.update(target);
        } catch (Exception e) {
            logger.error("更新虚机出错！", e);
            throw new ApiException("更新虚机失败！" + e.getMessage(), e);
        }
        logger.info("完成更新虚机请求: " + vmHost);
	}

    @RequestMapping(value = "/{id}/config", method = RequestMethod.POST)
    public VmTask config(@PathVariable String id, @RequestBody VmConfig vmConfig) throws ApiException {
        logger.info("收到配置虚机请求: " + vmConfig);
        VmTask taskInfo = null;
        try {
            taskInfo = vmService.config(id, vmConfig);
        } catch (TransactionSystemException te) {
            logger.error("配置虚机失败！", te);
            throw new ApiException("配置虚机失败！" + te.getApplicationException().getMessage());
        } catch (Exception e) {
            logger.error("配置虚机出错！", e);
            throw new ApiException("配置虚机失败！" + e.getMessage(), e);
        }
        logger.info("完成配置虚机请求: " + vmConfig);
        return taskInfo;
    }

    @RequestMapping(value = "/{id}/action", method = RequestMethod.POST)
    public VmHost action(@PathVariable String id, @RequestBody ActionType actionType,
            @RequestBody(required = false) VmHost target) throws ApiException {
        logger.info("收到操作虚机请求: id=" + id + ",action=" + actionType + ",target=" + target);
        VmHost vmHost = vmService.findById(id);
        // 前置条件
        if (vmHost == null) {
            throw new ApiException("目标虚机不存在！");
        }
        // if (!RunStatus.NONE.equals(vmHost.getRunStatus())) {
        // throw new ApiException("拒绝操作，虚机正在操作中！");
        // }
        // 执行相应动作
        switch (actionType) {
        case START:
            try {
                VmTask taskInfo = vmService.start(id);
                vmHost.setTaskInfo(taskInfo);
            } catch (Exception e) {
                throw new ApiException("启动虚机失败！" + e.getMessage(), e);
            }
            break;
        case STOP:
            try {
                VmTask taskInfo = vmService.stop(id);
                vmHost.setTaskInfo(taskInfo);
            } catch (Exception e) {
                throw new ApiException("关闭虚机失败！" + e.getMessage(), e);
            }
            break;
        case REFRESH:
            try {
                vmHost = vmService.refresh(id);
            } catch (Exception e) {
                throw new ApiException("刷新虚机失败！" + e.getMessage(), e);
            }
            break;
        case ASSIGN:
            try {
                String userId = target.getOwnerId();
                vmService.assign(id, userId);
            } catch (Exception e) {
                throw new ApiException("分配虚机失败！" + e.getMessage(), e);
            }
            break;
        case REVOKE:
            try {
                String userId = null;
                if (target != null) {
                    userId = target.getOwnerId();
                }
                vmService.revoke(id, userId);
            } catch (Exception e) {
                throw new ApiException("回收虚机失败！" + e.getMessage(), e);
            }
            break;
        case DESTROY:
            try {
                VmTask taskInfo = vmService.remove(id);
                vmHost.setTaskInfo(taskInfo);
            } catch (TransactionSystemException te) {
                logger.error("删除虚机失败！", te);
                throw new ApiException("删除虚机失败！" + te.getApplicationException().getMessage());
            } catch (Exception e) {
                throw new ApiException("销毁虚机失败！" + e.getMessage(), e);
            }
            break;
        case RELEASE:// 释放虚机资源
            try {
                vmService.releaseRes(id);
            } catch (Exception e) {
                throw new ApiException("释放资源失败！" + e.getMessage(), e);
            }
            break;
        case RESET_PASSWORD:// 释放虚机资源
            try {
                vmService.resetPassword(id);
            } catch (Exception e) {
                throw new ApiException("释放资源失败！" + e.getMessage(), e);
            }
            break;
        default:
            throw new ApiException("亲，系统不支持此该操作！");
        }
        return vmHost;
    }

    @RequestMapping(value = "/{id}/nets", method = RequestMethod.GET)
    public Page<VmNet> listNet(@PathVariable("id") String vmId) throws ApiException {
        logger.info("收到查询虚机网络列表请求：vmId=" + vmId);
        Page<VmNet> result = null;
        try {
            result = vmNetService.pageByVm(vmId, null);
        } catch (Exception e) {
            logger.error("查询虚机网络列表失败！" + e.getMessage(), e);
            throw new ApiException("查询虚机网络列表失败！" + e.getMessage(), e);
        }
        return result.map(netConverter);
    }

    @RequestMapping(value = "/{id}/nets", method = RequestMethod.POST)
    public VmNet addNet(@PathVariable("id") String vmId, VmNet vmNet) throws ApiException {
        logger.info("收到虚机加入网络请求: vmId=" + vmId + ",vmNet=" + vmNet);
        VmNet result = null;
        // 检查目标虚机
        if (StringUtils.isEmpty(vmId)) {
            logger.error("目标虚机ID为空！");
            throw new ApiException("虚机加入网络失败：目标虚机ID为空！");
        }
        VmHost vmHost = vmService.findById(vmId);
        if (vmHost == null) {
            logger.error("目标虚机不存在！");
            throw new ApiException("虚机加入网络失败：目标虚机不存在！");
        }
        // 添加网卡
        vmNet.setVmId(vmId);
        try {
            result = vmNetService.add(vmNet);
        } catch (Exception e) {
            logger.error("虚机加入网络失败！" + e.getMessage(), e);
            throw new ApiException("虚机加入网络失败！" + e.getMessage(), e);
        }
        return result;
    }

    @RequestMapping(value = "/{id}/nets/{net_id}", method = RequestMethod.DELETE)
    public void removeNet(@PathVariable("id") String vmId, @PathVariable("net_id") String netId) throws ApiException {
        logger.info("收到虚机移出网络请求: vmId=" + vmId + ",netId=" + netId);
        try {
            vmNetService.delete(netId);
        } catch (Exception e) {
            logger.error("虚机移出网络失败！" + e.getMessage(), e);
            throw new ApiException("虚机移出网络失败！" + e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{id}/disks", method = RequestMethod.GET)
    public Page<VmDisk> listDisk(@PathVariable("id") String vmId) throws ApiException {
        logger.info("收到查询虚机磁盘列表请求：vmId=" + vmId);
        Page<VmDisk> result = null;
        try {
            result = vmDiskService.pageByVm(vmId, null);
        } catch (Exception e) {
            logger.error("查询虚机磁盘列表失败！" + e.getMessage(), e);
            throw new ApiException("查询虚机磁盘列表失败！" + e.getMessage(), e);
        }
        return result.map(diskConverter);
    }

    @RequestMapping(value = "/{id}/disks", method = RequestMethod.POST)
    public VmDisk addDisk(@PathVariable("id") String vmId, VmDisk vmDisk) throws ApiException {
        logger.info("收到虚机添加磁盘请求: vmId=" + vmId + ",vmDisk=" + vmDisk);
        VmDisk result = null;
        // 检查目标虚机
        if (StringUtils.isEmpty(vmId)) {
            logger.error("目标虚机ID为空！");
            throw new ApiException("虚机添加磁盘失败：目标虚机ID为空！");
        }
        VmHost vmHost = vmService.findById(vmId);
        if (vmHost == null) {
            logger.error("目标虚机不存在！");
            throw new ApiException("虚机添加磁盘失败：目标虚机不存在！");
        }
        // 添加磁盘
        vmDisk.setVmId(vmId);
        try {
            result = vmDiskService.add(vmDisk);
        } catch (TransactionSystemException te) {
            logger.error("创建虚机失败！", te);
            throw new ApiException("创建虚机失败！" + te.getApplicationException().getMessage());
        } catch (Exception e) {
            logger.error("虚机添加磁盘失败！" + e.getMessage(), e);
            throw new ApiException("虚机添加磁盘失败！" + e.getMessage(), e);
        }
        return result;
    }

    @RequestMapping(value = "/{id}/nets/{disk_id}", method = RequestMethod.DELETE)
    public void removeDisk(@PathVariable("id") String vmId, @PathVariable("disk_id") String diskId)
            throws ApiException {
        logger.info("收到虚机删除磁盘请求: vmId=" + vmId + ",diskId=" + diskId);
        try {
            vmDiskService.deleteById(diskId);
        } catch (TransactionSystemException te) {
            logger.error("删除磁盘失败！", te);
            throw new ApiException("删除磁盘失败！" + te.getApplicationException().getMessage());
        } catch (Exception e) {
            logger.error("虚机删除磁盘失败！" + e.getMessage(), e);
            throw new ApiException("虚机删除磁盘失败！" + e.getMessage(), e);
        }
    }

    private VmHost buildVmHostVO(VmHost vmHost) {
        VmHost vo = new VmHost();
        BeanUtils.copyProperties(vmHost, vo);
        // 设置该虚机关联的用户实体
        if (!StringUtils.isEmpty(vmHost.getOwnerId())) {
            try {
                User user = userService.findUser(vmHost.getOwnerId());
                vo.setOwner(user);
            } catch (Exception e) {
                logger.error("查询关联用户异常！", e);
            }
        }
        // 设置该虚机关联的组织实体
        if (!StringUtils.isEmpty(vmHost.getOrgId())) {
            try {
                Organization org = orgService.showById(vmHost.getOrgId());
                vo.setOrganization(org);
            } catch (Exception e) {
                logger.error("查询关联组织异常！", e);
            }
        }
        // 设置该虚机关联的模板实体
        if (!StringUtils.isEmpty(vmHost.getTemplateId())) {
            try {
                VMTempletE entity = templateService.findByRelationId(vmHost.getTemplateId());
                if (entity != null) {
                    VmTemplate template = new VmTemplate();
                    BeanUtils.copyProperties(entity, template);
                    vo.setTemplate(template);
            }
            } catch (Exception e) {
                logger.error("查询关联模板异常！", e);
            }
        }
        // 设置该虚机关联的VDC实体
        if (!StringUtils.isEmpty(vmHost.getVdcId())) {
            try {
                ProviderVDC vdc = vdcService.findProviderVDC(vmHost.getVdcId());
                vo.setVdc(vdc);
            } catch (Exception e) {
                logger.error("获取VDC实体异常！", e);
            }
        }
        // 设置该虚机关联的存储池
        if (!StringUtils.isEmpty(vmHost.getsPoolId())) {
            try {
                StoragePool sPool = sPoolService.findStoragePool(vmHost.getsPoolId());
                vo.setsPool(sPool);
            } catch (Exception e) {
                logger.error("查询关联存储池异常！", e);
            }
        }
        return vo;
    }

    private VmConverter vmConverter = new VmConverter();

    private NetConverter netConverter = new NetConverter();

    private DiskConverter diskConverter = new DiskConverter();

    private class VmConverter implements Converter<VmHost, VmHost> {
        @Override
        public VmHost convert(VmHost source) {
            VmHost target = buildVmHostVO(source);
            return target;
        }
    }

    private class NetConverter implements Converter<VmNet, VmNet> {
        @Override
        public VmNet convert(VmNet source) {
            VmNet target = new VmNet();
            BeanUtils.copyProperties(source, target);
            // 设置该虚机关联的网络实体
            if (!StringUtils.isEmpty(source.getNetId())) {
                NetPool net = netPoolService.QueryNetpoolById(source.getNetId());
                target.setNet(net);
            }
            return target;
        }
    }

    private class DiskConverter implements Converter<VmDisk, VmDisk> {
        @Override
        public VmDisk convert(VmDisk source) {
            VmDisk target = new VmDisk();
            BeanUtils.copyProperties(source, target);
            // 设置该虚机关联的存储实体
            if (!StringUtils.isEmpty(source.getsPoolId())) {
                try {
                    StoragePool sPool = sPoolService.findStoragePool(source.getsPoolId());
                    target.setsPool(sPool);
                } catch (VDCException e) {
                    logger.error("查询存储池出错！", e);
                }
            }
            return target;
        }
    }

}
