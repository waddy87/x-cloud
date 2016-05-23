package org.waddys.xcloud.monitor.serviceImpl.impl;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.waddys.xcloud.monitor.bo.ComputeSystemBo;
import org.waddys.xcloud.monitor.bo.StorageBo;
import org.waddys.xcloud.monitor.bo.VMBo;
import org.waddys.xcloud.monitor.service.service.AbstractService;
import org.waddys.xcloud.monitor.service.service.ClusterService;
import org.waddys.xcloud.monitor.service.service.HostService;
import org.waddys.xcloud.monitor.service.service.StorageService;
import org.waddys.xcloud.monitor.service.service.VMService;
import org.waddys.xcloud.monitor.serviceImpl.entity.ClusterUI;
import org.waddys.xcloud.monitor.serviceImpl.service.ServiceI;
import org.waddys.xcloud.monitor.serviceImpl.util.PerfConstants;
import org.waddys.xcloud.monitor.serviceImpl.util.ToolsUtils;

@Service("monitor-abstractServiceImpl")
public class AbstractServiceImpl implements AbstractService {
    private static final Logger logger = LoggerFactory.getLogger(AbstractServiceImpl.class);

    @Qualifier("monitor-hostServiceImpl")
    @Autowired
    private HostService hostService;

    @Qualifier("monitor-storageServiceImpl")
    @Autowired
    private StorageService storageService;

    @Qualifier("monitor-clusterServiceImpl")
    @Autowired
    private ClusterService clusterService;

    @Qualifier("monitor-vmServiceImpl")
    @Autowired
    private VMService vMService;

    @Qualifier("monitor-toolsutils")
    @Autowired
    ToolsUtils toolsUtils;
    
    @Qualifier("monitor-serviceImpl")
    @Autowired
    ServiceI service;

    @Override
    public JSONObject getAllHostsPerformInfo() {
      /*  JSONObject result = new JSONObject();
        int powerOnStatus = 0;
        int total = 0;
        int statusOk = 0;
        double cpuMHZTotal = 0;
        double cpuMHZUsed = 0;
        double memoryTotal = 0;
        double memoryUsed = 0;
        double diskTotal = 0;
        double diskUsed = 0;
        double diskIOSpeed = 0;
        double networkTransmitSpeed = 0;
        Map<String, Object> topNBo = null;

        try {
            List<HostBo> hosts = this.hostService.getHosts();
            for (HostBo host : hosts) {
                total++;
                if (PerfConstants.OBJECT_POWER_STATUS_ON.equals(host.getPowerStatus())) {
                    powerOnStatus++;
                }
                if (PerfConstants.OBJECT_STATUS_GREEN.equals(host.getStatus())) {
                    statusOk++;
                }
                cpuMHZTotal = cpuMHZTotal + Double.valueOf(host.getCpuMHZTotal());
                cpuMHZUsed = cpuMHZUsed + Double.valueOf(host.getCpuMHZUsed());
                memoryTotal = memoryTotal + Double.valueOf(host.getMemoryTotal());
                memoryUsed = memoryUsed + Double.valueOf(host.getMemoryUsed());
                diskTotal = diskTotal + Double.valueOf(host.getDiskTotal());
                diskUsed = diskUsed + Double.valueOf(host.getDiskUsed());
                diskIOSpeed = diskIOSpeed + Double.valueOf(host.getDiskIOSpeed());
                networkTransmitSpeed = networkTransmitSpeed + Double.valueOf(host.getNetworkTransmitSpeed());
            }
            String[] sortType = { PerfConstants.CPU_USAGE_ID, PerfConstants.DISK_USAGE_ID,
                    PerfConstants.DISK_IO_SPEED_ID, PerfConstants.NET_IO_SPEED_ID };
            topNBo = this.topN(hosts, sortType);
            result = getDevAbstractInfo(powerOnStatus, total, statusOk, cpuMHZTotal, cpuMHZUsed, memoryTotal,
                    memoryUsed, diskTotal, diskUsed, diskIOSpeed, networkTransmitSpeed, topNBo, "hostInfo");

        } catch (NumberFormatException nfe) {
            logger.error("getAllHostsPerformInfo:" + nfe);
        } catch (Exception e) {
            logger.error("getAllHostsPerformInfo:" + e);
        }
        return result;*/
    	
    	
    	JSONObject retJson = null;
    	ClusterUI cu = null;

    	try {
			cu = this.service.getAllClusterUIInfo();
		} catch (Exception e) {
			logger.error("获取概要数据失败"+e);
		}

    	try {
			retJson = this.service.getClusterInfo(cu);
		} catch (Exception e) {
			logger.error("获取概要数据失败"+e);
		}
     
		return retJson;
    }

