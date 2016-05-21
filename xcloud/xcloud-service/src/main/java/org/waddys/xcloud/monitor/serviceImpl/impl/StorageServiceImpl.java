/**
 * Created on 2016年3月25日
 */
package org.waddys.xcloud.monitor.serviceImpl.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.waddys.xcloud.monitor.service.bo.StorageBo;
import org.waddys.xcloud.monitor.service.exception.CloudViewPerfException;
import org.waddys.xcloud.monitor.service.service.StorageService;
import org.waddys.xcloud.monitor.serviceImpl.entity.Host;
import org.waddys.xcloud.monitor.serviceImpl.entity.Storage;
import org.waddys.xcloud.monitor.serviceImpl.entity.VM;
import org.waddys.xcloud.monitor.serviceImpl.service.HistoryPerfAndAlertServiceI;
import org.waddys.xcloud.monitor.serviceImpl.util.Connection;
import org.waddys.xcloud.monitor.serviceImpl.util.ToolsUtils;
import org.waddys.xcloud.monitor.serviceImpl.util.VCenterManageUtils;

import com.vmware.vim25.DatastoreHostMount;
import com.vmware.vim25.DatastoreSummary;
import com.vmware.vim25.ManagedEntityStatus;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * 功能名: 请填写功能名 功能描述: 请简要描述功能的要点 Copyright: Copyright (c) 2016 公司: 曙光云计算技术有限公司
 *
 * @author wangqian
 * @version 2.0.0 sp1
 */
@Service("monitor-storageServiceImpl")
public class StorageServiceImpl implements StorageService {

