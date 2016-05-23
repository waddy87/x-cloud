/**
 * 
 */
package org.waddys.xcloud.monitor.service.service;

import java.util.List;

import org.waddys.xcloud.monitor.bo.HostBo;

/**
 * @author wangqian
 *
 */
public interface HostService {
    public List<HostBo> getHosts();

    public HostBo getHostById(String hostBo);

}
