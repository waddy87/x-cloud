package com.sugon.cloudview.cloudmanager.org.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.sugon.cloudview.cloudmanager.org.bo.Organization;

/**
 * 
 * @author zhangdapeng
 *
 */
public interface OrganizationService {

    public Organization add(Organization organization);

    public Organization update(Organization organization) throws Exception;

    public Organization show(String id);

    public void deleteById(String id) throws Exception;

    public List<Organization> listAll();

    public Organization showById(String id);

    public Page<Organization> pageAll(Organization search, PageRequest pageRequest);

}
