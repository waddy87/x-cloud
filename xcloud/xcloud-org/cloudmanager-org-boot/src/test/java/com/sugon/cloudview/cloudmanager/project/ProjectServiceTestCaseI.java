package com.sugon.cloudview.cloudmanager.project;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import com.sugon.cloudview.cloudmanager.Application;
import com.sugon.cloudview.cloudmanager.project.bo.Project;
import com.sugon.cloudview.cloudmanager.project.service.ProjectService;
import com.sugon.cloudview.cloudmanager.usermgmt.service.bo.User;
import com.sugon.cloudview.cloudmanager.usermgmt.service.exception.UserMgmtException;
import com.sugon.cloudview.cloudmanager.usermgmt.service.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ProjectServiceTestCaseI {

    @Autowired
    private ProjectService projectServcie;

    @Autowired
    private UserService userService;

    // @Mock
    // private ProjectDaoService projectDaoService;
    //
    // @Mock
    // private VmHostDaoService vmHostDaoService;
    //
    // @Mock
    // private UserService userService;
    //
    // @Mock
    // private ProjectVmDaoService projectVmDaoService;

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

    @Test
    public void test() {
        fail("Not yet implemented");
    }

    @Test
    public void test2() {
        String pid = "0000000053cb619f0153ea294cc20001";
        Project project = projectServcie.show(pid);
        Set<User> users = project.getUsers();
        System.out.println("users=" + users);
        if (!CollectionUtils.isEmpty(users)) {
            for (User user : users) {
                System.out.println(user.getUsername());
            }
        }
        // assertThat(users.size(), is(3));
    }

    public void testFind() {
        System.out.println(projectServcie);
        User user = new User();
        user.setUsername("zdp");
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
        User result = null;
        try {
            result = userService.save(user);
        } catch (UserMgmtException e) {
            e.printStackTrace();
        }
        Set<User> users = new HashSet<User>();
        System.out.println("添加用户");
        users.add(result);
        String pid = "0000000053cb619f0153ea294cc20001";
        try {
            System.out.println("添加关系");
            projectServcie.addUsers(pid, users);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.println(projectServcie.show("0000000053cb619f0153ea294cc20001"));

    }

}
