package com.sugon.cloudview.cloudmanager.pmmgmt.service.service;

import java.util.List;
import java.util.Map;

import com.sugon.cloudview.cloudmanager.pmmgmt.service.bo.PhysicalMachine;
import com.sugon.cloudview.cloudmanager.pmmgmt.service.exception.PhysicalMachineException;

public interface PhysicalMachineService {
    public PhysicalMachine save(PhysicalMachine physicalMachine)
            throws PhysicalMachineException;

    public List<PhysicalMachine> findAllPms() throws PhysicalMachineException;

    public PhysicalMachine findPm(String id) throws PhysicalMachineException;

    public void delete(String id) throws PhysicalMachineException;

    public void update(PhysicalMachine physicalMachine)
            throws PhysicalMachineException;

    public Map<String, Object> findPms(PhysicalMachine physicalMachine,
            Integer pageNum, Integer pageSize) throws PhysicalMachineException;

    public void batchUpdateOrgId(String orgId, String orgName, String pmIds)
            throws PhysicalMachineException;
}
