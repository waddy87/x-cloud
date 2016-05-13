/**
 * Created on 2016年4月13日
 */
package com.sugon.cloudview.cloudmanager.messagepush.serviceImpl.utils;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.mail.handlers.message_rfc822;

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
public class MessageSendWorker {

	@Autowired
	MessageSend message;
	
	  //自动启动发送线程
    @PostConstruct
    public void startThread(){
    	new Thread(message).start();
    }
    
}
