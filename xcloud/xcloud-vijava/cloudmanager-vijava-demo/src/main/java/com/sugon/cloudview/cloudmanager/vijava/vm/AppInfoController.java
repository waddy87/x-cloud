package com.sugon.cloudview.cloudmanager.vijava.vm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@ConfigurationProperties(value = "application")
//class AppInfoController {
//  String name;
//  String version;
//}


@RestController
@EnableAutoConfiguration
public class AppInfoController {

  @RequestMapping("/")
  public String home() {
    return "Hello";
  }

  public static void main(String[] args) {
    SpringApplication.run(AppInfoController.class, args);
  }
}
