package org.waddys.xcloud.monitor.serviceImpl.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.waddys.xcloud.monitor.service.bo.ClusterBo;
import org.waddys.xcloud.monitor.service.exception.CloudViewPerfException;
import org.waddys.xcloud.monitor.service.service.ClusterService;
import org.waddys.xcloud.monitor.serviceImpl.entity.Cluster;
import org.waddys.xcloud.monitor.serviceImpl.service.HistoryPerfAndAlertServiceI;
import org.waddys.xcloud.monitor.serviceImpl.service.HostAndVmPerfOp;
import org.waddys.xcloud.monitor.serviceImpl.util.Connection;
import org.waddys.xcloud.monitor.serviceImpl.util.PerfConstants;
import org.waddys.xcloud.monitor.serviceImpl.util.PerfUtils;
import org.waddys.xcloud.monitor.serviceImpl.util.ToolsUtils;
import org.waddys.xcloud.monitor.serviceImpl.util.VCenterManageUtils;

import com.vmware.vim25.DatastoreSummary;
import com.vmware.vim25.HostHardwareInfo;
import com.vmware.vim25.HostListSummary;
import com.vmware.vim25.ManagedEntityStatus;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.VirtualMachine;


@Service("monitor-clusterServiceImpl")
public class ClusterServiceImpl implements ClusterService {
    private static final Logger logger = LoggerFactory.getLogger(ClusterServiceImpl.class);
    @Qualifier("monitor-connection")
    @Autowired
    private Connection connection;

    @Qualifier("monitor-toolsutils")
    @Autowired
    private ToolsUtils toolsUtils;

    @Qualifier("monitor-historyPerfAndAlertServiceImpl")
    @Autowired
    private HistoryPerfAndAlertServiceI historyPerfAndAlertService;

    @Qualifier("monitor-hostAndVmPerfOpImpl")
    @Autowired
    private HostAndVmPerfOp hostAndVmPerfOp;

    private VCenterManageUtils opUtil;

