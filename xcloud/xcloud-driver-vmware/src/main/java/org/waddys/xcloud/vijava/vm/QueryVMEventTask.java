package org.waddys.xcloud.vijava.vm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.waddys.xcloud.vijava.base.BaseTask;
import org.waddys.xcloud.vijava.exception.VirtException;
import org.waddys.xcloud.vijava.impl.Session;
import org.waddys.xcloud.vijava.vm.QueryVMEvent.QueryVMEventAnswer;
import org.waddys.xcloud.vijava.vm.QueryVMEvent.QueryVMEventCmd;

import com.vmware.vim25.Event;
import com.vmware.vim25.EventFilterSpec;
import com.vmware.vim25.TaskEvent;
import com.vmware.vim25.TaskInfo;
import com.vmware.vim25.VmDeployedEvent;
import com.vmware.vim25.VmEvent;
import com.vmware.vim25.VmPoweredOffEvent;
import com.vmware.vim25.VmPoweredOnEvent;
import com.vmware.vim25.VmRemovedEvent;
import com.vmware.vim25.mo.EventHistoryCollector;
import com.vmware.vim25.mo.EventManager;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.util.MorUtil;

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
