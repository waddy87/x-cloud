package com.sugon.cloudview.cloudmanager.monitor.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sugon.cloudview.cloudmanager.monitor.service.bo.ClusterBo;
import com.sugon.cloudview.cloudmanager.monitor.service.bo.HostBo;
import com.sugon.cloudview.cloudmanager.monitor.service.bo.VMBo;
import com.sugon.cloudview.cloudmanager.monitor.service.service.ClusterService;
import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.util.HistoryData;
import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.util.PerfConstants;
import com.sugon.cloudview.cloudmanager.monitor.serviceImpl.util.ToolsUtils;

import net.sf.json.JSONObject;


/**
 * 功能名: 请填写功能名
 * 功能描述: 请简要描述功能的要点
 * Copyright: Copyright (c) 2016
 * 公司: 曙光云计算有限公司
 *
 * @author Xuby
 * @version 2.0sp1
 */
@Controller("monitor-clustercontroller")
@RequestMapping("/monitor/cluster")
public class ClusterController {
    @Qualifier("monitor-clusterServiceImpl")
    @Autowired
    private ClusterService clusterService;

    @Qualifier("monitor-history")
    @Autowired
    private HistoryData historyData;

    @Qualifier("monitor-toolsutils")
    @Autowired
    private ToolsUtils toolsUtils;

    @RequestMapping("/clusterTopN")
    @ResponseBody
    public JSONObject getClusterTopN() {
        // return clusterService.getClusters();
        JSONObject result = new JSONObject();
        List<ClusterBo> clusterBos = clusterService.getClusters();
        int topN = 10;
        result.put("clusterList", clusterBos);
        result.put("topnCpuUsage", toolsUtils.getResourceMetricTopN(clusterBos, topN, PerfConstants.CPU_USAGE_ID));
        result.put("topnCpuUsed", toolsUtils.getResourceMetricTopN(clusterBos, topN, PerfConstants.CPU_USED_ID));
        result.put("topnDiskIO", toolsUtils.getResourceMetricTopN(clusterBos, topN, PerfConstants.DISK_IO_SPEED_ID));
        result.put("topnHostNum", toolsUtils.getResourceMetricTopN(clusterBos, topN, PerfConstants.Host_NUMS_ID));
        result.put("topnVMNum", toolsUtils.getResourceMetricTopN(clusterBos, topN, PerfConstants.VM_NUMS_ID));
        result.put("topnMemUsage", toolsUtils.getResourceMetricTopN(clusterBos, topN, PerfConstants.MEM_USAGE_ID));
        result.put("topnMemUsed", toolsUtils.getResourceMetricTopN(clusterBos, topN, PerfConstants.MEM_USED_ID));
        result.put("topnNetIO", toolsUtils.getResourceMetricTopN(clusterBos, topN, PerfConstants.NET_IO_SPEED_ID));

        return result;
    }
    
    @RequestMapping("/clusterList")
    @ResponseBody
    public JSONObject getClusterList() {
        // return clusterService.getClusters();
        JSONObject result = new JSONObject();
        List<ClusterBo> clusterBos = clusterService.getClusters();
        String total = Integer.toString(clusterBos.size());
        result.put("total", total);
        result.put("rows", clusterBos);
        // result.put("clusterList", clusterBos);
        // System.err.println("----" + result);
        return result;
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getClusterById(@RequestBody String clusterId) {
        System.err.println("----" + clusterId);
        // return clusterService.getClusterById(clusterId);
        JSONObject result = new JSONObject();
        ClusterBo clusterBo = clusterService.getClusterById(clusterId);
        result.put("cluster", clusterBo);
        List<HostBo> hostBos = clusterBo.getHostList();
        List<VMBo> vmBos = clusterBo.getVmList();
        int topN = 3;
        result.put("topnHostCpuUsage", toolsUtils.getResourceMetricTopN(hostBos, topN, PerfConstants.CPU_USAGE_ID));
        result.put("topnHostMemUsage", toolsUtils.getResourceMetricTopN(hostBos, topN, PerfConstants.MEM_USAGE_ID));
        result.put("topnHostDiskIO", toolsUtils.getResourceMetricTopN(hostBos, topN, PerfConstants.DISK_IO_SPEED_ID));
        result.put("topnHostNetIO", toolsUtils.getResourceMetricTopN(hostBos, topN, PerfConstants.NET_IO_SPEED_ID));
        result.put("topnVMCpuUsage", toolsUtils.getResourceMetricTopN(vmBos, topN, PerfConstants.CPU_USAGE_ID));
        result.put("topnVMMemUsage", toolsUtils.getResourceMetricTopN(vmBos, topN, PerfConstants.MEM_USAGE_ID));
        result.put("topnVMDiskIO", toolsUtils.getResourceMetricTopN(vmBos, topN, PerfConstants.DISK_IO_SPEED_ID));
        result.put("topnVMNetIO", toolsUtils.getResourceMetricTopN(vmBos, topN, PerfConstants.NET_IO_SPEED_ID));

        result.put("history",
                historyData.getJsonHistoryData(clusterId, PerfConstants.ENTITY_CLUSTER, PerfConstants.HISTORY_ONEDAY));

        return result;
    }

    @RequestMapping("/history/{id}/{type}")
    @ResponseBody
    public JSONObject getClusterHistoryData(@PathVariable("id") String clusterId,
            @PathVariable("type") String historyType) {
        return historyData.getJsonHistoryData(clusterId, PerfConstants.ENTITY_CLUSTER, historyType);
    }
}
