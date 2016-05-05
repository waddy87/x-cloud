package com.sugon.cloudview.cloudmanager.managedvm.api.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sugon.cloudview.cloudmanager.common.base.exception.JsonResult;
import com.sugon.cloudview.cloudmanager.log.api.Log;
import com.sugon.cloudview.cloudmanager.managedvm.service.exception.OldVirtualMachineException;
import com.sugon.cloudview.cloudmanager.managedvm.service.service.OldVirtualMachineService;
import com.sugon.cloudview.cloudmanager.org.bo.Organization;
import com.sugon.cloudview.cloudmanager.org.constant.OrgStatus;
import com.sugon.cloudview.cloudmanager.org.service.OrganizationService;
import com.sugon.cloudview.cloudmanager.usermgmt.service.bo.User;
import com.sugon.cloudview.cloudmanager.vijava.vm.QueryVMConsole.QueryVMConsoleAnswer;
import com.sugon.cloudview.cloudmanager.vijava.vm.QueryVMConsole.QueryVMConsoleCmd;
import com.sugon.cloudview.cloudmanager.vijava.vm.QueryVMConsoleTask;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/oldvm")
public class OldVirtualMachineController {

	private static Logger logger = LoggerFactory.getLogger(OldVirtualMachineController.class);

	@Autowired
	private OldVirtualMachineService oldVirtualMachineService;

	@Autowired
	private OrganizationService organizationService;

	@RequestMapping(method = RequestMethod.GET, value = "/mandateIndex")
	public ModelAndView mandateIndex(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("managedVMManagement/mandateIndex");
		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("currentUser");
		mav.addObject("orgId", user.getOrgId());
		return mav;
		// return new ModelAndView("managedVMManagement/mandateIndex");
	}

