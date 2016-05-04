package com.sugon.cloudview.cloudmanager.vijava.vm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sugon.cloudview.cloudmanager.vijava.base.BaseTask;
import com.sugon.cloudview.cloudmanager.vijava.exception.VirtException;
import com.sugon.cloudview.cloudmanager.vijava.impl.Session;
import com.sugon.cloudview.cloudmanager.vijava.vm.QueryVMEvent.QueryVMEventAnswer;
import com.sugon.cloudview.cloudmanager.vijava.vm.QueryVMEvent.QueryVMEventCmd;
import com.sugon.vim25.Event;
import com.sugon.vim25.EventFilterSpec;
import com.sugon.vim25.TaskEvent;
import com.sugon.vim25.TaskInfo;
import com.sugon.vim25.VmDeployedEvent;
import com.sugon.vim25.VmEvent;
import com.sugon.vim25.VmPoweredOffEvent;
import com.sugon.vim25.VmPoweredOnEvent;
import com.sugon.vim25.VmRemovedEvent;
import com.sugon.vim25.mo.EventHistoryCollector;
import com.sugon.vim25.mo.EventManager;
import com.sugon.vim25.mo.ServiceInstance;
import com.sugon.vim25.mo.Task;
import com.sugon.vim25.mo.util.MorUtil;

public class QueryVMEventTask extends BaseTask<QueryVMEventAnswer> {

    private static Logger logger = LoggerFactory.getLogger(QueryVMEventTask.class);

	private QueryVMEventCmd queryVMEventCmd = null;
	private ServiceInstance si = null;
	
    public QueryVMEventTask(QueryVMEventCmd queryVMEventCmd) throws VirtException {
    	this.queryVMEventCmd = queryVMEventCmd;
    	
		//TODO 
		//1. 从环境连接池中获得所有的连接会话
		//2. 每个连接会话都查询未发送过的事件
    }

    @Override
    public QueryVMEventAnswer execute() {
    	
		//TODO 
		//1. 从环境连接池中获得所有的连接会话
		//2. 每个连接会话都查询未发送过的事件
    	QueryVMEventAnswer answer = new QueryVMEventAnswer();
			
		return answer;
    }
    
   
}
