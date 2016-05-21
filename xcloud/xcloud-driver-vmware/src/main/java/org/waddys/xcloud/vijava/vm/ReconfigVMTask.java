package org.waddys.xcloud.vijava.vm;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.waddys.xcloud.vijava.base.BaseTask;
import org.waddys.xcloud.vijava.data.VMDiskInfo;
import org.waddys.xcloud.vijava.data.VMNicInfo;
import org.waddys.xcloud.vijava.exception.VirtException;
import org.waddys.xcloud.vijava.impl.Session;
import org.waddys.xcloud.vijava.impl.VirtualMachineImpl;
import org.waddys.xcloud.vijava.util.ParamValidator;
import org.waddys.xcloud.vijava.util.TaskAnswerConvert;
import org.waddys.xcloud.vijava.vm.ReconfigVM.ReconfigVMAnswer;
import org.waddys.xcloud.vijava.vm.ReconfigVM.ReconfigVMCmd;

import com.sugon.vim25.ConcurrentAccess;
import com.sugon.vim25.Description;
import com.sugon.vim25.DuplicateName;
import com.sugon.vim25.FileFault;
import com.sugon.vim25.GuestProcessInfo;
import com.sugon.vim25.GuestProgramSpec;
import com.sugon.vim25.InsufficientResourcesFault;
import com.sugon.vim25.InvalidDatastore;
import com.sugon.vim25.InvalidName;
import com.sugon.vim25.InvalidProperty;
import com.sugon.vim25.InvalidState;
import com.sugon.vim25.ManagedObjectReference;
import com.sugon.vim25.NamePasswordAuthentication;
import com.sugon.vim25.RuntimeFault;
import com.sugon.vim25.TaskInProgress;
import com.sugon.vim25.VirtualDevice;
import com.sugon.vim25.VirtualDeviceConfigSpec;
import com.sugon.vim25.VirtualDeviceConfigSpecFileOperation;
import com.sugon.vim25.VirtualDeviceConfigSpecOperation;
import com.sugon.vim25.VirtualDisk;
import com.sugon.vim25.VirtualDiskFlatVer2BackingInfo;
import com.sugon.vim25.VirtualE1000;
import com.sugon.vim25.VirtualEthernetCard;
import com.sugon.vim25.VirtualEthernetCardNetworkBackingInfo;
import com.sugon.vim25.VirtualMachineConfigInfo;
import com.sugon.vim25.VirtualMachineConfigSpec;
import com.sugon.vim25.VirtualMachinePowerState;
import com.sugon.vim25.VmConfigFault;
import com.sugon.vim25.mo.Datastore;
import com.sugon.vim25.mo.GuestOperationsManager;
import com.sugon.vim25.mo.GuestProcessManager;
import com.sugon.vim25.mo.Network;
import com.sugon.vim25.mo.ServiceInstance;
import com.sugon.vim25.mo.Task;
import com.sugon.vim25.mo.VirtualMachine;
import com.sugon.vim25.mo.util.MorUtil;

public class ReconfigVMTask extends BaseTask<ReconfigVMAnswer> {

    private static Logger logger = LoggerFactory.getLogger(ReconfigVMTask.class);

    private ReconfigVMCmd reconfigVMCmd = null;
    private ServiceInstance si = null;
    
    private static final CharSequence cSequenceNetWork = "network";
    private static final CharSequence cSequenceDvportgroup = "dvportgroup";
    
    public ReconfigVMTask(ReconfigVMCmd cmd) throws VirtException {
        super();
		this.reconfigVMCmd = cmd;
		
		try {
			this.setSi(Session.getInstanceByToken(cmd.getToken()));
		} catch (Exception e) {
			logger.error("连接失败，原因" + e.getMessage());
			throw new VirtException("虚拟环境" + "连接异常 ：" + e.getMessage());
		}
    }
    

