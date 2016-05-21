package com.sugon.cloudview.cloudmanager.event.vmEvent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.collections.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sugon.cloudview.cloudmanager.event.api.EventController;
import com.sugon.cloudview.cloudmanager.event.type.VmOperatorFailed;
import com.sugon.cloudview.cloudmanager.event.type.VmOperatorResult;
import com.sugon.cloudview.cloudmanager.event.type.VmOperatorSuccess;
import com.sugon.cloudview.cloudmanager.event.type.VmOptorType4Event;
import com.sugon.cloudview.cloudmanager.event.util.StringUtils;
import com.sugon.cloudview.cloudmanager.vijava.impl.Session;
import com.sugon.vim25.Event;
import com.sugon.vim25.EventFilterSpec;
import com.sugon.vim25.MigrationResourceErrorEvent;
import com.sugon.vim25.TaskEvent;
import com.sugon.vim25.TaskInfo;
import com.sugon.vim25.TaskInfoState;
import com.sugon.vim25.VmBeingCreatedEvent;
import com.sugon.vim25.VmCloneFailedEvent;
import com.sugon.vim25.VmClonedEvent;
import com.sugon.vim25.VmCreatedEvent;
import com.sugon.vim25.VmDeployFailedEvent;
import com.sugon.vim25.VmDeployedEvent;
import com.sugon.vim25.VmEvent;
import com.sugon.vim25.VmFailedToPowerOffEvent;
import com.sugon.vim25.VmFailedToPowerOnEvent;
import com.sugon.vim25.VmFaultToleranceTurnedOffEvent;
import com.sugon.vim25.VmPoweredOffEvent;
import com.sugon.vim25.VmPoweredOnEvent;
import com.sugon.vim25.VmRemovedEvent;
import com.sugon.vim25.mo.EventHistoryCollector;
import com.sugon.vim25.mo.EventManager;
import com.sugon.vim25.mo.ServerConnection;
import com.sugon.vim25.mo.ServiceInstance;
import com.sugon.vim25.mo.Task;
import com.sugon.vim25.mo.util.MorUtil;

@Service
public class VMEvent {

    private static final Logger logger = LoggerFactory.getLogger(VMEvent.class);

    @Resource(name = "DirectEventController")
    private EventController directController;
    
    @Resource(name = "TopicEventController")
    private EventController topicController;

    class EventMainThread extends Thread{

        Map<String, ServerConnection> connMap;
        Map<String, MutliThread> threadMap = new HashMap<String, MutliThread>();

        public void run(){

            while (true) {
                try {
                	//every 1 minute compare connections whith child threads.
                    
                	EventMainThread.sleep(60000);
                	this.comp();
                    

                } catch (Exception e) {
//                	logger.error(e.getMessage());
                }
            }
        }

        //比较两个map并保持一致
        public synchronized void comp() throws Exception{

            try {
            	/*if(null != connMap && connMap.size() > 0){
            		for(String mapKey: connMap.keySet()){
            			Session.returnConnectionToPool(mapKey, connMap.get(mapKey));
            		}
            	}*/
            	
                connMap = Session.getConnections();
                if(null == connMap || connMap.size() <= 0){
                	return;
                }
                
//                logger.info("got connections...");
                for (String token : connMap.keySet()) {
                    if (threadMap.get(token) == null 
                    		|| "TERMINATED".equals(threadMap.get(token).getState().name())) {
                        MutliThread mthread = new MutliThread(connMap.get(token));
                        mthread.start();
                        logger.info("adding mthred {}......", token);
                        threadMap.put(token, mthread);
                    }
                }
                
                for (String token : threadMap.keySet()) {
                    if (connMap.get(token) == null) {
                        threadMap.get(token).destroy();
                        logger.info("removing mthred {}......",token);
                        threadMap.remove(token);
                    }
                }
                
            } catch (Exception e) {
                throw new Exception("connection err or thread err");
            }
        }// END comp
    }
    
    
    //轮询总线程，防止启动阻塞
    class MutliThread extends Thread{
    	
        private ServerConnection conn;
        private boolean destroy = false;

        MutliThread(ServerConnection conn){
            this.conn = conn;
        }

        public void destroy(){
            destroy = true;
        }

        public void run(){
        	
            try {
                ServiceInstance si = null;
                // TODO 配置文件与连接不一致的情况
                do {
                    Thread.sleep(3000);// 等待三秒 尽量减少无效循环
                    si = conn.getServiceInstance();//是否可以用 connMap
                } while (si == null);
                logger.info("VMEvent got vsphere connection {} ", si.toString());

                EventManager evtMgr = si.getEventManager();

                if (evtMgr != null) {
                    EventFilterSpec eventFilter = new EventFilterSpec();
                    eventFilter.setType(new String[] { "TaskEvent" });
                    EventHistoryCollector ehc = evtMgr.createCollectorForEvents(eventFilter);

                    ehc.getLatestPage();// Drop PreviousEvents
                    logger.debug("\nAfter Latest Page:");
                    ehc.resetCollector();// relocation the index
                    while (!destroy && si != null) {
//                    	System.out.println("lastactive:"+si.getSessionManager().getCurrentSession().getLastActiveTime().getTimeInMillis());
//                    	System.out.println("logintime:"+si.getSessionManager().getCurrentSession().getLoginTime().getTimeInMillis());
                        Event[] events = ehc.readNextEvents(10);
                        if (events == null) {
                            Thread.sleep(5000);
                            continue;
                        }
                        encapsulateEventsAndSend(events, 0, si);
                        Thread.sleep(3000);
                    }
                }

            } catch (Exception e) {
                logger.error("get default Instance error");
            }
        }
    }


