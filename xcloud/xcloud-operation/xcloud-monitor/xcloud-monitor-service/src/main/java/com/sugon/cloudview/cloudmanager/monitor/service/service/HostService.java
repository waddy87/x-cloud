/**
 * 
 */
package com.sugon.cloudview.cloudmanager.monitor.service.service;

import java.util.List;

import com.sugon.cloudview.cloudmanager.monitor.service.bo.HostBo;

/**
 * @author wangqian
 *
 */
public interface HostService {
    public List<HostBo> getHosts();

    public HostBo getHostById(String hostBo);

}
