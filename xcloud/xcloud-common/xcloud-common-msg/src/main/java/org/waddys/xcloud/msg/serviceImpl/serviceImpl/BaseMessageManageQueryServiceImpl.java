/**
 * 
 */
package org.waddys.xcloud.msg.serviceImpl.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.waddys.xcloud.msg.service.bo.MessageInfo;
import org.waddys.xcloud.msg.service.exception.MessageException;
import org.waddys.xcloud.msg.service.service.BaseMessageQueryService;
import org.waddys.xcloud.msg.serviceImpl.dao.entity.MessageInfoE;
import org.waddys.xcloud.msg.serviceImpl.dao.service.MessageDaoService;
import org.waddys.xcloud.msg.serviceImpl.utils.MessageUtil;

/**
 * @author 张浩然
 * @version 创建时间： 2016年3月28日
 */
@Service("baseMessageManageQueryServiceImpl")
public class BaseMessageManageQueryServiceImpl implements BaseMessageQueryService {

    private static Logger logger = LoggerFactory.getLogger(BaseMessageManageQueryServiceImpl.class);

    @Autowired
    private MessageDaoService messageDaoService;

    /**
     * 根据ID进行查找
     */
    @Override
    public MessageInfo findById(String id) throws MessageException {

        MessageInfoE messageInfoE = messageDaoService.findById(id);

        MessageInfo messageInfo = MessageUtil.ChangeIntoMessageInfo(messageInfoE);

        logger.debug("根据ID查询信息列表");

        return messageInfo;
    }

    /**
     * 将page转化成list
     * 
     * @param page
     * @param pre_page
     * @return
     */
    public List<MessageInfo> ListMessage(int page, int pre_page) {

        Page<MessageInfoE> pageList;
        pageList = this.messageDaoService.findAll(new PageRequest(page - 1, pre_page));

        logger.debug("返回列表成功");

        return pageToList(pageList);
    }

    public List<MessageInfo> ListMailObjectMessage(String param, int page, int pre_page) {

        Page<MessageInfoE> pageList;
        pageList = this.messageDaoService.findByMailSubject(param, new PageRequest(page - 1, pre_page));

        logger.debug("返回列表成功");

        return pageToList(pageList);
    }

    /**
     * 将page转换成list 来进行分页查询
     * 
     * @param pageList
     * @return 返回一个list的集合
     */
    public List<MessageInfo> pageToList(Page<MessageInfoE> pageList) {

        List<MessageInfo> list = new ArrayList<MessageInfo>();

        for (MessageInfoE messageInfoE : pageList) {

            list.add(MessageUtil.ChangeIntoMessageInfo(messageInfoE));
        }

        return list;
    }

    /**
     * 统计数据空中的数据
     * 
     * @return
     */
    public int countAll() {

        int countAll = this.messageDaoService.countAll();
        return countAll;
    }

}
