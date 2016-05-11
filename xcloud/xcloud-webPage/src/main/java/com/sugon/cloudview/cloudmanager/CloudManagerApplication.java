package com.sugon.cloudview.cloudmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.sugon.cloudview.cloudmanager.event.type.ConnectionInfo;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@ServletComponentScan
@EnableConfigurationProperties({ ConnectionInfo.class })
@ImportResource(locations = { "classpath:spring-config-shiro.xml" })
public class CloudManagerApplication extends SpringBootServletInitializer {
    // implements EmbeddedServletContainerCustomizer {

    private static Class<CloudManagerApplication> applicationClass = CloudManagerApplication.class;
    
    public CloudManagerApplication() {
        System.out.println(System.getenv());
        System.out.println(System.getProperties());
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(applicationClass, args);
    }

    /*
    @Override
    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
        configurableEmbeddedServletContainer.setSessionTimeout(10);
        configurableEmbeddedServletContainer.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/e/404"));
        configurableEmbeddedServletContainer.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/e/500"));
        configurableEmbeddedServletContainer.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/e/400"));
        if (configurableEmbeddedServletContainer instanceof TomcatEmbeddedServletContainerFactory) {
            // something to do
        } else if (configurableEmbeddedServletContainer instanceof UndertowEmbeddedServletContainerFactory) {
            // something to do
            throw new IllegalArgumentException("Undertow Keycloak integration is not yet implemented");
        } else if (configurableEmbeddedServletContainer instanceof JettyEmbeddedServletContainerFactory) {
            // something to do
            throw new IllegalArgumentException("Jetty Keycloak integration is not yet implemented");
        }
    }
    */
}