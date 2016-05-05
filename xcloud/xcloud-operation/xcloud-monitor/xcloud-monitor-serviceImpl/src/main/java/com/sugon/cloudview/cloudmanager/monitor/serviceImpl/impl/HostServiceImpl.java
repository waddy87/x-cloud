/**
 * 
 */
package com.sugon.cloudview.cloudmanager.monitor.serviceImpl.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sugon.cloudview.cloudmanager.monitor.service.bo.HostBo;
import com.sugon.cloudview.cloudmanager.monitor.service.exception.CloudViewPerfException;
import com.sugon.cloudview.cloudmanager.monitor.service.service.HostService;
import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.entity.Host;
import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.entity.VM;
import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.service.HistoryPerfAndAlertServiceI;
import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.service.HostAndVmPerfOp;
import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.util.Connection;
import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.util.PerfConstants;
import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.util.PerfUtils;
import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.util.ToolsUtils;
import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.util.VCenterManageUtils;
import com.sugon.vim25.HostSystemConnectionState;
import com.sugon.vim25.HostSystemPowerState;
import com.sugon.vim25.ManagedEntityStatus;
import com.sugon.vim25.mo.Datastore;
import com.sugon.vim25.mo.HostSystem;
import com.sugon.vim25.mo.VirtualMachine;

/**
 * 功能名: 请填写功能名 功能描述: 请简要描述功能的要点 Copyright: Copyright (c) 2016 公司: 曙光云计算技术有限公司
 *
 * @author wq
 * @version 2.0.0 sp1
 */
@Service("monitor-hostServiceImpl")
public class HostServiceImpl implements HostService {
    private static final Logger logger = LoggerFactory.getLogger(HostServiceImpl.class);

    // 物理机和虚拟机性能服务
    @Qualifier("monitor-hostAndVmPerfOpImpl")
    @Autowired
    private HostAndVmPerfOp hostAndVmPerfOp;

    @Qualifier("monitor-connection")
    @Autowired
    private Connection connection;
    String accessble = "accessble";
    String unaccessble = "unaccessble";
    private VCenterManageUtils vCenterManageUtils;

    @Autowired
    private ToolsUtils toolsUtils;

    @Qualifier("monitor-historyPerfAndAlertServiceImpl")
    @Autowired
    private HistoryPerfAndAlertServiceI alarmUtil;

    @Qualifier("monitor-resourceServiceImpl")
    @Autowired
    private ResourceServiceImpl resourceServiceImpl;

    @Override
    public List<HostBo> getHosts() {
        List<Host> hosts = hostAndVmPerfOp.getHostsEx();
        List<HostBo> hostBos = new ArrayList<HostBo>();
        for (Host host : hosts) {
    
            try {
				HostBo hostBo = new HostBo();
				toolsUtils.convert(host, hostBo);
				double cpuGHzTotal = toolsUtils.CpuMHzToGHz(Double.valueOf(hostBo.getCpuMHZTotal()));
				double cpuGHzUsed = toolsUtils.CpuMHzToGHz(Double.valueOf(hostBo.getCpuMHZUsed()));
				double memGBTotal = toolsUtils.MembyteToGB(Double.valueOf(hostBo.getMemoryTotal()));
				double memGBUsed = toolsUtils.MemMBToGB(Double.valueOf(hostBo.getMemoryUsed()));
				double storeTBTotal = toolsUtils.StoreMBToTB(Double.valueOf(hostBo.getDiskTotal()));
				double storeTBUsed = toolsUtils.StoreMBToTB(Double.valueOf(hostBo.getDiskUsed()));
				hostBo.setCpuGHzTotal(cpuGHzTotal);
				hostBo.setCpuGHzUsed(cpuGHzUsed);
				hostBo.setMemGBTotal(memGBTotal);
				hostBo.setMemGBUsed(memGBUsed);
				hostBo.setStoreTBTotal(storeTBTotal);
				hostBo.setStoreTBUsed(storeTBUsed);
				hostBos.add(hostBo);
			} catch (Exception e) {
				
			}
        }

        return hostBos;
    }

