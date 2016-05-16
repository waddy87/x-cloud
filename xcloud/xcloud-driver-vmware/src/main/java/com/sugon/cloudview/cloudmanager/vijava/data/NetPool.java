package com.sugon.cloudview.cloudmanager.vijava.data;

public class NetPool {
	
	private String name;
	private String id;
	private int vlan;
	private String gateway;
	private String subnet;
	private String dns;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getVlan() {
		return vlan;
	}

	public void setVlan(int i) {
		this.vlan = i;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getSubnet() {
		return subnet;
	}

	public void setSubnet(String subnet) {
		this.subnet = subnet;
	}

	public String getDns() {
		return dns;
	}

	public void setDns(String dns) {
		this.dns = dns;
	}
}
