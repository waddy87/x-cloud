package com.sugon.cloudview.cloudmanager.managedvm.serviceImpl.dao.serviceImpl;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.sugon.cloudview.cloudmanager.managedvm.serviceImpl.dao.entity.OldVirtualMachineE;
import com.sugon.cloudview.cloudmanager.managedvm.serviceImpl.dao.repository.OldVirtualMachineRepository;
import com.sugon.cloudview.cloudmanager.managedvm.serviceImpl.dao.service.OldVirtualMachineDaoService;

@Service("managedvm-oldVirtualMachineService")
@Transactional
public class OldVirtualMachineServiceImpl implements OldVirtualMachineDaoService {

	// 不写自动注入是不是也可以 构造函数中已经注入
	@Autowired
	private final OldVirtualMachineRepository oldVirtualMachineRepository;

	@Autowired
	public OldVirtualMachineServiceImpl(OldVirtualMachineRepository oldVirtualMachineRepository) {
		this.oldVirtualMachineRepository = oldVirtualMachineRepository;
	}

	@Override
	public List<OldVirtualMachineE> findAll() {
		return this.oldVirtualMachineRepository.findAll();
	}

	@Override
	public Page<OldVirtualMachineE> findAll(Pageable pageable) {
		return this.oldVirtualMachineRepository.findAll(pageable);
	}

	@Override
	public OldVirtualMachineE findById(String id) {
		return this.oldVirtualMachineRepository.findById(id);
	}

	@Override
	public OldVirtualMachineE findByVmId(String vmId) {
		return this.oldVirtualMachineRepository.findByVmId(vmId);
	}

	@Override
	public List<OldVirtualMachineE> findByIdIn(List<String> ids) {
		return this.oldVirtualMachineRepository.findByIdIn(ids);
	}

	@Override
	public OldVirtualMachineE saveOrUpdate(OldVirtualMachineE oldVirtualMachineE) {
		return this.oldVirtualMachineRepository.save(oldVirtualMachineE);
	}

	@Override
	public List<OldVirtualMachineE> saveOrUpdate(List<OldVirtualMachineE> oldVirtualMachineEs) {
		return this.oldVirtualMachineRepository.save(oldVirtualMachineEs);
	}

	@Override
	public void deleteOne(String id) {
		this.oldVirtualMachineRepository.delete(id);
	}

	@Override
	public void deleteBatch(List<OldVirtualMachineE> oldVirtualMachineEs) {
		this.oldVirtualMachineRepository.delete(oldVirtualMachineEs);
	}

	@Override
	public int count() {
		return this.oldVirtualMachineRepository.count();
	}

	@Override
	public int countByName(String name) {
		return this.oldVirtualMachineRepository.countByName(name);
	}

	@Override
	public Page<OldVirtualMachineE> findByName(String name, Pageable pageable) {
		return this.oldVirtualMachineRepository.findByName(name, pageable);
	}

	@Override
	public Page<OldVirtualMachineE> findByEntity(OldVirtualMachineE paramOldVME, Pageable pageable) {
		return this.oldVirtualMachineRepository.findAll(new Specification<OldVirtualMachineE>() {

			@Override
			public Predicate toPredicate(Root<OldVirtualMachineE> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();
				if (!StringUtils.isEmpty(paramOldVME.getOrgId())) {
					expressions.add(cb.equal(root.get("orgId"), paramOldVME.getOrgId()));
				}
				if (!StringUtils.isEmpty(paramOldVME.getName())) {
					expressions.add(cb.like(root.get("name"), "%" + paramOldVME.getName() + "%"));
				}
				return predicate;
			}
		}, pageable);
	}
}
