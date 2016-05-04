package com.sugon.cloudview.cloudmanager.messagepush.serviceImpl.dao.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sugon.cloudview.cloudmanager.messagepush.serviceImpl.dao.entity.MessageInfoE;
import com.sugon.cloudview.cloudmanager.messagepush.serviceImpl.dao.repository.MessageRepository;
import com.sugon.cloudview.cloudmanager.messagepush.serviceImpl.dao.service.MessageDaoService;

/**
 * @author 张浩然
 * @version 创建时间： 2016年3月24日
 */
@Service("messageService")
@Transactional
public class MessageServiceImpl implements MessageDaoService {

    @Autowired
    private final MessageRepository messageRepository;

    /**
     * 构造函数
     * 
     * @param messageRepository
     */
    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * 新增一个消息实体
     * 
     * @param messageInfoE
     * @return 返回一个消息对象
     */
    @Override
    public MessageInfoE save(MessageInfoE messageInfoE) {
        // TODO Auto-generated method stub
        return messageRepository.save(messageInfoE);
    }

    /**
     * 根据 ID 进行删除
     * 
     * @param id
     */
    @Override
    public void deleteById(String id) {
        // TODO Auto-generated method stub
        this.messageRepository.deleteById(id);
    }

    /**
     * 根据ID进行查找
     */
    @Override
    public MessageInfoE findById(String id) {
        // TODO Auto-generated method stub
        return this.messageRepository.findById(id);
    }

    /**
     * 根据状态进行查询
     */
    @Override
    public List<MessageInfoE> findByEventState(String state) {
        // TODO Auto-generated method stub
        return this.messageRepository.findByEventState(state);
    }

    /**
    * 根据ID进行分页查询
    */
    @Override
    public Page<MessageInfoE> findAll(Pageable pageable) {
        // TODO Auto-generated method stub
        return this.messageRepository.findAll(pageable);
    }

    /**
     * 查询数据库中的所有记录 并做统计
     */
    @Override
    public int countAll() {
        // TODO Auto-generated method stub
        return this.messageRepository.countAll();
    }

    /**
     * 根据条件分页查询
     */
    @Override
    public Page<MessageInfoE> findByMailSubject(String param, Pageable pageable) {
        // TODO Auto-generated method stub
        return this.messageRepository.findByMailSubject(param, pageable);
    }
}
