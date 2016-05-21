package org.waddys.xcloud.vm.dao.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.waddys.xcloud.vm.bo.VmHost;

/**
 * 虚机DAO接口
 * 
 * @author zhangdapeng
 *
 */
public interface VmHostDaoService {

    public VmHost createVmHost(VmHost VmHost);

    public VmHost updateVmHost(VmHost VmHost);

    public VmHost showVmHost(String name);

    public VmHost findById(String id);

    public List<VmHost> ListVmHosts(char status, String name, int page, int per_page);

    public List<VmHost> findAllVmHost();

    public long countByStatusAndname(char status, String name);

    public void deleteVmHostById(String id);

    public List<VmHost> listByProject(String pid);

    public List<VmHost> listByProject(String pid, int pageNumber, int pageSize);

    public Page<VmHost> pageAll(VmHost search, PageRequest pageRequest);

    public Page<VmHost> pageByProject(String pid, VmHost search, PageRequest pageRequest);

    public Page<VmHost> pageByOrg(String oid, VmHost search, PageRequest pageRequest);

    public Page<VmHost> findByBO(VmHost vmHost, Pageable pageable);

    public List<VmHost> findByBO(VmHost search);

    @Deprecated
    public List<VmHost> findAll(VmHost search);

    public VmHost findByName(String name);

    public boolean exists(String name);

    public Page<VmHost> findByHavingTask(VmHost search, Pageable pageable);

}
