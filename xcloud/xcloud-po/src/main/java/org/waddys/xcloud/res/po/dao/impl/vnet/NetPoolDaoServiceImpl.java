package org.waddys.xcloud.res.po.dao.impl.vnet;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.waddys.xcloud.res.exception.vnet.VNetException;
import org.waddys.xcloud.res.po.dao.repository.vnet.NetPoolRepository;
import org.waddys.xcloud.res.po.dao.vnet.NetPoolDaoService;
import org.waddys.xcloud.res.po.entity.vnet.NetPoolE;

/**
 * 虚拟网络DaoImpl类
 * 
 * @author LvQP
 *
 */
@Component("netPoolDaoServiceImpl")
@Transactional
public class NetPoolDaoServiceImpl implements NetPoolDaoService {

    @Autowired
    private NetPoolRepository netRepository;

    /**
     * 根据输入的虚拟网络名称模糊查询，分页显示结果集
     * 
     * @param netName
     *            虚拟网络名称
     * @param pageable
     *            分页信息对象
     * @return Page<NetPoolE> 分页结果集
     */
    @Override
    public Page<NetPoolE> QueryNetPoolByPage(String netName, Boolean isAvl, Pageable pageable) {
        if (!StringUtils.hasLength(netName)) {
            return netRepository.findByisAvl(isAvl, pageable);
        }
        return netRepository.findBynetNameAndIsAvlContainingAllIgnoringCase(netName, isAvl, pageable);
    }

    @Override
    public long count(NetPoolE netPoolE, Boolean isAvl) {
        return netRepository.countBynetName(netPoolE.getNetName(), isAvl);
    }

    @Override
    public void delNetPoolByConfigId(String configId) {
        netRepository.delNetPoolByConfigId(configId);
    }

    /**
     * 查找所有的List<NetPoolE>对象
     * 
     * @return List<NetPoolE>
     */
    @Override
    public List<NetPoolE> QueryNetPoolAll() {
        return netRepository.findAll();
    }

    /**
     * 插入新的虚拟网络对象
     * 
     * @param NetPoolE
     *            要插入的新对象
     * @return NetPoolE 插入成功的虚拟网络对象
     */
    @Override
    public NetPoolE saveNetPool(NetPoolE netpoolE) {
        return netRepository.save(netpoolE);
    }

    /**
     * 根据虚拟网络Id，删除虚拟网络对象
     * 
     * @param netpoolId
     *            要删除的虚拟网络Id
     * @return void 没有返回值
     * @throws VNetException
     */
    @Override
    public void deleteNetPool(String netpoolId) throws VNetException {

    }

    /**
     * 根据虚拟网络Id，查找虚拟网络对象NetPoolE
     * 
     * @param poolId
     *            要查找的虚拟网络Id
     * @return NetPoolE 虚拟网络对象
     */
    @Override
    public NetPoolE findNetPoolEByNetPoolId(String netPoolId) {
        return netRepository.findBynetPoolId(netPoolId);
    }

    /**
     * 修改虚拟网络
     * 
     * @param NetPoolE
     *            虚拟网络对象NetPoolE
     * @return NetPoolE 修改后的虚拟网络对象NetPoolE
     */
    @Override
    public NetPoolE updateNetPool(NetPoolE netpoolE) {
        return netRepository.save(netpoolE);
    }

    @Override
    public boolean distributionOrg(List<String> netPoolIDs, String orgId) {
        boolean result = true;
        try {
            if (null != netPoolIDs) {
                for (String id : netPoolIDs) {
                    netRepository.updateNetPoolOfOrg(id, orgId);
                }
            }
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Page<NetPoolE> queryVlanOrGatewayOrSubnetOrDns(Integer vlanno, String gateway, String subnet, String dns,
            Pageable pageable) {
        return netRepository.queryVlanNoORgatewayORsubNetORdns(vlanno, gateway, subnet, dns, pageable);
    }

    @Override
    public List<NetPoolE> QueryNetpoolByorgId(String orgId) {
        return netRepository.findByOrgId(orgId);
    }

    @Override
    public List<NetPoolE> findByIsAvl(Boolean isAvl) {

        return netRepository.findByIsAvl(isAvl);
    }

    @Override
    public List<NetPoolE> findByNetPool(NetPoolE netPoolE) {
        return netRepository.findAll(new Specification<NetPoolE>() {
            @Override
            public Predicate toPredicate(Root<NetPoolE> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                List<Expression<Boolean>> expressions = predicate.getExpressions();
                if (netPoolE != null) {
                    expressions.add(cb.equal(root.get("isAvl"), netPoolE.getIsAvl()));
                    if (!StringUtils.isEmpty(netPoolE.getOrgId())) {
                        expressions.add(cb.equal(root.get("orgId"), netPoolE.getOrgId()));
                    }
                }
                return predicate;
            }
        });
    }

}
