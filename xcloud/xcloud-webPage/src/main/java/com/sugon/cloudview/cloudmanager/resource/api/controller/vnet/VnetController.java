package com.sugon.cloudview.cloudmanager.resource.api.controller.vnet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sugon.cloudview.cloudmanager.common.base.utils.CIDRUtils;
import com.sugon.cloudview.cloudmanager.log.api.Log;
import com.sugon.cloudview.cloudmanager.log.impl.LogObject;
import com.sugon.cloudview.cloudmanager.log.impl.LogUitls;
import com.sugon.cloudview.cloudmanager.log.type.LogConst;
import com.sugon.cloudview.cloudmanager.org.bo.Organization;
import com.sugon.cloudview.cloudmanager.org.constant.OrgStatus;
import com.sugon.cloudview.cloudmanager.org.service.OrganizationService;
import com.sugon.cloudview.cloudmanager.resource.api.common.DateJsonValueProcessor;
import com.sugon.cloudview.cloudmanager.resource.service.bo.venv.VenvConfig;
import com.sugon.cloudview.cloudmanager.resource.service.bo.vnet.NetPool;
import com.sugon.cloudview.cloudmanager.resource.service.exception.venv.VenvException;
import com.sugon.cloudview.cloudmanager.resource.service.exception.vnet.VNetException;
import com.sugon.cloudview.cloudmanager.resource.service.service.venv.VenvConfigService;
import com.sugon.cloudview.cloudmanager.resource.service.service.vnet.NetPoolService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("/vnet")
public class VnetController {
    private static final Logger logger = LoggerFactory.getLogger(VnetController.class);

    @Autowired
    private NetPoolService netPoolService;
    @Autowired
    private VenvConfigService venvConfigService;
    @Autowired
    private OrganizationService organizationService;

    @RequestMapping(method = RequestMethod.GET, value = "/vnetList")
    public String vnetList(ModelMap model) {
        return "vnet/index";
    }