    @Override
    public ReconfigVMAnswer execute() throws VirtException{
    	
    	ReconfigVMAnswer answer = new ReconfigVMAnswer();
        	
        //1. 根据给定的虚拟机对象唯一标识，获取虚拟机对象
        String vmId = this.reconfigVMCmd.getVmId();
        VirtualMachine virtualMachine = VirtualMachineImpl.getVirtualMachineById(si, vmId);
        
        if (virtualMachine == null) {
            throw new VirtException("给定虚拟机Id:" + vmId + "无对应的实例");
        }
        answer.setVmId(virtualMachine.getMOR().get_value());
        answer.setName(virtualMachine.getName());
        logger.info("虚拟机的名字 ：" + virtualMachine.getName());
        
    	//只是单纯的修改密码
    	if(null != reconfigVMCmd && null != reconfigVMCmd.getCurrentPassword()
    			&& null != reconfigVMCmd.getNewPassword()){
    		changeGuestOSPasswd(virtualMachine, reconfigVMCmd);
    		return new ReconfigVMAnswer()
    				.setName(virtualMachine.getName())
    				.setName(reconfigVMCmd.getNewPassword())
    				.setSuccess(true);
    	}
    	

    	
        //2. 设置虚拟机配置参数
        VirtualMachineConfigSpec config = new VirtualMachineConfigSpec();
        
        // 1)设置虚拟机名称
        String vmName = this.reconfigVMCmd.getName();
        config.setName(vmName);
        config.setAnnotation("createdby cloudview");
        // 2)如果设置了内存大小，检查电源状态
        Long memSizeMB = this.reconfigVMCmd.getMemSizeMB();
		if (ParamValidator.validatorParamsNotEmpty(memSizeMB)){
			if (VirtualMachinePowerState.poweredOn.name().equals(
					virtualMachine.getRuntime().getPowerState().name())) {
				throw new VirtException("当前虚机不支持内存热插拔");
			} else {
				config.setMemoryMB(memSizeMB);
			}
		}
        // 3)如果设置了cpu个数，则使用设置的cpu个数，否则，默认cpu个数与模板相同
        Integer cpuNum = this.reconfigVMCmd.getCpuNum();
        if (ParamValidator.validatorParamsNotEmpty(cpuNum)) {
        	if (VirtualMachinePowerState.poweredOn.name().equals(
					virtualMachine.getRuntime().getPowerState().name())){
        		throw new VirtException("当前虚机不支持cpu热插拔");
        	} else {
        		config.setNumCPUs(cpuNum);
        	}
        }
        
        // 4)配置磁盘：添加，删除
        List<VirtualDeviceConfigSpec> devices = new ArrayList<VirtualDeviceConfigSpec>();
        List<VMDiskInfo> diskInfos = this.reconfigVMCmd.getDiskInfos();
        Datastore[] datastores = null;
		try {
			datastores = virtualMachine.getDatastores();
		} catch (InvalidProperty e1) {
			logger.error("不合法的参数");
			throw new VirtException("远程运行失败", e1);
		} catch (RuntimeFault e1) {
			logger.error("远程运行失败");
			throw new VirtException("远程运行失败", e1);
		} catch (RemoteException e1) {
			logger.error("远程调用失败");
			throw new VirtException("远程调用失败", e1);
		}
        if (ParamValidator.validatorParamsNotEmpty(diskInfos)) {
        	Datastore datastore = datastores[0];
            for (VMDiskInfo diskInfo : diskInfos) {
            	
            	if (ParamValidator.validatorParamsIsEmpty(diskInfo.getDiskOperation())){
            		throw new VirtException("磁盘操作类型为空，需要指定操作类型：setDiskOperation");
            	}
            	
            	VirtualDeviceConfigSpec vdiskSpec = null;
            	if(diskInfo.getDiskOperation().equals(VirtualDeviceConfigSpecOperation.remove)){
                	if (ParamValidator.validatorParamsIsEmpty(diskInfo.getDiskId())){
                		throw new VirtException("磁盘ID为空，删除磁盘需要指定磁盘ID");
                	}
	                vdiskSpec = createRemoveDiskConfigSpec(virtualMachine, diskInfo.getDiskId());
	                devices.add(vdiskSpec);
	                continue;
                }
                
            	if (ParamValidator.validatorParamsIsEmpty(diskInfo.getDiskSizeGB())){
            		throw new VirtException("添加磁盘需要自定磁盘大小：setDiskSizeGB");
            	}
            	
                long diskSizeKB = 0;
                if(diskInfo.getDiskSizeGB() != null){
                    diskSizeKB = diskInfo.getDiskSizeGB() * 1024 * 1024;
                }
                if (ParamValidator.validatorParamsIsEmpty(diskInfo.getDiskMode())) {
                    diskInfo.setDiskMode("persistent");
                }
                if (ParamValidator.validatorParamsIsEmpty(diskInfo.getThinProvisioned())) {
                    diskInfo.setThinProvisioned(true);
                }
                String datastoreName = datastore.getName();
                if (ParamValidator.validatorParamsNotEmpty(diskInfo.getDatastoreId())) {
            		ManagedObjectReference mor = new ManagedObjectReference();
            		mor.set_value(diskInfo.getDatastoreId());
            		mor.setType("Datastore");
            		Datastore ds =  (Datastore) MorUtil.createExactManagedEntity(si.getServerConnection(), mor);
            		datastoreName = ds.getName();
                }
                if(diskInfo.getDiskOperation().equals(VirtualDeviceConfigSpecOperation.add)){
	                vdiskSpec = createAddDiskConfigSpec(virtualMachine, diskInfo,
	                		datastoreName, diskSizeKB);
	                devices.add(vdiskSpec);
                }
                
            }
        }
        answer.setDiskInfos(diskInfos);
        
        // 5)配置网卡：添加，删除
        List<VMNicInfo> nicInfos = this.reconfigVMCmd.getNicInfos();
        if (ParamValidator.validatorParamsNotEmpty(nicInfos)) {
        	// 保存内部网卡值
        	
        	try{
        		execCmdOfQueryAdaptor(virtualMachine, "cloudview_adaptor_1");
        	}catch(Exception e){
        		logger.debug("查询vmtools状态失败，虚拟机{}可能在关机或未安装vmtools", vmId);
        	}
        	
            for (VMNicInfo nicInfo : nicInfos) {
            	
            	if (ParamValidator.validatorParamsIsEmpty(nicInfo.getAdapterOperation())){
            		throw new VirtException("网卡操作类型为空，需要指定操作类型：setAdapterOperation");
            	}
            	
            	VirtualDeviceConfigSpec vdiskSpec = null;
            	if(nicInfo.getAdapterOperation().equals(VirtualDeviceConfigSpecOperation.remove)){
                	if (ParamValidator.validatorParamsIsEmpty(nicInfo.getAdapterId())){
                		throw new VirtException("网卡ID为空，删除网卡需要指定网卡ID");
                	}
	                vdiskSpec = createRemoveNicConfigSpec(virtualMachine, nicInfo.getAdapterId());
	                devices.add(vdiskSpec);
	                continue;
                }
            	
                if (ParamValidator.validatorParamsIsEmpty(nicInfo.getNetworkId())
                		|| ParamValidator.validatorParamsIsEmpty(nicInfo.getAdapterIP())
                		|| ParamValidator.validatorParamsIsEmpty(nicInfo.getAdapterNetMask())
                		|| ParamValidator.validatorParamsIsEmpty(nicInfo.getAdapterGateWay())) {
                    throw new VirtException("新加的网卡必须指定网络配置信息：网络Id，网卡ip，网络掩码，网关");
                }

                if (ParamValidator.validatorParamsIsEmpty(this.reconfigVMCmd.getCurrentPassword())) {
                	reconfigVMCmd.setCurrentPassword("111111");
//                    throw new VirtException("配置IP必须指定虚拟机管理员账户的密码");
                }
                
                VirtualDeviceConfigSpec vNicSpec = createAddNicConfigSpec(virtualMachine, nicInfo);
                devices.add(vNicSpec);
            }
        }
        answer.setNicInfos(nicInfos);
        
        if (devices != null && devices.size() != 0) {
            VirtualDeviceConfigSpec[] deviceArray = new VirtualDeviceConfigSpec[devices.size()];
            for (int i = 0; i < devices.size(); i++) {
                deviceArray[i] = devices.get(i);
            }
            config.setDeviceChange(deviceArray);
        }
        
        
        // 6.执行重新配置操作
        Task task = null;
		try {
			task = virtualMachine.reconfigVM_Task(config);
		} catch (InvalidName e) {
			logger.error("不合法的名称");
			throw new VirtException("不合法的名称", e);
		} catch (VmConfigFault e) {
			logger.error("虚拟机配置错误");
			throw new VirtException("虚拟机配置错误", e);
		} catch (DuplicateName e) {
			logger.error("虚拟机重名");
			throw new VirtException("虚拟机重名", e);
		} catch (TaskInProgress e) {
			logger.error("正在执行任务");
			throw new VirtException("正在执行任务", e);
		} catch (FileFault e) {
			logger.error("文件异常");
			throw new VirtException("文件异常", e);
		} catch (InvalidState e) {
			logger.error("状态不合法");
			throw new VirtException("状态不合法", e);
		} catch (ConcurrentAccess e) {
			logger.error("并发访问");
			throw new VirtException("并发访问", e);
		} catch (InvalidDatastore e) {
			logger.error("不合法的Datastore");
			throw new VirtException("不合法的Datastore", e);
		} catch (InsufficientResourcesFault e) {
			logger.error("资源不足");
			throw new VirtException("资源不足", e);
		} catch (RuntimeFault e) {
			logger.error("任务运行时错误");
			throw new VirtException("任务运行时错误", e);
		} catch (RemoteException e) {
			logger.error("远程调用失败");
			throw new VirtException("远程调用失败", e);
		}
        logger.debug("task.getMOR().get_value()" + task.getMOR().get_value());

        try{
        	ConfigIPThread mthread = 
        			new ConfigIPThread(virtualMachine, nicInfos);
            mthread.start();
        	TaskAnswerConvert.setTask(answer, task);
        }catch(Exception e){
        	//不做任何处理，允许IP配置失败
        }
        
        return answer.setName(vmName).setSuccess(true);
    }

    
    // 配置网络IP地址，放在任务后，不影响设备的修改
    // 如果没有IP地址，这种情况处理流程
    class ConfigIPThread extends Thread{

