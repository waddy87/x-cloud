package com.sugon.cloudview.cloudmanager.pmmgmt.api.controller;

import java.util.Date;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sugon.cloudview.cloudmanager.common.base.utils.DateJsonValueProcessor;
import com.sugon.cloudview.cloudmanager.org.bo.Organization;
import com.sugon.cloudview.cloudmanager.org.constant.OrgStatus;
import com.sugon.cloudview.cloudmanager.org.service.OrganizationService;
import com.sugon.cloudview.cloudmanager.pmmgmt.service.bo.PhysicalMachine;
import com.sugon.cloudview.cloudmanager.pmmgmt.service.service.PhysicalMachineService;
import com.sugon.cloudview.cloudmanager.usermgmt.service.bo.RoleEnum;
import com.sugon.cloudview.cloudmanager.usermgmt.service.bo.User;

@Controller
@RequestMapping("/pmMgmt")
public class PmController {
    private static Logger logger = LoggerFactory.getLogger(PmController.class);

    @Autowired
    private PhysicalMachineService pmMgmtServiceImpl;
    @Autowired
    private OrganizationService organizationService;

    @RequestMapping(value = "/recoveryPm", method = RequestMethod.POST)
    public @ResponseBody String recoveryPm(
            @RequestParam(value = "pmIds", required = false) String pmIds,
            ModelMap model) {
        JSONObject resultObject = new JSONObject();
        resultObject.put("success", true);
        try {
            pmMgmtServiceImpl.batchUpdateOrgId(null, null, pmIds);
        } catch (Exception e) {
            resultObject.put("success", false);
            resultObject.put("message", e.getMessage());
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return resultObject + "";
    }

    @RequestMapping(value = "/distributionPm", method = RequestMethod.POST)
    public @ResponseBody String distributionPm(
            @RequestParam(value = "pmIds", required = false) String pmIds,
            @RequestParam(value = "orgId", required = false) String orgId,
            @RequestParam(value = "orgName", required = false) String orgName,
            ModelMap model) {
        JSONObject resultObject = new JSONObject();
        resultObject.put("success", true);
        try {
            pmMgmtServiceImpl.batchUpdateOrgId(orgId, orgName, pmIds);
        } catch (Exception e) {
            resultObject.put("success", false);
            resultObject.put("message", e.getMessage());
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return resultObject + "";
    }

    @RequestMapping(value = "/openPmDistributionPage", method = RequestMethod.GET)
    public String openPmDistributionPage(ModelMap model) {
        try {
            Organization org = new Organization();
            org.setStatus(OrgStatus.NORMAL);
            Page<Organization> orgList = organizationService.pageAll(org, null);
            model.put("orgList", orgList.getContent());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return "pmmgmt/distributionPm";
    }

    @RequestMapping(value = "/deletePm", method = RequestMethod.POST)
    public @ResponseBody String deletePm(
            @RequestParam(value = "id", required = false) String id,
            ModelMap model) {
        JSONObject resultObject = new JSONObject();
        resultObject.put("success", true);
        try {
            pmMgmtServiceImpl.delete(id);

        } catch (Exception e) {
            resultObject.put("success", false);
            resultObject.put("message", e.getMessage());
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return resultObject + "";
    }

    @RequestMapping(value = "/updatePm", method = RequestMethod.POST)
    public @ResponseBody String updatePm(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "ip", required = false) String ip,
            @RequestParam(value = "os", required = false) String os,
            @RequestParam(value = "ipmiIp", required = false) String ipmiIp,
            @RequestParam(value = "ipmiUserName", required = false) String ipmiUserName,
            @RequestParam(value = "ipmiPassword", required = false) String ipmiPassword,
            @RequestParam(value = "mac", required = false) String mac,
            @RequestParam(value = "hostType", required = false) String hostType,
            @RequestParam(value = "cpuType", required = false) String cpuType,
            @RequestParam(value = "deviceModel", required = false) String deviceModel,
            @RequestParam(value = "serialNumber", required = false) String serialNumber,
            @RequestParam(value = "description", required = false) String description) {
        JSONObject resultObject = new JSONObject();
        resultObject.put("success", true);
        try {
            PhysicalMachine pm = new PhysicalMachine();
            pm.setId(id);
            pm.setName(name);
            pm.setCpuType(cpuType);
            pm.setCreateDate(new Date());
            pm.setDescription(description);
            pm.setDeviceModel(deviceModel);
            pm.setHostType(hostType);
            pm.setIp(ip);
            pm.setIpmiIp(ipmiIp);
            pm.setIpmiPassword(ipmiPassword);
            pm.setIpmiUserName(ipmiUserName);
            pm.setMonitorMac(mac);
            pm.setOs(os);
            pm.setSerialNumber(serialNumber);
            pmMgmtServiceImpl.update(pm);
        } catch (Exception e) {
            resultObject.put("success", false);
            resultObject.put("message", e.getMessage());
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return resultObject + "";
    }

    @RequestMapping(value = "/openUpdatePmPage", method = RequestMethod.GET)
    public String openUpdatePmPage(
            @RequestParam(value = "id", required = false) String id,
            ModelMap model) {
        PhysicalMachine pm = new PhysicalMachine();
        try {
            pm = pmMgmtServiceImpl.findPm(id);
            model.put("pmInfo", pm);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return "pmmgmt/updatePm";
    }

    @RequestMapping(value = "/openPmDetailPage", method = RequestMethod.GET)
    public String openPmDetailPage(
            @RequestParam(value = "id", required = false) String id,
            ModelMap model) {
        PhysicalMachine pm = new PhysicalMachine();
        try {
            pm = pmMgmtServiceImpl.findPm(id);
            model.put("pmInfo", pm);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return "pmmgmt/pmDetail";
    }

    @RequestMapping(value = "/createPm", method = RequestMethod.POST)
    public @ResponseBody String createPm(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "ip", required = false) String ip,
            @RequestParam(value = "os", required = false) String os,
            @RequestParam(value = "ipmiIp", required = false) String ipmiIp,
            @RequestParam(value = "ipmiUserName", required = false) String ipmiUserName,
            @RequestParam(value = "ipmiPassword", required = false) String ipmiPassword,
            @RequestParam(value = "mac", required = false) String mac,
            @RequestParam(value = "hostType", required = false) String hostType,
            @RequestParam(value = "cpuType", required = false) String cpuType,
            @RequestParam(value = "deviceModel", required = false) String deviceModel,
            @RequestParam(value = "serialNumber", required = false) String serialNumber,
            @RequestParam(value = "description", required = false) String description) {
        JSONObject resultObject = new JSONObject();
        resultObject.put("success", true);
        try {
            PhysicalMachine pm = new PhysicalMachine();
            pm.setName(name);
            pm.setCpuType(cpuType);
            pm.setCreateDate(new Date());
            pm.setDescription(description);
            pm.setDeviceModel(deviceModel);
            pm.setHostType(hostType);
            pm.setIp(ip);
            pm.setIpmiIp(ipmiIp);
            pm.setIpmiPassword(ipmiPassword);
            pm.setIpmiUserName(ipmiUserName);
            pm.setMonitorMac(mac);
            pm.setOs(os);
            pm.setSerialNumber(serialNumber);
            pmMgmtServiceImpl.save(pm);
        } catch (Exception e) {
            resultObject.put("success", false);
            resultObject.put("message", e.getMessage());
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return resultObject + "";
    }

    @RequestMapping(value = "/openPmCreatePage", method = RequestMethod.GET)
    public String openPmCreatePage(ModelMap model) {
        return "pmmgmt/createPm";
    }

    @RequestMapping(value = "/queryPmTable", method = RequestMethod.POST)
    public @ResponseBody String queryPmTable(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", required = false) int page,
            @RequestParam(value = "rows", required = false) int rows) {
        JSONObject json = new JSONObject();
        try {
            Subject subject = SecurityUtils.getSubject();
            User current = (User) subject.getPrincipal();
            String roleCode = current.getRoles().get(0).getRoleCode();
            PhysicalMachine pmSearch = new PhysicalMachine();
            pmSearch.setName(name);
            if (roleCode.equals(RoleEnum.ORG_MANAGER.getCode())) {// 组织管理员
                pmSearch.setOrgId(current.getOrgId());
            }
            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(Date.class,
                    new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));
            Map<String, Object> map = pmMgmtServiceImpl.findPms(pmSearch, page,
                    rows);
            json.put("total", map.get("total"));
            json.put("rows", JSONArray.fromObject(map.get("pmList"), config));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return json + "";
    }

    @RequestMapping(value = "/pmIndex", method = RequestMethod.GET)
    public String pmIndex(ModelMap model) {
        return "pmmgmt/pmIndex";
    }

}
