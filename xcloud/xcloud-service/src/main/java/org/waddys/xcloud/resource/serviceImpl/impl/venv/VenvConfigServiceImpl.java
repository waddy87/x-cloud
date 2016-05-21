package org.waddys.xcloud.resource.serviceImpl.impl.venv;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.waddys.xcloud.common.base.exception.CloudviewException;
import org.waddys.xcloud.resource.service.bo.vdc.ComputingPool;
import org.waddys.xcloud.resource.service.bo.vdc.StoragePool;
import org.waddys.xcloud.resource.service.bo.venv.VenvConfig;
import org.waddys.xcloud.resource.service.exception.vdc.VDCException;
import org.waddys.xcloud.resource.service.exception.venv.VenvException;
import org.waddys.xcloud.resource.service.exception.vnet.VNetException;
import org.waddys.xcloud.resource.service.service.vdc.ComputingPoolService;
import org.waddys.xcloud.resource.service.service.vdc.StoragePoolService;
import org.waddys.xcloud.resource.service.service.venv.VenvConfigService;
import org.waddys.xcloud.resource.service.service.vnet.NetPoolService;
import org.waddys.xcloud.resource.serviceImpl.dao.entity.venv.VenvConfigE;
import org.waddys.xcloud.resource.serviceImpl.dao.service.venv.VenvConfigDaoService;
import org.waddys.xcloud.resource.serviceImpl.utils.CommonUtils;
import org.waddys.xcloud.templet.service.VMTempletService;
import org.waddys.xcloud.vijava.base.CloudviewExecutor;
import org.waddys.xcloud.vijava.data.ResPool;
import org.waddys.xcloud.vijava.environment.QueryNetPool.QueryNetPoolAnswer;
import org.waddys.xcloud.vijava.environment.QueryNetPool.QueryNetPoolCmd;
import org.waddys.xcloud.vijava.environment.QueryResources.QueryResourceAnswer;
import org.waddys.xcloud.vijava.environment.QueryResources.QueryResourceCmd;
import org.waddys.xcloud.vijava.exception.VirtException;

@Component("venvConfigServiceImpl")
public class VenvConfigServiceImpl implements VenvConfigService {

	private static Logger logger = LoggerFactory.getLogger(VenvConfigServiceImpl.class);

	@Autowired
	private VenvConfigDaoService venvConfigDaoService;
	@Autowired
	private NetPoolService netPoolService;
	@Autowired
	private ComputingPoolService computingPoolService;
	@Autowired
	private CloudviewExecutor cloudviewExecutor;
	@Autowired
	private StoragePoolService storagePoolService;
	@Autowired
	private VMTempletService vMTempletService;
//	@Autowired
//	private OldVirtualMachineService oldVirtualMachineService;

	@Override
	public List<VenvConfig> findConfigInfos() {
		List<VenvConfigE> configInfos = venvConfigDaoService.findConfigInfos();
		List<VenvConfig> configInfos1 = new ArrayList<VenvConfig>();
		if (configInfos.size() > 0) {
			for (VenvConfigE venvconfigInfoE : configInfos) {
				VenvConfig venvconfigInfo = new VenvConfig();
				CommonUtils.converterMethod(venvconfigInfo, venvconfigInfoE);
				configInfos1.add(venvconfigInfo);
			}
			return configInfos1;
		}
		return null;
	}

	@Override
	public VenvConfig addVenvConfig(VenvConfig venvConfig) throws VenvException {
		logger.debug("step into method addVenvConfig(),params:" + venvConfig);
		VenvConfigE venvConfigE = new VenvConfigE();
		CommonUtils.converterMethod(venvConfigE, venvConfig);
		VenvConfigE venvConfigE2 = venvConfigDaoService.addVenvConfig(venvConfigE);
		logger.debug("addVenvConfig() return :" + venvConfigE2);
		VenvConfig venvConfignew = new VenvConfig();
		CommonUtils.converterMethod(venvConfignew, venvConfigDaoService.addVenvConfig(venvConfigE));
		return venvConfignew;
	}

	@Override
	public boolean testVenvConfig(VenvConfig venvConfig) {
		return false;
	}

	@Override
	public Page<VenvConfig> findVenvConfigs(Pageable pageable) {
		// Page<VenvConfigE> venvConfigEs = venvConfigDaoService
		// .findVenvConfigs(pageable);
		// return venvConfigEs.map(Converters.converter);
		return null;
	}

	@Override
	public void updateVenvConfig(VenvConfig venvConfig) {
		VenvConfigE venvConfigE = new VenvConfigE();
		CommonUtils.converterMethod(venvConfigE, venvConfig);
		venvConfigDaoService.updateVenvConfig(venvConfigE);
	}

