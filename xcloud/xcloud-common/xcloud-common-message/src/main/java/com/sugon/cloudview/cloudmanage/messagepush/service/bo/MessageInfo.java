/**
 * 
 */
package com.sugon.cloudview.cloudmanage.messagepush.service.bo;

/**
 * @author 张浩然
 * @version 创建时间： 2016年3月23日
 */
public class MessageInfo {

    private String id;
    // 邮件主题
    private String mailSubject;
    // 邮件正文
    private String mailContent;
    // 短信内容
    private String smsContent;
    // 当前系统时间
    private String eventTime;
    // 状态
    private String eventState;
    // 备注
    private String description;

    // SMTP服务器地址
    private String smtpServer;
    // 发件人邮箱地址
    private String sender;
    // 收件人邮箱地址
    private String receiverAddr;
    // 登录SMTP服务器的用户名
    private String username;
    // 登录SMTP服务器的密码
    private String password;


    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the smtpServer
     */
    public String getSmtpServer() {
        return smtpServer;
    }

    /**
     * @param smtpServer
     *            the smtpServer to set
     */
    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender
     *            the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * @return the receiverAddr
     */
    public String getReceiverAddr() {
        return receiverAddr;
    }

    /**
     * @param receiverAddr
     *            the receiverAddr to set
     */
    public void setReceiverAddr(String receiverAddr) {
        this.receiverAddr = receiverAddr;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the mailSubject
     */
    public String getMailSubject() {
        return mailSubject;
    }

    /**
     * @param mailSubject
     *            the mailSubject to set
     */
    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    /**
     * @return the mailContent
     */
    public String getMailContent() {
        return mailContent;
    }

    /**
     * @param mailContent
     *            the mailContent to set
     */
    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    /**
     * @return the smsContent
     */
    public String getSmsContent() {
        return smsContent;
    }

    /**
     * @param smsContent
     *            the smsContent to set
     */
    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    /**
     * @return the eventTime
     */
    public String getEventTime() {
        return eventTime;
    }

    /**
     * @param eventTime
     *            the eventTime to set
     */
    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    /**
     * @return the eventState
     */
    public String getEventState() {
        return eventState;
    }

    /**
     * @param eventState
     *            the eventState to set
     */
    public void setEventState(String eventState) {
        this.eventState = eventState;
    }

}
