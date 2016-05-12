package com.sugon.cloudview.cloudmanager.resource.api.controller.vdc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sugon.cloudview.cloudmanager.resource.api.common.DateJsonValueProcessor;
import com.sugon.cloudview.cloudmanager.resource.service.bo.vdc.OrgVDC;
import com.sugon.cloudview.cloudmanager.resource.service.service.vdc.OrgVDCService;
import com.sugon.cloudview.cloudmanager.resource.service.service.vdc.ProviderVDCService;

@Controller
@RequestMapping("/orgVDC")
public class OrgVDCController {

    @Autowired
    private ProviderVDCService providerVDCService;
    @Autowired
    private OrgVDCService orgVDCService;

    private static Logger logger = LoggerFactory
            .getLogger(OrgVDCController.class);

    @RequestMapping(value = "/queryOrgVDCTable", method = RequestMethod.POST)
    public @ResponseBody String queryOrgVDCTable(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", required = false) int page,
            @RequestParam(value = "rows", required = false) int rows) {
        if (null == name) {
            name = "";
        }
        OrgVDC orgVDC = new OrgVDC();
        orgVDC.setName(name);
        List<OrgVDC> list = new ArrayList<OrgVDC>();
        JSONObject json = new JSONObject();
        try {
            list = orgVDCService.findOrgVDCs(orgVDC, page, rows);
            long total = orgVDCService.count(orgVDC);

            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(Date.class,
                    new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));
            json.put("total", total);
            json.put("rows", JSONArray.fromObject(list, config));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return json + "";
    }

    @RequestMapping(value = "/orgVDCList", method = RequestMethod.POST)
    public String orgVDCList(ModelMap model) {
        return "orgvdc/orgVDCIndex";
    }

}