    private static final Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class);

    @Qualifier("monitor-connection")
    @Autowired
    private Connection connection;
    String accessble = "accessble";
    String unaccessble = "unaccessble";
    private VCenterManageUtils vCenterManageUtils;

    @Qualifier("monitor-toolsutils")
    @Autowired
    private ToolsUtils toolsUtils;

    @Qualifier("monitor-historyPerfAndAlertServiceImpl")
    @Autowired
    private HistoryPerfAndAlertServiceI alarmUtil;

    @Qualifier("monitor-resourceServiceImpl")
    @Autowired
    private ResourceServiceImpl resourceServiceImpl;

    @Override
    public List<StorageBo> getStorages() {
        List<Datastore> datastores = null;
        try {
            datastores = getVCenterManageUtils().getAllDatastore();
        } catch (CloudViewPerfException e) {
        }
        List<Storage> storages = new ArrayList<Storage>();
        for (Datastore datastore : datastores) {
            try {
				Storage storage = new Storage();
				storage.setId(datastore.getMOR().getVal());
				storage.setName(datastore.getName());

				// 主机数量
				DatastoreHostMount[] hosts = datastore.getHost();
				storage.setHostNum(hosts.length);

				// 虚拟机列表
				int vmNum = 0;
				VirtualMachine[] vms = datastore.getVms();
				if (vms != null && vms.length != 0) {
				    for (VirtualMachine virtualmachine : vms) {
				    	if(virtualmachine.getConfig()==null){
							continue;
						}
				        if (!virtualmachine.getConfig().isTemplate()) {
				            vmNum++;
				        }
				    }
				}
   
				storage.setVmNum(vmNum);

				// 获取已触发告警信息
				storage.setTriggeredAlarm(alarmUtil.getTriggeredAlarms(datastore));

				DatastoreSummary datastoresummary = datastore.getSummary();
				double capacity = (double) datastoresummary.getCapacity() / 1024 / 1024;
				double freeSpace = (double) datastoresummary.getFreeSpace() / 1024 / 1024;
				double usedSpace = capacity - freeSpace;
				storage.setDiskTotal(String.valueOf(capacity));
				storage.setDiskUsed(String.valueOf(usedSpace));

				double diskUsagePercent = 0.0;

				if (Math.abs(capacity - 0) > 0.0001) {
				    BigDecimal diskUsageBd = new BigDecimal((usedSpace / capacity) * 100);
				    diskUsagePercent = diskUsageBd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				storage.setDiskUsage(String.valueOf(diskUsagePercent));

				// Map<String, String> otherInfo =
				// PerfUtils.getStorageOtherPerf(datastore);
				// long diskIOPS = Long.valueOf(ToolsUtils.GetMapValue(otherInfo,
				// PerfConstants.DISK_IOPS_READ_ID))
				// + Long.valueOf(ToolsUtils.GetMapValue(otherInfo,
				// PerfConstants.DISK_IOPS_WRITE_ID));
				// storage.setDiskIops(String.valueOf(diskIOPS));

				ManagedEntityStatus mEnStatus = datastore.getOverallStatus();
				// 设置电源状态
				if(datastore.getSummary()!=null){
				    boolean status = datastore.getSummary().isAccessible();
				    if (status) {
				        storage.setPowerStatus("on");
				    } else {
				        storage.setPowerStatus("off");
				    }
				}

				// 设置主机运行状态，是否正常
				storage.setStatus(ToolsUtils.convertManageObjectStatusToString(mEnStatus));
				storages.add(storage);
			} catch (Exception e) {
			}
        }

        List<StorageBo> storageBos = new ArrayList<StorageBo>();
        for (Storage storage : storages) {
            try {
				StorageBo storageBo = new StorageBo();
				toolsUtils.convert(storage, storageBo);
				double storeTBTotal = toolsUtils.StoreMBToTB(Double.valueOf(storageBo.getDiskTotal()));
				double storeTBUsed = toolsUtils.StoreMBToTB(Double.valueOf(storageBo.getDiskUsed()));
				storageBo.setStoreTBTotal(storeTBTotal);
				storageBo.setStoreTBUsed(storeTBUsed);
				storageBos.add(storageBo);
			} catch (Exception e) {
			}
        }
        return storageBos;
    }

    @Override
    public StorageBo getStorageById(String storageId) {
        Datastore datastore = null;
        try {
            datastore = getVCenterManageUtils().getDatastoreById(storageId);
        } catch (CloudViewPerfException e) {
            e.printStackTrace();
        }
        Storage storage = new Storage();
        storage.setId(datastore.getMOR().getVal());
        storage.setName(datastore.getName());
        storage.setStorageType(datastore.getSummary().getType());

        DatastoreHostMount[] hosts = datastore.getHost();

        if (null != hosts && hosts.length > 0) {
            List<Host> hostList = new ArrayList<Host>();
            HostSystem[] hostSystems = new HostSystem[hosts.length];
            for (int i = 0; i < hosts.length; i++) {
                String hostId = hosts[i].getKey().getVal();
                HostSystem hostSystem = null;
                try {
                    hostSystem = getVCenterManageUtils().getHostSystemById(hostId);
                } catch (CloudViewPerfException e) {
                    e.printStackTrace();
                }
                if (hostSystem != null) {
                    Host host = resourceServiceImpl.getHostBasicInfo(hostSystem, datastore);
                    hostList.add(host);
                    hostSystems[i] = hostSystem;
                }
                storage.setHostList(hostList);
                Map<String, Integer> hostMap = resourceServiceImpl.getHostStatusInfo(hostSystems);
                storage.setHostNum(hosts.length);
                storage.setHostAccessibleNum(hostMap.get(accessble));
                storage.setHostUnaccessibleNum(hostMap.get(unaccessble));
            }
        }
        // // 主机列表
        // try {
        // HostSystem[] hosts = datastore.getHostsystem();
        // List<Host> hostList = new ArrayList<Host>();
        // if (null != hosts && hosts.length > 0) {
        // for (HostSystem hostSystem : hosts) {
        // Host host = resourceServiceImpl.getHostBasicInfo(hostSystem);
        // hostList.add(host);
        // }
        // }
        // storage.setHostList(hostList);
        // Map<String, Integer> hostMap =
        // resourceServiceImpl.getHostStatusInfo(hosts);
        // storage.setHostNum(hosts.length);
        // storage.setHostAccessibleNum(hostMap.get(accessble));
        // storage.setHostUnaccessibleNum(hostMap.get(unaccessble));
        // } catch (Exception e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }

        // // 虚拟机列表
        // try {
        // VirtualMachine[] vms = datastore.getVms();
        // List<VM> vmList = new ArrayList<VM>();
        // if (null != vms && vms.length > 0) {
        // for (VirtualMachine virtualMachine : vms) {
        // // 虚拟机关联主机
        // ManagedObjectReference mor = new ManagedObjectReference();
        // mor.setType("HostSystem");
        // mor.setVal(virtualMachine.getRuntime().getHost().getVal());
        // HostSystem hostSystem = new
        // HostSystem(connection.getSi().getServerConnection(), mor);
        //
        // VM vm = resourceServiceImpl.getVMBasicInfo(virtualMachine,
        // hostSystem);
        // vmList.add(vm);
        // }
        // }
        // storage.setVmList(vmList);
        // Map<String, Integer> vmMap =
        // resourceServiceImpl.getVmStatusInfo(vms);
        // storage.setVmNum(vms.length);
        // storage.setVmAccessibleNum(vmMap.get(accessble));
        // storage.setVmUnaccessibleNum(vmMap.get(unaccessble));
        //
        // } catch (Exception e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // 虚拟机列表
        try {
            VirtualMachine[] vms = datastore.getVms();
            List<VM> vmList = new ArrayList<VM>();
            if (null != vms && vms.length > 0) {
                for (VirtualMachine virtualMachine : vms) {
                    VM vm = resourceServiceImpl.getVMBasicInfo(virtualMachine, datastore);
                    vmList.add(vm);
                }
            }
            storage.setVmList(vmList);
            Map<String, Integer> vmMap = resourceServiceImpl.getVmStatusInfo(vms);
            storage.setVmNum(vms.length);
            storage.setVmAccessibleNum(vmMap.get(accessble));
            storage.setVmUnaccessibleNum(vmMap.get(unaccessble));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取已触发告警信息
        storage.setTriggeredAlarm(alarmUtil.getTriggeredAlarms(datastore));

        DatastoreSummary datastoresummary = datastore.getSummary();
        double capacity = (double) datastoresummary.getCapacity() / 1024 / 1024;
        double freeSpace = (double) datastoresummary.getFreeSpace() / 1024 / 1024;
        double usedSpace = capacity - freeSpace;
        storage.setDiskTotal(String.valueOf(capacity));
        storage.setDiskUsed(String.valueOf(usedSpace));

        double diskUsagePercent = 0.0;
        if (Math.abs(capacity - 0) > 0.0001) {
            BigDecimal diskUsageBd = new BigDecimal((usedSpace / capacity) * 100);
            diskUsagePercent = diskUsageBd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        storage.setDiskUsage(String.valueOf(diskUsagePercent));

        // Map<String, String> otherInfo =
        // PerfUtils.getStorageOtherPerf(datastore);
        // long diskIOPS = Long.valueOf(ToolsUtils.GetMapValue(otherInfo,
        // PerfConstants.DISK_IOPS_READ_ID))
        // + Long.valueOf(ToolsUtils.GetMapValue(otherInfo,
        // PerfConstants.DISK_IOPS_WRITE_ID));
        // storage.setDiskIops(String.valueOf(diskIOPS));

        // powerStatus=datastore
        ManagedEntityStatus mEnStatus = datastore.getOverallStatus();
        // // 设置电源状态
        // storage.setPowerStatus(ToolsUtils.convertHostPowerStatusToString(powerStatus));

        // 设置主机运行状态，是否正常
        storage.setStatus(ToolsUtils.convertManageObjectStatusToString(mEnStatus));

        StorageBo storageBo = new StorageBo();
        toolsUtils.convert(storage, storageBo);
        double storeTBTotal = toolsUtils.StoreMBToTB(Double.valueOf(storageBo.getDiskTotal()));
        double storeTBUsed = toolsUtils.StoreMBToTB(Double.valueOf(storageBo.getDiskUsed()));
        storageBo.setStoreTBTotal(storeTBTotal);
        storageBo.setStoreTBUsed(storeTBUsed);
        return storageBo;
    }

    @Override
    public List<StorageBo> getStorageByClusterId(String clusterId) {
        List<Datastore> datastores = null;
        List<String> datastoreIdList = new ArrayList<String>();
        try {
            datastores = getVCenterManageUtils().getAllDatastore(clusterId);
            for (Datastore datastore : datastores) {
                datastoreIdList.add(datastore.getMOR().getVal());
            }
        } catch (CloudViewPerfException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        List<StorageBo> storages = new ArrayList<StorageBo>();
        if (datastores == null) {
            return storages;
        }
        
        for (String datastoreId : datastoreIdList) {
            StorageBo storageBo = getStorageById(datastoreId);
            storages.add(storageBo);
        }
        return storages;
    }

    /**
     * @return the opUtil
     */
    public VCenterManageUtils getVCenterManageUtils() {
        this.vCenterManageUtils = new VCenterManageUtils(this.connection);
        return vCenterManageUtils;
    }
}
