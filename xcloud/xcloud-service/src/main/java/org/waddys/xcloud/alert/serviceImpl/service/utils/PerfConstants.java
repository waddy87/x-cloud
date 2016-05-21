package org.waddys.xcloud.alert.serviceImpl.service.utils;

public class PerfConstants {
	public static final String CPU_USAGE_ID 		= "cpu.usage.AVERAGE"; //CPU使用率
	public static final String CPU_TOTAL_ID 		= "cpu.totalmhz.AVERAGE"; //CPU hz数
	public static final String CPU_USED_ID		    = "cpu.usagemhz.AVERAGE";  //cpu当前赫兹，可以表示某物理CPU的赫兹，也可以表示某vcpu的当前赫兹
	
	public static final String MEM_USAGE_ID 		= "mem.usage.AVERAGE";//mem使用率
	public static final String MEM_TOTAL_ID 		= "mem.totalmb.AVERAGE";//mem总容量
	public static final String MEM_USED_ID 		    = "mem.consumed.AVERAGE";  //当前mem使用量
	
	public static final String DISK_IOPS_ID 		= "disk.iops.AVERAGE"; //DISK IOPS,自定义标识
	public static final String DISK_IO_SPEED_ID 	= "disk.io.AVERAGE"; //DISK IO速率，自定义标识
	
	public static final String DISK_READ_ID 		= "disk.read.AVERAGE"; //DISK 读速率
	public static final String DISK_WRITE_ID 		= "disk.write.AVERAGE"; //DISK 写速率
	public static final String DISK_IOPS_READ_ID    = "disk.numberReadAveraged.AVERAGE"; //DISK IOPS 读
	public static final String DISK_IOPS_WRITE_ID   = "disk.numberWriteAveraged.AVERAGE"; //DISK IOPS 写
	
	public static final String VDISK_IOPS_ID 		= "vdisk.iops.AVERAGE"; //虚拟DISK IOPS,自定义标识
	public static final String VDISK_IO_SPEED_ID 	= "vdisk.io.AVERAGE"; //虚拟DISK IO速率,自定义标识
	
	public static final String VDISK_READ_ID 		= "virtualDisk.read.AVERAGE"; //虚拟DISK 读速率
	public static final String VDISK_WRITE_ID 		= "virtualDisk.write.AVERAGE"; //虚拟DISK 写速率
	public static final String VDISK_IOPS_READ_ID   = "virtualDisk.numberReadAveraged.AVERAGE"; //虚拟DISK IOPS 读
	public static final String VDISK_IOPS_WRITE_ID  = "virtualDisk.numberWriteAveraged.AVERAGE"; //虚拟DISK IOPS 写
	
	public static final String DISK_TOTAL_ID 		= "disk.total.AVERAGE"; //DISK 总容量
	public static final String DISK_USED_ID 		= "disk.used.AVERAGE"; //DISK 已使用量
	public static final String DISK_USAGE_ID 		= "disk.usage.AVERAGE"; //DISK 使用率
	
	
	public static final String NET_IO_SPEED_ID 	    = "net.transmitted.AVERAGE";  //网络IO 速率
	public static final String NET_RX_ID 		 	= "net.bytesRx.AVERAGE";  //网络IO接收速率
	public static final String NET_TX_ID 		 	= "net.bytesTx.AVERAGE";  //网络IO发送速率
	
	public static final String VM_NUMS_ID 		    = "num.vms.AVERAGE";  //主机上有多少个虚拟机
	
	public static final String OBJ_RUN_STATUS_ID    = "obj.run.status";   //主机或虚拟机的运行状态
	public static final String OBJ_POWER_STATUS_ID  = "obj.power.status";  //主机或虚拟机的电源状态
	
	public static final String IPADDRESS_ID         = "obj.ip.value";
	public static final String OPERATION_SYSTEM_ID  = "obj.operationsystem.name";
	
	
		
	
	public static String[] HOST_PERF = {
			                              NET_IO_SPEED_ID,  
										  DISK_READ_ID,  
										  DISK_WRITE_ID,  
										  DISK_IOPS_READ_ID,
										  DISK_IOPS_WRITE_ID, 
										  NET_RX_ID, 
										  NET_TX_ID
									   };
    // haoqy added
    public static String[] HOST_BASIC_PERF = { NET_IO_SPEED_ID, DISK_READ_ID, DISK_WRITE_ID };
    public static String[] VM_BASIC_PERF = { VDISK_READ_ID, VDISK_WRITE_ID, NET_IO_SPEED_ID };
    // end
	public static String[] VM_PERF  = {
										  CPU_USAGE_ID, 
										  NET_RX_ID,
										  NET_TX_ID, 
										  NET_IO_SPEED_ID, 
										  VDISK_IOPS_READ_ID,
										  VDISK_IOPS_WRITE_ID,
										  VDISK_READ_ID,
										  VDISK_WRITE_ID
									  };
	
	public static final int FRESH_RATE = 20;
	public static final String PERF_DEFAULT_VALUE   		= "0";  //当性能指标值不存在时的默认值，表示非法或者不能获取
	public static final long   PERF_DEFAULT_LONG_VALUE   	=  0;  //当性能指标值不存在时的默认值，表示非法或者不能获取
	public static final double PERF_DEFAULT_DOUBLE_VALUE   	=  0.0;  //当性能指标值不存在时的默认值，表示非法或者不能获取
	
