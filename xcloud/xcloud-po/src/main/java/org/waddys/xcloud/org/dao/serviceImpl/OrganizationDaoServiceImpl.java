package org.waddys.xcloud.org.dao.serviceImpl;

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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.waddys.xcloud.org.bo.Organization;
import org.waddys.xcloud.org.dao.entity.OrganizationE;
import org.waddys.xcloud.org.dao.repository.OrganizationRepository;
import org.waddys.xcloud.org.dao.service.OrganizationDaoService;

@Component("organizationDaoService")
@Transactional
public class OrganizationDaoServiceImpl implements OrganizationDaoService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationDaoServiceImpl(OrganizationRepository organizationRepository) {
        super();
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Organization addOrganization(Organization organization) {
        OrganizationE organizationE = convertToPo(organization);
        return convertToBo(organizationRepository.save(organizationE));
    }

    @Override
    public Organization updateOrganization(Organization organization) {
        OrganizationE organizationE = convertToPo(organization);
        return convertToBo(organizationRepository.saveAndFlush(organizationE));
    }

    @Override
    public Organization showOrganization(String name) {
        return convertToBo(organizationRepository.findByName(name));
    }

    @Override
    public Organization showOrganizationById(String id) {
        return convertToBo(organizationRepository.findById(id));
    }

    @Override
    public void deleteOrganization(String name) {
        OrganizationE organization = organizationRepository.findByName(name);
        if (null != organization) {
            organizationRepository.delete(organization);
        }
    }

    @Override
    public void deleteOrganizationById(String id) {

        OrganizationE organizationE = organizationRepository.findById(id);
        if (organizationE != null) {
            organizationRepository.delete(organizationE);

        }
    }

    @Override
    public Organization findById(String orgId) {
        OrganizationE source = organizationRepository.findById(orgId);
        return convertToBo(source);
    }

    @Override
    public Page<Organization> findByBO(Organization search, PageRequest pageable) {
        Page<OrganizationE> page = organizationRepository.findAll(new Specification<OrganizationE>() {
            @Override
            public Predicate toPredicate(Root<OrganizationE> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                List<Expression<Boolean>> expressions = predicate.getExpressions();
                if (search != null) {
                    if (search.getStatus() != null) {
                        expressions.add(cb.equal(root.get("status"), search.getStatus()));
                    }
                    if (!StringUtils.isEmpty(search.getName())) {
                        expressions.add(cb.like(root.get("name"), search.getName()));
                    }
                }
                return predicate;
            }
        }, pageable);
        return page.map(new ObjectConverter());
    }

    @Override
    public List<Organization> findAllOrganization() {
        List<Organization> list = new ArrayList<Organization>();
        List<OrganizationE> listE = organizationRepository.findAll();
        for (OrganizationE organizationE : listE) {
            list.add(convertToBo(organizationE));
        }
        return list;
    }

    private Organization convertToBo(OrganizationE organizationE) {
        if (organizationE == null) {
            return null;
        }
        Organization organization = new Organization();
        // organization.setId(organizationE.getId());
        // organization.setName(organizationE.getName());
        // organization.setAddress(organizationE.getAddress());
        // organization.setCreater(organizationE.getCreater());
        // organization.setCreate_time(organizationE.getCreate_time());
        // organization.setOwner(organizationE.getOwner());
        // organization.setRemarks(organizationE.getRemarks());
        // organization.setStatus(organizationE.getStatus());
        BeanUtils.copyProperties(organizationE, organization);

        return organization;
    }

    private OrganizationE convertToPo(Organization organization) {
        if (organization == null) {
            System.out.println("error: bo can't be empty.");
            return new OrganizationE();
        }
        OrganizationE organizationE = new OrganizationE();
        organizationE.setId(organization.getId());
        organizationE.setName(organization.getName());
        organizationE.setAddress(organization.getAddress());
        organizationE.setCreater(organization.getCreater());
        organizationE.setCreate_time(organization.getCreate_time());
        organizationE.setOwner(organization.getOwner());
        organizationE.setRemarks(organization.getRemarks());
        organizationE.setStatus(organization.getStatus());

        return organizationE;
    }

    public List<Organization> pageToList(Page<OrganizationE> pageList) {
        List<Organization> list = new ArrayList<Organization>();
        for (OrganizationE organizationE : pageList) {
            list.add(convertToBo(organizationE));
        }
        return list;
    }

    private class ObjectConverter implements Converter<OrganizationE, Organization> {
        @Override
        public Organization convert(OrganizationE source) {
            Organization target = new Organization();
            BeanUtils.copyProperties(source, target);
            return target;
        }
    }
}
