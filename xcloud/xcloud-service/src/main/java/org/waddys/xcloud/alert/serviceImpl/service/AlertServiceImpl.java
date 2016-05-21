/**
 * Created on 2016年3月25日
 */
package org.waddys.xcloud.alert.serviceImpl.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.waddys.xcloud.alert.service.bo.AlarmEntity;
import org.waddys.xcloud.alert.service.bo.AlertInfoQueryUI;
import org.waddys.xcloud.alert.service.bo.AlertSenderUI;
import org.waddys.xcloud.alert.service.bo.ResourceInfoUI;
import org.waddys.xcloud.alert.service.bo.SystemResourceType;
import org.waddys.xcloud.alert.service.bo.TriggerInfoUI;
import org.waddys.xcloud.alert.service.intf.AlertServiceI;
import org.waddys.xcloud.alert.serviceImpl.dao.service.AlertInfoDaoServiceI;
import org.waddys.xcloud.alert.serviceImpl.dao.service.AlertSenderDaoServiceI;
import org.waddys.xcloud.alert.serviceImpl.dao.service.AlertSyncTagDaoServiceI;
import org.waddys.xcloud.alert.serviceImpl.entity.AlertInfo;
import org.waddys.xcloud.alert.serviceImpl.entity.AlertSender;
import org.waddys.xcloud.alert.serviceImpl.service.utils.AlertMannagerUtils;
import org.waddys.xcloud.alert.serviceImpl.service.utils.CloudViewPerfException;
import org.waddys.xcloud.alert.serviceImpl.service.utils.Connection;
import org.waddys.xcloud.alert.serviceImpl.service.utils.ToolsUtils;
import org.waddys.xcloud.alert.serviceImpl.service.utils.VCenterManageUtils;

import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * 功能名: 请填写功能名 功能描述: 请简要描述功能的要点 Copyright: Copyright (c) 2016 公司: 曙光云计算技术有限公司
 *
 * @author yangkun
 * @version 2.0.0 sp1
 */

@Service("alertServiceImpl")
public class AlertServiceImpl implements AlertServiceI {

	@Autowired
	private AlertInfoDaoServiceI alertInfoDaoServiceI;

	@Autowired
	private AlertSenderDaoServiceI alertSenderDaoServiceI;

	@Qualifier("alert-connection")
	@Autowired
	private Connection connection;

	@Autowired
	private VCenterManageUtils opUtil;

	@Autowired
	private AlertMannagerUtils alertManagerUtils;

	private static final Logger logger = LoggerFactory
			.getLogger(AlertServiceImpl.class);
	
	

	@Override
	public Map<String, Object> getAlertInfo(String time, String resType,
			String resName, String alertLevel, int pageNum, int pageSize,List<String>resIds) {

		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Object> queryMap = null;
		List<AlertInfo> aList = null;
		long total = 1L;
		// 默认查询，isAuire、resType、resName、alertLevel 4个条件为空
		if (resType == null) {
			queryMap = alertInfoDaoServiceI.findAll(pageNum, pageSize);
		} else {
			if (resType!=null &&resType.contains("all")) {
				resType = null;
			}
			if (alertLevel!=null && alertLevel.contains("all")) {
				alertLevel = null;
			}
			if (time!=null && time.contains("all")) {
				time = null;
			}
			if (resName!=null &&resName.equalsIgnoreCase("")) {
				resName = null;
			}
			queryMap = alertInfoDaoServiceI.findByMutilColumn(time,
					resType, resName, alertLevel, pageNum, pageSize,resIds);
		}
		if (queryMap != null && !queryMap.isEmpty()) {
			total = (Long) queryMap.get("total");
			aList = (List<AlertInfo>) queryMap.get("rows");
		} else { // 查询结果为空
			total = 0;
			aList = new ArrayList<AlertInfo>();
			aList.add(new AlertInfo());
		}

		List<AlertInfoQueryUI> alertInfoQueryUIs = new ArrayList<AlertInfoQueryUI>();

		for (AlertInfo aInfo : aList) {
			AlertInfoQueryUI alertInfoQueryUI = new AlertInfoQueryUI();
			try {
				BeanUtils.copyProperties(alertInfoQueryUI, aInfo);
			} catch (Exception e) {
				logger.debug("实体转换异常" + e);
			}
			alertInfoQueryUIs.add(alertInfoQueryUI);
		}
		retMap.put("total", total);
		retMap.put("rows", alertInfoQueryUIs);
		return retMap;
	}

