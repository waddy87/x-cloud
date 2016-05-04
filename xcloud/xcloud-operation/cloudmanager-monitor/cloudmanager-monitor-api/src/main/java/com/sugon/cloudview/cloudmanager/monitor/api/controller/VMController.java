package com.sugon.cloudview.cloudmanager.monitor.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sugon.cloudview.cloudmanager.monitor.service.bo.VMBo;
import com.sugon.cloudview.cloudmanager.monitor.service.service.VMService;
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
@Controller("monitor-vmcontroller")
@RequestMapping("/monitor/vm")
public class VMController {
    @Qualifier("monitor-vmServiceImpl")
    @Autowired
    private VMService vmService;

    @Qualifier("monitor-toolsutils")
    @Autowired
    private ToolsUtils toolsUtils;

    @Qualifier("monitor-history")
    @Autowired
    private HistoryData historyData;

    // @RequestMapping("/all")
    // @ResponseBody
    // public List<VM> getAllVMs() {
    // // return hostAndVmPerfOp.getVmsEx();
    // return null;
    // }

    @RequestMapping("/vmTopN")
    @ResponseBody
    public JSONObject getAllVMs() {
        JSONObject result = new JSONObject();
        List<VMBo> vmBos = vmService.getAllVMs();
        int topN = 10;
        result.put("vmList", vmBos);
        result.put("topnCpuUsage", toolsUtils.getResourceMetricTopN(vmBos, topN, PerfConstants.CPU_USAGE_ID));
        result.put("topnCpuUsed", toolsUtils.getResourceMetricTopN(vmBos, topN, PerfConstants.CPU_USED_ID));
        result.put("topnDiskIO", toolsUtils.getResourceMetricTopN(vmBos, topN, PerfConstants.DISK_IO_SPEED_ID));
        result.put("topnMemUsage", toolsUtils.getResourceMetricTopN(vmBos, topN, PerfConstants.MEM_USAGE_ID));
        result.put("topnMemUsed", toolsUtils.getResourceMetricTopN(vmBos, topN, PerfConstants.MEM_USED_ID));
        result.put("topnNetIO", toolsUtils.getResourceMetricTopN(vmBos, topN, PerfConstants.NET_IO_SPEED_ID));
        result.put("topnDiskUsage", toolsUtils.getResourceMetricTopN(vmBos, topN, PerfConstants.DISK_USAGE_ID));
        result.put("topnDiskIOPS", toolsUtils.getResourceMetricTopN(vmBos, topN, PerfConstants.DISK_IOPS_ID));
        
        return result;
    }

    @RequestMapping("/vmList")
    @ResponseBody
    public JSONObject getVMList() {
        // return clusterService.getClusters();
        JSONObject result = new JSONObject();
        List<VMBo> vmBos = vmService.getAllVMs();
        String total = Integer.toString(vmBos.size());
        result.put("total", total);
        // result.put("rows", ToolsUtils.getPage(vmBos, page, rows));
        result.put("rows", vmBos);
        // result.put("clusterList", clusterBos);
        // System.err.println("----" + result);
        return result;
    }

    @RequestMapping("/lists")
    @ResponseBody
    public List<VMBo> getVMsByIds() {
        List<String> vmIds = new ArrayList<String>();
        vmIds.add("vm-217");
        vmIds.add("vm-175");
        vmIds.add("vm-216");
        vmIds.add("vm-215");
        vmIds.add("vm-110");
        vmIds.add("vm-199");
        vmIds.add("vm-169");
        vmIds.add("vm-197");
        return vmService.getVMsByIds(vmIds);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getVMById(@RequestBody String vmId) {
        // return vmService.getVMById(vmid);
        JSONObject result = new JSONObject();
        VMBo vmBo = vmService.getVMById(vmId);
        result.put("vm", vmBo);
        result.put("history",
                historyData.getJsonHistoryData(vmId, PerfConstants.ENTITY_VM, PerfConstants.HISTORY_ONEDAY));

        return result;
    }

    @RequestMapping("/history/{id}")
    @ResponseBody
    public JSONObject getVMHistoryData(@PathVariable("id") String vmId) {
        // return historyData.getJsonHistoryData(vmId, PerfConstants.ENTITY_DS,
        // PerfConstants.HISTORY_ONEDAY);
        return vmService.getHistory(vmId);
       
    }
    
    @RequestMapping("/test/{id}")
    @ResponseBody
    public JSONObject testVMById(@PathVariable("id") String vmId) {
        // return vmService.getVMById(vmid);
        JSONObject result = new JSONObject();
        VMBo vmBo = vmService.getVMById(vmId);
        result.put("vm", vmBo);
        result.put("history",
                historyData.getJsonHistoryData(vmId, PerfConstants.ENTITY_VM, PerfConstants.HISTORY_ONEDAY));

        return result;
    }
}
