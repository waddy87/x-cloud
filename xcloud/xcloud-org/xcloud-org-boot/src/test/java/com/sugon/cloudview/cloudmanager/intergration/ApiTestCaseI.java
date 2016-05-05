package com.sugon.cloudview.cloudmanager.intergration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.sugon.cloudview.cloudmanager.Application;
import com.sugon.cloudview.cloudmanager.driver.VmDriver;
import com.sugon.cloudview.cloudmanager.resource.service.service.vdc.ProviderVDCService;
import com.sugon.cloudview.cloudmanager.resource.service.service.vdc.StoragePoolService;
import com.sugon.cloudview.cloudmanager.util.JsonUtil;
import com.sugon.cloudview.cloudmanager.utils.AopTargetUtils;
import com.sugon.cloudview.cloudmanager.utils.MockUtils;
import com.sugon.cloudview.cloudmanager.vm.api.VmApi;
import com.sugon.cloudview.cloudmanager.vm.bo.VmHost;
import com.sugon.cloudview.cloudmanager.vm.bo.VmTask;
import com.sugon.cloudview.cloudmanager.vm.constant.ActionType;
import com.sugon.cloudview.cloudmanager.vm.constant.RunStatus;
import com.sugon.cloudview.cloudmanager.vm.constant.VmStatus;
import com.sugon.cloudview.cloudmanager.vm.dao.entity.VmHostE;
import com.sugon.cloudview.cloudmanager.vm.service.VmService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebIntegrationTest
@SpringApplicationConfiguration(classes = Application.class)
public class ApiTestCaseI {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private VmApi vmApi;

    @Autowired
    private VmService vmService;

    @Mock
    private VmDriver vmDriver;

    @Mock
    private ProviderVDCService vdcService;

    @Mock
    private StoragePoolService sPoolService;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        // ReflectionTestUtils.setField(vmService, "vdcService", vdcService);
        VmHost vmHost = MockUtils.mockVm();
        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                return "called with arguments: " + args;
            }
        }).when(vdcService).expenseProVDC("vdc-666", 2, 2048L, "spool-888", 10240L);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(vmService), "vdcService", vdcService);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(vmService), "vmDriver", vmDriver);
        VmTask vmTask = new VmTask();
        vmTask.setTaskId("task-100");
        Mockito.when(vmDriver.delete(vmHost.getId())).thenReturn(vmTask);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(vmApi), "sPoolService", sPoolService);
        when(vmDriver.create(vmHost)).thenReturn(vmHost);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(vmApi), "vmService", vmService);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void removeVm() throws Exception {
        // set mock data
        VmHost vmHost = MockUtils.mockVm();
        // ReflectionTestUtils.setField(AopTargetUtils.getTarget(vmApi),
        // "sPoolService", sPoolService);
        VmHostE vmHostE = new VmHostE();
        BeanUtils.copyProperties(vmHost, vmHostE);
        vmHostE.setRunStatus(RunStatus.NONE);
        vmHostE.setVmStatus(VmStatus.INITED);
        em.persist(vmHostE);
        VmTask taskInfo = new VmTask();
        taskInfo.setTaskId("task-100");
        Mockito.when(vmDriver.delete(vmHostE.getId())).thenReturn(taskInfo);

        // call the controller method
        vmApi.action(vmHostE.getId(), ActionType.DESTROY, vmHost);
        vmHost = vmApi.show(vmHostE.getId());

        // assertions
        System.out.println(JsonUtil.toJson(vmHost));
        assertNotNull(vmHost);
        assertThat(vmHost.getVmStatus(), equalTo(RunStatus.DELETING));
    }

    public void deleteVm() throws Exception {
        // set mock data
        VmHost vmHost = MockUtils.mockVm();

        // call the controller method
        vmHost = vmApi.create(vmHost);
        vmApi.delete(vmHost.getId());
        vmHost = vmApi.show(vmHost.getId());

        // assertions
        System.out.println(JsonUtil.toJson(vmHost));
        assertNotNull(vmHost);
    }

    public static void main(String[] args) {
        Integer number = 0;
        if (number instanceof Object) {
            System.out.println("hello");
        }
    }

}