    @Override
    public JSONObject getAllStoragePerformInfo() {
        JSONObject result = new JSONObject();
        int powerOnStatus = 0;
        int total = 0;
        int statusOk = 0;
        double cpuMHZTotal = 0;
        double cpuMHZUsed = 0;
        double memoryTotal = 0;
        double memoryUsed = 0;
        double diskTotal = 0;
        double diskUsed = 0;
        double diskIOSpeed = 0;
        double networkTransmitSpeed = 0;
        Map<String, Object> topNBo = null;

        try {
            List<StorageBo> storages = this.storageService.getStorages();
            for (StorageBo storage : storages) {
                System.out.println("getAllstorageInfo:" + storage.getStatus() + "--" + storage.getCpuMHZTotal() + "--"
                        + storage.getCpuMHZUsed() + "--" + storage.getMemoryTotal() + "--" + storage.getMemoryUsed()
                        + "--" + storage.getDiskTotal() + "--" + storage.getDiskUsed() + "--" + storage.getDiskIOSpeed()
                        + "--" + storage.getNetworkTransmitSpeed());
                total++;
                if (PerfConstants.OBJECT_POWER_STATUS_ON.equals(storage.getPowerStatus())) {
                    powerOnStatus++;
                }
                if (PerfConstants.OBJECT_STATUS_GREEN.equals(storage.getStatus())) {
                    statusOk++;
                }
                if (storage.getDiskTotal() != null) {
                    diskTotal = diskTotal + Double.valueOf(storage.getDiskTotal());
                }
                if (storage.getDiskUsed() != null) {
                    diskUsed = diskUsed + Double.valueOf(storage.getDiskUsed());
                }
            }
            result = getDevAbstractInfo(powerOnStatus, total, statusOk, cpuMHZTotal, cpuMHZUsed, memoryTotal,
                    memoryUsed, diskTotal, diskUsed, diskIOSpeed, networkTransmitSpeed, topNBo, "storageInfo");

        } catch (NumberFormatException nfe) {
            logger.error("getAllStorageInfo:" + nfe);
        } catch (Exception e) {
            logger.error("getAllStorageInfo" + e);
        }
        return result;
    }

    @Override
    public JSONObject getAllVMPerformInfo() {
        JSONObject result = new JSONObject();
        int powerOnStatus = 0;
        int total = 0;
        int statusOk = 0;
        double cpuMHZTotal = 0;
        double cpuMHZUsed = 0;
        double memoryTotal = 0;
        double memoryUsed = 0;
        double diskTotal = 0;
        double diskUsed = 0;
        double diskIOSpeed = 0;
        double networkTransmitSpeed = 0;
        Map<String, Object> topNBo = null;
        try {
            List<VMBo> vMBos = this.vMService.getAllVMs();
            for (VMBo vMBo : vMBos) {
                // System.out.println("getAllVMPerformInfo" +
                // vMBo.getCpuMHZTotal() + "--" + vMBo.getCpuMHZUsed() + "--"
                // + vMBo.getMemoryTotal() + "--" + vMBo.getMemoryUsed() + "" +
                // vMBo.getDiskTotal() + "--"
                // + vMBo.getDiskUsed() + "--" + vMBo.getDiskIOSpeed() + "--" +
                // vMBo.getNetworkTransmitSpeed());
                total++;
                if (PerfConstants.OBJECT_POWER_STATUS_ON.equals(vMBo.getPowerStatus())) {
                    powerOnStatus++;
                }
                if (PerfConstants.OBJECT_STATUS_GREEN.equals(vMBo.getStatus())) {
                    statusOk++;
                }
                cpuMHZTotal = cpuMHZTotal + Double.valueOf(vMBo.getCpuMHZTotal());
                cpuMHZUsed = cpuMHZUsed + Double.valueOf(vMBo.getCpuMHZUsed());
                memoryTotal = memoryTotal + Double.valueOf(vMBo.getMemoryTotal());
                memoryUsed = memoryUsed + Double.valueOf(vMBo.getMemoryUsed());
                diskTotal = diskTotal + Double.valueOf(vMBo.getDiskTotal());
                diskUsed = diskUsed + Double.valueOf(vMBo.getDiskUsed());
                diskIOSpeed = diskIOSpeed + Double.valueOf(vMBo.getDiskIOSpeed());
                networkTransmitSpeed = networkTransmitSpeed + Double.valueOf(vMBo.getNetworkTransmitSpeed());

            }
            result = getDevAbstractInfo(powerOnStatus, total, statusOk, cpuMHZTotal, cpuMHZUsed, memoryTotal,
                    memoryUsed, diskTotal, diskUsed, diskIOSpeed, networkTransmitSpeed, topNBo, "vmInfo");

        } catch (NumberFormatException nfe) {
            logger.error("vmInfo:" + nfe);
        } catch (Exception e) {
            logger.error("vmInfo" + e);
        }
        return result;
    }

