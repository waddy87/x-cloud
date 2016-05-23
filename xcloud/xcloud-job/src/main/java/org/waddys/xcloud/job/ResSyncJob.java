/**
 * 
 */
package org.waddys.xcloud.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.waddys.xcloud.task.exception.TaskinfoException;
import org.waddys.xcloud.task.service.service.TaskInfoService;

/**
 * @author zhangdapeng
 *
 */
@EnableScheduling
@Service("res.sync.job")
public class ResSyncJob {
	private static final Logger logger = LoggerFactory.getLogger(ResSyncJob.class);

	@Autowired
	private TaskInfoService taskService;
	
	@Scheduled(cron = "0 */1 *  * * * ")
	public void syncTask(){
		try {
			taskService.updateTaskInfo();
		} catch (TaskinfoException e) {
			logger.error("同步任务异常："+e.getMessage(),e);
		}
	}
	
}
