package org.waddys.xcloud;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class Sysconfig {

	@Value("${vm.disk.threshold}")
	private String diskThreshold = "123";
	public Sysconfig() {
		System.out.println("diskThreshold="+diskThreshold);
	}

	@PostConstruct
    public void init() {
		System.out.println("diskThreshold="+diskThreshold);
		
	}
	
}
