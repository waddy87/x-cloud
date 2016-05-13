/**
 * 
 */
package com.sugon.cloudview.cloudmanager.messagepush.serviceImpl.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sugon.cloudview.cloudmanage.messagepush.service.bo.MessageInfo;
import com.sugon.cloudview.cloudmanager.messagepush.serviceImpl.dao.entity.MessageInfoE;

/**
 * @author 张浩然
 * @version 创建时间： 2016年3月24日
 */
public class MessageUtil {

    // 将entity类型转化成bo类型
    public static MessageInfo ChangeIntoMessageInfo(MessageInfoE messageInfoE) {

        MessageInfo messageInfo = new MessageInfo();

        messageInfo.setId(messageInfoE.getId());
        messageInfo.setMailSubject(messageInfoE.getMailSubject());
        messageInfo.setMailContent(messageInfoE.getMailContent());
        messageInfo.setSmsContent(messageInfoE.getSmsContent());
        messageInfo.setEventState(messageInfoE.getEventState());
        messageInfo.setEventTime(messageInfoE.getEventTime());
        messageInfo.setDescription(messageInfoE.getDescription());

        return messageInfo;
    }

    // bo类型与entity类型进行相互转化，最后返回一个entity类型
    public static MessageInfoE ChangeIntoMessageInfoE(MessageInfoE messageInfoE, MessageInfo messageInfo) {

        messageInfoE.setId(messageInfo.getId());
        messageInfoE.setMailSubject(messageInfo.getMailSubject());
        messageInfoE.setMailContent(messageInfo.getMailContent());
        messageInfoE.setEventState(messageInfo.getEventState());
        messageInfoE.setSmsContent(messageInfo.getSmsContent());
        messageInfoE.setDescription(messageInfo.getDescription());
        messageInfoE.setEventTime(messageInfo.getEventTime());

        return messageInfoE;

    }

    public static String DateToString(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

        String format = sdf.format(date);

        return format;
    }
}
