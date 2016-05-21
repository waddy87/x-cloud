package org.waddys.xcloud.vijava.api;

import java.rmi.RemoteException;
import java.util.List;

import com.vmware.vim25.CustomizationSpec;
import com.vmware.vim25.OptionValue;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.VirtualMachineCloneSpec;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.VirtualMachineRelocateSpec;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.VirtualMachineSnapshot;

/**
 * 虚拟机操作接口
 * 
 * @category see
 * @author
 */
public interface VirtualMachineI {

    /**
     * 查询集合，模糊查询 1. 如果参数为空，返回所有的虚拟机 ? vijava提供哪些查询接口 ？根据哪些条件查询 SearchIndex
     * 提供简单findxxxx public ObjectContent[]
     * retrieveProperties(ManagedObjectReference _this, PropertyFilterSpec[]
     * specSet) throws java.rmi.RemoteException, InvalidProperty, RuntimeFault {
     * 
     * @return 满足条件的虚拟机
     */
    public List<VirtualMachine> queryVirtualMachine() throws RuntimeFault, RemoteException;

    /**
     * 精确查询 1. 如果参数为空，返回所有的虚拟机 ? vijava提供哪些查询接口 ？根据哪些条件查询 SearchIndex
     * 提供简单findxxxx
     * 
     * @param
     * @return 满足条件的虚拟机
     */
    public VirtualMachine queryVirtualMachineByUuid(String uuid) throws RuntimeFault, RemoteException;

    // 如何保持连接

    // 数据同步是否单独接口

    // 确定接口 -> 梳理业务
    /**
     * 创建一个虚拟机并将其与指定资源池关联
     * 
     * @param vmSpec
     *            虚拟机硬件配置
     * @param rp
     *            虚拟机将要关联的资源池
     * @param host
     *            虚拟机将要运行的主机，主机必须是指定资源池所在的计算资源的成员，该参数在独立主机或者DRS集群可以不设置
     * @return
     * @throws RuntimeFault
     *             可能的异常，已存在、名称重复、文件已存在、文件错误、资源不足、无效的存储
     *             、无效的名称、无效的状态、不支持、超出主机最大支持虚拟机个数、运行时异常 虚拟机配置错误、虚拟机wwn冲突
     * @throws RemoteException
     * */
    public Task createVM(VirtualMachineConfigSpec vmSpec, ResourcePool rp, HostSystem host) throws RuntimeFault,
            RemoteException;

    public String createVMSync(VirtualMachineConfigSpec vmSpec, ResourcePool rp, HostSystem host) throws RuntimeFault,
            RemoteException;

    /**
     * 克隆虚拟机，保持原虚拟机（源模板）的情况下生成新虚拟机（新模板）。 如果源是虚拟机，目标是虚拟机，则执行克隆操作，
     * 如果源是虚拟机，目标是模板，则执行从虚拟机创建模板操作 如果源是模板，目标是虚拟机，则执行模板部署操作
     * 如果源是模板，目标是模板，则执行克隆模板操作
     * 
     * @param vm
     *            源虚拟机
     * @param folder
     *            目标虚拟机的位置
     * @param name
     *            目标虚拟机的名称
     * @param spce
     *            如何克隆虚拟机的规范。在该规范中指定的folder优先于folder参数。
     * @return
     * @throws RuntimeFault
     *             可能的异常：客户化错误、文件错误、资源不足错误、无效参数、无效数据存储、无效状态、
     *             迁移错误、不支持、运行时错误、任务进行中、虚拟机配置错误
     * @throws RemoteException
     */
    public Task cloneVM(VirtualMachine vm, Folder folder, String name, VirtualMachineCloneSpec spce)
            throws RuntimeFault, RemoteException;

