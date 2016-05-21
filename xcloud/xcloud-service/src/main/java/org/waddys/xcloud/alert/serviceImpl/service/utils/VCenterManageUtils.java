/**
 * 
 */
package org.waddys.xcloud.alert.serviceImpl.service.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



import org.springframework.stereotype.Service;
import org.waddys.xcloud.alert.serviceImpl.service.utils.Connection;

import com.sugon.vim25.ManagedObjectReference;
import com.sugon.vim25.TaskFilterSpec;
import com.sugon.vim25.TaskFilterSpecByTime;
import com.sugon.vim25.TaskFilterSpecByUsername;
import com.sugon.vim25.TaskFilterSpecTimeOption;
import com.sugon.vim25.TaskInfo;
import com.sugon.vim25.VirtualDevice;
import com.sugon.vim25.mo.Alarm;
import com.sugon.vim25.mo.ClusterComputeResource;
import com.sugon.vim25.mo.Datacenter;
import com.sugon.vim25.mo.Datastore;
import com.sugon.vim25.mo.DistributedVirtualPortgroup;
import com.sugon.vim25.mo.DistributedVirtualSwitch;
import com.sugon.vim25.mo.Folder;
import com.sugon.vim25.mo.HostSystem;
import com.sugon.vim25.mo.InventoryNavigator;
import com.sugon.vim25.mo.ManagedEntity;
import com.sugon.vim25.mo.Network;
import com.sugon.vim25.mo.ResourcePool;
import com.sugon.vim25.mo.ServerConnection;
import com.sugon.vim25.mo.ServiceInstance;
import com.sugon.vim25.mo.Task;
import com.sugon.vim25.mo.TaskHistoryCollector;
import com.sugon.vim25.mo.TaskManager;
import com.sugon.vim25.mo.VirtualMachine;
import com.sugon.vim25.mo.VmwareDistributedVirtualSwitch;

/**
 * @author yangkun
 *
 */
@Service
public class VCenterManageUtils {

	
	public Folder getRootFolder(){
		ServiceInstance si=Connection.getSi();
		if(si!=null){
			return  si.getRootFolder();
		}
		return null;
	}
	
	public ServerConnection getServerConnection(){
		ServiceInstance si=Connection.getSi();
		if(si!=null){
			return si.getServerConnection();
		}
		return null;
	}
	
	public ServiceInstance getSi(){
		return Connection.getSi();
	}
	
	/**
	 * 根据alarmId获取告警对象
	 * @param alarmId
	 * @return
	 * @throws CloudViewPerfException
	 */
	public Alarm getAlarmById(String alarmId)
			throws CloudViewPerfException {
		try {
			ManagedObjectReference mor = new ManagedObjectReference();
			mor.setType(PerfConstants.ENTITY_ALARM);
			mor.setVal(alarmId);
			Alarm alarm = new Alarm(getServerConnection(), mor);
			return alarm;
		} catch (Exception e) {
			throw new CloudViewPerfException("Find alarmId[" + alarmId + "] fault");
		}
	}
	
	/**
	 * 根据MOR获取告警对象实体
	 * @param mor
	 * @return
	 */
	public Alarm getAlarmByMor(ManagedObjectReference mor){
		Alarm alarm = new Alarm(getServerConnection(), mor);
		return alarm;
	}
	
	/**
	 * 根据虚拟机id获取对象
	 *
	 * @param vmId
	 *            vcenter中虚拟机 ID
	 * @return
	 * @throws CloudViewPerfException
	 */
	public VirtualMachine getVirtualMachineById(String vmId)
			throws CloudViewPerfException {

		try {
			ManagedObjectReference mor = new ManagedObjectReference();
			mor.setType(PerfConstants.ENTITY_VM);
			mor.setVal(vmId);
			VirtualMachine vm = new VirtualMachine(getServerConnection(), mor);
			return vm;
		} catch (Exception e) {
			throw new CloudViewPerfException("Find vmID[" + vmId + "] fault");
		}

	}

