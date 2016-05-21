package org.waddys.xcloud.vijava.vm;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.waddys.xcloud.vijava.base.BaseTask;
import org.waddys.xcloud.vijava.data.VMDiskInfo;
import org.waddys.xcloud.vijava.data.VMNetworkInfo;
import org.waddys.xcloud.vijava.data.VMachine;
import org.waddys.xcloud.vijava.exception.VirtException;
import org.waddys.xcloud.vijava.impl.Session;
import org.waddys.xcloud.vijava.impl.VirtualMachineImpl;
import org.waddys.xcloud.vijava.util.VmConvertUtils;
import org.waddys.xcloud.vijava.vm.QueryVM.PowerState;
import org.waddys.xcloud.vijava.vm.QueryVM.QueryVMAnswer;
import org.waddys.xcloud.vijava.vm.QueryVM.QueryVMCmd;

import com.sugon.vim25.CustomFieldValue;
import com.sugon.vim25.Event;
import com.sugon.vim25.EventFilterSpec;
import com.sugon.vim25.EventFilterSpecByEntity;
import com.sugon.vim25.GuestInfo;
import com.sugon.vim25.GuestNicInfo;
import com.sugon.vim25.Tag;
import com.sugon.vim25.TaskFilterSpec;
import com.sugon.vim25.TaskFilterSpecByEntity;
import com.sugon.vim25.TaskFilterSpecRecursionOption;
import com.sugon.vim25.TaskInfo;
import com.sugon.vim25.TaskInfoState;
import com.sugon.vim25.VirtualDevice;
import com.sugon.vim25.VirtualDisk;
import com.sugon.vim25.VirtualMachineConfigInfo;
import com.sugon.vim25.VmDeployedEvent;
import com.sugon.vim25.VmEvent;
import com.sugon.vim25.VmPoweredOffEvent;
import com.sugon.vim25.VmPoweredOnEvent;
import com.sugon.vim25.VmRemovedEvent;
import com.sugon.vim25.VmSuspendedEvent;
import com.sugon.vim25.VmSuspendingEvent;
import com.sugon.vim25.mo.ClusterComputeResource;
import com.sugon.vim25.mo.Datastore;
import com.sugon.vim25.mo.Folder;
import com.sugon.vim25.mo.InventoryNavigator;
import com.sugon.vim25.mo.ManagedEntity;
import com.sugon.vim25.mo.Network;
import com.sugon.vim25.mo.ServiceInstance;
import com.sugon.vim25.mo.TaskHistoryCollector;
import com.sugon.vim25.mo.VirtualMachine;

public class QueryVMTask extends BaseTask<QueryVMAnswer> {

    private static Logger logger = LoggerFactory.getLogger(QueryVMTask.class);

    private static final Boolean success = true;
    private static final Boolean failure = false;

    /**
     * 查询虚拟机的参数
     */
    private QueryVMCmd queryCmd;
    private ServiceInstance si = null;

    public QueryVMTask(QueryVMCmd queryCmd) throws VirtException {
        super();
        this.queryCmd = queryCmd;
    	
		try {
			this.setSi(Session.getInstanceByToken(queryCmd.getToken()));
		} catch (Exception e) {
			logger.error("连接失败，原因" + e.getMessage());
			throw new VirtException("虚拟环境" + "连接异常 ：" + e.getMessage());
		}
		
    }
    