	public static String[] HOST_PROPERTIES = {
												"name",
												"summary",
												"hardware"			
											 };
	public static String[] VM_PROPERTIES = {
												"name",
												"config",
												"summary"		
		 									 };
	
	public static final String ENTITY_ALL = "ManagedEntity";// 全部实体
	public static final String ENTITY_DC = "Datacenter";// 数据中心
	public static final String ENTITY_CLUSTER = "ClusterComputeResource";// 集群（物理分区）
	public static final String ENTITY_HOST = "HostSystem";// 主机（物理机）
	public static final String ENTITY_RP = "ResourcePool";// 资源池
	public static final String ENTITY_VM = "VirtualMachine";// 虚拟机
	public static final String ENTITY_SWICTH = "VmwareDistributedVirtualSwitch";// 虚拟交换机
	public static final String ENTITY_VPG = "DistributedVirtualPortgroup";// 虚拟端口组
	public static final String ENTITY_TASK = "Task";// 任务
	public static final String ENTITY_DS = "Datastore";// 数据存储
	public static final String ENTITY_ALARM = "Alarm";// 告警
	
	public static final String OBJECT_STATUS_GREEN = "ok";// 主机或者虚拟机的状态
	public static final String OBJECT_STATUS_YELLOW = "warning";// 
	public static final String OBJECT_STATUS_RED = "critical";// 
	public static final String OBJECT_STATUS_GRAY = "unknown";// 
	
	public static final String OBJECT_POWER_STATUS_ON = "on";
	public static final String OBJECT_POWER_STATUS_SUSPEND = "suspend";
	public static final String OBJECT_POWER_STATUS_OFF = "off";
	public static final String OBJECT_POWER_STATUS_STANDBY = "standby";
	public static final String OBJECT_POWER_STATUS_UNKNOWN = "unknown";
	
	public static final String HOST_CONN_STATUS_CONNECTED = "connected";
	public static final String HOST_CONN_STATUS_DISCONNECTED = "disconnected";
	public static final String HOST_CONN_STATUS_NORES = "notResponding";
	
	public static final String UNKNOW_NAME	= "无";
	
	public static final String HOST_PREFIX_KEY = "cloudview_host@";
	
	//echart 图上横纵坐标值在json中的所属的属性名
	public static final String JSON_PERF_TOPN_KEY 		= "topn";
	public static final String JSON_HOST_PERF_NAME_KEY 	= "hosts";
	public static final String JSON_VM_PERF_NAME_KEY 	= "vms";
	public static final String JSON_PERF_VALUE_KEY  	= "values";
	
	
	
	public static final String JSON_NAME_KEY 			= "name";
	public static final String JSON_ID_KEY 				= "id";
	public static final String JSON_DC_KEY 				= "datacenter";
	public static final String JSON_CLUSTER_KEY 		= "cluster";
	
	public static final String JSON_TRIGGER_ALARM_KEY 	= "triggeredAlarm";
	
	public static final String JSON_ALARM_ID_KEY 		= "alarmId";
	public static final String JSON_ALARM_NAME_KEY 		= "alarmName";
	public static final String JSON_ALARM_DESC_KEY 		= "alarmDesc";
	public static final String JSON_ALARM_TIME_KEY 		= "alarmTime";
	public static final String JSON_ALARM_ACK_TIME_KEY 	= "alarmAckTime";
	public static final String JSON_ALARM_ACK_USER__KEY = "alarmAckUser";
	
	
	//性能指标项
	public static final String JSON_CPU_USAGE_KEY 		= "cpuUsage";
	public static final String JSON_CPU_USED_KEY  		= "cpuUsed";
	
	public static final String JSON_MEM_USAGE_KEY 		= "memUsage";
	public static final String JSON_MEM_USED_KEY 		= "memUsed";
			
	public static final String JSON_DISK_IO_KEY 		= "diskIo";
	public static final String JSON_DISK_IOPS_KEY 		= "diskIops";
	
	public static final String JSON_NET_IO_KEY 			= "netIo";
	public static final String JSON_VM_NUMS_KEY 		= "vmNums";
	
	public static final String JSON_CPU_TOTAL_KEY 		= "cpuTotal";
	public static final String JSON_MEM_TOTAL_KEY 		= "memTotal";
	
	
	public static final String JSON_DISK_USED_KEY 		= "diskUsed";
	public static final String JSON_DISK_TOTAL_KEY 		= "diskTotal";
	public static final String JSON_DISK_USAGE_KEY 		= "diskUsage";
	
	public static final String JSON_STATUSL_KEY 		= "objStatus";
	public static final String JSON_POWER_STATUS_KEY 	= "objPowerStatus";
	public static final String JSON_CONN_STATUS_KEY 	= "objConnStatus";
	
	public static final String JSON_IP_ADDRESS_KEY 		= "ipAddress";
	public static final String JSON_HOSTNAME_KEY 		= "hostName";
	
	
	
	

}
