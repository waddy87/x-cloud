package com.sugon.cloudview.cloudmanager.monitor.service.service;

import java.util.List;

import com.sugon.cloudview.cloudmanager.monitor.service.bo.VMBo;

import net.sf.json.JSONObject;

/**
 * 功能名: 请填写功能名 
 * 功能描述: 请简要描述功能的要点 
 * Copyright: Copyright (c) 2014 
 * 公司: 曙光云计算有限公司
 *
 * @author Xuby
 * @version 1.8.0
 */
public interface VMService {

    /**
     * 功能: 根据传入的虚拟机id列表，返回虚拟机对象列表，若传入的id在vcenter不存在则不加入返回的虚拟机列表
     *
     * @param vmIds
     * @return
     */
    public List<VMBo> getVMsByIds(List<String> vmIds);


    /**
     * 功能: 根据传入的虚拟机id，返回虚拟机对象，若id不存在则返回null
     *
     * @param vmId
     * @return
     */
    public VMBo getVMById(String vmId);

    /**
     * 功能: 获取所有虚拟机列表
     *
     * @return
     */
    public List<VMBo> getAllVMs();

    /**
     * 功能: 根据虚拟机id获取虚拟机历史数据
     *
     * @param vmId
     * @return
     */
    public JSONObject getHistory(String vmId);
}
