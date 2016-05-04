package com.sugon.cloudview.cloudmanager.vijava.base;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sugon.cloudview.cloudmanager.vijava.environment.ConnectCloudVMTaskFactory;
import com.sugon.cloudview.cloudmanager.vijava.environment.QueryNetPoolTaskFactory;
import com.sugon.cloudview.cloudmanager.vijava.environment.QueryResourceTaskFactory;
import com.sugon.cloudview.cloudmanager.vijava.environment.QueryTaskTaskFactory;
import com.sugon.cloudview.cloudmanager.vijava.environment.TestConnectInfoTaskFactory;
import com.sugon.cloudview.cloudmanager.vijava.exception.VirtException;
import com.sugon.cloudview.cloudmanager.vijava.network.QueryNetworkTaskFactory;
import com.sugon.cloudview.cloudmanager.vijava.vm.CreateVMTaskFactory;
import com.sugon.cloudview.cloudmanager.vijava.vm.DeleteVMTaskFactory;
import com.sugon.cloudview.cloudmanager.vijava.vm.QueryVMConsoleTaskFactory;
import com.sugon.cloudview.cloudmanager.vijava.vm.QueryVMEventTaskFactory;
import com.sugon.cloudview.cloudmanager.vijava.vm.QueryVMTaskFactory;
import com.sugon.cloudview.cloudmanager.vijava.vm.ReconfigVMTaskFactory;
import com.sugon.cloudview.cloudmanager.vijava.vm.VMPowerOpTaskFactory;

@Component("cloudviewExecutor")
public class CloudviewExecutorImpl implements CloudviewExecutor  {
	
    private static Logger logger = LoggerFactory.getLogger(CloudviewExecutorImpl.class);

    private static Map<String, BaseTaskFactory> map = new HashMap<String, BaseTaskFactory>();

    // 通过注入方式
    static {
    	map.put("com.sugon.cloudview.cloudmanager.vijava.vm.CreateVM$CreateVMCmd", new CreateVMTaskFactory());
    	map.put("com.sugon.cloudview.cloudmanager.vijava.vm.DeleteVM$DeleteVMCmd", new DeleteVMTaskFactory());
        map.put("com.sugon.cloudview.cloudmanager.vijava.vm.QueryVM$QueryVMCmd", new QueryVMTaskFactory());
        map.put("com.sugon.cloudview.cloudmanager.vijava.vm.VMPowerOperate$VMPowerOpCmd", new VMPowerOpTaskFactory());
        map.put("com.sugon.cloudview.cloudmanager.vijava.network.QueryNetwork$QueryNetworkCmd", new QueryNetworkTaskFactory());
        map.put("com.sugon.cloudview.cloudmanager.vijava.environment.QueryResources$QueryResourceCmd", new QueryResourceTaskFactory());
        map.put("com.sugon.cloudview.cloudmanager.vijava.environment.QueryNetPool$QueryNetPoolCmd", new QueryNetPoolTaskFactory());
        map.put("com.sugon.cloudview.cloudmanager.vijava.environment.ConnectCloudVM$ConnectCloudVMCmd", new ConnectCloudVMTaskFactory());
        map.put("com.sugon.cloudview.cloudmanager.vijava.environment.TestConnectInfo$TestConnectInfoCmd", new TestConnectInfoTaskFactory());
        map.put("com.sugon.cloudview.cloudmanager.vijava.vm.QueryVMEvent$QueryVMEventCmd", new QueryVMEventTaskFactory());
        map.put("com.sugon.cloudview.cloudmanager.vijava.vm.QueryTask$QueryTaskCmd", new QueryTaskTaskFactory());
        map.put("com.sugon.cloudview.cloudmanager.vijava.vm.QueryVMConsole$QueryVMConsoleCmd", new QueryVMConsoleTaskFactory());
        map.put("com.sugon.cloudview.cloudmanager.vijava.vm.ReconfigVM$ReconfigVMCmd", new ReconfigVMTaskFactory());
    }
    

    /**
     * 同步调用：有的不支持同步调用
     */
    public <T extends Answer> T execute(Cmd<T> cmd) throws VirtException {
        if (map.get(cmd.getClass().getName()) != null) {
        	logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        	logger.debug(cmd.toString());
            T answer = map.get(cmd.getClass().getName()).createTask(cmd).execute();
            logger.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            logger.debug(answer.toString());
            return answer;
        } else {
            throw new VirtException("未实现");
        }
    }

}
