package org.waddys.xcloud.vijava.vm;

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
import org.waddys.xcloud.vijava.impl.DatastoreImpl;
import org.waddys.xcloud.vijava.impl.Session;
import org.waddys.xcloud.vijava.impl.VirtualMachineImpl;
import org.waddys.xcloud.vijava.util.ParamValidator;
import org.waddys.xcloud.vijava.util.TaskAnswerConvert;
import org.waddys.xcloud.vijava.vm.CreateVM.CreateVMAnswer;
import org.waddys.xcloud.vijava.vm.CreateVM.CreateVMCmd;

import com.vmware.vim25.CustomizationAdapterMapping;
import com.vmware.vim25.CustomizationFixedIp;
import com.vmware.vim25.CustomizationFixedName;
import com.vmware.vim25.CustomizationGlobalIPSettings;
import com.vmware.vim25.CustomizationIPSettings;
import com.vmware.vim25.CustomizationLinuxOptions;
import com.vmware.vim25.CustomizationLinuxPrep;
import com.vmware.vim25.CustomizationSpec;
import com.vmware.vim25.Description;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.OptionValue;
import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.VirtualDeviceBackingInfo;
import com.vmware.vim25.VirtualDeviceConfigSpec;
import com.vmware.vim25.VirtualDeviceConfigSpecFileOperation;
import com.vmware.vim25.VirtualDeviceConfigSpecOperation;
import com.vmware.vim25.VirtualDisk;
import com.vmware.vim25.VirtualDiskFlatVer2BackingInfo;
import com.vmware.vim25.VirtualE1000;
import com.vmware.vim25.VirtualEthernetCard;
import com.vmware.vim25.VirtualEthernetCardNetworkBackingInfo;
import com.vmware.vim25.VirtualMachineCloneSpec;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.VirtualMachineGuestOsFamily;
import com.vmware.vim25.VirtualMachineRelocateSpec;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.Network;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.util.MorUtil;

public class CreateVMTask extends BaseTask<CreateVMAnswer> {

    private static Logger logger = LoggerFactory.getLogger(CreateVMTask.class);

    private CreateVMCmd createCmd = null;
    private ServiceInstance si = null;
    private static final CharSequence cSequenceNetWork = "network";
    private static final CharSequence cSequenceDvportgroup = "dvportgroup";
    
    public CreateVMTask(CreateVMCmd cmd) throws VirtException {
        super();
		this.createCmd = cmd;
		
		try {
			this.setSi(Session.getInstanceByToken(cmd.getToken()));
		} catch (Exception e) {
			logger.error("连接失败，原因" + e.getMessage());
			throw new VirtException("虚拟环境" + "连接异常 ：" + e.getMessage());
		}
    }
    

