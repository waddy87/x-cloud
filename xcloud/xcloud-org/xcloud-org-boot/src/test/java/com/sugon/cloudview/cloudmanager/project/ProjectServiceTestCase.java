package com.sugon.cloudview.cloudmanager.project;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sugon.cloudview.cloudmanager.project.dao.service.ProjectDaoService;
import com.sugon.cloudview.cloudmanager.project.dao.service.ProjectVmDaoService;
import com.sugon.cloudview.cloudmanager.project.impl.ProjectServiceImpl;
import com.sugon.cloudview.cloudmanager.usermgmt.service.service.UserService;
import com.sugon.cloudview.cloudmanager.vm.dao.service.VmHostDaoService;

//@ConfigurationProperties(locations = "classpath:application.properties")
//@ComponentScan
//@RunWith(SpringJUnit4ClassRunner.class)
// @SpringApplicationConfiguration(classes = RestApplication.class)
public class ProjectServiceTestCase {

    @InjectMocks
    private ProjectServiceImpl projectServcie;

    @Mock
    private ProjectDaoService projectDaoService;

    @Mock
    private VmHostDaoService vmHostDaoService;

    @Mock
    private UserService userService;

    @Mock
    private ProjectVmDaoService projectVmDaoService;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void init() {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        fail("Not yet implemented");
    }

    @Test
    public void testFind() {
        System.out.println(projectServcie);
        System.out.println(projectServcie.listAll());
    }

}
