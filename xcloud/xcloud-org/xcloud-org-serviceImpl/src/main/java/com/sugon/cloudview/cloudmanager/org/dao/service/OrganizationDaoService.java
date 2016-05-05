package com.sugon.cloudview.cloudmanager.org.dao.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.sugon.cloudview.cloudmanager.org.bo.Organization;

public interface OrganizationDaoService {
    public Organization addOrganization(Organization organization);

    public Organization updateOrganization(Organization organization);

    public Organization showOrganization(String name);

    public Organization showOrganizationById(String id);

    public void deleteOrganization(String name);

    public List<Organization> findAllOrganization();

    public void deleteOrganizationById(String id);

    public Page<Organization> findByBO(Organization search, PageRequest pageable);

    public Organization findById(String orgId);

    // public OrganizationE getOrganizationById(String id);

    // public List<OrganizationE> findAllOrganizations();

    // public Page<OrganizationE> findAllOrganizations(Pageable pageable);

    // public List<OrganizationE> save(List<OrganizationE> organizations);

    // public OrganizationE save(String id, String name, String address, String
    // remarks, String creater, String owner,
    // Date create_time, String status);

    // public boolean deleteOrganization(OrganizationE organnization);

    // public boolean deleteAllOrganizations();
    // void delete(OrganizationE entity);

    // void deleteOrganizations(List<OrganizationE> organizations);

}