	@Override
	public void delVenvConfig(String configId) {
		VenvConfigE venvConfigE = this.venvConfigDaoService.findVenvConfigById(configId);
		venvConfigDaoService.delVenvConfig(venvConfigE);
	}
	/**
	 * 返回值0：成功；1：同步模板失败；2:同步利旧虚拟机失败;-1：同步数据失败
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String synDataVenvConfig(VenvConfig venvconfig) throws VenvException {
		/**
		 * 保存虚拟化环境到数据库
		 */
		venvconfig = addVenvConfig(venvconfig);
		/**
		 * 网络池
		 */
		logger.debug("开始同步网络池数据");
		QueryNetPoolCmd qcmd = new QueryNetPoolCmd().setIp(venvconfig.getiP());
		QueryNetPoolAnswer qanswer;
		try {
			qanswer = cloudviewExecutor.execute(qcmd);
		} catch (VirtException e) {
			logger.debug("调用cloudviewExecutor.execute(QueryNetPoolCmd)接口失败" + e.getMessage());
			throw new VenvException("调用cloudviewExecutor接口失败");
		}
		List<org.waddys.xcloud.vijava.data.NetPool> lstnetPool = qanswer.getNetList();
		logger.debug("QueryNetPoolAnswer，返回网络结果：" + lstnetPool.toString() + ",网络池数量：" + lstnetPool.size());
		if (lstnetPool != null && lstnetPool.size() > 0) {
			for (org.waddys.xcloud.vijava.data.NetPool vijavanetPool : lstnetPool) {
				org.waddys.xcloud.resource.service.bo.vnet.NetPool netPool = new org.waddys.xcloud.resource.service.bo.vnet.NetPool();
				netPool.setNetPoolId(vijavanetPool.getId());
				netPool.setNetName(vijavanetPool.getName());
				netPool.setConfigId(venvconfig.getConfigId());
				netPool.setVlanNO(vijavanetPool.getVlan());
				netPool.setDns("0.0.0.0");
				netPool.setGateway("0.0.0.0");
				netPool.setIsAvl(true);
				netPool.setSubNet("0.0.0.0/24");
				netPool.setSynDate(new Date());
				try {
					netPoolService.saveNetPool(netPool);
					logger.debug("同步网络池数据成功");
				} catch (VNetException e) {
					logger.debug("调用netPoolService.saveNetPool()保存网络池数据失败" + e.getMessage());
					throw new VenvException("接入虚拟化环境成功,保存网络池数据失败");
				}
			}
		}

