package com.sugon.cloudview.cloudmanager.vm.dao.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.sugon.cloudview.cloudmanager.project.bo.Project;
import com.sugon.cloudview.cloudmanager.project.dao.entity.ProjectE;
import com.sugon.cloudview.cloudmanager.project.dao.repository.ProjectRepository;
import com.sugon.cloudview.cloudmanager.vm.bo.VmHost;
import com.sugon.cloudview.cloudmanager.vm.constant.VmStatus;
import com.sugon.cloudview.cloudmanager.vm.dao.entity.VmHostE;
import com.sugon.cloudview.cloudmanager.vm.dao.repository.VmHostRepository;
import com.sugon.cloudview.cloudmanager.vm.dao.service.VmHostDaoService;

@Component("VmHostDaoService")
@Transactional
public class VmHostDaoServiceImpl implements VmHostDaoService {

    @Autowired
    private VmHostRepository vmHostRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    public VmHostDaoServiceImpl(VmHostRepository vmHostRepository) {
        super();
        this.vmHostRepository = vmHostRepository;
    }

    @Override
    public VmHost showVmHost(String name) {
        return convertToBo(vmHostRepository.findByName(name));
    }

    @Override
    public VmHost findByName(String name) {
        return convertToBo(vmHostRepository.findByName(name));
    }

    @Override
    public VmHost findById(String id) {
        return convertToBo(vmHostRepository.findById(id));
    }

    @Override
    public List<VmHost> ListVmHosts(char status, String name, int page, int per_page) {
        Page<VmHostE> pageList = null;
        pageList = vmHostRepository.findByStatusAndNameContainingAllIgnoringCase(status, name,
                new PageRequest(page, per_page));
        return pageToList(pageList);
    }

    @Override
    public void deleteVmHostById(String id) {
        VmHostE vmHostE = vmHostRepository.findById(id);
        if (vmHostE != null) {
            vmHostRepository.delete(vmHostE);

        }
    }

    @Override
    public List<VmHost> findAllVmHost() {
        List<VmHost> list = new ArrayList<VmHost>();
        List<VmHostE> listE = vmHostRepository.findAll();
        for (VmHostE vmHostE : listE) {
            list.add(convertToBo(vmHostE));
        }
        return list;
    }

    @Override
    public long countByStatusAndname(char status, String name) {
        // TODO Auto-generated method stub
        long num = 0;
        if (status != '\0' && (null == name || "" == name)) {
            num = vmHostRepository.countByStatus(status);
        } else if (status == '\0' && (null != name || "" != name)) {
            num = vmHostRepository.countByName(name);
        } else if (status == '\0' && (null == name || "" == name)) {
            num = vmHostRepository.count();
        } else {
            num = vmHostRepository.countByStatusAndName(status, name);
        }
        return num;
    }

    private VmHost convertToBo(VmHostE vmHostE) {
        if (vmHostE == null) {
            return null;
        }
        VmHost vmHost = new VmHost();
        BeanUtils.copyProperties(vmHostE, vmHost);
        return vmHost;
    }

    private VmHostE convertToPo(VmHost vmHost) {
        if (vmHost == null) {
            System.out.println("error: bo can't be empty.");
            return new VmHostE();
        }
        VmHostE vmHostE = new VmHostE();
        BeanUtils.copyProperties(vmHost, vmHostE);
        return vmHostE;
    }

    public List<VmHost> pageToList(Page<VmHostE> pageList) {
        List<VmHost> list = new ArrayList<VmHost>();
        for (VmHostE vmHostE : pageList) {
            list.add(convertToBo(vmHostE));
        }
        return list;
    }

    @Override
    public List<VmHost> listByProject(String pid) {
        // TODO Auto-generated method stub
        return vmHostRepository.listByProject(pid);
    }

    @Override
    public List<VmHost> listByProject(String pid, int pageNumber, int pageSize) {
        // TODO Auto-generated method stub
        Page<VmHost> page = vmHostRepository.listByProject(pid, new PageRequest(pageNumber, pageSize));
        return page.getContent();
    }

    @Override
    public Page<VmHost> pageAll(VmHost search, PageRequest pageRequest) {
        String name = "";
        if (search != null || !StringUtils.isEmpty(name)) {
            name = search.getName();
        }
        return vmHostRepository.findByNameContainingAllIgnoringCase(name, pageRequest);
    }

    @Override
    public Page<VmHost> pageByProject(String pid, VmHost search, PageRequest pageRequest) {
        String name = "";
        if (search != null || !StringUtils.isEmpty(name)) {
            name = search.getName();
        }
        return vmHostRepository.pageByProject(pid, name, pageRequest);
    }

    @Override
    public Page<VmHost> pageByOrg(String oid, VmHost search, PageRequest pageRequest) {
        // TODO Auto-generated method stub
        return vmHostRepository.findByOrgIdAndNameContainingAllIgnoringCase(oid, search.getName(), pageRequest);
    }

