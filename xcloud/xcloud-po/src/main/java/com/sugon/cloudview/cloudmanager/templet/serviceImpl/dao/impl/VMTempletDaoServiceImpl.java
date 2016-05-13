/**
 * Created on 2016年3月12日
 */
package com.sugon.cloudview.cloudmanager.templet.serviceImpl.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sugon.cloudview.cloudmanager.common.base.exception.CloudviewException;
import com.sugon.cloudview.cloudmanager.templet.service.entity.VMTempletE;
import com.sugon.cloudview.cloudmanager.templet.serviceImpl.dao.VMTempletDaoService;
import com.sugon.cloudview.cloudmanager.templet.serviceImpl.dao.repository.VMTempletRepository;

/**
 * 功能名: 虚拟机模板持久层服务实现
 * 功能描述: 虚拟机模板持久层服务实现
 * Copyright: Copyright (c) 2016
 * 公司: 曙光云计算技术有限公司
 *
 * @author 曾兵
 * @version 2.0.0 sp1
 */
@Service("VMTempletDaoService")
public class VMTempletDaoServiceImpl implements VMTempletDaoService{
	
	@Autowired
	private VMTempletRepository vmTempletRepository;
	
	/**
	 * @return the vmTempletRepository
	 */
	public VMTempletRepository getVmTempletRepository() {
		return vmTempletRepository;
	}

	/**
	 * @param vmTempletRepository the vmTempletRepository to set
	 */
	public void setVmTempletRepository(VMTempletRepository vmTempletRepository) {
		this.vmTempletRepository = vmTempletRepository;
	}

	/* (non-Javadoc)
	 * @see com.sugon.cloudview.cloudmanager.templet.serviceImpl.dao.VMTempletDaoService#release(com.sugon.cloudview.cloudmanager.templet.service.entity.VMTempletE)
	 */
	@Override
	public VMTempletE release(VMTempletE vmTempletE) throws CloudviewException {
		
		return this.addVMTemplet(vmTempletE);
	}

	/* (non-Javadoc)
	 * @see com.sugon.cloudview.cloudmanager.templet.serviceImpl.dao.VMTempletDaoService#unRelease(com.sugon.cloudview.cloudmanager.templet.service.entity.VMTempletE)
	 */
	@Override
	public VMTempletE unRelease(VMTempletE vmTempletE) throws CloudviewException {

		return this.addVMTemplet(vmTempletE);
	}

	/* (non-Javadoc)
	 * @see com.sugon.cloudview.cloudmanager.templet.serviceImpl.dao.VMTempletDaoService#modifyVMTempletE(com.sugon.cloudview.cloudmanager.templet.service.entity.VMTempletE)
	 */
	@Override
	public VMTempletE modifyVMTempletE(VMTempletE vmTempletE) throws CloudviewException {
		
		return this.addVMTemplet(vmTempletE);
	}

	/* (non-Javadoc)
	 * @see com.sugon.cloudview.cloudmanager.templet.serviceImpl.dao.VMTempletDaoService#addVMTemplet(com.sugon.cloudview.cloudmanager.templet.service.entity.VMTempletE)
	 */
	@Override
	public VMTempletE addVMTemplet(VMTempletE vmTempletE) throws CloudviewException {

		return vmTempletRepository.save(vmTempletE);
	}

	/* (non-Javadoc)
	 * @see com.sugon.cloudview.cloudmanager.templet.serviceImpl.dao.VMTempletDaoService#findByRelationId(java.lang.String)
	 */
	@Override
	public VMTempletE findByRelationId(String relationId) throws CloudviewException {

		return vmTempletRepository.findByRelationId(relationId);
	}

	/* (non-Javadoc)
	 * @see com.sugon.cloudview.cloudmanager.templet.serviceImpl.dao.VMTempletDaoService#findAllTemplet()
	 */
	@Override
	public List<VMTempletE> findAllTemplet(String evn) throws CloudviewException {
		
		return vmTempletRepository.findAll();
	}

	/* (non-Javadoc)
	 * @see com.sugon.cloudview.cloudmanager.templet.serviceImpl.dao.VMTempletDaoService#findAllTemplet(org.springframework.data.domain.Pageable)
	 */
	/*
	 * @Override public Page<VMTempletE> findAllTemplet(VMTempletE vmTempletE,
	 * Pageable pageable) throws CloudviewException {
	 * 
	 * // return vmTempletRepository.findByNameContaining(vmTempletE.getName(),
	 * // pageable); if (null == vmTempletE.getName()) { vmTempletE.setName("");
	 * } if (null == vmTempletE.getOs()) { vmTempletE.setOs(""); } if (null ==
	 * vmTempletE.getStatus()) { vmTempletE.setStatus(""); } return
	 * vmTempletRepository.findByNameAndOsAndStatusLikeOrderByRelationId(
	 * vmTempletE.getName(), vmTempletE.getOs(), vmTempletE.getStatus(),
	 * pageable); }
	 */
	@Override
	public Page<VMTempletE> findAllTemplet(VMTempletE vmTempletE, Pageable pageable) throws CloudviewException {

		return vmTempletRepository.findAll(new Specification<VMTempletE>() {
			@Override
			public Predicate toPredicate(Root<VMTempletE> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();
				if (vmTempletE.getName() != null) {
					expressions.add(cb.like(root.get("name"), "%" + vmTempletE.getName() + "%"));
				}
				if (vmTempletE.getStatus() != null) {
					expressions.add(cb.like(root.get("status"), "%" + vmTempletE.getStatus() + "%"));
				}
				if (vmTempletE.getOs() != null) {
					expressions.add(cb.like(root.get("os"), "%" + vmTempletE.getOs() + "%"));
				}
				expressions.add(cb.equal(root.get("visible"), "0"));
				query.orderBy(cb.desc(root.get("createTime")));
				return predicate;
			}
		}, pageable);
	}

	/* (non-Javadoc)
	 * @see com.sugon.cloudview.cloudmanager.templet.serviceImpl.dao.VMTempletDaoService#countAllTemplet(com.sugon.cloudview.cloudmanager.templet.service.entity.VMTempletE, org.springframework.data.domain.Pageable)
	 */
	@Override
	public long countAllTemplet(VMTempletE vmTempletE) throws CloudviewException {
		if (null == vmTempletE.getName()) {
			vmTempletE.setName("");
		}
		if (null == vmTempletE.getOs()) {
			vmTempletE.setOs("");
		}
		if (null == vmTempletE.getStatus()) {
			vmTempletE.setStatus("");
		}
		// return vmTempletRepository.countByName(vmTempletE.getName());
		return vmTempletRepository.countByNameAndOsAndStatus(vmTempletE.getName(), vmTempletE.getOs(),
				vmTempletE.getStatus());
	}

	public Page<VMTempletE> multiExpressionQuery(String name, String os, String state, Pageable pageable) {
		return vmTempletRepository.findAll(new Specification<VMTempletE>() {
			@Override
			public Predicate toPredicate(Root<VMTempletE> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();
				if (name != null) {
					expressions.add(cb.like(root.get("name"), "%" + name + "%"));
				}
				if (state != null) {
					expressions.add(cb.like(root.get("status"), "%" + state + "%"));
				}
				if (os != null) {
					expressions.add(cb.like(root.get("os"), "%" + os + "%"));
				}
				return predicate;
			}
		}, pageable);
	}

    @Override
    public VMTempletE findById(Integer id) throws CloudviewException {
        return vmTempletRepository.findById(id);
    }
}
