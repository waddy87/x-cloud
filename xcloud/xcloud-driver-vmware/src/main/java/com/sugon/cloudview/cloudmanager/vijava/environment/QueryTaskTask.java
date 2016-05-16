package com.sugon.cloudview.cloudmanager.vijava.environment;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sugon.cloudview.cloudmanager.vijava.base.BaseTask;
import com.sugon.cloudview.cloudmanager.vijava.exception.VirtException;
import com.sugon.cloudview.cloudmanager.vijava.impl.Session;
import com.sugon.cloudview.cloudmanager.vijava.vm.QueryTask.QueryTaskAnswer;
import com.sugon.cloudview.cloudmanager.vijava.vm.QueryTask.QueryTaskAnswer.QueriedTaskInfo;
import com.sugon.cloudview.cloudmanager.vijava.vm.QueryTask.QueryTaskCmd;
import com.sugon.vim25.Event;
import com.sugon.vim25.EventFilterSpec;
import com.sugon.vim25.InvalidProperty;
import com.sugon.vim25.InvalidState;
import com.sugon.vim25.ManagedObjectReference;
import com.sugon.vim25.RuntimeFault;
import com.sugon.vim25.TaskFilterSpec;
import com.sugon.vim25.TaskFilterSpecByEntity;
import com.sugon.vim25.TaskFilterSpecByTime;
import com.sugon.vim25.TaskFilterSpecByUsername;
import com.sugon.vim25.TaskFilterSpecRecursionOption;
import com.sugon.vim25.TaskFilterSpecTimeOption;
import com.sugon.vim25.TaskInfo;
import com.sugon.vim25.TaskInfoState;
import com.sugon.vim25.TaskReason;
import com.sugon.vim25.TaskReasonAlarm;
import com.sugon.vim25.TaskReasonSchedule;
import com.sugon.vim25.TaskReasonSystem;
import com.sugon.vim25.TaskReasonUser;
import com.sugon.vim25.VmDeployedEvent;
import com.sugon.vim25.VmEvent;
import com.sugon.vim25.mo.Folder;
import com.sugon.vim25.mo.ManagedEntity;
import com.sugon.vim25.mo.ServiceInstance;
import com.sugon.vim25.mo.Task;
import com.sugon.vim25.mo.TaskHistoryCollector;
import com.sugon.vim25.mo.TaskManager;
import com.sugon.vim25.mo.util.MorUtil;

public class QueryTaskTask extends BaseTask<QueryTaskAnswer> {

	private static Logger logger = LoggerFactory.getLogger(QueryTaskTask.class);

	private ServiceInstance si = null;
	private QueryTaskCmd queryTaskCmd = null;

	public QueryTaskTask(QueryTaskCmd queryTaskCmd) throws VirtException {

		try {

			this.si = Session.getInstanceByToken(queryTaskCmd.getToken());
			String user = si.getServerConnection().getUsername();
			// System.out.println(user);
			queryTaskCmd.setUser(user);
			this.queryTaskCmd = queryTaskCmd;
		} catch (Exception e) {
			logger.error("虚拟环境" + queryTaskCmd.getToken() + "连接异常 ："
					+ e.getMessage());
			throw new VirtException("虚拟环境" + queryTaskCmd.getToken() + "连接异常 ："
					+ e.getMessage());

		}
	}

	@Override
	public QueryTaskAnswer execute() {

		QueryTaskTask queryTaskTask = null;
		QueryTaskAnswer answer = new QueryTaskAnswer();
		List<QueriedTaskInfo> lsTaskInfo = null;
		try {
			queryTaskTask = new QueryTaskTask(queryTaskCmd);
		} catch (VirtException e) {
		}

		TaskManager taskMgr = queryTaskTask.si.getTaskManager();
		if (taskMgr != null) {
			Folder root = queryTaskTask.si.getRootFolder();
			TaskFilterSpec tfs;
			try {
				if (queryTaskCmd.getTaskIds() == null
						|| queryTaskCmd.getTaskIds().size() <= 0) {
					tfs = queryTaskTask
							.createTaskFilterSpec(root, queryTaskCmd);
					TaskInfo[] tis;
					TaskHistoryCollector thc;
					thc = taskMgr.createCollectorForTasks(tfs);
					// Note: 10 <= pagesize <= 62
					thc.setCollectorPageSize(10);
					tis = thc.getLatestPage();
					lsTaskInfo = convertTaskInfos(tis, queryTaskTask);
					while (true) {
						tis = thc.readNextTasks(50);
						if (tis == null) {
							break;
						}
						lsTaskInfo.addAll(convertTaskInfos(tis, queryTaskTask));
					}
					thc.destroyCollector();
				} else {// END if

					lsTaskInfo = converTaskInfos(queryTaskCmd.getTaskIds(),
							queryTaskTask);
				}
				answer.setSuccess(true);
				answer.setLsTaskInfo(lsTaskInfo);
				// logger.debug("The Answer:" + answer);

			} catch (InvalidState e) {
				answer.setSuccess(false);
				answer.setErrMsg("InvalideState");
			} catch (RuntimeFault e) {
				answer.setSuccess(false);
				answer.setErrMsg("RuntimeFault");
			} catch (RemoteException e) {
				answer.setSuccess(false);
				answer.setErrMsg("RemoteException");
			}
		}
		logger.info(answer.toString());
		return answer;
	}

