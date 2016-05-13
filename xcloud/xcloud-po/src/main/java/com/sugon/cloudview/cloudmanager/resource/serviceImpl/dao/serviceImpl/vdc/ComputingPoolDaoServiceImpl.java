package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.serviceImpl.vdc;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.vdc.ComputingPoolE;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.repository.vdc.ComputingPoolRepository;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.service.vdc.ComputingPoolDaoService;

/**
 * 计算池dao层接口实现
 * 
 * @author sun
 *
 */
@Component("computingPoolDaoServiceImpl")
@Transactional
public class ComputingPoolDaoServiceImpl implements ComputingPoolDaoService {

    @Autowired
    private ComputingPoolRepository computingPoolRepository;

    @Override
    public ComputingPoolE save(ComputingPoolE computingPoolE) {
        return this.computingPoolRepository.save(computingPoolE);
    }

    @Override
    public List<ComputingPoolE> findAllComputingPools() {

        return this.computingPoolRepository.findAll();
    }

    @Override
    public ComputingPoolE findComputingPool(String id) {
        return computingPoolRepository.findOne(id);
    }

    @Override
    public void delete(String id) {
        computingPoolRepository.delete(id);
    }

    @Override
    public void deleteByConfigId(String configId) {
        computingPoolRepository.deleteByConfigId(configId);

    }

    @Override
    public List<ComputingPoolE> findByIsDistribute(Boolean isDistribute) {
        return computingPoolRepository.findByIsDistribute(isDistribute);
    }

    @Override
    public void update(ComputingPoolE computingPoolE) {
        computingPoolRepository.update(computingPoolE.getIsDistribute(),
                computingPoolE.getComputingPoolId());

    }

    @Override
    public List<ComputingPoolE> findByIsAvl(Boolean isAvl) {
        return computingPoolRepository.findByIsAvl(isAvl);
    }

    @Override
    public List<ComputingPoolE> findAllComputingPools(
            ComputingPoolE computingPoolE) {
        return computingPoolRepository
                .findAll(new Specification<ComputingPoolE>() {
                    @Override
                    public Predicate toPredicate(Root<ComputingPoolE> root,
                            CriteriaQuery<?> query, CriteriaBuilder cb) {
                        Predicate predicate = cb.conjunction();
                        List<Expression<Boolean>> expressions = predicate
                                .getExpressions();
                        if (computingPoolE != null) {
                            if (null != computingPoolE.getIsAvl()) {
                                expressions.add(cb.equal(root.get("isAvl"),
                                        computingPoolE.getIsAvl()));
                            }
                            if (null != computingPoolE.getIsDistribute()) {
                                expressions.add(cb.equal(
                                        root.get("isDistribute"),
                                        computingPoolE.getIsDistribute()));
                            }
                        }
                        return predicate;
                    }
                });
    }
}
