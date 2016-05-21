package org.waddys.xcloud.vijava.environment;

import org.waddys.xcloud.vijava.base.Answer;
import org.waddys.xcloud.vijava.base.BaseTask;
import org.waddys.xcloud.vijava.base.BaseTaskFactory;
import org.waddys.xcloud.vijava.base.Cmd;
import org.waddys.xcloud.vijava.environment.ConnectCloudVM.ConnectCloudVMCmd;
import org.waddys.xcloud.vijava.exception.VirtException;

public class ConnectCloudVMTaskFactory implements BaseTaskFactory{

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Answer> BaseTask<T> createTask(Cmd<T> cmd)
			throws VirtException {
		// TODO Auto-generated method stub
		return (BaseTask<T>) new ConnectCloudVMTask((ConnectCloudVMCmd) cmd);
		
	}

}
