package com.sugon.cloudview.cloudmanager.resource.service.service.venv;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sugon.cloudview.cloudmanager.resource.service.bo.venv.VenvConfig;
import com.sugon.cloudview.cloudmanager.resource.service.exception.venv.VenvException;

/**
 * 功能名称：虚拟机化环境管理服务接口 功能描述：用于提供前台业务层功能
 * 
 * @author ghj
 * @version Cloudview sp2.0
 */

public interface VenvConfigService {
    /**
     * 虚拟环境列表展示， s * @return
     */
    public List<VenvConfig> findConfigInfos();
    /**
     * 功能：接入一虚拟环境
     * 
     * @param: venvConfig.setConfigId("虚拟化环境01");
     *         venvConfig.setiP("10.0.33.87"); venvConfig.setConfigName("root");
     *         venvConfig.setPassword("sugon123"); venvConfig.setCreateDate(new
     *         Date());
     * @return: 添加成功：true 添加失败：false
     */
    public VenvConfig addVenvConfig(VenvConfig venvConfig) throws VenvException;

    /**
     * 功能：测试待接入虚拟环境是否通过
     * 
     * @param: venvConfig.setConfigId("虚拟化环境01");
     *         venvConfig.setiP("10.0.33.87"); venvConfig.setConfigName("root");
     *         venvConfig.setPassword("sugon123"); venvConfig.setCreateDate(new
     *         Date());
     * @return: 测试通过：ture 测试不通过：false
     */
    public boolean testVenvConfig(VenvConfig venvConfig) throws VenvException;

    /**
     * 功能：分页获取所有接入的虚拟环境列表
     * 
     * @param:无 @return： 虚拟环境列表
     */
    Page<VenvConfig> findVenvConfigs(Pageable pageable) throws VenvException;

    /**
     * 功能：按条件模糊查询虚拟环境列表
     * 
     * @param:无 @return： 虚拟环境列表
     */
    Page<VenvConfig> findVenvConfig(VenvConfig venvConfig, Pageable pageable) throws VenvException;

    /**
     * 功能：修改虚拟机环境配置信息
     * 
     * @param:{venvConfigId:"" @return： 修改成功：true；修改失败：false
     */
    public void updateVenvConfig(VenvConfig venvConfig) throws VenvException;

    /**
     * 功能：删除一条虚拟机环境记录
     * 
     * @return： 删除成功：true；删除失败：false
     */
    public void delVenvConfig(String configId) throws VenvException;

    /**
     * 功能：同步虚拟机环境信息,初次添加虚拟化环境
     * 
     * @param computingPoolo
     *            所有存储池
     * @param netPools
     *            所有网络池
     * @param venvConfig
     * @return
     * @throws VenvException
     */
    public String synDataVenvConfig(VenvConfig venvconfig)
            throws VenvException;

    /**
     * ghj 虚拟化环境同步
     */
    String synDataVenvConfigCompare(String configId, String ip) throws VenvException;

    /**
     * 功能：根据s
     * 
     * @return：
     */
    public VenvConfig findVenvConfigByconfigId(String configId) throws VenvException;



}