    @RequestMapping(value = "/querynetTable", method = RequestMethod.POST)
    public @ResponseBody String querynetTable(@RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", required = false) int page,
            @RequestParam(value = "rows", required = false) int rows) {
        if (null == name) {
            name = "";
        }
        NetPool netPool = new NetPool();
        netPool.setNetName(name);
        List<NetPool> list1 = new ArrayList<NetPool>();
        JSONObject json = new JSONObject();
        try {
            list1 = netPoolService.QueryNetPoolByPage(page, rows, name, true);
            List<NetPool> list = new ArrayList<NetPool>();
            for (NetPool netPool2 : list1) {
                if (!("").equals(netPool2.getOrgId()) && netPool2.getOrgId() != null) {
                    Organization org = organizationService.showById(netPool2.getOrgId());
                    netPool2.setOrgName(org.getName());
                }
                list.add(netPool2);
            }
            long total = netPoolService.count(netPool, true);
            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));
            json.put("total", total);
            json.put("rows", JSONArray.fromObject(list, config));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json + "";
    }

    @RequestMapping(value = "/unquerynetTable", method = RequestMethod.POST)
    public @ResponseBody String unquerynetTable(@RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", required = false) int page,
            @RequestParam(value = "rows", required = false) int rows) {
        if (null == name) {
            name = "";
        }
        NetPool netPool = new NetPool();
        netPool.setNetName(name);
        List<NetPool> list1 = new ArrayList<NetPool>();
        JSONObject json = new JSONObject();
        try {
            list1 = netPoolService.QueryNetPoolByPage(page, rows, name, false);
            List<NetPool> list = new ArrayList<NetPool>();
            for (NetPool netPool2 : list1) {
                if (!("").equals(netPool2.getOrgId()) && netPool2.getOrgId() != null) {
                    Organization org = organizationService.showById(netPool2.getOrgId());
                    netPool2.setOrgName(org.getName());
                }
                list.add(netPool2);
            }
            long total = netPoolService.count(netPool, false);
            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));
            json.put("total", total);
            json.put("rows", JSONArray.fromObject(list, config));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json + "";
    }

    /**
     * 同步虚拟化环境的网络池信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/synNetpoolDataVenvConfig")
    @Log(message = "同步虚拟化环境网络池数据", resourceType = "虚拟网络", operationType = "同步", businessType = "虚拟网络", moduleType = LogConst.M_VNET)
    public String synNetpoolDataVenvConfig() throws VNetException {
        logger.debug("setp into cotroller, method is synNetpoolDataVenvConfig()");
        JSONObject jo = new JSONObject();
        LogObject logObject = new LogObject();
        try {
            List<VenvConfig> venvConfigs = venvConfigService.findConfigInfos();
            if (venvConfigs != null && venvConfigs.size() > 0) {
                for (VenvConfig venvConfig : venvConfigs) {
                    logger.debug("执行数据同步操作!");
                    netPoolService.synNetPoolData(venvConfig.getConfigId(), venvConfig.getiP());
                    logger.debug("执行数据同步操作成功!");
                }
            }
            jo.put("flag", true);
        } catch (Exception e) {
            logger.debug("failure addvenvConfigInfo()");
            jo.put("flag", false);
            jo.put("message", e.getMessage());
            logObject.setOperationResult("1");
        }
        LogUitls.putArgs(logObject);
        return jo.toString();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/detailVnetPage")
    @Log(message = "查看网络{0}详情", resourceType = "虚拟网络", operationType = "详情", businessType = "虚拟网络", moduleType = LogConst.M_VNET)
    public String detailVnetPage(@RequestParam(value = "netpoolid") String netpoolid, ModelMap model)
            throws VNetException {
        logger.debug("step into method editOrdetailVnetPage(),netpoolid=" + netpoolid);
        LogObject logObject = new LogObject();
        Object ary[] = new Object[1];
        if (netpoolid != null && netpoolid != "") {
            NetPool netPool = netPoolService.QueryNetpoolById(netpoolid);
            logger.debug("NetPool=" + netPool.toString());
            ary[0] = netPool.getNetName();
            logObject.setObjects(ary);
            if (netPool != null) {
                Organization org = organizationService.showById(netPool.getOrgId());
                model.addAttribute("org", org);
                model.addAttribute("netPool", netPool);
            }
        }
        LogUitls.putArgs(logObject);
        return "vnet/detailvnet";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/editVnetPage")
    public String editVnetPage(@RequestParam(value = "netpoolid") String netpoolid, ModelMap model)
            throws VNetException {
        logger.debug("step into method editOrdetailVnetPage(),netpoolid=" + netpoolid);
        if (netpoolid != null && netpoolid != "") {
            NetPool netPool = netPoolService.QueryNetpoolById(netpoolid);
            logger.debug("NetPool=" + netPool.toString());
            if (netPool != null) {
                Organization org = organizationService.showById(netPool.getOrgId());
                model.addAttribute("org", org);
                model.addAttribute("netPool", netPool);
            }
        }
        return "vnet/editvnet";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/distrabutePage")
    public String distrabutePage(@RequestParam(value = "netpoolids") String netpoolids, ModelMap model)
            throws VNetException {
        logger.debug("step into method distrabutePage(),id=" + netpoolids);
        try {
            logger.debug("start get networkPool,netpoolids=" + netpoolids);
            if (netpoolids != "" && netpoolids != null) {
                String netpoolId[] = netpoolids.split(",");
                List<NetPool> lstNetpool = new ArrayList<NetPool>();
                for (String id : netpoolId) {
                    NetPool netPool = netPoolService.QueryNetpoolById(id);
                    netPool.setSubNet(netPool.getSubNet() + "/" + netPool.getSubNetNo());
                    lstNetpool.add(netPool);
                }
                model.addAttribute("lstNetpool", lstNetpool);
            }
            logger.debug("step into method organizationService.pageAll()");
            Organization organization = new Organization();
            organization.setStatus(OrgStatus.NORMAL);
            Page<Organization> page = organizationService.pageAll(organization, null);
            logger.debug("method organizationService.listAll(),return lstOrg.size()" + page.getContent().size());
            model.addAttribute("lstOrg", page.getContent());
        } catch (Exception e) {
            logger.debug("进入分配网络页面异常" + e.getMessage());
        }
        return "vnet/distrabutePage";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/distrabuteNetPool")
    @ResponseBody
    @Log(message = "给组织{0}分配网络", resourceType = "虚拟网络", operationType = "分配", businessType = "虚拟网络", moduleType = LogConst.M_VNET)
    public String distrabuteNetPool(@RequestParam(value = "netpoolIds") String netpoolIds,
            @RequestParam(value = "orgId") String orgId, @RequestParam(value = "orgName") String orgName)
                    throws VNetException {
        logger.debug("step into method distrabuteNetPool(),netpoolIds=" + netpoolIds + "orgId=" + orgId);
        JSONObject jo = new JSONObject();
        LogObject logObject = new LogObject();
        Object ary[] = new Object[1];
        ary[0] = orgName;
        logObject.setObjects(ary);
        try {
            logger.debug("start get networkPool,netpoolIds=" + netpoolIds);
            if (netpoolIds != "" && netpoolIds != null) {
                String netpoolId[] = netpoolIds.split(",");
                for (String id : netpoolId) {
                    NetPool netPool = netPoolService.QueryNetpoolById(id);
                    netPool.setOrgId(orgId);
                    netPool.setOrgName(orgName);
                    netPool.setSubNet(netPool.getSubNet() + "/" + netPool.getSubNetNo());
                    netPoolService.saveNetPool(netPool);
                }
                jo.put("flag", true);
            }
        } catch (Exception e) {
            jo.put("flag", false);
            jo.put("messeage", e.getMessage());
            logObject.setOperationResult("1");
        }
        LogUitls.putArgs(logObject);
        return jo + "";
    }

    /**
     * 修改虚拟环境
     */
    @RequestMapping(method = RequestMethod.POST, value = "/editBynetpoolId")
    @ResponseBody
    @Log(message = "修改虚拟网络{0}", resourceType = "虚拟网络", operationType = "修改", businessType = "虚拟网络", moduleType = LogConst.M_VNET)
    public String editBynetpoolId(@RequestParam(value = "id") String id,
            @RequestParam(value = "gateway") String gateway, @RequestParam(value = "subNet") String subNet,
            @RequestParam(value = "subNetNo") String subNetNo, @RequestParam(value = "dns") String dns)
                    throws VenvException {
        logger.debug("step into method addvenvConfigInfo(),id=" + id);
        JSONObject jo = new JSONObject();
        LogObject logObject = new LogObject();
        Object ary[] = new Object[1];
        try {
            NetPool netPool = netPoolService.QueryNetpoolById(id);
            netPool.setGateway(gateway);
            netPool.setSubNet(subNet + "/" + subNetNo);
            netPool.setDns(dns);
            netPool.setSynDate(new Date());
            // 子网IP校验
            CIDRUtils cidrUtils = new CIDRUtils(netPool.getSubNet());
            String rightIP = cidrUtils.getNetworkAddress();
            NetPool netPool1 = netPoolService.updateNetPool(netPool);
            if (rightIP.equals(subNet)) {
                jo.put("flag", "0");
                ary[0] = netPool1.getNetName();
                logObject.setObjects(ary);
            } else {
                jo.put("flag", "1");
                jo.put("messeage", rightIP);
                ary[0] = netPool1.getNetName() + "子网掩码";
                logObject.setObjects(ary);
                LogUitls.putArgs(logObject);
                return jo + "";
            }
        } catch (Exception e) {
            jo.put("flag", "2");
            jo.put("messeage", e.getMessage());
            logObject.setOperationResult("1");
        }
        LogUitls.putArgs(logObject);
        return jo + "";
    }

    @RequestMapping(value = "/findOne/{id}", method = RequestMethod.GET)
    @ResponseBody
    @Transactional(readOnly = true)
    public String findOne(@PathVariable("id") String poolId) {
        try {
            NetPool result = netPoolService.QueryNetpoolById(poolId);
            return JSONObject.fromObject(result).toString();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

}
