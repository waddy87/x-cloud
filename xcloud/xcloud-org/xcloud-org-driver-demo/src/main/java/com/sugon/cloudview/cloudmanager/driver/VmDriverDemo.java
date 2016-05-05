/**
 * 
 */
package com.sugon.cloudview.cloudmanager.driver;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sugon.cloudview.cloudmanager.vm.bo.VmConfig;
import com.sugon.cloudview.cloudmanager.vm.bo.VmDisk;
import com.sugon.cloudview.cloudmanager.vm.bo.VmHost;
import com.sugon.cloudview.cloudmanager.vm.bo.VmNet;
import com.sugon.cloudview.cloudmanager.vm.bo.VmTask;

/**
 * @author zhangdapeng
 *
 */
@Service("vmDriverDemo")
public class VmDriverDemo implements IVmDriver {
    private static final Logger logger = LoggerFactory.getLogger(VmDriverDemo.class);

    @Override
    public VmHost create(VmHost vmHost) throws Exception {
        /**
         * 调用虚拟化接口 （入参：虚机名称、模板id、计算池id、vcpu数量、内存容量、存储池id、存储容量， 返回：虚机创建任务id）
         */
        String taskId = "123456";
        final String vmName = vmHost.getName();
        final int vcpuNumber = vmHost.getvCpuNumer();
        final Long vmemCapacity = vmHost.getvMemCapacity();
        final String storId = vmHost.getsPoolId();
        final String templateId = vmHost.getTemplateId();
        logger.debug("开始调用虚拟化接口：{name=" + vmName + ",vcpu=" + vcpuNumber + ",vmem=" + vmemCapacity + ",template="
                + templateId + ",stor="
                + storId);
        try {
            // FIXME
        } catch (Exception e) {
            logger.error("调用虚拟化接口出错！", e);
            throw new Exception("调用虚拟化接口出错！", e);
        }
        logger.debug("成功调用虚拟化接口，获取taskId=" + taskId);
        vmHost.setTaskId(taskId);
        return vmHost;
    }

    @Override
    public VmTask delete(String vmId) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public VmTask config(String vmId, VmConfig vmConfig) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public VmTask start(String vmId) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public VmTask stop(String vmId) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public VmNet addNet(String vmId, VmNet net) throws Exception {
        VmNet vmNet = new VmNet();
        vmNet.setTaskId("task-100");
        vmNet.setInternalId("net-100");
        return vmNet;
    }

    @Override
    public VmTask removeNet(String vmId, VmNet net) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public VmDisk addDisk(String vmId, VmDisk vmDisk) throws Exception {
        vmDisk.setTaskId("task-100");
        vmDisk.setInternalId("disk-100");
        return vmDisk;
    }

    @Override
    public VmTask removeDisk(String vmId, VmDisk vmDisk) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<VmDisk> listDisk(String vmId) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public VmHost refreshVmByTask(String taskId, VmHost vmHost) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public VmHost refreshVmById(String vmId, VmHost vmHost) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public VmTask setPassword(String vmId, String newPassword, String oldPassword) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
