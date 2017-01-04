package org.waddys.xcloud.driver.base;
import java.net.URL;

import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vmware.vim25.mo.ServerConnection;
import com.vmware.vim25.mo.ServiceInstance;

public class ConnectionFactory extends BaseKeyedPooledObjectFactory<ConnectionConfig, ServerConnection> {

    private static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);

    @Override
    public ServerConnection create(ConnectionConfig config) throws Exception {

        ServerConnection conn = null;

        logger.info("开始创建连接对象：{}", config);
        if (config != null) {
            String _api_url = "https://" + config.getIp() + "/sdk";
            ServiceInstance si = new ServiceInstance(new URL(_api_url), config.getUser(), config.getPasswd(),
                    true);
            if (null != si) {
                conn = si.getServerConnection();
            }
            logger.info("完成创建连接对象：{}", conn);
        }
        return conn;
    }

    @Override
    public PooledObject<ServerConnection> wrap(ServerConnection conn) {
        return new DefaultPooledObject<ServerConnection>(conn);
    }

}