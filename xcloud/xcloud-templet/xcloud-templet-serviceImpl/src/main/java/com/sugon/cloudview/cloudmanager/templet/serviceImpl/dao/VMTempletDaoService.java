/**
 * Created on 2016年3月12日
 */
package com.sugon.cloudview.cloudmanager.templet.serviceImpl.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sugon.cloudview.cloudmanager.common.base.exception.CloudviewException;
import com.sugon.cloudview.cloudmanager.templet.service.entity.VMTempletE;

/**
 * 功能名: 虚拟机模板吃就成操作接口服务
 * 功能描述: 提供虚拟机模板的生命周期操作接口
 * Copyright: Copyright (c) 2016
 * 公司: 曙光云计算技术有限公司
 *
 * @author 曾兵
 * @version 2.0.0 sp1
 */
public interface VMTempletDaoService {

	/**
	 * 
	 * 功能: 添加模板信息
	 *
	 * @param vmTempletE
	 * @return
	 * @throws CloudviewException
	 */
	public VMTempletE addVMTemplet(VMTempletE vmTempletE) throws CloudviewException;
	
	/**
	 * 
	 * 功能: 发布模板信息
	 *
	 * @param vmTempletE  虚拟机模板
	 * @return 返回成功与否 
	 */
	public VMTempletE release(VMTempletE vmTempletE) throws CloudviewException;
	
	/**
	 * 
	 * 功能: 取消发布模板信息
	 *
	 * @param vmTempletE  虚拟机模板
	 * @return 返回成功与否 
	 */
	public VMTempletE unRelease(VMTempletE vmTempletE) throws CloudviewException;
	
	/**
	 * 
	 * 功能: 修改虚拟机模板信息
	 *
	 * @param vmTempletE 虚拟机模板
	 * @return 返回修改后的虚拟机模板
	 */
	public VMTempletE modifyVMTempletE(VMTempletE vmTempletE) throws CloudviewException;
	
	/**
	 * 
	 * 功能: 通过底层关联ID查询模板
	 *
	 * @param relationId 关联ID
	 * @return
	 * @throws CloudviewException
	 */
	public VMTempletE findByRelationId(String relationId) throws CloudviewException; 
	
	/**
	 * 
	 * 功能: 查询所有的模板信息 
	 *
	 * @return
	 * @throws CloudviewException
	 */
	public List<VMTempletE> findAllTemplet(String evn) throws CloudviewException;
	
    /**
     * 分页查询
     * 
     * @param pageable
     * @return
     * @throws CloudviewException
     */
    public Page<VMTempletE> findAllTemplet(VMTempletE vmTempletE, Pageable pageable) throws CloudviewException;
    
    public long countAllTemplet(VMTempletE vmTempletE) throws CloudviewException;

    /**
     * 根据ID查询模板
     * 
     * @param id
     *            数据库唯一标识
     * @return
     * @throws CloudviewException
     */
    public VMTempletE findById(Integer id) throws CloudviewException;
}
