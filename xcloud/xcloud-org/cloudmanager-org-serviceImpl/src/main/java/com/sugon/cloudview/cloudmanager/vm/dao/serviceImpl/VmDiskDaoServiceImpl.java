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

import com.sugon.cloudview.cloudmanager.vm.bo.VmDisk;
import com.sugon.cloudview.cloudmanager.vm.dao.entity.VmDiskE;
import com.sugon.cloudview.cloudmanager.vm.dao.repository.VmDiskRepository;
import com.sugon.cloudview.cloudmanager.vm.dao.service.VmDiskDaoService;

/**
 * 虚机磁盘DAO组件
 * 
 * @author zhangdapeng
 *
 */
@Component("vmDiskDaoService")
public class VmDiskDaoServiceImpl implements VmDiskDaoService {

    @Autowired
    private VmDiskRepository vmDiskRepository;

    @Override
    public Page<VmDisk> findByBO(VmDisk vmDisk, PageRequest pageable) {
        Page<VmDiskE> page = vmDiskRepository.findAll(new Specification<VmDiskE>() {
            @Override
            public Predicate toPredicate(Root<VmDiskE> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                List<Expression<Boolean>> expressions = predicate.getExpressions();
                if (vmDisk != null) {
                    if (!StringUtils.isEmpty(vmDisk.getVmId())) {
                        expressions.add(cb.equal(root.get("vmId"), vmDisk.getVmId()));
                    }
                }
                return predicate;
            }
        }, pageable);
        return page.map(new ObjectConverter());
    }

    @Override
    public VmDisk add(VmDisk vmDisk) throws Exception {
        VmDiskE entity = bo2po(vmDisk);
        entity = vmDiskRepository.save(entity);
        return po2bo(entity);
    }

    @Override
    public VmDisk findById(String id) {
        VmDiskE source = vmDiskRepository.findById(id);
        VmDisk target = po2bo(source);
        return target;
    }

    @Override
    public void delete(VmDisk vmDisk) throws Exception {
        if (vmDisk != null) {
            VmDiskE entity = bo2po(vmDisk);
            vmDiskRepository.delete(entity);
        }
    }

    private VmDiskE bo2po(VmDisk bo) {
        VmDiskE po = new VmDiskE();
        BeanUtils.copyProperties(bo, po);
        return po;
    }

    private VmDisk po2bo(VmDiskE po) {
        VmDisk bo = new VmDisk();
        BeanUtils.copyProperties(po, bo);
        return bo;
    }

    private class ObjectConverter implements Converter<VmDiskE, VmDisk> {
        @Override
        public VmDisk convert(VmDiskE source) {
            VmDisk target = new VmDisk();
            BeanUtils.copyProperties(source, target);
            return target;
        }
    }

}
