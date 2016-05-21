/**
 * Created on 2016年3月25日
 */
package org.waddys.xcloud.monitor.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.waddys.xcloud.monitor.service.bo.HostBo;
import org.waddys.xcloud.monitor.service.bo.VMBo;
import org.waddys.xcloud.monitor.service.service.HostService;
import org.waddys.xcloud.monitor.serviceImpl.util.HistoryData;
import org.waddys.xcloud.monitor.serviceImpl.util.PerfConstants;
import org.waddys.xcloud.monitor.serviceImpl.util.ToolsUtils;

import net.sf.json.JSONObject;

/**
 * 功能名: 请填写功能名
 * 功能描述: 请简要描述功能的要点
 * Copyright: Copyright (c) 2016
 * 公司: 曙光云计算技术有限公司
 *
 * @author wangqian
 * @version 2.0.0 sp1
 */
@Controller("monitor-hostController")
@RequestMapping("/monitor/host")
public class HostController {

    @Qualifier("monitor-hostServiceImpl")
    @Autowired
    private HostService hostService;

    @Qualifier("monitor-history")
    @Autowired
    private HistoryData historyData;

    @Qualifier("monitor-toolsutils")
    @Autowired
    private ToolsUtils toolsUtils;

    @RequestMapping("/hostTopN")
    @ResponseBody
    public JSONObject getHostTopN() {
        // return hostService.getHosts();
        JSONObject result = new JSONObject();
        List<HostBo> hostBos = hostService.getHosts();
        int topN = 10;
        result.put("hostList", hostBos);
        result.put("topnCpuUsage", toolsUtils.getResourceMetricTopN(hostBos, topN, PerfConstants.CPU_USAGE_ID));
        result.put("topnCpuUsed", toolsUtils.getResourceMetricTopN(hostBos, topN, PerfConstants.CPU_USED_ID));
        result.put("topnDiskIO", toolsUtils.getResourceMetricTopN(hostBos, topN, PerfConstants.DISK_IO_SPEED_ID));
        result.put("topnDiskIops", toolsUtils.getResourceMetricTopN(hostBos, topN, PerfConstants.DISK_IOPS_ID));
        result.put("topnVMNum", toolsUtils.getResourceMetricTopN(hostBos, topN, PerfConstants.VM_NUMS_ID));
        result.put("topnMemUsage", toolsUtils.getResourceMetricTopN(hostBos, topN, PerfConstants.MEM_USAGE_ID));
        result.put("topnMemUsed", toolsUtils.getResourceMetricTopN(hostBos, topN, PerfConstants.MEM_USED_ID));
        result.put("topnNetIO", toolsUtils.getResourceMetricTopN(hostBos, topN, PerfConstants.NET_IO_SPEED_ID));
        return result;
    }

    @RequestMapping("/hostList")
    @ResponseBody
    public JSONObject getHostList() {
        JSONObject result = new JSONObject();
        List<HostBo> hostBos = hostService.getHosts();
        String total = Integer.toString(hostBos.size());
        result.put("total", total);
        result.put("rows", hostBos);
        return result;
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getHostById(@RequestBody String hostId) {
        JSONObject result = new JSONObject();
        HostBo hostBo = hostService.getHostById(hostId);
        List<VMBo> vmBos = hostBo.getVmList();
        int topN = 3;
        result.put("host", hostBo);
        if (vmBos != null && vmBos.size() != 0) {
            result.put("topnCpuUsage", toolsUtils.getResourceMetricTopN(vmBos, topN, PerfConstants.CPU_USAGE_ID));
            result.put("topnMemUsage", toolsUtils.getResourceMetricTopN(vmBos, topN, PerfConstants.MEM_USAGE_ID));
            result.put("topnDiskIO", toolsUtils.getResourceMetricTopN(vmBos, topN, PerfConstants.DISK_IO_SPEED_ID));
            result.put("topnNetIO", toolsUtils.getResourceMetricTopN(vmBos, topN, PerfConstants.NET_IO_SPEED_ID));
        } else {
            result.put("topnCpuUsage", null);
            result.put("topnMemUsage", null);
            result.put("topnDiskIO", null);
            result.put("topnNetIO", null);
        }
        result.put("history",
                historyData.getJsonHistoryData(hostId, PerfConstants.ENTITY_HOST, PerfConstants.HISTORY_ONEDAY));

        return result;
    }

    @RequestMapping("/history/{id}/{type}")
    @ResponseBody
    public JSONObject getHostHistoryData(@PathVariable("id") String hostId, @PathVariable("type") String historyType) {
        return historyData.getJsonHistoryData(hostId, PerfConstants.ENTITY_HOST, historyType);
    }
}
