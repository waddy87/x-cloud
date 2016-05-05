package com.sugon.cloudview.cloudmanager.alert.serviceImpl.service.utils;

import org.springframework.stereotype.Service;

import com.sugon.cloudview.cloudmanager.vijava.impl.Session;
import com.sugon.vim25.mo.ServiceInstance;

@Service("alert-connection")
public class Connection {

	public static ServiceInstance getSi() {
		//由vijava接口维护最新连接信息，每次都从该对象获取最新连接。
		try {
			ServiceInstance siInstance= Session.getInstanceByToken("");
			return siInstance;
		} catch (Exception e) {
			return null;
		}
		
	}	


}