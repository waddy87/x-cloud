package com.sugon.cloudview.cloudmanager.resource.serviceImpl.impl.vnet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sugon.cloudview.cloudmanager.resource.service.bo.vnet.NetPool;
import com.sugon.cloudview.cloudmanager.resource.service.exception.vnet.VNetException;
import com.sugon.cloudview.cloudmanager.resource.service.service.vnet.NetPoolService;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.entity.vnet.NetPoolE;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.dao.service.vnet.NetPoolDaoService;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.utils.CommonUtils;
import com.sugon.cloudview.cloudmanager.resource.serviceImpl.utils.Converters;
import com.sugon.cloudview.cloudmanager.vijava.base.CloudviewExecutor;
import com.sugon.cloudview.cloudmanager.vijava.environment.QueryNetPool.QueryNetPoolAnswer;
import com.sugon.cloudview.cloudmanager.vijava.environment.QueryNetPool.QueryNetPoolCmd;
import com.sugon.cloudview.cloudmanager.vijava.exception.VirtException;

@Service("netPoolServiceImpl")
public class NetPoolServiceImpl implements NetPoolService {
	private static Logger logger = LoggerFactory.getLogger(NetPoolServiceImpl.class);
	@Autowired
	private NetPoolDaoService netPoolDaoService;
	@Autowired
	private CloudviewExecutor cloudviewExecutor;

	@Override
	public List<NetPool> QueryNetPoolByPage(int pageNum, int pageSize, String inputName, Boolean isAvl) {
		Pageable pageable = new PageRequest(pageNum - 1, pageSize);
		Page<NetPoolE> netPoolPage = netPoolDaoService.QueryNetPoolByPage(inputName, isAvl, pageable);
		List<NetPoolE> selectNetPoolE = netPoolPage.getContent();
		List<NetPool> resultList = getNetPoolListBydblist(selectNetPoolE);
		return resultList;
	}

	@Override
	public long count(NetPool netPool, Boolean isAvl) throws VNetException {
		NetPoolE netPoolE = new NetPoolE();
		long total = 0;
		try {
			CommonUtils.converterMethod(netPoolE, netPool);
			total = netPoolDaoService.count(netPoolE, isAvl);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new VNetException("查询总数失败！");
		}
		return total;
	}

