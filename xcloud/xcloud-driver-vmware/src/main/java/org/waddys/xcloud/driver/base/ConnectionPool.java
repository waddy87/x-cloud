package org.waddys.xcloud.driver.base;

import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vmware.vim25.UserSession;
import com.vmware.vim25.mo.ServerConnection;
import com.vmware.vim25.mo.ServiceInstance;

public class ConnectionPool {

    private static Logger logger = LoggerFactory.getLogger(ConnectionPool.class);

    private static ConnectionFactory factory = new ConnectionFactory();
    protected static GenericKeyedObjectPool<ConnectionConfig, ServerConnection> _pool = new GenericKeyedObjectPool<ConnectionConfig, ServerConnection>(
            factory);

    private synchronized static ServerConnection _getFromPool(ConnectionConfig config) throws Exception {

        ServerConnection conn = _pool.borrowObject(config);
        logger.debug("After borrow conneciton：     active:{}, idle:{}, waiters:{}", _pool.getNumActive(),
                _pool.getNumIdle(), _pool.getNumWaiters());

        if (conn == null) {
            return null;
        }

        try {
            // 通过本地usersession和服务器端usersession 的Logintime 来计算出来两边的时间差
            @SuppressWarnings("deprecation")
            UserSession usessionLocal = conn.getUserSession();
            UserSession usessionServer = conn.getServiceInstance().getSessionManager().getCurrentSession();
            long deltaTime = usessionServer.getLoginTime().getTimeInMillis()
                    - usessionLocal.getLoginTime().getTimeInMillis();

            // 用服务器端的lastactivetime减去时间差 就是连接最后一次活跃时间（本地时间）
            long startTime = usessionServer.getLastActiveTime().getTimeInMillis() - deltaTime;

            // 获取当前时间
            long currentTime = System.currentTimeMillis();

            // 如果最后活跃时间 距今超过20分钟 强制清理连接 然后重连
            if (currentTime >= (startTime + 1200000)) {
                logger.debug("refresh pool object");
                _pool.invalidateObject(config, conn);
                conn = _pool.borrowObject(config);
            }
        } catch (Exception e) {
            logger.debug("refresh pool object");
            _pool.invalidateObject(config, conn);
            conn = _pool.borrowObject(config);
        }

        return conn;
    }

    /**
     * 根据 token 获取连接
     * 
     * @param token
     * @return
     * @throws Exception
     */
    public static ServiceInstance getConncetion(String ip, String user, String passwd) throws Exception {
        return getConncetion(ip, 80, user, passwd);
    }

    public static ServiceInstance getConncetion(String ip, int port, String user, String passwd) throws Exception {
        return getConncetion(new ConnectionConfig(ip, port, user, passwd));
    }

    public static ServiceInstance getConncetion(ConnectionConfig config) throws Exception {
        ServerConnection conn = _getFromPool(config);
        logger.debug(conn.getSessionStr());
        return conn.getServiceInstance();
    }

    public synchronized static void returnConnection(ConnectionConfig config, ServiceInstance si) throws Exception {
        logger.debug("config = " + config);
        logger.debug("si = " + si);

        _pool.returnObject(config, si.getServerConnection());
        logger.debug("After Return conneciton：     active:{}, idle:{}, waiters:{}", _pool.getNumActive(),
                _pool.getNumIdle(), _pool.getNumWaiters());
    }

    public static void getStatus() {
        logger.info("maxTotal:{} - numIdle:{} - numActive:{}", _pool.getMaxTotal(), _pool.getNumIdle(),
                _pool.getNumActive());
    }

}
