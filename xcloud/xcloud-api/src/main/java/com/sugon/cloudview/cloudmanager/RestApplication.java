package com.sugon.cloudview.cloudmanager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

//import com.sugon.cloudview.cloudmanager.event.type.ConnectionInfo;
import com.sugon.cloudview.cloudmanager.vijava.base.CloudviewExecutorImpl;
import com.sugon.cloudview.cloudmanager.vijava.environment.ConnectCloudVM.ConnectCloudVMAnswer;
import com.sugon.cloudview.cloudmanager.vijava.environment.ConnectCloudVM.ConnectCloudVMCmd;
import com.sugon.cloudview.cloudmanager.vijava.exception.VirtException;

@SpringBootApplication
//@EnableConfigurationProperties({ ConnectionInfo.class })
public class RestApplication {
    private static final Logger logger = LoggerFactory.getLogger(RestApplication.class);

    /**
     * 主入口
     * 
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(RestApplication.class, args);
        init(context.getEnvironment());
        System.out.println("=== STARTUP Rest App NOW ===");
    }

    /**
     * 初始化环境
     * 
     * @param env
     */
    public static void init(Environment env) {
        ConnectCloudVMCmd cmd = new ConnectCloudVMCmd();
        String serverIp = env.getProperty("venv.server.ip", "10.0.31.251");
        String username = env.getProperty("venv.user.name", "admin");
        String password = env.getProperty("venv.user.password", "Sugon!123");
        logger.info("连接虚拟化环境：{serverIp=" + serverIp + ",username=" + username + "}");
        cmd.setIp(serverIp);
        cmd.setUsername(username);
        cmd.setUserpwd(password);
        try {
            ConnectCloudVMAnswer answer = (new CloudviewExecutorImpl()).execute(cmd);
            if (answer.isSuccess()) {
                logger.info("连接虚拟化环境成功！");
            } else {
                logger.error("连接虚拟化环境失败！" + answer.getErrMsg());
            }
        } catch (VirtException e) {
            logger.error("连接虚拟化环境出错！", e);
        }

	}

}