	@RequestMapping(method = RequestMethod.GET, value = "/vmVnc")
	public String vmVnc(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "vmId", required = true) String vmId, ModelMap model) {
		String vncUrl = "";
		try {
			QueryVMConsoleCmd queryVMConsoleCmd = new QueryVMConsoleCmd();
			queryVMConsoleCmd.setVmId(vmId);
			QueryVMConsoleTask task = new QueryVMConsoleTask(queryVMConsoleCmd);
			QueryVMConsoleAnswer answer = task.execute();
			vncUrl = answer.getConsoleURL();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		model.put("vncUrl", vncUrl);
		return "managedVMManagement/vnc";
	}
	@RequestMapping(method = RequestMethod.GET, value = "/assignVM/{names}")
	public ModelAndView assignIndex(@PathVariable List<String> names) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("managedVMManagement/assignVM");
		System.out.println("name是" + names);
		try {
			// List<Organization> orgList = organizationService.listAll();
			Organization organization = new Organization();
			organization.setStatus(OrgStatus.NORMAL);
			Page<Organization> orgList = organizationService.pageAll(organization, null);
			JSONArray orgArr = null;
			if (null != orgList) {
				orgArr = JSONArray.fromObject(orgList.getContent());
			}

			if (names.size() > 3) {
				String name = names.get(2) + "等";
				names = names.subList(0, 2);
				names.add(name);
			}

			JSONArray nameArr = JSONArray.fromObject(names);

			mav.addObject("orgList", orgArr);
			mav.addObject("nameList", nameArr);
			logger.debug("跳转成功！");
		} catch (Exception localException) {
			logger.error("跳转失败！");
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/synchronize", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	@Transactional(readOnly = false)
	@Log(message = "同步利旧虚拟机", resourceType = "利旧虚拟机", operationType = "同步", moduleType = "利旧虚拟机")
	public String syncVM() {

		try {
			System.out.println("开始同步！！！");
			logger.debug("开始同步！");
			oldVirtualMachineService.syncVM();
			logger.debug("同步完成！");
			System.out.println("同步完成！！！");
			return new JsonResult().success().setResult("同步成功").toString();
			// 采用下面的方式会报堆栈溢出的异常，原因待分析
			// return new
			// JsonResult().success().addResultObject("同步成功").toString();
		} catch (OldVirtualMachineException e) {
			logger.error("同步失败！");
			return new JsonResult().failed().setError(e).toString();
		}
	}

	// @RequestMapping(method = RequestMethod.GET,
	// value = "/mandatedoldvms",
	// produces = { "application/json;charset=UTF-8" })
	// @ResponseBody
	// @Transactional(readOnly = true)
	// public String getOldVM4Table(@RequestParam(value = "name", required =
	// false) String name,
	// @RequestParam int page,
	// @RequestParam int rows) {
	//
	// JSONObject jsonObject = new JSONObject();
	//
	// if (name == null) {
	// name = "";
	// }
	//
	// try {
	// jsonObject.put("total",
	// Integer.valueOf(oldVirtualMachineService.countByName(name)));
	// jsonObject.put("rows", oldVirtualMachineService.listByName(name, page,
	// rows));
	// } catch (OldVirtualMachineException e) {
	// jsonObject.clear();
	// jsonObject.put("flag", Boolean.valueOf(false));
	// jsonObject.put("message", e.getMessage());
	// }
	// return jsonObject + "";
	// }

	@RequestMapping(method = RequestMethod.GET, value = "/mandatedoldvms", produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	@Transactional(readOnly = true)
	public String getOldVM4Table(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "orgId", required = false) String orgId, @RequestParam int page,
			@RequestParam int rows) {

		JSONObject jsonObject = new JSONObject();

		if (name == null) {
			name = "";
		}

		if (orgId == null) {
			orgId = "";
		}

		System.out.println("组织Id是:" + orgId);

		try {
			Map<String, Object> map = oldVirtualMachineService.list4Table(name, orgId, page, rows);
			jsonObject.put("total", map.get("total"));
			jsonObject.put("rows", map.get("list"));
		} catch (OldVirtualMachineException e) {
			jsonObject.clear();
			jsonObject.put("flag", Boolean.valueOf(false));
			jsonObject.put("message", e.getMessage());
		}
		return jsonObject + "";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/assign/{id}/org/{org_id}", produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	@Transactional(readOnly = false)
	@Log(message = "分配利旧虚拟机", resourceType = "利旧虚拟机", operationType = "分配", moduleType = "利旧虚拟机")
	public String assign(@PathVariable String id, @PathVariable String org_id) {
		try {
			return new JsonResult().success().setResult(oldVirtualMachineService.assign(id, org_id)).toString();
		} catch (OldVirtualMachineException e) {
			return new JsonResult().failed().setError(e).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/assign", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	@Transactional(readOnly = false)
	@Log(message = "批量分配利旧虚拟机", resourceType = "利旧虚拟机", operationType = "批量分配", moduleType = "利旧虚拟机")
	public String assignMany(@RequestParam List<String> ids, @RequestParam String orgId) {
		try {
			if (ids.size() == 1) {
				return new JsonResult().success().setResult(oldVirtualMachineService.assign(ids.get(0), orgId))
						.toString();
			} else {
				oldVirtualMachineService.assign(ids, orgId);
				logger.debug("分配成功");
				return new JsonResult().success().setResult("分配成功").toString();
			}
		} catch (OldVirtualMachineException e) {
			return new JsonResult().failed().setError(e).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/recycle/{id}", produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	@Transactional(readOnly = false)
	@Log(message = "回收利旧虚拟机", resourceType = "利旧虚拟机", operationType = "回收", moduleType = "利旧虚拟机")
	public String recycle(@PathVariable String id) {
		try {
			return new JsonResult().success().setResult(oldVirtualMachineService.recycle(id)).toString();
		} catch (OldVirtualMachineException e) {
			return new JsonResult().failed().setError(e).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/recycle", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	@Transactional(readOnly = false)
	@Log(message = "批量回收利旧虚拟机", resourceType = "利旧虚拟机", operationType = "批量回收", moduleType = "利旧虚拟机")
	public String recycleMany(@RequestParam List<String> ids) {
		try {
			oldVirtualMachineService.recycle(ids);
			logger.debug("回收成功");
			return new JsonResult().success().setResult("回收成功").toString();
		} catch (OldVirtualMachineException e) {
			return new JsonResult().failed().setError(e).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/delete/{id}", produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	@Transactional(readOnly = false)
	@Log(message = "删除利旧虚拟机", resourceType = "利旧虚拟机", operationType = "删除", moduleType = "利旧虚拟机")
	public String delete(@PathVariable String id) {
		// try {
		// oldVirtualMachineService.deleteVMRecord(id);
		// logger.debug("删除虚拟机记录成功");
		// return new JsonResult().success().setResult("删除虚拟机记录成功").toString();
		// } catch (OldVirtualMachineException e) {
		// return new JsonResult().failed().setError(e).toString();
		// }

		try {
			oldVirtualMachineService.deleteVM(id);
			return new JsonResult().success().setResult("开始删除利旧虚拟机").toString();
		} catch (OldVirtualMachineException e) {
			return new JsonResult().failed().setError(e).toString();
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/start/{id}", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	@Transactional(readOnly = false)
	@Log(message = "开启利旧虚拟机", resourceType = "利旧虚拟机", operationType = "开启", moduleType = "利旧虚拟机")
	public String start(@PathVariable String id) {
		try {
			oldVirtualMachineService.start(id);
			return new JsonResult().success().setResult("开始启动利旧虚拟机").toString();
		} catch (OldVirtualMachineException e) {
			return new JsonResult().failed().setError(e).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/start", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	@Transactional(readOnly = false)
	@Log(message = "批量开启利旧虚拟机", resourceType = "利旧虚拟机", operationType = "批量开启", moduleType = "利旧虚拟机")
	public String startMany(@RequestParam List<String> ids) {
		try {
			oldVirtualMachineService.startBatch(ids);
			return new JsonResult().success().setResult("开始启动利旧虚拟机").toString();
		} catch (OldVirtualMachineException e) {
			System.out.println(e.getMessage());
			return new JsonResult().failed().setError(e).toString();
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/stop/{id}", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	@Transactional(readOnly = false)
	@Log(message = "停止利旧虚拟机", resourceType = "利旧虚拟机", operationType = "停止", moduleType = "利旧虚拟机")
	public String stop(@PathVariable String id) {
		try {
			oldVirtualMachineService.stop(id);
			return new JsonResult().success().setResult("开始停止利旧虚拟机").toString();
		} catch (OldVirtualMachineException e) {
			return new JsonResult().failed().setError(e).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/stop", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	@Transactional(readOnly = false)
	@Log(message = "批量停止利旧虚拟机", resourceType = "利旧虚拟机", operationType = "批量停止", moduleType = "利旧虚拟机")
	public String stopMany(@RequestParam List<String> ids) {
		try {
			oldVirtualMachineService.stopBatch(ids);
			return new JsonResult().success().setResult("开始停止利旧虚拟机").toString();
		} catch (OldVirtualMachineException e) {
			return new JsonResult().failed().setError(e).toString();
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/restart/{id}", produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	@Transactional(readOnly = false)
	@Log(message = "重启利旧虚拟机", resourceType = "利旧虚拟机", operationType = "重启", moduleType = "利旧虚拟机")
	public String restart(@PathVariable String id) {
		try {
			oldVirtualMachineService.restart(id);
			return new JsonResult().success().setResult("开始重启利旧虚拟机").toString();
		} catch (OldVirtualMachineException e) {
			return new JsonResult().failed().setError(e).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/restart", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	@Transactional(readOnly = false)
	@Log(message = "批量重启利旧虚拟机", resourceType = "利旧虚拟机", operationType = "批量重启", moduleType = "利旧虚拟机")
	public String restartMany(@RequestParam List<String> ids) {
		try {
			oldVirtualMachineService.restatBatch(ids);
			return new JsonResult().success().setResult("开始重启利旧虚拟机").toString();
		} catch (OldVirtualMachineException e) {
			return new JsonResult().failed().setError(e).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/display", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	@Transactional(readOnly = true)
	public String display(@RequestBody String id) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("oldVM", oldVirtualMachineService.display(id));
			jsonObject.put("history", oldVirtualMachineService.displayHistory(id));
		} catch (OldVirtualMachineException e) {
			jsonObject.clear();
			jsonObject.put("flag", Boolean.valueOf(false));
			jsonObject.put("message", e.getMessage());
		}
		return jsonObject + "";
	}
}