    @Override
    public QueryVMAnswer execute() {
    	
        logger.debug("查询参数=" + this.queryCmd);
        try {
            
            // 获取所有虚拟机
            Folder rootFolder = si.getRootFolder();
            ManagedEntity[] vmEntities = new InventoryNavigator(rootFolder).searchManagedEntities("VirtualMachine");
            if (vmEntities == null || vmEntities.length == 0) {
                return new QueryVMAnswer().setSuccess(success).setVmList(null);
            }
            List<VirtualMachine> vmList = new ArrayList<VirtualMachine>();
            for (ManagedEntity me : vmEntities) {
                vmList.add((VirtualMachine) me);
            }
            
            // 按照各个查询条件查询虚拟机，最后取交集
            String idValue = this.queryCmd.getVmId();
            if (idValue != null) {
                List<VirtualMachine> idList = new ArrayList<VirtualMachine>();
                for (ManagedEntity me : vmEntities) {
                    VirtualMachine vm = (VirtualMachine) me;
                    if (idValue.equals(vm.getMOR().get_value())) {
                        idList.add(vm);
                        break;
                    }
                }
                vmList.retainAll(idList);
                logger.debug("满足id条件的虚拟机列表是" + vmList);
            }

            String nameValue = this.queryCmd.getVmName();
            if (nameValue != null) {
                List<VirtualMachine> nameList = new ArrayList<VirtualMachine>();
                for (ManagedEntity me : vmEntities) {
                    VirtualMachine vm = (VirtualMachine) me;
                    if (vm.getName().contains(nameValue)) {
                        nameList.add(vm);
                    }
                }
                vmList.retainAll(nameList);
                logger.debug("满足name条件的虚拟机列表是" + vmList);
            }

            PowerState powerStatusValue = this.queryCmd.getPowerStatus();
            if (powerStatusValue != null) {
                List<VirtualMachine> powerStateList = new ArrayList<VirtualMachine>();
                for (ManagedEntity me : vmEntities) {
                    VirtualMachine vm = (VirtualMachine) me;
                    if (vm.getRuntime() != null) {
                        if (powerStatusValue.name().equals(vm.getRuntime().getPowerState().name())) {
                            powerStateList.add(vm);
                        }
                    }
                }
                vmList.retainAll(powerStateList);
                logger.debug("满足powerstate条件的虚拟机列表是" + vmList);
            }

            String guestOsIdValue = this.queryCmd.getGuestOSId();
            if (guestOsIdValue != null) {
                List<VirtualMachine> guestOsList = new ArrayList<VirtualMachine>();
                for (ManagedEntity me : vmEntities) {
                    VirtualMachine vm = (VirtualMachine) me;
                    if (vm.getGuest() != null && vm.getGuest().getGuestId() != null) {
                        if (vm.getGuest().getGuestId().contains(guestOsIdValue)) {
                            guestOsList.add(vm);
                        }
                    }
                }
                vmList.retainAll(guestOsList);
                logger.debug("满足guestOs条件的虚拟机列表是" + vmList);
            }


            String ipValue = this.queryCmd.getIp();
            if (ipValue != null) {
                List<VirtualMachine> ipList = new ArrayList<VirtualMachine>();
                for (ManagedEntity me : vmEntities) {
                    VirtualMachine vm = (VirtualMachine) me;
                    if (vm.getGuest() != null && vm.getGuest().getIpAddress() != null) {
                        if (vm.getGuest().getIpAddress().contains(ipValue)) {
                            ipList.add(vm);
                        }
                    }
                }
                vmList.retainAll(ipList);
                logger.debug("满足ip条件的虚拟机列表是" + vmList);
            }

            String datastoreValue = this.queryCmd.getDatastoreName();
            if (datastoreValue != null) {
                List<VirtualMachine> datastoreList = new ArrayList<VirtualMachine>();
                for (ManagedEntity me : vmEntities) {
                    VirtualMachine vm = (VirtualMachine) me;
                    if (vm.getDatastores() != null) {
                        for (Datastore datastore : vm.getDatastores()) {
                            if (datastoreValue.equalsIgnoreCase(datastore.getName())) {
                                datastoreList.add(vm);
                                break;
                            }
                        }
                    }
                }
                vmList.retainAll(datastoreList);
                logger.debug("满足datastore条件的虚拟机列表是" + vmList);
            }

            String networkValue = this.queryCmd.getNetworkName();
            if (networkValue != null) {
                List<VirtualMachine> networkList = new ArrayList<VirtualMachine>();
                for (ManagedEntity me : vmEntities) {
                    VirtualMachine vm = (VirtualMachine) me;
                    if (vm.getNetworks() != null) {
                        for (Network net : vm.getNetworks()) {
                            if (networkValue.equalsIgnoreCase(net.getName())) {
                                networkList.add(vm);
                                break;
                            }
                        }
                    }
                }
                vmList.retainAll(networkList);
                logger.debug("满足network条件的虚拟机列表是" + vmList);
            }

            String poolValue = this.queryCmd.getPoolName();
            if (poolValue != null) {
                List<VirtualMachine> poolList = new ArrayList<VirtualMachine>();
                for (ManagedEntity me : vmEntities) {
                    VirtualMachine vm = (VirtualMachine) me;
                    if (vm.getResourcePool() != null) {
                        if (poolValue.equalsIgnoreCase(vm.getResourcePool().getName())) {
                            poolList.add(vm);
                        }
                    }
                }
                vmList.retainAll(poolList);
                logger.debug("满足resourcepool条件的虚拟机列表是" + vmList);
            }

            Boolean isTemplateValue = this.queryCmd.getIsTemplate();
            if (isTemplateValue != null) {
                List<VirtualMachine> vmOrTempList = new ArrayList<VirtualMachine>();
                for (ManagedEntity me : vmEntities) {
                    VirtualMachine vm = (VirtualMachine) me;
                    VirtualMachineConfigInfo config = vm.getConfig();
                    if(config == null)
                    	continue;
                    if (isTemplateValue.equals(vm.getConfig().template)) {
                        vmOrTempList.add(vm);
                    }
                }
                vmList.retainAll(vmOrTempList);
                logger.debug("满足isTemplate条件的虚拟机列表是" + vmList);
            }

            
            // 利旧虚拟机
            //isCloudviewVM = null: all VM queried
            //isCloudviewVM = true : only cloudview VM queried
            //isCloudviewVM = false: only not cloudview VM queried
            Boolean isCloudviewVM = this.queryCmd.getIsCloudviewVM();
            if (null != isCloudviewVM) {
                List<VirtualMachine> cloudviewVMList = new ArrayList<VirtualMachine>();
                for (ManagedEntity me : vmEntities) {
                    VirtualMachine vm = (VirtualMachine) me;
                    VirtualMachineConfigInfo config = vm.getConfig();
                    if(config == null)
                    	continue;
                    if(isCloudviewVM && null != vm.getConfig().getAnnotation() && 
                    		vm.getConfig().getAnnotation().contains("createdby cloudview")){
                    	cloudviewVMList.add(vm);
                    }else if(!isCloudviewVM && (null == vm.getConfig().getAnnotation() ||
                    		!vm.getConfig().getAnnotation().contains("createdby cloudview"))){
                    	cloudviewVMList.add(vm);
                    }
                }
                vmList.retainAll(cloudviewVMList);
                logger.debug("满足tag条件的虚拟机列表是" + vmList);
            }
            
            
            List<VMachine> lsVM = new ArrayList<VMachine>();
            for (VirtualMachine vm : vmList) {
                ClusterComputeResource cluster = VirtualMachineImpl.getClusterByVmId(si, vm.getMOR().get_value());
                VMachine vMachine = convertVM(vm, cluster);
                lsVM.add(vMachine);
            }
            
            logger.debug("lsVM is:" + lsVM);
            QueryVMAnswer ret = new QueryVMAnswer().setSuccess(success).setVmList(lsVM);

            logger.debug("ret is:" + ret);
            return ret;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new QueryVMAnswer().setSuccess(failure).setErrMsg(e.getMessage());
        }
    }