    	private VirtualMachine vm;
    	private List<VMNicInfo> nicInfos;
    	ConfigIPThread(VirtualMachine vm, 
    			List<VMNicInfo> nicInfos){
            this.vm = vm;
            this.nicInfos = nicInfos;
        }
    	
        public void run(){
            if (ParamValidator.validatorParamsNotEmpty(nicInfos)) {
                for (VMNicInfo nicInfo : nicInfos) {
                    try {
						changeGuestNicIP(vm, nicInfo);
					} catch (VirtException e) {
						logger.error("config ip error...");
					}
                }
            }
        }
    }
    
    /**
     * 配置磁盘
     */
    private VirtualDeviceConfigSpec createAddDiskConfigSpec(VirtualMachine vm, 
    		VMDiskInfo diskInfo, String dataStoreName, long diskSizeKB) {
    	
        VirtualDeviceConfigSpec diskSpec = new VirtualDeviceConfigSpec();
        diskSpec.setOperation(VirtualDeviceConfigSpecOperation.add);
        diskSpec.setFileOperation(VirtualDeviceConfigSpecFileOperation.create);

        VirtualDisk vd = new VirtualDisk();
        vd.setCapacityInKB(diskSizeKB);
        diskSpec.setDevice(vd);
        vd.setKey(getDiskkey(vm));
        vd.setUnitNumber(getDiskUnitNumber(vm));
        vd.setControllerKey(getDiskControllerkey(vm));
        VirtualDiskFlatVer2BackingInfo diskfileBacking = new VirtualDiskFlatVer2BackingInfo();
        String diskName = getRandomString(12);
        String fileName = "[" + dataStoreName + "] " + vm.getName() + "/" + diskName + ".vmdk";
        diskfileBacking.setFileName(fileName);
        diskfileBacking.setDiskMode(diskInfo.getDiskMode());
        diskfileBacking.setThinProvisioned(diskInfo.getThinProvisioned());
        vd.setBacking(diskfileBacking);
        
        diskInfo.setDiskId("" + vd.getKey());
        diskInfo.setName(fileName);
        diskInfo.setDiskOperation(VirtualDeviceConfigSpecOperation.add);
        return diskSpec;
    }
    
