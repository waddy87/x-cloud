package org.waddys.xcloud.intergration;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;
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
import org.waddys.xcloud.user.po.dao.UserDaoService;
import org.waddys.xcloud.user.po.entity.RoleE;
import org.waddys.xcloud.user.po.entity.UserE;
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
public class VmDaoTest {
    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private VmHostDaoService vmDAO;
    
   // @Test
    public void testPageByProjectId(){
    	// mock a project with 2 vm
    	ProjectE p = MockUtils.mockProjectE();
    	VmHostE vm1 = MockUtils.mockVmE();
    	VmHostE vm2 = MockUtils.mockVmE();
    	p.addVm(vm1);
    	p.addVm(vm2);
    	em.persist(p);
    	
    	// call: query vm list by project id
    	Project pSearch = new Project();
    	pSearch.setId(p.getId());
    	Page<VmHost> page = vmDAO.pageByProject2(pSearch, null);
    	
    	// assertions
    	System.out.println(JsonUtil.toJson(page));
    	assertThat(page.getTotalElements(), equalTo(2L));
    }
    
    /**
     * 多对一关系（虚机对项目）：
     * 根据关联实体属性（项目名称）做高级查询
     */
    @Test
    public void testPageByProjectName(){
    	// mock a project OA with 2 vm
    	ProjectE p1 = MockUtils.mockProjectE();
    	p1.setName("project-oa");
    	VmHostE vm1 = MockUtils.mockVmE();
    	VmHostE vm2 = MockUtils.mockVmE();
    	p1.addVm(vm1);
    	p1.addVm(vm2);
    	em.persist(p1);
//    	VmHostE vm1 = MockUtils.mockVmE();
//    	vm1.setProject(p1);
//    	em.persist(vm1);
//    	VmHostE vm2 = MockUtils.mockVmE();
//    	vm2.setProject(p1);
//    	em.persist(vm2);
    	
    	// mock a project SOA with 1 vm
    	ProjectE p2 = MockUtils.mockProjectE();
    	p2.setName("project-soa");
    	VmHostE vm3 = MockUtils.mockVmE();
    	p2.addVm(vm3);
    	em.persist(p2);
//    	VmHostE vm3 = MockUtils.mockVmE();
//    	vm3.setProject(p2);
//    	em.persist(vm3);
    	
    	
    	// call: query vm list by project name "oa"
    	Project pSearch = new Project();
    	pSearch.setName("oa");
    	Page<VmHost> page = vmDAO.pageByProject2(pSearch, null);
    	
    	// assertions: return 3 vm
    	System.out.println(JsonUtil.toJson(page));
    	assertThat(page.getTotalElements(), equalTo(3L));
    }
    

    /**
     * 一对多关系（虚机对网卡）：
     * 根据关联实体属性（网卡IP地址）做高级查询
     */
    @Test
    public void testPageByIp(){
    	// mock a vm with 2 net
    	VmHostE vm1 = MockUtils.mockVmE();
    	vm1.setName("vm-1");
    	em.persist(vm1);
    	VmNetE net1 = MockUtils.mockVmNetE();
    	net1.setIp("127.0.0.1");
    	net1.setVmId(vm1.getId());
    	em.persist(net1);
    	VmNetE net2 = MockUtils.mockVmNetE();
    	net2.setIp("172.0.0.1");
    	net2.setVmId(vm1.getId());
    	em.persist(net2);

    	// mock a vm with 1 net
    	VmHostE vm2 = MockUtils.mockVmE();
    	vm2.setName("vm-2");
    	em.persist(vm1);
    	VmNetE net21 = MockUtils.mockVmNetE();
    	net21.setIp("127.0.0.1");
    	net21.setVmId(vm1.getId());
    	em.persist(net21);
    	
    	// call: query vm list by ip
    	VmHost search = new VmHost();
    	search.setIp("127.0.0.1");
    	Page<VmHost> page = vmDAO.findByBO(search, null);
    	VmHost search2 = new VmHost();
    	search2.setIp("172.0.0.1");
    	Page<VmHost> page2 = vmDAO.findByBO(search2, null);
    	
    	// assertions: return 2 vm
    	System.out.println(JsonUtil.toJson(page));
    	assertThat(page.getTotalElements(), equalTo(2L));
    	// assertions: return 1 vm
    	System.out.println(JsonUtil.toJson(page2));
    	assertThat(page2.getTotalElements(), equalTo(1L));
    }
    
}
