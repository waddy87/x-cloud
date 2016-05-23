/**
 * Created on 2016年3月25日
 */
package org.waddys.xcloud.monitor.action.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.waddys.xcloud.monitor.serviceImpl.util.ToolsUtils;


/**
 * 功能名: 请填写功能名
 * 功能描述: 请简要描述功能的要点
 * Copyright: Copyright (c) 2016
 * 公司: 曙光云计算技术有限公司
 *
 * @author yangkun
 * @version 2.0.0 sp1
 */
@Controller
@RequestMapping(path = "/monitor")
public class MonitorController {
    private static final Logger logger = LoggerFactory.getLogger(MonitorController.class);

    @Qualifier("monitor-toolsutils")
    @Autowired
    ToolsUtils toolsUtils;

    @RequestMapping(path = "/toMonitorIndex", method = RequestMethod.GET)
    public String toMonitorIndex() {
        return "/monitor/monitorIndex";
    }

    @RequestMapping(path = "/toMonitorResource", method = RequestMethod.GET)
    public String toMonitorResource() {
        return "/monitor/clusterlist";
    }


    @RequestMapping(path = "/toMonitorVM", method = RequestMethod.GET)
    public String toMonitorVM() {
        return "/monitor/vmlist";
    }

    @RequestMapping(path = "/toMonitorHost", method = RequestMethod.GET)
    public String toMonitorHost() {
        return "/monitor/host";
    }


    @RequestMapping(path = "/toMonitorStorage", method = RequestMethod.GET)
    public String toMonitorStorage() {
        return "/monitor/storage";
    }
}