	private VirtualDeviceConfigSpec createRemoveDiskConfigSpec(
			VirtualMachine vm, String key) {
		
		VirtualDeviceConfigSpec diskSpec = new VirtualDeviceConfigSpec();
		VirtualDisk disk = (VirtualDisk) findVirtualDevice(vm.getConfig(), key);

		if (disk != null) {
			diskSpec.setOperation(VirtualDeviceConfigSpecOperation.remove);
			diskSpec.setFileOperation(VirtualDeviceConfigSpecFileOperation.destroy);
			diskSpec.setDevice(disk);
			return diskSpec;
		} else {
			System.out.println("No device found: " + key);
			return null;
		}
	}
	
	private VirtualDeviceConfigSpec createRemoveNicConfigSpec(
			VirtualMachine vm, String key) {
		
		VirtualDeviceConfigSpec diskSpec = new VirtualDeviceConfigSpec();
		VirtualE1000 nic = (VirtualE1000) findVirtualDevice(vm.getConfig(), key);

		if (nic != null) {
			diskSpec.setOperation(VirtualDeviceConfigSpecOperation.remove);
			diskSpec.setDevice(nic);
			return diskSpec;
		} else {
			System.out.println("No device found: " + key);
			return null;
		}
	}
	
	private static VirtualDevice findVirtualDevice(
			VirtualMachineConfigInfo vmConfig, String key) {
		
		VirtualDevice[] devices = vmConfig.getHardware().getDevice();
		for (int i = 0; i < devices.length; i++) {
			if (key.equals("" + devices[i].getKey())) {
				return devices[i];
			}
		}
		return null;
	}