	/**
	 * 根据群集id获取对象
	 *
	 * @param clusterId
	 * @return
	 * @throws CloudViewPerfException
	 */
	public ClusterComputeResource getClusterComputeResourceById(String clusterId)
			throws CloudViewPerfException {

		try {
			ManagedObjectReference mor = new ManagedObjectReference();
			mor.setType(PerfConstants.ENTITY_CLUSTER);
			mor.setVal(clusterId);
			ClusterComputeResource cluster = new ClusterComputeResource(
					getServerConnection(), mor);
			return cluster;
		} catch (Exception e) {
			throw new CloudViewPerfException("Find clusterID[" + clusterId
					+ "] fault");
		}

	}

	/**
	 * 根据资源池id获取对象
	 *
	 * @param respoolId
	 * @return
	 * @throws CloudViewPerfException
	 */
	public ResourcePool getResourcePoolById(String respoolId)
			throws CloudViewPerfException {

		try {
			ManagedObjectReference mor = new ManagedObjectReference();
			mor.setType(PerfConstants.ENTITY_RP);
			mor.setVal(respoolId);
			ResourcePool respool = new ResourcePool(getServerConnection(), mor);
			return respool;
		} catch (Exception e) {
			throw new CloudViewPerfException("Find respoolID[" + respoolId
					+ "] fault");
		}

	}

	/**
	 * 根据主机id获取对象
	 *
	 * @param hostId
	 * @return
	 * @throws CloudViewPerfException
	 */
	public HostSystem getHostSystemById(String hostId)
			throws CloudViewPerfException {

		try {
			ManagedObjectReference mor = new ManagedObjectReference();
			mor.setType(PerfConstants.ENTITY_HOST);
			mor.setVal(hostId);
			HostSystem hostSystem = new HostSystem(getServerConnection(), mor);
			return hostSystem;
		} catch (Exception e) {
			throw new CloudViewPerfException("Find hostID[" + hostId + "] fault");
		}

	}

	/**
	 * 根据数据中心id获取对象
	 *
	 * @param hostId
	 * @return
	 * @throws CloudViewPerfException
	 */
	public Datacenter getDatacenterById(String dcId) throws CloudViewPerfException {
		try {
			ManagedObjectReference mor = new ManagedObjectReference();
			mor.setType(PerfConstants.ENTITY_DC);
			mor.setVal(dcId);
			Datacenter dc = new Datacenter(getServerConnection(), mor);
			return dc;
		} catch (Exception e) {
			throw new CloudViewPerfException("Find hostID[" + dcId + "] fault");
		}
	}

	/**
	 * 通过任务ID构造任务对象，未确认此对象是否真正存在，用于测试
	 *
	 * @param taskId
	 * @return
	 * @throws CloudViewPerfException
	 */
	public Task getTaskById(String taskId) throws CloudViewPerfException {

		try {
			ManagedObjectReference mor = new ManagedObjectReference();
			mor.setType("Task");
			mor.setVal(taskId);
			Task task = new Task(getServerConnection(), mor);
			return task;
		} catch (Exception e) {
			throw new CloudViewPerfException("Find taskID[" + taskId + "] fault",
					e);
		}
	}

	public TaskInfo getTaskInfoById(String taskId) throws CloudViewPerfException {

		try {
			ManagedObjectReference mor = new ManagedObjectReference();
			mor.setType("Task");
			mor.setVal(taskId);
			Task task = new Task(getServerConnection(), mor);
			return task.getTaskInfo();
		} catch (Exception e) {
			throw new CloudViewPerfException("Find taskID[" + taskId + "] fault",
					e);
		}
	}