	/**
	 * 转换查询结果到LinkedList<QueriedTaskInfo>
	 * 
	 * @param list
	 *            of taskid
	 * @param queryTaskTask
	 * @throws InvalidState
	 * @throws RemoteException
	 * @throws RuntimeFault
	 * @throws InvalidProperty
	 */
	private List<QueriedTaskInfo> converTaskInfos(Map<String, Date> taskIds,
			QueryTaskTask queryTaskTask) throws InvalidState, RuntimeFault,
			RemoteException {

		List<QueriedTaskInfo> lsTaskInfo = new LinkedList<QueriedTaskInfo>();
		for (String taskId : taskIds.keySet()) {
			QueriedTaskInfo queriedTaskInfo = new QueriedTaskInfo();
			ManagedObjectReference taskMor = new ManagedObjectReference();
			taskMor.set_value(taskId);
			taskMor.setType("Task");
			Task task = new Task(queryTaskTask.si.getServerConnection(),
					taskMor);
			TaskInfo ti = null;
			try {
				ti = task.getTaskInfo();
			} catch (Exception e) {

			}
			Date startTime;
			if (ti == null) {
				startTime = taskIds.get(taskId);
				QueryTaskCmd cmd = new QueryTaskCmd();
				cmd.setStartTime(startTime);
				cmd.setEndTime(startTime);
				TaskFilterSpec filter = createTaskFilterSpec(null, cmd);
				TaskManager taskMgr = queryTaskTask.si.getTaskManager();
				TaskHistoryCollector thc = taskMgr
						.createCollectorForTasks(filter);
				List<TaskInfo> tis = new ArrayList<TaskInfo>();
				while (true) {
					TaskInfo[] tia = thc.readNextTasks(50);
					if (tia == null) {
						break;
					}
					tis.addAll(Arrays.asList(tia));
				}
				thc.destroyCollector();
				for (TaskInfo taskInfo : tis) {
					if (taskInfo.getKey().equals(taskId)) {
						ti = taskInfo;
						break;
					}
				}
			}
			if(ti != null){
				convertTaskInfo(ti, queriedTaskInfo, queryTaskTask);
			}
			lsTaskInfo.add(queriedTaskInfo);
		}

		return lsTaskInfo;
	}

	/**
	 * 转换查询结果到LinkedList<QueriedTaskInfo>
	 * 
	 * @param ti
	 * @param queryTaskTask
	 */
	static List<QueriedTaskInfo> convertTaskInfos(TaskInfo[] tis,
			QueryTaskTask queryTaskTask) {

		List<QueriedTaskInfo> lsTaskInfo = new LinkedList<QueriedTaskInfo>();
		for (int i = 0; tis != null && i < tis.length; i++) {
			QueryTaskAnswer.QueriedTaskInfo queriedTaskInfo = new QueryTaskAnswer.QueriedTaskInfo();
			try {
				if(queryTaskTask.queryTaskCmd.getTaskId() != null && !queryTaskTask.queryTaskCmd.getTaskId().equals(tis[i].getKey()))
					continue;
				else{
					convertTaskInfo(tis[i], queriedTaskInfo, queryTaskTask);
				}
			} catch (RuntimeFault e) {

			} catch (RemoteException e) {

			}
			lsTaskInfo.add(queriedTaskInfo);
		}
		return lsTaskInfo;
	}

