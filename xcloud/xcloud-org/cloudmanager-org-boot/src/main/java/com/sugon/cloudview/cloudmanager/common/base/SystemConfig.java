/**
 * 
 */
package com.sugon.cloudview.cloudmanager.common.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 系统配置
 * 
 * @author zhangdapeng
 *
 */
@ConfigurationProperties(prefix = "cloudview")
public class SystemConfig {

    public final static String CLOUDVIEW_OS_PASSWORD_DEFAULT = "cloudview";

    @Value("${cloudview.vm.os.password}")
    public String osPassword = CLOUDVIEW_OS_PASSWORD_DEFAULT;

    public String getOsPassword() {
        return osPassword;
    }

    public void setOsPassword(String osPassword) {
        this.osPassword = osPassword;
    }

}
