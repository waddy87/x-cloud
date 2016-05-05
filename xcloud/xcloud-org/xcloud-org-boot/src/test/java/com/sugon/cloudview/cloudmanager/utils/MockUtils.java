/**
 * 
 */
package com.sugon.cloudview.cloudmanager.utils;

import java.lang.reflect.Field;
import java.util.Date;

import javax.persistence.Column;

import org.mockito.Mockito;

import com.sugon.cloudview.cloudmanager.org.bo.Organization;
import com.sugon.cloudview.cloudmanager.resource.service.bo.vdc.ComputingPool;
import com.sugon.cloudview.cloudmanager.resource.service.bo.vdc.ProviderVDC;
import com.sugon.cloudview.cloudmanager.resource.service.bo.vdc.StoragePool;
import com.sugon.cloudview.cloudmanager.resource.service.bo.vnet.NetPool;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.vdc.ProviderVDCE;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.vdc.StoragePoolE;
import com.sugon.cloudview.cloudmanager.templet.service.entity.VMTempletE;
import com.sugon.cloudview.cloudmanager.vm.bo.VmHost;
import com.sugon.cloudview.cloudmanager.vm.bo.VmNet;
import com.sugon.cloudview.cloudmanager.vm.constant.RunStatus;
import com.sugon.cloudview.cloudmanager.vm.constant.SourceType;
import com.sugon.cloudview.cloudmanager.vm.constant.VmStatus;

/**
 * @author zhangdapeng
 *
 */
public class MockUtils {

    public static void main(String[] args) {
        // MockUtils.getParameters(StoragePoolE.class);
        System.out.println(Mockito.anyString());
    }

    public static void getParameters(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            System.out.println(f.getName());
        }
    }

    public static void getFieldsValue(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        /**
         * 基本类型、包装类型、String类型
         */
        String[] types = { "java.lang.Integer", "java.lang.Double", "java.lang.Float", "java.lang.Long",
                "java.lang.Short", "java.lang.Byte", "java.lang.Boolean", "java.lang.Char", "java.lang.String", "int",
                "double", "long", "short", "byte", "boolean", "char", "float" };
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                for (String str : types) {
                    if (f.getType().getName().equals(str))
                        System.out.println("字段：" + f.getName() + " 类型为：" + f.getType().getName() + " 值为：" + f.get(obj));
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    public static Object buildObject(Class clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            Class fieldType = field.getType();
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                int length = column.length();
            }
        }
        return null;
    }

    public static Organization mockOrg() {
        Organization result = new Organization();
        return result;
    }

    public static VmHost mockVm() {
        VmHost vmHost = new VmHost();
        vmHost.setName("test-name");
        vmHost.setCreaterId("test-creater");
        vmHost.setOrgId("test-org");
        vmHost.setSourceType(SourceType.ASSIGN);
        vmHost.setCreateTime(new Date());
        vmHost.setVdcId("test-vdc");
        vmHost.setcPoolId("test-cpool");
        vmHost.setsPoolId("test-spool");
        vmHost.setStorCapacity(40L);
        vmHost.setTemplateId("test-template");
        vmHost.setvCpuNumer(2);
        vmHost.setvMemCapacity(4096L);
        vmHost.setRunStatus(RunStatus.CREATING);
        vmHost.setVmStatus(VmStatus.NONE);
        vmHost.setStatus("A");
        vmHost.setIsAssigned(true);
        return vmHost;
    }

    public static VmNet mockVmNet() {
        VmNet result = new VmNet();
        result.setInternalId("nic-100");
        result.setNetId("net-101");
        result.setSubnet("192.168.1.0/24");
        result.setIp("192.168.1.52");
        result.setVmId("vm-100");
        result.setGateway("192.168.1.254");
        result.setDns("8.8.8.8");
        result.setCreateTime(new Date());
        result.setVlan(0);
        return result;
    }

    public static NetPool mockNetPool() {
        NetPool result = new NetPool();
        result.setNetPoolId("netpool-001");
        result.setNetName("netpool-hello");
        result.setIsAvl(true);
        result.setSynDate(new Date());
        result.setSubNet("192.168.1.0/24");
        result.setSubNetNo("192.168.1.0");
        result.setConfigId("config-001");
        result.setVlanNO(0);
        result.setGateway("192.168.1.254");
        result.setDns("8.8.8.8");
        return result;
    }

    public static ComputingPool mockCpool() {
        ComputingPool result = new ComputingPool();
        result.setComputingPoolId("resgroup-779");
        result.setConfigId("ff808081544c44a001544c45ab320001");
        result.setCptName("test-cpool");
        result.setCpuAvlCapacity(53160L);
        result.setCpuTotCapacity(53160L);
        result.setCpuUsedCapacity(0L);
        result.setIsAvl(true);
        result.setIsDistribute(true);
        result.setMemoryAvlCapacity(150669L);
        result.setMemoryTotCapacity(150669L);
        result.setMemoryUsedCapacity(0L);
        result.setSynDate(new Date());
        return result;
    }

    public static StoragePool mockSpool() {
        StoragePool result = new StoragePool();
        result.setName("test-spool");
        result.setConfigId("ff808081544c44a001544c45ab320001");
        result.setIsAvl(true);
        result.setSpSurplus(292L);
        result.setSpTotal(292L);
        result.setSpUsed(292L);
        result.setSynDate(new Date());
        return result;
    }

    public static StoragePoolE mockSpoolE() {
        StoragePoolE result = new StoragePoolE();
        result.setName("test-spool");
        result.setConfigId("ff808081544c44a001544c45ab320001");
        result.setIsAvl(true);
        result.setSpSurplus(292L);
        result.setSpTotal(292L);
        result.setSpUsed(292L);
        result.setSynDate(new Date());
        return result;
    }

    public static ProviderVDC mockVDC() {
        ProviderVDC result = new ProviderVDC();
        result.setCreateDate(new Date());
        result.setName("test-vdc");
        result.setvCpuNum(100);
        result.setvCpuOverplus(100);
        result.setvCpuUsed(0);
        result.setMemorySize(102400L);
        result.setMemoryOverplus(12400L);
        result.setMemoryUsed(0L);
        return result;
    }

    public static ProviderVDCE mockVDCE() {
        ProviderVDCE result = new ProviderVDCE();
        result.setCreateDate(new Date());
        result.setName("test-vdc");
        result.setvCpuNum(100);
        result.setvCpuOverplus(100);
        result.setvCpuUsed(0);
        result.setMemorySize(102400L);
        result.setMemoryOverplus(12400L);
        result.setMemoryUsed(0L);
        return result;
    }

    public static VMTempletE mockTemplate() {
        VMTempletE vmTempletE = new VMTempletE();
        vmTempletE.setCpu(1);
        vmTempletE.setCreateTime(new Date());
        vmTempletE.setUpdateTime(new Date());
        vmTempletE.setStatus("1");
        vmTempletE.setDiskSize(20L);
        vmTempletE.setMemory(4096L);
        vmTempletE.setName("test-template");
        vmTempletE.setOs("Microsoft Windows Server 2003 (64 位)");
        vmTempletE.setRelationId("vm-88888");
        vmTempletE.setVisible("0");
        return vmTempletE;
    }


}
