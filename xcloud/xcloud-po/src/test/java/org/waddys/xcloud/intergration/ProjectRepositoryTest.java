package org.waddys.xcloud.intergration;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
import org.springframework.transaction.annotation.Transactional;
import org.waddys.xcloud.Application;
import org.waddys.xcloud.project.bo.Project;
import org.waddys.xcloud.project.po.dao.repository.ProjectRepository;
import org.waddys.xcloud.project.po.entity.ProjectE;
import org.waddys.xcloud.project.po.entity.ProjectVM;
import org.waddys.xcloud.util.JsonUtil;
import org.waddys.xcloud.utils.MockUtils;
import org.waddys.xcloud.vm.po.entity.VmHostE;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ProjectRepositoryTest {
    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private ProjectRepository repos;


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

    public void test() {
        String jpql = "INSERT INTO user (id, create_date, email, org_id,org_name,password,realname,salt,telephone,username,locked,is_delete) VALUES ('1', '2016-3-30 18:29:53', '', '', '', 'd3c59d25033dbf980d29554025c23a75', 'admin', '8d78869f470951332959580424d4bf4f', '1234567890', 'admin',0, 0);";
        em.createNativeQuery(jpql).executeUpdate();
    }

    @Test
    public void testFindProjectByVm(){
    	// mock project
    	ProjectE p = MockUtils.mockProjectE();
    	em.persist(p);
    	String pid = p.getId();
    	
    	// mock vm
    	VmHostE vm = MockUtils.mockVmE();
    	em.persist(vm);
    	String vmId = vm.getId();
    	
    	// mock p-v
	    	ProjectVM pv = new ProjectVM();
	    	pv.setProjectId(pid);
	    	pv.setVmId(vmId);
	    	em.persist(pv);
    	
    	// call test method
    	ProjectE result = repos.findByVm(vmId);
    	
    	// assertions
    	//assertNotNull(result);
    	assertThat(result, notNullValue());
    	System.out.println(JsonUtil.toJson(result));
    }

    public void test2() {
        String jpql = "INSERT INTO user (id, create_date, email, org_id,org_name,password,realname,salt,telephone,username,locked,is_delete) VALUES ('1', '2016-3-30 18:29:53', '', '', '', 'd3c59d25033dbf980d29554025c23a75', 'admin', '8d78869f470951332959580424d4bf4f', '1234567890', 'admin',0, 0);";
        em.createNativeQuery(jpql).executeUpdate();

        String name = "admin";
        jpql = "select count(u.username) from UserE u where username like :name order by createDate desc";
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