    /**
     * 生成指定长度的随机字符串
     * 
     * @param length
     *            生成字符串的长度
     * @return
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 获取虚拟机磁盘管理的controlerkey
     * 
     * @param vm
     * @return
     */
    public static int getDiskControllerkey(VirtualMachine vm) {
        int controllerkey = 0;
        if (vm != null) {
            VirtualDevice[] devices = vm.getConfig().getHardware().getDevice();
            if (devices != null && devices.length > 0) {
                for (int i = 0; i < devices.length; i++) {
                    VirtualDevice device = devices[i];
                    if (device instanceof VirtualDisk) {
                        controllerkey = device.getControllerKey();
                    }
                }
            }
        }
        return controllerkey;
    }

    /**
     * 获取虚拟机磁盘已生成key
     * 
     * @param vm
     * @return
     */
    private static int getDiskkey(VirtualMachine vm) {
        int key = 0;
        if (vm != null) {
            VirtualDevice[] devices = vm.getConfig().getHardware().getDevice();
            if (devices != null && devices.length > 0) {
                for (int i = 0; i < devices.length; i++) {
                    VirtualDevice device = devices[i];
                    if (device instanceof VirtualDisk) {
                        key = device.getKey();
                    }
                }
            }
        }
        key = key + 1;
        return key;
    }

    /**
     * 获取虚拟机磁盘已生成UnitNumber
     * 
     * @param vm
     * @return
     */
    public static int getDiskUnitNumber(VirtualMachine vm) {
        int UnitNumber = 0;
        if (vm != null) {
            VirtualDevice[] devices = vm.getConfig().getHardware().getDevice();
            if (devices != null && devices.length > 0) {
                for (int i = 0; i < devices.length; i++) {
                    VirtualDevice device = devices[i];
                    if (device instanceof VirtualDisk) {
                        UnitNumber = device.getUnitNumber();
                    }
                }
            }
        }
        UnitNumber = UnitNumber + 1;
        return UnitNumber;
    }

    /**
     * 虚拟机网卡配置
     */
    private VirtualDeviceConfigSpec createAddNicConfigSpec(VirtualMachine virtualMachine,
    		VMNicInfo nicInfo) throws VirtException {
    	
        VirtualDeviceConfigSpec nicSpec = new VirtualDeviceConfigSpec();
        nicSpec.setOperation(VirtualDeviceConfigSpecOperation.add);
        
        if(ParamValidator.validatorParamsNotEmpty(nicInfo.getAdapterId())){
        	nicInfo.setAdapterName("adapter-" + nicInfo.getAdapterId());
        }else if(ParamValidator.validatorParamsIsEmpty(nicInfo.getAdapterName())){
        	String vmId = virtualMachine.getMOR().getVal();
        	String nicId = vmId.split("-")[1] + (new Random()).nextInt(10000000);
        	nicInfo.setAdapterName("adapter-" + nicId);
        }
        
        // 根据适配器类型创建相应的网卡实例
        VirtualEthernetCardNetworkBackingInfo nicBacking = 
        		new VirtualEthernetCardNetworkBackingInfo();
        

        Network network = null;
        if(nicInfo.getNetworkId().contains(cSequenceNetWork) 
        		|| nicInfo.getNetworkId().contains(cSequenceDvportgroup)){
        	ManagedObjectReference netMor  = new ManagedObjectReference();
        	netMor.setType("Network");
        	netMor.setVal(nicInfo.getNetworkId());
        	nicBacking.setNetwork(netMor);
        	network = (Network) MorUtil.
        			createExactManagedEntity(si.getServerConnection(), netMor);
        }
        nicBacking.setDeviceName(network.getName());
        
        Description info = new Description();
        info.setLabel(nicInfo.getAdapterName());
        info.setSummary(network.getName());
        
        VirtualEthernetCard nic = new VirtualE1000();
        nic.setDeviceInfo(info);
        // type: "generated", "manual", "assigned" by VC
        nic.setAddressType("generated");
        nic.setBacking(nicBacking);
        nic.setKey(getNickey(virtualMachine));

        nicInfo.setAdapterId("" + nic.getKey());
        nicSpec.setDevice(nic);
        return nicSpec;
    }

