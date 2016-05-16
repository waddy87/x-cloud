package com.sugon.cloudview.cloudmanager.vijava.util;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sugon.cloudview.cloudmanager.vijava.base.Answer;
import com.sugon.vim25.TaskInfo;
import com.sugon.vim25.mo.Task;

public class TaskAnswerConvert {
	 private static Logger logger = LoggerFactory.getLogger(TaskAnswerConvert.class);

	public static Answer setTask(Answer answer, Task task) {
		TaskInfo taskInfo;
		try {
			taskInfo = task.getTaskInfo();

			if (taskInfo != null) {
				answer.setTaskId(taskInfo.getKey());
				answer.setTaskName(taskInfo.getDescriptionId());
				answer.setTaskCreateTime(taskInfo.getQueueTime().getTime());
				answer.setTaskStatus(taskInfo.getState().name());
				if (answer.getTaskStatus().equals("running")) {
					answer.setTaskProcess(taskInfo.getProgress());
				} else if (answer.getTaskStatus().equals("success")) {
					answer.setTaskProcess(100);
				} else {
					answer.setTaskProcess(0);
				}
				if (!taskInfo.getDescriptionId().contains("clone"))
					answer.setResourceId(taskInfo.getEntity().get_value());
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			logger.error("queryTask err...");
			// throw new Exception("queryTask err...");
		}
		return answer;
		
	}
}