    @Override
    public HostBo getHostById(String hostId) {
        HostSystem hostSystem = null;
        try {
            hostSystem = getVCenterManageUtils().getHostSystemById(hostId);
        } catch (CloudViewPerfException e) {
            e.printStackTrace();
        }

        if (hostSystem != null) {
            Host host = new Host();
            host.setName(hostSystem.getName());
            host.setIpAddr(hostSystem.getName());// getName返回的是主机名或者IP地址
            host.setId(hostSystem.getMOR().getVal());

            // 所属数据中心
            try {
            	if(hostSystem.getParent()!=null && hostSystem.getParent().getParent()!=null && hostSystem.getParent().getParent().getParent()!=null ){
            		   host.setDataCenterName(hostSystem.getParent().getParent().getParent().getName());
            	}
             
            } catch (Exception e) {
                host.setDataCenterName("unknown");
                logger.debug("获取数据中心异常"+e);
                //e.printStackTrace();
            }

            // 所属集群
            try {
                host.setClusterName(hostSystem.getParent().getName());
            } catch (Exception e) {
                host.setClusterName("unknown");
                e.printStackTrace();
            }

            // 断开主机特殊处理
            if (hostSystem.getRuntime().getConnectionState() == HostSystemConnectionState.disconnected) {

                PerfUtils.InitialHost(host);
                host.setConnectionStatus(
                        ToolsUtils.convertHostConnectionStatusToString(hostSystem.getRuntime().getConnectionState()));
            }

            long starTime = System.currentTimeMillis();
            Map<String, String> commonInfo = PerfUtils.getHostCommonPerf(hostSystem);
            Map<String, String> otherInfo = PerfUtils.getHostOtherPerf(hostSystem);

            String hostName = hostSystem.getName();
            HostSystemPowerState powerStatus = hostSystem.getRuntime().getPowerState();
            ManagedEntityStatus mEnStatus = hostSystem.getSummary().getOverallStatus();

            // 虚拟机列表
            try {
                VirtualMachine[] vms = hostSystem.getVms();
                List<VM> vmList = new ArrayList<VM>();
                if (null != vms && vms.length > 0) {
                    for (VirtualMachine virtualMachine : vms) {
                        VM vm = resourceServiceImpl.getVMBasicInfo(virtualMachine, hostSystem);
                        vmList.add(vm);
                    }
                }
                host.setVmList(vmList);
                Map<String, Integer> vmMap = resourceServiceImpl.getVmStatusInfo(vms);
                host.setVmNum(vms.length);
                host.setVmAccessibleNum(vmMap.get(accessble));
                host.setVmUnaccessibleNum(vmMap.get(unaccessble));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Datastore[] dss = hostSystem.getDatastores();
                Map<String, Integer> dssMap = resourceServiceImpl.getStoreStatusInfo(dss);
                host.setStoreNum(dss.length);
                host.setStoreAccessibleNum(dssMap.get(accessble));
                host.setStoreUnaccessibleNum(dssMap.get(unaccessble));
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 获取已触发告警信息
            host.setTriggeredAlarm(alarmUtil.getTriggeredAlarms(hostSystem));

            try {

                // 设置电源状态
                host.setPowerStatus(ToolsUtils.convertHostPowerStatusToString(powerStatus));

                // 设置主机运行状态，是否正常
                host.setStatus(ToolsUtils.convertManageObjectStatusToString(mEnStatus));

                // 设置连接状态
                host.setConnectionStatus(
                        ToolsUtils.convertHostConnectionStatusToString(hostSystem.getRuntime().getConnectionState()));

                host.setCpuMHZUsed(commonInfo.get(PerfConstants.CPU_USED_ID));
                host.setCpuUsage(commonInfo.get(PerfConstants.CPU_USAGE_ID));

                host.setMemoryUsed(commonInfo.get(PerfConstants.MEM_USED_ID));
                host.setMemoryUsage(commonInfo.get(PerfConstants.MEM_USAGE_ID));

                host.setDiskReadSpeed(ToolsUtils.GetMapValue(otherInfo, PerfConstants.DISK_READ_ID));
                host.setDiskWriteSpeed(ToolsUtils.GetMapValue(otherInfo, PerfConstants.DISK_WRITE_ID));

                long diskIOSpeed = Long.valueOf(ToolsUtils.GetMapValue(otherInfo, PerfConstants.DISK_READ_ID))
                        + Long.valueOf(ToolsUtils.GetMapValue(otherInfo, PerfConstants.DISK_WRITE_ID));
                host.setDiskIOSpeed(String.valueOf(diskIOSpeed));

                long diskIOPS = Long.valueOf(ToolsUtils.GetMapValue(otherInfo, PerfConstants.DISK_IOPS_READ_ID))
                        + Long.valueOf(ToolsUtils.GetMapValue(otherInfo, PerfConstants.DISK_IOPS_WRITE_ID));
                host.setDiskIops(String.valueOf(diskIOPS));

                host.setNetworkReceiveSpeed(ToolsUtils.GetMapValue(otherInfo, PerfConstants.NET_RX_ID));
                host.setNetworkSendSpeed(ToolsUtils.GetMapValue(otherInfo, PerfConstants.NET_TX_ID));
                host.setNetworkTransmitSpeed(ToolsUtils.GetMapValue(otherInfo, PerfConstants.NET_IO_SPEED_ID));

                host.setCpuMHZTotal(commonInfo.get(PerfConstants.CPU_TOTAL_ID));
                host.setMemoryTotal(commonInfo.get(PerfConstants.MEM_TOTAL_ID));

                host.setDiskTotal(commonInfo.get(PerfConstants.DISK_TOTAL_ID));
                host.setDiskUsed(commonInfo.get(PerfConstants.DISK_USED_ID));

                double diskTotal = Double.valueOf(commonInfo.get(PerfConstants.DISK_TOTAL_ID));
                double usedSpace = Double.valueOf(commonInfo.get(PerfConstants.DISK_USED_ID));
                double diskUsagePercent = 0.0;

                if (Math.abs(diskTotal - 0) > 0.0001) {
                    BigDecimal diskUsageBd = new BigDecimal((usedSpace / diskTotal) * 100);
                    diskUsagePercent = diskUsageBd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                host.setDiskUsage(String.valueOf(diskUsagePercent));

                long endTime = System.currentTimeMillis();
                long Time = endTime - starTime;
                System.out.println("**********************************主机信息花费：" + Time + "ms");
            } catch (Exception e) {
                e.printStackTrace();
            }

            HostBo hostBo = new HostBo();
            toolsUtils.convert(host, hostBo);
            double cpuGHzTotal = toolsUtils.CpuMHzToGHz(Double.valueOf(hostBo.getCpuMHZTotal()));
            double cpuGHzUsed = toolsUtils.CpuMHzToGHz(Double.valueOf(hostBo.getCpuMHZUsed()));
            double memGBTotal = toolsUtils.MemMBToGB(Double.valueOf(hostBo.getMemoryTotal()));
            double memGBUsed = toolsUtils.MemMBToGB(Double.valueOf(hostBo.getMemoryUsed()));
            double storeTBTotal = toolsUtils.StoreMBToTB(Double.valueOf(hostBo.getDiskTotal()));
            double storeTBUsed = toolsUtils.StoreMBToTB(Double.valueOf(hostBo.getDiskUsed()));
            hostBo.setCpuGHzTotal(cpuGHzTotal);
            hostBo.setCpuGHzUsed(cpuGHzUsed);
            hostBo.setMemGBTotal(memGBTotal);
            hostBo.setMemGBUsed(memGBUsed);
            hostBo.setStoreTBTotal(storeTBTotal);
            hostBo.setStoreTBUsed(storeTBUsed);
            return hostBo;
        } else {
            return null;
        }
    }

    /**
     * @return the opUtil
     */
    public VCenterManageUtils getVCenterManageUtils() {
        this.vCenterManageUtils = new VCenterManageUtils(this.connection);
        return vCenterManageUtils;
    }
}
