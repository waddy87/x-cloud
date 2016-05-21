package org.waddys.xcloud.vijava.vm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.waddys.xcloud.vijava.base.BaseTask;
import org.waddys.xcloud.vijava.exception.VirtException;
import org.waddys.xcloud.vijava.impl.Session;
import org.waddys.xcloud.vijava.impl.VirtualMachineImpl;
import org.waddys.xcloud.vijava.util.ParamValidator;
import org.waddys.xcloud.vijava.vm.MigrateVM.MigrateVMAnswer;
import org.waddys.xcloud.vijava.vm.MigrateVM.MigrateVMCmd;

import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.TaskInfo;
import com.vmware.vim25.VirtualMachineRelocateSpec;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

public class MigrateVMTask extends BaseTask<MigrateVMAnswer> {

    private static Logger logger = LoggerFactory.getLogger(MigrateVMTask.class);

    private static final Boolean success = true;
    private static final Boolean failure = false;

    private MigrateVMCmd migrateCmd;
    private ServiceInstance si = null;

    public MigrateVMTask(MigrateVMCmd migrateVmCmd) throws VirtException {
        super();
        this.migrateCmd = migrateVmCmd;
    	
		try {
			this.setSi(Session.getInstanceByToken(migrateVmCmd.getToken()));
		} catch (Exception e) {
			logger.error("连接失败，原因" + e.getMessage());
			throw new VirtException("虚拟环境" + "连接异常 ：" + e.getMessage());
		}
    }

    @Override
    public MigrateVMAnswer execute() {
        logger.debug("this.migrateCmd is:" + this.migrateCmd);
        try {
            // 如果不设置资源池、主机，则不执行迁移操作
            String poolMoId = this.migrateCmd.getPoolId();
            String hostMoId = this.migrateCmd.getHostId();
            if (ParamValidator.validatorParamsIsEmpty(poolMoId) && ParamValidator.validatorParamsIsEmpty(hostMoId)) {
                logger.debug("未设置迁移目标，不执行迁移操作");
                return new MigrateVMAnswer().setSuccess(success);
            }
            // 1.获取连接
//            ServiceInstance si = Session.getInstance();
            // 2.获取要迁移的虚拟机对象
            String vmMoId = this.migrateCmd.getVmId();
            VirtualMachine vm = VirtualMachineImpl.getVirtualMachineById(si, vmMoId);
            if (vm == null) {
                throw new VirtException("指定的虚拟机:" + vmMoId + "不存在");
            }
            // TODO:虚拟机迁移是否需要指定状态才能迁移？
            // 3.设置计算资源
            ResourcePool pool = null;
            if (ParamValidator.validatorParamsNotEmpty(poolMoId)) {
                pool = VirtualMachineImpl.getResourcePoolById(si, poolMoId);
            }
            HostSystem host = null;
            if (ParamValidator.validatorParamsNotEmpty(hostMoId)) {
                host = VirtualMachineImpl.getHostSystemById(si, hostMoId);
            }

            VirtualMachineRelocateSpec spec = new VirtualMachineRelocateSpec();
            // 比较新指定的计算资源与虚拟机原计算资源是否不同
            ResourcePool oldPool = vm.getResourcePool();
            ManagedObjectReference oldHostMOR = vm.getRuntime().getHost();
            if ((oldHostMOR != null && host != null && (oldHostMOR.get_value().equals(host.getMOR().get_value())))
                    && (oldPool != null && pool != null
                            && (oldPool.getMOR().get_value().equals(pool.getMOR().get_value())))) {
                logger.debug("新指定的计算资源与原计算资源相同，不需进行迁移操作");
                return new MigrateVMAnswer().setSuccess(success);
            }

            if (pool != null) {
                spec.setPool(pool.getMOR());
            }
            if (host != null) {
                spec.setHost(host.getMOR());
            }
            // 5.TODO:是否需要指定只有满足某种状态的虚拟机才能执行迁移
            Task task = vm.migrateVM_Task(pool, host, null, null);

            String result = task.waitForTask();
            if (result.equals(Task.SUCCESS)) {
                logger.debug("迁移虚拟机成功");
                return new MigrateVMAnswer().setSuccess(success);
            } else {
                TaskInfo taskInfo = task.getTaskInfo();
                String errMsg = taskInfo.getError().localizedMessage;
                logger.error("迁移虚拟机失败，原因：" + errMsg);
                return new MigrateVMAnswer().setSuccess(failure).setErrMsg(errMsg);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new MigrateVMAnswer().setSuccess(failure).setErrMsg(e.getMessage());
        }
    }
    

	public ServiceInstance getSi() {
		return si;
	}

	public void setSi(ServiceInstance si) {
		this.si = si;
	}



}
