package org.waddys.xcloud.pm.po.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.waddys.xcloud.pm.po.entity.PhysicalMachineE;

public interface PhysicalMachineDaoService {
    public PhysicalMachineE save(PhysicalMachineE physicalMachineE);

    public List<PhysicalMachineE> findAllPms();

    public PhysicalMachineE findPm(String id);

    public void delete(String id);

    public void update(PhysicalMachineE physicalMachineE);

    public Page<PhysicalMachineE> findPms(PhysicalMachineE physicalMachineE,
            Pageable pageable);

    public void updateOrgIdByPmId(String orgId, String orgName, String id);
}
