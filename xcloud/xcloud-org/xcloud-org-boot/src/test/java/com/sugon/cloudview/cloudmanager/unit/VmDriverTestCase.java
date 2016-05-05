package com.sugon.cloudview.cloudmanager.unit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.sugon.cloudview.cloudmanager.driver.VmDriver;
import com.sugon.cloudview.cloudmanager.utils.MockUtils;
import com.sugon.cloudview.cloudmanager.vijava.base.CloudviewExecutor;
import com.sugon.cloudview.cloudmanager.vijava.vm.QueryVM.QueryVMAnswer;
import com.sugon.cloudview.cloudmanager.vijava.vm.QueryVM.QueryVMCmd;
import com.sugon.cloudview.cloudmanager.vm.bo.VmHost;
import com.sugon.cloudview.cloudmanager.vm.constant.RunStatus;
import com.sugon.cloudview.cloudmanager.vm.constant.VmStatus;

public class VmDriverTestCase {

    @InjectMocks
    private VmDriver vmDriver;

    @Mock
    private CloudviewExecutor cloudviewExecutor;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        // ReflectionTestUtils.setField(vmDriver, "cloudviewExecutor",
        // cloudviewExecutor);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        fail("Not yet implemented");
    }

    @Mock
    private VmHost vmMock;

    @Test
    public void testFind() throws Exception {
        // set mock data
        System.out.println(vmMock.getName());
        VmHost vmHost = MockUtils.mockVm();
        vmHost.setRunStatus(RunStatus.CREATING);
        vmHost.setVmStatus(VmStatus.NONE);
        String vmId = "vm-100";
        vmHost.setInternalId(vmId);
        QueryVMCmd cmd = new QueryVMCmd();
        cmd.setVmId(vmId);
        QueryVMAnswer answer = new QueryVMAnswer();
        answer.setSuccess(true);
        // when(cloudviewExecutor.execute(cmd)).thenReturn(answer);
        Mockito.when(cloudviewExecutor.execute(cmd)).thenReturn(answer);
        // Mockito.doReturn(answer).when(cloudviewExecutor.execute(cmd));

        // call the controller method
        vmHost = vmDriver.refreshVmById(vmId, vmHost);

        // assertions
        assertNotNull(vmHost);
    }

}
