package com.codewithizzy.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*It's good practice to always specify the response status*/

@RestController
public class FirstController {

  @GetMapping("/hello")
  public String sayHello() {
    return "Hello from my first controller";
  }

  @PostMapping("/post")
  public String post(
      @RequestBody String message) {
    return "Request accepted and message is : " + message;
  }

  @PostMapping("/post-order")
  public String post(
      @RequestBody Order order) {
    return "Request accepted and order is : " + order.toString();
  }

}
