package org.waddys.xcloud.monitor.serviceImpl.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.waddys.xcloud.monitor.bo.AlarmEntity;
import org.waddys.xcloud.monitor.bo.AlertEvent;
import org.waddys.xcloud.monitor.bo.MetricValue;
import org.waddys.xcloud.monitor.service.exception.CloudViewPerfException;
import org.waddys.xcloud.monitor.serviceImpl.common.SystemResourceType;
import org.waddys.xcloud.monitor.serviceImpl.entity.Cluster;
import org.waddys.xcloud.monitor.serviceImpl.entity.ClusterUI;
import org.waddys.xcloud.monitor.serviceImpl.entity.DataCenter;
import org.waddys.xcloud.monitor.serviceImpl.entity.Host;
import org.waddys.xcloud.monitor.serviceImpl.entity.VM;
import org.waddys.xcloud.monitor.serviceImpl.service.HistoryPerfAndAlertServiceI;
import org.waddys.xcloud.monitor.serviceImpl.service.HostAndVmPerfOp;
import org.waddys.xcloud.monitor.serviceImpl.service.ResourceServiceI;
import org.waddys.xcloud.monitor.serviceImpl.service.ServiceI;
import org.waddys.xcloud.monitor.serviceImpl.util.Connection;
import org.waddys.xcloud.monitor.serviceImpl.util.PerfConstants;
import org.waddys.xcloud.monitor.serviceImpl.util.PerfUtils;
import org.waddys.xcloud.monitor.serviceImpl.util.ToolsUtils;
import org.waddys.xcloud.monitor.serviceImpl.util.VCenterManageUtils;

import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

@Service("monitor-serviceImpl")
public class ServiceImpl implements ServiceI {

    private ServiceInstance serviceInstance;

/*    // 登录服务
    @Qualifier("monitor-loginServiceImpl")
    @Autowired
    public LoginServiceI loginService;*/

    // 物理机和虚拟机性能服务
    @Qualifier("monitor-hostAndVmPerfOpImpl")
    @Autowired
    private HostAndVmPerfOp hostAndVmOp;

    // 物理机和虚拟机配置信息服务
    @Qualifier("monitor-resourceServiceImpl")
    @Autowired
    private ResourceServiceI resourceService;

    // 获取24小时性能视图和事件服务
    @Qualifier("monitor-historyPerfAndAlertServiceImpl")
    @Autowired
    private HistoryPerfAndAlertServiceI historyPerfAndAlertService;

    // 获取connection对象
    @Qualifier("monitor-connection")
    @Autowired
    private Connection con;

    @Override
    public void setServiceInstance(Connection conn) {
        // 设置serviceInstance
    }

 /*   @Override
    public String login(String userName, String password, boolean isSSO) {
        // TODO Auto-generated method stub
        String result = loginService.login(userName, password, isSSO);
        return result;
    }*/

    @Override
    public VM getVmInfo(String name) {
        return resourceService.getVmInfo(name);

    }

    @Override
    public Host getHostInfo(String name) {
        return resourceService.getHostInfo(name);
    }

    @Override
    public VM getVmInfoEx(String vmId) {
        // TODO Auto-generated method stub
        return resourceService.getVmInfoEx(vmId);
    }

    @Override
    public Host getHostInfoEx(String hostId) {
        // TODO Auto-generated method stub
        return resourceService.getHostInfoEx(hostId);
    }

