package com.sugon.cloudview.cloudmanager.vijava.impl;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.pool.impl.GenericKeyedObjectPool;

import com.sugon.cloudview.cloudmanager.vijava.util.CloudVMConfig.ConnectionInfo;
import com.sugon.cloudview.cloudmanager.vijava.util.LoadSystemPropertyJYaml;
import com.sugon.vim25.UserSession;
import com.sugon.vim25.mo.ServerConnection;
import com.sugon.vim25.mo.ServiceInstance;

public class Session {

	private static String __vcenter_ip = "";
	private static String __vcenter_user = "";
	private static String __vcenter_passwd = "";
	private static String __vcenter_token = "";

	// private static ServiceInstance serviceInstance = null;
	private static ConnectionPoolFactory factory = new ConnectionPoolFactory();
	private static GenericKeyedObjectPool<String, ServerConnection> pool = new GenericKeyedObjectPool<String, ServerConnection>(
			factory);

	/**
	 * get connection from pool.
	 * 
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	private synchronized static ServerConnection getConnectionFromPool(
			String key) throws Exception {

		// TODO 此处需要验证 30分钟重置之后的si与原来的si是否一样~~~~ 如果不一样 那么原来的si怎么办
		ServerConnection conn = pool.borrowObject(key);
		try {
			if (conn != null) {
				// 通过本地usersession和服务器端usersession 的Logintime 来计算出来两边的时间差
				UserSession usessionLocal = conn.getUserSession();
				UserSession usessionServer = conn.getServiceInstance()
						.getSessionManager().getCurrentSession();
				long deltaTime = usessionServer.getLoginTime()
						.getTimeInMillis()
						- usessionLocal.getLoginTime().getTimeInMillis();

				// 用服务器端的lastactivetime减去时间差 就是连接最后一次活跃时间（本地时间）
				long startTime = usessionServer.getLastActiveTime()
						.getTimeInMillis() - deltaTime;

				// 获取当前时间
				long currentTime = System.currentTimeMillis();

				// 如果最后活跃时间 距今超过30分钟 强制清理连接 然后重连

				if (currentTime >= (startTime + 30 * 60 * 1000)) {
					// System.out.println("timeout.......");
					pool.returnObject(key, conn);
					pool.clear(key);
					conn = pool.borrowObject(key);

				}
			}
		} catch (Exception e) {
			throw new Exception("borrow conn from pool err...");
		}

		return conn;
	}

	/**
	 * This method is used for return connction to the pool.
	 * 
	 * @param ip
	 * @param conn
	 * @throws Exception
	 */
	private synchronized static void returnConnectionToPool(String key,
			ServerConnection conn) throws Exception {
		try {
			pool.returnObject(key, conn);
		} catch (Exception e) {
			throw new Exception("return conn to pool err...");
		}
	}

	/**
	 * this method is used to get connections in pool.
	 * 
	 * @return the map of connection in conn pool.
	 * @throws Exception
	 */
	public synchronized static Map<String, ServerConnection> getConnections()
			throws Exception {

		Map<String, ServerConnection> map = new HashMap<String, ServerConnection>();
		try {
			ConnectionInfo[] connInfos = LoadSystemPropertyJYaml.getConnInfos();
			if (connInfos != null) {
				for (ConnectionInfo connInfo : connInfos) {
					String token = connInfo.getVcenter_token();
					ServerConnection conn = getConnectionFromPool(token);
					map.put(token, conn);
					returnConnectionToPool(token, conn);
				}
			}
		} catch (Exception e) {
			throw new Exception("get connections err...");
		}
		return map;
	}

