/**
 * Created on 2016年3月31日
 */
package org.waddys.xcloud.log.dao.impl;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.waddys.xcloud.common.base.exception.CloudviewException;
import org.waddys.xcloud.common.base.utils.DateUtil;
import org.waddys.xcloud.common.base.utils.StringUtils;
import org.waddys.xcloud.log.dao.LogDaoService;
import org.waddys.xcloud.log.dao.impl.repository.LogRepository;
import org.waddys.xcloud.log.entity.LogInfoEntity;

/**
 * 功能名: 请填写功能名 功能描述: 请简要描述功能的要点
 * 
 * Copyright: Copyright (c) 2016 公司: 曙光云计算技术有限公司
 *
 * @author 曾兵
 * @version 2.0.0 sp1
 */
@Service("LogDaoService")
public class LogDaoServiceImpl implements LogDaoService {
	/**
	 * @return the logRepository
	 */
	public LogRepository getLogRepository() {
		return logRepository;
	}

	/**
	 * @param logRepository
	 *            the logRepository to set
	 */
	public void setLogRepository(LogRepository logRepository) {
		this.logRepository = logRepository;
	}

	private static final Logger logger = LoggerFactory.getLogger(LogDaoServiceImpl.class);

	@Autowired
	private LogRepository logRepository;

	@Override
	public Page<LogInfoEntity> findAllLoginfo(LogInfoEntity logInfoEntity, Pageable pageable,
			Map<String, String> otherParam) throws CloudviewException {
		logger.debug(logInfoEntity.toString());
		logger.debug(pageable.toString());
		return logRepository.findAll(new Specification<LogInfoEntity>() {
			@Override
			public Predicate toPredicate(Root<LogInfoEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();
				if (logInfoEntity.getBusinessType() != null) {
					expressions.add(cb.like(root.get("businessType"), "%" + logInfoEntity.getBusinessType() + "%"));
				}
				if (logInfoEntity.getUserRealName() != null) {
					expressions.add(cb.like(root.get("userRealName"), "%" + logInfoEntity.getUserRealName() + "%"));
				}
				if (logInfoEntity.getOrgId() != null) {
					expressions.add(cb.equal(root.get("orgId"), logInfoEntity.getOrgId()));
				}
				if (logInfoEntity.getUserName() != null) {
					expressions.add(cb.like(root.get("userName"), "%" + logInfoEntity.getUserName() + "%"));
				}
				if (logInfoEntity.getOperationResult() != null) {
					expressions
							.add(cb.like(root.get("operationResult"), "%" + logInfoEntity.getOperationResult() + "%"));
				}
				if (logInfoEntity.getModuleType() != null) {
					expressions.add(cb.like(root.get("moduleType"), "%" + logInfoEntity.getModuleType() + "%"));
				}
				if (logInfoEntity.getResourceId() != null) {
					expressions.add(cb.like(root.get("resourceId"), "%" + logInfoEntity.getResourceId() + "%"));
				}

				if (StringUtils.isNotBlank(otherParam.get("startTime"))) {
					try {
						expressions.add(cb.between(root.get("operatingTime"),
								DateUtil.parse(otherParam.get("startTime"), "yyyy-MM-dd hh:mm:ss"),
								DateUtil.parse(otherParam.get("endTime"), "yyyy-MM-dd hh:mm:ss")));
					} catch (ParseException e) {
						logger.error(e.getMessage());
					}
				}
				query.orderBy(cb.desc(root.get("operatingTime")));
				return predicate;
			}
		}, pageable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.waddys.xcloud.log.dao.LogDaoService#findByLevel(java.
	 * lang.String)
	 */
	@Override
	public LogInfoEntity findByLevel(String level) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.waddys.xcloud.log.dao.LogDaoService#save(com.sugon.
	 * cloudview.cloudmanager.log.entity.LogInfoEntity)
	 */
	@Override
	public LogInfoEntity save(LogInfoEntity logInfo) {

		return logRepository.save(logInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.waddys.xcloud.log.dao.LogDaoService#countAllLoginfo(
	 * org.waddys.xcloud.log.entity.LogInfoEntity)
	 */
	@Override
	public long countAllLoginfo(LogInfoEntity logInfoEntity, Map<String, String> otherParam) throws CloudviewException {
		long countlog = 0;
		logger.debug(logInfoEntity.toString());
		logger.debug(otherParam.toString());
		// try {
		// countlog = logRepository.countByAll(logInfoEntity.getModuleType(),
		// logInfoEntity.getUserName(),
		// logInfoEntity.getResourceId(),
		// DateUtil.parse(otherParam.get("startTime")),
		// DateUtil.parse(otherParam.get("endTime")));
		// } catch (ParseException e) {
		//
		// logger.error(e.getMessage());
		// }
		logger.debug("日志总数为：" + countlog);
		return countlog;
	}
}
