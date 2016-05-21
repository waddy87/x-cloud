/**
 * 
 */
package org.waddys.xcloud.msg.service.service;

import org.waddys.xcloud.msg.service.bo.MessageInfo;
import org.waddys.xcloud.msg.service.exception.MessageException;

/**
 * @author 张浩然
 * @version 创建时间： 2016年3月24日
 */
public interface BaseMessageService {

    /**
     * 增加一条消息记录 如果此记录存在 那么覆盖原先的那一条记录
     * 
     * @param messageInfo
     * @return 返回一条消息对象
     * @throws MessageException
     */
    public MessageInfo updateMessage(MessageInfo messageInfo) throws MessageException;

    /**
     * 根据ID删除一条消息
     * 
     * @param id
     * @throws MessageException
     */
    public void deleteMessage(String id) throws MessageException;

    /**
     * 增加一条消息
     * 
     * @param messageInfo
     * @return 返回一条消息对象
     * @throws MessageException
     */
    public MessageInfo addMessage(MessageInfo messageInfo) throws MessageException;

    /**
     * 发送邮件信息
     * 
     * @param messageInfo
     * @throws MessageException
     */
    public void sendMessage(MessageInfo messageInfo) throws MessageException;

}
