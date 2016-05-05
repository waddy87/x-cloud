/**
 * 
 *//*
package com.sugon.cloudview.cloudmanager.messagepush.serviceImpl.init;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sugon.cloudview.cloudmanage.messagepush.service.bo.MessageInfo;
import com.sugon.cloudview.cloudmanager.messagepush.serviceImpl.dao.entity.MessageInfoE;
import com.sugon.cloudview.cloudmanager.messagepush.serviceImpl.dao.service.MessageDaoService;
import com.sugon.cloudview.cloudmanager.messagepush.serviceImpl.utils.MessageQueue;
import com.sugon.cloudview.cloudmanager.messagepush.serviceImpl.utils.MessageUtil;

*//**
 * @author 张浩然
 * @version 创建时间： 2016年3月15日
 *//*
@Service("dataBaseInitializer")
@Transactional
public class DataBaseInitializer {

    @Autowired
    private MessageDaoService messageDaoService;

    @PostConstruct
    public void initDataBase() {

        // 初始化查找是否有未发送的信息，如果有，将信息重新加入到队列。
        if (messageDaoService.findByEventState("未发送").size() != 0) {
            // 查询未发送的信息，返回list集合。
            List<MessageInfoE> findByEvent_state = messageDaoService.findByEventState("未发送");

            for (int i = 0; i < findByEvent_state.size(); i++) {

                MessageInfoE messageInfoE = findByEvent_state.get(i);

                MessageInfo message = MessageUtil.ChangeIntoMessageInfo(messageInfoE);
                // SMTP发送服务器的名字
                message.setSmtpServer("smtp.126.com");
                // 此处设置登录的用户名
                message.setUsername("tj_zhr");
                // 此处设置登录的密码
                message.setPassword("zhanghaoran625");
                // 设置发送人地址
                message.setSender("tj_zhr@126.com");
                // 设置收件人的地址
                message.setReceiverAddr("zhanghaoran_mm@163.com");

                MessageQueue.getInstance().push(message);

            }
        }
    }

}
*/