		/**
		 * 添加资源池
		 */
		logger.debug("开始同步资源池数据");
		QueryResourceCmd cmd = new QueryResourceCmd().setIp(venvconfig.getiP());
		QueryResourceAnswer answer;
		try {
			answer = cloudviewExecutor.execute(cmd);
		} catch (VirtException e) {
			logger.debug("调用cloudviewExecutor.execute(QueryResourceCmd)接口失败" + e.getMessage());
			throw new VenvException("调用cloudviewExecutor接口失败");
		}
		List<ResPool> lstresPool = answer.getRpList();
		logger.debug("QueryResourceAnswer，返回资源池数量：" + lstresPool.size());
		if (lstresPool != null && lstresPool.size() > 0) {
			for (ResPool resPool : lstresPool) {
				ComputingPool computingPool_resource = new ComputingPool();
				computingPool_resource.setComputingPoolId(resPool.getId());
				/*
				 * 集群 资源池id不存 computingPool_resource.setRpId("123456");
				 */
				computingPool_resource.setCptName(resPool.getName());
				computingPool_resource.setCpuTotCapacity(resPool.getCpuTotal());
				computingPool_resource.setCpuUsedCapacity(resPool.getCpuUsed());
				computingPool_resource.setCpuAvlCapacity(resPool.getCpuAvailable());
				computingPool_resource.setMemoryTotCapacity(resPool.getMemoryTotal());
				computingPool_resource.setMemoryUsedCapacity(resPool.getMemoryUsed());
				computingPool_resource.setMemoryAvlCapacity(resPool.getMenoryAvailable());
				computingPool_resource.setSynDate(new Date());
				computingPool_resource.setConfigId(venvconfig.getConfigId());
				computingPool_resource.setIsAvl(true);
				computingPool_resource.setIsDistribute(false);
				List<org.waddys.xcloud.vijava.data.StoragePool> vijavastoragePools = resPool
						.getStoragePools();
				List<StoragePool> storagePools = new ArrayList<StoragePool>();
				if (vijavastoragePools != null && vijavastoragePools.size() > 0) {
					for (org.waddys.xcloud.vijava.data.StoragePool vijavastoragePool : vijavastoragePools) {
						/**
						 * 存储池
						 */
						StoragePool storagePool = new StoragePool();
						storagePool.setSpId(vijavastoragePool.getId());
						storagePool.setName(vijavastoragePool.getName());
						storagePool.setSpTotal(vijavastoragePool.getTotal());
						storagePool.setSpUsed(vijavastoragePool.getUsed());
						storagePool.setSpSurplus(vijavastoragePool.getAvailable());
						storagePool.setIsAvl(true);
						storagePool.setSynDate(new Date());
						storagePool.setConfigId(venvconfig.getConfigId());
						storagePools.add(storagePool);
						computingPool_resource.setStoragePools(storagePools);
					}

				}
				try {
					computingPoolService.save(computingPool_resource);
				} catch (VDCException e) {
					logger.debug("调用computingPoolService.save()保存计算池数据失败" + e.getMessage());
					throw new VenvException("接入虚拟化环境成功,保存计算池数据失败" + e.getMessage());
				}
			}
		}
		logger.debug("同步资源池数据数据完成");
		try {
			/**
			 * 同步模板数据
			 */
			logger.debug("开始同步模板数据");
			vMTempletService.synVMTemplet("");
			logger.debug("同步模板数据操作结束");
		} catch (CloudviewException e) {
			logger.debug("接入虚拟化环境成功,同步模板数据失败" + e.getMessage());
			new VenvException("接入虚拟化环境成功,同步模板数据失败");
			return "1";
		} catch (Exception e) {
			logger.debug("接入虚拟化环境成功,同步模板数据失败" + e.getMessage());
			new VenvException("接入虚拟化环境成功,同步模板数据失败");
			return "1";
		}
//		try {
//			/**
//			 * 同步利旧虚拟机数据
//			 */
//			logger.debug("开始同步利旧虚拟机数据");
//			oldVirtualMachineService.syncVM();
//			logger.debug("同步利旧虚拟机数据操作结束");
//		} catch (CloudviewException e) {
//			logger.debug("同步利旧虚拟机数据失败" + e.getMessage());
//			new VenvException("同步利旧虚拟机数据失败");
//			return "2";
//		} catch (Exception e) {
//			logger.debug("同步模板数据失败" + e.getMessage());
//			new VenvException("同步利旧虚拟机数据失败");
//			return "2";
//		}
		return "0";
	}

	@Override
	public VenvConfig findVenvConfigByconfigId(String configId) {
		VenvConfigE venvConfigE = this.venvConfigDaoService.findVenvConfigById(configId);
		VenvConfig venvConfig = new VenvConfig();
		CommonUtils.converterMethod(venvConfig, venvConfigE);
		return venvConfig;
	}

	@Override
	public Page<VenvConfig> findVenvConfig(VenvConfig venvConfig, Pageable pageable) {
		// Page<VenvConfigE> venvConfigEs = venvConfigDaoService
		// .findByconfigNameContaining(venvConfig.getConfigName(),
		// pageable);
		// return venvConfigEs.map(Converters.converter);
		return null;
	}
