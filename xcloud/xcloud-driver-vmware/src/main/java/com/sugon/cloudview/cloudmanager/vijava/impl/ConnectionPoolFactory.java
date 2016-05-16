package com.sugon.cloudview.cloudmanager.vijava.impl;

import java.net.URL;
import java.util.Calendar;

import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;

import com.sugon.cloudview.cloudmanager.vijava.util.CloudVMConfig.ConnectionInfo;
import com.sugon.cloudview.cloudmanager.vijava.util.LoadSystemPropertyJYaml;
import com.sugon.vim25.mo.ServerConnection;
import com.sugon.vim25.mo.ServiceInstance;

public class ConnectionPoolFactory extends BaseKeyedPoolableObjectFactory<String, ServerConnection> {

	@Override
	public ServerConnection makeObject(String key) throws Exception {

		ConnectionInfo connInfo = LoadSystemPropertyJYaml.getConnInfoByToken(key);
		ServerConnection conn = null;
		if (connInfo != null) {
			String __vcenter_url = "https://" + connInfo.getVcenter_ip() + "/sdk";
			ServiceInstance si = new ServiceInstance(new URL(__vcenter_url), 
					connInfo.getVcenter_user(), connInfo.getVcenter_passwd(), true);
			if (null != si) {
				conn = si.getServerConnection();
			}
		}
		return conn;
	}

	public static void main(String args[]) throws Exception {
		
		System.out.println(Calendar.getInstance().getTimeInMillis());
		ConnectionPoolFactory factory = new ConnectionPoolFactory();
		GenericKeyedObjectPool<String, ServerConnection> pool = 
				new GenericKeyedObjectPool<String, ServerConnection>(factory);
		System.out.println("\n===========" + "===========");
		System.out.println("池中处于闲置状态的实例pool.getNumIdle()：" + pool.getNumIdle());
		ConnectionInfo conni = new ConnectionInfo();
		String vcenter_url = "https://" + "10.0.31.251" + "/sdk";

		conni.setVcenter_ip(vcenter_url);
		conni.setVcenter_user("admin");
		conni.setVcenter_passwd("Sugon!123");

		// 从池里取一个对象，新创建makeObject或将以前闲置的对象取出来
		// pool.addObject(conni);
		ServerConnection conn = pool.borrowObject("10.0.31.251");
		System.out.println("session:" + conn.getSessionStr());
		System.out.println(Calendar.getInstance());
	}
}
