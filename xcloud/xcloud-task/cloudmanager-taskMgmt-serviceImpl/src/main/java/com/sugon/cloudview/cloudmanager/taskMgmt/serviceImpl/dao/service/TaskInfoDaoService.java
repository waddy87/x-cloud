package com.sugon.cloudview.cloudmanager.taskMgmt.serviceImpl.dao.service;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.sugon.cloudview.cloudmanager.taskMgmt.serviceImpl.dao.entity.TaskInfoE;

public interface TaskInfoDaoService {

	TaskInfoE findOne(String taskInfoId);
	TaskInfoE addTaskInfo(TaskInfoE taskInfoE);

	TaskInfoE updateTaskInfo(TaskInfoE taskInfoE);

	Page<TaskInfoE> findAllTaskInfo( Pageable pageable,  Map<String, String> otherParam);
	Page<TaskInfoE> findAllTaskInfoMy( Pageable pageable,  Map<String, String> otherParam);
	
	List<TaskInfoE> findByStatus(String status);

	Page<TaskInfoE> findTaskRecent( Pageable pageable,  Map<String, String> otherParam);
	
}
