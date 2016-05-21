package org.waddys.xcloud.templet.api.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.waddys.xcloud.common.base.exception.CloudviewException;
import org.waddys.xcloud.log.api.Log;
import org.waddys.xcloud.log.impl.LogObject;
import org.waddys.xcloud.log.impl.LogUitls;
import org.waddys.xcloud.templet.api.DateJsonValueProcessor;
import org.waddys.xcloud.templet.service.VMTempletService;
import org.waddys.xcloud.templet.service.entity.VMTempletE;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 功能名: 虚拟机模板控制层实现类 功能描述: 模板服务与界面交互 Copyright: Copyright (c) 2016 公司:
 * 曙光云计算技术有限公司
 *
 * @author 曾兵
 * @version 2.0.0 sp1
 *
 */
@Controller
@RequestMapping(path = "/templet")
public class VMTempletController {
	private static final Logger logger = LoggerFactory.getLogger(VMTempletController.class);

	@Autowired
	private VMTempletService vmTempletService;

	/**
	 * 
	 * 功能: 进入虚拟机模板首页
	 *
	 */
	@RequestMapping(path = "/toVMTempletIndex", method = RequestMethod.GET)
	public String toVMTempletIndex() {

		return "vmtemplet/vmtemplateIndex";
	}

	/**
	 * 
	 * 功能: 通过参数查询具体模板信息
	 *
	 * @param name
	 *            名称
	 * @param os
	 *            操作系统
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(path = "/getAllVMTemplet4Table", method = RequestMethod.POST)
	public @ResponseBody String getAllVMTemplet4Table(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "os", required = false) String os,
			@RequestParam(value = "page", required = false) int page,
			@RequestParam(value = "rows", required = false) int rows) {
		JSONObject json = new JSONObject();
		Page<VMTempletE> vmtempletList = null;
		VMTempletE vmTempletE = new VMTempletE();
		if (null != name) {
			vmTempletE.setName(name);
		}
		if (null != os) {
			vmTempletE.setOs(os);
		}
		try {
			vmtempletList = vmTempletService.findAllVMTemplet(vmTempletE, page, rows);
			JsonConfig config = new JsonConfig();
			config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));
			json.put("total", vmtempletList.getTotalElements());
			json.put("rows", JSONArray.fromObject(vmtempletList.getContent(), config));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return json + "";
	}

	/**
	 * 
	 * 功能: 虚拟机模板同步
	 *
	 */
	@RequestMapping(path = "/synVMTemplet", method = RequestMethod.GET)
	@ResponseBody
	@Log(message = "同步模板信息", operationType = "同步模板", resourceType = "虚拟机模板", moduleType = "模板管理")
	public void synVMTemplet() {

		LogObject logobject = new LogObject();
		try {
			vmTempletService.synVMTemplet("");
			logobject.setOperationResult("0");
		} catch (CloudviewException e) {
			logobject.setOperationResult("1");
			logger.error(e.getMessage());

		}

		logobject.setResourceType("虚拟机模板");
		LogUitls.putArgs(logobject);
	}

	/**
	 * 
	 * 功能: 发布模板
	 *
	 * @param vmTempletId
	 */
	@RequestMapping(path = "/releaseTemplet", method = RequestMethod.GET)
	@ResponseBody
	@Log(message = "发布模板信息: {0}", operationType = "发布模板", resourceType = "虚拟机模板",  moduleType = "模板管理")
	public void releaseTemplet(@RequestParam(value = "vmTempletId", required = false) String vmTempletId) {
		logger.debug(vmTempletId);
		VMTempletE vmTempletE = new VMTempletE();
		vmTempletE.setRelationId(vmTempletId);
		vmTempletE.setStatus("2");
		LogObject logobject = new LogObject();
		try {
			vmTempletService.release(vmTempletE);
			logobject.setOperationResult("0");
		} catch (CloudviewException e) {
			logobject.setOperationResult("1");
			logger.error(e.getMessage());
		}

		logobject.setResourceType("虚拟机模板");
		logobject.setResourceId(vmTempletId);
		logobject.setObjects(new Object[] { vmTempletE.getName() });
		LogUitls.putArgs(logobject);

		logger.debug("模板发布成功" + vmTempletE.getName());
	}

	/**
	 * 
	 * 功能: 取消发布模板
	 *
	 * @param vmTempletId
	 */
	@RequestMapping(path = "/unReleaseTemplet", method = RequestMethod.GET)
	@ResponseBody
	@Log(message = "取消发布模板信息: {0}", operationType = "取消模板发布", resourceType = "虚拟机模板", moduleType = "模板管理")
	public void unReleaseTemplet(@RequestParam(value = "vmTempletId", required = false) String vmTempletId) {
		VMTempletE vmTempletE = new VMTempletE();
		vmTempletE.setRelationId(vmTempletId);
		vmTempletE.setStatus("1");
		LogObject logobject = new LogObject();
		try {
			vmTempletService.unRelease(vmTempletE);
			logobject.setOperationResult("0");
		} catch (CloudviewException e) {
			logobject.setOperationResult("1");
			logger.error(e.getMessage());
		}

		logobject.setResourceId(vmTempletId);
		logobject.setObjects(new Object[] { vmTempletE.getName() });

		LogUitls.putArgs(logobject);
	}

	/**
	 * 
	 * 功能: 更新模板
	 *
	 * @param vmTempletId
	 */
	@RequestMapping(path = "/updateTemplet", method = RequestMethod.GET)
	@ResponseBody
	public void updateTemplet(String vmTempletId) {
		VMTempletE vmTempletE = new VMTempletE();
		// vmTempletE.setId(vmTempletId);
		try {
			vmTempletService.release(vmTempletE);
		} catch (CloudviewException e) {
			// TODO Auto-generated catch block
		}
	}

	/**
	 * @return the vmTempletService
	 */
	public VMTempletService getVmTempletService() {
		return vmTempletService;
	}

	/**
	 * @param vmTempletService
	 *            the vmTempletService to set
	 */
	public void setVmTempletService(VMTempletService vmTempletService) {
		this.vmTempletService = vmTempletService;
	}

}