	@Override
	public void delNetPoolByConfigId(String configId) throws VNetException {
		logger.debug("step method delNetPoolByConfigId(),params:" + configId);
		try {
			if (configId != "" && configId != null) {
				netPoolDaoService.delNetPoolByConfigId(configId);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new VNetException("根据configId删除网络池失败！");
		}
	}

	@Override
	public List<NetPool> QueryNetPoolAll() {
		List<NetPoolE> allNetPoolE = netPoolDaoService.QueryNetPoolAll();
		List<NetPool> netPoollAll = getNetPoolListBydblist(allNetPoolE);
		return netPoollAll;
	}

	private List<NetPool> getNetPoolListBydblist(List<NetPoolE> allNetPoolE) {
		List<NetPool> netPoolAll = new ArrayList<NetPool>();
		for (NetPoolE netPoolE : allNetPoolE) {
			NetPool pool = new NetPool();
			CommonUtils.converterMethod(pool, netPoolE);
			netPoolAll.add(pool);
		}
		return netPoolAll;
	}

	@Override
	public NetPool saveNetPool(NetPool netpool) throws VNetException {
		NetPoolE netPoolE = new NetPoolE();
		CommonUtils.converterMethod(netPoolE, netpool);
		netPoolDaoService.saveNetPool(netPoolE);
		return null;
	}

	@Override
	public boolean distributionNetPool(List<String> netPools, String orgId) {
		boolean result = true;
		try {
			netPoolDaoService.distributionOrg(netPools, orgId);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@Override
	public void delNetPool(String netPoolId) throws VNetException {
		netPoolDaoService.deleteNetPool(netPoolId);
	}

	@Override
	public NetPool updateNetPool(NetPool netPool) {
		NetPoolE netpoolE = Converters.vnetConvertDataBase.convert(netPool);
		netpoolE = netPoolDaoService.updateNetPool(netpoolE);
		netPool = Converters.vnetConvertBusness.convert(netpoolE);
		return netPool;
	}

	@Override
	public NetPool QueryNetpoolById(String poolId) {
		NetPoolE netpoolE = netPoolDaoService.findNetPoolEByNetPoolId(poolId);
		NetPool netPool = new NetPool();
		CommonUtils.converterMethod(netPool, netpoolE);
		String a[] = netPool.getSubNet().split("/");
		netPool.setSubNetNo(a[1]);
		netPool.setSubNet(a[0]);
		return netPool;
	}

	@Override
	public List<NetPool> queryVlanOrGatewayOrSubnetOrDns(Integer vlanno, String gateway, String subnet, String dns,
			int pageNum, int pageSize) {
		Pageable pageable = new PageRequest(pageNum, pageSize);
		Page<NetPoolE> poolPages = netPoolDaoService.queryVlanOrGatewayOrSubnetOrDns(vlanno, gateway, subnet, dns,
				pageable);
		List<NetPoolE> poolList = poolPages.getContent();
		List<NetPool> resultList = getNetPoolListBydblist(poolList);
		return resultList;
	}

	@Override
	public List<NetPool> QueryNetpoolByorgId(String orgId) {
		logger.debug("step into method QueryNetpoolByorgId(),param orgId=" + orgId);
		if (orgId != null && orgId != "") {
			List<NetPoolE> lstNetPoolE = netPoolDaoService.QueryNetpoolByorgId(orgId);
			List<NetPool> lstNetPool = new ArrayList<NetPool>();
			for (NetPoolE netPoolE : lstNetPoolE) {
				NetPool netPool = new NetPool();
				CommonUtils.converterMethod(netPool, netPoolE);
				lstNetPool.add(netPool);
			}
			logger.debug("return lstNetPool" + lstNetPool.toString() + "lstNetPool.size()" + lstNetPool.size());
			return lstNetPool;
		} else {
			logger.debug("参数为空");
			return null;
		}
	}

	@Override
	public void synNetPoolData(String configId, String ip) throws VNetException {
		logger.debug("执行网络同步操作step into method synNetPoolData(),param configId=" + configId);
		try {
			QueryNetPoolCmd qcmd = new QueryNetPoolCmd().setIp(ip);
			logger.debug("获取QueryNetPoolAnswer接口中网络记录:  " + System.currentTimeMillis());
			QueryNetPoolAnswer qanswer = cloudviewExecutor.execute(qcmd);
			logger.debug("操作完成:  " + System.currentTimeMillis());
			if (qanswer != null) {
				logger.debug("同步虚拟环境:" + configId + ",网络数据");
				List<NetPool> dblstNetpool = findByIsAvl(true);
				logger.debug("数据库中网络记录数量：" + dblstNetpool.size());
				List<String> dbIds = new ArrayList<String>();
				List<String> dbIdsDel = new ArrayList<String>();
				List<String> dbIdsMod = new ArrayList<String>();
				for (NetPool netPool : dblstNetpool) {
					dbIds.add(netPool.getNetPoolId());
					dbIdsDel.add(netPool.getNetPoolId());
					dbIdsMod.add(netPool.getNetPoolId());
				}

				List<com.sugon.cloudview.cloudmanager.vijava.data.NetPool> cloudvmlstnetPool = qanswer.getNetList();
				logger.debug("cloudvm网络记录数量：" + cloudvmlstnetPool.size());
				List<String> cloudvmIds = new ArrayList<String>();
				List<String> cloudvmIdsDel = new ArrayList<String>();
				List<String> cloudvmIdsMod = new ArrayList<String>();
				for (com.sugon.cloudview.cloudmanager.vijava.data.NetPool netPool : cloudvmlstnetPool) {
					cloudvmIds.add(netPool.getId());
					cloudvmIdsDel.add(netPool.getId());
					cloudvmIdsMod.add(netPool.getId());
				}
				// 将新增的数据添加
				compareAdd(cloudvmIds, dbIds, cloudvmlstnetPool, dblstNetpool, configId);
				// 将删除的数据置为不可用
				compareDel(cloudvmIdsDel, dbIdsDel, cloudvmlstnetPool, dblstNetpool);
				// TO DO 将修改的数据更新
				compareMod(cloudvmIdsMod, dbIdsMod, cloudvmlstnetPool, dblstNetpool);
			}
		} catch (VirtException e) {
			throw new VNetException(e.getMessage());
		}
	}

	// 当数据有增加时
	public void compareAdd(List<String> cloudvmIds, List<String> dbIds,
			List<com.sugon.cloudview.cloudmanager.vijava.data.NetPool> cloudvmlstnetPool, List<NetPool> dblstNetpool,
			String configId) throws VNetException {
		// cloudvm中记录在db记录中没有，是新增； db中记录在cloudvm中记录没有，是删除
		cloudvmIds.removeAll(dbIds);// cloudvm中记录在db记录中没有，是新增
		for (String cloudvmId : cloudvmIds) {
			for (com.sugon.cloudview.cloudmanager.vijava.data.NetPool vijavanetPool : cloudvmlstnetPool) {
				if (cloudvmId.equals(vijavanetPool.getId())) {
					NetPool netPool = new NetPool();
					netPool.setNetPoolId(vijavanetPool.getId());
					netPool.setNetName(vijavanetPool.getName());
					netPool.setConfigId(configId);
					netPool.setVlanNO(vijavanetPool.getVlan());
					netPool.setDns("0.0.0.0");
					netPool.setGateway("0.0.0.0");
					netPool.setIsAvl(true);
					netPool.setSubNet("0.0.0.0/24");
					netPool.setSynDate(new Date());
					saveNetPool(netPool);
				}
			}
		}
	}

	// 当数据有减少时
	public void compareDel(List<String> cloudvmIds, List<String> dbIds,
			List<com.sugon.cloudview.cloudmanager.vijava.data.NetPool> cloudvmlstnetPool, List<NetPool> dblstNetpool)
					throws VNetException {
		// cloudvm中记录在db记录中没有，是新增； db中记录在cloudvm中记录没有，是删除
		dbIds.removeAll(cloudvmIds);// db中记录在cloudvm中记录没有，是删除
		for (String dbId : dbIds) {
			for (NetPool netPool : dblstNetpool) {
				if (dbId.equals(netPool.getNetPoolId())) {
					netPool.setIsAvl(false);
					netPool.setSynDate(new Date());
					saveNetPool(netPool);
				}
			}
		}
	}

	// 当数据有修改时
	public void compareMod(List<String> cloudvmIds, List<String> dbIds,
			List<com.sugon.cloudview.cloudmanager.vijava.data.NetPool> cloudvmlstnetPool, List<NetPool> dblstNetpool)
					throws VNetException {
		/**
		 * cloudvm中记录在db记录中没有，是新增；
		 * db中记录在cloudvm中记录没有，是删除，cloudvm中记录与db中相等的记录,做修改
		 */
		List<String> sameList = CommonUtils.getSamesection(cloudvmIds, dbIds);
		for (String sameId : sameList) {
			for (com.sugon.cloudview.cloudmanager.vijava.data.NetPool vijavanetPool : cloudvmlstnetPool) {
				if (sameId.equals(vijavanetPool.getId())) {
					NetPool netPool = QueryNetpoolById(sameId);
					netPool.setNetPoolId(vijavanetPool.getId());
					netPool.setVlanNO(vijavanetPool.getVlan());
					netPool.setSynDate(new Date());
					netPool.setSubNet(netPool.getSubNet() + "/" + netPool.getSubNetNo());
					saveNetPool(netPool);
				}
			}
		}
	}

	@Override
	public List<NetPool> findByIsAvl(Boolean isAvl) {
		List<NetPoolE> lstNetPoolE = netPoolDaoService.findByIsAvl(isAvl);
		List<NetPool> lstNetPool = new ArrayList<NetPool>();
		for (NetPoolE netPoolE : lstNetPoolE) {
			NetPool netPool = new NetPool();
			CommonUtils.converterMethod(netPool, netPoolE);
			lstNetPool.add(netPool);
		}
		return lstNetPool;
	}

	@Override
	public void recycleNetpoolByorgId(String orgId) throws VNetException {

		List<NetPool> netpools = QueryNetpoolByorgId(orgId);
		logger.debug("待回收网络池数量" + netpools.size());
		if (netpools != null && netpools.size() > 0) {
			for (NetPool netPool : netpools) {
				netPool.setOrgId("");
				netPool.setOrgName("");
				updateNetPool(netPool);
			}
		}
		logger.debug("根据orgID回收网络池成功");
	}

	@Override
	public void updateNetpoolByNetPool(NetPool netPool) throws VNetException {
		logger.debug("setp into method updateNetpoolByNetPool(),param netPool=" + netPool);
		List<NetPool> lst = QueryNetpoolByorgId(netPool.getOrgId());
		for (NetPool net : lst) {
			net.setOrgId(netPool.getOrgId());
			net.setOrgName(netPool.getOrgName());
			NetPool newNetPool = updateNetPool(netPool);
			logger.debug("保存后的对象" + newNetPool);
		}
	}

    @Override
    public List<NetPool> findByNetPool(NetPool netPool) {
        logger.debug("setp into method findByNetPool(),param NetPool=" + netPool);
        List<NetPool> lstNetpool = new ArrayList<NetPool>();
        NetPoolE netPoolE = new NetPoolE();
        CommonUtils.converterMethod(netPoolE, netPool);
        List<NetPoolE> lst = netPoolDaoService.findByNetPool(netPoolE);
        logger.debug("查询结果：" + lst);
        if (lst == null) {
            return lstNetpool;
        }
        for (NetPoolE netPoolE2 : lst) {
            NetPool netPoolnew = new NetPool();
            CommonUtils.converterMethod(netPoolnew, netPoolE2);
            lstNetpool.add(netPoolnew);
        }
        return lstNetpool;
    }
}