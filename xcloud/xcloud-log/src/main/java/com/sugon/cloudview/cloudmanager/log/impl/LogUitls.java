package com.sugon.cloudview.cloudmanager.log.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;

import com.sugon.cloudview.cloudmanager.usermgmt.service.bo.User;

public abstract class LogUitls {
	
	// 用于存储每个线程的request请求
	private static final ThreadLocal<HttpServletRequest> LOCAL_REQUEST = 
			new ThreadLocal<HttpServletRequest>();

	public final static String LOG_ARGUMENTS = "log_arguments";

	public static void putRequest(HttpServletRequest request) {
		LOCAL_REQUEST.set(request);
	}

	public static HttpServletRequest getRequest() {
		return LOCAL_REQUEST.get();
	}

	public static void removeRequest() {
		LOCAL_REQUEST.remove();
	}

	/**
	 * 将LogMessageObject放入LOG_ARGUMENTS。 描述
	 * @param logMessageObject
	 */
	public static void putArgs(LogObject logMessageObject) {
		
		HttpServletRequest request = getRequest();
		// HttpSession session = request.getSession();
		//UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
		//		UserInfo.USER_CONTEXT_ATTR_NAME);
		//if (null != userInfo) {
			// 统一使用用户真实名称userName，而不是登录名loginName
		//	logMessageObject.setUserName(userInfo.getUserDetail().getUserName());
			// TODO 不确定这里的id，是否正确？？
		//	logMessageObject.setUserId(userInfo.getUserDetail().getId());
		//}
		
		//TODO 1. 获得HttpServletRequest中的用户信息
		//2. 获得HttpServletRequest中的IP信息
		User u = (User) SecurityUtils.getSubject().getPrincipal();
		if (null != u) {
			logMessageObject.setUserName(u.getRealname());
			logMessageObject.setUserId(u.getId());
			logMessageObject.setOrgId(u.getOrgId());
		}
		logMessageObject.setIp(getIpAddr(request));
		request.setAttribute(LOG_ARGUMENTS, logMessageObject);
	}

	
	/**
	 * 从HttpServletRequest中获得请求的IP地址
	 * @param request
	 * @return
	 */
	private static String getIpAddr(HttpServletRequest request) {
		
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	

	/**
	 * 从HttpServletRequest中获得请求LogMessageObject
	 * @param logMessageObject
	 */
	public static LogObject getArgs() {
		HttpServletRequest request = getRequest();
		return (LogObject) request.getAttribute(LOG_ARGUMENTS);
	}

}
