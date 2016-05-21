package org.waddys.xcloud.messagepush.api.controller;
/*package org.waddys.xcloud.messagepush.api.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sugon.cloudview.cloudmanage.messagepush.service.bo.MessageInfo;
import com.sugon.cloudview.cloudmanage.messagepush.service.exception.MessageException;
import com.sugon.cloudview.cloudmanage.messagepush.service.service.MessageService;
import org.waddys.xcloud.messagepush.serviceImpl.serviceImpl.BaseMessageManageServiceImpl;
import org.waddys.xcloud.messagepush.serviceImpl.utils.MessageUtil;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "org.waddys.xcloud.**")
@EnableJpaRepositories("org.waddys.xcloud.**.repository")
@EntityScan("org.waddys.xcloud.**.entity")
@RequestMapping("/message")
public class MessageApplication {

    private static Class<MessageApplication> applicationClass = MessageApplication.class;

    // 消息发送接口调用
    @Autowired
    private BaseMessageManageServiceImpl baseMessageManageServiceImpl;

    @Autowired
    private MessageService messageServiceImpl;

    // 测试能否增加数据
    @RequestMapping("/add")
    public void addMessage() {

        MessageInfo messageInfo = new MessageInfo();

        messageInfo.setMailSubject("sugon email");
        messageInfo.setMailContent("sugon and vmware");
        messageInfo.setDescription("compare");
        messageInfo.setEventTime(MessageUtil.DateToString(new Date()));
        messageInfo.setEventState("未发送");

        try {
            baseMessageManageServiceImpl.addMessage(messageInfo);
        } catch (MessageException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @RequestMapping("/send")
    public void mailSend() {

        MessageInfo message = new MessageInfo();

        // SMTP服务器地址
        message.setSmtpServer("");
        // 此处设置登录的用户名
        message.setUsername("");
        // 此处设置登录的密码
        message.setPassword("");
        // 设置发送人地址
        message.setSender("");
        // 设置收件人的地址
        message.setReceiverAddr("");

        // 设置标题
        message.setMailSubject("测试邮件标题！");
        // 设置内容
        message.setMailContent(
                "2014年国电南自总经理黄源红年薪是51.8万元。2015年6月，国电南自总经理黄源红辞职。此后，国电南自迎来了新的总经理应光伟，根据年报，其2015年年薪为16.4万元，较前任下降了68.34%。2015年，国电南自净利润为3044.73万元，实现扭亏。");

        // ReceiveMessage receiveMessage1 = new ReceiveMessage();
        // ReceiveMessage receiveMessage2 = new ReceiveMessage();
        //
        // // MessageSend messageSend = new MessageSend();
        //
        // Thread thread1 = new Thread(receiveMessage1);
        // thread1.start();
        //
        // Thread thread2 = new Thread(receiveMessage2);
        // thread2.start();

        // Thread thread = new Thread(messageSend);
        // thread.start();

        try {
            messageServiceImpl.sendMessage(message);
        } catch (MessageException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(applicationClass, args);
    }
}
*/