	/**
	 * This method returns a default serviceInstance. You can use
	 * getInstanceByToken(String token) or getInstance(String ip, String
	 * userName, String passWord, String token) instead。
	 */
	private static ServiceInstance getInstance() throws Exception {

		ServerConnection conn = null;
		ServiceInstance serviceInstance = null;
		try {
			ConnectionInfo connInfo = LoadSystemPropertyJYaml.getConnInfo();
			if (connInfo != null) {
				String token = connInfo.getVcenter_token();
				if (token != null)
					conn = getConnectionFromPool(token);
				else
					initServiceInstance(connInfo.getVcenter_ip(),
							connInfo.getVcenter_user(),
							connInfo.getVcenter_passwd());
				if (conn != null) {
					serviceInstance = conn.getServiceInstance();
					returnConnectionToPool(token, conn);
				}
			} else {
				// TODO 发布之前一定要注释掉 并且抛出未连接异常
//				serviceInstance = initServiceInstance("10.0.36.52",
//						"Administrator@CloudVirtual.local","Sugon@123");
				serviceInstance = initServiceInstance("10.0.36.121","admin","Sugon!123");

			}
		} catch (Exception e) {
			throw new Exception("get default instance err...");
		}
		return serviceInstance;

	}

	/**
	 * This method used for check ConnectionInfo is right and add Conncet to
	 * CloudManager System. If ConnectionInfo have already saved,the param token
	 * is used to getInstance.
	 * 
	 * @param ip
	 * @param userName
	 * @param passWord
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static ServiceInstance getInstance(String ip,
			String userName, String passWord) throws Exception {

		__vcenter_ip = ip;
		__vcenter_user = userName;
		__vcenter_passwd = passWord;
		ServerConnection conn = null;
		ServiceInstance serviceInstance = null;
		try {
			serviceInstance = initServiceInstance(__vcenter_ip, __vcenter_user,
					__vcenter_passwd);

			conn = serviceInstance.getServerConnection();
			__vcenter_token = serviceInstance.getAboutInfo().getInstanceUuid();
			returnConnectionToPool(__vcenter_token, conn);
		} catch (Exception e) {
			throw new Exception("get instance by usr and pwd err...");
		}
		return serviceInstance;
	}

	/**
	 * TODO 尽快删除
	 * 
	 * @deprecated This method is deprecated on 20160331. You can use
	 *             getInstanceByToken(String token) or getInstance(String ip,
	 *             String userName, String passWord, String token) instead。
	 */
	public static ServiceInstance getInstanceByIP(String ip)
			throws Exception {

		ServiceInstance serviceInstance = null;
		ServerConnection conn = null;
		try {
			ConnectionInfo connInfo = LoadSystemPropertyJYaml
					.getConnInfoByIP(ip);
			// 这里未对connInfo 作非空检查 便于抛出未连接异常
			String token = connInfo.getVcenter_token();
			if (token != null)
				conn = getConnectionFromPool(token);
			else
				initServiceInstance(ip, connInfo.getVcenter_user(),
						connInfo.getVcenter_passwd());
			if (conn != null) {
				serviceInstance = conn.getServiceInstance();
				// System.out.println(pool.getNumActive());
				returnConnectionToPool(token, conn);
				// System.out.println(pool.getNumActive());
			}
		} catch (Exception e) {
			throw new Exception("get instance by ip err...");
		}
		return serviceInstance;
	}

	public static ServiceInstance getInstanceByToken(String token)
			throws Exception {

		// 1.如果token是空的 调用过时的getInstance()接口
		// 2.如果token不是空的 从Pool里面取 参考现有getInstancebyip做法
		ServiceInstance serviceInstance = null;
		ServerConnection conn = null;
		try {
			if (token != null && !token.equals(""))
				conn = getConnectionFromPool(token);
			else
				conn = getInstance().getServerConnection();

			if (conn != null) {
				serviceInstance = conn.getServiceInstance();
				returnConnectionToPool(token, conn);
			}
		} catch (Exception e) {
			throw new Exception("get instance by token err...");
		}
		return serviceInstance;

	}

	private static ServiceInstance initServiceInstance(String ip,
			String userName, String passWord) throws Exception {

		__vcenter_ip = ip;
		__vcenter_user = userName;
		__vcenter_passwd = passWord;
		
		String __vcenter_url = "https://" + __vcenter_ip + "/sdk";
		ServiceInstance serviceInstance = new ServiceInstance(new URL(__vcenter_url),
				__vcenter_user, __vcenter_passwd, true);
		return serviceInstance;
	}

}
