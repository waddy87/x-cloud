/**
 * 
 */
package org.waddys.xcloud.msg.service.service;

import org.waddys.xcloud.msg.service.bo.MessageInfo;
import org.waddys.xcloud.msg.service.exception.MessageException;

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