    @Override
    public ClusterBo getClusterById(String clusterId) {
        ClusterComputeResource clusterCR = null;
        ClusterBo clusterBo = null;
        try {
            clusterCR = this.getOpUtil().getClusterComputeResourceById(clusterId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            return clusterBo;
        }
        if (clusterCR == null) {
            return clusterBo;
        } else {
            Cluster cluster = ConvertClusterCRToCluseter(clusterCR);
            if (cluster == null) {
                return clusterBo;
            }
            try {
                clusterBo = new ClusterBo();
                clusterBo.setTriggeredAlarm(historyPerfAndAlertService.getTriggeredAlarms(clusterCR));
                // 加入主机、虚拟机详细列表
                cluster.setHostList(this.hostAndVmPerfOp.getHosts(cluster.getId()));
                cluster.setVmList(this.hostAndVmPerfOp.getVmsOnCluster(cluster.getId()));
                toolsUtils.convert(cluster, clusterBo);
            } catch (Exception e) {
                logger.error("----entity:" + cluster + "转bo:" + clusterBo + "失败！");
            }
        }

        return clusterBo;
    }

    @Override
    public List<ClusterBo> getClusters() {
        List<ClusterComputeResource> clusterComputeResources = null;
        List<ClusterBo> clusterBos = new ArrayList<ClusterBo>();
        try {
            clusterComputeResources = this.getOpUtil().getAllClusterComputeResources();
        } catch (CloudViewPerfException e) {
            logger.error("----获取全部集群失败！");
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            return clusterBos;
        }
        if (clusterComputeResources == null || clusterComputeResources.size() == 0) {
            return clusterBos;
        }
        for (int i = 0; i < clusterComputeResources.size(); i++) {
            ClusterComputeResource clusterCR = clusterComputeResources.get(i);
            Cluster cluster = ConvertClusterCRToCluseter(clusterCR);
            ClusterBo clusterBo = new ClusterBo();
            try {
                toolsUtils.convert(cluster, clusterBo);
                clusterBos.add(clusterBo);
            } catch (Exception e) {
                logger.error("----entity:" + cluster + "转bo:" + clusterBo + "失败！");
            }
        }
        return clusterBos;
    }

    /**
     * 功能: 将ClusterComputerResource对象转为Cluster对象
     *
     * @param clusterCR
     * @return
     */
    public Cluster ConvertClusterCRToCluseter(ClusterComputeResource clusterCR) {
        Cluster cluster = new Cluster();
        if (clusterCR == null) {
            return cluster;
        }
        
        try {
			cluster.setId(clusterCR.getMOR().get_value());
			cluster.setName(clusterCR.getName());

			int numHosts = clusterCR.getSummary().getNumHosts();
			cluster.setHostNumber(numHosts);

			 if( clusterCR.getParent()!=null  && clusterCR.getParent().getParent()!=null){
			    cluster.setDataCenterName(clusterCR.getParent().getParent().getName());
			 }
		} catch (Exception e1) {
			return cluster;
		}

        // 集群关联主机
        int connectedHostNum=0;
        
        int hostAccessibleNum = 0;
        int hostUnaccessibleNum = 0;
        int vmAccessibleNum = 0;
        int vmUnaccessibleNum = 0;
        int storeAccessibleNum = 0;
        int storeUnaccessibleNum = 0;
        int cpuNum = 0;
        long memoryTotal = 0;
        long memoryUsed = 0;
        long cpuHz = 0;
        long cpuMHzUsed = 0;

        long diskReadSpeed = 0;
        long diskWriteSpeed = 0;
        long diskIOPSReadSpeed = 0;
        long diskIOPSWriteSpeed = 0;
        long netRx = 0;
        long netTx = 0;
        HostSystem[] hostSystems = clusterCR.getHosts();
        if (hostSystems != null && hostSystems.length > 0) {
            for (int j = 0; j < hostSystems.length; j++) {
	           try {   
	                HostSystem system = hostSystems[j];
	
	                HostHardwareInfo hardwareInfo = system.getHardware();
	                HostListSummary hostListSummary = system.getSummary();
	
	                if (ManagedEntityStatus.green.equals(hostListSummary.getOverallStatus())) {
	                    hostAccessibleNum += 1;
	                } else {
	                    hostUnaccessibleNum += 1;
	                }
	                
	                if (PerfConstants.HOST_CONN_STATUS_CONNECTED.equals(system.getRuntime().getConnectionState())) {
	                    connectedHostNum += 1;
	                }
	
	                Map<String, String> hostMinitorData = PerfUtils.getHostOtherPerf(system);
	                if (hostMinitorData != null && hostMinitorData.size() > 0) {
	                    diskReadSpeed += Long.parseLong(hostMinitorData.get(PerfConstants.DISK_READ_ID));
	                    diskWriteSpeed += Long.parseLong(hostMinitorData.get(PerfConstants.DISK_WRITE_ID));
	                    diskIOPSReadSpeed += Long.parseLong(hostMinitorData.get(PerfConstants.DISK_IOPS_READ_ID));
	                    diskIOPSWriteSpeed += Long.parseLong(hostMinitorData.get(PerfConstants.DISK_IOPS_WRITE_ID));
	                    netRx += Long.parseLong(hostMinitorData.get(PerfConstants.NET_RX_ID));
	                    netTx += Long.parseLong(hostMinitorData.get(PerfConstants.NET_TX_ID));
	
	                }
	
	                cpuHz += hardwareInfo.cpuInfo.hz * hardwareInfo.cpuInfo.numCpuCores;
	                cpuNum += hostListSummary.getHardware().numCpuCores;
	                if (hostListSummary.quickStats.overallCpuUsage != null) {
	                    cpuMHzUsed += hostListSummary.quickStats.overallCpuUsage;
	                }
	
	                memoryTotal += hardwareInfo.memorySize;
	                if (hostListSummary.quickStats.overallMemoryUsage != null) {
	                    memoryUsed += hostListSummary.quickStats.overallMemoryUsage;
	                }
	
	         
	                    VirtualMachine[] hostvms = system.getVms();
	                    if (null != hostvms && hostvms.length > 0) {
	                        for (VirtualMachine vm : hostvms) {
	                        	if(vm==null || vm.getSummary()==null){
	                        		continue;
	                        	}
	                            if (ManagedEntityStatus.green.equals(vm.getSummary().getOverallStatus())) {
	                                vmAccessibleNum += 1;
	                            } else {
	                                vmUnaccessibleNum += 1;
	                            }
	
	                        }
	                    }
	                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    //e.printStackTrace();
                }
            }
        }
        cluster.setCpuNumber(Integer.toString(cpuNum));
        cluster.setCpuGHz(toolsUtils.CpuHzToGHz(cpuHz));
        cluster.setCpuUsedGHz(toolsUtils.CpuMHzToGHz(cpuMHzUsed));

        cluster.setCpuMHZTotal(Double.toString(toolsUtils.CpuHzToMHz(cpuHz)));
        cluster.setCpuMHZUsed(Double.toString(cpuMHzUsed));
        cluster.setCpuUsage(toolsUtils.getPercentage(cpuMHzUsed, toolsUtils.CpuHzToMHz(cpuHz)));
        cluster.setAvgCpuUsage(toolsUtils.getPercentage(cpuMHzUsed, toolsUtils.CpuHzToMHz(cpuHz)));

        cluster.setMemGB(toolsUtils.MembyteToGB(memoryTotal));
        cluster.setMemUsedGB(toolsUtils.MemMBToGB(memoryUsed));
        cluster.setMemoryUsage(toolsUtils.getPercentage(memoryUsed, toolsUtils.MembyteToMB(memoryTotal)));
        cluster.setAvgMemUsage(toolsUtils.getPercentage(memoryUsed, toolsUtils.MembyteToMB(memoryTotal)));

        cluster.setMemoryTotal(Double.toString(toolsUtils.MembyteToMB(memoryTotal)));
        cluster.setMemoryUsed(Double.toString(memoryUsed));

        cluster.setDiskReadSpeed(Long.toString(diskReadSpeed));
        cluster.setDiskWriteSpeed(Long.toString(diskWriteSpeed));
        cluster.setDiskIOSpeed(Long.toString(diskReadSpeed + diskWriteSpeed));
        cluster.setNetworkSendSpeed(Long.toString(netTx));
        cluster.setNetworkReceiveSpeed(Long.toString(netRx));
        cluster.setNetworkTransmitSpeed(Long.toString(netRx + netTx));
        cluster.setDiskIops(Long.toString(diskIOPSReadSpeed + diskIOPSWriteSpeed));

        cluster.setAvgDiskReadSpeed(toolsUtils.getAvg(diskReadSpeed, connectedHostNum));
        cluster.setAvgDiskWriteSpeed(toolsUtils.getAvg(diskWriteSpeed, connectedHostNum));
        cluster.setAvgNetwordReceive(toolsUtils.getAvg(netRx, connectedHostNum));
        cluster.setAvgNetworkSend(toolsUtils.getAvg(netTx, connectedHostNum));
        
        cluster.setHostAccessibleNum(hostAccessibleNum);
        cluster.setHostUnaccessibleNum(hostUnaccessibleNum);
        cluster.setHostNumber(hostAccessibleNum + hostUnaccessibleNum);

        cluster.setVmAccessibleNum(vmAccessibleNum);
        cluster.setVmUnaccessibleNum(vmUnaccessibleNum);
        cluster.setVmNum(vmAccessibleNum + vmUnaccessibleNum);
        
        // cluster.setHostList(this.hostAndVmPerfOp.getHosts(cluster.getId()));
        // cluster.setVmList(this.hostAndVmPerfOp.getVmsOnCluster(cluster.getId()));

        // 集群关联DataStore
        long storeTotal=0;
        long storeFree=0;
        long storeUsed = 0;
        Datastore[] datastores = clusterCR.getDatastores();
        if (datastores != null && datastores.length > 0) {
            for (int k = 0; k < datastores.length; k++) {
                Datastore datastore = datastores[k];

                DatastoreSummary dsSummary = datastore.getSummary();
                if(dsSummary!=null){
                	  if (dsSummary.accessible) {
                          storeAccessibleNum += 1;
                      } else {
                          storeUnaccessibleNum += 1;
                      }
                      storeFree += dsSummary.freeSpace;
                      storeTotal += dsSummary.capacity;
                }
            }
        }
        cluster.setStoreAccessibleNum(storeAccessibleNum);
        cluster.setStoreUnaccessibleNum(storeUnaccessibleNum);
        cluster.setStoreNum(storeAccessibleNum + storeUnaccessibleNum);

        storeUsed = storeTotal - storeFree;
        cluster.setStoreTB(toolsUtils.StorebyteToTB(storeTotal));
        cluster.setStoreUsedTB(toolsUtils.StorebyteToTB(storeUsed));
        cluster.setDiskTotal(Double.toString(toolsUtils.StorebyteToTB(storeTotal)));
        cluster.setDiskUsed(Double.toString(toolsUtils.StorebyteToTB(storeUsed)));

        cluster.setDiskUsage(toolsUtils.getPercentage(storeUsed, storeTotal));

        return cluster;
    }

    /**
     * @return the opUtil
     */
    public VCenterManageUtils getOpUtil() {
        // if (this.opUtil == null)
        this.opUtil = new VCenterManageUtils(this.connection);
        return opUtil;
    }

}
