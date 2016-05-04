package com.sugon.cloudview.cloudmanager.log.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sugon.cloudview.cloudmanager.common.base.exception.CloudviewException;
import com.sugon.cloudview.cloudmanager.common.base.utils.StringUtils;
import com.sugon.cloudview.cloudmanager.log.api.Log;
import com.sugon.cloudview.cloudmanager.log.api.LogAPI;
import com.sugon.cloudview.cloudmanager.log.dao.LogDaoService;
import com.sugon.cloudview.cloudmanager.log.entity.LogInfoEntity;
import com.sugon.cloudview.cloudmanager.log.type.LogLevel;
import com.sugon.cloudview.cloudmanager.taskMgmt.service.bo.TaskInfo;
import com.sugon.cloudview.cloudmanager.taskMgmt.service.exception.TaskinfoException;
import com.sugon.cloudview.cloudmanager.taskMgmt.service.service.TaskInfoService;

@Service
public class LogImpl implements LogAPI {

	private static final Logger logger = LoggerFactory.getLogger(LogImpl.class);

	public LogImpl() {
		rootLogLevel = LogLevel.ERROR;
		customLogLevel.put("com.sugon.webapps.service.log.export", LogLevel.TRACE);
	}

	@Autowired
	private LogDaoService logDaoService;

	@Autowired
	private TaskInfoService taskInfoService;

	private LogLevel rootLogLevel = LogLevel.ERROR;
	private Map<String, LogLevel> customLogLevel = new HashMap<String, LogLevel>();

	public void setRootLogLevel(LogLevel rootLogLevel) {
		this.rootLogLevel = rootLogLevel;
	}

	@Override
	public LogLevel getRootLogLevel() {
		return rootLogLevel;
	}

	public void setCustomLogLevel(Map<String, LogLevel> customLogLevel) {
		this.customLogLevel = customLogLevel;
	}

	@Override
	public Map<String, LogLevel> getCustomLogLevel() {
		return customLogLevel;
	}

	@Override
	public void log(String businessType, String operationType, String moduleType, String resourceType,
			Long operatingTime, String message, LogLevel logLevel, String id, String userName, String ip) {
		log(businessType, operationType, moduleType, resourceType, operatingTime, message, null, logLevel, id, userName,
				ip, null, null, null);
	}

