/**
 * Created on 2016年3月30日
 */
package org.waddys.xcloud.alert.serviceImpl.sync;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.waddys.xcloud.alert.bo.AlarmEntity;
import org.waddys.xcloud.alert.bo.SystemResourceType;
import org.waddys.xcloud.alert.po.dao.service.AlertInfoDaoServiceI;
import org.waddys.xcloud.alert.po.dao.service.AlertSenderDaoServiceI;
import org.waddys.xcloud.alert.po.dao.service.AlertSyncTagDaoServiceI;
import org.waddys.xcloud.alert.po.entity.AlertInfo;
import org.waddys.xcloud.alert.po.entity.AlertSender;
import org.waddys.xcloud.alert.po.entity.AlertSyncTag;
import org.waddys.xcloud.alert.serviceImpl.service.utils.AlertMannagerUtils;
import org.waddys.xcloud.alert.serviceImpl.service.utils.Connection;
import org.waddys.xcloud.alert.serviceImpl.service.utils.VCenterManageUtils;
import org.waddys.xcloud.msg.service.bo.MessageInfo;
import org.waddys.xcloud.msg.service.exception.MessageException;
import org.waddys.xcloud.msg.service.service.MessageService;

/**
 * 功能名: 请填写功能名 功能描述: 请简要描述功能的要点 Copyright: Copyright (c) 2016 公司: 曙光云计算技术有限公司
 *
 * @author yangkun
 * @version 2.0.0 sp1
 */
@Service("alertInfoSync")
public class AlertInfoSync implements Runnable {

	@Autowired
	private AlertMannagerUtils alertManagerUtils;

	@Qualifier("alert-connection")
	@Autowired
	private Connection connection;

	@Autowired
	private AlertSyncTagDaoServiceI alertSyncTagDaoServiceI;

	@Autowired
	private AlertInfoDaoServiceI alertInfoDaoServiceI;

	@Autowired
	private AlertSenderDaoServiceI alertSenderDaoServiceI;

	@Autowired
	private MessageService messageService;

	@Autowired
	private VCenterManageUtils opUtil;

	@Value("${messagePush.smtp}")
	private String smtpAddr;

	@Value("${messagePush.username}")
	private String userName;

	@Value("${messagePush.authcode}")
	private String authCode;

	@Value("${messagePush.sendmail}")
	private String sendMail;

	private static final Logger logger = LoggerFactory
			.getLogger(AlertInfoSync.class);

	/*
	 * 定时获取最新的告警数据(10s定时)
	 */

	public List<AlertInfo> getAlertInfoLatestAll()
			throws ParseException {

		// 添加si获取为空的处理逻辑，避免空指针
		if (connection.getSi() == null) {
			return null;
		}
		List<AlarmEntity> aList = alertManagerUtils.getTriggeredAlarms(
				getOpUtil(), connection.getSi().getRootFolder());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (aList != null && !aList.isEmpty()) {
			List<AlertInfo> resList = new ArrayList<AlertInfo>();
			for (AlarmEntity alEntity : aList) {
				Long alertDate = alEntity.getTime();
			/*	// 告警时间超过上轮最近的告警时间，证明是新告警
				if (alertDate > latestDate) {*/
					AlertInfo alInfo = new AlertInfo();
					// 装填alInfo对应的alert相关的12个参数
					alInfo.setAlertId(alEntity.getId());
					alInfo.setAlertName(alEntity.getName());
					alInfo.setResId(alEntity.getEntityId());
					alInfo.setResName(alEntity.getEntityName());
					alInfo.setResType(convertToResTypeById(alEntity
							.getEntityId()));
					alInfo.setTriggerId(alEntity.getId());
					alInfo.setTriggerDetail(alEntity.getTriggerDetail());
					alInfo.setAlertLevel(alEntity.getLevel());
					alInfo.setAlertTime(new Date(alertDate));
					alInfo.setAquire(alEntity.isAcknowledged());
					alInfo.setAquireDate(sdf.parse(alEntity
							.getAcknowledgedTime()));
					alInfo.setAquireUser(alEntity.getAcknowledgedUser());
					alInfo.setDescription(alEntity.getDescription());
					resList.add(alInfo);
			/*	}*/

			}
			return resList;
		}

		return null;

	}

	/*
	 * 根据实体id 转换为对应的资源类型（如vm-12，host-113）
	 */
	public String convertToResTypeById(String entityId) {
		if (entityId.contains("host")) {
			return SystemResourceType.hostSystem;
		} else if (entityId.contains("vm")) {
			return SystemResourceType.virtualMachine;
		} else if (entityId.contains("datastore")) {
			return SystemResourceType.dataStore;
		} else {
			return SystemResourceType.root;
		}
	}

