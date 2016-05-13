/**
 * 
 *//*
package com.sugon.cloudview.cloudmanager.messagepush.serviceImpl.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.sugon.cloudview.cloudmanage.messagepush.service.bo.MessageInfo;
import com.sugon.cloudview.cloudmanage.messagepush.service.exception.MessageException;
import com.sugon.cloudview.cloudmanager.messagepush.serviceImpl.serviceImpl.BaseMessageManageServiceImpl;

*//**
 * @author 张浩然
 * @version 创建时间： 2016年3月24日
 *//*
public class ReceiveMessage implements Runnable {


    @Autowired
    private BaseMessageManageServiceImpl baseMessageManageServiceImpl;
    *//**
     * 生产者
     *//*
    @Override
    public void run() {

        MessageInfo message = new MessageInfo();

        message.setSmtpServer("smtp.126.com");
        // 此处设置登录的用户名
        message.setUsername("tj_zhr");
        // 此处设置登录的密码
        message.setPassword("zhanghaoran625");
        // 设置收件人的地址
        message.setReceiverAddr("zhanghaoran_mm@163.com");
        // 设置发送人地址
        message.setSender("tj_zhr@126.com");
        // 设置标题
        message.setMailSubject("测试邮件标题！");
        // 设置内容
        message.setMailContent(
                "北京时间3月28日，根据意大利媒体《milanista》的独家报道，意大利著名球星马里奥-巴洛特利有望在今夏登陆中超联赛，对他有意的俱乐部正是目前中超的领头羊江苏苏宁。据了解，苏宁愿意为巴洛特利开出一份年薪1500万欧元的优厚合同。该媒体指出，巴洛特利对这份合同非常满意。本赛季，由于饱受伤病困扰，巴洛特利仅仅为米兰出场16次打入3球，但这并不妨碍他继续成为中超转会市场上的红人。上周，巴洛特利为米兰完成了梅开二度的好戏，于是立刻他就获得了苏宁的青睐。复活节后，苏宁的谈判代表现身米兰城一家知名的酒店，而巴洛特利的经纪人恰好也在此时进入该酒店。据媒体披露，双方的谈判非常顺利，苏宁开出了1500万欧元的年薪，让巴洛特利方面感到“无法拒绝。");

        message.setEventState("0");

        message.setEventTime(MessageUtil.DateToString(new Date()));

        // MessageQueue.getInstance().push(message);

        try {
            baseMessageManageServiceImpl.sendMessage(message);
        } catch (MessageException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
*/