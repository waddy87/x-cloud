package org.waddys.xcloud.web.config;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.Ssl;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.waddys.xcloud.log.spring.LogInterceptor;

@EnableWebMvc
@ComponentScan
@Configuration
@PropertySource("classpath:/tomcat.https.properties")
@EnableConfigurationProperties(WebConfig.TomcatSslConfig.class)
public class WebConfig extends WebMvcConfigurerAdapter {
//	@Bean
//	public EmbeddedServletContainerFactory servletContainer(TomcatSslConfig properties) {
//	    TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
//	    tomcat.addAdditionalTomcatConnectors(createSslConnector(properties));
//	    return tomcat;
//	}
//
//	private Connector createSslConnector(TomcatSslConfig properties) {
//	    Connector connector = new Connector();
//	    properties.configureConnector(connector);
//	    return connector;
//	}	
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer(TomcatSslConfig properties) {
	    return new EmbeddedServletContainerCustomizer() {
	        @Override
	        public void customize(ConfigurableEmbeddedServletContainer container) {
	            Ssl ssl = new Ssl();
	            //Server.jks中包含服务器私钥和证书
	            ssl.setKeyStore(properties.getKeystoreFile());
	            ssl.setKeyStorePassword(properties.getKeystorePassword());
	            container.setSsl(ssl);
	            container.setPort(properties.getPort());
	        }
	    };
	}
	
	@Bean
	public EmbeddedServletContainerFactory servletContainerFactory(TomcatSslConfig properties) {
	    TomcatEmbeddedServletContainerFactory factory =
	        new TomcatEmbeddedServletContainerFactory() {
	            @Override
	            protected void postProcessContext(Context context) {
	                //SecurityConstraint必须存在，可以通过其为不同的URL设置不同的重定向策略。
	                SecurityConstraint securityConstraint = new SecurityConstraint();
	                securityConstraint.setUserConstraint("CONFIDENTIAL");
	                SecurityCollection collection = new SecurityCollection();
	                collection.addPattern("/*");
	                securityConstraint.addCollection(collection);
	                context.addConstraint(securityConstraint);
	            }
	        };
	    factory.addAdditionalTomcatConnectors(createHttpConnector(properties));
	    return factory;
	}

	private Connector createHttpConnector(TomcatSslConfig properties) {
	    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
	    connector.setScheme("http");
	    connector.setSecure(false);
	    connector.setPort(properties.getHttpport());
	    connector.setRedirectPort(properties.getPort());
	    return connector;
	}

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/shiro").setViewName("shiro");
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    // Only used when running in embedded servlet
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public LogInterceptor getLogInterceptor() {
        return new LogInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLogInterceptor()).addPathPatterns("/**");
    }
    
    @ConfigurationProperties(prefix = "custom.tomcat.https")
    public static class TomcatSslConfig {
        private Integer httpport;
        private Integer port;
        private Boolean ssl = true;
        private Boolean secure = true;
        private String scheme = "https";
        private String keystoreFile;
        private File keystore;
        private String keystorePassword;
        //这里为了节省空间，省略了getters和setters，读者在实践的时候要加上

        public void configureConnector(Connector connector) {
            if (port != null) {
                connector.setPort(port);
            }
            if (secure != null) {
                connector.setSecure(secure);
            }
            if (scheme != null) {
                connector.setScheme(scheme);
            }
            if (ssl != null) {
                connector.setProperty("SSLEnabled", ssl.toString());
            }
            System.out.println("keystore:"+keystore+", keystorePassword="+keystorePassword);
            if (keystore != null && keystore.exists()) {
                connector.setProperty("keystoreFile", keystore.getAbsolutePath());
                connector.setProperty("keystorePassword", keystorePassword);
            }
        }

		public String getKeystoreFile() {
			return keystoreFile;
		}

		public void setKeystoreFile(String keystoreFile) {
			this.keystoreFile = keystoreFile;
		}

		public Integer getHttpport() {
			return httpport;
		}

		public void setHttpport(Integer httpport) {
			this.httpport = httpport;
		}

		public Integer getPort() {
			return port;
		}

		public void setPort(Integer port) {
			this.port = port;
		}

		public Boolean getSsl() {
			return ssl;
		}

		public void setSsl(Boolean ssl) {
			this.ssl = ssl;
		}

		public Boolean getSecure() {
			return secure;
		}

		public void setSecure(Boolean secure) {
			this.secure = secure;
		}

		public String getScheme() {
			return scheme;
		}

		public void setScheme(String scheme) {
			this.scheme = scheme;
		}

		public File getKeystore() {
			return keystore;
		}

		public void setKeystore(File keystore) {
			this.keystore = keystore;
		}

		public String getKeystorePassword() {
			return keystorePassword;
		}

		public void setKeystorePassword(String keystorePassword) {
			this.keystorePassword = keystorePassword;
		}
    }
    
}
