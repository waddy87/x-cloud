package org.waddys.xcloud.task.service.service;

import java.util.List;
import java.util.Map;

import org.waddys.xcloud.task.bo.TaskInfo;
import org.waddys.xcloud.task.exception.TaskinfoException;

/**
 * 功能描述： 任务管理模块，用户进行任务管理
 * 
 * @author ghj
 *
 */
public interface TaskInfoService {
	
	/**
	 * 功能描述：获得一条任务
	 * 
	 * @param taskInfo
	 *            任务实体
	 * @return 返回对象
	 */
	public TaskInfo findOne(String taskInfoId) throws TaskinfoException;

	/**
	 * 功能描述：添加一条任务
	 * 
	 * @param taskInfo
	 *            任务实体
	 * @return 返回对象
	 */
	public TaskInfo addTaskInfo(TaskInfo taskInfo) throws TaskinfoException;

	/**
	 * 功能描述：更新一条任务
	 * 
	 * @param taskInfo
	 *            任务实体
	 * @return 返回对象
	 */
	public TaskInfo updateTaskInfo(TaskInfo taskInfo) throws TaskinfoException;

	/**
	 * 功能描述：查询出所有任务
	 * 
	 * @param pageNum
	 *            当前第几页
	 * @param pageSize
	 *            一页显示多少条数据
	 * @param otherParam
	 *            查询参数
	 * @return
	 * @throws TaskinfoException
	 */
	public Map<String, Object>  findAllTaskInfo(int pageNum, int pageSize, Map<String, String> otherParam)
			throws TaskinfoException;

	/**
	 * 功能说明：根据任务状态查询任务列表
	 * 
	 * @param status
	 * @return 任务列表
	 */
	public List<TaskInfo> findByStatus(String status);
	
	/**
	 * 功能描述：获得所有任务中的最近10条任务
	 * @return
	 * @throws TaskinfoException
	 */
	public List<TaskInfo> findAllRecentTask()throws TaskinfoException;

	public void updateTaskInfo() throws TaskinfoException;
}
