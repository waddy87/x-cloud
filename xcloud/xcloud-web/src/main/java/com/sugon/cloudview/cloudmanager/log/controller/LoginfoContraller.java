/**
 * Created on 2016年3月30日
 */
package com.sugon.cloudview.cloudmanager.log.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sugon.cloudview.cloudmanager.common.base.utils.DateJsonValueProcessor;
import com.sugon.cloudview.cloudmanager.common.base.utils.StringUtils;
import com.sugon.cloudview.cloudmanager.log.api.LogAPI;
import com.sugon.cloudview.cloudmanager.log.entity.LogInfoEntity;
import com.sugon.cloudview.cloudmanager.usermgmt.service.bo.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 功能名: 日志管理 功能描述: 提供日志查询功能
 * 
 * Copyright: Copyright (c) 2016
 * 
 * 公司: 曙光云计算技术有限公司
 *
 * @author 曾兵
 * 
 * @version 2.0.0 sp1
 */
@Controller
@RequestMapping(path = "/log")
public class LoginfoContraller {
	private static final Logger logger = LoggerFactory.getLogger(LoginfoContraller.class);

	@Autowired
	private LogAPI logAPI;

	/**
	 * 
	 * 功能: 进入日志管理首页
	 *
	 */
	@RequestMapping(path = "/toLogIndex", method = RequestMethod.GET)
	public String toVMTempletIndex() {
		logger.debug("进入日志管理");
		return "log/logIndex";
	}

	/**
	 * 
	 * 功能: 基于前台参数返回日志信息
	 *
	 * @param model
	 *            模块类型
	 * @param page
	 * @param rows
	 * @param user
	 *            用户信息
	 * @param resourceId
	 *            资源信息
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	@RequestMapping(path = "/getAllLog4Table", method = RequestMethod.POST)
	public @ResponseBody String getAllVMTemplet4Table(@RequestParam(value = "model", required = false) String model,
			@RequestParam(value = "page", required = false) int page,
			@RequestParam(value = "rows", required = false) int rows,
			@RequestParam(value = "user", required = false) String user,
			@RequestParam(value = "resourceId", required = false) String resourceId,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime) {
		logger.debug("model:" + model + "  page:" + page + " user:" + user + " resourceId:" + resourceId + " startTime:"
				+ startTime + " endTime:" + endTime);
		JSONObject json = new JSONObject();
		Page<LogInfoEntity> logInfoList = null;
		LogInfoEntity logInfoEntity = new LogInfoEntity();
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {// 判断权限问题
			User user1 = (User) subject.getPrincipal();

			// BeanUtils.copyProperties(userE, user1);
			if (user1.getRoles().get(0).getRoleCode().equals("operation_manager")) {

			} else if (user1.getRoles().get(0).getRoleCode().equals("org_manager")) {// 组织管理员
				logInfoEntity.setOrgId(user1.getOrgId());
			} else {// 普通用户
				logInfoEntity.setUserName(user1.getId());
			}
		}

		Map<String, String> otherParam = new HashMap<String, String>();

		if (StringUtils.isNotBlank(model)) {
			logInfoEntity.setModuleType(model);
		}

		if (StringUtils.isNotBlank(resourceId)) {
			logInfoEntity.setResourceId(resourceId);
		}

		if (StringUtils.isNotBlank(startTime)) {
			otherParam.put("startTime", startTime);
		}

		if (StringUtils.isNotBlank(endTime)) {
			otherParam.put("endTime", endTime);
		}
		if (StringUtils.isNotBlank(user)) {
			logInfoEntity.setUserRealName(user);
		}
		try {
			logInfoList = logAPI.findAllLoginfo(logInfoEntity, page, rows, otherParam);

			JsonConfig config = new JsonConfig();
			config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
			json.put("total", logInfoList.getTotalElements());
			json.put("rows", JSONArray.fromObject(logInfoList.getContent(), config));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return json + "";
	}

}
