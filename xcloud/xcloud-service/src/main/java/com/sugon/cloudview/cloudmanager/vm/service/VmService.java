package com.sugon.cloudview.cloudmanager.vm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.sugon.cloudview.cloudmanager.vm.bo.VmConfig;
import com.sugon.cloudview.cloudmanager.vm.bo.VmHost;
import com.sugon.cloudview.cloudmanager.vm.bo.VmTask;

/**
 * 虚机服务接口
 * 
 * @author zhangdapeng
 *
 */
public interface VmService {

    /**
     * 根据ID查询虚机实体
     * 
     * @param id
     *            虚机业务标识
     * @return
     */
    public VmHost findById(String id);

    /**
     * 根据名称查询虚机实体
     * 
     * @param name
     *            虚机业务名称
     * @return
     */
    public VmHost findByName(String name);

    /**
     * 根据内部标识查询虚机
     * 
     * @param internalId
     *            虚机内部标识（对应虚拟化层）
     * @return
     */
    public VmHost findByInternalId(String internalId);

    /**
     * 根据任务标识查询虚机
     * 
     * @param taskId
     * @return
     */
    public VmHost findByTaskId(String taskId);

    /**
     * 创建虚机
     * 
     * @param VmHost
     * @return
     * @throws Exception
     */
    public VmHost create(VmHost VmHost) throws Exception;

    /**
     * 删除虚机（逻辑操作）
     * 
     * @param id
     *            虚机业务唯一标识
     */
    public void deleteById(String id) throws Exception;

    /**
     * 更新虚机
     * 
     * @param VmHost
     * @return
     * @throws Exception
     */
    public void update(VmHost VmHost) throws Exception;

    /**
     * 移除虚机（物理操作）
     * 
     * @param id
     *            虚机业务唯一标识
     * @return
     * @throws Exception
     */
    public VmTask remove(String id) throws Exception;

    /**
     * 释放资源
     * 
     * @param id
     *            虚机业务唯一标识
     * @throws Exception
     */
    public void releaseRes(String id) throws Exception;

    /**
     * 配置虚机
     * 
     * @param vmConfig
     * @return
     * @throws Exception
     */
    public VmTask config(String id, VmConfig vmConfig) throws Exception;

    /**
     * 重置密码
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public VmTask resetPassword(String id) throws Exception;

    /**
     * 启动虚机
     * 
     * @param id
     * @throws Exception
     */
    public VmTask start(String id) throws Exception;

    /**
     * 停止虚机
     * 
     * @param id
     * @throws Exception
     */
    public VmTask stop(String id) throws Exception;

    /**
     * 刷新虚机状态（同步虚拟化环境中的真实状态）
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public VmHost refresh(String id) throws Exception;

    /**
     * 分配虚机
     * 
     * @param id
     *            目标虚机业务标识
     * @param userId
     *            目标用户标识
     * @return
     * @throws Exception
     */
    public void assign(String id, String userId) throws Exception;

    /**
     * 回收虚机
     * 
     * @param id
     *            目标虚机业务标识
     * @param userId
     *            目标用户标识
     * @return
     * @throws Exception
     */
    public void revoke(String id, String userId) throws Exception;

    /**
     * 分页查询全部虚机列表
     * 
     * @param search
     *            查询对象（目前仅支持根据name模糊查询）
     * @param pageRequest
     *            分页参数
     * @return
     */
    public Page<VmHost> pageAll(VmHost search, PageRequest pageRequest);

    /**
     * 根据项目分页查询虚机列表
     * 
     * @param pid
     *            项目唯一标识
     * @param search
     *            查询对象（目前仅支持根据name模糊查询）
     * @param pageRequest
     *            分页参数
     * @return
     */
    public Page<VmHost> pageByPID(String pid, VmHost search, PageRequest pageRequest);

    /**
     * 根据组织分页查询虚机列表
     * 
     * @param oid
     *            组织唯一标识
     * @param search
     *            查询对象
     * @param pageRequest
     *            分页参数
     * @return 分页结果
     */
    public Page<VmHost> pageByOID(String oid, VmHost search, PageRequest pageRequest);

    /**
     * 根据用户分页查询虚机列表
     * 
     * @param uid
     *            用户唯一标识
     * @param search
     *            查询对象
     * @param pageRequest
     *            分页参数
     * @return 分页结果
     */
    public Page<VmHost> pageByUID(String uid, VmHost search, PageRequest pageRequest);

    /**
     * 根据vdc查询虚机列表（分页）
     * 
     * @param vdcId
     *            vdc唯一标识
     * @param search
     *            查询对象（目前仅支持根据name模糊查询）
     * @param pageRequest
     *            分页参数
     * @return 分页结果
     */
    public Page<VmHost> pageByVDC(String vdcId, VmHost search, PageRequest pageRequest);

    /**
     * 给任务列表定制服务接口
     * 
     * @param search
     * @param pageable
     * @return
     */
    public Page<VmHost> findByHavingTask(VmHost search, Pageable pageable);

    /**
     * 根据vdc查询虚机列表
     * 
     * @param vdcId
     *            vdc唯一标识
     * @param search
     *            查询对象（目前仅支持根据name模糊查询）
     * @return
     */
    public List<VmHost> listByVDC(String vdcId, VmHost search);

    public Long total(char status, String name);

    public List<VmHost> listAll();

    /**
     * 重名验证
     * 
     * @param name
     * @return
     */
    public boolean exists(String name);

}
