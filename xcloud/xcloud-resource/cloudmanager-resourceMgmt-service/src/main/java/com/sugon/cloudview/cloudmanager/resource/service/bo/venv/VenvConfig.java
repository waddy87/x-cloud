package com.sugon.cloudview.cloudmanager.resource.service.bo.venv;

import java.util.Date;

/**
 * 连接信息
 * 
 * @author ghj
 *
 */
public class VenvConfig {
	private String configId;
	/**
	 * 连接名称
	 */
	private String configName;
	/**
	 * 连接地址
	 */
    private String iP;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 创建时间
	 */
	private Date createDate;
    /**
     * 版本
     */
    private String version;
    /**
     * 状态
     */
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

    public String getiP() {
        return iP;
    }

    public void setiP(String iP) {
        this.iP = iP;
    }

    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "VenvConfig [configId=" + configId + ", configName=" + configName + ", iP=" + iP + ", userName="
				+ userName + ", password=" + password + ", createDate=" + createDate + "]";
	}
	
	
}
