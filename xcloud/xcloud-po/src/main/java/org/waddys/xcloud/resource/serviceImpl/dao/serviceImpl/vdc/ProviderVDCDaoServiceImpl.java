package org.waddys.xcloud.resource.serviceImpl.dao.serviceImpl.vdc;

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
import org.waddys.xcloud.resource.serviceImpl.dao.entity.vdc.ProviderVDCE;
import org.waddys.xcloud.resource.serviceImpl.dao.repository.vdc.ProviderVDCRepository;
import org.waddys.xcloud.resource.serviceImpl.dao.service.vdc.ProviderVDCDaoService;

/**
 * 计算池dao层接口实现
 * 
 * @author sun
 *
 */
@Component("providerVDCDaoServiceImpl")
@Transactional
public class ProviderVDCDaoServiceImpl implements ProviderVDCDaoService {
    @Autowired
    private ProviderVDCRepository providerVDCRepository;

    @Override
    public ProviderVDCE save(ProviderVDCE providerVDC) {
        return this.providerVDCRepository.save(providerVDC);
    }

    @Override
    public List<ProviderVDCE> findAllProviderVDCs() {

        return this.providerVDCRepository.findAll();
    }

    @Override
    public ProviderVDCE findProviderVDC(String id) {
        return providerVDCRepository.findOne(id);
    }

    @Override
    public void updateProviderVDC(ProviderVDCE providerVDCE) {
        providerVDCRepository.updateProviderVDC(providerVDCE.getpVDCId(),
                providerVDCE.getName(), providerVDCE.getDescription(),
                providerVDCE.getvCpuNum(), providerVDCE.getMemorySize());
    }

    @Override
    public void delete(String proVDCId) {
        providerVDCRepository.delete(proVDCId);
    }

    @Override
    public Page<ProviderVDCE> findProviderVDCs(ProviderVDCE providerVDCE,
            Pageable pageable) {
        return providerVDCRepository.findAll(new Specification<ProviderVDCE>() {
            @Override
            public Predicate toPredicate(Root<ProviderVDCE> root,
                    CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                List<Expression<Boolean>> expressions = predicate
                        .getExpressions();
                if (providerVDCE != null) {
                    if (StringUtils.isNotEmpty(providerVDCE.getName())) {
                        expressions.add(cb.like(root.get("name"), "%"
                                + providerVDCE.getName() + "%"));
                    }
                }
                return predicate;
            }
        }, pageable);
    }

    @Override
    public List<ProviderVDCE> findAllProviderVDCs(ProviderVDCE providerVDCE) {
        return providerVDCRepository.findAll(new Specification<ProviderVDCE>() {
            @Override
            public Predicate toPredicate(Root<ProviderVDCE> root,
                    CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                List<Expression<Boolean>> expressions = predicate
                        .getExpressions();
                if (providerVDCE != null) {
                    if (StringUtils.isNotEmpty(providerVDCE.getName())) {
                        expressions.add(cb.equal(root.get("name"),
                                providerVDCE.getName()));
                    }
                    if (StringUtils.isNotEmpty(providerVDCE.getpVDCId())) {
                        expressions.add(cb.notEqual(root.get("pVDCId"),
                                providerVDCE.getpVDCId()));
                    }
                }
                return predicate;
            }
        });
    }
}
