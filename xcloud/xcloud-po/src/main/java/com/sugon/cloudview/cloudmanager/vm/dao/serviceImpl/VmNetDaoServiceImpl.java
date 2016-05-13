/**
 * 
 */
package com.sugon.cloudview.cloudmanager.vm.dao.serviceImpl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sugon.cloudview.cloudmanager.vm.bo.VmNet;
import com.sugon.cloudview.cloudmanager.vm.dao.entity.VmNetE;
import com.sugon.cloudview.cloudmanager.vm.dao.repository.VmNetRepository;
import com.sugon.cloudview.cloudmanager.vm.dao.service.VmNetDaoService;

/**
 * 虚机网络DAO组件
 * 
 * @author zhangdapeng
 *
 */
@Component("VmNetDaoService")
public class VmNetDaoServiceImpl implements VmNetDaoService {

    @Autowired
    private VmNetRepository vmNetRepository;

    @Override
    public Page<VmNet> findByBO(VmNet vmNet, PageRequest pageable) {
        Page<VmNetE> page = vmNetRepository.findAll(new Specification<VmNetE>() {
            @Override
            public Predicate toPredicate(Root<VmNetE> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                List<Expression<Boolean>> expressions = predicate.getExpressions();
                if (vmNet != null) {
                    if (!StringUtils.isEmpty(vmNet.getVmId())) {
                        expressions.add(cb.equal(root.get("vmId"), vmNet.getVmId()));
                    }
                }
                return predicate;
            }
        }, pageable);
        return page.map(new ObjectConverter());
    }

    @Override
    public VmNet add(VmNet net) throws Exception {
        VmNetE entity = bo2po(net);
        entity = vmNetRepository.save(entity);
        return po2bo(entity);
    }

    @Override
    public VmNet findById(String id) {
        VmNetE source = vmNetRepository.findById(id);
        VmNet target = po2bo(source);
        return target;
    }

    @Override
    public void delete(VmNet net) throws Exception {
        if (net != null) {
            VmNetE entity = bo2po(net);
            vmNetRepository.delete(entity);
        }
    }

    @Override
    public void addBatch(List<VmNet> nets) throws Exception {
        // TODO Auto-generated method stub
        // vmNetRepository.save(entities);
    }

    private VmNetE bo2po(VmNet bo) {
        VmNetE po = new VmNetE();
        BeanUtils.copyProperties(bo, po);
        return po;
    }

    private VmNet po2bo(VmNetE po) {
        VmNet bo = new VmNet();
        BeanUtils.copyProperties(po, bo);
        return bo;
    }

    private class ObjectConverter implements Converter<VmNetE, VmNet> {
        @Override
        public VmNet convert(VmNetE source) {
            VmNet target = new VmNet();
            BeanUtils.copyProperties(source, target);
            return target;
        }
    }

}