	/**
	 * 查找历史任务对象
	 *
	 * @param taskId
	 * @return
	 * @throws CloudViewPerfException
	 */
	public TaskInfo getHistoryTaskById(String taskId)
			throws CloudViewPerfException {

		try {
			TaskManager taskMgr = this.getServerConnection().getServiceInstance()
					.getTaskManager();
			TaskFilterSpec tfs = createTaskFilterSpec();

			TaskHistoryCollector thc = taskMgr.createCollectorForTasks(tfs);

			// Note: 10 <= pagesize <= 62
			thc.setCollectorPageSize(15);

			// 一页50个，慢慢往上翻
			TaskInfo[] tis = thc.getLatestPage();
			TaskInfo taskInfo = queryTaskInTaskinfos(tis, taskId);
			while (taskInfo == null) {
				tis = thc.readNextTasks(50);
				if (tis == null) {
					break;
				} else {
					taskInfo = queryTaskInTaskinfos(tis, taskId);
				}

			}
			thc.destroyCollector();

			return taskInfo;
		} catch (Exception e) {
			throw new CloudViewPerfException("Find history task by ID[" + taskId
					+ "] fault", e);
		}

	}

	private TaskInfo queryTaskInTaskinfos(TaskInfo[] tis, String taskId) {
		for (TaskInfo taskInfo : tis) {
			if (taskId.equals(taskInfo.getKey())) {
				return taskInfo;
			}
		}
		return null;
	}

	/**
	 * 构造历史任务查询的Spec
	 *
	 * @param ent
	 * @return
	 */
	private TaskFilterSpec createTaskFilterSpec() {
		TaskFilterSpec tfs = new TaskFilterSpec();

		// only the root initiated tasks
		TaskFilterSpecByUsername nameFilter = new TaskFilterSpecByUsername();
		nameFilter.setUserList(new String[] { "Administrator" });
		// include tasks initiated by non-users,
		// for example, by ScheduledTaskManager.
		nameFilter.setSystemUser(true);
		tfs.setUserName(nameFilter);

		// // only the tasks with one entity itself
		// TaskFilterSpecByEntity entFilter = new TaskFilterSpecByEntity();
		// // entFilter.setEntity(ent.getMOR());
		// entFilter.setRecursion(TaskFilterSpecRecursionOption.all);
		// tfs.setEntity(entFilter);
		//
		// // only successfully finished tasks
		// tfs.setState(new TaskInfoState[] { TaskInfoState.success });

		// only tasks started within last one month
		// strictly speaking, time should be retrieved from server
		TaskFilterSpecByTime tFilter = new TaskFilterSpecByTime();
		Calendar cal = Calendar.getInstance();
		cal.roll(Calendar.MONTH, -1);// 一个月之前
		tFilter.setBeginTime(cal);
		// we ignore the end time here so it gets the latest.
		tFilter.setTimeType(TaskFilterSpecTimeOption.startedTime);
		tfs.setTime(tFilter);

		// Optionally, you limits tasks initiated by scheduled task
		// with the setScheduledTask() method.
		return tfs;
	}

	/**
	 * 根据虚拟机名称获得虚拟机对象
	 *
	 * @param vmName
	 * @return
	 * @throws CloudViewPerfException
	 */
	public VirtualMachine getVirtualMachineByName(String vmName)
			throws CloudViewPerfException {
		try {

			if (vmName == null || vmName.equals("")) {
				return null;
			}

			ManagedEntity managedEntity = new InventoryNavigator(getRootFolder())
					.searchManagedEntity(PerfConstants.ENTITY_VM, vmName);

			return (VirtualMachine) managedEntity;
		} catch (Exception e) {
			throw new CloudViewPerfException("Find vmName[" + vmName + "] fault",
					e);
		}

	}

	/**
	 * 根据群集名称获得群集对象
	 *
	 * @param clusterName
	 * @return
	 * @throws CloudViewPerfException
	 */
	public ClusterComputeResource getClusterComputeResourceByName(
			String clusterName) throws CloudViewPerfException {
		try {

			if (clusterName == null || clusterName.equals("")) {
				return null;
			}

			ManagedEntity managedEntity = new InventoryNavigator(getRootFolder())
					.searchManagedEntity(PerfConstants.ENTITY_CLUSTER, clusterName);

			return (ClusterComputeResource) managedEntity;
		} catch (Exception e) {
			throw new CloudViewPerfException("Find clusterName[" + clusterName
					+ "] fault", e);
		}

	}

