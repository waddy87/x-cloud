/**
 * 
 */
package com.sugon.cloudview.cloudmanager.messagepush.serviceImpl.serviceImpl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sugon.cloudview.cloudmanage.messagepush.service.bo.MessageInfo;
import com.sugon.cloudview.cloudmanage.messagepush.service.exception.MessageException;
import com.sugon.cloudview.cloudmanage.messagepush.service.service.BaseMessageService;
import com.sugon.cloudview.cloudmanager.messagepush.serviceImpl.dao.entity.MessageInfoE;
import com.sugon.cloudview.cloudmanager.messagepush.serviceImpl.dao.service.MessageDaoService;
import com.sugon.cloudview.cloudmanager.messagepush.serviceImpl.utils.MessageQueue;
import com.sugon.cloudview.cloudmanager.messagepush.serviceImpl.utils.MessageSend;
import com.sugon.cloudview.cloudmanager.messagepush.serviceImpl.utils.MessageUtil;

/**
 * @author 张浩然
 * @version 创建时间： 2016年3月24日
 */
@Service("baseMessageManageServiceImpl")
public class BaseMessageManageServiceImpl implements BaseMessageService {

    private static Logger logger = LoggerFactory.getLogger(BaseMessageManageServiceImpl.class);

    @Autowired
    private MessageDaoService messageDaoService;

    @Autowired
    private MessageSend messageSend;

    /**
     * 增加一条消息信息
     */
    @Override
    public MessageInfo addMessage(MessageInfo messageInfo) throws MessageException {

        MessageInfoE messageInfoE = new MessageInfoE();

        messageInfoE.setMailSubject(messageInfo.getMailSubject());
        messageInfoE.setMailContent(messageInfo.getMailContent());
        messageInfoE.setEventTime(messageInfo.getEventTime());
        messageInfoE.setEventState(messageInfo.getEventState());
        messageInfoE.setDescription(messageInfo.getDescription());

        MessageInfoE addMessage = messageDaoService.save(messageInfoE);

        MessageInfo changeIntoMessageInfo = MessageUtil.ChangeIntoMessageInfo(addMessage);

        System.out.println("增加消息成功！");
        logger.debug("增加消息成功！");

        return changeIntoMessageInfo;
    }

    /**
     * 更改消息
     */
    @Override
    public MessageInfo updateMessage(MessageInfo messageInfo) throws MessageException {

        // 根据当前ID进行查找，并返回entity类型
        MessageInfoE messageInfoE = messageDaoService.findById(messageInfo.getId());

        messageInfoE = MessageUtil.ChangeIntoMessageInfoE(messageInfoE, messageInfo);
        // 将信息重新保存到数据库
        MessageInfoE saveMessage = messageDaoService.save(messageInfoE);

        MessageInfo result = MessageUtil.ChangeIntoMessageInfo(saveMessage);

        logger.debug("更新消息成功！");

        return result;
    }

    @Override
    public void deleteMessage(String id) throws MessageException {
        // TODO Auto-generated method stub
    }

    /**
     * 发送邮件信息 需要对消息的实体进行设置 其中必须设置的为:<br>
     * 1.SMTP发送服务器<br>
     * 2.登录的用户名<br>
     * 3.登录的密码<br>
     * 4.发送人地址<br>
     * 5.收件人的地址<br>
     * 6.邮件标题<br>
     * 7.邮件内容<br>
     */
    @Override
    public void sendMessage(MessageInfo messageInfo) throws MessageException {

        messageInfo.setEventState("未发送");

        // 格式化日期时间
        messageInfo.setEventTime(MessageUtil.DateToString(new Date()));

        MessageInfo addMessage = addMessage(messageInfo);

        messageInfo.setId(addMessage.getId());

        MessageQueue.getInstance().push(messageInfo);

        logger.debug("发送邮件成功！");

    }
}
