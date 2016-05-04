package com.sugon.cloudview.cloudmanager.vijava.data;

public class VMNetworkInfo {

    private String dnsServer;
    private String dnsDomain;
    private String gateway;
    private String subnetMask;
    private String ip;

    public String getDnsServer() {
        return dnsServer;
    }

    public void setDnsServer(String dnsServer) {
        this.dnsServer = dnsServer;
    }

    public String getDnsDomain() {
        return dnsDomain;
    }

    public void setDnsDomain(String dnsDomain) {
        this.dnsDomain = dnsDomain;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getSubnetMask() {
        return subnetMask;
    }

    public void setSubnetMask(String subnetMask) {
        this.subnetMask = subnetMask;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "VMNetworkInfo [dnsServer=" + dnsServer + ", dnsDomain=" + dnsDomain + ", gateway=" + gateway
                + ", subnetMask=" + subnetMask + ", ip=" + ip + "]";
    }

}
