package com.sugon.cloudview.cloudmanager.taskMgmt.serviceImpl.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sugon.cloudview.cloudmanager.taskMgmt.service.bo.TaskInfo;
import com.sugon.cloudview.cloudmanager.taskMgmt.service.exception.TaskinfoException;
import com.sugon.cloudview.cloudmanager.taskMgmt.service.service.TaskInfoService;
import com.sugon.cloudview.cloudmanager.taskMgmt.serviceImpl.dao.entity.TaskInfoE;
import com.sugon.cloudview.cloudmanager.taskMgmt.serviceImpl.dao.service.TaskInfoDaoService;
import com.sugon.cloudview.cloudmanager.taskMgmt.serviceImpl.utils.CommonUtils;
import com.sugon.cloudview.cloudmanager.vijava.base.CloudviewExecutor;
import com.sugon.cloudview.cloudmanager.vijava.exception.VirtException;
import com.sugon.cloudview.cloudmanager.vijava.vm.QueryTask.QueryTaskAnswer;
import com.sugon.cloudview.cloudmanager.vijava.vm.QueryTask.QueryTaskAnswer.QueriedTaskInfo;
import com.sugon.cloudview.cloudmanager.vijava.vm.QueryTask.QueryTaskCmd;

@Configurable
@EnableScheduling
@Service("taskInfoServiceImpl")
public class TaskInfoServiceImpl implements TaskInfoService {
	private static Logger logger = LoggerFactory.getLogger(TaskInfoServiceImpl.class);
	@Autowired
	private TaskInfoDaoService taskInfoDaoService;
	@Autowired
	private CloudviewExecutor cloudviewExecutor;

	@Override
	public TaskInfo addTaskInfo(TaskInfo taskInfo) throws TaskinfoException {
		logger.debug("step into method addTaskInfo(),任务名称"+taskInfo);
		TaskInfoE taskInfoE = new TaskInfoE();
		taskInfo.setStatus("running");
		CommonUtils.converterMethod(taskInfoE, taskInfo);
		taskInfoE = taskInfoDaoService.addTaskInfo(taskInfoE);
		CommonUtils.converterMethod(taskInfo, taskInfoE);
		logger.debug("保存后的新任务"+taskInfo);
		return taskInfo;
	}

	@Override
	public TaskInfo updateTaskInfo(TaskInfo taskInfo) throws TaskinfoException {
		TaskInfoE taskInfoE = new TaskInfoE();
		CommonUtils.converterMethod(taskInfoE, taskInfo);
		taskInfoE = taskInfoDaoService.addTaskInfo(taskInfoE);
		CommonUtils.converterMethod(taskInfo, taskInfoE);
		return taskInfo;
	}

	/**
	 * 周期性抓取cloudmanager用户下的所有任务
	 * 
	 * @throws VirtException
	 */
	//@Scheduled(cron = "0/5 * * * * * ")
	public void addTaskInfo() throws TaskinfoException {
		logger.debug("step into method addTaskInfo()，定时抓取表中任务数据");
		try {
			QueryTaskCmd queryTaskCmd = new QueryTaskCmd();
			Date endTime = new Date();
			Long endTimes=endTime.getTime();
			
			Date startTime=new Date();
			Long starttimes=endTimes-(60*1000);
			startTime.setTime(starttimes);
			
			queryTaskCmd.setStartTime(startTime);
			queryTaskCmd.setEndTime(endTime);
			logger.debug("周期性抓取数据开始时间："+startTime+"截止时间:"+endTime);
			
			logger.debug("开始调用vijava接口方法：cloudviewExecutor.execute(queryTaskCmd)");
			QueryTaskAnswer answer = cloudviewExecutor.execute(queryTaskCmd);
			logger.debug("调用vijava接口方法返回answer：" + answer);
			if (answer != null && answer.isSuccess()) {
				List<QueriedTaskInfo> lst = answer.getLsTaskInfo();
				logger.debug("方法返回List<QueriedTaskInfo>个数：" + lst.size());
				for (QueriedTaskInfo queriedTaskInfo : lst) {
					TaskInfo taskInfo = new TaskInfo();
					taskInfo.setTaskinfoId(queriedTaskInfo.getTaskId());
					taskInfo.setTaskinfoName(queriedTaskInfo.getName());
					taskInfo.setResourceName(queriedTaskInfo.getEntity());// 资源名称
					taskInfo.setResourceId(queriedTaskInfo.getEntityId());
					taskInfo.setProcess(queriedTaskInfo.getProcess());
					taskInfo.setStatus(queriedTaskInfo.getStatus());
					taskInfo.setCreateDate(queriedTaskInfo.getStartTime());//开始时间、
					taskInfo.setCompletDate(queriedTaskInfo.getCompleteTime());
					taskInfo.setDescription(queriedTaskInfo.getDetail());
					if("success".equals(queriedTaskInfo.getStatus())){
						taskInfo.setDescription("complete");
					}
					addTaskInfo(taskInfo);
				}
			}
		}  catch (VirtException virtException) {
			logger.debug("调用vijava接口错误", virtException);
		}
	}

