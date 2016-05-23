package org.waddys.xcloud.task.action.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.waddys.xcloud.common.base.utils.StringUtils;
import org.waddys.xcloud.task.action.common.DateJsonValueProcessor;
import org.waddys.xcloud.task.bo.TaskInfo;
import org.waddys.xcloud.task.service.service.TaskInfoService;
import org.waddys.xcloud.vm.po.entity.VmHostE;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("/task")
public class TaskInfoController {
	private static final Logger logger = LoggerFactory.getLogger(TaskInfoController.class);
	@Autowired
	private TaskInfoService taskInfoService;

	@RequestMapping(value = "toTaskIndex", method = RequestMethod.GET)
	public String toTaskIndex(ModelMap model) {
		return "task/taskIndex";
	}

	@RequestMapping(value = "/querytaskTable", method = RequestMethod.POST)
	public @ResponseBody String getAllVMTemplet4Table(@RequestParam(value = "model", required = false) String model,
			@RequestParam(value = "page", required = false) int page,
			@RequestParam(value = "rows", required = false) int rows,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "resourceName", required = false) String resourceName) {
		logger.debug("model:" + model + "  page:" + page + " startTime:" + startTime + " endTime:" + endTime
				+ " endTime:" + endTime);
		JSONObject json = new JSONObject();
		Map<String, String> otherParam = new HashMap<String, String>();
		if (StringUtils.isNotBlank(startTime)) {
			otherParam.put("startTime", startTime);
		}
		if (StringUtils.isNotBlank(endTime)) {
			otherParam.put("endTime", endTime);
		}
		if (StringUtils.isNotBlank(resourceName)) {
			otherParam.put("resourceName", resourceName);
		}
		try {
			Map<String, Object> map = taskInfoService.findAllTaskInfo(page, rows, otherParam);
			JsonConfig config = new JsonConfig();
			config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
			json.put("total", map.get("total"));
			json.put("rows", JSONArray.fromObject(map.get("taskInfolst"), config));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json + "";
	}

}
