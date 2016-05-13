/**
 * 
 */
package com.sugon.cloudview.cloudmanager.messagepush.serviceImpl.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sugon.cloudview.cloudmanage.messagepush.service.bo.MessageInfo;

/**
 * @author 张浩然
 * @version 创建时间： 2016年3月24日
 */
public class MessageQueue {

    private static Logger logger = LoggerFactory.getLogger(MessageQueue.class);

    // 队列大小
    private static final int QUEUE_MAX_SIZE = 3000;
    private static MessageQueue messageQueue = new MessageQueue();

    // 阻塞队列
    private BlockingQueue<MessageInfo> blockingQueue = new LinkedBlockingQueue<MessageInfo>(QUEUE_MAX_SIZE);

    // 获取消息实例
    public static MessageQueue getInstance() {
        return messageQueue;
    }

    /**
     * 消息入队<br>
     * 把anObject加到BlockingQueue里,如果BlockQueue没有空间,则调用此方法的线程被阻断
     * 直到BlockingQueue里面有空间再继续
     * 
     * @param messageInfo
     */
    public void push(MessageInfo messageInfo) {
        try {
            this.blockingQueue.put(messageInfo);

            logger.debug("消息进入内存队列");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 消息出队 <br>
     * 取走BlockingQueue里排在首位的对象,若BlockingQueue为空,阻断进入等待状态直到
     * BlockingQueue有新的数据被加入;
     * 
     * @return
     */
    public MessageInfo poll() {

        MessageInfo result = null;

        try {
            result = this.blockingQueue.take();

            logger.debug("从内存队列取走消息");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 获取队列大小
     * 
     * @return
     */
    public int size() {
        return blockingQueue.size();
    }
}
