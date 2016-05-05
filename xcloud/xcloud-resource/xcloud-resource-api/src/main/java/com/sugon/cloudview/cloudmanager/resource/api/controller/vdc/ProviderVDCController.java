package com.sugon.cloudview.cloudmanager.resource.api.controller.vdc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sugon.cloudview.cloudmanager.resource.api.common.DateJsonValueProcessor;
import com.sugon.cloudview.cloudmanager.resource.service.bo.vdc.ComputingPool;
import com.sugon.cloudview.cloudmanager.resource.service.bo.vdc.ProvideVDCStoragePool;
import com.sugon.cloudview.cloudmanager.resource.service.bo.vdc.ProviderVDC;
import com.sugon.cloudview.cloudmanager.resource.service.bo.vdc.StoragePool;
import com.sugon.cloudview.cloudmanager.resource.service.exception.vdc.VDCException;
import com.sugon.cloudview.cloudmanager.resource.service.service.vdc.ComputingPoolService;
import com.sugon.cloudview.cloudmanager.resource.service.service.vdc.ProviderVDCService;
import com.sugon.cloudview.cloudmanager.resource.service.service.vdc.StoragePoolService;
import com.sugon.cloudview.cloudmanager.vm.bo.VmHost;
import com.sugon.cloudview.cloudmanager.vm.service.VmService;

@Controller
@RequestMapping("/proVDC")
public class ProviderVDCController {

    @Autowired
    private ProviderVDCService providerVDCService;
    @Autowired
    private ComputingPoolService computingPoolService;
    @Autowired
    private VmService vmService;
    @Autowired
    private StoragePoolService storagePoolService;

    private static Logger logger = LoggerFactory
            .getLogger(ProviderVDCController.class);

