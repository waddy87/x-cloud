package com.sugon.cloudview.cloudmanager.driver;

import java.util.List;

import com.sugon.cloudview.cloudmanager.vm.bo.VmConfig;
import com.sugon.cloudview.cloudmanager.vm.bo.VmDisk;
import com.sugon.cloudview.cloudmanager.vm.bo.VmHost;
import com.sugon.cloudview.cloudmanager.vm.bo.VmNet;
import com.sugon.cloudview.cloudmanager.vm.bo.VmTask;

/**
 * 虚机服务接口
 * 
 * @author zhangdapeng
 *
 */
public interface IVmDriver {

    /**
     * 创建虚机
     * 
     * @param VmHost
     * @return 任务标识
     * @throws Exception
     */
    public VmHost create(VmHost VmHost) throws Exception;

    /**
     * 删除虚机
     * 
     * @param vmId
     *            虚机唯一标识
     * @return 任务标识
     */
    public VmTask delete(String vmId) throws Exception;

    /**
     * 配置虚机
     * 
     * @param VmHost
     * @return 任务标识
     * @throws Exception
     */
    public VmTask config(String vmId, VmConfig vmConfig) throws Exception;

    /**
     * 设置OS密码
     * 
     * @param vmId
     *            虚机内部唯一标识
     * @return
     * @throws Exception
     */
    public VmTask setPassword(String vmId, String newPassword, String oldPassword) throws Exception;

    /**
     * 启动虚机
     * 
     * @param vmId
     *            目标虚机
     * @throws Exception
     */
    public VmTask start(String vmId) throws Exception;

    /**
     * 停止虚机
     * 
     * @param vmId
     *            目标虚机
     * @return 任务标识
     * @throws Exception
     */
    public VmTask stop(String vmId) throws Exception;

    /**
     * 根据任务标识刷新虚机状态
     * 
     * @param taskId
     *            虚机任务标识
     * @param vmHost
     *            目标虚机（需设置模板ID）
     * @return 更新后的虚机
     * @throws Exception
     */
    public VmHost refreshVmByTask(String taskId, VmHost vmHost) throws Exception;

    /**
     * 根据任务标识刷新虚机状态
     * 
     * @param vmId
     *            虚机内部标识
     * @param vmHost
     *            目标虚机（需设置模板ID）
     * @return 更新后的虚机
     * @throws Exception
     */
    public VmHost refreshVmById(String vmId, VmHost vmHost) throws Exception;

    /**
     * 添加网络
     * 
     * @param vmId
     *            虚机唯一标识
     * @param net
     *            目标网络
     * @return 返回值中包含taskId以及生成的磁盘内部ID
     * @throws Exception
     */
    public VmNet addNet(String vmId, VmNet net) throws Exception;

    /**
     * 移除网络
     * 
     * @param vmId
     *            虚机内部唯一标识
     * @param net
     *            目标网络
     * @return 任务标识
     * @throws Exception
     */
    public VmTask removeNet(String vmId, VmNet net) throws Exception;

    /**
     * 添加磁盘
     * 
     * @param vmId
     *            虚机内部唯一标识
     * @param vmDisk
     *            目标磁盘
     * @return 返回值中包含taskId以及生成的磁盘内部ID
     * @throws Exception
     */
    public VmDisk addDisk(String vmId, VmDisk vmDisk) throws Exception;

    /**
     * 删除磁盘
     * 
     * @param vmId
     *            虚机内部唯一标识
     * @param vmDisk
     *            目标磁盘
     * @return 任务标识
     * @throws Exception
     */
    public VmTask removeDisk(String vmId, VmDisk vmDisk) throws Exception;

    /**
     * 获取指定虚机的磁盘列表
     * 
     * @param vmId
     *            虚机内部唯一标识
     * @return 该虚机关联的磁盘列表
     * @throws Exception
     */
    public List<VmDisk> listDisk(String vmId) throws Exception;

    /**
     * 获取指定虚机的VNC地址
     * @param vmId
     *            虚机内部唯一标识
     * @return
     * @throws Exception
     */
	public String getVncUrl(String vmId) throws Exception;

}
