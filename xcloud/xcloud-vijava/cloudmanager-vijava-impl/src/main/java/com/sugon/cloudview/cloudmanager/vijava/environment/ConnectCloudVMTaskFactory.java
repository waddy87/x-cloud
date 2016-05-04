package com.sugon.cloudview.cloudmanager.vijava.environment;

import com.sugon.cloudview.cloudmanager.vijava.base.Answer;
import com.sugon.cloudview.cloudmanager.vijava.base.BaseTask;
import com.sugon.cloudview.cloudmanager.vijava.base.BaseTaskFactory;
import com.sugon.cloudview.cloudmanager.vijava.base.Cmd;
import com.sugon.cloudview.cloudmanager.vijava.environment.ConnectCloudVM.ConnectCloudVMCmd;
import com.sugon.cloudview.cloudmanager.vijava.exception.VirtException;

public class ConnectCloudVMTaskFactory implements BaseTaskFactory{

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Answer> BaseTask<T> createTask(Cmd<T> cmd)
			throws VirtException {
		// TODO Auto-generated method stub
		return (BaseTask<T>) new ConnectCloudVMTask((ConnectCloudVMCmd) cmd);
		
	}

}
