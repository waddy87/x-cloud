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
import org.waddys.xcloud.monitor.service.bo.StorageBo;
import org.waddys.xcloud.monitor.service.bo.VMBo;
import org.waddys.xcloud.monitor.service.service.StorageService;
import org.waddys.xcloud.monitor.serviceImpl.util.HistoryData;
import org.waddys.xcloud.monitor.serviceImpl.util.PerfConstants;
import org.waddys.xcloud.monitor.serviceImpl.util.ToolsUtils;

import net.sf.json.JSONObject;

/**
 * 功能名: 请填写功能名 功能描述: 请简要描述功能的要点 Copyright: Copyright (c) 2016 公司: 曙光云计算技术有限公司
 *
 * @author wangqian
 * @version 2.0.0 sp1
 */
@Controller("monitor-storageController")
@RequestMapping("/monitor/storage")
public class StorageController {

    @Qualifier("monitor-storageServiceImpl")
    @Autowired
    private StorageService storageService;

    @Qualifier("monitor-history")
    @Autowired
    private HistoryData historyData;

    @Qualifier("monitor-toolsutils")
    @Autowired
    private ToolsUtils toolsUtils;

    @RequestMapping("/storageTopN")
    @ResponseBody
    public JSONObject getStorageTopN() {
        // return storageService.getStorages();
        JSONObject result = new JSONObject();
        List<StorageBo> storageBos = storageService.getStorages();
        int topN = 10;
        result.put("storageList", storageBos);
        result.put("topnDiskTotal", toolsUtils.getResourceMetricTopN(storageBos, topN, PerfConstants.DISK_TOTAL_ID));
        result.put("topnDiskUsage", toolsUtils.getResourceMetricTopN(storageBos, topN, PerfConstants.DISK_USAGE_ID));
        // result.put("topnIops", toolsUtils.getResourceMetricTopN(storageBos,
        // topN, PerfConstants.DISK_IOPS_ID));
        result.put("topnHostNum", toolsUtils.getResourceMetricTopN(storageBos, topN, PerfConstants.Host_NUMS_ID));
        result.put("topnVMNum", toolsUtils.getResourceMetricTopN(storageBos, topN, PerfConstants.VM_NUMS_ID));
        // result.put("topnDiskIO", toolsUtils.getResourceMetricTopN(storageBos,
        // topN, PerfConstants.DISK_IO_SPEED_ID));
        return result;
    }

    @RequestMapping("/storageList")
    @ResponseBody
    public JSONObject getStorageList() {
        JSONObject result = new JSONObject();
        List<StorageBo> storageBos = storageService.getStorages();
        String total = Integer.toString(storageBos.size());
        result.put("total", total);
        result.put("rows", storageBos);
        return result;
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getStorageById(@RequestBody String storageId) {
        JSONObject result = new JSONObject();
        StorageBo storageBo = storageService.getStorageById(storageId);
        List<HostBo> hostBos = storageBo.getHostList();
        List<VMBo> vmBos = storageBo.getVmList();
        int topN = 3;
        result.put("storage", storageBo);
        if (hostBos != null && hostBos.size() > 0) {
            result.put("topnHostDiskUsage",
                    toolsUtils.getResourceMetricTopN(hostBos, topN, PerfConstants.DISK_USAGE_ID));
            result.put("topnHostIops", toolsUtils.getResourceMetricTopN(hostBos, topN, PerfConstants.DISK_IOPS_ID));
        } else {
            result.put("topnHostDiskUsage", null);
            result.put("topnHostIops", null);
        }
        if (vmBos != null && vmBos.size() > 0) {
            result.put("topnVmDiskUsage", toolsUtils.getResourceMetricTopN(vmBos, topN, PerfConstants.DISK_USAGE_ID));
            result.put("topnVmIops", toolsUtils.getResourceMetricTopN(vmBos, topN, PerfConstants.DISK_IOPS_ID));
        } else {
            result.put("topnVmDiskUsage", null);
            result.put("topnVmIops", null);
        }
        result.put("history",
                historyData.getJsonHistoryData(storageId, PerfConstants.ENTITY_DC, PerfConstants.HISTORY_ONEDAY));

        return result;
    }

    @RequestMapping("/history/{id}/{type}")
    @ResponseBody
    public JSONObject getStorageHistoryData(@PathVariable("id") String storageId,
            @PathVariable("type") String historyType) {
        return historyData.getJsonHistoryData(storageId, PerfConstants.ENTITY_DS, historyType);
    }
}