/**
 * 返回值0：成功；1：同步模板失败；2:同步利旧虚拟机失败;-1：同步数据失败
 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String synDataVenvConfigCompare(String configId, String ip) throws VenvException {
		try {
			// 网络池
			logger.debug("同步虚拟环境" + configId + "网络数据");
			netPoolService.synNetPoolData(configId, ip);
			logger.debug("同步网络数据操作完成");
		} catch (VNetException e) {
			logger.debug("同步网络数据失败" + e.getMessage());
			throw new VenvException("同步网络数据失败" + e.getMessage());
		}
		// 资源池
		logger.debug("同步资源池");
		synComputingPoolData(configId, ip);
		logger.debug("同步资源池操作完成");
		try {
			// 同步模板数据
			logger.debug("同步模板");
			vMTempletService.synVMTemplet("");
			logger.debug("同步模板操作完成");
		} catch (CloudviewException ex) {
			logger.debug("同步模板数据失败" + ex.getMessage());
			new VenvException("同步模板数据失败");
			return "1";
		} catch (Exception ex) {
			logger.debug("同步模板数据失败" + ex.getMessage());
			new VenvException("同步模板数据失败");
			return "1";
		}
//		try {
//			// 同步利旧虚拟机数据
//			logger.debug("利旧虚拟机数据");
//			oldVirtualMachineService.syncVM();
//			logger.debug("利旧虚拟机数据操作完成");
//		} catch (CloudviewException ex) {
//			logger.debug("接入虚拟化环境成功,同步利旧虚拟机数据失败" + ex.getMessage());
//			new VenvException("接入虚拟化环境成功,同步利旧虚拟机失败");
//			return "2";
//		} catch (Exception ex) {
//			logger.debug("接入虚拟化环境成功,同步利旧虚拟机数据失败" + ex.getMessage());
//			new VenvException("接入虚拟化环境成功,同步利旧虚拟机数据失败");
//			return "2";
//		}
		return "0";
	}

	public void synComputingPoolData(String configId, String ip) throws VenvException {
		try {
			logger.debug("step into method synComputingPoolData(),param configId=" + configId + ",ip=" + ip);
			QueryResourceCmd cmd = new QueryResourceCmd().setIp(ip);
			logger.debug("开始执行，同步资源池数据：" + System.currentTimeMillis());
			QueryResourceAnswer answerResource = cloudviewExecutor.execute(cmd);
			logger.debug("完成，同步资源池数据：" + System.currentTimeMillis());
			if (answerResource != null) {
				List<ResPool> cloudvmresPools = answerResource.getRpList();
				logger.debug("cloudvm资源池池数量：" + cloudvmresPools.size()+","+cloudvmresPools);
				List<ComputingPool> dbcomputnigPools = computingPoolService.findByIsAvl(true);
				logger.debug("数据库中可用计算池数量：" + dbcomputnigPools.size());
				// TO DO 得到数据库中所有的存储池
				List<StoragePool> dbstoragePools = storagePoolService.findAllStoragePools();
				logger.debug("数据库中存储池数量：" + dbstoragePools.size());

				List<String> dbStorageIds = new ArrayList<String>();
				List<String> dbStorageIdDels = new ArrayList<String>();
				for (StoragePool storagePool : dbstoragePools) {
					dbStorageIds.add(storagePool.getSpId());
					dbStorageIdDels.add(storagePool.getSpId());
				}

				List<String> clouvmResIds = new ArrayList<String>();
				List<String> clouvmResIdsDel = new ArrayList<String>();
				for (ResPool resPool : cloudvmresPools) {
					clouvmResIds.add(resPool.getId());
					clouvmResIdsDel.add(resPool.getId());
				}
				List<String> dbcomputingPoolIds = new ArrayList<String>();
				List<String> dbcomputingPoolIdsDel = new ArrayList<String>();
				for (ComputingPool computingPool : dbcomputnigPools) {
					dbcomputingPoolIds.add(computingPool.getComputingPoolId());
					dbcomputingPoolIdsDel.add(computingPool.getComputingPoolId());
				}
				/*
				 * 修改和新增 1.资源池是新增的，先做保存操作； 2.对已存在的资源池做更新操作3.对比该资源池内，可用存储池的情况；
				 */
				compareResourceAdd(cloudvmresPools, configId);
				// 将删除的数据置为不可用
				compareResourceDel(clouvmResIdsDel, dbcomputingPoolIdsDel, dbcomputnigPools);
			}
		} catch (VirtException e) {
			throw new VenvException(e.getMessage());
		} catch (VDCException e) {
			throw new VenvException(e.getMessage());
		}

	}

	public void compareResourceAdd(List<ResPool> cloudvmresPools, String configId) throws VenvException {
		try {
			for (ResPool resPool : cloudvmresPools) {
				// 遍历所有底层资源池，如果是已存在的执行更新操作，如果是新的数据，执行新增操作
				ComputingPool computingPool_resource=computingPoolService.findComputingPool(resPool.getId());
				if(computingPool_resource!=null&&computingPool_resource.getComputingPoolId()==null){
					computingPool_resource.setComputingPoolId(resPool.getId());
					computingPool_resource.setIsAvl(true);
					computingPool_resource.setIsDistribute(false);
					computingPool_resource.setConfigId(configId);
				}
				computingPool_resource.setCptName(resPool.getName());
				computingPool_resource.setCpuTotCapacity(resPool.getCpuTotal());
				computingPool_resource.setCpuUsedCapacity(resPool.getCpuUsed());
				computingPool_resource.setCpuAvlCapacity(resPool.getCpuAvailable());
				computingPool_resource.setMemoryTotCapacity(resPool.getMemoryTotal());
				computingPool_resource.setMemoryUsedCapacity(resPool.getMemoryUsed());
				computingPool_resource.setMemoryAvlCapacity(resPool.getMenoryAvailable());
				computingPool_resource.setSynDate(new Date());
				computingPool_resource = computingPoolService.save(computingPool_resource);
				List<org.waddys.xcloud.vijava.data.StoragePool> vijavastoragePools = resPool
						.getStoragePools();
				logger.debug("cloudvm接口数据：资源池"+resPool+"下可用存储池" + vijavastoragePools);
				List<String> vijavaStoragePoolIds = new ArrayList<String>();
				for (org.waddys.xcloud.vijava.data.StoragePool vijavastoragePool : vijavastoragePools) {
					vijavaStoragePoolIds.add(vijavastoragePool.getId());
					
				}
				// 资源池中的存储池添加了一个，分为新增的，和已存在的2种
				compareStorageAdd(computingPool_resource, vijavastoragePools, configId);
			}
		} catch (VDCException e) {
			throw new VenvException(e.getMessage());
		}
	}

	public void compareResourceDel(List<String> clouvmResIds, List<String> dbcomputingPoolIds,
			List<ComputingPool> dbcomputnigPools) throws VenvException {
		try {
			// cloudvm中记录在db记录中没有，是新增； db中记录在cloudvm中记录没有，是删除
			dbcomputingPoolIds.removeAll(clouvmResIds);// db中记录在cloudvm中记录没有，是删除
			for (String dbcomputingPoolId : dbcomputingPoolIds) {
				for (ComputingPool computingPool : dbcomputnigPools) {
					if (dbcomputingPoolId.equals(computingPool.getComputingPoolId())) {
						computingPool.setIsAvl(false);
						computingPool.setSynDate(new Date());
						computingPoolService.save(computingPool);
					}
				}
			}
		} catch (VDCException e) {
			throw new VenvException(e.getMessage());
		}
	}

	public void compareStorageAdd(ComputingPool computingPool_resource,
			List<org.waddys.xcloud.vijava.data.StoragePool> vijavastoragePools, String configId)
					throws VDCException {
		logger.debug("step into method compareStorageAdd(),同步资源池" + computingPool_resource.getComputingPoolId()
				+ ",下可用的存储池");
		/*
		 * 该资源池可用的存储池： 1.新增加的是，新的，的可用存储池; 2.新增加的是，已存在，的可用存储池
		 * 首先遍历，该资源池所有可用存储池，通过new对象保存，如果不存在该存储池，执行新增操作，如果存在该存储池执行更新操作
		 */
		List<StoragePool> storagePools = new ArrayList<StoragePool>();
		for (org.waddys.xcloud.vijava.data.StoragePool vijavastoragePool : vijavastoragePools) {
			StoragePool storagePool = new StoragePool();
			storagePool.setSpId(vijavastoragePool.getId());
			storagePool.setName(vijavastoragePool.getName());
			storagePool.setSpTotal(vijavastoragePool.getTotal());
			storagePool.setSpUsed(vijavastoragePool.getUsed());
			storagePool.setSpSurplus(vijavastoragePool.getAvailable());
			storagePool.setIsAvl(true);
			storagePool.setSynDate(new Date());
			storagePool.setConfigId(configId);
			storagePools.add(storagePool);
		}
		if (storagePools.size() > 0) {
			computingPool_resource.setStoragePools(storagePools);
			computingPoolService.save(computingPool_resource);
		}
	}

	public void compareStorageDel(ComputingPool computingPool_resource,
			List<org.waddys.xcloud.vijava.data.StoragePool> vijavastoragePools, String configId)
					throws VDCException {
		logger.debug("step into method compareStorageAdd(),同步资源池" + computingPool_resource.getComputingPoolId()
				+ ",下可用的存储池");
		/*
		 * 该资源池可用的存储池： 1.新增加的是，新的，的可用存储池; 2.新增加的是，已存在，的可用存储池
		 * 首先遍历，该资源池所有可用存储池，通过new对象保存，如果不存在该存储池，执行新增操作，如果存在该存储池执行更新操作
		 */
		List<StoragePool> storagePools = new ArrayList<StoragePool>();
		for (org.waddys.xcloud.vijava.data.StoragePool vijavastoragePool : vijavastoragePools) {
			StoragePool storagePool = new StoragePool();
			storagePool.setSpId(vijavastoragePool.getId());
			storagePool.setName(vijavastoragePool.getName());
			storagePool.setSpTotal(vijavastoragePool.getTotal());
			storagePool.setSpUsed(vijavastoragePool.getUsed());
			storagePool.setSpSurplus(vijavastoragePool.getAvailable());
			storagePool.setIsAvl(true);
			storagePool.setSynDate(new Date());
			storagePool.setConfigId(configId);
			storagePools.add(storagePool);
		}
		if (storagePools.size() > 0) {
			computingPool_resource.setStoragePools(storagePools);
			computingPoolService.save(computingPool_resource);
		}
	}
}
