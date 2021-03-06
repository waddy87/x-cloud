package org.waddys.xcloud.res.service.service.vdc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.waddys.xcloud.res.bo.vdc.ComputingPool;
import org.waddys.xcloud.res.bo.vdc.ConfigInfo;
import org.waddys.xcloud.res.bo.vnet.NetPool;
import org.waddys.xcloud.res.exception.venv.VenvException;

/**
 * 功能名称：虚拟机化环境管理服务接口 功能描述：用于提供前台业务层功能
 * 
 * @author ghj
 * @version Cloudview sp2.0
 */

public interface ConfigInfoService {

    /**
     * 功能：接入一虚拟环境
     * 
     * @param: {"name":"虚拟化环境001","ip":"10.0.33.115","username":"root",
     *             "userpwd":"111111"}
     * @return: {"flag":"true","version":"标准版"}
     */
    public ConfigInfo addConfigInfo(ConfigInfo configInfo) throws VenvException;

    public Page<ConfigInfo> findConfigInfos(Pageable pageable) throws VenvException;

    public List<ConfigInfo> findConfigInfos();

    public ConfigInfo findByconfigId(String configId);

    public void updateConfigInfo(String configName, String configId);

    public void updateConfigInfo(ConfigInfo configInfo);

    public void delConfigInfo(ConfigInfo configInfo);
}
