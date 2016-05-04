package com.sugon.cloudview.cloudmanager.vijava.util;


import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sugon.cloudview.cloudmanager.vijava.util.CloudVMConfig.ConnectionInfo;

public class LoadSystemPropertyJYaml {

	private final static Logger logger = LoggerFactory.getLogger(LoadSystemPropertyJYaml.class);
	
	/**
	 * 全局连接信息静态变量 
	 * saveCloudVMConfig：保存
	 * getCloudVMConfig： 读取
	 * clearConfigInfo： 清除
	 */
	public static CloudVMConfig cloudVMConfig = null;

	
	/**
	 * used for loading connection info 
	 * @param file
	 * @return CloudVMConfig 
	 * @throws IOException
	 */
	public static CloudVMConfig getCloudVMConfig() throws IOException  {
		
		return cloudVMConfig;
	}
	
	public static ConnectionInfo getConnInfoByIP(String ip) throws Exception {
		CloudVMConfig config = getCloudVMConfig();
		ConnectionInfo info = null;
		
		if (config != null) {
			for (ConnectionInfo vcenterInfo : config.getVcenterInfo()) {
			
				if (vcenterInfo.getVcenter_ip().equals(ip)){
					info = vcenterInfo;
					break;
				}
			}
		}
		
		return info;
	}
	
	
	/**
	 * used for getting the default connection(latest connection)
	 * @return ConnectionInfo
	 * @throws Exception
	 */
	public static ConnectionInfo getConnInfo() throws Exception {
		
		CloudVMConfig config = getCloudVMConfig();
		ConnectionInfo info = null;

		if (config != null) {
			ConnectionInfo[] vcenterInfo = config.getVcenterInfo();
			if (vcenterInfo != null && vcenterInfo.length != 0)
				info = vcenterInfo[vcenterInfo.length - 1];
		}
		return info;
	}
	
	
	/**
	 * used for getting the all of the connections
	 * @return ConnectionInfo
	 * @throws Exception
	 */
	public static ConnectionInfo[] getConnInfos() throws Exception {
			CloudVMConfig config = getCloudVMConfig();
			ConnectionInfo[] vcenterInfo = config.getVcenterInfo();
		return vcenterInfo;
	}
	
	/**
	 * used for get connection by token.
	 * @param token
	 * @return ConnectionInfo
	 * @throws Exception
	 */
	public static ConnectionInfo getConnInfoByToken(String token) throws Exception {
		
		CloudVMConfig config = getCloudVMConfig();
		ConnectionInfo info = null;
		
		if (config != null) {
			for (ConnectionInfo vcenterInfo : config.getVcenterInfo()) {
			
				if (token.equals(vcenterInfo.getVcenter_token()))
					info = vcenterInfo;
			}
		}
		
		return info;
	}
	
	/**
	 * used for saving connection info 
	 * @param cloudVMConfig
	 * @throws Exception
	 */
    public synchronized static void saveCloudVMConfig(CloudVMConfig cloudVMconfig) throws Exception {

    	logger.debug("add connection info: " + cloudVMconfig);
    	cloudVMConfig = cloudVMconfig;
    	
    }
    
    public  synchronized static void clearConfigInfo() throws Exception {
    	logger.debug("delete all connection info: " + cloudVMConfig);
    	cloudVMConfig = null;
    }
    
}