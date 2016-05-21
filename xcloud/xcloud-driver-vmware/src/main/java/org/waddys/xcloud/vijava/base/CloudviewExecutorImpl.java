package org.waddys.xcloud.vijava.base;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.waddys.xcloud.vijava.environment.ConnectCloudVMTaskFactory;
import org.waddys.xcloud.vijava.environment.QueryNetPoolTaskFactory;
import org.waddys.xcloud.vijava.environment.QueryResourceTaskFactory;
import org.waddys.xcloud.vijava.environment.QueryTaskTaskFactory;
import org.waddys.xcloud.vijava.environment.TestConnectInfoTaskFactory;
import org.waddys.xcloud.vijava.exception.VirtException;
import org.waddys.xcloud.vijava.network.QueryNetworkTaskFactory;
import org.waddys.xcloud.vijava.vm.CreateVMTaskFactory;
import org.waddys.xcloud.vijava.vm.DeleteVMTaskFactory;
import org.waddys.xcloud.vijava.vm.QueryVMConsoleTaskFactory;
import org.waddys.xcloud.vijava.vm.QueryVMEventTaskFactory;
import org.waddys.xcloud.vijava.vm.QueryVMTaskFactory;
import org.waddys.xcloud.vijava.vm.ReconfigVMTaskFactory;
import org.waddys.xcloud.vijava.vm.VMPowerOpTaskFactory;

@Component("cloudviewExecutor")
public class CloudviewExecutorImpl implements CloudviewExecutor  {
	
    private static Logger logger = LoggerFactory.getLogger(CloudviewExecutorImpl.class);

    private static Map<String, BaseTaskFactory> map = new HashMap<String, BaseTaskFactory>();

    // 通过注入方式
    static {
    	map.put("org.waddys.xcloud.vijava.vm.CreateVM$CreateVMCmd", new CreateVMTaskFactory());
    	map.put("org.waddys.xcloud.vijava.vm.DeleteVM$DeleteVMCmd", new DeleteVMTaskFactory());
        map.put("org.waddys.xcloud.vijava.vm.QueryVM$QueryVMCmd", new QueryVMTaskFactory());
        map.put("org.waddys.xcloud.vijava.vm.VMPowerOperate$VMPowerOpCmd", new VMPowerOpTaskFactory());
        map.put("org.waddys.xcloud.vijava.network.QueryNetwork$QueryNetworkCmd", new QueryNetworkTaskFactory());
        map.put("org.waddys.xcloud.vijava.environment.QueryResources$QueryResourceCmd", new QueryResourceTaskFactory());
        map.put("org.waddys.xcloud.vijava.environment.QueryNetPool$QueryNetPoolCmd", new QueryNetPoolTaskFactory());
        map.put("org.waddys.xcloud.vijava.environment.ConnectCloudVM$ConnectCloudVMCmd", new ConnectCloudVMTaskFactory());
        map.put("org.waddys.xcloud.vijava.environment.TestConnectInfo$TestConnectInfoCmd", new TestConnectInfoTaskFactory());
        map.put("org.waddys.xcloud.vijava.vm.QueryVMEvent$QueryVMEventCmd", new QueryVMEventTaskFactory());
        map.put("org.waddys.xcloud.vijava.vm.QueryTask$QueryTaskCmd", new QueryTaskTaskFactory());
        map.put("org.waddys.xcloud.vijava.vm.QueryVMConsole$QueryVMConsoleCmd", new QueryVMConsoleTaskFactory());
        map.put("org.waddys.xcloud.vijava.vm.ReconfigVM$ReconfigVMCmd", new ReconfigVMTaskFactory());
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
