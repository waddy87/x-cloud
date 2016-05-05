package com.sugon.cloudview.cloudmanager.monitor.service.service;


import java.util.List;

import com.sugon.cloudview.cloudmanager.monitor.service.bo.ClusterBo;

/**
 * 功能名: 集群接口
 * 功能描述: 请简要描述功能的要点
 * Copyright: Copyright (c) 2014
 * 公司: 曙光云计算有限公司
 *
 * @author Xuby
 * @version 1.8.0
 */
public interface ClusterService {
    /**
     * 功能: 获取全部集群信息（不包括主机、虚拟机列表）
     *
     * @return
     */
    public List<ClusterBo> getClusters();

    /**
     * 功能: 根据集群id获取集群详细信息
     *
     * @param clusterId
     * @return
     */
    public ClusterBo getClusterById(String clusterId);
}
