package com.sugon.cloudview.cloudmanager.pmmgmt.serviceimpl.dao.serviceimpl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sugon.cloudview.cloudmanager.pmmgmt.serviceimpl.dao.entity.PhysicalMachineE;
import com.sugon.cloudview.cloudmanager.pmmgmt.serviceimpl.dao.repository.PhysicalMachineRepository;
import com.sugon.cloudview.cloudmanager.pmmgmt.serviceimpl.dao.service.PhysicalMachineDaoService;

@Component("pmMgmtDaoServiceImpl")
@Transactional
public class PhysicalMachineDaoServiceImpl implements PhysicalMachineDaoService {
    @Autowired
    private PhysicalMachineRepository physicalMachineRepository;

    @Override
    public PhysicalMachineE save(PhysicalMachineE physicalMachineE) {
        return physicalMachineRepository.save(physicalMachineE);
    }

    @Override
    public List<PhysicalMachineE> findAllPms() {
        return physicalMachineRepository.findAll();
    }

    @Override
    public PhysicalMachineE findPm(String id) {
        return physicalMachineRepository.findOne(id);
    }

    @Override
    public void delete(String id) {
        physicalMachineRepository.delete(id);

    }

    @Override
    public void update(PhysicalMachineE physicalMachineE) {
        // TODO Auto-generated method stub

    }

    @Override
    public Page<PhysicalMachineE> findPms(PhysicalMachineE physicalMachineE,
            Pageable pageable) {
        return physicalMachineRepository.findAll(
                new Specification<PhysicalMachineE>() {
                    @Override
                    public Predicate toPredicate(Root<PhysicalMachineE> root,
                            CriteriaQuery<?> query, CriteriaBuilder cb) {
                        Predicate predicate = cb.conjunction();
                        List<Expression<Boolean>> expressions = predicate
                                .getExpressions();
                        if (physicalMachineE != null) {
                            if (StringUtils.isNotEmpty(physicalMachineE
                                    .getOrgId())) {
                                expressions.add(cb.equal(root.get("orgId"),
                                        physicalMachineE.getOrgId()));
                            }
                            if (StringUtils.isNotEmpty(physicalMachineE
                                    .getName())) {
                                expressions.add(cb.like(root.get("name"), "%"
                                        + physicalMachineE.getName() + "%"));
                            }
                        }
                        return predicate;
                    }
                }, pageable);
    }

    @Override
    public void updateOrgIdByPmId(String orgId, String orgName, String id) {
        physicalMachineRepository.updateOrgIdByPmId(orgId, orgName, id);

    }

}
