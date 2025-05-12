package com.codewithizzy.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootApplication {

	public static void main(String[] args) {
		// 'ctx' short for context
		var ctx = SpringApplication.run(SpringbootApplication.class, args);

		MyFirstClass myFirstClass = ctx.getBean(MyFirstClass.class);
		System.out.println(myFirstClass.sayHello());
	}

	@Bean
	public MyFirstClass myFirstClass() {
		return new MyFirstClass();
	}
}