	/*
	 * 检查alertSender中是否已经预定义的告警触发器
	 * 
	 * @resType 全部/物理机 (all的时候所有都进行发送)
	 * 
	 * @resId
	 */
	public List<AlertSender> checkAlertSender(String resType, String resId,
			String alarmId, String alertLevel) {

		// 资源类型为all，返回全部发送器
		List<AlertSender> retSendList = new ArrayList<AlertSender>();
		try {
			if (alertSenderDaoServiceI.countByResType(SystemResourceType.all) > 0L) {
				retSendList = alertSenderDaoServiceI
						.findByResType(SystemResourceType.all);
				// 如果某个资源类型为all，则返回该资源类型的所有发送器
			} else if (alertSenderDaoServiceI.countByResTypeAndResId(resType,
					SystemResourceType.all) > 0L) {
				retSendList = alertSenderDaoServiceI.findByResTypeAndResId(
						resType, SystemResourceType.all);
				// 如果某个资源的告警项为all,则返回该资源全部的发送器
			} else if (alertSenderDaoServiceI
					.countByResTypeAndResIdAndTriggerId(resType, resId,
							SystemResourceType.all) > 0L) {
				retSendList = alertSenderDaoServiceI
						.findByResTypeAndResIdAndTriggerId(resType, resId,
								SystemResourceType.all);
				// 如果为普通选项，则根据type、resId、alarmId，返回匹配的发送器即可
			} else if (alertSenderDaoServiceI
					.countByResTypeAndResIdAndTriggerId(resType, resId, alarmId) > 0L) {
				retSendList = alertSenderDaoServiceI
						.findByResTypeAndResIdAndTriggerId(resType, resId,
								alarmId);
			}

			// 针对以上查询到的发送器，再在alertSendLevel水平进行过滤
			if (retSendList != null && !retSendList.isEmpty()) {
				for (AlertSender alertSender : retSendList) {
					// 如果包含all,则需要发送，如果匹配级别则发送，否则从列表删除
					String aletSendLevel = alertSender.getAlertSendLevel();
					if (!aletSendLevel.contains("all")
							&& !aletSendLevel.contains(alertLevel)) {
						retSendList.remove(alertSender);
					}
				}
			}

			// 针对以上查询到的发送器，再在isEnable维度进行过滤
			if (retSendList != null && !retSendList.isEmpty()) {
				for (AlertSender alertSender : retSendList) {
					// 如果状态为未使能状态，则从列表删除
					if (alertSender.isEnable() == false) {
						retSendList.remove(alertSender);
					}
				}
			}

		} catch (Exception e) {
			// 吃掉异常
		}
		return retSendList;
	}

	/*
	 * 发送邮件接口
	 */
	public void sendEmail(AlertInfo alertInfo,List<AlertSender> userSendList ) {
		for (AlertSender userSend : userSendList) {
			MessageInfo mesInfo = new MessageInfo();
			mesInfo.setSmtpServer(smtpAddr);
			mesInfo.setUsername(userName);
			mesInfo.setPassword(authCode);
			mesInfo.setSender(sendMail);
			mesInfo.setReceiverAddr(userSend
					.getReceiver());
			mesInfo.setMailSubject(alertInfo
					.getResName() + "告警通知");
			mesInfo.setMailContent("资源："
					+ alertInfo.getResName() + "\n"
					+ "告警id："
					+ alertInfo.getTriggerId() + "\n"
					+ "告警描述："
					+ alertInfo.getDescription() + "\n"
					+ "告警时间："
					+ alertInfo.getAlertTime() + "\n");
			try {
				messageService.sendMessage(mesInfo);
			} catch (MessageException e) {
				logger.error( "资源" +alertInfo.getResName() +"发送告警"+ alertInfo.getTriggerId()  + "失败！\n");
			}
		}
	}

	/*
	 * 保存告警信息
	 */
	public void saveAlertInfo(AlertInfo oldAlertInfo,AlertInfo alertInfo ,Long latestAlertTime) {
		Long alertInfoIdLong = alertInfoDaoServiceI.findIdByResIdAndAlertId(alertInfo.getResId(),alertInfo.getAlertId());
		if (alertInfoIdLong != null) {
			oldAlertInfo = alertInfoDaoServiceI.findById(alertInfoIdLong);
		
			// 同步旧记录的id，对于旧记录也需要同步一下，方便后续过滤历史告警使用
			alertInfo.setId(alertInfoIdLong);
			if(alertInfo.getAlertTime().getTime()<latestAlertTime){
				return ;
			}
			//同步邮件发送状态
			alertInfo.setSendTime(oldAlertInfo.getSendTime());
			alertInfo.setSenderStatus(oldAlertInfo.getSenderStatus());	
			//标志为实时告警
			oldAlertInfo.setHistory(false);
			alertInfo.setHistory(false);
			//1小时内同步旧记录的告警确认状态(是否确认，确认用户，确认时间)
			if (oldAlertInfo != null
					&& oldAlertInfo.getAquireDate() != null ) {
				long now=(new Date()).getTime();
				long old=oldAlertInfo.getAquireDate().getTime() ;
				long diifTime=now-old;
				//如果是告警同步线程自己确认的，则不同步确认状态
				if(oldAlertInfo.getAquireUser()!=null && oldAlertInfo.getAquireUser().equals("AlertSyncThread")){
					//continue;
				}else{
					if (diifTime < 1 * 60 * 60 * 1000L ) {
						alertInfo.setAquire(oldAlertInfo.isAquire());
						alertInfo.setAquireDate(oldAlertInfo.getAquireDate());
						alertInfo.setAquireUser(oldAlertInfo.getAquireUser());
						logger.debug("同步告警确认状态，时间差（s）" + diifTime/1000L);
					}
				}
			}
			alertInfoDaoServiceI.update(alertInfo); // 需要级联删除对应的邮件发送状态记录
			logger.debug("更新告警" + alertInfoIdLong + "\t"
					+ oldAlertInfo.getResId() + "\n");
		} else {
			// 如果是新告警，则插入即可，并返回新记录
			//标志为实时告警
			alertInfo.setHistory(false);
			oldAlertInfo = alertInfoDaoServiceI
					.save(alertInfo);
			alertInfo.setId(oldAlertInfo.getId());
			oldAlertInfo.setHistory(false);
			logger.debug("增加告警" + oldAlertInfo.getId()
					+ "\t" + oldAlertInfo.getResId() + "\n");
		}
	}