    @Override
    public JSONObject getAllClusterPerformInfo() {
        JSONObject result = new JSONObject();
        int powerOnStatus = 0;
        int total = 0;
        int statusOk = 0;
        double cpuMHZTotal = 0;
        double cpuMHZUsed = 0;
        double memoryTotal = 0;
        double memoryUsed = 0;
        double diskTotal = 0;
        double diskUsed = 0;
        double diskIOSpeed = 0;
        double networkTransmitSpeed = 0;
        Map<String, Object> topNBo = null;
        try {
            // List<StorageBo> storages = this.clusterService.getClusterId());
            // for (StorageBo storage : storages) {
            // cpuMHZTotal = cpuMHZTotal +
            // Double.valueOf(storage.getCpuMHZTotal());
            // cpuMHZUsed = cpuMHZUsed +
            // Double.valueOf(storage.getCpuMHZUsed());
            // memoryTotal = memoryTotal +
            // Double.valueOf(storage.getMemoryTotal());
            // memoryUsed = memoryUsed +
            // Double.valueOf(storage.getMemoryUsed());
            // diskTotal = diskTotal + Double.valueOf(storage.getDiskTotal());
            // diskUsed = diskUsed + Double.valueOf(storage.getDiskUsed());
            // diskIOSpeed = diskIOSpeed +
            // Double.valueOf(storage.getDiskIOSpeed());
            // networkTransmitSpeed = networkTransmitSpeed +
            // Double.valueOf(storage.getNetworkTransmitSpeed());
            // }
            result = getDevAbstractInfo(powerOnStatus, total, statusOk, cpuMHZTotal, cpuMHZUsed, memoryTotal,
                    memoryUsed, diskTotal, diskUsed, diskIOSpeed, networkTransmitSpeed, topNBo, "storageInfo");

        } catch (NumberFormatException nfe) {
            logger.error("getAllClusterPerformInfo:" + nfe);
        } catch (Exception e) {
            logger.error("getAllClusterPerformInfo" + e);
        }
        return result;
    }

    public Map<String, Object> topN(List<?> computeSystemBos, String[] type) {
        int topn = 3;
        Map<String, Object> typeMap = new HashMap<String, Object>();
        for (int i = 0; i < type.length; i++) {
            String typeName = type[i];
            typeName = typeName.replace(".", "");
            System.out.println("topn name:" + typeName);
            typeMap.put(typeName, this.toolsUtils.getResourceMetricTopN(computeSystemBos, topn, type[i]));
        }
        return typeMap;
    }

