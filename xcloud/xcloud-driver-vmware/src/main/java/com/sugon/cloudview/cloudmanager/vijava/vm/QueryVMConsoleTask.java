package com.sugon.cloudview.cloudmanager.vijava.vm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sugon.cloudview.cloudmanager.vijava.base.BaseTask;
import com.sugon.cloudview.cloudmanager.vijava.exception.VirtException;
import com.sugon.cloudview.cloudmanager.vijava.impl.Session;
import com.sugon.cloudview.cloudmanager.vijava.impl.VirtualMachineImpl;
import com.sugon.cloudview.cloudmanager.vijava.util.ParamValidator;
import com.sugon.cloudview.cloudmanager.vijava.vm.QueryVMConsole.QueryVMConsoleAnswer;
import com.sugon.cloudview.cloudmanager.vijava.vm.QueryVMConsole.QueryVMConsoleCmd;
import com.sugon.vim25.mo.ServiceInstance;
import com.sugon.vim25.mo.VirtualMachine;

public class QueryVMConsoleTask extends BaseTask<QueryVMConsoleAnswer> {

    private static Logger logger = LoggerFactory.getLogger(QueryVMConsole.class);

    /**
     * 查询虚拟机的参数
     */
    private QueryVMConsoleCmd queryVMConsoleCmd;
    private ServiceInstance si = null;

    public QueryVMConsoleTask(QueryVMConsoleCmd queryVMConsoleCmd) throws VirtException {
    	
        this.queryVMConsoleCmd = queryVMConsoleCmd;
    	
		try {
			this.setSi(Session.getInstanceByToken(queryVMConsoleCmd.getToken()));
		} catch (Exception e) {
			logger.error("连接失败，原因" + e.getMessage());
			throw new VirtException("虚拟环境连接异常 ：" + e);
		}
		
    }
    

    @Override
    public QueryVMConsoleAnswer execute() throws VirtException{
    	
        logger.debug("查询参数=" + this.queryVMConsoleCmd);
        QueryVMConsoleAnswer answer = new QueryVMConsoleAnswer();
        try {	
        	String vmId = queryVMConsoleCmd.getVmId();
        	if (ParamValidator.validatorParamsIsEmpty(vmId)){
        		throw new VirtException("虚拟机ID为空");
        	}
        	
        	VirtualMachine vm = VirtualMachineImpl.getVirtualMachineById(si, vmId);
        	String vCenterIP = si.getServerConnection().getUrl().getHost();
        	
        	
//	        String vmURLBySessionTicket = String.format("https://%s:9443/vsphere-client/webconsole.html?"
//	        		+ "vmId=%s&vmName=%s&serverGuid=%s&locale=zh_CN&"
//	        		+ "host=%s:443&sessionTicket=%s&thumbprint", vCenterIP, vmId, vm.getName(),
//	        		si.getAboutInfo().getInstanceUuid(),  vCenterIP, 
//	        		si.getSessionManager().acquireCloneTicket());
	        
//	        VirtualMachineMksTicket ticket = vm.acquireMksTicket();
//	        String vmURLByVmTicket = String.format("wss://%s:9443/vsphere-client/webconsole/authd?"
//	        		+ "mksTicket=%s&host=%s&port=%d&"
//	        		+ "cfgFile=%s&sslThumbprint=%s", 
//	        		vCenterIP, ticket.getTicket(), ticket.getHost(), ticket.getPort(),
//	        		ticket.getCfgFile().replace("/", "%2F"), 
//	        		ticket.getSslThumbprint());
        	
	        String vmWebConsoleURL = String.format("wss://%s:9443/vsphere-client/webconsole/authd?"
	        		+ "vmId=%s&vmName=%s&serverGuid=%s&"
	        		+ "host=%s:443&sessionTicket=%s&sslThumbprint=",
	        		vCenterIP, vmId, vm.getName(),
	        		si.getAboutInfo().getInstanceUuid(),  vCenterIP, 
	        		si.getSessionManager().acquireCloneTicket());
	        
            return new QueryVMConsoleAnswer().setSuccess(true)
            		.setConsoleURL(vmWebConsoleURL);
        } catch (Exception e) {
            logger.error(e.getMessage());
            answer.setSuccess(false).setConsoleURL(null);
            answer.setErrMsg("查询失败");
            throw new VirtException(e.getMessage());
        }
    }

    
	public ServiceInstance getSi() {
		return si;
	}

	public void setSi(ServiceInstance si) {
		this.si = si;
	}


    
    public static void main(String args[]) throws Exception{
    	
    	try {		
    		QueryVMConsoleCmd queryVMConsoleCmd = new QueryVMConsoleCmd();
    		queryVMConsoleCmd.setVmId("vm-285");
			QueryVMConsoleTask task = new QueryVMConsoleTask(queryVMConsoleCmd);
			QueryVMConsoleAnswer answer = task.execute();
			System.out.println(answer);
			System.out.println("=================");
			System.out.println(answer.getConsoleURL());
			System.out.println();
		} catch (VirtException e) {
			e.printStackTrace();
		}
    	
    }
}

