package org.waddys.xcloud.intergration;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.waddys.xcloud.Application;
import org.waddys.xcloud.util.JsonUtil;
import org.waddys.xcloud.utils.MockUtils;
import org.waddys.xcloud.vm.bo.VmNet;
import org.waddys.xcloud.vm.po.entity.VmHostE;
import org.waddys.xcloud.vm.po.entity.VmNetE;
import org.waddys.xcloud.vm.service.VmNetService;
import org.waddys.xcloud.vm.service.VmService;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class VmServiceTest {
    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private VmService vmService;
    
    @Autowired
    private VmNetService vmNetService;

    @Test
    public void testDeleteVm() throws Exception{
    	// mock data
    	String ip1 = "1.1.1.1";
    	String ip2 = "2.2.2.2";
    	VmHostE vm = MockUtils.mockVmE();
    	vm.setName("vm-1");
    	em.persist(vm);
    	VmNetE vn = MockUtils.mockVmNetE();
    	vn.setIp(ip1);
    	vn.setVmId(vm.getId());
    	em.persist(vn);
    	VmNetE vn2 = MockUtils.mockVmNetE();
    	vn2.setIp(ip2);
    	vn2.setVmId(vm.getId());
    	em.persist(vn2);
    	Page<VmNet> nets = vmNetService.pageByVm(vm.getId(),null);
    	System.out.println(JsonUtil.toJson(nets));
    	assertThat(nets.getTotalElements(), is(2L));
    	
    	// call
    	vmService.deleteById(vm.getId());

    	// assertions
    	nets = vmNetService.pageByVm(vm.getId(),null);
    	System.out.println(JsonUtil.toJson(nets));
    	assertThat(nets.getTotalElements(), is(0L));
    }
    
    @Test
    public void testFindByIp(){
    	// mock data
    	String ip1 = "1.1.1.1";
    	String ip2 = "2.2.2.2";
    	VmHostE vm = MockUtils.mockVmE();
    	vm.setName("vm-1");
    	em.persist(vm);
    	VmNetE vn = MockUtils.mockVmNetE();
    	vn.setIp(ip1);
    	vn.setVmId(vm.getId());
    	em.persist(vn);
    	VmHostE vm2 = MockUtils.mockVmE();
    	vm2.setName("vm-2");
    	em.persist(vm2);
    	VmNetE vn2 = MockUtils.mockVmNetE();
    	vn2.setIp(ip1);
    	vn2.setVmId(vm2.getId());
    	em.persist(vn2);
    	VmNetE vn3 = MockUtils.mockVmNetE();
    	vn3.setIp(ip2);
    	vn3.setVmId(vm2.getId());
    	em.persist(vn3);
    	
    	// call test method
    	VmNet vmNet = new VmNet();
    	vmNet.setIp(ip1);
    	Page vmPage1 = vmService.pageByIp(ip1, null, null);

    	vmNet.setIp(ip2);
    	Page vmPage2 = vmService.pageByIp(ip2, null, null);
    	
    	// assertions
    	assertNotNull(vmPage1);
    	System.out.println(JsonUtil.toJson(vmPage1));
    	assertThat(vmPage1.getTotalElements(), is(2L));
    	assertThat(vmPage2.getTotalElements(), is(1L));
    }

}
