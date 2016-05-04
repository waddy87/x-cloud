package com.sugon.cloudview.cloudmanager.event.type;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rabbitmq")
public class ConnectionInfo {

    private final static int DEFAULT_PORT = 5672;
    private final static String DEFAULT_USERNAME = "guest";
    private final static String DEFAULT_PASSWORD = "guest";
    private final static int DEFAULT_PROCESS_THREAD_NUM = Runtime.getRuntime().availableProcessors() * 2;
    private static final int PREFETCH_SIZE = 1;

    @Value("${rabbitmq.server.ip}")
    private String serverHost = "127.0.0.1";
    // @Value("${rabbitmq.server.port}")
    private int port = DEFAULT_PORT;
    // @Value("${rabbitmq.server.username}")
    private String username = DEFAULT_USERNAME;
    // @Value("${rabbitmq.server.password}")
    private String password = DEFAULT_PASSWORD;
    // @Value("${rabbitmq.server.virtualHost}")
    private String virtualHost = null;
    /**
     * 和rabbitmq建立连接的超时时间
     */
    // @Value("${rabbitmq.connection.timeout}")
    private int connectionTimeout = 0;
    /**
     * 事件消息处理线程数，默认是 CPU核数 * 2
     */
    // @Value("${rabbitmq.event.msg.process.num}")
    private int eventMsgProcessNum = DEFAULT_PROCESS_THREAD_NUM;
    /**
     * 每次消费消息的预取值
     */
    // @Value("${rabbitmq.event.prefetch.size}")
    private int prefetchSize = PREFETCH_SIZE;

    public static ConnectionInfo getConnectionInfo(String serverHost) {
        return new ConnectionInfo().setServerHost(serverHost);
    }

    @PostConstruct
    public void init() {
        System.out.println("serverHost: " + serverHost);
        System.out.println("port: " + port);
        System.out.println("username: " + username);
        System.out.println("password: " + password);
        System.out.println("virtualHost: " + virtualHost);
        System.out.println("connectionTimeout: " + connectionTimeout);
        System.out.println("eventMsgProcessNum: " + eventMsgProcessNum);
        System.out.println("prefetchSize: " + prefetchSize);
    }

    public String getServerHost() {
        return serverHost;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public int getEventMsgProcessNum() {
        return eventMsgProcessNum;
    }

    public int getPrefetchSize() {
        return prefetchSize;
    }

    public ConnectionInfo setServerHost(String serverHost) {
        this.serverHost = serverHost;
        return this;
    }

    public ConnectionInfo setPort(int port) {
        if (port > 0) {
            this.port = port;
        }
        return this;
    }

    public ConnectionInfo setUsername(String username) {
        this.username = username;
        return this;
    }

    public ConnectionInfo setPassword(String password) {
        this.password = password;
        return this;
    }

    public ConnectionInfo setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
        return this;
    }

    public ConnectionInfo setConnectionTimeout(int connectionTimeout) {
        if (connectionTimeout > 0) {
            this.connectionTimeout = connectionTimeout;
        }
        return this;
    }

    public ConnectionInfo setEventMsgProcessNum(int eventMsgProcessNum) {
        if (eventMsgProcessNum > 0) {
            this.eventMsgProcessNum = eventMsgProcessNum;
        }
        return this;
    }

    public ConnectionInfo setPrefetchSize(int prefetchSize) {
        if (prefetchSize > 0) {
            this.prefetchSize = prefetchSize;
        }
        return this;
    }

}