	/**
	 * 转换查询结果到QueryTaskAnswer.QueriedTaskInfo
	 * 
	 * @param ti
	 * @param queryTaskTask
	 * @param queryTaskTask
	 * @throws RemoteException
	 * @throws RuntimeFault
	 */
	static void convertTaskInfo(TaskInfo ti,
			QueryTaskAnswer.QueriedTaskInfo queriedTaskInfo,
			QueryTaskTask queryTaskTask) throws RuntimeFault, RemoteException {
//		queriedTaskInfo.setName(ti.getName());
		queriedTaskInfo.setName(ti.getDescriptionId());
		queriedTaskInfo.setTaskId(ti.getKey());
		queriedTaskInfo.setEntity(ti.getEntityName());
		if (ti.getEntity() != null){
			queriedTaskInfo.setEntityId(ti.getEntity().get_value());
		}
		queriedTaskInfo.setReason(taskReason(ti.getReason()));
		Date qTime = ti.getQueueTime().getTime();
		queriedTaskInfo.setQueueTime(qTime);
		queriedTaskInfo.setCreateTime(qTime);
		TaskInfoState status = ti.getState();
		queriedTaskInfo.setProcess(ti.getProgress());
		Calendar calStart = ti.getStartTime();
		queriedTaskInfo.setStartTime(calStart == null ? null : calStart
				.getTime());
		Calendar calStop = ti.getCompleteTime();
		queriedTaskInfo.setCompleteTime(calStop == null ? null : calStop
				.getTime());
		if (ti.getState().name().equals("running")) {
			try {
				// Thread.sleep(1000);
				Task task = new Task(queryTaskTask.si.getServerConnection(),
						ti.getTask());
				queriedTaskInfo.setProcess(task.getTaskInfo().getProgress());
				logger.info("progressing...." + queriedTaskInfo.getProcess());
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		} else if (ti.getState().name().equals("error")
				|| ti.getState().name().equals("queued")) {
			queriedTaskInfo.setProcess(0);
		} else if (ti.getState().name().equals("success")) {
			queriedTaskInfo.setProcess(100);
		} else
			queriedTaskInfo.setProcess(0);
		// queriedTaskInfo.setProcess(ti.getProgress());
		queriedTaskInfo.setStatus(ti.getState().name());

		EventFilterSpec efs = new EventFilterSpec();
		efs.setEventChainId(ti.getEventChainId());
		Event[] eventChain = queryTaskTask.si.getEventManager()
				.queryEvents(efs);
		for (int index = 0; eventChain != null && index < eventChain.length; index++) {
			VmEvent vmEvent = null;
			if (eventChain[index] instanceof VmEvent) {
				vmEvent = (VmEvent) eventChain[index];
			} else {
				continue;
			}
			if (vmEvent instanceof VmDeployedEvent) {
				queriedTaskInfo.setEntity(vmEvent.vm.name);
				queriedTaskInfo.setEntityId(vmEvent.vm.getVm() == null? null:vmEvent.vm.getVm().get_value());
				System.out.println(ti.getEntityName() + "克隆到"
						+ queriedTaskInfo.getEntity());
			}

		}
		try {
			queriedTaskInfo.setDetail(ti.getError().getLocalizedMessage());
			queriedTaskInfo.setDesc(ti.getDescription().message);
			
//			queriedTaskInfo.setDetail(ti.getError().getFault()
//					.getLocalizedMessage());
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}

	/**
	 * 转换TaskReason为String
	 * 
	 * @param tr
	 * @return
	 */
	static String taskReason(TaskReason tr) {
		if (tr instanceof TaskReasonAlarm) {
			return "Alarm";
		} else if (tr instanceof TaskReasonSchedule) {
			return "ScheduledTask";
		} else if (tr instanceof TaskReasonSystem) {
			return "System";
		} else if (tr instanceof TaskReasonUser) {
			return "User : " + ((TaskReasonUser) tr).getUserName();
		}
		return "Unknown";
	}

	/**
	 * 提供过滤条件
	 * 
	 * @param ent
	 * @param cmd
	 * @return
	 * @throws RemoteException
	 * @throws RuntimeFault
	 */
	TaskFilterSpec createTaskFilterSpec(ManagedEntity ent, QueryTaskCmd cmd)
			throws RuntimeFault, RemoteException {
		TaskFilterSpec tfs = new TaskFilterSpec();
		// tfs.setKey(new String[]{"task-2555"});
		// tasks user
		if (null != cmd.getUser() && cmd.getUser().length() > 0) {
			TaskFilterSpecByUsername nameFilter = new TaskFilterSpecByUsername();
			nameFilter.setUserList(new String[] { cmd.getUser() });
			// include tasks initiated by non-users,
			// for example, by ScheduledTaskManager.
			nameFilter.setSystemUser(true);
			tfs.setUserName(nameFilter);
		}

		// tasks state
		if (null != cmd.getTaskInfoState()) {
			tfs.setState(new TaskInfoState[] { cmd.getTaskInfoState() });
		}

		// tasks Id
		if (null != cmd.getVmId()) {
			ManagedObjectReference vmMor = new ManagedObjectReference();
			vmMor.set_value(cmd.getVmId());
			vmMor.setType("VirtualMachine");
			TaskFilterSpecByEntity entFilter = new TaskFilterSpecByEntity();
			entFilter.setEntity(vmMor);
			entFilter.setRecursion(TaskFilterSpecRecursionOption.all);
			tfs.setEntity(entFilter);
		}

		// only tasks started within last one month
		// strictly speaking, time should be retrieved from server
		if (null != cmd.getLastMonth()) {
			TaskFilterSpecByTime tFilter = new TaskFilterSpecByTime();
			Calendar cal = Calendar.getInstance();
			cal.roll(Calendar.MONTH, -cmd.getLastMonth());
			tFilter.setBeginTime(cal);
			// we ignore the end time here so it gets the latest.
			tFilter.setTimeType(TaskFilterSpecTimeOption.startedTime);
			tfs.setTime(tFilter);
		}
		if (cmd.getEndTime() != null && cmd.getEndTime().equals(cmd.getStartTime())) {
			Date startTime = (Date) cmd.getStartTime().clone();
			Date endTime = (Date) cmd.getEndTime().clone();
			endTime.setTime(endTime.getTime() + 1000);
			TaskFilterSpecByTime tFilter = new TaskFilterSpecByTime();
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(startTime);
			tFilter.setBeginTime(cal1);
			cal2 = Calendar.getInstance();
			cal2.setTime(endTime);
			tFilter.setEndTime(cal2);
			logger.info("filter param startTime {}", tFilter.getBeginTime()
					.getTime());
			logger.info("filter param endTime {}", tFilter.getEndTime()
					.getTime());
			// we ignore the end time here so it gets the latest.
			tFilter.setTimeType(TaskFilterSpecTimeOption.queuedTime);
			tfs.setTime(tFilter);
		}
		if (null != cmd.getStartTime() && null != cmd.getEndTime()
				&& cmd.getEndTime().after(cmd.getStartTime())) {
			Date startTime = cmd.getStartTime();
			Date endTime = cmd.getEndTime();
			TaskFilterSpecByTime tFilter = new TaskFilterSpecByTime();
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(startTime);
			tFilter.setBeginTime(cal1);
			cal2 = Calendar.getInstance();
			cal2.setTime(endTime);
			tFilter.setEndTime(cal2);
			logger.info("filter param startTime {}", tFilter.getBeginTime()
					.getTime());
			logger.info("filter param endTime {}", tFilter.getEndTime()
					.getTime());
			// we ignore the end time here so it gets the latest.
			tFilter.setTimeType(TaskFilterSpecTimeOption.queuedTime);
			tfs.setTime(tFilter);
		}

		// Optionally, you limits tasks initiated by scheduled task
		// with the setScheduledTask() method.
		return tfs;
	}

	public static void main(String args[]) throws RuntimeFault,
			RemoteException, Exception {

		QueryTaskCmd queryTaskCmd = new QueryTaskCmd();

		// queryTaskCmd.setTaskInfoState(TaskInfoState.error);
		long time = Session.getInstanceByToken(null).currentTime()
				.getTimeInMillis();
		Date date = new Date();
		queryTaskCmd.setUser("admin");
		HashMap<String, Date> map = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		date = sdf.parse("2016-04-22 10:59:47");
		map.put("task-3035", date);
		/*
		 task-3035=Fri Apr 22 10:59:47 CST 2016} 
		 	Date date = new Date();
		date.setTime(date.getTime() - 2 * 24 * 3600000);
		queryTaskCmd.setStartTime(date);
		Date date1 = new Date();
		date1.setTime(date1.getTime() + 24 * 3600000);
		queryTaskCmd.setEndTime(date1);
		queryTaskCmd.setUser("admin");
		HashMap<String, Date> map = new HashMap<>();
		long aaa = 1460687807433L;
		Date date7 = new Date();
		date7.setTime(aaa);
		map.put("task-2555", date7);
		Date date8 = new Date();
		date8.setTime(1460664934024L);
		map.put("task-2464", date8);
		Date date9 = new Date();
		date9.setTime(1460664885585L);
		map.put("task-2463", date9);
		queryTaskCmd.setTaskIds(map);*/
//		queryTaskCmd.setVmId("vm-874");
// 		queryTaskCmd.setTaskId("task-88");
/*		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date1 = df.parse("2016-04-21 05:08:15");
		Date date2 = df.parse("2016-04-21 05:09:15");*/
//		HashMap<String, Date> map = new HashMap<>();
//		map.put("task-89", date);
		queryTaskCmd.setTaskIds(map);
/*		queryTaskCmd.setStartTime(date1);
		queryTaskCmd.setEndTime(date2);*/
		QueryTaskTask queryTaskTask;
		try {
			queryTaskTask = new QueryTaskTask(queryTaskCmd);
			QueryTaskAnswer answer = queryTaskTask.execute();
			System.out.println(answer);
		} catch (VirtException e) {
			
		}

	}
}
