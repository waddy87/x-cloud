/**
 * Created on 2016年3月12日
 */
package org.waddys.xcloud.template.service;

import org.springframework.data.domain.Page;
import org.waddys.xcloud.common.base.exception.CloudviewException;
import org.waddys.xcloud.template.po.entity.VMTempletE;

/**
 * 功能名: 虚拟机模板业务层接口服务 功能描述: 提供虚拟机模板的业务层接口 Copyright: Copyright (c) 2016 公司:
 * 曙光云计算技术有限公司
 *
 * @author 曾兵
 * @version 2.0.0 sp1
 */
public interface VMTempletService {

	/**
	 * 
	 * 功能: 同步虚拟机模板操作
	 * 
	 * 手动同步虚拟化环境中的模板信息
	 *
	 * @param venvID
	 *            虚拟化环境连接ID
	 * @return 返回虚拟机模板集合
	 */
	public String synVMTemplet(String venvID) throws CloudviewException;

	/**
	 * 
	 * 功能: 发布模板信息
	 *
	 * @param vmTempletE
	 *            虚拟机模板
	 * @return 返回成功与否
	 */
	public VMTempletE release(VMTempletE vmTempletE) throws CloudviewException;

	/**
	 * 
	 * 功能: 取消发布模板信息
	 *
	 * @param vmTempletE
	 *            虚拟机模板
	 * @return 返回成功与否
	 */
	public VMTempletE unRelease(VMTempletE vmTempletE) throws CloudviewException;

	/**
	 * 
	 * 功能: 修改虚拟机模板信息
	 *
	 * @param vmTempletE
	 *            虚拟机模板
	 * @return 返回修改后的虚拟机模板
	 */
	public VMTempletE modifyVMTempletE(VMTempletE vmTempletE) throws CloudviewException;

	/**
	 * 
	 * 功能: 通过底层关联ID查询模板
	 *
	 * @param relationId
	 *            关联ID
	 * @return
	 * @throws CloudviewException
	 */
	public VMTempletE findByRelationId(String relationId) throws CloudviewException;

	public Page<VMTempletE> findAllVMTemplet(VMTempletE vmTempletE, int pageNum, int pageSize)
			throws CloudviewException;

	/**
	 * 统计总数
	 * 
	 * @param providerVDCE
	 * @return
	 */
	public long countAllVMTemplet(VMTempletE vmTempletE) throws CloudviewException;

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
