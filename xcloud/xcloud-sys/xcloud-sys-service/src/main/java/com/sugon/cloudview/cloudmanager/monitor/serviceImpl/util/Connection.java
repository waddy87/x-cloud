package com.sugon.cloudview.cloudmanager.monitor.serviceImpl.util;

import java.rmi.RemoteException;
import org.springframework.stereotype.Service;
import com.sugon.cloudview.cloudmanager.vijava.impl.Session;
import com.sugon.vim25.RuntimeFault;
import com.sugon.vim25.mo.ServiceInstance;


@Service("monitor-connection")
public class Connection {

 
	public  static ServiceInstance si=null;
	
	//如果连接无法获取，则持续进行连接请求。
	
	public  static final long interval=5000;

	public Connection() {
		
	}
	
	//保证si长连接方法，并且是最新有效的连接对象，每隔5s，发送一次请求
	public void keepAlive(){
		
		Thread conThread=new Thread(
				new Thread(){
					@Override
					public void run(){
						while(true){
							try {
								ServiceInstance retSi= Session.getInstanceByToken("");
								Connection.si=retSi;
								Thread.sleep(interval);
							} catch (Exception e) {		
								//e.printStackTrace();
							}	
						}
						
					}
				}
				
				);
		conThread.start();
	}

	// 登陆成功后，获取连接
	private int getConection() {

			try {
				//si修改成单例模式，为了防止si的连接失效，或未建立连接，keepAlive保证5s连接一次
				synchronized (this ) {
					if(Connection.si==null){
						try {
							Connection.si = Session.getInstanceByToken("");
						} catch (Exception e) {
						}
						keepAlive();
					}
				}
			
			} catch (Exception e) {
				//e.printStackTrace();
			}
	
		return 0;
	}

	public Connection getConnectionInstance() {
		this.getConection();
		return this;
	}

	public ServiceInstance getSi() {
		//连接对象会超时，直接用单例模式，超时后无法获取到数据
		//if (this.si==null) {
			this.getConnectionInstance();
		//}
		return Connection.si;
	}


	// 注销的时候，释放连接
	public int releaseConnection() {
		try {
			Connection.si.getSessionManager().logout();
		} catch (RuntimeFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void setSi(ServiceInstance service) {
		Connection.si = service;
	}


}