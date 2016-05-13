/**
 * 
 */
package com.sugon.cloudview.cloudmanage.messagepush.service.service;

import com.sugon.cloudview.cloudmanage.messagepush.service.bo.MessageInfo;
import com.sugon.cloudview.cloudmanage.messagepush.service.exception.MessageException;

/**
 * @author 张浩然
 * @version 创建时间： 2016年3月28日
 */
public interface BaseMessageQueryService {

    /**
     * 根据ID进行查找
     * 
     * @param id
     * @return 返回一个消息实体对象
     */
    public MessageInfo findById(String id) throws MessageException;

}
