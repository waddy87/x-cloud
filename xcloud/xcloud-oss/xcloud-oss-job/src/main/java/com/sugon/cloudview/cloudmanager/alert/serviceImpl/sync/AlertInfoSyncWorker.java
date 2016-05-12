/**
 * Created on 2016年4月13日
 */
package com.sugon.cloudview.cloudmanager.alert.serviceImpl.sync;

import java.util.List;

import javax.annotation.PostConstruct;






import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*import com.sugon.cloudview.cloudmanage.messagepush.service.bo.MessageInfo;*/
import com.sugon.cloudview.cloudmanager.alert.serviceImpl.entity.AlertInfo;


/**
 * 功能名: 请填写功能名
 * 功能描述: 请简要描述功能的要点
 * Copyright: Copyright (c) 2016
 * 公司: 曙光云计算技术有限公司
 *
 * @author yangkun
 * @version 2.0.0 sp1
 */

@Service
public class AlertInfoSyncWorker {

	
	private static final Logger logger=LoggerFactory.getLogger(AlertInfoSync.class);
	
	@Autowired
	AlertInfoSync alertInfoSync;
	
	
	//@PostConstruct
	public void createSyncThread(){
		new Thread(alertInfoSync).start();
	}
}
