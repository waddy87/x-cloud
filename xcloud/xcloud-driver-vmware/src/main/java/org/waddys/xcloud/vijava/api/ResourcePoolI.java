package org.waddys.xcloud.vijava.api;

import java.rmi.RemoteException;

import com.sugon.vim25.InvalidProperty;
import com.sugon.vim25.RuntimeFault;
import com.sugon.vim25.mo.ResourcePool;
import com.sugon.vim25.mo.ServiceInstance;
/**
 * 资源池操作接口
 * 
 * @category see
 * @author
 */
public interface ResourcePoolI {

	/***
	 * 
	 * 根据moid获取资源池
	 * @param moid
	 * @return ResourcePool
	 * @throws Exception 
	 * @throws RemoteException 
	 * @throws RuntimeFault 
	 * @throws InvalidProperty 
	 **/
	public ResourcePool getResoucePoolbyMoid(String moid) throws RemoteException;
	/***
	 * 
	 * 获取ServiceInstance
	 * @return ServiceInstance
	 **/
	ServiceInstance getSi();
	/***
	 * 
	 * 设置ServiceInstance
	 * @param ServiceInstance
	 **/
	void setSi(ServiceInstance si);
}
