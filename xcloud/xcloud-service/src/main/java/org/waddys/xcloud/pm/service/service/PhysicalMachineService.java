package org.waddys.xcloud.pm.service.service;

import java.util.List;
import java.util.Map;

import org.waddys.xcloud.pm.bo.PhysicalMachine;
import org.waddys.xcloud.pm.exception.PhysicalMachineException;

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
