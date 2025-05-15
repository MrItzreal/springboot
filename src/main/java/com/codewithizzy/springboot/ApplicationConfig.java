package com.codewithizzy.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class ApplicationConfig {

  @Bean("bean1")
  // @Profile("dev")
  public MyFirstClass myFirstBean() {
    return new MyFirstClass("First bean");
  }

  @Bean
  // @Profile("test")
  public MyFirstClass mySecondBean() {
    return new MyFirstClass("Second bean");
  }

  @Bean
  public MyFirstClass myThirdBean() {
    return new MyFirstClass("Third bean");
  }
}