    @RequestMapping(value = "/checkProviderVDCName ", method = RequestMethod.POST)
    public @ResponseBody String checkProviderVDCName(
            @RequestParam(value = "pVDCId", required = false) String pVDCId,
            @RequestParam(value = "pVDCName", required = false) String pVDCName,
            ModelMap model) {
        JSONObject resultObject = new JSONObject();
        resultObject.put("success", true);
        try {
            ProviderVDC providerVDC = new ProviderVDC();
            providerVDC.setpVDCId(pVDCId);
            providerVDC.setName(pVDCName);
            List<ProviderVDC> pList = providerVDCService
                    .findAllProviderVDCs(providerVDC);
            if (pList != null && pList.size() > 0) {
                resultObject.put("success", false);
            }
        } catch (VDCException e) {
            resultObject.put("success", false);
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return resultObject + "";
    }

    @RequestMapping(value = "/getStoragePoolDetail", method = RequestMethod.POST)
    public @ResponseBody String getStoragePoolDetail(
            @RequestParam(value = "storagePoolId", required = false) String storagePoolId,
            ModelMap model) {
        JSONObject resultObject = new JSONObject();
        resultObject.put("success", true);
        try {
            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(Date.class,
                    new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));
            StoragePool sp = storagePoolService.findStoragePool(storagePoolId);
            resultObject.put("storagePoolInfo",
                    JSONObject.fromObject(sp, config));
        } catch (VDCException e) {
            resultObject.put("success", false);
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return resultObject + "";
    }

    @RequestMapping(value = "/queryPorVDCVm", method = RequestMethod.POST)
    public @ResponseBody String queryPorVDCVm(
            @RequestParam(value = "proVDCId", required = false) String proVDCId) {
        JSONObject resultObject = new JSONObject();
        resultObject.put("success", false);
        try {
            VmHost vmHost = new VmHost();
            vmHost.setStatus("A");
            List<VmHost> vmHostList = vmService.listByVDC(proVDCId, vmHost);
            if (null == vmHostList || vmHostList.isEmpty()) {
                resultObject.put("success", true);
            }
        } catch (Exception e) {
            resultObject.put("success", false);
            resultObject.put("message", e.getMessage());
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return resultObject + "";
    }

    @RequestMapping(value = "/updateProVDC", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public @ResponseBody String updateProVDC(
            @RequestParam(value = "pVDCId", required = false) String pVDCId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "vcpuNum", required = false) Integer vcpuNum,
            @RequestParam(value = "memorySize", required = false) Long memorySize,
            @RequestParam(value = "storageValueNew", required = false) String storageValueNew,
            @RequestParam(value = "storageValueOld", required = false) String storageValueOld,
            ModelMap model) {
        JSONObject resultObject = new JSONObject();
        resultObject.put("success", true);
        try {
            ProviderVDC providerVDC = new ProviderVDC();
            providerVDC.setpVDCId(pVDCId);
            providerVDC.setName(name);
            providerVDC.setDescription(description);
            providerVDC.setMemorySize(memorySize);
            providerVDC.setvCpuNum(vcpuNum);
            List<ProvideVDCStoragePool> list = new ArrayList<ProvideVDCStoragePool>();
            if (StringUtils.isNotBlank(storageValueOld)) {
                String[] storageArr = StringUtils
                        .stripEnd(storageValueOld, "@").split("@");
                int storageArrLength = storageArr.length;
                for (int i = 0; i < storageArrLength; i++) {
                    String[] storageTmpArr = storageArr[i].split(",");
                    if (storageTmpArr[0].equals("undefined")) {
                        continue;
                    }
                    ProvideVDCStoragePool provideVDCStoragePool = new ProvideVDCStoragePool();
                    provideVDCStoragePool.setIsCreate(false);
                    provideVDCStoragePool
                            .setpVDCStoragePoolId(storageTmpArr[0]);
                    provideVDCStoragePool.setSpId(storageTmpArr[1]);
                    provideVDCStoragePool.setSpUsed(Long
                            .parseLong(storageTmpArr[2]));
                    provideVDCStoragePool.setSpTotal(Long
                            .parseLong(storageTmpArr[3]));
                    list.add(provideVDCStoragePool);
                }
            }

            if (StringUtils.isNotBlank(storageValueNew)) {
                String[] storageArr = StringUtils
                        .stripEnd(storageValueNew, "@").split("@");
                int storageArrLength = storageArr.length;
                for (int i = 0; i < storageArrLength; i++) {
                    if (storageArr[i].equals("oldSpMes")) {
                        continue;
                    }
                    String[] storageTmpArr = storageArr[i].split(",");
                    ProvideVDCStoragePool provideVDCStoragePool = new ProvideVDCStoragePool();
                    provideVDCStoragePool.setIsCreate(true);
                    provideVDCStoragePool.setSpId(storageTmpArr[0]);
                    provideVDCStoragePool.setSpTotal(Long
                            .parseLong(storageTmpArr[1]));
                    provideVDCStoragePool.setSpName(storageTmpArr[2]);
                    list.add(provideVDCStoragePool);
                }
            }
            providerVDC.setProvideVDCStoragePool(list);
            providerVDCService.updateProviderVDC(providerVDC);

        } catch (Exception e) {
            resultObject.put("success", false);
            resultObject.put("message", e.getMessage());
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return resultObject + "";
    }

    @RequestMapping(value = "/proVDCUpdate", method = RequestMethod.GET)
    public String proVDCUpdate(
            @RequestParam(value = "pVDCId", required = false) String pVDCId,
            ModelMap model) {
        try {
            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(Date.class,
                    new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));
            ProviderVDC providerVDC = providerVDCService
                    .findProviderVDC(pVDCId);
            ComputingPool computingPool = computingPoolService
                    .findComputingPool(providerVDC.getComputingPoolId());
            List<ProvideVDCStoragePool> pvdcSpList = providerVDCService
                    .findStoragePools(pVDCId);
            model.put("providerVDC", JSONObject.fromObject(providerVDC));
            model.put("computingPoolInfo", JSONObject.fromObject(computingPool));
            model.put("pvdcSpListList", JSONArray.fromObject(pvdcSpList));
            // List<ComputingPool> cpList = computingPoolService
            // .findByIsDistribute(false);
            // model.put("cpList", JSONArray.fromObject(cpList, config));

            List<StoragePool> storagePoolList = computingPoolService
                    .findStoragePools(providerVDC.getComputingPoolId());
            model.put("storagePoolList",
                    JSONArray.fromObject(storagePoolList, config));
        } catch (VDCException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return "provdc/proVDCUpdate";
    }

    @RequestMapping(value = "/createProVDC", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public @ResponseBody String createProVDC(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "computingPoolId", required = false) String computingPoolId,
            @RequestParam(value = "computingPoolName", required = false) String computingPoolName,
            @RequestParam(value = "vcpuNum", required = false) Integer vcpuNum,
            @RequestParam(value = "memorySize", required = false) Long memorySize,
            @RequestParam(value = "storageValue", required = false) String storageValue,
            ModelMap model) {
        JSONObject resultObject = new JSONObject();
        resultObject.put("success", true);
        try {
            ProviderVDC providerVDC = new ProviderVDC();
            providerVDC.setName(name);
            providerVDC.setDescription(description);
            providerVDC.setComputingPoolId(computingPoolId);
            providerVDC.setComputingPoolName(computingPoolName);
            providerVDC.setCreateDate(new Date());
            providerVDC.setMemorySize(memorySize);
            providerVDC.setMemoryOverplus(memorySize);
            providerVDC.setMemoryUsed(0l);
            providerVDC.setvCpuNum(vcpuNum);
            providerVDC.setvCpuOverplus(vcpuNum);
            providerVDC.setvCpuUsed(0);

            if (StringUtils.isNotBlank(storageValue)) {
                List<ProvideVDCStoragePool> list = new ArrayList<ProvideVDCStoragePool>();
                String[] storageArr = StringUtils.stripEnd(storageValue, "@")
                        .split("@");
                int storageArrLength = storageArr.length;
                for (int i = 0; i < storageArrLength; i++) {
                    String[] storageTmpArr = storageArr[i].split(",");
                    ProvideVDCStoragePool provideVDCStoragePool = new ProvideVDCStoragePool();
                    provideVDCStoragePool.setSpId(storageTmpArr[0]);
                    provideVDCStoragePool.setSpSurplus(Long
                            .parseLong(storageTmpArr[1]));
                    provideVDCStoragePool.setSpTotal(Long
                            .parseLong(storageTmpArr[1]));
                    provideVDCStoragePool.setSpName(storageTmpArr[2]);
                    provideVDCStoragePool.setSpUsed(0l);
                    list.add(provideVDCStoragePool);
                }
                providerVDC.setProvideVDCStoragePool(list);
            }
            providerVDCService.save(providerVDC);
            ComputingPool computingPool = new ComputingPool();
            computingPool.setComputingPoolId(computingPoolId);
            computingPool.setIsDistribute(true);
            computingPoolService.update(computingPool);
        } catch (VDCException e) {
            resultObject.put("success", false);
            resultObject.put("message", e.getMessage());
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return resultObject + "";
    }

    @RequestMapping(value = "/getComputingPoolDetail", method = RequestMethod.POST)
    public String getComputingPoolDetail(
            @RequestParam(value = "computingPoolId", required = false) String computingPoolId,
            ModelMap model) {
        try {
            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(Date.class,
                    new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));
            ComputingPool cp = computingPoolService
                    .findComputingPool(computingPoolId);
            List<StoragePool> storagePoolList = computingPoolService
                    .findStoragePools(computingPoolId);
            model.put("computingPoolInfo", JSONObject.fromObject(cp, config));
            model.put("storagePoolList",
                    JSONArray.fromObject(storagePoolList, config));
        } catch (VDCException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return "provdc/computingPoolDetail";
    }

    @RequestMapping(value = "/proVDCCreate", method = RequestMethod.GET)
    public String proVDCCreate(ModelMap model) {
        try {
            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(Date.class,
                    new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));
            ComputingPool computingPool = new ComputingPool();
            computingPool.setIsAvl(true);
            computingPool.setIsDistribute(false);
            List<ComputingPool> cpList = computingPoolService
                    .findAllComputingPools(computingPool);
            model.put("cpList", JSONArray.fromObject(cpList, config));
        } catch (VDCException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return "provdc/proVDCCreate";
    }

    @RequestMapping(value = "/deleteProVDC", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public @ResponseBody String deleteProVDC(
            @RequestParam(value = "proVDCId", required = false) String proVDCId,
            @RequestParam(value = "computingPoolId", required = false) String computingPoolId) {
        JSONObject resultObject = new JSONObject();
        resultObject.put("success", true);
        try {

            providerVDCService.delete(proVDCId);
            ComputingPool computingPool = new ComputingPool();
            computingPool.setComputingPoolId(computingPoolId);
            computingPool.setIsDistribute(false);
            computingPoolService.update(computingPool);
        } catch (Exception e) {
            resultObject.put("success", false);
            resultObject.put("message", e.getMessage());
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return resultObject + "";
    }

    @RequestMapping(value = "/queryPorVDCTable", method = RequestMethod.POST)
    public @ResponseBody String queryPorVDCTable(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", required = false) int page,
            @RequestParam(value = "rows", required = false) int rows) {
        ProviderVDC providerVDC = new ProviderVDC();
        providerVDC.setName(name);
        JSONObject json = new JSONObject();
        try {
            Map<String, Object> map = providerVDCService.findProviderVDCs(
                    providerVDC, page, rows);
            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(Date.class,
                    new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));
            json.put("total", map.get("total"));
            json.put("rows",
                    JSONArray.fromObject(map.get("providerVDCList"), config));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return json + "";
    }

    @RequestMapping(value = "/providerVDCList", method = RequestMethod.GET)
    public String providerVDCList(ModelMap model) {
        // ProviderVDC providerVDC = new ProviderVDC();
        // providerVDC.setName("");
        // List<ProviderVDC> list = new ArrayList<ProviderVDC>();
        // try {
        // list = providerVDCService.findProviderVDCs(providerVDC, PAGENUM,
        // PAGESIZE);
        // long total = providerVDCService.count(providerVDC);
        // JsonConfig config = new JsonConfig();
        // config.registerJsonValueProcessor(Date.class,
        // new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));
        // model.put("total", total);
        // model.put("proVDCList", JSONArray.fromObject(list, config));
        // } catch (Exception e) {
        // e.printStackTrace();
        // logger.info(e.getMessage());
        // }
        return "provdc/index";
    }

    @RequestMapping(value = "/providerVDCDetail", method = RequestMethod.POST)
    public String providerVDCDetail(
            @RequestParam(value = "id", required = false) String id,
            ModelMap model) {
        ProviderVDC providerVDC = new ProviderVDC();
        try {
            providerVDC = providerVDCService.findProviderVDC(id);
            List<ProvideVDCStoragePool> proVDCSpList = providerVDCService
                    .findStoragePools(id);
            model.put("proVDCInfo", JSONObject.fromObject(providerVDC));
            model.put("proVDCSpList", JSONArray.fromObject(proVDCSpList));
        } catch (VDCException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }

        return "provdc/proVDCDetail";
    }
}
