package com.sugon.cloudview.cloudmanager.vijava.vim25.mo.demo.event;

import java.net.URL;

import com.sugon.vim25.Event;
import com.sugon.vim25.EventFilterSpec;
import com.sugon.vim25.TaskEvent;
import com.sugon.vim25.TaskInfo;
import com.sugon.vim25.VmDeployedEvent;
import com.sugon.vim25.VmPoweredOffEvent;
import com.sugon.vim25.VmPoweredOnEvent;
import com.sugon.vim25.VmRemovedEvent;
import com.sugon.vim25.mo.EventHistoryCollector;
import com.sugon.vim25.mo.EventManager;
import com.sugon.vim25.mo.ServiceInstance;
import com.sugon.vim25.mo.Task;
import com.sugon.vim25.mo.util.MorUtil;

//https://10.0.31.251/sdk admin Sugon!123
public class QueryHistoricalEvents {
	
	public static void main(String[] args) throws Exception {
		
		if (args.length != 3) {
			System.out.println("Usage: java QueryHistoricalEvents " + "<url> <username> <password>");
			return;
		}

		ServiceInstance si = new ServiceInstance(new URL(args[0]), args[1], args[2], true);
		EventManager evtMgr = si.getEventManager();

		if (evtMgr != null) {
			EventFilterSpec eventFilter = new EventFilterSpec();
			eventFilter.setType(new String[] {"TaskEvent"});
			EventHistoryCollector ehc = evtMgr.createCollectorForEvents(eventFilter);

			int total = 0;
			Event[] latestEvts = ehc.getLatestPage();
			;//Drop PreviousEvents
			total += latestEvts == null ? 0 : latestEvts.length;

			System.out.println("\nAfter Latest Page:");
			ehc.resetCollector();
			while (true) {
				Event[] events = ehc.readNextEvents(10);
				if (events == null) {
					Thread.sleep(3000);
					continue;
				}
				encapsulateEvents(events, total, si);
				total += events.length;
			}
		}
		si.getServerConnection().logout();
	}

	static void encapsulateEvents(Event[] events, int total, ServiceInstance si) {
		
		for (int i = 0; i < events.length; i++) {
			System.out.println("---------------------------------------");
			System.out.println("key = " + events[i].getKey());
			System.out.println("Event[" + (total + i) + "]=" + events[i].getClass().getName());
			System.out.println("Event: " + events[i].getCreatedTime().getTime());
			if(events[i] instanceof TaskEvent){
				TaskEvent taskEvent = (TaskEvent)events[i];
				TaskInfo taskInfo = taskEvent.info;
				System.out.println(taskInfo.getKey());
				System.out.println(taskInfo.getEntityName());
				System.out.println(taskInfo.getState());
				
				try {
					Task task = (Task)MorUtil.createExactManagedObject(si.getServerConnection(), taskInfo.task);
					task.waitForTask();
					taskInfo = task.getTaskInfo();
					System.out.println("taskName: " + taskInfo.getName());
					System.out.println("eventChainId: " + taskInfo.getEventChainId());
					System.out.println("taskState: " + taskInfo.getState());
					System.out.println("taskMID: " + task.getAssociatedManagedEntity().getMOR().getVal());
					
				    EventManager evtMgr = si.getEventManager();
				    EventFilterSpec efs = new EventFilterSpec();
				    efs.setEventChainId(taskInfo.getEventChainId());
				    Event[] eventChain = evtMgr.queryEvents(efs);
					for (int index = 0; eventChain != null && index < eventChain.length; index++) {
						System.out.println("\nEvent #" + index);
						System.out.println(eventChain[index].getClass().getName());
						if(eventChain[index] instanceof VmDeployedEvent){
							VmDeployedEvent vmDeployedEvent = (VmDeployedEvent)eventChain[index];
							System.out.println(vmDeployedEvent.vm.getVm().getVal());
						}else if(eventChain[index] instanceof VmPoweredOnEvent){
							VmPoweredOnEvent vmPoweredOnEvent = (VmPoweredOnEvent)eventChain[index];
							System.out.println(vmPoweredOnEvent.vm.getVm().getVal());
						}else if(eventChain[index] instanceof VmPoweredOffEvent){
							VmPoweredOffEvent vmPoweredOffEvent = (VmPoweredOffEvent)eventChain[index];
							System.out.println(vmPoweredOffEvent.vm.getVm().getVal());
						}else if(eventChain[index] instanceof VmRemovedEvent){
							VmRemovedEvent vmRemovedEvent = (VmRemovedEvent)eventChain[index];
							System.out.println(vmRemovedEvent.vm.getVm().getVal());
						}
						
					}
				} catch (Exception e) {
				}
			}
		}
	}
}