    /**
     * 重新配置虚拟机。给定配置中的所有变更将作为原子操作应用到虚拟机。该操作对虚拟机配置的修改实时生效，有一些配置不能修改， 如：不能创建或者添加磁盘。
     * ？还需补充哪些配置不支持修改
     * 
     * @param vm
     *            要重新配置的虚拟机
     * @param spec
     *            新的配置值
     * @return
     * @throws RuntimeFault
     * @throws RemoteException
     */
    public Task reconfigVM(VirtualMachine vm, VirtualMachineConfigSpec spec) throws RuntimeFault, RemoteException;

    /**
     * 重命名虚拟机
     * 
     * @throws RuntimeFault
     * @throws RemoteException
     */
    public void renameVM() throws RuntimeFault, RemoteException;

    /**
     * 将一个已经存在的虚拟机添加到folder
     * 
     * @param datastorePath
     *            虚拟机存储路径
     * @param vmName
     *            分配给虚拟机的名称，如果不设置，则将虚拟机的显示名称作为该值。名称必须是一个小于80个字符的非空字符串，
     *            使用URL语法时避免使用/、\和%
     * @param asTemplate
     *            表明虚拟机是否作为一个模板
     * @param rp
     *            虚拟机将要关联的资源池，如果作为一个模板导入，该参数不需设置
     * @param host
     *            虚拟机将要运行的目标主机。主机必须是指定资源池所在的计算资源的成员，对于独立主机或DRS群集，该参数可以被忽略，
     *            系统将选择缺省值
     * @return
     * @throws RuntimeFault
     * @throws RemoteException
     */
    public Task registerVM(String datastorePath, String vmName, Boolean asTemplate, ResourcePool rp, HostSystem host)
            throws RuntimeFault, RemoteException;

    /**
     * 从目录清单中移除指定虚拟机而不擅长磁盘上的虚拟机的文件。存储在管理服务器的所有高层次的信息将被删除，包括统计、资源池关联、权限和告警。
     * 
     * @param vm
     * @throws RuntimeFault
     * @throws RemoteException
     */
    public void unregisterVM(VirtualMachine vm) throws RuntimeFault, RemoteException;

    /**
     * 迁移虚拟机的运行到指定资源池或者云主机。不支持跨数据中心迁移。
     * 
     * @param vm
     *            要迁移的虚拟机
     * @param rp
     *            虚拟机迁移的目标资源池。如果该参数未设置，则虚拟机当前资源池作为目标资源池
     * @param host
     *            虚拟机迁移的目标主机。如果与目标池关联的计算资源标识独立主机或者启用DRS的群集，则该参数可不设置。前者情况下，
     *            独立主机被用作目标主机，后一种情况下，DRS系统从群集中选择适当的目标主机。
     * @param priority
     *            任务优先级
     * @param state
     *            如果指定该值，则虚拟机只有在满足指定状态时才能迁移
     * @return
     * @throws RuntimeFault
     * @throws RemoteException
     */
    public Task migrateVM(VirtualMachine vm, ResourcePool rp, HostSystem host, String priority, String state)
            throws RuntimeFault, RemoteException;

    /**
     * 重定位虚拟机的虚拟磁盘到指定位置，可选地，移动虚拟机到不同主机。也支持在当前主机处于非活动时，将模板重定位到新的主机。如果指定了host，
     * 则尝试将模板重定位到指定的host，否则，将自动选择合适的主机。从6.0开始，支持重定位虚拟机到新的vCenter服务。
     * 
     * @param vm
     *            要重定位的虚拟机
     * @param spec
     *            虚拟机重定位的说明
     * @param priority
     *            任务优先级
     * @return
     * @throws RuntimeFault
     * @throws RemoteException
     */
    public Task relocateVM(VirtualMachine vm, VirtualMachineRelocateSpec spec, String priority) throws RuntimeFault,
            RemoteException;

