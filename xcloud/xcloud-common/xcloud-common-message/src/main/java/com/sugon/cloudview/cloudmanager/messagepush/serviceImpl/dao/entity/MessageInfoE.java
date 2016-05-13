/**
 * 
 */
package com.sugon.cloudview.cloudmanager.messagepush.serviceImpl.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sugon.cloudview.dboperation.EntityBase;

/**
 * @author 张浩然
 * @version 创建时间： 2016年3月23日
 */

@Entity
@Table(name = "message_info")
public class MessageInfoE extends EntityBase {

    private static final long serialVersionUID = 8663979619206572489L;

    private String id;
    private String mailSubject;
    private String mailContent;
    private String smsContent;
    private String eventTime;
    private String eventState;
    private String description;

    /**
     * @return the id
     */
    @Id
    @GenericGenerator(name = "message_info-uuid", strategy = "uuid")
    @GeneratedValue(generator = "message_info-uuid")
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
     * @return the mailSubject
     */
    @Column(name = "mail_subject", length = 200, nullable = true)
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
    @Column(name = "mail_content", nullable = true)
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
    @Column(name = "sms_content", nullable = true)
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
    @Column(name = "event_time", nullable = true)
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
    @Column(name = "event_state", length = 10, nullable = true)
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


    /**
     * @return the description
     */
    @Column(name = "description", length = 200, nullable = true)
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
}