    @Override
    public CreateVMAnswer execute() {
        try {
        	
        	CreateVMAnswer answer = new CreateVMAnswer();

            //1. 根据给定的模板管理对象唯一标识，获取模板管理对象
            String templMoId = this.createCmd.getTemplateId();
            VirtualMachine template = VirtualMachineImpl.getVirtualMachineById(si, templMoId);
            if (template == null) {
                throw new VirtException("给定模板id=" + templMoId + "无对应的模板");
            }
            
            
            //2. 设置计算资源和存储资源
            String poolMoId = this.createCmd.getPoolId();
            String datastoreMoId = this.createCmd.getDatastoreId();
            ResourcePool pool = null;
            Datastore datastore = null;
            if (ParamValidator.validatorParamsNotEmpty(poolMoId)
                    && ParamValidator.validatorParamsNotEmpty(datastoreMoId)) {
                datastore = VirtualMachineImpl.getDatastoreById(si, datastoreMoId);
                if (ParamValidator.validatorParamsIsEmpty(datastore)) {
                    throw new VirtException("指定的数据存储datastoreMoId=" + datastoreMoId + "不存在");
                }
                pool = VirtualMachineImpl.getResourcePoolById(si, poolMoId);
                if (ParamValidator.validatorParamsIsEmpty(pool)) {
                    throw new VirtException("指定的资源池poolMoId=" + poolMoId + "不存在");
                }
                
                // 设置了资源池和数据存储，需要校验资源池、数据存储一致性
                List<Datastore> dss = VirtualMachineImpl.getDataStoresByResoucePool(si, poolMoId);
                boolean isConsistent = false;
                if (dss != null) {
                    for (Datastore ds : dss) {
                        if (ds.getMOR().get_value().equals(datastoreMoId)) {
                            isConsistent = true;
                            break;
                        }
                    }
                }
                // TODO:需恢复，因为根据资源池取数据存储的接口返回值不对，只能先注释掉
                if (!isConsistent) {
                    throw new VirtException(
                            "指定的资源池poolMoId=" + poolMoId + "和数据存储datastoreMoId=" + datastoreMoId + "不一致");
                }
            } else if (ParamValidator.validatorParamsNotEmpty(datastoreMoId)) {
                // 只设置了数据存储，则需要选择与数据存储一致的资源池
                datastore = VirtualMachineImpl.getDatastoreById(si, datastoreMoId);
                if (ParamValidator.validatorParamsIsEmpty(datastore)) {
                    throw new VirtException("指定的数据存储datastoreMoId=" + datastoreMoId + "不存在");
                }
                // 获取与数据存储一致的资源池
                List<ResourcePool> pools = VirtualMachineImpl.getResourcePoolsByDatastore(si, datastoreMoId);
                if (ParamValidator.validatorParamsIsEmpty(pools)) {
                    throw new VirtException("没有与指定数据存储datastoreId=" + datastoreMoId + "一致的资源池");
                }
                // 选择配额剩余最多的资源池
                pool = pools.get(0);
            } else if (ParamValidator.validatorParamsNotEmpty(poolMoId)) {
                // 只设置了资源池，选择与资源池一致的数据存储
                pool = VirtualMachineImpl.getResourcePoolById(si, poolMoId);
                if (ParamValidator.validatorParamsIsEmpty(pool)) {
                    throw new VirtException("指定的资源池poolMoId=" + poolMoId + "不存在");
                }
                DatastoreImpl di = new DatastoreImpl();
                di.setSi(si);
                List<Datastore> datastores = di.getDataStoresByResoucePool(poolMoId);
                if (ParamValidator.validatorParamsIsEmpty(datastores)) {
                    throw new VirtException("没有与指定资源池一致的数据存储");
                }
                // 选择剩余空间最大的数据存储
                datastore = datastores.get(0);
            } else {
                // TODO:资源池、数据存储均未设置，选择模板所在的数据存储，根据数据存储选择占用份额少的资源池？；
                List<ResourcePool> pools = VirtualMachineImpl.getResourcePools(si);
                if (ParamValidator.validatorParamsIsEmpty(pools)) {
                    throw new VirtException("系统中没有资源池");
                }
                pool = pools.get(0);
                List<Datastore> datastores = VirtualMachineImpl.getDataStoresByResoucePool(si,
                        pool.getMOR().get_value());
                if (ParamValidator.validatorParamsIsEmpty(datastores)) {
                    throw new VirtException("没有与资源池一致的数据存储");
                }
                datastore = datastores.get(0);
            }
            logger.debug("pool name is:" + pool.getName());
            logger.debug("datastore name is:" + datastore.getName());
            VirtualMachineRelocateSpec relocate = new VirtualMachineRelocateSpec();
            relocate.setPool(pool.getMOR());
            relocate.setDatastore(datastore.getMOR());
            // relocate.setDiskMoveType(
            // com.sugon.vim25.VirtualMachineRelocateDiskMoveOptions.createNewChildDiskBacking.name());
            
            
            //3. 设置虚拟机配置参数
            VirtualMachineConfigSpec config = new VirtualMachineConfigSpec();
            
            // 0) 设置虚拟机Extra Info
            OptionValue[] cloudviewOption = new OptionValue[1];
            cloudviewOption[0] = new OptionValue();
            cloudviewOption[0].setKey("machine.id");
            
            if(ParamValidator.validatorParamsNotEmpty(this.createCmd.getPasswd())){
            	answer.setPasswd(this.createCmd.getPasswd());
            }else{
            	answer.setPasswd(genRandomNum(8));
            }
            String machineId = "passwd:" + answer.getPasswd();
            //The format of ip_config is: ip:ip_value,netmask:netmask_value,gateway:gateway_value,dns1:dns1_value,dns2:dns2_value
//            extraConfig[0].setValue("ip:" + ip + ",netmask:" + netmask + ",gateway:" + gateway + ",dns1:" + dns1 + ",dns2:" + dns2);
            cloudviewOption[0].setKey("machine.id");
            cloudviewOption[0].setValue(machineId);
            config.setExtraConfig(cloudviewOption);
            config.setAnnotation("createdby cloudview");
            
            // 1)设置虚拟机名称
            String vmName = this.createCmd.getName();
            config.setName(vmName);
            // 2)如果设置了内存大小，则使用设置的内存大小，否则，默认内存大小与模板相同
            Long memSizeMB = this.createCmd.getMemSizeMB();
            if (ParamValidator.validatorParamsNotEmpty(memSizeMB)) {
                config.setMemoryMB(memSizeMB);
                // TODO:校验剩余资源是否满足需求
                // ResourcePoolResourceUsage memUsage =
                // pool.getRuntime().getMemory();
            }
            // 3)如果设置了cpu个数，则使用设置的cpu个数，否则，默认cpu个数与模板相同
            Integer cpuNum = this.createCmd.getCpuNum();
            if (ParamValidator.validatorParamsNotEmpty(cpuNum)) {
                config.setNumCPUs(cpuNum);
                // TODO:校验剩余资源是否满足需求
            }
            
            // 4)如果设置了磁盘，则新增磁盘，并返回模板的磁盘
            List<VirtualDeviceConfigSpec> devices = new ArrayList<VirtualDeviceConfigSpec>();
            List<VMDiskInfo> tDiskInfos = new LinkedList<VMDiskInfo>();
            int key = addTemplateDiskInfos(template, tDiskInfos);
            List<VMDiskInfo> diskInfos = this.createCmd.getDiskInfos();
            if (ParamValidator.validatorParamsNotEmpty(diskInfos)) {
            	datastore =  template.getDatastores()[0];
                for (VMDiskInfo diskInfo : diskInfos) {
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
                    VirtualDeviceConfigSpec vdiskSpec = createAddDiskConfigSpec(template, diskInfo,
                    		datastoreName, diskSizeKB, ++key);
                    devices.add(vdiskSpec);
                }
                // TODO:校验剩余资源是否满足需求
            }
            if(null == diskInfos){
            	diskInfos = new LinkedList<VMDiskInfo>();
            }
            diskInfos.addAll(tDiskInfos);
            answer.setDiskInfos(diskInfos);
            
            // 5) 先获得模板的网卡信息
            //     如果设置了网卡，则新增网卡
            List<VMNicInfo> tNicInfos = new LinkedList<VMNicInfo>();
        	key = addTemplateNicInfos(template, tNicInfos);
        	List<VMNicInfo> nicInfos = this.createCmd.getNicInfos();
            if (ParamValidator.validatorParamsNotEmpty(nicInfos)) {
                for (VMNicInfo nicInfo : nicInfos) {
                    String networkId = nicInfo.getNetworkId();
                    if (ParamValidator.validatorParamsIsEmpty(networkId)) {
                        throw new VirtException("必须指定网络");
                    }
                    VirtualDeviceConfigSpec vNicSpec = createAddNicConfigSpec(template, nicInfo, ++key);
                    devices.add(vNicSpec);
                }
            }
            if(null == nicInfos){
            	nicInfos = new LinkedList<VMNicInfo>();
            }
            nicInfos.addAll(tNicInfos);
            answer.setNicInfos(nicInfos);
            
            if (devices != null && devices.size() != 0) {
                VirtualDeviceConfigSpec[] deviceArray = new VirtualDeviceConfigSpec[devices.size()];
                for (int i = 0; i < devices.size(); i++) {
                    deviceArray[i] = devices.get(i);
                }
                config.setDeviceChange(deviceArray);
            }
            
            //4. 设置克隆选项
            VirtualMachineCloneSpec cloneSpec = new VirtualMachineCloneSpec();
            cloneSpec.setLocation(relocate);
            // 虚拟机创建不启动
            cloneSpec.setPowerOn(false);
            // 创建的是虚拟机而不是模板
            cloneSpec.setTemplate(false);
            cloneSpec.setConfig(config);

            
            //5. 配置IP地址
            List<VMNicInfo> networks = this.createCmd.getNicInfos();
            CustomizationSpec customizationSpec = null;
            String guestName = template.getConfig().getGuestFullName();
            if (networks != null && guestName != null) {
                String guestFamily = getGuestFamilyById(guestName);
                if (VirtualMachineGuestOsFamily.windowsGuest.name().equals(guestFamily)) {
                    customizationSpec = createWindowsCustomization(template, networks);
                } else if (VirtualMachineGuestOsFamily.linuxGuest.name().equals(guestFamily)) {
                    customizationSpec = createLinuxCustomization(template, networks);
                } 
            }
            if (customizationSpec != null) {
                cloneSpec.setCustomization(customizationSpec);
            }

            
            // 6.执行模板克隆操作，实际创建虚拟机
            Task task = template.cloneVM_Task((Folder) template.getParent(), vmName, cloneSpec);
            logger.debug("task.getMOR().get_value()" + task.getMOR().get_value());
            TaskAnswerConvert.setTask(answer, task);
            answer.setName(vmName).setSuccess(true);
             return answer;
        } catch (Exception e) {
            logger.error("模板生成虚拟机失败\n" + e);
            CreateVMAnswer answer = new CreateVMAnswer().setSuccess(false);
            answer.setErrMsg("模板生成虚拟机失败");
            return answer;
        }

    }

    
    private String getGuestFamilyById(String guestId) {
        if (guestId.contains("windows") || guestId.contains("Windows")) {
            return VirtualMachineGuestOsFamily.windowsGuest.name();
        }
        return VirtualMachineGuestOsFamily.linuxGuest.name();
    }
    
    
    private static int addTemplateDiskInfos(VirtualMachine template, List<VMDiskInfo> diskInfos){
    	
    	int key = 1999;
        if (template != null) {
            VirtualDevice[] devices = template.getConfig().getHardware().getDevice();
            if (devices != null && devices.length > 0) {
                for (int i = 0; i < devices.length; i++) {
                    VirtualDevice device = devices[i];
                    if (device instanceof VirtualDisk) {
                    	device = (VirtualDisk)device;
                    	VMDiskInfo diskInfo = new VMDiskInfo();
                    	key = device.getKey();
                    	diskInfo.setDiskId("" + key);
                    	VirtualDeviceBackingInfo backingInfo = device.getBacking();
                    	if(backingInfo instanceof VirtualDiskFlatVer2BackingInfo){
                    		backingInfo = (VirtualDiskFlatVer2BackingInfo)backingInfo;
                    		diskInfo.setDiskMode(((VirtualDiskFlatVer2BackingInfo) backingInfo).getDiskMode());
//                    		diskInfo.setName(((VirtualDiskFlatVer2BackingInfo) backingInfo).getFileName());
                    		diskInfo.setDiskSizeGB(((VirtualDisk) device).getCapacityInKB() / 1048576);
                    	}else{
                    		
                    	}
                    	diskInfos.add(diskInfo);
                    }
                }
            }
        }
        return key;
    }
    