	public VCenterManageUtils getOpUtil() {
	/*	if (this.opUtil == null) {
			this.opUtil = new VCenterManageUtils();
		}*/
		return this.opUtil;
	}

	public Long getLatestAlertTime() {
		return alertSyncTagDaoServiceI.findAll().get(0).getLatestAlertTime();
	}

	public boolean saveLatestAlertTime(Long time) {

		AlertSyncTag ast = new AlertSyncTag();
		ast.setId(1L);
		ast.setLatestAlertTime(time);
		try {
			alertSyncTagDaoServiceI.update(ast);
		} catch (Exception e) {
			logger.debug("保存最新告警时间异常" + e);
			return false;
		}
		return true;
	}

	/*
	 * 通过告警触发信息
	 */
	@Override
	public void run() {

		try {
			while (true) {
				// 1.获取latestAlertTime
				Long latestAlertTime = getLatestAlertTime();
				// 2.获取最新的告警时间
				List<AlertInfo> aInfos = getAlertInfoLatestAll();
				// 3.同步更新告警列表信息
				if (aInfos != null && !aInfos.isEmpty()) {
					loop : 	for (AlertInfo alertInfo : aInfos) {
							AlertInfo oldAlertInfo = new AlertInfo();
							try {
								//4.只对最新的告警进行数据的更新(比上轮时间新的告警)
								// 5.先存放告警到数据库，再对告警进行邮件发送，邮件发送通过告警模块进行结果回填
								// 如果告警存在，则直接更新即可（alarm-14，host-9）
								saveAlertInfo( oldAlertInfo, alertInfo,latestAlertTime );
							} catch (Exception e1) {
							}
							try {
								// 6.判断该条告警记录是否需要发送，默认12小时发送一次相同的告警信息
								if (oldAlertInfo != null
										&& oldAlertInfo.getSendTime() != null) {
									long now=(new Date()).getTime();
									long old=oldAlertInfo.getSendTime().getTime() ;
									long diifTime=now-old;
									logger.debug("两者相差时间（s）" + diifTime/1000L);
									if (diifTime < 12 * 60 * 60 * 1000L) {
										continue loop;
									}
								}
							} catch (Exception e1) {
							}
	
							try {
								// 7.更新告警发送时间和状态
								alertInfo.setSenderStatus(1);
								alertInfo.setSendTime(new Date());
								alertInfoDaoServiceI.update(alertInfo); // 需要级联删除对应的邮件发送状态记录
							} catch (Exception e) {
	
							}
	
							// 8.根据alertId、resId判断是否在alertSender中定义了发送器，如果定义了，则进行邮件发送。
							List<AlertSender> userSendList = checkAlertSender(
									alertInfo.getResType(), alertInfo.getResId(),
									alertInfo.getAlertId(),
									alertInfo.getAlertLevel());
								if (userSendList != null && !userSendList.isEmpty()
										&& userSendList.size() > 0) {
									sendEmail(alertInfo,userSendList);
								}
			
						try {
							// 8.更新最新的告警时间
							if(alertInfoDaoServiceI.findById(aInfos.get(0).getId())!=null){
								saveLatestAlertTime(aInfos.get(0).getAlertTime().getTime());
							}
						} catch (Exception e) {
						}
						
					}
					
					//9.同步告警状态,对于后台已经取消了的告警，设置状态为历史告警
					List<Long>ids=new ArrayList<Long>();
					for (AlertInfo alertInfo : aInfos) {
						ids.add(alertInfo.getId());
					}
					 try {
						List<AlertInfo> asyncAlertInfos=	alertInfoDaoServiceI.findByIdNotIn(ids);
							for (AlertInfo alertInfo :  asyncAlertInfos) {
								    alertInfo.setHistory(true);
									alertInfoDaoServiceI.update(alertInfo);
							}
					} catch (Exception e) {
					}
				}
				Thread.sleep(10000);
			}
		} catch (Exception e) {
			logger.error("启动告警同步线程失败" + e);
		}

	}

}
