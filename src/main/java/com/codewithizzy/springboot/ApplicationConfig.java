package com.codewithizzy.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

  @Bean("bean1")
  public MyFirstClass myFirstBean() {
    return new MyFirstClass("First bean");
  }

  @Bean("bean2")
  public MyFirstClass mySecondBean() {
    return new MyFirstClass("Second bean");
  }

  @Bean("bean3")
  public MyFirstClass myThirdBean() {
    return new MyFirstClass("Third bean");
  }
}