	/**
	 * 根据群集名称获得群集对象
	 *
	 * @param respoolName
	 * @return
	 * @throws CloudViewPerfException
	 */
	public ResourcePool getResourcePoolByName(String respoolName)
			throws CloudViewPerfException {
		try {

			if (respoolName == null || respoolName.equals("")) {
				return null;
			}

			ManagedEntity managedEntity = new InventoryNavigator(getRootFolder())
					.searchManagedEntity(PerfConstants.ENTITY_RP, respoolName);

			return (ResourcePool) managedEntity;
		} catch (Exception e) {
			throw new CloudViewPerfException("Find respoolName[" + respoolName
					+ "] fault", e);
		}

	}

	/**
	 * 根据群集名称获得群集对象
	 *
	 * @param hostName
	 * @return
	 * @throws CloudViewPerfException
	 */
	public HostSystem getHostSystemByName(String hostName)
			throws CloudViewPerfException {
		try {

			if (hostName == null || hostName.equals("")) {
				return null;
			}

			ManagedEntity managedEntity = new InventoryNavigator(getRootFolder())
					.searchManagedEntity(PerfConstants.ENTITY_HOST, hostName);

			return (HostSystem) managedEntity;
		} catch (Exception e) {
			throw new CloudViewPerfException("Find hostName[" + hostName
					+ "] fault", e);
		}

	}

	/**
	 * 根据虚拟交换机名称获得虚拟交换机对象
	 *
	 * @param vswitchName
	 * @return
	 * @throws CloudViewPerfException
	 */
	public VmwareDistributedVirtualSwitch getVmwareDistributedVirtualSwitchByName(
			String vswitchName) throws CloudViewPerfException {
		try {

			if (vswitchName == null || vswitchName.equals("")) {
				return null;
			}

			ManagedEntity managedEntity = new InventoryNavigator(getRootFolder())
					.searchManagedEntity(PerfConstants.ENTITY_SWICTH, vswitchName);

			return (VmwareDistributedVirtualSwitch) managedEntity;
		} catch (Exception e) {
			throw new CloudViewPerfException("Find vswtichName[" + vswitchName
					+ "] fault", e);
		}

	}

