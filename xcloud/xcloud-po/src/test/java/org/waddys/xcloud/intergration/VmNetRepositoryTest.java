package org.waddys.xcloud.intergration;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
//import org.testng.annotations.Test;
import org.waddys.xcloud.Application;
import org.waddys.xcloud.project.bo.Project;
import org.waddys.xcloud.project.po.dao.repository.ProjectRepository;
import org.waddys.xcloud.project.po.entity.ProjectE;
import org.waddys.xcloud.project.po.entity.ProjectVM;
import org.waddys.xcloud.util.JsonUtil;
import org.waddys.xcloud.utils.MockUtils;
import org.waddys.xcloud.vm.bo.VmHost;
import org.waddys.xcloud.vm.bo.VmNet;
import org.waddys.xcloud.vm.po.dao.VmHostDaoService;
import org.waddys.xcloud.vm.po.dao.VmNetDaoService;
import org.waddys.xcloud.vm.po.dao.repository.VmNetRepository;
import org.waddys.xcloud.vm.po.entity.VmHostE;
import org.waddys.xcloud.vm.po.entity.VmNetE;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class VmNetRepositoryTest {
    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private VmNetRepository netRepos;

    @Test
    public void testDeleteByVmId(){
    	// mock data
    	VmHostE vm = MockUtils.mockVmE();
    	em.persist(vm);
    	String vmId = vm.getId();
    	VmNetE vn = MockUtils.mockVmNetE();
    	vn.setVmId(vmId);
    	em.persist(vn);
    	VmNetE vn2 = MockUtils.mockVmNetE();
    	vn2.setVmId(vmId);
    	em.persist(vn2);
    	List<VmNetE> result = netRepos.findByVmId(vmId);
    	assertThat(result.size(), equalTo(2));
    	
    	// call test method
    	netRepos.deleteByVmId(vmId);
    	
    	// assertions
    	result = netRepos.findByVmId(vmId);
    	assertThat(result.size(), equalTo(0));
    }

    //@Test(enabled=false)
    @Test
    public void testFindByVmAndNet(){
    	// mock data
    	VmHostE vm = MockUtils.mockVmE();
    	em.persist(vm);
    	String vmId = vm.getId();
    	VmNetE vn = MockUtils.mockVmNetE();
    	vn.setVmId(vmId);
    	em.persist(vn);
    	
    	// call test method
    	VmNetE result = netRepos.findByVmIdAndNetId(vmId, vn.getNetId());
    	
    	// assertions
    	assertNotNull(result);
    	System.out.println(JsonUtil.toJson(result));
    }
    
}
