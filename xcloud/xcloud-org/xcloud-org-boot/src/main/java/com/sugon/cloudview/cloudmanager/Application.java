package com.sugon.cloudview.cloudmanager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

import com.sugon.cloudview.cloudmanager.common.base.SystemConfig;

@EnableConfigurationProperties({ SystemConfig.class })
@SpringBootApplication
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    /**
     * 主入口
     * 
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        System.out.println("=== STARTUP App NOW ===");
        System.out.println(new SystemConfig().getOsPassword());
    }

}