    // 构造返回结果
    public VMachine convertVM(VirtualMachine vm, ClusterComputeResource cluster) {
    	
    	//查询并构造任务相关属性
        VMachine vMachine = new VMachine();
        vMachine.setId(vm.getMOR().get_value());
        vMachine.setName(vm.getName());
	
    	TaskFilterSpecByEntity entity = new TaskFilterSpecByEntity();
		entity.setEntity(vm.getMOR());
		try {
			
			TaskFilterSpec filter = new TaskFilterSpec();
      		entity.setRecursion(TaskFilterSpecRecursionOption.all);
      		filter.setEntity(entity);
			TaskInfoState[] state = {TaskInfoState.running,TaskInfoState.queued};
			filter.setState(state );
			TaskHistoryCollector  thc = si.getTaskManager().createCollectorForTasks(filter);
		     List<TaskInfo> tis = new ArrayList<TaskInfo>();
			while (true) {
				TaskInfo[] tia = thc.readNextTasks(50);
				if (tia == null) {
					break;
				}
				tis.addAll(Arrays.asList(tia));
			}
			thc.destroyCollector();
			for(TaskInfo ti : tis){
				if(ti.descriptionId.contains("powerOff")){
					vMachine.setTaskId(ti.getKey());
					vMachine.setVmTaskStatus("stopping");
				}else if(ti.descriptionId.contains("powerOn")||ti.descriptionId.contains("suspend")){
					vMachine.setTaskId(ti.getKey());
					vMachine.setVmTaskStatus("starting");
				}else if(ti.descriptionId.contains("destroy")){
					vMachine.setTaskId(ti.getKey());
					vMachine.setVmTaskStatus("deleting");
				}
					
	    	/*EventFilterSpec efs = new EventFilterSpec();
	    	int chainId = ti.getEventChainId();
	    	efs.setEventChainId(chainId);
	    	Event[] eventChain = si.getEventManager().queryEvents(efs);
	    	
			for (int index = 0; eventChain != null && index < eventChain.length; index++) {
				VmEvent vmEvent = null;
				if (eventChain[index] instanceof VmEvent) {
					vmEvent = (VmEvent) eventChain[index];
				} else {
					continue;
				}
				if (vmEvent instanceof VmRemovedEvent) {
					vMachine.setTaskId(ti.getKey());
					vMachine.setVmTaskStatus("deleting");
				}
				else if(vmEvent instanceof VmPoweredOffEvent){
					vMachine.setTaskId(ti.getKey());
					vMachine.setVmTaskStatus("stopping");
				}
				else if(vmEvent instanceof VmPoweredOnEvent){
					vMachine.setTaskId(ti.getKey());
					vMachine.setVmTaskStatus("starting");
			}	
				else if(vmEvent instanceof VmSuspendingEvent){
					vMachine.setTaskId(ti.getKey());
					vMachine.setVmTaskStatus("starting");
			}	
				
			}//FOR END eventChain
*/			
			}//FOR END(tis)
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//操作系统运行状态
        GuestInfo guest = vm.getGuest();
        if (guest != null) {
            vMachine.setRunningStatus(vm.getGuest().getGuestState());
        }
        //电源状态
        vMachine.setPowerStatus(vm.getRuntime().getPowerState().name());
        vMachine.setOsName(vm.getConfig().getGuestFullName());
        vMachine.setOsId(vm.getConfig().getGuestId());
        vMachine.setCpuNum(vm.getSummary().getConfig().getNumCpu());
        Integer memSizeMB = vm.getSummary().getConfig().getMemorySizeMB();
        vMachine.setMemSizeMB(Long.valueOf(memSizeMB.toString()));
        vMachine.setIsTemplate(vm.getConfig().template);
        if (cluster != null) {
            vMachine.setClusterName(cluster.getName());
            vMachine.setClusterId(cluster.getMOR().get_value());
        }
        VirtualDevice[] devices = vm.getConfig().getHardware().getDevice();
        List<VMDiskInfo> diskInfos = new ArrayList<VMDiskInfo>();
        for (VirtualDevice device : devices) {
            if (device instanceof VirtualDisk) {
                VirtualDisk vd = (VirtualDisk) device;
                VMDiskInfo diskInfo = new VMDiskInfo();
                diskInfo.setDiskSizeGB(vd.getCapacityInKB() / 1048576);
                diskInfo.setName(vd.getDeviceInfo().getLabel());
                diskInfos.add(diskInfo);
            }
        }
        vMachine.setDiskInfos(diskInfos);
        //网卡信息
        List<VMNetworkInfo> networkInfos = new ArrayList<VMNetworkInfo>();
        if (guest != null) {
            GuestNicInfo[] nics = guest.getNet();
            if (nics != null) {
                for (int i = 0; i < nics.length; i++) {
                    VMNetworkInfo networkInfo = new VMNetworkInfo();
                    GuestNicInfo nicInfo = nics[i];
                    String[] ips = nicInfo.getIpAddress();
                    if (ips != null) {
                        List<String> ipList = VmConvertUtils.convertArray2List(ips);
                        networkInfo.setIp(ipList.toString());
                        networkInfos.add(networkInfo);
                    }
                }
            }
        }
        vMachine.setNetworkInfos(networkInfos);

        logger.debug("vMachine is:" + vMachine);
        return vMachine;
    }
    

	public ServiceInstance getSi() {
		return si;
	}

	public void setSi(ServiceInstance si) {
		this.si = si;
	}


    
    public static void main(String args[]){
    	
    	QueryVMCmd queryVMCmd = new QueryVMCmd();
//    	queryVMCmd.setIsTemplate(true);
//    	queryVMCmd.setVmName("6-linux-clone");
//    	queryVMCmd.setIsCloudviewVM(false);

		QueryVMTask task;
		try {
//			queryVMCmd.setVmId("vm-1198");
			queryVMCmd.setVmId("vm-1075");
			task = new QueryVMTask(queryVMCmd);

			QueryVMAnswer answer = task.execute();

			System.out.println(answer.getVmList().size());
		} catch (VirtException e) {
			e.printStackTrace();
		}
    	
//    	Boolean bo = null;
//    	System.out.println(bo);
    }
}