    /**
     * 打开虚拟机。如果指定虚拟机是挂起的，该操作将从挂起点恢复执行。
     * 当要打开集群中的虚拟机时，系统有可能隐含或者根据主机参数做虚拟机到其他主机的重定位。
     * 因此，可能抛出和重定位有关的错误。如果是DRS群集，当设置了自动放置虚拟机，DRS将被调用。
     * 
     * 如果要打开的虚拟机是容错的主虚拟机，则辅助虚拟机将在系统选择的主机上启动。如果虚拟机处于启用VMware
     * DRS群集中,那么DRS将被调用以获得辅助虚拟机的位置，但是but no vmotion nor host power operations
     * will be considered for these power ons
     * 
     * @param vm
     *            要打开的虚拟机
     * @param host
     *            虚拟机将要打开的主机。如果不指定该参数，将使用当前关联的主机。这个字段必须指定的主机是该虚拟机当前关联的相同计算资源的一部分
     *            。 如果主机不兼容，将使用当前关联主机
     * @return
     * @throws RuntimeFault
     * @throws RemoteException
     */
    public Task powerOnVM(VirtualMachine vm, HostSystem host) throws RuntimeFault, RemoteException;

    /**
     * 关闭虚拟机。如果指定虚拟机是容错的主虚拟机，该操作将导致备份虚拟机也关闭。
     * 
     * @param vm
     *            执行操作的虚拟机
     * @return
     * @throws RuntimeFault
     * @throws RemoteException
     */
    public Task powerOffVM(VirtualMachine vm) throws RuntimeFault, RemoteException;

    /**
     * 在数据中心上启动多个虚拟机。如果虚拟机是挂起的，该方法从挂起点恢复执行。虚拟机可以属于不同的群集。
     * 如果虚拟机列表中存在DRS管理的手动、或者DRS必须迁移手动管理的虚拟机或者为了启动这些虚拟机启动任何手动管理的主机
     * ，则将生成DRS建议，用户需要手动应用建议以真正启动这些虚拟机。否则，所有虚拟机将自动启动。
     * 
     * ?如何选择datacenter
     * 
     * @param vms
     *            要启动的虚拟机数组
     * @param options
     *            OptionValue数组
     * @return
     * @throws RuntimeFault
     * @throws RemoteException
     */
    public Task powerOnMultiVM(VirtualMachine[] vms, OptionValue[] options) throws RuntimeFault, RemoteException;

    /**
     * 挂起虚拟机
     * 
     * @param vm
     * @return
     * @throws RuntimeFault
     * @throws RemoteException
     */
    public Task suspendVM(VirtualMachine vm) throws RuntimeFault, RemoteException;

    /**
     * 立即关闭虚拟机
     * 
     * @param vm
     * @throws RuntimeFault
     * @throws RemoteException
     */
    public void teminateVM(VirtualMachine vm) throws RuntimeFault, RemoteException;

    /**
     * 重启虚拟机。如果当前是打开状态，那么该方法首先执行关闭，一旦电源状态为关闭，则执行打开。虽然该方法是在关闭之后跟着执行打开，
     * 但是这两个操作是原子操作，意味着其它电源操作在重置完成之前无法执行
     * 
     * @param vm
     *            要操作的虚拟机
     * @return
     * @throws RuntimeFault
     * @throws RemoteException
     */
    public Task resetVM(VirtualMachine vm) throws RuntimeFault, RemoteException;

    /**
     * 升级虚拟机的虚拟硬件到虚拟机当前所在主机支持的最新版本
     * 
     * @param vm
     *            要操作的虚拟机
     * @param version
     *            如果指定该值，则升级到指定的版本；如果未指定，则升级到所在主机支持的最新虚拟硬件。
     * @return
     * @throws RuntimeFault
     * @throws RemoteException
     */
    public Task upgradeVM(VirtualMachine vm, String version) throws RuntimeFault, RemoteException;