    private static int addTemplateNicInfos(VirtualMachine template, List<VMNicInfo> nicInfos){
    	
    	int key = 3999;
        if (template != null) {
            VirtualDevice[] devices = template.getConfig().getHardware().getDevice();
            if (devices != null && devices.length > 0) {
                for (int i = 0; i < devices.length; i++) {
                    VirtualDevice device = devices[i];
                    if (device instanceof VirtualEthernetCard) {
                    	device = (VirtualEthernetCard)device;
                    	VMNicInfo nicInfo = new VMNicInfo();
                    	key = device.getKey();
                    	nicInfo.setAdapterId("" + key);
                    	VirtualDeviceBackingInfo backingInfo = device.getBacking();
                    	if(backingInfo instanceof VirtualEthernetCardNetworkBackingInfo){
                    		backingInfo = (VirtualEthernetCardNetworkBackingInfo)backingInfo;
                    		nicInfo.setNetworkId(((VirtualEthernetCardNetworkBackingInfo) backingInfo).getNetwork().val);
                    	}else{
                    		
                    	}
                    	nicInfos.add(nicInfo);
                    }
                }
            }
        }
        return key;
    }
    
    /**
     * 配置磁盘
     */
    private VirtualDeviceConfigSpec createAddDiskConfigSpec(VirtualMachine vm, 
    		VMDiskInfo diskInfo, String dataStoreName, long diskSizeKB, int key) {
    	
        VirtualDeviceConfigSpec diskSpec = new VirtualDeviceConfigSpec();
        diskSpec.setOperation(VirtualDeviceConfigSpecOperation.add);
        diskSpec.setFileOperation(VirtualDeviceConfigSpecFileOperation.create);

        VirtualDisk vd = new VirtualDisk();
        vd.setCapacityInKB(diskSizeKB);
        diskSpec.setDevice(vd);
        vd.setKey(key);
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
    public static int getDiskkey(VirtualMachine vm) {
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
    		VMNicInfo nicInfo, int key) throws VirtException {
    	
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
        	network = (Network) MorUtil.createExactManagedEntity(si.getServerConnection(), netMor);
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
        nic.setKey(key);

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

    public static CustomizationSpec createLinuxCustomization(VirtualMachine vm, List<VMNicInfo> networks) {

        // Create customization specs/linux specific options
        CustomizationSpec customSpec = new CustomizationSpec();
        CustomizationLinuxOptions linuxOptions = new CustomizationLinuxOptions();
        customSpec.setOptions(linuxOptions);

        CustomizationLinuxPrep linuxPrep = new CustomizationLinuxPrep();
        linuxPrep.setDomain("example.domain.com");
        linuxPrep.setHwClockUTC(true);
        linuxPrep.setTimeZone("Asia/Shanghai");

        CustomizationFixedName fixedName = new CustomizationFixedName();
        fixedName.setName("linux-clone");
        linuxPrep.setHostName(fixedName);
        customSpec.setIdentity(linuxPrep);

        // Network related settings
        CustomizationGlobalIPSettings globalIPSettings = new CustomizationGlobalIPSettings();
        globalIPSettings.setDnsServerList(new String[] { "8.8.8.8", "8.8.4.4" });
        globalIPSettings.setDnsSuffixList(new String[] { "search.com", "my.search.com" });

        if (ParamValidator.validatorParamsNotEmpty(networks)) {
            CustomizationAdapterMapping[] adapterMappings = new CustomizationAdapterMapping[networks.size()];

            for (int i = 0; i < networks.size(); i++) {
            	VMNicInfo network = networks.get(i);
                CustomizationFixedIp fixedIp = new CustomizationFixedIp();
                String ip = network.getAdapterIP();
                if (ParamValidator.validatorParamsNotEmpty(ip)) {
                    fixedIp.setIpAddress(network.getAdapterIP());
                }
                CustomizationIPSettings customizationIPSettings = new CustomizationIPSettings();
                customizationIPSettings.setIp(fixedIp);
                String gateway = network.getAdapterGateWay();
                if (ParamValidator.validatorParamsNotEmpty(gateway)) {
                    customizationIPSettings.setGateway(new String[] { gateway });
                } else {
                    customizationIPSettings.setGateway(new String[] { "192.168.1.1" });
                }
                String subnetMask = network.getAdapterNetMask();
                if (ParamValidator.validatorParamsNotEmpty(subnetMask)) {
                    customizationIPSettings.setSubnetMask(subnetMask);
                } else {
                    customizationIPSettings.setSubnetMask("255.255.0.0");
                }
                String dnsServer = network.getAdapterDNS();
                if (ParamValidator.validatorParamsNotEmpty(dnsServer)) {
                    customizationIPSettings.setDnsServerList(new String[] { dnsServer });
                }

                CustomizationAdapterMapping adapterMapping = new CustomizationAdapterMapping();
                adapterMapping.setAdapter(customizationIPSettings);
                adapterMappings[i] = adapterMapping;
            }

            customSpec.setNicSettingMap(adapterMappings);
        }

        customSpec.setGlobalIPSettings(globalIPSettings);

        return customSpec;
    }

    public static CustomizationSpec createWindowsCustomization(VirtualMachine vm, List<VMNicInfo> networks) {
        // Windows needs valid product key in order to create fully working
        // clone. Otherwise you will get error message
        // when machine is cloned
    	
    	return null;
//    	//指定的参数不正确: LicenseFilePrintData
//        String productID = "HWRFF-2FFYX-XFXP2-DYFC3-BX3B7";
//
//        // Create customization specs/win specific options
//        // Windows are using SYSPREP for these kind of stuff
//        CustomizationSpec customSpec = new CustomizationSpec();
//        CustomizationWinOptions winOptions = new CustomizationWinOptions();
//
//        winOptions.setChangeSID(true);
//        // We don't want our preconfigured users to be deleted
//        winOptions.setDeleteAccounts(false);
//        customSpec.setOptions(winOptions);
//
//        CustomizationSysprep sprep = new CustomizationSysprep();
//
//        CustomizationGuiUnattended guiUnattended = new CustomizationGuiUnattended();
//        guiUnattended.setAutoLogon(false);
//        guiUnattended.setAutoLogonCount(0);
//        guiUnattended.setTimeZone(4);
//        sprep.setGuiUnattended(guiUnattended);
//
//        CustomizationIdentification custIdent = new CustomizationIdentification();
//        custIdent.setJoinWorkgroup("WORKGROUP");
//        sprep.setIdentification(custIdent);
//
//        CustomizationUserData custUserData = new CustomizationUserData();
//        CustomizationFixedName fixedName = new CustomizationFixedName();
//        fixedName.setName("windows-clone");
//
//        // set from cloned machine
//        custUserData.setProductId(productID); // REQUIRED FOR Windows
//        custUserData.setComputerName(fixedName);
//        custUserData.setFullName("windows-clone.example.com");
//        custUserData.setOrgName("example.com");
//
//        sprep.setUserData(custUserData);
//        customSpec.setIdentity(sprep);
//
//        CustomizationGlobalIPSettings globalIPSettings = new CustomizationGlobalIPSettings();
//        customSpec.setGlobalIPSettings(globalIPSettings);
//
//        if (ParamValidator.validatorParamsNotEmpty(networks)) {
//            CustomizationAdapterMapping[] adapterMappings = new CustomizationAdapterMapping[networks.size()];
//
//            for (int i = 0; i < networks.size(); i++) {
//            	VMNicInfo network = networks.get(i);
//
//                CustomizationIPSettings ipSettings = new CustomizationIPSettings();
//                CustomizationFixedIp fixedIp = new CustomizationFixedIp();
//                if (network.getAdapterIP() != null) {
//                    fixedIp.setIpAddress(network.getAdapterIP());
//                }
//                ipSettings.setIp(fixedIp);
//                String gateway = network.getAdapterGateWay();
//                if (ParamValidator.validatorParamsNotEmpty(gateway)) {
//                    ipSettings.setGateway(new String[] { gateway });
//                }
//                String subnetMask = network.getAdapterNetMask();
//                if (ParamValidator.validatorParamsNotEmpty(subnetMask)) {
//                    ipSettings.setSubnetMask(subnetMask);
//                }
//                String dnsServer = network.getAdapterDNS();
//                if (ParamValidator.validatorParamsNotEmpty(dnsServer)) {
//                    ipSettings.setDnsServerList(new String[] { dnsServer });
//                }
//
//                CustomizationAdapterMapping adapterMapping = new CustomizationAdapterMapping();
//                adapterMapping.setAdapter(ipSettings);
//                adapterMappings[i] = adapterMapping;
//            }
//            customSpec.setNicSettingMap(adapterMappings);
//        }
//
//        return customSpec;
    }

    
    public static String genRandomNum(int pwd_len) {
    	
        final int maxNum = 36;
        int i, count = 0; 
        char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
    		   'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
    		   'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
		while (count < pwd_len) {
			i = Math.abs(r.nextInt(maxNum));
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
        return pwd.toString();
    }

    
	public ServiceInstance getSi() {
		return si;
	}

	public void setSi(ServiceInstance si) {
		this.si = si;
	}
	
    public static void main(String args[]){
    	
    	CreateVMCmd createCmd = new CreateVMCmd();
//    	10.0.36.121上windows2003的模板
    	createCmd.setTemplateId("vm-1378");
//    	10.0.36.121上centos6.5的模板
//    	createCmd.setTemplateId("vm-288");
    	createCmd.setName("yangtest012");
    	createCmd.setPoolId("resgroup-779");
    	createCmd.setDatastoreId("datastore-875");
        
//        VMNicInfo vmNicInfo = new VMNicInfo();
//        vmNicInfo.setNetworkId("network-11");
//        vmNicInfo.setAdapterIP("10.10.11.12");
//        vmNicInfo.setAdapterNetMask("255.255.0.0");
//        vmNicInfo.setAdapterGateWay("10.10.10.10");
//        
//        VMNicInfo vmNicInfo1 = new VMNicInfo();
//        vmNicInfo1.setNetworkId("network-11");
//        vmNicInfo1.setAdapterIP("10.10.11.12");
//        vmNicInfo1.setAdapterNetMask("255.255.0.0");
//        vmNicInfo1.setAdapterGateWay("10.10.10.10");
//        
//        List<VMNicInfo> nicInfos = new LinkedList<VMNicInfo>();
//        nicInfos.add(vmNicInfo);
//        nicInfos.add(vmNicInfo1);
//        createCmd.setNicInfos(nicInfos);
//    	
//        VMDiskInfo diskInfo = new VMDiskInfo();
//        diskInfo.setDiskSizeGB(11L);
//        List<VMDiskInfo> diskInfos = new LinkedList<VMDiskInfo>();
//        diskInfos.add(diskInfo);
//        createCmd.setDiskInfos(diskInfos);
    	
		try {
			CreateVMTask createVMTask = new CreateVMTask(createCmd);
			CreateVMAnswer answer = createVMTask.execute();
			System.out.println(answer);
		} catch (VirtException e) {
			e.printStackTrace();
		}
         
    }
}