    public JSONObject getDevAbstractInfo(int powerOnStatus, int total, int statusOk, double cpuMHZTotal,
            double cpuMHZUsed, double memoryTotal, double memoryUsed, double diskTotal, double diskUsed,
            double diskIOSpeed, double networkTransmitSpeed, Map<String, Object> topN, String jsonKey) {
        JSONObject result = new JSONObject();
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        ComputeSystemBo hostSumResult = new ComputeSystemBo();
        try {
            // cpu info
            hostSumResult.setCpuMHZTotal(decimalFormat.format(cpuMHZTotal));
            hostSumResult.setCpuMHZUsed(decimalFormat.format(cpuMHZUsed));
            if (cpuMHZTotal != 0) {
                hostSumResult.setCpuUsage(decimalFormat.format(cpuMHZUsed / cpuMHZTotal * 100));
            } else {
                hostSumResult.setCpuUsage("0.00");
            }
            // memory info
            hostSumResult.setMemoryTotal(decimalFormat.format(memoryTotal));
            hostSumResult.setMemoryUsed(decimalFormat.format(memoryUsed));
            if (memoryTotal != 0) {
                hostSumResult.setMemoryUsage(decimalFormat.format(memoryUsed / memoryTotal * 100));
            } else {
                hostSumResult.setMemoryUsage("0.00");
            }
            // disk
            hostSumResult.setDiskTotal(decimalFormat.format(diskTotal));
            hostSumResult.setDiskUsed(decimalFormat.format(diskUsed));
            if (diskTotal != 0) {
                hostSumResult.setDiskUsage(decimalFormat.format(diskUsed / diskTotal * 100));
            } else {
                hostSumResult.setDiskUsage("0.00");
            }
            // diskIO
            hostSumResult.setDiskIOSpeed(decimalFormat.format(diskIOSpeed));
            // networkIO
            hostSumResult.setNetworkTransmitSpeed(decimalFormat.format(networkTransmitSpeed));
            Map<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("powerOnStatus", powerOnStatus);
            jsonMap.put("total", total);
            jsonMap.put("statusOk", statusOk);
            jsonMap.put("cpuTotal",
                    this.toolsUtils.CpuMHzToGHz(Double.valueOf(hostSumResult.getCpuMHZTotal()).longValue()) + "GHz");
            jsonMap.put("cpuUsed",
                    this.toolsUtils.CpuMHzToGHz(Double.valueOf(hostSumResult.getCpuMHZUsed()).longValue()) + "GHz");
            jsonMap.put("cpuUsage", hostSumResult.getCpuUsage() + "%");
            jsonMap.put("cpuFree",
                    this.toolsUtils.CpuMHzToGHz(Double.valueOf(hostSumResult.getCpuMHZTotal()).longValue()
                            - Double.valueOf(hostSumResult.getCpuMHZUsed()).longValue()) + "GHz");

            jsonMap.put("memorTotal",
                    this.toolsUtils.MemMBToGB(Double.valueOf(hostSumResult.getMemoryTotal()).longValue()) + "GB");
            jsonMap.put("memoryUsed",
                    this.toolsUtils.MemMBToGB(Double.valueOf(hostSumResult.getMemoryUsed()).longValue()) + "GB");
            jsonMap.put("memoryUsage", hostSumResult.getMemoryUsage() + "%");
            jsonMap.put("memoryFree",
                    this.toolsUtils.MemMBToGB(Double.valueOf(hostSumResult.getMemoryTotal()).longValue()
                            - Double.valueOf(hostSumResult.getMemoryUsed()).longValue()) + "GB");

            jsonMap.put("diskTotal", this.toolsUtils.StoreMBToTB(Double.valueOf(hostSumResult.getDiskTotal())) + "TB");
            jsonMap.put("diskUsed", this.toolsUtils.StoreMBToTB(Double.valueOf(hostSumResult.getDiskUsed())) + "TB");
            jsonMap.put("diskUsage", hostSumResult.getDiskUsage() + "%");
            jsonMap.put("diskFree",
                    this.toolsUtils.StoreMBToTB(
                            Double.valueOf(hostSumResult.getDiskTotal()) - Double.valueOf(hostSumResult.getDiskUsed()))
                    + "TB");

            jsonMap.put("diskIOSpeed", hostSumResult.getDiskIOSpeed());
            jsonMap.put("networkTransmitSpeed", hostSumResult.getNetworkTransmitSpeed());

            jsonMap.put("topN", topN);

            JSONObject jsonResult = JSONObject.fromObject(jsonMap);
            result.put(jsonKey, jsonResult);
        } catch (NumberFormatException nfe) {
            logger.error("getDevAbstractInfo:" + nfe);
        } catch (Exception e) {
            logger.error("getDevAbstractInfo:" + e);
        }
        return result;

    }

}
