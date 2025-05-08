package com.codewithizzy.springboot;

import org.springframework.context.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringbootApplication.class, args);
		var orderService = context.getBean(OrderService.class);
		orderService.placeOrder();
	}
}
