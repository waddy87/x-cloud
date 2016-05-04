package com.sugon.cloudview.cloudmanager.log.dao;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sugon.cloudview.cloudmanager.common.base.exception.CloudviewException;
import com.sugon.cloudview.cloudmanager.log.entity.LogInfoEntity;

public interface LogDaoService {

	LogInfoEntity findByLevel(String level);

	LogInfoEntity save(LogInfoEntity logInfo);

	public Page<LogInfoEntity> findAllLoginfo(LogInfoEntity logInfoEntity, Pageable pageable,
			Map<String, String> otherParam) throws CloudviewException;

	/**
	 * 统计总数
	 * 
	 * @param logInfoEntity
	 * @return
	 */
	public long countAllLoginfo(LogInfoEntity logInfoEntity, Map<String, String> otherParam) throws CloudviewException;
}
