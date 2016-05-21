/**
 * 
 */
package com.sugon.cloudview.cloudmanager.vm.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sugon.cloudview.cloudmanager.driver.IVmDriver;
import com.sugon.cloudview.cloudmanager.vm.bo.VmHost;
import com.sugon.cloudview.cloudmanager.vm.bo.VmNet;
import com.sugon.cloudview.cloudmanager.vm.constant.VmStatus;
import com.sugon.cloudview.cloudmanager.vm.dao.service.VmHostDaoService;
import com.sugon.cloudview.cloudmanager.vm.dao.service.VmNetDaoService;
import com.sugon.cloudview.cloudmanager.vm.service.VmNetService;

/**
 * 虚机网络服务组件
 * 
 * @author zhangdapeng
 *
 */
@Service("vmNetService")
public class VmNetServiceImpl implements VmNetService {
    private static final Logger logger = LoggerFactory.getLogger(VmNetServiceImpl.class);

    @Autowired
    private VmNetDaoService vmNetDaoService;

    @Autowired
    private VmHostDaoService vmHostDaoService;

    @Autowired
    private IVmDriver vmDriver;

    @Override
    public Page<VmNet> pageByVm(String vmId, PageRequest pageRequest) {
        logger.debug("根据虚机分页查询网络列表：vmId=" + vmId);
        VmNet search = new VmNet();
        search.setVmId(vmId);
        return vmNetDaoService.findByBO(search, pageRequest);
    }

    @Override
    public VmNet findByVmAndNet(String vmId, String netId) {
        logger.debug("根据虚拟机分页查询网络列表：vmId=" + vmId);
        return vmNetDaoService.findByVmAndNet(vmId, netId);
    }

    @Override
    public List<VmNet> listByVm(String vmId) {
        logger.debug("根据虚机查询网络列表：vmId=" + vmId);
        VmNet search = new VmNet();
        search.setVmId(vmId);
        return vmNetDaoService.findByBO(search, null).getContent();
    }

    @Override
    public VmNet add(VmNet vmNet) throws Exception {
        logger.info("开始关联网络：" + vmNet);
        VmNet result = null;
        // 检查目标
        VmHost vmHost = vmHostDaoService.findById(vmNet.getVmId());
        if (vmHost == null || VmStatus.DELETED.equals(vmHost.getStatus())) {
            logger.error("所属虚机已不存在!");
            throw new Exception("所属虚机已不存在!");
        }
        String vmId = vmHost.getInternalId();
        // 物理添加：调用虚拟化接口（虚拟化接口设置网络时需要）
        vmNet.setOsUsername(vmHost.getOsUsername());
        vmNet.setOsPassword(vmHost.getOsPassword());
        vmNet = vmDriver.addNet(vmId, vmNet);
        logger.debug("调用虚拟化接口成功，net内部标识=" + vmNet.getInternalId());
        // 逻辑添加
        try {
            vmNet.setCreateTime(new Date());
            result = vmNetDaoService.add(vmNet);
        } catch (Exception e) {
            logger.error("添加网络记录失败：net=" + vmNet.getIp(), e);
            throw new Exception("添加数据库记录失败！", e);
        }
        logger.info("完成关联网络：" + vmNet);
        return result;
    }

    @Override
    public void delete(String netId) throws Exception {
        logger.info("开始移出网络：netId=" + netId);
        // 检查目标网络
        VmNet net = vmNetDaoService.findById(netId);
        if (net != null) {
            VmHost vmHost = vmHostDaoService.findById(net.getVmId());
            if (vmHost == null || VmStatus.DELETED.equals(vmHost.getStatus())) {
                logger.error("所属虚机已不存在!");
                throw new Exception("所属虚机已不存在!");
            }
            // 物理删除：调用虚拟化接口
            vmDriver.removeNet(vmHost.getInternalId(), net);
            // 逻辑删除
            try {
                vmNetDaoService.delete(net);
            } catch (Exception e) {
                logger.error("删除网络记录失败：net=" + net.getIp(), e);
                throw new Exception("删除网络记录失败!", e);
            }
        } else {
            logger.warn("目标网络已不存在");
        }
        logger.info("完成移出网络：" + netId);
    }

}