    @Override
    public Cluster getClusterInfo(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClusterUI getClusterUIInfo(String name) {
        Cluster cluster = this.resourceService.getClusterInfo(name);
        ClusterUI ui = new ClusterUI();
        DecimalFormat df = new DecimalFormat("#0.00");

        ui.setId(cluster.getId());
        ui.setName(name);

        // CPU整体视图
        ui.setCpuTotalGHz(df.format(cluster.getCpuGHz()) + "");
        ui.setCpuUsedGHz(df.format(cluster.getCpuUsedGHz()) + "");
        ui.setCpuFreeGHz(df.format(cluster.getCpuGHz() - cluster.getCpuUsedGHz()) + "");
        ui.setCpuUsage(cluster.getCpuUsage());

        // 内存整体视图
        ui.setMemTotalGB(df.format(cluster.getMemGB()) + "");
        ui.setMemUsedGB(df.format(cluster.getMemUsedGB()) + "");
        ui.setMemFreeGB(df.format(cluster.getMemGB() - cluster.getMemUsedGB()) + "");
        ui.setMemUsage(cluster.getMemoryUsage());

        // 存储整体视图
        ui.setStoreTotalTB(df.format(cluster.getStoreTB()) + "");
        ui.setStoreUsedTB(df.format(cluster.getStoreUsedTB()) + "");
        ui.setStoreFreeTB(df.format(cluster.getStoreTB() - cluster.getStoreUsedTB()) + "");
        ui.setStoreUsage(cluster.getDiskUsage());

        // 状态信息
        ui.setHostNum(Integer.toString(cluster.getHostNumber()));
        ui.setHostAccessibleNum(Integer.toString(cluster.getHostAccessibleNum()));
        ui.setHostUnaccessibleNum(Integer.toString(cluster.getHostUnaccessibleNum()));

        ui.setVmNum(Integer.toString(cluster.getVmNum()));
        ui.setVmAccessibleNum(Integer.toString(cluster.getVmAccessibleNum()));
        ui.setVmUnaccessibleNum(Integer.toString(cluster.getVmUnaccessibleNum()));

        ui.setStoreNum(Integer.toString(cluster.getStoreNum()));
        ui.setStoreAccessibleNum(Integer.toString(cluster.getStoreAccessibleNum()));
        ui.setStoreUnaccessibleNum(Integer.toString(cluster.getStoreUnaccessibleNum()));

        // 主机TOP3左侧的平均值
        List<Host> hosts = cluster.getHostList();
        if (null != hosts && hosts.size() > 0) {
            // CPU
            // List<Host> hostCPUList =
            // this.hostAndVmOp.getHostCpuUsageTopN(hosts, hosts
            // .size());
            // double hostCpuTotal = 0;
            // for (Host host : hostCPUList) {
            // hostCpuTotal += Double.parseDouble(host.getCpuUsage());
            // }

            ui.setAvgHostCpuUsage(df.format(Double.parseDouble(cluster.getCpuUsage()) * 100));

            // MEM
            // double hostMemTotal = 0;
            // List<Host> hostMemList =
            // this.hostAndVmOp.getHostMemUsageTopN(hosts, hosts
            // .size());
            // for (Host host : hostMemList) {
            // hostMemTotal += Double.parseDouble(host.getMemoryUsage());
            // }
            ui.setAvgHostMemUsage(df.format(Double.parseDouble(cluster.getMemoryUsage()) * 100));

            // DISK IO
            double hostDiskIOTotal = 0;
            List<Host> hostDiskIOList = this.hostAndVmOp.getHostDiskIoTopN(hosts, hosts.size());
            for (Host host : hostDiskIOList) {
                hostDiskIOTotal += Double.parseDouble(host.getDiskIOSpeed());
            }
            ui.setAvgHostDiskIO((int) (hostDiskIOTotal / hostDiskIOList.size()) + "");

            // NIC SPEED
            double hostNicIOTotal = 0;
            List<Host> hostNicIOList = this.hostAndVmOp.getHostNetIoTopN(hosts, hosts.size());
            for (Host host : hostNicIOList) {
                hostNicIOTotal += Double.parseDouble(host.getNetworkTransmitSpeed());
            }
            ui.setAvgHostNetIO((int) (hostNicIOTotal / hostNicIOList.size()) + "");
        } else {
            ui.setAvgHostCpuUsage("0");
            ui.setAvgHostMemUsage("0");
            ui.setAvgHostDiskIO("0");
            ui.setAvgHostNetIO("0");
        }

        // 主机TOP3右侧
        ui.setTop3HostCpuUsage(this.hostAndVmOp.getHostCpuUsageTopN(hosts, 3));
        ui.setTop3HostMemUsage(this.hostAndVmOp.getHostMemUsageTopN(hosts, 3));
        ui.setTop3HostDiskIO(this.hostAndVmOp.getHostDiskIoTopN(hosts, 3));
        ui.setTop3HostNetIO(this.hostAndVmOp.getHostNetIoTopN(hosts, 3));

        // 虚拟机TOP3左侧的平均值
        List<VM> vms = cluster.getVmList();
        if (null != vms && vms.size() > 0) {
            // CPU
            double vmCPUUsage = 0;
            List<VM> vmCPUList = this.hostAndVmOp.getVmCpuUsageTopN(vms, vms.size());
            for (VM vm : vmCPUList) {
                vmCPUUsage += Double.parseDouble(vm.getCpuUsage());
            }
            ui.setAvgVMCpuUsage(df.format(vmCPUUsage / vms.size()) + "");

            // MEM
            double vmMemUsage = 0;
            List<VM> vmMemList = this.hostAndVmOp.getVmMemUsageTopN(vms, vms.size());
            for (VM vm : vmMemList) {
                vmMemUsage += Double.parseDouble(vm.getMemoryUsage());
            }
            ui.setAvgVMMemUsage(df.format(vmMemUsage / vms.size()) + "");

            // DISK IO
            double vmDiskIO = 0;
            List<VM> vmDiskList = this.hostAndVmOp.getVmDiskIoTopN(vms, vms.size());
            for (VM vm : vmDiskList) {
                vmDiskIO += Double.parseDouble(vm.getDiskIOSpeed());
            }
            ui.setAvgVMDiskIO((int) (vmDiskIO / vms.size()) + "");

            // NIC SPEED
            double vmNicSpeed = 0;
            List<VM> vmNicList = this.hostAndVmOp.getVmNetIoTopN(vms, vms.size());
            for (VM vm : vmNicList) {
                vmNicSpeed += Double.parseDouble(vm.getNetworkTransmitSpeed());
            }
            ui.setAvgVMNetIO((int) (vmNicSpeed / vms.size()) + "");
        } else {
            ui.setAvgVMCpuUsage("0");
            ui.setAvgVMDiskIO("0");
            ui.setAvgVMMemUsage("0");
            ui.setAvgVMNetIO("0");
        }

        // 虚拟机TOP3
        ui.setTop3VMCpuUsage(this.hostAndVmOp.getVmCpuUsageTopN(vms, 3));
        ui.setTop3VMDiskIO(this.hostAndVmOp.getVmDiskIoTopN(vms, 3));
        ui.setTop3VMMemUsage(this.hostAndVmOp.getVmMemUsageTopN(vms, 3));
        ui.setTop3VMNetIO(this.hostAndVmOp.getVmNetIoTopN(vms, 3));

        // 设置告警，最近20条告警
        ui.setAlarmList(PerfUtils.filterAlarm(this.resourceService.getClusterAlarm(cluster), 20));

        return ui;
    }

    @Override
    public List<Cluster> getClustersList() {
        // TODO Auto-generated method stub
        return this.resourceService.getSimpleClusterList();
    }

    @Override
    public DataCenter getDataCenterInfo() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<AlertEvent> getAlertEventByType(String Type, String name, int number) {
        return historyPerfAndAlertService.getAlertEventByType(Type, name, number);
    }

    @Override
    public Map<String, List<MetricValue>> get24HPerformData(String id, String Type) {

        return historyPerfAndAlertService.get24HPerformData(id, Type);
    }

    @Override
    public Map<String, Map<String, String>> getHostCommonTopN(int n, String clusterId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Map<String, String>> getHostOtherTopN(int n, String clusterId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Map<String, String>> getVmCommonTopN(int n, String clusterId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Map<String, String>> getVmOtherTopN(int n, String clusterId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Map<String, String>> getVmCommonTopNOnHost(int n, String hostId) {

        Map<String, Map<String, String>> metricMap = new HashMap<String, Map<String, String>>();
        List<VM> vms = hostAndVmOp.getVmsOnHost(hostId);
        int i = 0;
        List<VM> vmList = hostAndVmOp.getVmCpuUsageTopN(vms, n);

        // 这是cpu使用率
        List<VM> cpuUsageTopN = hostAndVmOp.getVmCpuUsageTopN(vms, n);
        metricMap.put(PerfConstants.CPU_USAGE_ID,
                PerfUtils.createVmMetricValueMap(cpuUsageTopN, PerfConstants.CPU_USAGE_ID));

        // 设置cpu使用量
        List<VM> cpuUsedTopN = hostAndVmOp.getVmCpuUsedTopN(vms, n);
        metricMap.put(PerfConstants.CPU_USED_ID,
                PerfUtils.createVmMetricValueMap(cpuUsedTopN, PerfConstants.CPU_USED_ID));

        // 设置内存使用率
        List<VM> memUsageTopN = hostAndVmOp.getVmMemUsageTopN(vms, n);
        metricMap.put(PerfConstants.MEM_USAGE_ID,
                PerfUtils.createVmMetricValueMap(memUsageTopN, PerfConstants.MEM_USAGE_ID));

        // 设置内存使用量
        List<VM> memUsedTopN = hostAndVmOp.getVmMemUsedTopN(vms, n);
        metricMap.put(PerfConstants.MEM_USED_ID,
                PerfUtils.createVmMetricValueMap(memUsedTopN, PerfConstants.MEM_USED_ID));

        // 设置磁盘io
        List<VM> diskIoSpeedTopN = hostAndVmOp.getVmDiskIoTopN(vms, n);
        metricMap.put(PerfConstants.VDISK_IO_SPEED_ID,
                PerfUtils.createVmMetricValueMap(diskIoSpeedTopN, PerfConstants.VDISK_IO_SPEED_ID));

        // 设置磁盘iops
        List<VM> diskIopsTopN = hostAndVmOp.getVmDiskIopsTopN(vms, n);
        metricMap.put(PerfConstants.DISK_IOPS_ID,
                PerfUtils.createVmMetricValueMap(diskIopsTopN, PerfConstants.DISK_IOPS_ID));

        // 设置网络io
        List<VM> netIoTopN = hostAndVmOp.getVmNetIoTopN(vms, n);
        metricMap.put(PerfConstants.NET_IO_SPEED_ID,
                PerfUtils.createVmMetricValueMap(netIoTopN, PerfConstants.NET_IO_SPEED_ID));

        // 设置网络接收io
        List<VM> netRecvIoTopN = hostAndVmOp.getVmNetRecvIoTopN(vms, n);
        metricMap.put(PerfConstants.NET_RX_ID,
                PerfUtils.createVmMetricValueMap(netRecvIoTopN, PerfConstants.NET_RX_ID));

        // 设置网络发送io
        List<VM> netSendIoTopN = hostAndVmOp.getVmNetSendIoTopN(vms, n);
        metricMap.put(PerfConstants.NET_TX_ID,
                PerfUtils.createVmMetricValueMap(netSendIoTopN, PerfConstants.NET_TX_ID));

        for (i = 0; i < vms.size(); i++) {
            VM vm = vms.get(i);
            Map<String, String> vmProperty = new HashMap<String, String>();
            vmProperty.put(PerfConstants.OBJ_RUN_STATUS_ID, vm.getStatus());
            vmProperty.put(PerfConstants.DISK_TOTAL_ID, vm.getDiskTotal());
            vmProperty.put(PerfConstants.DISK_USED_ID, vm.getDiskUsed());
            vmProperty.put(PerfConstants.CPU_TOTAL_ID, vm.getCpuMHZTotal());
            vmProperty.put(PerfConstants.MEM_TOTAL_ID, vm.getMemoryTotal());
            metricMap.put(ToolsUtils.makeHostKeyName(vm.getName()), vmProperty);
        }
        return metricMap;

    }

    @Override
    public Map<String, Map<String, String>> getVmOtherTopNOnHost(int n, String hostId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Map<String, String>> getHostTopN(int n, String clusterId) {
        // TODO Auto-generated method stub
        Map<String, Map<String, String>> metricMap = new HashMap<String, Map<String, String>>();
        List<Host> hosts = hostAndVmOp.getHosts(clusterId);
        int i = 0;

        // 这是cpu使用率
        List<Host> cpuUsageTopN = hostAndVmOp.getHostCpuUsageTopN(hosts, n);
        metricMap.put(PerfConstants.CPU_USAGE_ID,
                PerfUtils.createMetricValueMap(cpuUsageTopN, PerfConstants.CPU_USAGE_ID));

        // 设置cpu使用量
        List<Host> cpuUsedTopN = hostAndVmOp.getHostCpuUsedTopN(hosts, n);
        metricMap.put(PerfConstants.CPU_USED_ID,
                PerfUtils.createMetricValueMap(cpuUsedTopN, PerfConstants.CPU_USED_ID));

        // 设置内存使用率
        List<Host> memUsageTopN = hostAndVmOp.getHostMemUsageTopN(hosts, n);
        metricMap.put(PerfConstants.MEM_USAGE_ID,
                PerfUtils.createMetricValueMap(memUsageTopN, PerfConstants.MEM_USAGE_ID));

        // 设置内存使用量
        List<Host> memUsedTopN = hostAndVmOp.getHostMemUsedTopN(hosts, n);
        metricMap.put(PerfConstants.MEM_USED_ID,
                PerfUtils.createMetricValueMap(memUsedTopN, PerfConstants.MEM_USED_ID));

        // 设置磁盘io
        List<Host> diskIoSpeedTopN = hostAndVmOp.getHostDiskIoTopN(hosts, n);
        metricMap.put(PerfConstants.DISK_IO_SPEED_ID,
                PerfUtils.createMetricValueMap(diskIoSpeedTopN, PerfConstants.DISK_IO_SPEED_ID));

        // 设置磁盘iops
        List<Host> diskIopsTopN = hostAndVmOp.getHostDiskIopsTopN(hosts, n);
        metricMap.put(PerfConstants.DISK_IOPS_ID,
                PerfUtils.createMetricValueMap(diskIopsTopN, PerfConstants.DISK_IOPS_ID));

        // 设置网络io
        List<Host> netIoTopN = hostAndVmOp.getHostNetIoTopN(hosts, n);
        metricMap.put(PerfConstants.NET_IO_SPEED_ID,
                PerfUtils.createMetricValueMap(netIoTopN, PerfConstants.NET_IO_SPEED_ID));

        // 设置网络接收io
        List<Host> netRecvIoTopN = hostAndVmOp.getHostNetRecvIoTopN(hosts, n);
        metricMap.put(PerfConstants.NET_RX_ID, PerfUtils.createMetricValueMap(netRecvIoTopN, PerfConstants.NET_RX_ID));

        // 设置网络发送io
        List<Host> netSendIoTopN = hostAndVmOp.getHostNetSendIoTopN(hosts, n);
        metricMap.put(PerfConstants.NET_TX_ID, PerfUtils.createMetricValueMap(netSendIoTopN, PerfConstants.NET_TX_ID));

        // 设置主机上的虚拟机数量
        List<Host> vmNumsTopN = hostAndVmOp.getHostVmNumsTopN(hosts, n);
        metricMap.put(PerfConstants.VM_NUMS_ID, PerfUtils.createMetricValueMap(vmNumsTopN, PerfConstants.VM_NUMS_ID));

        for (i = 0; i < hosts.size(); i++) {
            Host host = hosts.get(i);
            Map<String, String> hostProperty = new HashMap<String, String>();
            hostProperty.put(PerfConstants.OBJ_RUN_STATUS_ID, host.getStatus());
            hostProperty.put(PerfConstants.OBJ_POWER_STATUS_ID, host.getPowerStatus());
            hostProperty.put(PerfConstants.DISK_TOTAL_ID, host.getDiskTotal());
            hostProperty.put(PerfConstants.DISK_USED_ID, host.getDiskUsed());
            hostProperty.put(PerfConstants.CPU_TOTAL_ID, host.getCpuMHZTotal());
            hostProperty.put(PerfConstants.MEM_TOTAL_ID, host.getMemoryTotal());
            metricMap.put(ToolsUtils.makeHostKeyName(host.getName()), hostProperty);
        }

        return metricMap;
    }

    /**
     * 获取主机信息
     * 
     * @param n
     *            ： 各指标的前n个值
     * @param type
     *            : 类型，0(遍历获取),1(批量获取)
     * @return
     */
    public JSONObject getHostTopNInner(int n, int type) {
        // TODO Auto-generated method stub
        List<Host> hosts = null;
        if (type == 0) {
            hosts = hostAndVmOp.getHostsEx();
        } else {
            hosts = hostAndVmOp.getHostsBatch();
        }

        int index = 0;
        JSONObject resultJsonObj = new JSONObject();
        JSONObject topNObj = new JSONObject();

        // 这是cpu使用率
        List<Host> cpuUsageTopN = hostAndVmOp.getHostCpuUsageTopN(hosts, n);
        JSONObject cpuUsageJson = PerfUtils.createPerfJsonObject(cpuUsageTopN, PerfConstants.CPU_USAGE_ID,
                PerfConstants.JSON_HOST_PERF_NAME_KEY, PerfConstants.JSON_PERF_VALUE_KEY, 1);
        topNObj.put(PerfConstants.JSON_CPU_USAGE_KEY, cpuUsageJson);

        // 设置cpu使用量
        List<Host> cpuUsedTopN = hostAndVmOp.getHostCpuUsedTopN(hosts, n);
        JSONObject cpuUsedJson = PerfUtils.createPerfJsonObject(cpuUsedTopN, PerfConstants.CPU_USED_ID,
                PerfConstants.JSON_HOST_PERF_NAME_KEY, PerfConstants.JSON_PERF_VALUE_KEY, 1);
        topNObj.put(PerfConstants.JSON_CPU_USED_KEY, cpuUsedJson);

        // 设置内存使用率
        List<Host> memUsageTopN = hostAndVmOp.getHostMemUsageTopN(hosts, n);
        JSONObject memUsageJson = PerfUtils.createPerfJsonObject(memUsageTopN, PerfConstants.MEM_USAGE_ID,
                PerfConstants.JSON_HOST_PERF_NAME_KEY, PerfConstants.JSON_PERF_VALUE_KEY, 1);
        topNObj.put(PerfConstants.JSON_MEM_USAGE_KEY, memUsageJson);

        // 设置内存使用量
        List<Host> memUsedTopN = hostAndVmOp.getHostMemUsedTopN(hosts, n);
        JSONObject memUsedJson = PerfUtils.createPerfJsonObject(memUsedTopN, PerfConstants.MEM_USED_ID,
                PerfConstants.JSON_HOST_PERF_NAME_KEY, PerfConstants.JSON_PERF_VALUE_KEY, 1);
        topNObj.put(PerfConstants.JSON_MEM_USED_KEY, memUsedJson);

        // 设置磁盘io
        List<Host> diskIoSpeedTopN = hostAndVmOp.getHostDiskIoTopN(hosts, n);
        JSONObject diskIoJson = PerfUtils.createPerfJsonObject(diskIoSpeedTopN, PerfConstants.DISK_IO_SPEED_ID,
                PerfConstants.JSON_HOST_PERF_NAME_KEY, PerfConstants.JSON_PERF_VALUE_KEY, 1);
        topNObj.put(PerfConstants.JSON_DISK_IO_KEY, diskIoJson);

        // 设置磁盘iops
        List<Host> diskIopsTopN = hostAndVmOp.getHostDiskIopsTopN(hosts, n);
        JSONObject diskIopsJson = PerfUtils.createPerfJsonObject(diskIopsTopN, PerfConstants.DISK_IOPS_ID,
                PerfConstants.JSON_HOST_PERF_NAME_KEY, PerfConstants.JSON_PERF_VALUE_KEY, 1);
        topNObj.put(PerfConstants.JSON_DISK_IOPS_KEY, diskIopsJson);

        // 设置网络io
        List<Host> netIoTopN = hostAndVmOp.getHostNetIoTopN(hosts, n);
        JSONObject netJson = PerfUtils.createPerfJsonObject(netIoTopN, PerfConstants.NET_IO_SPEED_ID,
                PerfConstants.JSON_HOST_PERF_NAME_KEY, PerfConstants.JSON_PERF_VALUE_KEY, 1);
        topNObj.put(PerfConstants.JSON_NET_IO_KEY, netJson);

        // 设置主机上的虚拟机数量
        List<Host> vmNumsTopN = hostAndVmOp.getHostVmNumsTopN(hosts, n);
        JSONObject vmNumsJson = PerfUtils.createPerfJsonObject(vmNumsTopN, PerfConstants.VM_NUMS_ID,
                PerfConstants.JSON_HOST_PERF_NAME_KEY, PerfConstants.JSON_PERF_VALUE_KEY, 2);
        topNObj.put(PerfConstants.JSON_VM_NUMS_KEY, vmNumsJson);

        resultJsonObj.put(PerfConstants.JSON_PERF_TOPN_KEY, topNObj);
        JSONArray hostsJson = new JSONArray();
        for (index = 0; index < hosts.size(); index++) {
            Host host = hosts.get(index);
            JSONObject hostJson = PerfUtils.createHostJsonObject(host);
            hostsJson.add(index, hostJson);
        }
        resultJsonObj.put(PerfConstants.JSON_HOST_PERF_NAME_KEY, hostsJson);

        return resultJsonObj;
    }

    @Override
    public JSONObject getHostTopN(int n) {
        return getHostTopNInner(n, 0);
    }

    @Override
    public JSONObject getHostTopNBatch(int n) {
        // TODO Auto-generated method stub
        return getHostTopNInner(n, 1);
    }

    @Override
    public JSONObject getVmInfoJson(String vmId) {

        JSONObject retunJSON = new JSONObject();
        VM vm = this.getVmInfoEx(vmId);
        String vmName = vm.getName();

        retunJSON.put("vmName", vmName);
        retunJSON.put("hostName", vm.getHostName());
        retunJSON.put("dataCenterName", vm.getDateCenterName());
        retunJSON.put("clusterName", vm.getClusterName());
        String hostIP = vm.getIpAddr();
        if (hostIP != null && hostIP.length() > 0) {
            hostIP = hostIP.replace(",", "   ,    ");
        }
        retunJSON.put("hostIp", hostIP);

        // cpuMHZTotal转换为Mhz单位，保留两位小数
        DecimalFormat df = new DecimalFormat("#0.00");
        String cpuMhzTotal = df.format(Double.parseDouble(vm.getCpuMHZTotal()) / 1.0);
        vm.setCpuMHZTotal(cpuMhzTotal);
        vm.setCpuMHZUsed(df.format(Double.parseDouble(vm.getCpuMHZUsed())));

        // 内存单位转换为G，保留两位小数
        vm.setMemoryTotal(df.format(Double.parseDouble(vm.getMemoryTotal()) / 1024.0));
        vm.setMemoryUsed(df.format(Double.parseDouble(vm.getMemoryUsed()) / 1024.0));

        // disk容量单位转换为G，保留两位小数
        vm.setDiskTotal(df.format(Double.parseDouble(vm.getDiskTotal()) / 1024.0));
        vm.setDiskUsed(df.format(Double.parseDouble(vm.getDiskUsed()) / 1024.0));
        vm.setDiskUsage(
                df.format(Double.parseDouble(vm.getDiskUsed()) * 100.0 / (Double.parseDouble(vm.getDiskTotal()))));

        // 虚拟机cpu和内存、磁盘、网络指标值
        DecimalFormat df1 = new DecimalFormat("#");
        // 获取虚拟机cpuMHZ总量

        // 获取虚拟机memTotalMax
        retunJSON.put("netSend", df1.format(Double.parseDouble(vm.getNetworkSendSpeed())));
        retunJSON.put("netRecv", df1.format(Double.parseDouble(vm.getNetworkReceiveSpeed())));

        // 新添加的指标
        retunJSON.put("cpuMhzMax", df1.format(Math.round(Double.valueOf(vm.getCpuMHZTotal()) / 10.0 + 1.0) * 10));
        // 获取cpuMHZ
        retunJSON.put("cpuMhzUsed", vm.getCpuMHZUsed());

        // 内存整体视图
        // 内存单位转换为G，保留两位小数
        retunJSON.put("memTotalMax", df1.format(Math.round(Double.valueOf(vm.getMemoryTotal()) / 10.0 + 1.0) * 10));
        retunJSON.put("memUsed", df.format(Double.parseDouble(vm.getMemoryUsed())));

        // 存储整体视图
        // disk容量单位转换为G，保留两位小数
        String storeTotalTB = vm.getDiskTotal();
        retunJSON.put("storeTotalTB", storeTotalTB);
        String storeUsedTB = vm.getDiskUsed();
        retunJSON.put("storeUsedTB", storeUsedTB);
        String storeFreeTB = df.format(Double.parseDouble(storeTotalTB) - (Double.parseDouble(storeUsedTB)));
        retunJSON.put("storeFreeTB", storeFreeTB);
        String storeUsage = vm.getDiskUsage();
        retunJSON.put("storeUsage", storeUsage);

        // 利用率转换为整数,给饼图使用
        String vmDiskUsagePie = String.valueOf(Math.round(Double.valueOf(vm.getDiskUsage())));
        retunJSON.put("vmDiskUsagePie", vmDiskUsagePie);

        String hostname = vm.getHostName();
        if (hostname != null) {
            Host host = this.resourceService.getHostBasicInfo(hostname);

            // cpuMHZTotal转换为Mhz单位，保留两位小数
            DecimalFormat dfHost = new DecimalFormat("#0.00");
            String cpuMhzTotalHost = dfHost.format(Double.parseDouble(host.getCpuMHZTotal()) / 1024.0);
            host.setCpuMHZTotal(cpuMhzTotalHost);
            host.setCpuMHZUsed(dfHost.format(Double.parseDouble(host.getCpuMHZUsed())));

            // 内存单位转换为G，保留两位小数
            host.setMemoryTotal(dfHost.format(Double.parseDouble(host.getMemoryTotal())));
            host.setMemoryUsed(dfHost.format(Double.parseDouble(host.getMemoryUsed()) / 1024.0));

            // disk容量单位转换为G，保留两位小数
            host.setDiskTotal(dfHost.format(Double.parseDouble(host.getDiskTotal()) * 1024.0));
            host.setDiskUsage(dfHost.format(
                    Double.parseDouble(host.getDiskUsed()) * 100.0 / (Double.parseDouble(host.getDiskTotal()))));

            // 虚拟机cpu和内存、磁盘、网络指标值
            DecimalFormat df2 = new DecimalFormat("#");

            retunJSON.put("hostNetSend", df2.format(Double.parseDouble(host.getNetworkSendSpeed())));
            retunJSON.put("hostNetRecv", df2.format(Double.parseDouble(host.getNetworkReceiveSpeed())));

            // 获取虚拟机cpuMHZ总量
            retunJSON.put("hostCpuMhzMax",
                    df2.format(Math.round(Double.valueOf(host.getCpuMHZTotal()) / 10.0 + 1.0) * 10));
            // 获取cpuMHZ
            retunJSON.put("hostCpuMhzUsed", host.getCpuMHZUsed());

            // 内存整体视图
            // 内存单位转换为G，保留两位小数
            // 获取虚拟机memTotalMax
            retunJSON.put("hostMemTotalMax",
                    df2.format(Math.round(Double.valueOf(host.getMemoryTotal()) / 10.0 + 1.0) * 10));
            retunJSON.put("hostMemUsed", df.format(Double.parseDouble(host.getMemoryUsed())));

            // 存储整体视图
            // disk容量单位转换为G，保留两位小数
            String hostStoreTotalTB = host.getDiskTotal();
            retunJSON.put("hostStoreTotalTB", hostStoreTotalTB);
            String hostStoreUsedTB = host.getDiskUsed();
            retunJSON.put("hostStoreUsedTB", hostStoreUsedTB);
            String hostStoreFreeTB = df
                    .format(Double.parseDouble(hostStoreTotalTB) - (Double.parseDouble(hostStoreUsedTB)));
            retunJSON.put("hostStoreFreeTB", hostStoreFreeTB);
            String hostStoreUsage = host.getDiskUsage();
            retunJSON.put("hostStoreUsage", hostStoreUsage);

            // 利用率转换为整数,给饼图使用
            String hostDiskUsagePie = String.valueOf(Math.round(Double.valueOf(host.getDiskUsage())));
            retunJSON.put("hostDiskUsagePie", hostDiskUsagePie);

        }

        // 获取24小时性能数据对象的JSON信息
        Map<String, List<MetricValue>> metricListMap = historyPerfAndAlertService.get24HPerformData(vmName,
                SystemResourceType.virtualMachine);

        JSONArray cpuTime = new JSONArray();
        JSONArray cpuValue = new JSONArray();

        JSONArray memTime = new JSONArray();
        JSONArray memValue = new JSONArray();

        JSONArray diskTime = new JSONArray();
        JSONArray diskValue = new JSONArray();

        JSONArray netTime = new JSONArray();
        JSONArray netValue = new JSONArray();

        if (metricListMap != null && metricListMap.isEmpty() == false) {
            for (String key : metricListMap.keySet()) {
                for (MetricValue mv : metricListMap.get(key)) {
                    if (key.equals("CPU_RATE")) {
                        cpuTime.add(mv.getCollectTime());
                        cpuValue.add(mv.getMetricValue());
                    } else if (key.equals("MEM_RATE")) {
                        memTime.add(mv.getCollectTime());
                        memValue.add(mv.getMetricValue());
                    } else if (key.equals("DSK_USAGE")) {
                        diskTime.add(mv.getCollectTime());
                        diskValue.add(mv.getMetricValue());
                    } else if (key.equals("NIC_AVG")) {
                        netTime.add(mv.getCollectTime());
                        netValue.add(mv.getMetricValue());
                    }
                    // System.out.println(key +"\t" +mv.getCollectTime() + "\t"
                    // + mv.getMetricValue() );
                }
            }
        }

        // 组装单个指标值的json对象
        JSONObject mapCpu = new JSONObject();
        mapCpu.put("collectTime", cpuTime);
        mapCpu.put("values", cpuValue);

        JSONObject mapMem = new JSONObject();
        mapMem.put("collectTime", memTime);
        mapMem.put("values", memValue);

        JSONObject mapDisk = new JSONObject();
        mapDisk.put("collectTime", diskTime);
        mapDisk.put("values", diskValue);

        JSONObject mapNet = new JSONObject();
        mapNet.put("collectTime", netTime);
        mapNet.put("values", netValue);

        // 组装所有指标值json对象
        JSONObject mapAllMertic = new JSONObject();
        mapAllMertic.put("cpuUsage", mapCpu);
        mapAllMertic.put("memUsage", mapMem);
        mapAllMertic.put("diskTps", mapDisk);
        mapAllMertic.put("netTps", mapNet);

        retunJSON.put("perf24", mapAllMertic);

        // 将物理机上的虚拟机topN数据传递回去
        VCenterManageUtils vcenterUtil = new VCenterManageUtils(con);
        VirtualMachine virtualMachine = null;
        try {
            virtualMachine = vcenterUtil.getVirtualMachineByName(vmName);
        } catch (CloudViewPerfException e) {
            // e.printStackTrace();
        }
        // 获取虚拟机的所有触发告警
        if (vm != null && virtualMachine.getMOR() != null) {
            List<AlarmEntity> alertList = PerfUtils
                    .filterAlarm(historyPerfAndAlertService.getTriggeredAlarms(virtualMachine), 10);
            retunJSON.put("alarmList", alertList);
        }

        return retunJSON;

    }

    @Override
    public JSONObject getHostInfoJson(String hostId) {
        Host host = this.getHostInfoEx(hostId);
        String hostName = host.getName();
        JSONObject retunJSON = new JSONObject();

        retunJSON.put("hostName", hostName);
        retunJSON.put("dataCenterName", host.getDataCenterName());
        retunJSON.put("clusterName", host.getClusterName());

        String hostIP = host.getIpAddr();
        if (hostIP != null && hostIP.length() > 0) {
            hostIP = hostIP.replace(",", "   ,    ");
        }
        retunJSON.put("hostIp", hostIP);

        // 传递cpu、存储、磁盘利用率条形图所需JSON数据

        // CPU整体视图
        // cpuMHZTotal转换为Mhz单位，保留两位小数
        DecimalFormat df = new DecimalFormat("#0.00");
        String cpuMhzTotal = df.format(Double.parseDouble(host.getCpuMHZTotal()) / 1000.0);
        retunJSON.put("cpuMHZTotal", cpuMhzTotal);
        retunJSON.put("cpuMHZUsed", host.getCpuMHZUsed());
        retunJSON.put("cpuMHZFree",
                df.format(Double.parseDouble(cpuMhzTotal) - (Double.parseDouble(host.getCpuMHZUsed()))));
        retunJSON.put("cpuUsage", host.getCpuUsage());

        // 内存整体视图
        // 内存单位转换为G，保留两位小数
        host.setMemoryUsed(df.format(Double.parseDouble(host.getMemoryUsed())));
        String memTotalGB = host.getMemoryTotal();
        retunJSON.put("memTotalGB", memTotalGB);
        String memUsedGB = host.getMemoryUsed();
        retunJSON.put("memUsedGB", memUsedGB);
        String memFreeGB = df.format(Double.parseDouble(memTotalGB) - (Double.parseDouble(memUsedGB)));
        retunJSON.put("memFreeGB", memFreeGB);
        String memUsage = host.getMemoryUsage();
        retunJSON.put("memUsage", df.format(Double.parseDouble(memUsage)));

        // 存储整体视图
        // disk容量单位转换为G，保留两位小数
        host.setDiskTotal(df.format(Double.parseDouble(host.getDiskTotal()) * 1024.0));
        host.setDiskUsage(
                df.format(Double.parseDouble(host.getDiskUsed()) * 100.0 / (Double.parseDouble(host.getDiskTotal()))));
        String storeTotalTB = host.getDiskTotal();
        retunJSON.put("storeTotalTB", storeTotalTB);
        String storeUsedTB = host.getDiskUsed();
        retunJSON.put("storeUsedTB", storeUsedTB);
        String storeFreeTB = df.format(Double.parseDouble(storeTotalTB) - (Double.parseDouble(storeUsedTB)));
        retunJSON.put("storeFreeTB", storeFreeTB);
        String storeUsage = host.getDiskUsage();
        retunJSON.put("storeUsage", storeUsage);

        // 利用率转换为整数,给饼图使用
        String cpuUsagePie = String.valueOf(Math.round(Double.valueOf(host.getCpuUsage())));
        String memUsagePie = String.valueOf(Math.round(Double.valueOf(host.getMemoryUsage())));
        String diskUsagePie = String.valueOf(Math.round(Double.valueOf(host.getDiskUsage())));
        retunJSON.put("cpuUsagePie", cpuUsagePie);
        retunJSON.put("memUsagePie", memUsagePie);
        retunJSON.put("diskUsagePie", diskUsagePie);

        // 群集内部主机 虚拟机 存储的总量，正常数量，异常数量
        int vmNum = host.getVmNum();
        retunJSON.put("vmNum", vmNum);
        int storeNum = host.getStoreNum();
        retunJSON.put("storeNum", storeNum);

        JSONArray stautsArray = new JSONArray();

        // 获取虚拟机的正常也异常状态信息，并组装成JSON对象
        JSONObject mapObjError = new JSONObject();
        mapObjError.put("name", "异常");
        mapObjError.put("value", host.getVmUnaccessibleNum());
        JSONObject mapObjOk = new JSONObject();
        mapObjOk.put("name", "正常");
        mapObjOk.put("value", host.getVmAccessibleNum());
        // 数组 + map
        JSONArray data = new JSONArray();
        data.add(0, mapObjError);
        data.add(1, mapObjOk);
        stautsArray.add(data);

        // 获取存储对象的正常也异常状态信息，并组装成JSON对象
        JSONObject mapObjError1 = new JSONObject();
        mapObjError1.put("name", "异常");
        mapObjError1.put("value", host.getStoreUnaccessibleNum());
        JSONObject mapObjOk1 = new JSONObject();
        mapObjOk1.put("name", "正常");
        mapObjOk1.put("value", host.getStoreAccessibleNum());
        // 数组 + map
        JSONArray data1 = new JSONArray();
        data1.add(0, mapObjError1);
        data1.add(1, mapObjOk1);
        stautsArray.add(data1);

        retunJSON.put("vMStoreStatus", stautsArray);

        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss:SSS");
        String startTime = time.format(new java.util.Date());
        // System.out.println("metricListMap开始:" + startTime);

        // 获取24小时性能数据对象的JSON信息
        Map<String, List<MetricValue>> metricListMap = historyPerfAndAlertService.get24HPerformData(host.getName(),
                SystemResourceType.hostSystem);

        JSONArray cpuTime = new JSONArray();
        JSONArray cpuValue = new JSONArray();

        JSONArray memTime = new JSONArray();
        JSONArray memValue = new JSONArray();

        JSONArray diskTime = new JSONArray();
        JSONArray diskValue = new JSONArray();

        JSONArray netTime = new JSONArray();
        JSONArray netValue = new JSONArray();

        if (metricListMap != null && metricListMap.isEmpty() == false) {
            for (String key : metricListMap.keySet()) {
                for (MetricValue mv : metricListMap.get(key)) {
                    if (key.equals("CPU_RATE")) {
                        cpuTime.add(mv.getCollectTime());
                        cpuValue.add(mv.getMetricValue());
                    } else if (key.equals("MEM_RATE")) {
                        memTime.add(mv.getCollectTime());
                        memValue.add(mv.getMetricValue());
                    } else if (key.equals("DSK_USAGE")) {
                        diskTime.add(mv.getCollectTime());
                        diskValue.add(mv.getMetricValue());
                    } else if (key.equals("NIC_AVG")) {
                        netTime.add(mv.getCollectTime());
                        netValue.add(mv.getMetricValue());
                    }
                }
            }
        }
        startTime = time.format(new java.util.Date());
        // System.out.println("metricListMap结束:" + startTime);

        // 组装单个指标值的json对象
        JSONObject mapCpu = new JSONObject();
        mapCpu.put("collectTime", cpuTime);
        mapCpu.put("values", cpuValue);

        JSONObject mapMem = new JSONObject();
        mapMem.put("collectTime", memTime);
        mapMem.put("values", memValue);

        JSONObject mapDisk = new JSONObject();
        mapDisk.put("collectTime", diskTime);
        mapDisk.put("values", diskValue);

        JSONObject mapNet = new JSONObject();
        mapNet.put("collectTime", netTime);
        mapNet.put("values", netValue);

        // 组装所有指标值json对象
        JSONObject mapAllMertic = new JSONObject();
        mapAllMertic.put("cpuUsage", mapCpu);
        mapAllMertic.put("memUsage", mapMem);
        mapAllMertic.put("diskTps", mapDisk);
        mapAllMertic.put("netTps", mapNet);

        retunJSON.put("perf24", mapAllMertic);

        // 将物理机上的虚拟机topN数据传递回去
        VCenterManageUtils vcenterUtil = new VCenterManageUtils(con);
        HostSystem hostSys = null;
        try {
            hostSys = vcenterUtil.getHostSystemByName(hostName);
        } catch (CloudViewPerfException e) {
            // e.printStackTrace();
        }

        if (hostSys != null && hostSys.getMOR() != null) {
            Map<String, Map<String, String>> vmTopListMap = this.getVmCommonTopNOnHost(3, hostSys.getMOR().val);

            // 所有指标
            JSONObject metricAll = new JSONObject();
            for (String metricKey : vmTopListMap.keySet()) {

                // cpu利用率
                if (metricKey.equals(PerfConstants.CPU_USAGE_ID)) {
                    // 单个指标
                    JSONObject cpuMericArray = new JSONObject();
                    // 单个指标组成json串
                    JSONArray cpuNameListJson = new JSONArray();
                    JSONArray cpuValueListJson = new JSONArray();
                    for (String vmName : vmTopListMap.get(metricKey).keySet()) {
                        // 添加虚拟机名称列表
                        cpuNameListJson.add(vmName);
                        String vmValueString = vmTopListMap.get(metricKey).get(vmName);
                        // 添加虚拟机值列表
                        cpuValueListJson.add(vmValueString);
                    }
                    cpuMericArray.put("vmsName", cpuNameListJson);
                    cpuMericArray.put("vmsValue", cpuValueListJson);

                    metricAll.put("cpuUsage", cpuMericArray);
                } else if (metricKey.equals(PerfConstants.MEM_USAGE_ID)) {
                    // 单个指标
                    JSONObject memMericArray = new JSONObject();
                    // 单个指标组成json串
                    JSONArray memNameListJson = new JSONArray();
                    JSONArray memValueListJson = new JSONArray();
                    for (String vmName : vmTopListMap.get(metricKey).keySet()) {
                        // 添加虚拟机名称列表
                        memNameListJson.add(vmName);
                        String vmValueString = vmTopListMap.get(metricKey).get(vmName);
                        // 添加虚拟机值列表
                        memValueListJson.add(vmValueString);
                    }
                    memMericArray.put("vmsName", memNameListJson);
                    memMericArray.put("vmsValue", memValueListJson);

                    metricAll.put("memUsage", memMericArray);
                } else if (metricKey.equals(PerfConstants.VDISK_IO_SPEED_ID)) {
                    // 单个指标
                    JSONObject diskMericArray = new JSONObject();
                    // 单个指标组成json串
                    JSONArray diskNameListJson = new JSONArray();
                    JSONArray diskValueListJson = new JSONArray();
                    for (String vmName : vmTopListMap.get(metricKey).keySet()) {
                        // 添加虚拟机名称列表
                        diskNameListJson.add(vmName);
                        String vmValueString = vmTopListMap.get(metricKey).get(vmName);
                        // 添加虚拟机值列表
                        diskValueListJson.add(vmValueString);
                    }
                    diskMericArray.put("vmsName", diskNameListJson);
                    diskMericArray.put("vmsValue", diskValueListJson);

                    metricAll.put("diskTps", diskMericArray);
                } else if (metricKey.equals(PerfConstants.NET_IO_SPEED_ID)) {
                    // 单个指标
                    JSONObject netMericArray = new JSONObject();
                    // 单个指标组成json串
                    JSONArray netNameListJson = new JSONArray();
                    JSONArray netValueListJson = new JSONArray();
                    for (String vmName : vmTopListMap.get(metricKey).keySet()) {
                        // 添加虚拟机名称列表
                        netNameListJson.add(vmName);
                        String vmValueString = vmTopListMap.get(metricKey).get(vmName);
                        // 添加虚拟机值列表
                        netValueListJson.add(vmValueString);
                    }
                    netMericArray.put("vmsName", netNameListJson);
                    netMericArray.put("vmsValue", netValueListJson);

                    metricAll.put("netTps", netMericArray);
                }

            }
            retunJSON.put("vmTop10", metricAll);

        }

        // 获取物理机的所有触发告警
        if (hostSys != null && hostSys.getMOR() != null) {
            List<AlarmEntity> alertList = PerfUtils.filterAlarm(historyPerfAndAlertService.getTriggeredAlarms(hostSys),
                    10);
            retunJSON.put("alarmList", alertList);
        }

        return retunJSON;
    }

    /**
     * 获取虚拟机信息接口
     * 
     * @param n
     *            ： 各指标值的前n个值
     * @param type
     *            : 类型，0(遍历获取),1(批量获取)
     * @return
     */
    public JSONObject getVMTopNInner(int n, int type) {
        // TODO Auto-generated method stub
        List<VM> vms = null;
        if (type == 0) {
            vms = hostAndVmOp.getVmsEx();
        } else {
            vms = hostAndVmOp.getVmsBatch();
        }

        int index = 0;
        JSONObject resultJsonObj = new JSONObject();
        JSONObject topNObj = new JSONObject();

        // 这是cpu使用率
        List<VM> cpuUsageTopN = hostAndVmOp.getVmCpuUsageTopN(vms, n);
        JSONObject cpuUsageJson = PerfUtils.createVmPerfJsonObject(cpuUsageTopN, PerfConstants.CPU_USAGE_ID,
                PerfConstants.JSON_VM_PERF_NAME_KEY, PerfConstants.JSON_PERF_VALUE_KEY, 1);
        topNObj.put(PerfConstants.JSON_CPU_USAGE_KEY, cpuUsageJson);

        // 设置cpu使用量
        List<VM> cpuUsedTopN = hostAndVmOp.getVmCpuUsedTopN(vms, n);
        JSONObject cpuUsedJson = PerfUtils.createVmPerfJsonObject(cpuUsedTopN, PerfConstants.CPU_USED_ID,
                PerfConstants.JSON_VM_PERF_NAME_KEY, PerfConstants.JSON_PERF_VALUE_KEY, 1);
        topNObj.put(PerfConstants.JSON_CPU_USED_KEY, cpuUsedJson);

        // 设置内存使用率
        List<VM> memUsageTopN = hostAndVmOp.getVmMemUsageTopN(vms, n);
        JSONObject memUsageJson = PerfUtils.createVmPerfJsonObject(memUsageTopN, PerfConstants.MEM_USAGE_ID,
                PerfConstants.JSON_VM_PERF_NAME_KEY, PerfConstants.JSON_PERF_VALUE_KEY, 1);
        topNObj.put(PerfConstants.JSON_MEM_USAGE_KEY, memUsageJson);

        // 设置内存使用量
        List<VM> memUsedTopN = hostAndVmOp.getVmMemUsedTopN(vms, n);
        JSONObject memUsedJson = PerfUtils.createVmPerfJsonObject(memUsedTopN, PerfConstants.MEM_USED_ID,
                PerfConstants.JSON_VM_PERF_NAME_KEY, PerfConstants.JSON_PERF_VALUE_KEY, 1);
        topNObj.put(PerfConstants.JSON_MEM_USED_KEY, memUsedJson);

        // 设置磁盘io
        List<VM> diskIoSpeedTopN = hostAndVmOp.getVmDiskIoTopN(vms, n);
        JSONObject diskIoJson = PerfUtils.createVmPerfJsonObject(diskIoSpeedTopN, PerfConstants.VDISK_IO_SPEED_ID,
                PerfConstants.JSON_VM_PERF_NAME_KEY, PerfConstants.JSON_PERF_VALUE_KEY, 1);
        topNObj.put(PerfConstants.JSON_DISK_IO_KEY, diskIoJson);

        // 设置磁盘iops
        List<VM> diskIopsTopN = hostAndVmOp.getVmDiskIopsTopN(vms, n);
        JSONObject diskIopsJson = PerfUtils.createVmPerfJsonObject(diskIopsTopN, PerfConstants.VDISK_IOPS_ID,
                PerfConstants.JSON_VM_PERF_NAME_KEY, PerfConstants.JSON_PERF_VALUE_KEY, 1);
        topNObj.put(PerfConstants.JSON_DISK_IOPS_KEY, diskIopsJson);

        // 设置网络io
        List<VM> netIoTopN = hostAndVmOp.getVmNetIoTopN(vms, n);
        JSONObject netJson = PerfUtils.createVmPerfJsonObject(netIoTopN, PerfConstants.NET_IO_SPEED_ID,
                PerfConstants.JSON_VM_PERF_NAME_KEY, PerfConstants.JSON_PERF_VALUE_KEY, 1);
        topNObj.put(PerfConstants.JSON_NET_IO_KEY, netJson);

        // 设置主机上的虚拟机数量
        List<VM> diskUsageTopN = hostAndVmOp.getVmDiskUsageTopN(vms, n);
        JSONObject diskUsageJson = PerfUtils.createVmPerfJsonObject(diskUsageTopN, PerfConstants.DISK_USAGE_ID,
                PerfConstants.JSON_VM_PERF_NAME_KEY, PerfConstants.JSON_PERF_VALUE_KEY, 2);
        topNObj.put(PerfConstants.JSON_DISK_USAGE_KEY, diskUsageJson);

        resultJsonObj.put(PerfConstants.JSON_PERF_TOPN_KEY, topNObj);
        JSONArray vmsJson = new JSONArray();
        for (index = 0; index < vms.size(); index++) {
            VM vm = vms.get(index);
            JSONObject vmJson = PerfUtils.createVmJsonObject(vm);
            vmsJson.add(index, vmJson);
        }
        resultJsonObj.put(PerfConstants.JSON_VM_PERF_NAME_KEY, vmsJson);

        return resultJsonObj;
    }

    @Override
    public JSONObject getVMTopN(int n) {
        return getVMTopNInner(n, 0);
    }

    @Override
    public JSONObject getVMTopNBatch(int n) {
        // TODO Auto-generated method stub
        return getVMTopNInner(n, 1);
    }

    @Override
    public ClusterUI getAllClusterUIInfo() {
        // TODO Auto-generated method stub
        // List<Cluster> clusterList = this.resourceService.getClustersList();
        ClusterUI ui = new ClusterUI();

        double cpuTotalGHz = 0.0, cpuUsedGHz = 0.0, cpuFreeGhz = 0.0;
        double memTotalGB = 0.0, memUsedGB = 0.0, memFreeGB = 0.0;
        double storeTotalTB = 0.0, storeUsedTB = 0.0, storeFreeTB = 0.0;
        int hostNum = 0, hostAccessNum = 0, hostUnaccessNum = 0;
        int vmNum = 0, vmAccessNum = 0, vmUnaccessNum = 0;
        int storeNum = 0, storeAccessNum = 0, storeUnaccessNum = 0;
        double hostCpuTotal = 0.0, hostMemTotal = 0.0, hostStoreSpeedTotal = 0.0, hostNicSpeedTotal = 0.0;
        double vmCpuTotal = 0.0, vmMemTotal = 0.0, vmStoreSpeedTotal = 0.0, vmNicSpeedTotal = 0.0;

        int connectedHostNum = 0;
        // List<Host> hostList = new ArrayList<Host>();
        // List<VM> vmList = new ArrayList<VM>();
        List<AlarmEntity> alarmList = new ArrayList<AlarmEntity>();
        List<Host> allHostList = this.hostAndVmOp.getHostsEx();
        List<VM> allVmList = this.hostAndVmOp.getVmsEx();
        Cluster dss = this.resourceService.getAllDataStoreInfo();

        DecimalFormat df = new DecimalFormat("#0.00");
        DecimalFormat df1 = new DecimalFormat("#0.0000");

        if (null != allHostList && allHostList.size() > 0) {

            hostNum = allHostList.size();

            for (Host host : allHostList) {

                if (host.getConnectionStatus() != PerfConstants.HOST_CONN_STATUS_DISCONNECTED) {
                    connectedHostNum += 1;
                }

                cpuTotalGHz += Double.parseDouble(host.getCpuMHZTotal()) / 1000;
                cpuUsedGHz += Double.parseDouble(host.getCpuMHZUsed()) / 1000;
                cpuFreeGhz += cpuTotalGHz - cpuUsedGHz;

                memTotalGB += Double.parseDouble(host.getMemoryTotal()) / 1000;
                memUsedGB += Double.parseDouble(host.getMemoryUsed());
                memFreeGB += memTotalGB - memUsedGB;

                if (PerfConstants.OBJECT_STATUS_GREEN.equalsIgnoreCase(host.getStatus())) {
                    hostAccessNum += 1;
                } else {
                    hostUnaccessNum += 1;
                }

                hostCpuTotal += Double.parseDouble(host.getCpuUsage());
                hostMemTotal += Double.parseDouble(host.getMemoryUsage());
                hostStoreSpeedTotal += Double.parseDouble(host.getDiskIOSpeed());
                hostNicSpeedTotal += Double.parseDouble(host.getNetworkTransmitSpeed());
                if (null != host.getTriggeredAlarm()) {
                    alarmList.addAll(host.getTriggeredAlarm());
                }

            }

            ui.setCpuTotalGHz(df.format(cpuTotalGHz) + "");
            ui.setCpuUsedGHz(df.format(cpuUsedGHz) + "");
            ui.setCpuFreeGHz(df.format(cpuTotalGHz - cpuUsedGHz) + "");
            if (cpuTotalGHz == 0) {
                ui.setCpuUsage(df.format(0.0) + "");
            } else {
                ui.setCpuUsage(df1.format(cpuUsedGHz / cpuTotalGHz) + "");
            }

            ui.setMemTotalGB(df.format(memTotalGB) + "");
            ui.setMemUsedGB(df.format(memUsedGB) + "");
            ui.setMemFreeGB(df.format(memTotalGB - memUsedGB) + "");
            if (memTotalGB == 0) {
                ui.setMemUsage(df.format(0.0) + "");
            } else {
                ui.setMemUsage(df1.format(memUsedGB / memTotalGB) + "");
            }

            ui.setHostNum(hostNum + "");
            ui.setHostAccessibleNum(hostAccessNum + "");
            ui.setHostUnaccessibleNum(hostUnaccessNum + "");
            ui.setAvgHostCpuUsage(df.format(cpuUsedGHz / cpuTotalGHz * 100));
            ui.setAvgHostMemUsage(df.format(memUsedGB / memTotalGB * 100)); // 使用与整体使用率一样的值
            ui.setAvgHostDiskIO((int) (hostStoreSpeedTotal / connectedHostNum) + "");
            ui.setAvgHostNetIO((int) (hostNicSpeedTotal / connectedHostNum) + "");
            ui.setTop3HostCpuUsage(this.hostAndVmOp.getHostCpuUsageTopN(allHostList, 3));
            ui.setTop3HostMemUsage(this.hostAndVmOp.getHostMemUsageTopN(allHostList, 3));
            ui.setTop3HostDiskIO(this.hostAndVmOp.getHostDiskIoTopN(allHostList, 3));
            ui.setTop3HostNetIO(this.hostAndVmOp.getHostNetIoTopN(allHostList, 3));

        }

        if (null != allVmList && allVmList.size() > 0) {
            vmNum = allVmList.size();
            for (VM vm : allVmList) {
                if (PerfConstants.OBJECT_STATUS_GREEN.equalsIgnoreCase(vm.getStatus())) {
                    vmAccessNum += 1;
                } else {
                    vmUnaccessNum += 1;
                }
                vmCpuTotal += Double.parseDouble(vm.getCpuUsage());
                vmMemTotal += Double.parseDouble(vm.getMemoryUsage());
                vmStoreSpeedTotal += Double.parseDouble(vm.getDiskIOSpeed());
                vmNicSpeedTotal += Double.parseDouble(vm.getNetworkTransmitSpeed());
                if (null != vm.getTriggeredAlarm()) {
                    alarmList.addAll(vm.getTriggeredAlarm());
                }

            }
            ui.setVmNum(vmNum + "");
            ui.setVmAccessibleNum(vmAccessNum + "");
            ui.setVmUnaccessibleNum(vmUnaccessNum + "");
            ui.setAvgVMCpuUsage(df.format(vmCpuTotal / vmNum));
            ui.setAvgVMMemUsage(df.format(vmMemTotal / vmNum));
            ui.setAvgVMDiskIO((int) (vmStoreSpeedTotal / vmNum) + "");
            ui.setAvgVMNetIO((int) (vmNicSpeedTotal / vmNum) + "");
            ui.setTop3VMCpuUsage(this.hostAndVmOp.getVmCpuUsageTopN(allVmList, 3));
            ui.setTop3VMMemUsage(this.hostAndVmOp.getVmMemUsageTopN(allVmList, 3));
            ui.setTop3VMDiskIO(this.hostAndVmOp.getVmDiskIoTopN(allVmList, 3));
            ui.setTop3VMNetIO(this.hostAndVmOp.getVmNetIoTopN(allVmList, 3));
        }

        if (null != dss) {

            ui.setStoreTotalTB(df.format(dss.getStoreTB()) + "");
            ui.setStoreUsedTB(df.format(dss.getStoreUsedTB()) + "");
            ui.setStoreFreeTB(df.format(dss.getStoreTB() - dss.getStoreUsedTB()) + "");

            if (dss.getStoreTB() == 0) {
                ui.setStoreUsage(df.format(0.0) + "");
            } else {
                ui.setStoreUsage(df1.format(dss.getStoreUsedTB() / dss.getStoreTB()) + "");
            }
            ui.setStoreNum(Integer.toString(dss.getStoreNum()));
            ui.setStoreAccessibleNum(Integer.toString(dss.getStoreAccessibleNum()));
            ui.setStoreUnaccessibleNum(Integer.toString(dss.getStoreUnaccessibleNum()));

            alarmList.addAll(dss.getAlarmList());

            // 按告警发生的时间降序排列
            Collections.sort(alarmList, new Comparator<AlarmEntity>() {
                @Override
                public int compare(AlarmEntity o1, AlarmEntity o2) {
                    // TODO Auto-generated method stub
                    if (o1.getTime().compareTo(o2.getTime()) > 0) {
                        return -1;
                    } else if (o1.getTime().compareTo(o2.getTime()) < 0) {
                        return 1;
                    }
                    return 0;
                }

            });
            System.out.println("alarmList " + alarmList.size() + "  " + alarmList.get(0).getTime());
            
            //将全部告警进行分类，根据物理机、虚拟机、存储、告警级别进行分类
            int host_warning=0;
            int host_ciritical=0;
            int vm_warning=0;
            int vm_ciritical=0;
            int store_warning=0;
            int store_ciritical=0;
            for(AlarmEntity ame:alarmList)		{
            	if(ame.getLevel().contains("warning") && ame.getEntityId().contains("host") ){
            		host_warning++;
            	}else if(ame.getLevel().contains("critical") && ame.getEntityId().contains("host") ){
            		host_ciritical++;
            	}else if(ame.getLevel().contains("warning") && ame.getEntityId().contains("vm") ){
            		vm_warning++;
            	}else if(ame.getLevel().contains("critical") && ame.getEntityId().contains("vm") ){
            		vm_ciritical++;
            	}else if(ame.getLevel().contains("warning") && ame.getEntityId().contains("datastore") ){
            		store_warning++;
            	}else if(ame.getLevel().contains("critical") && ame.getEntityId().contains("datastore") ){
            		store_ciritical++;
            	}
            }
            ui.setVm_ciritical(vm_ciritical);
            ui.setVm_warning(vm_warning);
            ui.setHost_ciritical(host_ciritical);
            ui.setHost_warning(host_warning);
            ui.setStore_ciritical(store_ciritical);
            ui.setStore_warning(store_warning);
            
            ui.setAlarmList(PerfUtils.filterAlarm(alarmList, 20)); //最近20条告警
            System.out.println("filterAlarm " + ui.getAlarmList().size());

        }
        return ui;
    }

    @Override
    public JSONObject getClusterInfo(ClusterUI cu) {
        // TODO Auto-generated method stub
        JSONObject retJson = new JSONObject();
        JSONObject hostCpuUsage = PerfUtils.createPerfJsonObject(cu.getTop3HostCpuUsage(), PerfConstants.CPU_USAGE_ID,
                "host", "value", 1);
        JSONObject hostMemuUsage = PerfUtils.createPerfJsonObject(cu.getTop3HostMemUsage(), PerfConstants.MEM_USAGE_ID,
                "host", "value", 1);
        JSONObject hostDiskIO = PerfUtils.createPerfJsonObject(cu.getTop3HostDiskIO(), PerfConstants.DISK_IO_SPEED_ID,
                "host", "value", 1);
        JSONObject hostNetIo = PerfUtils.createPerfJsonObject(cu.getTop3HostNetIO(), PerfConstants.NET_IO_SPEED_ID,
                "host", "value", 1);

        JSONObject vmCpuUsage = PerfUtils.createVmPerfJsonObject(cu.getTop3VMCpuUsage(), PerfConstants.CPU_USAGE_ID,
                "vm", "value", 1);
        JSONObject vmMemuUsage = PerfUtils.createVmPerfJsonObject(cu.getTop3VMMemUsage(), PerfConstants.MEM_USAGE_ID,
                "vm", "value", 1);
        JSONObject vmDiskIO = PerfUtils.createVmPerfJsonObject(cu.getTop3VMDiskIO(), PerfConstants.VDISK_IO_SPEED_ID,
                "vm", "value", 1);
        JSONObject vmNetIo = PerfUtils.createVmPerfJsonObject(cu.getTop3VMNetIO(), PerfConstants.NET_IO_SPEED_ID, "vm",
                "value", 1);

        // retJson.put("clusters", cu);
        DecimalFormat df = new DecimalFormat("#0.00");

        retJson.put("avgHostCpuUsage", cu.getAvgHostCpuUsage());
        retJson.put("avgHostMemUsage", cu.getAvgHostMemUsage());
        retJson.put("avgHostDiskIO", cu.getAvgHostDiskIO());
        retJson.put("avgHostNetIO", cu.getAvgHostNetIO());

        retJson.put("topHostCpuUsage", 100);
        retJson.put("topHostMemUsage", 100);
        retJson.put("topVMCpuUsage", 100);
        retJson.put("topVMMemUsage", 100);

        DecimalFormat df1 = new DecimalFormat("#");

        if (Integer.parseInt(cu.getHostNum()) > 0) {
            String topHostDiskIO = df1.format(
                    Math.round(Double.valueOf(cu.getTop3HostDiskIO().get(0).getDiskIOSpeed()) / 10.0 + 1.0) * 10);
            retJson.put("topHostDiskIO", topHostDiskIO);
            // retJson.put("topHostDiskIO",
            // Integer.parseInt(cu.getTop3HostDiskIO().get(0).getDiskIOSpeed()));
            if (null != cu.getTop3HostNetIO()) {
                String topHostNetIO = df1.format(
                        Math.round(Double.valueOf(cu.getTop3HostNetIO().get(0).getNetworkTransmitSpeed()) / 10.0 + 1.0)
                                * 10);
                retJson.put("topHostNetIO", topHostNetIO);
                // retJson.put("topHostNetIO",
                // Integer.parseInt(cu.getTop3HostNetIO().get(0).getNetworkTransmitSpeed()));
            } else {
                retJson.put("topHostNetIO", 100);
            }

        } else {
            retJson.put("topHostDiskIO", 100);
            retJson.put("topHostNetIO", 100);

        }

        if (Integer.parseInt(cu.getVmNum()) > 0) {
            String topVMDiskIO = df1
                    .format(Math.round(Double.valueOf(cu.getTop3VMDiskIO().get(0).getDiskIOSpeed()) / 10.0 + 1.0) * 10);
            retJson.put("topVMDiskIO", topVMDiskIO);
            String topVMNetIO = df1.format(
                    Math.round(Double.valueOf(cu.getTop3VMNetIO().get(0).getNetworkTransmitSpeed()) / 10.0 + 1.0) * 10);
            retJson.put("topVMNetIO", topVMNetIO);
            // retJson.put("topVMDiskIO",
            // Integer.parseInt(cu.getTop3VMDiskIO().get(0).getDiskIOSpeed()));
            // retJson.put("topVMNetIO",
            // Integer.parseInt(cu.getTop3VMNetIO().get(0).getNetworkTransmitSpeed()));

        } else {
            retJson.put("topVMDiskIO", 100);
            retJson.put("topVMNetIO", 100);
        }

        retJson.put("avgVMCpuUsage", cu.getAvgVMCpuUsage());
        retJson.put("avgVMMemUsage", cu.getAvgVMMemUsage());
        retJson.put("avgVMDiskIO", cu.getAvgVMDiskIO());
        retJson.put("avgVMNetIO", cu.getAvgVMNetIO());

        retJson.put("alarmList", cu.getAlarmList());

        retJson.put("hostCpuUsage", hostCpuUsage);
        retJson.put("hostMemUsage", hostMemuUsage);
        retJson.put("hostDiskIo", hostDiskIO);
        retJson.put("hostNetIo", hostNetIo);

        retJson.put("vmCpuUsage", vmCpuUsage);
        retJson.put("vmMemUsage", vmMemuUsage);
        retJson.put("vmDiskIo", vmDiskIO);
        retJson.put("vmNetIo", vmNetIo);

        // 获取物理机、虚拟机、存储状态饼图
        JSONArray stautsArray = new JSONArray();
        // 获取物理机对象的正常也异常状态信息，并组装成JSON对象
        for (int i = 0; i < 3; i++) {
            JSONObject mapObjError1 = new JSONObject();
            JSONObject mapObjOk1 = new JSONObject();
            mapObjError1.put("name", "异常");
            mapObjOk1.put("name", "正常");
            if (i == 0) {
                // 获取物理机状态对象
                mapObjError1.put("value", cu.getHostUnaccessibleNum());
                mapObjOk1.put("value", cu.getHostAccessibleNum());
            } else if (i == 1) {
                // 获取虚拟机状态对象
                mapObjError1.put("value", cu.getVmUnaccessibleNum());
                mapObjOk1.put("value", cu.getVmAccessibleNum());
            } else if (i == 2) {
                // 获取存储对象状态对象
                mapObjError1.put("value", cu.getStoreUnaccessibleNum());
                mapObjOk1.put("value", cu.getStoreAccessibleNum());
            }
            // 数组 + map
            JSONArray data1 = new JSONArray();
            data1.add(0, mapObjError1);
            data1.add(1, mapObjOk1);
            stautsArray.add(data1);
        }
        retJson.put("hostVmStoreStatus", stautsArray);

        // 传递cpu、存储、磁盘利用率条形图所需JSON数据
        // CPU整体视图
        String cpuTotalGHz = cu.getCpuTotalGHz();
        retJson.put("cpuTotalGHz", cpuTotalGHz);
        String cpuUsedGHz = cu.getCpuUsedGHz();
        retJson.put("cpuUsedGHz", cpuUsedGHz);
        String cpuFreeGHz = cu.getCpuFreeGHz();
        retJson.put("cpuFreeGHz", cpuFreeGHz);
        String cpuUsage = cu.getCpuUsage();
        if (cpuUsage != null) {
            retJson.put("cpuUsage", df.format(Double.parseDouble(cpuUsage) * 100));
        }
        // 内存整体视图
        String memTotalGB = cu.getMemTotalGB();
        retJson.put("memTotalGB", memTotalGB);
        String memUsedGB = cu.getMemUsedGB();
        retJson.put("memUsedGB", memUsedGB);
        String memFreeGB = cu.getMemFreeGB();
        retJson.put("memFreeGB", memFreeGB);
        String memUsage = cu.getMemUsage();
        if (memUsage != null) {
            retJson.put("memUsage", df.format(Double.parseDouble(memUsage) * 100));
        }
        // 存储整体视图
        String storeTotalTB = cu.getStoreTotalTB();
        retJson.put("storeTotalTB", storeTotalTB);
        String storeUsedTB = cu.getStoreUsedTB();
        retJson.put("storeUsedTB", storeUsedTB);
        String storeFreeTB = cu.getStoreFreeTB();
        retJson.put("storeFreeTB", storeFreeTB);
        String storeUsage = cu.getStoreUsage();
        // System.out.println("storeUsage=====" + storeUsage);
        if (storeUsage != null) {
            retJson.put("storeUsage", df.format(Double.parseDouble(storeUsage) * 100));
        }

        // 群集内部主机 虚拟机 存储的总量，正常数量，异常数量
        String hostNum = cu.getHostNum();
        retJson.put("hostNum", hostNum);
        String hostAccessibleNum = cu.getHostAccessibleNum();
        retJson.put("hostAccessibleNum", hostAccessibleNum);
        String hostUnaccessibleNum = cu.getHostUnaccessibleNum();
        retJson.put("hostUnaccessibleNum", hostUnaccessibleNum);

        String vmNum = cu.getVmNum();
        retJson.put("vmNum", vmNum);
        String vmAccessibleNum = cu.getVmAccessibleNum();
        retJson.put("vmAccessibleNum", vmAccessibleNum);
        String vmUnaccessibleNum = cu.getVmUnaccessibleNum();
        retJson.put("vmUnaccessibleNum", vmUnaccessibleNum);

        String storeNum = cu.getStoreNum();
        retJson.put("storeNum", storeNum);
        String storeAccessibleNum = cu.getStoreAccessibleNum();
        retJson.put("storeAccessibleNum", storeAccessibleNum);
        String storeUnaccessibleNum = cu.getStoreUnaccessibleNum();
        retJson.put("storeUnaccessibleNum", storeUnaccessibleNum);
        String cpuUsagePie = null;
        String memUsagePie = null;
        String diskUsagePie = null;
        if (cpuUsage != null) {
            cpuUsagePie = String.valueOf(Math.round(Double.valueOf(cpuUsage) * 100));
        }

        if (memUsage != null) {
            memUsagePie = String.valueOf(Math.round(Double.valueOf(memUsage) * 100));
        }
        if (storeUsage != null) {
            diskUsagePie = String.valueOf(Math.round(Double.valueOf(storeUsage) * 100));
        }

        retJson.put("cpuUsagePie", cpuUsagePie);
        retJson.put("memUsagePie", memUsagePie);
        retJson.put("diskUsagePie", diskUsagePie);
        
        //将告警分类状态数据放到json串中
        retJson.put("host_warning", cu.getHost_warning());
        retJson.put("host_ciritical", cu.getHost_ciritical());
        retJson.put("vm_warning", cu.getVm_warning());
        retJson.put("vm_ciritical", cu.getVm_ciritical());
        retJson.put("store_warning", cu.getStore_warning());
        retJson.put("store_ciritical", cu.getStore_ciritical());
     
        return retJson;
    }

	@Override
	public List<AlarmEntity> getAllTriggeredAlarms() {
		List<AlarmEntity> result=new ArrayList<AlarmEntity>();
		  VCenterManageUtils vcenterUtil = new VCenterManageUtils(con);
		  if(vcenterUtil!=null ){
				result=historyPerfAndAlertService.getTriggeredAlarms(vcenterUtil.getRootFolder());
		  }
		return result;
	}

    
}
