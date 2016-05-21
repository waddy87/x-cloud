package org.waddys.xcloud.monitor.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.waddys.xcloud.monitor.service.service.AbstractService;
import org.waddys.xcloud.monitor.serviceImpl.util.ToolsUtils;

import net.sf.json.JSONObject;

@Controller("monitor-abstractcontroller")
@RequestMapping(path = "/monitor/abstract")
public class AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(AbstractController.class);
    @Qualifier("monitor-abstractServiceImpl")
    @Autowired
    private AbstractService abstractService;

    @Qualifier("monitor-toolsutils")
    @Autowired
    ToolsUtils toolsUtils;

    @RequestMapping(path = "/getHostInfo", method = RequestMethod.GET)
    @ResponseBody
    public String getHostInfo() {
        JSONObject hostSumInfoJson = null;
        try {
            hostSumInfoJson = this.abstractService.getAllHostsPerformInfo();
            System.out.println(hostSumInfoJson.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
        if(hostSumInfoJson==null){
        	return null;
        }
        return hostSumInfoJson.toString();
    }

    @RequestMapping(path = "/getStorageInfo", method = RequestMethod.GET)
    @ResponseBody
    public String getStorageInfo() {
        JSONObject storageSumInfoJson = null;
        try {
            storageSumInfoJson = this.abstractService.getAllStoragePerformInfo();
            System.out.println(storageSumInfoJson.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
        return storageSumInfoJson.toString();
    }

    @RequestMapping(path = "/getVMInfo", method = RequestMethod.GET)
    @ResponseBody
    public String getVMInfo() {
        JSONObject vmSumInfoJson = null;
        try {
            vmSumInfoJson = this.abstractService.getAllVMPerformInfo();
            System.out.println(vmSumInfoJson.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
        return vmSumInfoJson.toString();
    }

    @RequestMapping(path = "/getClusterInfo", method = RequestMethod.GET)
    @ResponseBody
    public String getClusterInfo() {
        JSONObject clusterSumInfoJson = null;
        try {
            clusterSumInfoJson = this.abstractService.getAllClusterPerformInfo();
            System.out.println(clusterSumInfoJson.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
        return clusterSumInfoJson.toString();
    }  	

}