    /**
     * 获取虚拟机已有网卡信息
     * 
     * @param vm
     * @return
     */
    public static List<String> getNics(VirtualMachine vm) {
        List<String> nicNames = new ArrayList<String>();
        if (vm != null) {
            VirtualDevice[] devices = vm.getConfig().getHardware().getDevice();
            if (devices != null && devices.length > 0) {
                for (int i = 0; i < devices.length; i++) {
                    VirtualDevice device = devices[i];
                    if (device instanceof VirtualEthernetCard) {
                        String name = device.getDeviceInfo().getLabel();
                        nicNames.add(name);
                    }
                }
            }
        }
        return nicNames;
    }

    /**
     * 获取虚拟机网卡已有key
     * 
     * @param vm
     * @return
     */
    public static int getNickey(VirtualMachine vm) {
        int key = 0;
        if (vm != null) {
            VirtualDevice[] devices = vm.getConfig().getHardware().getDevice();
            if (devices != null && devices.length > 0) {
                for (int i = 0; i < devices.length; i++) {
                    VirtualDevice device = devices[i];
                    if (device instanceof VirtualEthernetCard) {
                        key = device.getKey();
                    }
                }
            }
        }
        key = key + 1;
        return key;
    }
    
    public static int getNicIndex(VirtualMachine vm) {
    	
        int key = 0;
        if (vm != null) {
            VirtualDevice[] devices = vm.getConfig().getHardware().getDevice();
            if (devices != null && devices.length > 0) {
                for (int i = 0; i < devices.length; i++) {
                    VirtualDevice device = devices[i];
                    if (device instanceof VirtualEthernetCard) {
                    	key++;
                    }
                }
            }
        }
        return key;
    }
    
    
    /**
     * 查询虚拟机内部网卡列表
     */
    private void execCmdOfQueryAdaptor(VirtualMachine vm, 
    		String file) throws VirtException{
    	
		GuestOperationsManager gom = si.getGuestOperationsManager();
		if (!"guestToolsRunning".equals(vm.getGuest().toolsRunningStatus)) {
			logger.error("The VMware Tools is not running in the Guest OS on VM " + vm.getName());
			logger.error("Exiting...");
		}

		String osFamily = vm.getGuest().getGuestFamily();
		logger.debug("osFamily: " + osFamily);
		if(null == osFamily || osFamily.length() <= 0) return;
		
		boolean isWindows = osFamily.contains("windowsGuest");
		String currentPassword = reconfigVMCmd.getCurrentPassword();
		NamePasswordAuthentication creds = new NamePasswordAuthentication();
		creds.username = isWindows ? "Administrator" : "root";
		creds.password = currentPassword;

		GuestProgramSpec spec = new GuestProgramSpec();
		if (isWindows) {
			spec.programPath = "C:\\Windows\\System32\\cmd.exe";
			String cmdString = "wmic nic get NetConnectionID | SORT > c:\\cvconfig\\" + file;
			spec.arguments = "/C " + cmdString;
		} else {
			spec.programPath = "/sbin/configNic.sh";
			spec.arguments = " ";
		}
		logger.debug("spec.arguments: " + spec.arguments);
		GuestProcessManager gpm = gom.getProcessManager(vm);
		
		long pid = -1;
		try {
			pid = gpm.startProgramInGuest(creds, spec);
			logger.debug("execCmdOfQueryAdaptor pid: " + pid);
			Thread.sleep(3000);
			while (true) {
				GuestProcessInfo[] infoList = gpm.listProcessesInGuest(creds, new long[] { pid });
				
				GuestProcessInfo info = infoList[0];
				if (info.getExitCode() == null) {
					logger.debug("Waiting for the process to exit ... ");
					Thread.sleep(3000);
				} else {
					logger.debug("exit code: " + info.getExitCode());
					break;
				}
			}
		} catch (Exception e) {
			//不抛异常，允许配置IP失败
			logger.error("execCmdOfQueryAdaptor failed: " + e.getMessage());
		}
    }
    
    
    
