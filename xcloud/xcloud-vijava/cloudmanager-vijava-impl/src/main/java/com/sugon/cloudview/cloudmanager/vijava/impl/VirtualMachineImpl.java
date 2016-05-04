package com.sugon.cloudview.cloudmanager.vijava.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sugon.cloudview.cloudmanager.vijava.api.ClusterI;
import com.sugon.cloudview.cloudmanager.vijava.api.ResourcePoolI;
import com.sugon.cloudview.cloudmanager.vijava.api.VirtualMachineI;
import com.sugon.cloudview.cloudmanager.vijava.exception.VirtException;
import com.sugon.cloudview.cloudmanager.vijava.util.DatastoreSpaceComparator;
import com.sugon.cloudview.cloudmanager.vijava.util.MoQueryUtils;
import com.sugon.cloudview.cloudmanager.vijava.util.ResPoolMemFreeComparator;
import com.sugon.vim25.CustomizationSpec;
import com.sugon.vim25.InvalidProperty;
import com.sugon.vim25.ManagedObjectReference;
import com.sugon.vim25.OptionValue;
import com.sugon.vim25.RuntimeFault;
import com.sugon.vim25.VirtualMachineCloneSpec;
import com.sugon.vim25.VirtualMachineConfigSpec;
import com.sugon.vim25.VirtualMachineMovePriority;
import com.sugon.vim25.VirtualMachinePowerState;
import com.sugon.vim25.VirtualMachineRelocateSpec;
import com.sugon.vim25.mo.ClusterComputeResource;
import com.sugon.vim25.mo.ComputeResource;
import com.sugon.vim25.mo.Datacenter;
import com.sugon.vim25.mo.Datastore;
import com.sugon.vim25.mo.Folder;
import com.sugon.vim25.mo.HostSystem;
import com.sugon.vim25.mo.InventoryNavigator;
import com.sugon.vim25.mo.ManagedEntity;
import com.sugon.vim25.mo.ResourcePool;
import com.sugon.vim25.mo.ServiceInstance;
import com.sugon.vim25.mo.Task;
import com.sugon.vim25.mo.VirtualMachine;
import com.sugon.vim25.mo.VirtualMachineSnapshot;
import com.sugon.vim25.mo.util.MorUtil;

public class VirtualMachineImpl implements VirtualMachineI {

    private static Logger logger = LoggerFactory.getLogger(VirtualMachineImpl.class);

