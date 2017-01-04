package org.waddys.xcloud.driver.base;

public class ConnectionConfig {

    private String ip;

    private int port;

    private String user;

    private String passwd;

    private String token;

    public ConnectionConfig() {
        // TODO Auto-generated constructor stub
    }

    public ConnectionConfig(String ip, int port, String user, String passwd) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.passwd = passwd;
    }
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}