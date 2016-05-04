package com.sugon.cloudview.cloudmanager.log.type;

public class LogConst {
	
	/**
	 * 业务类型
	 */
	public final static String HOST = "主机管理";
	public final static String NETWORK = "网络管理";
	public final static String STORAGE = "存储管理";
	
	/**
	 * 模块代码
	 */
	public final static String M_VENV_CONFIG = "虚拟化环境";
	public final static String M_PRO_VDC = "提供者VDC";
	public final static String M_VNET = "虚拟网络";
	public final static String M_VM_TEMPLAT = "模板管理";
	public final static String M_SERVICE_APPROVAL = "申请审批";
	public final static String M_ORG_MGMT = "组织管理";
	public final static String M_VM_MGMT = "虚拟机";
	public final static String M_VM_OLD = "利旧虚拟机";
	public final static String M_PM = "物理机";
	public final static String M_MONITOR = "监控管理";
	public final static String M_ALARM = "告警管理";
	public final static String M_LOG = "日志管理";
	public final static String M_PROJECT = "项目管理";
	public final static String M_APPLICATION = "资源申请";
	

	/**
	 * 操作类型
	 */
	public final static String OPERATIONTYPE_UPLOAD = "上传";
	public final static String OPERATIONTYPE_ADD = "新建";
	public final static String OPERATIONTYPE_UPDATE = "修改";
	public final static String OPERATIONTYPE_DELETE = "删除";
	public final static String OPERATIONTYPE_START = "启动";
	public final static String OPERATIONTYPE_STOP = "停止";
	public final static String OPERATIONTYPE_SHUTDOWN = "关机";
	public final static String OPERATIONTYPE_RESTART = "重启";
	public final static String OPERATIONTYPE_SUSPEND = "挂起";
	public final static String OPERATIONTYPE_AWAKE = "唤醒";
	public final static String OPERATIONTYPE_PAUSE = "暂停";
	public final static String OPERATIONTYPE_RESUME = "恢复";
	public final static String OPERATIONTYPE_BACKUP = "备份";
	public final static String OPERATIONTYPE_TRANSFORM = "转换";
	public final static String OPERATIONTYPE_VNC = "vnc访问";
	public final static String OPERATIONTYPE_MIGRAT = "迁移";
	public final static String OPERATIONTYPE_IMPORT = "导入";
	public final static String OPERATIONTYPE_AVAILABILITY = "高可用";
	public final static String OPERATIONTYPE_ADD_SNAPSHOT = "新建快照";
	public final static String OPERATIONTYPE_RESUME_SNAPSHOT = "恢复快照";
	public final static String OPERATIONTYPE_DELETE_SNAPSHOT = "删除快照";
	public final static String OPERATIONTYPE_MERGE_SNAPSHOT = "合并快照";

	public final static String OPERATIONTYPE_REGISTER = "注册";
	public final static String OPERATIONTYPE_CANCELLATION = "注销";
	public final static String OPERATIONTYPE_OPERATION = "运维";
	public final static String OPERATIONTYPE_ISOLATE = "隔离";
	public final static String OPERATIONTYPE_UNISOLATE = "撤销隔离";
	public final static String OPERATIONTYPE_OFFLINE = "离线";
	public final static String OPERATIONTYPE_CANCELOFFLINE = "撤销离线";

	public final static String OPERATIONTYPE_DEPLOY = "部署";
	public final static String OPERATIONTYPE_TRANSLATE = "转换";

	public final static String OPERATIONTYPE_FIREWALLRULES_APPLY = "应用防火墙规则";
	public final static String OPERATIONTYPE_FIREWALLRULES_ENABLE = "启用防火墙规则";
	public final static String OPERATIONTYPE_FIREWALLRULES_DISABLE = "禁用防火墙规则";
	public final static String OPERATIONTYPE_DHCP_OPERATION = "DHCP操作";
	public final static String OPERATIONTYPE_UNLINK_ROUTER = "断开路由器";
	public final static String OPERATIONTYPE_LINK_ROUTER = "关联路由器";
	
	public final static String OPERATIONTYPE_CONNECT = "连接";//连接存储系统的动作
	public final static String OPERATIONTYPE_DISCONNECT = "断开连接";
	