	@Override
	public void log(String businessType, String operationType, String moduleType, String resourceType,
			Long operatingTime, String message, Object[] objects, LogLevel logLevel, String id, String userName,
			String ip, String resourceId, String resourceName, String operationResult) {

		final LogInfoEntity logInfo = new LogInfoEntity();
		logInfo.setBusinessType(businessType);
		logInfo.setOperationType(operationType);
		logInfo.setModuleType(moduleType);
		logInfo.setResourceType(resourceType);
		logInfo.setOperatingTime(new Date());
		logInfo.setExecuteTime(operatingTime);
		logInfo.setMessage(message);
		logInfo.setLevel(logLevel.value());
		logInfo.setUserName(userName);
		logInfo.setIp(ip);
		logInfo.setResourceId(resourceId);
		logInfo.setResourceName(resourceName);
		logInfo.setOperationResult(operationResult);
		logDaoService.save(logInfo);

		// MessageFormat mFormat = new MessageFormat(message);
		// String result = mFormat.format(objects);
		logger.info(logInfo.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sugon.cloudview.cloudmanager.log.api.LogAPI#log(com.sugon.cloudview.
	 * cloudmanager.log.impl.LogObject)
	 */
	@Override
	public void log(Log log, LogObject logObject) {
		// TODO Auto-generated method stub
		final LogInfoEntity logInfo = new LogInfoEntity();
		logInfo.setBusinessType(log.businessType());
		logInfo.setOperationType(log.operationType());
		logInfo.setModuleType(log.moduleType());
		logInfo.setResourceType(log.resourceType());
		logInfo.setOperatingTime(new Date());
		logInfo.setExecuteTime(logObject.getExecuteTime());
		logInfo.setMessage(MessageFormat.format(log.message(), logObject.getObjects()));
		logInfo.setLevel(log.level().value());
		logInfo.setUserName(logObject.getUserId());
		logInfo.setUserRealName(logObject.getUserName());
		logInfo.setIp(logObject.getIp());
		logInfo.setResourceId(logObject.getResourceId());
		logInfo.setResourceName(logObject.getResourceName());
		logInfo.setDetailMessage(logObject.getDetailMessage());
		logInfo.setOrgId(logObject.getOrgId());
		if (StringUtils.isNotBlank(logObject.getOperationResult())) {
			logInfo.setOperationResult(logObject.getOperationResult());
		} else {
			logInfo.setOperationResult("0");
		}

		logDaoService.save(logInfo);
		ArrayList<TaskInfo> taskList = logObject.getTaskInfoList();
		if (null != logObject.getOperationResult() && !logObject.getOperationResult().equals("1")) {// 操作结果为失败的不处理
			logger.debug("失败类型的任务操作，不创建任务记录");
		} else {
			if (log.isTaskType() && null != logObject.getTaskId()) {// 任务类型操作

				aopTask4Operation(log, logObject);
				logger.debug("成功类型的任务操作，创建任务记录" + logObject.toString());
				return;
			} else if (null != taskList && taskList.size() > 0) {

				for (Iterator<TaskInfo> iterator = taskList.iterator(); iterator.hasNext();) {
					TaskInfo taskInfo = iterator.next();
					taskInfo.setUserId(logObject.getUserId());
					taskInfo.setOrgId(logObject.getOrgId());
					taskInfo.setProcess(new Integer(0));
					logger.info(logInfo.toString());
					try {
						taskInfoService.addTaskInfo(taskInfo);
					} catch (TaskinfoException e) {
						logger.error(e.getMessage());
					}
				}
			}
		}
		logger.info(logInfo.toString());
		// MessageFormat mFormat = new MessageFormat(message);
		// String result = mFormat.format(objects);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sugon.cloudview.cloudmanager.log.api.LogAPI#findAllLoginfo(com.sugon.
	 * cloudview.cloudmanager.log.entity.LogInfoEntity, int, int)
	 */
	@Override
	public Page<LogInfoEntity> findAllLoginfo(LogInfoEntity logInfoEntity, int pageNum, int pageSize,
			Map<String, String> otherParam) throws CloudviewException {
		Pageable pageable = new PageRequest(pageNum - 1, pageSize);
		return logDaoService.findAllLoginfo(logInfoEntity, pageable, otherParam);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sugon.cloudview.cloudmanager.log.api.LogAPI#countAllLoginfo(com.sugon
	 * .cloudview.cloudmanager.log.entity.LogInfoEntity)
	 */
	@Override
	public long countAllLoginfo(LogInfoEntity logInfoEntity, Map<String, String> otherParam) throws CloudviewException {
		// TODO Auto-generated method stub
		return logDaoService.countAllLoginfo(logInfoEntity, otherParam);
	}

	public void aopTask4Operation(Log log, LogObject logObject) {
		logger.debug("操作任务创建对象===================================================" + logObject.toString());
		TaskInfo taskInfo = new TaskInfo();
		taskInfo.setCreateDate(logObject.getTaskCreateTime());
		taskInfo.setTaskinfoId(logObject.getTaskId());
		taskInfo.setResourceId(logObject.getResourceId());
		taskInfo.setResourceName(logObject.getResourceName());
		taskInfo.setResourceType(log.resourceType());
		taskInfo.setUserId(logObject.getUserId());
		taskInfo.setOrgId(logObject.getOrgId());
		taskInfo.setTaskinfoName(logObject.getTaskInfoName());
		taskInfo.setProcess(new Integer(0));
		try {
			taskInfoService.addTaskInfo(taskInfo);
		} catch (TaskinfoException e) {
			logger.error(e.getMessage());
		}

	}

}