    /**
     * 生成虚拟机的快照，不支持容错的主备虚拟机
     * 
     * @param vm
     *            用于创建快照的虚拟机
     * @param name
     *            快照名称，对该虚拟机这个名称不需要唯一
     * @param description
     *            快照描述，如果忽略，系统将提供一个默认描述
     * @param memory
     *            如果值为TRUE，快照将包括虚拟机的内部状态转储（基本是内存转储），内存快照消耗时间和资源，创建需要的时间更长。
     *            如果值为FALSE，快照的电源状态将被设置为关闭。
     * @param quiesce
     *            If TRUE and the virtual machine is powered on when the
     *            snapshot is taken, VMware Tools is used to quiesce the file
     *            system in the virtual machine. This assures that a disk
     *            snapshot represents a consistent state of the guest file
     *            systems. If the virtual machine is powered off or VMware Tools
     *            are not available, the quiesce flag is ignored.
     * @throws RuntimeFault
     *             可能异常：文件错误、无效名称、无效电源状态、无效状态、不支持、运行时错误、快照错误、任务执行中、虚拟机配置错误
     * @throws RemoteException
     */
    public Task createSnapshot(VirtualMachine vm, String name, String description, Boolean memory, Boolean quiesce)
            throws RuntimeFault, RemoteException;

    /**
     * 删除快照并删除所有关联存储
     * 
     * @param snapshot
     *            要操作的快照
     * @param removeChildren
     *            指定是否删除整个快照子树的标志
     * @param consolidate
     *            如果值为TRUE，则该快照关联的虚拟磁盘将与可能的其它磁盘合并。缺省值为FALSE
     * @return
     * @throws RuntimeFault
     * @throws RemoteException
     */
    public Task removeSnapshot(VirtualMachineSnapshot snapshot, Boolean removeChildren, Boolean consolidate)
            throws RuntimeFault, RemoteException;

    /**
     * 恢复虚拟机为最新快照。如果不能存在快照，则不执行任何操作，虚拟机状态保持不变
     * 
     * @param vm
     *            要操作的虚拟机
     * @param host
     *            为虚拟机选择的主机，以防该操作导致虚拟机启动。如果该参数未设置，并且负载功能配置为自动负载均衡，会自动选择一个主机。否则，
     *            虚拟机保持现有主机关系
     * @param suppressPowerOn
     *            如果值为TRUE，则创建当前快照时无论虚拟机的电源状态是什么，虚拟机都不会启动。默认值为FALSE
     * @return
     * @throws RuntimeFault
     * @throws RemoteException
     */
    public Task revertToCurrentSnapshot(VirtualMachine vm, HostSystem host, Boolean suppressPowerOn)
            throws RuntimeFault, RemoteException;

    /**
     * Change the execution state of the virtual machine to the state of this
     * snapshot.
     * 
     * @param snapshot
     *            要操作的快照
     * @param host
     *            为虚拟机选择的主机，以防该操作引起虚拟机开机。如果快照在虚拟机启动后被使用，并且该操作中虚拟机关闭后背调用，
     *            操作将导致虚拟机开机以达到快照状态。如果该参数未设置，并且负载功能配置为自动负载均衡，会自动选择一个主机。否则，
     *            虚拟机保持现有主机关系
     * @param suppressPowerOn
     *            如果值为TRUE，则转换时无论虚拟机的电源状态是什么，虚拟机都不会启动。默认值为FALSE
     * @return
     * @throws RuntimeFault
     * @throws RemoteException
     */
    public Task revertToSnapshot(VirtualMachine vm, VirtualMachineSnapshot snapshot, HostSystem host,
            Boolean suppressPowerOn) throws RuntimeFault, RemoteException;

    /**
     * 删除虚拟机关联的所有快照。如果虚拟机没有任何快照，则该操作只简单地返回成功。
     * 
     * @param vm
     *            要操作的虚拟机
     * @param consolidate
     *            如果值为TRUE，则删除快照的虚拟磁盘将与其它可能的磁盘合并。默认设置为TRUE
     * @return
     * @throws RuntimeFault
     * @throws RemoteException
     */
    public Task removeAllSnapshots(VirtualMachine vm, Boolean consolidate) throws RuntimeFault, RemoteException;

