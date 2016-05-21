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
import org.waddys.xcloud.project.dao.entity.ProjectE;
import org.waddys.xcloud.project.dao.entity.ProjectVM;
import org.waddys.xcloud.project.dao.repository.ProjectRepository;
import org.waddys.xcloud.util.JsonUtil;
import org.waddys.xcloud.utils.MockUtils;
import org.waddys.xcloud.vm.dao.entity.VmHostE;
import org.waddys.xcloud.vm.dao.entity.VmNetE;
import org.waddys.xcloud.vm.dao.repository.VmNetRepository;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class VmNetRepositoryTest {
    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private VmNetRepository repos;

    @Test
    public void testFindByVmAndNet(){
    	// mock project
    	VmNetE vn = MockUtils.mockVmNetE();
    	em.persist(vn);
    	
    	// call test method
    	VmNetE result = repos.findByVmIdAndNetId(vn.getVmId(), vn.getNetId());
    	
    	// assertions
    	assertNotNull(result);
    	System.out.println(JsonUtil.toJson(result));
    }

}
