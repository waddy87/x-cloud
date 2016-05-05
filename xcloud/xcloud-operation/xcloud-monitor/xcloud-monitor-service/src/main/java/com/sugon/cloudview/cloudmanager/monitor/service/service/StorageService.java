/**
 * Created on 2016年3月25日
 */
package com.sugon.cloudview.cloudmanager.monitor.service.service;

import java.util.List;

import com.sugon.cloudview.cloudmanager.monitor.service.bo.StorageBo;

/**
 * 功能名: 请填写功能名
 * 功能描述: 请简要描述功能的要点
 * Copyright: Copyright (c) 2016
 * 公司: 曙光云计算技术有限公司
 *
 * @author wq
 * @version 2.0.0 sp1
 */
public interface StorageService {
    public List<StorageBo> getStorages();

    public StorageBo getStorageById(String storageId);

    public List<StorageBo> getStorageByClusterId(String clusterId);
}
