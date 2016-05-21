package org.waddys.xcloud.log.api;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.waddys.xcloud.common.base.exception.CloudviewException;
import org.waddys.xcloud.log.entity.LogInfoEntity;
import org.waddys.xcloud.log.impl.LogObject;
import org.waddys.xcloud.log.type.LogLevel;

public interface LogAPI {

	void log(String businessType, String operationType, String moduleType, String resourceType, Long operatingTime,
			String message, LogLevel logLevel, String id, String userName, String ip);

	void log(String businessType, String operationType, String moduleType, String resourceType, Long operatingTime,
			String message, Object[] objects, LogLevel logLevel, String id, String userName, String ip,
			String resourceId, String resourceName, String operationResult);

	/**
	 * 
	 * 得到全局日志等级
	 * 
	 * @return
	 */
	LogLevel getRootLogLevel();

	/**
	 * 
	 * 得到自定义包的日志等级
	 * 
	 * @return
	 */
	Map<String, LogLevel> getCustomLogLevel();

	/**
	 * 
	 * 功能: 日志拦截器添加日志信息
	 *
	 * @param log
	 *            拦截切面对象
	 * @param logObject
	 *            参数对象
	 */
	void log(Log log, LogObject logObject);

	public Page<LogInfoEntity> findAllLoginfo(LogInfoEntity logInfoEntity, int pageNum, int pageSize,
			Map<String, String> otherParam) throws CloudviewException;

	/**
	 * 统计总数
	 * 
	 * @param logInfoEntity
	 * @return
	 */
	public long countAllLoginfo(LogInfoEntity logInfoEntity, Map<String, String> otherParam) throws CloudviewException;
}
