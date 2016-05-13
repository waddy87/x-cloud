/**
 * 
 */
package com.sugon.cloudview.cloudmanager.messagepush.serviceImpl.utils;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sugon.cloudview.cloudmanage.messagepush.service.bo.MessageInfo;
import com.sugon.cloudview.cloudmanage.messagepush.service.exception.MessageException;
import com.sugon.cloudview.cloudmanage.messagepush.service.service.MessageService;

/**
 * @author 张浩然
 * @version 创建时间： 2016年3月24日
 */
@Service("messageSend")
public class MessageSend implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(MessageSend.class);

    @Autowired
    private MessageService messageServiceImpl;    
    
    /**
     * 接收消息 并发送<br>
     * 消费者
     */
    @Override
    public void run() {

        MailUtil mail = new MailUtil();

        while (true) {
            // 从队列中取出消息对象
            MessageInfo messageInfo = MessageQueue.getInstance().poll();

            //消息为空的时候，不做处理
            if(messageInfo!=null){
	            try {
	            	
	            	//发送邮件间隔不能太过频繁，否则会被163等第三方公司屏蔽，这里设定为10s
	            	Thread.sleep(10000);
	            	
	            	
	                // 如果邮件发送成功，将数据库中的状态修改为已发送 值为 1
	                if (mail.send(messageInfo)) {
	
	                    // 修改发送消息的状态值
	                    messageInfo.setEventState("已发送");
	
	                    // 更新并保存当前记录
	                    messageServiceImpl.updateMessage(messageInfo);
	
	                    System.out.println("发送邮件消息" + messageInfo + "成功");
	                    logger.debug("发送消息成功");
	
	                } else {
	                    // 如果发送不成功，改变状态
	                    messageInfo.setEventState("发送失败");
	                    messageServiceImpl.updateMessage(messageInfo);
	                    logger.debug("发送消息失败！");
	                }
	
	            } catch (Exception e) {
	            	logger.error("发送邮件失败！"+e);
	            }
            }
            
            try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				logger.error("线程休眠失败"+e);
			}
        }
    }
}