    /**
     * 使用新的名称或者新的描述或者二者都有重命名快照。至少需要指定一项。
     * 
     * @param snapshot
     *            要操作的快照
     * @param name
     *            快照的新名称
     * @param description
     *            快照的新描述
     * @throws RuntimeFault
     * @throws RemoteException
     */
    public void renameSnapshot(VirtualMachine vm, VirtualMachineSnapshot snapshot, String name, String description)
            throws RuntimeFault, RemoteException;

    /**
     * 将虚拟机转换为模板 注：虚拟机在关机状态才能转换成模板，模板不能启动、与资源池无关
     * 
     * @throws RuntimeFault
     *             可能的异常：文件错误、无效电源状态、无效状态、不支持、运行时错误、虚拟机配置错误
     * @throws RemoteException
     */
    public void revertVmToTemplate(VirtualMachine vm) throws RuntimeFault, RemoteException;

    /**
     * 将模板转换为虚拟机，重新建立虚拟机和资源池及主机的关联
     * 
     * @param vm
     *            要转换的虚拟机
     * @param rp
     *            虚拟机将关联的资源池
     * @param host
     *            虚拟机将要运行的主机，主机必须是指定资源池所在的计算资源的成员，对独立主机或者DRS群集，该参数可忽略，系统将选择默认值
     * @throws RuntimeFault
     *             可能的异常：文件错误、无效数据存储、无效状态、不支持、运行时错误、虚拟机配置错误
     * @throws RemoteException
     */
    public void revertTemplateToVm(VirtualMachine vm, ResourcePool rp, HostSystem host) throws RuntimeFault,
            RemoteException;

    /**
     * 重启客户端操作系统。给客户操作系统发出命令，请求其执行重启操作。立即返回，并不等待客户操作系统完成该操作。
     * 
     * @param vm
     *            要操作的虚拟机
     * @throws RuntimeFault
     * @throws RemoteException
     */
    public void rebootGuest(VirtualMachine vm) throws RuntimeFault, RemoteException;

    /**
     * 关闭客户端操作系统。给客户端操作系统发出命令，请求其完成所有服务的正常关机。立即返回，并不等待客户操作系统完成该操作。
     * 
     * @param vm
     * @throws RuntimeFault
     * @throws RemoteException
     */
    public void shutdownGuest(VirtualMachine vm) throws RuntimeFault, RemoteException;

    /**
     * 给客户端操作系统发出命令，请求其准备挂起操作。立即返回，并不等待客户操作系统完成该操作。
     * 
     * @param vm
     * @throws RuntimeFault
     * @throws RemoteException
     */
    public void standbyGuest(VirtualMachine vm) throws RuntimeFault, RemoteException;

    /**
     * 清除缓存的guest信息。Guest信息只能在虚拟机被关闭电源时清除。
     * 
     * @param vm
     *            要操作的虚拟机
     * @throws RuntimeFault
     * @throws RemoteException
     */
    public void resetGuestInformation(VirtualMachine vm) throws RuntimeFault, RemoteException;

    /**
     * 自定义虚拟机客户机操作系统
     * 
     * @param vm
     *            虚拟机
     * @param spec
     *            自定义规范对象
     * @return
     * @throws RuntimeFault
     *             可能异常：客户化异常、运行时异常
     * @throws RemoteException
     */
    public Task customizeVM(VirtualMachine vm, CustomizationSpec spec) throws RuntimeFault, RemoteException;

    // 主、备虚拟机

    // 导出虚拟机

    // 自定义虚拟机

    // recording相关

    // fault tolerance

    // ?有几个接口是由Folder、Datacenter执行的，怎么获取Folder、Datacenter？
    // ?异常怎么处理，自定义异常码
    // ?接口返回值是什么？很多Task，异步操作，是否需要同步接口？
    // ?接口参数有很多种类型，且有的类型较复杂，下面还有很多参数是其它类型，是否还需要包装？
}