	public final static String OPERATIONTYPE_CONFIG = "配置";//配置云主机
	public final static String OPERATIONTYPE_LOCK = "锁定";//锁定存储池
	public final static String OPERATIONTYPE_UNLOCK = "解锁";//解锁存储池
	public final static String OPERATIONTYPE_REPLACE = "更新";//更新存储池
	public final static String OPERATIONTYPE_READWRITE = "读写";//只读或读写存储池的权限
	
	public final static String OPERATIONTYPE_FORMAT = "格式化";//格式化磁盘
	public final static String OPERATIONTYPE_CLONE = "克隆";//磁盘克隆
	public final static String OPERATIONTYPE_ONLYREAD = "只读";//只读磁盘
	
	public final static String OPERATIONTYPE_APPLY = "申请";//申请IP池
	public final static String OPERATIONTYPE_FREE_STATICALLOTIP = "空闲  静态分配IP";//静态分配   空闲IP地址
	public final static String OPERATIONTYPE_FREE_KEEPIP = "空闲  保留IP";//空闲  保留IP地址
	public final static String OPERATIONTYPE_DYNAMICALLOT_KEEPIP = "动态分配  保留IP";//动态分配  保留IP
	public final static String OPERATIONTYPE_STATICALLOT_KEEPIP = "静态分配  保留IP";//静态分配  保留IP
	public final static String OPERATIONTYPE_STATICALLOT_RELEASEIP = "静态分配  释放IP";//静态分配  释放IP
	public final static String OPERATIONTYPE_KEEP_UNKEEPIP = "保留  解除保留IP";//静态分配  释放IP
	
	public final static String OPERATIONTYPE_DISTRIBUTION = "分配";//分配设备
	public final static String OPERATIONTYPE_INIT = "初始化";//初始化设备
	public final static String OPERATIONTYPE_ENABLE = "启用";//启用(协议地址、设备)
	public final static String OPERATIONTYPE_DISABLE = "禁用";//禁用(协议地址、设备)
	
	/**
	 * 资源类型
	 */
	public final static String RESOURCE_VM = "虚拟机";//虚拟机
	public final static String RESOURCE_SNAPSHOT = "快照";//快照
	public final static String RESOURCE_HOST = "主机";//主机
	public final static String RESOURCE_TEMPLATE = "模板";//模板
	public final static String RESOURCE_CLUSTER = "集群";//集群
	public final static String RESOURCE_RESOURCEPOOL = "资源池";//资源池
	public final static String RESOURCE_NETWORK = "网络";// 网络
	public final static String RESOURCE_IPSOURCE = "IP源";// 网络地址--IP源
	public final static String RESOURCE_IPPOOL = "IP池";// 网络地址--IP池
//	public final static String RESOURCE_IP = "IP";// 网络地址--IP地址
	public final static String RESOURCE_DEVICE = "设备";// 网络--设备
	public final static String RESOURCE_SCHEDULE = "调度";// 调度
	public final static String RESOURCE_SCHEDULEOBJ = "调度实例";// 调度实例
	public final static String RESOURCE_STORAGESYS = "存储系统";// 存储系统
//	public final static String RESOURCE_STORAGENODE = "storageNode";// 存储节点
//	public final static String RESOURCE_STORAGESERVICEACCESSPOINT = "serviceAccessPoint";// 服务访问点
//	public final static String RESOURCE_STORAGESYSDIR = "storeSysDir ";// 存储系统目录
	public final static String RESOURCE_STORAGEPOOL = "存储池";// 存储池
	public final static String RESOURCE_STORAGEDISK = "磁盘";// 存储磁盘
	public final static String RESOURCE_ISO = "镜像";// 存储镜像
	public final static String RESOURCE_APPLICATION_FORM = "申请单";// 申请单
	
	//为设备端口的操作：协议过滤、地址过滤、带宽限制
//	public final static String RESOURCE_DEVICEPORT = "devicePort";// 设备端口
//	public final static String RESOURCE_PROTOCOL = "protocol";// 协议(启用、禁用、删除)、协议过滤
//	public final static String RESOURCE_ADDRESSFILTER = "addressFilter";// 地址过滤
//	public final static String RESOURCE_BANDWIDTHLIMIT = "bandwidthLimt";// 带宽限制
	
//	public final static String RESOURCE_LLDP = "LLDP";// LLDP
//	public final static String RESOURCE_SWITCHDEVICEPORT = "switchDevicePort";// 交换机设备端口
//	public final static String RESOURCE_DHCP = "dhcp";// DHCP
	
}
