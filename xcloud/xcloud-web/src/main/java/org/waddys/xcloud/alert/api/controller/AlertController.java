/**
 * Created on 2016年3月25日
 */
package org.waddys.xcloud.alert.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.waddys.xcloud.alert.api.utils.JsonDateValueProcessor;
import org.waddys.xcloud.alert.service.bo.AlertInfoQueryUI;
import org.waddys.xcloud.alert.service.bo.AlertSenderUI;
import org.waddys.xcloud.alert.service.bo.SystemResourceType;
import org.waddys.xcloud.alert.service.intf.AlertServiceI;
import org.waddys.xcloud.usermgmt.service.bo.Role;
import org.waddys.xcloud.usermgmt.service.bo.RoleEnum;
import org.waddys.xcloud.usermgmt.service.bo.User;
import org.waddys.xcloud.vm.bo.VmHost;
import org.waddys.xcloud.vm.service.VmService;

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
@RequestMapping(path="/alert")
public class AlertController {
	
	@Autowired
	private AlertServiceI alertServiceI;
	
   @Autowired
   private VmService vms;
	
	@RequestMapping(path="/toAlertIndex" ,method=RequestMethod.GET)
	public String toAlertIndex(){
		return "alert/alarmIndex";
	}
	
	/*
	 * 告警信息查询主页面
	 */
	@RequestMapping(path="/alertInfo" ,method=RequestMethod.GET)
	public String alertInfo(){
		return "alert/alertInfo";
	}
	
	
	@RequestMapping(path="/queryAlertInfoTable" ,method=RequestMethod.POST)
	@ResponseBody
	public String queryAlertInfoTable(@RequestParam(value="name",required=false) String resName,
			@RequestParam(value="level",required=false) String alertLevel,
			@RequestParam(value="time",required=false) String time,
			@RequestParam(value="resType",required=false) String resType,
			@RequestParam(value="page",required=false) int page,
			@RequestParam(value="rows",required=false) int rows,HttpSession session){
		
		//通过当前用户的session获取oid
    	User user= 	(User)session.getAttribute("currentUser");
    	String oid=	user.getOrgId();
    	String uid=	user.getId();
    	List<Role> roles =user.getRoles();
    	List<String> vmidStrings=new ArrayList<String>();
    	
    	for(Role role:roles){
    		if(role.getRoleCode().equals(RoleEnum.OPERATION_MANAGER.getCode())){
    			//运营管理员单独页面处理
    		}else if(role.getRoleCode().equals(RoleEnum.ORG_MANAGER.getCode())){
    			resType=SystemResourceType.virtualMachine;
    			//通过oid获取虚拟机列表信息
    			Page<VmHost> pageVmHost=vms.pageByOID(oid, null, null);
    	    	if(pageVmHost!=null && pageVmHost.getTotalElements() >0L){
    	    		List<VmHost> vmHostList=pageVmHost.getContent();
    	    		for(VmHost vmhost: vmHostList){
    	    			vmidStrings.add(vmhost.getInternalId());
    	    		}
    	    	}else{
    	    		//虚拟机id集合为空，则返回空
    	    		return "";
    	    	}
    		}else if(role.getRoleCode().equals(RoleEnum.ORG_USER.getCode())){
    			resType=SystemResourceType.virtualMachine;
    			//通过oid获取虚拟机列表信息
    			Page<VmHost> pageVmHost=vms.pageByUID(uid, null, null);;
    	    	if(pageVmHost!=null && pageVmHost.getTotalElements() >0L){
    	    		List<VmHost> vmHostList=pageVmHost.getContent();
    	    		for(VmHost vmhost: vmHostList){
    	    			vmidStrings.add(vmhost.getInternalId());
    	    		}
    	    	}else{
    	    		//虚拟机id集合为空，则返回空
    	    		return "";
    	    	}
    		}
    	}	    
		
		Map<String, Object> lisAlertInfoQueryUIs=	alertServiceI.getAlertInfo(time, resType,resName, alertLevel, page, rows,vmidStrings);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("total", lisAlertInfoQueryUIs.get("total"));
		map.put("rows", (List<AlertInfoQueryUI>)lisAlertInfoQueryUIs.get("rows"));
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONObject jsonObject=new JSONObject();
		jsonObject=JSONObject.fromObject(map, jsonConfig);
		return  jsonObject +"";
	}
	
	
	@RequestMapping(path="/queryAlertSenderTable" ,method=RequestMethod.POST)
	@ResponseBody
	public String queryAlertSendTable(@RequestParam(value="name",required=false) String resName,
			@RequestParam(value="level",required=false) String alertLevel,
			@RequestParam(value="resType",required=false) String resType,
			@RequestParam(value="page",required=false) int page,
			@RequestParam(value="rows",required=false) int rows){
		
		Map<String, Object> lisAlertSenderUIs=	alertServiceI.getAlertSender( resType,resName, alertLevel, page, rows);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("total", lisAlertSenderUIs.get("total"));
		map.put("rows", (List<AlertSenderUI>)lisAlertSenderUIs.get("rows"));
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONObject jsonObject=new JSONObject();
		jsonObject=JSONObject.fromObject(map, jsonConfig);
		return  jsonObject +"";
	}
		
		
	/*
	 * alertSender查询页面的controller
	 */
	
	@RequestMapping(path="/alertSender" ,method=RequestMethod.GET)
	public ModelAndView alertSenderQuery(HttpServletRequest request, Model model){
		return new ModelAndView("alert/alertSender");
	}
	
		/*
		 * 告警确认
		 * 
		 */
		@RequestMapping(path="/aquire" ,method=RequestMethod.POST)
		@ResponseBody
		public JSONObject aquire(
			@RequestParam(value="resId",required=false) String resId,
			@RequestParam(value="triggerId",required=false) String triggerId
			){
				JSONObject ret=new JSONObject();
				try {
					 alertServiceI.aquireAlert(resId, triggerId);
					 ret.put("flag", true);
				} catch (Exception e) {
				   ret.put("flag", false);
				   ret.put("message", e.getMessage());
				}
				return ret;
		   }
	
	
		@RequestMapping(path="/batchDelete",method=RequestMethod.POST)
		@ResponseBody
		public JSONObject batchDelete(@RequestParam List<String> ids){
			JSONObject result=new JSONObject();
			try {
				alertServiceI.batchDeleteAlert(ids);
			} catch (Exception e) {
				result.put("flag", false);
				result.put("message", e.getMessage());
			}
			result.put("flag", true);
			return result;
		}
	
		@RequestMapping(path="/batchAquire",method=RequestMethod.POST)
		@ResponseBody
		public JSONObject batchAquire(@RequestParam List<String> ids){
			JSONObject result=new JSONObject();
			try {
				alertServiceI.batchAquireAlert(ids);
			} catch (Exception e) {
				result.put("flag", false);
				result.put("message", e.getMessage());
			}
			result.put("flag", true);
			return result;
		 }
			
		@RequestMapping(path="/orgAlert" ,method=RequestMethod.GET)
		public ModelAndView orgAlert(HttpServletRequest request, Model model){
			return new ModelAndView("alert/orgAlert");
		}
}
