package com.codewithizzy.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
// This class will be used as a web controller to receive web traffic.
public class HomeController {
  @RequestMapping("/")
  public String index() {
    return "index.html";
  }
}