	@Override
	public Map<String, Object> getAlertSender(String resType, String resName,
			String alertSenderLevel, int pageNum, int pageSize) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Object> queryMap = null;
		List<AlertSender> aList = null;
		long total = 1L;
		// 默认查询，isAuire、resType、resName、alertLevel 4个条件为空
		if (resType == null) {
			queryMap = alertSenderDaoServiceI.findAll(pageNum, pageSize);
		} else {
			if (resType.contains("all")) {
				resType = null;
			}
			if (alertSenderLevel.contains("all")) {
				alertSenderLevel = null;
			}
			if (resName.equalsIgnoreCase("")) {
				resName = null;
			}
			queryMap = alertSenderDaoServiceI.findByMutilColumn(resType,
					resName, alertSenderLevel, pageNum, pageSize);
		}
		if (queryMap != null && !queryMap.isEmpty()) {
			total = (Long) queryMap.get("total");
			aList = (List<AlertSender>) queryMap.get("rows");
		} else { // 查询结果为空
			total = 0;
			aList = new ArrayList<AlertSender>();
			aList.add(new AlertSender());
		}
		List<AlertSenderUI> alertSenderQueryUIs = new ArrayList<AlertSenderUI>();
		for (AlertSender aInfo : aList) {
			AlertSenderUI alertSenderQueryUI = new AlertSenderUI();
			try {
				BeanUtils.copyProperties(alertSenderQueryUI, aInfo);
			} catch (Exception e) {
				logger.debug("实体转换异常" + e);
			}
			alertSenderQueryUIs.add(alertSenderQueryUI);
		}
		retMap.put("total", total);
		retMap.put("rows", alertSenderQueryUIs);
		return retMap;
	}

	@Override
	public boolean saveAlertSender(AlertSenderUI alertSenderUI) {
		AlertSender alertSender = new AlertSender();
		try {
			BeanUtils.copyProperties(alertSender, alertSenderUI);
			alertSenderDaoServiceI.save(alertSender);
		} catch (Exception e) {
			logger.debug("保存告警发送器失败！");
			return false;
		}
		return true;
	}

	@Override
	public List<ResourceInfoUI> getResListByResType(String resTypeName) {

		// 集群
		if (SystemResourceType.computerResource.contains(resTypeName)) {
			// vijava获取资源列表信息
			List<ClusterComputeResource> cList = null;
			try {
				cList = getOpUtil().getAllClusterComputeResources();
			} catch (CloudViewPerfException e) {
				logger.debug("获取集群列表异常 " + e);
			}
			if (cList != null && !cList.isEmpty()) {
				List<ResourceInfoUI> resList = new ArrayList<ResourceInfoUI>();
				for (ClusterComputeResource computeResource : cList) {
					ResourceInfoUI resourceInfoUI = new ResourceInfoUI();
					resourceInfoUI.setName(computeResource.getName());
					resourceInfoUI.setId(computeResource.getMOR().getVal());
					resList.add(resourceInfoUI);
				}
				return resList;
			}
			// 主机
		} else if (SystemResourceType.hostSystem.contains(resTypeName)) {
			List<HostSystem> hostSystems = null;
			try {
				hostSystems = getOpUtil().getAllHostSystems();
			} catch (CloudViewPerfException e) {
				logger.debug("获取主机列表异常 " + e);
			}
			if (hostSystems != null && !hostSystems.isEmpty()) {
				List<ResourceInfoUI> resList = new ArrayList<ResourceInfoUI>();
				for (HostSystem hostSystem : hostSystems) {
					ResourceInfoUI resourceInfoUI = new ResourceInfoUI();
					resourceInfoUI.setName(hostSystem.getName());
					resourceInfoUI.setId(hostSystem.getMOR().getVal());
					resList.add(resourceInfoUI);
				}
				return resList;
			}
			// 虚拟机
		} else if (SystemResourceType.virtualMachine.contains(resTypeName)) {

			List<VirtualMachine> virtualMachines = null;
			try {
				virtualMachines = getOpUtil().getAllVirtualMachines();
			} catch (CloudViewPerfException e) {
				logger.debug("获取虚拟机列表异常 " + e);
			}
			if (virtualMachines != null && !virtualMachines.isEmpty()) {
				List<ResourceInfoUI> resList = new ArrayList<ResourceInfoUI>();
				for (VirtualMachine viMachine : virtualMachines) {
					ResourceInfoUI resourceInfoUI = new ResourceInfoUI();
					resourceInfoUI.setName(viMachine.getName());
					resourceInfoUI.setId(viMachine.getMOR().getVal());
					resList.add(resourceInfoUI);
				}
				return resList;
			}
		} else if (SystemResourceType.dataStore.contains(resTypeName)) {
			List<Datastore> datastores = null;
			try {
				datastores = getOpUtil().getAllDatastore();
			} catch (CloudViewPerfException e) {
				logger.debug("获取存储列表异常 " + e);
			}
			if (datastores != null && !datastores.isEmpty()) {
				List<ResourceInfoUI> resList = new ArrayList<ResourceInfoUI>();
				for (Datastore datastore : datastores) {
					ResourceInfoUI resourceInfoUI = new ResourceInfoUI();
					resourceInfoUI.setName(datastore.getName());
					resourceInfoUI.setId(datastore.getMOR().getVal());
					resList.add(resourceInfoUI);
				}
				return resList;
			}
		}
		return null;
	}

	@Override
	public List<TriggerInfoUI> getTriggerListByResAndResType(
			String resTypeName, String resId) {
		ManagedEntity cResource = null;

		// 集群
		if (resId.contains("domain")) {
			ClusterComputeResource clusterComputeResource = null;
			try {
				clusterComputeResource = getOpUtil()
						.getClusterComputeResourceById(resId.toString());
			} catch (CloudViewPerfException e) {
				logger.debug("获取集群触发器异常 " + e);
			}
			cResource = (ClusterComputeResource) clusterComputeResource;
			// 主机
		} else if (resId.contains("host")) {
			HostSystem hostSystem = null;
			try {
				hostSystem = getOpUtil().getHostSystemById(resId.toString());
			} catch (CloudViewPerfException e) {
				logger.debug("获取主机触发器异常 " + e);
			}
			cResource = (HostSystem) hostSystem;
			// 虚拟机
		} else if (resId.contains("vm")) {
			VirtualMachine vMachine = null;
			try {
				vMachine = getOpUtil().getVirtualMachineById(resId.toString());
			} catch (CloudViewPerfException e) {
				logger.debug("获取虚拟机触发器异常 " + e);
			}
			cResource = (VirtualMachine) vMachine;
		} else if (resId.contains("datastore")) {
			Datastore datastore = null;
			try {
				datastore = getOpUtil().getDatastoreById(resId.toString());
			} catch (CloudViewPerfException e) {
				logger.debug("获取存储触发器异常 " + e);
			}
			cResource = (Datastore) datastore;
		}

		if (cResource != null) {
			List<AlarmEntity> aList = alertManagerUtils.getTriggerAlarms(
					getOpUtil(), cResource);
			if (aList != null && !aList.isEmpty()) {
				List<TriggerInfoUI> resList = new ArrayList<TriggerInfoUI>();
				for (AlarmEntity alEntity : aList) {
					TriggerInfoUI triggUi = new TriggerInfoUI();
					triggUi.setTriggerName(alEntity.getName());
					triggUi.setTriggerId(alEntity.getId());
					resList.add(triggUi);
				}
				return resList;
			}
		}

		return null;
	}

	/**
	 * @return the opUtil
	 */
	public VCenterManageUtils getOpUtil() {
		return this.opUtil;
	}

	@Override
	public void aquireAlert(String resId, String triggerId) throws Exception {
			Map<String, Object> retMap=new HashMap<String, Object>();
			try {
				retMap= alertManagerUtils.aquireAlert(getOpUtil(), resId, triggerId);
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		/*	if((Boolean)retMap.get("ret")==false){
				throw new Exception("确认异常，告警不存在！");
			}*/
			try{
				//数据库同步该条告警记录, 以前台展现告警数据为准进行确认操作(后台同步告警数据可能消失)
				AlertInfo alertInfo=alertInfoDaoServiceI.findByResIdAndAlertId(resId, triggerId);
				if(alertInfo!=null){
					alertInfo.setAquire(true);
					alertInfo.setAquireDate((Date)retMap.get("time"));
					alertInfo.setAquireUser((String)retMap.get("user"));
					alertInfoDaoServiceI.update(alertInfo);
				}
			} catch (Exception e) {
				logger.debug("确认告警失败！");
				throw new Exception("告警确认异常!");
			}
	}

	@Override
	public void deleteAlertSender(String id) throws Exception {
		if(id==null){
			logger.error("删除失败，告警策略为空!");
			throw new Exception("删除失败，告警策略为空!");
		}
		try {
			alertSenderDaoServiceI.delete(Long.valueOf(id));
		} catch (Exception e) {
			logger.error("删除告警策略失败!");
			throw new Exception("删除告警策略失败!");
		}
	
	}
	
	@Override
	public void batchDeleteAlertSender(List<String> ids) throws Exception {
		List<AlertSender> asds=null;
		List<Long> idList=new ArrayList<Long>();
		if(ids!=null && ids.size()>0){
			for(String id:ids){
				idList.add(Long.valueOf(id));
			}
		}
		try {
			asds = alertSenderDaoServiceI.findAll(idList);
		} catch (Exception e) {
			logger.error("批量删除告警策略失败，请尝试单个删除！");
			throw new Exception("批量删除告警策略失败，请尝试单个删除！");
		}
		try {
			alertSenderDaoServiceI.deleteInBatch(asds);
		} catch (Exception e) {
			logger.error("批量删除告警策略失败，请尝试单个删除！");
			throw new Exception("批量删除告警策略失败，请尝试单个删除！");
		}
	
	}

	@Override
	public void enableAlertSender(String id,boolean enable) throws Exception {
		AlertSender asd=null;
		try {
			asd = alertSenderDaoServiceI.findById(Long.valueOf(id));
		} catch (Exception e) {
			logger.error("使能告警策略失败，告警策略为空！");
			throw new Exception("使能告警策略失败，告警策略为空！");
		}
		try {
			asd.setEnable(enable);
			alertSenderDaoServiceI.update(asd);
		} catch (Exception e) {
			logger.error("使能告警策略失败！");
			throw new Exception("使能告警策略失败！");
		}
	}
	
	@Override
	public void batchEnableAlertSender(List<String> ids,boolean enable) throws Exception {
		List<AlertSender> asds=null;
		List<Long> idList=new ArrayList<Long>();
		if(ids!=null && ids.size()>0){
			for(String id:ids){
				idList.add(Long.valueOf(id));
			}
		}
		try {
			asds = alertSenderDaoServiceI.findAll(idList);
		} catch (Exception e) {
			logger.error("使能告警策略失败，告警策略为空！");
			throw new Exception("使能告警策略失败，告警策略为空！");
		}
		try {
			for(AlertSender as:asds){
				as.setEnable(enable);
			    alertSenderDaoServiceI.update(as);
			}
		} catch (Exception e) {
			logger.error("使能告警策略失败！");
			throw new Exception("使能告警策略失败！");
		}
	}

	@Override
	public void batchDeleteAlert(List<String> ids) throws Exception {
		List<AlertInfo> asds=null;
		List<Long> idList=new ArrayList<Long>();
		if(ids!=null && ids.size()>0){
			for(String id:ids){
				idList.add(Long.valueOf(id));
			}
		}
		try {
			asds = alertInfoDaoServiceI.findAll(idList);
		} catch (Exception e) {
			logger.error("批量删除告警失败，请尝试单个删除！");
			throw new Exception("批量删除告警失败，请尝试单个删除！");
		}
		try {
			alertInfoDaoServiceI.deleteInBatch(asds);
		} catch (Exception e) {
			logger.error("批量删除告警失败，请尝试单个删除！");
			throw new Exception("批量删除告警失败，请尝试单个删除！");
		}
	}

	@Override
	public void batchAquireAlert(List<String> ids) throws Exception {
		List<AlertInfo> asds=null;
		List<Long> idList=new ArrayList<Long>();
		if(ids!=null && ids.size()>0){
			for(String id:ids){
				idList.add(Long.valueOf(id));
			}
		}
		try {
			asds = alertInfoDaoServiceI.findAll(idList);
		} catch (Exception e) {
			logger.error("批量确认告警失败!");
			throw new Exception("批量确认告警失败!");
		}
		try {
			for(AlertInfo as:asds){
				try {
					 alertManagerUtils.aquireAlert(getOpUtil(), as.getResId(), as.getTriggerId());
				} catch (Exception e) {
					throw new Exception(e.getMessage());
				}
				as.setAquire(true);
				as.setAquireDate(new Date());
				as.setAquireUser("cloudview");
				alertInfoDaoServiceI.update(as);
			}
		} catch (Exception e) {
			logger.error("批量确认告警失败!");
			throw new Exception("批量确认告警失败!");
		}
	}
	
	 

}
