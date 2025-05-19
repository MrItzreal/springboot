package com.codewithizzy.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

/* - It's good practice to always specify the response status.
 * - RestController used at the class level to indicate an annotated class is a controller.
*/

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

  @PostMapping("/post-order-record")
  public String postRecord(
      @RequestBody OrderRecord order) {
    return "Request accepted and order is : " + order.toString();
  }

  // http://localhost:8080/hello/Izzy
  // @GetMapping("/hello/{user-name}")
  public String pathVar(
      @PathVariable("user-name") String userName) {
    return "my value = " + userName;
  }

  // http://localhost:8080/hello?param_name=paramvalue
  @GetMapping("/hello")
  public String paramVar(
      @RequestParam("user-name") String userName,
      @RequestParam("user-lastname") String userLastName) {
    return "my value = " + userName + " " + userLastName;
  }
}