    @Override
    public List<VirtualMachine> queryVirtualMachine() throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public VirtualMachine queryVirtualMachineByUuid(String uuid) throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Task createVM(VirtualMachineConfigSpec vmSpec, ResourcePool rp, HostSystem host)
            throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String createVMSync(VirtualMachineConfigSpec vmSpec, ResourcePool rp, HostSystem host)
            throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Task cloneVM(VirtualMachine vm, Folder folder, String name, VirtualMachineCloneSpec spce)
            throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Task reconfigVM(VirtualMachine vm, VirtualMachineConfigSpec spec) throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void renameVM() throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public Task registerVM(String datastorePath, String vmName, Boolean asTemplate, ResourcePool rp, HostSystem host)
            throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void unregisterVM(VirtualMachine vm) throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub

    }

    public Task migrateVM(VirtualMachine vm, ResourcePool rp, HostSystem host, VirtualMachineMovePriority priority,
            VirtualMachinePowerState state) throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    public Task relocateVM(VirtualMachine vm, VirtualMachineRelocateSpec spec, VirtualMachineMovePriority priority)
            throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Task powerOnVM(VirtualMachine vm, HostSystem host) throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Task powerOffVM(VirtualMachine vm) throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Task powerOnMultiVM(VirtualMachine[] vms, OptionValue[] options) throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Task suspendVM(VirtualMachine vm) throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void teminateVM(VirtualMachine vm) throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public Task resetVM(VirtualMachine vm) throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Task upgradeVM(VirtualMachine vm, String version) throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Task createSnapshot(VirtualMachine vm, String name, String description, Boolean memory, Boolean quiesce)
            throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Task removeSnapshot(VirtualMachineSnapshot snapshot, Boolean removeChildren, Boolean consolidate)
            throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Task revertToCurrentSnapshot(VirtualMachine vm, HostSystem host, Boolean suppressPowerOn)
            throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Task revertToSnapshot(VirtualMachine vm, VirtualMachineSnapshot snapshot, HostSystem host,
            Boolean suppressPowerOn) throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Task removeAllSnapshots(VirtualMachine vm, Boolean consolidate) throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void renameSnapshot(VirtualMachine vm, VirtualMachineSnapshot snapshot, String name, String description)
            throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void revertVmToTemplate(VirtualMachine vm) throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void revertTemplateToVm(VirtualMachine vm, ResourcePool rp, HostSystem host)
            throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void rebootGuest(VirtualMachine vm) throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void shutdownGuest(VirtualMachine vm) throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void standbyGuest(VirtualMachine vm) throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void resetGuestInformation(VirtualMachine vm) throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public Task customizeVM(VirtualMachine vm, CustomizationSpec spec) throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Task migrateVM(VirtualMachine vm, ResourcePool rp, HostSystem host, String priority, String state)
            throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Task relocateVM(VirtualMachine vm, VirtualMachineRelocateSpec spec, String priority)
            throws RuntimeFault, RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 根据虚拟机管理对象唯一标识获取虚拟机对象
     * 
     * @param si
     *            服务实例
     * @param moId
     *            虚拟机管理对象唯一标识
     * @return 虚拟机对象，找不到时返回null
     * @throws VirtException 
     */
    public static VirtualMachine getVirtualMachineById(ServiceInstance si, String moId) throws VirtException {
		VirtualMachine vm = null;
		try {
			logger.debug("params is:si=" + si + ",moId=" + moId);
			ManagedObjectReference vmMor = new ManagedObjectReference();
			vmMor.set_value(moId);
			vmMor.setType("VirtualMachine");
			vm = (VirtualMachine) MorUtil.createExactManagedEntity(
					si.getServerConnection(), vmMor);
			logger.debug("vm is:" + vm.getName());
		} catch (Exception e) {
			throw new VirtException("can not find vm " + moId + "...");
		}
		return vm;
	}

    /**
     * 根据主机管理对象唯一标识获取主机对象
     * 
     * @param si
     *            服务实例
     * @param moId
     *            主机管理对象唯一标识
     * @return 主机对象，找不到时返回null
     */
    public static HostSystem getHostSystemById(ServiceInstance si, String moId) {
        logger.debug("params is:si=" + si + ",moId=" + moId);
        ManagedObjectReference hostMor = new ManagedObjectReference();
        hostMor.set_value(moId);
        hostMor.setType("HostSystem");
        HostSystem host = (HostSystem) MorUtil.createExactManagedEntity(si.getServerConnection(), hostMor);
        logger.debug("host is:" + host);
        return host;
    }

    /**
     * 根据资源池管理对象唯一标识获取资源池对象
     * 
     * @param si
     *            服务实例
     * @param moId
     *            资源池管理对象唯一标识
     * @return 资源池对象，找不到时返回null
     */
    public static ResourcePool getResourcePoolById(ServiceInstance si, String moId) {
        logger.debug("params is:si=" + si + ",moId=" + moId);
        ManagedObjectReference poolMor = new ManagedObjectReference();
        poolMor.set_value(moId);
        poolMor.setType("ResourcePool");
        ResourcePool pool = (ResourcePool) MorUtil.createExactManagedEntity(si.getServerConnection(), poolMor);
        logger.debug("pool is:" + pool);
        return pool;
    }

    /**
     * 根据数据存储管理对象唯一标识获取数据存储对象
     * 
     * @param si
     *            服务实例
     * @param moId
     *            数据存储管理对象唯一标识
     * @return 数据存储对象，找不到时返回null
     */
    public static Datastore getDatastoreById(ServiceInstance si, String moId) {
        logger.debug("params is:si=" + si + ",moId=" + moId);
        ManagedObjectReference datastoreMor = new ManagedObjectReference();
        datastoreMor.set_value(moId);
        datastoreMor.setType("Datastore");
        Datastore datastore = (Datastore) MorUtil.createExactManagedEntity(si.getServerConnection(), datastoreMor);
        logger.debug("datastore is:" + datastore);
        return datastore;
    }

    /**
     * 根据数据中心管理对象唯一标识获取数据中心对象
     * 
     * @param si
     *            服务实例
     * @param moId
     *            数据中心管理对象唯一标识
     * @return 数据中心对象，找不到时返回null
     */
    public static Datacenter getDatacenterById(ServiceInstance si, String moId) {
        logger.debug("params is:si=" + si + ",moId=" + moId);
        ManagedObjectReference datacenterMor = new ManagedObjectReference();
        datacenterMor.set_value(moId);
        datacenterMor.setType("Datacenter");
        Datacenter datacenter = (Datacenter) MorUtil.createExactManagedEntity(si.getServerConnection(), datacenterMor);
        logger.debug("datacenter is:" + datacenter);
        return datacenter;
    }

    /**
     * 根据数据存储获取关联的资源池，并按照使用率从大到小进行排序
     * 
     * @param si
     * @param datastoreid
     * @return
     */
    public static List<ResourcePool> getResourcePoolsByDatastore(ServiceInstance si, String datastoreid) {
        List<ResourcePool> poolList = new ArrayList<ResourcePool>();
        // TODO:

        return poolList;
    }

    /**
     * 获取数据中心下所有数据存储，并按照剩余空间从大到小进行排序
     * 
     * @param si
     *            服务实例
     * @param datacenterId
     *            数据中心管理对象唯一标识
     * @return 按照剩余空间从大到小排序后的数据存储列表
     * @throws VirtException
     */
    public static List<Datastore> getDatastores(ServiceInstance si, String datacenterId) throws VirtException {
        try {
            List<Datastore> list = new ArrayList<Datastore>();
            Datacenter datacenter = getDatacenterById(si, datacenterId);
            ManagedEntity[] mes = new InventoryNavigator(datacenter).searchManagedEntities("Datastore");
            if (mes != null && mes.length != 0) {
                for (ManagedEntity me : mes) {
                    Datastore ds = (Datastore) me;
                    list.add(ds);
                }
            }
            DatastoreSpaceComparator compartor = new DatastoreSpaceComparator();
            Collections.sort(list, compartor);

//            System.out.println(list);
            return list;
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new VirtException(e);
        }
    }

    /**
     * 获取所有存储池并按照剩余内存从大到小进行排序
     * 
     * @param si
     * @return
     * @throws VirtException
     */
    public static List<ResourcePool> getResourcePools(ServiceInstance si) throws VirtException {
        try {
            List<ResourcePool> list = new ArrayList<ResourcePool>();
            Folder rootFolder = si.getRootFolder();
            ManagedEntity[] mes = new InventoryNavigator(rootFolder).searchManagedEntities("ResourcePool");
            if (mes != null && mes.length != 0) {
                for (ManagedEntity me : mes) {
                    ResourcePool pool = (ResourcePool) me;
                    list.add(pool);
                }
            }
            ResPoolMemFreeComparator compartor = new ResPoolMemFreeComparator();
            Collections.sort(list, compartor);
            return list;
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new VirtException(e);
        }
    }
    /**
     * 根据资源池获取关联的数据存储，并按照剩余磁盘从大到小进行排序
     * 
     * @param si
     * @param moid
     * @return
     * @throws InvalidProperty
     * @throws RuntimeFault
     * @throws RemoteException
     */
    public static List<Datastore> getDataStoresByResoucePool(ServiceInstance si, String moid)
            throws InvalidProperty, RuntimeFault, RemoteException {
        // TODO:王文博提供的接口，用于测试，需删除
        ResourcePoolI rpi = new ResourcePoolImpl();
        rpi.setSi(si);
        ResourcePool rp = rpi.getResoucePoolbyMoid(moid);
        List<Datastore> list = new ArrayList<Datastore>();
//        rp.getOwner().getConfigStatus();
        ComputeResource cl = rp.getOwner();
        System.out.println(cl.getMOR().getType());
        // 集群资源
        if ("ClusterComputeResource".equals(cl.getMOR().getType()))
            list = getDataStoresByCluster(si, cl.getMOR().get_value());
        // 主机资源
        else
            list = Arrays.asList(cl.getDatastores());
        DatastoreSpaceComparator compartor = new DatastoreSpaceComparator();
        Collections.sort(list, compartor);
        return list;
    }

    public static List<Datastore> getDataStoresByCluster(ServiceInstance si, String clusterid) {
        // TODO:王文博提供的接口，用于测试，需删除
        ClusterI clusteri = new ClusterImpl();
        clusteri.setSi(si);
        List<Datastore> list = new ArrayList<Datastore>();
        ClusterComputeResource cluster = clusteri.getClusterByMoid(clusterid);
        HostSystem[] host = cluster.getHosts();// important

        Datastore[] ds = cluster.getDatastores();
        for (Datastore d : ds) {
            // if (host.length != d.getHost().length)
            // continue;
            // else
                list.add(d);
        }
        return list;
    }

    /**
     * 获取虚拟机/模板所在的数据中心
     * 
     * @param vmId
     *            虚拟机/模板管理对象唯一标识
     * @return 虚拟机/模板所在数据中心对象，找不到时返回null
     * @throws VirtException 
     */
    public static Datacenter getDatacenterByVmId(ServiceInstance si, String vmId) throws VirtException {
        logger.debug("params is:si=" + si + ",vmId=" + vmId);
        VirtualMachine vm = getVirtualMachineById(si, vmId);
        Datacenter datacenter = (Datacenter) MoQueryUtils.getNearestAncesterByType(vm, "Datacenter");
        if (datacenter != null) {
            logger.debug("datacenter id=" + datacenter.getMOR().get_value() + ",name=" + datacenter.getName());
        }
        return datacenter;
    }

    public static ClusterComputeResource getClusterByVmId(ServiceInstance si, String vmId) throws VirtException {
        logger.debug("params is:si=" + si + ",vmId=" + vmId);
        VirtualMachine vm = getVirtualMachineById(si, vmId);
        ManagedObjectReference hostMOR = vm.getRuntime().getHost();
        ClusterComputeResource cluster = null;
        if (hostMOR != null) {
            HostSystem host = getHostSystemById(si, hostMOR.get_value());
            cluster = (ClusterComputeResource) MoQueryUtils.getNearestAncesterByType(host, "ClusterComputeResource");
        }
        if (cluster != null) {
            logger.debug("cluster id=" + cluster.getMOR().get_value() + ",name=" + cluster.getName());
        }
        return cluster;
    }

    /**
     * 根据主机获取所在的数据中心
     * 
     * @param si
     *            服务实例
     * @param hostId
     *            主机管理对象唯一标识
     * @return 主机所在数据中心对象，找不到时返回null
     */
    public static Datacenter getDatacenterByHostId(ServiceInstance si, String hostId) {
        logger.debug("params is:si=" + si + ",hostId=" + hostId);
        HostSystem host = getHostSystemById(si, hostId);
        Datacenter datacenter = (Datacenter) MoQueryUtils.getNearestAncesterByType(host, "Datacenter");
        if (datacenter != null) {
            logger.debug("datacenter id=" + datacenter.getMOR().get_value() + ",name=" + datacenter.getName());
        }
        return datacenter;
    }

    public static Folder getFolderByVmId(ServiceInstance si, String vmId) throws VirtException {
        logger.debug("param is:si=" + si + ",vmId=" + vmId);
        VirtualMachine vm = getVirtualMachineById(si, vmId);
        Folder folder = (Folder) MoQueryUtils.getNearestAncesterByType(vm, "Folder");
        if (folder != null) {
            logger.debug("folder id=" + folder.getMOR().get_value() + ",name=" + folder.getName());
        }
        return folder;
    }

    public static void main(String[] args) throws Exception {
        // API-test下的资源池resgroup-779，CLUSTER下的资源池resgroup-64
        // API-test的数据存储datastore-786，CLUSTER下的数据存储datastore-10
        // API-test下的主机host-785，CLUSTER下的主机host-9
        // Datacenter datacenter = getDatacenterByHostId(Session.getInstance(),
        // "host-785");
        // System.out.println(datacenter);
        // List<Datastore> datastores = getDatastores(Session.getInstance(),
        // "datacenter-2");
        // System.out.println(datastores);
//        System.out.println(getClusterByVmId(Session.getInstance(), "vm-881"));
    }

}
