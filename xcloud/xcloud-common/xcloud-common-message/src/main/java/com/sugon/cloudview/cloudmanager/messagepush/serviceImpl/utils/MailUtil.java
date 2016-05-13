/**
 * 
 */
package com.sugon.cloudview.cloudmanager.messagepush.serviceImpl.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.springframework.beans.factory.annotation.Autowired;

import com.sugon.cloudview.cloudmanage.messagepush.service.bo.MessageInfo;

/**
 * @author 张浩然
 * @version 创建时间： 2016年3月25日
 */
public class MailUtil {

    @Autowired
    // private ConnectionAddr connectionAddr;

    // 记录所有附件文件的集合
    List<String> attachments = new ArrayList<String>();

    // 把邮件主题转换为中文
    public String transferChinese(String strText) {
        try {
            strText = MimeUtility.encodeText(new String(strText.getBytes(), "UTF-8"), "UTF-8", "B");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strText;
    }

    // 增加附件，将附件文件名添加的List集合中
    public void attachfile(String fname) {
        attachments.add(fname);
    }

    public boolean send(MessageInfo messageInfo) {

        // 创建邮件Session所需的Properties对象
        Properties props = new Properties();
        props.put("mail.smtp.host", messageInfo.getSmtpServer());
        props.put("mail.smtp.auth", "true");

        // 创建Session对象
        Session session = Session.getDefaultInstance(props
                // 以匿名内部类的形式创建登录服务器的认证对象
                , new Authenticator() {
                    @Override
                    public PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(messageInfo.getUsername(), messageInfo.getPassword());
                    }
                });
        try {
            // 构造MimeMessage并设置相关属性值
            MimeMessage msg = new MimeMessage(session);
            // 设置发件人
            if(messageInfo.getSender()==null || messageInfo.getSender().isEmpty()){
            	return false;
            }
            msg.setFrom(new InternetAddress(messageInfo.getSender()));
            // 设置收件人
            if(messageInfo.getReceiverAddr()==null || messageInfo.getReceiverAddr().isEmpty()){
            	return false;
            }
            InternetAddress[] addresses = { new InternetAddress(messageInfo.getReceiverAddr()) };
            msg.setRecipients(Message.RecipientType.TO, addresses);
            // 设置邮件主题
            String subject = transferChinese(messageInfo.getMailSubject());
            msg.setSubject(subject);
            // 构造Multipart
            Multipart mp = new MimeMultipart();
            // 向Multipart添加正文
            MimeBodyPart mbpContent = new MimeBodyPart();
            mbpContent.setText(messageInfo.getMailContent(),"utf-8");
            // 将BodyPart添加到MultiPart中
            mp.addBodyPart(mbpContent);
            // 向Multipart添加附件
            // 遍历附件列表，将所有文件添加到邮件消息里
            for (String efile : attachments) {
                MimeBodyPart mbpFile = new MimeBodyPart();
                // 以文件名创建FileDataSource对象
                FileDataSource fds = new FileDataSource(efile);
                // 处理附件
                mbpFile.setDataHandler(new DataHandler(fds));
                mbpFile.setFileName(fds.getName());
                // 将BodyPart添加到MultiPart中
                mp.addBodyPart(mbpFile);
            }
            // 清空附件列表
            attachments.clear();
            // 向Multipart添加MimeMessage
            msg.setContent(mp);
            // 设置发送日期
            msg.setSentDate(new Date());
            // 发送邮件
            Transport.send(msg);
        } catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }
        return true;
    }
}
