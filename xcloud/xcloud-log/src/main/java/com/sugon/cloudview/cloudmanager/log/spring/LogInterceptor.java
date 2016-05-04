package com.sugon.cloudview.cloudmanager.log.spring;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sugon.cloudview.cloudmanager.log.api.Log;
import com.sugon.cloudview.cloudmanager.log.api.LogAPI;
import com.sugon.cloudview.cloudmanager.log.impl.LogObject;
import com.sugon.cloudview.cloudmanager.log.impl.LogUitls;

public class LogInterceptor extends HandlerInterceptorAdapter {
	
	private final static Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

	@Autowired
	private LogAPI logAPI;
	public void setLogAPI(LogAPI logAPI) {
		this.logAPI = logAPI;
	}
	
	/**
	 * 将request存入LogUitl中的LOCAL_REQUEST。
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);
		LogUitls.putRequest(request);
		LogUitls.putArgs(new LogObject());
		return true;
	}

	
	/**
	 * 获得注解，在数据库中写入日志信息
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		if (!(handler instanceof HandlerMethod)) {return;}
		long startTime = (Long) request.getAttribute("startTime");
		long endTime = System.currentTimeMillis();
		final long executeTime = endTime - startTime;
		final HandlerMethod handlerMethod = (HandlerMethod) handler;
		final Method method = handlerMethod.getMethod();
		final LogObject logObject = LogUitls.getArgs();
		final Log log = method.getAnnotation(Log.class);
		if(null == log) {return;}
		logObject.setExecuteTime(executeTime);
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					HandlerMethod method = (HandlerMethod) handler;
					MethodParameter[] params = method.getMethodParameters();
					/*
					 * params[0].getParameterName();
					 * params[0].getParameterAnnotations();
					 */
					logAPI.log(log, logObject);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getStackTrace() + "");
				}
			}
		}).start();

	}

	
	/**
	 * 清除LogUitl中的LOCAL_REQUEST。
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		LogUitls.removeRequest();
	}
}
