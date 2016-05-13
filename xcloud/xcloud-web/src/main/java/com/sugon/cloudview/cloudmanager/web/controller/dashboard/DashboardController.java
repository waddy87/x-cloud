package com.sugon.cloudview.cloudmanager.web.controller.dashboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sugon.cloudview.cloudmanager.web.controller.dashboard.DashboardController;

@Controller
@RequestMapping(path = "/dashboard")
public class DashboardController {
	private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
	
	@RequestMapping(path = "/toDashboardOperationIndex", method = RequestMethod.GET)
	public String toDashboardOperationIndex() {
		return "dashboard/dashboardOperationIndex";
	}
	@RequestMapping(path = "/toDashboardOrgManagerIndex", method = RequestMethod.GET)
	public String toDashboardOrgManagerIndex() {
		return "dashboard/dashboardOrgManagerIndex";
	}
	@RequestMapping(path = "/toDashboardOrgUserIndex", method = RequestMethod.GET)
	public String toDashboardOrgUserIndex() {
		return "dashboard/dashboardOrgUserIndex";
	}
}