    @PostConstruct
    public void sendVMEvent() {
    	logger.info("=== 启动事件服务器 ===");
        new EventMainThread().start();
    }


    public void encapsulateEventsAndSend(Event[] events, int total, final ServiceInstance si) {

        Iterator<?> iterator = IteratorUtils.arrayIterator(events);
        while (iterator.hasNext()) {

            final Event event = (Event) iterator.next();
            if (!(event instanceof TaskEvent)) {
                continue;
            }

            Runnable task = new Runnable() {

                public void run() {
                    try {

                        VmOperatorResult vmOperatorResult = new VmOperatorResult();
                        vmOperatorResult.setCreatedTimeofVM(event.getCreatedTime().getTime());
                        TaskEvent taskEvent = (TaskEvent) event;
                        TaskInfo taskInfo = taskEvent.info;
                        vmOperatorResult.setTaskId(taskInfo.getKey());
                        vmOperatorResult.setEntityName(taskInfo.getEntityName());
                        Task task = (Task) MorUtil.createExactManagedObject(si.getServerConnection(), taskInfo.task);
                        
                        if(TaskInfoState.running == taskInfo.getState() 
                        		|| TaskInfoState.queued == taskInfo.getState()){
                            task.waitForTask();
                            taskInfo = task.getTaskInfo();  	
                        }
                        vmOperatorResult.setResult(taskInfo.getState().toString());

                        EventManager evtMgr = si.getEventManager();
                        EventFilterSpec efs = new EventFilterSpec();
                        efs.setEventChainId(taskInfo.getEventChainId());
                        Event[] eventChain = evtMgr.queryEvents(efs);
                        for (int index = 0; eventChain != null && index < eventChain.length; index++) {
                            VmEvent vmEvent = null;
                            if(eventChain[index] instanceof VmEvent){
                                vmEvent = (VmEvent) eventChain[index];
                            }else{
                                continue;
                            }
                            if (vmEvent instanceof VmBeingCreatedEvent
                            		|| vmEvent instanceof VmCreatedEvent) {
                                vmOperatorResult.setVmId(vmEvent.vm.getVm().getVal());
                                vmOperatorResult.setOperatorType(VmOptorType4Event.CreateVM.value);
                            }else if (vmEvent instanceof VmDeployedEvent
                            		|| vmEvent instanceof VmDeployFailedEvent
                            		|| vmEvent instanceof VmClonedEvent
                            		|| vmEvent instanceof VmCloneFailedEvent) {
                                vmOperatorResult.setVmId(vmEvent.vm.getVm().getVal());
                                vmOperatorResult.setOperatorType(VmOptorType4Event.CloneVM.value);
                            }else if(vmEvent instanceof VmRemovedEvent){
                                vmOperatorResult.setVmId(vmEvent.vm.getVm().getVal());
                                vmOperatorResult.setOperatorType(VmOptorType4Event.Destroy.value);
                            }else if(vmEvent instanceof VmPoweredOnEvent
                            		|| vmEvent instanceof VmFailedToPowerOnEvent){
                                vmOperatorResult.setVmId(vmEvent.vm.getVm().getVal());
                                vmOperatorResult.setOperatorType(VmOptorType4Event.PowerOnVM.value);
                            }else if(vmEvent instanceof VmPoweredOffEvent
                            		|| vmEvent instanceof VmFailedToPowerOffEvent
                            		|| vmEvent instanceof VmFaultToleranceTurnedOffEvent){
                                vmOperatorResult.setVmId(vmEvent.vm.getVm().getVal());
                                vmOperatorResult.setOperatorType(VmOptorType4Event.PowerOffVM.value);
                            }else if(vmEvent instanceof MigrationResourceErrorEvent){
                                vmOperatorResult.setVmId(vmEvent.vm.getVm().getVal());
                                vmOperatorResult.setOperatorType(VmOptorType4Event.MigrationVM.value);
                            }
                            logger.debug("processing event " + vmEvent.getClass().getName());
                        }
                        logger.debug("task result " + taskInfo.getState());
                        if(null == vmOperatorResult.getOperatorType()){
                            return;//业务不需要的事件不发送
                        }
                        String routeKey = StringUtils.getRoutingKey("vm", null, "info", "admin");
                        if (TaskInfoState.success.equals(taskInfo.getState())) {
                            VmOperatorSuccess vmOperatorSuccess = new VmOperatorSuccess( vmOperatorResult);
                            logger.debug("send Msg:------------------------------\n{}", vmOperatorSuccess);
                            topicController.send(routeKey,vmOperatorSuccess);
                        } else {
                            try{
                                String errorInfo = taskInfo.getError().getLocalizedMessage();
                                vmOperatorResult.setDesc(errorInfo);
                            }catch(Exception e){
                                ;//Do Nothing; just skip it
                            }
                            VmOperatorFailed vmOperatorFailed = new VmOperatorFailed(vmOperatorResult);
                            logger.debug("send Msg:------------------------------\n{}", vmOperatorFailed);
                            topicController.send(routeKey, vmOperatorFailed);
                        }

                    } catch (Exception e) {
                    }
                }
            };

            new Thread(task).start();
        }
    }

    public static void main(String a[]){
        new VMEvent().sendVMEvent();
    }
}