    /**
     * 开机情况下才能使用（模板统一密码）
     * 必须安装VMware Tools
     * 只修改管理员的账户
     */
	private void changeGuestOSPasswd(VirtualMachine vm, ReconfigVMCmd reconfigVMCmd)
			throws  VirtException {

		GuestOperationsManager gom = si.getGuestOperationsManager();
		if (!"guestToolsRunning".equals(vm.getGuest().toolsRunningStatus)) {
			logger.error("The VMware Tools is not running in the Guest OS on VM " + vm.getName());
			logger.error("Exiting...");
			throw new VirtException("没有安装VMware Tools，不能修改密码");
		}

		String osFamily = vm.getGuest().getGuestFamily();
		logger.debug("osFamily: " + osFamily);
		
		boolean isWindows = osFamily.contains("windowsGuest");
		String currentPassword = reconfigVMCmd.getCurrentPassword();
		String newPassword = reconfigVMCmd.getNewPassword();
		NamePasswordAuthentication creds = new NamePasswordAuthentication();
		creds.username = isWindows ? "Administrator" : "root";
		creds.password = currentPassword;

		GuestProgramSpec spec = new GuestProgramSpec();
		if (isWindows) {
			spec.programPath = "C:\\Windows\\System32\\cmd.exe";
			spec.arguments = "/C net user Administrator " + newPassword;
		} else {
			spec.programPath = "/bin/bash";
			spec.arguments = "-c 'echo " + newPassword + " | passwd root --stdin'";
		}
		GuestProcessManager gpm = gom.getProcessManager(vm);
		
		long pid = -1;
		try {
			pid = gpm.startProgramInGuest(creds, spec);
			logger.debug("changeGuestOSPasswd pid: " + pid);
			Thread.sleep(3000);
			creds.password = newPassword;
			while (true) {
				GuestProcessInfo[] infoList = gpm.listProcessesInGuest(creds, new long[] { pid });
				GuestProcessInfo info = infoList[0];
				if (info.getExitCode() == null) {
					logger.debug("Waiting for the process to exit ... ");
					Thread.sleep(3000);
				} else {
					logger.debug("exit code: " + info.getExitCode());
					break;
				}
			}
		} catch (Exception e) {
			logger.error("changeGuestOSPasswd failed: " + e.getMessage());
			throw new VirtException(e);
		}
	}
	
	
	private void changeGuestNicIP(VirtualMachine vm, 
			VMNicInfo nicInfo) throws  VirtException {

		try {
			//VI SDK invoke exception:com.sugon.vim25.InvalidState
			Thread.sleep(30000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		GuestOperationsManager gom = si.getGuestOperationsManager();
		String osFamily = vm.getGuest().getGuestFamily();
		logger.debug(vm.getName());
		logger.debug("osFamily: " + osFamily);
		
		boolean isWindows = osFamily.contains("windowsGuest");
		String currentPassword = reconfigVMCmd.getCurrentPassword();
		if(null == currentPassword || currentPassword.length() <= 0){
			currentPassword = "111111";
		}
		NamePasswordAuthentication creds = new NamePasswordAuthentication();
		creds.username = isWindows ? "Administrator" : "root";
		creds.password = currentPassword;

		GuestProgramSpec spec = new GuestProgramSpec();
		if (isWindows) {
        	try{
        		execCmdOfQueryAdaptor(vm, "cloudview_adaptor_2");
        	}catch(Exception e){
        		logger.debug("查询vmtools状态失败，虚拟机{}可能在关机或未安装vmtools", vm.getName());
        	}
			
			spec.programPath = "C:\\cvconfig\\configIP.bat";
			String argument = "";
			argument += nicInfo.getAdapterIP() + " ";
			argument += nicInfo.getAdapterNetMask() + " ";
			argument += nicInfo.getAdapterGateWay();
			logger.debug("changeGuestNicIP argument: " + argument);
			spec.arguments = argument;
//			spec.arguments = "c:\\cvconfig";
		} else {
			spec.programPath = "/sbin/configIP.sh";
			String argument = "";
			argument += nicInfo.getAdapterIP() + " ";
			argument += nicInfo.getAdapterNetMask() + " ";
			argument += nicInfo.getAdapterGateWay();
			logger.debug("changeGuestNicIP argument: " + argument);
			spec.arguments = argument;
		}
		GuestProcessManager gpm = gom.getProcessManager(vm);
		
		long pid = -1;
		try {
			pid = gpm.startProgramInGuest(creds, spec);
			logger.debug("changeGuestNicIP pid: " + pid);
			Thread.sleep(1500);
			while (true) {
				GuestProcessInfo[] infoList = gpm.listProcessesInGuest(creds, new long[] { pid });
				GuestProcessInfo info = infoList[0];
				if (info.getExitCode() == null) {
					logger.debug("Waiting for the process to exit ... ");
					Thread.sleep(3000);
				} else {
					logger.debug("exit code: " + info.getExitCode());
					break;
				}
			}
		} catch (Exception e) {
			logger.error("changeGuestNicIP failed: " + e.getMessage());
			throw new VirtException(e);
		}
	}

    
    
	public ServiceInstance getSi() {
		return si;
	}

	public void setSi(ServiceInstance si) {
		this.si = si;
	}
	
    public static void main(String args[]){
    	
    	ReconfigVMCmd reconfigVMCmd = new ReconfigVMCmd();
//    	reconfigVMCmd.setVmId("vm-290");
//    	reconfigVMCmd.setVmId("vm-285");
    	reconfigVMCmd.setVmId("vm-319");
    	//测试修改密码
//    	reconfigVMCmd.setCurrentPassword("222222");
//    	reconfigVMCmd.setNewPassword("111111");
    	
//    	//测试添加磁盘
//    	VMDiskInfo diskInfo = new VMDiskInfo();
//    	List<VMDiskInfo> diskInfos = new LinkedList<VMDiskInfo>();
//    	
//    	VirtualDeviceConfigSpecOperation diskOperation = VirtualDeviceConfigSpecOperation.add;
//		diskInfo.setDiskOperation(diskOperation );
//    	diskInfo.setDiskSizeGB(1L);
//    	diskInfo.setDatastoreId("datastore-198");
//    	diskInfos.add(diskInfo);
//    	reconfigVMCmd.setDiskInfos(diskInfos);
    	
    	//测试删除磁盘
//    	VMDiskInfo diskInfo = new VMDiskInfo();
//    	List<VMDiskInfo> diskInfos = new LinkedList<VMDiskInfo>();
//    	
//    	VirtualDeviceConfigSpecOperation diskOperation = VirtualDeviceConfigSpecOperation.remove;
//		diskInfo.setDiskOperation(diskOperation );
//		diskInfo.setDiskId("2003");
//    	diskInfos.add(diskInfo);
//    	reconfigVMCmd.setDiskInfos(diskInfos);
    	
    	//测试添加网卡
    	VirtualDeviceConfigSpecOperation adapterOperation = VirtualDeviceConfigSpecOperation.add;
    	VMNicInfo vmNicInfo = new VMNicInfo();
    	vmNicInfo.setAdapterOperation(adapterOperation);
    	reconfigVMCmd.setCurrentPassword("111111");
    	vmNicInfo.setNetworkId("network-83");
    	vmNicInfo.setAdapterIP("169.244.245.252");
    	vmNicInfo.setAdapterNetMask("255.255.0.0");
    	vmNicInfo.setAdapterGateWay("169.244.245.254");
    	List<VMNicInfo> vmNicInfos = new LinkedList<VMNicInfo>();
    	vmNicInfos.add(vmNicInfo);
    	reconfigVMCmd.setNicInfos(vmNicInfos);
    	
//    	//测试删除网卡
//    	VirtualDeviceConfigSpecOperation adapterOperation = VirtualDeviceConfigSpecOperation.remove;
//    	VMNicInfo vmNicInfo = new VMNicInfo();
//    	vmNicInfo.setAdapterOperation(adapterOperation);
//    	vmNicInfo.setAdapterId("4005");
//    	List<VMNicInfo> vmNicInfos = new LinkedList<VMNicInfo>();
//    	vmNicInfos.add(vmNicInfo);
//    	reconfigVMCmd.setNicInfos(vmNicInfos);
    	
		try {
			ReconfigVMTask createVMTask = new ReconfigVMTask(reconfigVMCmd);
			ReconfigVMAnswer answer = createVMTask.execute();
			System.out.println(answer);

		} catch (VirtException e) {
			e.printStackTrace();
		}
    	
//		try {
//			ReconfigVMTask createVMTask = new ReconfigVMTask(reconfigVMCmd);
//	    	VirtualMachine virtualMachine = VirtualMachineImpl.getVirtualMachineById(createVMTask.si, reconfigVMCmd.getVmId());
//	    	createVMTask.changeGuestNicIP(virtualMachine, vmNicInfo);
//		} catch (VirtException e) {
//		}

    }
}
