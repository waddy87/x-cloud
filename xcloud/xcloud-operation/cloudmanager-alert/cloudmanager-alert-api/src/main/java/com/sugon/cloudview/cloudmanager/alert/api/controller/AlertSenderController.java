/**
 * Created on 2016年4月7日
 */
package com.sugon.cloudview.cloudmanager.alert.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sugon.cloudview.cloudmanager.alert.service.bo.AlertSenderUI;
import com.sugon.cloudview.cloudmanager.alert.service.bo.ResourceInfoUI;
import com.sugon.cloudview.cloudmanager.alert.service.bo.TriggerInfoUI;
import com.sugon.cloudview.cloudmanager.alert.service.intf.AlertServiceI;

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
public class AlertSenderController {

	@Autowired
	private AlertServiceI alertServiceI;
	
	@RequestMapping(path="/queryResList",method=RequestMethod.GET)
	@ResponseBody
	public  JSONObject queryResListByResType(@RequestParam(value="resType",required=false)String resType){
		
		JSONObject jsonObject=new JSONObject();
		List<String> resourceInfoUIs= new ArrayList<String> ();
		List<String> resIds= new ArrayList<String> ();
		if(resType.contains("all")){
			//all返回空对象
			jsonObject.put("list", resourceInfoUIs);
		}else{
			List<ResourceInfoUI> list=alertServiceI.getResListByResType(resType);
			for(ResourceInfoUI resourceInfoUI:list){
				resIds.add(resourceInfoUI.getId());
				resourceInfoUIs.add(resourceInfoUI.getName());
			}
			jsonObject.put("list", resourceInfoUIs);
			jsonObject.put("id", resIds);
		}
		return jsonObject;
	}
	
	@RequestMapping(path="/queryAlarmList",method=RequestMethod.GET)
	@ResponseBody
	public  JSONObject queryAlarmList(@RequestParam(value="resource",required=false)String resource){
		
		JSONObject jsonObject=new JSONObject();
		List<String> resourceInfoUIs= new ArrayList<String> ();
		List<String> resId=new ArrayList<String> ();
		if(resource.contains("all")){
			//all返回空对象
			jsonObject.put("list", resourceInfoUIs);
			jsonObject.put("id", resId);
		}else{
			List<TriggerInfoUI> triggerInfoUIs=alertServiceI.getTriggerListByResAndResType(resource, resource);
			jsonObject.put("list", resourceInfoUIs);
			for(TriggerInfoUI resourceInfoUI:triggerInfoUIs){
				resId.add(resourceInfoUI.getTriggerId());
				resourceInfoUIs.add(resourceInfoUI.getTriggerName());
			}
			jsonObject.put("list", resourceInfoUIs);
			jsonObject.put("id", resId);
		}
		return jsonObject;
	}
	
	/*
	 * sendersave页面的controller
	 * alertSender post表单页面，放入alertSenderUI对象，方便传参过来
	 */
	@RequestMapping(path="/senderSaveForm" ,method=RequestMethod.GET)
	public ModelAndView  senderSaveForm(Model model){
		return new ModelAndView("alert/senderSaveForm");
	}
	
	@RequestMapping(path="/deleteSender/{id}",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject deleteSender(@PathVariable("id")String id){
		JSONObject result=new JSONObject();
		try {
			alertServiceI.deleteAlertSender(id);
		} catch (Exception e) {
			result.put("flag", false);
			result.put("message", e.getMessage());
		}
		result.put("flag", true);
		return result;
	}

	@RequestMapping(path="/eanbleSender/{id}/{status}",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject eanbleSender(@PathVariable("id")String id,@PathVariable("status")boolean status){
		JSONObject result=new JSONObject();
		try {
			alertServiceI.enableAlertSender(id, status);
		} catch (Exception e) {
			result.put("flag", false);
			result.put("message", e.getMessage());
		}
		result.put("flag", true);
		return result;
	}
	
	@RequestMapping(path="/batchEanbleSender",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject batchEanbleSender(@RequestParam List<String> ids,@RequestParam boolean status){
		JSONObject result=new JSONObject();
		try {
			alertServiceI.batchEnableAlertSender(ids, status);
		} catch (Exception e) {
			result.put("flag", false);
			result.put("message", e.getMessage());
		}
		result.put("flag", true);
		return result;
	}
	
	@RequestMapping(path="/batchDeleteSender",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject batchDeleteSender(@RequestParam List<String> ids){
		JSONObject result=new JSONObject();
		try {
			alertServiceI.batchDeleteAlertSender(ids);
		} catch (Exception e) {
			result.put("flag", false);
			result.put("message", e.getMessage());
		}
		result.put("flag", true);
		return result;
	}
	
	
	/*
	 * sendersave的form请求处理controller
	 * 通过alertSenderUI对象传参，保存发送器信息
	 */
	@RequestMapping(path="/senderSavePost" ,method=RequestMethod.POST)
	public String senderSave(
		@RequestParam(value="senderName",required=false) String senderName,
		@RequestParam(value="resTypeId",required=false) String resTypeId,
		@RequestParam(value="resourceId",required=false) String resourceId,
		@RequestParam(value="resourceName",required=false) String resourceName,
		@RequestParam(value="alarmId",required=false) String alarmId,
		@RequestParam(value="alarmName",required=false) String alarmName,
		@RequestParam(value="senderType",required=false) String senderType,
		@RequestParam(value="mailSMTP",required=false) String mailSMTP,
		@RequestParam(value="alertLevel",required=false) String alertLevel,
		@RequestParam(value="mailContent",required=false) String mailContent,
		@RequestParam(value="mailReceiver",required=false) String mailReceiver,
		@RequestParam(value="smsDevice",required=false) String smsDevice,
		@RequestParam(value="smsContent",required=false) String smsContent,
		@RequestParam(value="smsReceiver",required=false) String smsReceiver,
		@RequestParam(value="checkStatus",required=false) boolean checkStatus
		){
			AlertSenderUI alertSenderUI=new AlertSenderUI();
			
			alertSenderUI.setName(senderName);
			alertSenderUI.setResType(resTypeId);
			alertSenderUI.setResId(resourceId);
			alertSenderUI.setResName(resourceName);
			alertSenderUI.setTriggerId(alarmId);
			alertSenderUI.setTriggerName(alarmName);
			alertSenderUI.setAlertSendLevel(alertLevel);
			alertSenderUI.setSendType(senderType);
		/*	if(senderType.contains("0")){*/
				alertSenderUI.setReceiver(mailReceiver);
				alertSenderUI.setMessage(mailContent);
		/*		alertSenderUI.setTriggerDetail(mailSMTP);
			}else {
				alertSenderUI.setReceiver(smsReceiver);
				alertSenderUI.setMessage(smsContent);
				alertSenderUI.setTriggerDetail(smsDevice);
			}*/
			alertSenderUI.setEnable(checkStatus);
			alertSenderUI.setSendDate(new Date());
			
			boolean res=alertServiceI.saveAlertSender(alertSenderUI);
			return Boolean.toString(res);
	}
	
}