    @Override
    public boolean exists(String name) {
        boolean flag = false;
        List<VmHostE> vms = vmHostRepository.findAll(new Specification<VmHostE>() {
            @Override
            public Predicate toPredicate(Root<VmHostE> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                List<Expression<Boolean>> expressions = predicate.getExpressions();
                expressions.add(cb.equal(root.get("name"), name));
                expressions.add(cb.notEqual(root.get("vmStatus"), VmStatus.DELETED));
                return predicate;
            }
        });
        if (!CollectionUtils.isEmpty(vms)) {
            return true;
        }
        return flag;
    }

    @Override
    public List<VmHost> findByBO(VmHost search) {
        return this.findByBO(search, null).getContent();
    }

    @Override
    public Page<VmHost> findByHavingTask(VmHost search, Pageable pageable) {
        Page<VmHostE> page = vmHostRepository.findAll(new Specification<VmHostE>() {
            @Override
            public Predicate toPredicate(Root<VmHostE> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                List<Expression<Boolean>> expressions = predicate.getExpressions();
                if (search != null) {
                    expressions.add(cb.isNotNull(root.get("taskId")));
                    if (!StringUtils.isEmpty(search.getOrgId())) {
                        expressions.add(cb.equal(root.get("orgId"), search.getOrgId()));
                    }
                    if (!StringUtils.isEmpty(search.getOwnerId())) {
                        expressions.add(cb.equal(root.get("ownerId"), search.getOwnerId()));
                    }
                    if (!StringUtils.isEmpty(search.getVdcId())) {
                        expressions.add(cb.equal(root.get("vdcId"), search.getVdcId()));
                    }
                }
                return predicate;
            }
        }, pageable);
        return page.map(new Converter<VmHostE, VmHost>() {
            @Override
            public VmHost convert(VmHostE source) {
                VmHost target = new VmHost();
                BeanUtils.copyProperties(source, target);
                return target;
            }
        });
    }

    @Override
    public Page<VmHost> findByBO(VmHost search, Pageable pageable) {
        Page<VmHostE> page = vmHostRepository.findAll(new Specification<VmHostE>() {
            @Override
            public Predicate toPredicate(Root<VmHostE> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // Subquery<OrganizationE> subquery =
                // query.subquery(OrganizationE.class);
                // Root<OrganizationE> org = subquery.from(OrganizationE.class);
                // subquery.select(org.get(""));
                Predicate predicate = cb.conjunction();
                List<Expression<Boolean>> expressions = predicate.getExpressions();
                if (search != null) {
                    if (!StringUtils.isEmpty(search.getInternalId())) {
                        expressions.add(cb.equal(root.get("internalId"), search.getInternalId()));
                    }
                    if (!StringUtils.isEmpty(search.getTaskId())) {
                        expressions.add(cb.equal(root.get("taskId"), search.getTaskId()));
                    }
                    if (!StringUtils.isEmpty(search.getOrgId())) {
                        expressions.add(cb.equal(root.get("orgId"), search.getOrgId()));
                    }
                    if (!StringUtils.isEmpty(search.getOwnerId())) {
                        expressions.add(cb.equal(root.get("ownerId"), search.getOwnerId()));
                    }
                    if (!StringUtils.isEmpty(search.getVdcId())) {
                        expressions.add(cb.equal(root.get("vdcId"), search.getVdcId()));
                    }
                    if (search.getIsAssigned() != null) {
                        expressions.add(cb.equal(root.get("isAssigned"), search.getIsAssigned()));
                    }
                    if (search.getVmStatus() != null) {
                        expressions.add(cb.equal(root.get("vmStatus"), search.getVmStatus()));
                    }
                    if (search.getRunStatus() != null) {
                        expressions.add(cb.equal(root.get("runStatus"), search.getRunStatus()));
                    }
                    if (search.getStatus() != null) {
                        expressions.add(cb.equal(root.get("status"), search.getStatus()));
                    }
                    if (!StringUtils.isEmpty(search.getName())) {
                        expressions.add(cb.like(root.get("name"), "%" + search.getName() + "%"));
                    }
                }
                return predicate;
            }
        }, pageable);
        return page.map(new Converter<VmHostE, VmHost>() {
            @Override
            public VmHost convert(VmHostE source) {
                VmHost target = new VmHost();
                BeanUtils.copyProperties(source, target);
                ProjectE pe = projectRepository.findByVm(source.getId());
                if(pe!=null){
                	Project p = new Project();
                	BeanUtils.copyProperties(pe, p);
                	target.setProject(p);
                }
                // String orgId = source.getOrgId();
                // OrganizationE org = orgRepository.findById(orgId);
                // if (org != null) {
                // String orgName = org.getName();
                // target.setOrgName(orgName);
                // }
                return target;
            }
        });
    }

    @Override
    public VmHost createVmHost(VmHost vmHost) {
        // TODO Auto-generated method stub
        VmHostE vmHostE = convertToPo(vmHost);
        return convertToBo(vmHostRepository.save(vmHostE));
    }

    @Override
    public VmHost updateVmHost(VmHost vmHost) {
        // TODO Auto-generated method stub
        VmHostE vmHostE = convertToPo(vmHost);
        return convertToBo(vmHostRepository.saveAndFlush(vmHostE));
    }

    @Deprecated
    @Override
    public List<VmHost> findAll(VmHost search) {
        return this.findByBO(search);
    }

}
