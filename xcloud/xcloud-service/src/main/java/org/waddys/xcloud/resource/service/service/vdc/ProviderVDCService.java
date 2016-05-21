package org.waddys.xcloud.resource.service.service.vdc;

import java.util.List;
import java.util.Map;

import org.waddys.xcloud.resource.service.bo.vdc.ProvideVDCStoragePool;
import org.waddys.xcloud.resource.service.bo.vdc.ProviderVDC;
import org.waddys.xcloud.resource.service.exception.vdc.VDCException;

public interface ProviderVDCService {

    public void save(ProviderVDC providerVDC) throws VDCException;

    public ProviderVDC findProviderVDC(String providerVDCId)
            throws VDCException;

    public List<ProvideVDCStoragePool> findStoragePools(String providerVDCId)
            throws VDCException;

    public List<ProviderVDC> findAll() throws VDCException;

    public Map<String, Object> findProviderVDCs(ProviderVDC providerVDC,
            int pageNum, int pageSize) throws VDCException;

    public void updateProviderVDC(ProviderVDC providerVDC) throws VDCException;

    public void delete(String proVDCId) throws VDCException;

    public void expenseProVDC(String proVDCId, Integer vCpuNum,
            Long memorySize, String storagePoolId, Long storageSize)
            throws VDCException;

    public void recycleProVDC(String proVDCId, Integer vCpuNum,
            Long memorySize, String storagePoolId, Long storageSize)
            throws VDCException;

    /**
     * 根据提供者vdc属性查询提供者vdc列表，注意：此方法是为了修改提供者vdc时，查询结果排除自己而用，id为notEqual
     * expressions.add(cb.notEqual(root.get("pVDCId"),
     * providerVDCE.getpVDCId()));
     * 
     * @param providerVDC
     * @return
     * @throws VDCException
     */
    public List<ProviderVDC> findAllProviderVDCs(ProviderVDC providerVDC)
            throws VDCException;

    public void expenseProVDCCpu(String proVDCId, Integer cpuNum)
            throws VDCException;

    public void recycleProVDCCpu(String proVDCId, Integer cpuNum)
            throws VDCException;

    public void expenseProVDCMemory(String proVDCId, Long memorySize)
            throws VDCException;

    public void recycleProVDCMemory(String proVDCId, Long memorySize)
            throws VDCException;

}
