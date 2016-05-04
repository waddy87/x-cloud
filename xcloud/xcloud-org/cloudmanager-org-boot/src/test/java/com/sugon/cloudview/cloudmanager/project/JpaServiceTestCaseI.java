package com.sugon.cloudview.cloudmanager.project;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sugon.cloudview.cloudmanager.Application;
import com.sugon.cloudview.cloudmanager.org.bo.Organization;
import com.sugon.cloudview.cloudmanager.org.service.OrganizationService;
import com.sugon.cloudview.cloudmanager.project.bo.Project;
import com.sugon.cloudview.cloudmanager.project.service.ProjectService;
import com.sugon.cloudview.cloudmanager.usermgmt.service.bo.User;
import com.sugon.cloudview.cloudmanager.usermgmt.service.service.UserService;
import com.sugon.cloudview.cloudmanager.util.JsonUtil;
import com.sugon.cloudview.cloudmanager.vm.dao.repository.VmHostRepository;
import com.sugon.cloudview.cloudmanager.vm.service.VmService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class JpaServiceTestCaseI {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private VmHostRepository vmReposiory;

    @Autowired
    private VmService vmService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private OrganizationService orgService;

    @Autowired
    private UserService userService;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    public void testListVm() {
        vmReposiory.pageByProject("111", "", null);
    }

    @Test
    @Transactional
    public void createVm() {
        // clean
        em.createQuery("delete from VmHostE").executeUpdate();
        //
    }

    public void revokeVm() {
        try {
            vmService.revoke("ff808081543bd93501543be8b5fd0029", "");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void test() {
        String jpql = "select vm from VmHostE vm ";
        Query query = em.createQuery(jpql);
        List result = query.getResultList();
        System.out.println(JsonUtil.toJson(result));
    }

    @Test
    @Transactional
    public void listOrganization() throws Exception {
        // clean
        Query query = em.createQuery("delete from OrganizationE");
        query.executeUpdate();
        // 构造组织
        Organization organization = new Organization();
        organization.setName("test-org");
        organization.setCreater("test-creater");
        organization.setOwner("test-owner");
        organization = orgService.add(organization);
        //
        Organization search = new Organization();
        search.setName("%test%");
        Page<Organization> page = orgService.pageAll(search, null);
        //
        assertThat(page.getTotalElements(), is(1L));
    }

    @Test
    @Transactional
    public void removeUser() throws Exception {
        // clean
        em.createQuery("delete from ProjectE").executeUpdate();
        em.createQuery("delete from UserE").executeUpdate();
        em.createQuery("delete from OrganizationE").executeUpdate();
        // 定义目标
        String projectId = null;
        // 构造组织
        Organization organization = new Organization();
        organization.setName("test-org");
        organization.setCreater("test-creater");
        organization.setOwner("test-owner");
        organization = orgService.add(organization);
        // 构造项目
        Project project = new Project();
        project.setName("test-proj");
        project.setOrgId(organization.getId());
        project = projectService.add(project);
        projectId = project.getId();
        // 构造用户
        User user = new User();
        user.setUsername("test-user");
        user.setCreateDate(new Date());
        String email = "zdp@163.com";
        String password = "password";
        String realname = "zhangdp";
        String telephone = "18635229978";
        user.setEmail(email);
        user.setLocked(false);
        user.setOrgId(user.getOrgId());
        user.setPassword(password);
        user.setRealname(realname);
        user.setTelephone(telephone);
        user = userService.save(user);
        // 项目关联用户
        projectService.addUser(projectId, user);
        // 测试移出用户
        projectService.removeUser(projectId, user);
        // 获取用户列表
        Set<User> userSet = projectService.listUser(projectId);
        // 断言
        assertThat(userSet, notNullValue());
        assertThat(userSet.size(), is(0));
    }

    public void test2() {
        String name = "zdp";
        String jpql = "select u from UserE u where username like :name";
        Query query = em.createQuery(jpql);
        query.setParameter("name", name + "%");

        // 设置查询结果的结束记录数
        int maxResults = 3;
        int pageNo = 1;
        int pageSize = 3;
        query.setMaxResults(maxResults);
        // 设置查询结果的开始记录数（从0开始计数）
        int firstResult = (pageNo - 1) * pageSize;
        query.setFirstResult(firstResult);
        List result = query.getResultList();
        System.out.println(result.size());
    }

}
