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
public class UserDaoTest {
    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private UserDaoService userDAO;

    @Test
    public void testPageByRoleId(){
    	// mock role data
    	RoleE role = MockUtils.mockRoleE();
    	role.setRoleName("运营管理员");
    	em.persist(role);
    	RoleE role2 = MockUtils.mockRoleE();
    	role2.setRoleName("组织管理员");
    	em.persist(role2);
    	RoleE role3 = MockUtils.mockRoleE();
    	role3.setRoleName("组织成员");
    	em.persist(role3);
    	// mock user data
    	UserE user = MockUtils.mockUserE();
    	em.persist(user);
    	user.getRoles().add(role);
    	UserE user2 = MockUtils.mockUserE();
    	em.persist(user2);
    	user2.getRoles().add(role);
    	user2.getRoles().add(role2);
    	
    	// call test method
    	Page<UserE> page = userDAO.findByRole(null,null,null);
    	Page<UserE> page1 = userDAO.findByRole(role.getId(),null,null);
    	Page<UserE> page2 = userDAO.findByRole(role2.getId(),null,null);
    	Page<UserE> page3 = userDAO.findByRole(role3.getId(),null,null);
    	
    	// assertions
    	System.out.println(JsonUtil.toJson(page));
    	assertThat(page.getTotalElements(), equalTo(2L));
    	System.out.println(JsonUtil.toJson(page1));
    	assertThat(page1.getTotalElements(), equalTo(2L));
    	System.out.println(JsonUtil.toJson(page2));
    	assertThat(page2.getTotalElements(), equalTo(1L));
    	System.out.println(JsonUtil.toJson(page3));
    	assertThat(page3.getTotalElements(), equalTo(0L));
    }

    @Test
    public void testPageByRoleName(){
    	// mock role data
    	RoleE role = MockUtils.mockRoleE();
    	role.setRoleName("运营管理员");
    	em.persist(role);
    	RoleE role2 = MockUtils.mockRoleE();
    	role2.setRoleName("组织管理员");
    	em.persist(role2);
    	RoleE role3 = MockUtils.mockRoleE();
    	role3.setRoleName("组织成员");
    	em.persist(role3);
    	// mock user data
    	UserE user = MockUtils.mockUserE();
    	user.setRealname("user-1");
    	em.persist(user);
    	user.getRoles().add(role);
    	UserE user2 = MockUtils.mockUserE();
    	user2.setRealname("user-2");
    	em.persist(user2);
    	user2.getRoles().add(role);
    	user2.getRoles().add(role2);
    	UserE user3 = MockUtils.mockUserE();
    	user3.setRealname("user-3");
    	em.persist(user3);
    	user3.getRoles().add(role3);
    	
    	// call test method
    	Page<UserE> page1 = userDAO.findByRole(null,"组织成员",null);
    	Page<UserE> page2 = userDAO.findByRole(null,"%员%",null);
    	Page<UserE> page3 = userDAO.findByRole(null,null,null);
    	
    	// assertions
    	System.out.println(JsonUtil.toJson(page1));
    	assertThat(page1.getTotalElements(), equalTo(1L));
    	System.out.println(JsonUtil.toJson(page2));
    	assertThat(page2.getTotalElements(), equalTo(3L));
    	System.out.println(JsonUtil.toJson(page3));
    	assertThat(page3.getTotalElements(), equalTo(3L));
    }
    
}