	/**
	 * 周期性更新表中任务状态
	 * 
	 * @throws VirtException
	 */
	@Scheduled(cron = "0 */1 *  * * * ")
	//@Scheduled(cron = "0/15 * * * * * ")
	public void updateTaskInfo() throws TaskinfoException {
		logger.debug("step into method updateTaskInfo()定时更新表中任务数据......");
		List<TaskInfo> taskInfolst = findByStatus("running");
		HashMap<String, Date> map = new HashMap<>();
		logger.debug("待更新任务有"+taskInfolst.size()+taskInfolst);
		if(taskInfolst!=null&&taskInfolst.size()>0){
			for (TaskInfo taskInfo : taskInfolst) {
				map.put(taskInfo.getTaskinfoId(), taskInfo.getCreateDate());
			}
			logger.debug("参数map:"+map);
			try {
				QueryTaskCmd queryTaskCmd = new QueryTaskCmd();   
				queryTaskCmd.setTaskIds(map);
				logger.debug("开始调用vijava接口方法..........");
				QueryTaskAnswer answer = cloudviewExecutor.execute(queryTaskCmd);
				logger.debug("调用vijava接口方法返回：" + answer);
				if (answer != null && answer.isSuccess()) {
					List<QueriedTaskInfo> lst = answer.getLsTaskInfo();
					logger.debug("vijava接口方法返回：" + lst.size()+"有："+lst);
					for (QueriedTaskInfo queriedTaskInfo : lst) {
						if(queriedTaskInfo.getTaskId()==null){
							return ;
						}
						TaskInfo taskInfo = findOne(queriedTaskInfo.getTaskId());
						taskInfo.setTaskinfoName(queriedTaskInfo.getName());
						taskInfo.setProcess(queriedTaskInfo.getProcess());
						taskInfo.setStatus(queriedTaskInfo.getStatus());
						taskInfo.setCompletDate(queriedTaskInfo.getCompleteTime());
						taskInfo.setDescription(queriedTaskInfo.getDetail());
						if("success".equals(queriedTaskInfo.getStatus())){
							taskInfo.setDescription("complete");
						}
						logger.debug("更新任务为："+taskInfo);
						updateTaskInfo(taskInfo);
					}
				}
			} catch (VirtException e) {
				logger.debug("调用vijava接口错误", e);
			}
		}
		
	}
	
	@Override
	public Map<String, Object>  findAllTaskInfo(int pageNum, int pageSize, Map<String, String> otherParam)
			throws TaskinfoException {
		List<TaskInfo> taskInfolst = new ArrayList<TaskInfo>();
		Pageable pageable = new PageRequest(pageNum - 1, pageSize);
		Page<TaskInfoE> taskInfoEPage = taskInfoDaoService.findAllTaskInfoMy(pageable, otherParam);
		List<TaskInfoE> taskInfoEls = taskInfoEPage.getContent();
		for (TaskInfoE taskInfoE : taskInfoEls) {
			TaskInfo taskInfo = new TaskInfo();
			CommonUtils.converterMethod(taskInfo, taskInfoE);
			taskInfolst.add(taskInfo);
		}
		 Map<String, Object> map = new HashMap<String, Object>();
		  map.put("total", taskInfoEPage.getTotalElements());
          map.put("taskInfolst", taskInfolst);
		return map;
	}

	@Override
	public List<TaskInfo> findByStatus(String status) {
		List<TaskInfoE> taskInfoElst = taskInfoDaoService.findByStatus(status);
		List<TaskInfo> taskInfolst = new ArrayList<TaskInfo>();
		for (TaskInfoE taskInfoE : taskInfoElst) {
			TaskInfo taskInfo = new TaskInfo();
			CommonUtils.converterMethod(taskInfo, taskInfoE);
			taskInfolst.add(taskInfo);
		}
		return taskInfolst;
	}
	/*
	 * 运营管理员-查看全部任务前10条;组织管理员-用orgid关联;组织用户-userid关联;
	 */
	@Override
	public List<TaskInfo> findAllRecentTask() throws TaskinfoException {
		logger.debug("step into method findAllRecentTask()正在进行的任务列表......");
		Map<String, String> otherParam = new HashMap<String, String>();
		otherParam.put("status", "running");
		Map<String, Object> map =findAllTaskInfo(1,10,otherParam);
		List<TaskInfo> taskInfolst = (List<TaskInfo>) map.get("taskInfolst");
		logger.debug("正在进行的任务有："+taskInfolst.size());
		return taskInfolst;
	}

	@Override
	public TaskInfo findOne(String taskInfoId) throws TaskinfoException {
		TaskInfoE taskInfoE=taskInfoDaoService.findOne(taskInfoId);
		TaskInfo taskInfo=new TaskInfo();
		CommonUtils.converterMethod(taskInfo, taskInfoE);
		return taskInfo;
	}
}
