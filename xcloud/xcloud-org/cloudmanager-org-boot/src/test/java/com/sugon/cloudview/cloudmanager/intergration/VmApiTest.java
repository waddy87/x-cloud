package com.sugon.cloudview.cloudmanager.intergration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Before;
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
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.sugon.cloudview.cloudmanager.Application;
import com.sugon.cloudview.cloudmanager.driver.VmDriver;
import com.sugon.cloudview.cloudmanager.resource.service.bo.vnet.NetPool;
import com.sugon.cloudview.cloudmanager.resource.service.service.vdc.ProviderVDCService;
import com.sugon.cloudview.cloudmanager.resource.service.service.vdc.StoragePoolService;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.vdc.ComputingPoolE;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.vdc.ProviderVDCE;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.vdc.StoragePoolE;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.vnet.NetPoolE;
import com.sugon.cloudview.cloudmanager.templet.service.entity.VMTempletE;
import com.sugon.cloudview.cloudmanager.util.JsonUtil;
import com.sugon.cloudview.cloudmanager.utils.AopTargetUtils;
import com.sugon.cloudview.cloudmanager.utils.MockUtils;
import com.sugon.cloudview.cloudmanager.vm.api.VmApi;
import com.sugon.cloudview.cloudmanager.vm.bo.VmHost;
import com.sugon.cloudview.cloudmanager.vm.bo.VmNet;
import com.sugon.cloudview.cloudmanager.vm.bo.VmTask;
import com.sugon.cloudview.cloudmanager.vm.constant.ActionType;
import com.sugon.cloudview.cloudmanager.vm.constant.RunStatus;
import com.sugon.cloudview.cloudmanager.vm.constant.VmStatus;
import com.sugon.cloudview.cloudmanager.vm.dao.entity.VmHostE;
import com.sugon.cloudview.cloudmanager.vm.dao.entity.VmNetE;
import com.sugon.cloudview.cloudmanager.vm.service.VmService;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@WebIntegrationTest
@SpringApplicationConfiguration(classes = Application.class)
public class VmApiTest {

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

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        // ReflectionTestUtils.setField(vmService, "vdcService", vdcService);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(vmService), "vmDriver", vmDriver);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(vmApi), "vmService", vmService);
    }

    @Test
    public void createVm() throws Exception {
        // set mock data
        VmHost vmHost = MockUtils.mockVm();
        // Mockito.when(vmDriver.create(vmHost)).thenReturn(vmHost);
        Mockito.when(vmDriver.create(vmHost)).thenThrow(new Exception("模拟异常！"));
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(vmService), "vdcService", vdcService);
        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                return "called with arguments: " + args;
            }
        }).when(vdcService).expenseProVDC("vdc-666", 2, 2048L, "spool-888", 10240L);

        // call the controller method
        vmHost = vmApi.create(vmHost);

        // assertions
        System.out.println(JsonUtil.toJson(vmHost));
    }

    @Test
    public void removeVm_success() throws Exception {
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

    /**
     * 销毁虚机失败！虚机正在操作中，拒绝删除操作！
     */
    @Test
    public void removeVm_failed() throws Exception {
        // set mock data
        VmHost vmHost = MockUtils.mockVm();
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(vmApi), "sPoolService", sPoolService);
        when(vmDriver.create(vmHost)).thenReturn(vmHost);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(vmService), "vdcService", vdcService);
        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                return "called with arguments: " + args;
            }
        }).when(vdcService).expenseProVDC("vdc-666", 2, 2048L, "spool-888", 10240L);
        vmHost = vmApi.create(vmHost);
        vmHost.setRunStatus(RunStatus.CREATING);
        vmHost.setVmStatus(VmStatus.NONE);
        VmTask vmTask = new VmTask();
        vmTask.setTaskId("task-100");
        Mockito.when(vmDriver.delete(vmHost.getId())).thenReturn(vmTask);

        // call the controller method
        vmApi.action(vmHost.getId(), ActionType.DESTROY, vmHost);
        vmHost = vmApi.show(vmHost.getId());

        // assertions
        System.out.println(JsonUtil.toJson(vmHost));
        assertNotNull(vmHost);
    }

    @Test
    public void showVm() throws Exception {
        // set mock data
        StoragePoolE sPoole = MockUtils.mockSpoolE();
        sPoole.setSpId("spool-001");
        em.merge(sPoole);
        // set mock data
        VMTempletE vmTempletE = MockUtils.mockTemplate();
        em.persist(vmTempletE);
        // set mock data
        ProviderVDCE vdce = MockUtils.mockVDCE();
        em.persist(vdce);
        // set mock data
        ComputingPoolE cPoolE = new ComputingPoolE();
        BeanUtils.copyProperties(MockUtils.mockCpool(), cPoolE);
        em.persist(cPoolE);
        // set mock data
        String templateId = vmTempletE.getRelationId();
        VmHostE vmHostE = new VmHostE();
        BeanUtils.copyProperties(MockUtils.mockVm(), vmHostE);
        vmHostE.setTemplateId(templateId);
        vmHostE.setVdcId(vdce.getpVDCId());
        vmHostE.setcPoolId(cPoolE.getComputingPoolId());
        vmHostE.setsPoolId(sPoole.getSpId());
        em.persist(vmHostE);
        em.flush();

        // call the controller method
        VmHost result = vmApi.show(vmHostE.getId());

        // assertions
        System.out.println(JsonUtil.toJson(result));
        assertThat(result, notNullValue());
        assertThat(result.getsPool(), notNullValue());
        assertThat(result.getVdc(), notNullValue());
    }

    @Test
    public void listVm() throws Exception {
        // set mock data
        for (int i = 0; i < 100; i++) {
            VmHost vmHost = MockUtils.mockVm();
            VmHostE vmHostE = new VmHostE();
            BeanUtils.copyProperties(vmHost, vmHostE);
            String name = "test-vm-" + i;
            vmHostE.setName(name);
            em.persist(vmHostE);
        }
        em.flush();

        // call the controller method
        Page<VmHost> result = vmService.pageAll(null, null);

        // assertions
        assertNotNull(result);
        assertThat(result.getNumberOfElements(), equalTo(100));
        assertThat(result.getContent().size(), equalTo(100));
    }

    @Test
    public void listNet() throws Exception {
        VmHost vmHost = MockUtils.mockVm();
        VmHostE vmHostE = new VmHostE();
        BeanUtils.copyProperties(vmHost, vmHostE);
        em.persist(vmHostE);
        final String VM_ID = vmHostE.getId();
        NetPool netPool = MockUtils.mockNetPool();
        netPool.setNetPoolId("netpool-001");
        netPool.setNetName("netpool-hello");
        NetPoolE netPoolE = new NetPoolE();
        BeanUtils.copyProperties(netPool, netPoolE);
        em.persist(netPoolE);
        NetPoolE netPoolE2 = new NetPoolE();
        BeanUtils.copyProperties(netPool, netPoolE2);
        netPoolE2.setNetPoolId("netpool-002");
        netPoolE2.setNetName("netpool-hi");
        em.persist(netPoolE2);
        VmNet vmNet = MockUtils.mockVmNet();
        VmNetE vmNetE = new VmNetE();
        BeanUtils.copyProperties(vmNet, vmNetE);
        vmNetE.setVmId(VM_ID);
        vmNetE.setNetId("netpool-001");
        VmNetE vmNetE2 = new VmNetE();
        BeanUtils.copyProperties(vmNet, vmNetE2);
        vmNetE2.setVmId(VM_ID);
        vmNetE2.setNetId("netpool-002");
        em.persist(vmNetE);
        em.persist(vmNetE2);

        // call the controller method
        Page<VmNet> page = vmApi.listNet(VM_ID);
        System.out.println(JsonUtil.toJson(page));
    }

}