	/**
	 * 获取第一个找到的虚拟交换机对象
	 *
	 * @return
	 * @throws CloudViewPerfException
	 */
	public DistributedVirtualSwitch getDistributedVirtualSwitch()
			throws CloudViewPerfException {

		try {
			ManagedEntity[] managedEntities = new InventoryNavigator(getRootFolder())
					.searchManagedEntities(PerfConstants.ENTITY_SWICTH);

			if (managedEntities.length > 0) {
				return (DistributedVirtualSwitch) managedEntities[0];
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new CloudViewPerfException("Find vSwitch fault", e);
		}
	}

	/**
	 * 根据虚拟端口组ID获得虚拟端口组对象
	 *
	 * @param entityId
	 * @return
	 * @throws CloudViewPerfException
	 */
	public DistributedVirtualPortgroup getDistributedVirtualPortgroupById(
			String vpgId) throws CloudViewPerfException {
		try {
			ManagedObjectReference mor = new ManagedObjectReference();
			mor.setType(PerfConstants.ENTITY_VPG);
			mor.setVal(vpgId);
			DistributedVirtualPortgroup vpg = new DistributedVirtualPortgroup(
					getServerConnection(), mor);
			return vpg;
		} catch (Exception e) {
			throw new CloudViewPerfException("Find VPortGroupID[" + vpgId
					+ "] fault");
		}
	}

	/**
	 * 根据虚拟端口组名称获得虚拟端口组对象
	 *
	 * @param portgroupName
	 * @return
	 * @throws CloudViewPerfException
	 */
	public DistributedVirtualPortgroup getDistributedVirtualPortgroupByName(
			String portgroupName) throws CloudViewPerfException {
		try {

			if (portgroupName == null || portgroupName.equals("")) {
				return null;
			}

			ManagedEntity managedEntity = new InventoryNavigator(getRootFolder())
					.searchManagedEntity(PerfConstants.ENTITY_VPG, portgroupName);

			return (DistributedVirtualPortgroup) managedEntity;
		} catch (Exception e) {
			throw new CloudViewPerfException("Find vportgroupName["
					+ portgroupName + "] fault", e);
		}
	}

	/**
	 * 根据设备名称（或不指定）找制定类别的设备
	 *
	 * @param vm
	 * @param type
	 * @param name
	 * @return
	 */
	public VirtualDevice getDeviceByName(VirtualMachine vm, String type,
			String name) {
		if(vm.getConfig()==null){
			return null;
		}
		VirtualDevice[] devices = vm.getConfig().getHardware().getDevice();
		for (VirtualDevice device : devices) {
			if (device.getClass().getCanonicalName().indexOf(type) != -1) {
				if (name == null || name.equals("")) {
					return device;// 不指定名称，找第一个
				}
				if (name.equalsIgnoreCase(device.getDeviceInfo().getLabel())) {
					return device;// 指定名称，匹配才返回
				}
			}
		}
		return null;
	}

	/**
	 * 查找全部指定类型的设备
	 *
	 * @param vm
	 * @param type
	 * @return
	 */
	public List<VirtualDevice> getDevices(VirtualMachine vm, String type) {
		List<VirtualDevice> vdList = new ArrayList<VirtualDevice>();
		if(vm.getConfig()==null){
			return null;
		}
		VirtualDevice[] devices = vm.getConfig().getHardware().getDevice();
		for (VirtualDevice device : devices) {
			if (device.getClass().getCanonicalName().indexOf(type) != -1) {
				vdList.add(device);
			}
		}
		return vdList;
	}

	/**
	 * 根据找父节点方式获取数据中心实例
	 *
	 * @param entity
	 * @return
	 */
	public Datacenter getDatacenterByGetParent(ManagedEntity entity) {
		if (entity == null) {
			return null;
		}
		ManagedEntity mo = entity;
		while (!(mo instanceof Datacenter)) {
			mo = mo.getParent();
		}
		return (Datacenter) mo;
	}

	/**
	 * 根据实体id获取对象
	 *
	 * @param clusterId
	 * @return
	 * @throws CloudViewPerfException
	 */
	public Datastore getDatastoreById(String dsId) throws CloudViewPerfException {

		try {
			ManagedObjectReference mor = new ManagedObjectReference();
			mor.setType(PerfConstants.ENTITY_DS);
			mor.setVal(dsId);
			Datastore entity = new Datastore(getServerConnection(), mor);
			return entity;
		} catch (Exception e) {
			throw new CloudViewPerfException("Find datastore[" + dsId + "] fault");
		}

	}

	/**
	 * 获取集群内全部Datastore
	 *
	 * @param clusterId
	 * @return
	 * @throws CloudViewPerfException
	 */
	public List<Datastore> getAllDatastore(String clusterId)
			throws CloudViewPerfException {
		List<Datastore> retList = new ArrayList<Datastore>();
		try {

			ClusterComputeResource cluster = this
					.getClusterComputeResourceById(clusterId);
			Datastore[] datastore = cluster.getDatastores();

			for (int i = 0; i < datastore.length; i++) {
				retList.add(datastore[i]);
			}

			return retList;
		} catch (Exception e) {
			throw new CloudViewPerfException("Find all Datastore fault", e);
		}
	}

	/**
	 * 获取全部数据存储对象
	 *
	 * @return
	 * @throws CloudViewPerfException
	 */
	public List<Datastore> getAllDatastore() throws CloudViewPerfException {
		List<Datastore> retList = new ArrayList<Datastore>();
		try {

			ManagedEntity[] managedEntities = new InventoryNavigator(getRootFolder())
					.searchManagedEntities(PerfConstants.ENTITY_DS);

			for (ManagedEntity entity : managedEntities) {
				if (entity instanceof Datastore) {
					Datastore ds = (Datastore) entity;
					retList.add(ds);
				}
			}
			return retList;
		} catch (Exception e) {
			throw new CloudViewPerfException("Find all Datastore fault", e);
		}
	}

	/**
	 * 获取全部集群对象
	 *
	 * @return
	 * @throws CloudViewPerfException
	 */
	public List<ClusterComputeResource> getAllClusterComputeResources()
			throws CloudViewPerfException {
		List<ClusterComputeResource> retList = new ArrayList<ClusterComputeResource>();
		try {

			ManagedEntity[] managedEntities = new InventoryNavigator(getRootFolder())
					.searchManagedEntities(PerfConstants.ENTITY_CLUSTER);

			for (ManagedEntity entity : managedEntities) {
				if (entity instanceof ClusterComputeResource) {
					ClusterComputeResource cluster = (ClusterComputeResource) entity;
					retList.add(cluster);
				}
			}
			return retList;
		} catch (Exception e) {
			throw new CloudViewPerfException(
					"Find all ClusterComputeResources fault", e);
		}
	}

	/**
	 * 获取全部主机对象
	 *
	 * @param clusterId
	 * @return
	 * @throws CloudViewPerfException
	 */
	public List<HostSystem> getAllHostSystemInCluster(String clusterId)
			throws CloudViewPerfException {
		List<HostSystem> retList = new ArrayList<HostSystem>();
		try {

			ClusterComputeResource cluster = this
					.getClusterComputeResourceById(clusterId);

		//	HostSystem[] hostSystems = cluster.getHosts();
			//System.out.println("name"+clusterId);
			if(cluster.getHosts()!=null &&  cluster.getHosts().length >0){
				//System.out.println("aaaaaaaa"+cluster.getHosts().length);
				
				for (HostSystem host : cluster.getHosts()) {
					//System.out.println("dddddddddddddddd"+cluster.getHosts().length);
					retList.add(host);
				}
			}

			return retList;
		} catch (Exception e) {
			throw new CloudViewPerfException("Find all HostSystems fault", e);
		}
	}
	
	/**
	 * Authoer: dengjq@sugon.com
	 * 
	 * 获取所有主机对象，包括集群中的主机以及孤立主机
	 * 
	 * @return
	 * @throws CloudViewPerfException
	 */
	
	public List<HostSystem> getAllHostSystems()
			throws CloudViewPerfException {
		List<HostSystem> retList = new ArrayList<HostSystem>();
		try {

			ManagedEntity[] managedEntities = new InventoryNavigator(getRootFolder())
					.searchManagedEntities(PerfConstants.ENTITY_HOST);

			for (ManagedEntity entity : managedEntities) {
				if (entity instanceof HostSystem) {
					HostSystem hostSystem = (HostSystem) entity;
					retList.add(hostSystem);
				}
			}
			return retList;
		} catch (Exception e) {
			throw new CloudViewPerfException("Find all HostSystems fault", e);
		}
	}
	
	/**
	 * Authoer: dengjq@sugon.com	
	 * 获取所有虚拟机对象，包括集群中的主机以及
	 * 
	 * @return
	 * @throws CloudViewPerfException
	 */
	public List<VirtualMachine> getAllVirtualMachines()
			throws CloudViewPerfException {
		List<VirtualMachine> retList = new ArrayList<VirtualMachine>();
		try {

			ManagedEntity[] managedEntities = new InventoryNavigator(getRootFolder())
					.searchManagedEntities(PerfConstants.ENTITY_VM);

			for (ManagedEntity entity : managedEntities) {
				if (entity instanceof VirtualMachine) {
					VirtualMachine vm = (VirtualMachine) entity;
					retList.add(vm);
				}
			}
			return retList;
		} catch (Exception e) {
			throw new CloudViewPerfException("Find all HostSystems fault", e);
		}
	}

	public Network getNetwork() throws CloudViewPerfException {

		try {
			ManagedEntity[] managedEntity = new InventoryNavigator(getRootFolder())
					.searchManagedEntities("Network");

			if (managedEntity != null) {
				return (Network) managedEntity[0];
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new CloudViewPerfException("Find all Network fault", e);
		}

	}
	
	
}
