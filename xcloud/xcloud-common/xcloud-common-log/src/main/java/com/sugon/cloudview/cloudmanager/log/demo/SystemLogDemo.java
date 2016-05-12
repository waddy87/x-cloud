package com.sugon.cloudview.cloudmanager.log.demo;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemLogDemo {

	private final static Logger logger = LoggerFactory.getLogger(SystemLogDemo.class);

	public static void main(String[] args) {
		
		final int N = 100;
		for(int i = 0; i < N; i++)
		{
			logger.info("===================================");
			logger.info("" + logger.getClass());
			logger.error("Proccess {}. The temperature is {}. ", i,  new Date());
		}
	}

}
