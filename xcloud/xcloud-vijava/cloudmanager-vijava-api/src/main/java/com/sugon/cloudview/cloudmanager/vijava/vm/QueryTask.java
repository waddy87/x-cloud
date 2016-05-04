package com.sugon.cloudview.cloudmanager.vijava.vm;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.sugon.cloudview.cloudmanager.vijava.base.Answer;
import com.sugon.cloudview.cloudmanager.vijava.base.Cmd;
import com.sugon.vim25.TaskInfoState;

public class QueryTask {

    public static class QueryTaskCmd extends Cmd<QueryTaskAnswer> {
    	
        private static final long serialVersionUID = 653625974945794517L;
        
        private String vmId;
        private String user;
        private Integer lastMonth;
        private Date startTime;
        private Date endTime;
        private String taskId;
        private HashMap<String, Date> taskIds;//used for definite filter,if not null other param is ignored
        private List<String> taskIdList;
        private TaskInfoState taskInfoState;

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public Integer getLastMonth() {
			return lastMonth;
		}

		public void setLastMonth(Integer lastMonth) {
			this.lastMonth = lastMonth;
		}

		public TaskInfoState getTaskInfoState() {
			return taskInfoState;
		}

		public void setTaskInfoState(TaskInfoState taskInfoState) {
			this.taskInfoState = taskInfoState;
		}

		public String getToken() {
			return token;
		}

		public QueryTaskCmd setToken(String token) {
			this.token = token;
			return this;
		}
		
		public Date getStartTime() {
			return startTime;
		}

		public void setStartTime(Date startTime) {
			this.startTime = startTime;
		}

		public Date getEndTime() {
			return endTime;
		}

		public void setEndTime(Date endTime) {
			this.endTime = endTime;
		}

		
		public HashMap<String,Date> getTaskIds() {
			return taskIds;
		}

		
		public String getTaskId() {
			return taskId;
		}
		
		

		public List<String> getTaskIdList() {
			return taskIdList;
		}

		public void setTaskIdList(List<String> taskIdList) {
			this.taskIdList = taskIdList;
		}

		/**
		 * taskid is used for definite filter,if not null vmId is used
		 * @param taskIds
		 */
		public void setTaskId(String taskId) {
			this.taskId = taskId;
		}

		/**
		 * taskids is used for definite filter,if not null other param is ignored
		 * @param taskIds
		 */
		public void setTaskIds(HashMap<String, Date> taskIds) {
			this.taskIds = taskIds;
		}

		/**
         * 虚拟机Id，查询虚拟机相关的任务
         */
		public String getVmId() {
			return vmId;
		}

		public void setVmId(String vmId) {
			this.vmId = vmId;
		}

		@Override
		public String toString() {
			return "QueryTaskCmd [vmId=" + vmId + ", user=" + user
					+ ", lastMonth=" + lastMonth + ", startTime=" + startTime
					+ ", endTime=" + endTime + ", taskId=" + taskId
					+ ", taskIds=" + taskIds + ", taskIdList=" + taskIdList
					+ ", taskInfoState=" + taskInfoState + "]";
		}

		
    }
    

    public static class QueryTaskAnswer extends Answer {

        private static final long serialVersionUID = -513468422130231432L;
        
        private boolean success;
        private List<QueriedTaskInfo>lsTaskInfo = null;

		public boolean isSuccess() {
            return success;
        }

		public List<QueriedTaskInfo> getLsTaskInfo() {
			return lsTaskInfo;
		}

		public void setLsTaskInfo(List<QueriedTaskInfo> lsTaskInfo) {
			this.lsTaskInfo = lsTaskInfo;
		}

		public QueryTaskAnswer setSuccess(boolean success) {
            this.success = success;
            return this;
        }
        
        public static class QueriedTaskInfo{

        	private String name;
    		private String taskId;
    		private String entity;
    		private String reason;
    		private Date createTime;
    		private Date queueTime;
    		private Date startTime;
    		private Date completeTime;
    		private Integer process;
    		private String desc;
    		private String status;
    		private String detail;
    		private String entityId;
    		/**
    		 * 任务名称：可能为空
    		 * @return
    		 */
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			
			/**
			 * 任务Id，不为空，唯一
			 * @return
			 */
			public String getTaskId() {
				return taskId;
			}
			public void setTaskId(String taskId) {
				this.taskId = taskId;
			}
			
			/**
			 * 操作任务的实体名称
			 * @return
			 */
			public String getEntity() {
				return entity;
			}
			public void setEntity(String entity) {
				this.entity = entity;
			}
			
			/**
			 * 操作任务的实体id
			 * @return
			 */
			public String getEntityId() {
				return entityId;
			}
			public void setEntityId(String entity) {
				this.entityId = entity;
			}
			
			/**
			 * 任务类型：Alarm，ScheduledTask，System，User，Unknown
			 * @return
			 */
			public String getReason() {
				return reason;
			}
			public void setReason(String reason) {
				this.reason = reason;
			}
			
			/**
			 * 调度延迟
			 * @return
			 */
			public Date getQueueTime() {
				return queueTime;
			}
			public void setQueueTime(Date queueTime) {
				this.queueTime = queueTime;
			}
			
			public Date getStartTime() {
				return startTime;
			}
			public void setStartTime(Date startTime) {
				this.startTime = startTime;
			}
			
			
			public Date getCompleteTime() {
				return completeTime;
			}
			public void setCompleteTime(Date completeTime) {
				this.completeTime = completeTime;
			}
			
			/**
			 * 任务进度
			 * @return
			 */
			public Integer getProcess() {
				return process;
			}
			public void setProcess(Integer process) {
				this.process = process;
			}
			
			/**
			 * 任务描述
			 * @return
			 */
			public String getDesc() {
				return desc;
			}
			public void setDesc(String desc) {
				this.desc = desc;
			}
			
			/**
			 * 失败原因
			 * @return
			 */
			public String getStatus() {
				return status;
			}
			public void setStatus(String status) {
				this.status = status;
			}
			
			/**
			 * 详情：
			 * @return
			 */
			public String getDetail() {
				return detail;
			}
			
			public void setDetail(String detail) {
				this.detail = detail;
			}
			
			public Date getCreateTime() {
				return createTime;
			}
			
			public void setCreateTime(Date createTime) {
				this.createTime = createTime;
			}
			
			@Override
			public String toString() {
				return "QueriedTaskInfo [name=" + name + ", taskId=" + taskId + ", entity=" + entity +",entityId="+ entityId + ", reason=" + reason
						+ ", createTime=" + createTime + ", queueTime=" + queueTime + ", startTime=" + startTime
						+ ", completeTime=" + completeTime + ", process=" + process + ", desc=" + desc + ", status="
						+ status + ", detail=" + detail + "]\n";
			}
    		
        }

		@Override
		public String toString() {
			return "QueryTaskAnswer [success=" + success + ", lsTaskInfo=" + lsTaskInfo + "]";
		}
    }
    
}
