package com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.repository.venv;

import org.springframework.data.repository.CrudRepository;

import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.venv.HostE;

public interface HostRepository extends CrudRepository<HostE, Long> {
    HostE findByhostId(String hostId);
}
