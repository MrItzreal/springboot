package com.codewithizzy.springboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
// This class will be used as a web controller to receive web traffic.
public class HomeController {

  @Value("${spring.application.name}")
  private String appName;

  @RequestMapping("/")
  public String index() {
    System.out.println("appName: " + appName);
    return "index.html";
  }
}
